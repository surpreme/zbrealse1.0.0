package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

public class BankCardRecyAdapter extends RecyclerView.Adapter<BankCardRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<String> banknames;
    private List<String> banknumbers;

    public BankCardRecyAdapter(Context context, List<String> banknames, List<String> banknumbers) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.banknames = banknames;
        this.banknumbers = banknumbers;
    }

    @NonNull
    @Override
    public BankCardRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bank_carlayout, parent, false);
        BankCardRecyAdapter.ViewHolder viewHolder = new BankCardRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BankCardRecyAdapter.ViewHolder holder, int position) {
        holder.tv_bankname.setText(banknames.get(position));
        holder.tv_banknumber.setText(banknumbers.get(position));
//        holder.swipeMenuLayout

    }

    @Override
    public int getItemCount() {
        return banknumbers == null ? 0 : banknumbers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_bankname, tv_banknumber;
        private SwipeMenuLayout swipeMenuLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bankname = itemView.findViewById(R.id.tv_item_bankname);
            tv_banknumber = itemView.findViewById(R.id.tv_banknumber);
            swipeMenuLayout=itemView.findViewById(R.id.swipeMenuLayout);

        }
    }
}
