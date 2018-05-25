package com.example.macxpiiw.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

import static com.example.macxpiiw.myapplication.DBHelper.COL_Amphur;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Category;
import static com.example.macxpiiw.myapplication.DBHelper.COL_D_M_Y_Farming;
import static com.example.macxpiiw.myapplication.DBHelper.COL_D_M_Y_Survey;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Dew;
import static com.example.macxpiiw.myapplication.DBHelper.COL_FarmingID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Farming_ID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Farming_Status;
import static com.example.macxpiiw.myapplication.DBHelper.COL_GardenID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Garden_ID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Garden_Name;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Garden_Size;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Garden_Status;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Image_ID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Image_Pic;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Image_Status;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Image_Type;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Incidence;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Latitude;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Level_sea;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Light;
import static com.example.macxpiiw.myapplication.DBHelper.COL_LocationID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Location_ID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Location_Name;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Location_Status;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Longitude;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Moisture;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Moo;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Note;
import static com.example.macxpiiw.myapplication.DBHelper.COL_PlantID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Plant_Common_Name;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Plant_ID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Plant_IDServer;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Point;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Post_Code;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Province;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Rain;
import static com.example.macxpiiw.myapplication.DBHelper.COL_SamplePoint;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Sample_ID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Severity;
import static com.example.macxpiiw.myapplication.DBHelper.COL_SurveyID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Survey_ID;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Survey_Status;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Temp;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Time_Survey;
import static com.example.macxpiiw.myapplication.DBHelper.COL_Tumbon;
import static com.example.macxpiiw.myapplication.DBHelper.TABLE_farming;
import static com.example.macxpiiw.myapplication.DBHelper.TABLE_garden_survey;
import static com.example.macxpiiw.myapplication.DBHelper.TABLE_image;
import static com.example.macxpiiw.myapplication.DBHelper.TABLE_location_survey;
import static com.example.macxpiiw.myapplication.DBHelper.TABLE_plant;
import static com.example.macxpiiw.myapplication.DBHelper.TABLE_survey;
import static com.loopj.android.http.AsyncHttpClient.log;

public class UploadPage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;
    private TextView txtResult ;
    private TextView txtResult2 ;
    private Button butGO ;
    private Button butGO2 ;
    private Button butGO3 ;
    private Button checkbutt ;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    ProgressDialog loadingDialog;

    DBHelper controller = new DBHelper(this);

    public DBHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);
        getSupportActionBar().setTitle("อัพโหลด");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbHelper = new DBHelper(this);



        listView = (ExpandableListView)findViewById(R.id.lvExp);
        showUpload2();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);


        Button butGo = (Button) findViewById(R.id.butGo) ;
        butGo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                listAdapter.getCheckedItems() ;
                Log.d("checklog", String.valueOf(listAdapter.getCheckedItems()));

                Set<Pair<Long, Long>> checkedItems = listAdapter.getCheckedItems();
                for (Pair<Long,Long> c:checkedItems)
                {
                    Log.d("checkItem:",c.first+" "+c.second);
                    int i   = c.first.intValue() ;
                    int i2 = c.second.intValue() ;

                    Log.d("checkItem2:", String.valueOf(listAdapter.getGroup(i)));
                    Log.d("checkItem2:", String.valueOf(listAdapter.getChild(i,i2))) ;

                    String dataprovince = String.valueOf(listAdapter.getGroup(i)) ;
                    String[] dataprovince2 = dataprovince.split(" ") ;

                    String datagarden = String.valueOf(listAdapter.getChild(i,i2)) ;
                    String[] datagarden2 = datagarden.split(" ") ;



                    new loadjson(dataprovince2[0],datagarden2[0]).execute();

                }

//                listView.getItemAtPosition(1) ;

//                for(int i = 0 ;i <listAdapter.getCheckedItems().size();i++) {
//                    Log.d("checklog2", String.valueOf(listView.getItemAtPosition(i)));
//                }





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
                    Intent intent1 = new Intent(UploadPage.this , FarmPage.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(UploadPage.this , SurveyPage.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(UploadPage.this , HomePage.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(UploadPage.this , DownloadPage.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(UploadPage.this , Login.class);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent5);

                }

                return false;
            }
        });


//        Button butGo = (Button) findViewById(R.id.butGo) ;
//        butGo.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                loadingDialog = ProgressDialog.show(UploadPage.this, "กำลังอัพโหลดข้อมูล", "กรุณารอสักครู่", true, false);

//                testaom();
//                testaom2();
//                testaom3();
//                testaom4();

//                new loaddatajson().execute() ;

//                Toast.makeText(UploadPage.this, "กำลังอัพโหลด", Toast.LENGTH_SHORT).show();
//            }
//        });

    }




//    public void testaom() {
//        dbHelper = new DBHelper(this);
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//
//        params.put("locationJSON", dbHelper.UploadLocation());
//
//        client.post("http://158.108.144.4/RDSSATCC/sp_61_DiseaseSurvey/testpost.php", params, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                try {
//                    JSONObject jsonObject = new JSONObject(new String(response));
//                    JSONArray result = jsonObject.getJSONArray("result");
//                    for(int i=0; i<result.length();i++){
//                        JSONObject obj = (JSONObject)result.get(i);
//                        dbHelper.updateSyncStatusLocation(obj.get("id").toString(),obj.get("status").toString());
//                    }
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    Toast.makeText(getApplicationContext(), "อัพโหลดไม่สำเร็จ", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });
//    }
//
//    public void testaom2() {
//        dbHelper = new DBHelper(this);
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//
//
//
//        params.put("gardenJSON", dbHelper.UploadGarden());
//
//
//
//
//        client.post("http://158.108.144.4/RDSSATCC/sp_61_DiseaseSurvey/test1234.php", params, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                try {
//                    JSONObject jsonObject = new JSONObject(new String(response));
//                    JSONArray result = jsonObject.getJSONArray("result");
//                    for(int i=0; i<result.length();i++){
//                        JSONObject obj = (JSONObject)result.get(i);
//                        dbHelper.updateSyncStatusGarden(obj.get("id").toString(),obj.get("status").toString());
//                    }
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    Toast.makeText(getApplicationContext(), "อัพโหลดไม่สำเร็จ", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });
//
//    }
//
//    public void testaom3() {
//        dbHelper = new DBHelper(this);
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//
//
//        params.put("farmingJSON", dbHelper.UploadFarming());
//
//
//
//        client.post("http://158.108.144.4/RDSSATCC/sp_61_DiseaseSurvey/test12345.php", params, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                try {
//                    JSONObject jsonObject = new JSONObject(new String(response));
//                    JSONArray result = jsonObject.getJSONArray("result");
//                    for(int i=0; i<result.length();i++){
//                        JSONObject obj = (JSONObject)result.get(i);
//                        dbHelper.updateSyncStatusFarming(obj.get("id").toString(),obj.get("status").toString());
//                    }
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    Toast.makeText(getApplicationContext(), "อัพโหลดไม่สำเร็จ", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });
//
//
//    }
//
//    public void testaom4() {
//        dbHelper = new DBHelper(this);
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//
//
//        params.put("surveyJSON", dbHelper.UploadSurvey());
//
//
//        client.post("http://158.108.144.4/RDSSATCC/sp_61_DiseaseSurvey/test123456.php", params, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                try {
//                    JSONObject jsonObject = new JSONObject(new String(response));
//                    JSONArray result = jsonObject.getJSONArray("result");
//                    for(int i=0; i<result.length();i++){
//                        JSONObject obj = (JSONObject)result.get(i);
//                        dbHelper.updateSyncStatusSurvey(obj.get("id").toString(),obj.get("status").toString());
//                    }
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    Toast.makeText(getApplicationContext(), "อัพโหลดไม่สำเร็จ", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });
//
//    }

//    public void testaom5(String province,String garden) throws FileNotFoundException {
//
//        dbHelper = new DBHelper(this);
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.setForceMultipartEntityContentType(true);
//
//
//        params.put("imageJSON",dbHelper.UploadImage(province,garden));
//
//
//
//
//
//        client.post("http://158.108.144.4/RDSSATCC/sp_61_DiseaseSurvey/test12345678.php", params, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
////                Toast.makeText(UploadPage.this, "กำลังอัพโหลด", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onProgress(long bytesWritten, long totalSize) {
//                super.onProgress(bytesWritten, totalSize);
//                Log.d("debug",bytesWritten +"  "+ totalSize);
//            }
//
//            @Override
//            public void onUserException(Throwable error) {
//                super.onUserException(error);
//                Log.d("debug2",error.toString());
//            }
//
//
//
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                try {
//
//                    loadingDialog.dismiss();
//                    loadingDialog = ProgressDialog.show(UploadPage.this, "นานหน่อย", "นานจัง", true, false);
//
//                    JSONObject jsonObject = new JSONObject(new String(response));
//                    JSONArray result = jsonObject.getJSONArray("result");
//                    String s = "0" ;
//                    for(int i=0; i<result.length();i++){
//                        JSONObject obj = (JSONObject)result.get(i);
//
//                            dbHelper.updateSyncStatusImage(obj.get("id").toString(), obj.get("id2").toString(), obj.get("id3").toString(), obj.get("id4").toString(), obj.get("id5").toString(), obj.get("id6").toString(), obj.get("status").toString());
//
//                        }
//                    loadingDialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "อัพโหลดสำเร็จ "+statusCode, Toast.LENGTH_LONG).show();
//                    Log.d("aomjson", String.valueOf(jsonObject));
//
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    loadingDialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "อัพโหลดไม่สำเร็จ "+statusCode, Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                loadingDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "อัพโหลดไม่สำเร็จ "+statusCode, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//                loadingDialog.dismiss();
//                loadingDialog = ProgressDialog.show(UploadPage.this, "เกิดความผิดพลาดเกี่ยวกับอินเตอร์เน็ต", "กำลังเชื่อมต่อใหม่", true, false);
////                Toast.makeText(getApplicationContext(), "อัพโหลดไม่สำเร็จ "+retryNo, Toast.LENGTH_LONG).show();
//                if(retryNo == 5 ){
//                    loadingDialog.dismiss();
//                    AlertDialog.Builder builder =  new AlertDialog.Builder(UploadPage.this);
//                    builder.setMessage("อินเตอร์เน็ตหรือเซิฟเวอร์มีปัญหาไม่สามารถเชื่อมต่อได้");
//                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                            Intent relocation = new Intent(UploadPage.this, MainActivity.class);
//                            relocation.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            relocation.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            UploadPage.this.startActivity(relocation);
//
//
//                        }
//                    });
//
//                    builder.create().show();
//                }
//            }
//
//        });
//
//    }


    public void showUpload2(){
        DBHelper dbHelper = new DBHelper(this);
        String selectQuery = "SELECT * FROM " +TABLE_location_survey+" LEFT JOIN "+TABLE_garden_survey+" " +
                "ON "+TABLE_location_survey+"."+COL_Location_ID+" = "+TABLE_garden_survey+"."+COL_LocationID+" LEFT JOIN " + TABLE_farming+" "+
                "ON "+TABLE_garden_survey+"."+COL_Garden_ID+" = "+TABLE_farming+"."+COL_GardenID+" LEFT JOIN " + TABLE_survey+" "+
                "ON "+TABLE_farming+"."+COL_Farming_ID+" = "+TABLE_survey+"."+COL_FarmingID + " LEFT JOIN " + TABLE_image+" "+
                "ON "+TABLE_survey+"."+COL_Survey_ID+" = "+TABLE_image+"."+COL_SurveyID +
                " WHERE "+ TABLE_image + "." +  COL_Image_Status + " = '1'  group by " + COL_Province ;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        listDataHeader = new ArrayList<String>() ;
        ArrayList<String> count2 = new ArrayList<String>() ;
        listHash = new HashMap<>();
        int loopaom = 0 ;


        Log.d("selectQuery3", selectQuery);
        Log.d("countselect3", String.valueOf(cursor.getCount()));


        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

//                String selectQuery4 = "SELECT * FROM " + TABLE_location_survey + " where " + COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(COL_Province))+"' AND "+dbHelper.COL_Location_Status+ "= '1'";


                String selectQuery4 = "SELECT * FROM " + TABLE_location_survey +" LEFT JOIN "+TABLE_garden_survey+" " +
                        "ON "+TABLE_location_survey+"."+COL_Location_ID+" = "+TABLE_garden_survey+"."+COL_LocationID+" LEFT JOIN " + TABLE_farming+" "+
                        "ON "+TABLE_garden_survey+"."+COL_Garden_ID+" = "+TABLE_farming+"."+COL_GardenID+" LEFT JOIN " + TABLE_survey+" "+
                        "ON "+TABLE_farming+"."+COL_Farming_ID+" = "+TABLE_survey+"."+COL_FarmingID + " LEFT JOIN " + TABLE_image+" "+
                        "ON "+TABLE_survey+"."+COL_Survey_ID+" = "+TABLE_image+"."+COL_SurveyID +
                        " WHERE (" + TABLE_image + "." +  COL_Image_Status + " = '1' ) AND "
                        + COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(COL_Province))+"'";



                SQLiteDatabase db4 = dbHelper.getReadableDatabase();
                Cursor cursor4 = db4.rawQuery(selectQuery4, null);




                listDataHeader.add(cursor.getString(cursor.getColumnIndex(COL_Province))+" (ยังไม่ได้อัพโหลด "+cursor4.getCount()+" รูป)");


//                String selectQuery2 = "SELECT * FROM " + TABLE_location_survey + " where " + COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(COL_Province))+"' AND "+dbHelper.COL_Location_Status+ "= '1' group by "+dbHelper.COL_Amphur;


                String selectQuery2 = "SELECT * FROM " + TABLE_location_survey +" LEFT JOIN "+TABLE_garden_survey+" " +
                        "ON "+TABLE_location_survey+"."+COL_Location_ID+" = "+TABLE_garden_survey+"."+COL_LocationID+" LEFT JOIN " + TABLE_farming+" "+
                        "ON "+TABLE_garden_survey+"."+COL_Garden_ID+" = "+TABLE_farming+"."+COL_GardenID+" LEFT JOIN " + TABLE_survey+" "+
                        "ON "+TABLE_farming+"."+COL_Farming_ID+" = "+TABLE_survey+"."+COL_FarmingID + " LEFT JOIN " + TABLE_image+" "+
                        "ON "+TABLE_survey+"."+COL_Survey_ID+" = "+TABLE_image+"."+COL_SurveyID +
                        " WHERE " + TABLE_image + "." +  COL_Image_Status + " = '1' AND "
                        + COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(COL_Province))+"' group by "+COL_Garden_Name ;




                SQLiteDatabase db2 = dbHelper.getReadableDatabase();
                Cursor cursor2 = db2.rawQuery(selectQuery2, null);
                List<String> amphur = new ArrayList<>();
                if(cursor2.getCount() > 0){
                    while (cursor2.moveToNext()) {

                        amphur.add(cursor2.getString(cursor2.getColumnIndex(COL_Garden_Name))) ;

//                        String selectQuery3 = "SELECT * FROM " + TABLE_location_survey + " where " + dbHelper.COL_Amphur + " = '"+cursor2.getString(cursor2.getColumnIndex(dbHelper.COL_Amphur))+"' AND "+ COL_Province + " = '"+cursor.getString(cursor.getColumnIndex(COL_Province))+"' AND "+dbHelper.COL_Location_Status+ "= '1'";

                        String selectQuery3 = "SELECT * FROM " + TABLE_location_survey +" LEFT JOIN "+TABLE_garden_survey+" " +
                                "ON "+TABLE_location_survey+"."+COL_Location_ID+" = "+TABLE_garden_survey+"."+COL_LocationID+" LEFT JOIN " + TABLE_farming+" "+
                                "ON "+TABLE_garden_survey+"."+COL_Garden_ID+" = "+TABLE_farming+"."+COL_GardenID+" LEFT JOIN " + TABLE_survey+" "+
                                "ON "+TABLE_farming+"."+COL_Farming_ID+" = "+TABLE_survey+"."+COL_FarmingID + " LEFT JOIN " + TABLE_image+" "+
                                "ON "+TABLE_survey+"."+COL_Survey_ID+" = "+TABLE_image+"."+COL_SurveyID +
                                " WHERE "+ TABLE_image + "." +  COL_Image_Status + " = '1' AND "
                                + COL_Province + " = '"+cursor2.getString(cursor2.getColumnIndex(COL_Province))+"' AND "
                                + COL_Garden_Name + " = '"+cursor2.getString(cursor2.getColumnIndex(COL_Garden_Name))+"'";






                        SQLiteDatabase db3 = dbHelper.getReadableDatabase();
                        Cursor cursor3 = db3.rawQuery(selectQuery3, null);
                        int count = 0 ;
                        if(cursor3.getCount() > 0) {
                            while (cursor3.moveToNext()) {


                                count++;


                            }
                        }
                        count2.add(String.valueOf(count)) ;

                    }
                }


                List<String> result = new ArrayList<String>() ;
                for(int i = 0 ;  i < amphur.size(); i++){

                    result.add(amphur.get(i)+" ("+count2.get(i)+")") ;

                }


                listHash.put(listDataHeader.get(loopaom),result);
                loopaom++ ;
                Log.d("amphur", String.valueOf(amphur));
            }
        }




        Log.d("string", String.valueOf(listDataHeader));
        Log.d("count2", String.valueOf(count2));



    }

    public class loadjson extends AsyncTask<Void, Void, Void> {

        private  String a  ;
        private  String b ;


        public loadjson(String province , String garden){
            a = province ;
            b = garden ;
        }


        private final ProgressDialog dialog = new ProgressDialog(
                UploadPage.this);
        protected void onPreExecute() {
            this.dialog.setTitle("กำลังอัพโหลดไฟล์");
            this.dialog.setMessage("กรุณารอสักครู่...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }





        protected void onPostExecute(Void result) {

            Log.d("debug", String.valueOf(result));
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

        }


        @Override
        protected Void doInBackground(Void... voids) {
//
//            Handler mainHandler = new Handler(Looper.getMainLooper());
//            Runnable myRunnable = new Runnable() {
//                @Override
//                public void run() {
                    onPreExecute();


            SyncHttpClient client = new SyncHttpClient();
            RequestParams params = new RequestParams();


            params.put("imageJSON", dbHelper.UploadImage(a,b));
            Log.d("result", String.valueOf(params));


            client.post("http://158.108.144.4/RDSSATCC/sp_61_DiseaseSurvey/test12345678.php", params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    // called before request is started
//                Toast.makeText(UploadPage.this, "กำลังอัพโหลด", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                    try {


                        JSONObject jsonObject = new JSONObject(new String(response));
                        JSONArray result = jsonObject.getJSONArray("result");
                        String s = "0";
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject obj = (JSONObject) result.get(i);

                            dbHelper.updateSyncStatusImage(obj.get("id").toString(), obj.get("id2").toString(), obj.get("id3").toString(), obj.get("id4").toString(), obj.get("id5").toString(), obj.get("id6").toString(), obj.get("status").toString());

                        }

                        Log.d("aomjson", String.valueOf(jsonObject));


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block

                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }



            });
//                }
//            };
//            mainHandler.post(myRunnable);
            return null;
        }
    }


}


