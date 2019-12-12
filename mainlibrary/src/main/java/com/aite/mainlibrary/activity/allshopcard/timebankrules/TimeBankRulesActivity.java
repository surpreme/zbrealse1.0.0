package com.aite.mainlibrary.activity.allshopcard.timebankrules;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.numbershop.NumberShopActivity;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.view.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TimeBankRulesActivity extends BaseActivity<TimeBankRulesContract.View, TimeBankRulesPresenter> implements TimeBankRulesContract.View {

    @BindView(R2.id.gotimeshop_btn)
    Button gotimeshopBtn;
    @BindView(R2.id.webview)
    WebView webview;

    @Override
    protected int getLayoutResId() {
        return R.layout.time_bank_rules;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setColor(context, getResources().getColor(com.lzy.basemodule.R.color.red));
        initToolbar("积分规则", "...", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        gotimeshopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NumberShopActivity.class);
            }
        });


    }

    @Override
    protected void initDatas() {

        mPresenter.getRulesSuccess(initKeyParams());
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onGetRulesSuccess(Object msg) {
        webview.loadUrl(msg.toString());
        webview.setWebViewClient(new WebViewClient());

    }
}
