package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.NumberBankBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberBankRecyAdapter extends RecyclerView.Adapter<NumberBankRecyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<NumberBankBean.ListBean> numberbankbean;

    public NumberBankRecyAdapter(Context context, List<NumberBankBean.ListBean> numberbankbean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.numberbankbean = numberbankbean;
    }

    public OnClickLstenerInterface.OnThingClickInterface getOnItemRecyClickInterface() {
        return onItemRecyClickInterface;
    }

    public void setOnItemRecyClickInterface(OnClickLstenerInterface.OnThingClickInterface onItemRecyClickInterface) {
        this.onItemRecyClickInterface = onItemRecyClickInterface;
    }

    private OnClickLstenerInterface.OnThingClickInterface onItemRecyClickInterface;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_numberbank_recy, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * **
     * * pl_id : 27
     * * pl_memberid : 7
     * * pl_membername : 18614079738
     * * pl_adminid : null
     * * pl_adminname : null
     * * pl_points : 1920
     * * pl_addtime : 2019.12.11 09:34
     * * pl_desc : 义工参与时间银行服务【过后风格黄飞鸿风格】获得义工积分奖励
     * * pl_stage : volunteer
     * * pl_module : 1
     * <p>
     * 返回字段	类型	说明
     * datas->list_total	整型	总页数
     * datas->is_nextpage	整型	是否有下一页
     * datas->list[]	数组	记录列表
     * datas->list[]->pl_points	字符串	变动积分数
     * datas->list[]->pl_desc	字符串	描述
     * datas->list[]->pl_addtime	字符串	时间
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     * *
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.typeTv.setText(numberbankbean.get(position).getPl_desc());
        holder.dateTv.setText(numberbankbean.get(position).getPl_addtime());
        holder.numberTv.setText(String.format("+%s", numberbankbean.get(position).getPl_points()));


    }

    @Override
    public int getItemCount() {
        return numberbankbean == null ? 0 : numberbankbean.size();
    }


    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.type_tv)
        TextView typeTv;
        @BindView(R2.id.date_tv)
        TextView dateTv;
        @BindView(R2.id.user_information_ll)
        LinearLayout userInformationLl;
        @BindView(R2.id.number_tv)
        TextView numberTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

}
