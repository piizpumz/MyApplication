package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateSurveyRecord extends AppCompatActivity {

    private EditText mDMYEditText;
    private EditText mTimeEditText;
    private EditText mTempEditText;
    private EditText mMoistureEditText;
    private EditText mRainEditText;
    private EditText mLightEditText;
    private EditText mDewEditText;
    private EditText mSamplePointEditText;
    private EditText mPointEditText;
    private Long mFarmingIDEditText;
    private Spinner mCategorySpinner;
    private EditText mIncidenceEditText;
    private EditText mSeverity;

    private Button btn_update_survey;
    private DBHelper dbHelper;
    private long receivedSurveyId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_survey_record);
        getSupportActionBar().setTitle("แก้ไขการสำรวจ");


        mDMYEditText = (EditText) findViewById(R.id.updateDMY);
        mTimeEditText = (EditText) findViewById(R.id.updateTime);
        mTempEditText = (EditText) findViewById(R.id.updateTemp);
        mMoistureEditText = (EditText) findViewById(R.id.updateMoisture);
        mRainEditText = (EditText) findViewById(R.id.updateRain);
        mLightEditText = (EditText) findViewById(R.id.updateLight);
        mDewEditText = (EditText) findViewById(R.id.updateDew);
        mSamplePointEditText = (EditText) findViewById(R.id.updateSamplePoint);
        mPointEditText = (EditText) findViewById(R.id.updatePoint);
        mCategorySpinner = (Spinner) findViewById(R.id.spinner_update_category);
        btn_update_survey = (Button) findViewById(R.id.btn_updateSurvey);

        mIncidenceEditText = (EditText) findViewById(R.id.updateIncidence);
        mSeverity = (EditText) findViewById(R.id.updateSeverity);

        dbHelper = new DBHelper(this);

        try {
            receivedSurveyId = getIntent().getLongExtra("SURVEY_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Survey queriedSurvey = dbHelper.getSurvey(receivedSurveyId);

        mDMYEditText.setText(queriedSurvey.getD_m_y_survey());
        mTimeEditText.setText(queriedSurvey.getTime_survey());
        mTempEditText.setText(queriedSurvey.getTemp());
        mMoistureEditText.setText(queriedSurvey.getMoisture());
        mRainEditText.setText(queriedSurvey.getRain());
        mLightEditText.setText(queriedSurvey.getLight());
        mDewEditText.setText(queriedSurvey.getDew());
        mSamplePointEditText.setText(queriedSurvey.getSample_point());
        mPointEditText.setText(queriedSurvey.getPoint());
        mFarmingIDEditText= queriedSurvey.getFarmingID() ;
        mIncidenceEditText.setText(queriedSurvey.getIncidence());
        mSeverity.setText(queriedSurvey.getSeverity());


        btn_update_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSurvey();
            }
        });


    }


    private void updateSurvey(){
        String d_m_y_survey = mDMYEditText.getText().toString().trim();
        String time_survey = mTimeEditText.getText().toString().trim();
        String temp = mTempEditText.getText().toString().trim();
        String moisture = mMoistureEditText.getText().toString().trim();
        String rain = mRainEditText.getText().toString().trim();
        String light = mLightEditText.getText().toString().trim();
        String dew = mDewEditText.getText().toString().trim();
        String category = mCategorySpinner.getSelectedItem().toString().trim();
        String sample_point = mSamplePointEditText.getText().toString().trim();
        String point = mPointEditText.getText().toString().trim();
        String incidence = mIncidenceEditText.getText().toString().trim();
        String severity = mSeverity.getText().toString().trim();
        Long farmingID =mFarmingIDEditText ;

        Survey updatedSurvey = new Survey(d_m_y_survey , time_survey ,temp , moisture , rain , light , dew , category , sample_point , point ,farmingID , incidence , severity);

        dbHelper.updateSurveyRecord(receivedSurveyId , this ,updatedSurvey);
        finish();
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(UpdateSurveyRecord.this, SurveyPage.class));
    }


}
