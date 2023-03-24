package ph29152.fptpoly.duanoderfoodnhom1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Model.Notification;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    private Context context;
    private List<Notification> list = new ArrayList<>();

    public NotificationAdapter(Context context, List<Notification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification notification = list.get(position);
        holder.tvMaThongBao.setText(notification.getMaThongBao());
        holder.tvTongTienThongBao.setText(""+notification.getTongTien());
        holder.tvNoiDung.setText(notification.getNoiDung());
        if(notification.getTrangThai()==1){
            holder.cardThongBao.setBackground(null);
            holder.cardThongBao.setBackgroundColor(Color.GREEN);
        }else{
            holder.cardThongBao.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaThongBao, tvNoiDung, tvTongTienThongBao;
        CardView cardThongBao;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaThongBao=itemView.findViewById(R.id.tvMaThongBao);
            tvNoiDung=itemView.findViewById(R.id.tvNoiDung);
            tvTongTienThongBao=itemView.findViewById(R.id.tvTongTienThongBao);
            cardThongBao=itemView.findViewById(R.id.cardThongBao);
        }
    }
}
