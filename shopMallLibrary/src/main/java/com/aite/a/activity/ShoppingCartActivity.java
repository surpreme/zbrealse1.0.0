package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.li.adapter.GuessLikeAdapter;
import com.aite.a.activity.li.bean.GuessLikeBean;
import com.aite.a.adapter.ShoppingCartAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.ShoppingCartInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.aiteshangcheng.a.R;
import com.blankj.rxbus.RxBus;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 * Created by mayn on 2018/11/10.
 */
public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title, tv_management, tv_settlement, tv_price, tv_gl;
    private ListView lv_list;
    private ImageView iv_all;
    private LinearLayout ll_pickall, ll_null;
    private ShoppingCartInfo shoppingCartInfo;
    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean ispickall = false;//是否全选
    private NetRun netRun;
    private List<GuessLikeBean.DatasBean> mDatas = new ArrayList<>();
    private RecyclerView recycler_view;
    private Button btn_gobuy;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case getcart_list2_id://购物车
                    if (msg.obj != null) {
                        shoppingCartInfo = (ShoppingCartInfo) msg.obj;
                        if (shoppingCartInfo.cart_list == null || shoppingCartInfo.cart_list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                            tv_management.setVisibility(View.GONE);
                            tv_title.setText(getString(R.string.my_shopping_cart));
                        } else {
                            ll_null.setVisibility(View.GONE);
                            tv_management.setVisibility(View.VISIBLE);
                            if (shoppingCartAdapter == null) {
                                shoppingCartAdapter = new ShoppingCartAdapter(ShoppingCartActivity.this, shoppingCartInfo.cart_list, handler);
                                lv_list.setAdapter(shoppingCartAdapter);
                            } else {
                                shoppingCartAdapter.refreshData(shoppingCartInfo.cart_list);
                            }
                            int num = 0;
                            for (int i = 0; i < shoppingCartInfo.cart_list.size(); i++) {
                                num += shoppingCartInfo.cart_list.get(i).goods_list.size();
                            }
                            tv_title.setText(getString(R.string.my_shopping_cart) + "（" + num + "）");
                            shoppingCartAdapter.getTotalPrice();
                        }
                    }
                    break;
                case getcart_list2_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case up_cart_num_id://商品修改数量
                    if (msg.obj != null) {
                        Map<String, String> map = (Map<String, String>) msg.obj;
                        String error = map.get("error");
                        if (error == null) {
                            shoppingCartAdapter.setNumber(map);
                        } else {
                            Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case up_cart_num_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case drop_cart_id://删除
                    if (msg.obj.equals("1")) {
                        netRun.getCartList2();
                        Toast.makeText(appSingleton, getString(R.string.act_del_success), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(appSingleton, getString(R.string.act_del_fail), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case drop_cart_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case TOTAL_PRICE://选中价格
                    if (msg.obj != null) {
                        Map<String, Object> map = (Map<String, Object>) msg.obj;
                        float price = (float) map.get("price");
                        int num = (int) map.get("num");
                        boolean isall = (boolean) map.get("isall");
                        if (!tv_management.getText().toString().equals(getString(R.string.complete))) {
                            tv_settlement.setText(getString(R.string.balance) + "（" + num + "）");
                        }
                        tv_price.setText("￥" + price);

                        iv_all.setImageResource(isall ? R.drawable.check_red_2 : R.drawable.check_red_1);
                        ispickall = isall;

                    }
                    break;
                case SETCART_NUMBER://修改数量
                    if (msg.obj != null) {
                        Map<String, String> map = (Map<String, String>) msg.obj;
                        String goodsnumber = map.get("num");
                        String cart_id = map.get("cart_id");
                        netRun.upCartGoodsNum(cart_id, goodsnumber);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);
        findViewById();
    }

    @Override
    protected void findViewById() {
        btn_gobuy = findViewById(R.id.btn_gobuy);
        tv_title = findViewById(R.id.tv_title);
        tv_management = findViewById(R.id.tv_management);
        tv_settlement = findViewById(R.id.tv_settlement);
        tv_price = findViewById(R.id.tv_price);
        lv_list = findViewById(R.id.lv_list);
        ll_pickall = findViewById(R.id.ll_pickall);
        ll_null = findViewById(R.id.ll_null);
        iv_all = findViewById(R.id.iv_all);
        tv_gl = findViewById(R.id.tv_gl);
        recycler_view = findViewById(R.id.recycler_view);
        initView();
    }

    @Override
    protected void initView() {
        tv_settlement.setText(getString(R.string.balance) + "（0）");
        netRun = new NetRun(this, handler);
        ll_pickall.setOnClickListener(this);
        tv_management.setOnClickListener(this);
        tv_settlement.setOnClickListener(this);
        btn_gobuy.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.getCartList2();
        getGuessLike();
    }

    private void getGuessLike() {
        final GuessLikeAdapter guessLikeAdapter = new GuessLikeAdapter(this, mDatas);
        recycler_view.setAdapter(guessLikeAdapter);
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
        netRun.OnGuseeLove(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                GuessLikeBean guessLikeBean = BeanConvertor.convertBean(responseInfo.result, GuessLikeBean.class);
                mDatas.addAll(guessLikeBean.getDatas());
                guessLikeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        netRun.getCartList2();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_gobuy){
            RxBus.getDefault().post("jumpMain","jumpMain");
        }else if(view.getId()==R.id.ll_pickall){
            if (shoppingCartAdapter != null) {
                shoppingCartAdapter.pickAll(!ispickall);
                ispickall = !ispickall;
            }
        }else if(view.getId()==R.id.tv_management){
            if (tv_management.getText().toString().equals(getString(R.string.find_reminder334))) {
                tv_management.setText(getString(R.string.complete));
                tv_gl.setVisibility(View.GONE);
                tv_price.setVisibility(View.GONE);
                tv_settlement.setText(getString(R.string.delete));
            } else {
                tv_management.setText("" + getString(R.string.find_reminder334));
                tv_gl.setVisibility(View.VISIBLE);
                tv_price.setVisibility(View.VISIBLE);
                tv_settlement.setText(getString(R.string.balance) + "（0）");
                shoppingCartAdapter.getTotalPrice();
            }
        }else if(view.getId()==R.id.tv_settlement){
            if (tv_management.getText().toString().equals(getString(R.string.complete))) {//删除
                String pickGoodsId = shoppingCartAdapter.getPickGoodsId();
                if (pickGoodsId == null) {
                    Toast.makeText(appSingleton, getString(R.string.find_reminder337), Toast.LENGTH_SHORT).show();
                    return;
                }
                netRun.delCartGoods(pickGoodsId);
            } else {//结算
                String pickGoodsId = shoppingCartAdapter.getPickGoodsId2();
                if (pickGoodsId == null) {
                    Toast.makeText(appSingleton, getString(R.string.find_reminder337), Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("cart_id", pickGoodsId);
                bundle.putString("ifcart", "1");
                openActivity(OrderSureActivity.class, bundle);
            }
        }

//        switch (view.getId()) {
//            case R.id.btn_gobuy:
//                RxBus.getDefault().post("jumpMain","jumpMain");
//                break;
//            case R.id.ll_pickall://全选
//                if (shoppingCartAdapter != null) {
//                    shoppingCartAdapter.pickAll(!ispickall);
//                    ispickall = !ispickall;
//                }
//                break;
//            case R.id.tv_management://管理
//                if (tv_management.getText().toString().equals(getString(R.string.find_reminder334))) {
//                    tv_management.setText(getString(R.string.complete));
//                    tv_gl.setVisibility(View.GONE);
//                    tv_price.setVisibility(View.GONE);
//                    tv_settlement.setText(getString(R.string.delete));
//                } else {
//                    tv_management.setText("" + getString(R.string.find_reminder334));
//                    tv_gl.setVisibility(View.VISIBLE);
//                    tv_price.setVisibility(View.VISIBLE);
//                    tv_settlement.setText(getString(R.string.balance) + "（0）");
//                    shoppingCartAdapter.getTotalPrice();
//                }
//                break;
//            case R.id.tv_settlement://结算，删除
//                if (tv_management.getText().toString().equals(getString(R.string.complete))) {//删除
//                    String pickGoodsId = shoppingCartAdapter.getPickGoodsId();
//                    if (pickGoodsId == null) {
//                        Toast.makeText(appSingleton, getString(R.string.find_reminder337), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    netRun.delCartGoods(pickGoodsId);
//                } else {//结算
//                    String pickGoodsId = shoppingCartAdapter.getPickGoodsId2();
//                    if (pickGoodsId == null) {
//                        Toast.makeText(appSingleton, getString(R.string.find_reminder337), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putString("cart_id", pickGoodsId);
//                    bundle.putString("ifcart", "1");
//                    openActivity(OrderSureActivity.class, bundle);
//                }
//                break;
//        }
    }

    @Override
    public void ReGetData() {

    }
}
