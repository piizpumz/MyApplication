package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AddGarden2 extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private EditText mGardenNameEditText;
    private EditText mLongitudeEditText;
    private EditText mLatitudeEditText;
    private EditText mGardenSizeEditText;
    private EditText mLevelSeaEditText;
    private Button btn_PickLocation;
    private Button btn_AddGarden;
    private DBHelper dbHelper;
    private long receivedLocationId;
    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garden2);


        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        try{
            receivedLocationId =getIntent().getLongExtra("Location_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }





        mGardenNameEditText = (EditText) findViewById(R.id.addGarden_Name);
        mLongitudeEditText = (EditText) findViewById(R.id.addLongitude);
        mLatitudeEditText = (EditText) findViewById(R.id.addLatitude);
        mGardenSizeEditText = (EditText) findViewById(R.id.addGarden_Size);
        mLevelSeaEditText = (EditText) findViewById(R.id.addLevel_Sea);
        DBHelper dbHelper = new DBHelper(this);

        btn_AddGarden = (Button) findViewById(R.id.btn_addGarden);
        btn_AddGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGarden();
            }
        });


        btn_PickLocation = (Button) findViewById(R.id.btnpicklocation);
        btn_PickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AddGarden2.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }

                client.getLastLocation().addOnSuccessListener(AddGarden2.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location != null) {
                            Double lo = location.getLongitude();
                            Double la = location.getLatitude();
                            String x = la.toString();
                            String y = lo.toString();

                            mLatitudeEditText.setText(x);
                            mLongitudeEditText.setText(y);
                        }
                        else{
                            Toast.makeText(AddGarden2.this , "กรุณาเปิด google map ในเครื่องของคุณ" , Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });


        String name = dbHelper.NameLocation(receivedLocationId);
        getSupportActionBar().setTitle("เพิ่มแปลงสำรวจ : " + name);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }


    private void saveGarden() {
        Boolean Empty = true;
        String garden_name = mGardenNameEditText.getText().toString().trim();
        String longitude = mLongitudeEditText.getText().toString().trim();
        String latitude = mLatitudeEditText.getText().toString().trim();
        String level_sea = mLevelSeaEditText.getText().toString().trim();
        String garden_size = mGardenSizeEditText.getText().toString().trim();
        Long locationName = receivedLocationId;
        String status = null;
        dbHelper = new DBHelper(this);


        String location = String.valueOf(locationName);



        if (garden_name.isEmpty()) {
            //error name is empty
            Toast.makeText(this, "กรุณากรอกชื่อเกษตกร", Toast.LENGTH_SHORT).show();
            Empty = false;
        }

        if (Empty) {
            Garden garden = new Garden(garden_name, longitude, latitude, level_sea, garden_size, location , status);

            Boolean check = dbHelper.cheak_garden(garden);
            if(check) {
                dbHelper.saveNewGarden2(garden);
                finish();
            }
            else {
                Toast.makeText(this, "มีแปลงนี้อยู่แล้ว", Toast.LENGTH_SHORT).show();
            }
//            goBackHome();
        }
    }

    private void goBackHome(){
        startActivity(new Intent(AddGarden2.this, FarmPage.class));
    }


}
