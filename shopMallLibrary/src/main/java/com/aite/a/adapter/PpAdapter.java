package com.aite.a.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.model.GoodsListModerInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 品牌
 * Created by Administrator on 2018/1/26.
 */
public class PpAdapter extends BaseAdapter{
    private Context mcontext;
    private List<GoodsListModerInfo.datas.brand_list> brand_list;

    public PpAdapter(Context mcontext, List<GoodsListModerInfo.datas.brand_list> brand_list) {
        this.mcontext = mcontext;
        this.brand_list = brand_list;
    }

    /**
     * 重置
     */
    public void reset(){
        for (int i=0;i<brand_list.size();i++){
            brand_list.get(i).ischoose=false;
        }
        notifyDataSetChanged();
    }

    /**
     * 获取品牌编号
     * @return
     */
    public String getb_id(){
        for (int i=0;i<brand_list.size();i++){
            if (brand_list.get(i).ischoose){
                return brand_list.get(i).brand_id;
            }
        }
        return null;
    }

    /**
     * 选中
     * @param id
     */
    private void choose(int id){
        for (int i=0;i<brand_list.size();i++){
            brand_list.get(i).ischoose=false;
        }
        brand_list.get(id).ischoose=true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return brand_list.size();
    }

    @Override
    public Object getItem(int position) {
        return brand_list==null?null:brand_list.get(position);
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
        final GoodsListModerInfo.datas.brand_list brand = this.brand_list.get(position);
        if (brand.ischoose){
            holder.tv_zp.setBackgroundResource(R.drawable.goods_screen3);
            holder.tv_zp.setTextColor(0XFFFFFFFF);
        }else{
            holder.tv_zp.setBackgroundResource(R.drawable.goods_screen2);
            holder.tv_zp.setTextColor(0XFF808080);
        }
        holder.tv_zp.setText(brand.brand_name);
        holder.tv_zp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择
                choose(position);
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
