package com.bpf.service.impl;

import com.bpf.dao.OwnerDao;
import com.bpf.dao.impl.OwnerDaoImpl;
import com.bpf.pojo.Owner;
import com.bpf.service.OwnerService;

public class OwnerServiceImpl implements OwnerService {

    private OwnerDao ownerDao = new OwnerDaoImpl();

    @Override
    public int insertOwner(Owner owner) {
        return ownerDao.insertOwner(owner);
    }

    @Override
    public Owner getOwnerByMid(String mid) {
        return ownerDao.queryOwnerByMid(mid);
    }
}
