package com.bpf.pojo;

public class FileName {
    // 资源路径
    private String sourceUrl;
    // 视频链接
    private String videoUrl;
    // 视频下载路径
    private String videoPath;
    // 音频链接
    private String audioUrl;
    // 音频下载路径
    private String audioPath;
    // 合成路径
    private String outputPath;

    // 暂停时间
    private int sleep;

    public FileName() {
    }

    /**
     * 用于测试
     */
    public FileName(int sleep) {
        this.sleep = sleep;
    }

    public FileName(String sourceUrl, String videoUrl, String videoPath, String audioUrl, String audioPath, String outputPath, int sleep) {
        this.sourceUrl = sourceUrl;
        this.videoUrl = videoUrl;
        this.videoPath = videoPath;
        this.audioUrl = audioUrl;
        this.audioPath = audioPath;
        this.outputPath = outputPath;
        this.sleep = sleep;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    @Override
    public String toString() {
        return "FileName{" +
                "sourceUrl='" + sourceUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoPath='" + videoPath + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", audioPath='" + audioPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", sleep=" + sleep +
                '}';
    }
}
