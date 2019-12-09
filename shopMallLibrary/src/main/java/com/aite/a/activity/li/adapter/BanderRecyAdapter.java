package com.aite.a.activity.li.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.GoodsListActivity;
import com.aite.a.activity.li.bean.BrandBean;
import com.aiteshangcheng.a.R;
import com.weixin.paydemo.Constants;

import java.util.ArrayList;
import java.util.List;

public class BanderRecyAdapter extends RecyclerView.Adapter<BanderRecyAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    public static final int ITEM_TYPE = 100;
    private List<BrandBean.DatasBean> mDatas;

    public BanderRecyAdapter(Context context, List<BrandBean.DatasBean> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    } //重写改方法，设置ItemViewType

    @Override
    public int getItemViewType(int position) {
        //返回值与使用时设置的值需保持一致
        return ITEM_TYPE;
    }


    public OnclickInterface getOnclickInterface() {
        return onclickInterface;
    }

    public void setOnclickInterface(OnclickInterface onclickInterface) {
        this.onclickInterface = onclickInterface;
    }

    private OnclickInterface onclickInterface;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.text_itemlayout, parent, false);
        BanderRecyAdapter.ViewHolder viewHolder = new BanderRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        String currentStrLetter = mDatas.get(position).getBrand_name().charAt(0) + "";
//        String currentStrLetter = (SpUtils.getPingYin(mDatas.get(position).getBrand_name()).toUpperCase()).charAt(0) + "";
//        if (position > 0) {
//            String lastStrLetter = (SpUtils.getPingYin(mDatas.get(position - 1).getBrand_name()).toUpperCase()).charAt(0) + "";
//            if (lastStrLetter.equals(currentStrLetter)) {
//                holder.tv_index.setVisibility(View.GONE);
//            } else {
//                holder.tv_index.setText(currentStrLetter);
//                holder.tv_index.setVisibility(View.VISIBLE);
//            }
//        } else {
//            holder.tv_index.setText(currentStrLetter);
//            holder.tv_index.setVisibility(View.VISIBLE);
//        }
        holder.tv_index.setText(mDatas.get(position).getTitle());
        holder.list.clear();
        holder.list.addAll(mDatas.get(position).getList());
//        if (holder.banderChridenRecyAdapter==null){
        holder.banderChridenRecyAdapter = new BanderChridenRecyAdapter(context, holder.list);
        GridLayoutManager layoutManage = new GridLayoutManager(context, 4);
        holder.gridrecy.setLayoutManager(layoutManage);
        if (holder.gridrecy.getItemDecorationCount() == 0) {
            holder.gridrecy.addItemDecoration(new GridSpacingItemDecoration(4, 20, false));
        }
        holder.gridrecy.setAdapter(holder.banderChridenRecyAdapter);
//        }
//        holder.textView.setText(mDatas.get(position).getBrand_name());
//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onclickInterface.getPostion(position);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_index;
        LinearLayout layout;
        RecyclerView gridrecy;
        private List<BrandBean.DatasBean.ListBean> list = new ArrayList<>();
        private BanderChridenRecyAdapter banderChridenRecyAdapter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_index = itemView.findViewById(R.id.tv_index);
            layout = itemView.findViewById(R.id.layout);
            gridrecy = itemView.findViewById(R.id.gridrecy);


        }
    }

    public interface OnclickInterface {
        void getPostion(int postion);
    }

    public class BanderChridenRecyAdapter extends RecyclerView.Adapter<BanderChridenRecyAdapter.ViewHolder> {
        private Context context;
        private List<BrandBean.DatasBean.ListBean> mDatas;



        public BanderChridenRecyAdapter(Context context, List<BrandBean.DatasBean.ListBean> mDatas) {
            this.context = context;
            this.mDatas = mDatas;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.textView.setText(mDatas.get(position).getBrand_name());
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
//            holder.textView.setLin
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.size()<position)return;
                    if (mDatas.get(position).getBrand_id()==null||mDatas.get(position).getBrand_name()==null)return;
                    Bundle bundle = new Bundle();
                    bundle.putString("b_id", mDatas.get(position).getBrand_id());
                    bundle.putString("gc_name", mDatas.get(position).getBrand_name());
                    Intent intent = new Intent(context, GoodsListActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }


}
