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

import com.aite.mainlibrary.Mainbean.MainUiDataBean;
import com.aite.mainlibrary.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;


public class HelpElderRecyAdapter extends RecyclerView.Adapter<HelpElderRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MainUiDataBean.PensionAdvsBean> pensionAdvsBeans;

    public HelpElderRecyAdapter(Context context, List<MainUiDataBean.PensionAdvsBean> pensionAdvsBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.pensionAdvsBeans = pensionAdvsBeans;
    }

    @NonNull
    @Override
    public HelpElderRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.help_elder, parent, false);
        HelpElderRecyAdapter.ViewHolder viewHolder = new HelpElderRecyAdapter.ViewHolder(view);
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
     * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06269595991492265.png","adv_pic_url":""}
     * adv_title : 助餐
     * adv_desc :
     * page_pension : 1
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(pensionAdvsBeans.get(position).getAdv_title());
        Glide.with(context).
                load(pensionAdvsBeans.get(position).getAdv_content().getAdv_pic())
                .into(holder.backgroundimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstenerInterface.getPostion(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pensionAdvsBeans == null ? 0 : pensionAdvsBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView backgroundimg;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundimg = itemView.findViewById(R.id.background_img);
            title = itemView.findViewById(R.id.text);

        }
    }
}
