package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

public class SosUserRecyAdapter extends RecyclerView.Adapter<SosUserRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private int[] imgs;
    private String[] names;

    public SosUserRecyAdapter(Context context, int[] imgs, String[] names) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imgs = imgs;
        this.names = names;
    }

    @NonNull
    @Override
    public SosUserRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sosuser_layout, parent, false);
        SosUserRecyAdapter.ViewHolder viewHolder = new SosUserRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SosUserRecyAdapter.ViewHolder holder, int position) {
//        holder.tv_bankname.setText(banknames.get(position));
//        holder.tv_banknumber.setText(banknumbers.get(position));
//        holder.swipeMenuLayout

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_bankname, tv_banknumber;
        private SwipeMenuLayout swipeMenuLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bankname = itemView.findViewById(R.id.tv_item_bankname);
            tv_banknumber = itemView.findViewById(R.id.tv_banknumber);
            swipeMenuLayout = itemView.findViewById(R.id.swipeMenuLayout);

        }
    }
}
