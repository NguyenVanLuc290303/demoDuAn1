package ph29152.fptpoly.duanoderfoodnhom1.FragmentAdmin;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import ph29152.fptpoly.duanoderfoodnhom1.Adapter.NewOrderAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.ListProductCart;
import ph29152.fptpoly.duanoderfoodnhom1.Model.PayBill;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class NewOrder extends Fragment {
    RecyclerView rcv;
    NewOrderAdapter adapter;
    List<PayBill> list =new ArrayList<>();
    Connection_SQL connection;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_order,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv=view.findViewById(R.id.rcvNewOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllListBill();
        adapter = new NewOrderAdapter(list,getContext());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public List<PayBill> getAllListBill() {

        try {
            connection = new Connection_SQL();
            String sql = "SELECT * FROM THANHTOAN";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("IDTHANHTOAN");
                int idusers = resultSet.getInt("IDUSERS");
                int madanhsachsanpham = resultSet.getInt("MADANHSACHSANPHAM");
                double tongtien = resultSet.getDouble("TONGTIEN");
                int trangthai = resultSet.getInt("TRANGTHAI");
                String ngaydat = resultSet.getString("NGAYDAT");
                String tthai = String.valueOf(trangthai);
                if(tthai == null){
                    trangthai = 1;
                }
                PayBill bill = new PayBill(id,idusers,madanhsachsanpham,tongtien,trangthai,ngaydat);
                list.add(bill);

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
