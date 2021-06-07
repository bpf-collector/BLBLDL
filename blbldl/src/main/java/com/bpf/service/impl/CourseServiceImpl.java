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

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao = new CourseDaoImpl();
    private OwnerDao ownerDao = new OwnerDaoImpl();
    private PageDao pageDao = new PageDaoImpl();

    private PageService pageService = new PageServiceImpl();

    @Override
    public JSONObject getCourseInfo(String url) {
        String data = null;
        try {
            Document doc = Jsoup.connect(url).get();
            String html = doc.head().html();
            String patt1 = "window.__INITIAL_STATE__=";
            String patt2 = ";(function(){var";
            int start = html.indexOf(patt1);
            int end = html.indexOf(patt2);
            data = html.substring(start + patt1.length(), end);
        } catch (ConnectException e) {
            // java.net.ConnectException: Connection timed out: connect
            System.out.println("[CourseServiceImpl] getCourseInfo 重新连接中...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            getCourseInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.fromObject(data);
    }

    @Override
    public void insertCourse(String url) {
        JSONObject courseInfo = getCourseInfo(url);
        JSONObject videoData = courseInfo.getJSONObject("videoData");

        // 添加并查询up主信息
        JSONObject owner = videoData.getJSONObject("owner");
        String mid = String.valueOf(owner.get("mid"));
        String name = String.valueOf(owner.get("name"));
        if (ownerDao.queryOwnerByMid(mid) == null) {
            // UP主还未添加
            ownerDao.insertOwner(new Owner(name, mid));
        }
        int ownerId = ownerDao.queryIdByMid(mid);

        // 添加课程信息
        String aid = String.valueOf(videoData.get("aid"));
        String bvId = String.valueOf(videoData.get("bvid"));
        String title = String.valueOf(videoData.get("title"));
        String intro = String.valueOf(videoData.get("desc"));
        int videos = Integer.parseInt(String.valueOf(videoData.get("videos")));
        if (courseDao.queryCourseByBvId(bvId) == null) {
            // 课程还未添加
            courseDao.insertCourse(new Course(aid, bvId, title, videos, ownerId, intro));
        }

        // 添加视频信息（部分信息）
        if (!isAllSaved(bvId)) {
            int saved = 0;
            JSONArray pages = videoData.getJSONArray("pages");
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

        // 更新视频信息
        String sourceUrl = url.split("\\?")[0];
        for (int i = 0; i <= videos; i++) {
            System.out.println("[CourseServiceImpl] 更新视频信息" + (i+1));
            String tUrl = sourceUrl + "?p=" + i;
            try {
                Map<String, Object> map = pageService.getPageInfoMap(tUrl);
                pageDao.updatePageByBvIdAndPageNo(map);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @Override
    public Course getCourseByBvId(String bvId) {
        return courseDao.queryCourseByBvId(bvId);
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
