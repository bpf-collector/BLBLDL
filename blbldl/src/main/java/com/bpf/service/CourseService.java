package com.bpf.service;

import com.bpf.pojo.Course;
import net.sf.json.JSONObject;

public interface CourseService {

    JSONObject getCourseInfo(String url);

    void insertCourse(String url);

    Course getCourseByBvId(String bvId);

    boolean isAllSaved(String bvId);

    void updateCourse(String url, int[] pageNo);

    String getAIdByBvId(String bvId);
}
