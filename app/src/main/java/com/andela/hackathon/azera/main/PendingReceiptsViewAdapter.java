package com.andela.hackathon.azera.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.andela.hackathon.azera.R;
import java.util.List;

public class PendingReceiptsViewAdapter extends RecyclerView.Adapter<PendingReceiptsViewAdapter.MyHolder> {
	private List<Receipt> receipts;
	private Context context;
	private static final String TAG = PendingReceiptsViewAdapter.class.getSimpleName();

	public PendingReceiptsViewAdapter(List<Receipt> receipts, Context context) {
		this.receipts = receipts;
		this.context = context;
	}

	@Override
	public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.fragment_pending, parent, false);
		MyHolder myHolder = new MyHolder(view);
		return myHolder;
	}

	@Override public void onBindViewHolder(MyHolder holder, int position) {
		Receipt receipt_list = receipts.get(position);
		holder.receipt_name.setText(
				String.format("%s %s", receipt_list.getCategory(), receipt_list.getTags()));
		holder.date_info.setText(receipt_list.getTags()); // TODO Change to date info
	}

	@Override public int getItemCount() {
		int arr = 0;

		try {
			arr = receipts.size() > 0 ? receipts.size() : 0;
		} catch(Exception e) {
			Log.d(TAG, e.toString());
		}

		return arr;
	}

	public class MyHolder extends RecyclerView.ViewHolder {
		TextView receipt_name, date_info;

		public MyHolder(View itemView) {
			super(itemView);
			receipt_name = (TextView) itemView.findViewById(R.id.txt_receipt_name);
			date_info = (TextView) itemView.findViewById(R.id.txt_date_info);
		}

	}
}
