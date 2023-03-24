package ph29152.fptpoly.duanoderfoodnhom1.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Hamburger;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class AddNewProductActivity extends AppCompatActivity {
    private TextInputLayout edTenSP, edGiaTien, edSoLuong, edMoTaNgan, edMoTaChiTiet;
    private Button btnSelectImage, btnAddNew;
    private ImageView imgSanPham;
    private List<Hamburger> list = new ArrayList<>();
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private String encodedImage;
    Connection_SQL connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        anhxa();

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNew();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgSanPham.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void addNew() {
        String tenSP = edTenSP.getEditText().getText().toString().trim();
        String giaTien = edGiaTien.getEditText().getText().toString().trim();
        String soLuong = edSoLuong.getEditText().getText().toString().trim();
        String moTaNgan = edMoTaNgan.getEditText().getText().toString().trim();
        String moTaChiTiet = edMoTaChiTiet.getEditText().getText().toString().trim();
        if(bitmap == null) {
            Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }if(tenSP.isEmpty()||giaTien.isEmpty()||soLuong.isEmpty()||moTaNgan.isEmpty()||moTaChiTiet.isEmpty()){
            Toast.makeText(this, "Vui long nhap day du cac truong", Toast.LENGTH_SHORT).show();
            return;
        }if(Double.parseDouble(giaTien)<=0){
            Toast.makeText(this, "Gia tien phai lon hon 0", Toast.LENGTH_SHORT).show();
            return;
        }if(Integer.parseInt(soLuong)<=0){
            Toast.makeText(this, "So Luong phai lon hon 0", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        try {
            connection = new Connection_SQL();
            String sql = "INSERT INTO HAMBURGER (TEN, MOTANGAN,MOTACHITIET,GIATIEN,SOLUONG,HINHANH, DABAN) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setString(1, tenSP);
            statement.setString(2, moTaNgan);
            statement.setString(3, moTaChiTiet);
            statement.setDouble(4, Double.parseDouble(giaTien));
            statement.setInt(5, Integer.parseInt(soLuong));
            statement.setString(6, encodedImage);
            statement.setInt(7,0);
            int rowInserted = statement.executeUpdate();
            if (rowInserted==1){
                Toast.makeText(getApplicationContext(), "Save to DB Success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "them khong thanh cong", Toast.LENGTH_SHORT).show();
            }
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "them that bai", Toast.LENGTH_SHORT).show();
        }
    }

    private void anhxa() {
        edTenSP=findViewById(R.id.edTenSanPham);
        edGiaTien=findViewById(R.id.edGiaTien);
        edSoLuong=findViewById(R.id.edSoLuong);
        edMoTaNgan=findViewById(R.id.edMoTaNgan);
        edMoTaChiTiet=findViewById(R.id.edMoTaChiTiet);
        btnSelectImage=findViewById(R.id.btnSelectImage);
        btnAddNew=findViewById(R.id.btnAddNew);
        imgSanPham=findViewById(R.id.imgSanPham);
    }


}