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

import com.aite.mainlibrary.Mainbean.MineTogetherServiceBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostServiceBookRecyAdapter extends RecyclerView.Adapter<PostServiceBookRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MineTogetherServiceBean.ListBean> listBean;

    public PostServiceBookRecyAdapter(Context context, List<MineTogetherServiceBean.ListBean> listBean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.minepostbook_allrecyitem, parent, false);
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
     * 返回字段	类型	说明
     * datas->list_total	整型	总页数
     * datas->is_nextpage	整型	是否有下一页
     * datas->list[]	数组	服务列表
     * datas->list[]->id	整型	ID
     * datas->list[]->tb_id	整型	服务id
     * datas->list[]->tb_title	字符串	服务标题
     * datas->list[]->member_avatar	字符串	头像
     * datas->list[]->credit	字符串	积分
     * datas->list[]->remarks	字符串	服务备注
     * datas->list[]->status	字符串	状态 1已接单 2参与中 3已完成
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     * <p>
     * 返回字段	类型	说明
     * datas->list_total	整型	总页数
     * datas->is_nextpage	整型	是否有下一页
     * datas->list[]	数组	服务列表
     * datas->list[]->id	整型	ID
     * datas->list[]->title	字符串	服务标题
     * datas->list[]->member_avatar	字符串	头像
     * datas->list[]->credit	字符串	积分
     * datas->list[]->remarks	字符串	服务备注
     * datas->list[]->start_time	字符串	开始时间 年月日
     * datas->list[]->end_time	字符串	结束时间 月日
     * datas->list[]->status	字符串	服务状态 -1已取消 0待审核 1已通过 2已拒绝
     * datas->list[]->order_status	字符串	服务接单状态 -1服务已取消 0待接单 1已接单 2已开始 3已结束 4已评价
     * datas->list[]->is_delete	字符串	是否可以删除 1可以 0不可
     * datas->list[]->is_cancel	字符串	是否可以取消 1可以 0不可isverify
     * datas->list[]->isverify	字符串	是否可以核销 1可以 0不可
     * datas->list[]->qrcodeimg	字符串	核销二维码链接 可以核销是才有
     * datas->list[]->is_evaluate	字符串	是否可以评价 1可以 0不可
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.titleTv.setText(listBean.get(position).getTitle());
        Glide.with(context).load(listBean.get(position).getMemebr_avatar()).into(holder.icon);
        if (listBean.get(position).getStatus().equals("1"))
            holder.isOveringTv.setText("已接单");
        else if (listBean.get(position).getStatus().equals("2"))
            holder.isOveringTv.setText("参与中");
        else if (listBean.get(position).getStatus().equals("3"))
            holder.isOveringTv.setText("已完成");
        holder.elderSureTv.setVisibility(listBean.get(position).getIsverify()==1?View.VISIBLE:View.GONE);
        holder.unusedTv.setVisibility(listBean.get(position).getIs_evaluate()==1?View.VISIBLE:View.GONE);
        if (listBean.get(position).getIsverify()==1){
            holder.elderSureTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopwindowUtils.getmInstance().showImgPopupWindow(context,listBean.get(position).getQrcodeimg());
                }
            });
        }
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
        @BindView(R2.id.isOvering_tv)
        TextView isOveringTv;
        @BindView(R2.id.address_tv)
        TextView addressTv;
        @BindView(R2.id.get_number_tv)
        TextView getNumberTv;
        @BindView(R2.id.time_tv)
        TextView timeTv;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;
        @BindView(R2.id.elder_sure_tv)
        TextView elderSureTv;
        @BindView(R2.id.unused_tv)
        TextView unusedTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
