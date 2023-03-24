package ph29152.fptpoly.duanoderfoodnhom1.Fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Adapter.NotificationAdapter;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Notification;
import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class NotificationFragment extends Fragment {
    RecyclerView rcv;                   //khai báo view
    NotificationAdapter adapter;        //khai báo adapter
    List<Notification> list = new ArrayList<>();        //list<thông báo>
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcvNotification);                  //ánh xạ
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());    //set layoutManager cho recyclerView
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);                          //set hiện thị dọc hay ngang
        getListFromFireBase();                                          //gọi hàm lấy danh sách từ firebase
        adapter = new NotificationAdapter(getContext(),list);           //khởi tạo adapter
        rcv.setAdapter(adapter);                                        //set recyclerView cho adapter

    }


    private void getListFromFireBase() {
        Users user = UserSession.getCurrentUser();            //lấy user hiện tại từ class user
        int idUsers = user.getIdUser();                        //lấy ID user
        String stringIdUsers = String.valueOf(idUsers);         //ép kiểu cho IdUsers
        FirebaseDatabase database = FirebaseDatabase.getInstance();     //gọi realtime database
        DatabaseReference reference = database.getReference("ThongBao").child(stringIdUsers);   //add IDuser vào key ThongBao
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Notification notification = snapshot.getValue(Notification.class);      //đọc dữ liệu từ realtime base
                if(notification!=null){
                    list.add(notification);                                         //add dto vào list thông báo
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {    // hàm update thông báo
                Notification notification = snapshot.getValue(Notification.class);  //đọc dữ liệu realtime database
                if(list==null || list.isEmpty() || notification == null){           // nếu list trống hoặc list băng null hoặc thông báo bằng null thì return;
                    return;
                }
                for(int i = 0 ; i<list.size() ; i++ ){                              //tạo vòng lặp để duyệt list
                    if(notification.getIdThongBao()==list.get(i).getIdThongBao()){
                        list.set(i,notification);                                   //update vị trí list , dto
                        break;
                    }
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {            // hàm xóa thông báo
                Notification notification = snapshot.getValue(Notification.class);
                if(list==null || list.isEmpty() || notification == null){            // nếu list trống hoặc list băng null hoặc thông báo bằng null thì return;
                    return;
                }
                for (int i = 0 ; i < list.size() ; i++){                            //tạo vòng lặp để duyệt list
                    if(notification.getIdThongBao()==list.get(i).getIdThongBao()){
                        list.remove(list.get(i));                                      //xóa vị trí ở list
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
