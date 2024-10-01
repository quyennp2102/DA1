/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.NhanVien;
import com.g4.repository.G4Repository;
import com.g4.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NhanVienRepository extends G4Repository<NhanVien, String> {

    String insert_sql = "Insert into NhanVien(MatKhau, TenNhanVien, vaiTro, GioiTinh, Email, DiaChi, NgaySinh, SoDienThoai)values(?,?,?,?,?,?,?,?)";
    String update_sql = "Update NhanVien set MatKhau = ?, TenNhanVien = ?, VaiTro = ?, GioiTinh = ?, Email = ?, NgaySinh = ?, SoDienThoai = ?, DiaChi = ? Where Id = ?";
    String delete_sql = "Delete from NhanVien Where Id = ?";
    String select_all_sql = "select * from NhanVien";
    String select_by_id_sql = "Select * from NhanVien Where MaNV = ?";

    public static String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý nếu thuật toán mã hóa không được hỗ trợ
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(NhanVien entity) {
        String hashedPassword = encode(entity.getMatKhau());
        JdbcHelper.update(insert_sql, hashedPassword, entity.getTenNV(), entity.isVaiTro(), entity.getGioiTinh(), entity.getEmail(), entity.getDiaChi(), entity.getNgaySinh(), entity.getSdt());
    }

    @Override
    public void update(NhanVien entity) {
        String hashedPassword = encode(entity.getMatKhau());
        JdbcHelper.update(update_sql, hashedPassword, entity.getTenNV(), entity.isVaiTro(), entity.getGioiTinh(), entity.getEmail(), entity.getNgaySinh(), entity.getSdt(), entity.getDiaChi(), entity.getId());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(select_all_sql);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(select_by_id_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setId(rs.getString("Id"));
                entity.setTenNV(rs.getString("TenNhanVien"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setEmail(rs.getString("Email"));
                entity.setGioiTinh(rs.getInt("GioiTinh"));
                entity.setNgaySinh(rs.getString("NgaySinh"));
                entity.setSdt(rs.getString("SoDienThoai"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
        } catch (Exception e) {
        }
        return list;
    }

}
