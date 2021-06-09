package com.bpf.service.impl;

import com.bpf.dao.CourseDao;
import com.bpf.dao.OwnerDao;
import com.bpf.dao.impl.CourseDaoImpl;
import com.bpf.dao.impl.OwnerDaoImpl;
import com.bpf.pojo.Course;
import com.bpf.pojo.Owner;
import com.bpf.service.SearchService;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private CourseDao courseDao = new CourseDaoImpl();
    private OwnerDao ownerDao = new OwnerDaoImpl();

    @Override
    public List<Course> getAllCourse() {
        return courseDao.queryAllCourse();
    }

    @Override
    public List<Course> getCourseLikeTitle(String title) {
        return courseDao.queryCourseLikeTitle(title);
    }

    @Override
    public List<Owner> getAllOwner() {
        return ownerDao.queryAllOwner();
    }

    @Override
    public List<Course> getCourseByOwnerId(int ownerId) {
        return courseDao.queryCourseByOwnerId(ownerId);
    }
}
