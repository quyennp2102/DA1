/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.repository.G4Repository;
import com.g4.viewmodel.KhuyenMaiViewModel;
import com.g4.entity.KhuyenMai;
import com.g4.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author khuong duc
 */
public class KhuyenMaiRepository extends G4Repository<KhuyenMai, String>{
    String insert_sql = "Insert into KhuyenMai(TenKhuyenMai, MoTa, KieuGiamGia, MucGIamGia, NgayBatDau, NgayKetThuc) values(?,?,?,?,?,?)";
    String update_sql = "Update KhuyenMai set TenKhuyenMai=?, MoTa=?, KieuGiamGia=?, MucGIamGia=?, NgayBatDau=?, NgayKetThuc=? Where id = ?";
    String delete_sql = "Delete from KhuyenMai Where id = ?";
    String select_all_sql = "select * from KhuyenMai where TrangThai like 1 and TenKhuyenMai like ?";
    String select_by_id_sql = "Select * from KhuyenMai Where id = ?";
    
    @Override
    public void insert(KhuyenMai entity) {
        JdbcHelper.update(insert_sql, entity.getTenKM(), entity.getMoTa(), entity.isKieugiamGia(), entity.getMuctramGiam(), entity.getNgaybatDau(), entity.getNgayketThuc());
    }

    @Override
    public void update(KhuyenMai entity) {
        JdbcHelper.update(update_sql, entity.getId(), entity.getTenKM(), entity.getMoTa(), entity.isKieugiamGia(), entity.getMuctramGiam(), entity.getNgaybatDau(), entity.getNgayketThuc());      
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    public List<KhuyenMai> selectAll() {
        return selectBySql(select_all_sql);
    }

    @Override
    public KhuyenMai selectById(String id) {
        List<KhuyenMai> list = selectBySql(select_by_id_sql, id);
        if(list.isEmpty()){
        return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhuyenMai> selectBySql(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                KhuyenMai entity = new KhuyenMai();
                entity.setId(rs.getString("Id"));
                entity.setTenKM(rs.getString("TenKhuyenMai"));
                entity.setMoTa(rs.getString("MoTa"));
                entity.setKieugiamGia(rs.getBoolean("KieuGiamGia"));
                entity.setMuctramGiam(rs.getString("MucGiamGia"));
                entity.setNgaybatDau(rs.getString("NgayBatDau"));
                entity.setNgayketThuc(rs.getString("NgayKetThuc")); 
                list.add(entity);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public void luu(KhuyenMai kmmoi) throws SQLException {
        try {
            Connection conn = JdbcHelper.openDbConnection();
        
        
        PreparedStatement stm = conn.prepareStatement(insert_sql);
        
        stm.setString(1, kmmoi.getTenKM());
        stm.setString(5, kmmoi.getNgaybatDau());
        stm.setString(6, kmmoi.getNgayketThuc());
        stm.setString(4, kmmoi.getMuctramGiam());
        stm.setBoolean(3, kmmoi.isKieugiamGia());
        stm.setString(2, kmmoi.getMoTa());
        
        stm.executeQuery();
        
        stm.close();
        conn.close();
        } catch (Exception e) {
        }
    }
    
    public void sua(KhuyenMai kmmoi, String id) throws SQLException {
        try {
            Connection conn = JdbcHelper.openDbConnection();
        
        
        PreparedStatement stm = conn.prepareStatement(update_sql);
        
        stm.setString(1, kmmoi.getTenKM());
        stm.setString(5, kmmoi.getNgaybatDau());
        stm.setString(6, kmmoi.getNgayketThuc());
        stm.setString(4, kmmoi.getMuctramGiam());
        stm.setBoolean(3, kmmoi.isKieugiamGia());
        stm.setString(2, kmmoi.getMoTa());
        stm.setInt(7, Integer.parseInt(id));
        
        stm.executeQuery();
        
        stm.close();
        conn.close();
        } catch (Exception e) {
        }
    }
    public void xoa(String id) throws SQLException {
        try {
            Connection conn = JdbcHelper.openDbConnection();
        String caulenh = "update KhuyenMai set TrangThai = 0 where Id = ?";
        
        PreparedStatement stm = conn.prepareStatement(caulenh);
        stm.setInt(1, Integer.parseInt(id));
        
        stm.executeQuery();
        
        stm.close();
        conn.close();
        } catch (Exception e) {
        }
    }
    
    public List<KhuyenMai> selectAll2(String tukhoa) throws SQLException {
        return selectBySql2("%"+tukhoa+"%");
    }

    private List<KhuyenMai> selectBySql2(String string) throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Connection conn = JdbcHelper.openDbConnection();
        List<KhuyenMai> conban = new ArrayList<>();
        //String caulenh = "select * from SanPham where TrangThai like 1 and TenSanPham like ? or TrangThai like 1 and MaSanPham like ?";
        
        PreparedStatement stm = conn.prepareStatement(select_all_sql);
        stm.setString(1, string);
        ResultSet rs = stm.executeQuery();
        List<KhuyenMai> list = new ArrayList<>();
        while(rs.next()){
                KhuyenMai entity = new KhuyenMai();
                entity.setId(rs.getString("Id"));
                entity.setTenKM(rs.getString("TenKhuyenMai"));
                entity.setMoTa(rs.getString("MoTa"));
                entity.setKieugiamGia(rs.getBoolean("KieuGiamGia"));
                entity.setMuctramGiam(rs.getString("MucGiamGia"));
                entity.setNgaybatDau(rs.getString("NgayBatDau"));
                entity.setNgayketThuc(rs.getString("NgayKetThuc")); 
                list.add(entity);
            }
        rs.close();
        stm.close();
        conn.close();
        return list;
        
    }
}
