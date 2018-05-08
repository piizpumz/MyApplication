package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class UpdateGardenRecord extends AppCompatActivity {
    private EditText mGardenNameText;
    private EditText mLongitudeText;
    private EditText mLatitudeText;
    private EditText mLevelSeaText;
    private EditText mGardenSizeText;
    private Button mUpdateBtn;
    private Spinner mLocationNameText;

    private DBHelper dbHelper;
    private DBHelper dbHelper2;
    private long receivedGardenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_garden_record);
        getSupportActionBar().setTitle("แก้ไขแปลงสำรวจ");

        mGardenNameText = (EditText) findViewById(R.id.updateGarden_Name);
        mLongitudeText = (EditText) findViewById(R.id.updateLongitude);
        mLatitudeText = (EditText) findViewById(R.id.updateLatitude);
        mLevelSeaText = (EditText) findViewById(R.id.updateLevel_Sea);
        mGardenSizeText = (EditText) findViewById(R.id.updateGarden_Size);

        dbHelper = new DBHelper(this);
        ArrayList<String> listLocationName = dbHelper.getallLocationName();
        mLocationNameText = (Spinner) findViewById(R.id.spinner_Location);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt , listLocationName);
        mLocationNameText.setAdapter(adapter);

        mUpdateBtn = (Button) findViewById(R.id.btn_updateGarden);

        dbHelper2 = new DBHelper(this);


        try{
            receivedGardenId =getIntent().getLongExtra("Garden_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        Garden queriedGarden = dbHelper2.getGarden(receivedGardenId);
        mGardenNameText.setText(queriedGarden.getGarden_name());
        mLongitudeText.setText(queriedGarden.getLongitude());
        mLatitudeText.setText(queriedGarden.getLatitude());
        mLevelSeaText.setText(queriedGarden.getLevel_sea());
        mGardenSizeText.setText(queriedGarden.getGarden_size());
        mLocationNameText.setSelection((getIndex(mLocationNameText, receivedGardenId)));


        Log.d("tset", "--------"+receivedGardenId);


        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGarden();
            }
        });

    }

    private void updateGarden(){

        String garden_name = mGardenNameText.getText().toString().trim();
        String longitude = mLongitudeText.getText().toString().trim();
        String latitude = mLatitudeText.getText().toString().trim();
        String level_sea = mLevelSeaText.getText().toString().trim();
        String garden_size = mGardenSizeText.getText().toString().trim();
        String locationName = mLocationNameText.getSelectedItem().toString().trim();

        String[] name = locationName.split(",");


        Garden updatedGarden = new Garden(garden_name , longitude , latitude , level_sea ,garden_size , name[0]);
        dbHelper2.updateGardenRecord(receivedGardenId , this ,  updatedGarden);
        finish();


    }


    private int getIndex(Spinner spinner, Long myString){
        dbHelper = new DBHelper(this);
        int X=dbHelper.getLocationforUpdateGarden(myString);
        return X;
    }

    private void goBackHome() {
        startActivity(new Intent(this, GardenPage.class));
    }
}
