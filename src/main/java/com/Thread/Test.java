package com.Thread;

//import com.gyq.dao.ProCityDao;
import com.gyq.dao.ProvinceCityDao;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        ProvinceCityDao pDao = new ProvinceCityDao();
        List<String> pList = pDao.getCityList();

        int index = 1;
        while(index < pList.size()){

            TestThread tt = new TestThread(pList.get(index));
            tt.start();
            index++;
        }





    }


}
