package com.bpf.dao.impl;

import com.bpf.dao.CourseDao;
import com.bpf.pojo.Course;
import com.bpf.pojo.Page;

import java.util.List;

public class CourseDaoImpl extends BaseDao implements CourseDao {
    @Override
    public int insertCourse(Course course) {
        String sql = "insert into course(aid,bvId,title,videos,intro,ownerId) values(?,?,?,?,?,?)";
        return update(sql, course.getAid(), course.getBvId(), course.getTitle(),
                course.getVideos(), course.getIntro(), course.getOwnerId());
    }

    @Override
    public int updateCourseSavedByBvId(String bvId, int saved) {
        String sql = "update course set saved = ? where bvId = ?";
        return update(sql, saved, bvId);
    }

    @Override
    public Course queryCourseByBvId(String bvId) {
        String sql = "select aid,bvId,title,videos,intro,ownerId,saved from course where bvId = ?";
        return queryForOne(Course.class, sql, bvId);
    }

    @Override
    public List<Page> queryPagesByBvId(String bvId) {
        String sql = "select * from page where bvId = ? order by pageNo";
        return queryForList(Page.class, sql, bvId);
    }

    @Override
    public List<Course> queryCourseByOwnerId(int ownerId) {
        String sql = "select aid,bvId,title,videos,intro,ownerId from course where ownerId = ?";
        return queryForList(Course.class, sql, ownerId);
    }

    @Override
    public List<Course> queryAllCourse() {
        String sql = "select aid,bvId,title,videos,intro,ownerId,saved from course";
        return queryForList(Course.class, sql);
    }

    @Override
    public List<Course> queryCourseLikeTitle(String title) {
        String sql = "select aid,bvId,title,videos,intro,ownerId,saved from course where title like ?";
        return queryForList(Course.class, sql, "%"+title+"%");
    }
}
