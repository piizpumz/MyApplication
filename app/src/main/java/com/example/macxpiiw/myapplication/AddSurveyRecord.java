package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddSurveyRecord extends AppCompatActivity {

    private EditText mDMYEditText;
    private EditText mTimeEditText;
    private EditText mTempEditText;
    private EditText mMoistureEditText;
    private EditText mRainEditText;
    private EditText mLightEditText;
    private EditText mDewEditText;
    private EditText mSamplePointEditText;
    private EditText mPointEditText;
    private Spinner mCategorySpinner;
    private Spinner mFarmingIDEditText;
    private EditText mIncidence;
    private EditText mSeverity;

    private Button btn_add_survey;
    private DBHelper dbHelper;
    private long receivedFarmingId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey_record);
        getSupportActionBar().setTitle("เพิ่มการสำรวจ");

//        mDMYEditText = (EditText) findViewById(R.id.addDMY);
//        mTimeEditText = (EditText) findViewById(R.id.addTime);
        mTempEditText = (EditText) findViewById(R.id.addTemp);
        mMoistureEditText = (EditText) findViewById(R.id.addMoisture);
        mRainEditText = (EditText)findViewById(R.id.addRain);
        mLightEditText = (EditText) findViewById(R.id.addLight);
        mDewEditText = (EditText) findViewById(R.id.addDew);
        mSamplePointEditText= (EditText) findViewById(R.id.addSamplePoint);
        mPointEditText = (EditText) findViewById(R.id.addPoint);

        mIncidence = (EditText) findViewById(R.id.addIncidence);
        mSeverity = (EditText) findViewById(R.id.addSeverity);

        mCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        btn_add_survey = (Button) findViewById(R.id.btn_addSurvey);


        try{
            receivedFarmingId =getIntent().getLongExtra("FARMING_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }



        btn_add_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSurvey();
            }
        });
    }

    private void saveSurvey() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeNow = new SimpleDateFormat("HH:mm");
        String dateNow2 = dateNow.format(c.getTime());
        String timeNow2 = timeNow.format(c.getTime());


        String d_m_y_survey = dateNow2;
        String time_survey = timeNow2;
        String temp = mTempEditText.getText().toString().trim();
        String moisture = mMoistureEditText.getText().toString().trim();
        String rain = mRainEditText.getText().toString().trim();
        String light = mLightEditText.getText().toString().trim();
        String dew = mDewEditText.getText().toString().trim();
        String category = mCategorySpinner.getSelectedItem().toString().trim();
        String sample_point = mSamplePointEditText.getText().toString().trim();
        String point = mPointEditText.getText().toString().trim();
        String incidence = mIncidence.getText().toString().trim();
        String severity = mSeverity.getText().toString().trim();
        Long farmingID = receivedFarmingId;
        dbHelper = new DBHelper(this);

        Survey survey = new Survey(d_m_y_survey , time_survey , temp , moisture , rain , light , dew , category , sample_point ,point ,farmingID , incidence , severity);
        dbHelper.saveNewSurvey(survey);
        finish();
        goBackHome();
    }
    private void goBackHome(){
        startActivity(new Intent(AddSurveyRecord.this, SurveyPage.class));
    }
}
