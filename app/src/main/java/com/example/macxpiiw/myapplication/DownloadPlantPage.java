package com.example.macxpiiw.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DownloadPlantPage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;




    private Button addButt;

    private Button btn_importAll ;
    private MySQLConnect mySQLConnect;
    private List<JSONObject> items;
    private List<JSONObject> collectItems;
    private List<String> showItems;


    private SwipeRefreshLayout swipeRefreshLayout;


    private DBHelper dbHelper ;


    private ExpandableListView listView;
    private ExpandableListAdapter2 listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_plant_page);
        getSupportActionBar().setTitle("ดาวน์โหลดพันธุ์ข้าว");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



//        btn_importAll = (Button) findViewById(R.id.import_all);
        addButt = (Button) findViewById(R.id.addButt);

        dbHelper = new DBHelper(this);
        mySQLConnect = new MySQLConnect(DownloadPlantPage.this);


        items = mySQLConnect.getData(1);



        new loaddata2().execute() ;

        new loaddata().execute();



        listView = (ExpandableListView)findViewById(R.id.lvExp);
        showPlant(items);
        listAdapter = new ExpandableListAdapter2(this,listDataHeader,listHash);
       listView.setAdapter(listAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showPlant(items);
                new loaddata().execute() ;
                listAdapter = new ExpandableListAdapter2(DownloadPlantPage.this,listDataHeader,listHash);
                listView.setAdapter(listAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        addButt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                listView = (ExpandableListView)findViewById(R.id.lvExp);
                showPlant(items);
                listAdapter = new ExpandableListAdapter2(DownloadPlantPage.this,listDataHeader,listHash);
                listView.setAdapter(listAdapter);

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


                if(items.size()!=0) {

                    showPlant(items);
                    listAdapter = new ExpandableListAdapter2(DownloadPlantPage.this,listDataHeader,listHash);
                    listView.setAdapter(listAdapter);
                    swipeRefreshLayout.setRefreshing(false);


                    AlertDialog.Builder builder = new AlertDialog.Builder(DownloadPlantPage.this);
                    builder.setTitle("ดาวน์โหลดพันธุ์ข้าวสำเร็จ");
                    builder.setMessage("ท่านต้องการไปที่หน้าแปลงสำรวจหรือไม่");
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
                    Intent intent4 = new Intent(DownloadPlantPage.this , Login2.class);
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

                String select = "select * from " + dbHelper.TABLE_plant + " WHERE " + dbHelper.COL_Plant_Common_Name + " = '" + collectItems.get(j).getString("Plant_Common_Name") + "'";
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


//    public void showUpload(){
//        DBHelper dbHelper = new DBHelper(this);
//        String selectQuery = "SELECT "+dbHelper.COL_Province+ " FROM " + dbHelper.TABLE_location_survey + " group by " + dbHelper.COL_Province;
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        listDataHeader = new ArrayList<String>() ;
//        ArrayList<String> count2 = new ArrayList<String>() ;
//        listHash = new HashMap<>();
//        int loopaom = 0 ;
//
//
//        if(cursor.getCount() > 0){
//            while (cursor.moveToNext()){
//
//                String selectQuery4 = "SELECT "+dbHelper.COL_Province+ " FROM " + dbHelper.TABLE_location_survey + " where " + dbHelper.COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+"'";
//                SQLiteDatabase db4 = dbHelper.getReadableDatabase();
//                Cursor cursor4 = db4.rawQuery(selectQuery4, null);
//
//
//                listDataHeader.add(cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+" ("+cursor4.getCount()+")");
//
//
//                String selectQuery2 = "SELECT * FROM " + dbHelper.TABLE_location_survey + " where " + dbHelper.COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+"' group by "+dbHelper.COL_Amphur;
//                SQLiteDatabase db2 = dbHelper.getReadableDatabase();
//                Cursor cursor2 = db2.rawQuery(selectQuery2, null);
//                List<String> amphur = new ArrayList<>();
//                if(cursor2.getCount() > 0){
//                    while (cursor2.moveToNext()) {
//
//                        amphur.add(cursor2.getString(cursor2.getColumnIndex(dbHelper.COL_Amphur))) ;
//
//                        String selectQuery3 = "SELECT * FROM " + dbHelper.TABLE_location_survey + " where " + dbHelper.COL_Amphur + " = '"+cursor2.getString(cursor2.getColumnIndex(dbHelper.COL_Amphur))+"' AND "+ dbHelper.COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(dbHelper.COL_Province))+"'";
//                        SQLiteDatabase db3 = dbHelper.getReadableDatabase();
//                        Cursor cursor3 = db3.rawQuery(selectQuery3, null);
//                        int count = 0 ;
//                        if(cursor3.getCount() > 0) {
//                            while (cursor3.moveToNext()) {
//
//
//                                count++;
//
//
//                            }
//                        }
//                        count2.add(String.valueOf(count)) ;
//
//                    }
//                }
////                else
////                {
////                    count2.add(String.valueOf(0)) ;
////                }
//
//
//                List<String> result = new ArrayList<String>() ;
//                for(int i = 0 ;  i < amphur.size(); i++){
//
//                    result.add(amphur.get(i)+" ("+count2.get(i)+")") ;
//
//                }
//
//
//                listHash.put(listDataHeader.get(loopaom),result);
//                loopaom++ ;
//                Log.d("amphur", String.valueOf(amphur));
//            }
//        }
//
//
////        ArrayAdapter arrayAdapter;
////        arrayAdapter = new ArrayAdapter(this,R.layout.setlanguage, R.id.tvName,showUpload());
////        listAll.setAdapter(arrayAdapter) ;
//
////        ArrayList<String> result = new ArrayList<String>() ;
////        for(int i = 0 ;  i < listDataHeader.size(); i++){
////
////            result.add(listDataHeader.get(i)+" มีจำนวน "+count2.get(i)+" พื้นที่ในเครื่อง") ;
////
////        }
//
//
//        Log.d("string", String.valueOf(listDataHeader));
//        Log.d("count2", String.valueOf(count2));
//
////        return result ;
//
//    }

    public void showPlant(List<JSONObject> items) {

        listDataHeader = new ArrayList<String>();
        ArrayList<String> plantName = new ArrayList<String>();
        ArrayList<String> plantName2 = new ArrayList<String>();
        listHash = new HashMap<>();



        collectItems = new ArrayList<JSONObject>();
        showItems = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {

            collectItems.add(items.get(i));

        }
        Log.d("size", String.valueOf(items.size()));

        for (int j = 0; j < collectItems.size(); j++) {

            try {
                plantName2.add(collectItems.get(j).getString("Plant_Common_Name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




        listDataHeader.add("พันธุ์ข้าว ("+collectItems.size()+")");




        listHash.put(listDataHeader.get(0), plantName2);


    }


    public class loaddata extends AsyncTask<Void, Void, Integer> {

        int numSync = 0;
        private List<JSONObject> collectItems2;

        private final ProgressDialog dialog = new ProgressDialog(
                DownloadPlantPage.this);
        protected void onPreExecute() {
            this.dialog.setTitle("กำลังตรวจสอบข้อมูล");
            this.dialog.setMessage("กรุณารอสักครู่...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }





        protected void onPostExecute(Integer result) {

            Log.d("debug", String.valueOf(result));


            if(result==9999){

                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DownloadPlantPage.this);
                builder.setMessage("เกิดข้อผิดพลาด");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                builder.create().show();

            }
            else{

                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DownloadPlantPage.this);
                builder.setMessage("มีจำนวนที่ต้องดาวน์โหลด "+result+" พันธุ์");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                builder.create().show();

            }



        }


        @Override
        protected Integer doInBackground(Void... voids) {

            collectItems2 = new ArrayList<JSONObject>();
            for (int i = 0; i < items.size(); i++) {

                collectItems2.add(items.get(i));

            }
            try {
                numSync = SaveAllPlant2((ArrayList<JSONObject>) collectItems2);
            } catch (JSONException e) {
                e.printStackTrace();
            }




            return numSync;
        }


        public int SaveAllPlant2(ArrayList<JSONObject> collectItems2) throws JSONException {

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            int countDownload = 0 ;


            if(collectItems2.size() == 0){
//                Toast.makeText(DownloadPlantPage.this, "ดาวน์โหลดพันธุ์ข้าวไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                Log.d("size", String.valueOf(collectItems2.size()));
                countDownload = 9999 ;

            }else {

//            Toast.makeText(DownloadPlantPage.this, "ดาวน์โหลดพันธุ์ข้าวสำเร็จทั้งหมด "+collectItems.size()+" พันธุ์", Toast.LENGTH_SHORT).show();
                Log.d("size2",String.valueOf(collectItems2.size()));
                for (int j = 0; j < collectItems2.size(); j++) {

                    String select = "select * from " + dbHelper.TABLE_plant + " WHERE " + dbHelper.COL_Plant_Common_Name + " = '" + collectItems2.get(j).getString("Plant_Common_Name") + "'";
                    Cursor cursor = db.rawQuery(select, null);

                    if (cursor.getCount() != 0) {
                        Log.d("select", select);

                    } else {

                        countDownload++;
                    }


                }


            }

            Log.d("count", String.valueOf(countDownload));
            return countDownload ;

        }


    }

    public class loaddata2 extends AsyncTask<Void, Void, Integer> {

        private final ProgressDialog dialog = new ProgressDialog(
                DownloadPlantPage.this);
        protected void onPreExecute() {
            this.dialog.setTitle("กำลังตรวจสอบข้อมูล");
            this.dialog.setMessage("กรุณารอสักครู่...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }



        protected void onPostExecute(Integer result) {

            Log.d("debug", String.valueOf(result));



                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

            showPlant(items);
            listAdapter = new ExpandableListAdapter2(DownloadPlantPage.this,listDataHeader,listHash);
            listView.setAdapter(listAdapter);
            swipeRefreshLayout.setRefreshing(false);




        }


        @Override
        protected Integer doInBackground(Void... voids) {


            do{



            }while(checkSum()==0);



            return null;
        }


        public int checkSum(){

            int sum = items.size() ;
            return sum ;
        }


    }






}

