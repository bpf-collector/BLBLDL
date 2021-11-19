package com.bpf.service.impl;

import com.bpf.mapper.CourseMapper;
import com.bpf.pojo.Course;
import com.bpf.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public int insertCourse(Course course) {
        return courseMapper.insertSelective(course);
    }

    @Override
    public Course selectCourseByBvid(String bvid) {
        return courseMapper.selectByPrimaryKey(bvid);
    }
}
