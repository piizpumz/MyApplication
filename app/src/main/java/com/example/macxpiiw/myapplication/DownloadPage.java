package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DownloadPage extends AppCompatActivity implements View.OnClickListener {
    private CardView todownload_plant, todownload_location;
    private BottomNavigationView bottomNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_page);
        getSupportActionBar().setTitle("ดาวน์โหลด");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        todownload_plant = (CardView) findViewById(R.id.to_download_plant);
        todownload_location = (CardView) findViewById(R.id.to_download_location);

        todownload_plant.setOnClickListener(this);
        todownload_location.setOnClickListener(this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.farm_menu){
                    Intent intent1 = new Intent(DownloadPage.this , FarmPage.class);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(DownloadPage.this , SurveyPage.class);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(DownloadPage.this , HomePage.class);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(DownloadPage.this , DownloadPage.class);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(DownloadPage.this , Login.class);
                    startActivity(intent5);

                }

                return false;
            }
        });



    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.to_download_plant:
                i = new Intent(this, DownloadPlantPage.class);startActivity(i);
                break;
            case R.id.to_download_location:
                i = new Intent(this, DownloadLocationPage.class);startActivity(i);
                break;

        }
    }
}
