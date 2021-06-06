package com.bpf.dao;

import com.bpf.dao.impl.OwnerDaoImpl;
import com.bpf.pojo.Owner;
import org.junit.Test;

public class OwnerDaoTest {

    private OwnerDao ownerDao = new OwnerDaoImpl();

    @Test
    public void insertOwner() {
        ownerDao.insertOwner(new Owner("bpf", "007"));
    }

    @Test
    public void queryOwnerByMid() {
        System.out.println(ownerDao.queryOwnerByMid("007"));
    }

    @Test
    public void queryOwnerById() {
        System.out.println(ownerDao.queryOwnerById(9));
    }

    @Test
    public void queryIdByMid() {
        System.out.println(ownerDao.queryIdByMid("0070"));
    }
}
