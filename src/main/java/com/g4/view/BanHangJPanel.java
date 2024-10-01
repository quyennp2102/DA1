package com.g4.view;

import com.g4.entity.KetCau;
import com.g4.entity.HoaDonChiTiet;
import com.g4.entity.DungTich;
import com.g4.entity.MauSac;
import com.g4.entity.SanPham;
import com.g4.entity.HieuUng;
import com.g4.main.Main;
import com.g4.repository.impl.BanHangRepository;
import com.g4.repository.impl.KetCauRepo;
import com.g4.repository.impl.DungTichRepo;
import com.g4.repository.impl.MauSacRepo;
import com.g4.repository.impl.HieuUngRepo;
import com.g4.utils.Auth;
import com.g4.utils.MsgBox;
import com.g4.viewmodel.GioHangViewModel;
import com.g4.viewmodel.HoaDonViewModel;
import com.g4.viewmodel.SanPhamViewModel;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class BanHangJPanel extends javax.swing.JPanel {

    private final BanHangRepository bhs = new BanHangRepository();

    DecimalFormat fomat = new DecimalFormat("###,###,###");

    private DefaultTableModel tblModelHoaDon = new DefaultTableModel();
    private DefaultTableModel tblModelGioHang = new DefaultTableModel();
    private DefaultTableModel tblModelSanPham = new DefaultTableModel();
    private List<GioHangViewModel> listGH = new ArrayList<>();
    private List<SanPhamViewModel> listSP = new ArrayList<>();
    private List<HoaDonViewModel> listHD = new ArrayList<>();

    private DungTichRepo kichCoRepository = new DungTichRepo();
    private MauSacRepo mauSacRepository = new MauSacRepo();
    private KetCauRepo chatLieuReporitory = new KetCauRepo();
    private HieuUngRepo thuongHieuRepository = new HieuUngRepo();

    public BanHangJPanel() {
        initComponents();
        init();
    }

    private void init() {
        listHD = bhs.getALLHD();
        loadHoaDon(listHD);
        listSP = bhs.getAllSP();
        loadSanPham(listSP);

        loadKichThuoc();
        loadChatLieu();
        loadThuongHieu();
        loadMauSac();

        if (demTrangThai() > 4) {
            btnTaoHoaDon.setEnabled(false);
        }
        //Enter txt tiền khách đưa --> tiền thừa
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double thanhToan = Double.valueOf(lblThanhToan.getText().replace(",", ""));
                Double tienKhachDua = Double.valueOf(txtTienKhachDua.getText());
                Double tienThua;

                if (tienKhachDua == 0 || tienKhachDua == null) {
                    tienThua = 0.0;
                } else {
                    tienThua = tienKhachDua - thanhToan;
                }
                lblTienThua.setText(String.valueOf(fomat.format(tienThua)));
            }
        };
        txtTienKhachDua.addActionListener(action);

    }

    private int demTrangThai() {
        int a = 0;
        for (HoaDonViewModel x : listHD) {
            if (x.getTrangThai() == 1) {
                x.getTrangThai();
                a++;
            }
        }
        return a;
    }

    public void loadGioHang(List<GioHangViewModel> listGioHangS) {
        tblModelGioHang = (DefaultTableModel) tbGioHang.getModel();
        tblModelGioHang.setRowCount(0);
        for (GioHangViewModel gh : listGioHangS) {
            tblModelGioHang.addRow(gh.todataRow());
        }
    }

    public void loadSanPham(List<SanPhamViewModel> listSanPhams) {
        tblModelSanPham = (DefaultTableModel) tbSanPham.getModel();
        tblModelSanPham.setRowCount(0);
        for (SanPhamViewModel sp : listSanPhams) {
            tblModelSanPham.addRow(sp.todataRowSanPham());
        }
    }

    public void loadHoaDon(List<HoaDonViewModel> listHoaDons) {
        tblModelHoaDon = (DefaultTableModel) tbHoaDon.getModel();
        tblModelHoaDon.setRowCount(0);
        for (HoaDonViewModel hd : listHoaDons) {
            tblModelHoaDon.addRow(hd.toRowDataHD());
        }
    }

    private void insertHoaDon() {
        // Tạo hóa đơn
        if (demTrangThai() > 3) {
            btnTaoHoaDon.setEnabled(false);
        }
        HoaDonViewModel hd = new HoaDonViewModel();
        String id = Auth.user.getId();
        hd.setIdNV(id);
        hd.setIdKH(bhs.findByIDKH(lblMaKH.getText()));
        hd.setTrangThai(1);
        //Lưu hóa đơn tạo vào bảng hóa đơn
        bhs.addHoaDon(hd);
        //Hóa đơn chờ
        listHD = bhs.getALLHD();
        loadHoaDon(listHD);
    }

    private void fillInsertSanPhamGH() {
        GioHangViewModel gh = new GioHangViewModel();
        int rowHD = tbHoaDon.getSelectedRow();
        int row = tbSanPham.getSelectedRow();
        SanPhamViewModel sp = listSP.get(row);
        if (rowHD < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn muốn thêm sản phẩm");
        } else {
            String soLuong = JOptionPane.showInputDialog("Mời bạn nhập số lượng: ");

            if (soLuong != null) {
                if (!soLuong.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Nhập đúng định dạng");
                } else if (Integer.parseInt(soLuong) > sp.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá -.-");
                } else {
                    // Thêm sản phẩm vào giỏ hàng
                    HoaDonViewModel hd = listHD.get(rowHD);
                    gh.setSoLuong(Integer.parseInt(soLuong));
                    gh.setMaSP(sp.getMaSP());
                    gh.setTenSP(sp.getTenSP());
                    gh.setDonGia(sp.getGiaBan());
                    boolean trung = false;
                    for (GioHangViewModel x : listGH) {
                        if (x.getMaSP().contains(sp.getMaSP())) {
                            trung = true;
                        }
                    }
                    if (trung) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong giỏ hàng, để thêm số lượng vui lòng chọn chức năng cập nhật");
                    } else {
                        // Thêm sản phẩm vào list giỏ hàng
                        listGH.add(gh);
                        sp.setSoLuong(sp.getSoLuong() - Integer.parseInt(soLuong));
                        loadSanPham(listSP);

                        String idHD = hd.getId();
                        String idSP = sp.getId();
                        int soLuong1 = Integer.parseInt(soLuong);
                        Double donGia = sp.getGiaBan();

                        // add giỏ hàng vào HDCT
                        HoaDonChiTiet hdct = new HoaDonChiTiet(idHD, idSP, soLuong1, donGia);
                        JOptionPane.showMessageDialog(this, bhs.addHDCT(hdct));
                        listGH = bhs.getGioHang(idHD);
                        loadGioHang(listGH);

                        //Cập nhật số lượng trong bảng Sản phẩm CT
                        SanPham sp1 = new SanPham(sp.getSoLuong());
                        bhs.updateSoLuong(sp1, idSP);

                        //Fill thành tiền, thanh toán, giảm giá
                        double thanhTien = 0;
                        double thanhToan = 0;
                        double giamGia = 0;

                        for (GioHangViewModel gha : listGH) {
                            thanhTien += gha.getSoLuong() * gh.getDonGia();
                        }
                        lblThanhTien.setText(String.valueOf(fomat.format(thanhTien)));
                        lblThanhToan.setText(String.valueOf(fomat.format(thanhToan = thanhTien)));
                    }
                }
            }
        }
    }

    private void fillDataHD(int index) {
        //fill data hoa don
        HoaDonViewModel hd = listHD.get(index);
        lblMaHD.setText(hd.getMaHD());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        lblMaKH.setText(hd.getIdKH());
        lblTenKhachHangNhanh.setText(hd.getTenKhachHang());
        lblNgayTao.setText(sdf.format(hd.getNgayTao()));

    }

    private void fillHoaDonCho() {
        int index = tbHoaDon.getSelectedRow();
        HoaDonViewModel hd = listHD.get(index);
        String idHD = hd.getId();
        listGH = bhs.getGioHang(idHD);
        loadGioHang(listGH);

        double thanhTien = 0;
        double thanhToan = 0;
        double giamGia = 0;

        for (GioHangViewModel gh : listGH) {
            thanhTien += gh.getSoLuong() * gh.getDonGia();
        }

        lblThanhTien.setText(String.valueOf(fomat.format(thanhTien)));
        lblThanhToan.setText(String.valueOf(fomat.format(thanhToan = thanhTien)));
        fillDataHD(index);

        txtTienKhachDua.setText("");
        lblTienThua.setText("0");

        btnThanhToan.setEnabled(true);
        btnHuyHoaDon.setEnabled(true);
        btnLamMoi.setEnabled(true);
    }

    private void updateSanPhamGioHang() {
        int indexHD = tbHoaDon.getSelectedRow();
        int indexGH = tbGioHang.getSelectedRow();
        if (indexGH < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần cập nhật số lượng");

        } else {
            String soLuongMoi = JOptionPane.showInputDialog("Mời nhập số lượng cần cập nhật: ");
            if (soLuongMoi != null) {
                if (!soLuongMoi.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Nhập đúng định dạng");
                } else {
                    GioHangViewModel gh = listGH.get(indexGH);
                    HoaDonViewModel hd = listHD.get(indexHD);
                    String idCTSP = gh.getIdSP();
                    String id = gh.getId();
                    String idHD = hd.getId();
                    int soLuongCu = gh.getSoLuong();
                    int soLuongCapNhat = 0;
                    if (Integer.valueOf(soLuongMoi) < soLuongCu) {
                        soLuongCapNhat = soLuongCu - Integer.valueOf(soLuongMoi);
                        SanPham ctsp = new SanPham(soLuongCapNhat);
                        bhs.capNhatSoLuong(ctsp, idCTSP);
                    } else {
                        soLuongCapNhat = Integer.valueOf(soLuongMoi) - soLuongCu;
                        SanPham ctsp = new SanPham(soLuongCapNhat);
                        bhs.capNhatSoLuong2(ctsp, idCTSP);
                    }
                    gh.setSoLuong(Integer.valueOf(soLuongMoi));
                    bhs.updateSoLuongHDCT(gh, id);
                    listGH = bhs.getGioHang(idHD);
                    loadGioHang(listGH);
                    listSP = bhs.getAllSP();
                    loadSanPham(listSP);
                    double thanhTien = 0;
                    double thanhToan = 0;
                    double giamGia = 0;
                    double tienKhachDua = 0;
                    
                    for (GioHangViewModel gha : listGH) {
                        thanhTien += gha.getSoLuong() * gha.getDonGia();                       
                    }

                    lblThanhTien.setText(String.valueOf(fomat.format(thanhTien)));
                    lblThanhToan.setText(String.valueOf(fomat.format(thanhToan = thanhTien)));             
                }
            }
        }
    }

    private void deleteGioHang() {
        int indexHD = tbHoaDon.getSelectedRow();
        int indexGH = tbGioHang.getSelectedRow();
        if (indexGH < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");

        } else {

            GioHangViewModel gh = listGH.get(indexGH);
            HoaDonViewModel hd = listHD.get(indexHD);
            int soLuongGH = gh.getSoLuong();
            String idHD = hd.getId();

            String id = gh.getId();
            String idCTSP = gh.getIdSP();
            int tempTT = JOptionPane.showOptionDialog(this, "Bạn có chắc muốn xóa sản phẩm khỏi giỏ hàng không ?", "Xóa sản phẩm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (tempTT == JOptionPane.YES_OPTION) {
                SanPham ctsp = new SanPham(soLuongGH);
                bhs.capNhatSoLuong(ctsp, idCTSP);
                JOptionPane.showMessageDialog(this, bhs.deleteGioHang(id));
                listSP = bhs.getAllSP();
                listGH = bhs.getGioHang(idHD);

                loadGioHang(listGH);
                loadSanPham(listSP);
                double thanhTien = 0;
                double thanhToan = 0;
                double giamGia = 0;
                String cbbVoucher = cbbGiaGiam.getSelectedItem().toString();
                for (GioHangViewModel gha : listGH) {
                    thanhTien += gha.getSoLuong() * gha.getDonGia();
                }
                if (cbbVoucher.equals("5%")) {
                    giamGia = 0.95;
                } else if (cbbVoucher.equals("10%")) {
                    giamGia = 0.90;
                } else if (cbbVoucher.equals("15%")) {
                    giamGia = 0.85;
                } else if (cbbVoucher.equals("20%")) {
                    giamGia = 0.80;
                } else if (cbbVoucher.equals("25%")) {
                    giamGia = 0.75;
                } else if (cbbVoucher.equals("30%")) {
                    giamGia = 0.70;
                } else if (cbbVoucher.equals("35%")) {
                    giamGia = 0.65;
                } else if (cbbVoucher.equals("40%")) {
                    giamGia = 0.60;
                } else if (cbbVoucher.equals("45%")) {
                    giamGia = 0.55;
                } else {
                    giamGia = 0.5;
                }
                lblThanhTien.setText(String.valueOf(fomat.format(thanhTien)));
                lblThanhToan.setText(String.valueOf(fomat.format(thanhToan = thanhTien * giamGia)));

            }
        }
    }

    private void huyHoaDon() {
        int index = tbHoaDon.getSelectedRow();
        int sl = listGH.size();

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn muốn hủy");
        } else {
            int tempTT = JOptionPane.showOptionDialog(this, "Bạn có chắc muốn hủy hóa đơn không ?", "Hủy hóa đơn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (tempTT == JOptionPane.YES_OPTION) {
                if (listHD != null) {
                    for (GioHangViewModel ghu : listGH) {
                        int soLuongGH = ghu.getSoLuong();
                        String idCTSP = ghu.getIdSP();
                        SanPham ctsp = new SanPham(soLuongGH);
                        bhs.capNhatSoLuong(ctsp, idCTSP);

                    }
                    HoaDonViewModel hdid = listHD.get(index);
                    String idHD = hdid.getId();
                    bhs.deleteHDCT(idHD);
                    bhs.deleteHD(idHD);
                    listSP = bhs.getAllSP();
                    listHD = bhs.getALLHD();
                    listGH = bhs.getGioHang(idHD);
                    loadHoaDon(listHD);
                    loadSanPham(listSP);
                    loadGioHang(listGH);
//                    btnThanhToan.setEnabled(false);
                    if (demTrangThai() < 6) {
                        btnTaoHoaDon.setEnabled(true);
                    }
                    lblMaHD.setText("HD++");
                    lblNgayTao.setText("date");
                    lblThanhTien.setText("0");
                    cbbGiaGiam.setSelectedItem("0");
                    lblThanhToan.setText("0");
                    txtTienKhachDua.setText("0");
                    lblTienThua.setText("0");
                }
            }

        }
    }

    private void thanhToan() {
        long millis = System.currentTimeMillis();

        String thanhToan = lblThanhToan.getText();

        String tenKH = lblTenKhachHangNhanh.getText();

        String tienKhachDua1 = txtTienKhachDua.getText();
        String tienThua = lblTienThua.getText();

        int temp = 3;

        if (lblThanhTien.getText().equals("0.0")) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm trước khi thanh toán");
        } else if (txtTienKhachDua.getText().matches("\\s+") && txtTienKhachDua.equals("0") && cbbHTTT.getSelectedItem().equals("Tiền mặt")) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
            txtTienKhachDua.setText("");
        } else {
            int tempTT = JOptionPane.showOptionDialog(this, "Bạn có chắc muốn thanh toán không ?", "Thanh toán", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (tempTT == JOptionPane.YES_OPTION) {
                String maHd = lblMaHD.getText();
                HoaDonViewModel hd = new HoaDonViewModel();
                hd.setNgayThanhToan(new Date(millis));
                hd.setTongTien(Double.valueOf(thanhToan.replace(",", "")));
                if (cbbHTTT.getSelectedItem().equals("Tiền mặt")) {
                    hd.setHinhThucThanhToan(true);
                    if (txtTienKhachDua.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa");
                        return; // return to prevent further execution
                    }
                    double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());
                    if (tienKhachDua < hd.getTongTien()) {
                        JOptionPane.showMessageDialog(this, "Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền");
                        return; // return to prevent further execution
                    }
                } else if (cbbHTTT.getSelectedItem().equals("Chuyển khoản")) {
                    hd.setHinhThucThanhToan(false);
                }
                hd.setTrangThai(temp);
                String message = bhs.updateTrangThai(hd, maHd);
                JOptionPane.showMessageDialog(this, message);

                listHD = bhs.getALLHD();
                loadHoaDon(listHD);

                listGH = bhs.getGioHang(maHd);
                loadGioHang(listGH);

                txtHoaDonPDF.append("\n          SHOP G4\n"
                        + "\n              Hóa Đơn Thanh Toán \n"
                        + "---------------------------------------------------\n"
                        + "Ngày thanh toán:    " + hd.getNgayThanhToan() + "\n"
                        + "Tên khách hàng:    " + tenKH + "\n"
                        + "Thành tiền:             " + thanhToan + "   VNĐ" + "\n");

                if (hd.isHinhThucThanhToan()) { // If payment method is "Tiền mặt"
                    txtHoaDonPDF.append("Tiền khách đưa:     " + tienKhachDua1 + "   VNĐ" + "\n"
                            + "Tiền thừa:              " + tienThua + "   VNĐ" + "\n");
                } else { // If payment method is "Chuyển khoản"
                    txtHoaDonPDF.append("Phương thức thanh toán:     Chuyển khoản" + "\n");
                }

                txtHoaDonPDF.append("---------------------------------------------------\n"
                        + "              Cảm Ơn Quý Khách\n");
                temp = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không");

                String filePath = "src/main/resources/com/g4/bills/bill.pdf"; // Specify the file path where the PDF will be saved
                
                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));
                    document.open();

                    Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                    Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);

                    // Add the invoice content to the PDF document
                    document.add(new Paragraph("SHOP G4", titleFont));
                    document.add(new Paragraph("Hoa Don Thanh Toan", titleFont));
                    document.add(new Paragraph("---------------------------------------------------", contentFont));
                    document.add(new Paragraph("Ngay thanh toan:    " + hd.getNgayThanhToan(), contentFont));
                    document.add(new Paragraph("Ten khach hang:    " + tenKH, contentFont));
                    document.add(new Paragraph("Thanh tien:             " + thanhToan + "   VND", contentFont));

                    if (hd.isHinhThucThanhToan()) { // If payment method is "Tiền mặt"
                        document.add(new Paragraph("Tien khach dua:     " + tienKhachDua1 + "   VND", contentFont));
                        document.add(new Paragraph("Tien thua:              " + tienThua + "   VND", contentFont));
                    } else { // If payment method is "Chuyển khoản"
                        document.add(new Paragraph("Phuong thuc thanh toan:     Chuyen khoan", contentFont));
                    }

                    document.add(new Paragraph("---------------------------------------------------", contentFont));
                    document.add(new Paragraph("              Cam on quy khach", contentFont));

                    document.close();

                    System.out.println("Invoice PDF generated successfully at: " + filePath);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }

                btnThanhToan.setEnabled(false);
                btnHuyHoaDon.setEnabled(false);
                btnLamMoi.setEnabled(false);

            }
            lblMaHD.setText("HD++");
            lblThanhTien.setText("0");
            cbbGiaGiam.setSelectedItem("0");
            lblThanhToan.setText("0");
            txtTienKhachDua.setText("0");
            lblTienThua.setText("0");
            if (demTrangThai() < 5) {
                btnTaoHoaDon.setEnabled(true);
            }
        }

    }

    private void loadKichThuoc() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cbbKichThuoc.getModel();
        boxModel.removeAllElements();
        try {
            List<DungTich> list = kichCoRepository.getKichCo();
            for (DungTich coGiay : list) {
                boxModel.addElement(coGiay);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu kích cỡ giày");
        }
    }

    private void loadMauSac() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cbbMauSac.getModel();
        boxModel.removeAllElements();
        try {
            List<MauSac> list = mauSacRepository.getMau();
            for (MauSac mauSac : list) {
                boxModel.addElement(mauSac);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu màu sắc");
        }
    }

    private void loadChatLieu() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cbbChatLieuGiay.getModel();
        boxModel.removeAllElements();
        try {
            List<KetCau> list = chatLieuReporitory.getChatLieu();
            for (KetCau chatLieu : list) {
                boxModel.addElement(chatLieu);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu Chất liệu giày");
        }
    }

    private void loadThuongHieu() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cbbThuongHieu.getModel();
        boxModel.removeAllElements();
        try {
            List<HieuUng> list = thuongHieuRepository.getThuongHieu();
            for (HieuUng thuongHieu : list) {
                boxModel.addElement(thuongHieu);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu thương hiệu");
        }
    }

    public void setMaKH(String maKH) {
        lblMaKH.setText(maKH);
    }

    public void setTenKH(String tenKH) {
        lblTenKhachHangNhanh.setText(tenKH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        txtTimSP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbbKichThuoc = new javax.swing.JComboBox<>();
        cbbMauSac = new javax.swing.JComboBox<>();
        cbbChatLieuGiay = new javax.swing.JComboBox<>();
        cbbThuongHieu = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbGioHang = new javax.swing.JTable();
        btnXoaSanPham = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        lblTenKhachHangNhanh = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTenTienThua1 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        cbbGiaGiam = new javax.swing.JComboBox<>();
        lblThanhToan = new javax.swing.JLabel();
        cbbHTTT = new javax.swing.JComboBox<>();
        btnTaoHoaDon = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        lblTenTienKhachDua = new javax.swing.JLabel();
        lblTenTienThua = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtHoaDonPDF = new javax.swing.JTextArea();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chờ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Ngày tạo", "Nhân viên tạo", "Khách hàng", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHoaDon);
        if (tbHoaDon.getColumnModel().getColumnCount() > 0) {
            tbHoaDon.getColumnModel().getColumn(0).setMinWidth(80);
            tbHoaDon.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên sản phẩm", "Kích thước", "Màu sắc", "Chất liệu giày", "Thương hiệu", "Số lượng", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbSanPham);
        if (tbSanPham.getColumnModel().getColumnCount() > 0) {
            tbSanPham.getColumnModel().getColumn(0).setMinWidth(80);
            tbSanPham.getColumnModel().getColumn(0).setMaxWidth(80);
            tbSanPham.getColumnModel().getColumn(6).setMinWidth(60);
            tbSanPham.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        txtTimSP.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimSPCaretUpdate(evt);
            }
        });

        jLabel2.setText("Tìm kiếm sản phẩm:");

        cbbKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbKichThuocActionPerformed(evt);
            }
        });

        cbbMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMauSacActionPerformed(evt);
            }
        });

        cbbChatLieuGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChatLieuGiayActionPerformed(evt);
            }
        });

        cbbThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbThuongHieuActionPerformed(evt);
            }
        });

        jLabel8.setText("Kích thước");

        jLabel10.setText("Màu sắc");

        jLabel11.setText("Chất liệu giày");

        jLabel12.setText("Thương hiệu");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbChatLieuGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbThuongHieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(cbbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbChatLieuGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbGioHang);
        if (tbGioHang.getColumnModel().getColumnCount() > 0) {
            tbGioHang.getColumnModel().getColumn(0).setMinWidth(80);
            tbGioHang.getColumnModel().getColumn(0).setMaxWidth(80);
            tbGioHang.getColumnModel().getColumn(2).setMinWidth(60);
            tbGioHang.getColumnModel().getColumn(2).setMaxWidth(60);
            tbGioHang.getColumnModel().getColumn(3).setMinWidth(100);
            tbGioHang.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        btnXoaSanPham.setBackground(new java.awt.Color(255, 255, 102));
        btnXoaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSanPham.setText("Xóa sản phẩm");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(255, 255, 102));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnXoaSanPham)
                .addGap(18, 18, 18)
                .addComponent(btnCapNhat)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setText("Mã khách hàng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText("Tên khách hàng");

        lblMaKH.setText("KH00");

        lblTenKhachHangNhanh.setText("Khách vãng lai");

        jButton1.setText("Chọn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addGap(73, 73, 73)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenKhachHangNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(85, 85, 85)))
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblMaKH))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblTenKhachHangNhanh)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Mã hóa đơn:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Ngày tạo");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Tổng tiền");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Giảm giá:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Thanh toán");

        lblTenTienThua1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTienThua1.setText("Hình thức thanh toán");

        lblMaHD.setText("HD++");

        lblNgayTao.setText("date");

        lblThanhTien.setForeground(new java.awt.Color(255, 0, 0));
        lblThanhTien.setText("0");

        cbbGiaGiam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0" }));

        lblThanhToan.setForeground(new java.awt.Color(255, 0, 0));
        lblThanhToan.setText("0");

        cbbHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));
        cbbHTTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHTTTActionPerformed(evt);
            }
        });

        btnTaoHoaDon.setBackground(new java.awt.Color(255, 255, 102));
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        lblTenTienKhachDua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTienKhachDua.setText("Tiền khách đưa");

        lblTenTienThua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTienThua.setText("Tiền thừa");

        lblTienThua.setForeground(new java.awt.Color(255, 0, 0));
        lblTienThua.setText("0");

        txtTienKhachDua.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenTienKhachDua)
                    .addComponent(lblTenTienThua))
                .addGap(73, 73, 73)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTienThua)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTienKhachDua)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTienThua)
                    .addComponent(lblTienThua))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(lblTenTienThua1))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbGiaGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNgayTao)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(lblMaHD)
                                .addGap(69, 69, 69)
                                .addComponent(btnTaoHoaDon))
                            .addComponent(lblThanhTien)
                            .addComponent(lblThanhToan)
                            .addComponent(cbbHTTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblMaHD)
                    .addComponent(btnTaoHoaDon))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblNgayTao))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblThanhTien))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbbGiaGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblThanhToan))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTienThua1)
                    .addComponent(cbbHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnThanhToan.setBackground(new java.awt.Color(255, 255, 102));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnHuyHoaDon.setBackground(new java.awt.Color(255, 255, 102));
        btnHuyHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuyHoaDon.setText("Hủy hóa đơn");
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(255, 255, 102));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Ghi chú:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(btnThanhToan))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel24)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnHuyHoaDon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLamMoi))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 8, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToan)
                    .addComponent(btnHuyHoaDon)
                    .addComponent(btnLamMoi))
                .addContainerGap())
        );

        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtHoaDonPDF.setColumns(20);
        txtHoaDonPDF.setRows(5);
        txtHoaDonPDF.setEnabled(false);
        jScrollPane5.setViewportView(txtHoaDonPDF);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        insertHoaDon();
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked
        fillInsertSanPhamGH();
    }//GEN-LAST:event_tbSanPhamMouseClicked

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        fillHoaDonCho();
    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        updateSanPhamGioHang();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        deleteGioHang();
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        thanhToan();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        huyHoaDon();
    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        listSP = bhs.getAllSP();
        loadSanPham(listSP);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Main m = new Main();
        m.openKhachHangNhanh();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbbHTTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHTTTActionPerformed
        if (cbbHTTT.getSelectedItem().equals("Tiền mặt")) {
            lblTenTienKhachDua.setVisible(true);
            lblTenTienThua.setVisible(true);
            txtTienKhachDua.setVisible(true);
            lblTienThua.setVisible(true);
        } else {
            lblTenTienKhachDua.setVisible(false);
            lblTenTienThua.setVisible(false);
            txtTienKhachDua.setVisible(false);
            lblTienThua.setVisible(false);
        }
    }//GEN-LAST:event_cbbHTTTActionPerformed

    private void txtTimSPCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimSPCaretUpdate
        listSP = bhs.SearchSPBH(txtTimSP.getText());
        loadSanPham(listSP);
    }//GEN-LAST:event_txtTimSPCaretUpdate

    private void cbbKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbKichThuocActionPerformed
        listSP = bhs.SearchSPBH(cbbKichThuoc.getSelectedItem().toString());
        loadSanPham(listSP);
    }//GEN-LAST:event_cbbKichThuocActionPerformed

    private void cbbMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMauSacActionPerformed
        listSP = bhs.SearchSPBH(cbbMauSac.getSelectedItem().toString());
        loadSanPham(listSP);
    }//GEN-LAST:event_cbbMauSacActionPerformed

    private void cbbChatLieuGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChatLieuGiayActionPerformed
        listSP = bhs.SearchSPBH(cbbChatLieuGiay.getSelectedItem().toString());
        loadSanPham(listSP);
    }//GEN-LAST:event_cbbChatLieuGiayActionPerformed

    private void cbbThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbThuongHieuActionPerformed
        listSP = bhs.SearchSPBH(cbbThuongHieu.getSelectedItem().toString());
        loadSanPham(listSP);
    }//GEN-LAST:event_cbbThuongHieuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbChatLieuGiay;
    private javax.swing.JComboBox<String> cbbGiaGiam;
    private javax.swing.JComboBox<String> cbbHTTT;
    private javax.swing.JComboBox<String> cbbKichThuoc;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbThuongHieu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblTenKhachHangNhanh;
    private javax.swing.JLabel lblTenTienKhachDua;
    private javax.swing.JLabel lblTenTienThua;
    private javax.swing.JLabel lblTenTienThua1;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JTable tbGioHang;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextArea txtHoaDonPDF;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimSP;
    // End of variables declaration//GEN-END:variables
}
