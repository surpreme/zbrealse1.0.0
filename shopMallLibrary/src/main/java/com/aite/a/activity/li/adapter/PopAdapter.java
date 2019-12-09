package com.aite.a.activity.li.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.Viewholder> {
    private List<String> mDatas;
    private Context context;
    private LayoutInflater inflater;


    public PopAdapter( Context context,List<String> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.near_pop_item_layout, parent, false);
        PopAdapter.Viewholder viewholder = new PopAdapter.Viewholder(view);
        return viewholder;
    }

    public OnclickInterface getOnclickInterface() {
        return onclickInterface;
    }

    public void setOnclickInterface(OnclickInterface onclickInterface) {
        this.onclickInterface = onclickInterface;
    }

    private OnclickInterface onclickInterface;

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.msg_tv.setText(mDatas.get(position));
        holder.msg_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickInterface.getPostion(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private TextView msg_tv;

        public Viewholder(View itemView) {
            super(itemView);
            msg_tv = itemView.findViewById(R.id.msg_tv);
        }
    }

    public interface OnclickInterface {
        void getPostion(int postion);
    }

}
