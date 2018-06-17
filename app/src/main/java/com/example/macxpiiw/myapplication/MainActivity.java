package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView homecard, farmcard, surveycard, imagecard, downloadcard, uploadcard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper mHelper = new DBHelper(this);
        SQLiteDatabase mDB = mHelper.getWritableDatabase();
        mHelper.close();// First method
        mDB.close();
        getSupportActionBar().setTitle("สำรวจโรคข้าว");

        //defining cards
        homecard = (CardView) findViewById(R.id.home_card);
        farmcard = (CardView) findViewById(R.id.farm_card);
        surveycard = (CardView) findViewById(R.id.survey_card);
//        imagecard = (CardView) findViewById(R.id.image_card);
        downloadcard = (CardView) findViewById(R.id.download_card);
        uploadcard = (CardView) findViewById(R.id.upload_card);

        //add click listener
        homecard.setOnClickListener(this);
        farmcard.setOnClickListener(this);
        surveycard.setOnClickListener(this);
//        imagecard.setOnClickListener(this);
        downloadcard.setOnClickListener(this);
        uploadcard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.home_card:
                i = new Intent(this, HomePage.class);
                startActivity(i);
                break;
            case R.id.farm_card:
                i = new Intent(this, FarmPage.class);
                startActivity(i);
                break;
            case R.id.survey_card:
                i = new Intent(this, SurveyPage.class);
                startActivity(i);
                break;
//            case R.id.image_card:
//                i = new Intent(this, ImagePage.class);startActivity(i);
//                break;
            case R.id.download_card:
                i = new Intent(this, Login2.class);
                startActivity(i);
                break;
            case R.id.upload_card:
                i = new Intent(this, Login.class);
                startActivity(i);
                break;

        }


    }
}
