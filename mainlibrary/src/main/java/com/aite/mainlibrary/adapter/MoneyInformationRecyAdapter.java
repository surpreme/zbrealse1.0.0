package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneyInformationRecyAdapter extends RecyclerView.Adapter<MoneyInformationRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<String> banknames;
    private List<String> banknumbers;

    public MoneyInformationRecyAdapter(Context context, List<String> banknames, List<String> banknumbers) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.banknames = banknames;
        this.banknumbers = banknumbers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.moneyinformation, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.swipeMenuLayout

    }

    @Override
    public int getItemCount() {
        return banknumbers == null ? 0 : banknumbers.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.type_tv)
        TextView typeTv;
        @BindView(R2.id.money_number_tv)
        TextView moneyNumberTv;
        @BindView(R2.id.time_tv)
        TextView timeTv;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
