package com.andela.hackathon.azera.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.andela.hackathon.azera.R;
import com.squareup.picasso.Picasso;
import java.util.Collections;
import java.util.List;

public class ReceiptsViewAdapter extends RecyclerView.Adapter<ReceiptsViewAdapter.MyHolder> {
	private List<Receipt> receipts = Collections.emptyList();
	private Context context;
	private static final String TAG = ReceiptsViewAdapter.class.getSimpleName();

	public ReceiptsViewAdapter(List<Receipt> receipts, Context context) {
		this.receipts = receipts;
		this.context = context;
	}

	@Override
	public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_receipt, parent, false);
		MyHolder myHolder = new MyHolder(view);
		return myHolder;
	}

	@Override public void onBindViewHolder(MyHolder holder, int position) {
		Receipt receipt_list = receipts.get(position);
		holder.receipt_name.setText(
				String.format("%s %s", receipt_list.category, receipt_list.tags));
		holder.description.setText(receipt_list.description);
		Picasso.with(context).load(receipt_list.imageUrl)
				.resize(100,100)
				.centerCrop()
				.into(holder.receipt_img);
		holder.date_info.setText(receipt_list.status);
	}

	@Override public int getItemCount() {
		return receipts.size();
	}

	public class MyHolder extends RecyclerView.ViewHolder {
		TextView receipt_name, date_info, description;
		ImageView receipt_img;

		public MyHolder(View itemView) {
			super(itemView);
			receipt_name = (TextView) itemView.findViewById(R.id.txt_receipt_name);
			description = (TextView) itemView.findViewById(R.id.txt_description);
			receipt_img = (ImageView) itemView.findViewById(R.id.receipt_thumb);
			date_info = (TextView) itemView.findViewById(R.id.txt_date_created);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(context, ReceiptActivity.class);
					context.startActivity(intent);
				}
			});

		}

	}
}
