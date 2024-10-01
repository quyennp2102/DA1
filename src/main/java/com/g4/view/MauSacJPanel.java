/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.g4.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import com.g4.repository.impl.MauSacRepo;
import com.g4.entity.MauSac;
import com.g4.utils.Utility;
import java.util.ArrayList;
import com.g4.viewmodel.MauSacViewModel;
/**
 *
 * @author khuong duc
 */
public class MauSacJPanel extends javax.swing.JFrame {
    
    private MauSacRepo msrepo = new MauSacRepo();
    private List<MauSac> listMS = new ArrayList<>();

    private Utility uti = new Utility();
    
    private DefaultTableModel modelmau = new DefaultTableModel();
    private List<MauSacViewModel> listMSVM = new ArrayList<>();
    private String mausac;
    
    /**
     * Creates new form MauSacJPanel
     */
    public MauSacJPanel(java.awt.Frame parent, boolean modal) {
        initComponents();
        
        modelmau = (DefaultTableModel) tbl_ttmausac.getModel();
        tbl_ttmausac.setModel(modelmau);
        showData(listMS = msrepo.getMauSac());
    }
    
    private void showData(List<MauSac> list){
        modelmau.setRowCount(0);
        
        for(MauSac ms : list){
            Object[] data = new Object[]{
                ms.getId(),ms.getTenMauSac()
            };
            modelmau.addRow(data);
        }
    }
    
    //private void fillData(int index){
    //    MauSac ms = listMS.get(index);
        //txtmauID.setText(ms.getId());
    //    txt_ttMS.setText(ms.getTenMauSac());
    //}
    private void filldata(){
        List<MauSac> ms = msrepo.getMauSac();
        modelmau.setRowCount(0);
        for (MauSac object : msrepo.getMauSac()) {
            modelmau.addRow(new Object[]{object.getId(), object.getTenMauSac()});
        }
        
    }
    private String getMS(int i){
        try {
            String ten="";
            ten=txt_ttMS.getText().trim();
            
            if(ten.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(this, "Nhap mau");
                return null;
            }else {
                return ten;
            }
            
        } catch (Exception e) {
            return null;
        }
        
    }
    //public boolean checkDL(){
    //    if(uti.CheckRong(txt_ttMS.getText())){
    //        JOptionPane.showMessageDialog(this, "Mã không được để trống");
     //       txt_ttMS.requestFocus();
    //        txt_ttMS.setText("");
    //        return true;
    //    }if (uti.DemChuoi(txt_ttMS.getText()) > 20) {
    //        JOptionPane.showMessageDialog(this, "Mã không lớn hơn 20 ký tự");
    //        txt_ttMS.requestFocus();
    //        txt_ttMS.setText("");
    //        return true;
    //    }
     //   return false;
    //}
    
    public String getMauSac(){
        return mausac;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnmausac = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_ttmausac = new javax.swing.JTable();
        lbl_mausac = new javax.swing.JLabel();
        btn_ttThemMS = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_ttMS = new javax.swing.JTextField();
        txtmauID = new javax.swing.JTextField();
        btn_ttClearms = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnmausac.setBackground(new java.awt.Color(204, 204, 204));

        tbl_ttmausac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Màu sắc"
            }
        ));
        tbl_ttmausac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ttmausacMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_ttmausac);

        lbl_mausac.setText("Màu sắc");

        btn_ttThemMS.setText("Thêm");
        btn_ttThemMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttThemMSActionPerformed(evt);
            }
        });

        jLabel1.setText("ID: ");

        jLabel2.setText("Màu sắc: ");

        txt_ttMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ttMSActionPerformed(evt);
            }
        });

        txtmauID.setText("_ _");

        btn_ttClearms.setText("Clear");
        btn_ttClearms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttClearmsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnmausacLayout = new javax.swing.GroupLayout(jpnmausac);
        jpnmausac.setLayout(jpnmausacLayout);
        jpnmausacLayout.setHorizontalGroup(
            jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnmausacLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ttClearms, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_ttThemMS, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(28, 28, 28))
            .addGroup(jpnmausacLayout.createSequentialGroup()
                .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnmausacLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(lbl_mausac))
                    .addGroup(jpnmausacLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(29, 29, 29)
                        .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ttMS, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmauID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnmausacLayout.setVerticalGroup(
            jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnmausacLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_mausac)
                .addGap(3, 3, 3)
                .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtmauID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_ttMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnmausacLayout.createSequentialGroup()
                        .addComponent(btn_ttThemMS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_ttClearms)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(jpnmausac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpnmausac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_ttmausacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ttmausacMouseClicked
        // TODO add your handling code here:
        int vitri = tbl_ttmausac.getSelectedRow();
        txtmauID.setText(String.valueOf(msrepo.getMauSac().get(vitri).getId()));
        txt_ttMS.setText(msrepo.getMauSac().get(vitri).getTenMauSac());
    }//GEN-LAST:event_tbl_ttmausacMouseClicked

    private void btn_ttThemMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttThemMSActionPerformed
        // TODO add your handling code here:
        String tenMau = getMS(1);
        if(tenMau!=null){
            try {
                msrepo.themMau(tenMau);
            } catch (Exception e) {
            }
            filldata();
            
        }
        
    }//GEN-LAST:event_btn_ttThemMSActionPerformed

    private void txt_ttMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ttMSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ttMSActionPerformed

    private void btn_ttClearmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttClearmsActionPerformed
        // TODO add your handling code here:
        txtmauID.setText("_ _");
        txt_ttMS.setText("");
    }//GEN-LAST:event_btn_ttClearmsActionPerformed

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
            java.util.logging.Logger.getLogger(MauSacJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MauSacJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MauSacJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MauSacJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MauSacJPanel dialog = new MauSacJPanel(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ttClearms;
    private javax.swing.JButton btn_ttThemMS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel jpnmausac;
    private javax.swing.JLabel lbl_mausac;
    private javax.swing.JTable tbl_ttmausac;
    private javax.swing.JTextField txt_ttMS;
    private javax.swing.JTextField txtmauID;
    // End of variables declaration//GEN-END:variables
}
