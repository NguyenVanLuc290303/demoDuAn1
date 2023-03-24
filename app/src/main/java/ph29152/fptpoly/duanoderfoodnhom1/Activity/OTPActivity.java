package ph29152.fptpoly.duanoderfoodnhom1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import ph29152.fptpoly.duanoderfoodnhom1.R;

public class OTPActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText ed_OTP;
    Button btn_addOTP;
    String mphoneNumber,mID;
    String TAG = "zzz";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        firebaseAuth = FirebaseAuth.getInstance();
        ed_OTP = findViewById(R.id.ed_OTP);
        btn_addOTP = findViewById(R.id.btn_enterOTP);
        DataIntent();
        btn_addOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enter_OTP = ed_OTP.getText().toString();
                onClickOTP(enter_OTP);
            }
        });
    }

    private void onClickOTP(String enter_otp) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mID,enter_otp);
        signInWithPhoneAuthCredential(phoneAuthCredential);
    }

    private void DataIntent() {
        mphoneNumber = getIntent().getStringExtra("phone");
        mID = getIntent().getStringExtra("id");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            Intent intent = new Intent(OTPActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTPActivity.this, "manhinhphone code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}