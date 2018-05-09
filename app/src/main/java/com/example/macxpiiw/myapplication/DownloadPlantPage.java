package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DownloadPlantPage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;


    private Button addButt;
    private TextView dataView;
    private Button btn_importAll ;
    private MySQLConnect mySQLConnect;
    private List<JSONObject> items;
    private List<JSONObject> collectItems;
    private List<String> showItems;

    private DBHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_plant_page);
        getSupportActionBar().setTitle("ดาวน์โหลดพันธุ์ข้าว");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


//        btn_importAll = (Button) findViewById(R.id.import_all);
        addButt = (Button) findViewById(R.id.addButt);
        dataView = (TextView) findViewById(R.id.dataView);
        dbHelper = new DBHelper(this);
        mySQLConnect = new MySQLConnect(DownloadPlantPage.this);
        items = mySQLConnect.getData(1);
        update();



        addButt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                collectItems = new ArrayList<JSONObject>();
                showItems = new ArrayList<String>();
                for (int i = 0; i < items.size(); i++) {

                    collectItems.add(items.get(i));

                }
                try {
                    SaveAllPlant((ArrayList<JSONObject>) collectItems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AlertDialog.Builder builder =  new AlertDialog.Builder(DownloadPlantPage.this);
                builder.setTitle( "ดาวน์โหลดพันธุ์ข้าวสำเร็จ");
                builder.setMessage("ท่านต้องการไปที่หน้าแปลงสำรวจหรือไหม");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goBackHome();
                        dialog.dismiss();

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
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.farm_menu){
                    Intent intent1 = new Intent(DownloadPlantPage.this , FarmPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(DownloadPlantPage.this , SurveyPage.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(DownloadPlantPage.this , HomePage.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(DownloadPlantPage.this , DownloadPage.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(DownloadPlantPage.this , Login.class);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent5);

                }

                return false;
            }
        });







    }

    public void update(){

//        for (int i = 0; i < collectItems.size(); i++){
//            try {
//                showItems.add(collectItems.get(i).getString("Plant_ID")+","+collectItems.get(i).getString("Plant_Common_Name"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        String x = dbHelper.getTime() ;

        dataView.setText(x);


    }

    public void SaveAllPlant(ArrayList<JSONObject> collectItems) throws JSONException {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();
        boolean check = true;

//        db.execSQL("DELETE FROM "+dbHelper.TABLE_plant);
        db.execSQL("DELETE FROM "+dbHelper.TABLE_plant_last_update);

        if(collectItems.size() == 0){
            Toast.makeText(DownloadPlantPage.this, "ดาวน์โหลดพันธุ์ข้าวไม่สำเร็จ", Toast.LENGTH_SHORT).show();
            Log.d("size", String.valueOf(collectItems.size()));
            check = false;
        }else {
            int count = 0 ;
//            Toast.makeText(DownloadPlantPage.this, "ดาวน์โหลดพันธุ์ข้าวสำเร็จทั้งหมด "+collectItems.size()+" พันธุ์", Toast.LENGTH_SHORT).show();
            Log.d("size2",String.valueOf(collectItems.size()));
            for (int j = 0; j < collectItems.size(); j++) {

                String select = "select * from " + dbHelper.TABLE_plant + " WHERE " + dbHelper.COL_Plant_IDServer + " = '" + collectItems.get(j).getString("Plant_ID") +
                        "' AND " + dbHelper.COL_Plant_Common_Name + " = '" + collectItems.get(j).getString("Plant_Common_Name") + "'";
                Cursor cursor = db.rawQuery(select, null);

                if (cursor.getCount() != 0) {
                    Log.d("select", select);
                    count++;
                } else {

                    values.put(dbHelper.COL_Plant_IDServer, collectItems.get(j).getString("Plant_ID"));
                    values.put(dbHelper.COL_Plant_Common_Name, collectItems.get(j).getString("Plant_Common_Name"));
                    db.insert(dbHelper.TABLE_plant, null, values);
                }

                values2.put(dbHelper.COL_plant_last_update_time, String.valueOf(Calendar.getInstance().getTime()));
                db.insert(dbHelper.TABLE_plant_last_update, null, values2);
            }
            check = true;



        }

        if(check){


        }
    }

    private void goBackHome(){
        startActivity(new Intent(DownloadPlantPage.this, FarmPage.class));
    }

}

