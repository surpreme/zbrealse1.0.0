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

import com.aite.mainlibrary.Mainbean.TimeBankListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeBankRecyAdapter extends RecyclerView.Adapter<TimeBankRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<TimeBankListBean.ListBean> listBean;

    public TimeBankRecyAdapter(Context context, List<TimeBankListBean.ListBean> listBean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_air_bank, parent, false);
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

    /**
     * id : 2
     * title : 呼吸
     * member_id : 7
     * class_id : 1
     * address : 南山
     * start_time : 2019-11-24
     * end_time : 01-01
     * credit : 0.00
     * is_order : 1
     * class_name : 测试喘息
     * memebr_avatar : http://zhongbyi.aitecc.com/data/upload/shop/common/default_user_portrait.gif
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.tv_bankname.setText(banknames.get(position));
//        holder.tv_banknumber.setText(banknumbers.get(position));
//        holder.swipeMenuLayout
        Glide.with(context).load(listBean.get(position).getMemebr_avatar()).into(holder.icon);
        holder.titleTv.setText(listBean.get(position).getTitle());
        holder.addressTv.setText(listBean.get(position).getAddress());
        holder.timeTv.setText(String.format("%s-%s", listBean.get(position).getStart_time(), listBean.get(position).getEnd_time()));
        holder.getNumberTv.setText(String.format("%s积分", listBean.get(position).getCredit()));
        holder.serviceBtn.setText(listBean.get(position).getIs_order() == 1 ? "接单" : "已接单");
        holder.serviceBtn.setAlpha(listBean.get(position).getIs_order()==1?1.0f:0.5f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.get_number_tv)
        TextView getNumberTv;
        @BindView(R2.id.address_tv)
        TextView addressTv;
        @BindView(R2.id.time_tv)
        TextView timeTv;
        @BindView(R2.id.service_btn)
        TextView serviceBtn;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;
        @BindView(R2.id.icon)
        ImageView icon;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
