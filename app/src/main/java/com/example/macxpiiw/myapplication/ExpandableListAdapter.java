package com.example.macxpiiw.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by reale on 21/11/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;

    private ArrayList<Pair<Integer,ArrayList<CheckBox>>> checkBoxs2 = new ArrayList<>();

    private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
    CheckBox cb;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;


    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = Group Item , i1 = ChildItem
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group,null);
        }
        TextView lblListHeader = (TextView)view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        if (checkBoxs2.size()!=0) {
            if (checkBoxs2.size()>i){
            if (checkBoxs2.get(i) == null) {
                checkBoxs2.add(new Pair<Integer, ArrayList<CheckBox>>(i, new ArrayList<CheckBox>()));
            }
            }
            else
                checkBoxs2.add(new Pair<Integer, ArrayList<CheckBox>>(i, new ArrayList<CheckBox>()));
        }
        else {

                checkBoxs2.add(new Pair<Integer, ArrayList<CheckBox>>(i, new ArrayList<CheckBox>()));

            }

        return view;
    }
    private OnCheckAll onCheckAll = new OnCheckAll() {
        @Override
        public void onCheckAll() {

            for (Pair<Integer,ArrayList<CheckBox>> p:checkBoxs2)
            {
                for (CheckBox c:p.second)
                {
                    c.performClick();
                }
            }


        }
    };
    interface OnCheckAll
    {
        void onCheckAll();
    }


    public void checkAllCheckBox()
    {
        onCheckAll.onCheckAll();
    }
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String)getChild(i,i1);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,null);
        }


        TextView txtListChild = (TextView)view.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);


        cb = (CheckBox) view.findViewById(R.id.myCheckBox);
        // add tag to remember groupId/childId
        final Pair<Long, Long> tag = new Pair<Long, Long>(
                getGroupId(i),
                getChildId(i, i1));
        cb.setTag(tag);
        // set checked if groupId/childId in checked items
        cb.setChecked(mCheckedItems.contains(tag));
        // set OnClickListener to handle checked switches
        cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final CheckBox cb = (CheckBox) v;
                final Pair<Long, Long> tag = (Pair<Long, Long>) v.getTag();

                if (cb.isChecked()) {
                    mCheckedItems.add(tag);
                } else {
                    mCheckedItems.remove(tag);
                }
            }
        });



        Pair<Integer,ArrayList<CheckBox>> c3 = checkBoxs2.get(i);
        ArrayList<CheckBox> ccc = c3.second;
        if (ccc.size()>i1){
            if (ccc.get(i1)!=null)
                ccc.set(i1,cb);
            else
                c3.second.add(cb);
        }
        else
        {
            c3.second.add(cb);
        }




        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public Set<Pair<Long, Long>> getCheckedItems() {
        return mCheckedItems;
    }

}