package com.aite.mainlibrary.activity.allqr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


public class QrCodeActivity extends BaseActivity implements QRCodeView.Delegate, View.OnClickListener {
    @BindView(R2.id.iv_back)
    ImageView iv_back;
    @BindView(R2.id.zxingview)
    ZXingView zXingView;
    private static final int REQUEST_CODE = 4538776;


    @Override
    protected int getLayoutResId() {
        return R.layout.qrcode_layout;
    }

    @Override
    protected void initView() {
        zXingView.setDelegate(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        zXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
//               getDatas(data);
            }


        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        PopWindowsUtils.getmInstance().showQRerrorPopupWindow(context,"二维码异常 请重新扫描二维码");
//        getDatas("2000000000130401");
//        PopWindowsUtils.getmInstance().showcenterPopupWindow(context);
//        PopWindowsUtils.getmInstance().showQRerrorPopupWindow(context, "test");

    }

    @Override
    protected void onStop() {
        super.onStop();
        zXingView.closeFlashlight();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingView.startCamera();
        zXingView.startSpotAndShowRect();
        zXingView.startSpot();
        zXingView.setType(BarcodeType.ALL, null);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {

    }

    private void showPopwindow() {
//        showeditnumberPopupWindow(context, R.layout.qrcode_layout, 1.0f);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        String tipText = zXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            //在这里加入了根据传感器光线暗的时候自动打开闪光灯
            if (!tipText.contains(ambientBrightnessTip)) {
                zXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
                zXingView.openFlashlight();
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                zXingView.getScanBoxView().setTipText(tipText);
            }
        }

    }

    /**
     * 处理打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
//        ToastTopUtils.showTopToasts(QrCodeActivity.this, "扫描二维码出错");

    }

    @Override
    public void onClick(View v) {

    }

}
