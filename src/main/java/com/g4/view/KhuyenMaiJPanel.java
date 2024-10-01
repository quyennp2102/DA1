/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g4.view;

import com.g4.entity.KhuyenMai;
import com.g4.viewmodel.KhuyenMaiViewModel;
import com.g4.repository.impl.KhuyenMaiRepository;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.util.Date;
/**
 *
 * @author tuphp
 */
public class KhuyenMaiJPanel extends javax.swing.JPanel {

    private KhuyenMaiRepository khmRepository = new KhuyenMaiRepository();
    private DefaultTableModel dtm = new DefaultTableModel();
    
    private String dftk = "";
    /**
     * Creates new form KhachHangJPanel
     */
    public KhuyenMaiJPanel() {
        initComponents();
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     public void loadData() throws SQLException{
        List<KhuyenMai> list = khmRepository.selectAll2(tukhoatimkiem);
        dtm = (DefaultTableModel) tbl_khuyenMai.getModel();
        dtm.setRowCount(0);
        for (KhuyenMai x : list){
            dtm.addRow(new Object[]{
            x.getTenKM(), x.getNgaybatDau(), x.getNgayketThuc(), x.isKieugiamGia(), x.getMoTa(), x.getMuctramGiam()
            });
        }
    }
     
    public boolean validateForm(){
        if(txt_tenKM.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Ten khuyen mai");
            return false;
        }
        if(txt_ngayBD2.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Ngay bat dau");
            return false;
        }
        if(txt_ngayKT2.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Ngay ket thuc");
            return false;
        }
        if(txt_phanTramGiam2.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Muc giam gia");
            return false;
        }
        if(txt_moTa2.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mo ta");
            return false;
        }
        return true;
    }
    public KhuyenMai getKMinput(){
        KhuyenMai km = new KhuyenMai();
        km.setTenKM(txt_tenKM.getText());
        km.setNgaybatDau(txt_ngayBD2.getText());
        km.setNgayketThuc(txt_ngayKT2.getText());
        km.setMuctramGiam(txt_phanTramGiam2.getText());
        km.setMoTa(txt_moTa2.getText());
        //km.setKieugiamGia(true);
        if(cbo_kieuKM2.getSelectedItem().equals("Phần trăm")){
        km.setKieugiamGia(true);
        }
        else{
        km.setKieugiamGia(false);
        }
        
        return km;
    }
    public void save(){
        if(validateForm()){
            KhuyenMai km = getKMinput();
            try{
                khmRepository.insert(km);
            }catch (Exception e){
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Them thanh cong");
        }
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String idbua;
    public void mouseClick() throws ParseException, SQLException{
        dtm = (DefaultTableModel) tbl_khuyenMai.getModel();
        KhuyenMai km = new KhuyenMai();
        int row = tbl_khuyenMai.getSelectedRow();
        
        txt_tenKM.setText(tbl_khuyenMai.getValueAt(row, 0).toString());
        txt_ngayBD2.setText(tbl_khuyenMai.getValueAt(row, 1).toString());
        txt_ngayKT2.setText(tbl_khuyenMai.getValueAt(row, 2).toString());
        txt_moTa2.setText(tbl_khuyenMai.getValueAt(row, 4).toString());
        txt_phanTramGiam2.setText(tbl_khuyenMai.getValueAt(row, 5).toString());
        
        KhuyenMai bunhin = khmRepository.selectAll2(tukhoatimkiem).get(row);
        idbua = bunhin.getId();
        
        if(bunhin.isKieugiamGia()){
            cbo_kieuKM2.setSelectedIndex(0);
        }
        else{
            cbo_kieuKM2.setSelectedIndex(1);
        }
        
    }
    public void deleteKM() throws SQLException {
        int row = tbl_khuyenMai.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng");
        }
        String id = tbl_khuyenMai.getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(this, "Xóa thành công");
        khmRepository.delete(id);
        loadData();

    }
    //public void UpdateKM(){
    //    int row = tbl_khuyenMai.getSelectedRow();
    //    if (row < 0){
    //        JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng");
    //    }
    //    KhuyenMai km = new KhuyenMai();
    //    km.setTenKM(txt_tenKM.getText());
    //    km.setNgaybatDau(txt_ngayBD2.getText());
    //   km.setNgayketThuc(txt_ngayKT2.getText());
    //    km.setMoTa(txt_moTa2.getText());
    //    km.setMuctramGiam(txt_phanTramGiam2.getText());


    //    khmRepository.update(km);
    //    JOptionPane.showMessageDialog(this, "Sửa thành công");
    //    loadData();
    //}
    public void clearFrom(){
        txt_tenKM.setText("");
        txt_ngayBD2.setText("");
        txt_ngayKT2.setText("");
        //cbo_kieuKM2.setSelectedIndex(0);
        txt_phanTramGiam2.setText("");
        txt_moTa2.setText("");
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        txt_tenKM = new javax.swing.JTextField();
        txt_ngayBD2 = new javax.swing.JTextField();
        txt_ngayKT2 = new javax.swing.JTextField();
        txt_phanTramGiam2 = new javax.swing.JTextField();
        txt_moTa2 = new javax.swing.JTextField();
        cbo_kieuKM2 = new javax.swing.JComboBox<>();
        lbl_tenKM2 = new javax.swing.JLabel();
        lbl_ngayBD2 = new javax.swing.JLabel();
        lbl_ngayKT2 = new javax.swing.JLabel();
        lbl_phanTramGiam2 = new javax.swing.JLabel();
        lbl_moTa2 = new javax.swing.JLabel();
        btn_themKH2 = new javax.swing.JButton();
        btn_suaKM2 = new javax.swing.JButton();
        btn_xoaKM2 = new javax.swing.JButton();
        btn_clearKM2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_timKiem = new javax.swing.JLabel();
        btn_timKiem = new javax.swing.JButton();
        txt_timKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_khuyenMai = new javax.swing.JTable();
        lbl_DSKM = new javax.swing.JLabel();

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        txt_tenKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenKMActionPerformed(evt);
            }
        });

        cbo_kieuKM2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phần trăm", "VND" }));

        lbl_tenKM2.setText("TÊN KHUYẾN MÃI");

        lbl_ngayBD2.setText("BẮT ĐẦU");

        lbl_ngayKT2.setText("KẾT THÚC");

        lbl_phanTramGiam2.setText("MỨC GIẢM");

        lbl_moTa2.setText("MÔ TẢ");

        btn_themKH2.setBackground(new java.awt.Color(102, 204, 255));
        btn_themKH2.setText("THÊM");
        btn_themKH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themKH2ActionPerformed(evt);
            }
        });

        btn_suaKM2.setBackground(new java.awt.Color(153, 255, 153));
        btn_suaKM2.setText("SỬA");
        btn_suaKM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaKM2ActionPerformed(evt);
            }
        });

        btn_xoaKM2.setBackground(new java.awt.Color(255, 51, 51));
        btn_xoaKM2.setText("XÓA");
        btn_xoaKM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaKM2ActionPerformed(evt);
            }
        });

        btn_clearKM2.setBackground(new java.awt.Color(255, 255, 102));
        btn_clearKM2.setText("CLEAR");
        btn_clearKM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearKM2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ngayBD2)
                            .addComponent(lbl_tenKM2)
                            .addComponent(lbl_ngayKT2))
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_moTa2)
                            .addComponent(lbl_phanTramGiam2)
                            .addComponent(btn_themKH2)
                            .addComponent(btn_xoaKM2))
                        .addGap(42, 42, 42)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tenKM, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ngayBD2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ngayKT2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txt_moTa2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_phanTramGiam2))
                        .addGap(42, 42, 42))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_clearKM2)
                            .addComponent(btn_suaKM2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbo_kieuKM2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tenKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tenKM2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ngayBD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ngayBD2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ngayKT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ngayKT2))
                .addGap(6, 6, 6)
                .addComponent(cbo_kieuKM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_phanTramGiam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_phanTramGiam2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_moTa2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_moTa2))
                .addGap(46, 46, 46)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_themKH2)
                    .addComponent(btn_suaKM2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_xoaKM2)
                    .addComponent(btn_clearKM2))
                .addGap(55, 55, 55))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("QUẢN LÝ KHUYẾN MÃI");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        lbl_timKiem.setText("TÌM KIẾM");

        btn_timKiem.setText("TÌM");
        btn_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timKiemActionPerformed(evt);
            }
        });

        tbl_khuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN", "BẮT ĐẦU", "KẾT THÚC", "KIỂU", "MÔ TẢ", "PHẦN TRĂM GIẢM"
            }
        ));
        tbl_khuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_khuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_khuyenMai);

        lbl_DSKM.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_DSKM.setText("DANH SÁCH KHUYẾN MÃI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lbl_timKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btn_timKiem)
                .addGap(123, 123, 123))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(lbl_DSKM)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_timKiem)
                    .addComponent(btn_timKiem))
                .addGap(15, 15, 15)
                .addComponent(lbl_DSKM, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tenKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenKMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenKMActionPerformed

    private void btn_themKH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themKH2ActionPerformed
        // TODO add your handling code here:
        // save();
        try{
            khmRepository.luu(getKMinput());
            
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearFrom();
    }//GEN-LAST:event_btn_themKH2ActionPerformed
    
    String tukhoatimkiem = "";
    private void btn_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timKiemActionPerformed
        tukhoatimkiem = txt_timKiem.getText().trim();
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_timKiemActionPerformed

    private void btn_suaKM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaKM2ActionPerformed
        // TODO add your handling code here:
        try{
            khmRepository.sua(getKMinput(),idbua);
            
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearFrom();
        //KhuyenMai kmSua = 
    }//GEN-LAST:event_btn_suaKM2ActionPerformed

    private void btn_xoaKM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaKM2ActionPerformed
        // TODO add your handling code here:
        try {
            khmRepository.xoa(idbua);
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearFrom();
    }//GEN-LAST:event_btn_xoaKM2ActionPerformed

    private void tbl_khuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_khuyenMaiMouseClicked
        // TODO add your handling code here:
        try {
            mouseClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbl_khuyenMaiMouseClicked

    private void btn_clearKM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearKM2ActionPerformed
        // TODO add your handling code here:
        clearFrom();
    }//GEN-LAST:event_btn_clearKM2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clearKM2;
    private javax.swing.JButton btn_suaKM2;
    private javax.swing.JButton btn_themKH2;
    private javax.swing.JButton btn_timKiem;
    private javax.swing.JButton btn_xoaKM2;
    private javax.swing.JComboBox<String> cbo_kieuKM2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_DSKM;
    private javax.swing.JLabel lbl_moTa2;
    private javax.swing.JLabel lbl_ngayBD2;
    private javax.swing.JLabel lbl_ngayKT2;
    private javax.swing.JLabel lbl_phanTramGiam2;
    private javax.swing.JLabel lbl_tenKM2;
    private javax.swing.JLabel lbl_timKiem;
    private javax.swing.JTable tbl_khuyenMai;
    private javax.swing.JTextField txt_moTa2;
    private javax.swing.JTextField txt_ngayBD2;
    private javax.swing.JTextField txt_ngayKT2;
    private javax.swing.JTextField txt_phanTramGiam2;
    private javax.swing.JTextField txt_tenKM;
    private javax.swing.JTextField txt_timKiem;
    // End of variables declaration//GEN-END:variables
}
