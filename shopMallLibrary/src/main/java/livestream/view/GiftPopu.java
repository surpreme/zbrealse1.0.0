package livestream.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import livestream.adapter.GiftListAdapter;
import livestream.mode.GiftListInfo;

/**
 * 礼物
 */
public class GiftPopu extends PopupWindow implements View.OnClickListener, Mark {
    private Activity mActivity = null;
    private GiftListInfo giftListInfo;
    private TextView tv_send;
    private GridView gv_gift;
    private GiftListAdapter adapter;
    private String room_id;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case gift_list_id://礼物列表
                    if (msg.obj != null) {
                        giftListInfo = (GiftListInfo) msg.obj;
                        adapter = new GiftListAdapter(mActivity, giftListInfo.datas.gift_list);
                        gv_gift.setAdapter(adapter);
                    }
                    break;
                case gift_list_err:
                    Toast.makeText(mActivity, mActivity.getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case giving_gift_id://赠送礼物
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            Toast.makeText(mActivity, mActivity.getString(R.string.goodsdatails_reminder65), Toast.LENGTH_SHORT).show();
                            if (mdate != null&&adapter!=null) {
                                GiftListInfo.datas.gift_list pickGift = adapter.getPickGift();
                                mdate.onItemClick(pickGift.gift_image_gif,pickGift.gift_name,"1");
                                dismiss();
                            }
                        } else {
                            Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case giving_gift_err:
                    Toast.makeText(mActivity, mActivity.getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public GiftPopu(Activity activity, String room_id) {
        mActivity = activity;
        this.room_id = room_id;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_zb_gift, null);
        gv_gift = view.findViewById(R.id.gv_gift);
        tv_send = view.findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);
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
        netRun = new NetRun(mActivity, handler);
        netRun.GiftList();
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup3);
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
        if (v.getId() == R.id.tv_send) {//发送
            if (adapter == null) return;
            String pickID = adapter.getPickID();
            if (pickID == null) return;
            netRun.GivingGift(pickID, room_id);
        }
    }

    private date mdate = null;

    public void setdate(date p) {
        mdate = p;
    }

    public interface date {
        void onItemClick(String gif,String giftname,String number);
    }

}
