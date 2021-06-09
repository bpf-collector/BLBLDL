package com.bpf.service;

import com.bpf.pojo.Course;
import com.bpf.pojo.Owner;

import java.util.List;

public interface SearchService {

    List<Course> getAllCourse();

    List<Course> getCourseLikeTitle(String title);

    List<Owner> getAllOwner();

    List<Course> getCourseByOwnerId(int ownerId);
}
