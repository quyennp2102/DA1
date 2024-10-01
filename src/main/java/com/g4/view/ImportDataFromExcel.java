package com.g4.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportDataFromExcel extends javax.swing.JFrame {

    private DefaultTableModel model;

    public ImportDataFromExcel() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
        //lấy tên sp
//    public String layIDSanPham(String tenCV) {
//        String idCV = null;
//        ArrayList<SanPhamDomain> list = (ArrayList<SanPhamDomain>) sanPhamService.getAll();
//        for (SanPhamDomain chucVu : list) {
//            if (chucVu.getTenSP().equals(tenCV)) {
//                idCV = chucVu.getId();
//            }
//
//        }
//        return idCV;
//
//    }

    //lấy tên nhà sản xuất
//    public String layIDNSX(String tenCV) {
//        String idCV = null;
//        ArrayList<NSXdomain> list = (ArrayList<NSXdomain>) nsxService.getAll();
//        for (NSXdomain chucVu : list) {
//            if (chucVu.getTen().equals(tenCV)) {
//                idCV = chucVu.getId();
//            }
//
//        }
//        return idCV;
//
//    }

    //lấy tên màu sắc
//    public String layIDMauSac(String tenCV) {
//        String idCV = null;
//        ArrayList<MauSacdomain> list = (ArrayList<MauSacdomain>) mauSacService.getAll();
//        for (MauSacdomain chucVu : list) {
//            if (chucVu.getTen().equals(tenCV)) {
//                idCV = chucVu.getId();
//            }
//
//        }
//        return idCV;
//
//    }

    //lấy tên dòng sản phẩm
//    public String layIDDongSP(String tenCV) {
//        String idCV = null;
//        ArrayList<DongSPDomain> list = (ArrayList<DongSPDomain>) dongSanPhamService.getAll();
//        for (DongSPDomain chucVu : list) {
//            if (chucVu.getTen().equals(tenCV)) {
//                idCV = chucVu.getId();
//            }
//
//        }
//        return idCV;
//
//    }

    //lấy tên chất liệu
//    public String layIDChatLieu(String tenCV) {
//        String idCV = null;
//        ArrayList<ChatLieuDomain> list = (ArrayList<ChatLieuDomain>) chatLieuService.getAll();
//        for (ChatLieuDomain chucVu : list) {
//            if (chucVu.getTen().equals(tenCV)) {
//                idCV = chucVu.getId();
//            }
//
//        }
//        return idCV;
//
//    }

    //lấy tên size
//    public String layIDSize(String tenCV) {
//        String idCV = null;
//        ArrayList<SizeDomain> list = (ArrayList<SizeDomain>) service.getAll();
//        for (SizeDomain chucVu : list) {
//            if (chucVu.getSoSize().equals(tenCV)) {
//                idCV = chucVu.getId();
//            }
//
//        }
//        return idCV;
//
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbbang = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Improt(Excel)");
        jButton1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Đóng");
        jButton2.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tbbang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Giá bán", "Số lượng", "Mô tả", "Kích thước", "Màu sắc", "Chất liệu", "Thương hiệu"
            }
        ));
        jScrollPane1.setViewportView(tbbang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        model = (DefaultTableModel) tbbang.getModel();

        model.setColumnCount(0);

        model.addColumn("Mã SP");
        model.addColumn("Tên SP");
        model.addColumn("Giá bán");
        model.addColumn("Số lượng");
        model.addColumn("Mô tả");
        model.addColumn("Kích cỡ");
        model.addColumn("Màu sắc");
        model.addColumn("Chất liệu");
        model.addColumn("Thương hiệu");
        model.setRowCount(0);
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportWorkBook = null;

        String currentDirectoryPath = "đường dẫn file";
        JFileChooser excelFileChooserImport = new JFileChooser(currentDirectoryPath);

        // Đặt bộ lọc cho JFileChooser để chỉ lấy các tệp tin có đuôi xlsx
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
        excelFileChooserImport.setFileFilter(filter);

        int excelChooser = excelFileChooserImport.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            File excelFile = excelFileChooserImport.getSelectedFile();
            try {
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelImportWorkBook = new XSSFWorkbook(excelBIS);

                XSSFSheet excelSheet = excelImportWorkBook.getSheetAt(0);

                for (int i = 0; i < excelSheet.getLastRowNum(); i++) {
                    XSSFRow excelRow = excelSheet.getRow(i);
                    XSSFCell cell1 = excelRow.getCell(0);
                    XSSFCell cell2 = excelRow.getCell(1);
                    XSSFCell cell3 = excelRow.getCell(2);
                    XSSFCell cell4 = excelRow.getCell(3);
                    XSSFCell cell5 = excelRow.getCell(4);
                    XSSFCell cell6 = excelRow.getCell(5);
                    XSSFCell cell7 = excelRow.getCell(6);
                    XSSFCell cell8 = excelRow.getCell(7);
                    XSSFCell cell9 = excelRow.getCell(8);

                    model.addRow(new Object[]{cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9});

                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ImportDataFromExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ImportDataFromExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed

//        int insertedRows = 0;
//        int row = tbbang.getRowCount();
//        ChiTiet ct = new ChiTiet();
//        for (int i = 0; i < row; i++) {
//            String ten = layIDSanPham(model.getValueAt(i, 0).toString());
//            String nsx = layIDNSX(model.getValueAt(i, 1).toString());
//            String mauSac = layIDMauSac(model.getValueAt(i, 2).toString());
//            String dongSP = layIDDongSP(model.getValueAt(i, 3).toString());
//            String chatLieu = layIDChatLieu(model.getValueAt(i, 4).toString());
//
////            String size = layIDSize(model.getValueAt(i, 5).toString());
////            String dob[] = size.split("\\.");
////            String part1 = dob[0];
////            System.out.println(size);
//            String size = model.getValueAt(i, 5).toString();
//            String dob[] = size.split("\\.");
//            String part1 = dob[0];
////            System.out.println(part1);
//            String layIDsize = layIDSize(part1);
////            System.out.println(layIDsize);
//
//            String moTa = model.getValueAt(i, 6).toString();
//            String soLuongTon = model.getValueAt(i, 7).toString();
//            String dob1[] = soLuongTon.split("\\.");
//            String part2 = dob1[0];
////            System.out.println(part2);
//            String giaNhap = model.getValueAt(i, 8).toString();
//            String giaBan = model.getValueAt(i, 9).toString();
//            String anh = model.getValueAt(i, 10).toString();
//            String trangThai = model.getValueAt(i, 11).toString();
//            String dob3[] = trangThai.split("\\.");
//            String part3 = dob3[0];
////            System.out.println(part3);
//            try {
//                ct.setIdSP(ten);
//                ct.setIdNSX(nsx);
//                ct.setIdMauSac(mauSac);
//                ct.setDongSP(dongSP);
//                ct.setChatLieu(chatLieu);
//                ct.setSize(layIDsize);
//                ct.setMoTa(moTa);
//                ct.setSoLuongTon(part2);
//                ct.setGiaNhap(giaNhap);
//                ct.setGiaBan(giaBan);
//                ct.setAnh(anh);
//                ct.setTrangThai(part3);
//                boolean kt = ctrepo.add(ct);
//            } catch (Exception e) {
//            }
//
//        }
//        JOptionPane.showMessageDialog(this, "Them thanh cong");


    }//GEN-LAST:event_btnLuuActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImportDataFromExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImportDataFromExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImportDataFromExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImportDataFromExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImportDataFromExcel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbbang;
    // End of variables declaration//GEN-END:variables
}
