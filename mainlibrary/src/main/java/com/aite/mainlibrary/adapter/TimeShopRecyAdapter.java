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

import com.aite.mainlibrary.Mainbean.TimeShoplistBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimeShopRecyAdapter extends RecyclerView.Adapter<TimeShopRecyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<TimeShoplistBean.ListBean> listBean;

    public TimeShopRecyAdapter(Context context, List<TimeShoplistBean.ListBean> listBean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_time_shop, parent, false);
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
     * **
     * * pgoods_id : 4
     * * pgoods_name : 西装
     * * pgoods_points : 5000
     * * pgoods_image : http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287977541665083_mid.png
     * * pgoods_image_old : 06287977541665083.png
     * * pgoods_image_small : http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287977541665083_small.png
     * * ex_state : end
     * *
     * datas->list_total	整型	总页数
     * datas->is_nextpage	整型	是否有下一页
     * datas->list[]	数组	记录列表
     * datas->list[]->pgoods_id	字符串	礼品id
     * datas->list[]->pgoods_name	字符串	礼品名称
     * datas->list[]->pgoods_points	字符串	所需积分
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listBean.get(position).getPgoods_image_small()).into(holder.icon);
        holder.titleTv.setText(listBean.get(position).getPgoods_name());
        holder.getNumberTv.setText(String.format("%s积分", listBean.get(position).getPgoods_points()));
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
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.get_number_tv)
        TextView getNumberTv;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;
        @BindView(R2.id.sure_get_btn)
        TextView sureGetBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
