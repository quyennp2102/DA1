/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.SanPham;
import com.g4.utils.JdbcHelper;
import com.g4.viewmodel.SanPhamViewModel;
import java.sql.Connection;
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
public class SanPhamRepository {
    String slect_all_sp_bh = "SELECT SP.Id, SP.MaSanPham, SP.TenSanPham, SP.GiaBan, SP.SoLuong, KC.KetCau, MS.MauSac, NH.NhomHuong, HU.HieuUng,DT.DungTich FROM dbo.SanPham SP\n"
            + "			INNER JOIN dbo.KetCau KC ON SP.IdKetCau = KC.Id\n"
            + "			INNER JOIN dbo.MauSac MS ON SP.IdMauSac = MS.Id\n"
            + "			INNER JOIN dbo.NhomHuong NH ON SP.IdNhomHuong = NH.Id\n"
            + "			INNER JOIN dbo.HieuUng HU ON SP.IdHieuUng = HU.Id\n"
            + "			INNER JOIN dbo.DungTich DT ON SP.IdDungTich = DT.Id\n"
            + "WHERE SP.TrangThai = 1";
    
    public List<SanPham> getBan(String kwd) throws SQLException{
        Connection conn = JdbcHelper.openDbConnection();
        List<SanPham> conban = new ArrayList<>();
        String caulenh = "select * from SanPham where TrangThai like 1 and TenSanPham like ? or TrangThai like 1 and MaSanPham like ?";
        
        PreparedStatement stm = conn.prepareStatement(caulenh);
        stm.setString(1, "%"+kwd+"%");
        stm.setString(2, "%"+kwd+"%");
        ResultSet rs = stm.executeQuery();
       
            
        while (rs.next()) {                
            SanPham mau = new SanPham();

            mau.setId(rs.getInt("Id"));

            mau.setIdKetCau(rs.getInt("IdKetCau"));
            mau.setIdMauSac(rs.getInt("IdMauSac"));
            mau.setIdNhomHuong(rs.getInt("IdNhomHuong"));
            mau.setIdHieuUng(rs.getInt("IdHieuUng"));
            mau.setIdDungTich(rs.getInt("IdDungTich"));

            mau.setMaSP(rs.getString("MaSanPham"));
            mau.setTenSP(rs.getString("TenSanPham"));
            mau.setGiaTien(rs.getDouble("GiaBan"));
            mau.setSoLuong(rs.getInt("SoLuong"));
            mau.setTrangThai(1);

            conban.add(mau);
        }
        rs.close();
        stm.close();
        conn.close();
        return conban;
    }
   
    public void luu(SanPham spmoi) throws SQLException{
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh= "insert into SanPham(IdKetCau, IdMauSac, IdNhomHuong, IdHieuUng, IdDungTich, MaSanPham, TenSanPham, GiaBan, SoLuong, MoTa, TrangThai)"
                                    + "values(?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            
            stm.setInt(1, spmoi.getIdKetCau());
            stm.setInt(2, spmoi.getIdMauSac());
            stm.setInt(3, spmoi.getIdNhomHuong());
            stm.setInt(4, spmoi.getIdHieuUng());
            stm.setInt(5, spmoi.getIdDungTich());
            
            stm.setString(6, spmoi.getMaSP());
            stm.setString(7, spmoi.getTenSP());
            stm.setDouble(8, spmoi.getGiaTien());
            stm.setInt(9, spmoi.getSoLuong());
            stm.setString(10, spmoi.getMoTa());
            stm.setInt(11, 1);
            
            stm.executeQuery();
            
            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }    
        public void sua(SanPham spSua) throws SQLException {
            try {
                Connection conn = JdbcHelper.openDbConnection();
                String caulenh = "update SanPham set IdKetCau=?, IdMauSac=?, IdNhomHuong=?, IdHieuUng=?, IdDungTich=?, MaSanPham=?, TenSanPham=?, GiaBan=?, SoLuong=?, MoTa=?, TrangThai=? where Id = ?";
        
                PreparedStatement stm = conn.prepareStatement(caulenh);
        
                stm.setInt(1, spSua.getIdKetCau());
                stm.setInt(2, spSua.getIdMauSac());
                stm.setInt(3, spSua.getIdNhomHuong());
                stm.setInt(4, spSua.getIdHieuUng());
                stm.setInt(5, spSua.getIdDungTich());
            
                stm.setString(6, spSua.getMaSP());
                stm.setString(7, spSua.getTenSP());
                stm.setDouble(8, spSua.getGiaTien());
                stm.setInt(9, spSua.getSoLuong());
                stm.setString(10, spSua.getMoTa());
                stm.setInt(11, 1);
        
                stm.setInt(12, spSua.getId());
        
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
        
        
        public List<SanPham> getAll(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<SanPham> listmau = new ArrayList<>();
            String caulenh = "select * from SanPham where TrangThai like 1";
            
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()){
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("Id"));

                sp.setIdKetCau(rs.getInt("IdKetCau"));
                sp.setIdMauSac(rs.getInt("IdMauSac"));
                sp.setIdNhomHuong(rs.getInt("IdNhomHuong"));
                sp.setIdHieuUng(rs.getInt("IdHieuUng"));
                sp.setIdDungTich(rs.getInt("IdDungTich"));

                sp.setMaSP(rs.getString("MaSanPham"));
                sp.setTenSP(rs.getString("TenSanPham"));
                sp.setGiaTien(rs.getDouble("GiaBan"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setTrangThai(1);
                
                listmau.add(sp);
            }
            rs.close();
            stm.close();
            conn.close();
            return listmau;
        } catch (Exception e) {
            return null;
        }
    }
        public SanPham getById(int id){
            SanPham spham = new SanPham();
            for(SanPham sp: getAll()){
                if(id==sp.getId()){
                    spham = sp;
                    break;
                }
            }
            return spham;
        }
        public SanPham getBySanPham(String ten){
            SanPham banmau = new SanPham();
            for(SanPham cl: getAll()){
                if(ten.equalsIgnoreCase(cl.getMaSP())){
                    banmau = cl;
                    break;
                }
            }
            return  banmau;
        }
        
        
        public List<SanPhamViewModel> selectBySqlSP(String sql, Object... args) {
        List<SanPhamViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPhamViewModel entity = new SanPhamViewModel();
                entity.setId(rs.getString(1));
                entity.setMaSP(rs.getString(2));
                entity.setTenSP(rs.getString(3));
                entity.setGiaTien(rs.getString(4));
                entity.setSoLuong(rs.getString(5));
                entity.setIdKetCau(rs.getString(6));
                entity.setIdMauSac(rs.getString(7));
                entity.setIdNhomHuong(rs.getString(8));
                entity.setIdHieuUng(rs.getString(9));
                entity.setIdDungTich(rs.getString(10));
                
                
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
        public List<SanPhamViewModel> getAllTT() {
            return selectBySqlSP(slect_all_sp_bh);
        }
        public List<SanPhamViewModel> searchTT(String input){
            List<SanPhamViewModel> listTT = new ArrayList<>();
            if(input==null){
                return getAllTT();
            }
            for(SanPhamViewModel x: getAllTT()){
                if(x.getId().contains(input)
                    || x.getMaSP().contains(input)
                    || x.getTenSP().contains(input)
                    || x.getGiaTien().contains(input)
                    || x.getSoLuong().contains(input)
                    || x.getIdKetCau().contains(input)
                    || x.getIdMauSac().contains(input)
                    || x.getIdNhomHuong().contains(input)
                    || x.getIdHieuUng().contains(input)
                    || x.getIdDungTich().contains(input)){
                    listTT.add(x);
                }
            }
            return listTT;
        }
}
