package com.bpf.dao.impl;

import com.bpf.dao.PageDao;
import com.bpf.pojo.Page;

import java.util.HashMap;
import java.util.Map;

public class PageDaoImpl extends BaseDao implements PageDao {
    @Override
    public int insertPage(Page page) {
        String sql = "insert into page(bvId,cid,pageNo,part,duration) values(?,?,?,?,?)";
        return update(sql, page.getBvId(), page.getCid(), page.getPageNo(),
                page.getPart(), page.getDuration());
    }

    @Override
    public int updatePageByBvIdAndPageNo(Map<String, Object> map) {
        String sql = "update page set width=?,height=?,quality=?,videoUrl=?,audioUrl=? where bvId=? and pageNo = ?";
        return update(sql, map.get("width"), map.get("height"), map.get("quality"),
                map.get("videoUrl"), map.get("audioUrl"), map.get("bvId"), map.get("pageNo"));
    }

    @Override
    public Page queryPageByCid(String cid) {
        String sql = "select * from page where cid = ?";
        return queryForOne(Page.class, sql, cid);
    }

    @Override
    public Map<String, String> queryUrlsByBvIdAndPageNo(String bvId, int pageNo) {
        String sql1 = "select videoUrl from page where bvId = ? and pageNo = ?";
        String sql2 = "select audioUrl from page where bvId = ? and pageNo = ?";
        Object videoUrl = queryForValue(sql1, bvId, pageNo);
        Object audioUrl = queryForValue(sql2, bvId, pageNo);

        Map<String, String> map = new HashMap<>();
        if (videoUrl != null) {
            map.put("videoUrl", String.valueOf(videoUrl));
            map.put("audioUrl", String.valueOf(audioUrl));
        }

        return map;
    }

    @Override
    public String queryCidByBvIdAndPageNo(String bvId, int pageNo) {
        String sql = "select cid from page where bvId = ? and pageNo = ?";
        Object value = queryForValue(sql, bvId, pageNo);

        return String.valueOf(value);
    }
}
