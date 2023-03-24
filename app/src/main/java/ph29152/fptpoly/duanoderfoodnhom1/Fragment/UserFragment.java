package ph29152.fptpoly.duanoderfoodnhom1.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import ph29152.fptpoly.duanoderfoodnhom1.Model.UserSession;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;


public class UserFragment extends Fragment {
    private TextView tvNameUser,tvPhoneUser,tvPasswordUser,tvEmailUser,tvAddressUser,tvViTien;  //khai báo các view
    private ImageView imgAvatarUser;
    private Button btnUpdateUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNameUser=view.findViewById(R.id.tvNameUser);                      //ánh xạ các view khai báo
        tvPhoneUser=view.findViewById(R.id.tvPhoneUser);
        tvPasswordUser=view.findViewById(R.id.tvPasswordUser);
        tvEmailUser=view.findViewById(R.id.tvEmailUser);
        tvAddressUser=view.findViewById(R.id.tvAddressUser);
        tvViTien=view.findViewById(R.id.tvViTien);
        imgAvatarUser=view.findViewById(R.id.imgAvatarUser);
        btnUpdateUser=view.findViewById(R.id.btnUpdateUser);                 //end ánh xạ

        Users users = UserSession.getCurrentUser();                     //lấy dto user hiện tại từ dto user
        tvNameUser.setText("Tên người dùng: "+users.getTen());          //setText cho các view lấy từ dto hiện tại khai báo ở trên
        tvPhoneUser.setText("Số điện thoại: "+users.getSoDienThoai());
        tvPasswordUser.setText("Mật Khẩu: "+users.getMatKhau());
        tvEmailUser.setText("Email: "+users.getEmail());
        tvAddressUser.setText("Địa Chỉ: "+users.getDiaChi());
        tvViTien.setText("Số dư ví: $"+users.getViTien());
        tvPhoneUser.setText(users.getSoDienThoai());                    //end setText
        if(users.getHinhAnh()!=null){                                   //set logic nếu tốn tại thì set Imgview lên
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGBA_F16;            //set cấu hình bitmap
            opts.inMutable = true;
            byte[] decodedString = Base64.decode(users.getHinhAnh(), Base64.DEFAULT);       //lấy từ hình ảnh từ dto user về dạng byte[]
            Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,opts);       // rồi từ byte[] nén sang bitmap đồ họa
            myBitmap.setHasAlpha(true);
            imgAvatarUser.setImageBitmap(myBitmap);                     //set imgview được ánh xạ ở vào bitmap
        }else{
            imgAvatarUser.setImageResource(R.drawable.imgadmin);          //nếu không tồn tại thì set là imgadmin;
        }

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
