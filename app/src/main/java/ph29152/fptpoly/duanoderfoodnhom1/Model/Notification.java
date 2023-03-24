package ph29152.fptpoly.duanoderfoodnhom1.Model;

public class Notification {
    private int idThongBao, idUsers, trangThai;
    private String noiDung;
    private double tongTien;
    private String maThongBao;

    public Notification() {
    }

    public Notification(int idThongBao, int idUsers, int trangThai, String noiDung, double tongTien, String maThongBao) {
        this.idThongBao = idThongBao;
        this.idUsers = idUsers;
        this.trangThai = trangThai;
        this.noiDung = noiDung;
        this.tongTien = tongTien;
        this.maThongBao = maThongBao;
    }

    public int getIdThongBao() {
        return idThongBao;
    }

    public void setIdThongBao(int idThongBao) {
        this.idThongBao = idThongBao;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaThongBao() {
        return maThongBao;
    }

    public void setMaThongBao(String maThongBao) {
        this.maThongBao = maThongBao;
    }
}