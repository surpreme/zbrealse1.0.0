package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.TypeChoiceUIBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceEatTypeRecyAdapter extends RecyclerView.Adapter<ChoiceEatTypeRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<TypeChoiceUIBean.ListClassBean> listClassBeans;

    public ChoiceEatTypeRecyAdapter(Context context, List<TypeChoiceUIBean.ListClassBean> listClassBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listClassBeans = listClassBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.choicetypeeat_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public OnClickLstenerInterface.OnRecyClickInterface getLstenerInterface() {
        return lstenerInterface;
    }

    public void setLstenerInterface(OnClickLstenerInterface.OnRecyClickInterface lstenerInterface) {
        this.lstenerInterface = lstenerInterface;
    }

    private OnClickLstenerInterface.OnRecyClickInterface lstenerInterface;

    /**
     * gc_id : 16
     * gc_name : 特色经典
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.typeTv.setText(listClassBeans.get(position).getGc_name());
    }

    @Override
    public int getItemCount() {
        return listClassBeans == null ? 0 : listClassBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.type_tv)
        TextView typeTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
