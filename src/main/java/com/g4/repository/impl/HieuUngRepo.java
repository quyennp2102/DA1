/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.HieuUng;
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
 * @author DELL
 */
public class HieuUngRepo {
    public List<HieuUng> getHieuUng(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<HieuUng> listmau = new ArrayList<>();
            String caulenh = "select * from HieuUng where TrangThai like 1";
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()){
                HieuUng layMau = new HieuUng();
                layMau.setId(rs.getInt("Id"));
                layMau.setTenhieuung(rs.getString("HieuUng"));
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
        public HieuUng getByID(int id){
            HieuUng banmau = new HieuUng();
            for(HieuUng cl: getHieuUng()){
                if(id==cl.getId()){
                    banmau = cl;
                    break;
                }
            }
            return banmau;
        }
        
        public HieuUng getByHieuUng(String ten){
            HieuUng banmau = new HieuUng();
            for(HieuUng cl: getHieuUng()){
                if(ten.equalsIgnoreCase(cl.getTenhieuung())){
                    banmau = cl;
                    break;
                }
            }
            return  banmau;
        } 
        
        public void themHieuUng(String tenHieuUng){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "insert into HieuUng(HieuUng, TrangThai)"
                        + "values(?,1)";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenHieuUng);
                
                stm.executeQuery();
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        
        public void suaHieuUng(String tenHieuUng, int IDHieuUng){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "update HieuUng set HieuUng = ? where Id = ?";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenHieuUng);
                stm.setInt(2, IDHieuUng);
                
                stm.executeQuery();
                
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        public void xoaHieuUng(int IDHieuUng){
            try {
                Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update HieuUng set TrangThai = 0 where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setInt(1, IDHieuUng);

            stm.executeQuery();

            stm.close();
            conn.close();
            } catch (Exception e) {
            }
        }
}
