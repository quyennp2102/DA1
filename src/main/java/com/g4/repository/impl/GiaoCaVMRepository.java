/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.GiaoCa;
import com.g4.entity.NhanVien;
import com.g4.repository.G4Repository;
import com.g4.utils.JdbcHelper;
import com.g4.viewmodel.GiaoCaViewModel;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ddawng
 */
public class GiaoCaVMRepository extends G4Repository<GiaoCaViewModel, Integer> {

    String select_all_sql = "SELECT dbo.GiaoCa.Id, dbo.NhanVien.TenNhanVien, dbo.CaLam.NgayGiaoCa, dbo.CaLam.GioBatDau, dbo.CaLam.GioKetThuc\n"
            + "FROM     dbo.CaLam INNER JOIN\n"
            + "                  dbo.GiaoCa ON dbo.CaLam.Id = dbo.GiaoCa.IdCaLam INNER JOIN\n"
            + "                  dbo.NhanVien ON dbo.GiaoCa.IdNhanVien = dbo.NhanVien.Id ";

    @Override
    public void insert(GiaoCaViewModel entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(GiaoCaViewModel entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GiaoCaViewModel> selectAll() {
         return selectBySql(select_all_sql);
    }

    @Override
    public GiaoCaViewModel selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GiaoCaViewModel> selectBySql(String sql, Object... args) {
         List<GiaoCaViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                GiaoCaViewModel entity = new GiaoCaViewModel();
                entity.setId(rs.getString(1));
                entity.setTenNV(rs.getString(2));
                entity.setNgayGiaoCa(rs.getString(3));
                entity.setGioBatDau(rs.getString(4));
                entity.setGioKetThu(rs.getString(5));
                list.add(entity);
            }
        } catch (Exception e) {
        }
        return list;
    }

}
