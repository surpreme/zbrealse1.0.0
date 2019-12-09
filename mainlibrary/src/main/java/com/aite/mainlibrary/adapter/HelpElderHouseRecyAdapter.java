package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.HelpElderHouseListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HelpElderHouseRecyAdapter extends RecyclerView.Adapter<HelpElderHouseRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<HelpElderHouseListBean.ListBean> listBean;

    public HelpElderHouseRecyAdapter(Context context, List<HelpElderHouseListBean.ListBean> listBean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recy_item_helpdoctorhouse, parent, false);
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
     * datas->list[]	数组	列表
     * datas->list[]->store_id	整型	养老院ID
     * datas->list[]->store_name	字符串	养老院名称
     * datas->list[]->store_avatar	字符串	头像
     * datas->list[]->is_binding	整型	是否已绑定 1是
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTv.setText(listBean.get(position).getStore_name());
        Glide.with(context).load(listBean.get(position).getStore_avatar()).into(holder.userIconIv);
        holder.stateTv.setText(listBean.get(position).getIs_binding().equals("1") ? "解绑" : "已绑定");
        holder.stateTv.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R2.id.user_icon_iv)
        ImageView userIconIv;
        @BindView(R2.id.name_tv)
        TextView nameTv;
        @BindView(R2.id.state_tv)
        TextView stateTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
