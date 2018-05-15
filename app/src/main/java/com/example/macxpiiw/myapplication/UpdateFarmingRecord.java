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

public class UpdateFarmingRecord extends AppCompatActivity {

    private EditText mDMY_FarmingText;
    private EditText mGardenNameText;
    private EditText mPlantText;
    private Button mUpdateBtn;


    private Spinner mGardenSpinner;
    private Spinner mPlantSpinner;


    private DBHelper dbHelper;

    private long receivedFarmingId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_farming_record);


        mDMY_FarmingText = (EditText) findViewById(R.id.updateDMY_Farming);

        mPlantSpinner = (Spinner) findViewById(R.id.updatePlant_spinner);
        mUpdateBtn = (Button) findViewById(R.id.btn_updateFarming);

        dbHelper = new DBHelper(this);


        ArrayList<String> listgarden = dbHelper.getallGarden();
        mGardenSpinner = (Spinner) findViewById(R.id.updateGardenName_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_for_gardenname,R.id.for_GardenName , listgarden);
        mGardenSpinner.setAdapter(adapter);

        ArrayList<String> listplant = dbHelper.getallPlant();
        mPlantSpinner = (Spinner) findViewById(R.id.updatePlant_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_for_plantname,R.id.for_PlanName , listplant);
        mPlantSpinner.setAdapter(adapter1);



        try{
            receivedFarmingId =getIntent().getLongExtra("FARMING_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        Farming queriedFarimg = dbHelper.getFarming(receivedFarmingId);

        mDMY_FarmingText.setText(queriedFarimg.getD_m_y_farming());
        mGardenSpinner.setSelection((getIndex1(mGardenSpinner, Long.valueOf(queriedFarimg.getGardenid()))));
        mPlantSpinner.setSelection((getIndex2(mPlantSpinner, Long.valueOf(queriedFarimg.getPlantid()))));




        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFarming();
            }
        });



        getSupportActionBar().setTitle("แก้ไขการเพาะปลูกที่ " + receivedFarmingId);

    }

    private void updateFarming(){

        String dmy_farming = mDMY_FarmingText.getText().toString().trim();
        String gardenname = mGardenSpinner.getSelectedItem().toString().trim();
        String plant = mPlantSpinner.getSelectedItem().toString().trim();
        String status = null ;

        String[] namegarden = gardenname.split(",");


        Farming updateFarming = new Farming(dmy_farming , namegarden[0] , plant , status);
        dbHelper.updateFarmingRecord(receivedFarmingId , this , updateFarming);
        finish();


    }

    private int getIndex1(Spinner spinner, Long gardenId){
        dbHelper = new DBHelper(this);
        int X=dbHelper.getGardenforUpdateFarming(gardenId);
        return X;
    }

    private int getIndex2(Spinner spinner, Long plantId){
        dbHelper = new DBHelper(this);
        int X=dbHelper.getPlantforUpdatefarming(plantId);
        return X;
    }

    private void goBackHome() {
        startActivity(new Intent(this, FarmingPage.class));
    }
}
