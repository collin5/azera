package com.andela.hackathon.azera.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import com.andela.hackathon.azera.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by collins on 9/8/17.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public static final String TAG = MainPagerAdapter.class.getSimpleName();

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



        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }

    public static class Pending extends Fragment{

			private ArrayList<Task> allReceipts;
			private RecyclerView recyclerView;
			private LinearLayoutManager linearLayoutManager;
			private DatabaseReference databaseReference;
			private PendingReceiptsAdapter pendingReceiptsAdapter;
			private TextView receiptName;

    	View view;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_pending, container, false);
						allReceipts = new ArrayList<Task>();
						databaseReference = FirebaseDatabase.getInstance().getReference();

						recyclerView = (RecyclerView) view.findViewById(R.id.receipt_list);
						linearLayoutManager = new LinearLayoutManager(view.getContext());
						recyclerView.setLayoutManager(linearLayoutManager);
						receiptName = (TextView) view.findViewById(R.id.txt_receipt_name);
						recyclerView.setLayoutManager(linearLayoutManager);


					databaseReference.addChildEventListener(new ChildEventListener() {
						@Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
							Log.d(TAG, "onChildAdded: " + dataSnapshot);
						}

						@Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
							Log.d(TAG, "onChildAdded: " + dataSnapshot);
						}

						@Override public void onChildRemoved(DataSnapshot dataSnapshot) {
							Log.d(TAG, "onChildAdded: " + dataSnapshot);
						}

						@Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {
							Log.d(TAG, "onChildAdded: " + dataSnapshot);
						}

						@Override public void onCancelled(DatabaseError databaseError) {
						}
					});


					//pendingReceiptsAdapter = new PendingReceiptsAdapter(dataSet);

            return view;
        }

        public void getAllReceipts(DataSnapshot dataSnapshot) {
        	for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
        		String title = singleSnapshot.getValue(String.class);

					}
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
