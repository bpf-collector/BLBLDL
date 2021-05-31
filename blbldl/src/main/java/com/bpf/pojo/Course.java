package com.bpf.pojo;

import java.util.Map;

public class Course {
    // 课程编号
    private String aid;
    // 课程代码
    private String bvId;
    // 课程标题
    private String title;
    // 课程总集数
    private int videos;
    // 课程介绍
    private String intro;
    // 课程所属的UP主
    private int ownerId;
    // 课程中已保存的视频数量
    private int saved = 0;
    // 课程的视频详情
    private Map<Integer, Page> pages;

    public Course() {}

    public Course(String aid, String bvId, String title, int videos, int ownerId) {
        this.aid = aid;
        this.bvId = bvId;
        this.title = title;
        this.videos = videos;
        this.ownerId = ownerId;
    }

    public Course(String aid, String bvId, String title, int videos, int ownerId, String intro) {
        this.aid = aid;
        this.bvId = bvId;
        this.title = title;
        this.videos = videos;
        this.intro = intro;
        this.ownerId = ownerId;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getBvId() {
        return bvId;
    }

    public void setBvId(String bvId) {
        this.bvId = bvId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public Map<Integer, Page> getPages() {
        return pages;
    }

    public void setPages(Map<Integer, Page> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Course{" +
                "aid='" + aid + '\'' +
                ", bvId='" + bvId + '\'' +
                ", title='" + title + '\'' +
                ", videos=" + videos +
                ", intro='" + intro + '\'' +
                ", ownerId=" + ownerId +
                ", pages=" + pages +
                '}';
    }
}
