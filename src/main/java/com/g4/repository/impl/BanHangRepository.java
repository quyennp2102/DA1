/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.HoaDon;
import com.g4.entity.HoaDonChiTiet;
import com.g4.entity.SanPham;
import com.g4.utils.JdbcHelper;
import com.g4.viewmodel.GioHangViewModel;
import com.g4.viewmodel.HoaDonViewModel;
import com.g4.viewmodel.KhachHangViewModel;
import com.g4.viewmodel.SanPhamViewModel;
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
 * @author tuphp
 */
public class BanHangRepository {

    String slect_hd_chua_thanh_toan_bh = "SELECT hd.Id, hd.MaHD, hd.NgayTao, nv.TenNhanVien,kh.MaKhachHang ,kh.TenKhachHang, hd.TrangThai\n"
            + "FROM dbo.HoaDon hd        \n"
            + "            INNER JOIN dbo.NhanVien nv ON hd.IdNhanVien = nv.Id\n"
            + "			INNER JOIN dbo.KhachHang kh ON hd.IdKhachHang = kh.Id\n"
            + "WHERE hd.TrangThai = 1\n"
            + "ORDER BY hd.MaHD DESC";
    String slect_all_sp_bh = "SELECT SP.Id, SP.MaSanPham, SP.TenSanPham, KT.KichCo, MS.TenMauSac, CLG.TenChatLieu, TH.TenThuongHieu, SP.SoLuong, SP.GiaBan\n"
            + "FROM dbo.SanPham SP\n"
            + "			INNER JOIN dbo.KichCoGiay KT ON SP.IdKichCoGiay = KT.Id\n"
            + "			INNER JOIN dbo.MauSac MS ON SP.IdMauSac = MS.Id\n"
            + "			INNER JOIN dbo.ChatLieuGiay CLG ON SP.IdChatLieuGiay = CLG.Id\n"
            + "			INNER JOIN dbo.ThuongHieu TH ON SP.IdThuongHieu = TH.Id";
    String select_gh_bh = "SELECT HDCT.Id, SP.Id, SP.MaSanPham, SP.TenSanPham, HDCT.SoLuong, SP.GiaBan\n"
            + "FROM dbo.SanPham SP\n"
            + "	INNER JOIN dbo.HoaDonChiTiet HDCT ON SP.Id = HDCT.IdSanPham\n"
            + "WHERE HDCT.IdHoaDon LIKE ?";
    String insert_hdct = "INSERT INTO HoaDonChiTiet (IdHoaDon,IdSanPham,SoLuong,DonGia) VALUES (?,?,?,?)";
    String update_soluong_sp_by_id = "UPDATE SanPham SET SoLuong = ? WHERE ID = ?";
    String delete_hdct_by_idHoaDon = "Delete from HoaDonChiTiet where IdHoaDon = ?";
    String delete_hd_by_id = "Delete from HoaDon where Id = ?";
    String update_NVKH = "UPDATE HoaDon SET IdNhanVien = ? WHERE MaHD = ?";
    String update_thanh_toan = "UPDATE HoaDon SET NgayThanhToan = GETDATE(), TongTien = ?, HinhThucThanhToan = ?, TrangThai = ? WHERE MaHD = ?";
    String delete_giohang = "Delete from HoaDonChiTiet where Id = ?";
    String capNhatSoLuong2 = "Update SanPham Set SoLuong = SoLuong - ? where Id = ?";
    String capNhatSoLuong = "Update SanPham Set SoLuong = SoLuong + ? where Id = ?";
    String updateSoLuong = "UPDATE SanPham SET SoLuong = ? WHERE ID = ?";
    String updateSoLuongHDCT = "UPDATE HoaDonChiTiet SET SoLuong = ? WHERE Id = ?";
    String insert_hoadon = "Insert into HoaDon(IdNhanVien,IdKhachHang, MaHD) values (?,?,?);";

    String findByIdKH_ma = "SELECT Id FROM KhachHang WHERE MaKhachHang = ?";
    String select_KHN = "select * from KhachHang";

    String timKiem_sp = "SELECT SP.MaSanPham, SP.TenSanPham, KT.KichCo, MS.TenMauSac, CLG.TenChatLieu, TH.TenThuongHieu, SP.SoLuong, SP.GiaBan\n"
            + "       FROM dbo.SanPham SP\n"
            + "          		INNER JOIN dbo.KichCoGiay KT ON SP.IdKichCoGiay = KT.Id\n"
            + "          		INNER JOIN dbo.MauSac MS ON SP.IdMauSac = MS.Id\n"
            + "         		INNER JOIN dbo.ChatLieuGiay CLG ON SP.IdChatLieuGiay = CLG.Id\n"
            + "          		INNER JOIN dbo.ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n"
            + "          WHERE SP.MaSanPham like ? \n"
            + "			OR SP.TenSanPham LIKE ? \n"
            + "			OR KT.KichCo LIKE ? \n"
            + "			OR MS.TenMauSac LIKE ? \n"
            + "          	OR CLG.TenChatLieu LIKE ? \n"
            + "         	OR TH.TenThuongHieu LIKE ? ;";

    public List<SanPhamViewModel> SearchSPBH(String input) {
        List<SanPhamViewModel> listNV = new ArrayList<>();
        if (input == null) {
            return getAllSP();
        }
        for (SanPhamViewModel x : getAllSP()) {
            if (x.getMaSP().contains(input)
                    || x.getTenSP().contains(input)
                    || x.getIdKT().contains(input)
                    || x.getIDMS().contains(input)
                    || x.getIdDG().contains(input)
                    || x.getIdTH().contains(input)) {
                listNV.add(x);
            }
        }
        return listNV;
    }

    // tìm id khách hàng theo mã
    public String findByIDKH(String maKH) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(findByIdKH_ma)) {
            ps.setString(1, maKH); // Sử dụng setString() thay vì setObject()
            ResultSet rs = ps.executeQuery(); // Thực thi truy vấn và lưu kết quả trả về vào ResultSet
            if (rs.next()) {
                String id = rs.getString("Id"); // Lấy giá trị cột "Id" từ ResultSet
                return id; // Trả về giá trị Id nếu tìm thấy khách hàng
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Lỗi truy vấn dữ liệu hoặc không tìm thấy khách hàng";
    }

    public String deleteHDCT(String idHD) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(delete_hdct_by_idHoaDon)) {
            ps.setObject(1, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Ôi hỏng";
    }

    public String deleteHD(String idHD) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(delete_hd_by_id)) {
            ps.setObject(1, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Ôi hỏng";
    }

    public String deleteGioHang(String id) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(delete_giohang)) {
            ps.setObject(1, id);

            if (ps.executeUpdate() > 0) {
                return "Xóa sản phẩm thành công";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Để xác nhận vui lòng chọn lại sản phẩm cần xóa";
    }

    public String updateNVKH(HoaDon hdUpdate, String ma) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(update_NVKH)) {
            ps.setObject(2, ma);
            ps.setObject(1, hdUpdate.getIdNV());
            if (ps.executeUpdate() > 0) {
                return "Thay đổi thành công";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Thay đổi thất bại";
    }

    public String updateTrangThai(HoaDonViewModel hd, String ma) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(update_thanh_toan)) {
            ps.setObject(1, hd.getTongTien());
            ps.setObject(2, hd.isHinhThucThanhToan());
            ps.setObject(3, hd.getTrangThai());
            ps.setObject(4, ma);
            if (ps.executeUpdate() > 0) {
                return "Thành công";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Thất bại";
    }

    // chỉnh lại số lượng đã thêm vào giỏ hàng
    public String capNhatSoLuong2(SanPham ctsp, String id) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(capNhatSoLuong2)) {
            ps.setObject(1, ctsp.getSoluong());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String capNhatSoLuong(SanPham ctsp, String id) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(capNhatSoLuong)) {
            ps.setObject(1, ctsp.getSoluong());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // số lượng mới
    public String updateSoLuongHDCT(GioHangViewModel gh, String id) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(updateSoLuongHDCT)) {
            ps.setObject(1, gh.getSoLuong());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // hàm này cập nhật lại số lượng khi thêm vào giỏ hàng
    public String updateSoLuong(SanPham ctsp, String id) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(updateSoLuong)) {
            ps.setObject(1, ctsp.getSoluong());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // insert hóa đơn chi tiết
    public String addHDCT(HoaDonChiTiet hdct) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(insert_hdct)) {
            ps.setObject(1, hdct.getIdHD());
            ps.setObject(2, hdct.getIdSP());
            ps.setObject(3, hdct.getSoLuong());
            ps.setObject(4, hdct.getDonGia());
            if (ps.executeUpdate() > 0) {
                return "Thêm sản phẩm thành công";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Thêm sản phẩm thất bại";
    }

    // insert hóa đơn
    public String addHoaDon(HoaDonViewModel hd) {
        try (Connection con = JdbcHelper.openDbConnection(); PreparedStatement ps = con.prepareStatement(insert_hoadon)) {
            // Truy vấn lấy mã hóa đơn lớn nhất hiện tại
            String query = "SELECT MAX(MaHD) FROM HoaDon";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String maxMaHD = resultSet.getString(1);
                if (maxMaHD != null) {
                    // Chuyển đổi mã hóa đơn thành kiểu số nguyên
                    int maxMaHDNumber = Integer.parseInt(maxMaHD.replace("HD", ""));
                    // Tăng giá trị mã hóa đơn lên 1
                    int nextMaHDNumber = maxMaHDNumber + 1;
                    // Tạo mã hóa đơn mới
                    String nextMaHD = "HD" + String.format("%03d", nextMaHDNumber);
                    hd.setMaHD(nextMaHD);
                } else {
                    // Nếu không có mã hóa đơn, tạo mã đầu tiên
                    hd.setMaHD("HD001");
                }
            }
            // Tiếp tục thêm hóa đơn với mã đã cập nhật
            ps.setObject(1, hd.getIdNV());
            ps.setObject(2, hd.getIdKH());
            ps.setObject(3, hd.getMaHD());
            if (ps.executeUpdate() > 0) {
                return "Thêm hóa đơn thành công";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Thêm hóa đơn thất bại";
    }

    // load data giỏ hàng
    public List<GioHangViewModel> getGioHang(String id) {
        return this.selectBySqlGH(select_gh_bh, "%" + id + "%");
    }

    // load data hóa đơn
    public List<HoaDonViewModel> getALLHD() {
        return selectBySqlHD(slect_hd_chua_thanh_toan_bh);
    }

    // load data sản phẩm
    public List<SanPhamViewModel> getAllSP() {
        return selectBySqlSP(slect_all_sp_bh);
    }

    // load data thêm nhanh khách hàng
    public List<KhachHangViewModel> getAllKHN() {
        return selectBySqlKHN(select_KHN);
    }

    public List<HoaDonViewModel> selectBySqlHD(String sql, Object... args) {
        List<HoaDonViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDonViewModel entity = new HoaDonViewModel();
                entity.setId(rs.getString(1));
                entity.setMaHD(rs.getString(2));
                entity.setNgayTao(rs.getDate(3));
                entity.setIdNV(rs.getString(4));
                entity.setIdKH(rs.getString(5));
                entity.setTenKhachHang(rs.getString(6));
                entity.setTrangThai(rs.getInt(7));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
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
                entity.setIdKT(rs.getString(4));
                entity.setIDMS(rs.getString(5));
                entity.setIdDG(rs.getString(6));
                entity.setIdTH(rs.getString(7));
                entity.setSoLuong(rs.getInt(8));
                entity.setGiaBan(rs.getDouble(9));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<GioHangViewModel> selectBySqlGH(String sql, Object... args) {
        List<GioHangViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                GioHangViewModel entity = new GioHangViewModel();
                entity.setId(rs.getString(1));
                entity.setIdSP(rs.getString(2));
                entity.setMaSP(rs.getString(3));
                entity.setTenSP(rs.getString(4));
                entity.setSoLuong(rs.getInt(5));
                entity.setDonGia(rs.getDouble(6));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<KhachHangViewModel> selectBySqlKHN(String sql, Object... args) {
        List<KhachHangViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhachHangViewModel entity = new KhachHangViewModel();
                entity.setId(rs.getString(1));
                entity.setMaKH(rs.getString(2));
                entity.setTenKH(rs.getString(3));
                entity.setSdt(rs.getString(4));
                entity.setNgayTao(rs.getDate(5));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
