package com.gyq.Utils;

import java.sql.*;

public class JDBCUtils {
    private static String url = "jdbc:mysql://localhost:3306/bjpowernode?characterEncoding=utf-8&serverTimezone=UTC";
    private static String username = "root";
    private static String password = "root";
    private static String driver = "com.mysql.cj.jdbc.Driver";


    //数据库连接
    public static Connection getJdbcConnection() {
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(stmt, conn);
    }

    // 释放资源
    public static void close(PreparedStatement stmt, Connection conn) {
        if (stmt != null) {
            try {
                stmt.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
