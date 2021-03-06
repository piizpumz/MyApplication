package com.example.macxpiiw.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.macxpiiw.myapplication.AddGardenRecord;
import com.example.macxpiiw.myapplication.DBHelper;
import com.example.macxpiiw.myapplication.GardenAdapter;
import com.example.macxpiiw.myapplication.R;

/**
 * Created by macxpiiw on 5/2/2018 AD.
 */

public class page3 extends Fragment{
    private DBHelper myDB;
    private Button button;
    private BottomNavigationView bottomNavigationView ;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DBHelper dbHelper;
    private GardenAdapter adapter;
    private String filter = "";
    private SearchView searchView ;
    private FloatingActionButton fab_btn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page3, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        searchView = (SearchView) view.findViewById(R.id.searchView);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populaterecyclerView(filter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //populaterecyclerView(filter);



        searchView.setQueryHint("ชื่อเกษตรกร,หมู่บ้าน");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                populaterecyclerView(newText);

                return false;
            }
        });

        fab_btn = (FloatingActionButton) view.findViewById(R.id.fabBtn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_garden = new Intent(getContext(), AddGardenRecord.class);
                startActivity(add_garden);
            }
        });



        return view;
    }


    private void populaterecyclerView(String filter) {
        dbHelper = new DBHelper(getContext());
        adapter = new GardenAdapter(dbHelper.gardenList(filter) , getContext() , mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {

        super.onResume();
        populaterecyclerView(filter);
    }
}
