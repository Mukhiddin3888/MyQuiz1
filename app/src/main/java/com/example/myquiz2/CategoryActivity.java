package com.example.myquiz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {

        GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        gridView = findViewById(R.id.grdv_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        List<String> catList = new ArrayList<>();
        catList.add("category 1");
        catList.add("category 2");
        catList.add("category 3");
        catList.add("category 4");
        catList.add("category 5");
        catList.add("category 6");


        CatGridAdapter adapter = new CatGridAdapter(catList);
        gridView.setAdapter(adapter);


    }
}