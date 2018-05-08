package com.example.macxpiiw.myapplication;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.macxpiiw.myapplication.Fragment.Page2;

import com.example.macxpiiw.myapplication.Fragment.Page4;
import com.example.macxpiiw.myapplication.Fragment.page3;

public class FarmPage extends AppCompatActivity /*implements View.OnClickListener*/ {

    private CardView tofarming, togarden , tolocation;
    private BottomNavigationView bottomNavigationView ;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_page);
        getSupportActionBar().setTitle("แปลงสำรวจ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        tofarming=(CardView) findViewById(R.id.to_farming);
//        togarden=(CardView) findViewById(R.id.to_garden);
//        tolocation=(CardView) findViewById(R.id.to_location);
//        //set click
//        tofarming.setOnClickListener(this);
//        togarden.setOnClickListener(this);
//        tolocation.setOnClickListener(this);
        tab =  (TabLayout)findViewById(R.id.tablayout);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id,new Page2()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id,new page3()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id,new Page4()).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id,new Page2()).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.farm_menu){
                    Intent intent1 = new Intent(FarmPage.this , FarmPage.class);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(FarmPage.this , SurveyPage.class);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(FarmPage.this , HomePage.class);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(FarmPage.this , DownloadPage.class);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(FarmPage.this , Login.class);
                    startActivity(intent5);

                }

                return false;
            }
        });





    }



//    @Override
//    public void onClick(View view) {
//        Intent i;
//
//        switch (view.getId()) {
//            case R.id.to_farming:
//                i = new Intent(this, FarmingPage.class);startActivity(i);
//                break;
//            case R.id.to_garden:
//                i = new Intent(this, GardenPage.class);startActivity(i);
//                break;
//            case R.id.to_location:
//                i = new Intent(this, LocationPage.class);startActivity(i);
//                break;
//
//        }
//
//
//    }
}
