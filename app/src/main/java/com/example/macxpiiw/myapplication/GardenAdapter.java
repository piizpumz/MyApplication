package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by macxpiiw on 3/14/2018 AD.
 */

public class GardenAdapter extends RecyclerView.Adapter<GardenAdapter.ViewHolder>{
    private List<Garden> mGardenList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private DBHelper dbHelper ;



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView gardenGardenNameTxtV;
        public TextView gardenLongitudeTxtV;
        public TextView gardenLatitudeTxtV;
        public TextView gardenLevelseaTxtV;
        public TextView gardenGardenSizeTxtV;
        public TextView gardenLocationTxtV;
        public CardView cardView;
        private Button btn1;
        private Button btn2;
        private Button btn3;



        public View layout;





        public ViewHolder(View v) {
            super(v);
            layout =v;

            gardenGardenNameTxtV = (TextView) v.findViewById(R.id.gardenname);
            gardenLongitudeTxtV = (TextView) v.findViewById(R.id.longitude);
            gardenLatitudeTxtV = (TextView) v.findViewById(R.id.latitude);
            gardenLevelseaTxtV = (TextView) v.findViewById(R.id.levelsea);
            gardenGardenSizeTxtV = (TextView) v.findViewById(R.id.gardensize);
            gardenLocationTxtV = (TextView) v.findViewById(R.id.locationid);

            cardView = (CardView) v.findViewById(R.id.cardView);

            btn1= (Button) v.findViewById(R.id.btn1);
            btn2= (Button) v.findViewById(R.id.btn2);
            btn3= (Button) v.findViewById(R.id.btn3);
        }
    }

    public void add(int position, Garden garden ) {
        mGardenList.add(position, garden);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mGardenList.remove(position);
        notifyItemRemoved(position);
    }




    public GardenAdapter(List<Garden> myDataset , Context context , RecyclerView recyclerView){
        mGardenList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;


    }

    @Override
    public GardenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_garden_row, parent , false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Garden garden = mGardenList.get(position);

        DBHelper dbHelper = new DBHelper(mContext);
        String NameLocation = dbHelper.getNameLocation(position , garden);
        String tumbonLocation = dbHelper.getTumbonLocation(position , garden);

        int Count = dbHelper.CountFarming(garden.getId());


        holder.gardenGardenNameTxtV.setText("ชื่อเกษตรกร : " + garden.getGarden_name() + " (" + Count + " เพาะปลูก)");
        holder.gardenLocationTxtV.setText("หมู่บ้าน : " + NameLocation + " (ตำบล:" + tumbonLocation +")" );
        holder.gardenLongitudeTxtV.setText("ลองติจูด : " + garden.getLongitude());
        holder.gardenLatitudeTxtV.setText("ละติจูด : " + garden.getLatitude());
        holder.gardenGardenSizeTxtV.setText("พื้นที่ระบาด : " + garden.getGarden_size());
        holder.gardenLevelseaTxtV.setText("ความสูงจากน้ำทะเล : " + garden.getLevel_sea());


        holder.btn2.setVisibility(View.INVISIBLE);
        holder.btn3.setVisibility(View.INVISIBLE);
        holder.cardView.setCardBackgroundColor(Color.parseColor("#CCCCCC"));


//
        if (garden.getGarden_satatus()!=null) {
            if (garden.getGarden_satatus().equals("1")) {

                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.btn1.setVisibility(View.VISIBLE);
                holder.btn2.setVisibility(View.VISIBLE);
                holder.btn3.setVisibility(View.VISIBLE);
            }
        }


        holder.btn1.setVisibility(Button.VISIBLE);

        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddFarming(garden.getId());
            }
        });

        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUpdateGarden(garden.getId());
            }
        });

        holder.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
                builder.setTitle("ยืนยันการลบ");
                builder.setMessage("ท่าแแน่ใจที่จะลบแปลง " +garden.getGarden_name()+ " หรือไม่ ?");

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
                        dbHelper.deleteGardenRecord(garden.getId(),mContext);
                        mGardenList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mGardenList.size());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("ท่าแแน่ใจที่จะลบแปลง " +garden.getGarden_name()+ " หรือไม่ ?");
                builder.setPositiveButton("ลบ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
                        builder.setTitle("ยืนยันการลบ");
                        builder.setMessage("ท่าแแน่ใจที่จะลบแปลง " +garden.getGarden_name()+ " หรือไม่ ?");

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
                                dbHelper.deleteGardenRecord(garden.getId(),mContext);
                                mGardenList.remove(position);
                                mRecyclerV.removeViewAt(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mGardenList.size());
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                    }
                });

                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.create().show();

                return false;
            }
        });



//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog(mContext);
////                dialog.setTitle("แปลง : " + garden.getGarden_name());
//                dialog.setContentView(R.layout.custom_dialog_garden);
//                dialog.setCancelable(true);
//                TextView DialogOption0 = (TextView) dialog.findViewById(R.id.option0);
//                Button DialogOption1 = (Button) dialog.findViewById(R.id.option1);
//                Button DialogOption2 = (Button) dialog.findViewById(R.id.option2);
//                Button DialogOption3 = (Button) dialog.findViewById(R.id.option3);
//
//
//                DialogOption0.setText("แปลง : " + garden.getGarden_name());
//
//                DialogOption1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        goToAddFarming(garden.getId());
//                        dialog.dismiss();
//                    }
//                });
//
//                DialogOption2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        gotoUpdateGarden(garden.getId());
//                        dialog.dismiss();
//                    }
//                });
//
//                DialogOption3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        final AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
//                        builder.setTitle("ยืนยันการลบ");
//                        builder.setMessage("ท่าแแน่ใจที่จะลบแปลงนี้หรือไม่ ?");
//
//                        builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        builder.setNegativeButton("ยืนยน", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                DBHelper dbHelper = new DBHelper(mContext);
//                                dbHelper.deleteGardenRecord(garden.getId(),mContext);
//                                mGardenList.remove(position);
//                                mRecyclerV.removeViewAt(position);
//                                notifyItemRemoved(position);
//                                notifyItemRangeChanged(position, mGardenList.size());
//                                notifyDataSetChanged();
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.create().show();
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//            }
//        });


    }




    private void gotoUpdateGarden(long gardenId){
        Intent goToUpdate = new Intent(mContext, UpdateGardenRecord.class);
        goToUpdate.putExtra("Garden_ID", gardenId);
        mContext.startActivity(goToUpdate);
    }

    private void goToAddFarming(long gardenId){
        Intent gotoAdd = new Intent(mContext, AddFarming2.class);
        gotoAdd.putExtra("Garden_ID" , gardenId);
        mContext.startActivity(gotoAdd);
    }


    @Override
    public int getItemCount() {
        return mGardenList.size();
    }


}
