package com.aite.a.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;




import java.util.ArrayList;
import java.util.List;

import com.aiteshangcheng.a.R;

/**
 * 数字键盘
 */
public class MyKeyboard extends PopupWindow implements OnClickListener {
    private Activity mActivity = null;
    private EditText et_number;
    private TextView tv_ok, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_0;
    private RelativeLayout rl_keypad_del;
    private List<String> num;

    public MyKeyboard(Activity activity) {
        mActivity = activity;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(height / 2);
        View view = View.inflate(mActivity, R.layout.keypad_popu, null);
        et_number = (EditText) view.findViewById(R.id.et_number);
        tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        tv_1 = (TextView) view.findViewById(R.id.tv_1);
        tv_2 = (TextView) view.findViewById(R.id.tv_2);
        tv_3 = (TextView) view.findViewById(R.id.tv_3);
        tv_4 = (TextView) view.findViewById(R.id.tv_4);
        tv_5 = (TextView) view.findViewById(R.id.tv_5);
        tv_6 = (TextView) view.findViewById(R.id.tv_6);
        tv_7 = (TextView) view.findViewById(R.id.tv_7);
        tv_8 = (TextView) view.findViewById(R.id.tv_8);
        tv_9 = (TextView) view.findViewById(R.id.tv_9);
        tv_0 = (TextView) view.findViewById(R.id.tv_0);
        rl_keypad_del = (RelativeLayout) view.findViewById(R.id.rl_keypad_del);
        tv_ok.setOnClickListener(this);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_7.setOnClickListener(this);
        tv_8.setOnClickListener(this);
        tv_9.setOnClickListener(this);
        tv_0.setOnClickListener(this);
        rl_keypad_del.setOnClickListener(this);
//        et_number.setClickable(false);
        et_number.setInputType(InputType.TYPE_NULL);
        num = new ArrayList<>();
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
                WindowManager.LayoutParams lp = mActivity
                        .getWindow().getAttributes();
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
                    WindowManager.LayoutParams lp = mActivity
                            .getWindow().getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }

        ;
    };

    private void showEvent() {
        h.sendEmptyMessageDelayed(0, 500);
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
//		dismiss();
        if (mhuidiao != null)
            if(v.getId()==R.id.tv_ok){
                String str = et_number.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    mhuidiao.onItemClick(str);
                    dismiss();
                }
            }else if(v.getId()==R.id.tv_1){
                num.add("1");
                setnum();
            }else if(v.getId()==R.id.tv_2){
                num.add("2");
                setnum();
            }else if(v.getId()==R.id.tv_3){
                num.add("3");
                setnum();
            }else if(v.getId()==R.id.tv_4){
                num.add("4");
                setnum();
            }else if(v.getId()==R.id.tv_5){
                num.add("5");
                setnum();
            }else if(v.getId()== R.id.tv_6){
                num.add("6");
                setnum();
            }else if(v.getId()==R.id.tv_7){
                num.add("7");
                setnum();
            }else if(v.getId()==R.id.tv_8){
                num.add("8");
                setnum();
            }else if(v.getId()==R.id.tv_9){
                num.add("9");
                setnum();
            }else if(v.getId()==R.id.tv_0){
                num.add("0");
                setnum();
            }else if(v.getId()==R.id.rl_keypad_del){
                if (num.size() != 0) {
                    num.remove(num.size() - 1);
                    setnum();
                }
            }




//            switch (v.getId()) {
//                case R.id.tv_ok:
//                    String str = et_number.getText().toString();
//                    if (!TextUtils.isEmpty(str)) {
//                        mhuidiao.onItemClick(str);
//                        dismiss();
//                    }
//                    break;
//                case R.id.tv_1:
//                    num.add("1");
//                    setnum();
//                    break;
//                case R.id.tv_2:
//                    num.add("2");
//                    setnum();
//                    break;
//                case R.id.tv_3:
//                    num.add("3");
//                    setnum();
//                    break;
//                case R.id.tv_4:
//                    num.add("4");
//                    setnum();
//                    break;
//                case R.id.tv_5:
//                    num.add("5");
//                    setnum();
//                    break;
//                case R.id.tv_6:
//                    num.add("6");
//                    setnum();
//                    break;
//                case R.id.tv_7:
//                    num.add("7");
//                    setnum();
//                    break;
//                case R.id.tv_8:
//                    num.add("8");
//                    setnum();
//                    break;
//                case R.id.tv_9:
//                    num.add("9");
//                    setnum();
//                    break;
//                case R.id.tv_0:
//                    num.add("0");
//                    setnum();
//                    break;
//                case R.id.rl_keypad_del:
//                    if (num.size() != 0) {
//                        num.remove(num.size() - 1);
//                        setnum();
//                    }
//                    break;
//            }
    }

    private void setnum() {
        et_number.setText("");
        for (int i = 0; i < num.size(); i++) {
            et_number.append(num.get(i));
        }
    }

    private huidiao mhuidiao = null;

    public void sethuidiao(huidiao p) {
        mhuidiao = p;
    }

    public interface huidiao {
        void onItemClick(String number);
    }
}
