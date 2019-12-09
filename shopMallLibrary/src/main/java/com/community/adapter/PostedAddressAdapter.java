package com.community.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.community.model.SelectAddressInfo;

import java.util.List;


/**
 * 发布动态地址
 * Created by Administrator on 2017/12/16.
 */
public class PostedAddressAdapter extends BaseAdapter {
    private Context mContext;
    private Handler handler;
    private List<SelectAddressInfo> data;

    public PostedAddressAdapter(Context mContext, Handler handler, List<SelectAddressInfo> data) {
        this.mContext = mContext;
        this.handler = handler;
        this.data = data;
    }

    /**
     * 刷新
     * @param data
     */
    public void refreshData(List<SelectAddressInfo> data){
        if (data==null)return;
        this.data=data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView = View.inflate(mContext, R.layout.item_postedaddress, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.tv_name.setText(data.get(position).address);
        holder.tv_desc.setText(data.get(position).addressdesc);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(106, data.get(position).address));
            }
        });
        if (data.get(position).ischoose) {
            holder.iv_choose.setVisibility(View.VISIBLE);
        } else {
            holder.iv_choose.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_name,tv_desc;
        ImageView iv_choose;
        RelativeLayout rl_item;

        public ViewHolder(View convertView) {
            tv_name = convertView.findViewById(R.id.tv_name);
            tv_desc = convertView.findViewById(R.id.tv_desc);
            iv_choose = convertView.findViewById(R.id.iv_choose);
            rl_item = convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}

