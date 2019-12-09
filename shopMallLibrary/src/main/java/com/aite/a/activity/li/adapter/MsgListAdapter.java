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

import com.aite.a.activity.li.bean.IconBean;
import com.aite.a.activity.li.bean.MsgBean;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import chat.activity.DialogueActivity;

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<MsgBean.DatasBean.MessageArrayBean> mDatas;
    private List<IconBean.DatasBean> icbean;

    public MsgListAdapter(Context context, List<MsgBean.DatasBean.MessageArrayBean> datas,List<IconBean.DatasBean> icbean) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.icbean=icbean;
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
    public MsgListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.msg_list_layout, parent, false);
        MsgListAdapter.ViewHolder viewHolder = new MsgListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MsgListAdapter.ViewHolder holder, final int position) {
        holder.msg_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DialogueActivity.class);
                intent.putExtra("member_id",String.valueOf(mDatas.get(position).getFrom_member_id()));
                context.startActivity(intent);
            }
        });
        holder.shop_message.setText(mDatas.get(position).getMessage_body());
        holder.shop_name.setText(mDatas.get(position).getFrom_member_name());
        holder.shop_time.setText(stampToDate(1000*Long.valueOf(mDatas.get(position).getMessage_time())));
        for (int i=0;icbean.size()>i;i++){
            if (icbean.get(i).getMember_id().equals(mDatas.get(position).getFrom_member_id())){
                Glide.with(context).load(icbean.get(i).getMember_avatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.shop_icon);
                break;
            }
//            Glide.with(context).load(
//                    icbean.get(i).getMember_id()==mDatas.get(position).getFrom_member_id()?
//                            icbean.get(i).getMember_avatar():null).into(holder.shop_icon);
        }

//        Glide.with(context).load(position < icbean.size() ?icbean.get(position).getMember_avatar():null).into(holder.shop_icon);





    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name, shop_message,shop_time;
        ImageView shop_icon;
        LinearLayout msg_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.tv_shop_name);
            shop_message = itemView.findViewById(R.id.tv_shop_message);
            shop_icon = itemView.findViewById(R.id.im_shop_msg);
            shop_time = itemView.findViewById(R.id.tv_shop_time);
            msg_layout=itemView.findViewById(R.id.msg_layout);


        }
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long timeMillis){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
