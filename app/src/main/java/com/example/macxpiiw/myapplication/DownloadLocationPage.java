package com.example.macxpiiw.myapplication;

import android.content.ContentValues;
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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;



public class DownloadLocationPage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;

    private Spinner addBox ;
    Button addButt;
    private TextView dataView ;
    private MySQLConnect mySQLConnect ;
    private List<JSONObject> items ;
    private List<JSONObject> collectItems;
    private List<String> showItems;



    private DBHelper dbHelper ;


    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_location_page);
        getSupportActionBar().setTitle("ดาวน์โหลดพื้นที่สำรวจ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        DBHelper myDB= new DBHelper(this);
        myDB.getWritableDatabase();
        init();

        listView = (ExpandableListView)findViewById(R.id.lvExp);
        showUpload();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
//        listView.setAdapter(listAdapter);




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.farm_menu){
                    Intent intent1 = new Intent(DownloadLocationPage.this , FarmPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent1 = new Intent(DownloadLocationPage.this , SurveyPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent1 = new Intent(DownloadLocationPage.this , HomePage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent1 = new Intent(DownloadLocationPage.this , DownloadPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent1 = new Intent(DownloadLocationPage.this , UploadPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                }

                return false;
            }
        });


    }

    @Override
    public void onResume() {

        super.onResume();
        listView.setAdapter(listAdapter);
    }


    public void init() {
        addBox = (Spinner) findViewById(R.id.addBox);

        addButt = (Button) findViewById(R.id.addButt);
        dataView = (TextView) findViewById(R.id.dataView);
        dbHelper = new DBHelper(this);
        mySQLConnect = new MySQLConnect(DownloadLocationPage.this);
        items = mySQLConnect.getData(2);

        update();

        addButt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String s = addBox.getSelectedItem().toString();

                collectItems = new ArrayList<JSONObject>();
                showItems = new ArrayList<String>();
                for (int i = 0; i < items.size(); i++) {
                    try {

                        if (items.get(i).getString("Province").equals(s)) {
                            collectItems.add(items.get(i));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    SaveAllLocation((ArrayList<JSONObject>) collectItems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//        addButt.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String s = addBox.getSelectedItem().toString();
//
//                Location testaom = dbHelper.getLocation2(s) ;
//
//
//
//                Log.d("xxxx", "--------"+ testaom.getProvince());
//            }
//        });
//
//
//
//    }


    public void update(){

        String x = dbHelper.getTime2() ;
//        dataView.setText(x);

    }


    public void SaveAllLocation(ArrayList<JSONObject> collectItems) throws JSONException {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();

//        db.execSQL("DELETE FROM "+dbHelper.TABLE_location_survey);
        db.execSQL("DELETE FROM "+dbHelper.TABLE_location_last_update);


        if(collectItems.size() == 0){
            Toast.makeText(DownloadLocationPage.this, "ดาวน์โหลดพื้นที่สำเร็จไม่สำเร็จ", Toast.LENGTH_SHORT).show();
            Log.d("size", String.valueOf(collectItems.size()));
        }
        else {
//            Toast.makeText(DownloadLocationPage.this, "ดาวน์โหลดพื้นที่สำเร็จสำเร็จทั้งหมด " + collectItems.size() + " พื้นที่", Toast.LENGTH_SHORT).show();
            Log.d("size2", String.valueOf(collectItems.size()));
            for (int j = 0; j < collectItems.size(); j++) {

                String select = "select * from " + dbHelper.TABLE_location_survey + " WHERE " + dbHelper.COL_Location_Name + " = '" + collectItems.get(j).getString("Location_Name") +
                        "' AND " + dbHelper.COL_Moo + " = '" + collectItems.get(j).getString("Moo") +
                        "' AND " + dbHelper.COL_Tumbon + " = '" + collectItems.get(j).getString("Tumbon") + "' AND " + dbHelper.COL_Amphur + " = '" + collectItems.get(j).getString("Amphur") +
                        "' AND " + dbHelper.COL_Province + " = '" + collectItems.get(j).getString("Province") + "' AND " + dbHelper.COL_Post_Code + " = '" + collectItems.get(j).getString("Post_Code")+"'";
                Cursor cursor = db.rawQuery(select, null);
                if (cursor.getCount() != 0) {
                    Log.d("select", select);

                } else {
//                    Toast.makeText(getApplicationContext(), "ดาวน์โหลดพื้นที่สำรวจสำเร็จทั้งหมด "+cursor.getCount()+" พื้นที่", Toast.LENGTH_LONG).show();
                    values.put(dbHelper.COL_Location_Name, collectItems.get(j).getString("Location_Name"));
                    values.put(dbHelper.COL_Moo, collectItems.get(j).getString("Moo"));
                    values.put(dbHelper.COL_Tumbon, collectItems.get(j).getString("Tumbon"));
                    values.put(dbHelper.COL_Amphur, collectItems.get(j).getString("Amphur"));
                    values.put(dbHelper.COL_Province, collectItems.get(j).getString("Province"));
                    values.put(dbHelper.COL_Post_Code, collectItems.get(j).getString("Post_Code"));
                    db.insert(dbHelper.TABLE_location_survey, null, values);

                }

                values2.put(dbHelper.COL_location_last_update_time, String.valueOf(Calendar.getInstance().getTime()));
                db.insert(dbHelper.TABLE_location_last_update, null, values2);
            }
            Toast.makeText(getApplicationContext(), "พื้นที่สำรวจในเครื่องอัพเดทเหมือนเซิฟเวอร์แล้ว", Toast.LENGTH_LONG).show();
        }

    }


    public void showUpload(){
        DBHelper dbHelper = new DBHelper(this);
        String selectQuery = "SELECT "+dbHelper.COL_Province+ " FROM " + dbHelper.TABLE_location_survey + " group by " + dbHelper.COL_Province;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        listDataHeader = new ArrayList<String>() ;
        ArrayList<String> count2 = new ArrayList<String>() ;
        listHash = new HashMap<>();
        int loopaom = 0 ;


        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                String selectQuery4 = "SELECT "+dbHelper.COL_Province+ " FROM " + dbHelper.TABLE_location_survey + " where " + dbHelper.COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+"'";
                SQLiteDatabase db4 = dbHelper.getReadableDatabase();
                Cursor cursor4 = db4.rawQuery(selectQuery4, null);


                listDataHeader.add(cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+" ("+cursor4.getCount()+")");


                String selectQuery2 = "SELECT * FROM " + dbHelper.TABLE_location_survey + " where " + dbHelper.COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+"' group by "+dbHelper.COL_Amphur;
                SQLiteDatabase db2 = dbHelper.getReadableDatabase();
                Cursor cursor2 = db2.rawQuery(selectQuery2, null);
                List<String> amphur = new ArrayList<>();
                if(cursor2.getCount() > 0){
                    while (cursor2.moveToNext()) {

                        amphur.add(cursor2.getString(cursor2.getColumnIndex(dbHelper.COL_Amphur))) ;

                        String selectQuery3 = "SELECT * FROM " + dbHelper.TABLE_location_survey + " where " + dbHelper.COL_Amphur + " = '"+cursor2.getString(cursor2.getColumnIndex(dbHelper.COL_Amphur))+"' AND "+ dbHelper.COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+"'";
                        SQLiteDatabase db3 = dbHelper.getReadableDatabase();
                        Cursor cursor3 = db3.rawQuery(selectQuery3, null);
                        int count = 0 ;
                        if(cursor3.getCount() > 0) {
                            while (cursor3.moveToNext()) {


                                count++;


                            }
                        }
                        db3.close();
                        count2.add(String.valueOf(count)) ;

                    }
                }
                db2.close();
//                else
//                {
//                    count2.add(String.valueOf(0)) ;
//                }


                List<String> result = new ArrayList<String>() ;
                for(int i = 0 ;  i < amphur.size(); i++){

                    result.add(amphur.get(i)+" ("+count2.get(i)+")") ;

                }


                listHash.put(listDataHeader.get(loopaom),result);
                loopaom++ ;
                Log.d("amphur", String.valueOf(amphur));
            }
        }
        db.close();


//        ArrayAdapter arrayAdapter;
//        arrayAdapter = new ArrayAdapter(this,R.layout.setlanguage, R.id.tvName,showUpload());
//        listAll.setAdapter(arrayAdapter) ;

//        ArrayList<String> result = new ArrayList<String>() ;
//        for(int i = 0 ;  i < listDataHeader.size(); i++){
//
//            result.add(listDataHeader.get(i)+" มีจำนวน "+count2.get(i)+" พื้นที่ในเครื่อง") ;
//
//        }


        Log.d("string", String.valueOf(listDataHeader));
        Log.d("count2", String.valueOf(count2));

//        return result ;

    }




}