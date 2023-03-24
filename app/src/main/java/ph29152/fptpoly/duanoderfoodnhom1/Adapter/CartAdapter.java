package ph29152.fptpoly.duanoderfoodnhom1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
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
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Activity.CartActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.CartManager;
import ph29152.fptpoly.duanoderfoodnhom1.Model.ListProductCart;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    List<ListProductCart> list = new ArrayList<>();
    Context context ;
    static double total = 0;
    Connection_SQL connection;


    public CartAdapter(List<ListProductCart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ListProductCart cart = list.get(position);
        holder.tvTenSanPhamCart.setText(cart.getTenHamburger());
        holder.tvSoLuongCart.setText(""+cart.getSoLuong());
        holder.tvGiaTienCart.setText(""+cart.getGiaTien());
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;

        byte[] decodedString = Base64.decode(cart.getAnhSanPham(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,opts);
        myBitmap.setHasAlpha(true);
        holder.anhSanPhamCart.setImageBitmap(myBitmap);
        holder.cardCong.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                    @Override
                    public void onClick(View v) {
                        int sl = Integer.parseInt(holder.tvSoLuongCart.getText().toString());
                        sl = sl + 1;
                        holder.tvSoLuongCart.setText(""+sl);
                        try {
                            connection = new Connection_SQL();
                            Users users = UserSession.getCurrentUser();
                            int userID = users.getIdUser();
                            String sqll = "UPDATE DANHSACHSANPHAM " +
                                    "SET SOLUONG = SOLUONG + 1 " +
                                    "WHERE TENHAMBURGER = ? AND IDUSERS = ?;";
                            PreparedStatement sttm = connection.SQLconnection().prepareStatement(sqll);
                            sttm.setString(1, cart.getTenHamburger());
                            sttm.setInt(2, userID);
                            int rowsUpdated = sttm.executeUpdate();
                            if (rowsUpdated > 0) {

                                for (ListProductCart item : list) {
                                    total += item.getGiaTien() * item.getSoLuong();
                                }
                                CartActivity.tvTongTien.setText("" + total);
                            } else {
                                Toast.makeText(context, "Cập nhật số lượng sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });



        holder.cardTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.tvSoLuongCart.getText().toString())>1) {
                    int sl = Integer.parseInt(holder.tvSoLuongCart.getText().toString());
                    sl = sl - 1;
                    holder.tvSoLuongCart.setText("" + sl);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView anhSanPhamCart;
        TextView tvTenSanPhamCart,tvSoLuongCart,tvGiaTienCart;
        CardView cardTru,cardCong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhSanPhamCart=itemView.findViewById(R.id.anhSanPhamCart);
            tvTenSanPhamCart=itemView.findViewById(R.id.tvTenSanPhamCart);
            tvGiaTienCart=itemView.findViewById(R.id.tvGiaTienCart);
            tvSoLuongCart=itemView.findViewById(R.id.tvSoLuongCart);
            cardTru=itemView.findViewById(R.id.cardTru);
            cardCong=itemView.findViewById(R.id.cardCong);
        }
    }

//    public Connection connectionSQLSever (){
//        Connection connection = null;
//        try{
//            //khai bao che do ket noi muc dich la de lay quyen ket noi
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);// thiet lap chinh sach ket noi bao gom tat ca cac quyen
//            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();//đăng ký driver JDBC và kết nối tới cơ sở dữ liệu
//            String connectString = "jdbc:jtds:sqlserver://192.168.0.102:1433;databaseName=YummyApp;user=sa;password=Password.1";
//            connection = DriverManager.getConnection(connectString);
//            Log.i("Thong bao", "Ket noi thanh cong den sql server su dung com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return connection;
//    }
}
