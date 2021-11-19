package com.bpf.service.impl;

import com.bpf.mapper.PageMapper;
import com.bpf.pojo.Page;
import com.bpf.pojo.PageExample;
import com.bpf.pojo.PageWithBLOBs;
import com.bpf.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageServiceImpl implements
        PageService {

    @Autowired
    private PageMapper pageMapper;

    @Override
    public int insertPage(PageWithBLOBs page) {
        return pageMapper.insertSelective(page);
    }

    @Override
    public PageWithBLOBs selectPageByCid(String cid) {
        return pageMapper.selectByPrimaryKey(cid);
    }

    @Override
    public List<PageWithBLOBs> selectPagesByBvid(String bvid) {
        PageExample example = new PageExample();
        example.createCriteria().andBvidEqualTo(bvid);
        List<PageWithBLOBs> pageList = pageMapper.selectByExampleWithBLOBs(example);

        return pageList.stream().sorted(Comparator.comparing(Page::getPageno)).collect(Collectors.toList());
    }

    @Override
    public PageWithBLOBs selectPageByBvidAndPageno(String bvid, Integer pageno) {
        PageExample example = new PageExample();
        example.createCriteria().andBvidEqualTo(bvid).andPagenoEqualTo(pageno);
        List<PageWithBLOBs> pageList = pageMapper.selectByExampleWithBLOBs(example);
        return pageList.get(0);
    }
}
