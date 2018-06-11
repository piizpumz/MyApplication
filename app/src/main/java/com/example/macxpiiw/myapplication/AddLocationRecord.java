package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

import com.example.macxpiiw.myapplication.location_model.province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class AddLocationRecord extends AppCompatActivity {

    private Spinner spinprovince , spinneramphur , spinnertumbon;
    private EditText mlocationName , mmoo , mtumbon , mamphur , mprovince , mpostcode;
    private Button btnAddData;
    private DBHelper dbHelper;

    private String province_name;
    private String amphur_name;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_record);
        getSupportActionBar().setTitle("เพิ่มพื่นที่สำรวจ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mlocationName = (EditText)findViewById(R.id.addLocation_Name);
        mmoo = (EditText)findViewById(R.id.addMoo);
//        mtumbon = (EditText)findViewById(R.id.addTumbon);
//        mamphur = (EditText)findViewById(R.id.addAmphur);
        mpostcode = (EditText)findViewById(R.id.addPost_Code);
        btnAddData = (Button)findViewById(R.id.btn_addlocation);
        spinprovince = (Spinner)findViewById(R.id.spinner_province);
        spinneramphur = (Spinner) findViewById(R.id.spinner_Amphur);
        spinnertumbon = (Spinner) findViewById(R.id.spinner_Tumbon);


        ArrayList<String> listprovince = get_province();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_dropdown_item_1line , listprovince );
        spinprovince.setAdapter(adapter);





        spinprovince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                province_name = spinprovince.getSelectedItem().toString().trim();

                ArrayList<String> listamphur = get_amphur(province_name);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddLocationRecord.this , android.R.layout.simple_dropdown_item_1line , listamphur );
                spinneramphur.setAdapter(adapter1);;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinneramphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                amphur_name = spinneramphur.getSelectedItem().toString().trim();
                ArrayList<String> list_tumbon = get_tumbon(amphur_name);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddLocationRecord.this , android.R.layout.simple_dropdown_item_1line , list_tumbon);
                spinnertumbon.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocaion();
            }
        });

    }

    private void saveLocaion(){
        Boolean Empty = true;
        String location_name = mlocationName.getText().toString().trim();
        String moo = mmoo.getText().toString().trim();
        String tumbon = spinnertumbon.getSelectedItem().toString().trim();
        String amphur = spinneramphur.getSelectedItem().toString().trim();
        String province = spinprovince.getSelectedItem().toString().trim();
        String postcode = mpostcode.getText().toString().trim();
        String status = null;


       dbHelper = new DBHelper(this);

        if(location_name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "กรุณากรอกชื่อหมู่บ้าน", Toast.LENGTH_SHORT).show();
            Empty = false;
        }

        if (Empty) {
            Location locaion = new Location(location_name, moo, tumbon, amphur, province, postcode , status);

            Boolean check = dbHelper.cheak_location(locaion);
            if(check) {
                dbHelper.saveNewLocation(locaion);
                finish();
            }
            else {
                Toast.makeText(this, "มีชื่อหมู่บ้านนี้อยู่แล้ว", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public ArrayList<String> get_province(){
        String json;
        ArrayList<String> Province = new ArrayList<>();

        try {
            InputStream is = getAssets().open("province.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer , "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Log.d("555" , "eiei");
//            ObjectMapper mapper = new ObjectMapper();
//            List<province> myObjects = mapper.readValue(json, new TypeReference<List<province>>(){});
//            Log.d("555" , String.valueOf(myObjects.size()));
//
//            for(int i =0  ; i < myObjects.size() ; i++){
//                Log.d("555" , String.valueOf(myObjects.get(i).getId()));
//
//            }

            for(int i = 0; i < jsonArray.length(); i++ )
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                Province.add(obj.getString("name"));
            }

        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Province;
    }

    public ArrayList<String> get_amphur(String province){
        String json;
        String json_province = get_provinceID(province);
        ArrayList<String> Proince = new ArrayList<>();
        ArrayList<String> Amphur = new ArrayList<>();

        try {
            InputStream is = getAssets().open("amphur.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer , "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

//            ObjectMapper mapper = new ObjectMapper();
//            List<province> myObjects = mapper.readValue(json, new TypeReference<List<province>>(){});
//            Log.d("555" , String.valueOf(myObjects.size()));
//
//            for(int i =0  ; i < myObjects.size() ; i++){
//                Log.d("555" , String.valueOf(myObjects.get(i).getId()));
//
//            }

            Log.d("provinceID" , "eiei" + json_province);

            for(int i = 0; i < jsonArray.length(); i++ )
            {
                JSONObject obj = jsonArray.getJSONObject(i);

                if(obj.getString("changwat_pid").equals(json_province))
                {
                    Amphur.add(obj.getString("name"));
                }

            }




        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Amphur;


    }

    public ArrayList<String> get_tumbon(String amphur){
        String json;
        String json_amphur = get_amphurID(amphur);
        Log.d("json_amphur" , json_amphur);
        ArrayList<String> Tumbon = new ArrayList<>();

        try {
            InputStream is = getAssets().open("tumbon.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer , "UTF-8");
            JSONArray jsonArray = new JSONArray(json);


            for(int i = 0; i < jsonArray.length(); i++ )
            {
                JSONObject obj = jsonArray.getJSONObject(i);

                if(obj.getString("amphoe_pid").equals(json_amphur))
                {
                    Tumbon.add(obj.getString("name"));
                    Log.d("tumbon" , String.valueOf(Tumbon));
                }

            }


        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Tumbon;

    }


    public String get_provinceID(String ProvinceName){
        String json;
        String ProvinceID = "";

        try {
            InputStream is = getAssets().open("province.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer , "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Log.d("555" , "eiei");
//

            for(int i = 0; i < jsonArray.length(); i++ )
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.getString("name").equals(ProvinceName))
                {
                    ProvinceID = obj.getString("pid");
                }


            }





        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ProvinceID;
    }

    public String get_amphurID(String AmphurName){
        String json;
        String AmphurID = "";

        try {
            InputStream is = getAssets().open("amphur.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer , "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Log.d("555" , "eiei");
//

            for(int i = 0; i < jsonArray.length(); i++ )
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.getString("name").equals(AmphurName))
                {
                    AmphurID = obj.getString("pid");
                }


            }

        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return AmphurID;

    }



}
