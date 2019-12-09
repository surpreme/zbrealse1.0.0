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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.model.MonthInfo;
import com.aite.a.model.YearInfo;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 积分详情
 */
public class IntegralDetailPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private ListView lv_level1, lv_level2;
    private Level1Adapter level1Adapter;
    private Level2Adapter level2Adapter;
    private String year="2010";//年
    private int year2, month;

    public IntegralDetailPopu(Activity activity) {
        mActivity = activity;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(ClutterUtils.getScreenHeight(mActivity)/4);
        View view = View.inflate(mActivity, R.layout.popupwindow_integraldetail, null);
        lv_level1 = view.findViewById(R.id.lv_level1);
        lv_level2 = view.findViewById(R.id.lv_level2);

        Calendar calendar = Calendar.getInstance();  //获取当前时间，作为图标的名字
        year2 = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        List<YearInfo> data = new ArrayList<>();
        List<MonthInfo> data2 = new ArrayList<>();
        for (int i = 2010; i <= year2; i++) {
            data.add(new YearInfo(i + "", false));
        }
        for (int i = 1; i < 13; i++) {
            data2.add(new MonthInfo(i + "", false));
        }
        data.get(0).ispick = true;
        level1Adapter = new Level1Adapter(data);
        lv_level1.setAdapter(level1Adapter);
        level2Adapter = new Level2Adapter(data2);
        lv_level2.setAdapter(level2Adapter);
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
        switch (v.getId()) {//发起群聊
        }
        dismiss();
    }


    /**
     * 年份
     */
    private class Level1Adapter extends BaseAdapter {
        private List<YearInfo> data;

        public Level1Adapter(List<YearInfo> data) {
            this.data = data;
        }

        private void setpick(int id) {
            for (int i = 0; i < data.size(); i++) {
                data.get(i).ispick = false;
            }
            data.get(id).ispick = true;
            notifyDataSetChanged();
            level2Adapter.setpick(0);
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
                convertView = View.inflate(mActivity, R.layout.item_address, null);
                new ViewHodler(convertView);
            }
            ViewHodler hodler = (ViewHodler) convertView.getTag();
            if (data.get(position).ispick) {
                hodler.tv_address.setBackgroundColor(Color.WHITE);
            } else {
                hodler.tv_address.setBackgroundColor(0xfff1f1f1);
            }
            hodler.tv_address.setText(data.get(position).year + "年");
            hodler.tv_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    year = data.get(position).year;
                    setpick(position);
                }
            });
            return convertView;
        }

        class ViewHodler {
            TextView tv_address;

            public ViewHodler(View convertView) {
                tv_address = (TextView) convertView.findViewById(R.id.tv_address);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 月份
     */
    private class Level2Adapter extends BaseAdapter {
        private List<MonthInfo> data;

        public Level2Adapter(List<MonthInfo> data) {
            this.data = data;
        }

        public void setpick(int id) {
            for (int i = 0; i < data.size(); i++) {
                data.get(i).ispick = false;
            }
            data.get(id).ispick = true;
            notifyDataSetChanged();
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
                convertView = View.inflate(mActivity, R.layout.item_month, null);
                new ViewHodler(convertView);
            }
            final ViewHodler hodler = (ViewHodler) convertView.getTag();
            final MonthInfo monthInfo = data.get(position);
            if (year2 == Integer.parseInt(year) && Integer.parseInt(monthInfo.month) > month) {
                hodler.rl_item.setBackgroundColor(0xfff1f1f1);
            } else {
                hodler.rl_item.setBackgroundColor(Color.WHITE);
            }
            hodler.tv_month.setText(monthInfo.month + "月");
            if (monthInfo.ispick) {
                hodler.iv_pick.setVisibility(View.VISIBLE);
            } else {
                hodler.iv_pick.setVisibility(View.GONE);
            }
            hodler.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (year2 == Integer.parseInt(year) && Integer.parseInt(monthInfo.month) > month)
                        return;
                    if (mmenu != null) {
                        mmenu.onItemClick(year, monthInfo.month);
                    }
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHodler {
            TextView tv_month;
            ImageView iv_pick;
            RelativeLayout rl_item;

            public ViewHodler(View convertView) {
                tv_month = convertView.findViewById(R.id.tv_month);
                iv_pick = convertView.findViewById(R.id.iv_pick);
                rl_item = convertView.findViewById(R.id.rl_item);
                convertView.setTag(this);
            }
        }
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(String tear, String month);
    }

}
