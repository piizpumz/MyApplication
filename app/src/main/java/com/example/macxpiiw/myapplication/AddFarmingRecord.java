package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddFarmingRecord extends AppCompatActivity {
    private EditText mDMY_FarmingEditText;
    private Spinner mGardenSpinner;
    private Spinner mPlantSpinner;

    private EditText mPlantEditText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btnDatePick;
    private Button foradd;

    private Button btn_AddFarming;
    private DBHelper dbHelper ;
    private EditText editText_plant;
    private Button btnn;

    private long receivedGardenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farming_record);
        getSupportActionBar().setTitle("เพิ่มการเพาะปลูก");

        mDMY_FarmingEditText = (EditText) findViewById(R.id.addDMY_Farming);

        try{
            receivedGardenId =getIntent().getLongExtra("Garden_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }



        final DBHelper dbHelper = new DBHelper(this);
        ArrayList<String> listgarden = dbHelper.getallGarden();
        mGardenSpinner = (Spinner) findViewById(R.id.spinner_GardenName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_for_gardenname,R.id.for_GardenName , listgarden);
        mGardenSpinner.setAdapter(adapter);
//        mGardenSpinner.setSelection(getIndex(receivedGardenId));


        foradd = (Button) findViewById(R.id.foraddplant);
        foradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddFarmingRecord.this);
                dialog.setContentView(R.layout.plant_dialog);

                editText_plant = (EditText) dialog.findViewById(R.id.edit_nameplant);
                btnn = (Button) dialog.findViewById(R.id.btn_foradd);

                btnn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addplant();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });



        //พันธ์ุข้าว สปินเนอร์
        ArrayList<String> listplant = dbHelper.getallPlant();
        mPlantSpinner = (Spinner) findViewById(R.id.spinner_plantName);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_for_plantname,R.id.for_PlanName , listplant);
        mPlantSpinner.setAdapter(adapter1);



        btn_AddFarming = (Button) findViewById(R.id.btn_addFarming);

        btn_AddFarming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    saveFarming();
                }catch (Exception e){
                    Toast.makeText(AddFarmingRecord.this, "กรุณากรอกข้อมูลให้ถูกต้อง", Toast.LENGTH_LONG).show();
                }
            }
        });



        btnDatePick = (Button) findViewById(R.id.btnpickdate);
        btnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int Year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddFarmingRecord.this ,
                        android.R.style.Theme_Black_NoTitleBar,mDateSetListener,Year,Month,Day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = null;
                month = month + 1;
                if(month <10){
                    if(day < 10){
                        date = "0" + day + "-0" + month + "-" + year ;
                    }
                    else {
                        date = day + "-0" + month + "-" +year;
                    }
                }
                else {
                    if(day<10) {
                        date = "0" + day + "-" + month + "-" + year;
                    }
                    else {
                        date = day + "-" + month + "-" + year;
                    }

                }
                mDMY_FarmingEditText.setText(date);


            }

        };








    }

    private void saveFarming() {
        Boolean Empty = true;
        String d_m_y_farming = mDMY_FarmingEditText.getText().toString().trim();
        String gardenid = mGardenSpinner.getSelectedItem().toString().trim();
        String plantid = mPlantSpinner.getSelectedItem().toString().trim(); // ตรงนี้เปลี่ยน เวลาเอา สปินเนอฺร์ plant มาใส่
        dbHelper = new DBHelper(this);



        if (d_m_y_farming.isEmpty()) {
            //error name is empty
            Toast.makeText(this, "กรุณากรอกวัน-เดือน-ปี เพาะปลูก", Toast.LENGTH_SHORT).show();
            Empty = false;
        }


        if (Empty) {

            String[] Date = d_m_y_farming.split("-");
            String dateeee = Date[2] + "-"+Date[1]+"-"+Date[0];
            String[] name = gardenid.split(",");
            String status = null ;


            Farming farming = new Farming(dateeee, name[0], plantid , status);
            dbHelper.saveNewFarming(farming);
            finish();
//            goBackHome();
        }
    }

    private void addplant(){
        String addplant = editText_plant.getText().toString().trim();
        dbHelper = new DBHelper(this);
        dbHelper.saveNewPlant(addplant);
        ArrayList<String> listplant = dbHelper.getallPlant();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_for_plantname,R.id.for_PlanName , listplant);
        mPlantSpinner.setAdapter(adapter1);

    }


    private int getIndex(Long receivedGardenId){
        dbHelper = new DBHelper(this);
        int X=dbHelper.getPositionGarden(receivedGardenId);
        return X;
    }


    private void goBackHome(){
        startActivity(new Intent(AddFarmingRecord.this, FarmPage.class));
    }



}
