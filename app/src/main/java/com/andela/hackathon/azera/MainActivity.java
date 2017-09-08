package com.andela.hackathon.azera;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initTabs();
    }

    private void initTabs(){
        tabLayout = (TabLayout) findViewById(R.id.tablayout_main);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        String tabs[] = {
                "Scan", "Pending", "Approved"
        };
        for (String name: tabs){
            tabLayout.addTab(tabLayout.newTab().setText(name));

            // use camera icon for scan tab instead of text
            tabLayout.getTabAt(0).setText(null).setIcon(R.drawable.ic_camera);
        }
    }

}
