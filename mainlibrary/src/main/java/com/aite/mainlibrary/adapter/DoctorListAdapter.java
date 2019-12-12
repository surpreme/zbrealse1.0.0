package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.base.BaseRecyclerViewAdapter;
import com.lzy.basemodule.util.TextUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建时间 2019/12/11 10:27
 * 描述: 医生列表
 */
public class DoctorListAdapter extends BaseRecyclerViewAdapter<String> {

    private Context mContext;

    public DoctorListAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_doctor, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(mDataList.get(position));
    }

    class ViewHolder extends BaseRvHolder {
        @BindView(R2.id.iv_icon)
        ImageView mIvIcon;
        @BindView(R2.id.tv_name)
        TextView mTvName;
        @BindView(R2.id.tv_city)
        TextView mTvCity;
        @BindView(R2.id.tv_hospital)
        TextView mTvHospital;
        @BindView(R2.id.tv_site)
        TextView mTvSite;
        @BindView(R2.id.tv_good_at)
        TextView mTvGoodAt;
        @BindView(R2.id.tv_grade)
        TextView mTvGrade;
        @BindView(R2.id.tv_subscribe_num)
        TextView mTvSubscribeNum;
        @BindView(R2.id.tv_consult_num)
        TextView mTvConsultNum;
        @BindView(R2.id.tv_reply_num)
        TextView mTvReplyNum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        protected void bindView(String s) {
            mTvGrade.setText(TextUtil.highlight(mContext, "评分9.4", "9.4", "#FFB400", 0, 0));
            mTvSubscribeNum.setText(TextUtil.highlight(mContext, "预约量232", "232", "#FFB400", 0, 0));
            mTvConsultNum.setText(TextUtil.highlight(mContext, "咨询量454", "454", "#FFB400", 0, 0));
            mTvReplyNum.setText(TextUtil.highlight(mContext, "回复速度快", "快", "#FFB400", 0, 0));
        }
    }
}
