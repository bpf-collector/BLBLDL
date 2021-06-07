package com.bpf.service;

import com.bpf.pojo.Course;
import com.bpf.service.impl.CourseServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

public class CourseServiceTest {

    private CourseService courseService = new CourseServiceImpl();

    @Test
    public void getCourseInfo() {
        String url = "https://www.bilibili.com/video/BV1eJ411c7rf";
        JSONObject courseInfo = courseService.getCourseInfo(url);

        JSONObject videoData = courseInfo.getJSONObject("videoData");
        JSONObject owner = videoData.getJSONObject("owner");
        JSONArray pages = videoData.getJSONArray("pages");
        JSONObject page_1 = pages.getJSONObject(0);

        System.out.println("aid: " + videoData.get("aid"));
        System.out.println("bvid: " + videoData.get("bvid"));
        System.out.println("videos: " + videoData.get("videos"));
        System.out.println("title: " + videoData.get("title"));

        System.out.println("owner: " + owner.get("mid") + ", " + owner.get("name"));

        System.out.println("pages: ");
        System.out.println("\t[1]: ");
        System.out.println("\t\tcid: " + page_1.get("cid"));
        System.out.println("\t\tpage: " + page_1.get("page"));
        System.out.println("\t\tpart: " + page_1.get("part"));
        System.out.println("\t\tduration: " + page_1.get("duration"));
    }

    @Test
    public void insertCourse() {
        String url = "https://www.bilibili.com/video/BV1eJ411c7rf?p=2";
        courseService.insertCourse(url);
    }

    @Test
    public void getCourseByBvId() {
        String bvId = "BV1j7411Q7hQ";
        Course course = courseService.getCourseByBvId(bvId);
        System.out.println(course);
    }

    @Test
    public void isAllSaved() {
        String bvId = "BV1j7411Q7hQ";
        System.out.println(courseService.isAllSaved(bvId));
    }
}
