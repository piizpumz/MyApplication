package com.example.macxpiiw.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
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

public class AddImageRecord extends AppCompatActivity {
    private ImageView PicView;
    private Spinner addType;
    private Spinner addDisease;
    private EditText addSurveyID;
    private EditText addSampleID;

    private EditText addNote ;
    private Button btn_add_Image , btn_choose;
    public DBHelper dbHelper;
    private long receivedSurveyId;


    public byte[] lastImage ;


    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_record);
        getSupportActionBar().setTitle("เพิ่มรูปภาพ");

        PicView = (ImageView)findViewById(R.id.image_picView);
//      addSurveyID = (EditText)findViewById(R.id.addSurveyID);
        addSampleID=(EditText)findViewById(R.id.addSampleID);
        addType = (Spinner) findViewById(R.id.spinner_type_image);
        addDisease = (Spinner) findViewById(R.id.spinner_disease);
        addNote =(EditText)findViewById(R.id.addNote);
        btn_add_Image = (Button)findViewById(R.id.btn_addImage);
        btn_choose = (Button)findViewById(R.id.btn_choosePic);


        try{
            receivedSurveyId =getIntent().getLongExtra("SURVEY_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }



        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AddImageRecord.this);
                builder.setTitle("Choose option");
                builder.setMessage("Select from Gallery or Camera");
                builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                                AddImageRecord.this,
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



        btn_add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    saveImage();
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

    private void saveImage(){

        Long surveyID = receivedSurveyId;
        String sampleID = addSampleID.getText().toString().trim();
        String type = addType.getSelectedItem().toString().trim();
        String note = addNote.getText().toString().trim();
        byte[] imageview = lastImage ;
        dbHelper = new DBHelper(this);
        Image image = new Image(surveyID ,sampleID, type, note , imageview );
        dbHelper.saveNewImage(image);
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
        startActivity(new Intent(AddImageRecord.this, SurveyPage.class));
    }


    public void realImage(Bitmap bitmap2){

        lastImage = imageViewtoByte(bitmap2);

    }

}