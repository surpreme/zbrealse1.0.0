package com.community.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

/**
 * 加入群组
 * Created by mayn on 2018/9/17.
 */
public class JoinGroupActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback;
    private EditText et_desc;
    private TextView tv_confirm;
    private String circle_id;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case join_group_id://加入群组
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        if (str.equals(getString(R.string.find_reminder77))){
                            finish();
                        }
                    }
                    break;
                case join_group_err:
                    Toast.makeText(appSingleton, getString(R.string.find_reminder77), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingroup);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        et_desc = (EditText) findViewById(R.id.et_desc);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        initView();
    }

    @Override
    protected void initView() {
        circle_id = getIntent().getStringExtra("circle_id");
        iv_goback.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_confirm://确认
//                String desc = et_desc.getText().toString();
//                if (TextUtils.isEmpty(desc)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder78), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.JoinGroup(circle_id,desc);
//                break;
//        }
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.tv_confirm){
            //确认
            String desc = et_desc.getText().toString();
            if (TextUtils.isEmpty(desc)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder78), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.JoinGroup(circle_id,desc);
        }
    }

    @Override
    public void ReGetData() {

    }
}
