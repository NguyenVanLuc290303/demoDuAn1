package ph29152.fptpoly.duanoderfoodnhom1.FragmentAdmin;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Adapter.CartAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Adapter.ProductAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.AdminAdapter.RevenueStatisticAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Hamburger;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Invoice;
import ph29152.fptpoly.duanoderfoodnhom1.Model.ListProductCart;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class RevenueStatisticFragment extends Fragment {
    RecyclerView rcv;
    RevenueStatisticAdapter adapter;
    List<Invoice> list =new ArrayList<>();
    Connection_SQL connection;
    TextView tvDoanhThu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_revenue_statistic,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv=view.findViewById(R.id.rcvInvoice);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllListInvoice();
        adapter = new RevenueStatisticAdapter(list,getContext());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        try{
            connection = new Connection_SQL();
            String sqlDoanhThu = "SELECT SUM(TONGTIEN) as DOANHTHU FROM HOADON WHERE TRANGTHAI = ?";
            PreparedStatement stm = connection.SQLconnection().prepareStatement(sqlDoanhThu);
            stm.setInt(1,2);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                double doanhThu = rs.getDouble("DOANHTHU");
                tvDoanhThu.setText("Doanh Thu : "+String.valueOf(doanhThu));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public List<Invoice> getAllListInvoice() {
        try {
            connection = new Connection_SQL();
            String sql = "SELECT * FROM HOADON ORDER BY IDHOADON DESC";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idhoadon = resultSet.getInt("IDHOADON");
                int idthanhtoan = resultSet.getInt("IDTHANHTOAN");
                int idUserrr = resultSet.getInt("IDUSERS");
                String tenUserrr = resultSet.getString("TENUSERS");
                double tongTien = resultSet.getDouble("TONGTIEN");
                int trangthai = resultSet.getInt("TRANGTHAI");
                String ngayXuat = resultSet.getString("NGAYXUAT");
                Invoice invoice = new Invoice(idhoadon,idthanhtoan,idUserrr,trangthai,tenUserrr,ngayXuat,tongTien);
                list.add(invoice);
            }

            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
