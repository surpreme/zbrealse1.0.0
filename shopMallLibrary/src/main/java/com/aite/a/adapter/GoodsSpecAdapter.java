package com.aite.a.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.model.GoodsDetailsInfo;
import com.aiteshangcheng.a.R;
import com.donkingliang.labels.LabelsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aite.a.base.Mark.GOODS_SETSPEC;

/**
 * 商品规格
 * Created by mayn on 2018/11/14.
 */
public class GoodsSpecAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GoodsDetailsInfo.GoodsInfo.goods_spec_info> goods_spec_info;
    private Handler handler;

    public GoodsSpecAdapter(Context mcontext, List<GoodsDetailsInfo.GoodsInfo.goods_spec_info> goods_spec_info, Handler handler) {
        this.mcontext = mcontext;
        this.goods_spec_info = goods_spec_info;
        this.handler = handler;
    }

    /**
     * 修改选中
     *
     * @param id
     * @param spec_value
     */
    private void setPick(int id, List<GoodsDetailsInfo.GoodsInfo.goods_spec_info.spec_value> spec_value) {
        for (int j = 0; j < spec_value.size(); j++) {
            spec_value.get(j).ispick = false;
        }
        spec_value.get(id).ispick = true;
        notifyDataSetChanged();
        handler.sendMessage(handler.obtainMessage(GOODS_SETSPEC, getId()));
    }

    /**
     * 获取选中位置
     *
     * @param spec_value
     * @return
     */
    private int getPickPosition(List<GoodsDetailsInfo.GoodsInfo.goods_spec_info.spec_value> spec_value) {
        for (int j = 0; j < spec_value.size(); j++) {
            if (spec_value.get(j).ispick) {
                return j;
            }
        }
//        spec_value.get(0).ispick = true;
        return -1;
    }

    /**
     * 获取选中ID
     *
     * @return
     */
    public Map<String,String> getId() {
        List<Integer> ids = new ArrayList<>();
        StringBuffer name=new StringBuffer();
        int num=0;
        for (int i = 0; i < goods_spec_info.size(); i++) {
            for (int j = 0; j < goods_spec_info.get(i).spec_value.size(); j++) {
                if (goods_spec_info.get(i).spec_value.get(j).ispick) {
                    ids.add(Integer.parseInt(goods_spec_info.get(i).spec_value.get(j).id));
                    name.append(goods_spec_info.get(i).spec_value.get(j).value+"\t");
                    num++;
                    continue;
                }
            }
        }
        if (goods_spec_info.size()!=num)return null;
        Collections.sort(ids);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            str.append(ids.get(i) + "|");
        }
        String str2 = str.toString().substring(0, str.toString().length() - 1);
        Map<String,String>map=new HashMap<>();
        map.put("id",str2);
        map.put("name",name.toString());
        return map;
    }

    @Override
    public int getCount() {
        return goods_spec_info == null ? 0 : goods_spec_info.size();
    }

    @Override
    public Object getItem(int i) {
        return goods_spec_info == null ? null : goods_spec_info.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_goodsspec, null);
            new GoodsSpecHolder(view);
        }
        GoodsSpecHolder holder = (GoodsSpecHolder) view.getTag();
        final GoodsDetailsInfo.GoodsInfo.goods_spec_info goods_spec_info = this.goods_spec_info.get(i);
        holder.tv_name.setText(goods_spec_info.spec_name);

        holder.labels.setLabels(goods_spec_info.spec_value, new LabelsView.LabelTextProvider<GoodsDetailsInfo.GoodsInfo.goods_spec_info.spec_value>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, GoodsDetailsInfo.GoodsInfo.goods_spec_info.spec_value data) {
                return data.value;
            }
        });
        int pickPosition = getPickPosition(goods_spec_info.spec_value);
        if (pickPosition!=-1){
            holder.labels.setSelects(pickPosition);//设置选中
        }
        holder.labels.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                setPick(position, goods_spec_info.spec_value);
            }
        });
        return view;
    }

    class GoodsSpecHolder {
        TextView tv_name;
        LabelsView labels;

        public GoodsSpecHolder(View view) {
            tv_name = view.findViewById(R.id.tv_name);
            labels = view.findViewById(R.id.labels);
            view.setTag(this);
        }
    }
}
