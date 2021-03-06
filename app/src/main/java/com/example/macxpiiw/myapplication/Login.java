package com.example.macxpiiw.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class Login extends AppCompatActivity{

    EditText user;
    EditText pass;
    CardView btn;
    private MySQLConnect mySQLConnect;

    String status = "0";

    String result = "0";

    String userlogin = "0" ;

    String userid = "0" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }


        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        btn = (CardView) findViewById(R.id.cardView);


        mySQLConnect = new MySQLConnect(this) ;
        mySQLConnect.getData(3);


        btn.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid= user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                new checklogin(userid,password).execute()  ;

//                Log.e("login", "..."+goUpload+"...");
//
//                if(goUpload.equals("admin")){
//
//                    Toast.makeText(Login.this, "Login OK  : ADMIN", Toast.LENGTH_SHORT).show();
//                    Intent newActivity = new Intent(Login.this,UploadPage.class);
//                    newActivity.putExtra("USERID" , userlogin);
//                    startActivity(newActivity);
//
//                }
//                else if(goUpload.equals("user")){
//
//                    Toast.makeText(Login.this, "Login OK  : USER", Toast.LENGTH_SHORT).show();
//                    Intent newActivity = new Intent(Login.this,UploadPage2.class);
//                    newActivity.putExtra("USERID" , userlogin);
//                    startActivity(newActivity);
//
//                }
//                else{
//
//                    Toast.makeText(Login.this, "กรอกรหัสผ่านผิดผลาด ", Toast.LENGTH_SHORT).show();
//
//                }
            }
        });
    }

    private String checkLogin(String Username,String Password){

        String url = "http://158.108.144.4/RDSSATCC/sp_61_DiseaseSurvey/login.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("Username", Username));

        params.add(new BasicNameValuePair("Password", Password));


        String resultServer  = getHttpPost(url,params);




        JSONObject c;

        try {

            c = new JSONObject(resultServer);
            status = c.getString("status");
            result = c.getString("result");
            userid = c.getString("UserID") ;
            userlogin = c.getString("Username");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(result == "pass" && status == "admin"){
            Log.e("Log", result+status+userlogin);

            return status ;



        }
        else if(result == "pass" && status == "user"){
            Log.e("Log2", result+status+userlogin);
            return status ;

        }

        else if(result == "pass" && status == "guest") {
            Log.e("Log2", result + status + userlogin);
            return status;
        }

        else {
            Log.e("Log3", result+status);

            return status ;

        }

    }


    public String getHttpPost(String url,List<NameValuePair> params) {

        StringBuilder str = new StringBuilder();

        HttpClient client = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);



        try {

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();

            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) { // Status OK

                HttpEntity entity = response.getEntity();

                InputStream content = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                String line;

                while ((line = reader.readLine()) != null) {

                    str.append(line);

                }

            } else {

                Log.e("Log", "Failed to download result..");

            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return str.toString();

    }


    public class checklogin extends AsyncTask<Void, Integer, String> {

        private final ProgressDialog dialog = new ProgressDialog(
                Login.this);

        String userid ;
        String password ;

        public checklogin(String userid, String password) {

            this.userid = userid ;
            this.password = password ;

        }


        protected void onPreExecute() {
            this.dialog.setTitle("กำลังเข้าสู่ระบบ");
            this.dialog.setMessage("กรุณารอสักครู่...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }





        protected void onPostExecute(String result) {

            Log.d("debug", String.valueOf(result));
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.equals("admin")||result.equals("user")){

                Toast.makeText(Login.this, "Login OK  : "+result, Toast.LENGTH_SHORT).show();
                Intent newActivity = new Intent(Login.this,UploadPage.class);
                newActivity.putExtra("USERID" , userlogin);
                startActivity(newActivity);

            }
            else if(result.equals("guest")){

                Toast.makeText(Login.this, "Login OK  : "+result, Toast.LENGTH_SHORT).show();
                Intent newActivity = new Intent(Login.this,UploadPage2.class);
                newActivity.putExtra("USERID" , userlogin);
                startActivity(newActivity);

            }
            else if(result.equals("fail")){

                Toast.makeText(Login.this, "กรอกรหัสผ่านผิดพลาด ", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(Login.this, "เกิดความผิดพลาด กรุณาลองใหม่ ", Toast.LENGTH_SHORT).show();

            }



        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("test",String.valueOf(values[0]));
            super.onProgressUpdate(values);
            Log.d("test",String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Void... voids) {



            return checkLogin(userid,password);
        }




    }






}
