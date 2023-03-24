package ph29152.fptpoly.duanoderfoodnhom1.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Hamburger implements Serializable {
    private int id;
    private String ten,moTaNgan,moTaChiTiet,hinhAnh;
    private int soLuong;
    private double giaTien;

    public Hamburger() {
    }

    public Hamburger(int id, String ten, String moTaNgan, String moTaChiTiet, int soLuong, double giaTien, String hinhAnh) {
        this.id = id;
        this.ten = ten;
        this.moTaNgan = moTaNgan;
        this.moTaChiTiet = moTaChiTiet;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTaNgan() {
        return moTaNgan;
    }

    public void setMoTaNgan(String moTaNgan) {
        this.moTaNgan = moTaNgan;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }
}
