package com.aite.a.zxing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import com.aiteshangcheng.a.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import courier.BatchOperationActivity;
import courier.CourierHomeTabActivity;
import jd.page.JDHomePage;

/**
 * Initial the camera
 *
 * @author xiangzhihong
 * @ClassName: CaptureActivity
 * @Description: TODO
 */
public class CaptureActivity extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private boolean isRepetition = false;//是否重复扫描
    private int type=0;//0 首页,1入库,2批量
    private ArrayList<String> data;//批量数据
    private TextView mTitle, tv_wc;
    private ImageView mGoHome;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qr_code_scan);

        CameraManager.init(getApplication());
        initControl();

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    private void initControl() {
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        mTitle = (TextView) findViewById(R.id.details_textview_title);
        tv_wc = (TextView) findViewById(R.id.tv_wc);

        mGoHome = (ImageView) findViewById(R.id.details_imageview_gohome);
        isRepetition = getIntent().getBooleanExtra("isRepetition", false);
        type=getIntent().getIntExtra("type",0);
        if (isRepetition) {
            data=new ArrayList();
            mTitle.setText("批量入库");
            tv_wc.setVisibility(View.VISIBLE);
        } else {
            tv_wc.setVisibility(View.GONE);
            mTitle.setText(R.string.scan_title);
        }
        mGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_wc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//批量入库
                Intent intent = new Intent(CaptureActivity.this, BatchOperationActivity.class);
                intent.putStringArrayListExtra("result",data);
                setResult(type,intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private Handler hd=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10011:
                    handler.restartPreviewAndDecode();
                    break;
            }
        }
    };

    /**
     * 结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        String resultString = result.getText();
        if (isRepetition) {//批量
            boolean isadd=true;
            for (int i=0;i<data.size();i++){
                if (resultString.equals(data.get(i))){
                    Toast.makeText(CaptureActivity.this, "请勿重复添加相同订单", Toast.LENGTH_SHORT).show();
                    isadd=false;
                    break;
                }
            }
            if (isadd){
                data.add(resultString);
            }
            hd.sendEmptyMessageDelayed(10011,1000);
        } else {//单个
            if (type==0){//首页
                Intent intent = new Intent(CaptureActivity.this, JDHomePage.class);
                intent.putExtra("result",resultString);
                setResult(type,intent);
                finish();
            }else if (type==1){//入库
                Intent intent = new Intent(CaptureActivity.this, CourierHomeTabActivity.class);
                intent.putExtra("result",resultString);
                setResult(type,intent);
                finish();
            }
        }
        // 直接跳商品详情
//		if (resultString.matches(".*www.macaushibao.com/mall.*")) {
//			String jieguo = resultString.substring(
//					resultString.indexOf("goods-") + 6,
//					resultString.indexOf(".html"));
//			Intent intent = new Intent(this, GoodsDatailsActivity.class);
//			intent.putExtra("goods_id", jieguo);
//			startActivity(intent);
//			finish();
//			return;
//		}
        if (resultString.equals("")) {
            Toast.makeText(CaptureActivity.this,
                    "扫描失败!", Toast.LENGTH_SHORT)
                    .show();
        }
//        else {
//            Intent it = new Intent(Intent.ACTION_VIEW);
//            it.setData(Uri.parse(resultString));
//            it = Intent.createChooser(it, null);
//            startActivity(it);
//        }
//        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 扫描正确后的震动声音,如果感觉apk大了,可以删除
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}
