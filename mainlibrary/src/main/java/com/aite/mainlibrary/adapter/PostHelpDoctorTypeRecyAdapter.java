package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.TypeBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostHelpDoctorTypeRecyAdapter extends RecyclerView.Adapter<PostHelpDoctorTypeRecyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<TypeBean.ClassBean> classBeans;

    public PostHelpDoctorTypeRecyAdapter(Context context, List<TypeBean.ClassBean> classBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.classBeans = classBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.text, parent, false);
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


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(classBeans.get(position).getClass_name());
        holder.itemView.setAlpha(0.8f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.getPostion(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return classBeans == null ? 0 : classBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.text)
        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
