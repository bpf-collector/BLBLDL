package com.bpf.service.impl;

import com.bpf.dao.CourseDao;
import com.bpf.dao.OwnerDao;
import com.bpf.dao.PageDao;
import com.bpf.dao.impl.CourseDaoImpl;
import com.bpf.dao.impl.OwnerDaoImpl;
import com.bpf.dao.impl.PageDaoImpl;
import com.bpf.pojo.Course;
import com.bpf.pojo.Owner;
import com.bpf.pojo.Page;
import com.bpf.service.CourseService;
import com.bpf.service.PageService;
import com.bpf.utils.FileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao = new CourseDaoImpl();
    private OwnerDao ownerDao = new OwnerDaoImpl();
    private PageDao pageDao = new PageDaoImpl();

    private PageService pageService = new PageServiceImpl();

    @Override
    public JSONObject getCourseInfo(String bvId) {
        String urls = "http://api.bilibili.com/x/web-interface/view?bvid=";

        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(urls + bvId);
            URLConnection connection = url.openConnection();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str = null;
            while ((str = br.readLine()) != null) {
                builder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return JSONObject.fromObject(builder.toString());
    }

    @Override
    public void insertCourse(String url) {
        String[] split = url.split("\\?")[0].split("/");
        String bvId = split[split.length - 1];
        JSONObject courseInfo = getCourseInfo(bvId);

        JSONObject data = courseInfo.getJSONObject("data");

        // 添加并查询up主信息
        JSONObject owner = data.getJSONObject("owner");
        String mid = String.valueOf(owner.get("mid"));
        String name = String.valueOf(owner.get("name"));
        if (ownerDao.queryOwnerByMid(mid) == null) {
            // UP主还未添加
            ownerDao.insertOwner(new Owner(name, mid));
        }
        int ownerId = ownerDao.queryIdByMid(mid);

        // 添加课程信息
        String aid = String.valueOf(data.get("aid"));
        String title = String.valueOf(data.get("title"));
        String intro = String.valueOf(data.get("desc"));
        int videos = Integer.parseInt(String.valueOf(data.get("videos")));
        if (courseDao.queryCourseByBvId(bvId) == null) {
            // 课程还未添加
            int insert = courseDao.insertCourse(new Course(aid, bvId, title, videos, ownerId, intro));
            System.out.println("添加课程: " + bvId + ", " + insert);
        }

        // 添加视频信息（部分信息）
        if (!isAllSaved(bvId)) {
            int saved = 0;
            JSONArray pages = data.getJSONArray("pages");
            for (int i = 0; i < videos; i++) {
                System.out.println("[CourseServiceImpl] 添加视频信息" + (i+1));
                JSONObject pageJson = pages.getJSONObject(i);
                String cid = String.valueOf(pageJson.get("cid"));
                if (pageDao.queryPageByCid(cid) != null) {
                    // 已添加该视频
                    continue;
                }
                int pageNo = (int) pageJson.get("page");
                String part = String.valueOf(pageJson.get("part"));
                part = FileUtil.replaceSign(part);
                int duration = (int) pageJson.get("duration");
                pageDao.insertPage(new Page(bvId, cid, pageNo, part, duration));
                saved++;
            }

            courseDao.updateCourseSavedByBvId(bvId, saved);
        }
    }

    public void updateCourse(String bvId, int[] pageNos) {
        // 更新视频信息
        for (int pageNo : pageNos) {
            updateCourse(bvId, pageNo);
        }
    }

    @Override
    public String getAIdByBvId(String bvId) {
        return courseDao.queryAIdByBvId(bvId);
    }

    public void updateCourse(String bvId, int pageNo) {
        // 更新视频信息
        System.out.println("[CourseServiceImpl] 更新视频信息" + pageNo);
        try {
            String aId = getAIdByBvId(bvId);
            Map<String, Object> map = pageService.getPageInfoMap(bvId, aId, pageNo);
            pageDao.updatePageByBvIdAndPageNo(map);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    @Override
    public Course getCourseByBvId(String bvId) {
        Course course = courseDao.queryCourseByBvId(bvId);
        List<Page> pages = courseDao.queryPagesByBvId(bvId);
        Map<Integer, Page> map = new HashMap<>();
        for (Page page: pages) {
            map.put(page.getPageNo(), page);
        }
        course.setPages(map);

        return course;
    }

    @Override
    public boolean isAllSaved(String bvId) {
        Course course = courseDao.queryCourseByBvId(bvId);
        if (course != null) {
            return course.getVideos() == course.getSaved();
        }

        return false;
    }
}
