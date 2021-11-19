package com.bpf.dao;

import com.bpf.pojo.Page;

import java.util.Map;

public interface PageDao {

    int insertPage(Page page);

    int updatePageByBvIdAndPageNo(Map<String, Object> map);

    Page queryPageByCid(String cid);

    Map<String, String> queryUrlsByBvIdAndPageNo(String bvId, int pageNo);

    String queryCidByBvIdAndPageNo(String bvId, int pageNo);
}
