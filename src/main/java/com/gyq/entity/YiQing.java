package com.gyq.entity;

public class YiQing {
    private String ago_time;
    private String p_city;
    private String no_state_new;
    private String local_new;

    public String getAgo_time() {
        return ago_time;
    }

    public void setAgo_time(String ago_time) {
        this.ago_time = ago_time;
    }

    public String getP_city() {
        return p_city;
    }

    public void setP_city(String p_city) {
        this.p_city = p_city;
    }

    public String getNo_state_new() {
        return no_state_new;
    }

    public void setNo_state_new(String no_state_new) {
        this.no_state_new = no_state_new;
    }

    public String getLocal_new() {
        return local_new;
    }

    public void setLocal_new(String local_new) {
        this.local_new = local_new;
    }

    @Override
    public String toString() {
        return "YiQing{" +
                "ago_time='" + ago_time + '\'' +
                ", p_city='" + p_city + '\'' +
                ", no_state_new='" + no_state_new + '\'' +
                ", local_new='" + local_new + '\'' +
                '}';
    }
}

