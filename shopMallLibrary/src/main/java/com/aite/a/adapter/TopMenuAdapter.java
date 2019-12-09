package com.aite.a.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.aite.a.model.NewsclassifyInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 顶部菜单
 * Created by mayn on 2018/4/20.
 */

public class TopMenuAdapter extends RecyclerView.Adapter<TopMenuHolder> {
    private Context mcontext;
    private List<NewsclassifyInfo> newsclassifyInfo;
    private Handler handler;
    int width;
    public TopMenuAdapter(Context mcontext, List<NewsclassifyInfo> newsclassifyInfo, Handler handler) {
        this.mcontext = mcontext;
        this.newsclassifyInfo = newsclassifyInfo;
        this.handler = handler;
        WindowManager wm= (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    /**
     * 选中
     * @param id
     */
    private void setmenu(int id){
        for (int i=0;i<newsclassifyInfo.size();i++){
            newsclassifyInfo.get(i).ischoose=false;
        }
        newsclassifyInfo.get(id).ischoose=true;
        handler.sendMessage(handler.obtainMessage(211,newsclassifyInfo.get(id).class_id));
        notifyDataSetChanged();
    }

    @Override
    public TopMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TopMenuHolder holder = new TopMenuHolder(LayoutInflater.from(mcontext)
                .inflate(R.layout.information_topmenu_item2, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TopMenuHolder holder, final int position) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.tv_menuname.getLayoutParams();
//        layoutParams.weight=width/4;
//        holder.tv_menuname.setLayoutParams(layoutParams);
        NewsclassifyInfo newsclassifyInfo = this.newsclassifyInfo.get(position);
        holder.tv_menuname.setText(newsclassifyInfo.class_name);
        if (newsclassifyInfo.ischoose){
            holder.tv_menuname.setTextColor(Color.RED);
        }else{
            holder.tv_menuname.setTextColor(0xff6D6D6D);
        }
        holder.tv_menuname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmenu(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsclassifyInfo==null?0:newsclassifyInfo.size();
    }
}

class TopMenuHolder extends RecyclerView.ViewHolder {
    TextView tv_menuname;
    public TopMenuHolder(View itemView) {
        super(itemView);
        tv_menuname = (TextView) itemView
                .findViewById(R.id.tv_menuname);
    }
}