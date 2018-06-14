package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteAll extends AppCompatActivity {
    Button delete_all;
    DBHelper dbHelper ;

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


                new loaddata().execute();


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

    public class loaddata extends AsyncTask<Void, Void, List<Integer>> {

        private final ProgressDialog dialog = new ProgressDialog(
                DeleteAll.this);

        protected void onPreExecute() {
            this.dialog.setTitle("กำลังตรวจสอบข้อมูล");
            this.dialog.setMessage("กรุณารอสักครู่...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }


        protected void onPostExecute(List<Integer> result) {

            Log.d("debug", String.valueOf(result));

            String alert5 ;

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }


            AlertDialog.Builder builder =  new AlertDialog.Builder(DeleteAll.this);

            if(result.get(4)==0){


                alert5 = "";


            }

            else {
                alert5 = "รูปจำนวน " + result.get(4) + " ยังไม่ได้อัพโหลด";
            }
            String alert6 = "ถ้าคุณยืนยัน ข้อมูลทั้งหมดของคุณจะถูกลบออกจากแอปพลิเคชัน(ข้อมูลบน Server จะไม่ถูกลบ) คุณต้องการลบหรือไหม";



            builder.setMessage(alert5+"\n"+ alert6);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DeleteAll() ;
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "ลบข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();


                }
            });



            if(result.get(4)>0) {
                builder.setNegativeButton("ไปยังหน้าอัพโหลด", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
            }

            builder.create().show();


        }


        @Override
        protected List<Integer> doInBackground(Void... voids) {

            List<Integer> Listsum = new ArrayList<>();
            dbHelper = new DBHelper(DeleteAll.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();


            String selectlocation = "SELECT * FROM "+dbHelper.TABLE_location_survey;
            String selectgarden = "SELECT * FROM "+dbHelper.TABLE_garden_survey;
            String selectfarming = "SELECT * FROM "+dbHelper.TABLE_farming;
            String selectsurvey = "SELECT * FROM "+dbHelper.TABLE_survey;
            String selectimage = "SELECT * FROM "+dbHelper.TABLE_image;

            Cursor cursorlocation = db.rawQuery(selectlocation, null);
            Cursor cursorgarden = db.rawQuery(selectgarden, null);
            Cursor cursorfarming = db.rawQuery(selectfarming, null);
            Cursor cursorsurvey = db.rawQuery(selectsurvey, null);
            Cursor cursorimage = db.rawQuery(selectimage, null);

            if (cursorlocation.getCount() != 0) {

                Listsum.add(cursorlocation.getCount());

            } else {

                Listsum.add(0);

            }
            if (cursorgarden.getCount() != 0) {

                Listsum.add(cursorgarden.getCount());

            } else {

                Listsum.add(0);

            }
            if (cursorfarming.getCount() != 0) {

                Listsum.add(cursorfarming.getCount());

            } else {

                Listsum.add(0);

            }
            if (cursorsurvey.getCount() != 0) {

                Listsum.add(cursorsurvey.getCount());

            } else {

                Listsum.add(0);

            }
            if (cursorimage.getCount() != 0) {

                Listsum.add(cursorimage.getCount());

            } else {

                Listsum.add(0);

            }





            return Listsum;
        }
    }


}
