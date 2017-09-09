package com.andela.hackathon.azera.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.andela.hackathon.azera.R;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
	private List<Receipt> receipt;
	protected Context context;

	public RecyclerViewAdapter(List<Receipt> list, Context context) {
		this.receipt = list;
		this.context = context;
	}

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
		TextView receipt_name, date_info;

		public ViewHolder(View itemView) {
			super(itemView);
			receipt_name = (TextView) itemView.findViewById(R.id.txt_receipt_name);
			date_info = (TextView) itemView.findViewById(R.id.txt_date_info);
		}

	}
}
