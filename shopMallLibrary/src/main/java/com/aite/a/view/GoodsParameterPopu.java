package com.aite.a.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.adapter.GoodsParameterAdapter;
import com.aite.a.model.GoodsDetailsInfo;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;

import java.util.List;


/**
 * 商品参数
 */
public class GoodsParameterPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private TextView tv_close;
    private ListView lv_parameter;
    private List<GoodsDetailsInfo.GoodsInfo.goods_param_info> goods_param_info;
    private GoodsParameterAdapter goodsParameterAdapter;

    public GoodsParameterPopu(Activity activity, List<GoodsDetailsInfo.GoodsInfo.goods_param_info> goods_param_info) {
        mActivity = activity;
        this.goods_param_info = goods_param_info;

        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight((int) (ClutterUtils.getScreenHeight(mActivity) * 0.7));
        View view = View.inflate(mActivity, R.layout.popupwindow_goodsparameter, null);
        tv_close = view.findViewById(R.id.tv_close);
        lv_parameter = view.findViewById(R.id.lv_parameter);
        tv_close.setOnClickListener(this);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        goodsParameterAdapter = new GoodsParameterAdapter(mActivity, goods_param_info);
        lv_parameter.setAdapter(goodsParameterAdapter);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup);

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }

    Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    WindowManager.LayoutParams lp = mActivity.getWindow()
                            .getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }
    };

    private void showEvent() {
//        h.sendEmptyMessage(0);
        h.sendEmptyMessageDelayed(0, 300);
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
//        switch (v.getId()) {
//            case R.id.tv_close:
//                dismiss();
//                break;
//        }
        if(v.getId()==R.id.tv_close){
            dismiss();
        }
    }

}
