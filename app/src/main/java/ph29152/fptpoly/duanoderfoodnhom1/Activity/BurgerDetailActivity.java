package ph29152.fptpoly.duanoderfoodnhom1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.CartManager;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Hamburger;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class BurgerDetailActivity extends AppCompatActivity {
    CardView btnback,btnCard;
    ImageView imgBurger;
    TextView tvTenSanPham, tvMoTaNgan,tvMoTaChiTiet,tvGiaTien, tvTang, tvGiam, tvSoLuong;
    Button btnAddToCart;
    int idHamburger,soLuong ;
    String ten,moTaNgan,moTaChiTiet,hinhAnh;
    double giaTien;
    List<CartManager> listCart = new ArrayList<>();
    Connection_SQL connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger_detail);
        anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Hamburger burger = (Hamburger) bundle.getSerializable("burger");
        tvTenSanPham.setText(burger.getTen());
        tvMoTaNgan.setText(burger.getMoTaNgan());
        tvMoTaChiTiet.setText(burger.getMoTaChiTiet());
        tvGiaTien.setText(""+burger.getGiaTien());
        ten=burger.getTen();
        moTaNgan=burger.getMoTaNgan();
        idHamburger=burger.getId();
        soLuong=burger.getSoLuong();
        moTaChiTiet=burger.getMoTaChiTiet();
        hinhAnh=burger.getHinhAnh();
        giaTien=burger.getGiaTien();
        // Chuỗi đường dẫn base64 dạng string
        String base64String = burger.getHinhAnh();
        // Chuyển đổi đường dẫn base64 thành Bitmap
        byte[] decodedString = Base64.decode(base64String.substring(base64String.indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // Hiển thị Bitmap trong ImageView
        imgBurger.setImageBitmap(bitmap);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BurgerDetailActivity.this,BurgerActivity.class));
                finish();
            }
        });

        // xu li logic tang giam so luong
        tvTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(tvSoLuong.getText().toString());
                sl = sl +1;
                tvSoLuong.setText(""+sl);

            }
        });
        tvGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(tvSoLuong.getText().toString())>1){
                    int sl = Integer.parseInt(tvSoLuong.getText().toString());
                    sl = sl - 1;
                    tvSoLuong.setText(""+sl);
                }else{
                    return;
                }
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart();
            }
        });
    }



//    public Connection connectionSQLSever (){
//        Connection connection = null;
//        try{
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
//            String connectString = "jdbc:jtds:sqlserver://192.168.0.102:1433;databaseName=YummyApp;user=sa;password=Password.1";
//            connection = DriverManager.getConnection(connectString);
//            Log.i("Thong bao", "Ket noi thanh cong den sql server su dung com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return connection;
//    }

    private void addToCart() {
        Users user = UserSession.getCurrentUser();
        int userId = user.getIdUser();
        String query = "INSERT INTO DANHSACHSANPHAM (IDUSERS, IDHAMBURGER , MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = new Connection_SQL();
            PreparedStatement stmt = connection.SQLconnection().prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, idHamburger);
            stmt.setInt(3, CartActivity.maDssp);
            stmt.setString(4, ten);
            stmt.setInt(5, Integer.parseInt(tvSoLuong.getText().toString()));
            stmt.setDouble(6, giaTien);
            stmt.setString(7,hinhAnh);
            stmt.executeUpdate();
            stmt.close();
            connection.SQLconnection().close();
            Toast.makeText(this, "Them vao gio hang thanh cong", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showCart() {
        Intent intent = new Intent(BurgerDetailActivity.this,CartActivity.class);
        startActivity(intent);
        finish();
    }

    private void anhxa() {
        btnback=findViewById(R.id.btnBurgerDetailBack);
        btnCard=findViewById(R.id.btnCartBurgerDetail);
        imgBurger=findViewById(R.id.anhSanPhamBurgerDetail);
        tvTenSanPham=findViewById(R.id.tvTenSanPhamBurgerDetail);
        tvMoTaNgan=findViewById(R.id.tvMotaNganBurgerDetail);
        tvMoTaChiTiet=findViewById(R.id.tvMoTaChiTietBurgerDetail);
        tvGiaTien=findViewById(R.id.tvGiaTienBurgerDetail);
        btnAddToCart= findViewById(R.id.btnThemVaoGioHangBurgerDetail);
        tvTang=findViewById(R.id.tvTang);
        tvGiam=findViewById(R.id.tvGiam);
        tvSoLuong=findViewById(R.id.tvSoLuongBurgerDetail);

    }

}