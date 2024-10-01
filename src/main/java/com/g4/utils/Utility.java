package com.g4.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author admin
 */
public class Utility {
    // Code các phương thức hay dùng như: Các phương thức sử lý chuỗi, Check số, chữ...

    // 1. Phương thức zen mã theo tên Nguyễn Anh Dũng => dungna
    public static String ZenMaTheoTen(String value) {// NGUYỄN ANH DŨNG
        if (value.isEmpty() || value.isBlank()) {
            return "";
        }
        var temp = value.trim().toLowerCase();// nguyễn anh dũng
        String[] arrName = temp.split("\\s+");// Cắt chuỗi về mảng
        String finalName = VietHoaChuCaiDau(arrName[arrName.length - 1]).trim();// Dũng
        for (int i = 0; i < arrName.length - 1; i++) {
            finalName += LayChuCaiDau(arrName[i]);// Dungna
        }
        return unAccent(finalName); //Dungna
    }

    // 2. Phương thức viết hoa chữ cái đầu nGUYỄN ANH dũng => Nguyễn Anh Dũng
    public static String VietHoaFullName(String value) {// NGUYỄN ANH DŨNG
        if (value.isEmpty() || value.isBlank()) {
            return "";
        }
        var temp = value.trim().toLowerCase();// nguyễn anh dũng
        String[] arrName = temp.split("\\s+");// Cắt chuỗi về mảng
        String finalName = "";
        for (int i = 0; i < arrName.length; i++) {
            finalName += VietHoaChuCaiDau(arrName[i]);// Nguyễn Anh Dũng

        }
        return finalName;// Nguyễn Anh Dũng
    }

    // Phương thức viết hoa chữ cái đầu
    public static String VietHoaChuCaiDau(String value) { // DŨNG => Dũng
        var temp = value.trim().toLowerCase();// dũng
        return String.valueOf(temp.charAt(0)).toUpperCase() + temp.substring(1) + " ";// => Dũng
    }

    // Phương thức lấy chữ cái đầu 
    public static String LayChuCaiDau(String value) { // DŨNG => D
        var temp = value.trim().toLowerCase();// dũng
        return String.valueOf(temp.charAt(0));// => D
    }

    // Phương thức lấy các chữ đầu 
    public static String LayCacChuCaiDau(String value) {// NGUYỄN ANH DŨNG
        if (value.isEmpty() || value.isBlank()) {
            return "";
        }
        var temp = value.trim().toLowerCase();// nguyễn anh dũng
        String[] arrName = temp.split("\\s+");// Cắt chuỗi về mảng
        String finalName = "";
        for (int i = 0; i < arrName.length; i++) {
            finalName += LayChuCaiDau(arrName[i]);// NAD
        }
        return finalName.toUpperCase(); //NAD
    }

    // Phương lấy chuỗi đến khoảng trắng đầu tiên
    public String getChuoiTheoKhoangTrang(String input) {
        if (input == null) {
            return null;
        }
        String[] arrChuoi = input.trim().split("\\s+");
        String chuoi = arrChuoi[0];
        return chuoi;
    }

    // Lấy ngày trong chuỗi ngày tháng
    public String getChuoiNgay(String input) {
        String[] arrNgay = input.trim().split("-");
        String Chuoingay = arrNgay[2];
        return Chuoingay;
    }

    // Lấy tháng trong chuỗi ngày tháng
    public String getThang(String input) {
        if (input == null) {
            return 0+"";
        }
        String[] arrThang = input.trim().split("-");
        String ChuoiThang = arrThang[1];
        return ChuoiThang;
    }

    // Lấy năm trong chuỗi ngày tháng
    public String getNam(String input) {
        if (input == null) {
            return 0+"";
        }
        String[] arrNam = input.trim().split("-");
        String Nam = arrNam[0];
        return Nam;
    }
    
    // chữ in hoa
    public String inHoa(String input) {
        return input.toUpperCase();
    }

    // check rỗng
    public boolean CheckRong(String input) {
        if (input.isEmpty() || input.isBlank()) {
            return true;
        }
        return false;
    }

    // Check dữ liệu chữ
    public boolean CheckChu(String input) {
        String p_hoten = "[a-zA-Z ]+";
        if (!input.matches(p_hoten)) {
            return true;
        }
        return false;
    }
    
    // Check số thập phân
    public boolean CheckSoThapPhan(String input){
        String p_soThapPhan ="[+-]?([0-9]*[.])?[0-9]+";
        if (!input.matches(p_soThapPhan)) {
            return true;
        }
        return false;
    }
    
    // check dữ liệu số
    public boolean CheckSo(String input) {
        String p_so = "[0-9]+";
        if (!input.matches(p_so)) {
            return true;
        }
        return false;
    }

    // check dữ liệu sdt
    public boolean CheckSdt(String input) {
        String p_sdt = "0[0-9]{9}";
        if (!input.matches(p_sdt)) {
            return true;
        }
        return false;
    }
    //check ngày tháng yyyy-mm-dd
    public boolean CheckNgayThang(String input){
        String p_day = "^([0-9]{4}[-/]?((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))|([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00)[-/]?02[-/]?29)$";
        if (!input.matches(p_day)) {
            return true;
        }
        return false;
    }

    // Đếm chuỗi
    public int DemChuoi(String input) {
        String[] arrPass = input.trim().split("");
        int count = 0;
        for (int i = 0; i < arrPass.length; i++) {
             count++;
        }
        return count;
    }
    
  
    // Random số ngẫu nhiên
    public static int ranDom() {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 100;
        int randomInt = (int) randomDouble;
        return randomInt;

    }

    private static String unAccent(String s) {//Convert từ tiếng việt có dấu về tiếng việt 0 dấu
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "");
    }

}
