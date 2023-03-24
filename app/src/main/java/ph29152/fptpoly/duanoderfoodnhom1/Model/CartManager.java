package ph29152.fptpoly.duanoderfoodnhom1.Model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private int idCart, idUsers, idHamburger, soLuong;
    private double giaTien;
    private String tenHamburger;
    private String hinhAnh;

    public CartManager() {
    }

    public CartManager(int idCart, int idUsers, int idHamburger, int soLuong, double giaTien, String tenHamburger, String hinhAnh) {
        this.idCart = idCart;
        this.idUsers = idUsers;
        this.idHamburger = idHamburger;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.tenHamburger = tenHamburger;
        this.hinhAnh = hinhAnh;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getIdHamburger() {
        return idHamburger;
    }

    public void setIdHamburger(int idHamburger) {
        this.idHamburger = idHamburger;
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

    public String getTenHamburger() {
        return tenHamburger;
    }

    public void setTenHamburger(String tenHamburger) {
        this.tenHamburger = tenHamburger;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}