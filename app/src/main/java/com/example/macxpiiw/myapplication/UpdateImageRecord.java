package com.example.macxpiiw.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateImageRecord extends AppCompatActivity {


    private ImageView PicView;
    private EditText SampleID;
    private Spinner Type ;
    private Spinner Disease;
    private EditText Note;
    private Button btn_Update;
    private Button btn_choose;

    public DBHelper dbHelper;
    private long receivedImageId;


    public byte[] lastImage ;
    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_record);
        getSupportActionBar().setTitle("แก้ไขรูปภาพ");


        PicView = (ImageView) findViewById(R.id.image_picView);
        SampleID = (EditText) findViewById(R.id.UpdateSampleID);
        Type = (Spinner) findViewById(R.id.spinner_type_image);
        Disease = (Spinner) findViewById(R.id.spinner_disease);
        Note = (EditText) findViewById(R.id.UpdateNote);
        btn_choose = (Button) findViewById(R.id.btn_choosePic);
        btn_Update =  (Button) findViewById(R.id.btn_UpdateImage);



        dbHelper = new DBHelper(this);
        try{
            receivedImageId =getIntent().getLongExtra("Image_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        Image image = dbHelper.getImage(receivedImageId);
        final Long surveyid = image.getSurveyID();

        Bitmap bmp = BitmapFactory.decodeByteArray(image.getImage() , 0 , image.getImage().length);
        PicView.setImageBitmap(Bitmap.createScaledBitmap(bmp , 200 , 200 , false));
        SampleID.setText(image.getSampleID());
        Note.setText(image.getNote());


        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(UpdateImageRecord.this);
                builder.setTitle("Choose option");
                builder.setMessage("Select from Gallery or Camera");
                builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                                UpdateImageRecord.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_GALLERY
                        );

                    }
                });
                builder.setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog , int whiich) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,0);

                    }
                });

                builder.create().show();

            }
        });


        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    updateImage(surveyid);
                    Toast.makeText(getApplicationContext() , "บันทึกสำเร็จ!", Toast.LENGTH_SHORT).show();
                    goBackHome();


                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext() ," กรุณาใส่รูป  ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }

    public void updateImage(Long Idsurvey){


        byte[] imageview = lastImage ;
        Long surveyID = Idsurvey;
        String sampleID = SampleID.getText().toString().trim();
        String type = Type.getSelectedItem().toString().trim();
        String note = Note.getText().toString().trim();
        String disease = Disease.getSelectedItem().toString().trim();
        String diseaseid = nametoid(disease);
        String status = "";


        dbHelper = new DBHelper(this);
        Image updateimage = new Image(surveyID ,sampleID, type, note , imageview , diseaseid , status);
        dbHelper.updateImageRecord(receivedImageId , this ,  updateimage);
        finish();
        goBackHome();

    }

    private byte[] imageViewtoByte(Bitmap image){
        Bitmap bitmap = image ;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(), "You dont have permission to access fill ", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize=10; //decrease decoded image

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

//                InputStream inputStream2 = getContentResolver().openInputStream(uri);
//                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream2) ;
//                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);

                realImage(bitmap);


                PicView.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            realImage(bitmap);
            PicView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void goBackHome(){
        startActivity(new Intent(UpdateImageRecord.this, SurveyPage.class));
    }


    public void realImage(Bitmap bitmap2){

        lastImage = imageViewtoByte(bitmap2);

    }

    private String nametoid(String diseaseName){
        String id = "";
        if(diseaseName.equals("ไม่สามารถระบุชนิดโรค"))
        {
            id = "0";
        }
        if(diseaseName.equals("โรคไหม้ (Pyricularia oryzae)"))
        {
            id = "61";
        }
        if(diseaseName.equals("โรคเมล็ดด่าง (Curvularia lunata, Cercospora oryzae, Bipolaris oryzae , Fusarium semitectum , Trichoconis padwickii, Sarocladium oryzae)"))
        {
            id = "0";
        }
        if(diseaseName.equals("โรคกาบใบแห้ง (Rhizoctonia solani )"))
        {
            id = "0";
        }
        if(diseaseName.equals("โรคใบจุดสีน้ำตาล (Bipolaris oryzae)"))
        {
            id = "57";
        }
        if(diseaseName.equals("โรคกาบใบเน่า (Sarocladium oryzae Sawada)"))
        {
            id = "0";
        }
        if(diseaseName.equals("โรคถอดฝักดาบ (Fusarium fujikuroi Nirenberg)"))
        {
            id = "54";
        }
        if(diseaseName.equals("โรคใบวงสีน้ำตาล (Rhynocosporium oryzae Hashioka&Yokogi)"))
        {
            id = "59";
        }
        if(diseaseName.equals("โรคใบขีดสีน้ำตาล (Cercospora oryzae I. Miyake)"))
        {
            id = "56";
        }
        if(diseaseName.equals("โรคขอบใบแห้ง (Xanthomonas oryzae pv. oryzae)"))
        {
            id = "52";
        }
        if(diseaseName.equals("โรคใบขีดโปร่งแสง (Xanthomonas oryzae pv. oryzicola)"))
        {
            id = "55";
        }
        if(diseaseName.equals("โรคใบหงิก (Rice Ragged Stunt Virus)"))
        {
            id = "0";
        }
        if(diseaseName.equals("โรคเขียวเตี้ย (Rice Grassy Stunt Virus)"))
        {
            id = "53";
        }
        if(diseaseName.equals("โรคใบสีส้ม  (Rice Tungro Bacilliform Virus และ Rice Tungro Spherical Virus)"))
        {
            id = "60";
        }
        if(diseaseName.equals("โรคหูด (Rice Gall Dwarf Virus)"))
        {
            id = "64";
        }
        if(diseaseName.equals("โรคใบสีแสด (เชื้อไฟโตพลาสมา)"))
        {
            id = "0";
        }
        if(diseaseName.equals("โรคเหลืองเตี้ย  (เชื้อไฟโตพลาสมา)"))
        {
            id = "63";
        }
        if(diseaseName.equals("โรครากปม (Meloidogyne graminicola)"))
        {
            id = "66";
        }
        if(diseaseName.equals("โรคใบแถบแดง (Microbacterium sp. หรือ Gonatophragmium sp.)"))
        {
            id = "58";
        }

        return id;
    }













}
