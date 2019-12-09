package chat.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.activity.AddressManageActivity;
import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import chat.adapter.ChatListAdapter;
import chat.adapter.SessionBottomAdapter;
import chat.model.IMUserSigInfo;
import chat.model.SessionMuneInfo;
import chat.utils.TXImUtils;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 对话
 * Created by mayn on 2018/10/25.
 */
public class DialogueActivity extends Activity implements View.OnClickListener, Mark, SwipeRefreshLayout.OnRefreshListener, View.OnFocusChangeListener {
    private ImageView iv_goback, iv_inputtype, iv_smile, iv_menu;
    private TextView tv_title, tv_voice, tv_send;
    private RecyclerView rv_list, rv_btnmenu;
    private RelativeLayout rl_pager;
    private LinearLayout ll_voicets, ll_gotobottom;
    private SwipeRefreshLayout msg_refresh;
    private EditText et_input;
    private TIMConversation conversation;//会话
    private String member_id, goods_image, goods_name, goods_price, goods_sales, goods_id, order_id;
    private IMUserSigInfo iMUserSigInfo;//IM签名实体类
    private ChatListAdapter chatListAdapter;//消息适配
    private SessionBottomAdapter sessionBottomAdapter;//表情/菜单适配
    private int intenh, lastmenutype, menutype;//菜单高度/上一次选中的菜单类型/菜单类型
    private List<SessionMuneInfo> sessionMuneInfo;//菜单
    private boolean isly = true;//是否提示录音权限
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;//图片
    private boolean isRefresh = false;//刷新状态
    private MediaPlayer plater;//录音
    private boolean GoToBottomIsShow = false;//去底部是否显示
    private int GoToBottomWidth;//去底部的动画运动宽度
    private Animation GoToBottomShow, GoToBottomHide;//去底部的按钮动画

    private APPSingleton application;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case get_imusersig_id://获取会员签名
                    if (msg.obj != null) {
                        iMUserSigInfo = (IMUserSigInfo) msg.obj;
                        if (iMUserSigInfo.message.contains(getString(R.string.succeed))) {
                            tv_title.setText(iMUserSigInfo.datas.identifierNick);
                            conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, iMUserSigInfo.datas.identifier);//会话类型：单聊
                            getMessageLogging(10, null);
                        } else {
                            Toast.makeText(DialogueActivity.this, iMUserSigInfo.message, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
                case imorder_affirm_id://确认订单
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (!str.equals("1")) {
                            Toast.makeText(application, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case imorder_affirm_err:
                    Toast.makeText(application, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case imorder_address_id://订单修改
                    Log.i("--------------","订单修改1  ");
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (!str.equals("1")) {
                            Toast.makeText(application, str, Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(application, AddressManageActivity.class);
                            intent.putExtra("cb_out", "out");
                            startActivityForResult(intent, Addres_Result);
                        }
                    }
                    break;
                case imorder_address_err:
                    Toast.makeText(application, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case imorder_address2_id://订单修改提交
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (!str.equals("1")) {
                            Toast.makeText(application, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case imorder_address2_err:
                    Toast.makeText(application, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case EMOJI_CODE:
                    if (msg.obj != null) {
                        //表情
                        int emoji = (int) msg.obj;
                        et_input.append(new String(Character.toChars(emoji)));
                    }
                    break;
                case CHOOSE_MENU:
                    if (msg.obj != null) {
                        //菜单
                        int str = (int) msg.obj;
                        switch (str) {
                            case 0:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (!Settings.System.canWrite(DialogueActivity.this)) {
                                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                                Uri.parse("package:" + getPackageName()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        startPick();
                                    }
                                }
                                break;
                            case 1:
                                TXImUtils.TXsendGoodsMsg(conversation, goods_image, goods_name, goods_price, goods_sales, goods_id);
                                break;
                        }
                    }
                    break;
                case MESSAGE_TOBOTTOM://滚动到底部
//                    rv_list.scrollToPosition(chatListAdapter.getItemCount() - 1);
                    if (chatListAdapter != null && chatListAdapter.getItemCount() != 0) {
                        rv_list.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
                    }
                    break;
                case DELAY_MENU://弹出菜单
                    showMenu();
                    break;
                case ORDER_AFFIRM://订单确认
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        netRun.ImOrderAffirm(str);
                    }
                    break;
                case ORDER_ADDRESS://订单修改
                    if (msg.obj != null) {
                        order_id = (String) msg.obj;
                        netRun.ImOrderAddress(order_id);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialogue);
        findViewById();
    }

    protected void findViewById() {
        iv_goback = findViewById(R.id.iv_goback);
        iv_menu = findViewById(R.id.iv_menu);
        iv_inputtype = findViewById(R.id.iv_inputtype);
        iv_smile = findViewById(R.id.iv_smile);
        tv_title = findViewById(R.id.tv_title);
        tv_voice = findViewById(R.id.tv_voice);
        tv_send = findViewById(R.id.tv_send);
        rv_list = findViewById(R.id.rv_list);
        rv_btnmenu = findViewById(R.id.rv_btnmenu);
        et_input = findViewById(R.id.et_input);
        msg_refresh = findViewById(R.id.msg_refresh);
        ll_voicets = findViewById(R.id.ll_voicets);
        rl_pager = findViewById(R.id.rl_pager);
        ll_gotobottom = findViewById(R.id.ll_gotobottom);
        initView();
    }

    protected void initView() {
        application = (APPSingleton) getApplication();
        iv_goback.setOnClickListener(this);
        iv_inputtype.setOnClickListener(this);
        iv_smile.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        et_input.setOnClickListener(this);
        rl_pager.setOnClickListener(this);
        ll_gotobottom.setOnClickListener(this);
        et_input.setOnFocusChangeListener(this);
        plater = new MediaPlayer();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        member_id = intent.getStringExtra("member_id");
        goods_image = intent.getStringExtra("goods_image");
        goods_name = intent.getStringExtra("goods_name");
        goods_price = intent.getStringExtra("goods_price");
        goods_sales = intent.getStringExtra("goods_sales");
        goods_id = intent.getStringExtra("goods_id");
        netRun = new NetRun(this, handler);
        //监听输入框
        et_input.addTextChangedListener(txt);
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        tv_voice.setOnTouchListener(ontouch);
        rv_list.setOnTouchListener(ontouch);
        //底部菜单
        rv_btnmenu.setLayoutManager(new GridLayoutManager(this, 7));
        intenh = (int) (ClutterUtils.getScreenHeight(this) * 0.4);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rv_btnmenu.getLayoutParams();
        layoutParams.height = intenh;
        rv_btnmenu.setLayoutParams(layoutParams);
        sessionMuneInfo = new ArrayList<>();
        sessionMuneInfo.add(new SessionMuneInfo(R.drawable.chatmenu_icon1, getString(R.string.image)));
        sessionMuneInfo.add(new SessionMuneInfo(R.drawable.chatmenu_icon2, getString(R.string.goods)));
        sessionBottomAdapter = new SessionBottomAdapter(1, DialogueActivity.this, handler, intenh / 5, sessionMuneInfo);
        rv_btnmenu.setAdapter(sessionBottomAdapter);
        msg_refresh.setOnRefreshListener(this);
        rv_list.addOnScrollListener(onscroll);
        //去底部按钮
        GoToBottomWidth = ClutterUtils.dp2px(DialogueActivity.this, 110);
        GoToBottomShow = new TranslateAnimation(GoToBottomWidth, 0
                , 0, 0);
        GoToBottomShow.setFillAfter(true);
        GoToBottomShow.setInterpolator(new OvershootInterpolator());
        GoToBottomShow.setDuration(300);
        GoToBottomShow.setAnimationListener(animation);

        GoToBottomHide = new TranslateAnimation(0, GoToBottomWidth
                , 0, 0);
        GoToBottomHide.setFillAfter(true);
        GoToBottomHide.setInterpolator(new OvershootInterpolator());
        GoToBottomHide.setDuration(300);
        GoToBottomHide.setAnimationListener(animation);
        initData();
    }


    protected void initData() {
        netRun.getIMUserSig(member_id);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.iv_goback){
            finish();
        }else if (v.getId()==R.id.iv_inputtype){
            //输入类型
            ClutterUtils.closeKeyboard(DialogueActivity.this);
            rv_btnmenu.setVisibility(View.GONE);
            if (et_input.getVisibility() == View.VISIBLE) {
                iv_inputtype.setImageResource(R.drawable.session_keyboard);
                et_input.setVisibility(View.GONE);
                tv_voice.setVisibility(View.VISIBLE);
            } else {
                iv_inputtype.setImageResource(R.drawable.session_voice);
                et_input.setVisibility(View.VISIBLE);
                tv_voice.setVisibility(View.GONE);
            }
        }else if(v.getId()== R.id.iv_smile){
            menutype = 1;
            ClutterUtils.closeKeyboard(DialogueActivity.this);
            handler.sendEmptyMessageDelayed(DELAY_MENU, 100);
        }else if (v.getId()==R.id.tv_send){
            String txt = et_input.getText().toString();
            if (TextUtils.isEmpty(txt)) {
                Toast.makeText(this, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
                return;
            }
            et_input.setText("");
            TXImUtils.TXsendTextMsg(conversation, txt);
        }else if(v.getId()==R.id.iv_menu){
            menutype = 2;
            ClutterUtils.closeKeyboard(DialogueActivity.this);
            handler.sendEmptyMessageDelayed(DELAY_MENU, 100);
        }else if(v.getId()==R.id.et_input){
            rv_btnmenu.setVisibility(View.GONE);
            handler.sendEmptyMessageDelayed(MESSAGE_TOBOTTOM, 150);
        }else if(v.getId()==R.id.rl_pager){
            ClutterUtils.closeKeyboard(DialogueActivity.this);
            rv_btnmenu.setVisibility(View.GONE);
        }else if(v.getId()== R.id.ll_gotobottom){
            handler.sendEmptyMessage(MESSAGE_TOBOTTOM);
        }







//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.iv_inputtype:
//                //输入类型
//                ClutterUtils.closeKeyboard(DialogueActivity.this);
//                rv_btnmenu.setVisibility(View.GONE);
//                if (et_input.getVisibility() == View.VISIBLE) {
//                    iv_inputtype.setImageResource(R.drawable.session_keyboard);
//                    et_input.setVisibility(View.GONE);
//                    tv_voice.setVisibility(View.VISIBLE);
//                } else {
//                    iv_inputtype.setImageResource(R.drawable.session_voice);
//                    et_input.setVisibility(View.VISIBLE);
//                    tv_voice.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.iv_smile://表情
//                menutype = 1;
//                ClutterUtils.closeKeyboard(DialogueActivity.this);
//                handler.sendEmptyMessageDelayed(DELAY_MENU, 100);
//                break;
//            case R.id.tv_send://发送
//                String txt = et_input.getText().toString();
//                if (TextUtils.isEmpty(txt)) {
//                    Toast.makeText(this, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                et_input.setText("");
//                TXImUtils.TXsendTextMsg(conversation, txt);
//                break;
//            case R.id.iv_menu://菜单
//                menutype = 2;
//                ClutterUtils.closeKeyboard(DialogueActivity.this);
//                handler.sendEmptyMessageDelayed(DELAY_MENU, 100);
//                break;
//            case R.id.et_input://隐藏菜单
//                rv_btnmenu.setVisibility(View.GONE);
//                handler.sendEmptyMessageDelayed(MESSAGE_TOBOTTOM, 150);
//                break;
////            case R.id.msg_refresh://隐藏菜单
////                rv_btnmenu.setVisibility(View.GONE);
////                handler.sendEmptyMessageDelayed(MESSAGE_TOBOTTOM,150);
////                break;
//            case R.id.rl_pager:
//                ClutterUtils.closeKeyboard(DialogueActivity.this);
//                rv_btnmenu.setVisibility(View.GONE);
//                break;
//            case R.id.ll_gotobottom://回到底部
//                handler.sendEmptyMessage(MESSAGE_TOBOTTOM);
//                break;
//        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        Log.i("--------------", "焦点  " + b);
        if (b) {
            rv_btnmenu.setVisibility(View.GONE);
            handler.sendEmptyMessageDelayed(MESSAGE_TOBOTTOM, 150);
        }
    }

    @Override
    public void onRefresh() {//消息记录
        if (chatListAdapter == null) return;
        if (!isRefresh) {
            isRefresh = true;
            getMessageLogging(10, chatListAdapter.getFirstMessage());
        }
    }

    /**
     * 显示菜单
     */
    private void showMenu() {
        if (rv_btnmenu.getVisibility() == View.VISIBLE && menutype == lastmenutype) {
            rv_btnmenu.setVisibility(View.GONE);
            return;
        }
        //弹出菜单/表情
        if (menutype == 1) {
            rv_btnmenu.setLayoutManager(new GridLayoutManager(DialogueActivity.this, 7));
            sessionBottomAdapter.setType(1, intenh / 5);
            rv_btnmenu.setVisibility(View.VISIBLE);
        } else {
            rv_btnmenu.setLayoutManager(new GridLayoutManager(DialogueActivity.this, 4));
            sessionBottomAdapter.setType(2, intenh / 2);
            rv_btnmenu.setVisibility(View.VISIBLE);
        }
        lastmenutype = menutype;
    }

    /**
     * 语音按压监听
     */
    private View.OnTouchListener ontouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.rv_list) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ClutterUtils.closeKeyboard(DialogueActivity.this);
                    rv_btnmenu.setVisibility(View.GONE);
                }
                return false;
            } else if (v.getId() == R.id.tv_voice) {
                boolean persimmions = TXImUtils.getPersimmions(DialogueActivity.this);
                if (persimmions) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            // 移动
                            break;
                        case MotionEvent.ACTION_DOWN:
                            // 按下
                            tv_voice.setBackgroundResource(R.drawable.ed_input12);
                            ll_voicets.setVisibility(View.VISIBLE);
                            TXImUtils.startSoundRecording();
                            break;
                        case MotionEvent.ACTION_UP:
                            // 抬起
                            tv_voice.setBackgroundResource(R.drawable.ed_input11);
                            ll_voicets.setVisibility(View.GONE);
                            TXImUtils.stopSoundRecording(conversation, plater, DialogueActivity.this);
                            break;
                    }
                } else if (isly) {
                    isly = false;
                    Toast.makeText(DialogueActivity.this, getString(R.string.insufficient_permissions), Toast.LENGTH_LONG).show();
                }
                return true;
            } else {
                return true;
            }
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
                tv_send.setVisibility(View.GONE);
                iv_menu.setVisibility(View.VISIBLE);
            } else {
                tv_send.setVisibility(View.VISIBLE);
                iv_menu.setVisibility(View.INVISIBLE);
            }
        }
    };

    /**
     * 去底部动画监听
     */
    private Animation.AnimationListener animation = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            if (GoToBottomShow == animation) {//开始显示
                ll_gotobottom.setVisibility(View.VISIBLE);
                ll_gotobottom.setClickable(true);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (GoToBottomHide == animation) {//隐藏完成
                ll_gotobottom.setVisibility(View.GONE);
                ll_gotobottom.setClickable(false);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 消息滚动监听
     */
    private RecyclerView.OnScrollListener onscroll = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0 && chatListAdapter != null) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv_list.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                if ((chatListAdapter.getItemCount() - position) > 8) {
                    if (!GoToBottomIsShow) {
                        ll_gotobottom.startAnimation(GoToBottomShow);
                        GoToBottomIsShow = true;
                    }
                } else {
                    if (GoToBottomIsShow) {
                        ll_gotobottom.startAnimation(GoToBottomHide);
                        GoToBottomIsShow = false;
                    }
                }
            }
        }
    };

    /**
     * 获取消息记录
     */
    public void getMessageLogging(int num, TIMMessage msg) {
        //获取此会话的消息
        conversation.getLocalMessage(num, //获取此会话最近的 10 条消息
                msg, //不指定从哪条消息开始获取 - 等同于从最新的消息开始往前
                new TIMValueCallBack<List<TIMMessage>>() {//回调接口
                    @Override
                    public void onError(int code, String desc) {//获取消息失败
                        Log.d("---------------", "获取消息记录失败. code: " + code + " errmsg: " + desc);
                        //隐藏刷新进度条
                        msg_refresh.setRefreshing(false);
                        isRefresh = false;
                        if (chatListAdapter == null) {
                            List<TIMMessage> msgs = new ArrayList<>();
                            chatListAdapter = new ChatListAdapter(DialogueActivity.this, msgs,
                                    application.iMUserSigInfo.datas.headurl, iMUserSigInfo.datas.headurl, iMUserSigInfo.datas.identifier, handler, plater);
                            TXImUtils.adapter = chatListAdapter;
                            rv_list.setAdapter(chatListAdapter);
                        }
                    }

                    @Override
                    public void onSuccess(List<TIMMessage> msgs) {//获取消息成功
                        Log.d("---------------", "获取消息成功");
                        //隐藏刷新进度条
                        msg_refresh.setRefreshing(false);
                        isRefresh = false;
                        //根据时间戳排序
                        Collections.sort(msgs, new Comparator() {
                            @Override
                            public int compare(Object o1, Object o2) {
                                TIMMessage stu1 = (TIMMessage) o1;
                                TIMMessage stu2 = (TIMMessage) o2;
                                if (stu1.timestamp() > stu2.timestamp()) {
                                    return 1;
                                } else if (stu1.timestamp() == stu2.timestamp()) {
                                    return 0;
                                } else {
                                    return -1;
                                }
                            }
                        });
                        if (chatListAdapter == null) {
                            chatListAdapter = new ChatListAdapter(DialogueActivity.this, msgs,
                                    application.iMUserSigInfo.datas.headurl, iMUserSigInfo.datas.headurl, iMUserSigInfo.datas.identifier, handler, plater);
                            TXImUtils.adapter = chatListAdapter;
                            rv_list.setAdapter(chatListAdapter);
                            handler.sendEmptyMessage(MESSAGE_TOBOTTOM);
                        } else {
                            chatListAdapter.AddMessageHistory(msgs);
                            if (msgs != null && msgs.size() != 0) {
                                rv_list.smoothScrollToPosition(msgs.size() - 1);
                            }
                        }

//                        for (TIMMessage msg : msgs) {
//                            //可以通过timestamp()获得消息的时间戳, isSelf()是否为自己发送的消息
//                            Log.e("---------------", "get msg: " + msg.timestamp() + " self: " + msg.isSelf() + " seq: " + msg.msg.seq());
//                        }
                    }
                });
    }

    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    图片存在 " + outputFile.exists());
            String str = outputFile.getPath().replaceAll("/storage/emulated/0", "");
            TXImUtils.TXsendImageMsg(conversation, str);
        }
    };

    // 调用系统相册
    protected void startPick() {
        PermissionGen.needPermission(DialogueActivity.this,
                LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Addres_Result) {
            // 获取 activity的返回数据
            if (data != null) {
                if (data.getStringExtra("addres").equals("addres")) {
                    Bundle bundle = data.getExtras();
                    String address_id = bundle.getString("address_id");
                    netRun.ImOrderAddress2(order_id, address_id);
                }
            }
        } else {
            // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
            mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle(getString(R.string.access_request));
        //设置正文
        builder.setMessage(getString(R.string.access_request2));

        //添加确定按钮点击事件
        builder.setPositiveButton(getString(R.string.go_to_set), new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + DialogueActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }

    @Override
    protected void onDestroy() {
        // 释放播放器
        plater.release();
        TXImUtils.adapter = null;
        super.onDestroy();
    }


}
