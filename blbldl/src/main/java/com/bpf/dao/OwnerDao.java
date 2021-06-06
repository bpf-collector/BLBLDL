package com.bpf.dao;

import com.bpf.pojo.Owner;

public interface OwnerDao {

    int insertOwner(Owner owner);

    Owner queryOwnerByMid(String mid);

    Owner queryOwnerById(int id);

    int queryIdByMid(String mid);
}
