package com.bpf.pojo;

public class Page {
    // 所属的课程代码
    private String bvId;
    // 视频编号
    private String cid;
    // 视频集数
    private int pageNo;
    // 视频标题
    private String part;
    // 视频时长
    private int duration;
    // 视频宽度
    private int width;
    // 视频高度
    private int height;
    // 视频质量
    private String quality;
    // 视频链接
    private String videoUrl;
    // 音频链接
    private String audioUrl;

    public Page() {}

    public Page(String bvId, String cid, int pageNo, String part, int duration) {
        this.bvId = bvId;
        this.cid = cid;
        this.pageNo = pageNo;
        this.part = part;
        this.duration = duration;
    }

    public String getBvId() {
        return bvId;
    }

    public void setBvId(String bvId) {
        this.bvId = bvId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Override
    public String toString() {
        return "Page{" +
                "bvId='" + bvId + '\'' +
                ", cid='" + cid + '\'' +
                ", pageNo=" + pageNo +
                ", part='" + part + '\'' +
                ", duration=" + duration +
                ", width=" + width +
                ", height=" + height +
                ", quality='" + quality + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                '}';
    }
}
