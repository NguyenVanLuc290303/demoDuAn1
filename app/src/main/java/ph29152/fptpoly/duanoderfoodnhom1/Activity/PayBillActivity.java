package ph29152.fptpoly.duanoderfoodnhom1.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ph29152.fptpoly.duanoderfoodnhom1.Adapter.CartAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Adapter.PayAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.MainActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Model.CartManager;
import ph29152.fptpoly.duanoderfoodnhom1.Model.ListProductCart;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class PayBillActivity extends AppCompatActivity {
    RecyclerView rcvPay;
    PayAdapter adapter;
    List<ListProductCart> list =new ArrayList<>();
    TextView tvSoDuViTien,tvTongTienPay,tvTongTienHang,tvShip;
    Button btnBuyNow;
    ConstraintLayout backgroundCart;
    TextInputLayout edName,edPhone,edAddress,edVoucher;
    double ttHang;
    double tongTienThanhToan ;
    double ship;
    double viTien;
    Connection_SQL connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);
        tvSoDuViTien=findViewById(R.id.tvSoDuViTienPay);
        btnBuyNow=findViewById(R.id.btnPayNowPay);
        tvTongTienHang=findViewById(R.id.tvTongTienhang);
        tvTongTienPay=findViewById(R.id.tvTongTienPay);
        edName=findViewById(R.id.edNamePay);
        edPhone=findViewById(R.id.edPhonePay);
        edAddress=findViewById(R.id.edAddressPay);
        edVoucher=findViewById(R.id.edVoucherPay);
        tvShip=findViewById(R.id.tvShip);
        rcvPay = findViewById(R.id.rcvPay);
        backgroundCart=findViewById(R.id.backgroundCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllListPay();
        adapter = new PayAdapter(list,this);
        rcvPay.setAdapter(adapter);
        Users user = UserSession.getCurrentUser();
        viTien = user.getViTien();
        tvSoDuViTien.setText("$"+viTien);
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNow();
            }
        });
        if(edAddress.getEditText().getText().toString().trim().equalsIgnoreCase("Thái Bình")
                ||edAddress.getEditText().getText().toString().trim().equalsIgnoreCase("Thai Binh")
                ||edAddress.getEditText().getText().toString().trim().equalsIgnoreCase("ThaiBinh")
        ){
            ship = 1;
            tvShip.setText("$"+ship);
        }else{
            ship = 2;
            tvShip.setText("$"+ship);
        }
//        tinhShip();
        tongTienThanhToan();
    }

    private void tongTienThanhToan() {
        String voucher = edVoucher.getEditText().getText().toString().trim();
        double giamTien=0;
        if(voucher.equalsIgnoreCase("PH29152")){
            giamTien = 2;
        }
        if(voucher.equalsIgnoreCase("PH29289")){
            giamTien=1;
        }
        if(voucher.equalsIgnoreCase("PH29221")){
            giamTien=1.15;
        }
        if(voucher.equalsIgnoreCase("PH29203")){
            giamTien=1.2;
        }
        if(voucher.equalsIgnoreCase("PH29191")){
            giamTien=1.1;
        }
        tongTienThanhToan = ttHang + ship - giamTien;
        tvTongTienPay.setText("$"+tongTienThanhToan);

    }


    private void buyNow() {
        if(viTien - tongTienThanhToan >=0){
            thanhToanThanhCong();
//            list.clear();
//            backgroundCart.setBackgroundResource(R.drawable.fly_burger_login);
        }else{
            Toast.makeText(this, "So du khong du, ban co muon nap them tien vao vi ?", Toast.LENGTH_SHORT).show();
        }
    }

    private void thanhToanThanhCong() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PayBillActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        builder.setMessage("Bạn có chắc chắn muốn mua ngay ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateViTien();
                try{
                    connection = new Connection_SQL();
                    Users user = UserSession.getCurrentUser();
                    int userId = user.getIdUser();
                    String sql = "SELECT MADANHSACHSANPHAM FROM DANHSACHSANPHAM WHERE IDUSERS = ?" ;
                    PreparedStatement stm = connection.SQLconnection().prepareStatement(sql);
                    stm.setInt(1, userId);
                    ResultSet rs = stm.executeQuery();
                    int maDanhSachSanPham = CartActivity.maDssp;
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
// Hiển thị thời điểm hiện tại
                    String currentTime = "Ngày " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute + ":" + second;

                    if(rs.next()){
                        maDanhSachSanPham = rs.getInt("MADANHSACHSANPHAM");
                    }
                    String sql2 = "INSERT INTO THANHTOAN (IDUSERS,MADANHSACHSANPHAM,TONGTIEN,TRANGTHAI,NGAYDAT) VALUES (? , ?, ?, ?, ?)";
                    PreparedStatement stm2 = connection.SQLconnection().prepareStatement(sql2);
                    stm2.setInt(1,userId);
                    stm2.setInt(2,maDanhSachSanPham);
                    stm2.setDouble(3,tongTienThanhToan);
                    stm2.setInt(4,1);
                    stm2.setString(5,currentTime);
                    stm2.executeQuery();

//                    adapter.notifyDataSetChanged();

                    CartActivity.maDssp = CartActivity.maDssp +1;

                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(PayBillActivity.this, PaySuccess.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void updateViTien() {
        try {
            connection = new Connection_SQL();
            Users user = UserSession.getCurrentUser();
            int userId = user.getIdUser();
            double viTienNew= tongTienThanhToan;
            String sql = "UPDATE USERS " +
                    "SET VITIEN = VITIEN - ? " +
                    "WHERE IDUSERS = ?";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setDouble(1, viTienNew);
            statement.setInt(2,userId);
            statement.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ListProductCart> getAllListPay() {

        try {
            connection = new Connection_SQL();
            Users user = UserSession.getCurrentUser();
            int userId = user.getIdUser();
            String sql = "SELECT * FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDDANHSACHSANPHAM");
                int idusers = resultSet.getInt("IDUSERS");
                int idhamburger = resultSet.getInt("IDHAMBURGER");
                int maDanhSachSanPham = resultSet.getInt("MADANHSACHSANPHAM");
                String tenhamburger = resultSet.getString("TENHAMBURGER");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("ANHSANPHAM");
                ListProductCart cart = new ListProductCart(id,maDanhSachSanPham,idusers,idhamburger,tenhamburger,hinhAnh,soLuong,giaTien);
                list.add(cart);
            }
            String sql2 = "SELECT * FROM USERS WHERE IDUSERS = ?";
            PreparedStatement stm = connection.SQLconnection().prepareStatement(sql2);
            stm.setInt(1,userId);
            ResultSet resultSet1 = stm.executeQuery();
            while(resultSet1.next()){
                String ten = resultSet1.getString("TEN");
                String diaChi= resultSet1.getString("DIACHI");
                String sdt = resultSet1.getString("SODIENTHOAI");
                Objects.requireNonNull(edName.getEditText()).setText(ten);
                Objects.requireNonNull(edAddress.getEditText()).setText(diaChi);
                Objects.requireNonNull(edPhone.getEditText()).setText(sdt);
            }
            String sql3 = "SELECT SUM(GIATIEN * SOLUONG) as TONG FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement stm2 = connection.SQLconnection().prepareStatement(sql3);
            stm2.setInt(1, userId);
            ResultSet resultSet2 = stm2.executeQuery();
            if(resultSet2.next()){
                 ttHang = resultSet2.getDouble("TONG");
                tvTongTienHang.setText("$"+ttHang);
            }
            resultSet2.close();
            stm2.close();
            resultSet1.close();
            stm.close();
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}