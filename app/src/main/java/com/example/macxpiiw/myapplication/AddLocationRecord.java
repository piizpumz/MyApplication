package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddLocationRecord extends AppCompatActivity {

    private Spinner spinprovince;
    private EditText mlocationName , mmoo , mtumbon , mamphur , mprovince , mpostcode;
    private Button btnAddData;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_record);
        getSupportActionBar().setTitle("เพิ่มพื่นที่สำรวจ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mlocationName = (EditText)findViewById(R.id.addLocation_Name);
        mmoo = (EditText)findViewById(R.id.addMoo);
        mtumbon = (EditText)findViewById(R.id.addTumbon);
        mamphur = (EditText)findViewById(R.id.addAmphur);
        mpostcode = (EditText)findViewById(R.id.addPost_Code);
        btnAddData = (Button)findViewById(R.id.btn_addlocation);
        spinprovince = (Spinner)findViewById(R.id.spinner_province);


        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocaion();
            }
        });

    }

    private void saveLocaion(){
        Boolean Empty = true;
        String location_name = mlocationName.getText().toString().trim();
        String moo = mmoo.getText().toString().trim();
        String tumbon = mtumbon.getText().toString().trim();
        String amphur = mamphur.getText().toString().trim();
        String province = spinprovince.getSelectedItem().toString().trim();
        String postcode = mpostcode.getText().toString().trim();
        String status = null;


       dbHelper = new DBHelper(this);

        if(location_name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "กรุณากรอกชื่อหมู่บ้าน", Toast.LENGTH_SHORT).show();
            Empty = false;
        }

        if (Empty) {
            Location locaion = new Location(location_name, moo, tumbon, amphur, province, postcode , status);

            Boolean check = dbHelper.cheak_location(locaion);
            if(check) {
                dbHelper.saveNewLocation(locaion);
                finish();
            }
            else {
                Toast.makeText(this, "มีชื่อหมู่บ้านนี้อยู่แล้ว", Toast.LENGTH_SHORT).show();
            }


//            goBackHome();
        }

    }

    private void goBackHome(){
        startActivity(new Intent(AddLocationRecord.this, FarmPage.class));
    }
}
