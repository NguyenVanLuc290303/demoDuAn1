package ph29152.fptpoly.duanoderfoodnhom1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Activity.Admin.ProductDetailActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Activity.BurgerDetailActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.MainActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Hamburger;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    List<Hamburger> list = new ArrayList<>();
    Context context;
    List<Users> listUser=new ArrayList<>();
    Connection_SQL connection;

    public ProductAdapter(List<Hamburger> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_burger,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Hamburger burger = list.get(position);
        holder.tvTenSanPham.setText(burger.getTen());
        holder.tvGiaTien.setText(""+burger.getGiaTien());
        holder.tvMoTaNgan.setText(burger.getMoTaNgan());
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(burger.getHinhAnh(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,opts);
        myBitmap.setHasAlpha(true);
        holder.imgBurger.setImageBitmap(myBitmap);
        try {
            connection = new Connection_SQL();
            String query = "SELECT QUYEN FROM Users";
            PreparedStatement stmt = null;
            try {
                stmt = connection.SQLconnection().prepareStatement(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs.next()) {
                    int quyen = 0;
                    try {
                        quyen = rs.getInt("QUYEN");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (quyen == 0) {
                        holder.cardViewList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context.getApplicationContext(), ProductDetailActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    } else {
                        holder.cardViewList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }
                } else {
                    Toast.makeText(context.getApplicationContext(), "Toang", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.constraintItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), BurgerDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("burger",burger);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSanPham, tvMoTaNgan,tvGiaTien;
        ImageView imgBurger;
        CardView cardViewList;
        ConstraintLayout constraintItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGiaTien=itemView.findViewById(R.id.tvGiaTienact);
            tvMoTaNgan=itemView.findViewById(R.id.tvMotaNganact);
            tvTenSanPham=itemView.findViewById(R.id.tvTenSPact);
            imgBurger=itemView.findViewById(R.id.imgBurger);
            cardViewList =itemView.findViewById(R.id.btnThemVaoGioact);
            constraintItem = itemView.findViewById(R.id.constraintItem);

        }
    }

//    public Connection connectionSQLSever (){
//        Connection connection = null;
//        try{
//            //khai bao che do ket noi muc dich la de lay quyen ket noi
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);// thiet lap chinh sach ket noi bao gom tat ca cac quyen
//            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();//đăng ký driver JDBC và kết nối tới cơ sở dữ liệu
//            String connectString = "jdbc:jtds:sqlserver://192.168.0.102;databaseName=YummyApp;user=sa;password=Password.1";
//            connection = DriverManager.getConnection(connectString);
//            Log.i("Thong bao", "Ket noi thanh cong den sql server su dung com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return connection;
//    }


}
