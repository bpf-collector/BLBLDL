package com.bpf.service;

import com.bpf.pojo.Owner;

public interface OwnerService {

    int insertOwner(Owner owner);

    Owner getOwnerByMid(String mid);


}
