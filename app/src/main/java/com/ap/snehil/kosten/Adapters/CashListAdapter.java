package com.ap.snehil.kosten.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ap.snehil.kosten.Modals.Cash;
import com.ap.snehil.kosten.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static com.ap.snehil.kosten.R.id.parent;

/**
 * Created by HP on 13-08-2017.
 */

public class CashListAdapter extends RecyclerView.Adapter<CashListAdapter.cashViewHolder>{
    public static final String TAG="CashListAdapter";
    Context context;
    ArrayList<Cash> cash;

    public CashListAdapter(Context context, ArrayList<Cash> cash) {
        Log.d(TAG, "CashListAdapter: ************************");
        this.context = context;
        this.cash = cash;
    }
    public void updateCashList(ArrayList<Cash> newcashList) {
        Log.d(TAG, "updatePost: ");
        this.cash = newcashList;
        notifyDataSetChanged();
    }

    @Override
    public cashViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: **********************");
        LayoutInflater li =
                (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.layout_cash, parent, false);
        return new CashListAdapter.cashViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cashViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ***********************88");
        final Cash thisCash = cash.get(position);
        holder.tvCost.setText(thisCash.getCost());
        holder.tvDate.setText(thisCash.getDate());
        Log.i(TAG, "onBindViewHolder: imageURI "+thisCash.getPath().getPath());
        String filePath ="file://"+thisCash.getPath().getPath();
        //Log.i(TAG, "onBindViewHolder: filePath "+filePath);
       Uri fileUri=Uri.parse(filePath);
       // Log.i(TAG, "onBindViewHolder: File "+file);
        Picasso.with(context)
                .load(fileUri)
                .resize(150,150)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.ivReceipt);
    }


    @Override
    public int getItemCount() {
        return cash.size();
}
    class cashViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate,tvCost;
        ImageView ivReceipt;
        public cashViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "cashViewHolder: ***************88");
            tvCost =(TextView) itemView.findViewById(R.id.tvCost);
            tvDate =(TextView) itemView.findViewById(R.id.tvDate);
            ivReceipt =(ImageView) itemView.findViewById(R.id.ivReceipt);
        }
    }
}
