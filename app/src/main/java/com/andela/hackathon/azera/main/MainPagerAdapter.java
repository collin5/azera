package com.andela.hackathon.azera.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.hackathon.azera.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
			private FirebaseDatabase database = FirebaseDatabase.getInstance();
			private DatabaseReference databaseReference = database.getReference("receipts");
			private ReceiptsViewAdapter receiptsViewAdapter;

    	View view;


			@Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
					FirebaseAuth mAuth = FirebaseAuth.getInstance();
					final FirebaseUser currentUser = mAuth.getCurrentUser();

					view = inflater.inflate(R.layout.fragment_pending, container, false);
					recyclerView = (RecyclerView) view.findViewById(R.id.receipt_list);
					linearLayoutManager = new LinearLayoutManager(view.getContext());
					recyclerView.setLayoutManager(linearLayoutManager);

					receipts = new ArrayList<Receipt>();
					recyclerView.setItemAnimator(new DefaultItemAnimator());

					Log.d(TAG, "Data created: " + receipts);
					receiptsViewAdapter = new ReceiptsViewAdapter(receipts, recyclerView.getContext());
					recyclerView.setAdapter(receiptsViewAdapter);

					databaseReference.addValueEventListener(new ValueEventListener() {
						@Override public void onDataChange(DataSnapshot dataSnapshot) {
							receipts = new ArrayList<Receipt>();
							for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
								Receipt value = dataSnapshot1.getValue(Receipt.class);
								if (value != null && value.status.equals("pending") && value.user_id.equals(
										currentUser.getUid())) {
									receipts.add(value);
								}
							}
							Log.w(TAG, "Returned : " + receipts);
							receiptsViewAdapter = new ReceiptsViewAdapter(receipts, view.getContext());
							recyclerView.setAdapter(receiptsViewAdapter);
						}

						@Override public void onCancelled(DatabaseError databaseError) {
							Log.w(TAG, "Database Error: " + databaseError.toException());
						}
					});

            return view;
        }
		}

    public static class Approved extends Fragment{
			private ArrayList<Receipt> receipts;
			private RecyclerView recyclerView;
			private LinearLayoutManager linearLayoutManager;
			private FirebaseDatabase database = FirebaseDatabase.getInstance();
			private DatabaseReference databaseReference = database.getReference("receipts");
			private ReceiptsViewAdapter receiptsViewAdapter;

        View view;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
					view = inflater.inflate(R.layout.fragment_approved, container, false);

					FirebaseAuth mAuth = FirebaseAuth.getInstance();
					final FirebaseUser currentUser = mAuth.getCurrentUser();

					recyclerView = (RecyclerView) view.findViewById(R.id.approved_list);
					linearLayoutManager = new LinearLayoutManager(view.getContext());
					recyclerView.setLayoutManager(linearLayoutManager);

					receipts = new ArrayList<Receipt>();
					recyclerView.setItemAnimator(new DefaultItemAnimator());

					Log.d(TAG, "Data created: " + receipts);
					receiptsViewAdapter = new ReceiptsViewAdapter(receipts, recyclerView.getContext());
					recyclerView.setAdapter(receiptsViewAdapter);

					databaseReference.addValueEventListener(new ValueEventListener() {
						@Override public void onDataChange(DataSnapshot dataSnapshot) {
							receipts = new ArrayList<Receipt>();
							for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
								Receipt value = dataSnapshot1.getValue(Receipt.class);
								if (value != null && value.status.equals("accepted") && value.user_id.equals(
										currentUser.getUid())) {
									receipts.add(value);
								}
							}
							Log.w(TAG, "Approved Receipts : " + receipts);
							receiptsViewAdapter = new ReceiptsViewAdapter(receipts, view.getContext());
							recyclerView.setAdapter(receiptsViewAdapter);
						}

						@Override public void onCancelled(DatabaseError databaseError) {
							Log.w(TAG, "Database Error: " + databaseError.toException());
						}
					});

            return view;
        }
    }
}
