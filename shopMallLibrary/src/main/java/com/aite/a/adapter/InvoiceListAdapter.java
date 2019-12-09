package com.aite.a.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.model.InvoiceListInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

import static com.aite.a.base.Mark.DELETE_INVOICE;

/**
 * 发票列表
 * Created by mayn on 2018/8/16.
 */

public class InvoiceListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<InvoiceListInfo.data.inv_list> inv_list;
    private Handler handler;

    public InvoiceListAdapter(Context mcontext, List<InvoiceListInfo.data.inv_list> inv_list, Handler handler) {
        this.mcontext = mcontext;
        this.inv_list = inv_list;
        this.handler = handler;
    }

    /**
     * 修改选中
     * @param position
     */
    private void setpick(int position){
        for (int i=0;i<inv_list.size();i++){
            inv_list.get(i).ispick=false;
        }
        inv_list.get(position).ispick=true;
        notifyDataSetChanged();
    }

    /**
     * 获取选中
     * @return
     */
    public InvoiceListInfo.data.inv_list getpick(){
        for (int i=0;i<inv_list.size();i++){
            if (inv_list.get(i).ispick){
                return inv_list.get(i);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return inv_list == null ? 0 : inv_list.size();
    }

    @Override
    public Object getItem(int position) {
        return inv_list == null ? null : inv_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_invoice, null);
            new InvoiceListHolder(convertView);
        }
        InvoiceListHolder holder = (InvoiceListHolder) convertView.getTag();
        final InvoiceListInfo.data.inv_list inv_list = this.inv_list.get(position);
        holder.cb_ordinary.setChecked(inv_list.ispick);
        holder.tv_cen.setText(inv_list.content);
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除
                handler.sendMessage(handler.obtainMessage(DELETE_INVOICE,inv_list.inv_id));
            }
        });
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setpick(position);
            }
        });
        return convertView;
    }

    class InvoiceListHolder {
        RelativeLayout rl_item;
        CheckBox cb_ordinary;
        TextView tv_del,tv_cen;

        public InvoiceListHolder(View convertView) {
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            cb_ordinary = (CheckBox) convertView.findViewById(R.id.cb_ordinary);
            tv_del = (TextView) convertView.findViewById(R.id.tv_del);
            tv_cen = (TextView) convertView.findViewById(R.id.tv_cen);
            convertView.setTag(this);
        }
    }
}
