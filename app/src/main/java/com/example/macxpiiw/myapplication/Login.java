package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity{

    EditText user;
    EditText pass;
    CardView btn;
    private MySQLConnect mySQLConnect;


    String testlogin ="" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        btn = (CardView) findViewById(R.id.cardView);

        Log.d("checklogin", testlogin);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private void checkLogin(){
        String userid= String.valueOf(user.getText());
        String password = String.valueOf(pass.getText());


        if(true){
            startActivity(new Intent(Login.this, UploadPage.class));

        }
        else if(testlogin(userid,password) == "0"){
            Toast.makeText(this, "กรอกรหัสผ่านผิดผลาด "+testlogin(userid,password), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "มันพังไรก็ไม่รู้ "+testlogin(userid,password)+" พังหมดแล้ว ", Toast.LENGTH_SHORT).show();
        }
    }




    public String testlogin(String userid,String password) {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        params.put("loginJSON", sentLogin(userid,password));


        final String[] resultLogin = new String[1];
        resultLogin[0] = "" ;




        client.post("http://158.108.207.4/sp_61_DiseaseSurvey/login.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Log.d("teststatus55555", "กำลังตรวจสอบ");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"

                try {

                    JSONObject jsonObject = new JSONObject(new String(response));
                    JSONArray result = jsonObject.getJSONArray("result");
                    for(int i=0; i<result.length();i++){
                        JSONObject obj = (JSONObject)result.get(i);
                        resultLogin[i] = obj.get("status").toString();

                        testlogin = obj.get("status").toString() ;


                    }



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

        Log.d("testresult", resultLogin[0]);
        return resultLogin[0] ;

    }

    public String sentLogin(String Username,String Password){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Username", Username);
        map.put("Password", Password);

        wordList.add(map);



        Gson gson = new GsonBuilder().create();

        Log.d("wordList", String.valueOf(wordList));

        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }



}
