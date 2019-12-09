package com.community.utils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.CreateGroupbgInfo;

import java.util.List;


/**
 * 选择群组图片
 */
public class CreateGroupbgPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private SmsAdapter smsAdapter;
    private List<CreateGroupbgInfo> data;
    private GridView gv_bg;

    public CreateGroupbgPopu(Activity activity, List<CreateGroupbgInfo> data) {
        mActivity = activity;
        this.data = data;
        WindowManager wm = mActivity.getWindowManager();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_groupbg, null);
        gv_bg = (GridView) view.findViewById(R.id.gv_bg);
        smsAdapter = new SmsAdapter(data);
        gv_bg.setAdapter(smsAdapter);
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
        switch (v.getId()) {
        }
        dismiss();
    }

    /**
     * 短信适配
     */
    private class SmsAdapter extends BaseAdapter {
        private List<CreateGroupbgInfo> data;

        public SmsAdapter(List<CreateGroupbgInfo> data) {
            this.data = data;
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
                convertView = View.inflate(mActivity, R.layout.item_groupbg, null);
                new ViewHodler(convertView);
            }
            ViewHodler hodler = (ViewHodler) convertView.getTag();
            Glide.with(mActivity).load(data.get(position).adv_pic).into(hodler.iv_img);
            hodler.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mmenu != null) {
                        mmenu.onItemClick(data.get(position));
                    }
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHodler {
            ImageView iv_img;

            public ViewHodler(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                convertView.setTag(this);
            }
        }
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(CreateGroupbgInfo data);
    }

}
