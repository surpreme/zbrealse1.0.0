package livestream.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMGroupEventListener;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMGroupTipsType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;

import chat.utils.TXImUtils;
import livestream.adapter.Bullet2Adapter;
import livestream.mode.BeautifyInfo;
import livestream.mode.LiveRoomDatailsInfo;
import livestream.view.BeautifyPopu;
import livestream.view.HostMenuPopu;
import livestream.view.UserDataPopu;

/**
 * 主播页面
 * Created by Administrator on 2017/9/26.
 */
public class HostActivity extends Activity implements View.OnClickListener,Mark{
    private ImageView iv_menu1,iv_menu2,iv_menu3,iv_menu4,iv_menu5,iv_menu6,iv_face;
    private TextView tv_name,tv_phone,tv_number,tv_label,tv_contribution,tv_send;
    private RecyclerView rv_spectator,rv_chat;
    private LinearLayout ll_btnmenu;
    private RelativeLayout ll_input,lin;
    private EditText et_input;
    private CircleImageView iv_icon;
    //直播
    private TXCloudVideoView mCaptureView;
    private TXLivePushConfig mLivePushConfig;
    private TXLivePusher mLivePusher;
    private String rtmpUrl,room_id;
//    private String rtmpUrl= "rtmp://8293.livepush.myqcloud.com/live/8293_ed4787a2c3?bizid=8293&txSecret=ea0ccd886891992237863fb20c82bf0c&txTime=59CFBF7F";
    //闪光灯
    private boolean isflash=false;
    //是否隐藏软键盘
    private boolean isgone = false;
    //美颜参数
    private BeautifyInfo beautifydatas;
    //弹幕适配
    private Bullet2Adapter bullet2Adapter;
    private LiveRoomDatailsInfo liveRoomDatailsInfo;
    private TIMConversation conversation;//会话
    //个人信息弹窗
    private UserDataPopu postedPopu;
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case liveroom_datails_id://房间信息
                    if (msg.obj != null) {
                        liveRoomDatailsInfo = (LiveRoomDatailsInfo) msg.obj;
                        Glide.with(HostActivity.this).load(liveRoomDatailsInfo.live_member_info.member_avatar).into(iv_icon);
                        tv_name.setText(liveRoomDatailsInfo.live_member_info.member_truename);
                        tv_phone.setText(liveRoomDatailsInfo.live_member_info.room_number);
                        tv_contribution.setText(liveRoomDatailsInfo.live_member_info.fans_num);
                        tv_label.setText(liveRoomDatailsInfo.live_room_info.title);
                        postedPopu = new UserDataPopu(HostActivity.this, liveRoomDatailsInfo.live_member_info);
//                        if (liveRoomDatailsInfo.member_info.is_follow.equals("1")) {
//                            postedPopu.setAttention();
//                        }
                    }
                    break;
                case liveroom_datails_err:
                    Toast.makeText(HostActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    //键盘显示状态
                    isgone = true;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_host);
        findViewById();
    }

    protected void findViewById() {
        iv_icon= findViewById(R.id.iv_icon);
        iv_menu1= (ImageView) findViewById(R.id.iv_menu1);
        iv_menu2= (ImageView) findViewById(R.id.iv_menu2);
        iv_menu3= (ImageView) findViewById(R.id.iv_menu3);
        iv_menu4= (ImageView) findViewById(R.id.iv_menu4);
        iv_menu5= (ImageView) findViewById(R.id.iv_menu5);
        iv_menu6= (ImageView) findViewById(R.id.iv_menu6);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_label= (TextView) findViewById(R.id.tv_label);
        tv_contribution= (TextView) findViewById(R.id.tv_contribution);
        rv_spectator= (RecyclerView) findViewById(R.id.rv_spectator);
        rv_chat= (RecyclerView) findViewById(R.id.rv_chat);
        mCaptureView = (TXCloudVideoView) findViewById(R.id.video_view);
        ll_btnmenu= (LinearLayout) findViewById(R.id.ll_btnmenu);
        ll_input= findViewById(R.id.ll_input);
        et_input= findViewById(R.id.et_input);
        iv_face= findViewById(R.id.iv_face);
        tv_send= findViewById(R.id.tv_send);
        lin= findViewById(R.id.lin);
        initView();
    }

    protected void initView() {
        netRun=new NetRun(this,handler);
        iv_menu1.setOnClickListener(this);
        iv_menu2.setOnClickListener(this);
        iv_menu3.setOnClickListener(this);
        iv_menu4.setOnClickListener(this);
        iv_menu5.setOnClickListener(this);
        iv_menu6.setOnClickListener(this);
        tv_number.setOnClickListener(this);
        tv_label.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        rv_spectator.setLayoutManager(new LinearLayoutManager(this));
        rv_chat.setLayoutManager(new LinearLayoutManager(this));
        bullet2Adapter = new Bullet2Adapter(this, new ArrayList<TIMMessage>(), handler);
        rv_chat.setAdapter(bullet2Adapter);
        TXImUtils.bullet2Adapter = bullet2Adapter;
        beautifydatas=new BeautifyInfo();
        lin.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                lin.getWindowVisibleDisplayFrame(rect);
                int rootInvisibleHeight = lin.getRootView().getHeight() - rect.bottom;
                if (rootInvisibleHeight <= 100) {
                    //软键盘隐藏
                    ll_input.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isgone) {
                                ll_input.setVisibility(View.GONE);
                                ll_btnmenu.setVisibility(View.VISIBLE);
                                isgone = false;
                            }
                        }
                    }, 100);
                } else {
                    handler.sendEmptyMessageDelayed(101, 500);
                }
            }
        });
        initData();
    }

    protected void initData() {
        rtmpUrl=getIntent().getStringExtra("push_url");
        room_id=getIntent().getStringExtra("room_id");
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, room_id);//会话类型：群聊
        TIMManager.getInstance().setGroupEventListener(new TIMGroupEventListener() {//群事件消息
            @Override
            public void onGroupTipsEvent(TIMGroupTipsElem timGroupTipsElem) {
                Log.i("--------------", "群事件消息 " + timGroupTipsElem.getTipsType());
                if (timGroupTipsElem.getTipsType() == TIMGroupTipsType.Join) {
                    //构造一条消息
                    TIMMessage msg = new TIMMessage();
                    // xml 协议的自定义消息
                    String sampleXml = timGroupTipsElem.getOpUser() + " 进入了直播间";
                    //向 TIMMessage 中添加自定义内容
                    TIMCustomElem elem = new TIMCustomElem();
                    elem.setData(sampleXml.getBytes());      //自定义 byte[]
                    elem.setDesc("Join"); //自定义描述信息
                    //将 elem 添加到消息
                    if (msg.addElement(elem) != 0) {
                        Log.d("-------------", "addElement failed");
                        return;
                    }
                    bullet2Adapter.addMsg(msg);
                }
            }
        });
        //创建Pusher对象
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        //开启自动对焦
        mLivePushConfig.setTouchFocus(false);
        mLivePusher.setConfig(mLivePushConfig);
        //设置等待图片
        mLivePushConfig.setPauseImg(BitmapFactory.decodeResource(getResources(), R.drawable.zb_pause_publish));
        //startPusher 的作用是告诉 SDK 音视频流要推到哪个推流URL上去。
        mLivePusher.startPusher(rtmpUrl);

        //startCameraPreview 则是将界面元素和Pusher对象关联起来，从而能够将手机摄像头采集到的画面渲染到屏幕上。
        mLivePusher.startCameraPreview(mCaptureView);
        //质量/是否开启 Qos 流量控制/是否允许动态分辨率
        mLivePusher.setVideoQuality(TXLiveConstants.RTMP_CHANNEL_TYPE_STANDARD, false, false);
        //style             磨皮风格：  0：光滑  1：自然  2：朦胧
        //beautyLevel       磨皮等级： 取值为0-9.取值为0时代表关闭美颜效果.默认值:0,即关闭美颜效果.
        //whiteningLevel    美白等级： 取值为0-9.取值为0时代表关闭美白效果.默认值:0,即关闭美白效果.
        //ruddyLevel        红润等级： 取值为0-9.取值为0时代表关闭美白效果.默认值:0,即关闭美白效果.
        //
//        mLivePusher.setBeautyFilter(0, 0, 0, 0);
        netRun.LiveRoomDatails(room_id);
        joinGroup("弹幕");//加入弹幕群
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_menu1) {//菜单
            showpopu();
        } else if (id == R.id.iv_menu2) {//消息
            ll_input.setVisibility(View.VISIBLE);
            ll_btnmenu.setVisibility(View.GONE);
            et_input.setFocusable(true);
            et_input.setFocusableInTouchMode(true);
            et_input.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et_input.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et_input, 0);
        } else if (id == R.id.tv_send) {//发送
            String s = et_input.getText().toString();
            if (TextUtils.isEmpty(s)) {
                return;
            }
            TXsendTextMsg(s);
            et_input.setText("");
        } else if (id == R.id.iv_icon) {
            showpopu2();
        } else if (id == R.id.iv_menu3) {//美颜
            showbeautifypopu();
        } else if (id == R.id.iv_menu4) {//BGM
        } else if (id == R.id.iv_menu5) {//分享
        } else if (id == R.id.tv_number) {//观众
        } else if (id == R.id.tv_label) {// 阳光时刻榜
        } else if (id == R.id.ll_contribution) {// 贡献榜
        } else if (id == R.id.iv_menu6) {
            finish();
        }
    }

    private PopupWindow.OnDismissListener dism=new PopupWindow.OnDismissListener(){

        @Override
        public void onDismiss() {
            ll_btnmenu.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 加入弹幕群
     */
    private void joinGroup(String somereason) {
        TIMGroupManager.getInstance().applyJoinGroup(room_id, somereason, new TIMCallBack() {
            @java.lang.Override
            public void onError(int code, String desc) {
                //接口返回了错误码 code 和错误描述 desc，可用于原因
                //错误码 code 列表请参见错误码表
                Log.e("------------", "加群失败  code=" + code + "  desc=" + desc);
            }

            @java.lang.Override
            public void onSuccess() {
                Log.i("------------", "加群成功");
            }
        });
    }

    /**
     * 发送文字消息
     *
     * @param txt 内容
     */
    private void TXsendTextMsg(String txt) {
        //构造一条消息
        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(txt);
        //将 Elem 添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d("---------------", "添加元素失败");
            return;
        }
        if (bullet2Adapter != null) bullet2Adapter.addMsg(msg);
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                Log.d("---------------", "发送消息失败. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                rv_chat.smoothScrollToPosition(bullet2Adapter.getItemCount() - 1);
                Log.e("---------------", "发送消息成功");
            }
        });
    }

    /**
     * 用户资料弹窗
     */
    private void showpopu2() {
        if (liveRoomDatailsInfo == null || liveRoomDatailsInfo.live_member_info == null) return;

        postedPopu.setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        ll_btnmenu.setVisibility(View.GONE);
        postedPopu.setdate(new UserDataPopu.date() {
            @Override
            public void onItemClick(String type) {
                switch (type) {
                    case "1"://关注
//                        netRun.FollowRoom(room_id);
                        break;
                    case "2"://举报
//                        Intent intent = new Intent(HostActivity.this, RoomReportActivity.class);
//                        intent.putExtra("room_id", room_id);
//                        startActivity(intent);
                        break;
                }
            }
        });
        postedPopu.showAtLocation(iv_menu1, Gravity.BOTTOM, 0, 0);
        postedPopu.setOnDismissListener(dism);
    }


    /**
     * 菜单弹窗
     */
    private void showpopu() {
        HostMenuPopu postedPopu = new HostMenuPopu(HostActivity.this);
        postedPopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#88565251")));
        postedPopu.setdate(new HostMenuPopu.date() {
            @Override
            public void onItemClick(String year) {
                if (year.equals(getString(R.string.find_reminder218))){//反转
                    // 默认是前置摄像头
                    mLivePusher.switchCamera();
                }else if (year.equals(getString(R.string.find_reminder68))){//公告

                }else if (year.equals(getString(R.string.mute))){//静音

                }else if (year.equals(getString(R.string.find_reminder219))){//清屏

                }else if (year.equals(getString(R.string.find_reminder220))){//闪光灯
                    if (isflash){
                        isflash=false;
                    }else{
                        isflash=true;
                    }
                    if (!mLivePusher.turnOnFlashLight(isflash)) {
                        Toast.makeText(HostActivity.this,
                                "您的手机不支持", Toast.LENGTH_SHORT).show();
                    }
                }else if (year.equals(getString(R.string.message))){//消息

                }else if (year.equals(getString(R.string.find_reminder221))){//大字幕

                }
            }
        });
        ll_btnmenu.setVisibility(View.GONE);
        postedPopu.showAtLocation(iv_menu1, Gravity.BOTTOM, 0, 0);
        postedPopu.setOnDismissListener(dism);
    }

    /**
     * 美颜弹窗
     */
    private void showbeautifypopu() {
        BeautifyPopu postedPopu = new BeautifyPopu(HostActivity.this,beautifydatas);
        postedPopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#88565251")));
        postedPopu.setdate(new BeautifyPopu.date() {

            @Override
            public void onItemClick(BeautifyInfo beautifyInfo) {
                beautifydatas=beautifyInfo;
                //美颜
                mLivePusher.setBeautyFilter(beautifydatas.style, beautifydatas.beautyLevel, beautifydatas.whiteningLevel, beautifydatas.ruddyLevel);
                //滤镜
                if (beautifydatas.choosestyle==0){
                    mLivePusher.setSpecialRatio(0);
                    return;
                }
                Bitmap bmp=null;
                switch (beautifydatas.choosestyle){
                    case 1:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_qingliang);
                        break;
                    case 2:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_qingxin);
                        break;
                    case 3:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_rixi);
                        break;
                    case 4:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_weimei);
                        break;
                    case 5:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_huaijiu);
                        break;
                    case 6:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_landiao);
                        break;
                    case 7:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_langman);
                        break;
                    case 8:
                        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zb_filter_fennen);
                        break;
                }
                if (mLivePusher != null) {
                    mLivePusher.setFilter(bmp);
                }
                mLivePusher.setSpecialRatio(beautifydatas.specialratio);
            }
        });
        ll_btnmenu.setVisibility(View.GONE);
        postedPopu.showAtLocation(iv_menu1, Gravity.BOTTOM, 0, 0);
        postedPopu.setOnDismissListener(dism);
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.i("-----------------","onStop   ");
        mCaptureView.onPause();  // mCaptureView 是摄像头的图像渲染view
        mLivePusher.pausePusher(); // 通知 SDK 进入“后台推流模式”了
    }

    @Override
    public void onResume() {
        super.onResume();
        mCaptureView.onResume();     // mCaptureView 是摄像头的图像渲染view
        mLivePusher.resumePusher();  // 通知 SDK 重回前台推流
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("-----------------","onDestroy   ");
        stopRtmpPublish();
    }

    //结束推流，注意做好清理工作
    public void stopRtmpPublish() {
        Log.i("-----------------","结束推流   ");
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
    }
}
