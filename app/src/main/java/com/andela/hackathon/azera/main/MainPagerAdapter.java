package com.andela.hackathon.azera.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.hackathon.azera.R;

/**
 * Created by collins on 9/8/17.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainPagerAdapter.Scan();
            case 1:
                return new MainPagerAdapter.Pending();
            case 2:
                return new MainPagerAdapter.Approved();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public static class Scan extends Fragment{

        View view;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_scan, container, false);
            return view;
        }
    }

    public static class Pending extends Fragment{

        View view;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_pending, container, false);
            return view;
        }
    }

    public static class Approved extends Fragment{

        View view;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_approved, container, false);
            return view;
        }
    }
}
