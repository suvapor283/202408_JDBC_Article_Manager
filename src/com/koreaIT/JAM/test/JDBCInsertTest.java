package com.koreaIT.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/2024_08_JAM";
        String username = "root";
        String password = "";

        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection(url, username, password);
            
            String sql = "INSERT INTO article";
            sql += " regDate = NOW()";
            sql += ", updateDate = NOW()";
            sql += ", title = '제목'";
            sql += ", `body` = '내용';";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                	pstmt.close();
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
}