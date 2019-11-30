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

public class ElseServiceUIAdapter extends RecyclerView.Adapter<ElseServiceUIAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private int[] imgs;
    private String[] names;
    private int[] backgrounds;

    public OnClickLstenerInterface.OnRecyClickInterface getOnClickLstenerInterface() {
        return onClickLstenerInterface;
    }

    public void setOnClickLstenerInterface(OnClickLstenerInterface.OnRecyClickInterface onClickLstenerInterface) {
        this.onClickLstenerInterface = onClickLstenerInterface;
    }

    private OnClickLstenerInterface.OnRecyClickInterface onClickLstenerInterface;

    public ElseServiceUIAdapter(Context context, int[] imgs, String[] names, int[] backgrounds) {
        this.context = context;
        this.imgs = imgs;
        this.names = names;
        this.backgrounds = backgrounds;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.else_service_icon_otem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.icon.setImageResource(imgs[position]);
        holder.name.setText(names[position]);
        holder.fatherLayout.setBackgroundResource(backgrounds[position]);
        holder.fatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLstenerInterface.getPostion(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return names == null ? 0 : names.length;
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.name)
        TextView name;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
