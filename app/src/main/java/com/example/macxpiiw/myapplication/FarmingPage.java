package com.example.macxpiiw.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.Button;

public class FarmingPage extends AppCompatActivity {

    private DBHelper myDB;
    private Button button;
    private BottomNavigationView bottomNavigationView ;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DBHelper dbHelper;
    private FarmingAdapter adapter;
    private String filter = "";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farming_page);
        getSupportActionBar().setTitle("เพาะปลูก");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        init();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent relocation = new Intent(FarmingPage.this, FarmingPage.class);
                startActivity(relocation);
            }
        });

        populaterecyclerView(filter);

        searchView = (SearchView) findViewById(R.id.searchView);
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
                    Intent intent1 = new Intent(FarmingPage.this , FarmPage.class);
                    startActivity(intent1);

                } else if (item.getItemId() == R.id.survey_menu){
                    Intent intent2 = new Intent(FarmingPage.this , SurveyPage.class);
                    startActivity(intent2);

                } else if (item.getItemId() == R.id.home_menu){
                    Intent intent3 = new Intent(FarmingPage.this , MainActivity.class);
                    startActivity(intent3);

                } else if (item.getItemId() == R.id.download_menu){
                    Intent intent4 = new Intent(FarmingPage.this , DownloadPage.class);
                    startActivity(intent4);

                } else if (item.getItemId() == R.id.upload_menu){
                    Intent intent5 = new Intent(FarmingPage.this , Login.class);
                    startActivity(intent5);

                }

                return false;
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        MenuItem add = menu.findItem(R.id.addMenu);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent add_location = new Intent(FarmingPage.this, AddFarmingRecord.class);
                startActivity(add_location);
                return false;
            }
        });

        return true;

    }


    private void populaterecyclerView(String filter) {
        dbHelper = new DBHelper(this);
        adapter = new FarmingAdapter(dbHelper.farmingList(filter) , this , mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }





//    public void init(){
//        button = (Button) findViewById(R.id.to_add_farming);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent add_garden = new Intent(FarmingPage.this, AddFarmingRecord.class);
//                startActivity(add_garden);
//            }
//        });
//    }
}
