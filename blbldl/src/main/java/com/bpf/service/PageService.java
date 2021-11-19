package com.bpf.service;

import com.bpf.pojo.PageWithBLOBs;

import java.util.List;

public interface PageService {

    int insertPage(PageWithBLOBs page);

    PageWithBLOBs selectPageByCid(String cid);

    List<PageWithBLOBs> selectPagesByBvid(String bvid);

    PageWithBLOBs selectPageByBvidAndPageno(String bvid, Integer pageno);
}
