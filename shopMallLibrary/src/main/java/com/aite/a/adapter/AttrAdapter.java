package com.aite.a.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.model.GoodsListModerInfo;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 筛选属性
 * Created by Administrator on 2018/1/26.
 */
public class AttrAdapter extends BaseAdapter {
    private Context mcontext;
    List<GoodsListModerInfo.datas.attr_list> attr_list;

    public AttrAdapter(Context mcontext, List<GoodsListModerInfo.datas.attr_list> attr_list) {
        this.mcontext = mcontext;
        this.attr_list = attr_list;
    }

    /**
     * 重置
     */
    public void reset(){
        for (int i=0;i<attr_list.size();i++){
            for (int j=0;j<attr_list.get(i).value.size();j++){
                attr_list.get(i).value.get(j).ischoose=false;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 获取选中属性编号
     */
    public String geta_id(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<attr_list.size();i++){
            for (int j=0;j<attr_list.get(i).value.size();j++){
                if (attr_list.get(i).value.get(j).ischoose){
                    stringBuffer.append(attr_list.get(i).value.get(j).attr_value_id+"_");
                }
            }
        }
        String a_id = stringBuffer.toString();
        if (a_id!=null&&a_id.length()!=0){
            a_id=a_id.substring(0,a_id.length()-1);
            return a_id;
        }
        return null;
    }

    @Override
    public int getCount() {
        return attr_list.size();
    }

    @Override
    public Object getItem(int position) {
        return attr_list == null ? null : attr_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_attr1, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        GoodsListModerInfo.datas.attr_list attr = this.attr_list.get(position);
        holder.tv_name.setText(attr.name);
        ValueAdapter valueAdapter=new ValueAdapter(attr.value);
        holder.gv_attr.setAdapter(valueAdapter);
//        holder.gv_attr
        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
        MyGridView gv_attr;

        public ViewHolder(View convertView) {
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            gv_attr = (MyGridView) convertView.findViewById(R.id.gv_attr);
            convertView.setTag(this);
        }
    }

    class ValueAdapter extends BaseAdapter {
        List<GoodsListModerInfo.datas.attr_list.value> value;

        public ValueAdapter(List<GoodsListModerInfo.datas.attr_list.value> value) {
            this.value = value;
        }

        @Override
        public int getCount() {
            return value.size();
        }

        @Override
        public Object getItem(int position) {
            return value == null ? null : value.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mcontext, R.layout.item_attr, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();
            final GoodsListModerInfo.datas.attr_list.value value = this.value.get(position);
            if (value.ischoose){
                holder.tv_zp.setBackgroundResource(R.drawable.goods_screen3);
                holder.tv_zp.setTextColor(0XFFFFFFFF);
            }else{
                holder.tv_zp.setBackgroundResource(R.drawable.goods_screen2);
                holder.tv_zp.setTextColor(0XFF808080);
            }
            holder.tv_zp.setText(value.attr_value_name);
            holder.tv_zp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择
                    if (value.ischoose){
                        value.ischoose=false;
                    }else{
                        value.ischoose=true;
                    }
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHodler {
            TextView tv_zp;

            public ViewHodler(View convertView) {
                tv_zp = (TextView) convertView.findViewById(R.id.tv_zp);
                convertView.setTag(this);
            }
        }
    }
}
