package com.g4.entity;

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
public class KetCau {
    
    private int id;
    private String tenketcau;
    private int trangthai;

    
    public Object[] todataRow(){
        return new Object[]{tenketcau};
    }
    
}
