package com.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.adapter.GroupDatailsMemberAdapter;
import com.community.model.GroupDatailsInfo;
import com.donkingliang.labels.LabelsView;

import java.util.ArrayList;

/**
 * 群组详情
 * Created by mayn on 2018/9/17.
 */
public class GroupDatailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_icon,iv_bg,iv_qzicon;
    private TextView tv_name, tv_desc, tv_number, tv_join,tv_notice,tv_time,tv_gg;
    private MyGridView gv_icon;
    private LabelsView labels;

    private String id;
    private GroupDatailsInfo groupDatailsInfo;
    private GroupDatailsMemberAdapter groupDatailsMemberAdapter;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case group_datails_id://群组详情
                    if (msg.obj!=null){
                        groupDatailsInfo= (GroupDatailsInfo) msg.obj;
                        if (groupDatailsInfo.circle_info==null){
                            Toast.makeText(appSingleton, getString(R.string.find_reminder66), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Glide.with(GroupDatailsActivity.this).load(groupDatailsInfo.circle_info.circle_img).into(iv_icon);
                        Glide.with(GroupDatailsActivity.this).load(groupDatailsInfo.circle_info.circle_back_img).into(iv_bg);
                        Glide.with(GroupDatailsActivity.this).load(groupDatailsInfo.circle_info.circle_master_avatar).into(iv_qzicon);
                        tv_name.setText(groupDatailsInfo.circle_info.circle_name);
                        tv_desc.setText(groupDatailsInfo.circle_info.circle_desc);
                        tv_notice.setText(groupDatailsInfo.circle_info.circle_pursuereason);
                        tv_gg.setText(groupDatailsInfo.circle_info.circle_notice);
                        tv_time.setText(groupDatailsInfo.circle_info.circle_addtime);
                        tv_number.setText(getString(R.string.find_reminder67)+"   "+groupDatailsInfo.circle_info.member_count);
                        groupDatailsMemberAdapter=new GroupDatailsMemberAdapter(GroupDatailsActivity.this,groupDatailsInfo.circle_member_list);
                        gv_icon.setAdapter(groupDatailsMemberAdapter);
                        ArrayList<String> label = new ArrayList<>();
                        label.addAll(groupDatailsInfo.circle_info.circle_tag_arr);
                        labels.setLabels(label);
                    }
                    break;
                case group_datails_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupdatails);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_gg = (TextView) findViewById(R.id.tv_gg);
        tv_join = (TextView) findViewById(R.id.tv_join);
        tv_notice = (TextView) findViewById(R.id.tv_notice);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        iv_qzicon = (ImageView) findViewById(R.id.iv_qzicon);
        gv_icon = (MyGridView) findViewById(R.id.gv_icon);
        labels = (LabelsView) findViewById(R.id.labels);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        tv_join.setOnClickListener(this);
        id=getIntent().getStringExtra("circle_id");
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.GroupDatails(id);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.tv_join){
            //申请加入
            Intent intent = new Intent(GroupDatailsActivity.this, JoinGroupActivity.class);
            intent.putExtra("circle_id",groupDatailsInfo.circle_info.circle_id);
            startActivity(intent);
        }
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_join://申请加入
//                Intent intent = new Intent(GroupDatailsActivity.this, JoinGroupActivity.class);
//                intent.putExtra("circle_id",groupDatailsInfo.circle_info.circle_id);
//                startActivity(intent);
//                break;
//        }
    }

    @Override
    public void ReGetData() {

    }
}
