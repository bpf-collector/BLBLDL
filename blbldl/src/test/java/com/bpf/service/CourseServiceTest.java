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
        String bvId = "BV1eJ411c7rf";
        bvId = "BV1B7411L7tE";
        JSONObject courseInfo = courseService.getCourseInfo(bvId);

        if(courseInfo == null) {
            System.out.println("数据为空...");
            return;
        }

        JSONObject data = courseInfo.getJSONObject("data");
        JSONObject owner = data.getJSONObject("owner");
        JSONArray pages = data.getJSONArray("pages");
        JSONObject page_1 = pages.getJSONObject(0);

        System.out.println("aid: " + data.get("aid"));
        System.out.println("bvid: " + data.get("bvid"));
        System.out.println("videos: " + data.get("videos"));
        System.out.println("title: " + data.get("title"));

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
        url = "https://www.bilibili.com/video/BV1B7411L7tE?p=2";
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
