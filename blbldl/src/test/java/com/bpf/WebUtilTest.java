package com.bpf;

import com.bpf.dao.CourseDao;
import com.bpf.dao.impl.CourseDaoImpl;
import com.bpf.utils.WebUtil;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.*;

public class WebUtilTest {

    @Test
    public void connectURL() throws IOException {
        String url = "https://upos-sz-mirrorks3.bilivideo.com/upgcxcode/11/26/52432611/52432611_da2-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1619435232&gen=playurlv2&os=ks3bv&oi=3085848335&trid=ff1d2188d1744bddb3d098aca8145aaeu&platform=pc&upsig=219fb205694afe08ea571565f423061a&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=0&orderid=1,3&agrr=1&logo=40000000";
               url = "https://upos-sz-mirrorks3.bilivideo.com/upgcxcode/11/26/52432611/52432611_da2-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1619446825&gen=playurlv2&os=ks3bv&oi=3401598977&trid=f4f72e02c8654d4e81279aaddbf495b6u&platform=pc&upsig=aecaa96239ac4b3a8723d50e44c156c9&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=0&orderid=1,3&agrr=0&logo=40000000";
        String refererUrl = "http://bilibili.com/BV1PW411X75p?p=3";
        BufferedInputStream bis = WebUtil.connectURL(url, refererUrl);
        System.out.println(bis);
    }

    @Test
    public void deleteFile() {
        String filepath = "E:\\tmp\\13_尚硅谷_修改及自定义模板_.mp4";
        WebUtil.deleteFile(filepath);
    }

    /**
     * 获取某个具体视频详细信息
     * @throws IOException
     */
    @Test
    public void testConnect() throws IOException {
        String url = "https://www.bilibili.com/video/BV1kv411q7Qc?p=1";
        Document document = Jsoup.connect(url).get();
        String html = document.head().html();
        String patt1 = "window.__INITIAL_STATE__=";
        String patt2 = ";(function(){";
        int start = html.indexOf(patt1);
        int end = html.indexOf(patt2);
        String data = html.substring(start + patt1.length(), end);
        System.out.println(data);
    }

    /**
     * 获取视频详细信息
     */
    @Test
    public void testGetCourseInfo() throws IOException {
        String urls = "http://api.bilibili.com/x/web-interface/view?bvid=";
        String bvId = "BV1kv411q7Qc";

        BufferedReader br = null;
        StringBuilder builder = null;
        try {
            URL url = new URL(urls + bvId);
            URLConnection connection = url.openConnection();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            builder = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null) {
                builder.append(str);
            }

            JSONObject json = JSONObject.fromObject(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }

    @Test
    public void testGetBvIdFromUrl() {
        String url = "https://www.bilibili.com/video/BV1kv411q7Qc?p=11";
        String[] split = url.split("\\?")[0].split("/");
        String bvId = split[split.length-1];

        System.out.println(url);
        System.out.println(bvId);
    }

    @Test
    public void testDownload() throws IOException {
        // http://api.bilibili.com/x/player/playurl?bvid=BV1B7411L7tE&cid=153705215&qn=80&fnval=16
        String url = "https://cn-gdgz-fx-bcache-05.bilivideo.com/upgcxcode/15/52/153705215/153705215_nb2-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1635774623&gen=playurlv2&os=bcache&oi=3525705766&trid=0000d45bfe663d45496f99d3e5a287148f65u&platform=pc&upsig=e36811d5c44bd600e7eb3e84e100deca&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&cdnid=3805&mid=427184223&bvc=vod&nettype=0&orderid=0,3&agrr=0&logo=80000000";
        String path = "E:/tmp/tmp.mp3";
        WebUtil.downloadFile(url, path, "");
        BufferedInputStream bis = WebUtil.connectURL(url, "");
        System.out.println("bis: " + bis.available());
        File file = new File(path);
        System.out.println("file: " + file.length());

    }
}