package com.example.macxpiiw.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by macxpiiw on 3/4/2018 AD.
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String TAG = "DBHelper";

    private static final String DATABASE_NAME = "diseaseDBv3.6";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_location_survey = "location_survey";
    public static final String TABLE_garden_survey = "garden_survey";
    public static final String TABLE_farming = "farming";
    public static final String TABLE_plant= "plant";
    public static final String TABLE_survey = "survey";
    public static final String TABLE_image = "image";
    public static final String TABLE_plant_last_update = "plant_last_update" ;
    public static final String TABLE_location_last_update = "location_last_update" ;
    public static final String TABLE_disease_plant = "disease_plant";


    public static final String COL_Location_ID = "_id";
    public static final String COL_Location_Name = "Location_Name";
    public static final String COL_Moo = "Moo";
    public static final String COL_Tumbon = "Tumbon";
    public static final String COL_Amphur = "Amphur";
    public static final String COL_Province = "Province";
    public static final String COL_Post_Code = "Post_Code";
    public static final String COL_Location_Status = "Location_Status";



    public static final String COL_Garden_ID = "_id";
    public static final String COL_Garden_Name = "Garden_Name";
    public static final String COL_Longitude = "Longitude";
    public static final String COL_Latitude = "Latitude";
    public static final String COL_Level_sea = "Level_sea";
    public static final String COL_Garden_Size = "Garden_Size";
    public static final String COL_LocationID = "LocationID";
    public static final String COL_Garden_Status = "Garden_Status";

    public static final String COL_Plant_ID = "_id";
    public static final String COL_Plant_IDServer = "Plant_IDServer";
    public static final String COL_Plant_Common_Name = "Plant_Common_Name";
    public static final String COL_Plant_Status = "Plant_Status";


    public static final String COL_Farming_ID = "_id";
    public static final String COL_D_M_Y_Farming = "D_M_Y_Farming";
    public static final String COL_GardenID = "GardenID";
    public static final String COL_PlantID = "PlantID";
    public static final String COL_Farming_Status = "Farming_Status";

    public static final String COL_Survey_ID = "_id";
    public static final String COL_D_M_Y_Survey = "D_M_Y_Survey";
    public static final String COL_Time_Survey = "Time_Survey";
    public static final String COL_Temp = "Survey_Temp";
    public static final String COL_Moisture = "Moisture";
    public static final String COL_Rain ="Rain";
    public static final String COL_Light = "Light";
    public static final String COL_Dew = "Dew";
    public static final String COL_Category ="Category";
    public static final String COL_SamplePoint = "SamplePoint";
    public static final String COL_Point = "Point";
    public static final String COL_FarmingID = "FarmingID";
    public static final String COL_Incidence = "Incidence";
    public static final String COL_Severity = "Severity";
    public static final String COL_Survey_Status = "Survey_Status";


    public static final String COL_Image_ID = "_id";
    public static final String COL_Image_Pic = "Image_Pic";
    public static final String COL_Image_Type = "Image_Type";
    public static final String COL_Note = "Note";
    public static final String COL_Sample_ID = "Sample_ID";
    public static final String COL_SurveyID = "SurveyID";
    public static final String COL_DiseaseID = "DiseaseID";
    public static final String COL_Image_Status = "Status_Status";

    public static final String COL_plant_last_update_ID = "_id" ;
    public static final String COL_plant_last_update_time = "plant_last_update_time" ;

    public static final String COL_location_last_update_ID = "_id" ;
    public static final String COL_location_last_update_time = "location_last_update_time" ;

    public static final String COL_Disease_Plant_ID = "_id";
    public static final String COL_Disease_Plant_Name = "Disease_Plant_Name";
    public static final String COL_Disease_Plant_Eng = "Disease_Plant_Eng";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " +  TABLE_location_survey + " ( " + COL_Location_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_Location_Name + " TEXT NOT NULL, "
                + COL_Moo  + " TEXT ,"
                + COL_Tumbon + " TEXT ,"
                + COL_Amphur + " TEXT , "
                + COL_Province + " TEXT , "
                + COL_Post_Code + " TEXT , "
                + COL_Location_Status + " TEXT ); ");

        db.execSQL("CREATE TABLE " +  TABLE_garden_survey + " ( " + COL_Garden_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_Garden_Name + " TEXT , "
                + COL_Longitude + " TEXT ,"
                + COL_Latitude +" TEXT ,"
                + COL_Level_sea +" TEXT , "
                + COL_Garden_Size +" TEXT , "
                + COL_Garden_Status + " TEXT , "
                + COL_LocationID +" TEXT );");

        db.execSQL("CREATE TABLE " +  TABLE_plant + " ( " + COL_Plant_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_Plant_IDServer +" INTEGER , "
                + COL_Plant_Common_Name + " TEXT ,"
                + COL_Plant_Status +" TEXT );");

        db.execSQL("CREATE TABLE " +  TABLE_farming + " ( " + COL_Farming_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_D_M_Y_Farming + " DATE ,"
                + COL_GardenID + " TEXT ,"
                + COL_PlantID + " TEXT ,"
                + COL_Farming_Status +" TEXT ,"
                + "FOREIGN KEY(" + COL_GardenID + ") REFERENCES " + TABLE_garden_survey + "("+COL_Garden_ID+") , "
                + "FOREIGN KEY(" + COL_PlantID+ " ) REFERENCES " +TABLE_plant + "( "+COL_Plant_ID +"));");

        db.execSQL("CREATE TABLE " +  TABLE_survey + " ( " + COL_Survey_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_D_M_Y_Survey + " TEXT , "
                + COL_Time_Survey + " TEXT ,"
                + COL_Moisture +" TEXT ,"
                + COL_Temp +" TEXT ,"
                + COL_Rain +" TEXT ,"
                + COL_Light +" TEXT ,"
                + COL_Dew +" TEXT ,"
                + COL_Category +" TEXT ,"
                + COL_SamplePoint +" TEXT ,"
                + COL_Point +" TEXT ,"
                + COL_Incidence +" TEXT , "
                + COL_Severity +" TEXT , "
                + COL_FarmingID +" INTEGER , "
                + COL_Survey_Status +" TEXT ," +
                "FOREIGN KEY(" + COL_FarmingID+ " ) REFERENCES " +TABLE_farming + "("+COL_Farming_ID+"));");

        db.execSQL("CREATE TABLE " +  TABLE_image + " ( " + COL_Image_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_Image_Pic + " BLOB , "
                + COL_Image_Type + " TEXT , "
                + COL_Note + " TEXT , "
                + COL_Sample_ID + " TEXT ,"
                + COL_SurveyID +" INTEGER , "
                + COL_DiseaseID +" TEXT , "
                + COL_Image_Status +" TEXT ," +
                "FOREIGN KEY(" + COL_SurveyID+ " ) REFERENCES " +TABLE_survey + "("+COL_Survey_ID+"));");


        db.execSQL("CREATE TABLE " +  TABLE_plant_last_update + " ( " + COL_plant_last_update_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_plant_last_update_time + " DATE )");

        db.execSQL("CREATE TABLE " +  TABLE_location_last_update + " ( " + COL_location_last_update_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_location_last_update_time + " DATE )");
        

        Log.d("CREATE TABLE" , "Create Table Success");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);

    }




    // plant
    public void saveNewPlant(String nameplant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_Plant_Common_Name , nameplant);
        values.put(COL_Plant_Status , "1");

        db.insert(TABLE_plant,null, values);
        db.close();

    }


    //start location

    public void saveNewLocation(Location location){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_Location_Name , location.getLocation_name());
        values.put(COL_Moo , location.getMoo());
        values.put(COL_Tumbon , location.getTumbon());
        values.put(COL_Amphur , location.getAmphur());
        values.put(COL_Province , location.getProvince());
        values.put(COL_Post_Code , location.getPost_code());
        values.put(COL_Location_Status , "1");


        db.insert(TABLE_location_survey,null, values);
        db.close();
    }

    public List<Location> locationList(String filter) {
        String query;

        if (filter.equals("")){
            query =  "SELECT * FROM " + TABLE_location_survey + " ORDER BY " + COL_Location_ID + " DESC ";
        } else {
            query = "SELECT * FROM " + TABLE_location_survey + " WHERE "+ COL_Location_Name +" LIKE '" + filter +"%' " +
                    "OR "+ COL_Moo +" LIKE '" + filter +"%' " +
                    "OR "+ COL_Tumbon +" LIKE '" + filter +"%' " +
                    "OR "+ COL_Amphur +" LIKE '" + filter +"%' " +
                    "OR "+ COL_Province +" LIKE '" + filter +"%' ";
        }

        List<Location> locationLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        Location location;

        if (cursor.moveToFirst()){
            do{
                location = new Location();

                location.setId(cursor.getLong(cursor.getColumnIndex(COL_Location_ID)));
                location.setLocation_name(cursor.getString(cursor.getColumnIndex(COL_Location_Name)));
                location.setMoo(cursor.getString(cursor.getColumnIndex(COL_Moo)));
                location.setTumbon(cursor.getString(cursor.getColumnIndex(COL_Tumbon)));
                location.setAmphur(cursor.getString(cursor.getColumnIndex(COL_Amphur)));
                location.setProvince(cursor.getString(cursor.getColumnIndex(COL_Province)));
                location.setPost_code(cursor.getString(cursor.getColumnIndex(COL_Post_Code)));
                location.setStatus(cursor.getString(cursor.getColumnIndex(COL_Location_Status)));
                locationLinkedList.add(location);
            } while (cursor.moveToNext());
        }

        return locationLinkedList;
    }

    public Location getLocation(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_location_survey + " WHERE _id="+ id ;
        Cursor cursor = db.rawQuery(query , null);

        Location receivedLocation = new Location();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedLocation.setLocation_name(cursor.getString(cursor.getColumnIndex(COL_Location_Name)));
            receivedLocation.setMoo(cursor.getString(cursor.getColumnIndex(COL_Moo)));
            receivedLocation.setTumbon(cursor.getString(cursor.getColumnIndex(COL_Tumbon)));
            receivedLocation.setAmphur(cursor.getString(cursor.getColumnIndex(COL_Amphur)));
            receivedLocation.setProvince(cursor.getString(cursor.getColumnIndex(COL_Province)));
            receivedLocation.setPost_code(cursor.getString(cursor.getColumnIndex(COL_Post_Code)));
        }
        Log.d("tset", "--------"+receivedLocation);
        return receivedLocation;

    }

    public void  deleteLocationRecord(long id , Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Boolean cheak = true;
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_garden_survey + " WHERE " + COL_LocationID + "  ='" + id + "'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            cheak =false;
        }
        if(cheak) {
            db.execSQL("DELETE FROM " + TABLE_location_survey + " WHERE _id ='" + id + "'");

            Toast.makeText(context, "ลบแปลงสำรวจสำเร็จ", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "ไม่สามารถลบรายการนี้ได้เนื่องจาก มีแปลงสำรวจใช้รายการนี้อยู่", Toast.LENGTH_SHORT).show();
            Log.d("Delete", String.valueOf(cheak));
        }
    }

    public void updateLocationRecord(long locationId, Context context, Location updatedlocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_location_survey+" SET location_name = '" + updatedlocation.getLocation_name() +
                "', moo = '" + updatedlocation.getMoo() +
                "', tumbon = '" + updatedlocation.getTumbon() +
                "', amphur = '" + updatedlocation.getAmphur() +
                "', province = '" + updatedlocation.getProvince() +
                "', post_code = '" + updatedlocation.getPost_code() +
                "', post_code = '" + updatedlocation.getPost_code() +
                "', Location_Status = '1' WHERE _id ='" + locationId + "'");
        Toast.makeText(context, "แก้ไขพื้นที่เพาะปลูกสำเร็จ", Toast.LENGTH_SHORT).show();
    }

    //end of location




    //start garden

    public void saveNewGarden(Garden garden){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_location_survey + " WHERE " + COL_Location_Name + " = '"+garden.getLocationID()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Location_ID));
            }
        }


        values.put(COL_Garden_Name , garden.getGarden_name());
        values.put(COL_Longitude , garden.getLongitude());
        values.put(COL_Latitude , garden.getLatitude());
        values.put(COL_Level_sea , garden.getLevel_sea());
        values.put(COL_Garden_Size , garden.getGarden_size());
        values.put(COL_LocationID , selectID ) ;
        values.put(COL_Garden_Status , "1");
        db.insert(TABLE_garden_survey ,null , values);
        db.close();

    }


    public void saveNewGarden2(Garden garden){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(COL_Garden_Name , garden.getGarden_name());
        values.put(COL_Longitude , garden.getLongitude());
        values.put(COL_Latitude , garden.getLatitude());
        values.put(COL_Level_sea , garden.getLevel_sea());
        values.put(COL_Garden_Size , garden.getGarden_size());
        values.put(COL_LocationID , garden.getLocationID() ) ;
        values.put(COL_Garden_Status , "1");
        db.insert(TABLE_garden_survey ,null , values);
        db.close();

    }

    public List<Garden> gardenList(String filter) {
        String query;
        if(filter.equals("")){
            query = "SELECT  * FROM " + TABLE_garden_survey+ " ORDER BY " + COL_Garden_ID + " DESC ";
        }else{
            query = "SELECT  * FROM " + TABLE_garden_survey + " INNER JOIN "+TABLE_location_survey+" " +
                    "ON "+TABLE_garden_survey+"."+COL_LocationID+" = "+TABLE_location_survey+"."+COL_Location_ID+" WHERE "+ TABLE_garden_survey+"."+COL_Garden_Name +" LIKE '" + filter +"%' " +
                    "OR " + TABLE_location_survey+"."+COL_Location_Name +" LIKE '" + filter +"%' ";
        }
        List<Garden> gardenLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        Garden garden ;

        if (cursor.moveToFirst()){
            do{
                garden = new Garden();

                garden.setId(cursor.getLong(cursor.getColumnIndex(COL_Garden_ID)));
                garden.setGarden_name(cursor.getString(cursor.getColumnIndex(COL_Garden_Name)));
                garden.setLongitude(cursor.getString(cursor.getColumnIndex(COL_Longitude)));
                garden.setLatitude(cursor.getString(cursor.getColumnIndex(COL_Latitude)));
                garden.setLevel_sea(cursor.getString(cursor.getColumnIndex(COL_Level_sea)));
                garden.setGarden_size(cursor.getString(cursor.getColumnIndex(COL_Garden_Size)));
                garden.setLocationID(cursor.getString(cursor.getColumnIndex(COL_LocationID)));
                garden.setGarden_satatus(cursor.getString(cursor.getColumnIndex(COL_Garden_Status)));
                gardenLinkedList.add(garden);
            }while (cursor.moveToNext());
        }

        return gardenLinkedList;

    }


    public Garden getGarden(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_garden_survey + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Garden receivedGarden = new Garden();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedGarden.setGarden_name(cursor.getString(cursor.getColumnIndex(COL_Garden_Name)));
            receivedGarden.setLongitude(cursor.getString(cursor.getColumnIndex(COL_Longitude)));
            receivedGarden.setLatitude(cursor.getString(cursor.getColumnIndex(COL_Latitude)));
            receivedGarden.setLevel_sea(cursor.getString(cursor.getColumnIndex(COL_Level_sea)));
            receivedGarden.setGarden_size(cursor.getString(cursor.getColumnIndex(COL_Garden_Size)));
            receivedGarden.setLocationID(cursor.getString(cursor.getColumnIndex(COL_LocationID)));

        }
        return receivedGarden;
    }

    public void deleteGardenRecord(long id , Context context){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Boolean cheak = true;
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_farming + " WHERE " + COL_GardenID + "  ='" + id + "'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            cheak =false;
        }
        if(cheak) {
            db.execSQL("DELETE FROM "+ TABLE_garden_survey +" WHERE _id='"+id+"'");
            Toast.makeText(context, "ลบแปลงสำรวจสำเร็จ", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "ไม่สามารถลบรายการนี้ได้เนื่องจาก มีการเพาะปลูกใช้รายการนี้อยู่", Toast.LENGTH_SHORT).show();
            Log.d("Delete", String.valueOf(cheak));
        }
    }


    public void updateGardenRecord(long gardenId , Context context , Garden updateGarden){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_location_survey + " WHERE " + COL_Location_Name + " = '"+updateGarden.getLocationID()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Location_ID));
            }
        }

        db.execSQL("UPDATE "+TABLE_garden_survey+" SET "+COL_Garden_Name+" = '" + updateGarden.getGarden_name() +
                "', "+COL_Longitude+" = '" + updateGarden.getLongitude() +
                "', "+COL_Latitude+" = '" + updateGarden.getLatitude() +
                "', "+COL_Level_sea+" = '" + updateGarden.getLevel_sea() +
                "', "+COL_Garden_Size+" = '" + updateGarden.getGarden_size() +
                "', "+COL_LocationID+" = '" + selectID +
                "', "+COL_Garden_Status+" = '1' WHERE _id ='" + gardenId + "'");
        Toast.makeText(context, "แก้ไขแปลงสำรวจสำเร็จ", Toast.LENGTH_SHORT).show();
    }

    //end garden




    //start Farming

    public void saveNewFarming(Farming farming){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_garden_survey + " WHERE " + COL_Garden_Name + " = '"+farming.getGardenid()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Garden_ID));
            }
        }


        String selectID2 = "";
        String query2;
        query2 = "SELECT * FROM " + TABLE_plant + " WHERE " + COL_Plant_Common_Name + " = '"+farming.getPlantid()+"' ";
        Cursor cursor2 = db.rawQuery(query2,null);
        if(cursor2.getCount()>0){
            while (cursor2.moveToNext()){
                selectID2 = cursor2.getString(cursor.getColumnIndex(COL_Plant_ID));
            }
        }

        values.put(COL_D_M_Y_Farming , farming.getD_m_y_farming());
        values.put(COL_GardenID , selectID);
        values.put(COL_PlantID , selectID2);
        values.put(COL_Farming_Status , "1");
        db.insert(TABLE_farming,null, values);
        db.close();

    }

    public void saveNewFarming2(Farming farming){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_garden_survey + " WHERE " + COL_Garden_Name + " = '"+farming.getGardenid()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Garden_ID));
            }
        }


        String selectID2 = "";
        String query2;
        query2 = "SELECT * FROM " + TABLE_plant + " WHERE " + COL_Plant_Common_Name + " = '"+farming.getPlantid()+"' ";
        Cursor cursor2 = db.rawQuery(query2,null);
        if(cursor2.getCount()>0){
            while (cursor2.moveToNext()){
                selectID2 = cursor2.getString(cursor2.getColumnIndex(COL_Plant_ID));
            }
        }

        values.put(COL_D_M_Y_Farming , farming.getD_m_y_farming());
        values.put(COL_GardenID , farming.getGardenid());
        values.put(COL_PlantID , selectID2);
        values.put(COL_Farming_Status , "1");
        db.insert(TABLE_farming,null, values);
        db.close();

    }

    public List<Farming> farmingList(String filter) {
        String query;
        if(filter.equals("")){
            query = "SELECT  * FROM " + TABLE_farming + " ORDER BY " + COL_Farming_ID + " DESC ";
        }else{

            query = "SELECT  * FROM " + TABLE_farming + " INNER JOIN "+TABLE_garden_survey+" " +
                    "ON "+TABLE_farming+"."+COL_GardenID+" = "+TABLE_garden_survey+"."+COL_Garden_ID+" WHERE "+ TABLE_garden_survey+"."+COL_Garden_Name +" LIKE '" + filter +"%' ";
        }

        List<Farming> farmingLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Farming farming;

        if (cursor.moveToFirst()) {
            do {
                farming = new Farming();

                farming.setId(cursor.getLong(cursor.getColumnIndex(COL_Farming_ID)));
                farming.setD_m_y_farming(cursor.getString(cursor.getColumnIndex(COL_D_M_Y_Farming)));
                farming.setGardenid(cursor.getString(cursor.getColumnIndex(COL_GardenID)));
                farming.setPlantid(cursor.getString(cursor.getColumnIndex(COL_PlantID)));
                farming.setStatus(cursor.getString(cursor.getColumnIndex(COL_Farming_Status)));
                farmingLinkedList.add(farming);
            } while (cursor.moveToNext());
        }
        return farmingLinkedList;
    }

    public Farming getFarming(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_farming + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Farming receivedFarming = new Farming();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedFarming.setD_m_y_farming(cursor.getString(cursor.getColumnIndex(COL_D_M_Y_Farming)));
            receivedFarming.setGardenid(cursor.getString(cursor.getColumnIndex(COL_GardenID)));
            receivedFarming.setPlantid(cursor.getString(cursor.getColumnIndex(COL_PlantID)));
        }
        return receivedFarming;
    }

    public void deleteFarmingRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        Boolean cheak = true;
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_survey + " WHERE " + COL_FarmingID + "  ='" + id + "'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            cheak =false;
        }
        if(cheak) {
            db.execSQL("DELETE FROM "+TABLE_farming+" WHERE _id ='"+id+"'");
            Toast.makeText(context, "ลบการเพาะปลูกสำเร็จ.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "ไม่สามารถลบรายการนี้ได้เนื่องจาก มีการเพาะปลูกใช้รายการนี้อยู่", Toast.LENGTH_SHORT).show();
            Log.d("Delete", String.valueOf(cheak));
        }


    }

    public void updateFarmingRecord(long farmingId , Context context , Farming updateFarming){
        SQLiteDatabase db = this.getWritableDatabase();


        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_garden_survey + " WHERE " + COL_Garden_Name + " = '"+updateFarming.getGardenid()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Garden_ID));
            }
        }


        String selectID2 = "";
        String query2;
        query2 = "SELECT * FROM " + TABLE_plant + " WHERE " + COL_Plant_Common_Name + " = '"+updateFarming.getPlantid()+"' ";
        Cursor cursor2 = db.rawQuery(query2,null);
        if(cursor2.getCount()>0){
            while (cursor2.moveToNext()){
                selectID2 = cursor2.getString(cursor.getColumnIndex(COL_Plant_ID));
            }
        }

        Log.d("test x" , " 0 " +updateFarming.getGardenid());
        Log.d("test xx" , " 0 " +selectID);



        db.execSQL("UPDATE "+TABLE_farming+" SET "+COL_D_M_Y_Farming+" = '" + updateFarming.getD_m_y_farming() +
                "', "+COL_GardenID+" = '" + selectID +
                "', "+COL_PlantID+" = '" + selectID2 +
                "', "+COL_Farming_Status+" = '1' WHERE _id ='" + farmingId + "'");
        Toast.makeText(context, "แก้ไขการเพาะปลูกสำเร็จ", Toast.LENGTH_SHORT).show();
    }

    //end Farming




    //start survey

    public void saveNewSurvey(Survey survey){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_D_M_Y_Survey , survey.getD_m_y_survey());
        values.put(COL_Time_Survey , survey.getTime_survey());
        values.put(COL_Moisture , survey.getMoisture());
        values.put(COL_Temp , survey.getTemp());
        values.put(COL_Rain , survey.getRain());
        values.put(COL_Light , survey.getLight());
        values.put(COL_Dew , survey.getDew());
        values.put(COL_Category , survey.getCategory());
        values.put(COL_SamplePoint , survey.getSample_point());
        values.put(COL_Point , survey.getPoint());
        values.put(COL_FarmingID , survey.getFarmingID());
        values.put(COL_Incidence , survey.getIncidence());
        values.put(COL_Severity , survey.getSeverity());
        values.put(COL_Survey_Status , "1");

        db.insert(TABLE_survey , null , values);
        db.close();
    }
    // id ต้องเปลี่ยนเป็น _id
    public List<Survey> surveyList(String filter){
        String query;
        if (filter.equals("")){
            query = "SELECT * FROM " + TABLE_survey + " ORDER BY " + COL_Survey_ID + " DESC ";
        }else {
            query = "SELECT  * FROM " + TABLE_farming + " INNER JOIN "+TABLE_garden_survey+" " +
                    "ON "+TABLE_farming+"."+COL_GardenID+" = "+TABLE_garden_survey+"."+COL_Garden_ID+" INNER JOIN " + TABLE_location_survey+" "+
                    "ON "+TABLE_garden_survey+"."+COL_LocationID+" = "+TABLE_location_survey+"."+COL_Location_ID+" INNER JOIN " + TABLE_survey+" "+
                    "ON "+TABLE_survey+"."+COL_FarmingID+" = "+TABLE_farming+"."+COL_Farming_ID + " WHERE " + TABLE_garden_survey + "." +  COL_Garden_Name +" LIKE '" + filter +"%' ";
        }

        List<Survey> surveyLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Survey survey;

        if(cursor.moveToFirst()){
            do{
                survey = new Survey();

                survey.setId(cursor.getLong(cursor.getColumnIndex(COL_Survey_ID)));
                survey.setD_m_y_survey(cursor.getString(cursor.getColumnIndex(COL_D_M_Y_Survey)));
                survey.setTime_survey(cursor.getString(cursor.getColumnIndex(COL_Time_Survey)));
                survey.setMoisture(cursor.getString(cursor.getColumnIndex(COL_Moisture)));
                survey.setTemp(cursor.getString(cursor.getColumnIndex(COL_Temp)));
                survey.setRain(cursor.getString(cursor.getColumnIndex(COL_Rain)));
                survey.setLight(cursor.getString(cursor.getColumnIndex(COL_Light)));
                survey.setDew(cursor.getString(cursor.getColumnIndex(COL_Dew)));
                survey.setCategory(cursor.getString(cursor.getColumnIndex(COL_Category)));
                survey.setSample_point(cursor.getString(cursor.getColumnIndex(COL_SamplePoint)));
                survey.setPoint(cursor.getString(cursor.getColumnIndex(COL_Point)));
                survey.setFarmingID(cursor.getLong(cursor.getColumnIndex(COL_FarmingID)));
                survey.setIncidence(cursor.getString(cursor.getColumnIndex(COL_Incidence)));
                survey.setSeverity(cursor.getString(cursor.getColumnIndex(COL_Severity)));
                survey.setStatus(cursor.getString(cursor.getColumnIndex(COL_Survey_Status)));
                surveyLinkedList.add(survey);
            } while (cursor.moveToNext());
        }

        return surveyLinkedList;

    }

    public Survey getSurvey(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_survey + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Survey receivedSurvey = new Survey();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedSurvey.setId(cursor.getLong(cursor.getColumnIndex(COL_Survey_ID)));
            receivedSurvey.setD_m_y_survey(cursor.getString(cursor.getColumnIndex(COL_D_M_Y_Survey)));
            receivedSurvey.setTime_survey(cursor.getString(cursor.getColumnIndex(COL_Time_Survey)));
            receivedSurvey.setMoisture(cursor.getString(cursor.getColumnIndex(COL_Moisture)));
            receivedSurvey.setTemp(cursor.getString(cursor.getColumnIndex(COL_Temp)));
            receivedSurvey.setRain(cursor.getString(cursor.getColumnIndex(COL_Rain)));
            receivedSurvey.setLight(cursor.getString(cursor.getColumnIndex(COL_Light)));
            receivedSurvey.setDew(cursor.getString(cursor.getColumnIndex(COL_Dew)));
            receivedSurvey.setCategory(cursor.getString(cursor.getColumnIndex(COL_Category)));
            receivedSurvey.setSample_point(cursor.getString(cursor.getColumnIndex(COL_SamplePoint)));
            receivedSurvey.setPoint(cursor.getString(cursor.getColumnIndex(COL_Point)));
            receivedSurvey.setFarmingID(cursor.getLong(cursor.getColumnIndex(COL_FarmingID)));
            receivedSurvey.setIncidence(cursor.getString(cursor.getColumnIndex(COL_Incidence)));
            receivedSurvey.setSeverity(cursor.getString(cursor.getColumnIndex(COL_Severity)));
        }

        return receivedSurvey;
    }

    public void deleteSurveyRecord(long id , Context context){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_survey+" WHERE _id='"+id+"'");
        db.execSQL("DELETE FROM "+TABLE_image+" WHERE " + COL_SurveyID + " = '"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    public void updateSurveyRecord(long surveyId , Context context , Survey updateSurvey){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_survey+" SET "+COL_D_M_Y_Survey+" = '" + updateSurvey.getD_m_y_survey() +
                "', "+COL_Time_Survey+" = '" + updateSurvey.getTime_survey() +
                "', "+COL_Moisture+" = '" + updateSurvey.getMoisture() +
                "', "+COL_Temp+" = '" + updateSurvey.getTemp() +
                "', "+COL_Rain+" = '" + updateSurvey.getRain() +
                "', "+COL_Light+" = '" + updateSurvey.getLight() +
                "', "+COL_Dew+" = '" + updateSurvey.getDew() +
                "', "+COL_Category+" = '" + updateSurvey.getCategory() +
                "', "+COL_SamplePoint+" = '" + updateSurvey.getSample_point() +
                "', "+COL_Point+" = '" + updateSurvey.getPoint() +
                "', "+COL_FarmingID+" = '" + updateSurvey.getFarmingID() +
                "', "+COL_Incidence+" = '" + updateSurvey.getIncidence() +
                "', "+COL_Severity+" = '" + updateSurvey.getSeverity() +
                "', "+COL_Survey_Status+" = '1' WHERE _id ='" + surveyId + "'");
        Toast.makeText(context, "แก้ไขการสำรวจสำเร็จ", Toast.LENGTH_SHORT).show();



    }

    //end survey



    //start Image

    public void saveNewImage(Image image){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] x = image.getImage();


        Bitmap bmp = BitmapFactory.decodeByteArray(x , 0 , x.length);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=10;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG , 100 , bytes);




        values.put(COL_SurveyID , image.getSurveyID());
        values.put(COL_Sample_ID , image.getSampleID());
        values.put(COL_Image_Type , image.getType());
        values.put(COL_Note , image.getNote());
        values.put(COL_Image_Pic , x);
        values.put(COL_DiseaseID , image.getDisease());
        values.put(COL_Image_Status , "1");
        db.insert(TABLE_image,null, values);
        db.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    public List<Image> imageList(String filter , Long surveyId) {

        String query;

        if (filter.equals("")){
            query =  "SELECT * FROM " + TABLE_image + " WHERE " + COL_SurveyID + " = " + surveyId + " ORDER BY " + COL_Image_ID + " DESC ";
        } else {
            query = "SELCT * FROM " + TABLE_image + " ORDER BY "+ filter;
        }

        List<Image> imageLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        Image image;
        Bitmap bt = null;


        if (cursor.moveToFirst()){
            do{
                image = new Image();
                image.setId(cursor.getLong(cursor.getColumnIndex(COL_Image_ID)));
                image.setSurveyID(cursor.getLong(cursor.getColumnIndex(COL_SurveyID)));
                image.setSampleID(cursor.getString(cursor.getColumnIndex(COL_Sample_ID)));
                image.setType(cursor.getString(cursor.getColumnIndex(COL_Image_Type)));
                image.setNote(cursor.getString(cursor.getColumnIndex(COL_Note)));
                image.setImage(cursor.getBlob(cursor.getColumnIndex(COL_Image_Pic)));
                image.setDisease(cursor.getString(cursor.getColumnIndex(COL_DiseaseID)));
                imageLinkedList.add(image);
            } while (cursor.moveToNext());
        }
        return imageLinkedList;


    }

    public Image getImage(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_image + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query , null);

        Image receivedImage = new Image();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            receivedImage.setSurveyID(cursor.getLong(cursor.getColumnIndex(COL_SurveyID)));
            receivedImage.setSampleID(cursor.getString(cursor.getColumnIndex(COL_Sample_ID)));
            receivedImage.setType(cursor.getString(cursor.getColumnIndex(COL_Image_Type)));
            receivedImage.setNote(cursor.getString(cursor.getColumnIndex(COL_Note)));
            receivedImage.setImage(cursor.getBlob(cursor.getColumnIndex(COL_Image_Pic)));
            receivedImage.setDisease(cursor.getString(cursor.getColumnIndex(COL_DiseaseID)));
        }
        return receivedImage;

    }

    public void delectImageRecord(long id , Context context){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_image+" WHERE _id='"+id+"'");
        Toast.makeText(context, "ลบรูปภาพสำเร็จ", Toast.LENGTH_SHORT).show();
    }

    public void updateImageRecord(long imageId , Context context , Image updateImage){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        byte[] x = updateImage.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(x , 0 , x.length);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=10;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG , 100 , bytes);

        db.execSQL("DELETE FROM "+TABLE_image+" WHERE _id='"+imageId+"'");

        values.put(COL_SurveyID , updateImage.getSurveyID());
        values.put(COL_Sample_ID , updateImage.getSampleID());
        values.put(COL_Image_Type , updateImage.getType());
        values.put(COL_Note , updateImage.getNote());
        values.put(COL_Image_Pic , x);
        values.put(COL_DiseaseID , updateImage.getDisease());
        values.put(COL_Image_Status , "1");
        db.insert(TABLE_image,null, values);
        Toast.makeText(context, "แก้ไขรูปภาพสำเร็จ", Toast.LENGTH_SHORT).show();
        db.close();



    }

    //end of Image




    //spinner getAll Item
    public ArrayList<String> getallLocationName() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_location_survey + " ORDER BY " + COL_Location_ID + " DESC ";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                 while (cursor.moveToNext()) {
                    String LocationName = cursor.getString(cursor.getColumnIndex(COL_Location_Name));
                    String Moo = cursor.getString(cursor.getColumnIndex(COL_Moo));
                    String Tumbon = cursor.getString(cursor.getColumnIndex(COL_Tumbon));
                    list.add(LocationName + ", (ตำบล : " + Tumbon +")" );
            }
        }
        db.setTransactionSuccessful();
    } catch (Exception e){
            e.printStackTrace();
        }
        finally {
        db.endTransaction();
        db.close();
        }
        return list;
    }

    public ArrayList<String> getallMoo() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_location_survey + " ORDER BY " + COL_Location_ID + " DESC ";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String LocationName = cursor.getString(cursor.getColumnIndex(COL_Moo));
                    list.add(LocationName);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getallGarden() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String  selectQuery = "SELECT  * FROM " + TABLE_garden_survey + " INNER JOIN "+TABLE_location_survey+" " +
                    "ON "+TABLE_garden_survey+"."+COL_LocationID+" = "+TABLE_location_survey+"."+COL_Location_ID + " ORDER BY " + COL_Garden_ID + " DESC";

            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String GardenName = cursor.getString(cursor.getColumnIndex(COL_Garden_Name));
                    String LocationName =cursor.getString(cursor.getColumnIndex(COL_Location_Name));
                    list.add(GardenName + ", (หมู่บ้าน : " + LocationName +")" );
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getallPlant() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_plant + " ORDER BY " + COL_Plant_ID + " DESC ";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String PlantCommonName = cursor.getString(cursor.getColumnIndex(COL_Plant_Common_Name));
                    list.add(PlantCommonName);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getallFarming() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_farming;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String FarmingId = cursor.getString(cursor.getColumnIndex(COL_Farming_ID));
                    list.add(FarmingId);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    //end spinner



    //get Time
    public String getTime(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_plant_last_update;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String x = "";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                x = cursor.getString(cursor.getColumnIndex(COL_plant_last_update_time));
            }
        }

        return x ;
    }

    public String getTime2(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_location_last_update;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String x = "";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                x = cursor.getString(cursor.getColumnIndex(COL_location_last_update_time));
            }
        }

        return x ;
    }


    //get Name
    public String NameLocation(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_location_survey + " WHERE " + COL_Location_ID + " = '"+id+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Location_Name));
            }
        }

        return selectID;

    }

    public String NameGarden(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_garden_survey + " WHERE " + COL_Garden_ID + " = '"+id+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Garden_Name));
            }
        }

        return selectID;

    }



    //get Name form ID
    public String getNameLocation(int position , Garden garden){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_location_survey + " WHERE " + COL_Location_ID + " = '"+garden.getLocationID()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Location_Name));
            }
        }

        return selectID;

    }
    //get Name form ID
    public String getTumbonLocation(int position , Garden garden){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_location_survey + " WHERE " + COL_Location_ID + " = '"+garden.getLocationID()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Tumbon));
            }
        }

        return selectID;

    }


    // โชว์ชื่อ

    public String getNameGarden(int position , Farming farming){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_garden_survey + " WHERE " + COL_Garden_ID + " = '"+farming.getGardenid()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Garden_Name));
            }
        }

        return selectID;

    }

    public String getNamePlant(int position , Farming farming){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT * FROM " + TABLE_plant + " WHERE " + COL_Plant_ID + " = '"+farming.getPlantid()+"' ";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Plant_Common_Name));
            }
        }
        return selectID;

    }

    public String getLocationNameforFarming(int position , Farming farming){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT  * FROM " + TABLE_farming + " INNER JOIN "+TABLE_garden_survey+" " +
                "ON "+TABLE_farming+"."+COL_GardenID+" = "+TABLE_garden_survey+"."+COL_Garden_ID+" INNER JOIN " + TABLE_location_survey+" "+
                "ON "+TABLE_garden_survey+"."+COL_LocationID+" = "+TABLE_location_survey+"."+COL_Location_ID + " WHERE " +TABLE_farming+"." + COL_Farming_ID + " = '"+farming.getId()+"' ";

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Location_Name));
            }
        }

        return selectID;

    }

    public String getLocationNameforSurvey(int position , Survey survey){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectID = "";
        String query;
        query = "SELECT  * FROM " + TABLE_farming + " INNER JOIN "+TABLE_garden_survey+" " +
                "ON "+TABLE_farming+"."+COL_GardenID+" = "+TABLE_garden_survey+"."+COL_Garden_ID+" INNER JOIN " + TABLE_location_survey+" "+
                "ON "+TABLE_garden_survey+"."+COL_LocationID+" = "+TABLE_location_survey+"."+COL_Location_ID+" INNER JOIN " + TABLE_survey+" "+
                "ON "+TABLE_survey+"."+COL_FarmingID+" = "+TABLE_farming+"."+COL_Farming_ID + " WHERE " + TABLE_survey + "." +  COL_Survey_ID + " = '"+survey.getId()+"' ";

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                selectID = cursor.getString(cursor.getColumnIndex(COL_Garden_Name));
            }
        }

        return selectID;

    }


    //spinner update set

    public int getLocationforUpdateGarden(Long gardenid){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        String query2;
        int nameLoca = 0;
        int Count = 0;
        query = "SELECT * FROM " + TABLE_location_survey + " ORDER BY " + COL_Location_ID + " DESC ";

        query2 = "SELECT * FROM " + TABLE_garden_survey + " INNER JOIN " +TABLE_location_survey + " ON " + TABLE_location_survey + "." +COL_Location_ID+ " = " +TABLE_garden_survey+ "." +COL_LocationID+" WHERE " +TABLE_garden_survey+"."+ COL_Garden_ID + " = '"+gardenid+"' ";

        Cursor cursor2 = db.rawQuery(query2,null);
        if(cursor2.getCount()>0){
            while (cursor2.moveToNext()){
                nameLoca = cursor2.getInt(cursor2.getColumnIndex(COL_Location_ID));
                Log.d("test nameLoca" , String.valueOf(nameLoca));

            }
        }


        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            Log.d("test SelectID" , String.valueOf(nameLoca));
            do {
                selectID = cursor.getInt(cursor.getColumnIndex(COL_Location_ID));
                if(selectID == nameLoca){
                    Log.d("test X" , "true");
                    break;
                }
                Count = Count +1 ;
            } while (cursor.moveToNext());
        }
        Log.d("test Count" , String.valueOf(Count));
        return Count;
    }

    public int getGardenforUpdateFarming(Long gardenid){
        SQLiteDatabase db = this.getWritableDatabase();
        long selectID;
        String query;
        String query2;
        long nameGarden = gardenid;
        int Count = 0;
        Log.d("test nameGarden" , String.valueOf(nameGarden));
        query = "SELECT * FROM " + TABLE_garden_survey + " ORDER BY " + COL_Garden_ID + " DESC ";

        query2 = "SELECT * FROM " + TABLE_garden_survey + " INNER JOIN " +TABLE_location_survey + " ON " + TABLE_location_survey + "." +COL_Location_ID+ " = " +TABLE_garden_survey+ "." +COL_LocationID+" WHERE " +TABLE_garden_survey+"."+ COL_Garden_ID + " = '"+gardenid+"' ";

//        Cursor cursor2 = db.rawQuery(query2,null);
//        if(cursor2.getCount()>0){
//            while (cursor2.moveToNext()){
//                nameLoca = cursor2.getInt(cursor2.getColumnIndex(COL_Location_ID));
//                Log.d("test nameLoca" , String.valueOf(nameLoca));
//
//            }
//        }


        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            Log.d("test SelectID" , String.valueOf(nameGarden));
            do {
                selectID = cursor.getLong(cursor.getColumnIndex(COL_Garden_ID));
                if(selectID == nameGarden){
                    Log.d("test X" , "true");
                    break;
                }
                Count = Count +1 ;
            } while (cursor.moveToNext());
        }
        Log.d("test Count" , String.valueOf(Count));
        return Count;
    }

    public int getPlantforUpdatefarming(Long gardenid){
        SQLiteDatabase db = this.getWritableDatabase();
        Long selectID;
        String query;
        String query2;
        Long namePlant = gardenid;
        Log.d("test namePlant" , String.valueOf(namePlant));
        int Count = 0;
        query = "SELECT * FROM " + TABLE_plant + " ORDER BY " + COL_Plant_ID + " DESC ";

        query2 = "SELECT * FROM " + TABLE_garden_survey + " INNER JOIN " +TABLE_location_survey + " ON " + TABLE_location_survey + "." +COL_Location_ID+ " = " +TABLE_garden_survey+ "." +COL_LocationID+" WHERE " +TABLE_garden_survey+"."+ COL_Garden_ID + " = '"+gardenid+"' ";

//        Cursor cursor2 = db.rawQuery(query2,null);
//        if(cursor2.getCount()>0){
//            while (cursor2.moveToNext()){
//                nameLoca = cursor2.getInt(cursor2.getColumnIndex(COL_Location_ID));
//                Log.d("test nameLoca" , String.valueOf(nameLoca));
//
//            }
//        }


        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            Log.d("test SelectID" , String.valueOf(namePlant));
            do {
                selectID = cursor.getLong(cursor.getColumnIndex(COL_Plant_ID));
                if(selectID == namePlant){
                    Log.d("test X" , "true");
                    break;
                }
                Count = Count +1 ;
            } while (cursor.moveToNext());
        }
        Log.d("test Count" , String.valueOf(Count));
        return Count;
    }


    //spinner set add

    public int getPositionLocation(Long locationId){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        Long id = locationId;
        int Count = 0;

        query = "SELECT * FROM " + TABLE_location_survey + " ORDER BY " + COL_Location_ID + " DESC ";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                selectID = cursor.getInt(cursor.getColumnIndex(COL_Location_ID));
                if(selectID == id){
                    Log.d("test X" , "true");
                    break;
                }
                Count = Count +1 ;
            } while (cursor.moveToNext());
        }
        Log.d("test Count" , String.valueOf(Count));
        return Count;



    }

    public int getPositionGarden(Long gardenId){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        Long id = gardenId;
        int Count = 0;

        query = "SELECT * FROM " + TABLE_garden_survey + " ORDER BY " + COL_Garden_ID + " DESC ";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                selectID = cursor.getInt(cursor.getColumnIndex(COL_Garden_ID));
                if(selectID == id){
                    break;
                }
                Count = Count +1 ;
            } while (cursor.moveToNext());
        }
        return Count;
    }
    public int getPositionFarming(Long farmingId){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        Long id = farmingId;
        int Count = 0;

        query = "SELECT * FROM " + TABLE_farming + " ORDER BY " + COL_Farming_ID + " DESC ";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                selectID = cursor.getInt(cursor.getColumnIndex(COL_Farming_ID));
                if(selectID == id){
                    break;
                }
                Count = Count +1 ;
            } while (cursor.moveToNext());
        }
        return Count;
    }


    //get count pic
    public int getCountImage(Long surveyid){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        Long id = surveyid;
        int Count = 0;

        query = "SELECT * FROM " + TABLE_image + " WHERE " + COL_SurveyID + " = " + surveyid;

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
               Count++;
            } while (cursor.moveToNext());
        }

        return Count;

    }





    // upload

    public String UploadLocation(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_location_survey + " where " + COL_Location_Status +" = '1'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COL_Location_ID, cursor.getString(0));
                map.put(COL_Location_Name, cursor.getString(1));
                map.put(COL_Moo, cursor.getString(2));
                map.put(COL_Tumbon, cursor.getString(3));
                map.put(COL_Amphur, cursor.getString(4));
                map.put(COL_Province, cursor.getString(5));
                map.put(COL_Post_Code, cursor.getString(6));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    public String UploadGarden(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_garden_survey + " where " + COL_Garden_Status +" = '1'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COL_Garden_ID, cursor.getString(0));
                map.put(COL_Garden_Name, cursor.getString(1));
                map.put(COL_Latitude, cursor.getString(2));
                map.put(COL_Longitude, cursor.getString(3));
                map.put(COL_Level_sea, cursor.getString(4));
                map.put(COL_Garden_Size, cursor.getString(5));
//                map.put(COL_LocationID, cursor.getString(5));
                String locationID = cursor.getString(cursor.getColumnIndex(COL_LocationID)) ;
                String selectQuery2 = "SELECT * FROM " + TABLE_location_survey + " where " + COL_Location_ID +" = '" + locationID +"'" ;
                SQLiteDatabase database2 = this.getWritableDatabase();
                Cursor cursor2 = database2.rawQuery(selectQuery2, null);
                if (cursor2.moveToFirst()) {

                    map.put(COL_Location_Name, cursor2.getString(1));
                    map.put(COL_Moo, cursor2.getString(2));
                    map.put(COL_Tumbon, cursor2.getString(3));
                    map.put(COL_Amphur, cursor2.getString(4));
                    map.put(COL_Province, cursor2.getString(5));
                    map.put(COL_Post_Code, cursor2.getString(6));

                }
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    public String UploadFarming(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_farming + " where " + COL_Farming_Status +" = '1'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(COL_Farming_ID, cursor.getString(0));
                map.put(COL_D_M_Y_Farming, cursor.getString(1));

                String plantID = cursor.getString(cursor.getColumnIndex(COL_PlantID)) ;
                String selectQuery4 = "SELECT * FROM " + TABLE_plant + " where " + COL_Plant_ID +" = '" + plantID +"'" ;
                SQLiteDatabase database4 = this.getWritableDatabase();
                Cursor cursor4 = database4.rawQuery(selectQuery4, null);
                if(cursor4.moveToFirst()){

                    map.put(COL_Plant_IDServer,cursor4.getString(1)) ;

                }

                String gardenID = cursor.getString(cursor.getColumnIndex(COL_GardenID)) ;
                String selectQuery2 = "SELECT * FROM " + TABLE_garden_survey + " where " + COL_Garden_ID +" = '" + gardenID +"'" ;
                SQLiteDatabase database2 = this.getWritableDatabase();
                Cursor cursor2 = database2.rawQuery(selectQuery2, null);
                if (cursor2.moveToFirst()) {

                    map.put(COL_Garden_Name, cursor2.getString(1));
                    map.put(COL_Latitude, cursor2.getString(2));
                    map.put(COL_Longitude, cursor2.getString(3));
                    map.put(COL_Level_sea, cursor2.getString(4));
                    map.put(COL_Garden_Size, cursor2.getString(5));
                    String locationID = cursor2.getString(cursor2.getColumnIndex(COL_LocationID)) ;
                    String selectQuery3 = "SELECT * FROM " + TABLE_location_survey + " where " + COL_Location_ID +" = '" + locationID +"'" ;
                    SQLiteDatabase database3 = this.getWritableDatabase();
                    Cursor cursor3 = database3.rawQuery(selectQuery3, null);
                    if (cursor3.moveToFirst()) {

                        map.put(COL_Location_Name, cursor3.getString(1));
                        map.put(COL_Moo, cursor3.getString(2));
                        map.put(COL_Tumbon, cursor3.getString(3));
                        map.put(COL_Amphur, cursor3.getString(4));
                        map.put(COL_Province, cursor3.getString(5));
                        map.put(COL_Post_Code, cursor3.getString(6));

                    }


                }
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    public String UploadSurvey(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_survey + " where " + COL_Survey_Status +" = '1'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COL_Survey_ID, cursor.getString(0));
                map.put(COL_D_M_Y_Survey, cursor.getString(cursor.getColumnIndex(COL_D_M_Y_Survey)));
                map.put(COL_Time_Survey, cursor.getString(2));
                map.put(COL_Temp, cursor.getString(3));
                map.put(COL_Moisture, cursor.getString(4));
                map.put(COL_Rain, cursor.getString(5));
                map.put(COL_Light, cursor.getString(6));
                map.put(COL_Dew, cursor.getString(7));
                map.put(COL_Category, cursor.getString(8));
                map.put(COL_SamplePoint, cursor.getString(9));
                map.put(COL_Point, cursor.getString(10));

                map.put(COL_Incidence, cursor.getString(11));
                map.put(COL_Severity, cursor.getString(12));

                String farmingID = cursor.getString(cursor.getColumnIndex(COL_FarmingID)) ;
                String selectQuery2 = "SELECT * FROM " + TABLE_farming + " where " + COL_Farming_ID +" = '" + farmingID +"'" ;
                SQLiteDatabase database2 = this.getWritableDatabase();
                Cursor cursor2 = database2.rawQuery(selectQuery2, null);
                if (cursor2.moveToFirst()) {

                    map.put(COL_D_M_Y_Farming, cursor2.getString(1));
                    String gardenID = cursor2.getString(cursor2.getColumnIndex(COL_GardenID));
                    String selectQuery3 = "SELECT * FROM " + TABLE_garden_survey + " where " + COL_Garden_ID +" = '" + gardenID +"'" ;
                    SQLiteDatabase database3 = this.getWritableDatabase();
                    Cursor cursor3 = database3.rawQuery(selectQuery3, null);
                    if(cursor3.moveToFirst()){

                        map.put(COL_Garden_Name, cursor3.getString(1));
                        map.put(COL_Latitude, cursor3.getString(2));
                        map.put(COL_Longitude, cursor3.getString(3));
                        map.put(COL_Level_sea, cursor3.getString(4));
                        map.put(COL_Garden_Size, cursor3.getString(5));

                        String locationID = cursor3.getString(cursor3.getColumnIndex(COL_LocationID));
                        String selectQuery4 = "SELECT * FROM " + TABLE_location_survey + " where " + COL_Location_ID +" = '" + locationID +"'" ;
                        SQLiteDatabase database4 = this.getWritableDatabase();
                        Cursor cursor4 = database4.rawQuery(selectQuery4, null);
                        if(cursor4.moveToFirst()){

                            map.put(COL_Location_Name, cursor4.getString(1));
                            map.put(COL_Moo, cursor4.getString(2));
                            map.put(COL_Tumbon, cursor4.getString(3));
                            map.put(COL_Amphur, cursor4.getString(4));
                            map.put(COL_Province, cursor4.getString(5));
                            map.put(COL_Post_Code, cursor4.getString(6));



                        }


                    }

                    String plantID = cursor2.getString(cursor2.getColumnIndex(COL_PlantID));
                    String selectQuery5 = "SELECT * FROM " + TABLE_plant + " where " + COL_Plant_ID +" = '" + plantID +"'" ;
                    SQLiteDatabase database5 = this.getWritableDatabase();
                    Cursor cursor5 = database5.rawQuery(selectQuery5, null);
                    if(cursor5.moveToFirst()){
                        map.put(COL_Plant_IDServer,cursor5.getString(1)) ;
                    }


                }
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);

    }

    public String UploadImage(){
        ArrayList<HashMap<String, String>> wordList;
        String encoded_string ;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_image + " where " + COL_Image_Status +" = '1'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();

                map.put(COL_Image_ID, cursor.getString(0));
                map.put(COL_Image_Type, cursor.getString(2));
                map.put(COL_Note, cursor.getString(3));
                map.put(COL_Sample_ID, cursor.getString(4));


                Log.d("imageID", cursor.getString(0));

                byte[] byteArray = cursor.getBlob(1);
                encoded_string = Base64.encodeToString(byteArray, 0);
                map.put(COL_Image_Pic, encoded_string);

                String SurveyID = cursor.getString(5);
                String selectQuery2 = "SELECT * FROM " + TABLE_survey + " where " + COL_Survey_ID + " = '" + SurveyID + "'";
                SQLiteDatabase database2 = this.getWritableDatabase();
                Cursor cursor2 = database2.rawQuery(selectQuery2, null);
                if (cursor2.moveToFirst()) {

                    map.put(COL_D_M_Y_Survey, cursor2.getString(cursor2.getColumnIndex(COL_D_M_Y_Survey)));
                    map.put(COL_Time_Survey, cursor2.getString(2));
                    map.put(COL_Temp, cursor2.getString(3));
                    map.put(COL_Moisture, cursor2.getString(4));
                    map.put(COL_Rain, cursor2.getString(5));
                    map.put(COL_Light, cursor2.getString(6));
                    map.put(COL_Dew, cursor2.getString(7));
                    map.put(COL_Category, cursor2.getString(8));
                    map.put(COL_SamplePoint, cursor2.getString(9));
                    map.put(COL_Point, cursor2.getString(10));

                    map.put(COL_Incidence, cursor2.getString(11));
                    map.put(COL_Severity, cursor2.getString(12));

                    String farmingID = cursor2.getString(cursor2.getColumnIndex(COL_FarmingID));
                    String selectQuery3 = "SELECT * FROM " + TABLE_farming + " where " + COL_Farming_ID + " = '" + farmingID + "'";
                    SQLiteDatabase database3 = this.getWritableDatabase();
                    Cursor cursor3 = database3.rawQuery(selectQuery3, null);
                    if (cursor3.moveToFirst()) {

                        map.put(COL_D_M_Y_Farming, cursor3.getString(1));
                        String gardenID = cursor3.getString(cursor3.getColumnIndex(COL_GardenID));
                        String selectQuery4 = "SELECT * FROM " + TABLE_garden_survey + " where " + COL_Garden_ID + " = '" + gardenID + "'";
                        SQLiteDatabase database4 = this.getWritableDatabase();
                        Cursor cursor4 = database4.rawQuery(selectQuery4, null);
                        if (cursor4.moveToFirst()) {

                            map.put(COL_Garden_Name, cursor4.getString(1));
                            map.put(COL_Latitude, cursor4.getString(2));
                            map.put(COL_Longitude, cursor4.getString(3));
                            map.put(COL_Level_sea, cursor4.getString(4));
                            map.put(COL_Garden_Size, cursor4.getString(5));

                            String locationID = cursor4.getString(cursor4.getColumnIndex(COL_LocationID));
                            String selectQuery5 = "SELECT * FROM " + TABLE_location_survey + " where " + COL_Location_ID + " = '" + locationID + "'";
                            SQLiteDatabase database5 = this.getWritableDatabase();
                            Cursor cursor5 = database5.rawQuery(selectQuery5, null);
                            if (cursor5.moveToFirst()) {

                                map.put(COL_Location_Name, cursor5.getString(1));
                                map.put(COL_Moo, cursor5.getString(2));
                                map.put(COL_Tumbon, cursor5.getString(3));
                                map.put(COL_Amphur, cursor5.getString(4));
                                map.put(COL_Province, cursor5.getString(5));
                                map.put(COL_Post_Code, cursor5.getString(6));


                            }


                        }

                        String plantID = cursor3.getString(cursor3.getColumnIndex(COL_PlantID));
                        String selectQuery6 = "SELECT * FROM " + TABLE_plant + " where " + COL_Plant_ID + " = '" + plantID + "'";
                        SQLiteDatabase database6 = this.getWritableDatabase();
                        Cursor cursor6 = database6.rawQuery(selectQuery6, null);
                        if (cursor6.moveToFirst()) {
                            map.put(COL_Plant_IDServer, cursor6.getString(1));
                        }


                    }
                }
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);

    }



    public void updateSyncStatusLocation(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update "+ TABLE_location_survey +  " set "+ COL_Location_Status +"= '"+ status +"' where "+  COL_Location_ID+ " = '"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void updateSyncStatusGarden(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update "+ TABLE_garden_survey +  " set "+ COL_Garden_Status +"= '"+ status +"' where "+  COL_Garden_ID+ " = '"+ id +"'";
        Log.d("query2",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void updateSyncStatusFarming(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update "+ TABLE_farming +  " set "+ COL_Farming_Status +"= '"+ status +"' where "+  COL_Farming_ID+ " = '"+ id +"'";
        Log.d("query3",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void updateSyncStatusSurvey(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update "+ TABLE_survey +  " set "+ COL_Survey_Status +"= '"+ status +"' where "+  COL_Survey_ID+ " = '"+ id +"'";
        Log.d("query4",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void updateSyncStatusImage(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update "+ TABLE_image +  " set "+ COL_Image_Status +"= '"+ status +"' where "+  COL_Image_ID+ " = '"+ id +"'";
        Log.d("query5",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }




    public int CountGarden(Long locationid){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        Long id = locationid;
        int Count = 0;

        query = "SELECT * FROM " + TABLE_garden_survey + " WHERE " + COL_LocationID + " = " + locationid;

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Count++;
            } while (cursor.moveToNext());
        }

        return Count;
    }


    public int CountFarming(Long gardenid){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        Long id = gardenid;
        int Count = 0;

        query = "SELECT * FROM " + TABLE_farming + " WHERE " + COL_GardenID + " = " + gardenid;

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Count++;
            } while (cursor.moveToNext());
        }

        return Count;
    }


    public int CountSurvey(Long farmingid){
        SQLiteDatabase db = this.getWritableDatabase();
        int selectID;
        String query;
        Long id = farmingid;
        int Count = 0;

        query = "SELECT * FROM " + TABLE_survey + " WHERE " + COL_FarmingID + " = " + farmingid;

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Count++;
            } while (cursor.moveToNext());
        }

        return Count;
    }













}