package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity{

    EditText user;
    EditText pass;
    CardView btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        btn = (CardView) findViewById(R.id.cardView);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private void checkLogin(){
        String userid= user.getText().toString().trim();
        String password = pass.getText().toString().trim();


        if(Objects.equals(userid, "adviser")){
            if(Objects.equals(password, "1234")){
                startActivity(new Intent(Login.this, UploadPage.class));
            }
            else{
                Toast.makeText(this, "กรอกรหัสผ่านผิดผลาด", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "ชื่อผู้ใช้หรือรหัสผ่าน ผิดผลาด ", Toast.LENGTH_SHORT).show();
        }
    }

}
