package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.OnClickLstenerInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneyCardRecyAdapter extends RecyclerView.Adapter<MoneyCardRecyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private int[] imgs;
    private String[] names;
    private OnClickLstenerInterface.OnRecyClickInterface onRecyClickInterface;

    public OnClickLstenerInterface.OnRecyClickInterface getOnRecyClickInterface() {
        return onRecyClickInterface;
    }

    public void setOnRecyClickInterface(OnClickLstenerInterface.OnRecyClickInterface onRecyClickInterface) {
        this.onRecyClickInterface = onRecyClickInterface;
    }

    public MoneyCardRecyAdapter(Context context, int[] imgs, String[] names) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imgs = imgs;
        this.names = names;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemmine_money_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.icon.setImageResource(imgs[position]);
        holder.typeTv.setText(names[position]);
        holder.fatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyClickInterface.getPostion(position);

            }
        });
//        holder.tv_bankname.setText(banknames.get(position));
//        holder.tv_banknumber.setText(banknumbers.get(position));
//        holder.swipeMenuLayout

    }

    @Override
    public int getItemCount() {
        return names == null ? 0 : names.length;
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.type_tv)
        TextView typeTv;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
