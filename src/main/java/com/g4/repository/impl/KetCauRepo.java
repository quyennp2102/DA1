/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.KetCau;
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
public class KetCauRepo {
    public List<KetCau> getKetCau(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<KetCau> listmau = new ArrayList<>();
            String caulenh = "select * from KetCau where TrangThai like 1";
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()){
                KetCau layMau = new KetCau();
                layMau.setId(rs.getInt("Id"));
                layMau.setTenketcau(rs.getString("KetCau"));
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
        public KetCau getByID(int id){
            KetCau banmau = new KetCau();
            for(KetCau cl: getKetCau()){
                if(id==cl.getId()){
                    banmau = cl;
                    break;
                }
            }
            return banmau;
        }
        
        public KetCau getByKetCau(String ten){
            KetCau banmau = new KetCau();
            for(KetCau cl: getKetCau()){
                if(ten.equalsIgnoreCase(cl.getTenketcau())){
                    banmau = cl;
                    break;
                }
            }
            return  banmau;
        } 
        
        public void themKetCau(String tenKetCau){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "insert into KetCau(KetCau, TrangThai)"
                        + "values(?,1)";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenKetCau);
                
                stm.executeQuery();
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        
        public void suaKetCau(String tenKetCau, int IDKetCau){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "update KetCau set KetCau = ? where Id = ?";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenKetCau);
                stm.setInt(2, IDKetCau);
                
                stm.executeQuery();
                
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        public void xoaKetCau(int IDKetCau){
            try {
                Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update KetCau set TrangThai = 0 where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setInt(1, IDKetCau);

            stm.executeQuery();

            stm.close();
            conn.close();
            } catch (Exception e) {
            }
        }
}
