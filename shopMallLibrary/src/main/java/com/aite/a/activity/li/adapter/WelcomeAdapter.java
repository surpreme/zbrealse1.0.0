package com.aite.a.activity.li.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<String> mDatas;

    public WelcomeAdapter(Context context, List<String> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }


    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public WelcomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.img_layout, parent, false);
        WelcomeAdapter.ViewHolder viewHolder = new WelcomeAdapter.ViewHolder(view);
        return viewHolder;
    }

    public OnclickViewListener getOnclickViewListener() {
        return onclickViewListener;
    }

    public void setOnclickViewListener(OnclickViewListener onclickViewListener) {
        this.onclickViewListener = onclickViewListener;
    }

    OnclickViewListener onclickViewListener;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(mDatas.get(position)).into(holder.welcome_img);
        if (position == mDatas.size()-1) {
            holder.welcome_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickViewListener.postion(position);

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView welcome_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            welcome_img = itemView.findViewById(R.id.welcome_img);


        }
    }
    public interface OnclickViewListener{
        void postion(int i);
    }
}