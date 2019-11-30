package com.aite.mainlibrary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class RecyMainIconAdapter extends RecyclerView.Adapter<RecyMainIconAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private int[] imgs;
    private String[] names;
    private static float screenwidth;
    private OnClickLstenerInterface.OnRecyClickInterface onRecyClickInterface;

    public OnClickLstenerInterface.OnRecyClickInterface getOnRecyClickInterface() {
        return onRecyClickInterface;
    }

    public void setOnRecyClickInterface(OnClickLstenerInterface.OnRecyClickInterface onRecyClickInterface) {
        this.onRecyClickInterface = onRecyClickInterface;
    }

    public RecyMainIconAdapter(Context context, int[] imgs, String[] names, float screenwidth) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imgs = imgs;
        this.names = names;
        this.screenwidth = screenwidth / 5;

    }

    @NonNull
    @Override
    public RecyMainIconAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mine_setting_item, parent, false);
        RecyMainIconAdapter.ViewHolder viewHolder = new RecyMainIconAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyMainIconAdapter.ViewHolder holder, final int position) {
        if (imgs != null && names != null) {
            holder.title.setText(names[position]);
            holder.icon.setImageResource(imgs[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyClickInterface.getPostion(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return names == null ? 0 : names.length;
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.title)
        TextView title;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            itemView.getLayoutParams().width = x;
            if (screenwidth == 0) return;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) screenwidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(layoutParams);
        }
    }
}
