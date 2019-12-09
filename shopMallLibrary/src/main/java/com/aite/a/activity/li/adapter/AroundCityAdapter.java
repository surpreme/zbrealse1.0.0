package com.aite.a.activity.li.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.li.bean.BrandBean;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

public class AroundCityAdapter extends RecyclerView.Adapter<AroundCityAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    public static final int ITEM_TYPE = 10078;
    private List<String> mDatas;

    public AroundCityAdapter(Context context, List<String> datas) {
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


    public BanderRecyAdapter.OnclickInterface getOnclickInterface() {
        return onclickInterface;
    }

    public void setOnclickInterface(BanderRecyAdapter.OnclickInterface onclickInterface) {
        this.onclickInterface = onclickInterface;
    }

    private BanderRecyAdapter.OnclickInterface onclickInterface;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public AroundCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.text_itemlayout, parent, false);
        AroundCityAdapter.ViewHolder viewHolder = new AroundCityAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AroundCityAdapter.ViewHolder holder, final int position) {
        holder.tv_index.setText(mDatas.get(position));
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
        private BanderRecyAdapter.BanderChridenRecyAdapter banderChridenRecyAdapter;


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

}
