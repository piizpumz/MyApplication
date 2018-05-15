package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.macxpiiw.myapplication.Location;

import java.util.List;

/**
 * Created by macxpiiw on 3/4/2018 AD.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<Location> mLocationList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView locationLocation_NameTxtV;
        public TextView locationMooTxtV;
        public TextView locationTumbonTxtV;
        public TextView locationAmphurTxtV;
        public TextView locationProvinceTxtV;
        public TextView locationPostCodeTxtV;
        public CardView cardView;
        private Button btn1;
        private Button btn2;
        private Button btn3;



        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout =v;
            locationLocation_NameTxtV = (TextView) v.findViewById(R.id.locationname);
            locationMooTxtV = (TextView) v.findViewById(R.id.moo);
            locationTumbonTxtV = (TextView) v.findViewById(R.id.tumbon);
            locationAmphurTxtV = (TextView) v.findViewById(R.id.amphur);
            locationProvinceTxtV = (TextView) v.findViewById(R.id.province);
//          locationPostCodeTxtV = (TextView) v.findViewById(R.id.postcode);
            cardView = (CardView) v.findViewById(R.id.cardView);
            btn1= (Button) v.findViewById(R.id.btn1);
            btn2= (Button) v.findViewById(R.id.btn2);
            btn3= (Button) v.findViewById(R.id.btn3);





        }
    }

    public void add(int position, Location location){
        mLocationList.add(position , location);
        notifyItemInserted(position);
    }

    public void remove(int position){
        mLocationList.remove(position);
        notifyItemRemoved(position);
    }

    public LocationAdapter(List<Location> myDataset , Context context , RecyclerView recyclerView){
        mLocationList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @Override
    public void onBindViewHolder(LocationAdapter.ViewHolder holder, final int position) {
        final Location location = mLocationList.get(position);

        DBHelper dbHelper = new DBHelper(mContext);
        int Count = dbHelper.CountGarden(location.getId());

        holder.locationLocation_NameTxtV.setText("ชื่อหมู่บ้าน : " + location.getLocation_name() + " (" + Count + " แปลง)");
        holder.locationMooTxtV.setText("หมู่ที่ : " + location.getMoo());
        holder.locationTumbonTxtV.setText("ตำบล : " + location.getTumbon());
        holder.locationAmphurTxtV.setText("อำเภอ : "+ location.getAmphur());
        holder.locationProvinceTxtV.setText("จังหวัด : "+ location.getProvince());


        holder.btn2.setVisibility(View.INVISIBLE);
        holder.btn3.setVisibility(View.INVISIBLE);
        holder.cardView.setCardBackgroundColor(Color.parseColor("#CCCCCC"));


//
        if (location.getStatus()!=null) {
            if (location.getStatus().equals("1")) {

                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.btn1.setVisibility(View.VISIBLE);
                holder.btn2.setVisibility(View.VISIBLE);
                holder.btn3.setVisibility(View.VISIBLE);
            }
        }


                holder.btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToAddGarden(location.getId());
                    }
                });

                holder.btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToUpdateLocation(location.getId());
                    }
                });

                holder.btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("ยืนยันการลบ");
                        builder.setMessage("ท่าแแน่ใจที่จะลบ " + location.getLocation_name() + " หรือไม่ ?");

                        builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("ยืนยน", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper dbHelper = new DBHelper(mContext);
                                dbHelper.deleteLocationRecord(location.getId(), mContext);
                                mLocationList.remove(position);
                                mRecyclerV.removeViewAt(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mLocationList.size());
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });


//        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
//
//
//            @Override
//            public boolean onLongClick(View view)  {
//                final Dialog dialog = new Dialog(mContext);
//                dialog.setContentView(R.layout.custom_dialog_location);
//                dialog.setCancelable(true);
//                TextView DialogOption0 = (TextView) dialog.findViewById(R.id.option0);
//                Button DialogOption1 = (Button) dialog.findViewById(R.id.option1);
//                Button DialogOption2 = (Button) dialog.findViewById(R.id.option2);
//                Button DialogOption3 = (Button) dialog.findViewById(R.id.option3);
//                final Button DialogOption4 = (Button) dialog.findViewById(R.id.option4);
//
//
//                DialogOption0.setText("หมู่บ้าน : " + location.getLocation_name());
//
//                DialogOption1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        goToAddGarden(location.getId());
//                        dialog.dismiss();
//                    }
//                });
//
//                DialogOption2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        goToUpdateLocation(location.getId());
//                        dialog.dismiss();
//                    }
//                });
//
//                DialogOption3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        final AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
//                        builder.setTitle("ยืนยันการลบ");
//                        builder.setMessage("ท่าแแน่ใจที่จะลบพื้นที่สำรวจนี้หรือไม่ ?");
//
//                        builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//
//
//                            }
//                        });
//
//                        builder.setNegativeButton("ยืนยน", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                DBHelper dbHelper = new DBHelper(mContext);
//                        dbHelper.deleteLocationRecord(location.getId(),mContext);
//                        mLocationList.remove(position);
//                        mRecyclerV.removeViewAt(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, mLocationList.size());
//                        notifyDataSetChanged();
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.create().show();
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//                return false;
//            }
//        });

    }

    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_location_row, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    private void goToUpdateLocation(long locationId){
        Intent goToupdate = new Intent(mContext, UpdateLocationRecord.class);
        goToupdate.putExtra("Location_ID" , locationId);
        mContext.startActivity(goToupdate);
    }

    private void goToAddGarden(long locationId){
        Intent gotoAdd = new Intent(mContext, AddGardenRecord.class);
        gotoAdd.putExtra("Location_ID" , locationId);
        mContext.startActivity(gotoAdd);
    }

    private void goToGarden(long locationId){
        Intent gotoGarden = new Intent(mContext , GardenPage.class);
        gotoGarden.putExtra("Location_ID" , locationId);
        mContext.startActivity(gotoGarden);
    }

    @Override
    public int getItemCount() {
        return  mLocationList.size();
    }


}
