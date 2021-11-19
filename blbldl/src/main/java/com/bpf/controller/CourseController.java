package com.bpf.controller;

import com.bpf.pojo.Course;
import com.bpf.pojo.Owner;
import com.bpf.pojo.Page;
import com.bpf.pojo.PageWithBLOBs;
import com.bpf.service.CourseService;
import com.bpf.service.OwnerService;
import com.bpf.service.PageService;
import com.bpf.vo.CourseVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private PageService pageService;

    /**
     * 查询课程下每个视频的下载链接
     * @param bvid
     * @return
     */
    @GetMapping("/urls")
    @ResponseBody
    public Map<Integer, String> getPageUrls(String bvid) {
        List<PageWithBLOBs> pages = pageService.selectPagesByBvid(bvid);
        Map<Integer, String> map = new HashMap<>();

        for (PageWithBLOBs page : pages) {
            try {
                String downUrl = connGetPageUrl(bvid, page.getCid());
                map.put(page.getPageno(), downUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 访问接口查询page的下载链接
     * @param bvid
     * @param cid
     * @return
     * @throws IOException
     */
    private String connGetPageUrl(String bvid, String cid) throws IOException {
        String url = "http://api.bilibili.com/x/player/playurl?qn=80&bvid=" + bvid + "&cid=" + cid;
        JSONObject data = connect(url);
        String downUrl = data.getJSONObject("data").getJSONArray("durl").getJSONObject(0).getString("url");

        return downUrl;
    }

    /**
     * 下载课程的第几个视频
     * @param bvid
     * @param pageno
     * @param response
     * @return
     */
    @GetMapping("/url")
    @ResponseBody
    public String download(String bvid, Integer pageno, HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            PageWithBLOBs page = pageService.selectPageByBvidAndPageno(bvid, pageno);
            response.setCharacterEncoding("UTF-8");
            String filename = new String(page.getPart().getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".flv\"");
            System.out.println("[CourseController] 请求下载 " + page.getPart() + ".flv");

            String downUrl = connGetPageUrl(bvid, page.getCid());

            URL u = new URL(downUrl);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("referer", "https://player.bilibili.com/");

            bis = new BufferedInputStream(conn.getInputStream());
            bos = new BufferedOutputStream(response.getOutputStream());
            byte [] buffer = new byte[1024];
            int len = 0;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Success";
    }

    /**
     * 查找课程、插入课程
     * @param url
     * @param model
     * @return
     */
    @GetMapping("search")
    public String searchCourse(String url, Model model) {
            String bvId = splitBvId(url);
            CourseVo courseVo = new CourseVo();

            Course course = courseService.selectCourseByBvid(bvId);
            if (course == null) {
                System.out.println("课程还没保存");
                // 数据库还没保存
                try {
                    courseVo = insertCourse(bvId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("课程已保存");
                System.out.println(course);
                BeanUtils.copyProperties(course, courseVo);
                courseVo.setOwner(ownerService.selectOwnerById(course.getOwnerid()));
                List<PageWithBLOBs> pages = pageService.selectPagesByBvid(bvId);
                courseVo.setPageList(pages);
            }

            model.addAttribute("course", courseVo);

        return "result";
    }

    private CourseVo insertCourse(String bvId) throws IOException {

        JSONObject data = connect("http://api.bilibili.com/x/web-interface/view?bvid=" + bvId);
        CourseVo courseVo = new CourseVo();

        // 解析json
        if (data.getInt("code") == 0) {
            // 请求成功
            data = data.getJSONObject("data");

            // 1. UP信息 owner
            JSONObject owner = data.getJSONObject("owner");
            String mid = String.valueOf(owner.getInt("mid"));
            Owner own = ownerService.selectOwnerByMid(mid);
            if (own == null) {
                // UP还未存储
                own = new Owner();
                own.setMid(mid);
                own.setName(owner.getString("name"));
                ownerService.insertOwner(own);
            }

            // 2. 课程信息 course
            Course course = new Course();
            course.setAid(data.getString("aid"));
            course.setBvid(data.getString("bvid"));
            course.setVideos(data.getInt("videos"));
            course.setTitle(data.getString("title"));
            course.setIntro(data.getString("desc"));
            course.setOwnerid(own.getId());

            courseService.insertCourse(course);

            // 3. page
            JSONArray pages = data.getJSONArray("pages");
            List<PageWithBLOBs> pageList = new LinkedList<>();
            for (int i = 0; i < pages.size(); i++) {
                JSONObject pageJson = pages.getJSONObject(i);
                PageWithBLOBs page = new PageWithBLOBs();
                page.setCid(pageJson.getString("cid"));
                page.setPageno(pageJson.getInt("page"));
                page.setPart(pageJson.getString("part"));
                page.setDuration(pageJson.getInt("duration"));
                page.setBvid(course.getBvid());
                pageList.add(page);
                pageService.insertPage(page);
            }

            BeanUtils.copyProperties(course, courseVo);
            courseVo.setOwner(own);
            courseVo.setPageList(pageList);
        }

        return courseVo;
    }

    private String splitBvId(String url) {
        // https://www.bilibili.com/video/BV1cV411p7jg?p=34
        String[] split1 = url.split("\\?");
        String[] split2 = split1[0].split("/");
        return split2[split2.length-1];
    }

    private JSONObject connect(String url) throws IOException {

        StringBuilder builder = new StringBuilder();
        URLConnection conn = new URL(url).openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String str = null;
        while ((str = br.readLine()) != null) {
            builder.append(str);
        }

        return JSONObject.fromObject(builder.toString());
    }
}
