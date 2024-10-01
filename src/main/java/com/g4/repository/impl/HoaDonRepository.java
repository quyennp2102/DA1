/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.repository.G4Repository;
import com.g4.viewmodel.HoaDonViewModel;
import com.g4.entity.HoaDon;
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
public class HoaDonRepository extends G4Repository<HoaDon, String>{
    String select_all_sql = "select * from HoaDon where MaHD like ? and TrangThai like ? or MaHD like ? and TrangThai like ?";
    String select_by_id_sql = "Select * from HoaDon Where id = ?";
    String select_TongHD_sql = "select count(id) from HoaDon";
    String select_TongKH_sql = "select COUNT(DISTINCT idKhachHang) from HoaDon";
    
    public List<HoaDon> selectAll() {
        return selectBySql(select_all_sql);
    }

    public HoaDon selectById(String id) {
        List<HoaDon> list = selectBySql(select_by_id_sql, id);
        if(list.isEmpty()){
        return null;
        }
        return list.get(0);
    }

    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                HoaDon entity = new HoaDon();
                entity.setId(rs.getString("Id"));
                entity.setIdNV(rs.getInt("IdNhanVien"));
                entity.setMaHD(rs.getString("MaHD"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setTongTien(rs.getDouble("TongTien"));
                entity.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setTrangThai(rs.getInt("TrangThai"));
                entity.setHTTT(rs.getInt("HinhThucThanhToan"));

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

    public List<HoaDon> selectAll2(String tukhoa, int trangthai, int trangthai2) throws SQLException {
        return selectBySql2("%"+tukhoa+"%", trangthai, trangthai2);
    }
    public List<HoaDon> selectBySql2(String string, int trangthai, int trangthai2) throws SQLException{
        Connection conn = JdbcHelper.openDbConnection();
       
        
        PreparedStatement stm = conn.prepareStatement(select_all_sql);
        stm.setString(1, string);
        stm.setString(3, string);
        stm.setInt(2, trangthai);
        stm.setInt(4, trangthai2);
        ResultSet rs = stm.executeQuery();
        List<HoaDon> list = new ArrayList<>();
        while (rs.next()) {                
            HoaDon hd = new HoaDon();

            hd.setId(rs.getString("Id"));

            hd.setIdNV(rs.getInt("IdNhanVien"));
            hd.setMaHD(rs.getString("MaHD"));
            hd.setNgayTao(rs.getDate("NgayTao"));
            hd.setTongTien(rs.getDouble("TongTien"));
            hd.setNgayThanhToan(rs.getDate("NgayThanhToan"));
            hd.setGhiChu(rs.getString("GhiChu"));
            hd.setTrangThai(rs.getInt("TrangThai"));
            hd.setHTTT(rs.getInt("HinhThucThanhToan"));

            list.add(hd);
        }
        rs.close();
        stm.close();
        conn.close();
        return list;
    
    }
    
    public String TongHD(){
        try {
            ResultSet rs = JdbcHelper.query(select_TongHD_sql);
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        
        return "0";
    }
    
    public String TongKH(){
        try {
            ResultSet rs = JdbcHelper.query(select_TongKH_sql);
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        return "0";
    }
}
