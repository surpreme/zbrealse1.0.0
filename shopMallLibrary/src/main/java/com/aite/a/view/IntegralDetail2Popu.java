package com.aite.a.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.model.IntegralTypeInfo;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;

import java.util.List;


/**
 * 积分详情
 */
public class IntegralDetail2Popu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private List<IntegralTypeInfo> integralTypeInfo;
    private TypeAdapter typeAdapter;
    private TextView tv_reset, tv_affirm;
    private GridView gv_list;

    public IntegralDetail2Popu(Activity activity, List<IntegralTypeInfo> integralTypeInfo) {
        mActivity = activity;
        this.integralTypeInfo = integralTypeInfo;
//        WindowManager wm = mActivity.getWindowManager();
//        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(ClutterUtils.getScreenHeight(mActivity) / 3);
        View view = View.inflate(mActivity, R.layout.popupwindow_integraldetail2, null);
        tv_reset = view.findViewById(R.id.tv_reset);
        tv_affirm = view.findViewById(R.id.tv_affirm);
        gv_list = view.findViewById(R.id.gv_list);
        tv_affirm.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        typeAdapter = new TypeAdapter(integralTypeInfo);
        gv_list.setAdapter(typeAdapter);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

        // 设置PopupWindow弹出窗体动画效果
//        setAnimationStyle(R.style.AnimBottomPopup4);
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

    @Override
    public void onClick(View v) {
        if (mmenu == null) return;
//        switch (v.getId()) {
//            case R.id.tv_reset://重置
//                break;
//            case R.id.tv_affirm://确认
//                mmenu.onItemClick(typeAdapter.getpick());
//                break;
//        }
        if(v.getId()==R.id.tv_affirm){
            //确认
            mmenu.onItemClick(typeAdapter.getpick());
        }

        dismiss();
    }

    /**
     * 类型
     */
    private class TypeAdapter extends BaseAdapter {
        private List<IntegralTypeInfo> data;

        public TypeAdapter(List<IntegralTypeInfo> data) {
            this.data = data;
        }

        private void setpick(int id) {
            for (int i = 0; i < data.size(); i++) {
                data.get(i).ispick = false;
            }
            data.get(id).ispick = true;
            notifyDataSetChanged();
        }

        public IntegralTypeInfo getpick(){
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).ispick)return data.get(i);
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
                convertView = View.inflate(mActivity, R.layout.item_integraldetailtype, null);
                new ViewHodler(convertView);
            }
            ViewHodler hodler = (ViewHodler) convertView.getTag();
            IntegralTypeInfo integralTypeInfo = data.get(position);
            if (integralTypeInfo.ispick) {
                hodler.tv_type.setBackgroundResource(R.drawable.integraldetail_btn2);
                hodler.tv_type.setTextColor(Color.WHITE);
            } else {
                hodler.tv_type.setBackgroundResource(R.drawable.integraldetail_btn);
                hodler.tv_type.setTextColor(Color.BLACK);
            }
            hodler.tv_type.setText(integralTypeInfo.txt);
            hodler.tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setpick(position);
                }
            });
            return convertView;
        }

        class ViewHodler {
            TextView tv_type;

            public ViewHodler(View convertView) {
                tv_type = (TextView) convertView.findViewById(R.id.tv_type);
                convertView.setTag(this);
            }
        }
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(IntegralTypeInfo integralTypeInfo);
    }

}
