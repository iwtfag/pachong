package com.gyq.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gyq.Utils.JDBCUtils;
import com.gyq.entity.YiQing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceCityDao {
    public List<String> getCityList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> cityList = new ArrayList<String>();

        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "select province.pname,city.cname from city,province where province.pid=city.pid";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String pname = rs.getString("pname");
                String cname = rs.getString("cname");
                cityList.add(pname + "-" + cname);
                int index = 1;
                while (index<=cityList.size()){
                    index++;

                }
                System.out.println("全国的城市共有"+index+"个");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return cityList;


    }


    public StringBuffer spiderPCityData(String pCity) {
        StringBuffer Param = null;
        try {
            Param = new StringBuffer();
            Param.append("https://voice.baidu.com/newpneumonia/getv2?from=mola-virus&stage=publish&target=trendCity&area=").append(pCity);
            String a = Param.toString();
            Document document = Jsoup.connect(a).ignoreContentType(true).header("Content-Type", "application/json;charset=UTF-8").get();

            Elements elements = document.getElementsByTag("body");

            String text = elements.eq(0).text();
            JSONObject obj = JSON.parseObject(text);//obj是一个json对象


            JSONArray data = (JSONArray) obj.get("data");//data中所有数据


            //todo next line data get(0)
            JSONObject realData = (JSONObject) data.get(0);
            //获取城市
            String province_city = (String) realData.get("name");


            // 获取趋势
            JSONObject trend = (JSONObject) realData.get("trend");

            // 获取updateDate
            JSONArray updateDateList = trend.getJSONArray("updateDate");


            //JSONArray noStatusNew = trend.getJSONArray("list");
            JSONArray list = trend.getJSONArray("list");

            JSONObject o = (JSONObject) list.get(0);

            // 获取新增无状态:
            JSONArray noStatusList = o.getJSONArray("data");


            // 获取新增本土:TODO
            JSONObject o1 = (JSONObject) list.get(1);
            JSONArray localNewList = o1.getJSONArray("data");

            for (int i = 0; i < updateDateList.size(); i++) {
                String update_date = (String) updateDateList.get(i);
                int no_status_new = (Integer) noStatusList.get(i);
                int local_new = (Integer) localNewList.get(i);
                YiQing yq = new YiQing();
                yq.setP_city(province_city);
                yq.setNo_state_new(String.valueOf(no_status_new));
                yq.setLocal_new(String.valueOf(local_new));
                yq.setAgo_time(update_date);

                YiQingDao.saveData(yq);


                System.out.println(province_city + "," + update_date + "," + no_status_new + "," + local_new);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Param;
    }

}
