package com.aite.a.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 积分兑换成功
 *
 * @author Administrator
 */
public class IntegralWinActivity extends BaseActivity implements
        OnClickListener {
    private TextView _tv_name, tv_jf, tv_jx, tv_ck;
    private ImageView _iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralwin);
        findViewById();
    }


    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_jf = (TextView) findViewById(R.id.tv_jf);
        tv_jx = (TextView) findViewById(R.id.tv_jx);
        tv_ck = (TextView) findViewById(R.id.tv_ck);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.evaluation_tips56));
        _iv_back.setOnClickListener(this);
        tv_jx.setOnClickListener(this);
        tv_ck.setOnClickListener(this);
        String stringExtra = getIntent().getStringExtra("jf");
        tv_jf.setText(stringExtra);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_jx:
//			// 继续
//			finish();
//			break;
//		case R.id.tv_ck:
//			// 查看
//			Intent intent2 = new Intent(IntegralWinActivity.this,ExchangeRecordActivity.class);
//			startActivity(intent2);
//			finish();
//			break;
//		}
        if (v.getId() == R.id._iv_back) {
            finish();

        } else if (v.getId() == R.id.tv_jx) {
            // 继续
            finish();
        } else if (v.getId() == R.id.tv_ck) {
            // 查看
            Intent intent2 = new Intent(IntegralWinActivity.this, ExchangeRecordActivity.class);
            startActivity(intent2);
            finish();
        }
    }
}
