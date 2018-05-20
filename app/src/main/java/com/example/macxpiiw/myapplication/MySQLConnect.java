package com.example.macxpiiw.myapplication;

import android.app.Activity;
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
    private String URL = "http://158.108.144.4/RDSSATCC/", GET_URL = "sp_61_DiseaseSurvey/testdb.php",GET_URL2 = "sp_61_DiseaseSurvey/testdb2.php",GET_URL3 = "sp_61_DiseaseSurvey/testdb3.php" ;

    public MySQLConnect() {

        main = null ;


    }
    public MySQLConnect(Activity mainA){

        main = mainA ;
        list = new ArrayList<String>() ;
        jsonList = new ArrayList<JSONObject>();

    }
    public List<JSONObject> getData(int x){

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
                Toast.makeText(main, "กรุณาเชื่อมต่ออินเตอร์เน็ต", Toast.LENGTH_LONG).show();

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


    public String sentLogin(String user,String pass){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Username", user);
        map.put("Password", pass);

        wordList.add(map);



        Gson gson = new GsonBuilder().create();

        Log.d("wordList", String.valueOf(wordList));

        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }


    public String checkLogin(String status){

        Log.d("teststatus", status);
        if(status=="1"){
            return  "true" ;
        }else{
            return "false" ;
        }

    }
}
