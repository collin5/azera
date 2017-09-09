package com.andela.hackathon.azera.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andela.hackathon.azera.R;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
	private List<Receipt> receipt;
	protected Context context;

	@Override
	public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

	}

	@Override public int getItemCount() {
		return 0;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public ViewHolder(View itemView) {
			super(itemView);
		}

	}
}
