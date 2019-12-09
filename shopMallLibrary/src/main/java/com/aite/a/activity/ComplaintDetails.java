package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.ComplaintDialogueAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.ComplaintDetailsInfo;
import com.aite.a.model.ComplaintDialogueInfo;
import com.aite.a.model.ComplaintslistInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aite.a.view.MyShiGuangZhou;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.utils.ClutterUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 投诉详情
 *
 * @author Administrator
 */
public class ComplaintDetails extends BaseActivity implements OnClickListener {
    private TextView _tv_name, tv_progress1, tv_progress2, tv_progress3,
            tv_progress4, tv_progress5, tv_storename, tv_theme, tv_time,
            tv_content, tv_return, tv_theme2, tv_content2, tv_dialog1, tv_dialog2, tv_dialog3;
    private ImageView _iv_back, iv_img1, iv_img2, iv_img3, iv_ssimg1, iv_ssimg2, iv_ssimg3;
    private MyListView lv_dialog;
    private EditText et_dialog;
    private MyShiGuangZhou msgz_sgz;
    private LinearLayout ll_appeal, ll_pendingappeal;
    private ComplaintslistInfo.datas.list list;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private ComplaintDetailsInfo complaintDetailsInfo;
    private List<ComplaintDialogueInfo> complaintDialogueInfo;
    private ComplaintDialogueAdapter complaintDialogueAdapter;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case complaint_details_id:
                    if (msg.obj != null) {
                        complaintDetailsInfo = (ComplaintDetailsInfo) msg.obj;
                        String complain_state = complaintDetailsInfo.complain_state;
                        switch (complain_state) {
                            case "10":
                                ll_pendingappeal.setVisibility(View.GONE);
                                ll_appeal.setVisibility(View.GONE);
                                msgz_sgz.setProgress(1);
                                tv_progress1.setTextColor(0xffFF5186);
                                tv_progress2.setTextColor(0xff979797);
                                tv_progress3.setTextColor(0xff979797);
                                tv_progress4.setTextColor(0xff979797);
                                tv_progress5.setTextColor(0xff979797);
                                break;
                            case "20":
                                ll_pendingappeal.setVisibility(View.VISIBLE);
                                ll_appeal.setVisibility(View.GONE);
                                init2();
                                msgz_sgz.setProgress(2);
                                tv_progress1.setTextColor(0xffFF5186);
                                tv_progress2.setTextColor(0xffFF5186);
                                tv_progress3.setTextColor(0xff979797);
                                tv_progress4.setTextColor(0xff979797);
                                tv_progress5.setTextColor(0xff979797);
                                break;
                            case "30":
                                ll_pendingappeal.setVisibility(View.VISIBLE);
                                ll_appeal.setVisibility(View.VISIBLE);
                                init2();
                                netRun.getComplainTalk(complaintDetailsInfo.complain_id);
                                msgz_sgz.setProgress(3);
                                tv_progress1.setTextColor(0xffFF5186);
                                tv_progress2.setTextColor(0xffFF5186);
                                tv_progress3.setTextColor(0xffFF5186);
                                tv_progress4.setTextColor(0xff979797);
                                tv_progress5.setTextColor(0xff979797);
                                break;
                            case "40":
                                init2();
                                netRun.getComplainTalk(complaintDetailsInfo.complain_id);
                                msgz_sgz.setProgress(4);
                                tv_progress1.setTextColor(0xffFF5186);
                                tv_progress2.setTextColor(0xffFF5186);
                                tv_progress3.setTextColor(0xffFF5186);
                                tv_progress4.setTextColor(0xffFF5186);
                                tv_progress5.setTextColor(0xff979797);
                                break;
                            case "99":
                                init2();
                                netRun.getComplainTalk(complaintDetailsInfo.complain_id);
                                msgz_sgz.setProgress(5);
                                tv_progress1.setTextColor(0xffFF5186);
                                tv_progress2.setTextColor(0xffFF5186);
                                tv_progress3.setTextColor(0xffFF5186);
                                tv_progress4.setTextColor(0xffFF5186);
                                tv_progress5.setTextColor(0xffFF5186);
                                break;
                        }
                    }
                    tv_storename.setText(complaintDetailsInfo.accused_name);
                    tv_theme.setText(complaintDetailsInfo.complain_subject_content);
                    tv_time.setText(TimeStamp2Date(complaintDetailsInfo.complain_datetime, "yyyy-MM-dd HH:mm"));
                    tv_content.setText(complaintDetailsInfo.complain_content);
                    if (complaintDetailsInfo.complain_pic1 != null && !complaintDetailsInfo.complain_pic1.equals("")) {
                        iv_img1.setVisibility(View.VISIBLE);
                        bitmapUtils.display(iv_img1, complaintDetailsInfo.complain_pic1);
                    } else {
                        iv_img1.setVisibility(View.GONE);
                    }
                    if (complaintDetailsInfo.complain_pic2 != null && !complaintDetailsInfo.complain_pic2.equals("")) {
                        iv_img2.setVisibility(View.VISIBLE);
                        bitmapUtils.display(iv_img2, complaintDetailsInfo.complain_pic2);
                    } else {
                        iv_img2.setVisibility(View.GONE);
                    }
                    if (complaintDetailsInfo.complain_pic3 != null && !complaintDetailsInfo.complain_pic3.equals("")) {
                        iv_img3.setVisibility(View.VISIBLE);
                        bitmapUtils.display(iv_img3, complaintDetailsInfo.complain_pic3);
                    } else {
                        iv_img3.setVisibility(View.GONE);
                    }

                    break;
                case complaint_details_err:
                    Toast.makeText(ComplaintDetails.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case get_complain_talk_id://对话
                    if (msg.obj != null) {
                        complaintDialogueInfo = (List<ComplaintDialogueInfo>) msg.obj;
                        if (complaintDialogueAdapter == null) {
                            complaintDialogueAdapter = new ComplaintDialogueAdapter(ComplaintDetails.this, complaintDialogueInfo);
                            lv_dialog.setAdapter(complaintDialogueAdapter);
                        } else {
                            complaintDialogueAdapter.refreshData(complaintDialogueInfo);
                        }
                    }
                    break;
                case get_complain_talk_err:
                    Toast.makeText(ComplaintDetails.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case publish_complain_talk_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            netRun.getComplainTalk(complaintDetailsInfo.complain_id);
                        } else {
                            Toast.makeText(ComplaintDetails.this, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case publish_complain_talk_err:
                    Toast.makeText(ComplaintDetails.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case apply_handle_id://提交仲裁
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(ComplaintDetails.this, str, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case apply_handle_err:
                    Toast.makeText(ComplaintDetails.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintdetails);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = findViewById(R.id._tv_name);
        tv_progress1 = findViewById(R.id.tv_progress1);
        tv_progress2 = findViewById(R.id.tv_progress2);
        tv_progress3 = findViewById(R.id.tv_progress3);
        tv_progress4 = findViewById(R.id.tv_progress4);
        tv_progress5 = findViewById(R.id.tv_progress5);
        tv_storename = findViewById(R.id.tv_storename);
        tv_theme = findViewById(R.id.tv_theme);
        tv_time = findViewById(R.id.tv_time);
        tv_content = findViewById(R.id.tv_content);
        _iv_back = findViewById(R.id._iv_back);
        iv_img1 = findViewById(R.id.iv_img1);
        iv_img2 = findViewById(R.id.iv_img2);
        iv_img3 = findViewById(R.id.iv_img3);
        msgz_sgz = findViewById(R.id.msgz_sgz);
        tv_return = findViewById(R.id.tv_return);
        lv_dialog = findViewById(R.id.lv_dialog);
        tv_theme2 = findViewById(R.id.tv_theme2);
        tv_content2 = findViewById(R.id.tv_content2);
        iv_ssimg1 = findViewById(R.id.iv_ssimg1);
        iv_ssimg2 = findViewById(R.id.iv_ssimg2);
        iv_ssimg3 = findViewById(R.id.iv_ssimg3);
        lv_dialog = findViewById(R.id.lv_dialog);
        et_dialog = findViewById(R.id.et_dialog);
        tv_dialog1 = findViewById(R.id.tv_dialog1);
        tv_dialog2 = findViewById(R.id.tv_dialog2);
        tv_dialog3 = findViewById(R.id.tv_dialog3);
        ll_pendingappeal = findViewById(R.id.ll_pendingappeal);
        ll_appeal = findViewById(R.id.ll_appeal);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.complaint_prompt14));
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        _iv_back.setOnClickListener(this);
        tv_return.setOnClickListener(this);
        tv_dialog1.setOnClickListener(this);
        tv_dialog2.setOnClickListener(this);
        tv_dialog3.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initData() {
        list = (ComplaintslistInfo.datas.list) getIntent().getSerializableExtra("list");
        String complain_id = getIntent().getStringExtra("complain_id");
        netRun.Complaintdetails(complain_id, "1");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id._iv_back||v.getId()==R.id.tv_return){
            finish();
        }else if(v.getId()==R.id.tv_dialog1){
            String s = et_dialog.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(this, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.publishComplainTalk(complaintDetailsInfo.complain_id, s);
            et_dialog.setText("");
        }else if(v.getId()==R.id.tv_dialog2){
            netRun.getComplainTalk(complaintDetailsInfo.complain_id);
        }else if(v.getId()==R.id.tv_dialog3){
            netRun.ApplyHandle(complaintDetailsInfo.complain_id);
        }

//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_return:
//                finish();
//                break;
//            case R.id.tv_dialog1://发布
//                String s = et_dialog.getText().toString();
//                if (TextUtils.isEmpty(s)) {
//                    Toast.makeText(this, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.publishComplainTalk(complaintDetailsInfo.complain_id, s);
//                et_dialog.setText("");
//                break;
//            case R.id.tv_dialog2://刷新
//                netRun.getComplainTalk(complaintDetailsInfo.complain_id);
//                break;
//            case R.id.tv_dialog3://提交仲裁
//                netRun.ApplyHandle(complaintDetailsInfo.complain_id);
//                break;
//        }
    }

    @Override
    public void ReGetData() {

    }

    /**
     * 初始化第二步
     */
    private void init2() {
        tv_theme2.setText(ClutterUtils.TimeStamp2Date(list.complain_datetime, "yyyy-MM-dd HH:mm"));
        tv_content2.setText(list.complain_content);
        if (list.complain_pic1 != null && !list.complain_pic1.equals("")) {
            iv_ssimg1.setVisibility(View.VISIBLE);
            Glide.with(ComplaintDetails.this).load(list.complain_pic1).into(iv_ssimg1);
        } else {
            iv_ssimg1.setVisibility(View.GONE);
        }
        if (list.complain_pic2 != null && !list.complain_pic2.equals("")) {
            iv_ssimg2.setVisibility(View.VISIBLE);
            Glide.with(ComplaintDetails.this).load(list.complain_pic2).into(iv_ssimg2);
        } else {
            iv_ssimg2.setVisibility(View.GONE);
        }
        if (list.complain_pic3 != null && !list.complain_pic3.equals("")) {
            iv_ssimg3.setVisibility(View.VISIBLE);
            Glide.with(ComplaintDetails.this).load(list.complain_pic3).into(iv_ssimg3);
        } else {
            iv_ssimg3.setVisibility(View.GONE);
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
