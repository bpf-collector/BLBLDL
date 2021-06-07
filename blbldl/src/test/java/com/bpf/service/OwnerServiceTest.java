package com.bpf.service;

import com.bpf.pojo.Owner;
import com.bpf.service.impl.OwnerServiceImpl;
import org.junit.Test;

public class OwnerServiceTest {

    private OwnerService ownerService = new OwnerServiceImpl();

    @Test
    public void insertOwner() {
        ownerService.insertOwner(new Owner("bpf", "0070"));
    }

    @Test
    public void getOwnerByMid() {
        System.out.println(ownerService.getOwnerByMid("0070"));
    }
}
