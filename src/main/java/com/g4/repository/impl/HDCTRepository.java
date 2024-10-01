/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.repository.G4Repository;
import com.g4.viewmodel.KhuyenMaiViewModel;
import com.g4.entity.HoaDonChiTiet;
import com.g4.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author khuong duc
 */
public class HDCTRepository  extends G4Repository<HoaDonChiTiet, String>{
    String select_all_sql = "select * from HoaDonChiTiet";
    String select_by_id_sql = "Select * from HoaDonChiTiet Where idHoaDon like ?";
    String select_SoLuong_sql = "select sum(SoLuong) from HoaDonChiTiet";
    String select_SLDG_sql = "select soluong, dongia from hoadonchitiet";
    
    
    @Override
    public void insert(HoaDonChiTiet entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(HoaDonChiTiet entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
        return selectBySql(select_all_sql);
    }

    @Override
    public HoaDonChiTiet selectById(String id) {
        List<HoaDonChiTiet> list = selectBySql(select_by_id_sql, id);
        if(list.isEmpty()){
        return null;
        }
        return list.get(0);  }

    @Override
    public List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                HoaDonChiTiet entity = new HoaDonChiTiet();
                entity.setIdSP(rs.getString("IdSanPham"));
                entity.setIdHD(rs.getString("IdHoaDon"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setDonGia(rs.getDouble("DonGia"));

                list.add(entity);
            }
        } catch (Exception e) {
        }
        return list;    }
    
    public List<HoaDonChiTiet> selectByHoaDon(int id) {
        List<HoaDonChiTiet> list = selectBySql(select_by_id_sql, id);
        if(list.isEmpty()){
        return null;
        }
        return list;
        }
    
    public String TongSLSP(){
        try {
            ResultSet rs = JdbcHelper.query(select_SoLuong_sql);
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "0";
    }
    
    public double TongDT(){
        try {
            ResultSet rs = JdbcHelper.query(select_SLDG_sql);
            double tong = 0;
            int soluong = 0;
            double dongia = 0;
            while (rs.next()){
                soluong = rs.getInt(1);
                dongia = rs.getDouble(2);
                tong += soluong * dongia;
            }
            return tong;
        } catch (Exception e) {
        }
        return 0;
    }
    
}
