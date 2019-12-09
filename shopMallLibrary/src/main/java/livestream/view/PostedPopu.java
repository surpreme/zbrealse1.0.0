package livestream.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.aite.a.activity.IdentityActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import livestream.activity.PostedActivity;
import livestream.activity.PrepareActivity;

import static com.aite.a.base.Mark.is_ertification_err;
import static com.aite.a.base.Mark.is_ertification_id;

public class PostedPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private LinearLayout ll_livestream, ll_posted, ll_con;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case is_ertification_id://是否通过审核
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")){
                            Intent intent1 = new Intent(mActivity, PrepareActivity.class);
                            mActivity.startActivity(intent1);
                        }else{
                            Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
                            Intent intent14 = new Intent(mActivity, IdentityActivity.class);
                            intent14.putExtra("is_live","1");
                            mActivity.startActivity(intent14);
                        }
                    }
                    break;
                case is_ertification_err:
                    Toast.makeText(mActivity, mActivity.getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public PostedPopu(Activity activity) {
        mActivity = activity;
        netRun = new NetRun(activity, handler);
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_zbpsted, null);
        ll_livestream = (LinearLayout) view.findViewById(R.id.ll_livestream);
        ll_posted = (LinearLayout) view.findViewById(R.id.ll_posted);
        ll_con = (LinearLayout) view.findViewById(R.id.ll_con);
        ll_livestream.setOnClickListener(this);
        ll_posted.setOnClickListener(this);
        ll_con.setOnClickListener(this);
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
        dismiss();
        int id = v.getId();
        if (id == R.id.ll_livestream) {//直播
            netRun.isCertification();
        } else if (id == R.id.ll_posted) {//发布
            Intent intent = new Intent(mActivity, PostedActivity.class);
            mActivity.startActivity(intent);
        }
    }
}
