package ph29152.fptpoly.duanoderfoodnhom1.Model;

public class ListProductCart {
    private int idDanhSachSanPham, idUsers,idHamburger;
    private int maDanhSachSanPham = 0;
    private String tenHamburger, anhSanPham;
    private int soLuong;
    private double giaTien;

    public ListProductCart() {
    }

    public ListProductCart(int idDanhSachSanPham, int maDanhSachSanPham, int idUsers, int idHamburger, String tenHamburger, String anhSanPham, int soLuong, double giaTien) {
        this.idDanhSachSanPham = idDanhSachSanPham;
        this.maDanhSachSanPham = maDanhSachSanPham;
        this.idUsers = idUsers;
        this.idHamburger = idHamburger;
        this.tenHamburger = tenHamburger;
        this.anhSanPham = anhSanPham;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }

    public int getIdDanhSachSanPham() {
        return idDanhSachSanPham;
    }

    public void setIdDanhSachSanPham(int idDanhSachSanPham) {
        this.idDanhSachSanPham = idDanhSachSanPham;
    }

    public int getMaDanhSachSanPham() {
        return maDanhSachSanPham;
    }

    public void setMaDanhSachSanPham(int maDanhSachSanPham) {
        this.maDanhSachSanPham = maDanhSachSanPham;
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

    public String getTenHamburger() {
        return tenHamburger;
    }

    public void setTenHamburger(String tenHamburger) {
        this.tenHamburger = tenHamburger;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
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
