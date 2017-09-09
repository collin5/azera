package com.andela.hackathon.azera.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andela.hackathon.azera.R;
import java.util.List;

public class PendingReceiptsAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
	private List<Receipt> receipt;
	protected Context context;

	public PendingReceiptsAdapter(Context context, List<Receipt> task {
		this.receipt = receipt;
		this.context = context;
	}

	@Override public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerViewHolders viewHolder = null;
		View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pending, parent, false)
		viewHolder = new RecyclerViewHolders(layoutView, task);
		return viewHolder;
	}

	@Override public void onBindViewHolder(RecyclerViewHolders holder, int position) {
		holder.txt_receip cvct_name.setText();
	}

	@Override public int getItemCount() {
		return 0;
	}
}
