package livestream.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import chat.utils.TXImUtils;
import livestream.adapter.Bullet2Adapter;
import livestream.mode.LiveRoomDatailsInfo;
import livestream.view.GiftPopu;
import livestream.view.PrivateLetterPopu;
import livestream.view.SpectatorListPopu;
import livestream.view.UserDataPopu;

/**
 * 观众
 * Created by Administrator on 2017/9/28.
 */
public class AudienceActivity extends Activity implements View.OnClickListener, Mark {
    private ImageView iv_menu1, iv_menu2, iv_menu3, iv_menu4, iv_menu5, iv_menu6, iv_face, iv_gif;
    private CircleImageView iv_icon;
    private TextView tv_name, tv_phone, tv_number, tv_label, tv_contribution, tv_attention, tv_send, tv_giftname;
    private RecyclerView rv_spectator, rv_chat;
    private LinearLayout ll_btnmenu, ll_gift;
    private RelativeLayout ll_input, lin;
    private EditText et_input;
    private ImageView iv_fail;
    private NetRun netRun;
    private LiveRoomDatailsInfo liveRoomDatailsInfo;
    //直播
    private TXCloudVideoView mPlayerView;
    private TXLivePlayer mLivePlayer;
    private String flvUrl, room_id;
    //弹幕适配
//    private BulletAdapter bulletAdapter;
    private Bullet2Adapter bullet2Adapter;
    private TIMConversation conversation;//会话
    //是否隐藏软键盘
    private boolean isgone = false;
    //个人信息弹窗
    private UserDataPopu postedPopu;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case liveroom_datails_id://房间信息
                    if (msg.obj != null) {
                        liveRoomDatailsInfo = (LiveRoomDatailsInfo) msg.obj;
                        Glide.with(AudienceActivity.this).load(liveRoomDatailsInfo.live_member_info.member_avatar).into(iv_icon);
                        tv_name.setText(liveRoomDatailsInfo.live_member_info.member_truename);
                        tv_phone.setText(liveRoomDatailsInfo.live_member_info.room_number);
                        tv_contribution.setText(liveRoomDatailsInfo.live_member_info.fans_num);
                        tv_label.setText(liveRoomDatailsInfo.live_room_info.title);
                        postedPopu = new UserDataPopu(AudienceActivity.this, liveRoomDatailsInfo.live_member_info);
                        if (liveRoomDatailsInfo.member_info.is_follow.equals("1")) {
                            tv_attention.setVisibility(View.GONE);
                            postedPopu.setAttention();
                        } else {
                            tv_attention.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case liveroom_datails_err:
                    Toast.makeText(AudienceActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case follow_room_id://直播关注
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            postedPopu.setAttention();
                            tv_attention.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(AudienceActivity.this, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case follow_room_err:
                    Toast.makeText(AudienceActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    //键盘显示状态
                    isgone = true;
                    break;
                case 102:
                    //用户资料
                    showpopu2();
                    break;
                case MESSAGE_TOBOTTOM://滚动到底部
//                    rv_list.scrollToPosition(chatListAdapter.getItemCount() - 1);
                    if (bullet2Adapter != null) {
                        rv_chat.smoothScrollToPosition(bullet2Adapter.getItemCount() - 1);
                    }
                    break;
                case GIFT_GIF:
                    ll_gift.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_zb_audience);
        findViewById();
    }

    protected void findViewById() {
        iv_icon = (CircleImageView) findViewById(R.id.iv_icon);
        iv_menu1 = (ImageView) findViewById(R.id.iv_menu1);
        iv_menu2 = (ImageView) findViewById(R.id.iv_menu2);
        iv_menu3 = (ImageView) findViewById(R.id.iv_menu3);
        iv_menu4 = (ImageView) findViewById(R.id.iv_menu4);
        iv_menu5 = (ImageView) findViewById(R.id.iv_menu5);
        iv_menu6 = (ImageView) findViewById(R.id.iv_menu6);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_attention = (TextView) findViewById(R.id.tv_attention);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_label = (TextView) findViewById(R.id.tv_label);
        tv_contribution = (TextView) findViewById(R.id.tv_contribution);
        rv_spectator = (RecyclerView) findViewById(R.id.rv_spectator);
        rv_chat = (RecyclerView) findViewById(R.id.rv_chat);
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        ll_btnmenu = (LinearLayout) findViewById(R.id.ll_btnmenu);
        ll_input = (RelativeLayout) findViewById(R.id.ll_input);
        et_input = (EditText) findViewById(R.id.et_input);
        iv_face = (ImageView) findViewById(R.id.iv_face);
        tv_send = (TextView) findViewById(R.id.tv_send);
        lin = (RelativeLayout) findViewById(R.id.lin);
        iv_fail = (ImageView) findViewById(R.id.iv_fail);
        ll_gift = findViewById(R.id.ll_gift);
        tv_giftname = findViewById(R.id.tv_giftname);
        iv_gif = findViewById(R.id.iv_gif);
        initView();
    }

    protected void initView() {
        tv_attention.setOnClickListener(this);
        iv_menu1.setOnClickListener(this);
        iv_menu2.setOnClickListener(this);
        iv_menu3.setOnClickListener(this);
        iv_menu4.setOnClickListener(this);
        iv_menu5.setOnClickListener(this);
        iv_menu6.setOnClickListener(this);
        tv_number.setOnClickListener(this);
        iv_face.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        tv_label.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        rv_spectator.setLayoutManager(new LinearLayoutManager(this));
        rv_chat.setLayoutManager(new LinearLayoutManager(this));
//        bulletAdapter = new BulletAdapter(this, handler);
        bullet2Adapter = new Bullet2Adapter(this, new ArrayList<TIMMessage>(), handler);
        rv_chat.setAdapter(bullet2Adapter);
        TXImUtils.bullet2Adapter = bullet2Adapter;
//        bulletAdapter.additem(new BulletInfo("5", "", getString(R.string.find_reminder209), getString(R.string.find_reminder210)));

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
        flvUrl = getIntent().getStringExtra("flvUrl");
        room_id = getIntent().getStringExtra("room_id");
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
        //创建player对象
        mLivePlayer = new TXLivePlayer(this);
        //关键player对象与界面view
        mLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐FLV
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
                //事件通知
                Log.i("-------------------", "事件通知 " + i);
                if (i == -2301) {
                    iv_fail.setVisibility(View.VISIBLE);
                }
//                if (bundle!=null){
//                    String string = "Bundle{ ";
//                    for (String key : bundle.keySet()) {
//                        string += " " + key + " => " + bundle.get(key) + ";";
//                    }
//                    string += " }Bundle";
//                    Log.i("-------------------","事件通知 "+string);
//                }
            }

            @Override
            public void onNetStatus(Bundle bundle) {
                //质量反馈

            }
        });
        netRun.LiveRoomDatails(room_id);
        joinGroup("弹幕");//加入弹幕群
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_menu1) {//发弹幕
            ll_input.setVisibility(View.VISIBLE);
            ll_btnmenu.setVisibility(View.GONE);
            et_input.setFocusable(true);
            et_input.setFocusableInTouchMode(true);
            et_input.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et_input.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et_input, 0);
        } else if (id == R.id.iv_menu2) {//私信
            showpopu();
        } else if (id == R.id.iv_menu3) {//点歌
        } else if (id == R.id.iv_menu4) {//礼物
            showpopu4();
        } else if (id == R.id.iv_menu5) {//分享
        } else if (id == R.id.tv_number) {//观众
            showpopu3();
        } else if (id == R.id.tv_label) {// 阳光时刻榜
        } else if (id == R.id.ll_contribution) {// 贡献榜
        } else if (id == R.id.iv_menu6) {
            finish();
        } else if (id == R.id.iv_face) {//表情
        } else if (id == R.id.tv_send) {//发送
            String s = et_input.getText().toString();
            if (TextUtils.isEmpty(s)) {
                return;
            }
            TXsendTextMsg(s);
            et_input.setText("");
        } else if (id == R.id.iv_icon) {//头像
            showpopu2();
        } else if (id == R.id.tv_attention) {//关注
            netRun.FollowRoom(room_id);
        }
    }

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
     * 私信弹窗
     */
    private void showpopu() {
        PrivateLetterPopu postedPopu = new PrivateLetterPopu(AudienceActivity.this);
        postedPopu.setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        ll_btnmenu.setVisibility(View.GONE);
        postedPopu.showAtLocation(iv_menu1, Gravity.BOTTOM, 0, 0);
        postedPopu.setOnDismissListener(dism);
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
                        netRun.FollowRoom(room_id);
                        break;
                    case "2"://举报
                        Intent intent = new Intent(AudienceActivity.this, RoomReportActivity.class);
                        intent.putExtra("room_id", room_id);
                        startActivity(intent);
                        break;
                }
            }
        });
        postedPopu.showAtLocation(iv_menu1, Gravity.BOTTOM, 0, 0);
        postedPopu.setOnDismissListener(dism);
    }

    /**
     * 在线观众弹窗
     */
    private void showpopu3() {
        SpectatorListPopu postedPopu = new SpectatorListPopu(AudienceActivity.this, handler);
        postedPopu.setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        ll_btnmenu.setVisibility(View.GONE);
        postedPopu.showAtLocation(iv_menu1, Gravity.BOTTOM, 0, 0);
        postedPopu.setOnDismissListener(dism);
    }

    /**
     * 送礼
     */
    private void showpopu4() {
        GiftPopu giftPopu = new GiftPopu(AudienceActivity.this, room_id);
        giftPopu.setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        ll_btnmenu.setVisibility(View.GONE);
        giftPopu.setdate(new GiftPopu.date() {
            @Override
            public void onItemClick(String gif, String giftname, String number) {
                ll_gift.setVisibility(View.VISIBLE);
                tv_giftname.setText(liveRoomDatailsInfo.member_info.member_truename + "\n" + giftname + "x" + number);
                Glide.with(AudienceActivity.this).load(gif).into(iv_gif);
                handler.sendEmptyMessageDelayed(GIFT_GIF,6000);
            }
        });
        giftPopu.showAtLocation(iv_menu1, Gravity.BOTTOM, 0, 0);
        giftPopu.setOnDismissListener(dism);
    }

    private PopupWindow.OnDismissListener dism = new PopupWindow.OnDismissListener() {

        @Override
        public void onDismiss() {
            ll_btnmenu.setVisibility(View.VISIBLE);
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
                    }

                    @Override
                    public void onSuccess(List<TIMMessage> msgs) {//获取消息成功
                        Log.d("---------------", "获取消息成功");

//                        for (TIMMessage msg : msgs) {
//                            //可以通过timestamp()获得消息的时间戳, isSelf()是否为自己发送的消息
//                            Log.e("---------------", "get msg: " + msg.timestamp() + " self: " + msg.isSelf() + " seq: " + msg.msg.seq());
//                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        mPlayerView.onDestroy();
    }

}
