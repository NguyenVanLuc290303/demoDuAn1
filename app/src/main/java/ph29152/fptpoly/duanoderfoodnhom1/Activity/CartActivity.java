package ph29152.fptpoly.duanoderfoodnhom1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Adapter.CartAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.CartManager;
import ph29152.fptpoly.duanoderfoodnhom1.Model.ListProductCart;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class CartActivity extends AppCompatActivity{
    RecyclerView rcvCart;
    CartAdapter adapter;
    List<ListProductCart> list =new ArrayList<>();
    public static TextView tvTongTien;
    Button btnBuyNow;
    CardView btnBacktoBurgerDetail;
    int a= 0;
    public static int maDssp=0;
    Connection_SQL connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tvTongTien=findViewById(R.id.tvTongTienCart);
        btnBuyNow=findViewById(R.id.btnBuyNowCart);
        btnBacktoBurgerDetail=findViewById(R.id.btnBackInCart);
        rcvCart = findViewById(R.id.rcvCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllListCart();

        adapter = new CartAdapter(list,this);
        rcvCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tongTien();
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,PayBillActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnBacktoBurgerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,BurgerDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public List<ListProductCart> getAllListCart() {

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
                String tenhamburger = resultSet.getString("TENHAMBURGER");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("ANHSANPHAM");
                String maDanhSachSanPham = String.valueOf(resultSet.getInt("MADANHSACHSANPHAM"));
                if(maDanhSachSanPham==null){
                    maDanhSachSanPham = String.valueOf(a);
                    maDssp = Integer.parseInt(maDanhSachSanPham);
                }
                ListProductCart cart = new ListProductCart(id,maDssp,idusers,idhamburger,tenhamburger,hinhAnh,soLuong,giaTien);
                list.add(cart);

            }
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void tongTien(){
        try {
            connection = new Connection_SQL();
            Users user = UserSession.getCurrentUser();
            int userId = user.getIdUser();
            String sql = "SELECT SUM(GIATIEN * SOLUONG) as TONG FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                double tt = resultSet.getDouble("TONG");
                tvTongTien.setText(""+tt);
            }else{

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}