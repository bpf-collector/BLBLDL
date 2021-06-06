package com.bpf.dao;

import com.bpf.dao.impl.PageDaoImpl;
import com.bpf.pojo.Page;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PageDaoTest {

    private PageDao pageDao = new PageDaoImpl();

    @Test
    public void insertPage() {
        pageDao.insertPage(new Page("1010", "101001", 1, "首先", 60));
    }

    @Test
    public void updatePageByBvIdAndPageNo() {
        Map<String, Object> map = new HashMap<>();
        map.put("bvId", "1010");
        map.put("pageNo", 1);
        map.put("width", 1024);
        map.put("height", 768);
        map.put("quality", "高清 1080P");
        map.put("audioUrl", "http...");
        map.put("videoUrl", "http...");

        pageDao.updatePageByBvIdAndPageNo(map);
    }

    @Test
    public void queryPageByCid() {
        System.out.println(pageDao.queryPageByCid("101001"));
    }

    @Test
    public void queryUrlsByBvIdAndPageNo() {
        System.out.println(pageDao.queryUrlsByBvIdAndPageNo("1010", 1));
    }

}
