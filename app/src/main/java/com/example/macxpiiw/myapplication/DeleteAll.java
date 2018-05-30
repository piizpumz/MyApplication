package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DeleteAll extends AppCompatActivity {
    Button delete_all;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_all);
        getSupportActionBar().setTitle("ลบข้อมูลทั้งหมด");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        delete_all = (Button)findViewById(R.id.delete_all);

        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("ถ้าคุณยืนยัน ข้อมูลทั้งหมดของคุณจะถูกลบออกจากแอปพลิเคชัน(ข้อมูลบน Server จะไม่ถูกลบ) คุณต้องการลบหรือไหม");
                builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteAll();
                        dialog.dismiss();
                        Toast.makeText(DeleteAll.this , "ลบข้อมูลเรียบร้อย" , Toast.LENGTH_LONG).show();


                    }
                });

                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.create().show();


            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.farm_menu){
                    Intent intent1 = new Intent(DeleteAll.this , FarmPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(DeleteAll.this , SurveyPage.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(DeleteAll.this , HomePage.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(DeleteAll.this , DownloadPage.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(DeleteAll.this , Login.class);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent5);

                }

                return false;
            }
        });
    }


    private void DeleteAll(){
        dbHelper = new DBHelper(this);
        dbHelper.Delete_All();

    }
}
