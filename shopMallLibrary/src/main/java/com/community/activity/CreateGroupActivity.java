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
import com.bumptech.glide.Glide;
import com.community.model.CreateGroupbgInfo;
import com.community.utils.CreateGroupbgPopu;
import java.util.List;


/**
 * 创建群组
 * Created by mayn on 2018/9/18.
 */
public class CreateGroupActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_icon;
    private EditText et_name, et_labe, et_condition, et_introduction;
    private TextView tv_create;
    private List<CreateGroupbgInfo> createGroupbgInfo;
    private String circle_back_img="";
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case create_group_id://创建群组
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            finish();
                        }
                    }
                    break;
                case create_group_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case create_groupbg_id://创建群组背景
                    if (msg.obj!=null){
                        createGroupbgInfo= (List<CreateGroupbgInfo>) msg.obj;

                    }
                    break;
                case create_groupbg_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        et_name = (EditText) findViewById(R.id.et_name);
        et_labe = (EditText) findViewById(R.id.et_labe);
        et_condition = (EditText) findViewById(R.id.et_condition);
        et_introduction = (EditText) findViewById(R.id.et_introduction);
        tv_create = (TextView) findViewById(R.id.tv_create);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
        tv_create.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.CreateGroupbg();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.iv_icon){
            //头像
            showpopw();
        }else if(v.getId()==R.id.tv_create){
            //提交
            String name = et_name.getText().toString();
            String labe = et_labe.getText().toString();
            String condition = et_condition.getText().toString();
            String introduction = et_introduction.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder32), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(labe)) {
                labe = "";
            }
            if (TextUtils.isEmpty(condition)) {
                condition = "";
            }
            if (TextUtils.isEmpty(introduction)) {
                introduction = "";
            }
            netRun.CreateGroup(name, introduction, labe, condition, circle_back_img);
        }

//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.iv_icon://头像
//                showpopw();
//                break;
//            case R.id.tv_create://提交
//                String name = et_name.getText().toString();
//                String labe = et_labe.getText().toString();
//                String condition = et_condition.getText().toString();
//                String introduction = et_introduction.getText().toString();
//                if (TextUtils.isEmpty(name)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder32), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(labe)) {
//                    labe = "";
//                }
//                if (TextUtils.isEmpty(condition)) {
//                    condition = "";
//                }
//                if (TextUtils.isEmpty(introduction)) {
//                    introduction = "";
//                }
//                netRun.CreateGroup(name, introduction, labe, condition, circle_back_img);
//                break;
//        }
    }

    /**
     * 显示图片
     */
    private void showpopw(){
        if (createGroupbgInfo==null)return;
        CreateGroupbgPopu createGroupbgPopu=new CreateGroupbgPopu(CreateGroupActivity.this,createGroupbgInfo);
        createGroupbgPopu.setmenu(new CreateGroupbgPopu.menu() {
            @Override
            public void onItemClick(CreateGroupbgInfo data) {
                Glide.with(CreateGroupActivity.this).load(data.adv_pic).into(iv_icon);
                circle_back_img=data.adv_pic_value;
            }
        });
        createGroupbgPopu.showAsDropDown(iv_icon);
    }


    @Override
    public void ReGetData() {

    }
}
