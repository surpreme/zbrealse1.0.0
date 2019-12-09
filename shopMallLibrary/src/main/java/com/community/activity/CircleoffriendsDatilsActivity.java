package com.community.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.Mark;
import com.aite.a.model.CircleoffriendsDatilsInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.adapter.CircleoffriendsDatils2Adapter;
import com.community.adapter.CircleoffriendsDatilsAdapter;

/**
 * 朋友圈详情
 * Created by mayn on 2018/10/11.
 */
public class CircleoffriendsDatilsActivity extends Activity implements View.OnClickListener ,Mark{
    private ImageView iv_goback, iv_pl, iv_gz, iv_icon;
    private TextView tv_name, tv_desc, tv_address, tv_time, tv_del, tv_sendmessage, tv_dz;
    private MyGridView gv_img;
    private MyListView lv_pl;
    private EditText et_input;
    private String theme_id;
    private CircleoffriendsDatilsInfo circleoffriendsDatilsInfo;
    private CircleoffriendsDatilsAdapter circleoffriendsDatilsAdapter;
    private CircleoffriendsDatils2Adapter circleoffriendsDatils2Adapter;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case dynamic_datails_id://详情
                    if (msg.obj != null) {
                        circleoffriendsDatilsInfo = (CircleoffriendsDatilsInfo) msg.obj;
                        if (circleoffriendsDatilsInfo.theme_info.is_like.equals("1")){
                            iv_gz.setImageResource(R.drawable.heart2);
                        }else{
                            iv_gz.setImageResource(R.drawable.heart1);
                        }
                        Glide.with(CircleoffriendsDatilsActivity.this).load(circleoffriendsDatilsInfo.theme_info.member_avatar).into(iv_icon);
                        tv_name.setText(circleoffriendsDatilsInfo.theme_info.member_name);
                        tv_desc.setText(circleoffriendsDatilsInfo.theme_info.theme_name);
                        tv_time.setText(circleoffriendsDatilsInfo.theme_info.theme_addtime);
                        StringBuffer str = new StringBuffer();
                        String str2 = "";
                        for (int i = 0; i < circleoffriendsDatilsInfo.theme_like.size(); i++) {
                            str.append(circleoffriendsDatilsInfo.theme_like.get(i).member_name + ",");
                        }
                        if (str.length() != 0) {
                            str2 = str.substring(0, str.length() - 1);
                        }
                        if (str2==null||str2.length()==0){
                            tv_dz.setVisibility(View.GONE);
                        }else{
                            tv_dz.setVisibility(View.VISIBLE);
                            tv_dz.setText(str2);
                        }

                        if (circleoffriendsDatilsInfo.theme_info.address == null) {
                            tv_address.setVisibility(View.GONE);
                        } else {
                            tv_address.setVisibility(View.VISIBLE);
                            tv_address.setText(circleoffriendsDatilsInfo.theme_info.address);
                        }
                        if (circleoffriendsDatilsInfo.theme_info.thumb_list != null && circleoffriendsDatilsInfo.theme_info.thumb_list.size() != 0) {
                            gv_img.setVisibility(View.VISIBLE);
                            if (circleoffriendsDatilsInfo.theme_info.thumb_list.size() < 2) {
                                gv_img.setNumColumns(1);
                            } else if (circleoffriendsDatilsInfo.theme_info.thumb_list.size() < 3) {
                                gv_img.setNumColumns(2);
                            } else {
                                gv_img.setNumColumns(3);
                            }
                            circleoffriendsDatilsAdapter = new CircleoffriendsDatilsAdapter
                                    (CircleoffriendsDatilsActivity.this, circleoffriendsDatilsInfo.theme_info.thumb_list);
                            gv_img.setAdapter(circleoffriendsDatilsAdapter);
                        }

                        circleoffriendsDatils2Adapter=new CircleoffriendsDatils2Adapter(CircleoffriendsDatilsActivity.this,
                                circleoffriendsDatilsInfo.threply_list);
                        lv_pl.setAdapter(circleoffriendsDatils2Adapter);
                    }
                    break;
                case dynamic_datails_err:
                    Toast.makeText(CircleoffriendsDatilsActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case dynamic_comment_id://评论
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(CircleoffriendsDatilsActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            netRun.DynamicDatails(theme_id);
                        }
                    }
                    break;
                case dynamic_comment_err:
                    Toast.makeText(CircleoffriendsDatilsActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case praise_circleoffriends_id://点赞
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(CircleoffriendsDatilsActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            netRun.DynamicDatails(theme_id);
                        }
                    }
                    break;
                case praise_circleoffriends_err:
                    Toast.makeText(CircleoffriendsDatilsActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_circleoffriendsdatils);
        findViewById();
    }

    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_pl = (ImageView) findViewById(R.id.iv_pl);
        iv_gz = (ImageView) findViewById(R.id.iv_gz);
        tv_dz = (TextView) findViewById(R.id.tv_dz);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_del = (TextView) findViewById(R.id.tv_del);
        tv_sendmessage = (TextView) findViewById(R.id.tv_sendmessage);
        gv_img = (MyGridView) findViewById(R.id.gv_img);
        lv_pl = (MyListView) findViewById(R.id.lv_pl);
        et_input = (EditText) findViewById(R.id.et_input);
        initView();
    }

    protected void initView() {
        iv_goback.setOnClickListener(this);
        tv_dz.setOnClickListener(this);
        tv_sendmessage.setOnClickListener(this);
        iv_gz.setOnClickListener(this);
        theme_id = getIntent().getStringExtra("theme_id");
        netRun = new NetRun(this, handler);
        initData();
    }

    protected void initData() {
        netRun.DynamicDatails(theme_id);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_dz://点赞
//                Intent intent = new Intent(CircleoffriendsDatilsActivity.this, PraiseListActivity.class);
//                intent.putExtra("theme_id",theme_id);
//                startActivity(intent);
//                break;
//            case R.id.tv_sendmessage://发消息
//                String s = et_input.getText().toString();
//                if (TextUtils.isEmpty(s)) {
//                    Toast.makeText(CircleoffriendsDatilsActivity.this, getString(R.string.input_comments), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.DynamicComment(theme_id, s);
//                et_input.setText("");
//                break;
//            case R.id.iv_gz://点赞
//                netRun.PraiseCircleoffriends(theme_id);
//                break;
//        }

        if(v.getId()== R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.tv_dz){
            //点赞
            Intent intent = new Intent(CircleoffriendsDatilsActivity.this, PraiseListActivity.class);
            intent.putExtra("theme_id",theme_id);
            startActivity(intent);
        }else if(v.getId()==R.id.tv_sendmessage){
            //发消息
            String s = et_input.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(CircleoffriendsDatilsActivity.this, getString(R.string.input_comments), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.DynamicComment(theme_id, s);
            et_input.setText("");
        }else if(v.getId()== R.id.iv_gz){
            //点赞
            netRun.PraiseCircleoffriends(theme_id);
        }
    }

}
