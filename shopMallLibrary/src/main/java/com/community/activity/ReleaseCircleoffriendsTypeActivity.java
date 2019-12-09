package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 朋友圈可见状态
 * Created by mayn on 2018/10/13.
 */
public class ReleaseCircleoffriendsTypeActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_menu1, rl_menu2, rl_menu3;
    private ImageView iv_choose, iv_choose2, iv_choose3;
    private TextView tv_goback, tv_accomplish;
    private String type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasecircleoffriendstype);
        findViewById();
    }

    @Override
    protected void findViewById() {
        rl_menu1 = (RelativeLayout) findViewById(R.id.rl_menu1);
        rl_menu2 = (RelativeLayout) findViewById(R.id.rl_menu2);
        rl_menu3 = (RelativeLayout) findViewById(R.id.rl_menu3);
        iv_choose = (ImageView) findViewById(R.id.iv_choose);
        iv_choose2 = (ImageView) findViewById(R.id.iv_choose2);
        iv_choose3 = (ImageView) findViewById(R.id.iv_choose3);
        tv_goback = (TextView) findViewById(R.id.tv_goback);
        tv_accomplish = (TextView) findViewById(R.id.tv_accomplish);
        initView();
    }

    @Override
    protected void initView() {
        rl_menu1.setOnClickListener(this);
        rl_menu2.setOnClickListener(this);
        rl_menu3.setOnClickListener(this);
        tv_goback.setOnClickListener(this);
        tv_accomplish.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.rl_menu1:
//                type = "0";
//                iv_choose.setVisibility(View.VISIBLE);
//                iv_choose2.setVisibility(View.INVISIBLE);
//                iv_choose3.setVisibility(View.INVISIBLE);
//                break;
//            case R.id.rl_menu2:
//                type = "2";
//                iv_choose2.setVisibility(View.VISIBLE);
//                iv_choose.setVisibility(View.INVISIBLE);
//                iv_choose3.setVisibility(View.INVISIBLE);
//                break;
//            case R.id.rl_menu3:
//                type = "1";
//                iv_choose3.setVisibility(View.VISIBLE);
//                iv_choose.setVisibility(View.INVISIBLE);
//                iv_choose2.setVisibility(View.INVISIBLE);
//                break;
//            case R.id.tv_goback:
//                finish();
//                break;
//            case R.id.tv_accomplish://完成
//                Intent intent=getIntent();
//                intent.putExtra("type",type);
//                setResult(108,intent);
//                finish();
//                break;
//        }
        if(v.getId()==R.id.rl_menu1){
            type = "0";
            iv_choose.setVisibility(View.VISIBLE);
            iv_choose2.setVisibility(View.INVISIBLE);
            iv_choose3.setVisibility(View.INVISIBLE);
        }else if(v.getId()== R.id.rl_menu2){
            type = "2";
            iv_choose2.setVisibility(View.VISIBLE);
            iv_choose.setVisibility(View.INVISIBLE);
            iv_choose3.setVisibility(View.INVISIBLE);
        }else if(v.getId()==R.id.rl_menu3){
            type = "1";
            iv_choose3.setVisibility(View.VISIBLE);
            iv_choose.setVisibility(View.INVISIBLE);
            iv_choose2.setVisibility(View.INVISIBLE);
        }else if(v.getId()==R.id.tv_goback){
            finish();
        }else if(v.getId()==R.id.tv_accomplish){
            //完成
            Intent intent=getIntent();
            intent.putExtra("type",type);
            setResult(108,intent);
            finish();
        }
    }

    @Override
    public void ReGetData() {

    }
}
