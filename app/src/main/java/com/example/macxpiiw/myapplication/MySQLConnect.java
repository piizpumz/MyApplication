package com.example.macxpiiw.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mynewww on 12/3/2018 AD.
 */

public class MySQLConnect {
    private final Activity main ;
    private List<String> list ;
    private List<JSONObject> jsonList;
    private String URL = "http://158.108.144.4/RDSSATCC/", GET_URL = "sp_61_DiseaseSurvey/testdb.php",GET_URL2 = "sp_61_DiseaseSurvey/testdb2.php",GET_URL3 = "sp_61_DiseaseSurvey/db_connection.php" ;

    public MySQLConnect() {

        main = null ;


    }
    public MySQLConnect(Activity mainA){

        main = mainA ;
        list = new ArrayList<String>() ;
        jsonList = new ArrayList<JSONObject>();

    }
    public List<JSONObject> getData(final int x){

        String url = "" ;

        if(x==1) {
            url = URL + GET_URL;
        }
        else if(x==2){
            url = URL + GET_URL2;
        }
        else if(x==3){
            url = URL + GET_URL3;
        }


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(main, "กรุณาเชื่อมต่ออินเตอร์เน็ต", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder =  new AlertDialog.Builder(main);
                builder.setMessage("อินเตอร์เน็ตหรือเซิฟเวอร์มีปัญหาไม่สามารถเชื่อมต่อได้");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                            Intent relocation = new Intent(main, MainActivity.class);
                            relocation.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            relocation.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            main.startActivity(relocation);


                    }
                });

                builder.create().show();


            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(main.getApplicationContext()) ;
        requestQueue.add(stringRequest);


//        return list ;
        return jsonList;
    }

    public void showJSON(String  response) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {


                JSONObject collectData = result.getJSONObject(i);

                jsonList.add(collectData);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }


    }

}
