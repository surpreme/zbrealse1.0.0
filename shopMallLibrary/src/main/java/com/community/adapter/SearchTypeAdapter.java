package com.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.community.model.SearchInfo;

import java.util.List;

/**
 * 搜索分类
 * Created by mayn on 2018/9/19.
 */
public class SearchTypeAdapter extends BaseAdapter {
    private Context mcontext;
    private List<SearchInfo> data;

    public SearchTypeAdapter(Context mcontext, List<SearchInfo> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    /**
     * 选中
     * @param id
     */
    private void setpick(int id){
        for (int i=0;i<data.size();i++){
            data.get(i).ispick=false;
        }
        data.get(id).ispick=true;
        notifyDataSetChanged();
    }

    /**
     * 获取选中
     * @return
     */
    public String getpick(){
        for (int i=0;i<data.size();i++){
            if (data.get(i).ispick){
                return data.get(i).name;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_searchtype, null);
            new SearchTypeHolder(convertView);
        }
        SearchInfo searchInfo = data.get(position);
        SearchTypeHolder holder = (SearchTypeHolder) convertView.getTag();
        if (searchInfo.ispick) {
            holder.tv_type.setTextColor(0xffFD6C8B);
        } else {
            holder.tv_type.setTextColor(0xff000000);
        }
        holder.tv_type.setText(searchInfo.name);
        holder.tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setpick(position);
            }
        });
        return convertView;
    }

    class SearchTypeHolder {
        TextView tv_type;

        public SearchTypeHolder(View convertView) {
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(this);
        }
    }
}
