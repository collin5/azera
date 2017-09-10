package com.andela.hackathon.azera.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.hackathon.azera.R;
import com.squareup.picasso.Picasso;

public class ReceiptActivity extends AppCompatActivity {

    private String title;
    private String description;
    private ActionBar actionBar;
    private String imageUrl;

    private TextView desView;
    private ImageView preview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getExtras();
        initActionbar();
        this.init();
    }

    void getExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            this.title = extras.getString("title");
            this.description = extras.getString("description");
            this.imageUrl = extras.getString("imageUrl");
        }
    }

    void initActionbar(){
        actionBar = getSupportActionBar();
        actionBar.setTitle(this.title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    void init(){
        desView = findViewById(R.id.recieptActivity_description);
        desView.setText(this.description);

        preview = findViewById(R.id.recieptActivity_preview);
        Picasso.with(this).load(imageUrl).resize(500,500).into(preview);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
