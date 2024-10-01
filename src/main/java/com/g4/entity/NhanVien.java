package com.g4.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {

    private String id;
    private String tenNV;
    private int gioiTinh;
    private String ngaySinh;
    private Date ngayTao;
    
    private String diaChi;
    private String email;
    private String sdt;
    private String matKhau;
    private int trangThai;
    private boolean vaiTro;
    
    
    public String GioiTinh(int gioiTinh){
        if(gioiTinh == 1){
            return "Nam";
        }else{
            return "Nu";
        }
    }
    
    public String VaiTro(boolean vaiTro){
        if(vaiTro == true){
            return "Nhan vien";
        }else{
            return "Quan li";
        }
    }
    
    public String TrangThai(int trangthai){
        if(trangthai == 0){
            return "Con lam";
        }else{
            return "Da nghi lam";
        }
    }
    
}
