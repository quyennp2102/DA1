/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.DungTich;
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
public class DungTichRepo {
    public List<DungTich> getDungTich(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<DungTich> listmau = new ArrayList<>();
            String caulenh = "select * from DungTich where TrangThai like 1";
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()){
                DungTich layMau = new DungTich();
                layMau.setId(rs.getInt("Id"));
                layMau.setDungtich(rs.getString("DungTich"));
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
        public DungTich getByID(int id){
            DungTich banmau = new DungTich();
            for(DungTich cl: getDungTich()){
                if(id==cl.getId()){
                    banmau = cl;
                    break;
                }
            }
            return banmau;
        }
        
        public DungTich getByDungTich(String ten){
            DungTich banmau = new DungTich();
            for(DungTich cl: getDungTich()){
                if(ten.equalsIgnoreCase(cl.getDungtich())){
                    banmau = cl;
                    break;
                }
            }
            return  banmau;
        } 
        
        public void themDungTich(String tenDungTich){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "insert into DungTich(DungTich, TrangThai)"
                        + "values(?,1)";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenDungTich);
                
                stm.executeQuery();
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        
        public void suaDungTich(String tenDungTich, int IDDungTich){
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "update DungTich set DungTich = ? where Id = ?";
                
                PreparedStatement stm = conn.prepareStatement(caulenh);
                
                stm.setString(1, tenDungTich);
                stm.setInt(2, IDDungTich);
                
                stm.executeQuery();
                
                stm.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        public void xoaDungTich(int IDDungTich){
            try {
                Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update DungTich set TrangThai = 0 where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setInt(1, IDDungTich);

            stm.executeQuery();

            stm.close();
            conn.close();
            } catch (Exception e) {
            }
        }
}
