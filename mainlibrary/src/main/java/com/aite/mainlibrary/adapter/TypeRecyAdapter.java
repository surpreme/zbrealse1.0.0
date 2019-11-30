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

import com.aite.mainlibrary.Mainbean.ElseTimeBankListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeRecyAdapter extends RecyclerView.Adapter<TypeRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ElseTimeBankListBean.ClassListBean> listBean;

    public TypeRecyAdapter(Context context, List<ElseTimeBankListBean.ClassListBean> listBean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.radiogroup_recy_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private OnClickLstenerInterface.OnRecyClickInterface clickInterface;

    public OnClickLstenerInterface.OnRecyClickInterface getClickInterface() {
        return clickInterface;
    }

    public void setClickInterface(OnClickLstenerInterface.OnRecyClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(listBean.get(position).getNasme());
        if (listBean.get(position).isIsCheck()) {
            holder.ivImg.setVisibility(View.VISIBLE);
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.agreen));
        } else {
            holder.ivImg.setVisibility(View.GONE);
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));

        }
        holder.father_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.getPostion(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBean == null ? 0 : listBean.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_title)
        TextView tvTitle;
        @BindView(R2.id.iv_img)
        ImageView ivImg;
        @BindView(R2.id.father_layout)
        LinearLayout father_layout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
