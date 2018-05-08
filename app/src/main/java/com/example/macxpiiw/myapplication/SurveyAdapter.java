package com.example.macxpiiw.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by macxpiiw on 3/15/2018 AD.
 */

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {
    private List<Survey> mSurveyList;
    private Context mContext;
    private RecyclerView mRecyclerV;





    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView surveySurveyID;
        public TextView surveyDMYTxtV;
        public TextView surveyTimeTxtV;
        public TextView surveyTempTxtV;
        public TextView surveyMoistureTxtV;
        public TextView surveyRainTxtV;
        public TextView surveyLightTxtV;
        public TextView surveyDewTxtV;
        public TextView surveyCategoryTxtV;
        public TextView surveySamplePointTxtV;
        public TextView surveyPointTxtV;
        public TextView surveyFarmingIDTxtV;
        public TextView surveyIncidenceTxtV;
        public TextView surveySeverityTxtV;

        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;

            surveySurveyID = (TextView) v.findViewById(R.id.survey_id);
            surveyDMYTxtV= (TextView) v.findViewById(R.id.dmy_survey);
            surveyTimeTxtV= (TextView) v.findViewById(R.id.time_survey);
            surveyIncidenceTxtV = (TextView) v.findViewById(R.id.incidence);
            surveySeverityTxtV = (TextView) v.findViewById(R.id.severity);
//            surveyTempTxtV= (TextView) v.findViewById(R.id.temp);
//            surveyMoistureTxtV= (TextView)v.findViewById(R.id.moisture);
//            surveyRainTxtV = (TextView) v.findViewById(R.id.rain);
//            surveyLightTxtV = (TextView) v.findViewById(R.id.light);
//            surveyDewTxtV = (TextView) v.findViewById(R.id.dew);
//            surveyCategoryTxtV = (TextView) v.findViewById(R.id.category);
//            surveySamplePointTxtV = (TextView) v.findViewById(R.id.sample_point);
//            surveyPointTxtV = (TextView) v.findViewById(R.id.point);
            surveyFarmingIDTxtV = (TextView) v.findViewById(R.id.farmingid);

        }
    }


    public void add(int position, Survey survey) {
        mSurveyList.add(position, survey);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mSurveyList.remove(position);
        notifyItemRemoved(position);
    }

    public SurveyAdapter(List<Survey> myDataset, Context context , RecyclerView recyclerView){
        mSurveyList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @Override
    public SurveyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.single_survey_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Survey survey = mSurveyList.get(position);

        DBHelper dbHelper = new DBHelper(mContext);
        String NameLocation = dbHelper.getLocationNameforSurvey(position , survey) ;

        holder.surveySurveyID.setText("การสำรวจที่ : " + survey.getId());
        holder.surveyFarmingIDTxtV.setText("เกษตกร : " + NameLocation);
        holder.surveyDMYTxtV.setText("วันที่สำรวจ : " + survey.getD_m_y_survey());
        holder.surveyTimeTxtV.setText("เวลาที่สำรวจ : " + survey.getTime_survey());
        holder.surveyIncidenceTxtV.setText("Incidence : " + survey.getIncidence());
        holder.surveySeverityTxtV.setText("Severity :  " + survey.getSeverity());

//        holder.surveyTempTxtV.setText("อุณหภูมิ(*C) : " + survey.getTemp());
//        holder.surveyMoistureTxtV.setText("ความชื้นสัมพัทธ์(%) : " + survey.getMoisture());
//        holder.surveyRainTxtV.setText("ปริมาณน้ำฝน(mm) : " + survey.getRain());
//        holder.surveyLightTxtV.setText("ปริมาณแสง(uM/m^2s) : " + survey.getLight());
//        holder.surveyDewTxtV.setText("จุดน้ำค้าง(*C) : " + survey.getDew());
//        holder.surveyCategoryTxtV.setText("ประเภทนา :" + survey.getCategory());
//        holder.surveySamplePointTxtV.setText("จุดเก็บตัวอย่างต่อ1W : " +survey.getSample_point());
//        holder.surveyPointTxtV.setText("จำนวนกอ : " + survey.getPoint());

//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
//                builder.setTitle("Choose option");
//                builder.setMessage("Update or Delete Location?");
//                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        goToUpdateSurvey(survey.getId());
//
//                    }
//                });
//                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog , int whiich) {
//                        DBHelper dbHelper = new DBHelper(mContext);
//                        dbHelper.deleteSurveyRecord(survey.getId(),mContext);
//
//                        mSurveyList.remove(position);
//                        mRecyclerV.removeViewAt(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, mSurveyList.size());
//                        notifyDataSetChanged();
//
//                    }
//                });
//                builder.setNegativeButton("Add Image", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        goToAddImage(survey.getId());
//
//                    }
//                });
//                builder.create().show();
//            }
//        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mContext);

                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCancelable(true);
                TextView DialogOption0 = (TextView) dialog.findViewById(R.id.option0);
                Button DialogOption1 = (Button) dialog.findViewById(R.id.option1);
                Button DialogOption2 = (Button) dialog.findViewById(R.id.option2);
                Button DialogOption3 = (Button) dialog.findViewById(R.id.option3);
                final Button DialogOption4 = (Button) dialog.findViewById(R.id.option4);


                DialogOption0.setText("การสำรวจที่ : " + survey.getId());

                DialogOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToViewImage(survey.getId());
                        dialog.dismiss();
                    }
                });

                DialogOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToAddImage(survey.getId());
                        dialog.dismiss();
                    }
                });

                DialogOption3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToUpdateSurvey(survey.getId());
                        dialog.dismiss();
                    }
                });

                DialogOption4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
                        builder.setTitle("ยืนยันการลบ");
                        builder.setMessage("หากลบการสำรวจ รุปภาพที่อยู่ในการสำรวจนี้จะถูกลบออกด้วย ท่านแน่ใจที่จะลบ?");

                        builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                            }
                        });

                        builder.setNegativeButton("ยืนยน", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goToDelete(position , survey.getId());
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

    }




    private void goToUpdateSurvey(long surveyId){
        Intent goToUpdate = new Intent(mContext, UpdateSurveyRecord.class);
        goToUpdate.putExtra("SURVEY_ID", surveyId);
        mContext.startActivity(goToUpdate);

    }

    private void goToViewImage(long surveyId){
        Intent goToImage = new Intent(mContext , ImagePage.class);
        goToImage.putExtra("SURVEY_ID" , surveyId);
        mContext.startActivity(goToImage);
    }


    private void goToAddImage(long imageId){
        Intent goToadd = new Intent(mContext, AddImageRecord.class);
        goToadd.putExtra("SURVEY_ID" , imageId);
        mContext.startActivity(goToadd);
    }

    private void goToDelete(int position , long id){
        DBHelper dbHelper = new DBHelper(mContext);
        Survey survey = null;
        dbHelper.deleteSurveyRecord(id,mContext);

        mSurveyList.remove(position);
        mRecyclerV.removeViewAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mSurveyList.size());
        notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return mSurveyList.size();
    }


}



