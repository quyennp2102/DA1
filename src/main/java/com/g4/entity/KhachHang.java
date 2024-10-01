package com.g4.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KhachHang {

    private int id;
    private String maKH;
    private String tenKH;
    private String sdt;
    private Date ngayTao;
    private int trangThai;

  

    public KhachHang(String maKH, String tenKH, String sdt, Date ngayTao, int trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }
    
   
    
}
