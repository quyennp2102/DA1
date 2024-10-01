/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.CaLam;
import com.g4.entity.NhanVien;
import com.g4.repository.G4Repository;
import com.g4.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Ddawng
 */
public class CaLamRepository extends G4Repository<CaLam, Integer> {

    String insert_sql = "insert into CaLam (CaTrongNgay, GioBatDau, GioKetThuc) values (?,?,?)";
    String update_sql = "update CaLam set GioKetThuc = ? where id = ?";
    String select_id_new = "SELECT TOP 1 id\n"
            + "FROM CaLam\n"
            + "ORDER BY id DESC";
    

    @Override
    public void insert(CaLam entity) {
        JdbcHelper.update(insert_sql, entity.getCaTrongNgay(), entity.getGioBatDau(), entity.getGioKetThuc());
    }

    @Override
    public void update(CaLam entity) {
        
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CaLam> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CaLam selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CaLam> selectBySql(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int CaLamHienTai() {
        int newid = 0;
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(select_id_new)) {
           
            ResultSet rs = ps.executeQuery(); 
            if (rs.next()) {
                newid = rs.getInt(1);    
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return newid;
    }
    
    public void update2(String date, int id){
        JdbcHelper.update(update_sql, date, id);
    }

}
