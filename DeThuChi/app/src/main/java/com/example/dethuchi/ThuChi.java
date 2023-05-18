package com.example.dethuchi;

import java.util.Date;

public class ThuChi {
    public int id;
    public String tenKhoan;
    public String ngayThang;
    public int soTien;
    public int logic;

    public ThuChi() {
    }

    public ThuChi(int id, String tenKhoan, String ngayThang, int soTien, int logic) {
        this.id = id;
        this.tenKhoan = tenKhoan;
        this.ngayThang = ngayThang;
        this.soTien = soTien;
        this.logic = logic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhoan() {
        return tenKhoan;
    }

    public void setTenKhoan(String tenKhoan) {
        this.tenKhoan = tenKhoan;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public int isLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }
}
