/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author tuphp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaLam {
    
    private int Id;
    
    private Date NgayGiaoCa;
    
    private int CaTrongNgay;
    
    private String GioBatDau;
    
    private String GioKetThuc;
    
}
