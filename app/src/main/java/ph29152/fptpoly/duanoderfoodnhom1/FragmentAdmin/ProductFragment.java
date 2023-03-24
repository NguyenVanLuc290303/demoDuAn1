package ph29152.fptpoly.duanoderfoodnhom1.FragmentAdmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Activity.Admin.AddNewProductActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Adapter.ProductAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Hamburger;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class ProductFragment extends Fragment {
    private ProductAdapter adapter;
    private List<Hamburger> list ;
    CardView btnThemMoiSanPham;
    Connection_SQL connection;
    RecyclerView rcv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,container,false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnThemMoiSanPham=view.findViewById(R.id.btnThemMoiSanPham);
        rcv=view.findViewById(R.id.rcvListProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setSpanCount(3);
        list=new ArrayList<>();
        getAllListProduct();
        adapter=new ProductAdapter(list,getContext());
        adapter.notifyDataSetChanged();
        rcv.setAdapter(adapter);

        btnThemMoiSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddNewProductActivity.class);
                startActivity(intent);
            }
        });

    }

    public List<Hamburger> getAllListProduct() {

        try {
            connection = new Connection_SQL();
            String sql = "SELECT * FROM HAMBURGER";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDHAMBURGER");
                String ten = resultSet.getString("TEN");
                String moTaNgan = resultSet.getString("MOTANGAN");
                String moTaChiTiet = resultSet.getString("MOTACHITIET");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("HINHANH");
                Hamburger hamburger = new Hamburger(id,ten,moTaNgan,moTaChiTiet,soLuong,giaTien,hinhAnh);
                list.add(hamburger);
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
