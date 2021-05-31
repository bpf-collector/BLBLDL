package com.bpf.pojo;

/**
 * UP主信息
 */
public class Owner {
    // 数据库用户编号
    private int id;
    // 用户昵称
    private String name;
    // 用户编号
    private String mid;

    public Owner() {}

    public Owner(String name, String mid) {
        this.name = name;
        this.mid = mid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mid='" + mid + '\'' +
                '}';
    }
}
