package com.andela.hackathon.azera.main;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.andela.hackathon.azera.R;

import java.util.ArrayList;

public class SendRecieptActivity extends AppCompatActivity {

    ImageView preview;

    Bitmap reciept = null;

    ActionBar actionBar;
    AppCompatSpinner categorySpinner;

    ArrayList<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reciept);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preview = findViewById(R.id.reciept_preview);
        preview.setImageBitmap(Statics.bitmap);

        initCategories();

        initActionbar();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    void initActionbar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    void initCategories(){
        // setup categories
        categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(categoriesAdapter);
        // TODO: 9/9/17 get data from firebase
        categoriesAdapter.add("Category 1");
        categoriesAdapter.add("Category 2");
        categoriesAdapter.notifyDataSetChanged();
    }

}
