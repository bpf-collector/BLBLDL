package com.bpf.dao;

import com.bpf.dao.impl.CourseDaoImpl;
import com.bpf.pojo.Course;
import com.bpf.pojo.Page;
import org.junit.Test;

import java.util.List;

public class CourseDaoTest {

    private CourseDao courseDao = new CourseDaoImpl();

    public void insertCourse() {}

    public void updateCourseSavedByBvId() {}

    @Test
    public void queryCourseByBvId() {
        String bvId = "BV17W411g7zP";
        Course course = courseDao.queryCourseByBvId(bvId);
        System.out.println(course);
    }

    @Test
    public void queryPagesByBvId() {
        String bvId = "BV17W411g7zP";
        courseDao.queryPagesByBvId(bvId).forEach(System.out::println);
    }

    @Test
    public void queryAllCourse() {
        courseDao.queryAllCourse().forEach(System.out::println);
    }

    @Test
    public void queryCourseLikeTitle() {
        List<Course> courses = courseDao.queryCourseLikeTitle("尚硅谷");
        System.out.println(courses);
    }
}
