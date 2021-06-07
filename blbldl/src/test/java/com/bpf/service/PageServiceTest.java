package com.bpf.service;

import com.bpf.pojo.Course;
import com.bpf.pojo.FileName;
import com.bpf.pojo.Page;
import com.bpf.service.impl.PageServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class PageServiceTest {

    private PageService pageService = new PageServiceImpl();

    @Test
    public void getPageInfo() {
        String url = "https://www.bilibili.com/video/BV1eJ411c7rf";
        JSONObject pageInfo = pageService.getPageInfo(url);

        JSONObject data = pageInfo.getJSONObject("data");
        JSONArray accept_description = data.getJSONArray("accept_description");
        JSONObject dash = data.getJSONObject("dash");

        int pageNo = 1;
        if (url.contains("p=")) {
            pageNo = Integer.parseInt(url.split("p=")[1]);
        }
        System.out.println("pageNo: " + pageNo);
        System.out.println("bvid: " + url.split("\\?")[0]
                .split("/")[url.split("/").length-1]);
        System.out.println("quality: " + accept_description.get(0));
        System.out.println("duration: " + dash.get("duration"));

        JSONObject video = dash.getJSONArray("video").getJSONObject(0);

        System.out.println("width: " + video.get("width"));
        System.out.println("height: " + video.get("height"));
        System.out.println("videoUrl: " + video.get("baseUrl"));
        System.out.println("audioUrl: " + dash.getJSONArray("audio")
                .getJSONObject(0).get("baseUrl"));
    }

    public void insertPage() {}

    @Test
    public void getPageInfoMap() {
        String url = "https://www.bilibili.com/video/BV1eJ411c7rf?p=2";
        Map<String, Object> map = pageService.getPageInfoMap(url);
        for (String key: map.keySet()) {
            System.out.println(key + ": " + map.get(key));
        }
    }

    public void getFileNameByPageNo() {}

}
