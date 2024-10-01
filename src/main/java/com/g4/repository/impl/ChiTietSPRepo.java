/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import java.util.List;
import com.g4.entity.ChiTietSP;
import com.g4.entity.SanPham;
import com.g4.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author khuong duc
 */
public class ChiTietSPRepo {
    public List<SanPham> getCTSP(String kwd) throws SQLException{
        Connection conn = JdbcHelper.openDbConnection();
            List<SanPham> listctsp = new ArrayList<>();
            String caulenh = "select * from SanPham where TrangThai like 1 and TenSanPham like ? or TrangThai like 1 and MaSanPham like ?"; 
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            stm.setString(1, "%"+kwd+"%");
            stm.setString(2, "%"+kwd+"%");
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()){
                SanPham layctsp = new SanPham();
                layctsp.setId(rs.getInt("Id"));
                layctsp.setMaSP(rs.getString("MaSanPham"));
                layctsp.setTenSP(rs.getString("TenSanPham"));
                layctsp.setSoLuong(rs.getInt("SoLuong"));
                
                listctsp.add(layctsp);
            }
            rs.close();
            stm.close();
            conn.close();
            return listctsp;
    }
    public void luu(ChiTietSP spmoi) throws SQLException{
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh= "insert into SanPham(MaSanPham, TenSanPham)"
                                    + "values(?,?)";
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            
            stm.setString(1, spmoi.getMa());
            stm.setString(2, spmoi.getTen());

            
            stm.executeQuery();
            
            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
    
    public void sua(ChiTietSP ctspSua) throws SQLException {
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "update SanPham set  MaSanPham=?, TenSanPham=? where Id = ?";
        
                PreparedStatement stm = conn.prepareStatement(caulenh);

            
                stm.setString(1, ctspSua.getMa());
                stm.setString(2, ctspSua.getTen());

        
                stm.setInt(3, ctspSua.getId());
        
                stm.executeQuery();
        
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
    }
    
    public void xoa(int parseInt) throws SQLException {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update SanPham set TrangThai = 0 where Id = ?";
        
            PreparedStatement stm = conn.prepareStatement(caulenh);
            stm.setInt(1, parseInt);
        
            stm.executeQuery();
        
            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
    
    public SanPham getByID(int id) throws SQLException{
            SanPham banmau = new SanPham();
            for(SanPham cl: getCTSP("")){
                if(id==cl.getId()){
                    banmau = cl;
                    break;
                }
            }
            return banmau;
        }
}
