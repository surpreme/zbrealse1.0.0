package com.aite.a.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;

/**
 * 我的店铺详细信息
 *
 * @author CAOYOU
 */
public class MyStoreDataActivity extends BaseActivity implements OnClickListener {
    private TextView _tx_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_store_data);
        findViewById();
        initView();
        initData();
    }

    @Override
    public void ReGetData() {
    }

    @Override
    protected void findViewById() {
        iv_back = (ImageView) findViewById(R.id._iv_back);
        _tx_right = (TextView) findViewById(R.id._tx_right);
        tv_title_name = (TextView) findViewById(R.id._tv_name);
        tv_title_name.setText(getI18n(R.string.shop_data));
        _tx_right.setText(getI18n(R.string.confirm));
        _tx_right.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//        }
        if (v.getId() == R.id._iv_back) {
            finish();
        }
    }

}
