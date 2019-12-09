package com.aite.a.activity.li.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.activity.li.bean.GuessLikeBean;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class GuessLikeAdapter extends RecyclerView.Adapter<GuessLikeAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<GuessLikeBean.DatasBean> mDatas;

    public GuessLikeAdapter(Context context, List<GuessLikeBean.DatasBean> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }

    public GuessLikeAdapter.OnclickInterface getOnclickInterface() {
        return onclickInterface;
    }

    public void setOnclickInterface(GuessLikeAdapter.OnclickInterface onclickInterface) {
        this.onclickInterface = onclickInterface;
    }

    private GuessLikeAdapter.OnclickInterface onclickInterface;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public GuessLikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.guesslike_item_layout, parent, false);
        GuessLikeAdapter.ViewHolder viewHolder = new GuessLikeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuessLikeAdapter.ViewHolder holder, final int position) {
//        String currentStrLetter = mDatas.get(position).getBrand_name().charAt(0) + "";
        Glide.with(context).load(mDatas.get(position).getGoods_image()).into(holder.gusee_like_img);
        holder.gusee_like_name.setText(mDatas.get(position).getGoods_name());
        holder.gusee_like_price.setText("$"+mDatas.get(position).getGoods_price());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,
                        ProductDetailsActivity.class);
                intent.putExtra("goods_id",mDatas.get(position).getGoods_id());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView gusee_like_price, gusee_like_name;
        ImageView gusee_like_img;
        LinearLayout layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gusee_like_img = itemView.findViewById(R.id.gusee_like_img);
            gusee_like_name = itemView.findViewById(R.id.gusee_like_name);
            gusee_like_price = itemView.findViewById(R.id.gusee_like_price);
            layout = itemView.findViewById(R.id.layout);


        }
    }

    public interface OnclickInterface {
        void getPostion(int postion);
    }

}
