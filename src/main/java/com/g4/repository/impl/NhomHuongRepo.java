/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.NhomHuong;
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
public class NhomHuongRepo {
    public List<NhomHuong> getNhomHuong(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<NhomHuong> listmau = new ArrayList<>();
            String caulenh = "select * from NhomHuong where TrangThai like 1";
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()){
                NhomHuong layMau = new NhomHuong();
                layMau.setId(rs.getInt("Id"));
                layMau.setTenNhomHuong(rs.getString("NhomHuong"));
                layMau.setTrangthai(1);
                
                listmau.add(layMau);
            }
            rs.close();
            stm.close();
            conn.close();
            return listmau;
        } catch (Exception e) {
            return null;
        }
    }    
        public NhomHuong getByID(int id){
            NhomHuong banmau = new NhomHuong();
            for(NhomHuong cl: getNhomHuong()){
                if(id==cl.getId()){
                    banmau = cl;
                    break;
                }
            }
            return banmau;
        }
        
        public NhomHuong getByNhomHuong(String ten){
            NhomHuong banmau = new NhomHuong();
            for(NhomHuong cl: getNhomHuong()){
                if(ten.equalsIgnoreCase(cl.getTenNhomHuong())){
                    banmau = cl;
                    break;
                }
            }
            return  banmau;
        } 
        
        public void themMau(String tenNhomHuong){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "insert into NhomHuong(NhomHuong, TrangThai)"
                        + "values(?,1)";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenNhomHuong);
                
                stm.executeQuery();
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        
        public void suaNhomHuong(String tenNhomHuong, int IDNhomHuong){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "update NhomHuong set NhomHuong = ? where Id = ?";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenNhomHuong);
                stm.setInt(2, IDNhomHuong);
                
                stm.executeQuery();
                
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        public void xoaNhomHuong(int IDNhomHuong){
            try {
                Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update NhomHuong set TrangThai = 0 where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setInt(1, IDNhomHuong);

            stm.executeQuery();

            stm.close();
            conn.close();
            } catch (Exception e) {
            }
        }
}
