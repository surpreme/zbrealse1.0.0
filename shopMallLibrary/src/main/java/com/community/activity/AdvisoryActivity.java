package com.community.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;
import com.community.adapter.ChatListAdapter;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 在线咨询 advisory
 * Created by mayn on 2018/9/5.
 */
public class AdvisoryActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_inputtype, iv_smile, iv_menu;
    private TextView tv_name, tv_new, tv_sendmessage, tv_voice;
    private RecyclerView rv_list;
    private EditText et_input;
    private ChatListAdapter chatListAdapter;

    private boolean isly = true, isvoice = false;//录音权限提示/是否语音输入

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisory);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_inputtype = (ImageView) findViewById(R.id.iv_inputtype);
        iv_smile = (ImageView) findViewById(R.id.iv_smile);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_new = (TextView) findViewById(R.id.tv_new);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
        tv_sendmessage = (TextView) findViewById(R.id.tv_sendmessage);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        et_input = (EditText) findViewById(R.id.et_input);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        iv_inputtype.setOnClickListener(this);
        iv_smile.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        tv_sendmessage.setOnClickListener(this);
        et_input.addTextChangedListener(txt);
        tv_voice.setOnTouchListener(ontouch);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
//        chatListAdapter=new ChatListAdapter(this,data);
//        rv_list.setAdapter(chatListAdapter);
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
//            case R.id.tv_new://个人资料
//                break;
//            case R.id.iv_inputtype://输入类型切换
//                setInputType();
//                break;
//            case R.id.iv_smile://表情
//                ShutKeyKeyboard();
//                break;
//            case R.id.iv_menu://菜单
//                ShutKeyKeyboard();
//                break;
//            case R.id.tv_sendmessage://发送消息
//                break;
//        }

        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.iv_inputtype){
            //输入类型切换
            setInputType();
        }else if(v.getId()==R.id.iv_smile){
            //表情
            ShutKeyKeyboard();
        }else if(v.getId()==R.id.iv_menu){
            //菜单
            ShutKeyKeyboard();
        }
    }

    /**
     * 修改输入方式
     */
    private void setInputType() {
        if (isvoice) {
            et_input.setVisibility(View.VISIBLE);
            iv_smile.setVisibility(View.VISIBLE);
            tv_voice.setVisibility(View.GONE);
            iv_inputtype.setImageResource(R.drawable.session_voice);
            isvoice = false;
        } else {
            et_input.setVisibility(View.GONE);
            iv_smile.setVisibility(View.GONE);
            tv_voice.setVisibility(View.VISIBLE);
            iv_inputtype.setImageResource(R.drawable.session_keyboard);
            ShutKeyKeyboard();
            isvoice = true;
        }
    }

    /**
     * 语音按压监听
     */
    private View.OnTouchListener ontouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    // 移动
                    break;
                case MotionEvent.ACTION_DOWN:
                    // 按下
                    tv_voice.setBackgroundResource(R.drawable.ed_input12);
//                        utils.SendAudioMessage(1,toChatUsername,false);
                    break;
                case MotionEvent.ACTION_UP:
                    // 抬起
                    tv_voice.setBackgroundResource(R.drawable.ed_input11);
//                        utils.SendAudioMessage(2,toChatUsername,false);
                    break;
            }
            return true;
        }
    };

    /**
     * 输入监听
     */
    private TextWatcher txt = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String s1 = s.toString();
            if (s1 == null || s1.length() == 0) {
                tv_sendmessage.setVisibility(View.GONE);
                iv_menu.setVisibility(View.VISIBLE);
            } else {
                tv_sendmessage.setVisibility(View.VISIBLE);
                iv_menu.setVisibility(View.INVISIBLE);
            }
        }
    };

    /**
     * 关闭软键盘
     */
    private void ShutKeyKeyboard() {
        et_input.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
    }

    @Override
    public void ReGetData() {

    }
}
