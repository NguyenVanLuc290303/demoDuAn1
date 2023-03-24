package ph29152.fptpoly.duanoderfoodnhom1.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Activity.CartActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.ListProductCart;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Notification;
import ph29152.fptpoly.duanoderfoodnhom1.Model.PayBill;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder>{
    private List<PayBill> list = new ArrayList<>();
    private Context context;
    Connection_SQL connection;

    public NewOrderAdapter(List<PayBill> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_order,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PayBill item = list.get(position);
        final int a = position;
        holder.tvThoiGian.setText(""+item.getNgayDat());
        holder.tvTongTien.setText(""+item.getTongTien());
        holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Bạn có chắc chắn muốn xác nhận đơn này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenUsers= "";
                        PayBill payBill = list.get(a);
                        try {
                            connection = new Connection_SQL();
                            String sql2 = "SELECT TEN FROM USERS WHERE IDUSERS = ?";
                            PreparedStatement stm = connection.SQLconnection().prepareStatement(sql2);
                            stm.setInt(1,payBill.getIdUsers());
                            ResultSet rs = stm.executeQuery();
                            if(rs.next()){
                                tenUsers = rs.getString("TEN");
                            }
                            String sqll = "INSERT INTO HOADON (IDTHANHTOAN,IDUSERS,TENUSERS,TONGTIEN,TRANGTHAI,NGAYXUAT) VALUES (?, ?, ?, ?, ?, ?)";
                            PreparedStatement sttm = connection.SQLconnection().prepareStatement(sqll);
                            sttm.setInt(1, payBill.getIdThanhToan());
                            sttm.setInt(2, payBill.getIdUsers());
                            sttm.setString(3, tenUsers);
                            sttm.setDouble(4, payBill.getTongTien());
                            sttm.setInt(5, payBill.getTrangThai()+1);
                            sttm.setString(6, payBill.getNgayDat());
                            int rowsUpdated = sttm.executeUpdate();
                            if (rowsUpdated > 0) {
                                // xu li xoa du lieu trong thanh toan
                                Toast.makeText(context, "Xac nhan thanh cong", Toast.LENGTH_SHORT).show();
                                // sau khi thanh toan thanh cong
//                                    Connection_SQL c = new Connection_SQL();
//                                    String sql = "SELECT * FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
//                                    PreparedStatement statement = c.SQLconnection().prepareStatement(sql);
//                                    statement.setInt(1, payBill.getIdUsers());
//                                    ResultSet rls = statement.executeQuery();
//                                    while (rls.next()) {
//                                        int idhamburger = rls.getInt("IDHAMBURGER");
//                                        int soLuong = rls.getInt("SOLUONG");
//                                        String capnhatbanra = "UPDATE HAMBURGER " +
//                                                "SET BANRA = BANRA + ? " +
//                                                "WHERE IDHAMBURGER = ?";
//                                        PreparedStatement pst = connection.SQLconnection().prepareStatement(capnhatbanra);
//                                        pst.setInt(1,soLuong);
//                                        pst.setInt(2,idhamburger);
//                                        ResultSet rss = stm.executeQuery();
//                                        if(rss.next()){
//                                            Log.e("UpdateBanRa", "Update so luong ban ra thanh cong");
//                                        }
//                                    }
                                // thuc hien xoa khoi list torng database
                                Connection_SQL connection1 = new Connection_SQL();
                                String sqlDelete = "DELETE FROM THANHTOAN WHERE IDTHANHTOAN = ?";
                                PreparedStatement p = connection1.SQLconnection().prepareStatement(sqlDelete);
                                p.setInt(1, payBill.getIdThanhToan());
                                int rsDelete = p.executeUpdate();
                                if(rsDelete >0 ){
                                    Toast.makeText(context, "Xoa trong SQL thanh cong", Toast.LENGTH_SHORT).show();
                                }
                                // xu li thong bao
                                Connection_SQL connect = new Connection_SQL();
                                String sqlThongBao = "INSERT INTO THONGBAO (IDTHONGBAO,IDUSERS,TRANGTHAI,NOIDUNG,TONGTIEN) VALUES (?, ?, ?, ?, ?)";
                                PreparedStatement pst = connect.SQLconnection().prepareStatement(sqlThongBao);
                                pst.setInt(1,payBill.getIdThanhToan());
                                pst.setInt(2,payBill.getIdUsers());
                                pst.setInt(3,1);
                                pst.setString(4,"Đơn hàng của bạn đã được xác nhận!");
                                pst.setDouble(5,payBill.getTongTien());
                                int rsThongBao1 = pst.executeUpdate();
                                if(rsThongBao1 > 0){
                                    Connection_SQL connectionn = new Connection_SQL();
                                    String sqlSelect = "SELECT * FROM THONGBAO WHERE IDTHONGBAO = ?";
                                    PreparedStatement ptm = connectionn.SQLconnection().prepareStatement(sqlSelect);
                                    ptm.setInt(1,payBill.getIdThanhToan());
//                                    ptm.setInt(2,payBill.getIdUsers());
                                    ResultSet rsSelect = ptm.executeQuery();
                                    if(rsSelect.next()){
                                        int idThongBao = rsSelect.getInt("IDTHONGBAO");
                                        int idU = rsSelect.getInt("IDUSERS");
                                        String noidungg = rsSelect.getString("NOIDUNG");
                                        double tongt = rsSelect.getDouble("TONGTIEN");
                                        String stringIdthongbao = String.valueOf(idThongBao);
                                        DatabaseReference rf = FirebaseDatabase.getInstance().getReference("ThongBao").child(String.valueOf(idU));
                                        if(stringIdthongbao!=null){
                                            Notification notification = new Notification(idThongBao,idU,1,noidungg,tongt, payBill.getNgayDat());
                                            rf.child(stringIdthongbao).setValue(notification);
                                        }else{
                                            Log.e("Loi thong bao", "id thong bao khong ton tai");
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(context, "Xac nhan that bai", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        list.remove(a);
                        notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        holder.btnHuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Bạn có chắc chắn muốn huỷ đơn ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PayBill payBill = list.get(a);
                        Dialog dialog1 = new Dialog(context);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setContentView(R.layout.dialog_lido);
                        Window window = dialog1.getWindow();
                        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        WindowManager.LayoutParams winLayoutParams = window.getAttributes();
                        winLayoutParams.gravity = Gravity.CENTER;
                        window.setAttributes(winLayoutParams);
                        TextInputLayout edLiDo;
                        Button btnXacNhanHuyDon;
                        edLiDo=dialog1.findViewById(R.id.edLiDoHuy);
                        btnXacNhanHuyDon=dialog1.findViewById(R.id.btnXacNhanHuyDon);
                        btnXacNhanHuyDon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String lido = edLiDo.getEditText().getText().toString().trim();
                                if(lido==null){
                                    edLiDo.setError("Vui lòng điền lí do huỷ cho khách hàng");
                                }else{
                                    edLiDo.setError(null);
                                    try{
                                        connection = new Connection_SQL();
                                        String sql2 = "SELECT TEN FROM USERS WHERE IDUSERS = ?";
                                        PreparedStatement stm = connection.SQLconnection().prepareStatement(sql2);
                                        stm.setInt(1,payBill.getIdUsers());
                                        ResultSet rs = stm.executeQuery();
                                        String tenUsers = "";
                                        if(rs.next()){
                                            tenUsers = rs.getString("TEN");
                                        }
                                        String sqll = "INSERT INTO HOADON (IDTHANHTOAN,IDUSERS,TENUSERS,TONGTIEN,TRANGTHAI,NGAYXUAT) VALUES (?, ?, ?, ?, ?, ?)";
                                        PreparedStatement sttm = connection.SQLconnection().prepareStatement(sqll);
                                        sttm.setInt(1, payBill.getIdThanhToan());
                                        sttm.setInt(2, payBill.getIdUsers());
                                        sttm.setString(3, tenUsers);
                                        sttm.setDouble(4, payBill.getTongTien());
                                        sttm.setInt(5, payBill.getTrangThai()+2);
                                        int test = payBill.getTrangThai()+2;
                                        Log.e("TAG", "test = " + test);
                                        sttm.setString(6, payBill.getNgayDat());
                                        int rowsUpdated = sttm.executeUpdate();
                                        if (rowsUpdated > 0) {
                                            // xu li xoa du lieu trong thanh toan
                                            Toast.makeText(context, "Xac nhan huy thanh cong", Toast.LENGTH_SHORT).show();
                                            // thuc hien xoa khoi list torng database
                                            Connection_SQL connection1 = new Connection_SQL();
                                            String sqlDelete = "DELETE FROM THANHTOAN WHERE IDTHANHTOAN = ?";
                                            PreparedStatement p = connection1.SQLconnection().prepareStatement(sqlDelete);
                                            p.setInt(1, payBill.getIdThanhToan());
                                            int rsDelete = p.executeUpdate();
                                            if (rsDelete > 0) {
                                                Toast.makeText(context, "Xoa trong SQL thanh cong", Toast.LENGTH_SHORT).show();
                                            }

                                            //xu li thong bao
                                            Connection_SQL connect = new Connection_SQL();
                                            String sqlThongBao = "INSERT INTO THONGBAO (IDTHONGBAO,IDUSERS,TRANGTHAI,NOIDUNG,TONGTIEN) VALUES (?, ?, ?, ?, ?)";
                                            PreparedStatement pst = connect.SQLconnection().prepareStatement(sqlThongBao);
                                            pst.setInt(1, payBill.getIdThanhToan());
                                            pst.setInt(2, payBill.getIdUsers());
                                            pst.setInt(3, 2);
                                            pst.setString(4, "Đơn hàng của bạn đã bị huỷ, lí do : " + lido + "\nSố tiền đơn này sẽ được hoàn lại vào ví tiền");
                                            pst.setDouble(5, payBill.getTongTien());
                                            int rsThongBao1 = pst.executeUpdate();
                                            if (rsThongBao1 > 0) {
                                                Connection_SQL connectionn = new Connection_SQL();
                                                String sqlSelect = "SELECT * FROM THONGBAO WHERE IDTHONGBAO = ?";
                                                PreparedStatement ptm = connectionn.SQLconnection().prepareStatement(sqlSelect);
                                                ptm.setInt(1, payBill.getIdThanhToan());
//                                            ptm.setInt(2, payBill.getIdUsers());
                                                ResultSet rsSelect = ptm.executeQuery();
                                                if (rsSelect.next()) {
                                                    int idThongBao = rsSelect.getInt("IDTHONGBAO");
                                                    int idU = rsSelect.getInt("IDUSERS");
                                                    String noidungg = rsSelect.getString("NOIDUNG");
                                                    double tongt = rsSelect.getDouble("TONGTIEN");
                                                    String stringIdthongbao = String.valueOf(idThongBao);
                                                    DatabaseReference rf = FirebaseDatabase.getInstance().getReference("ThongBao").child(String.valueOf(idU));
                                                    if (stringIdthongbao != null) {
                                                        Notification notification = new Notification(idThongBao, idU, 2, noidungg, tongt, payBill.getNgayDat());
                                                        rf.child(stringIdthongbao).setValue(notification);
                                                    } else {
                                                        Log.e("Loi thong bao", "id thong bao khong ton tai");
                                                    }

                                                }


                                            }
                                        }else{
                                            Toast.makeText(context, "Xac nhan that bai", Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    list.remove(a);
                                    notifyDataSetChanged();

                                }
                                dialog1.dismiss();
                            }

                        });
                        dialog1.show();


                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvThoiGian, tvTongTien;
        Button btnXacNhan,btnHuyDon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvThoiGian=itemView.findViewById(R.id.tvThoiGian);
            tvTongTien=itemView.findViewById(R.id.tvTongTien);
            btnXacNhan=itemView.findViewById(R.id.btnXacNhan);
            btnHuyDon= itemView.findViewById(R.id.btnHuyDonhang);
        }
    }

}
