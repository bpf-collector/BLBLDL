package com.bpf.dao.impl;

import com.bpf.utils.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {

    private QueryRunner queryRunner = new QueryRunner();

    public int update(String sql, Object ... args) {
        Connection conn = JDBCUtil.getConnection();
        try {
            return queryRunner.update(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
        return -1;
    }

    public <T> T queryForOne(Class<T> clazz, String sql, Object ... args) {
        Connection conn = JDBCUtil.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
        return null;
    }

    public <T> List<T> queryForList(Class<T> clazz, String sql, Object ... args) {
        Connection conn = JDBCUtil.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanListHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
        return null;
    }

    public Object queryForValue(String sql, Object ... args) {
        Connection conn = JDBCUtil.getConnection();
        try {
            return queryRunner.query(conn, sql, new ScalarHandler(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
        return null;
    }
}
