package ph29152.fptpoly.duanoderfoodnhom1.Model;

public class PayBill {
    private int idThanhToan, idUsers, maDanhSachSanPham;
    private double tongTien;
    private int trangThai;
    private String ngayDat;

    public PayBill() {
    }


    public PayBill(int idThanhToan, int idUsers, int maDanhSachSanPham, double tongTien, int trangThai, String ngayDat) {
        this.idThanhToan = idThanhToan;
        this.idUsers = idUsers;
        this.maDanhSachSanPham = maDanhSachSanPham;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.ngayDat = ngayDat;
    }

    public int getIdThanhToan() {
        return idThanhToan;
    }

    public void setIdThanhToan(int idThanhToan) {
        this.idThanhToan = idThanhToan;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getMaDanhSachSanPham() {
        return maDanhSachSanPham;
    }

    public void setMaDanhSachSanPham(int maDanhSachSanPham) {
        this.maDanhSachSanPham = maDanhSachSanPham;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }
}
