package com.bpf.dao;

import com.bpf.pojo.Owner;

import java.util.List;

public interface OwnerDao {

    int insertOwner(Owner owner);

    Owner queryOwnerByMid(String mid);

    Owner queryOwnerById(int id);

    int queryIdByMid(String mid);

    List<Owner> queryAllOwner();
}
