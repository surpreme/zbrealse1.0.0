package livestream.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import livestream.mode.AreaList;
import livestream.view.AddressPopu;

/**
 * 直播准备
 * Created by Administrator on 2017/9/18.
 */
public class PrepareActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_return, iv_reverse;
    private LinearLayout ll_address, ll_choosechannel, ll_addlabel;
    private TextView tv_address, tv_start, tv_type, tv_tag;
    private EditText et_title;
    //直播
    private TXCloudVideoView mCaptureView;
    private TXLivePushConfig mLivePushConfig;
    private TXLivePusher mLivePusher;
    private String classid, label, province_info, city_info;
    private List<AreaList> areaLists;
    private APPSingleton application;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case start_push_id://开始直播
                    mdialog.dismiss();
                    if (msg.obj != null) {
                        Map<String, String> map = (Map<String, String>) msg.obj;
                        String push_url = map.get("push_url");
                        if (push_url != null) {
                            String room_id = map.get("room_id");
                            Intent intent2 = new Intent(PrepareActivity.this, HostActivity.class);
                            intent2.putExtra("push_url", push_url);
                            intent2.putExtra("room_id", room_id);
                            startActivity(intent2);
                            finish();
                        } else {
                            Toast.makeText(appSingleton, map.get("error"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case start_push_err:
                    mdialog.dismiss();
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case address_list_id:
                    //地区列表
                    if (msg.obj != null) {
                        areaLists = (List<AreaList>) msg.obj;
                    }
                    break;
                case address_list_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbprepare);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_return = (ImageView) findViewById(R.id.iv_return);
        iv_reverse = (ImageView) findViewById(R.id.iv_reverse);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_choosechannel = (LinearLayout) findViewById(R.id.ll_choosechannel);
        ll_addlabel = (LinearLayout) findViewById(R.id.ll_addlabel);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_tag = (TextView) findViewById(R.id.tv_tag);
        et_title = (EditText) findViewById(R.id.et_title);
        mCaptureView = (TXCloudVideoView) findViewById(R.id.video_view);
        initView();
    }

    @Override
    protected void initView() {
        application = (APPSingleton) getApplication();
        tv_address.setText(application.city);
        city_info = application.city;
        iv_return.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        iv_reverse.setOnClickListener(this);
        ll_choosechannel.setOnClickListener(this);
        ll_addlabel.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        //创建Pusher对象
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        //开启自动对焦
        mLivePushConfig.setTouchFocus(false);
        mLivePusher.setConfig(mLivePushConfig);
        //startPusher 的作用是告诉 SDK 音视频流要推到哪个推流URL上去。
        //        mLivePusher.startPusher(rtmpUrl);
        //startCameraPreview 则是将界面元素和Pusher对象关联起来，从而能够将手机摄像头采集到的画面渲染到屏幕上。
        mLivePusher.startCameraPreview(mCaptureView);
        //质量/是否开启 Qos 流量控制/是否允许动态分辨率
        mLivePusher.setVideoQuality(TXLiveConstants.RTMP_CHANNEL_TYPE_STANDARD, false, false);
        //style             磨皮风格：  0：光滑  1：自然  2：朦胧
        //beautyLevel       磨皮等级： 取值为0-9.取值为0时代表关闭美颜效果.默认值:0,即关闭美颜效果.
        //whiteningLevel    美白等级： 取值为0-9.取值为0时代表关闭美白效果.默认值:0,即关闭美白效果.
        //ruddyLevel        红润等级： 取值为0-9.取值为0时代表关闭美白效果.默认值:0,即关闭美白效果.
        //
//        mLivePusher.setBeautyFilter(1, 5, 5, 5);
        netRun.getAddressList(null);//地址
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_return) {
            finish();
        } else if (id == R.id.ll_address) {//选地址
            showpopu();
        } else if (id == R.id.iv_reverse) {//摄像头反转
//                ToggleCamera();
            // 默认是前置摄像头
            mLivePusher.switchCamera();
        } else if (id == R.id.ll_choosechannel) {//选择频道
            Intent intent1 = new Intent(PrepareActivity.this, ClassificationActivity.class);
            startActivityForResult(intent1, SETROOM_TYPE2);
        } else if (id == R.id.ll_addlabel) {//添加标签
            Intent intent = new Intent(PrepareActivity.this, ChooseLabelActivity.class);
            startActivityForResult(intent, SETROOM_LABLE);
        } else if (id == R.id.tv_start) {//开始直播
            final String title = et_title.getText().toString();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, getString(R.string.find_reminder8), Toast.LENGTH_SHORT).show();
                return;
            }
            if (classid == null) {
                Toast.makeText(appSingleton, getString(R.string.select_category), Toast.LENGTH_SHORT).show();
                return;
            }
            if (label == null) {
                Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder62), Toast.LENGTH_SHORT).show();
                return;
            }
            if (city_info == null) {
                Toast.makeText(appSingleton, getString(R.string.please_choose_address), Toast.LENGTH_SHORT).show();
                return;
            }
            mdialog.show();
            mLivePusher.snapshot(new TXLivePusher.ITXSnapshotListener() {
                @Override
                public void onSnapshot(Bitmap bmp) {
                    if (null != bmp) {
                        //获取到截图 bitmap
                        netRun.StartPush(title, saveCropPic(bmp), classid, label, city_info);
                    }
                }
            });
        }
    }

    /**
     * 显示弹窗
     */
    private void showpopu() {
        if (areaLists != null) {
            AddressPopu postedPopu = new AddressPopu(PrepareActivity.this, areaLists);
            postedPopu.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor("#00000000")));
            postedPopu.setdate(new AddressPopu.date() {
                @Override
                public void onItemClick(String address, String address2) {
                    province_info = address;
                    city_info = address2;
                    tv_address.setText(address2);
                }
            });
            postedPopu.showAsDropDown(ll_address, 0, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SETROOM_TYPE2://分类ID
                if (data != null) {
                    classid = data.getStringExtra("id");
                    tv_type.setText(data.getStringExtra("name"));
                }
                break;
            case SETROOM_LABLE://标签
                if (data != null) {
                    label = data.getStringExtra("label");
                    tv_tag.setText(label);
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("-----------------", "onStop   ");
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
        Log.i("-----------------", "onDestroy   ");
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
    }

    // 把裁剪后的图片保存到sdcard上
    private File saveCropPic(Bitmap bmp) {
        File tempFile = new File(Environment.getExternalStorageDirectory(),
                "cover.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }

}
