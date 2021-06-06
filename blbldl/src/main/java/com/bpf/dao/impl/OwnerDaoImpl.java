package com.bpf.dao.impl;

import com.bpf.dao.OwnerDao;
import com.bpf.pojo.Owner;

public class OwnerDaoImpl extends BaseDao implements OwnerDao {
    @Override
    public int insertOwner(Owner owner) {
        String sql = "insert into owner(name,mid) values(?,?)";
        return update(sql, owner.getName(), owner.getMid());
    }

    @Override
    public Owner queryOwnerByMid(String mid) {
        String sql = "select * from owner where mid = ?";
        return queryForOne(Owner.class, sql, mid);
    }

    @Override
    public Owner queryOwnerById(int id) {
        String sql = "select * from owner where id = ?";
        return queryForOne(Owner.class, sql, id);
    }

    @Override
    public int queryIdByMid(String mid) {
        Owner owner = queryOwnerByMid(mid);
        if (owner != null) {
            return owner.getId();
        }

        return -1;
    }
}
