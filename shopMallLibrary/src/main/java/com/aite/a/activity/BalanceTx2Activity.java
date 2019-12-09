package com.aite.a.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 提现完成
 * Created by mayn on 2018/11/10.
 */
public class BalanceTx2Activity extends BaseActivity implements View.OnClickListener{
    private TextView _tv_name,tv_money,tv_bank,tv_confirm;
    private ImageView _iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_balancetx2);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name=findViewById(R.id._tv_name);
        tv_money=findViewById(R.id.tv_money);
        tv_bank=findViewById(R.id.tv_bank);
        tv_confirm=findViewById(R.id.tv_confirm);
        _iv_back=findViewById(R.id._iv_back);
        initView();
    }

    @Override
    protected void initView() {
        String money=getIntent().getStringExtra("money");
        String bank=getIntent().getStringExtra("bank");
        tv_money.setText(money);
        tv_bank.setText(bank);
        _tv_name.setText(getString(R.string.find_reminder333));
        _iv_back.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_confirm://确认提交
//                finish();
//                break;
//        }
        if(view.getId()==R.id._iv_back){
            finish();
        }else if(view.getId()==R.id.tv_confirm){
            finish();
        }

    }

    @Override
    public void ReGetData() {

    }
}
