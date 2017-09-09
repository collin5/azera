package com.andela.hackathon.azera.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import com.andela.hackathon.azera.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

			private ArrayList<Receipt> receipts;
			private RecyclerView recyclerView;
			private LinearLayoutManager linearLayoutManager;
			private FirebaseDatabase database;
			private DatabaseReference databaseReference;
			private PendingReceiptsViewAdapter pendingReceiptsViewAdapter;
			private TextView receiptName;

    	View view;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_pending, container, false);
						recyclerView = (RecyclerView) view.findViewById(R.id.receipt_list);
						linearLayoutManager = new LinearLayoutManager(view.getContext());
						recyclerView.setLayoutManager(linearLayoutManager);
						receiptName = (TextView) view.findViewById(R.id.txt_receipt_name);
						recyclerView.setLayoutManager(linearLayoutManager);
					  receipts = new ArrayList<Receipt>();

						Log.d(TAG, "Getting database reference");

						database = FirebaseDatabase.getInstance();
						databaseReference = database.getReference("receipts");

					databaseReference.addValueEventListener(new ValueEventListener() {
							@Override public void onDataChange(DataSnapshot dataSnapshot) {
								receipts = new ArrayList<Receipt>();
								for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
								  Receipt value = dataSnapshot1.getValue(Receipt.class);
								  Receipt receipt = new Receipt();
									String category = value.getCategory();
									String tag = value.getTags();
									String status = value.getStatus();
									String imgUrl = value.getImageUrl();
									String userID = value.getUser_id();

									receipt.setCategory(category);
									receipt.setTags(tag);
									receipt.setStatus(status);
									receipt.setImageUrl(imgUrl);
									receipt.setImageUrl(userID);

									Log.d(TAG, "Data returned: " + value);
									receipts.add(receipt);
								}
							}

							@Override public void onCancelled(DatabaseError databaseError) {
								Log.w(TAG, "Get data Error: " + databaseError.toException());
							}
						});


					pendingReceiptsViewAdapter = new PendingReceiptsViewAdapter(receipts, view.getContext());
					RecyclerView.LayoutManager recycle = new GridLayoutManager(view.getContext(), 1);
					recyclerView.setLayoutManager(recycle);
					recyclerView.setItemAnimator(new DefaultItemAnimator());
					recyclerView.setAdapter(pendingReceiptsViewAdapter);

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
