package com.community.adapter;

import android.content.Context;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.community.model.ForumClassifyInfo;

import java.util.List;

import static com.aite.a.base.Mark.FORUMMENU;

/**
 * 帖子分类
 * Created by mayn on 2018/9/19.
 */
public class ForumListMenuAdapter extends RecyclerView.Adapter<ForumListMenuHolder> {
    private Context mcontext;
    private List<ForumClassifyInfo> data;
    private Handler handler;

    public ForumListMenuAdapter(Context mcontext, List<ForumClassifyInfo> data, Handler handler) {
        this.mcontext = mcontext;
        this.data = data;
        this.handler = handler;
    }

    /**
     * 选中
     *
     * @param id
     */
    private void setpick(int id) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).ispick = false;
        }
        data.get(id).ispick = true;
        notifyDataSetChanged();
        handler.sendMessage(handler.obtainMessage(FORUMMENU,data.get(id).class_id));
    }

    @Override
    public ForumListMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ForumListMenuHolder holder = new ForumListMenuHolder(LayoutInflater.from(mcontext)
                .inflate(R.layout.item_forumlistmenu, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ForumListMenuHolder holder, final int position) {
        ForumClassifyInfo forumClassifyInfo = data.get(position);
        if (forumClassifyInfo.ispick) {
            holder.v_slide.setVisibility(View.VISIBLE);
        } else {
            holder.v_slide.setVisibility(View.GONE);
        }
        holder.tv_menu.setText(forumClassifyInfo.class_name);
        holder.tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setpick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

class ForumListMenuHolder extends RecyclerView.ViewHolder {
    TextView tv_menu;
    View v_slide;

    public ForumListMenuHolder(View itemView) {
        super(itemView);
        tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
        v_slide = itemView.findViewById(R.id.v_slide);
    }
}