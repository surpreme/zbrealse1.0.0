package com.aite.a.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.model.GoodsDetailsInfo;
import com.aiteshangcheng.a.R;

import java.util.List;


/**
 * 客服
 */
public class ServicePopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private List<GoodsDetailsInfo.store_callcenter> data;
    private ListView lv_list;
    private ImageView iv_close;
    private callcenterAdapter callcenteradapter;

    public ServicePopu(Activity activity, List<GoodsDetailsInfo.store_callcenter> data) {
        mActivity = activity;
        this.data = data;
//        WindowManager wm = mActivity.getWindowManager();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_callcenter, null);
        lv_list = view.findViewById(R.id.lv_list);
        iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        callcenteradapter=new callcenterAdapter(data);
        lv_list.setAdapter(callcenteradapter);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00ffffff);
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
        setAnimationStyle(R.style.AnimBottomPopup);
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

    class callcenterAdapter extends BaseAdapter {
        private List<GoodsDetailsInfo.store_callcenter> callcenter;

        public callcenterAdapter(List<GoodsDetailsInfo.store_callcenter> callcenter) {
            this.callcenter = callcenter;
        }

        @Override
        public int getCount() {
            return callcenter == null ? 0 : callcenter.size();
        }

        @Override
        public Object getItem(int i) {
            return callcenter == null ? null : callcenter.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mActivity, R.layout.item_callcenter, null);
                new callcenterHolder(view);
            }
            callcenterHolder holder = (callcenterHolder) view.getTag();
            GoodsDetailsInfo.store_callcenter store_callcenter = callcenter.get(i);
            holder.tv_title.setText(store_callcenter.type_name);
            callcenter2Adapter adapter=new callcenter2Adapter(store_callcenter.callcenter_list);
            holder.gv_menu.setAdapter(adapter);
            return view;
        }

        class callcenterHolder {
            TextView tv_title;
            MyGridView gv_menu;

            public callcenterHolder(View view) {
                tv_title = view.findViewById(R.id.tv_title);
                gv_menu = view.findViewById(R.id.gv_menu);
                view.setTag(this);
            }
        }
    }

    class callcenter2Adapter extends BaseAdapter {
        private List<GoodsDetailsInfo.store_callcenter.callcenter_list> callcenter_list;

        public callcenter2Adapter(List<GoodsDetailsInfo.store_callcenter.callcenter_list> callcenter_list) {
            this.callcenter_list = callcenter_list;
        }

        @Override
        public int getCount() {
            return callcenter_list == null ? 0 : callcenter_list.size();
        }

        @Override
        public Object getItem(int i) {
            return callcenter_list == null ? null : callcenter_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mActivity, R.layout.item_callcenter2, null);
                new callcenter2Holder(view);
            }
            callcenter2Holder holder = (callcenter2Holder) view.getTag();
            final GoodsDetailsInfo.store_callcenter.callcenter_list callcenter_list = this.callcenter_list.get(i);
            holder.tv_name.setText(callcenter_list.name);
            if (callcenter_list.type.equals("qq")){
                holder.iv_icon.setImageResource(R.drawable.qqfcf);
            }else if (callcenter_list.type.equals("im")){
                holder.iv_icon.setImageResource(R.drawable.jd_message2);
            }else if (callcenter_list.type.equals("ww")){
                holder.iv_icon.setImageResource(R.drawable.wwfcf);
            }
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (mmenu!=null){
                        mmenu.onItemClick(callcenter_list);
                    }
                }
            });
            return view;
        }

        class callcenter2Holder {
            TextView tv_name;
            ImageView iv_icon;
            LinearLayout ll_item;

            public callcenter2Holder(View view) {
                tv_name = view.findViewById(R.id.tv_name);
                iv_icon = view.findViewById(R.id.iv_icon);
                ll_item = view.findViewById(R.id.ll_item);
                view.setTag(this);
            }
        }
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(GoodsDetailsInfo.store_callcenter.callcenter_list callcenter_list);
    }

}
