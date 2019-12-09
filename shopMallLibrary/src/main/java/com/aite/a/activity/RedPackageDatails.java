package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.aite.a.model.RedPackageDatailsInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

/**
 * 红包详情
 *
 * @author Administrator
 */
public class RedPackageDatails extends BaseActivity implements OnClickListener {

    private LinearLayout ll_redpackage1;
    private ImageView iv_redpackage1, _iv_back;
    private TextView tv_share, _tv_name, tv_money, tv_time, tv_share2;
    private RelativeLayout rl_redpackage2;
    private String id;
    private NetRun netRun;
    private RedPackageDatailsInfo redPackageDatailsInfo;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case redpackage_datails_id:
                    if (msg.obj != null) {
                        // 红包详情
                        redPackageDatailsInfo = (RedPackageDatailsInfo) msg.obj;

                    }
                    break;
                case redpackage_datails_err:
                    Toast.makeText(RedPackageDatails.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case open_redpackage_id:
                    // 开红包
                    if (msg.obj != null) {
                        List<String> list = (List<String>) msg.obj;
                        if (list.size() == 1) {
                            Toast.makeText(RedPackageDatails.this, list.get(0),
                                    Toast.LENGTH_SHORT).show();
                        } else if (list.size() == 2) {
                            Toast.makeText(RedPackageDatails.this, list.get(1),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
                case open_redpackage_err:
                    Toast.makeText(RedPackageDatails.this,
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
        setContentView(R.layout.activity_redpackagedatails);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_redpackage1 = (LinearLayout) findViewById(R.id.ll_redpackage1);
        iv_redpackage1 = (ImageView) findViewById(R.id.iv_redpackage1);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        tv_share = (TextView) findViewById(R.id.tv_share);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_share2 = (TextView) findViewById(R.id.tv_share2);
        rl_redpackage2 = (RelativeLayout) findViewById(R.id.rl_redpackage2);

        initView();
    }

    @Override
    protected void initView() {
        _iv_back.setOnClickListener(this);
        iv_redpackage1.setOnClickListener(this);
        tv_share2.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        Intent intent2 = getIntent();
        _tv_name.setText(intent2.getStringExtra("name"));
        String money = intent2.getStringExtra("money");
        String time = intent2.getStringExtra("time");
        if (money != null) {
            rl_redpackage2.setVisibility(View.VISIBLE);
            tv_share2.setVisibility(View.VISIBLE);
            ll_redpackage1.setVisibility(View.GONE);
            tv_money.setText(getString(R.string.order_reminder83) + money);
            tv_time.setText(getString(R.string.order_reminder84) + time);
        } else {
            rl_redpackage2.setVisibility(View.GONE);
            tv_share2.setVisibility(View.GONE);
            ll_redpackage1.setVisibility(View.VISIBLE);
            id = intent2.getStringExtra("id");
            netRun.RedPackagedatails(id);
        }
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.iv_redpackage1) {
            // 开红包
            if (redPackageDatailsInfo != null) {
                netRun.OpenRedPackage(redPackageDatailsInfo.id);
            }
        } else if (v.getId() == R.id.tv_share2) {
            //分享
            shareText(getString(R.string.order_reminder90), getString(R.string.app_name), "http://aitecc.com/mall/");
        }

//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.iv_redpackage1:
//                // 开红包
//                if (redPackageDatailsInfo != null) {
//                    netRun.OpenRedPackage(redPackageDatailsInfo.id);
//                }
//                break;
//            case R.id.tv_share2:
//                //分享
//                shareText(getString(R.string.order_reminder90), getString(R.string.app_name), "http://aitecc.com/mall/");
//                break;
//        }
    }

    /**
     * 分享文字内容
     *
     * @param dlgTitle 分享对话框标题
     * @param subject  主题
     * @param content  分享内容（文字）
     */
    private void shareText(String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题  
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题  
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题  
            startActivity(intent);
        }
    }

}
