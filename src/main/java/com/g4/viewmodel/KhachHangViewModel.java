package com.g4.viewmodel;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangViewModel {
    
    private String id;
    private String maKH;
    private String tenKH;
    private String sdt;
    private Date ngayTao;
    
    public Object[] todataRow() {
        return new Object[]{maKH, tenKH, sdt, ngayTao};
    }
    
}
