package chat.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.parse.JsonParse;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.ImagePreview;
import com.community.utils.ClutterUtils;
import com.tencent.TIMCallBack;
import com.tencent.TIMConnListener;
import com.tencent.TIMConversation;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMUser;
import com.tencent.TIMUserStatusListener;
import com.tencent.TIMValueCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import chat.adapter.ChatListAdapter;
import chat.model.CustomMsgInfo;
import livestream.adapter.Bullet2Adapter;

import static com.aite.a.base.Mark.ORDER_ADDRESS;
import static com.aite.a.base.Mark.ORDER_AFFIRM;

/**
 * 腾讯消息
 * Created by mayn on 2018/10/25.
 */
public class TXImUtils {
    public static ChatListAdapter adapter;//单聊消息适配器
    public static Bullet2Adapter bullet2Adapter;//弹幕消息适配器
    private static File SoundFile;//录音
    private static MediaRecorder recorder;
    private static AnimationDrawable animationDrawable;//语音播放动画
    public static NotificationManager manager;//通知
    private static int NOTIFICATION_FLAG = 1;//通知id

    /**
     * 添加消息监听
     */
    public static void initTxim(final Context mcontext) {
        boolean init = TIMManager.getInstance().init(mcontext);//通讯管理器初始化
        Log.e("---------------", "初始化" + init);
        TIMManager.getInstance().setLogPrintEnable(false);//不打印LOG
        TIMManager.getInstance().disableCrashReport();//禁用 Crash 上报
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            /**
             * 收到新消息回调
             * @param list 收到的新消息
             * @return 正常情况下，如果注册了多个listener, SDK会顺序回调到所有的listener。当碰到listener的回调返回true的时候，
             * 将终止继续回调后续的listener。
             */
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                TIMConversation conversation = list.get(0).getConversation();
                TIMMessage timMessage = list.get(0);
                TIMGroupMemberInfo senderGroupMemberProfile = timMessage.getSenderGroupMemberProfile();
                Log.i("----------------", "收到消息 " + list.get(0).getElement(0).getType().name() + "   用户 " + conversation.getPeer());
                if (senderGroupMemberProfile != null) {//群消息
                    if (bullet2Adapter != null) {
                        bullet2Adapter.addAllMsg(list);
                    }
                } else if (adapter != null && adapter.formuser.equals(conversation.getPeer())) {
                    adapter.addAllMsg(list);
                } else {//发通知
                    for (int i = 0; i < list.size(); i++) {
                        notifi(list.get(i), mcontext);
                    }
                }
                return false;
            }
        });

        //设置网络连接监听器，连接建立／断开时回调
        TIMManager.getInstance().setConnectionListener(new TIMConnListener() {//连接监听器
            @Override
            public void onConnected() {//连接建立
                Log.e("---------------", "连接建立");
            }

            @Override
            public void onDisconnected(int code, String desc) {//连接断开
                //接口返回了错误码 code 和错误描述 desc，可用于定位连接断开原因
                //错误码 code 含义请参见错误码表
                Log.e("---------------", "连接断开" + code + "   " + desc);
            }

            @Override
            public void onWifiNeedAuth(String s) {

            }
        });

        //设置用户状态变更监听器，在回调中进行相应的处理
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                //被踢下线
                Log.i("---------------", "被踢下线");
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要换票后重新登录
                Log.i("---------------", "票据过期，需要换票后重新登录");
            }
        });
    }

    /**
     * 解析适配
     *
     * @param timMessage 消息
     * @param layout     消息父布局
     * @param mcontext   上下文
     * @param msgmaxw    消息最大宽度
     * @param plater     消息播放器
     */
    public static void AnalysisMsg(TIMMessage timMessage, LinearLayout layout, final Context mcontext, int msgmaxw, final MediaPlayer plater) {
        layout.removeAllViews();
        for (int i = 0; i < timMessage.getElementCount(); ++i) {
            TIMElem elem = timMessage.getElement(i);
            //获取当前元素的类型
            TIMElemType elemType = elem.getType();
            if (elemType == TIMElemType.Text) {//处理文本消息
                TIMTextElem e = (TIMTextElem) elem;
                String text = e.getText();
                View v = View.inflate(mcontext, R.layout.item_msgtxt, null);
                TextView tv_txt = v.findViewById(R.id.tv_txt);
                tv_txt.setText(text);
                layout.addView(v);
            } else if (elemType == TIMElemType.Image) {//处理图片消息
                TIMImageElem e = (TIMImageElem) elem;
                Log.d("---------------", "getPath " + e.getPath());
                View v = View.inflate(mcontext, R.layout.item_msgimage, null);
                ImageView iv_image = v.findViewById(R.id.iv_image);
                float outWidth;
                float outHeight;
                final ArrayList<String> img=new ArrayList<>();
                if (e.getPath() != null && e.getPath().length() > 0) {
                    File file = new File(e.getPath());
                    Log.d("---------------", "file存在 " + file.exists());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getPath(), options);
                    outWidth = options.outWidth;
                    outHeight = options.outHeight;
                    Glide.with(mcontext).load(file).into(iv_image);
                    img.add(file.getPath());
                } else {
                    ArrayList<TIMImage> imageList = e.getImageList();
                    Log.d("---------------", "getUrl " + imageList.get(1).getUrl());
                    outWidth = imageList.get(1).getWidth();
                    outHeight = imageList.get(1).getHeight();
                    Glide.with(mcontext).load(imageList.get(1).getUrl()).into(iv_image);
                    img.add(imageList.get(1).getUrl());
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_image.getLayoutParams();
                if (outWidth > msgmaxw) {
                    layoutParams.width = msgmaxw;
                    layoutParams.height = (int) (msgmaxw * (outHeight / outWidth));
                } else {
                    layoutParams.width = (int) outWidth;
                    layoutParams.height = (int) outHeight;
                }
                iv_image.setLayoutParams(layoutParams);
                iv_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mcontext, ImagePreview.class);
                        intent.putStringArrayListExtra("photourl", img);
                        intent.putExtra("id", 0);
                        mcontext.startActivity(intent);
                    }
                });
                layout.addView(v);
            } else if (elemType == TIMElemType.Sound) {//处理语音消息
                final TIMSoundElem e = (TIMSoundElem) elem;
                View v = View.inflate(mcontext, R.layout.item_msgsound1, null);
                View v2 = View.inflate(mcontext, R.layout.item_msgsound2, null);
                final ImageView iv_voice = v.findViewById(R.id.iv_voice);
                TextView tv_voicesaze = v2.findViewById(R.id.tv_voicesaze);
                tv_voicesaze.setText(e.getDuration() + "s");

                if (timMessage.isSelf()) {
                    iv_voice.setImageResource(R.drawable.right_voice_pldy);
                    layout.addView(v2);
                    layout.addView(v);
                } else {
                    iv_voice.setImageResource(R.drawable.left_voice_pldy);
                    layout.addView(v);
                    layout.addView(v2);
                }
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File file1 = new File(e.getPath()==null?"":e.getPath());
                        if (file1.exists()) {
                            voicecli(plater, iv_voice, file1.getPath());
                        } else {
                            final File file = new File(Environment.getExternalStorageDirectory(),
                                    e.getUuid() + "Hxvoice.amr");
                            e.getSoundToFile(file.getPath(), new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    Log.d("---------------", "下载语音失败 error code" + i + "  " + s);
                                }

                                @Override
                                public void onSuccess() {
                                    Log.d("---------------", "下载语音成功");
                                    voicecli(plater, iv_voice, file.getPath());
                                }
                            });
                        }
                    }
                });
            } else if (elemType == TIMElemType.Custom) {//处理定制消息
                TIMCustomElem e = (TIMCustomElem) elem;
                try {
                    String data = new String(e.getData(), "UTF-8");
//                    String desc = e.getDesc();
                    Log.i("--------------", "定制消息 " + data);
                    JsonParse.getCustomMsgInfo(data);
                    final CustomMsgInfo goods = JsonParse.getCustomMsgInfo(data);
                    if (goods != null) {
                        if (goods.CustomType != null && goods.CustomType.equals("goods")) {
                            View v = View.inflate(mcontext, R.layout.item_msggoods, null);
                            ImageView iv_img = v.findViewById(R.id.iv_img);
                            TextView tv_name = v.findViewById(R.id.tv_name);
                            TextView tv_price = v.findViewById(R.id.tv_price);
                            TextView tv_sales = v.findViewById(R.id.tv_sales);
                            RelativeLayout rl_goods = v.findViewById(R.id.rl_goods);
                            Glide.with(mcontext).load(goods.goods.goods_image).into(iv_img);
                            tv_name.setText(goods.goods.goods_name);
                            tv_price.setText("￥" + goods.goods.goods_price);
                            tv_sales.setText(mcontext.getString(R.string.goodsdatails_reminder60) +
                                    goods.goods.goods_sales + mcontext.getString(R.string.goodsdatails_reminder61));
                            rl_goods.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(mcontext, ProductDetailsActivity.class);
                                    intent.putExtra("goods_id", goods.goods.goods_id);
                                    mcontext.startActivity(intent);
                                }
                            });
                            layout.addView(v);
                        } else if (goods.CustomType.equals("orderAddress")) {
                            View v = View.inflate(mcontext, R.layout.item_msgorder, null);
                            ImageView iv_img = v.findViewById(R.id.iv_img);
                            TextView tv_goodsname = v.findViewById(R.id.tv_goodsname);
                            TextView tv_desc = v.findViewById(R.id.tv_desc);
                            TextView tv_name = v.findViewById(R.id.tv_name);
                            TextView tv_phone = v.findViewById(R.id.tv_phone);
                            TextView tv_address = v.findViewById(R.id.tv_address);
                            TextView tv_confirmed = v.findViewById(R.id.tv_confirmed);
                            TextView tv_alter = v.findViewById(R.id.tv_alter);
                            TextView tv_affirm = v.findViewById(R.id.tv_affirm);
                            RelativeLayout rl_goods = v.findViewById(R.id.rl_goods);
                            Glide.with(mcontext).load(goods.order_info.goods_image).into(iv_img);
                            tv_goodsname.setText(goods.order_info.goods_name);
                            tv_desc.setText(mcontext.getString(R.string.gong) + goods.order_info.goods_total
                                    + mcontext.getString(R.string.a_goods) + "\t" + mcontext.getString(R.string.find_reminder335) + "￥" + goods.order_info.order_amount);
                            tv_name.setText(mcontext.getString(R.string.name_ng) + goods.order_info.reciver_name);
                            tv_phone.setText(mcontext.getString(R.string.phone_133) + goods.order_info.reciver_mobile);
                            tv_address.setText(mcontext.getString(R.string.address_sc) + goods.order_info.reciver_address);
                            if (goods.order_info.confirm_address == null || goods.order_info.confirm_address.equals("0")) {
                                tv_alter.setVisibility(View.VISIBLE);
                                tv_affirm.setVisibility(View.VISIBLE);
                                tv_confirmed.setVisibility(View.GONE);
                            } else {
                                tv_alter.setVisibility(View.GONE);
                                tv_affirm.setVisibility(View.GONE);
                                tv_confirmed.setVisibility(View.VISIBLE);
                            }
                            tv_alter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {//修改
                                    adapter.handler.sendMessage(adapter.handler.obtainMessage(ORDER_ADDRESS, goods.order_info.order_id));
                                }
                            });
                            tv_affirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {//确认
                                    adapter.handler.sendMessage(adapter.handler.obtainMessage(ORDER_AFFIRM, goods.order_info.order_id));
                                }
                            });
                            rl_goods.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {//商品
                                    Intent intent = new Intent(mcontext, ProductDetailsActivity.class);
                                    intent.putExtra("goods_id", goods.order_info.goods_id);
                                    mcontext.startActivity(intent);
                                }
                            });
                            layout.addView(v);
                        }
                    }
                } catch (UnsupportedEncodingException e1) {
                    Log.d("---------------", "定制消息转换错误 " + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 登陆
     *
     * @param identifier 用户名
     * @param userSig    用户帐号签名，由私钥加密获得
     * @param sdkAppId   sdkAppId，由腾讯分配
     */
    public static void TXimLogin(String identifier, String userSig, int sdkAppId) {
        // identifier 为用户名，userSig 为用户登录凭证
        Log.d("---------------", "IM登录");
        TIMUser user = new TIMUser();
        user.setIdentifier(identifier);
        //发起登录请求
        TIMManager.getInstance().login(sdkAppId, user, userSig,
                new TIMCallBack() {//回调接口

                    @Override
                    public void onSuccess() {//登录成功
                        Log.d("---------------", "登录成功");
                    }

                    @Override
                    public void onError(int code, String desc) {//登录失败
                        Log.d("---------------", "登录失败. code: " + code + " errmsg: " + desc);
                    }
                });
    }

    /**
     * 注销
     */
    public static void TXimLogout() {
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.d("---------------", "logout failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {//登出成功
                Log.d("---------------", "登出成功");
            }
        });
    }

    /**
     * 是否有录音权限
     *
     * @param mcontext
     * @return
     */
    @TargetApi(23)
    public static boolean getPersimmions(Context mcontext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mcontext.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // 无权限
                return false;
            } else {
                // 有权限
                return true;
            }
        } else {
            // 低于23
            return true;
        }
    }

    /**
     * 发送文字消息
     *
     * @param conversation 会话
     * @param txt          内容
     */
    public static void TXsendTextMsg(TIMConversation conversation, String txt) {
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
        if (adapter != null) adapter.addMsg(msg);
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                Log.d("---------------", "发送消息失败. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("---------------", "发送消息成功");
            }
        });
    }

    /**
     * 发送图片消息
     *
     * @param conversation 会话
     * @param f            路径
     */
    public static void TXsendImageMsg(TIMConversation conversation, String f) {
        //构造一条消息
        TIMMessage msg = new TIMMessage();
        //添加图片
        TIMImageElem elem = new TIMImageElem();
        elem.setPath(Environment.getExternalStorageDirectory() + f);
        //将 Elem 添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d("---------------", "添加元素失败");
            return;
        }
        if (adapter != null) adapter.addMsg(msg);
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d("---------------", "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("---------------", "发送消息成功");
            }
        });
    }

    /**
     * 发语音消息
     *
     * @param conversation 会话
     * @param f            路径
     * @param duration     时长
     */
    public static void TXsendSoundMsg(TIMConversation conversation, String f, int duration) {
        //构造一条消息
        TIMMessage msg = new TIMMessage();
        //添加语音
        TIMSoundElem elem = new TIMSoundElem();
        elem.setPath(f); //填写语音文件路径
        elem.setDuration(duration);  //填写语音时长
        //将 Elem 添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d("---------------", "添加元素失败");
            return;
        }
        if (adapter != null) adapter.addMsg(msg);
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.d("---------------", "发送消息失败. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("---------------", "发送消息成功");
            }
        });
    }

    /**
     * 发送商品消息
     *
     * @param conversation
     * @param goods_image
     * @param goods_name
     * @param goods_price
     * @param goods_sales
     * @param goods_id
     */
    public static void TXsendGoodsMsg(TIMConversation conversation, String goods_image, String goods_name,
                                      String goods_price, String goods_sales, String goods_id) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("CustomType", "goods");
            JSONObject goods = new JSONObject();
            goods.put("goods_image", goods_image);
            goods.put("goods_name", goods_name);
            goods.put("goods_price", goods_price);
            goods.put("goods_sales", goods_sales);
            goods.put("goods_id", goods_id);
            jsonObject.put("goods", goods);
        } catch (JSONException e) {
            Log.d("---------------", "json错误 " + e.getMessage());
            e.printStackTrace();
        }
        //构造一条消息
        TIMMessage msg = new TIMMessage();
        // xml 协议的自定义消息
        Log.i("--------------", "商品信息" + jsonObject.toString());

        String sampleXml = jsonObject.toString();
        //向 TIMMessage 中添加自定义内容
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(sampleXml.getBytes());      //自定义 byte[]
        elem.setDesc(goods_name); //自定义描述信息
        //将 elem 添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d("---------------", "添加消息失败");
            return;
        }
        if (adapter != null) adapter.addMsg(msg);
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.d("---------------", "发送消息失败. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("---------------", "发送消息成功");
            }
        });
    }

    /**
     * 开始录音
     */
    public static void startSoundRecording() {
        SoundFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + "Hxvoice.amr");
        recorder = new MediaRecorder();// 创建一个音频对象
        // 从麦克风源进行录音
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置输出格式
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        // 设置编码格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(SoundFile.getAbsolutePath());// 把音频输出到手机的某个文件中
        try {
            recorder.prepare();// 预加载
        } catch (Exception e) {
            Log.d("---------------", "预加载错误");
            e.printStackTrace();
        }
        recorder.start();// 开始
    }

    /**
     * 停止录音
     */
    public static void stopSoundRecording(TIMConversation conversation, MediaPlayer plater, Context mcontext) {
        // 录音停止
        if (recorder != null) {
            recorder.stop();// 停止
            recorder.release();// 释放资源
            recorder = null;
        }
        int length;
        try {
            plater.reset();
            plater.setDataSource(SoundFile.getPath());
            plater.prepare();
            length = (int) (plater.getDuration() / 1000.0);
            Log.i("----------------", "录音时长  " + length);
            if (length < 2) {
                Toast.makeText(mcontext, mcontext.getString(R.string.recording_short), Toast.LENGTH_SHORT).show();
                return;
            } else if (length > 60) {
                Toast.makeText(mcontext, mcontext.getString(R.string.recording_long), Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Log.i("----------------", "录音错误  " + e.getMessage());
            Toast.makeText(mcontext, mcontext.getString(R.string.recording_error), Toast.LENGTH_SHORT).show();
            return;
        }
        TXsendSoundMsg(conversation, SoundFile.getPath(), length);
    }

    /**
     * 语音
     *
     * @param img
     * @param localurl
     */
    public static void voicecli(MediaPlayer plater, ImageView img, String localurl) {
        if (plater.isPlaying()) {
            plater.stop();
            try {
                if (animationDrawable != null) {
                    animationDrawable.stop();
                    // 回到第一帧
                    animationDrawable.selectDrawable(0);
                }
            } catch (Exception e) {
                Log.i("-------------------", "停止语音错误 " + e.getMessage());
            }
        } else {
            plater.reset();
            animationDrawable = (AnimationDrawable) img.getDrawable();
            animationDrawable.start();
            try {
                plater.setDataSource(localurl);// 获得文件路径
                plater.prepare();// 预加载
                plater.start();// 播放
            } catch (Exception e) {
                Log.i("-------------------", "语音路径错误 " + e.getMessage());
                e.printStackTrace();
            }
            plater.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 停止动画
                    animationDrawable.stop();
                    // 回到第一帧
                    animationDrawable.selectDrawable(0);
                }
            });
        }
    }

    /**
     * 消息时间
     *
     * @param position
     * @param mcontext
     * @return
     */
    public static String MsgTime(int position, Context mcontext) {
        List<TIMMessage> msg = adapter.getMsg();
        long timestamp = msg.get(position).timestamp();
        if (position == 0) {
            return TimeUtil.QQFormatTime(timestamp * 1000, mcontext);
        } else {
            long onatimestamp = msg.get(position - 1).timestamp();
            if ((timestamp - onatimestamp) > 60) {
                return TimeUtil.QQFormatTime(timestamp * 1000, mcontext);
            } else {
                return null;
            }
        }
    }

    /**
     * 加入弹幕群
     */
    public static void joinGroup(String room_id, String somereason) {
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
     * 通知
     */
    private static void notifi(final TIMMessage mxg, Context mcontext) {
        long timestamp = mxg.timestamp();
        for (int i = 0; i < mxg.getElementCount(); ++i) {
            TIMElem elem = mxg.getElement(i);
            //获取当前元素的类型
            TIMElemType elemType = elem.getType();
            String text = "";
            if (elemType == TIMElemType.Text) {//处理文本消息
                TIMTextElem e = (TIMTextElem) elem;
                text = e.getText();
            } else if (elemType == TIMElemType.Image) {//处理图片消息
                text = mcontext.getString(R.string.goodsdatails_reminder58);
            } else if (elemType == TIMElemType.Sound) {//处理语音消息
                text = mcontext.getString(R.string.goodsdatails_reminder59);
            } else if (elemType == TIMElemType.Custom) {//定制消息
                TIMCustomElem e = (TIMCustomElem) elem;
                text = e.getDesc();
            }
            Notification myNotify = new Notification();
            myNotify.icon = R.mipmap.ic_launcher;
            myNotify.tickerText = text == null ? "" : text;
            myNotify.when = System.currentTimeMillis();
            // 默认声音
            myNotify.defaults = Notification.DEFAULT_SOUND;
            // 震动
            long[] vibrate = {0, 500, 0, 0};
            myNotify.vibrate = vibrate;
            // myNotify.defaults|=Notification.DEFAULT_VIBRATE;

            // myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
            final RemoteViews rv = new RemoteViews(APPSingleton.getContext().getPackageName(),
                    R.layout.hx_notification);
            TIMConversation conversation = mxg.getConversation();
            rv.setTextViewText(R.id.tv_name, conversation.getPeer() == null ? "" : conversation.getPeer());
            rv.setTextViewText(R.id.tv_centext, text == null ? "" : text);
            rv.setTextViewText(R.id.tv_time,
                    ClutterUtils.TimeStamp2Date(timestamp + "", "yyyy-MM-dd HH:mm"));
            rv.setImageViewResource(R.id.iv_title, R.drawable.ic_launcher);
            myNotify.contentView = rv;
            try {
//                Intent notificationIntent = new Intent(APPSingleton.getContext(), DialogueActivity.class);
//                notificationIntent.putExtra("to_member_id",
//                        noticeMenberInfo.member_id);
//                PendingIntent contentItent = PendingIntent.getActivity(APPSingleton.getContext(),
//                        NOTIFICATION_FLAG, notificationIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//                // 设置跳转
//                myNotify.contentIntent = contentItent;
                manager.notify(NOTIFICATION_FLAG, myNotify);
            } catch (Exception e) {
                System.out.println("---------------设置Notification失败");
            }

        }
        // NOTIFICATION_FLAG++;
    }
}
