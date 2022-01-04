package com.Thread;

import com.gyq.dao.ProvinceCityDao;

public class TestThread extends Thread {
    private String pCity;

    public TestThread(String province_city) {
        this.pCity = province_city;
    }

    @Override
    public void run() {

        ProvinceCityDao dao = new ProvinceCityDao();
        dao.spiderPCityData(this.pCity);

    }
}
