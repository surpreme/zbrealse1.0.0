package com.aite.a.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 商品参数
 * Created by mayn on 2018/11/14.
 */
public class GoodsParameterAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GoodsDetailsInfo.GoodsInfo.goods_param_info> goods_param_info;

    public GoodsParameterAdapter(Context mcontext, List<GoodsDetailsInfo.GoodsInfo.goods_param_info> goods_param_info) {
        this.mcontext = mcontext;
        this.goods_param_info = goods_param_info;
    }

    @Override
    public int getCount() {
        return goods_param_info == null ? 0 : goods_param_info.size();
    }

    @Override
    public Object getItem(int i) {
        return goods_param_info == null ? null : goods_param_info.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_goodsparameter, null);
            new GoodsParameterHolder(view);
        }
        GoodsParameterHolder holder = (GoodsParameterHolder) view.getTag();
        GoodsDetailsInfo.GoodsInfo.goods_param_info goods_param_info = this.goods_param_info.get(i);
        holder.tv_title.setText(goods_param_info.name);
        GoodsParameter2Adapter adapter = new GoodsParameter2Adapter(goods_param_info.data);
        holder.lv_list.setAdapter(adapter);
        return view;
    }

    class GoodsParameterHolder {
        TextView tv_title;
        MyListView lv_list;

        public GoodsParameterHolder(View view) {
            tv_title = view.findViewById(R.id.tv_title);
            lv_list = view.findViewById(R.id.lv_list);
            view.setTag(this);
        }
    }

    class GoodsParameter2Adapter extends BaseAdapter {
        private List<GoodsDetailsInfo.GoodsInfo.goods_param_info.data> data;

        public GoodsParameter2Adapter(List<GoodsDetailsInfo.GoodsInfo.goods_param_info.data> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int i) {
            return data == null ? null : data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mcontext, R.layout.item_goodsparameter2, null);
                new GoodsParameter2(view);
            }
            GoodsParameter2 holder = (GoodsParameter2) view.getTag();
            GoodsDetailsInfo.GoodsInfo.goods_param_info.data data = this.data.get(i);
            holder.tv_name.setText(data.param_name);
            holder.tv_val.setText(data.param_value);
            return view;
        }

        class GoodsParameter2 {
            TextView tv_name, tv_val;

            public GoodsParameter2(View view) {
                tv_name = view.findViewById(R.id.tv_name);
                tv_val = view.findViewById(R.id.tv_val);
                view.setTag(this);
            }
        }
    }
}
