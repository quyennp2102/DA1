package com.g4.viewmodel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class SanPhamViewModel {

    private String id;
    private String idHieuUng;
    private String idMauSac;
    private String idNhomHuong;
    private String idDungTich;
    private String idKetCau;
    private String maSP;
    private String tenSP;
    private String giaTien;
    private String soLuong;
    private String moTa;
    
    public Object[] todataRowSanPham() {
   
        return new Object[]{maSP, tenSP, giaTien, soLuong, idKetCau, idMauSac, idNhomHuong, idHieuUng, idDungTich};
    }

}
