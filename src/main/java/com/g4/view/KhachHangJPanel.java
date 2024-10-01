/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g4.view;

import com.g4.entity.KhachHang;
import com.g4.repository.IKhachHangRepository;
import com.g4.repository.impl.KhachHangRepository;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tuphp
 */
public class KhachHangJPanel extends javax.swing.JPanel {

    private IKhachHangRepository KHrepository = new KhachHangRepository();

    /**
     * Creates new form KhachHangJPanel
     */
    public KhachHangJPanel() {
        initComponents();
        loadData();
    }

    public void loadData() {
        try {
            List<KhachHang> list = KHrepository.getKH();
            DefaultTableModel defaultTableModel = (DefaultTableModel) tblKhachHang.getModel();
            defaultTableModel.setRowCount(0);
            for (KhachHang x : list) {
                Object[] rowDT = {
                    x.getId(), x.getMaKH(),
                    x.getTenKH(),
                    x.getSdt(),
                    x.getNgayTao(),
                    (x.getTrangThai() == 1) ? "Active" : "Inactive"
                };
                defaultTableModel.addRow(rowDT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FindKH() {
        String fw = txtTimKiem.getText();
        try {
            List<KhachHang> list = KHrepository.findKH(fw);
            DefaultTableModel defaultTableModel = (DefaultTableModel) tblKhachHang.getModel();
            defaultTableModel.setRowCount(0);
            for (KhachHang x : list) {
                Object[] rowDT = {
                    x.getId(),
                    x.getTenKH(),
                    x.getMaKH(),
                    x.getSdt(),
                    x.getNgayTao(),
                    (x.getTrangThai() == 1) ? "Active" : "Inactive"
                };
                defaultTableModel.addRow(rowDT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtID.setText("[Customer ID]");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSDT.setText("");
        txtNgayTao.setText("[Current Date]");
        txtTimKiem.setText("");
        cbTrangthai.setSelectedIndex(0);
    }

    private void fillform() {
        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            return;
        }

        txtID.setText(tblKhachHang.getValueAt(row, 0).toString());
        txtMaKH.setText(tblKhachHang.getValueAt(row, 1).toString());
        txtTenKH.setText(tblKhachHang.getValueAt(row, 2).toString());
        txtSDT.setText(tblKhachHang.getValueAt(row, 3).toString());
        txtNgayTao.setText(tblKhachHang.getValueAt(row, 4).toString());

        boolean trangThai = "Active".equals(tblKhachHang.getValueAt(row, 5).toString());
        cbTrangthai.setSelectedIndex(trangThai ? 1 : 0);
    }

    private void delete() throws SQLException {

        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            return;
        }
        String IDdelete = tblKhachHang.getValueAt(row, 0).toString();
        this.KHrepository.delete(IDdelete);
        JOptionPane.showMessageDialog(this, "Delete successful!");
        clearForm();
        loadData();
    }

    private void deleteFEV() throws SQLException {

        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            return;
        }
        String IDdelete = tblKhachHang.getValueAt(row, 0).toString();
        this.KHrepository.deleteForever(IDdelete);
        JOptionPane.showMessageDialog(this, "Delete successful!");
        clearForm();
        loadData();

    }

    private KhachHang getForm(int kieu) throws ParseException, SQLException {
        String maKH = txtMaKH.getText();
        String tenKH = txtTenKH.getText();

        String sdt = txtSDT.getText();

        int trangThai = cbTrangthai.getSelectedIndex();
        try {

            String trangThaiStr = String.valueOf(trangThai);
            Date ngayTao = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());
            if (maKH.isEmpty() || tenKH.isEmpty() || sdt.isEmpty() || trangThaiStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all information!");
                return null;
            } else {
                boolean checkTT = false;
                for (KhachHang kh : KHrepository.getKH()) {
                    if (maKH.equalsIgnoreCase(kh.getMaKH())) {
                        checkTT = true;
                        break;
                    }
                }
                for (KhachHang kh : KHrepository.getKHCu()) {
                    if (maKH.equalsIgnoreCase(kh.getMaKH())) {
                        checkTT = true;
                        break;
                    }
                }
                if (checkTT == true && kieu == 1) {
                    JOptionPane.showMessageDialog(this, "Mã này đã tồn tại");
                    return null;
                } else {
                    return new KhachHang(maKH, tenKH, sdt, ngayTao, trangThai);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giá bán và số lượng phải là một chữ số");
            return null;
        }

    }

    private void addNew() throws SQLException, ParseException {
        KhachHang kh = getForm(1);

        if (kh == null) {
            return;
        }

        try {
            this.KHrepository.insert(kh);
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Thêm mới không thành công!");

        }

        loadData();
        clearForm();

    }

    private void Sua() throws SQLException, ParseException {
        KhachHang khSua = getForm(2);

        if (khSua == null) {
            JOptionPane.showMessageDialog(this, "Cannot get data");
            return;
        }

        try {
            khSua.setId(Integer.parseInt(txtID.getText()));
            this.KHrepository.update(khSua);
            JOptionPane.showMessageDialog(this, "Update successful!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Thêm mới không thành công!");

        }

        loadData();
        clearForm();
    }

    public void fillKHDeleted() {
        try {
            List<KhachHang> list = KHrepository.getKHCu();
            DefaultTableModel defaultTableModel = (DefaultTableModel) tblKhachHang.getModel();
            defaultTableModel.setRowCount(0);
            for (KhachHang x : list) {
                Object[] rowDT = {
                    x.getId(),
                    x.getMaKH(),
                    x.getTenKH(),
                    x.getSdt(),
                    x.getNgayTao(),
                    (x.getTrangThai() == 1) ? "Active" : "Inactive"};
                defaultTableModel.addRow(rowDT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        txtID = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnLichSuNhanVien = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        cbTrangthai = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();

        jLabel13.setText("ID");

        txtID.setText("IDKH");

        jLabel6.setText("Mã Khách Hàng");

        jLabel7.setText("Ngày Tạo");

        jLabel2.setText("Họ và Tên");

        jLabel3.setText("Số Điện Thoại");

        txtNgayTao.setText("ngay bây giờ");

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnQuayLai.setText("Quay lại");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnLichSuNhanVien.setText("Lịch sử");
        btnLichSuNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLichSuNhanVienActionPerformed(evt);
            }
        });

        jLabel11.setText("Tìm Kiếm");

        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã Khách Hàng", "Tên Khách Hàng", "SDT", "Ngày Tạo", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        cbTrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactive", "Active" }));

        jLabel10.setText("Trạng Thái");

        jLabel9.setText("QUẢN LÍ KHÁCH HÀNG");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel13))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtID)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(114, 114, 114)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayTao)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa)
                                .addGap(18, 18, 18)
                                .addComponent(btnClear)
                                .addGap(18, 18, 18)
                                .addComponent(btnLichSuNhanVien)
                                .addGap(18, 18, 18)
                                .addComponent(btnQuayLai)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTim))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1088, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(446, 446, 446)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtID))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(cbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel9)
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtNgayTao))
                        .addGap(137, 137, 137)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLichSuNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTim)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int quyetDinh = JOptionPane.showConfirmDialog(this, "Bạn xác nhận muốn sửa?", "", JOptionPane.YES_NO_OPTION);
        if (quyetDinh == JOptionPane.YES_OPTION) {
            try {
                Sua();
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(KhachHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed

        loadData();
        clearForm();
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            return;
        }
        boolean trangThai = "Active".equals(tblKhachHang.getValueAt(row, 5).toString());
        int quyetDinh = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "", JOptionPane.YES_NO_OPTION);
        if (quyetDinh == JOptionPane.YES_OPTION) {
            try {
                if (trangThai == true) {
                    delete();
                } else {
                    int quyetDinh2 = JOptionPane.showConfirmDialog(this, "Khách hàng này sẽ bị xóa vĩnh viễn. Bạn vẫn muốn xóa chứ!", "", JOptionPane.YES_NO_OPTION);
                    if (quyetDinh2 == JOptionPane.YES_OPTION) {
                        deleteFEV();
                        fillKHDeleted();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnLichSuNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLichSuNhanVienActionPerformed
        fillKHDeleted();
        clearForm();
    }//GEN-LAST:event_btnLichSuNhanVienActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        FindKH();
        clearForm();
    }//GEN-LAST:event_btnTimActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        fillform();
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            addNew();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(KhachHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLichSuNhanVien;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbTrangthai;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JLabel txtID;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JLabel txtNgayTao;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
