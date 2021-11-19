package com.bpf.service;

import com.bpf.pojo.Course;

public interface CourseService {

    /**
     * 插入课程
     * @param course
     * @return
     */
    int insertCourse(Course course);

    Course selectCourseByBvid(String bvid);
}
