/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.g4.repository;

import com.g4.entity.KhachHang;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ddawng
 */
public interface IKhachHangRepository {
    
   public List<KhachHang> getKH() ;
   public List<KhachHang> getKHCu() ;
    public void insert(KhachHang Newnv) ;
    public void update(KhachHang nv);
    public void delete(String id) ;
    public List<KhachHang> findKH(String findWord) ;
    public void deleteForever(String id);
}
