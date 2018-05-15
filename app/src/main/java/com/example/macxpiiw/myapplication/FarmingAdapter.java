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

import java.util.List;


public class FarmingAdapter extends RecyclerView.Adapter<FarmingAdapter.ViewHolder> {
    private List<Farming> mFarmingList;
    private Context mContext;
    private RecyclerView mRecyclerV;



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView farmingFarmingIDTxtV;
        public TextView farmingDMY_FarmingTxtV;
        public TextView farmingGardenIDTxtV;
        public TextView farmingPlantIDTxtV;
        public CardView cardView;
        private Button btn1;
        private Button btn2;
        private Button btn3;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout=v;

            farmingFarmingIDTxtV = (TextView) v.findViewById(R.id.farming_id);
            farmingDMY_FarmingTxtV = (TextView) v.findViewById(R.id.dmy_farming);
            farmingGardenIDTxtV = (TextView) v.findViewById(R.id.gardenid);
            farmingPlantIDTxtV = (TextView) v.findViewById(R.id.plantid);

            cardView = (CardView) v.findViewById(R.id.cardView);
            btn1= (Button) v.findViewById(R.id.btn1);
            btn2= (Button) v.findViewById(R.id.btn2);
            btn3= (Button) v.findViewById(R.id.btn3);
        }
    }


    public void add(int position, Farming farming ) {
        mFarmingList.add(position, farming);
        notifyItemInserted(position);
    }


    public void remove(int position) {
        mFarmingList.remove(position);
        notifyItemRemoved(position);
    }


    public FarmingAdapter(List<Farming> myDataset , Context context , RecyclerView recyclerView){

        mFarmingList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView ;

    }


    @Override
    public FarmingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.single_farming_row, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(FarmingAdapter.ViewHolder holder, final int position) {
        final Farming farming = mFarmingList.get(position);
        DBHelper dbHelper = new DBHelper(mContext);

        String NameGarden = dbHelper.getNameGarden(position , farming);
        String NamePlant = dbHelper.getNamePlant(position , farming);
        String NameLocation = dbHelper.getLocationNameforFarming(position , farming) ;

        int Count = dbHelper.CountSurvey(farming.getId());

        holder.farmingFarmingIDTxtV.setText("การเพาะปลูกที่ : " + farming.getId() + " (" + Count + " การสำรวจ)");
        holder.farmingDMY_FarmingTxtV.setText("วันที่เพาะปลูก : " + farming.getD_m_y_farming());
        holder.farmingGardenIDTxtV.setText("ชื่อเกษตรกร : " + NameGarden + "(หมู้บ้าน:" + NameLocation + ")");
        holder.farmingPlantIDTxtV.setText("พันธุ์ข้าวที่ปลูก : " + NamePlant);


        holder.btn2.setVisibility(View.INVISIBLE);
        holder.btn3.setVisibility(View.INVISIBLE);
        holder.cardView.setCardBackgroundColor(Color.parseColor("#CCCCCC"));


//
        if (farming.getStatus()!=null) {
            if (farming.getStatus().equals("1")) {

                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.btn1.setVisibility(View.VISIBLE);
                holder.btn2.setVisibility(View.VISIBLE);
                holder.btn3.setVisibility(View.VISIBLE);
            }
        }



        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddSurvey(farming.getId());
            }
        });

        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUpdateFarming(farming.getId());
            }
        });

        holder.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
                builder.setTitle("ยืนยันการลบ");
                builder.setMessage("ท่าแแน่ใจที่จะลบเพาะปลูกที่ " +farming.getId()+ " หรือไม่");

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
                        dbHelper.deleteFarmingRecord(farming.getId(), mContext);

                        mFarmingList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mFarmingList.size());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });



//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog(mContext);
//                dialog.setContentView(R.layout.custom_dialog_farming);
//                dialog.setCancelable(true);
//                TextView DialogOption0 = (TextView) dialog.findViewById(R.id.option0);
//                Button DialogOption1 = (Button) dialog.findViewById(R.id.option1);
//                Button DialogOption2 = (Button) dialog.findViewById(R.id.option2);
//                Button DialogOption3 = (Button) dialog.findViewById(R.id.option3);
//
//                DialogOption0.setText("เพาะปลูกที่ : " + farming.getId());
//
//                DialogOption1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        gotoAddSurvey(farming.getId());
//                        dialog.dismiss();
//                    }
//                });
//
//                DialogOption2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        gotoUpdateFarming(farming.getId());
//                        dialog.dismiss();
//                    }
//                });
//
//                DialogOption3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        final AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
//                        builder.setTitle("ยืนยันการลบ");
//                        builder.setMessage("ท่าแแน่ใจที่จะลบเพาะปลูกนี้หรือไม่");
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
//                                dbHelper.deleteFarmingRecord(farming.getId(), mContext);
//
//                                mFarmingList.remove(position);
//                                mRecyclerV.removeViewAt(position);
//                                notifyItemRemoved(position);
//                                notifyItemRangeChanged(position, mFarmingList.size());
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




    private void gotoUpdateFarming(long farmingId){
        Intent goToUpdate = new Intent(mContext, UpdateFarmingRecord.class);
        goToUpdate.putExtra("FARMING_ID", farmingId);
        mContext.startActivity(goToUpdate);

    }

    private void gotoAddSurvey(long farmingId){
        Intent goToAdd = new Intent(mContext,  AddSurveyRecord.class);
        goToAdd.putExtra("FARMING_ID" , farmingId);
        mContext.startActivity(goToAdd);
    }


    @Override
    public int getItemCount() {
        return mFarmingList.size();
    }



}
