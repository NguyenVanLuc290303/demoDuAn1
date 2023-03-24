package ph29152.fptpoly.duanoderfoodnhom1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ph29152.fptpoly.duanoderfoodnhom1.MainActivity;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class PaySuccess extends AppCompatActivity {
    Button btnBackToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        btnBackToMain=findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaySuccess.this, MainActivity.class));
                finish();
            }
        });
    }
}