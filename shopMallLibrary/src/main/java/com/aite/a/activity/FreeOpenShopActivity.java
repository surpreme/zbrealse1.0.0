package com.aite.a.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 我要入驻
 *
 * @author Administrator
 */
public class FreeOpenShopActivity extends BaseActivity implements
        OnClickListener {
    private Button bt_personal, bt_enterprise, bt_property, bt_supplier;
    private TextView _tv_name;
    private ImageView _iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeopenshop);
        findViewById();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        bt_personal = (Button) findViewById(R.id.bt_personal);
        bt_enterprise = (Button) findViewById(R.id.bt_enterprise);
        bt_property = (Button) findViewById(R.id.bt_property);
        bt_supplier = (Button) findViewById(R.id.bt_supplier);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);

        bt_personal.setOnClickListener(this);
        bt_enterprise.setOnClickListener(this);
        bt_property.setOnClickListener(this);
        bt_supplier.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getI18n(R.string.agreement3));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_personal:
//                Intent intent2 = new Intent(FreeOpenShopActivity.this,
//                        MerchantsinActivity.class);
//                intent2.putExtra("titel", getString(R.string.individual_settled));
//                startActivity(intent2);
//                break;
//            case R.id.bt_enterprise:
//                Intent intent3 = new Intent(FreeOpenShopActivity.this,
//                        FirmruzhuActivity.class);
//                intent3.putExtra("titel", getString(R.string.join_merchant7));
//                startActivity(intent3);
//                break;
//            case R.id.bt_property:
//                Intent intent4 = new Intent(FreeOpenShopActivity.this,
//                        FirmruzhuActivity.class);
//                intent4.putExtra("titel", getString(R.string.join_merchant6));
//                startActivity(intent4);
//                break;
//            case R.id.bt_supplier:
//                Intent intent5 = new Intent(FreeOpenShopActivity.this,
//                        FirmruzhuActivity.class);
//                intent5.putExtra("titel", getString(R.string.join_merchant8));
//                startActivity(intent5);
//                break;
//            case R.id._iv_back:
//                finish();
//                break;
//        }
        if (v.getId() == R.id.bt_personal) {
            Intent intent2 = new Intent(FreeOpenShopActivity.this,
                    MerchantsinActivity.class);
            intent2.putExtra("titel", getString(R.string.individual_settled));
            startActivity(intent2);
        } else if (v.getId() == R.id.bt_enterprise) {
            Intent intent3 = new Intent(FreeOpenShopActivity.this,
                    FirmruzhuActivity.class);
            intent3.putExtra("titel", getString(R.string.join_merchant7));
            startActivity(intent3);
        } else if (v.getId() == R.id.bt_property) {
            Intent intent4 = new Intent(FreeOpenShopActivity.this,
                    FirmruzhuActivity.class);
            intent4.putExtra("titel", getString(R.string.join_merchant6));
            startActivity(intent4);
        } else if (v.getId() == R.id.bt_supplier) {
            Intent intent5 = new Intent(FreeOpenShopActivity.this,
                    FirmruzhuActivity.class);
            intent5.putExtra("titel", getString(R.string.join_merchant8));
            startActivity(intent5);
        } else if (v.getId() == R.id._iv_back) {

        }
    }
}
