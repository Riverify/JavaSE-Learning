package com.river.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 *  数据库工具类
 *  避免代码重复
 */
public abstract class DBUtil3 {
    private DBUtil3() {
    }

    static Connection conn = null;
    static String driver = "";
    static String url = "";
    static String user = "";
    static String password = "";


    static {
        // 创建Properties对象
        Properties prop = new Properties();

        // 读取属性文件
        InputStream is = DBUtil3.class.getResourceAsStream("/jdbc.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过key获取values;
        driver = prop.getProperty("driver");
        url = prop.getProperty("url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");

    }

    /**
     * 获取数据库的连接
     * @return
     */
    public static Connection getConnection() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 建立数据库连接
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭所有资源
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DML Update insert delete
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int n = 0; // 添加失败

        // 加载驱动
        try {
            conn = DBUtil3.getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            n = pstmt.executeUpdate();

            // 处理结果
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DBUtil3.closeAll(rs, pstmt, conn);
        }
        return n;
    }
}
