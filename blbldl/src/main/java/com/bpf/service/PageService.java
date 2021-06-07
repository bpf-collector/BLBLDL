package com.bpf.service;

import com.bpf.pojo.Course;
import com.bpf.pojo.FileName;
import com.bpf.pojo.Page;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface PageService {

    JSONObject getPageInfo(String url);

    int insertPage(Page page);

    Map<String, Object> getPageInfoMap(String url);

    List<FileName> getFileNameByPageNo(Course course,
        int[] pageNo, String tmpPath, String outPath, int sleep);
}
