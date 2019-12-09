package com.aite.a.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.model.NavigationInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

import static com.community.utils.ClutterUtils.getScreenWidth;

/**
 * Created by Changer on 2019/3/22
 */

public class HomeNavigRvAdapter extends RecyclerView.Adapter<HomeNavigRvAdapter.MyViewHold> {

    private final List<NavigationInfo> items;
    private final int screenWidth;

    public HomeNavigRvAdapter(Activity activity, List<NavigationInfo> navigationInfo) {
        this.items = navigationInfo;
        screenWidth = getScreenWidth(activity);
    }

    @NonNull
    @Override
    public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bc_navigation, parent, false);
        return new MyViewHold(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {
        NavigationInfo data = this.items.get(position);
        holder.tv_miyamenutext.setText(data.name);
        holder.iv_miyamenuiag.setImageResource(data.img);

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class MyViewHold extends RecyclerView.ViewHolder{
        TextView tv_miyamenutext;
        ImageView iv_miyamenuiag;
        LinearLayout ll_item;
        MyViewHold(View itemView) {
            super(itemView);
            tv_miyamenutext =  itemView
                    .findViewById(R.id.tv_miyamenutext);
            iv_miyamenuiag = itemView
                    .findViewById(R.id.iv_miyamenuiag);
            int itemWidth = screenWidth / 5;
            ll_item = itemView.findViewById(R.id.ll_item);
            ViewGroup.LayoutParams layoutParams = ll_item.getLayoutParams();
            layoutParams.width = itemWidth ;
//            ll_item.setLayoutParams(layoutParams);

        }
    }
}
