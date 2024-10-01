package com.g4.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SanPham {
    
    private int id;
    private int idHieuUng;
    private int idMauSac;
    private int idNhomHuong;
    private int idDungTich;
    private int idKetCau;
    private String maSP;
    private String tenSP;
    private double giaTien;
    private int soLuong;
    private String moTa;
    private int trangThai;
    
    public SanPham(int soLuong){
        this.soLuong = soLuong;
    }
    

}
