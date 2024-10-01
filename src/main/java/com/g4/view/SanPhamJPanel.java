/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g4.view;

import com.g4.entity.KetCau;
import com.g4.entity.DungTich;
import com.g4.entity.MauSac;
import com.g4.entity.SanPham;
import com.g4.entity.HieuUng;
import com.g4.entity.NhomHuong;
import com.g4.entity.ChiTietSP;
import com.g4.repository.impl.KetCauRepo;
import com.g4.repository.impl.DungTichRepo;
import com.g4.repository.impl.MauSacRepo;
import com.g4.repository.impl.SanPhamRepository;
import com.g4.repository.impl.HieuUngRepo;
import com.g4.repository.impl.NhomHuongRepo;
import com.g4.utils.Auth;
import com.g4.main.Main;
import com.g4.repository.impl.ChiTietSPRepo;
import com.g4.viewmodel.SanPhamViewModel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author tuphp
 */
public class SanPhamJPanel extends javax.swing.JPanel {

    /**
     * Creates new form SanPhamJPanel
     */
    DecimalFormat fomat = new DecimalFormat("###,###,###");
    
    private SanPhamRepository spRP = new SanPhamRepository();
    
    private DungTichRepo dtRP = new DungTichRepo();
    private KetCauRepo kcRP = new KetCauRepo();
    private MauSacRepo msRP = new MauSacRepo();
    private NhomHuongRepo nhRP = new NhomHuongRepo();
    private HieuUngRepo huRP = new HieuUngRepo();
    private ChiTietSPRepo ctspRepo = new ChiTietSPRepo();
    
    private List<SanPhamViewModel> listSPVM = new ArrayList<>();
    private List<SanPham> listSP = new ArrayList<>();
    
    private DefaultTableModel modelSPban = new DefaultTableModel();
    private DefaultTableModel modelCTSPban = new DefaultTableModel(); 
    private DefaultTableModel tblmodelSP = new DefaultTableModel();
    
    private DefaultTableModel modelMau = new DefaultTableModel();
    private DefaultTableModel modelKeuCau = new DefaultTableModel();
    private DefaultTableModel modelNhomHuong = new DefaultTableModel();
    private DefaultTableModel modelHieuUng = new DefaultTableModel();
    private DefaultTableModel modelDungTich = new DefaultTableModel();
    
    
    private String dfkw = "";
    
    public SanPhamJPanel() {
        initComponents();
        
        modelSPban = (DefaultTableModel) tbl_sanpham.getModel();
        modelCTSPban = (DefaultTableModel) tbl_ctsp.getModel();
        
        modelKeuCau = (DefaultTableModel) tbl_ttketcau.getModel();
        modelMau = (DefaultTableModel) tbl_ttmausac.getModel();
        modelNhomHuong = (DefaultTableModel) tbl_ttnhomhuong.getModel();
        modelHieuUng = (DefaultTableModel) tbl_tthieuung.getModel();
        modelDungTich = (DefaultTableModel) tbl_ttdungtich.getModel();
        
        fillBan();
        fillThuocTinh();
        fillCTSP();

        loadSanPham(listSPVM);
    }
    
    public void loadSanPham(List<SanPhamViewModel >listspvm){
        
        modelSPban.setRowCount(0);
        for (SanPhamViewModel sp : listspvm) {
            modelSPban.addRow(sp.todataRowSanPham());
        }
    }
    
    public void setMau(String mausac){
        lbl_mausac.setText(mausac);
    }
    
    public void setKetCau(String ketcau){
        lbl_ketcau.setText(ketcau);
    }
    
    public void setNhomHuong(String nhomhuong){
        lbl_nhomhuong.setText(nhomhuong);
    }
    public void setDungTich(String dungtich){
        lbl_dungtich.setText(dungtich);
    }
    public void setHieuUng(String hieuung){
        lbl_hieuung.setText(hieuung);
    }

    
    private void fillBan() {
        try {
            List<SanPham> conban = spRP.getBan(dfkw);
            modelSPban.setRowCount(0);
            for(SanPham sp: conban){
                modelSPban.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getGiaTien(),
                    sp.getSoLuong(),
                    sp.getIdKetCau(),
                    sp.getIdMauSac(),
                    sp.getIdNhomHuong(),
                    sp.getIdHieuUng(),
                    sp.getIdDungTich()
                });
            }

        } catch (Exception e) {
        }
    }
    
    private void fillForm(SanPham duocchon, boolean banko) {
        txt_giabansp.setText(String.valueOf(duocchon.getGiaTien()));
        txtsanphamID.setText(String.valueOf(duocchon.getId()));
        txt_masp.setText(duocchon.getMaSP());
        txt_soluongsp.setText(String.valueOf(duocchon.getSoLuong()));
        txt_tensp.setText(duocchon.getTenSP());
        
        cbo_nhomhuong.setSelectedItem(nhRP.getByID(duocchon.getIdNhomHuong()).getTenNhomHuong());
        cbo_hieuung.setSelectedItem(huRP.getByID(duocchon.getIdHieuUng()).getTenhieuung());
        cbo_mausac.setSelectedItem(msRP.getByID(duocchon.getIdMauSac()).getTenMauSac());
        cbo_ketcau.setSelectedItem(kcRP.getByID(duocchon.getIdKetCau()).getTenketcau());
        cbo_dungtich.setSelectedItem(dtRP.getByID(duocchon.getIdDungTich()).getDungtich());
        

    }
    
    private void fillformCTSP(SanPham duocchon, boolean ban){
        txt_mactsp.setText(String.valueOf(duocchon.getMaSP()));
        txt_tenctsp.setText(String.valueOf(duocchon.getTenSP()));
        txt_idctSP.setText(String.valueOf(duocchon.getId()));
    }
    private void fillCTSP(){
        try {
            List<SanPham> ct = ctspRepo.getCTSP(dfkw);
            modelCTSPban.setRowCount(0);
            for(SanPham ctsp: ct){
                modelCTSPban.addRow(new Object[]{
                    
                    ctsp.getMaSP(),
                    ctsp.getTenSP(),
                    ctsp.getId()

                });
            }
        } catch (Exception e) {
        }
    }
    
    private void fillThuocTinh(){
        cbo_ketcau.removeAllItems();
        cbo_mausac.removeAllItems();
        cbo_nhomhuong.removeAllItems();
        cbo_hieuung.removeAllItems();
        cbo_dungtich.removeAllItems();
        
        cbo_ketcautimkiem.removeAllItems();
        cbo_mausactimkiem.removeAllItems();
        cbo_nhomhuongtimkiem.removeAllItems();
        cbo_hieuungtimkiem.removeAllItems();
        cbo_dungtichtimkiem.removeAllItems();
        
        List<KetCau> listKC = kcRP.getKetCau();
        
        for(KetCau kc: listKC){
            cbo_ketcau.addItem(kc.getTenketcau());
            cbo_ketcautimkiem.addItem(kc.getTenketcau());
        }
        bangKetCau();
        
        for(MauSac ms: msRP.getMauSac()){
            cbo_mausac.addItem(ms.getTenMauSac());
            cbo_mausactimkiem.addItem(ms.getTenMauSac());
        }
        bangMau();
        
        for(NhomHuong nh: nhRP.getNhomHuong()){
            cbo_nhomhuong.addItem(nh.getTenNhomHuong());
            cbo_nhomhuongtimkiem.addItem(nh.getTenNhomHuong());
        }
        bangNhomHuong();
        
        for(HieuUng hu: huRP.getHieuUng()){
            cbo_hieuung.addItem(hu.getTenhieuung());
            cbo_hieuungtimkiem.addItem(hu.getTenhieuung());
        }     
        bangHieuUng();
        
        for(DungTich dt: dtRP.getDungTich()){
            cbo_dungtich.addItem(dt.getDungtich());
            cbo_dungtichtimkiem.addItem(dt.getDungtich());
        }   
        bangDungTich();
    }
    private void MaSP(){
        modelSPban.setRowCount(0);
        for (SanPham object : spRP.getAll()) {
            modelSPban.addRow(new Object[]{object.getId(), object.getMaSP()});
        }
    }
    
    private void bangMau() {
        modelMau.setRowCount(0);
        for (MauSac object : msRP.getMauSac()) {
            modelMau.addRow(new Object[]{object.getId(), object.getTenMauSac()});
        }
    }

    private void bangKetCau() {
        modelKeuCau.setRowCount(0);
        for (KetCau object : kcRP.getKetCau()) {
            modelKeuCau.addRow(new Object[]{object.getId(), object.getTenketcau()});
        }
    }
    
    private void bangNhomHuong() {
        modelNhomHuong.setRowCount(0);
        for (NhomHuong object : nhRP.getNhomHuong()) {
            modelNhomHuong.addRow(new Object[]{object.getId(), object.getTenNhomHuong()});
        }
    }
    
    private void bangHieuUng() {
        modelHieuUng.setRowCount(0);
        for (HieuUng object : huRP.getHieuUng()) {
            modelHieuUng.addRow(new Object[]{object.getId(), object.getTenhieuung()});
        }
    }
    
    private void bangDungTich() {
        modelDungTich.setRowCount(0);
        for (DungTich object : dtRP.getDungTich()) {
            modelDungTich.addRow(new Object[]{object.getId(), object.getDungtich()});
        }
    }
    private void clearMau() {
        txtmauID.setText("_ _");
        txt_ttMS.setText("");
    }

    private void clearKC() {
        txtkxID.setText("_ _");
        txt_ttKC.setText("");
    }
    
    private void clearNH() {
        txtnhID.setText("_ _");
        txt_ttNH.setText("");
    }
    
    private void clearHieu() {
        txthuID.setText("_ _");
        txt_ttHU.setText("");
    }
    
    private void clearDungTich() {
        txtdtID.setText("_ _");
        txt_ttDT.setText("");
    }
    
    private String getThuocTinh(int i) {
        try {
            String ten="";
        
            switch (i) {
                case 1 -> ten=txt_ttMS.getText().trim();
                case 2 -> ten=txt_ttKC.getText().trim();
                case 3 -> ten=txt_ttNH.getText().trim();
                case 4 -> ten=txt_ttHU.getText().trim();
                case 5 -> ten=txt_ttDT.getText().trim();
                default -> {
                }
            }

            if(ten.equalsIgnoreCase("")){
                switch (i) {
                    case 1 -> JOptionPane.showMessageDialog(jpnthuoctinh, "Nhập tên màu");
                    case 2 -> JOptionPane.showMessageDialog(jpnthuoctinh, "Nhập kết cấu");
                    case 3 -> JOptionPane.showMessageDialog(jpnthuoctinh, "Nhập nhóm hương");
                    case 4 -> JOptionPane.showMessageDialog(jpnthuoctinh, "Nhập hiệu ứng");
                    case 5 -> JOptionPane.showMessageDialog(jpnthuoctinh, "Nhập dung tích");
                    default -> {
                    }
                }
                return null;
            }
            else{
                return ten;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    private ChiTietSP getctsp(int i){
        String mactsp = txt_mactsp.getText().trim();
        String tenctsp = txt_tenctsp.getText().trim();
         try {
            if(mactsp.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(jpbban, "Nhập mã");
                return null;
            }else if(tenctsp.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(jpbban, "Nhập tên");
                return null;
            }else {
                boolean checktontai = false;
                
                for(SanPham kiemtra: ctspRepo.getCTSP("")){
                    if(mactsp.equalsIgnoreCase(kiemtra.getMaSP())){
                        checktontai = true;
                        break;
                    }
                }
                
                
                if(checktontai==true && i==1){
                    JOptionPane.showMessageDialog(jpbban, "Mã này đã tồn tại");
                    return null;
                }
                else{
                    ChiTietSP ctsp = new ChiTietSP();                                   
                    
                    
                    ctsp.setMa(mactsp);                                   
                    ctsp.setTen(tenctsp);
                    
                    return ctsp;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
    private SanPham getSP(int kieu){
        String maSP = txt_masp.getText().trim();
        String tenSP = txt_tensp.getText().trim();
        
        double giaban;
        int soluong;
        
        int mausac = msRP.getMauSac().get(cbo_mausac.getSelectedIndex()).getId();
        int ketcau = kcRP.getKetCau().get(cbo_ketcau.getSelectedIndex()).getId();
        int nhomhuong = nhRP.getNhomHuong().get(cbo_nhomhuong.getSelectedIndex()).getId();
        int hieuung = huRP.getHieuUng().get(cbo_hieuung.getSelectedIndex()).getId();
        int dungtich = dtRP.getDungTich().get(cbo_dungtich.getSelectedIndex()).getId();
    
        try {
            giaban = Double.parseDouble(txt_giabansp.getText());
            soluong = Integer.parseInt(txt_soluongsp.getText());
            
            if(maSP.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(jpbban, "Nhập mã");
                return null;
            }
            else if(tenSP.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(jpbban, "Nhập tên");
                return null;
            }
            
            else if(giaban<=0){
                JOptionPane.showMessageDialog(jpbban, "Giá bán phải lớn hơn 0");
                return null;
            }
            else if(soluong<0){
                JOptionPane.showMessageDialog(jpbban, "Số lượng phải là một số dương");
                return null;
            }
            else{
                
                boolean checktontai = false;
                
                for(SanPham kiemtra: spRP.getBan("")){
                    if(maSP.equalsIgnoreCase(kiemtra.getMaSP())){
                        checktontai = true;
                        break;
                    }
                }
                
                
                if(checktontai==true && kieu==1){
                    JOptionPane.showMessageDialog(jpbban, "Mã này đã tồn tại");
                    return null;
                }
                else{
                    SanPham sp = new SanPham();                                   
                    
                    sp.setIdKetCau(ketcau);
                    sp.setIdMauSac(mausac);
                    sp.setIdNhomHuong(nhomhuong);
                    sp.setIdHieuUng(hieuung);
                    sp.setIdDungTich(dungtich);
                    
                    sp.setMaSP(maSP);                                   
                    sp.setTenSP(tenSP);
                    sp.setGiaTien(giaban);
                    sp.setSoLuong(soluong);
                    sp.setTrangThai(1);
                    
                    return sp;
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(jpbban, "Giá bán và số lượng phải là một chữ số");
            return null;
        }
    }
    
    private void clearForm() {
        txt_giabansp.setText("");
        txtsanphamID.setText("_ _");
        txt_masp.setText("");
        txt_soluongsp.setText("");
        txt_tensp.setText("");
        
        cbo_masp.setSelectedIndex(0);
        cbo_ketcau.setSelectedIndex(0);
        cbo_nhomhuong.setSelectedIndex(0);
        cbo_mausac.setSelectedIndex(0);
        cbo_hieuung.setSelectedIndex(0);
        cbo_dungtich.setSelectedIndex(0);
    }
    
    private void clearFormCTSP(){
        txt_idctSP.setText("_ _");
        txt_mactsp.setText("");
        txt_tenctsp.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnsanpham = new javax.swing.JTabbedPane();
        jpn_sp = new javax.swing.JPanel();
        jpbban = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_sanpham = new javax.swing.JTable();
        lbl_sanpham = new javax.swing.JLabel();
        lbl_tk = new javax.swing.JLabel();
        txt_sptimkiem = new javax.swing.JTextField();
        btn_timkiem = new javax.swing.JButton();
        btn_datlai = new javax.swing.JButton();
        cbo_ketcautimkiem = new javax.swing.JComboBox<>();
        cbo_mausactimkiem = new javax.swing.JComboBox<>();
        cbo_nhomhuongtimkiem = new javax.swing.JComboBox<>();
        cbo_hieuungtimkiem = new javax.swing.JComboBox<>();
        cbo_dungtichtimkiem = new javax.swing.JComboBox<>();
        lbl_ketcautimkiem = new javax.swing.JLabel();
        lbl_mausactimkiem = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbl_dungtichtimkiem = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbl_maSp = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btn_themsp = new javax.swing.JButton();
        btn_suasp = new javax.swing.JButton();
        btn_xoasp = new javax.swing.JButton();
        btn_datlaisp = new javax.swing.JButton();
        btn_themexcel = new javax.swing.JButton();
        txt_masp = new javax.swing.JTextField();
        txt_tensp = new javax.swing.JTextField();
        txt_giabansp = new javax.swing.JTextField();
        txt_soluongsp = new javax.swing.JTextField();
        cbo_hieuung = new javax.swing.JComboBox<>();
        cbo_ketcau = new javax.swing.JComboBox<>();
        cbo_nhomhuong = new javax.swing.JComboBox<>();
        cbo_mausac = new javax.swing.JComboBox<>();
        cbo_dungtich = new javax.swing.JComboBox<>();
        txtsanphamID = new javax.swing.JTextField();
        btn_themthuoctinh = new javax.swing.JButton();
        cbo_masp = new javax.swing.JComboBox<>();
        btn_themnhanhhieuung = new javax.swing.JButton();
        btn_themnhanhketcau = new javax.swing.JButton();
        btn_themnhanhnhomhuong = new javax.swing.JButton();
        btn_themnhanhmausac = new javax.swing.JButton();
        btn_themnhanhdungtich = new javax.swing.JButton();
        jpnthuoctinh = new javax.swing.JPanel();
        jpnmausac = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_ttmausac = new javax.swing.JTable();
        lbl_mausac = new javax.swing.JLabel();
        btn_ttThemMS = new javax.swing.JButton();
        btn_ttSuaMS = new javax.swing.JButton();
        btn_ttXoaMS = new javax.swing.JButton();
        btn_ttClearMS = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_ttMS = new javax.swing.JTextField();
        txtmauID = new javax.swing.JTextField();
        jpnhieuung = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_tthieuung = new javax.swing.JTable();
        lbl_hieuung = new javax.swing.JLabel();
        btnthemHU = new javax.swing.JButton();
        btnsuaHU = new javax.swing.JButton();
        btnxoaHU = new javax.swing.JButton();
        btn_ttClearHU = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_ttHU = new javax.swing.JTextField();
        txthuID = new javax.swing.JTextField();
        jpnketcau = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_ttketcau = new javax.swing.JTable();
        lbl_ketcau = new javax.swing.JLabel();
        btn_ttThemKC = new javax.swing.JButton();
        btn_ttSuaKC = new javax.swing.JButton();
        btn_ttXoaKC = new javax.swing.JButton();
        btn_ttClearKC = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_ttKC = new javax.swing.JTextField();
        txtkxID = new javax.swing.JTextField();
        jpnnhomhuong = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_ttnhomhuong = new javax.swing.JTable();
        lbl_nhomhuong = new javax.swing.JLabel();
        btn_ttThemNH = new javax.swing.JButton();
        btn_ttSuaNH = new javax.swing.JButton();
        btn_ttXoaNH = new javax.swing.JButton();
        btn_ttClearNH = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_ttNH = new javax.swing.JTextField();
        txtnhID = new javax.swing.JTextField();
        jpndungtich = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_ttdungtich = new javax.swing.JTable();
        lbl_dungtich = new javax.swing.JLabel();
        btn_ttThemDT = new javax.swing.JButton();
        btn_ttSuaDT = new javax.swing.JButton();
        btn_ttXoaDT = new javax.swing.JButton();
        btn_ttClearDT = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txt_ttDT = new javax.swing.JTextField();
        txtdtID = new javax.swing.JTextField();
        jpnctsp = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_ctsp = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_mactsp = new javax.swing.JTextField();
        txt_tenctsp = new javax.swing.JTextField();
        btn_themctsp = new javax.swing.JButton();
        btn_suactsp = new javax.swing.JButton();
        btn_xoactsp = new javax.swing.JButton();
        btn_clearctsp = new javax.swing.JButton();
        txt_tkctsp = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btn_tkctsp = new javax.swing.JButton();
        lblidctsp = new javax.swing.JLabel();
        txt_idctSP = new javax.swing.JTextField();

        tbl_sanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Giá bán", "Số lượng", "Kết cấu", "Màu sắc", "Nhóm hương", "Hiệu ứng", "Dung tích"
            }
        ));
        tbl_sanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sanphamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_sanpham);

        lbl_sanpham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_sanpham.setText("Sản phẩm");

        lbl_tk.setText("Từ khóa tìm kiếm");

        btn_timkiem.setText("Tìm kiếm");
        btn_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemActionPerformed(evt);
            }
        });

        btn_datlai.setText("Đặt lại");
        btn_datlai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_datlaiActionPerformed(evt);
            }
        });

        cbo_ketcautimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_ketcautimkiemActionPerformed(evt);
            }
        });

        cbo_mausactimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_mausactimkiemActionPerformed(evt);
            }
        });

        cbo_nhomhuongtimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_nhomhuongtimkiemActionPerformed(evt);
            }
        });

        cbo_hieuungtimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_hieuungtimkiemActionPerformed(evt);
            }
        });

        cbo_dungtichtimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_dungtichtimkiemActionPerformed(evt);
            }
        });

        lbl_ketcautimkiem.setText("Kết cấu:");

        lbl_mausactimkiem.setText("Màu sắc:");

        jLabel25.setText("Nhóm hương:");

        jLabel26.setText("Hiệu ứng:");

        lbl_dungtichtimkiem.setText("Dung tích:");

        javax.swing.GroupLayout jpbbanLayout = new javax.swing.GroupLayout(jpbban);
        jpbban.setLayout(jpbbanLayout);
        jpbbanLayout.setHorizontalGroup(
            jpbbanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jpbbanLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jpbbanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpbbanLayout.createSequentialGroup()
                        .addGap(257, 257, 257)
                        .addComponent(lbl_sanpham)
                        .addContainerGap())
                    .addGroup(jpbbanLayout.createSequentialGroup()
                        .addGroup(jpbbanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_tk)
                            .addGroup(jpbbanLayout.createSequentialGroup()
                                .addComponent(lbl_ketcautimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbo_ketcautimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpbbanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpbbanLayout.createSequentialGroup()
                                .addComponent(txt_sptimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_timkiem)
                                .addGap(33, 33, 33)
                                .addComponent(btn_datlai)
                                .addGap(71, 71, 71))
                            .addGroup(jpbbanLayout.createSequentialGroup()
                                .addComponent(lbl_mausactimkiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbo_mausactimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbo_nhomhuongtimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbo_hieuungtimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(lbl_dungtichtimkiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbo_dungtichtimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jpbbanLayout.setVerticalGroup(
            jpbbanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpbbanLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(lbl_sanpham)
                .addGap(31, 31, 31)
                .addGroup(jpbbanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tk)
                    .addComponent(txt_sptimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timkiem)
                    .addComponent(btn_datlai))
                .addGap(41, 41, 41)
                .addGroup(jpbbanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_ketcautimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_mausactimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_nhomhuongtimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_hieuungtimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_dungtichtimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ketcautimkiem)
                    .addComponent(lbl_mausactimkiem)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(lbl_dungtichtimkiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setText("ID: ");

        lbl_maSp.setText("Mã SP:");

        jLabel5.setText("Tên SP: ");

        jLabel6.setText("Giá bán: ");

        jLabel7.setText("Số lượng: ");

        jLabel8.setText("Hiệu ứng: ");

        jLabel9.setText("Kết cấu: ");

        jLabel10.setText("Nhóm hương: ");

        jLabel11.setText("Màu sắc: ");

        jLabel12.setText("Dung tích: ");

        btn_themsp.setText("Thêm");
        btn_themsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themspActionPerformed(evt);
            }
        });

        btn_suasp.setText("Sửa");
        btn_suasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaspActionPerformed(evt);
            }
        });

        btn_xoasp.setText("Xóa");
        btn_xoasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaspActionPerformed(evt);
            }
        });

        btn_datlaisp.setText("Làm mới");
        btn_datlaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_datlaispActionPerformed(evt);
            }
        });

        btn_themexcel.setText("Thêm từ excel");
        btn_themexcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themexcelActionPerformed(evt);
            }
        });

        txt_masp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maspActionPerformed(evt);
            }
        });

        txtsanphamID.setText("_ _");

        btn_themthuoctinh.setText("Thêm chi tiết");
        btn_themthuoctinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themthuoctinhActionPerformed(evt);
            }
        });

        btn_themnhanhhieuung.setText("+");
        btn_themnhanhhieuung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themnhanhhieuungActionPerformed(evt);
            }
        });

        btn_themnhanhketcau.setText("+");
        btn_themnhanhketcau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themnhanhketcauActionPerformed(evt);
            }
        });

        btn_themnhanhnhomhuong.setText("+");
        btn_themnhanhnhomhuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themnhanhnhomhuongActionPerformed(evt);
            }
        });

        btn_themnhanhmausac.setText("+");
        btn_themnhanhmausac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themnhanhmausacActionPerformed(evt);
            }
        });

        btn_themnhanhdungtich.setText("+");
        btn_themnhanhdungtich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themnhanhdungtichActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbo_nhomhuong, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(btn_datlaisp)
                                .addGap(8, 8, 8)
                                .addComponent(btn_themexcel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3)
                                        .addComponent(lbl_maSp, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_masp, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                        .addComponent(txt_tensp)
                                        .addComponent(txt_giabansp)
                                        .addComponent(txt_soluongsp)
                                        .addComponent(cbo_ketcau, 0, 138, Short.MAX_VALUE)
                                        .addComponent(cbo_hieuung, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(cbo_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17))
                                    .addComponent(cbo_dungtich, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtsanphamID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_masp, 0, 93, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_themnhanhnhomhuong)
                                    .addComponent(btn_themnhanhmausac)
                                    .addComponent(btn_themnhanhdungtich)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(btn_themnhanhketcau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_themnhanhhieuung, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 52, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_themsp, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_suasp, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_xoasp)
                        .addGap(18, 18, 18)
                        .addComponent(btn_themthuoctinh)))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtsanphamID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_maSp)
                    .addComponent(txt_masp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_masp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_tensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_giabansp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_soluongsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbo_hieuung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themnhanhhieuung))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbo_ketcau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themnhanhketcau))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbo_nhomhuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themnhanhnhomhuong))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbo_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themnhanhmausac))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbo_dungtich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themnhanhdungtich))
                .addGap(73, 73, 73)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_themthuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_themsp)
                        .addComponent(btn_suasp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_xoasp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_themexcel, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(btn_datlaisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(136, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpn_spLayout = new javax.swing.GroupLayout(jpn_sp);
        jpn_sp.setLayout(jpn_spLayout);
        jpn_spLayout.setHorizontalGroup(
            jpn_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_spLayout.createSequentialGroup()
                .addComponent(jpbban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpn_spLayout.setVerticalGroup(
            jpn_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpbban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpnsanpham.addTab("Chi tiết sản phẩm", jpn_sp);

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

        btn_ttSuaMS.setText("Sửa");
        btn_ttSuaMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttSuaMSActionPerformed(evt);
            }
        });

        btn_ttXoaMS.setText("Xóa");
        btn_ttXoaMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttXoaMSActionPerformed(evt);
            }
        });

        btn_ttClearMS.setText("Làm mới");
        btn_ttClearMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttClearMSActionPerformed(evt);
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

        javax.swing.GroupLayout jpnmausacLayout = new javax.swing.GroupLayout(jpnmausac);
        jpnmausac.setLayout(jpnmausacLayout);
        jpnmausacLayout.setHorizontalGroup(
            jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnmausacLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jpnmausacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnmausacLayout.createSequentialGroup()
                        .addComponent(btn_ttThemMS)
                        .addGap(24, 24, 24))
                    .addComponent(btn_ttSuaMS)
                    .addComponent(btn_ttXoaMS)
                    .addComponent(btn_ttClearMS)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttSuaMS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttXoaMS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttClearMS)))
                .addContainerGap())
        );

        jpnhieuung.setBackground(new java.awt.Color(204, 204, 204));

        tbl_tthieuung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Hiệu ứng"
            }
        ));
        tbl_tthieuung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_tthieuungMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_tthieuung);

        lbl_hieuung.setText("Hiệu ứng");

        btnthemHU.setText("Thêm");
        btnthemHU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemHUActionPerformed(evt);
            }
        });

        btnsuaHU.setText("Sửa");
        btnsuaHU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaHUActionPerformed(evt);
            }
        });

        btnxoaHU.setText("Xóa");
        btnxoaHU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaHUActionPerformed(evt);
            }
        });

        btn_ttClearHU.setText("Làm mới");
        btn_ttClearHU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttClearHUActionPerformed(evt);
            }
        });

        jLabel20.setText("ID: ");

        jLabel21.setText("Hiệu ứng: ");

        txthuID.setText("_ _");

        javax.swing.GroupLayout jpnhieuungLayout = new javax.swing.GroupLayout(jpnhieuung);
        jpnhieuung.setLayout(jpnhieuungLayout);
        jpnhieuungLayout.setHorizontalGroup(
            jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnhieuungLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnsuaHU)
                    .addComponent(btnthemHU)
                    .addComponent(btnxoaHU)
                    .addComponent(btn_ttClearHU))
                .addGap(0, 13, Short.MAX_VALUE))
            .addGroup(jpnhieuungLayout.createSequentialGroup()
                .addGroup(jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnhieuungLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(lbl_hieuung))
                    .addGroup(jpnhieuungLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ttHU, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txthuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnhieuungLayout.setVerticalGroup(
            jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnhieuungLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_hieuung, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txthuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txt_ttHU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jpnhieuungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnhieuungLayout.createSequentialGroup()
                        .addComponent(btnthemHU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnsuaHU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnxoaHU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttClearHU)))
                .addContainerGap())
        );

        jpnketcau.setBackground(new java.awt.Color(204, 204, 204));

        tbl_ttketcau.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Kết cấu"
            }
        ));
        tbl_ttketcau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ttketcauMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_ttketcau);

        lbl_ketcau.setText("Kết cấu");

        btn_ttThemKC.setText("Thêm");
        btn_ttThemKC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttThemKCActionPerformed(evt);
            }
        });

        btn_ttSuaKC.setText("Sửa");
        btn_ttSuaKC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttSuaKCActionPerformed(evt);
            }
        });

        btn_ttXoaKC.setText("Xóa");
        btn_ttXoaKC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttXoaKCActionPerformed(evt);
            }
        });

        btn_ttClearKC.setText("Làm mới");
        btn_ttClearKC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttClearKCActionPerformed(evt);
            }
        });

        jLabel14.setText("ID: ");

        jLabel15.setText("Kết cấu: ");

        txtkxID.setBackground(new java.awt.Color(204, 204, 204));
        txtkxID.setText("_ _");

        javax.swing.GroupLayout jpnketcauLayout = new javax.swing.GroupLayout(jpnketcau);
        jpnketcau.setLayout(jpnketcauLayout);
        jpnketcauLayout.setHorizontalGroup(
            jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnketcauLayout.createSequentialGroup()
                .addGroup(jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnketcauLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_ttThemKC)
                            .addComponent(btn_ttSuaKC)
                            .addComponent(btn_ttXoaKC)
                            .addComponent(btn_ttClearKC)))
                    .addGroup(jpnketcauLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnketcauLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnketcauLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(28, 28, 28)))
                        .addGroup(jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ttKC, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtkxID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnketcauLayout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(lbl_ketcau)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jpnketcauLayout.setVerticalGroup(
            jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnketcauLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_ketcau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtkxID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_ttKC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnketcauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnketcauLayout.createSequentialGroup()
                        .addComponent(btn_ttThemKC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttSuaKC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttXoaKC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttClearKC)))
                .addContainerGap())
        );

        jpnnhomhuong.setBackground(new java.awt.Color(204, 204, 204));

        tbl_ttnhomhuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nhóm hương"
            }
        ));
        tbl_ttnhomhuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ttnhomhuongMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_ttnhomhuong);

        lbl_nhomhuong.setText("Nhóm hương");

        btn_ttThemNH.setText("Thêm");
        btn_ttThemNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttThemNHActionPerformed(evt);
            }
        });

        btn_ttSuaNH.setText("Sửa");
        btn_ttSuaNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttSuaNHActionPerformed(evt);
            }
        });

        btn_ttXoaNH.setText("Xóa");
        btn_ttXoaNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttXoaNHActionPerformed(evt);
            }
        });

        btn_ttClearNH.setText("Làm mới");
        btn_ttClearNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttClearNHActionPerformed(evt);
            }
        });

        jLabel17.setText("ID: ");

        jLabel18.setText("Nhóm hương: ");

        txtnhID.setText("_ _");

        javax.swing.GroupLayout jpnnhomhuongLayout = new javax.swing.GroupLayout(jpnnhomhuong);
        jpnnhomhuong.setLayout(jpnnhomhuongLayout);
        jpnnhomhuongLayout.setHorizontalGroup(
            jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnnhomhuongLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_nhomhuong)
                .addGap(146, 146, 146))
            .addGroup(jpnnhomhuongLayout.createSequentialGroup()
                .addGroup(jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnnhomhuongLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_ttThemNH)
                            .addComponent(btn_ttSuaNH)
                            .addComponent(btn_ttXoaNH)
                            .addComponent(btn_ttClearNH)))
                    .addGroup(jpnnhomhuongLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addGap(8, 8, 8)
                        .addGroup(jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ttNH, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnhID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jpnnhomhuongLayout.setVerticalGroup(
            jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnnhomhuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_nhomhuong, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtnhID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txt_ttNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jpnnhomhuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnnhomhuongLayout.createSequentialGroup()
                        .addComponent(btn_ttThemNH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttSuaNH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttXoaNH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttClearNH)))
                .addContainerGap())
        );

        jpndungtich.setBackground(new java.awt.Color(204, 204, 204));

        tbl_ttdungtich.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Dung tích"
            }
        ));
        tbl_ttdungtich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ttdungtichMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_ttdungtich);

        lbl_dungtich.setText("Dung tích");

        btn_ttThemDT.setText("Thêm");
        btn_ttThemDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttThemDTActionPerformed(evt);
            }
        });

        btn_ttSuaDT.setText("Sửa");
        btn_ttSuaDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttSuaDTActionPerformed(evt);
            }
        });

        btn_ttXoaDT.setText("Xóa");
        btn_ttXoaDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttXoaDTActionPerformed(evt);
            }
        });

        btn_ttClearDT.setText("Làm mới");
        btn_ttClearDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttClearDTActionPerformed(evt);
            }
        });

        jLabel23.setText("ID: ");

        jLabel24.setText("Dung tích: ");

        txtdtID.setText("_ _");

        javax.swing.GroupLayout jpndungtichLayout = new javax.swing.GroupLayout(jpndungtich);
        jpndungtich.setLayout(jpndungtichLayout);
        jpndungtichLayout.setHorizontalGroup(
            jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpndungtichLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ttThemDT)
                    .addComponent(btn_ttSuaDT)
                    .addComponent(btn_ttXoaDT)
                    .addComponent(btn_ttClearDT))
                .addGap(22, 22, 22))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpndungtichLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dungtich)
                .addGap(167, 167, 167))
            .addGroup(jpndungtichLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpndungtichLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpndungtichLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(23, 23, 23)))
                .addGroup(jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_ttDT, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpndungtichLayout.setVerticalGroup(
            jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpndungtichLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_dungtich, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtdtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txt_ttDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpndungtichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpndungtichLayout.createSequentialGroup()
                        .addComponent(btn_ttThemDT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttSuaDT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttXoaDT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ttClearDT)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jpnthuoctinhLayout = new javax.swing.GroupLayout(jpnthuoctinh);
        jpnthuoctinh.setLayout(jpnthuoctinhLayout);
        jpnthuoctinhLayout.setHorizontalGroup(
            jpnthuoctinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnthuoctinhLayout.createSequentialGroup()
                .addGroup(jpnthuoctinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jpnhieuung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnmausac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnthuoctinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnthuoctinhLayout.createSequentialGroup()
                        .addComponent(jpndungtich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(391, Short.MAX_VALUE))
                    .addGroup(jpnthuoctinhLayout.createSequentialGroup()
                        .addComponent(jpnketcau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnnhomhuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jpnthuoctinhLayout.setVerticalGroup(
            jpnthuoctinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnthuoctinhLayout.createSequentialGroup()
                .addGroup(jpnthuoctinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jpnnhomhuong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnketcau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnmausac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnthuoctinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnhieuung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpndungtich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnsanpham.addTab("Thuộc tính", jpnthuoctinh);

        tbl_ctsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm"
            }
        ));
        tbl_ctsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ctspMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_ctsp);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Mã sản phẩm");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Tên sản phẩm");

        txt_mactsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_mactspActionPerformed(evt);
            }
        });

        txt_tenctsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenctspActionPerformed(evt);
            }
        });

        btn_themctsp.setText("Thêm");
        btn_themctsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themctspActionPerformed(evt);
            }
        });

        btn_suactsp.setText("Sửa");
        btn_suactsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suactspActionPerformed(evt);
            }
        });

        btn_xoactsp.setText("Xóa");
        btn_xoactsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoactspActionPerformed(evt);
            }
        });

        btn_clearctsp.setText("Làm mới");
        btn_clearctsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearctspActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("Từ khóa tìm kiếm");

        btn_tkctsp.setText("Tìm kiếm");
        btn_tkctsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tkctspActionPerformed(evt);
            }
        });

        lblidctsp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblidctsp.setText("ID");

        txt_idctSP.setText("_ _");
        txt_idctSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idctSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnctspLayout = new javax.swing.GroupLayout(jpnctsp);
        jpnctsp.setLayout(jpnctspLayout);
        jpnctspLayout.setHorizontalGroup(
            jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnctspLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addContainerGap())
            .addGroup(jpnctspLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblidctsp)
                    .addComponent(jLabel19)
                    .addGroup(jpnctspLayout.createSequentialGroup()
                        .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_tkctsp, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_mactsp, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tenctsp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_idctSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnctspLayout.createSequentialGroup()
                                .addGap(236, 236, 236)
                                .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_xoactsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_themctsp, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                                .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnctspLayout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(btn_suactsp, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnctspLayout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addComponent(btn_clearctsp, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jpnctspLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(btn_tkctsp, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(406, Short.MAX_VALUE))
        );
        jpnctspLayout.setVerticalGroup(
            jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnctspLayout.createSequentialGroup()
                .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnctspLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_themctsp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_suactsp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_xoactsp, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_clearctsp, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnctspLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblidctsp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_idctSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_mactsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tenctsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tkctsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tkctsp))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jpnsanpham.addTab("Sản phẩm", jpnctsp);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnsanpham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_sanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanphamMouseClicked
        // TODO add your handling code here:
        try {
            int vitridong = tbl_sanpham.getSelectedRow();
            SanPham duocchon = spRP.getBan(dfkw).get(vitridong);

            fillForm(duocchon, true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tbl_sanphamMouseClicked

    private void btn_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiemActionPerformed
        // TODO add your handling code here:
        dfkw = txt_sptimkiem.getText();
        fillBan();
    }//GEN-LAST:event_btn_timkiemActionPerformed

    private void btn_datlaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_datlaiActionPerformed
        // TODO add your handling code here:
        dfkw = "";
        txt_sptimkiem.setText("");
        fillBan();
    }//GEN-LAST:event_btn_datlaiActionPerformed

    private void cbo_ketcautimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_ketcautimkiemActionPerformed
        // TODO add your handling code here:
        listSPVM = spRP.searchTT(cbo_ketcautimkiem.getSelectedItem().toString());
        loadSanPham(listSPVM);
    }//GEN-LAST:event_cbo_ketcautimkiemActionPerformed

    private void cbo_mausactimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_mausactimkiemActionPerformed
        // TODO add your handling code here:
        listSPVM = spRP.searchTT(cbo_mausactimkiem.getSelectedItem().toString());
        loadSanPham(listSPVM);
    }//GEN-LAST:event_cbo_mausactimkiemActionPerformed

    private void cbo_nhomhuongtimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_nhomhuongtimkiemActionPerformed
        // TODO add your handling code here:
        listSPVM = spRP.searchTT(cbo_nhomhuongtimkiem.getSelectedItem().toString());
        loadSanPham(listSPVM);
    }//GEN-LAST:event_cbo_nhomhuongtimkiemActionPerformed

    private void cbo_hieuungtimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_hieuungtimkiemActionPerformed
        // TODO add your handling code here:
        listSPVM = spRP.searchTT(cbo_hieuungtimkiem.getSelectedItem().toString());
        loadSanPham(listSPVM);
    }//GEN-LAST:event_cbo_hieuungtimkiemActionPerformed

    private void cbo_dungtichtimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_dungtichtimkiemActionPerformed
        // TODO add your handling code here:
        listSPVM = spRP.searchTT(cbo_dungtichtimkiem.getSelectedItem().toString());
        loadSanPham(listSPVM);
    }//GEN-LAST:event_cbo_dungtichtimkiemActionPerformed

    private void btn_themspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themspActionPerformed
        // TODO add your handling code here:
        SanPham spmoi = getSP(1);
        if(spmoi!=null){
            try {
                spRP.luu(spmoi);
            } catch (SQLException ex) {
                //Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            dfkw = "";
            fillBan();
            fillThuocTinh();
            clearForm();
        }

    }//GEN-LAST:event_btn_themspActionPerformed

    private void btn_suaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaspActionPerformed
        // TODO add your handling code here:
        SanPham spSua = getSP(2);
        if(spSua!=null){
            try {
                spSua.setId(Integer.parseInt(txtsanphamID.getText()));
                spRP.sua(spSua);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(jpbban, "Mã này đã tồn tại");
            }
            dfkw="";
            fillBan();
            fillThuocTinh();
            clearForm();
        }
    }//GEN-LAST:event_btn_suaspActionPerformed

    private void btn_xoaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaspActionPerformed
        // TODO add your handling code here:
        try {
            int muonxoa = Integer.parseInt(txtsanphamID.getText());
            int quyetDinh = JOptionPane.showConfirmDialog(jpbban, "Bạn có muốn xóa không", "", JOptionPane.YES_NO_OPTION);
            if(quyetDinh==JOptionPane.YES_OPTION){
                try {
                    spRP.xoa(muonxoa);
                } catch (SQLException ex) {
                    Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (Exception e) {
        }

        dfkw = "";
        fillBan();
        fillThuocTinh();
        clearForm();

    }//GEN-LAST:event_btn_xoaspActionPerformed

    private void btn_datlaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_datlaispActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btn_datlaispActionPerformed

    private void btn_themexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themexcelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_themexcelActionPerformed

    private void txt_maspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maspActionPerformed

    private void btn_themthuoctinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themthuoctinhActionPerformed
        // TODO add your handling code here:
        SanPham spSua = getSP(2);
        if(spSua!=null){
            try {
                spSua.setId(Integer.parseInt(txtsanphamID.getText()));
                spRP.sua(spSua);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(jpbban, "Mã này đã tồn tại");
            }
            dfkw="";
            fillBan();
            fillThuocTinh();
            clearForm();
        }
    }//GEN-LAST:event_btn_themthuoctinhActionPerformed

    private void btn_themnhanhhieuungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themnhanhhieuungActionPerformed
        // TODO add your handling code here:
        Main m = new Main();
        m.openHUNhanh();
        fillBan();
        fillThuocTinh();
    }//GEN-LAST:event_btn_themnhanhhieuungActionPerformed

    private void btn_themnhanhketcauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themnhanhketcauActionPerformed
        // TODO add your handling code here:
        Main m = new Main();
        m.openKCNhanh();
        fillBan();
        fillThuocTinh();
    }//GEN-LAST:event_btn_themnhanhketcauActionPerformed

    private void btn_themnhanhnhomhuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themnhanhnhomhuongActionPerformed
        // TODO add your handling code here:
        Main m = new Main();
        m.openNHNhanh();
        fillBan();
        fillThuocTinh();
    }//GEN-LAST:event_btn_themnhanhnhomhuongActionPerformed

    private void btn_themnhanhmausacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themnhanhmausacActionPerformed
        // TODO add your handling code here:
        Main m = new Main();
        m.openMSNhanh();
        fillBan();
        fillThuocTinh();
    }//GEN-LAST:event_btn_themnhanhmausacActionPerformed

    private void btn_themnhanhdungtichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themnhanhdungtichActionPerformed
        // TODO add your handling code here:
        Main m = new Main();
        m.openDTNhanh();
        fillBan();
        fillThuocTinh();
    }//GEN-LAST:event_btn_themnhanhdungtichActionPerformed

    private void tbl_ttmausacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ttmausacMouseClicked
        // TODO add your handling code here:
        int vitri = tbl_ttmausac.getSelectedRow();
        txtmauID.setText(String.valueOf(msRP.getMauSac().get(vitri).getId()));
        txt_ttMS.setText(msRP.getMauSac().get(vitri).getTenMauSac());
    }//GEN-LAST:event_tbl_ttmausacMouseClicked

    private void btn_ttThemMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttThemMSActionPerformed
        // TODO add your handling code here:
        String tenMau = getThuocTinh(1);
        if(tenMau!=null){
            try {
                msRP.themMau(tenMau);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearMau();
        }
    }//GEN-LAST:event_btn_ttThemMSActionPerformed

    private void btn_ttSuaMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttSuaMSActionPerformed
        // TODO add your handling code here:
        String tenMau = getThuocTinh(1);
        if(tenMau!=null){
            try {
                int IDmau = Integer.parseInt(txtmauID.getText());
                msRP.suaMau(tenMau, IDmau);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearMau();
        }
    }//GEN-LAST:event_btn_ttSuaMSActionPerformed

    private void btn_ttXoaMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttXoaMSActionPerformed
        // TODO add your handling code here:
        try {
            int IDmau = Integer.parseInt(txtmauID.getText());
            msRP.xoaMau(IDmau);
        } catch (Exception e) {
        }
        fillThuocTinh();
        clearMau();
    }//GEN-LAST:event_btn_ttXoaMSActionPerformed

    private void btn_ttClearMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttClearMSActionPerformed
        // TODO add your handling code here:
        clearMau();
    }//GEN-LAST:event_btn_ttClearMSActionPerformed

    private void txt_ttMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ttMSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ttMSActionPerformed

    private void tbl_tthieuungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_tthieuungMouseClicked
        // TODO add your handling code here:
        int vitri = tbl_tthieuung.getSelectedRow();
        txthuID.setText(String.valueOf(huRP.getHieuUng().get(vitri).getId()));
        txt_ttHU.setText(huRP.getHieuUng().get(vitri).getTenhieuung());
    }//GEN-LAST:event_tbl_tthieuungMouseClicked

    private void btnthemHUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemHUActionPerformed
        // TODO add your handling code here:
        String tenHU = getThuocTinh(4);
        if(tenHU!=null){
            try {
                huRP.themHieuUng(tenHU);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearHieu();
        }
    }//GEN-LAST:event_btnthemHUActionPerformed

    private void btnsuaHUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaHUActionPerformed
        // TODO add your handling code here:
        String tenHU = getThuocTinh(4);
        if(tenHU!=null){
            try {
                int IDmau = Integer.parseInt(txthuID.getText());
                huRP.suaHieuUng(tenHU, IDmau);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearHieu();
        }
    }//GEN-LAST:event_btnsuaHUActionPerformed

    private void btnxoaHUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaHUActionPerformed
        // TODO add your handling code here:
        try {
            int IDmau = Integer.parseInt(txthuID.getText());
            huRP.xoaHieuUng(IDmau);
        } catch (Exception e) {
        }
        fillThuocTinh();
        clearHieu();
    }//GEN-LAST:event_btnxoaHUActionPerformed

    private void btn_ttClearHUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttClearHUActionPerformed
        // TODO add your handling code here:
        clearHieu();
    }//GEN-LAST:event_btn_ttClearHUActionPerformed

    private void tbl_ttketcauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ttketcauMouseClicked
        // TODO add your handling code here:
        int vitri = tbl_ttketcau.getSelectedRow();
        txtkxID.setText(String.valueOf(kcRP.getKetCau().get(vitri).getId()));
        txt_ttKC.setText(kcRP.getKetCau().get(vitri).getTenketcau());
    }//GEN-LAST:event_tbl_ttketcauMouseClicked

    private void btn_ttThemKCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttThemKCActionPerformed
        // TODO add your handling code here:
        String tenKC = getThuocTinh(2);
        if(tenKC!=null){
            try {
                kcRP.themKetCau(tenKC);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearKC();
        }

    }//GEN-LAST:event_btn_ttThemKCActionPerformed

    private void btn_ttSuaKCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttSuaKCActionPerformed
        // TODO add your handling code here:
        String tenKC = getThuocTinh(2);
        if(tenKC!=null){
            try {
                int Idkc = Integer.parseInt(txtkxID.getText());
                kcRP.suaKetCau(tenKC, Idkc);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearKC();
        }
    }//GEN-LAST:event_btn_ttSuaKCActionPerformed

    private void btn_ttXoaKCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttXoaKCActionPerformed
        // TODO add your handling code here:
        try {
            int Idkc = Integer.parseInt(txtkxID.getText());
            kcRP.xoaKetCau(Idkc);
        } catch (Exception e) {
        }
        fillThuocTinh();
        clearKC();
    }//GEN-LAST:event_btn_ttXoaKCActionPerformed

    private void btn_ttClearKCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttClearKCActionPerformed
        // TODO add your handling code here:
        clearKC();
    }//GEN-LAST:event_btn_ttClearKCActionPerformed

    private void tbl_ttnhomhuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ttnhomhuongMouseClicked
        // TODO add your handling code here:
        int vitri = tbl_ttnhomhuong.getSelectedRow();
        txtnhID.setText(String.valueOf(nhRP.getNhomHuong().get(vitri).getId()));
        txt_ttNH.setText(nhRP.getNhomHuong().get(vitri).getTenNhomHuong());
    }//GEN-LAST:event_tbl_ttnhomhuongMouseClicked

    private void btn_ttThemNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttThemNHActionPerformed
        // TODO add your handling code here:
        String tennh = getThuocTinh(3);
        if(tennh!=null){
            try {
                nhRP.themMau(tennh);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearNH();
        }
    }//GEN-LAST:event_btn_ttThemNHActionPerformed

    private void btn_ttSuaNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttSuaNHActionPerformed
        // TODO add your handling code here:
        String tennh = getThuocTinh(3);
        if(tennh!=null){
            try {
                int IDmau = Integer.parseInt(txtnhID.getText());
                nhRP.suaNhomHuong(tennh, IDmau);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearNH();
        }
    }//GEN-LAST:event_btn_ttSuaNHActionPerformed

    private void btn_ttXoaNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttXoaNHActionPerformed
        // TODO add your handling code here:
        try {
            int IDmau = Integer.parseInt(txtnhID.getText());
            nhRP.xoaNhomHuong(IDmau);
        } catch (Exception e) {
        }
        fillThuocTinh();
        clearNH();
    }//GEN-LAST:event_btn_ttXoaNHActionPerformed

    private void btn_ttClearNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttClearNHActionPerformed
        // TODO add your handling code here:
        clearNH();
    }//GEN-LAST:event_btn_ttClearNHActionPerformed

    private void tbl_ttdungtichMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ttdungtichMouseClicked
        // TODO add your handling code here:
        int vitri = tbl_ttdungtich.getSelectedRow();
        txtdtID.setText(String.valueOf(dtRP.getDungTich().get(vitri).getId()));
        txt_ttDT.setText(dtRP.getDungTich().get(vitri).getDungtich());
    }//GEN-LAST:event_tbl_ttdungtichMouseClicked

    private void btn_ttThemDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttThemDTActionPerformed
        // TODO add your handling code here:
        String tenDT = getThuocTinh(5);
        if(tenDT!=null){
            try {
                dtRP.themDungTich(tenDT);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearDungTich();
        }
    }//GEN-LAST:event_btn_ttThemDTActionPerformed

    private void btn_ttSuaDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttSuaDTActionPerformed
        // TODO add your handling code here:
        String tenDT = getThuocTinh(5);
        if(tenDT!=null){
            try {
                int IDmau = Integer.parseInt(txtdtID.getText());
                dtRP.suaDungTich(tenDT, IDmau);
            } catch (Exception e) {
            }
            fillThuocTinh();
            clearDungTich();
        }
    }//GEN-LAST:event_btn_ttSuaDTActionPerformed

    private void btn_ttXoaDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttXoaDTActionPerformed
        // TODO add your handling code here:
        try {
            int IDmau = Integer.parseInt(txtdtID.getText());
            dtRP.xoaDungTich(IDmau);
        } catch (Exception e) {
        }
        fillThuocTinh();
        clearDungTich();
    }//GEN-LAST:event_btn_ttXoaDTActionPerformed

    private void btn_ttClearDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttClearDTActionPerformed
        // TODO add your handling code here:
        clearDungTich();
    }//GEN-LAST:event_btn_ttClearDTActionPerformed

    private void tbl_ctspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ctspMouseClicked
        // TODO add your handling code here:
        try {
            int vitridong = tbl_ctsp.getSelectedRow();
            SanPham duocchon = ctspRepo.getCTSP(dfkw).get(vitridong);

            fillformCTSP(duocchon, true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tbl_ctspMouseClicked

    private void txt_mactspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_mactspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_mactspActionPerformed

    private void txt_tenctspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenctspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenctspActionPerformed

    private void btn_themctspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themctspActionPerformed
        // TODO add your handling code here:
        ChiTietSP ctspmoi = getctsp(1);
        if(ctspmoi!=null){
            try {
                ctspRepo.luu(ctspmoi);
            } catch (SQLException ex) {
                //Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            dfkw = "";
            fillCTSP();
            fillBan();
            clearFormCTSP();
        }
    }//GEN-LAST:event_btn_themctspActionPerformed

    private void btn_suactspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suactspActionPerformed
        // TODO add your handling code here:
        ChiTietSP ctspSua = getctsp(2);
        if(ctspSua!=null){
            try {
                ctspSua.setId(Integer.parseInt(txt_idctSP.getText()));
                ctspRepo.sua(ctspSua);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(jpbban, "Mã này đã tồn tại");
            }
            dfkw="";
            fillCTSP();
            fillBan();
            clearFormCTSP();
        }
    }//GEN-LAST:event_btn_suactspActionPerformed

    private void btn_xoactspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoactspActionPerformed
        // TODO add your handling code here:
        try {
            int muonxoa = Integer.parseInt(txt_idctSP.getText());
            int quyetDinh = JOptionPane.showConfirmDialog(jpnctsp, "Bạn có muốn xóa không", "", JOptionPane.YES_NO_OPTION);
            if(quyetDinh==JOptionPane.YES_OPTION){
                try {
                    ctspRepo.xoa(muonxoa);
                } catch (SQLException ex) {
                    Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (Exception e) {
        }

        dfkw = "";
        fillBan();
        fillCTSP();
        clearFormCTSP();
    }//GEN-LAST:event_btn_xoactspActionPerformed

    private void btn_clearctspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearctspActionPerformed
        // TODO add your handling code here:
        clearFormCTSP();
    }//GEN-LAST:event_btn_clearctspActionPerformed

    private void btn_tkctspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tkctspActionPerformed
        // TODO add your handling code here:
        dfkw = txt_tkctsp.getText();
        fillCTSP();
    }//GEN-LAST:event_btn_tkctspActionPerformed

    private void txt_idctSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idctSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idctSPActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clearctsp;
    private javax.swing.JButton btn_datlai;
    private javax.swing.JButton btn_datlaisp;
    private javax.swing.JButton btn_suactsp;
    private javax.swing.JButton btn_suasp;
    private javax.swing.JButton btn_themctsp;
    private javax.swing.JButton btn_themexcel;
    private javax.swing.JButton btn_themnhanhdungtich;
    private javax.swing.JButton btn_themnhanhhieuung;
    private javax.swing.JButton btn_themnhanhketcau;
    private javax.swing.JButton btn_themnhanhmausac;
    private javax.swing.JButton btn_themnhanhnhomhuong;
    private javax.swing.JButton btn_themsp;
    private javax.swing.JButton btn_themthuoctinh;
    private javax.swing.JButton btn_timkiem;
    private javax.swing.JButton btn_tkctsp;
    private javax.swing.JButton btn_ttClearDT;
    private javax.swing.JButton btn_ttClearHU;
    private javax.swing.JButton btn_ttClearKC;
    private javax.swing.JButton btn_ttClearMS;
    private javax.swing.JButton btn_ttClearNH;
    private javax.swing.JButton btn_ttSuaDT;
    private javax.swing.JButton btn_ttSuaKC;
    private javax.swing.JButton btn_ttSuaMS;
    private javax.swing.JButton btn_ttSuaNH;
    private javax.swing.JButton btn_ttThemDT;
    private javax.swing.JButton btn_ttThemKC;
    private javax.swing.JButton btn_ttThemMS;
    private javax.swing.JButton btn_ttThemNH;
    private javax.swing.JButton btn_ttXoaDT;
    private javax.swing.JButton btn_ttXoaKC;
    private javax.swing.JButton btn_ttXoaMS;
    private javax.swing.JButton btn_ttXoaNH;
    private javax.swing.JButton btn_xoactsp;
    private javax.swing.JButton btn_xoasp;
    private javax.swing.JButton btnsuaHU;
    private javax.swing.JButton btnthemHU;
    private javax.swing.JButton btnxoaHU;
    private javax.swing.JComboBox<String> cbo_dungtich;
    private javax.swing.JComboBox<String> cbo_dungtichtimkiem;
    private javax.swing.JComboBox<String> cbo_hieuung;
    private javax.swing.JComboBox<String> cbo_hieuungtimkiem;
    private javax.swing.JComboBox<String> cbo_ketcau;
    private javax.swing.JComboBox<String> cbo_ketcautimkiem;
    private javax.swing.JComboBox<String> cbo_masp;
    private javax.swing.JComboBox<String> cbo_mausac;
    private javax.swing.JComboBox<String> cbo_mausactimkiem;
    private javax.swing.JComboBox<String> cbo_nhomhuong;
    private javax.swing.JComboBox<String> cbo_nhomhuongtimkiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel jpbban;
    private javax.swing.JPanel jpn_sp;
    private javax.swing.JPanel jpnctsp;
    private javax.swing.JPanel jpndungtich;
    private javax.swing.JPanel jpnhieuung;
    private javax.swing.JPanel jpnketcau;
    private javax.swing.JPanel jpnmausac;
    private javax.swing.JPanel jpnnhomhuong;
    private javax.swing.JTabbedPane jpnsanpham;
    private javax.swing.JPanel jpnthuoctinh;
    private javax.swing.JLabel lbl_dungtich;
    private javax.swing.JLabel lbl_dungtichtimkiem;
    private javax.swing.JLabel lbl_hieuung;
    private javax.swing.JLabel lbl_ketcau;
    private javax.swing.JLabel lbl_ketcautimkiem;
    private javax.swing.JLabel lbl_maSp;
    private javax.swing.JLabel lbl_mausac;
    private javax.swing.JLabel lbl_mausactimkiem;
    private javax.swing.JLabel lbl_nhomhuong;
    private javax.swing.JLabel lbl_sanpham;
    private javax.swing.JLabel lbl_tk;
    private javax.swing.JLabel lblidctsp;
    private javax.swing.JTable tbl_ctsp;
    private javax.swing.JTable tbl_sanpham;
    private javax.swing.JTable tbl_ttdungtich;
    private javax.swing.JTable tbl_tthieuung;
    private javax.swing.JTable tbl_ttketcau;
    private javax.swing.JTable tbl_ttmausac;
    private javax.swing.JTable tbl_ttnhomhuong;
    private javax.swing.JTextField txt_giabansp;
    private javax.swing.JTextField txt_idctSP;
    private javax.swing.JTextField txt_mactsp;
    private javax.swing.JTextField txt_masp;
    private javax.swing.JTextField txt_soluongsp;
    private javax.swing.JTextField txt_sptimkiem;
    private javax.swing.JTextField txt_tenctsp;
    private javax.swing.JTextField txt_tensp;
    private javax.swing.JTextField txt_tkctsp;
    private javax.swing.JTextField txt_ttDT;
    private javax.swing.JTextField txt_ttHU;
    private javax.swing.JTextField txt_ttKC;
    private javax.swing.JTextField txt_ttMS;
    private javax.swing.JTextField txt_ttNH;
    private javax.swing.JTextField txtdtID;
    private javax.swing.JTextField txthuID;
    private javax.swing.JTextField txtkxID;
    private javax.swing.JTextField txtmauID;
    private javax.swing.JTextField txtnhID;
    private javax.swing.JTextField txtsanphamID;
    // End of variables declaration//GEN-END:variables

    
}
