/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.viewmodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author khuong duc
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 *
 * @author khuong duc
 */
public class KhuyenMaiViewModel {
    private String id;
    
    private String tenKM;
    
    private String ngaybatDau;
    
    private String ngayketThuc;
    
    private String moTa;
    
    private String muctramGiam;
    
    private boolean kieugiamGia;
    
    private int trangThai;
}
