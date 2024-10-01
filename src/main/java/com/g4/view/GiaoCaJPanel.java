/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g4.view;

import com.g4.entity.CaLam;
import com.g4.entity.GiaoCa;
import com.g4.entity.NhanVien;
import com.g4.repository.impl.CaLamRepository;
import com.g4.repository.impl.GiaoCaRepository;
import com.g4.repository.impl.GiaoCaVMRepository;
import com.g4.utils.Auth;
import com.g4.viewmodel.GiaoCaViewModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author tuphp
 */
public class GiaoCaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form GiaoCaJPanel
     *
     *
     */
    private GiaoCaVMRepository giaocaVMRepo = new GiaoCaVMRepository();
    private CaLamRepository calamRepo = new CaLamRepository();
    private GiaoCaRepository giaocaRepo = new GiaoCaRepository();
    private DefaultTableModel defaultTableModel = new DefaultTableModel();

    public GiaoCaJPanel() {
        initComponents();
        lblTenNV.setText(Auth.user.getTenNV());
        lblTrangThai.setText("Chưa vào ca");
        loadDataGiaoCa();
    }

    public void loadDataGiaoCa() {
        List<GiaoCaViewModel> list = giaocaVMRepo.selectAll();
        defaultTableModel = (DefaultTableModel) tbGiaoCa.getModel();
        defaultTableModel.setRowCount(0);
        int i = 1;
        for (GiaoCaViewModel x : list) {
            defaultTableModel.addRow(new Object[]{
                i, x.getId(), x.getTenNV(), x.getNgayGiaoCa(), x.getGioBatDau(), x.getGioKetThu()
            });
            i++;
        }

    }

    public CaLam inputCaLam() {
        CaLam cl = new CaLam();

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(now);

        cl.setGioBatDau(formattedTime);

        try {
            Date ca1StartTime = sdf.parse("08:00");
            Date ca1EndTime = sdf.parse("12:00");
            Date ca2StartTime = sdf.parse("12:00");
            Date ca2EndTime = sdf.parse("16:00");
            Date ca3StartTime = sdf.parse("16:00");
            Date ca3EndTime = sdf.parse("22:00");

            Date formattedTimeAsDate = sdf.parse(formattedTime);

            if (formattedTimeAsDate.compareTo(ca1StartTime) >= 0 && formattedTimeAsDate.before(ca1EndTime)) {
                cl.setCaTrongNgay(1);
            } else if (formattedTimeAsDate.compareTo(ca2StartTime) >= 0 && formattedTimeAsDate.before(ca2EndTime)) {
                cl.setCaTrongNgay(2);
            } else if (formattedTimeAsDate.compareTo(ca3StartTime) >= 0 && formattedTimeAsDate.before(ca3EndTime)) {
                cl.setCaTrongNgay(3);
            } else {
                cl.setCaTrongNgay(4);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lblTrangThai.setText("Đang trong ca làm");
        lblGioBatDau.setText(formattedTime);

        return cl;
    }

    public boolean validateCaLam() {
        if (lblTrangThai.getText().equals("Đang trong ca làm")) {
            return false;
        }
        return true;
    }

    public GiaoCa inputGiaoCa() {
        GiaoCa gc = new GiaoCa();
        gc.setIdNV(Auth.user.getId().toString());
        int idcl = calamRepo.CaLamHienTai();
        gc.setIdCL(idcl);
        return gc;
    }

    public void BatDauCaLam() {
        int tl = JOptionPane.showConfirmDialog(this, "Bạn có muốn bắt đầu ca làm không?", "Bắt đầu ca", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (tl == JOptionPane.YES_OPTION) {
            if (validateCaLam()) {
                CaLam cl = inputCaLam();
                System.out.println("" + cl.getId());
                try {
                    calamRepo.insert(cl);
                    GiaoCa gc = inputGiaoCa();
                    giaocaRepo.insert(gc);
                    lblGioKetThuc.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Bắt đầu ca làm thành công");

            } else {
                JOptionPane.showMessageDialog(this, "Bạn đã bắt đầu ca làm rồi");
            }
            loadDataGiaoCa();
        }

    }

    public void KetThucCaLam() {
        int tl = JOptionPane.showConfirmDialog(this, "Bạn có muốn kết thúc ca làm không?", "Kết thúc ca", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (tl == JOptionPane.YES_OPTION) {
            if (lblTrangThai.getText().equals("Chưa vào ca") || lblTrangThai.getText().equals("Kết thúc ca làm")) {
                JOptionPane.showMessageDialog(this, "Bạn phải bắt đầu ca làm");
            }
            CaLam cl = new CaLam();

            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String formattedTime = sdf.format(now);

            cl.setGioKetThuc(formattedTime);
            int idcl = calamRepo.CaLamHienTai();

            calamRepo.update2(formattedTime, idcl);
            JOptionPane.showMessageDialog(this, "Kết thúc ca làm thành công");
            lblTrangThai.setText("Kết thúc ca làm");
            lblGioKetThuc.setText(formattedTime);

            loadDataGiaoCa();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tbGiaoCa = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        btnGBD = new javax.swing.JButton();
        btnGKT = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblGioBatDau = new javax.swing.JLabel();
        lblGioKetThuc = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        tbGiaoCa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, "", null, null, null},
                {null, null, "", null, null, null},
                {null, null, "", null, null, null},
                {null, null, "", null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên nhân viên", "Ngày giao ca", "Giờ bắt đầu", "Giờ kết thúc"
            }
        ));
        jScrollPane1.setViewportView(tbGiaoCa);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Thông tin giao ca");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Tên nhân viên");

        lblTenNV.setText("?");

        btnGBD.setText("Bắt đầu ");
        btnGBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGBDActionPerformed(evt);
            }
        });

        btnGKT.setText("Kết thúc");
        btnGKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGKTActionPerformed(evt);
            }
        });

        jLabel4.setText("Trạng thái");

        lblTrangThai.setText("?");

        jLabel2.setText("Giờ kết thúc");

        jLabel5.setText("Giờ bắt đầu");

        lblGioBatDau.setText("?");

        lblGioKetThuc.setText("?");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel6.setText("Tìm kiếm ngày");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTrangThai)
                    .addComponent(lblTenNV))
                .addGap(106, 106, 106)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblGioBatDau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                        .addComponent(btnGBD, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnGKT, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblGioKetThuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnGBD, btnGKT});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblTenNV)
                    .addComponent(btnGBD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGKT, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(lblGioBatDau))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblTrangThai)
                            .addComponent(jLabel2)
                            .addComponent(lblGioKetThuc))
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(20, 20, 20))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(356, 356, 356)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGBDActionPerformed
        BatDauCaLam();
    }//GEN-LAST:event_btnGBDActionPerformed

    private void btnGKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGKTActionPerformed
        KetThucCaLam();
    }//GEN-LAST:event_btnGKTActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        if (txtTimKiem == null) {
            loadDataGiaoCa();
        } else {
            DefaultTableModel dmt = (DefaultTableModel) tbGiaoCa.getModel();
            String search = txtTimKiem.getText().toLowerCase();
            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dmt);
            tbGiaoCa.setRowSorter(tr);
            tr.setRowFilter(RowFilter.regexFilter(search));
        }
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGBD;
    private javax.swing.JButton btnGKT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGioBatDau;
    private javax.swing.JLabel lblGioKetThuc;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JTable tbGiaoCa;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
