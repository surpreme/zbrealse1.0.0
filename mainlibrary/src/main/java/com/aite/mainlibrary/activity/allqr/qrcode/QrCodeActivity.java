package com.aite.mainlibrary.activity.allqr.qrcode;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.net.URI;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class QrCodeActivity extends BaseActivity<QrCodeContract.View, QrCodePresenter> implements QrCodeContract.View, QRCodeView.Delegate, View.OnClickListener {


    @BindView(R2.id.zxingview)
    ZXingView zXingView;
    private static final int REQUEST_CODE = 453876;

    @Override
    protected int getLayoutResId() {
        return R.layout.qrcode_layout;
    }

    @Override
    protected void initView() {
        initToolbar("扫码");
        zXingView.setDelegate(this);

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
    protected void onResume() {
        super.onResume();
        zXingView.startCamera();
        zXingView.startSpotAndShowRect();
        zXingView.startSpot();
        zXingView.setType(BarcodeType.ALL, null);
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

    private HttpParams initParams(String ID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("id", ID);
        httpParams.put("type", getIntent().getStringExtra("type"));
        return httpParams;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        LogUtils.d(result);
//        Uri uri = Uri.parse(result);
//        String id = uri.getQueryParameter("id");
        showToast("扫码成功", Gravity.TOP);
        mPresenter.sureBook(initParams(getUrlKey(result, "id")));

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

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onSureSuccess(Object msg) {
        LogUtils.d(msg.toString());
        onBackPressed();

    }

}
