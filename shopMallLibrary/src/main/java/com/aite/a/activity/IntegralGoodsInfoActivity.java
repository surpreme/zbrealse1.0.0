package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegralGoodsInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 兑换商品详情
 *
 * @author Administrator
 */
public class IntegralGoodsInfoActivity extends BaseActivity implements
        OnClickListener {
    private TextView _tv_name, tv_name, tv_lastprice, tv_integral, tv_number,
            tv_time, bt_exchange, tv_limit;
    private ImageView _iv_back, iv_goodsimg;
    private EditText et_number;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private IntegralGoodsInfo integralGoodsInfo;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case integral_giftinfo_id:
                    if (msg.obj != null) {
                        integralGoodsInfo = (IntegralGoodsInfo) msg.obj;
                        bitmapUtils.display(iv_goodsimg,
                                integralGoodsInfo.prodinfo.pgoods_image);
                        tv_name.setText(integralGoodsInfo.prodinfo.pgoods_name);
                        tv_lastprice.setText(getString(R.string.evaluation_tips37)+" ￥"
                                + integralGoodsInfo.prodinfo.pgoods_price);
                        tv_integral.setText(getString(R.string.evaluation_tips38)
                                + integralGoodsInfo.prodinfo.pgoods_points + getString(R.string.integrall));
                        tv_number.setText(getString(R.string.evaluation_tips39)
                                + integralGoodsInfo.prodinfo.pgoods_storage);
                        if (!integralGoodsInfo.prodinfo.pgoods_limitnum.equals("0")) {
                            tv_number.setVisibility(View.VISIBLE);
                            tv_number.setText(getString(R.string.evaluation_tips40)
                                    + integralGoodsInfo.prodinfo.pgoods_limitnum + getString(R.string.evaluation_tips41));
                        } else {
                            tv_number.setVisibility(View.GONE);
                        }

                        // tv_time.setText("本次兑换时间还剩 : "
                        // + integralGoodsInfo.prodinfo.pgoods_storage);
                    }
                    break;
                case integral_giftinfo_err:
                    Toast.makeText(IntegralGoodsInfoActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case addIntegralgift_cart_id://添加积分购物车
                    if (msg.obj != null) {
                        Intent intent2 = new Intent(IntegralGoodsInfoActivity.this, IntegralCartActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                    break;
                case addIntegralgift_cart_err:
                    Toast.makeText(IntegralGoodsInfoActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralgoodsinfo);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_lastprice = (TextView) findViewById(R.id.tv_lastprice);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_time = (TextView) findViewById(R.id.tv_time);
        bt_exchange = (TextView) findViewById(R.id.bt_exchange);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_goodsimg = (ImageView) findViewById(R.id.iv_goodsimg);
        et_number = (EditText) findViewById(R.id.et_number);
        tv_limit = (TextView) findViewById(R.id.tv_limit);
        initView();
    }

    @Override
    protected void initView() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_goodsimg
                .getLayoutParams();
        layoutParams.height = width;
        iv_goodsimg.setLayoutParams(layoutParams);

        _tv_name.setText(getString(R.string.evaluation_tips42));
        _iv_back.setOnClickListener(this);
        bt_exchange.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("id");
        netRun.IntegralGiftInfo(id);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.bt_exchange:
//                // 确认兑换
//                String quantity = et_number.getText().toString();
//                if (TextUtils.isEmpty(quantity) || quantity.equals("")) {
//                    quantity = "1";
//                }
//                netRun.AddIntegralGiftCart(integralGoodsInfo.prodinfo.pgoods_id, quantity);
//                break;
//        }
        if(v.getId()==R.id._iv_back){
            finish();
        }else if(v.getId()==R.id.bt_exchange){
            // 确认兑换
            String quantity = et_number.getText().toString();
            if (TextUtils.isEmpty(quantity) || quantity.equals("")) {
                quantity = "1";
            }
            netRun.AddIntegralGiftCart(integralGoodsInfo.prodinfo.pgoods_id, quantity);
        }

    }
}
