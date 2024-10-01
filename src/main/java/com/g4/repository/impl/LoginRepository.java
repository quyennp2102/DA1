package com.g4.repository.impl;

import com.g4.entity.NhanVien;

import com.g4.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoginRepository {

    String slect_by_user_sql = "SELECT * FROM NhanVien WHERE Email =?";
    String slect_all_sql = "SELECT * FROM NhanVien";

    public NhanVien selectByUser(String user) {
        List<NhanVien> list = selectBySql(slect_by_user_sql, user);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setId(rs.getString("Id"));
                entity.setTenNV(rs.getString("TenNhanVien"));
                entity.setEmail(rs.getString("Email"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));   
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
