package com.ap.snehil.kosten.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.ap.snehil.kosten.Modals.Record;
import com.ap.snehil.kosten.R;

import java.util.ArrayList;

/**
 * Created by HP on 14-08-2017.
 */

public class GetListAdapter extends RecyclerView.Adapter<GetListAdapter.GetViewHolder>{
    public static final String TAG="GetListAdapter";
    Context context;
    ArrayList<Record> record;

    public GetListAdapter(Context context, ArrayList<Record> record) {
        this.context = context;
        this.record = record;
    }
    public void updateGetList(ArrayList<Record> newrecordList) {
        Log.d(TAG, "updateGetList: **************************");
        this.record= newrecordList;
        notifyDataSetChanged();
    }

    @Override
    public GetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li =
                (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.layout_get, parent, false);
        return new GetListAdapter.GetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GetViewHolder holder, int position) {
        final Record thisRecord = record.get(position);
        holder.tvAmount.setText(thisRecord.getAmount());
        holder.tvName.setText(thisRecord.getName());

    }


    @Override
    public int getItemCount() {
        return record.size();
    }

    class GetViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvAmount;
        public GetViewHolder(View itemView) {
            super(itemView);
            tvAmount =(TextView) itemView.findViewById(R.id.tvAmount);
            tvName =(TextView) itemView.findViewById(R.id.tvName);

        }
    }
}

