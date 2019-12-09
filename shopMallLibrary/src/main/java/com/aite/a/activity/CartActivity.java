package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.AppManager;
import com.aite.a.HomeTabActivity;
import com.aite.a.adapter.Cart2Adapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.CartListInfo2;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.StringUtils;
import com.aiteshangcheng.a.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 *
 * @author xiaoyu
 */
public class CartActivity extends BaseActivity implements OnClickListener {
    // ====标题
    private ImageView iv_back;
    private TextView tv_title_name;
    // ====标题
    // ===界面布局控件
    private CheckBox cb_all_title;
    private TextView cb_all_count;
    private Button btn_pay;
    private ListView lv_list;
    public String cart_id;
    private boolean flag; // 全选或全取消
    private ImageView shooping_image;
    private LinearLayout cart_1;
    private RelativeLayout cart_2;
    private Button shooping;
    private TextView text1;
    private String shoopping;
    private Cart2Adapter cartAdapter;
    private View dibu;
    private CartListInfo2 cartList;
    private NetRun netRun;
    private RelativeLayout i_carttitle;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    double calculatePrice = calculatePrice();
                    DecimalFormat df = new DecimalFormat("#0.00");
                    String a = df.format(calculatePrice);
                    cb_all_count.setText(getI18n(R.string.total) + ":￥ " + a);
                    cb_all_title.setChecked(false);
                    break;
                case 1:
                    flag = false;
                    cb_all_title.setChecked(flag);
                    netRun.getCartList();
                    break;
                case 2:
                    flag = (Boolean) msg.obj;
                    cb_all_title.setChecked(flag);
                    btn_pay.setText(getI18n(R.string.balance) + "（"
                            + String.valueOf(num()) + "）");
                    break;
                case 3:
                    netRun.getCartList();
                    break;
                case cart_list_id:
                    cartList = (CartListInfo2) msg.obj;

                    // cb_all_count.setText(cartList.sum);
                    // System.out.println("------------------"+cartList.);

                    if (cartList.cart_list != null && cartList.cart_list.size() > 0) {
                        dibu.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.GONE);
                        shooping_image.setVisibility(View.GONE);
                        shooping.setVisibility(View.GONE);
                        cart_1.setVisibility(View.VISIBLE);
                        cart_2.setVisibility(View.VISIBLE);
                        // 重组
                        initlist(cartList);
                    } else {
                        dibu.setVisibility(View.GONE);
                        text1.setVisibility(View.VISIBLE);
                        shooping_image.setVisibility(View.VISIBLE);
                        shooping.setVisibility(View.VISIBLE);
                        cart_1.setVisibility(View.GONE);
                        cart_2.setVisibility(View.GONE);
                    }

                    mdialog.dismiss();
                    break;
                case cart_list_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(CartActivity.this,
                            getI18n(R.string.systembusy));
                    break;
                case cart_list_start:
//				mdialog.setMessage(getI18n(R.string.act_waiting));
                    mdialog.show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        netRun = new NetRun(this, handler);
        shoopping = getIntent().getStringExtra("shoopping");
        findViewById();
    }

    TextView tv_editor;

    @Override
    protected void findViewById() {
        btn_pay = (Button) findViewById(R.id.cart_btn_pay);
        lv_list = (ListView) findViewById(R.id.cart_list_view);
        tv_title_name = (TextView) findViewById(R.id._tv_name);
        cb_all_count = (TextView) findViewById(R.id.cart_cb_all_count);
        cb_all_title = (CheckBox) findViewById(R.id.cart_cb_all_title);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        text1 = (TextView) findViewById(R.id.text1);
        shooping_image = (ImageView) findViewById(R.id.shooping_image);
        shooping = (Button) findViewById(R.id.shooping);
        cart_1 = (LinearLayout) findViewById(R.id.cart_1);
        cart_2 = (RelativeLayout) findViewById(R.id.cart_rl_xia);
        dibu = findViewById(R.id.dibu);
        i_carttitle = (RelativeLayout) findViewById(R.id.i_carttitle);
        // if (shoopping != null) {
        // iv_back.setBackgroundResource(R.drawable.jd_return);
        // iv_back.setVisibility(View.VISIBLE);
        // }
        iv_back.setVisibility(View.GONE);
        initView();
    }

    @Override
    protected void initView() {
        cb_all_count.setOnClickListener(this);
        cb_all_count.setText(getI18n(R.string.total) + ":￥ 0.0");
        cb_all_title.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        btn_pay.setText(getI18n(R.string.balance) + "(0)");
        tv_title_name.setText(getI18n(R.string.cart));
        iv_back.setOnClickListener(this);
        shooping.setOnClickListener(this);

        // // //底部结算适配cart_2
        // RelativeLayout.LayoutParams buttonParams =
        // (android.widget.RelativeLayout.LayoutParams) cart_2
        // .getLayoutParams();
        // buttonParams.height = getScreenWidth(this) / 9;
        // cart_2.setLayoutParams(buttonParams);
        //
        // // 顶部适配
        // LinearLayout.LayoutParams layoutParams = (LayoutParams) i_carttitle
        // .getLayoutParams();
        // layoutParams.height = getScreenWidth(this) / 9;
        // i_carttitle.setLayoutParams(layoutParams);
        //
        // // 空购物车适配
        // RelativeLayout.LayoutParams Params =
        // (android.widget.RelativeLayout.LayoutParams) shooping_image
        // .getLayoutParams();
        // Params.height = getScreenWidth(this) / 4;
        // Params.width=getScreenWidth(this) / 4;
        // shooping_image.setLayoutParams(Params);

        if (State.UserKey != null)
            initData();
        cartAdapter = new Cart2Adapter(CartActivity.this, handler);
        lv_list.setAdapter(cartAdapter);
    }

    /**
     * 请求数据
     */
    @Override
    protected void initData() {
        netRun.getCartList();
    }

    /**
     * 重组商品列表
     */
    private void initlist(CartListInfo2 cartList) {
        // 取店铺名
        List<String> stoore = new ArrayList<String>();
        for (int i = 0; i < cartList.cart_list.size(); i++) {
            String store_name = cartList.cart_list.get(i).store_name;
            boolean isadd = true;
            if (stoore.size() > 0) {
                for (int j = 0; j < stoore.size(); j++) {
                    if (store_name.equals(stoore.get(j))) {
                        isadd = false;
                        continue;
                    }
                }
            }
            if (isadd) {
                stoore.add(store_name);
            }
        }
        // 根据店铺重组
        List<List<CartListInfo2.cart_list>> cartdata = new ArrayList<List<CartListInfo2.cart_list>>();
        for (int j2 = 0; j2 < stoore.size(); j2++) {
            String name = stoore.get(j2);
            List<CartListInfo2.cart_list> cart = new ArrayList<CartListInfo2.cart_list>();
            for (int j = 0; j < cartList.cart_list.size(); j++) {
                if (name.equals(cartList.cart_list.get(j).store_name)) {
                    cart.add(cartList.cart_list.get(j));
                }
            }
            cartdata.add(cart);
        }
        cartAdapter.setCartList(cartdata);
        cartAdapter.notifyDataSetChanged();
    }

    /**
     * 获取购买参数
     */
    private String getCart_ID() {
        List<String> cartId = new ArrayList<String>();
        List<List<CartListInfo2.cart_list>> cartList2 = cartAdapter.getCartList();
        if (cartList2 != null) {
            for (int i = 0; i < cartList2.size(); i++) {
                List<CartListInfo2.cart_list> list = cartList2
                        .get(i);
                if (list.size() > 0) {
                    for (int j2 = 0; j2 < list.size(); j2++) {
                        if (list.get(j2).isChoosed) {
                            cartId.add(list.get(j2).cart_id
                                    + "|"
                                    + list.get(j2).goods_num);
                        }
                    }
                }
            }
            String string = StringUtils.listToString(cartId, ",");
            return string;
        }
        // if (cartAdapter.cartList.cart_list.size() > 0) {
        // for (int i = 0; i < cartAdapter.cartList.cart_list.size(); i++) {
        // if (cartList.cart_list.get(i).isChoosed) {
        // cartId.add(cartList.cart_list.get(i).cart_id + "|"
        // + cartList.cart_list.get(i).goods_num);
        // }
        // }
        // String string = StringUtils.listToString(cartId, ",");
        // return string;
        // }
        return null;
    }

    // 全选或全取消
    private void selected(boolean flag) {
        List<List<CartListInfo2.cart_list>> cartList2 = cartAdapter.getCartList();
        if (cartList2 != null) {
            for (int i = 0; i < cartList2.size(); i++) {
                List<CartListInfo2.cart_list> list = cartList2
                        .get(i);
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).isChoosed = flag;
                }
            }
            double calculatePrice = calculatePrice();
            DecimalFormat df = new DecimalFormat("#0.00");
            String a = df.format(calculatePrice);
            cb_all_count.setText(getI18n(R.string.total) + ":￥ " + a);
            cartAdapter.notifyDataSetChanged();
        }

        // if (cartList != null) {
        // for (int i = 0; i < cartAdapter.cartList.cart_list.size(); i++) {
        // cartAdapter.getCartList().get(i).isChoosed = flag;
        // }
        // cartAdapter.notifyDataSetChanged();
        // }
    }

    /**
     * 计算商品数目
     *
     * @return
     */
    private int num() {
        int isselected = 0;
        List<List<CartListInfo2.cart_list>> cartList2 = cartAdapter.getCartList();
        if (cartList2 != null) {
            for (int i = 0; i < cartList2.size(); i++) {
                List<CartListInfo2.cart_list> list = cartList2
                        .get(i);
                if (list.size() > 0) {
                    for (int j2 = 0; j2 < list.size(); j2++) {
                        if (list.get(j2).isChoosed == true) {
                            isselected = isselected + 1;
                        }
                    }
                }
            }
        }

        // if (cartAdapter.cartList.cart_list.size() > 0) {
        // for (int i = 0; i < cartList.cart_list.size(); i++) {
        // if (cartList.cart_list.get(i).isChoosed == true) {
        // isselected = isselected + 1;
        // }
        // }
        // }
        return isselected;
    }

    /**
     * 判断是否全选
     *
     * @return
     */
    private boolean isselected() {
        boolean isselected = false;
        List<List<CartListInfo2.cart_list>> cartList2 = cartAdapter.getCartList();
        if (cartList2 != null) {
            for (int i = 0; i < cartList2.size(); i++) {
                List<CartListInfo2.cart_list> list = cartList2
                        .get(i);
                if (list.size() > 0) {
                    for (int j2 = 0; j2 < list.size(); j2++) {
                        if (list.get(j2).isChoosed == true) {
                            return true;
                        }
                    }
                }
            }
        }

        // if (cartAdapter.cartList.cart_list.size() > 0) {
        // for (int i = 0; i < cartAdapter.cartList.cart_list.size(); i++) {
        // if (cartAdapter.getCartList().get(i).isChoosed == true) {
        // return true;
        // }
        // }
        // }
        return isselected;
    }

    /**
     * 计算价格
     *
     * @return
     */
    private double calculatePrice() {
        double price = 0;

        List<List<CartListInfo2.cart_list>> cartList2 = cartAdapter.getCartList();
        if (cartList2 != null) {
            for (int i = 0; i < cartList2.size(); i++) {
                List<CartListInfo2.cart_list> list = cartList2
                        .get(i);
                if (list.size() > 0) {
                    for (int j2 = 0; j2 < list.size(); j2++) {
                        if (list.get(j2).isChoosed == true) {
                            double goodsPrice = Double
                                    .valueOf(list.get(j2).goods_price);
                            double num = Double.valueOf(list.get(j2).goods_num);
                            price = price + goodsPrice * num;
                        }
                    }
                }
            }
            cartAdapter.notifyDataSetChanged();
        }

        // if (cartAdapter.cartList.cart_list.size() > 0) {
        // for (int i = 0; i < cartAdapter.cartList.cart_list.size(); i++) {
        // if (cartAdapter.getCartList().get(i).isChoosed == true) {
        // double goodsPrice = Double.valueOf(cartList.cart_list
        // .get(i).goods_price);
        // double num = Double
        // .valueOf(cartList.cart_list.get(i).goods_num);
        // price = price + goodsPrice * num;
        // }
        // }
        // }

        return price;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.cart_btn_pay){
            if (isselected() == false) {
                CommonTools.showShortToast(this,
                        getI18n(R.string.please_choose_goods));
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("cart_id", getCart_ID());
                bundle.putString("ifcart", "1");
                openActivity(OrderSureActivity.class, bundle);
            }
        }else if(v.getId()==R.id.cart_cb_all_title){
            if (cb_all_title.isChecked()) {
                flag = true;
                selected(true);
                btn_pay.setText(getI18n(R.string.balance) + "("
                        + String.valueOf(num()) + ")");
            } else {
                cb_all_count.setText(getI18n(R.string.total) + ":￥ 0.00");
                flag = false;
                selected(false);
                btn_pay.setText(getI18n(R.string.balance) + "(0)");
            }
        }else if(v.getId()==R.id.shooping){
            if (shoopping != null) {
                AppManager.getInstance().killAllActivity();
                openActivity(HomeTabActivity.class);
            } else {
                intent = new Intent(MAIN_);
                this.sendBroadcast(intent);
            }
        }else if(v.getId()== R.id._iv_back){
            finish();
        }
//
//        switch (v.getId()) {
//            case R.id.cart_btn_pay:
//                if (isselected() == false) {
//                    CommonTools.showShortToast(this,
//                            getI18n(R.string.please_choose_goods));
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("cart_id", getCart_ID());
//                    bundle.putString("ifcart", "1");
//                    openActivity(OrderSureActivity.class, bundle);
//                }
//                break;
//            case R.id.cart_cb_all_title:
//                if (cb_all_title.isChecked()) {
//                    flag = true;
//                    selected(true);
//                    btn_pay.setText(getI18n(R.string.balance) + "("
//                            + String.valueOf(num()) + ")");
//                } else {
//                    cb_all_count.setText(getI18n(R.string.total) + ":￥ 0.00");
//                    flag = false;
//                    selected(false);
//                    btn_pay.setText(getI18n(R.string.balance) + "(0)");
//                }
//                break;
//            case R.id.shooping:
//                if (shoopping != null) {
//                    AppManager.getInstance().killAllActivity();
//                    openActivity(HomeTabActivity.class);
//                } else {
//                    intent = new Intent(MAIN_);
//                    this.sendBroadcast(intent);
//                }
//
//                break;
//            case R.id._iv_back:
//                finish();
//                break;
//        }
    }

    @Override
    protected void onResume() {
        findViewById();
        cb_all_title.setChecked(false);
        flag = false;
        super.onResume();
    }

    @Override
    public void ReGetData() {
        initData();
    }

    // 获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}
