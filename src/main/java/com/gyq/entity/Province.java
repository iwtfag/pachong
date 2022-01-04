package com.gyq.entity;

public class Province {
    private Integer pid;
    private Integer pname;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPname() {
        return pname;
    }

    public void setPname(Integer pname) {
        this.pname = pname;
    }

    @Override
    public String toString() {
        return "province{" +
                "pid=" + pid +
                ", pname=" + pname +
                '}';
    }
}
