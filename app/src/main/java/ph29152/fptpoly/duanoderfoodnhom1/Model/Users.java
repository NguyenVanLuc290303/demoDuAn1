package ph29152.fptpoly.duanoderfoodnhom1.Model;

public class Users {
    private int idUser;
    private String ten, email, soDienThoai, diaChi, matKhau;
    private int quyen = 1; // quyen bang 1 thi la nguoi dung bang 0 la admin
    private double viTien;
    private String hinhAnh;

    public Users() {
    }

    public Users( String ten, String email, String soDienThoai, String diaChi, String matKhau, int quyen) {
        this.ten = ten;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.matKhau = matKhau;
        this.quyen = quyen;
    }

    public Users(int idUser, String ten, String email, String soDienThoai, String diaChi, String matKhau, int quyen, double viTien) {
        this.idUser = idUser;
        this.ten = ten;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.matKhau = matKhau;
        this.quyen = quyen;
        this.viTien = viTien;
    }





    public Users(int idUser, String ten, String email, String soDienThoai, String diaChi, String matKhau, int quyen, double viTien, String hinhAnh) {
        this.idUser = idUser;
        this.ten = ten;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.matKhau = matKhau;
        this.quyen = quyen;
        this.viTien = viTien;
        this.hinhAnh = hinhAnh;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    public double getViTien() {
        return viTien;
    }

    public void setViTien(double viTien) {
        this.viTien = viTien;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
