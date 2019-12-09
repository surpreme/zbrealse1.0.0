package courier.View;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import courier.model.MothChooseInfo;
import level3.adapter.ArrayWheelAdapter;
import level3.widget.WheelView;


/**
 * 月份选择
 */
public class MonthChoosePopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private RelativeLayout rl_c;
    private WheelView wv_1, wv_2;

    public MonthChoosePopu(Activity activity) {
        mActivity = activity;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_moch2, null);
        rl_c = (RelativeLayout) view.findViewById(R.id.rl_c);
        wv_1 = (WheelView) view.findViewById(R.id.wv_1);
        wv_2 = (WheelView) view.findViewById(R.id.wv_2);
        rl_c.setOnClickListener(this);
        initTime();
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

    /**
     * 初始化时间
     */
    private void initTime() {
        final List<MothChooseInfo> monthlist = new ArrayList<>();
        List<String> monthlist2 = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int y = 2017;
        for (int i = 2017; i < year+1; i++) {
            List<String> yearlist=new ArrayList<>();
            if (y<year){//12月
                for (int j = 1; j < 13; j++) {
                    if (j<10){
                        yearlist.add("0"+j);
                    }else{
                        yearlist.add(""+j);
                    }
                }
            }else{//当前月
                for (int j = 1; j < month+1; j++) {
                    if (j<10){
                        yearlist.add("0"+j);
                    }else{
                        yearlist.add(""+j);
                    }
                }
            }
            monthlist.add(new MothChooseInfo(y + "",yearlist));
            monthlist2.add(y + "");
            y++;
        }

        wv_1.setWheelAdapter(new ArrayWheelAdapter<String>(mActivity));
        wv_1.setSkin(WheelView.Skin.Holo);
        wv_1.setWheelData(monthlist2);
        wv_2.setWheelAdapter(new ArrayWheelAdapter<String>(mActivity));
        wv_2.setSkin(WheelView.Skin.Holo);
        wv_2.setWheelData(monthlist.get(0).month);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 15;
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textSize = 12;
        wv_1.setStyle(style);
        wv_2.setStyle(style);
        wv_1.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                yearstr=s;
                List<String> month1 = monthlist.get(position).month;
                if (month1.size()==0){
                    month1.add("");
                }
                if (month1 != null && month1.size() != 0) {
                    wv_2.smoothScrollToPosition(0);
                    wv_2.setWheelData(month1);
                }
                if (mmenu != null&&yearstr!=null&&monthstr!=null){
                    mmenu.onItemClick(yearstr,monthstr);
                }
            }
        });
        wv_2.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                monthstr=s;
                if (mmenu != null&&yearstr!=null&&monthstr!=null){
                    mmenu.onItemClick(yearstr,monthstr);
                }
            }
        });
    }

    public String yearstr,monthstr;

    @Override
    public void onClick(View v) {
        if (mmenu == null) return;
        switch (v.getId()) {//发起群聊
        }
        dismiss();
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(String year,String month);
    }

}
