package livestream.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;

import livestream.mode.LiveRoomDatailsInfo;

/**
 * 用户资料
 */
public class UserDataPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private CircleImageView iv_icon;
    private ImageView iv_more, iv_sex;
    private LabelsView labels;
    private TextView tv_name, tv_level, tv_zbnumber, tv_address, tv_motto, tv_attentionnum, tv_fans,
            tv_gift1, tv_gift2, tv_attention, tv_eit, tv_privateletter;
    private LiveRoomDatailsInfo.live_member_info data;

    public UserDataPopu(Activity activity, LiveRoomDatailsInfo.live_member_info data) {
        mActivity = activity;
        this.data = data;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_zb_userdata, null);
        iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        iv_sex = (ImageView) view.findViewById(R.id.iv_sex);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_level = (TextView) view.findViewById(R.id.tv_level);
        tv_zbnumber = (TextView) view.findViewById(R.id.tv_zbnumber);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_motto = (TextView) view.findViewById(R.id.tv_motto);
        tv_attentionnum = (TextView) view.findViewById(R.id.tv_attentionnum);
        tv_fans = (TextView) view.findViewById(R.id.tv_fans);
        tv_gift1 = (TextView) view.findViewById(R.id.tv_gift1);
        tv_gift2 = (TextView) view.findViewById(R.id.tv_gift2);
        tv_attention = (TextView) view.findViewById(R.id.tv_attention);
        tv_eit = (TextView) view.findViewById(R.id.tv_eit);
        labels = (LabelsView) view.findViewById(R.id.labels);
        tv_privateletter = (TextView) view.findViewById(R.id.tv_privateletter);
        iv_more.setOnClickListener(this);
        tv_attention.setOnClickListener(this);
        tv_eit.setOnClickListener(this);
        tv_privateletter.setOnClickListener(this);
        Glide.with(activity).load(data.member_avatar).into(iv_icon);
        tv_name.setText(data.member_truename);
        tv_level.setText(data.member_level);
        tv_address.setText(data.region_info);
        tv_attentionnum.setText(data.follow_num);
        tv_fans.setText(data.fans_num);
        tv_gift1.setText(data.get_gifts);
        tv_gift2.setText(data.send_gifts);
        tv_zbnumber.setText(activity.getString(R.string.goodsdatails_reminder63) + data.room_number);
        labels.setLabels(data.room_label, new LabelsView.LabelTextProvider<LiveRoomDatailsInfo.live_member_info.room_label>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, LiveRoomDatailsInfo.live_member_info.room_label data) {
                //根据data和position返回label需要显示的数据。
                return data.mtag_name;
            }
        });

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

    public void setAttention() {
        tv_attention.setTextColor(0xffF4639F);
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
        int id = v.getId();
        if (id == R.id.iv_more) {//更多
        } else if (id == R.id.tv_attention) {//加关注
            if (mdate != null) {
                mdate.onItemClick("1");
            }
        } else if (id == R.id.tv_eit) {//@TA  举报
            if (mdate != null) {
                mdate.onItemClick("2");
            }
        } else if (id == R.id.tv_privateletter) {//私信
        }
    }


    private date mdate = null;

    public void setdate(date p) {
        mdate = p;
    }

    public interface date {
        void onItemClick(String type);
    }


}
