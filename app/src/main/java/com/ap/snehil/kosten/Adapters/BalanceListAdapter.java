package com.ap.snehil.kosten.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ap.snehil.kosten.Modals.Record;
import com.ap.snehil.kosten.R;

import java.util.ArrayList;

/**
 * Created by HP on 14-08-2017.
 */

public class BalanceListAdapter extends RecyclerView.Adapter<BalanceListAdapter.OweViewHolder>{
    public static final String TAG="BalanceListAdapter";
    Context context;
    ArrayList<Record> record;

    public BalanceListAdapter(Context context, ArrayList<Record> record) {
        this.context = context;
        this.record = record;
    }
    public void updateOweList(ArrayList<Record> newrecordList) {
        Log.d(TAG, "updateOweList: ****************************88");
        this.record= newrecordList;
        notifyDataSetChanged();
    }

    @Override
    public OweViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: *****************************");
        LayoutInflater li =
                (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.layout_balance, parent, false);
        return new BalanceListAdapter.OweViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OweViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ******************************");
        final Record thisRecord = record.get(position);
        holder.tvName.setText(thisRecord.getName());
        holder.tvDate.setText(thisRecord.getDate());
        holder.tvReason.setText(thisRecord.getReason());
        holder.tvGiven.setText(thisRecord.getGiven());
        holder.tvTaken.setText(thisRecord.getTaken());

    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: *********************************8");
        return record.size();
    }

    class OweViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvDate,tvReason,tvGiven,tvTaken;
        public OweViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "OweViewHolder: *********************************8");
            tvDate =(TextView) itemView.findViewById(R.id.tvDate);
            tvName =(TextView) itemView.findViewById(R.id.tvName);
            tvReason =(TextView) itemView.findViewById(R.id.tvReason);
            tvTaken =(TextView) itemView.findViewById(R.id.tvTaken);
            tvGiven=(TextView) itemView.findViewById(R.id.tvGiven);

        }
    }
}

