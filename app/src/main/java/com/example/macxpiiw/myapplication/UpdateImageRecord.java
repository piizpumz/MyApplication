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

    private Long mSurveyIDText;
    private EditText mSampleIDText;
    private EditText mNoteText;
    private Spinner mTypeSpinner ;
    private ImageView mImageView;
    private Button btnChoosePic , btn_UpdateImage;
    private DBHelper dbHelper;
    private long receivedImageId;
    final int REQUEST_CODE_GALLERY = 999;
    Image image ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_record);
        getSupportActionBar().setTitle("แก้ไขรูปภาพ");

        mSampleIDText = (EditText) findViewById(R.id.UpdateSampleID);
        mTypeSpinner = (Spinner) findViewById(R.id.spinner_Update_type_image);
        mNoteText = (EditText) findViewById(R.id.UpdateNote);
        mImageView = (ImageView) findViewById(R.id.Updateimage_picView);
        btnChoosePic = (Button) findViewById(R.id.Updatebtn_choosePic);
        btn_UpdateImage = (Button) findViewById(R.id.btn_UpdateImage);

        dbHelper = new DBHelper(this);

        try{
            receivedImageId =getIntent().getLongExtra("Image_ID" , 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        Image queiedImage = dbHelper.getImage(receivedImageId);
        mSurveyIDText = queiedImage.getSurveyID();
        mSampleIDText.setText(queiedImage.getSampleID());
        mNoteText.setText(queiedImage.getNote());
        Log.d("ImageID", "--------"+receivedImageId);


        btn_UpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImage();
            }
        });



        btnChoosePic.setOnClickListener(new View.OnClickListener() {
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


    }

    public void updateImage(){

        Long surveyID = mSurveyIDText ;
        String sampleID = mSampleIDText.getText().toString().trim();
        String type = mTypeSpinner.getSelectedItem().toString().trim();
        String note = mNoteText.getText().toString().trim();
        byte[] imageview = imageViewtoByte(mImageView);
        String disease = "";
        String status = "";
        dbHelper = new DBHelper(this);

        Image updateimage = new Image(surveyID ,sampleID, type, note , imageview , disease , status );
        dbHelper.updateImageRecord(receivedImageId , this ,  updateimage);
        finish();
        goBackHome();
    }



    private byte[] imageViewtoByte(ImageView image){
        Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 , stream);
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
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    private void goBackHome(){
        startActivity(new Intent(UpdateImageRecord.this, ImagePage.class));
    }



}
