package com.andela.hackathon.azera.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.andela.hackathon.azera.R;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager pager;


    private FloatingActionButton fab;
    private boolean fabState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initPager();
        initTabs();
    }


    void initPager(){
        pager = findViewById(R.id.pager_main);
        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
    }

    void initTabs(){
        tabLayout = findViewById(R.id.tablayout_main);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        String tabs[] = {
                "Scan", "Pending", "Approved"
        };
        for (String name: tabs){
            tabLayout.addTab(tabLayout.newTab().setText(name));

            // use camera icon for scan tab instead of text
            tabLayout.getTabAt(0).setText(null).setIcon(R.drawable.ic_camera);
        }

        tabLayout.addOnTabSelectedListener(this);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
        switch (tab.getPosition()){
            case 0:
                fab.show();
                break;
            case 1:
                fab.hide();
                break;
            case 2:
                fab.hide();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}
