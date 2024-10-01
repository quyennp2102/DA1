package com.g4.repository.impl;

import com.g4.entity.KhachHang;
import com.g4.repository.IKhachHangRepository;
import com.g4.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangRepository implements IKhachHangRepository {

    @Override
    public void insert(KhachHang newKH) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String INSERT_KH_SQL = "INSERT INTO KhachHang (MaKhachHang, TenKhachHang, SoDienThoai, NgayTao, TrangThai) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stm = conn.prepareStatement(INSERT_KH_SQL);

            stm.setString(1, newKH.getMaKH());
            stm.setString(2, newKH.getTenKH());
            stm.setString(3, newKH.getSdt());
            stm.setDate(4, new java.sql.Date(newKH.getNgayTao().getTime())); 
            stm.setInt(5, 1);
            stm.executeUpdate();
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(KhachHang updatedKH) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String UPDATE_KH_SQL = "UPDATE KhachHang SET MaKhachHang=?, TenKhachHang=?, SoDienThoai=?, TrangThai=? WHERE Id = ?";

            PreparedStatement stm = conn.prepareStatement(UPDATE_KH_SQL);

            stm.setString(1, updatedKH.getMaKH());
            stm.setString(2, updatedKH.getTenKH());
            stm.setString(3, updatedKH.getSdt());
//            stm.setDate(4, (Date) updatedKH.getNgayTao());  // Chuyển đổi java.util.Date sang java.sql.Date
            stm.setInt(4, updatedKH.getTrangThai());
            stm.setInt(5, updatedKH.getId());

            stm.executeUpdate();

            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<KhachHang> getKH(){
        Connection conn = JdbcHelper.openDbConnection();
        List<KhachHang> listKH = new ArrayList<>();

        String GET_KH_SQL = "SELECT * FROM KhachHang WHERE TrangThai = 1";
        try (PreparedStatement stm = conn.prepareStatement(GET_KH_SQL); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setId(rs.getInt("Id"));
                khachHang.setMaKH(rs.getString("MaKhachHang"));
                khachHang.setTenKH(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SoDienThoai"));
                khachHang.setNgayTao(rs.getDate("NgayTao"));
                khachHang.setTrangThai(rs.getInt("TrangThai"));

                listKH.add(khachHang);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listKH;
    }

    @Override
    public List<KhachHang> getKHCu() {
        Connection conn = JdbcHelper.openDbConnection();
        List<KhachHang> listKH = new ArrayList<>();

        String GET_KHCU_SQL = "SELECT * FROM KhachHang WHERE TrangThai = 0";
        try (PreparedStatement stm = conn.prepareStatement(GET_KHCU_SQL); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setId(rs.getInt("Id"));
                khachHang.setMaKH(rs.getString("MaKhachHang"));
                khachHang.setTenKH(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SoDienThoai"));
                khachHang.setNgayTao(rs.getDate("NgayTao"));
                khachHang.setTrangThai(rs.getInt("TrangThai"));

                listKH.add(khachHang);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listKH;
    }

    @Override
    public void delete(String id)  {
        try (Connection conn = JdbcHelper.openDbConnection()) {
            String DELETE_TAMTHOI_SQL = "UPDATE KhachHang SET TrangThai = 0 WHERE Id = ?";
            try (PreparedStatement stm = conn.prepareStatement(DELETE_TAMTHOI_SQL)) {
                stm.setString(1, id);
                stm.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public List<KhachHang> findKH(String findWord) {
        Connection conn = JdbcHelper.openDbConnection();
        List<KhachHang> listKH = new ArrayList<>();
        List<KhachHang> lisTKiem = new ArrayList<>();

        String FIND_KH_SQL = "SELECT * FROM KhachHang ";
        try (PreparedStatement stm = conn.prepareStatement(FIND_KH_SQL); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setId(rs.getInt("Id"));
                khachHang.setMaKH(rs.getString("MaKhachHang"));
                khachHang.setTenKH(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SoDienThoai"));
                khachHang.setNgayTao(rs.getDate("NgayTao"));
                khachHang.setTrangThai(rs.getInt("TrangThai"));

                listKH.add(khachHang);
            }
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (KhachHang khachHang : listKH) {
            String findMaKH = khachHang.getMaKH();
            String findTen = khachHang.getTenKH();
            if (findMaKH.equalsIgnoreCase(findWord) || findTen.equalsIgnoreCase(findWord)) {
                lisTKiem.add(khachHang);
            }
        }

        return lisTKiem;
    }

    @Override
    public void deleteForever(String id) {
         try (Connection conn = JdbcHelper.openDbConnection()) {
            String DELETE_SQL = "delete from KhachHang  WHERE Id = ?";
            try (PreparedStatement stm = conn.prepareStatement(DELETE_SQL)) {
                stm.setString(1, id);
                stm.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
