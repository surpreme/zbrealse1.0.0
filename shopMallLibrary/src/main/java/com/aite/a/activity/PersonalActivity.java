package com.aite.a.activity;

import hotel.HotelHomeActivity;
import hotel.HotelOrderListActivity;
import livestream.activity.LiveStreamTabActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.HomeTabActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.User;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 个人中心
 *
 * @author xiaoyu
 */

public class PersonalActivity extends BaseActivity implements OnClickListener,
        Mark {
    private LinearLayout ect_payment1, ect_delivery, ect_collect, ect_assess,
            refund_afterSales, seller_central, myapplicationlayout,
            callcenterlayout, rl_top_bg;
    private RelativeLayout rl_buyer_orders, rl_merchant_orders,
            rl_address_manage, rl_favorite, rl_my_store, rl_change_pwd,
            rl_cart, rl_perdata, rl_my_coushu, rl_allorder, rl_coushu1,
            rl_coushu2, rl_coushu3, rl_vrorder, rl_servicevrorder,
            rl_myevaluationn, rl_kongbai2;
    private ImageView iv_buyer_orders, iv_merchant_orders, iv_address_manage,
            iv_favorite, iv_my_store, iv_integral, iv_cart;
    private TextView tv_name, tv_collectionbaby, tv_collectionstore,
            tv_bcbalance, tv_freeze, tv_bcintegral, tv_withdrawals, tv_txt1,
            tv_txt2, tv_txt4, tv_txt5, tv_txt6, personal_user;
    private CircleImageView personal_iv_avatr;
    protected User user;
    private String client = "android";
    private TextView predepoit;
    private TextView goods_fi_count, tv_new, tv_pay, tv_send, tv_pj,
            tv_success;
    private TextView store_fi_count;
    private LinearLayout personal_bg, num;
    private String nicknamet;
    private String head_pict;
    private String sext;
    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;
    private String names;
    private String icons;
    private String sexs;
    private NetRun netRun;
    private Intent intent2;
    private MyGridView mgv_maijia, mgv_myapplication, mgv_maijia2,
            mgv_navigation, mgv_callcenter;
    private RelativeLayout i_personaltitle;
    private GridLayout gl_maijia, gl_maijia2;
    private MjAdapter mjadapter;
    private MjAdapter2 mjAdapter2;
    private MyApplicationAdapter mAppAdapter;
    private CallCenterAdapter ccAdapter;
    private NavigationAdapter navigationAdapter;

    // 买家菜单数组
    private int[] maijiaimg = new int[]{R.drawable.maijiadingdan,
            R.drawable.xuniorder, R.drawable.fuwuorder, R.drawable.gouwuche,
            R.drawable.myzuji, R.drawable.dizhi, R.drawable.gerenziliao,
            R.drawable.xiugaimima, R.drawable.zujifcf, R.drawable.xunifcf,
            R.drawable.myevaluation, R.drawable.fenxiao, /*
														 * R.drawable.fenxiao ,
														 * R.drawable.forum, R
														 * .drawable.integral ,
														 * R.drawable.integral ,
														 * R.drawable.integral ,
														 */
            R.drawable.integral, R.drawable.mallmenu_16,
            R.drawable.mallmenu_15, R.drawable.distribution_center};
    private List<String> maijiatext;

    // 我的应用
    private int[] myapplicationimg = new int[]{R.drawable.integral,
            R.drawable.news, R.drawable.shequ, R.drawable.weifaxian,
            R.drawable.open_shop, R.drawable.forum, R.drawable.hotel_order, R.drawable.service_menu, R.drawable.service_menu, R.drawable.service_menu};
    private List<String> myapplicationtext;

    // 客服中心
    private int[] callcenterimg = new int[]{R.drawable.kefu,
            R.drawable.tousu, R.drawable.kefu, R.drawable.kongbainull};
    private List<String> callcentertext;

    // 买家菜单数组
    private int[] maijiaimg2 = new int[]{R.drawable.shangjiadianpu,
            R.drawable.shangjiadingdan, R.drawable.kongbainull,
            R.drawable.kongbainull};

    private List<String> maijiatext2;
    // 导航菜单数组
    private int[] navigationimage = new int[]{R.drawable.daizhifufcf,
            R.drawable.daifahuofcf, R.drawable.daishouhuofcf,
            R.drawable.bc_shop_cart, R.drawable.bc_collection};
    private List<String> navigationtext;
    private List<String> navigationtextnum;

    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case member_id:
                    if (msg.obj != null) {
                        user = (User) msg.obj;
                        // 订单状态数量修改
                        setordersnum(user.ORDER_STATE_NEW, user.ORDER_STATE_PAY,
                                user.ORDER_STATE_SEND, user.ORDER_STATE_SUCCESS);
                        // 文字颜色修改
                        String name = user.nickname == null ? ""
                                : user.nickname.equals("null") ? ""
                                : user.nickname.equals("") ? ""
                                : user.nickname;
                        tv_name.setText(name);
//					settextviewcolor(goods_fi_count, user.goods_favorites_count
//							+ "\n", getI18n(R.string.collection_goods));
//					settextviewcolor(predepoit, "￥" + user.predepoit + "\n",
//							getI18n(R.string.the_balance));
//					settextviewcolor(store_fi_count, user.store_favorites_count
//							+ "\n", getI18n(R.string.collection_shop));
                        tv_collectionbaby.setText(user.vouchercount);
                        tv_collectionstore.setText(user.redpackage_count);

                        tv_bcbalance.setText(user.predepoit);
                        tv_freeze.setText(user.freeze_predeposit);
                        tv_bcintegral.setText(user.point);
                        String zh = user.user_name == null ? "加载中.."
                                : user.user_name.equals("null") ? "加载中.."
                                : user.user_name.equals("") ? "加载中.."
                                : user.user_name;
                        personal_user.setText("账号 : " + zh);

                        // 头像
//					Glide.with(PersonalActivity.this).load(user.avator).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(personal_iv_avatr);
                        if (user.avator != null && !user.avator.equals("null")
                                && !user.avator.equals("")) {
                            bitmapUtils.display(personal_iv_avatr, user.avator);
                            new DownloadImageTask().execute(user.avator);
                        }
                        if (user.store_id != null && !user.store_id.equals("0")) {
                            // 卖家接口完成之后在显示
                            seller_central.setVisibility(View.VISIBLE);
                            // myapplicationlayout.setVisibility(View.VISIBLE);
                            // callcenterlayout.setVisibility(View.VISIBLE);
                        } else {
                            seller_central.setVisibility(View.GONE);
                            // myapplicationlayout.setVisibility(View.GONE);
                            // callcenterlayout.setVisibility(View.GONE);

                        }
                        // 请求背景(暂无修改背景借口)
                        // bitmapUtils.display(personal_bg, user.personal_bg);

                        // bitmapUtils.display(personal_bg,
                        // "http://b.hiphotos.baidu.com/image/h%3D200/sign=025e31065dafa40f23c6c9dd9b65038c/10dfa9ec8a136327137e4407968fa0ec08fac777.jpg");
                        // 增加从网络获取背景图片 begin 测试数据,后台返回url的时候在替换url
                        // new
                        // DownloadImageTask().execute("http://b.hiphotos.baidu.com/image/h%3D200/sign=025e31065dafa40f23c6c9dd9b65038c/10dfa9ec8a136327137e4407968fa0ec08fac777.jpg");
                        // 增加从网络获取背景图片 end
                    } else {
                        /*
                         * CommonTools.showShortToast(PersonalActivity.this,
                         * getI18n(R.string.act_no_data));
                         */
                        Toast.makeText(PersonalActivity.this, R.string.act_no_data,
                                Toast.LENGTH_SHORT).show();
                    }
                    mdialog.dismiss();
                    break;
                case member_err:
                    /*
                     * CommonTools.showShortToast(PersonalActivity.this,
                     * getI18n(R.string.act_net_error));
                     */
                    Toast.makeText(PersonalActivity.this, R.string.act_net_error,
                            Toast.LENGTH_SHORT).show();
                    mdialog.dismiss();
                    break;
                case member_start:
                    // mdialog.setMessage(getI18n(R.string.act_loading));
                    // mdialog.show();
                    break;
                case login_id:
                    // 登录
                    initData();
                    break;
            }
        }

        ;
    };

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Drawable result) {
            if (result == null) {
                return;
            }
            personal_iv_avatr.setImageDrawable(result);
        }
    }

    private Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过第二个参数(文件名)来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), null);
        } catch (IOException e) {
            Log.d("skythinking", e.getMessage());
        }
        if (drawable == null) {
            Log.d("skythinking", "null drawable");
        } else {
            Log.d("skythinking", "not null drawable");
        }

        return drawable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity);
        getQQuser();
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);

        findViewById();
    }

    /**
     * 自动登录
     */
    private void longinn() {
        SharedPreferences sp = getSharedPreferences("LoginActivity",
                MODE_PRIVATE);
        String username = sp.getString("username", " ");
        String password = sp.getString("password", " ");
        netRun.login(username, password, client);
    }

    OnClickListener orderClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PersonalActivity.this,
                    BuyerOrderFgActivity.class);
            if (v.getId() == R.id.ect_delivery) {
                intent.putExtra("viewPager", 1);
            } else if (v.getId() == R.id.ect_collect) {
                intent.putExtra("viewPager", 2);
            } else if (v.getId() == R.id.ect_assess) {
                intent.putExtra("viewPager", 3);
            } else if (v.getId() == R.id.refund_afterSales) {
                intent.putExtra("viewPager", 4);
            } else if (v.getId() == R.id.ect_payment1) {
                intent.putExtra("viewPager", 4);
            }
//			switch (v.getId()) {
//			case R.id.ect_delivery:
//				intent.putExtra("viewPager", 1);
//				break;
//			case R.id.ect_collect:
//				intent.putExtra("viewPager", 2);
//				break;
//			case R.id.ect_assess:
//				intent.putExtra("viewPager", 3);
//				break;
//			case R.id.refund_afterSales:
//				intent.putExtra("viewPager", 4);
//				break;
//			case R.id.ect_payment1:
//				intent.putExtra("viewPager", 4);
//				break;
//
//			}
            startActivity(intent);
        }
    };

    private void setordersnum(String neww, String pay, String send,
                              String success) {
        // 订单状态数量
        navigationtextnum = new ArrayList<String>();
        navigationtextnum.add(neww);
        navigationtextnum.add(pay);
        navigationtextnum.add(send);
        navigationtextnum.add("0");
        navigationtextnum.add("0");
        // 导航菜单
        navigationAdapter = new NavigationAdapter();
        mgv_navigation.setAdapter(navigationAdapter);
        if (neww != null && !neww.equals("0")) {
            tv_new.setVisibility(View.VISIBLE);
            tv_new.setText(neww);
        }
        if (pay != null && !pay.equals("0")) {
            tv_pay.setVisibility(View.VISIBLE);
            tv_pay.setText(pay);
        }
        if (send != null && !send.equals("0")) {
            tv_send.setVisibility(View.VISIBLE);
            tv_send.setText(send);
        }
        if (success != null && !success.equals("0")) {
            tv_pj.setVisibility(View.VISIBLE);
            tv_pj.setText(success);
            tv_success.setVisibility(View.VISIBLE);
            tv_success.setText(success);
        }
    }

    @Override
    protected void findViewById() {
        personal_bg = (LinearLayout) findViewById(R.id.personal_bg);
        myapplicationlayout = (LinearLayout) findViewById(R.id.myapplicationlayout);
        callcenterlayout = (LinearLayout) findViewById(R.id.callcenterlayout);
        seller_central = (LinearLayout) findViewById(R.id.seller_central);
        ect_payment1 = (LinearLayout) findViewById(R.id.ect_payment1);
        ect_payment1.setOnClickListener(orderClick);
        ect_delivery = (LinearLayout) findViewById(R.id.ect_delivery);
        ect_delivery.setOnClickListener(orderClick);
        ect_collect = (LinearLayout) findViewById(R.id.ect_collect);
        ect_collect.setOnClickListener(orderClick);
        ect_assess = (LinearLayout) findViewById(R.id.ect_assess);
        ect_assess.setOnClickListener(orderClick);
        refund_afterSales = (LinearLayout) findViewById(R.id.refund_afterSales);
        refund_afterSales.setOnClickListener(orderClick);
        tv_name = (TextView) findViewById(R.id.personal_tv_name);
        personal_iv_avatr = (CircleImageView) findViewById(R.id.personal_iv_avatr);
        iv_buyer_orders = (ImageView) findViewById(R.id.iv_buyer_orders);
        iv_address_manage = (ImageView) findViewById(R.id.iv_address_manage);
        iv_favorite = (ImageView) findViewById(R.id.iv_favorite);
        iv_integral = (ImageView) findViewById(R.id.iv_integral);
        iv_cart = (ImageView) findViewById(R.id.iv_cart);
        iv_my_store = (ImageView) findViewById(R.id.iv_my_store);
        iv_merchant_orders = (ImageView) findViewById(R.id.iv_merchant_orders);
        rl_buyer_orders = (RelativeLayout) findViewById(R.id.rl_buyer_orders);
        rl_favorite = (RelativeLayout) findViewById(R.id.rl_favorite);
        rl_address_manage = (RelativeLayout) findViewById(R.id.rl_address_manage);
        rl_change_pwd = (RelativeLayout) findViewById(R.id.rl_change_pwd);
        rl_cart = (RelativeLayout) findViewById(R.id.rl_cart);
        rl_my_store = (RelativeLayout) findViewById(R.id.rl_my_store);
        rl_merchant_orders = (RelativeLayout) findViewById(R.id.rl_merchant_orders);
        predepoit = (TextView) findViewById(R.id.predepoit);
        goods_fi_count = (TextView) findViewById(R.id.goods_fi_count);
        store_fi_count = (TextView) findViewById(R.id.store_fi_count);
        tv_new = (TextView) findViewById(R.id.tv_new);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_pj = (TextView) findViewById(R.id.tv_pj);
        tv_success = (TextView) findViewById(R.id.tv_success);
        gl_maijia = (GridLayout) findViewById(R.id.gl_maijia);
        gl_maijia2 = (GridLayout) findViewById(R.id.gl_maijia2);
        rl_perdata = (RelativeLayout) findViewById(R.id.rl_perdata);
        rl_my_coushu = (RelativeLayout) findViewById(R.id.rl_my_coushu);
        rl_allorder = (RelativeLayout) findViewById(R.id.rl_allorder);
        rl_myevaluationn = (RelativeLayout) findViewById(R.id.rl_myevaluationn);
        rl_kongbai2 = (RelativeLayout) findViewById(R.id.rl_kongbai2);
        // TextView seller_tx = (TextView) findViewById(R.id.seller_tx);
        // LinearLayout num = (LinearLayout) findViewById(R.id.num);
        // num.setVisibility(View.GONE);
        // seller_tx.setVisibility(View.GONE);
        // rl_integral.setVisibility(View.GONE);
        // rl_my_store.setVisibility(View.GONE);
        // rl_merchant_orders.setVisibility(View.GONE);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_right = (ImageView) findViewById(R.id._iv_right);
        tv_title_name = (TextView) findViewById(R.id._tv_name);
        rl_coushu1 = (RelativeLayout) findViewById(R.id.rl_coushu1);
        rl_coushu2 = (RelativeLayout) findViewById(R.id.rl_coushu2);
        rl_coushu3 = (RelativeLayout) findViewById(R.id.rl_coushu3);
        rl_vrorder = (RelativeLayout) findViewById(R.id.rl_vrorder);
        rl_servicevrorder = (RelativeLayout) findViewById(R.id.rl_servicevrorder);
        i_personaltitle = (RelativeLayout) findViewById(R.id.i_personaltitle);
        rl_top_bg = (LinearLayout) findViewById(R.id.rl_top_bg);
        num = (LinearLayout) findViewById(R.id.num);
        mgv_maijia = (MyGridView) findViewById(R.id.mgv_maijia);
        mgv_maijia2 = (MyGridView) findViewById(R.id.mgv_maijia2);
        mgv_myapplication = (MyGridView) findViewById(R.id.mgv_application);
        mgv_callcenter = (MyGridView) findViewById(R.id.mgv_callcenter);
        mgv_navigation = (MyGridView) findViewById(R.id.mgv_navigation);

        tv_collectionbaby = (TextView) findViewById(R.id.tv_collectionbaby);
        tv_collectionstore = (TextView) findViewById(R.id.tv_collectionstore);
        tv_bcbalance = (TextView) findViewById(R.id.tv_bcbalance);
        tv_freeze = (TextView) findViewById(R.id.tv_freeze);
        tv_bcintegral = (TextView) findViewById(R.id.tv_bcintegral);
        tv_withdrawals = (TextView) findViewById(R.id.tv_withdrawals);
        tv_txt1 = (TextView) findViewById(R.id.tv_txt1);
        tv_txt2 = (TextView) findViewById(R.id.tv_txt2);
        tv_txt4 = (TextView) findViewById(R.id.tv_txt4);
        tv_txt5 = (TextView) findViewById(R.id.tv_txt5);
        tv_txt6 = (TextView) findViewById(R.id.tv_txt6);
        personal_user = (TextView) findViewById(R.id.personal_user);

        setVisibility();
        setBackbg();
        initView();
        String extra = getIntent().getStringExtra("login_tag1");
        if (extra != null && extra.equals("zidong")) {
            longinn();
        }
    }

    private void setVisibility() {
        iv_back.setVisibility(View.GONE);
        iv_buyer_orders.setVisibility(View.INVISIBLE);
        iv_address_manage.setVisibility(View.INVISIBLE);
        iv_favorite.setVisibility(View.INVISIBLE);
        iv_integral.setVisibility(View.INVISIBLE);
        iv_cart.setVisibility(View.INVISIBLE);
        iv_my_store.setVisibility(View.INVISIBLE);
        iv_merchant_orders.setVisibility(View.INVISIBLE);
        // 买家中心
        maijiatext = new ArrayList<String>();
        maijiatext.add(getString(R.string.buyer_orders));
        maijiatext.add(getString(R.string.virtualorders));
        maijiatext.add(getString(R.string.gif2));
        maijiatext.add(getString(R.string.my_shopping_cart));
        maijiatext.add(getString(R.string.my_footprint));
        maijiatext.add(getString(R.string.address_manage));
        maijiatext.add(getString(R.string.perdata));
        maijiatext.add(getString(R.string.update_password));
        maijiatext.add(getString(R.string.collectionfcf2));
        maijiatext.add(getString(R.string.collectionfcf));
        maijiatext.add(getString(R.string.myevaluation));
        maijiatext.add(getString(R.string.distribution));
        /*
         * maijiatext.add(getString(R.string.distribution));
         * maijiatext.add(getString(R.string.mallbbs));
         * maijiatext.add(getString(R.string.integral_mall));
         * maijiatext.add(getString(R.string.app_news));
         * maijiatext.add(getString(R.string.customerservice));
         */
        maijiatext.add(getString(R.string.integral_mall));
        maijiatext.add(getString(R.string.distribution_center27));
        maijiatext.add(getString(R.string.distribution_center25));
        maijiatext.add(getString(R.string.stand_inside_letter));
        // 我的应用
        myapplicationtext = new ArrayList<String>();
        // myapplicationtext.add(getString(R.string.distribution));
        // myapplicationtext.add(getString(R.string.mallbbs));
        myapplicationtext.add(getString(R.string.integrall));
        myapplicationtext.add(getString(R.string.news));
        myapplicationtext.add(getString(R.string.shequ));
        myapplicationtext.add(getString(R.string.weifaxian));
        myapplicationtext.add(getString(R.string.hotel));
        myapplicationtext.add(getString(R.string.redpackageactivity));
        myapplicationtext.add(getString(R.string.hotel_order));
        myapplicationtext.add(getString(R.string.service));
        myapplicationtext.add("直播");
        myapplicationtext.add("签到");

        callcentertext = new ArrayList<String>();
        callcentertext.add(getString(R.string.platformservice));
        callcentertext.add(getString(R.string.tradecomplaint));
        callcentertext.add(getString(R.string.distribution_center24));
        // callcentertext.add(getString(R.string.distribution_center28));
        callcentertext.add("");
        // 卖家中心
        maijiatext2 = new ArrayList<String>();
        maijiatext2.add(getString(R.string.my_shop));
        maijiatext2.add(getString(R.string.merchant_order));
        maijiatext2.add("");
        maijiatext2.add("");
        // 导航菜单
        navigationtext = new ArrayList<String>();
        navigationtext.add(getString(R.string.notpaying));
        navigationtext.add(getString(R.string.to_be_shipped));
        navigationtext.add(getString(R.string.goods_to_be_received));
        navigationtext.add(getString(R.string.my_shopping_cart));
        navigationtext.add(getString(R.string.collectionfcf));
        // 好友列表
        // getUsernameList();

    }

    // private String[] maijiatext = new String[] {
    // getString(R.string.buyer_orders),
    // getString(R.string.virtualorders),
    // getString(R.string.serviceorders),
    // getString(R.string.my_shopping_cart),
    // getString(R.string.my_footprint),
    // getString(R.string.address_manage), getString(R.string.perdata),
    // getString(R.string.update_password),
    // getString(R.string.collectionfcf2),
    // getString(R.string.collectionfcf),
    // getString(R.string.myevaluation),
    // getString(R.string.want_to_open_shop),
    // };
    private void setBackbg() {
        rl_buyer_orders.setBackgroundColor(getResources().getColor(
                R.color.white));
        rl_address_manage.setBackgroundColor(getResources().getColor(
                R.color.white));
        rl_favorite.setBackgroundColor(getResources().getColor(R.color.white));
        rl_change_pwd
                .setBackgroundColor(getResources().getColor(R.color.white));
        rl_cart.setBackgroundColor(getResources().getColor(R.color.white));
        rl_merchant_orders.setBackgroundColor(getResources().getColor(
                R.color.white));
        rl_my_store.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initData() {
        if (State.UserKey == null) {
            intent = new Intent(MAIN_);
            this.sendBroadcast(intent);
        } else {
            netRun.getMember();
        }
        tv_name.setText(names);
        // saveBitmap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setVisibility();
        setBackbg();
    }

    @Override
    protected void initView() {
        tv_txt1.setOnClickListener(this);
        tv_txt2.setOnClickListener(this);
        tv_txt4.setOnClickListener(this);
        tv_txt5.setOnClickListener(this);
        tv_txt6.setOnClickListener(this);
        tv_withdrawals.setOnClickListener(this);

        rl_buyer_orders.setOnClickListener(this);
        rl_address_manage.setOnClickListener(this);
        rl_favorite.setOnClickListener(this);
        rl_change_pwd.setOnClickListener(this);
        rl_cart.setOnClickListener(this);
        rl_merchant_orders.setOnClickListener(this);
        rl_my_store.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        personal_iv_avatr.setOnClickListener(this);
        tv_title_name.setText(getI18n(R.string.personal_center));
        iv_right.setBackgroundResource(R.drawable.top_more);
        iv_right.setOnClickListener(this);
        predepoit.setOnClickListener(this);
        goods_fi_count.setOnClickListener(this);
        store_fi_count.setOnClickListener(this);
        personal_bg.setOnClickListener(this);
        rl_perdata.setOnClickListener(this);
        rl_allorder.setOnClickListener(this);
        rl_coushu1.setOnClickListener(this);
        rl_coushu2.setOnClickListener(this);
        rl_vrorder.setOnClickListener(this);
        rl_kongbai2.setOnClickListener(this);
        rl_servicevrorder.setOnClickListener(this);
        rl_myevaluationn.setOnClickListener(this);
        // // 顶部高度适配rl_cd
        // LayoutParams layoutParams = i_personaltitle.getLayoutParams();
        // layoutParams.height = getScreenWidth(this) / 9;
        // i_personaltitle.setLayoutParams(layoutParams);
        // // 背景高度适配
        // LayoutParams Params = rl_top_bg.getLayoutParams();
        // Params.height = (int) (getScreenWidth(this) / 2.5);
        // rl_top_bg.setLayoutParams(Params);
        // // 头像适配
        // LayoutParams titleParams = personal_iv_avatr.getLayoutParams();
        // titleParams.height = (int) ((getScreenWidth(this) / 6));
        // titleParams.width = (int) ((getScreenWidth(this) / 6));
        // personal_iv_avatr.setLayoutParams(titleParams);
        // // 我的订单适配
        // LinearLayout.LayoutParams Params2 =
        // (android.widget.LinearLayout.LayoutParams)
        // rl_allorder.getLayoutParams();
        // Params2.height = (int) ((getScreenWidth(this) /4) / 3);
        // rl_allorder.setLayoutParams(Params2);
        // // 顶部白条适配
        // RelativeLayout.LayoutParams Params3 =
        // (android.widget.RelativeLayout.LayoutParams) num.getLayoutParams();
        // Params3.height = (int) ((getScreenWidth(this) /9));
        // num.setLayoutParams(Params3);
        // 买家中心
        mjadapter = new MjAdapter();
        mgv_maijia.setAdapter(mjadapter);
        // 卖家中心
        mjAdapter2 = new MjAdapter2();
        mgv_maijia2.setAdapter(mjAdapter2);
        // 我的应用
        mAppAdapter = new MyApplicationAdapter();
        mgv_myapplication.setAdapter(mAppAdapter);

        ccAdapter = new CallCenterAdapter();
        mgv_callcenter.setAdapter(ccAdapter);

        // 修改卖家中心菜单的规格
        gl_maijia.post(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        client(rl_buyer_orders);
                        client(rl_address_manage);
                        client(rl_favorite);
                        client(rl_cart);
                        client(rl_change_pwd);
                        client(rl_perdata);
                        client(rl_coushu1);
                        client(rl_coushu2);
                        client(rl_vrorder);
                        client(rl_servicevrorder);
                        client(rl_myevaluationn);
                        client(rl_kongbai2);
                    }
                });
            }
        });
        initData();

        gl_maijia2.post(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        client(rl_merchant_orders);
                        client(rl_my_store);
                        client(rl_my_coushu);
                        client(rl_coushu3);
                    }
                });

            }
        });
    }

    private Boolean isoren = false;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_txt1) {
            // 代金券
            // startActivity(new Intent(PersonalActivity.this,
            // IntegralShopActivity.class));
            Uri uri = Uri
                    .parse("http://aitecc.com/wap/index.php?act=pointshop");
            Intent intentu = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentu);
        } else if (v.getId() == R.id.tv_txt2) {
            // 红包
            Intent intenthb = new Intent(PersonalActivity.this,
                    RedPackageActivityList.class);
            startActivity(intenthb);
        } else if (v.getId() == R.id.tv_txt4) {
            // 余额
            Intent intentye = new Intent(PersonalActivity.this,
                    MyMoneyActivity.class);
            intentye.putExtra("isfreeze", false);
            startActivity(intentye);
        } else if (v.getId() == R.id.tv_txt5) {
            // 佣金
            Intent intentdj = new Intent(PersonalActivity.this,
                    MyMoneyActivity.class);
            intentdj.putExtra("isfreeze", true);
            startActivity(intentdj);
        } else if (v.getId() == R.id.tv_txt6) {
            // 积分
            Intent intentjf = new Intent(PersonalActivity.this,
                    IntegralInfoActivity.class);
            startActivity(intentjf);
        } else if (v.getId() == R.id.tv_withdrawals) {
            // 提现
            intent = new Intent(this, BalanceTxActivity.class);
            intent.putExtra("predepoit", user.predepoit);
            startActivity(intent);
        } else if (v.getId() == R.id.goods_fi_count) {
            intent = new Intent(this, FavoriteListFargmentActivity.class);
            intent.putExtra("i", 1);
            startActivity(intent);
        } else if (v.getId() == R.id.store_fi_count) {
            intent = new Intent(this, FavoriteListFargmentActivity.class);
            intent.putExtra("i", 2);
            startActivity(intent);
        } else if (v.getId() == R.id.personal_iv_avatr) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("icon", user.avator);
            openActivity(PersonalInformationActivity.class, bundle3);
        } else if (v.getId() == R.id._iv_back) {
            if (intent == null) {
                intent = new Intent();
            }
            intent.setClass(PersonalActivity.this, HomeTabActivity.class);
            startActivity(intent);
            overrIn();
        } else if (v.getId() == R.id._iv_right) {
            openActivity(MoreActivity.class);
            overrIn();
        } else if (v.getId() == R.id.rl_buyer_orders) {
            setAnyThingChange(rl_buyer_orders, iv_buyer_orders);
            openActivity(BuyerOrderFgActivity.class);
            overrIn();
        } else if (v.getId() == R.id.rl_change_pwd) {
            setAnyThingChange(rl_change_pwd, iv_integral);
            openActivity(ChangePassword.class);
            overrIn();
        } else if (v.getId() == R.id.rl_cart) {
            setAnyThingChange(rl_cart, iv_cart);
            Bundle bundle5 = new Bundle();
            bundle5.putString("shoopping", "shoopping");
            openActivity(CartActivity.class, bundle5);
            overrIn();
        } else if (v.getId() == R.id.rl_address_manage) {
            setAnyThingChange(rl_address_manage, iv_address_manage);
            openActivity(AddressManageActivity.class);
            overrIn();
        } else if (v.getId() == R.id.rl_favorite) {
            startActivity(new Intent(PersonalActivity.this,
                    MyfootprintActivity.class));
        } else if (v.getId() == R.id.rl_merchant_orders) {
            setAnyThingChange(rl_merchant_orders, iv_merchant_orders);
            openActivity(SellerOrderActivity.class);
            overrIn();
        } else if (v.getId() == R.id.rl_my_store) {
            setAnyThingChange(rl_my_store, iv_my_store);
            openActivity(MyStoreActivity.class);
            // storeUI();
            overrIn();
        } else if (v.getId() == R.id.personal_bg) {
            editAvatar();
        } else if (v.getId() == R.id.rl_perdata) {
            Bundle bundle = new Bundle();
            bundle.putString("icon", user.avator);
            openActivity(PersonalInformationActivity.class, bundle);
        } else if (v.getId() == R.id.rl_allorder) {
            Intent intentt = new Intent(PersonalActivity.this,
                    BuyerOrderFgActivity.class);
            intentt.putExtra("viewPager", 0);
            startActivity(intentt);
        } else if (v.getId() == R.id.rl_coushu1) {
            intent = new Intent(this, FavoriteListFargmentActivity.class);
            intent.putExtra("i", 1);
            startActivity(intent);
        } else if (v.getId() == R.id.rl_coushu2) {
            intent = new Intent(this, FavoriteListFargmentActivity.class);
            intent.putExtra("i", 2);
            startActivity(intent);
        } else if (v.getId() == R.id.rl_vrorder) {
            Intent intent3 = new Intent(PersonalActivity.this, VrOrder.class);
            intent3.putExtra("type", "0");
            startActivity(intent3);
        } else if (v.getId() == R.id.rl_servicevrorder) {
            Intent intent4 = new Intent(PersonalActivity.this, VrOrder.class);
            intent4.putExtra("type", "1");
            startActivity(intent4);
        } else if (v.getId() == R.id.rl_myevaluationn) {
            Intent intent6 = new Intent(PersonalActivity.this,
                    Myevaluation.class);
            intent6.putExtra("touxiang", user.avator);
            intent6.putExtra("names", user.user_name);
            startActivity(intent6);
        }

//		switch (v.getId()) {
//		case R.id.tv_txt1:
//			// 代金券
//			// startActivity(new Intent(PersonalActivity.this,
//			// IntegralShopActivity.class));
//			Uri uri = Uri
//					.parse("http://aitecc.com/wap/index.php?act=pointshop");
//			Intent intentu = new Intent(Intent.ACTION_VIEW, uri);
//			startActivity(intentu);
//			break;
//		case R.id.tv_txt2:
//			// 红包
//			Intent intenthb = new Intent(PersonalActivity.this,
//					RedPackageActivityList.class);
//			startActivity(intenthb);
//			break;
//		case R.id.tv_txt4:
//			// 余额
//			Intent intentye = new Intent(PersonalActivity.this,
//					MyMoneyActivity.class);
//			intentye.putExtra("isfreeze", false);
//			startActivity(intentye);
//			break;
//		case R.id.tv_txt5:
//			// 佣金
//			Intent intentdj = new Intent(PersonalActivity.this,
//					MyMoneyActivity.class);
//			intentdj.putExtra("isfreeze", true);
//			startActivity(intentdj);
//			break;
//		case R.id.tv_txt6:
//			// 积分
//			Intent intentjf = new Intent(PersonalActivity.this,
//					IntegralInfoActivity.class);
//			startActivity(intentjf);
//			break;
//		case R.id.tv_withdrawals:
//			// 提现
//			intent = new Intent(this, BalanceTxActivity.class);
//			intent.putExtra("predepoit", user.predepoit);
//			startActivity(intent);
////			startActivity(new Intent(PersonalActivity.this,
////					BalanceTxActivity.class));
//			break;
//		case R.id.goods_fi_count:
//			intent = new Intent(this, FavoriteListFargmentActivity.class);
//			intent.putExtra("i", 1);
//			startActivity(intent);
//			break;
//		case R.id.store_fi_count:
//			intent = new Intent(this, FavoriteListFargmentActivity.class);
//			intent.putExtra("i", 2);
//			startActivity(intent);
//			break;
//		case R.id.personal_iv_avatr:
//			Bundle bundle3 = new Bundle();
//			bundle3.putString("icon", user.avator);
//			openActivity(PersonalInformationActivity.class, bundle3);
//			break;
//		case R.id._iv_back:
//			if (intent == null) {
//				intent = new Intent();
//			}
//			intent.setClass(PersonalActivity.this, HomeTabActivity.class);
//			startActivity(intent);
//			overrIn();
//			break;
//		case R.id._iv_right:
//			openActivity(MoreActivity.class);
//			overrIn();
//			break;
//
//		case R.id.rl_buyer_orders:
//			setAnyThingChange(rl_buyer_orders, iv_buyer_orders);
//			openActivity(BuyerOrderFgActivity.class);
//			overrIn();
//			break;
//		case R.id.rl_change_pwd:
//			setAnyThingChange(rl_change_pwd, iv_integral);
//			openActivity(ChangePassword.class);
//			overrIn();
//			break;
//		case R.id.rl_cart:
//			setAnyThingChange(rl_cart, iv_cart);
//			Bundle bundle5 = new Bundle();
//			bundle5.putString("shoopping", "shoopping");
//			openActivity(CartActivity.class, bundle5);
//			overrIn();
//			break;
//		case R.id.rl_address_manage:
//			setAnyThingChange(rl_address_manage, iv_address_manage);
//			openActivity(AddressManageActivity.class);
//			overrIn();
//			break;
//		case R.id.rl_favorite:
//			// setAnyThingChange(rl_favorite, iv_favorite);
//			// openActivity(FavoriteListFargmentActivity.class);
//			// overrIn();
//			startActivity(new Intent(PersonalActivity.this,
//					MyfootprintActivity.class));
//			break;
//		case R.id.rl_merchant_orders:
//			setAnyThingChange(rl_merchant_orders, iv_merchant_orders);
//			openActivity(SellerOrderActivity.class);
//			overrIn();
//			break;
//		case R.id.rl_my_store:
//			setAnyThingChange(rl_my_store, iv_my_store);
//			openActivity(MyStoreActivity.class);
//			// storeUI();
//			overrIn();
//			break;
//		case R.id.personal_bg:
//			editAvatar();
//			break;
//		case R.id.rl_perdata:
//			Bundle bundle = new Bundle();
//			bundle.putString("icon", user.avator);
//			openActivity(PersonalInformationActivity.class, bundle);
//			break;
//		case R.id.rl_allorder:
//			Intent intentt = new Intent(PersonalActivity.this,
//					BuyerOrderFgActivity.class);
//			intentt.putExtra("viewPager", 0);
//			startActivity(intentt);
//			break;
//		case R.id.rl_coushu1:
//			intent = new Intent(this, FavoriteListFargmentActivity.class);
//			intent.putExtra("i", 1);
//			startActivity(intent);
//			break;
//		case R.id.rl_coushu2:
//			intent = new Intent(this, FavoriteListFargmentActivity.class);
//			intent.putExtra("i", 2);
//			startActivity(intent);
//			break;
//		case R.id.rl_vrorder:
//			Intent intent3 = new Intent(PersonalActivity.this, VrOrder.class);
//			intent3.putExtra("type", "0");
//			startActivity(intent3);
//			break;
//		case R.id.rl_servicevrorder:
//			Intent intent4 = new Intent(PersonalActivity.this, VrOrder.class);
//			intent4.putExtra("type", "1");
//			startActivity(intent4);
//			break;
//		case R.id.rl_myevaluationn:
//			Intent intent6 = new Intent(PersonalActivity.this,
//					Myevaluation.class);
//			intent6.putExtra("touxiang", user.avator);
//			intent6.putExtra("names", user.user_name);
//			startActivity(intent6);
//			break;
//		case R.id.predepoit:
//			// 提现
//			// startActivity(new Intent(PersonalActivity.this,
//			// BalanceTxActivity.class));
//			break;
//		case R.id.rl_kongbai2:
//			// Intent intent5 = new
//			// Intent(PersonalActivity.this,DistributionActivity.class);
//			// startActivity(intent5);
//			// Intent intent5 = new
//			// Intent(PersonalActivity.this,FreeOpenShopActivity.class);
//			// startActivity(intent5);
//			break;
//
//		}
    }

    /** -------------------------环信----------------------- */

    /**
     * 添加好友
     */
    private void HxAddFriend() {
        // 参数为要添加的好友的username和添加理由
        // try {
        // if (TextUtils.isEmpty(ed_search.getText().toString())) {
        // Toast.makeText(PersonalActivity.this,
        // getString(R.string.input_name), Toast.LENGTH_SHORT)
        // .show();
        // return;
        // }
        // EMClient.getInstance().contactManager()
        // .addContact(ed_search.getText().toString(), "");
        // ed_search.setText("");
        // Toast.makeText(PersonalActivity.this,
        // getString(R.string.request_successful), Toast.LENGTH_SHORT)
        // .show();
        // } catch (HyphenateException e) {
        // System.out.println("----------------添加失败");
        // e.printStackTrace();
        // }
    }


    /** -------------------------环信----------------------- */

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getI18n(R.string.select_picture_source));
        String[] items = new String[]{getI18n(R.string.media_lib),
                getI18n(R.string.take_picture)};
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0:
                        // 调用相册
                        startPick(dialog);
                        break;
                    case 1:
                        // 调用拍照
                        startCamera(dialog);
                        break;
                }
            }
        });
        dialog.setNegativeButton(getI18n(R.string.cancel), null);
        dialog.create().show();
    }

    // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_CARMERA);
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_PICK);
    }

    // 调用系统裁剪
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }

    private Bitmap bmp = null;

    // 将裁剪后的图片显示在ImageView上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
            // bmp = bundle.getParcelable("data");
            Drawable path = Drawable.createFromPath(tempFile.getAbsolutePath());
            // personal_bg.setImageBitmap(path);
            personal_bg.setBackgroundDrawable(path);
            Log.i("skythinking", tempFile.getAbsolutePath());
        }
    }

    // 把裁剪后的图片保存到sdcard上
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CARMERA:
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case PHOTO_PICK:
                if (null != data) {
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        bmp = bundle.getParcelable("data");
                        saveCropPic(bmp);
                    }
                    setPicToView(data);
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void storeUI() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        // ad.setTitle("登陆方式");// 设置对话框标题
        ad.setItems(new String[]{getI18n(R.string.my_shop),
                        getI18n(R.string.want_to_open_shop)},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openActivity(MyStoreActivity.class);
                                break;
                            case 1:
                                openActivity(OpenStoreActivity.class);
                                break;
                        }
                    }
                });
        ad.show();
    }

    /**
     * 改变一些状态
     */
    private void setAnyThingChange(RelativeLayout rl, ImageView iv) {
        rl.setBackgroundColor(getResources().getColor(R.color.personal_bg));
        iv.setVisibility(iv.VISIBLE);
    }

    @Override
    public void ReGetData() {
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

//	private void settextviewcolor(TextView view, String amount, String tex) {
//		String[] colors = getResources().getStringArray(R.array.colors);
//		StringBuffer sb = new StringBuffer();
//		String[] con = new String[] { amount, tex };
//
//		for (int i = 0; i < con.length; i++) {
//			sb.append(con[i]);
//		}
//
//		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
//				sb.toString());
//		int begin = 0;
//
//		for (int j = 0; j < colors.length; j++) {
//			String color = colors[j];
//			String[] s = color.split(",");
//
//			int mycolor = Color.rgb(Integer.parseInt(s[0]),
//					Integer.parseInt(s[1]), Integer.parseInt(s[2]));
//
//			int textlength = con[j].length();
//			spannableStringBuilder.setSpan(new ForegroundColorSpan(mycolor),
//					begin, begin + textlength,
//					spannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
//			begin += textlength;
//		}
//		view.setText(spannableStringBuilder);
//	}

    /**
     * 适配屏幕
     *
     * @param view
     */
    private void client(RelativeLayout view) {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();

        LayoutParams para = view.getLayoutParams();
        para.width = width / 4;
        para.height = width / 4;
        view.setLayoutParams(para);
    }

    private void getQQuser() {
        intent2 = getIntent();
        if (null != intent2) {
            names = intent2.getStringExtra("nickname");
            icons = intent2.getStringExtra("head_pic");
            sexs = intent2.getStringExtra("sex");

        }
    }

    private Bitmap decodeStream;

    /**
     * 保存方法
     */
    public void saveBitmap() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url2 = new URL(icons);
                    // 获得对象
                    HttpURLConnection openConnection = (HttpURLConnection) url2
                            .openConnection();
                    // 延时
                    openConnection.setConnectTimeout(5 * 1000);
                    // 连接
                    openConnection.connect();
                    // 返回是否正确
                    if (openConnection.getResponseCode() == 200) {
                        // 获结果
                        InputStream inputStream = openConnection
                                .getInputStream();
                        // 转成Bitmap
                        decodeStream = BitmapFactory.decodeStream(inputStream);

                        // File f = new File("/sdcard/namecard/");
                        // if (f.exists()) {
                        // f.delete();
                        // }
                        // try {
                        // FileOutputStream out = new
                        // FileOutputStream(tempFile);
                        // decodeStream.compress(Bitmap.CompressFormat.PNG, 90,
                        // out);
                        // out.flush();
                        // out.close();
                        // System.out.println("-----------"+tempFile.getAbsolutePath());
                        // } catch (FileNotFoundException e) {
                        // e.printStackTrace();
                        // } catch (IOException e) {
                        // e.printStackTrace();
                        // }
                    } else {
                        System.out.println("------------失敗");
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            personal_iv_avatr.setImageBitmap(decodeStream);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // 买家中心
    private class MjAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            System.out.println("------------卖家中心   " + maijiaimg.length);
            return maijiaimg.length;
        }

        @Override
        public Object getItem(int position) {

            return maijiaimg == null ? null : maijiaimg[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = View.inflate(PersonalActivity.this,
                    R.layout.maijia_item, null);
            ImageView iv_menuimg = (ImageView) convertView
                    .findViewById(R.id.iv_menuimg);
            TextView tv_menutext = (TextView) convertView
                    .findViewById(R.id.tv_menutext);

            LinearLayout ll_menuitem = (LinearLayout) convertView
                    .findViewById(R.id.ll_menuitem);
            // 高度适配
            LayoutParams layoutParams = ll_menuitem.getLayoutParams();
            layoutParams.height = getScreenWidth(PersonalActivity.this) / 4;
            ll_menuitem.setLayoutParams(layoutParams);

            iv_menuimg.setImageResource(maijiaimg[position]);
            tv_menutext.setText(maijiatext.get(position));
            ll_menuitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            // 订单管理
                            openActivity(BuyerOrderFgActivity.class);
                            overrIn();
                            break;
                        case 1:
                            // 虚拟订单
                            Intent intent3 = new Intent(PersonalActivity.this,
                                    VrOrder.class);
                            intent3.putExtra("type", "0");
                            startActivity(intent3);
                            break;
                        case 2:
                            // 兑换记录
                            Intent intent4 = new Intent(PersonalActivity.this,
                                    ExchangeRecordActivity.class);
                            startActivity(intent4);
                            // Intent intent4 = new Intent(PersonalActivity.this,
                            // VrOrder.class);
                            // intent4.putExtra("type", "1");
                            // startActivity(intent4);
                            break;
                        case 3:
                            // 购物车
                            Bundle bundle5 = new Bundle();
                            bundle5.putString("shoopping", "shoopping");
                            openActivity(CartActivity.class, bundle5);
                            overrIn();
                            break;
                        case 4:
                            // 我的足迹
                            startActivity(new Intent(PersonalActivity.this,
                                    MyfootprintActivity.class));
                            break;
                        case 5:
                            // 地址管理
                            openActivity(AddressManageActivity.class);
                            overrIn();
                            break;
                        case 6:
                            // 个人资料
                            Bundle bundle3 = new Bundle();
                            bundle3.putString("icon", user.avator);
                            openActivity(PersonalInformationActivity.class, bundle3);
                            break;
                        case 7:
                            // 修改密码
                            openActivity(ChangePassword.class);
                            overrIn();
                            break;
                        case 8:
                            // 在线充值
                            // startActivity(new Intent(PersonalActivity.this,
                            // OnlineTopUpActivity.class));
                            // 收藏店铺
                            intent = new Intent(PersonalActivity.this,
                                    FavoriteListFargmentActivity.class);
                            intent.putExtra("i", 2);
                            startActivity(intent);
                            break;
                        case 9:
                            // 收藏列表
                            intent = new Intent(PersonalActivity.this,
                                    FavoriteListFargmentActivity.class);
                            intent.putExtra("i", 1);
                            startActivity(intent);
                            break;
                        case 10:
                            // 我的评价

                            Intent intent6 = new Intent(PersonalActivity.this,
                                    Myevaluation.class);
                            intent6.putExtra("touxiang", user.avator);
                            intent6.putExtra("names", user.user_name);
                            startActivity(intent6);
                            break;
                        case 11:
                            // 我要开店
                            // Intent intent5 = new Intent(PersonalActivity.this,
                            // FreeOpenShopActivity.class);
                            // startActivity(intent5);
                            // 分销中心
                            if (user == null || user.member_rank == null
                                    || user.member_rank.equals("0")) {
                                Toast.makeText(PersonalActivity.this,
                                        getString(R.string.distribution_center11),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent16 = new Intent(PersonalActivity.this,
                                        DistributionCenterActivity.class);
                                startActivity(intent16);
                            }
                            break;
                        case 12:
                            // 在线充值
                            // startActivity(new Intent(PersonalActivity.this,
                            // OnlineTopUpActivity.class));
                            // 积分商城
                            Intent intentjf = new Intent(PersonalActivity.this,
                                    IntegralShopActivity.class);
                            intentjf.putExtra("person_in", "1");
                            startActivity(intentjf);
                            break;
                        case 13:
                            // 会员认证
                            Intent intent7 = new Intent(PersonalActivity.this,
                                    IdentityActivity.class);
                            startActivity(intent7);
                            break;
                        case 14:
                            // 账户安全
                            Intent intent9 = new Intent(PersonalActivity.this,
                                    PhoneCertificationActivity.class);
                            startActivity(intent9);
                            break;
                        case 15:
                            // 站内信
                            Intent intent14 = new Intent(PersonalActivity.this,
                                    StationLetterListActivity.class);
                            intent14.putExtra("type", "ordinary");
                            startActivity(intent14);
                            break;
                    }
                }
            });
            return convertView;
        }
    }

    // 卖家中心
    private class MjAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {

            return maijiaimg2.length;
        }

        @Override
        public Object getItem(int position) {

            return maijiaimg2 == null ? null : maijiaimg2[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = View.inflate(PersonalActivity.this,
                    R.layout.maijia_item, null);
            ImageView iv_menuimg = (ImageView) convertView
                    .findViewById(R.id.iv_menuimg);
            TextView tv_menutext = (TextView) convertView
                    .findViewById(R.id.tv_menutext);

            LinearLayout ll_menuitem = (LinearLayout) convertView
                    .findViewById(R.id.ll_menuitem);
            // 高度适配
            LayoutParams layoutParams = ll_menuitem.getLayoutParams();
            layoutParams.height = getScreenWidth(PersonalActivity.this) / 4;
            ll_menuitem.setLayoutParams(layoutParams);

            iv_menuimg.setImageResource(maijiaimg2[position]);
            tv_menutext.setText(maijiatext2.get(position));
            ll_menuitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            // 我的店铺
                            openActivity(MyStoreActivity.class);
                            break;
                        case 1:
                            // 商家订单
                            openActivity(SellerOrderActivity.class);
                            overrIn();
                            break;
                        case 2:
                            // // 服务订单
                            // Intent intent4 = new Intent(PersonalActivity.this,
                            // VrOrder.class);
                            // intent4.putExtra("type", "0");
                            // startActivity(intent4);
                            break;
                        case 3:
                            // // 购物车
                            // Bundle bundle5 = new Bundle();
                            // bundle5.putString("shoopping", "shoopping");
                            // openActivity(CartActivity.class, bundle5);
                            // overrIn();
                            break;

                    }
                }
            });
            return convertView;
        }
    }

    // 我的应用
    private class MyApplicationAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // Auto-generated method stub
            return myapplicationimg.length;
        }

        @Override
        public Object getItem(int position) {
            // Auto-generated method stub
            return myapplicationimg == null ? null : myapplicationimg[position];
        }

        @Override
        public long getItemId(int position) {
            // Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = View.inflate(PersonalActivity.this,
                    R.layout.maijia_item, null);
            ImageView iv_menuimg = (ImageView) convertView
                    .findViewById(R.id.iv_menuimg);
            TextView tv_menutext = (TextView) convertView
                    .findViewById(R.id.tv_menutext);

            LinearLayout ll_menuitem = (LinearLayout) convertView
                    .findViewById(R.id.ll_menuitem);
            // 高度适配
            LayoutParams layoutParams = ll_menuitem.getLayoutParams();
            layoutParams.height = getScreenWidth(PersonalActivity.this) / 4;
            ll_menuitem.setLayoutParams(layoutParams);

            iv_menuimg.setImageResource(myapplicationimg[position]);
            tv_menutext.setText(myapplicationtext.get(position));
            ll_menuitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (position) {
                        // 分销中心
                        // case 0:
                        // // startActivity(new Intent(PersonalActivity.this,
                        // // DistributionActivity.class));
                        // if (user == null || user.member_rank == null
                        // || user.member_rank.equals("0")) {
                        // Toast.makeText(PersonalActivity.this,
                        // getString(R.string.distribution_center11),
                        // Toast.LENGTH_SHORT).show();
                        // } else {
                        // Intent intent16 = new Intent(PersonalActivity.this,
                        // DistributionCenterActivity.class);
                        // startActivity(intent16);
                        // }
                        // break;
                        // // 商城论坛
                        // case 1:
                        // startActivity(new Intent(PersonalActivity.this,
                        // BbsActivity.class));
                        // break;
                        // 积分商城
                        case 0:
                            // startActivity(new Intent(PersonalActivity.this,
                            // IntegralShopActivity.class));
                            Intent intent4 = new Intent(PersonalActivity.this,
                                    IntegralInfoActivity.class);
                            intent4.putExtra("person_in", "1");
                            startActivity(intent4);
                            break;
                        // 新闻
                        case 1:
                            startActivity(new Intent(PersonalActivity.this,
                                    InformationActivity.class));
                            break;
                        // 社区
                        case 2:
                            Intent intent1 = new Intent(PersonalActivity.this,
                                    InformationActivity.class);
                            intent1.putExtra("person_in", "2");
                            startActivity(intent1);
                            break;
                        // 微发现
                        case 3:
                            Intent intent2 = new Intent(PersonalActivity.this,
                                    InformationActivity.class);
                            intent2.putExtra("person_in", "1");
                            startActivity(intent2);
                            break;
                        // 酒店
                        case 4:
                            // 积分商城
//						Intent intent6 = new Intent(PersonalActivity.this,
//								IntegralShopActivity.class);
//						intent6.putExtra("person_in", "1");
//						startActivity(intent6);
                            Intent intent6 = new Intent(PersonalActivity.this,
                                    HotelHomeActivity.class);
                            startActivity(intent6);
                            break;
                        // 红包活动
                        case 5:
                            Intent intent7 = new Intent(PersonalActivity.this,
                                    RedPackageActivityList.class);
                            startActivity(intent7);
                            break;
                        //酒店订单
                        case 6:
                            Intent intent8 = new Intent(PersonalActivity.this,
                                    HotelOrderListActivity.class);
                            startActivity(intent8);
                            break;
                        //服务
                        case 7:
                            Intent intent9 = new Intent(PersonalActivity.this,
                                    ServicehomeActivity.class);
                            startActivity(intent9);
                            break;
                        //直播
                        case 8:
                            Intent intent10 = new Intent(PersonalActivity.this,
                                    LiveStreamTabActivity.class);
                            startActivity(intent10);
                            break;
                        //签到
                        case 9:
                            Intent intent11 = new Intent(PersonalActivity.this,
                                    MyCalendarActivity.class);
                            intent11.putExtra("name", user.nickname);
                            intent11.putExtra("icoon", user.avator);
                            startActivity(intent11);
                            break;
                        default:
                            break;
                    }
                }
            });
            return convertView;
        }

    }

    private class CallCenterAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // Auto-generated method stub
            return callcenterimg.length;
        }

        @Override
        public Object getItem(int position) {
            // Auto-generated method stub
            return callcenterimg == null ? null : callcenterimg[position];
        }

        @Override
        public long getItemId(int position) {
            // Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = View.inflate(PersonalActivity.this,
                    R.layout.maijia_item, null);
            ImageView iv_menuimg = (ImageView) convertView
                    .findViewById(R.id.iv_menuimg);
            TextView tv_menutext = (TextView) convertView
                    .findViewById(R.id.tv_menutext);

            LinearLayout ll_menuitem = (LinearLayout) convertView
                    .findViewById(R.id.ll_menuitem);
            // 高度适配
            LayoutParams layoutParams = ll_menuitem.getLayoutParams();
            layoutParams.height = getScreenWidth(PersonalActivity.this) / 4;
            ll_menuitem.setLayoutParams(layoutParams);

            iv_menuimg.setImageResource(callcenterimg[position]);
            tv_menutext.setText(callcentertext.get(position));
            ll_menuitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (position) {
                        // 平台客服
                        case 0:
                            startActivity(new Intent(PersonalActivity.this,
                                    CustomerServiceActtivity.class));
                            break;
                        // 交易投诉
                        case 1:
                            Intent intent11 = new Intent(PersonalActivity.this,
                                    ComplaintsListActivity.class);
                            startActivity(intent11);
                            break;
                        // 退款退货
                        case 2:
                            Intent intent19 = new Intent(PersonalActivity.this,
                                    RefundActivity.class);
                            startActivity(intent19);
                            break;
                        // 违规举报
                        case 3:
                            // Intent intent20 = new Intent(PersonalActivity.this,
                            // ViolationsReportActivity.class);
                            // startActivity(intent20);
                            break;
                        default:
                            break;
                    }
                }
            });
            return convertView;
        }

    }

    // 我的订单
    private class NavigationAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return navigationimage.length;
        }

        @Override
        public Object getItem(int position) {

            return navigationimage == null ? null : navigationimage[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(PersonalActivity.this,
                        R.layout.navigation_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            // 高度适配
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.ll_navigationitem
                    .getLayoutParams();
            layoutParams.height = getScreenWidth(PersonalActivity.this) / 5;
            holder.ll_navigationitem.setLayoutParams(layoutParams);

            holder.iv_1daizhifufcf.setImageResource(navigationimage[position]);
            holder.tv_navigationname.setText(navigationtext.get(position));
            if (navigationtextnum != null
                    && !navigationtextnum.get(position).equals("0")) {
                holder.tv_new.setVisibility(View.VISIBLE);
                holder.tv_new.setText(navigationtextnum.get(position));
            } else {
                holder.tv_new.setVisibility(View.INVISIBLE);
            }
            holder.ll_navigationitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PersonalActivity.this,
                            BuyerOrderFgActivity.class);
                    switch (position) {
                        case 0:
                            // 待支付
                            intent.putExtra("viewPager", 1);
                            startActivity(intent);
                            break;
                        case 1:
                            // 待发货
                            intent.putExtra("viewPager", 2);
                            startActivity(intent);
                            break;
                        case 2:
                            // 待收货
                            intent.putExtra("viewPager", 3);
                            startActivity(intent);
                            break;
                        case 3:
                            // 购物车
                            Bundle bundle5 = new Bundle();
                            bundle5.putString("shoopping", "shoopping");
                            openActivity(CartActivity.class, bundle5);
                            overrIn();
                            // intent.putExtra("viewPager", 4);
                            break;
                        case 4:
                            // 收藏
                            Intent shoucang = new Intent(PersonalActivity.this,
                                    FavoriteListFargmentActivity.class);
                            shoucang.putExtra("i", 1);
                            startActivity(shoucang);
                            // intent.putExtra("viewPager", 4);
                            break;
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            LinearLayout ll_navigationitem;
            ImageView iv_1daizhifufcf;
            TextView tv_new, tv_navigationname;

            public ViewHolder(View convertView) {
                ll_navigationitem = (LinearLayout) convertView
                        .findViewById(R.id.ll_navigationitem);
                iv_1daizhifufcf = (ImageView) convertView
                        .findViewById(R.id.iv_1daizhifufcf);
                tv_new = (TextView) convertView.findViewById(R.id.tv_new);
                tv_navigationname = (TextView) convertView
                        .findViewById(R.id.tv_navigationname);
                convertView.setTag(this);
            }
        }
    }


    // 获得屏幕宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}
