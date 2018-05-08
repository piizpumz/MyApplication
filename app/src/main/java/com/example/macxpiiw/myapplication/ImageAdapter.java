package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Image> mImageList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView imageserveyIDTxtV;
        public TextView imagesampleIDTxtV;
        public TextView imageTypeTxtV;
        public TextView imageNoteTxtV;
        public ImageView imageViewTxtV;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageserveyIDTxtV = (TextView) v.findViewById(R.id.surveyId);
            imagesampleIDTxtV = (TextView) v.findViewById(R.id.sampleId);
            imageTypeTxtV = (TextView) v.findViewById(R.id.type);
            imageNoteTxtV = (TextView) v.findViewById(R.id.note);
            imageViewTxtV = (ImageView) v.findViewById(R.id.imageView);

        }
    }

    public void add(int position , Image image){
        mImageList.add(position , image);
        notifyItemInserted(position);
    }

    public void remove(int position){
        mImageList.remove(position);
        notifyItemRemoved(position);
    }


    public ImageAdapter(List<Image> myDataset , Context context , RecyclerView recyclerView ){
        mImageList =myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_image_row , parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Image image = mImageList.get(position);
        byte[] ImagePic = image.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(ImagePic , 0 , ImagePic.length);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG , 100 , bytes);



        holder.imageserveyIDTxtV.setText("การสำรวจที่ : " + image.getSurveyID());
        holder.imagesampleIDTxtV.setText("ตัวอย่างที่ : " + image.getSampleID());
        holder.imageTypeTxtV.setText("ประเภท : " + image.getType());
        holder.imageNoteTxtV.setText("คำอธิบาย : " + image.getNote());
        holder.imageViewTxtV.setImageBitmap(bmp);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
                builder.setMessage("ต้องการจะลบหรือแก้ไข รูปภาพ?");
                builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToUpdateImage(image.getId());

                    }
                });
                builder.setNeutralButton("ลบ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog , int whiich) {

                        DBHelper dbHelper = new DBHelper(mContext);
                        dbHelper.delectImageRecord(image.getId(),mContext);

                        mImageList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mImageList.size());
                        notifyDataSetChanged();


                    }
                });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.create().show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }


    private void goToUpdateImage(long imageId){
        Intent goToupdate = new Intent(mContext, UpdateImageRecord.class);
        goToupdate.putExtra("Image_ID" , imageId);
        mContext.startActivity(goToupdate);
    }





}
