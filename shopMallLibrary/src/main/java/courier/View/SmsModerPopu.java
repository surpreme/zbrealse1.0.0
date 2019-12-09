package courier.View;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.SmsModerInfo;


/**
 * 短信模板
 */
public class SmsModerPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private ListView lv_sms;
    private RelativeLayout rl_c;
    private SmsAdapter smsAdapter;
    private List<SmsModerInfo> smsModerInfo;

    public SmsModerPopu(Activity activity, List<SmsModerInfo> smsModerInfo) {
        mActivity = activity;
        this.smsModerInfo = smsModerInfo;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_smsmoder, null);
        lv_sms = (ListView) view.findViewById(R.id.lv_sms);
        rl_c = (RelativeLayout) view.findViewById(R.id.rl_c);
        rl_c.setOnClickListener(this);
        smsAdapter = new SmsAdapter(smsModerInfo);
        lv_sms.setAdapter(smsAdapter);
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

    @Override
    public void onClick(View v) {
        if (mmenu == null) return;
        switch (v.getId()) {//发起群聊
        }
        dismiss();
    }

    /**
     * 短信适配
     */
    private class SmsAdapter extends BaseAdapter {
        private List<SmsModerInfo> smsModerInfo;

        public SmsAdapter(List<SmsModerInfo> smsModerInfo) {
            this.smsModerInfo = smsModerInfo;
        }

        @Override
        public int getCount() {
            return smsModerInfo == null ? 0 : smsModerInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return smsModerInfo == null ? null : smsModerInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=View.inflate(mActivity,R.layout.item_sms,null);
                new ViewHodler(convertView);
            }
            ViewHodler hodler = (ViewHodler) convertView.getTag();
            hodler.tv_name.setText(smsModerInfo.get(position).mmt_name);
            hodler.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mmenu != null){
                        mmenu.onItemClick(position);
                    }
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHodler {
            TextView tv_name;

            public ViewHodler(View convertView) {
                tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(this);
            }
        }
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(int id);
    }

}
