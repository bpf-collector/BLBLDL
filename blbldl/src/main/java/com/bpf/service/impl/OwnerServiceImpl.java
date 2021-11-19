package com.bpf.service.impl;

import com.bpf.mapper.OwnerMapper;
import com.bpf.pojo.Owner;
import com.bpf.pojo.OwnerExample;
import com.bpf.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerMapper ownerMapper;


    @Override
    public int insertOwner(Owner owner) {
        return ownerMapper.insertSelective(owner);
    }

    @Override
    public Owner selectOwnerByMid(String mid) {
        OwnerExample example = new OwnerExample();
        example.createCriteria().andMidEqualTo(mid);
        List<Owner> owners = ownerMapper.selectByExample(example);
        return owners.get(0);
    }

    @Override
    public List<Owner> selectAllOwner() {
        return ownerMapper.selectByExample(new OwnerExample());
    }

    @Override
    public Owner selectOwnerById(Integer id) {
        return ownerMapper.selectByPrimaryKey(id);
    }
}
