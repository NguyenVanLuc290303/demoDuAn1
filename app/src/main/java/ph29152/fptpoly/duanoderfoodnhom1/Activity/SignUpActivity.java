package ph29152.fptpoly.duanoderfoodnhom1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class SignUpActivity extends AppCompatActivity {
    String TAG = "zzz";

    CardView btn_signup;
    TextInputLayout til_username,til_password,til_phone,til_address,til_email;
    ImageView img_signup;
    Connection_SQL connection_sql;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        til_username = findViewById(R.id.layUserName_signup);
        til_password = findViewById(R.id.layPassword_signup);
        til_phone = findViewById(R.id.layPhoneNumber);
        til_address = findViewById(R.id.layDiaChi);
        til_email = findViewById(R.id.layEmail);
        btn_signup = findViewById(R.id.cardSignup);
        firebaseAuth = FirebaseAuth.getInstance();              //gọi firebase auth



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupFirebaseAuth();
                Signup();
            }
        });
    }

    private void SignupFirebaseAuth() {
        String phone = til_phone.getEditText().getText().toString().trim();
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone)
                .setActivity(this)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        SignupWithAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        SendOTPtoPhone(phone,s);
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }

    private void SendOTPtoPhone(String phone, String s) {
        Intent intent_OPT = new Intent(SignUpActivity.this,OTPActivity.class);
        intent_OPT.putExtra("phone",phone);
        intent_OPT.putExtra("id",s);
        startActivity(intent_OPT);

    }

    private void SignupWithAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
//                            gotoMainActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SignUpActivity.this, "manhinhphone code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void Signup() {
        connection_sql = new Connection_SQL();
        String username = til_username.getEditText().getText().toString().trim();
        String password = til_password.getEditText().getText().toString().trim();
        String phone = til_phone.getEditText().getText().toString().trim();
        String address = til_address.getEditText().getText().toString().trim();
        String email = til_email.getEditText().getText().toString().trim();


        Users users = new Users();
        users.setTen(username);
        users.setMatKhau(password);
        users.setSoDienThoai(phone);
        users.setDiaChi(address);
        users.setEmail(email);



        String sql_signup = "insert into USERS(TEN,EMAIL,SODIENTHOAI,DIACHI,MATKHAU,QUYEN) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(sql_signup);
            statement.setString(1,users.getTen());
            statement.setString(2,users.getEmail());
            statement.setString(3,users.getSoDienThoai());
            statement.setString(4,users.getDiaChi());
            statement.setString(5,users.getMatKhau());
            statement.setInt(6,1);
            int check_insert = statement.executeUpdate();
            connection_sql.SQLconnection().close();
            if (check_insert ==1){
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                Intent intent_Login = new Intent(SignUpActivity.this,OTPActivity.class);
//                startActivity(intent_Login);
//                finish();
            }
            else {
                Toast.makeText(this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}