/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author khuong duc
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class KhuyenMai {
    private String id;
    
    private String tenKM;
    
    private String ngaybatDau;
    
    private String ngayketThuc;
    
    private String moTa;
    
    private String muctramGiam;
    
    private boolean kieugiamGia; 
}
