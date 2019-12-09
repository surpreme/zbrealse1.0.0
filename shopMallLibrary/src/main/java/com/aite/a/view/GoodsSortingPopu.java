package com.aite.a.view;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.model.GoodszhpxInfo;
import com.aiteshangcheng.a.R;


import java.util.ArrayList;
import java.util.List;


/**
 * 商品排序
 * Created by Administrator on 2018/1/11.
 */
public class GoodsSortingPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private ListView lv_px;
    private MyAdapter myAdapter;
    private List<GoodszhpxInfo>goodszhpxInfo;
    public GoodsSortingPopu(final Activity mActivity,int choose) {
        this.mActivity = mActivity;
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_goodssorting, null);
        lv_px = (ListView) view.findViewById(R.id.lv_px);
        goodszhpxInfo=new ArrayList<>();
        goodszhpxInfo.add(new GoodszhpxInfo(mActivity.getString(R.string.join_merchant9),choose==1?true:false));
        goodszhpxInfo.add(new GoodszhpxInfo(mActivity.getString(R.string.join_merchant13),choose==2?true:false));
        goodszhpxInfo.add(new GoodszhpxInfo(mActivity.getString(R.string.join_merchant14),choose==3?true:false));
        goodszhpxInfo.add(new GoodszhpxInfo(mActivity.getString(R.string.join_merchant15),choose==4?true:false));
        myAdapter=new MyAdapter(goodszhpxInfo);
        lv_px.setAdapter(myAdapter);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup4);
    }

    Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    WindowManager.LayoutParams lp = mActivity.getWindow()
                            .getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }

        ;
    };

    private void showEvent() {
        h.sendEmptyMessage(0);
//        h.sendEmptyMessageDelayed(0, 300);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showEvent();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showEvent();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showEvent();
    }

    private class MyAdapter extends BaseAdapter {
        public List<GoodszhpxInfo> data = new ArrayList<>();

        public MyAdapter(List<GoodszhpxInfo> data) {
            this.data = data;
        }

        /**
         * 修改选中
         * @param position
         */
        public void setchoose(int position){
            for (int i=0;i<data.size();i++){
                data.get(i).ischoose=false;
            }
            data.get(position).ischoose=true;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_goodssorting, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            GoodszhpxInfo goodszhpxInfo = data.get(position);
            if (goodszhpxInfo.ischoose){
                holder.iv_choose1.setVisibility(View.VISIBLE);
                holder.tv_comprehensive.setTextColor(0XFFF44848);
            }else{
                holder.iv_choose1.setVisibility(View.GONE);
                holder.tv_comprehensive.setTextColor(0XFF808080);
            }
            holder.tv_comprehensive.setText(goodszhpxInfo.name);
            holder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setchoose(position);
                    if (mmenu!=null){
                        mmenu.onItemClick(position+"");
                    }
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHolder {
            RelativeLayout rl_item;
            TextView tv_comprehensive;
            ImageView iv_choose1;

            public ViewHolder(View convertView) {
                rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
                tv_comprehensive = (TextView) convertView.findViewById(R.id.tv_comprehensive);
                iv_choose1 = (ImageView) convertView.findViewById(R.id.iv_choose1);
                convertView.setTag(this);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(String type);
    }
}
