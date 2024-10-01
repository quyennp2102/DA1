package com.g4.main;

import com.g4.swing.EventMenuSelected;
import com.g4.utils.Auth;
import com.g4.view.SanPhamJPanel;
import com.g4.view.KhachHangJPanel;
import com.g4.view.NhanVienJPanel;
import com.g4.view.BanHangJPanel;
import com.g4.view.DailogKhachHangBH;
import com.g4.view.GiaoCaJPanel;
import com.g4.view.HoaDonJPanel;
import com.g4.view.KhuyenMaiJPanel;
import com.g4.view.ThongKeJPanel;

import com.g4.view.HieuUngJPanel;
import com.g4.view.DungTichJPanel;
import com.g4.view.KetCauJPanel;
import com.g4.view.MauSacJPanel;
import com.g4.view.NhomHuongJPanel;
import java.awt.Color;
import javax.swing.JComponent;

public class Main extends javax.swing.JFrame {

    private final SanPhamJPanel sanPhamJPanel = new SanPhamJPanel();
    private final KhachHangJPanel khachHangJPanel = new KhachHangJPanel();
    private final NhanVienJPanel nhanVienJPanel = new NhanVienJPanel();
    private final BanHangJPanel banHangJPanel = new BanHangJPanel();
    private final HoaDonJPanel hoaDonJPanel = new HoaDonJPanel();
    private final ThongKeJPanel thongKeJPanel = new ThongKeJPanel();
    private final GiaoCaJPanel giaoCaJPanel = new GiaoCaJPanel();
    private final KhuyenMaiJPanel khuyenMaiJPanel = new KhuyenMaiJPanel();

    public Main() {
        initComponents();
        init(); 
    }

 public void openKhachHangNhanh() {
        DailogKhachHangBH dkh = new DailogKhachHangBH(this, true);
        dkh.setVisible(true);
      

        // Sau khi JDialog đóng, lấy giá trị từ JDialog
        String maKH = dkh.getMaKH();
        String tenKH = dkh.getTenKH();

        // Truyền giá trị vào MyPanel
      
        banHangJPanel.setMaKH(maKH);
        banHangJPanel.setTenKH(tenKH);

    }
 
 public void openMSNhanh(){
        MauSacJPanel ms = new MauSacJPanel(this, true);
        ms.setVisible(true);
        
        String mausac = ms.getMauSac();
        
        sanPhamJPanel.setMau(mausac); 
    }
    
    public void openKCNhanh(){
        KetCauJPanel kc = new KetCauJPanel(this, true);
        kc.setVisible(true);
        
        String ketcau = kc.getKetCau();
        
        sanPhamJPanel.setKetCau(ketcau);
    }
    
    public void openNHNhanh(){
        NhomHuongJPanel nh = new NhomHuongJPanel(this, true);
        nh.setVisible(true);
        
        String nhomhuong = nh.getNhomHuong();
        
        sanPhamJPanel.setNhomHuong(nhomhuong);
    }
    
    public void openHUNhanh(){
        HieuUngJPanel hu = new HieuUngJPanel(this, true);
        hu.setVisible(true);
        
        String hieuung = hu.getHieuUng();
        
        sanPhamJPanel.setHieuUng(hieuung);
    }
    
    public void openDTNhanh(){
        DungTichJPanel dt = new DungTichJPanel(this, true);
        dt.setVisible(true);
        
        String dungtich = dt.getDungTich();
        
        sanPhamJPanel.setDungTich(dungtich);
    }

    private void setForm(JComponent com) {
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        menu.initMoving(Main.this);
        menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    setForm(giaoCaJPanel);
                } else if (index == 1) {
                    setForm(banHangJPanel);
                } else if (index == 2) {
                    setForm(hoaDonJPanel);
                } else if (index == 3) {
                    setForm(sanPhamJPanel);
                } else if (index == 4) {
                    setForm(nhanVienJPanel);
                } else if (index == 5) {
                    setForm(khachHangJPanel);
                } else if (index == 6) {
                    setForm(thongKeJPanel);
                } else if (index == 7) {
                    setForm(khuyenMaiJPanel);
                } else if (index == 8) {
                    dangXuat();
                } else if (index == 9) {
                    System.exit(0);
                }
            }
        });
        setForm(new GiaoCaJPanel());
    }

    void openLogin() {
        new LoginJDialog(this, true).setVisible(true);
    }

    void dangXuat() {
        Auth.clear();
        dispose();
        openLogin();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.g4.swing.PanelBorder();
        menu = new com.g4.swing.Menu();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelBorder1.setBackground(new java.awt.Color(242, 242, 242));

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1110, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //public static void main(String args[]) {
    //    java.awt.EventQueue.invokeLater(new Runnable() {
    //        public void run() {
    //            new Main().setVisible(true);
    //        }
    //    });
    //}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private com.g4.swing.Menu menu;
    private com.g4.swing.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}
