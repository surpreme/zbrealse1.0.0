package com.aite.mainlibrary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridViewIconAdapter extends BaseAdapter {
    private Context context;
    private int[] imgs;
    private String[] names;

    public GridViewIconAdapter(Context context, int[] imgs, String[] names) {
        this.context = context;
        this.imgs = imgs;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names == null ? 0 : names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = null;

        if (convertView == null) {
            view = View.inflate(context,
                    R.layout.mine_setting_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        if (imgs != null && names != null) {
            holder.title.setText(names[position]);
            holder.icon.setImageResource(imgs[position]);
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.title)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
