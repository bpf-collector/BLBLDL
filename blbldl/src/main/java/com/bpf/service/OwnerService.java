package com.bpf.service;

import com.bpf.pojo.Owner;

import java.util.List;

public interface OwnerService {

    int insertOwner(Owner owner);

    Owner selectOwnerByMid(String mid);

    List<Owner> selectAllOwner();

    Owner selectOwnerById(Integer id);
}
