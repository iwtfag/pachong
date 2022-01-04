package com.gyq.dao;

import com.gyq.Utils.JDBCUtils;
import com.gyq.entity.YiQing;

import java.sql.*;

public class YiQingDao {


    /**
     * 存储疫情数据
     *
     * @param a 需要存储的疫情对象
     * @return 结果是-1时存储失败，1时成功
     */
    public static int saveData(YiQing a) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int ret = -1;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "insert into yiqing(ago_time,p_city,no_state_new,local_new)value(?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, a.getAgo_time());
            stmt.setString(2, a.getP_city());
            stmt.setString(3, a.getNo_state_new());
            stmt.setString(4, a.getLocal_new());
            ret = stmt.executeUpdate();
            if (ret != 1) {
                System.out.println("添加失败");
            } else {
                System.out.println("添加成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }
        return ret;

    }

    public YiQing QueryYiQing(String ago_time, String p_city) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        YiQing yiQing = null;
        try {
            conn = JDBCUtils.getJdbcConnection();
            String sql = "select ago_time,p_city,no_state_new,local_new from yiqing where ago_time=? and p_city=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, ago_time);
            stmt.setString(2, p_city);
            rs = stmt.executeQuery();

            if (rs.next()) {
                yiQing = new YiQing();
                yiQing.setAgo_time(rs.getString("ago_time"));
                yiQing.setP_city(rs.getString("p_city"));
                yiQing.setNo_state_new(rs.getString("no_state_new"));
                yiQing.setLocal_new(rs.getString("local_new"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return yiQing;
    }


}
