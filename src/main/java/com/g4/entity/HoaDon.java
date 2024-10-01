package com.g4.entity;

import com.g4.viewmodel.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {

    private String id;
    private int idNV;  
    private String maHD;
    private Date ngayTao;
    private double TongTien;   
    private Date ngayThanhToan;
    private String ghiChu;
    private int trangThai;
    private int HTTT;
  
}
