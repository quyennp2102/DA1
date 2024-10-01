package com.g4.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonViewModel {

    private String id;
    private String maHD;
    private Date ngayTao;
    private Date ngayThanhToan;
    private String idNV;
    private String idKH;
    private String tenKhachHang;
    private int trangThai;
    private Double TongTien;
    private boolean hinhThucThanhToan;

    public String trangThai() {
        if (getTrangThai() == 0) {
            return "Đã hủy";
        } else if (getTrangThai() == 1) {
            return "Chờ thanh toán";
        } else {
            return "Đã thanh toán";
        }
    }

    public Object[] toRowDataHD() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(ngayTao);

        return new Object[]{maHD, formattedDate, idNV, tenKhachHang, trangThai()};
    }

}
