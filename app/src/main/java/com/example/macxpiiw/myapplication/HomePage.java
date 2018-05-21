package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ViewFlipper;


public class HomePage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;
    private ScrollView scrollView;
    private ViewFlipper viewFlipper;
    private ViewFlipper viewFlipper2;
    private ViewFlipper viewFlipper3;
    private int [] image;
    private int [] image2;
    private int [] image3;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setTitle("แนะนำการใช้งาน");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        image = new int[]{R.drawable.img01 , R.drawable.img02 , R.drawable.img03};
        image2 = new int[]{R.drawable.img11 , R.drawable.img12 , R.drawable.img13};
        image3 = new int[]{R.drawable.img21 , R.drawable.img26 , R.drawable.img22 , R.drawable.img25 , R.drawable.img23 , R.drawable.img24};
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlip);
        viewFlipper2 = (ViewFlipper) findViewById(R.id.viewFlip2);
        viewFlipper3 = (ViewFlipper) findViewById(R.id.viewFlip3);


        for(int i = 0  ; i< image.length ; i++){
            setViewFlipperImage(image[i]);
        }

        for (int image: image){
            setViewFlipperImage(image);
        }


        for(int i = 0  ; i< image2.length ; i++){
            setViewFlipperImage2(image2[i]);
        }

        for (int image2: image2){
            setViewFlipperImage2(image2);
        }

        for(int i = 0  ; i< image3.length ; i++){
            setViewFlipperImage3(image3[i]);
        }

        for (int image3: image3){
            setViewFlipperImage3(image3);
        }




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.farm_menu){
                    Intent intent1 = new Intent(HomePage.this , FarmPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(HomePage.this , SurveyPage.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(HomePage.this , HomePage.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(HomePage.this , DownloadPage.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(HomePage.this , Login.class);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent5);

                }

                return false;
            }
        });

    }


    public void setViewFlipperImage(int image){
        ImageView imageView  = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setAutoStart(true);
    }

    public void setViewFlipperImage2(int image){
        ImageView imageView  = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper2.addView(imageView);
        viewFlipper2.setAutoStart(true);
    }

    public void setViewFlipperImage3(int image){
        ImageView imageView  = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper3.addView(imageView);
        viewFlipper3.setAutoStart(true);
    }




}