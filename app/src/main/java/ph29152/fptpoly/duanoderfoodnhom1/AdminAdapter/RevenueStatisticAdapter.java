package ph29152.fptpoly.duanoderfoodnhom1.AdminAdapter;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Adapter.CartAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Hamburger;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Invoice;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class RevenueStatisticAdapter extends RecyclerView.Adapter<RevenueStatisticAdapter.MyViewHolder>{
    List<Invoice> list = new ArrayList<>();
    Context context ;
    Connection_SQL connection;

    public RevenueStatisticAdapter(List<Invoice> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revenue_statistic,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Invoice item = list.get(position);
        holder.tvTenRevenue.setText(""+item.getTenUsers());
        holder.tvDiaChiRevenue.setText("ngai lam");
        holder.tvThoiGianRevenue.setText(""+item.getNgayXuat());
        holder.tvSoDienThoaiRevenue.setText("Cuxng ngai not");
        holder.tvTrangThaiRevenue.setText(""+item.getTrangThai());
        holder.tvTongTienRevenue.setText(""+item.getTongTien());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }//adapter san pham da ban => thong ke doanh thu

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvThoiGianRevenue,tvTenRevenue,tvDiaChiRevenue,tvSoDienThoaiRevenue, tvTongTienRevenue, tvTrangThaiRevenue;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvThoiGianRevenue=itemView.findViewById(R.id.tvThoiGianRevenue);
            tvTenRevenue=itemView.findViewById(R.id.tvTenUserRevenue);
            tvDiaChiRevenue=itemView.findViewById(R.id.tvDiaChiRevenue);
            tvSoDienThoaiRevenue=itemView.findViewById(R.id.tvSoDienThoaiRevenue);
            tvTongTienRevenue=itemView.findViewById(R.id.tvTongTienRevenue);
            tvTrangThaiRevenue=itemView.findViewById(R.id.tvTrangThaiRevenue);

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
