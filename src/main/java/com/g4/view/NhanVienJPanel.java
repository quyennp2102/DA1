/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g4.view;

import com.g4.entity.NhanVien;
import com.g4.repository.impl.NhanVienRepository;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author tuphp
 */
public class NhanVienJPanel extends javax.swing.JPanel {

    /**
     * Creates new form NhanVienJPanel
     */
    private NhanVienRepository repository = new NhanVienRepository();
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");

    public NhanVienJPanel() {
        initComponents();
        loadData();

    }

    public void loadData() {
        List<NhanVien> list = repository.selectAll();
        defaultTableModel = (DefaultTableModel) TBL.getModel();
        defaultTableModel.setRowCount(0);
        for (NhanVien x : list) {
            defaultTableModel.addRow(new Object[]{
                x.getId(), x.getTenNV(), x.GioiTinh(x.getGioiTinh()), x.getEmail(), x.getSdt(), x.getMatKhau(), x.getNgaySinh(), x.getNgayTao(), x.getDiaChi(), x.VaiTro(x.isVaiTro()), x.TrangThai(x.getTrangThai())
            });
        }

    }

    public NhanVien getNVInput() {
        NhanVien nv = new NhanVien();
        nv.setTenNV(txtTen.getText());
        nv.setDiaChi(txtDiachi.getText());
        nv.setEmail(txtEmail.getText());
        if (rdNam.isSelected()) {
            nv.setGioiTinh(1);
        } else {
            nv.setGioiTinh(0);
        }

        String ngaySinh = ft.format(txtNgaySinh.getDate());

        nv.setMatKhau(new String(txtMatkhau.getPassword()));
        nv.setNgaySinh(ngaySinh);

        nv.setSdt(txtSDT.getText());
        if (cbVaitro.getSelectedItem().equals("Nhan vien")) {
            nv.setVaiTro(true);
        } else {
            nv.setVaiTro(false);
        }

        return nv;
    }

    public boolean validateform() {
        if (txtTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ten nhan vien trong");
            return false;
        }
        if (txtDiachi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dia chi trong");
            return false;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email trong");
            return false;
        }
        if (txtSDT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "So dien thoai trong");
            return false;
        }
//        if (date_ngaySInh.equals("")) {
//            JOptionPane.showMessageDialog(this, "Ngay sinh trong");
//            return false;
//        }
//        if (!rdNam.isSelected() || !rdNu.isSelected()) {
//            JOptionPane.showMessageDialog(this, "Vui long chon gioi tinh");
//            return false;
//        }
        if (!isPasswordFieldNotEmpty(txtMatkhau)) {
            JOptionPane.showMessageDialog(this, "Vui long nhap mat khau");
            return false;
        }
        System.out.println(txtMatkhau.getPassword());
        return true;
    }

    public static boolean isPasswordFieldNotEmpty(JPasswordField passwordField) {
        char[] password = passwordField.getPassword();
        return password.length > 0;
    }
    
    public void save() {
        if (validateform()) {
            NhanVien nv = getNVInput();
            try {
                repository.insert(nv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Them thanh cong");
        }
        loadData();
    }

    public void chuotclick() throws ParseException {

        defaultTableModel = (DefaultTableModel) TBL.getModel();
        NhanVien nv = new NhanVien();
        int row = TBL.getSelectedRow();
        txtTen.setText(TBL.getValueAt(row, 1).toString());
        txtDiachi.setText(TBL.getValueAt(row, 8).toString());
        txtEmail.setText(TBL.getValueAt(row, 3).toString());
        txtSDT.setText(TBL.getValueAt(row, 4).toString());
        if (TBL.getValueAt(row, 2).toString().equals("Nam")) {
            rdNam.setSelected(true);
            rdNu.setSelected(false);
        } else {
            rdNu.setSelected(true);
            rdNam.setSelected(false);
        }
//        txtMatkhau.setText((TBL.getValueAt(row, 5).toString()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Chuyển đổi chuỗi ngày thành đối tượng Date
            String dateTable = TBL.getValueAt(row, 6).toString();
            Date date = sdf.parse(dateTable);
            txtNgaySinh.setDate(date);
            // In ra giá trị ngày đã chuyển đổi
            System.out.println("Converted Date: " + date);

            // Bạn có thể sử dụng đối tượng Date này cho mục đích khác
        } catch (ParseException e) {
            e.printStackTrace();
        }

        defaultTableModel = (DefaultTableModel) TBL.getModel();
        String vaiTroValue = TBL.getValueAt(row, 10).toString();
        cbVaitro.setSelectedItem(vaiTroValue);
    }

    public void deleteNV() throws SQLException {
        int row = TBL.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng");
        }
        String id = TBL.getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(this, "Xóa thành công");
        repository.delete(id);
        loadData();

    }

    public void UpdateNV() {
        int row = TBL.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng");
        }
        NhanVien nv = new NhanVien();
        nv.setDiaChi(txtDiachi.getText());
        nv.setEmail(txtEmail.getText());
        if (rdNam.isSelected()) {
            nv.setGioiTinh(1);
        } else {
            nv.setGioiTinh(0);
        }
        nv.setMatKhau(new String(txtMatkhau.getPassword()));
        nv.setSdt(txtSDT.getText());
        nv.setTenNV(txtTen.getText());
        String ngaySinh = ft.format(txtNgaySinh.getDate());
        nv.setNgaySinh(ngaySinh);
        nv.setSdt(txtSDT.getText());
        if (cbVaitro.getSelectedItem().equals("Nhan vien")) {
            nv.setVaiTro(true);
        } else {
            nv.setVaiTro(false);
        }

        if (rdNam.isSelected()) {
            nv.setGioiTinh(1);
        } else {
            nv.setGioiTinh(0);
        }
        nv.setId(TBL.getValueAt(row, 0).toString());
        repository.update(nv);
        JOptionPane.showMessageDialog(this, "Sửa thành công");
        loadData();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBL = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiachi = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        cbVaitro = new javax.swing.JComboBox<>();
        txtMatkhau = new javax.swing.JPasswordField();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();

        jLabel1.setText("Ho va ten");

        TBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Ten Nhan Vien", "Gioi Tinh", "Email", "SDT", "Mat Khau", "Ngay Sinh", "Ngay Tao", "Dia Chi", "Vai Tro", "Trang Thai"
            }
        ));
        TBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TBLMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TBL);

        btnThem.setText("Them");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jLabel2.setText("Gioi tinh");

        jLabel3.setText("Ngay sinh");

        jLabel4.setText("SDT");

        jLabel5.setText("Dia chi");

        buttonGroup1.add(rdNam);
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nu");

        txtDiachi.setColumns(20);
        txtDiachi.setRows(5);
        jScrollPane2.setViewportView(txtDiachi);

        jLabel6.setText("Email");

        jLabel8.setText("Mat khau");

        jLabel9.setText("Vai tro");

        jLabel11.setText("Tim kiem");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        cbVaitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhan vien", "Quan li", " " }));

        btnSua.setText("Sua");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jButton1.setText("Load");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtNgaySinh.setDateFormatString("yyyy/MM/dd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdNam)
                                        .addGap(38, 38, 38)
                                        .addComponent(rdNu))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbVaitro, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                                .addComponent(txtMatkhau)))
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(89, 89, 89))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSua, btnThem, btnXoa});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtMatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbVaitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(rdNam)
                            .addComponent(rdNu))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSua, btnThem, btnXoa});

    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        save();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        UpdateNV();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        try {
            deleteNV();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void TBLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLMouseClicked
        try {
            chuotclick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_TBLMouseClicked

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        DefaultTableModel dmt = (DefaultTableModel) TBL.getModel();
        String search = txtTimKiem.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dmt);
        TBL.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadData();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TBL;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbVaitro;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JTextArea txtDiachi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtMatkhau;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
