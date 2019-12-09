package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.MyRefereeInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 我的推荐人
 *
 * @author Administrator
 */
public class MyRefereeActivity extends BaseActivity implements OnClickListener {

    private ImageView iv_bcreturn, iv_sex1, iv_sex2, iv_sex3;
    private TextView tv_bctitle, tv_name, tv_1name, tv_2name, tv_3name,
            tv_isdocus1, tv_isdocus2, tv_isdocus3, tv_email, tv_email2,
            tv_email3, tv_time, tv_time2, tv_time3, tv_no1, tv_no2, tv_no3;
    private CircleImageView cv_icon, cv_level1, cv_level2, cv_level3;
    private RelativeLayout rl_level1;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private MyRefereeInfo myRefereeInfo;
    private LinearLayout ll_level1, ll_level2, ll_level3;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case my_referee_id:
                    if (msg.obj != null) {
                        myRefereeInfo = (MyRefereeInfo) msg.obj;

                        bitmapUtils.display(cv_icon,
                                myRefereeInfo.member_info.avator);
                        tv_name.setText(myRefereeInfo.member_info.member_name);
                        // 一级
                        tv_no1.setText(myRefereeInfo.reseller_recommend_list.first_reseller_info.message);
                        if (myRefereeInfo.reseller_recommend_list.first_reseller_info.media_id != null) {
                            rl_level1.setVisibility(View.VISIBLE);

                            bitmapUtils
                                    .display(
                                            cv_level1,
                                            myRefereeInfo.reseller_recommend_list.first_reseller_info.avator);
                            if (myRefereeInfo.reseller_recommend_list.first_reseller_info.member_name == null
                                    || myRefereeInfo.reseller_recommend_list.first_reseller_info.member_name
                                    .equals("")) {
                                tv_1name.setText(myRefereeInfo.reseller_recommend_list.first_reseller_info.member_truename);
                            } else {
                                tv_1name.setText(myRefereeInfo.reseller_recommend_list.first_reseller_info.member_name);
                            }
                            tv_isdocus1
                                    .setText(myRefereeInfo.reseller_recommend_list.first_reseller_info.is_subscribe
                                            .equals("0") ? getString(R.string.distribution_center13) : getString(R.string.distribution_center14));
                            tv_email.setText(getString(R.string.distribution_center15)
                                    + " "
                                    + myRefereeInfo.reseller_recommend_list.first_reseller_info.member_email);
                            tv_time.setText(getString(R.string.distribution_center16)
                                    + " "
                                    + TimeStamp2Date(
                                    myRefereeInfo.reseller_recommend_list.first_reseller_info.member_login_time,
                                    "yyyy-MM-dd HH:mm"));
                            if (myRefereeInfo.reseller_recommend_list.first_reseller_info.member_sex
                                    .equals("1")) {
                                iv_sex1.setImageResource(R.drawable.member_sex1);
                            } else if (myRefereeInfo.reseller_recommend_list.first_reseller_info.member_sex
                                    .equals("2")) {
                                iv_sex1.setImageResource(R.drawable.member_sex2);
                            } else if (myRefereeInfo.reseller_recommend_list.first_reseller_info.member_sex
                                    .equals("3")) {
                                iv_sex1.setImageResource(R.drawable.member_sex3);
                            }
                        } else {
                            rl_level1.setVisibility(View.GONE);
                        }
                        // 二级
                        if (myRefereeInfo.reseller_recommend_list.second_reseller_info != null) {
                            ll_level2.setVisibility(View.VISIBLE);
                            tv_no2.setText(myRefereeInfo.reseller_recommend_list.second_reseller_info.message);
                            bitmapUtils
                                    .display(
                                            cv_level2,
                                            myRefereeInfo.reseller_recommend_list.second_reseller_info.avator);
                            if (myRefereeInfo.reseller_recommend_list.second_reseller_info.member_name == null
                                    || myRefereeInfo.reseller_recommend_list.second_reseller_info.member_name
                                    .equals("")) {
                                tv_2name.setText(myRefereeInfo.reseller_recommend_list.second_reseller_info.member_truename);
                            } else {
                                tv_2name.setText(myRefereeInfo.reseller_recommend_list.second_reseller_info.member_name);
                            }

                            tv_isdocus2
                                    .setText(myRefereeInfo.reseller_recommend_list.second_reseller_info.is_subscribe
                                            .equals("0") ? getString(R.string.distribution_center13) : getString(R.string.distribution_center14));
                            tv_email2
                                    .setText(getString(R.string.distribution_center15)
                                            + " "
                                            + myRefereeInfo.reseller_recommend_list.second_reseller_info.member_email);
                            tv_time2.setText(getString(R.string.distribution_center16)
                                    + " "
                                    + TimeStamp2Date(
                                    myRefereeInfo.reseller_recommend_list.second_reseller_info.member_login_time,
                                    "yyyy-MM-dd HH:mm"));
                            if (myRefereeInfo.reseller_recommend_list.second_reseller_info.member_sex
                                    .equals("1")) {
                                iv_sex2.setImageResource(R.drawable.member_sex1);
                            } else if (myRefereeInfo.reseller_recommend_list.second_reseller_info.member_sex
                                    .equals("2")) {
                                iv_sex2.setImageResource(R.drawable.member_sex2);
                            } else if (myRefereeInfo.reseller_recommend_list.second_reseller_info.member_sex
                                    .equals("3")) {
                                iv_sex2.setImageResource(R.drawable.member_sex3);
                            }
                        } else {
                            ll_level2.setVisibility(View.GONE);
                        }
                        // 三级
                        if (myRefereeInfo.reseller_recommend_list.third_reseller_info != null) {
                            ll_level3.setVisibility(View.VISIBLE);
                            tv_no3.setText(myRefereeInfo.reseller_recommend_list.third_reseller_info.message);
                            bitmapUtils
                                    .display(
                                            cv_level3,
                                            myRefereeInfo.reseller_recommend_list.third_reseller_info.avator);
                            if (myRefereeInfo.reseller_recommend_list.third_reseller_info.member_name == null
                                    || myRefereeInfo.reseller_recommend_list.third_reseller_info.member_name
                                    .equals("")) {
                                tv_3name.setText(myRefereeInfo.reseller_recommend_list.third_reseller_info.member_truename);
                            } else {
                                tv_3name.setText(myRefereeInfo.reseller_recommend_list.third_reseller_info.member_name);
                            }

                            tv_isdocus3
                                    .setText(myRefereeInfo.reseller_recommend_list.third_reseller_info.is_subscribe
                                            .equals("0") ? getString(R.string.distribution_center13) : getString(R.string.distribution_center14));
                            tv_email3
                                    .setText(getString(R.string.distribution_center15)
                                            + " "
                                            + myRefereeInfo.reseller_recommend_list.third_reseller_info.member_email);
                            tv_time3.setText(getString(R.string.distribution_center16)
                                    + " "
                                    + TimeStamp2Date(
                                    myRefereeInfo.reseller_recommend_list.third_reseller_info.member_login_time,
                                    "yyyy-MM-dd HH:mm"));
                            if (myRefereeInfo.reseller_recommend_list.third_reseller_info.member_sex
                                    .equals("1")) {
                                iv_sex3.setImageResource(R.drawable.member_sex1);
                            } else if (myRefereeInfo.reseller_recommend_list.third_reseller_info.member_sex
                                    .equals("2")) {
                                iv_sex3.setImageResource(R.drawable.member_sex2);
                            } else if (myRefereeInfo.reseller_recommend_list.third_reseller_info.member_sex
                                    .equals("3")) {
                                iv_sex3.setImageResource(R.drawable.member_sex3);
                            }
                        } else {
                            ll_level3.setVisibility(View.GONE);
                        }

                    }
                    break;
                case my_referee_err:
                    Toast.makeText(MyRefereeActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreferees);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
        iv_sex1 = (ImageView) findViewById(R.id.iv_sex1);
        iv_sex2 = (ImageView) findViewById(R.id.iv_sex2);
        iv_sex3 = (ImageView) findViewById(R.id.iv_sex3);
        tv_bctitle = (TextView) findViewById(R.id._tv_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_no1 = (TextView) findViewById(R.id.tv_no1);
        tv_no2 = (TextView) findViewById(R.id.tv_no2);
        tv_no3 = (TextView) findViewById(R.id.tv_no3);
        tv_1name = (TextView) findViewById(R.id.tv_1name);
        tv_2name = (TextView) findViewById(R.id.tv_2name);
        tv_3name = (TextView) findViewById(R.id.tv_3name);
        tv_isdocus1 = (TextView) findViewById(R.id.tv_isdocus1);
        tv_isdocus2 = (TextView) findViewById(R.id.tv_isdocus2);
        tv_isdocus3 = (TextView) findViewById(R.id.tv_isdocus3);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_email2 = (TextView) findViewById(R.id.tv_email2);
        tv_email3 = (TextView) findViewById(R.id.tv_email3);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);
        cv_icon = (CircleImageView) findViewById(R.id.cv_icon);
        cv_level1 = (CircleImageView) findViewById(R.id.cv_level1);
        cv_level2 = (CircleImageView) findViewById(R.id.cv_level2);
        cv_level3 = (CircleImageView) findViewById(R.id.cv_level3);
        rl_level1 = (RelativeLayout) findViewById(R.id.rl_level1);
        ll_level1 = (LinearLayout) findViewById(R.id.ll_level1);
        ll_level2 = (LinearLayout) findViewById(R.id.ll_level2);
        ll_level3 = (LinearLayout) findViewById(R.id.ll_level3);

        tv_bctitle.setText(R.string.distribution_center17);
        initView();
    }

    @Override
    protected void initView() {
        iv_bcreturn.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.MyReferee();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//        }
        if (v.getId() == R.id._iv_back) {
            finish();
        }
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString 时间戳
     * @param formats         格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }
}
