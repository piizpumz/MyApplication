package com.example.macxpiiw.myapplication;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;

import android.widget.Spinner;


import java.util.ArrayList;


public class LocationPage extends AppCompatActivity  {
    private DBHelper myDB;
    private Button button;
    private BottomNavigationView bottomNavigationView ;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DBHelper dbHelper;
    private LocationAdapter adapter;
    private String filter = "";
    private SearchView searchView ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_page);
        getSupportActionBar().setTitle("พื้นที่สำรวจ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        searchView = (SearchView) findViewById(R.id.searchView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent relocation = new Intent(LocationPage.this, LocationPage.class);
                startActivity(relocation);
            }
        });
        populaterecyclerView(filter);


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




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.farm_menu){
                    Intent intent1 = new Intent(LocationPage.this , FarmPage.class);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(LocationPage.this , SurveyPage.class);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(LocationPage.this , MainActivity.class);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(LocationPage.this , DownloadPage.class);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(LocationPage.this , Login.class);
                    startActivity(intent5);

                }

                return false;
            }
        });

    }

    private void populaterecyclerView(String filter) {
        dbHelper = new DBHelper(this);
        adapter = new LocationAdapter(dbHelper.locationList(filter) , this , mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        MenuItem add = menu.findItem(R.id.addMenu);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent add_location = new Intent(LocationPage.this, AddLocationRecord.class);
                startActivity(add_location);
                return false;
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }






    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

//    public void init(){
//        button = (Button) findViewById(R.id.to_add_location);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent add_location = new Intent(LocationPage.this, AddLocationRecord.class);
//                startActivity(add_location);
//            }
//        });
//    }



}
