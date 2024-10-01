package com.g4.utils;

import java.sql.*;

public class JdbcHelper {//Lớp này giải quyết kết nối xử lý truy vấn

    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String connectionSql = "jdbc:sqlserver://localhost:1433;databaseName=QLBG_DUAN1_JAVA";
    private static String acc = "duc";
    private static String pass = "123";

    private static Connection conn;

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            System.out.println("Lỗi Driver");
        }
    }

    //1. Mở kết nối
    public static Connection openDbConnection() {
        try {
            return DriverManager.getConnection(connectionSql, acc, pass);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //2. Thực thi truy vấn Thêm, Sửa , Xoá
    public static int update(String sql, Object... args) {
        PreparedStatement pstm = getStmt(sql, args);
        try {
            try {
                return pstm.executeUpdate();
            } finally {
                pstm.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //3. Trả lại 1 tập đối tượng
    public static ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement pstm = getStmt(sql, args);
        return pstm.executeQuery();
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //4. Chuẩn bị câu truy vấn trước khi thực thi - Các varargs sử dụng dấu ba chấm (...) sau kiểu dữ liệu.
    public static PreparedStatement getStmt(String sql, Object... args) {
        try {
            conn = openDbConnection();
            PreparedStatement ps;
            //ps = conn.prepareCall(sql) Gọi store procedure
            ps = conn.prepareStatement(sql);//Dùng để triển khai các câu lệnh truy vấn thường
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);//Cộng các value sau câu truy vấn
            }
            return ps;
        } catch (SQLException ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(openDbConnection());
    }
}
