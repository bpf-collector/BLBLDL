package com.bpf.service;

import com.bpf.pojo.Course;
import com.bpf.pojo.FileName;
import com.bpf.pojo.Page;
import com.bpf.service.impl.CourseServiceImpl;
import com.bpf.service.impl.PageServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class PageServiceTest {

    private PageService pageService = new PageServiceImpl();
    private CourseService courseService = new CourseServiceImpl();

    @Test
    public void getPageInfo() {
        String bvId = "BV1kv411q7Qc";
        int pageNo = 2;
        String aId = courseService.getAIdByBvId(bvId);
        String cid = pageService.getCidByBvIdAndPageNo(bvId, aId, pageNo);
        JSONObject pageInfo = pageService.getPageInfo(bvId, cid);

        JSONObject data = pageInfo.getJSONObject("data");
        JSONArray accept_description = data.getJSONArray("accept_description");
        JSONObject dash = data.getJSONObject("dash");

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
        String bvId = "BV1kv411q7Qc";
        String aId = courseService.getAIdByBvId(bvId);
        int pageNo = 2;
        String cid = pageService.getCidByBvIdAndPageNo(bvId, aId, pageNo);
        Map<String, Object> map = pageService.getPageInfoMap(bvId, aId, pageNo);
        for (String key: map.keySet()) {
            System.out.println(key + ": " + map.get(key));
        }
    }

    public void getFileNameByPageNo() {}

}
