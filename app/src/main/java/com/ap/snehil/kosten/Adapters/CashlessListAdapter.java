package com.ap.snehil.kosten.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ap.snehil.kosten.Modals.SMSdata;
import com.ap.snehil.kosten.R;

import java.util.ArrayList;

/**
 * Created by HP on 15-08-2017.
 */

public class CashlessListAdapter extends RecyclerView.Adapter<CashlessListAdapter.receiptViewHolder> {
    ArrayList<SMSdata> smsData;
    Context context;

    public CashlessListAdapter(ArrayList<SMSdata> smsData, Context context) {
        this.smsData = smsData;
        this.context = context;
    }


    final public static String TAG ="cashless Adapter";
    public void updateCashlessList(ArrayList<SMSdata> newcashlessList) {
        Log.d(TAG, "updatePost: ");
        this.smsData = newcashlessList;
        notifyDataSetChanged();
    }


    @Override
    public receiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li =
                (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.layout_cashless, parent, false);
        return new CashlessListAdapter.receiptViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(receiptViewHolder holder, int position) {
        final SMSdata thisdata = smsData.get(position);
        Log.d(TAG, "onBindViewHolder: "+thisdata.getDate()+thisdata.getBody());
        holder.tvCashlessDate.setText(thisdata.getDate());
        holder.tvCashlessBody.setText(thisdata.getBody());
    }

    @Override
    public int getItemCount() {
        return smsData.size();
    }

    class receiptViewHolder extends RecyclerView.ViewHolder {
        TextView tvCashlessDate,tvCashlessBody;
        public receiptViewHolder(View itemView) {
            super(itemView);
            tvCashlessDate =(TextView) itemView.findViewById(R.id.tvCashlessDate);
            tvCashlessBody =(TextView) itemView.findViewById(R.id.tvCashlessBody);

        }
    }
}

