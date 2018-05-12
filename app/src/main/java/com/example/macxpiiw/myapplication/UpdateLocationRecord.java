package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateLocationRecord extends AppCompatActivity {

    private EditText mLocationNameText;
    private EditText mMooText;
    private EditText mTumbonText;
    private EditText mAmphurlText;
    private EditText mPostCodeText;
    private Button mUpdateBtn;
    private Spinner mProvinceText;

    private DBHelper dbHelper;
    private long receivedLocationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location_record);
        getSupportActionBar().setTitle("แก้ไขพื้นที่สำรวจ");

        //init
        mLocationNameText = (EditText)findViewById(R.id.updateLocation_Name);
        mMooText = (EditText)findViewById(R.id.updateMoo);
        mTumbonText = (EditText)findViewById(R.id.updateTumbon);
        mAmphurlText = (EditText)findViewById(R.id.updateAmphur);
        mProvinceText = (Spinner) findViewById(R.id.spinner_update_province);
        mPostCodeText = (EditText)findViewById(R.id.updatePost_Code);
        mUpdateBtn = (Button)findViewById(R.id.btn_updatelocation);


        dbHelper = new DBHelper(this);

        try{
            receivedLocationId =getIntent().getLongExtra("Location_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        Location queriedLocation = dbHelper.getLocation(receivedLocationId);
        mLocationNameText.setText(queriedLocation.getLocation_name());
        mMooText.setText(queriedLocation.getMoo());
        mTumbonText.setText(queriedLocation.getTumbon());
        mAmphurlText.setText(queriedLocation.getAmphur());
//      mProvinceText.setText(queriedLocation.getProvince());
        mPostCodeText.setText(queriedLocation.getPost_code());
        Log.d("tset", "--------"+receivedLocationId);


        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation();
            }
        });



    }

    private void updateLocation(){
        String location_name = mLocationNameText.getText().toString().trim();
        String moo = mMooText.getText().toString().trim();
        String tumbon = mTumbonText.getText().toString().trim();
        String amphur = mAmphurlText.getText().toString().trim();
        String province = mProvinceText.getSelectedItem().toString().trim();
        String postcode = mPostCodeText.getText().toString().trim();
        String status = null;


        Location updatedLocation = new Location(location_name , moo , tumbon , amphur , province , postcode , status);

        dbHelper.updateLocationRecord(receivedLocationId , this ,  updatedLocation);
        finish();

    }

    private void goBackHome() {
        startActivity(new Intent(this, LocationPage.class));
    }


}
