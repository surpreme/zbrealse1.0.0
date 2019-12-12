package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.bean.IImgBaseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayRadioGroupRecyAdapter extends RecyclerView.Adapter<PayRadioGroupRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<? extends IImgBaseBean> listBean;

    public PayRadioGroupRecyAdapter(Context context, /*TestBean testBean*/List<? extends IImgBaseBean> bean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = bean;
    }

    public void clearData() {
        if (!listBean.isEmpty()) {
            listBean.clear();
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pay_radiogroup_recy_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private OnClickLstenerInterface.OnRecyClickInterface clickInterface;
    private OnClickLstenerInterface.OnRecyClickInterfaceAndString clickInterfaceAndString;

    public OnClickLstenerInterface.OnRecyClickInterfaceAndString getClickInterfaceAndString() {
        return clickInterfaceAndString;
    }

    public void setClickInterfaceAndString(OnClickLstenerInterface.OnRecyClickInterfaceAndString clickInterfaceAndString) {
        this.clickInterfaceAndString = clickInterfaceAndString;
    }

    public OnClickLstenerInterface.OnRecyClickInterface getClickInterface() {
        return clickInterface;
    }

    public void setClickInterface(OnClickLstenerInterface.OnRecyClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(listBean.get(position).getNasme());
        Glide.with(context).load(listBean.get(position).getImg()).into(holder.ivImg);
        holder.payGetCheckbox.setChecked(listBean.get(position).isIsCheck());
        holder.payGetCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < listBean.size(); i++) {
                        if (i == position) listBean.get(i).setChecked(true);
                        else listBean.get(i).setChecked(false);
                    }
                    notifyDataSetChanged();
                    clickInterface.getPostion(Integer.valueOf(listBean.get(position).getId()));

                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listBean.get(position).setChecked();
                clickInterface.getPostion(Integer.valueOf(listBean.get(position).getId()));
//                clickInterfaceAndString.getPostion();
                listBean.get(position).setChecked(true);
                for (int i = 0; i < listBean.size(); i++) {
                    if (i == position) listBean.get(i).setChecked(true);
                    else listBean.get(i).setChecked(false);
                }
                notifyDataSetChanged();
            }

        });

    }

    @Override
    public int getItemCount() {
        return listBean == null ? 0 : listBean.size();
    }


    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.iv_img)
        ImageView ivImg;
        @BindView(R2.id.tv_title)
        TextView tvTitle;
        @BindView(R2.id.pay_get_checkbox)
        RadioButton payGetCheckbox;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
