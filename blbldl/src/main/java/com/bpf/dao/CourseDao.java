package com.bpf.dao;

import com.bpf.pojo.Course;
import com.bpf.pojo.Page;

import java.util.List;

public interface CourseDao {

    int insertCourse(Course course);

    int updateCourseSavedByBvId(String bvId, int saved);

    Course queryCourseByBvId(String bvId);

    List<Page> queryPagesByBvId(String bvId);

    List<Course> queryCourseByOwnerId(int ownerId);

    List<Course> queryAllCourse();

    List<Course> queryCourseLikeTitle(String title);
}
