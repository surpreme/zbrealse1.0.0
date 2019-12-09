package com.aite.a.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.li.adapter.GuessLikeAdapter;
import com.aite.a.activity.li.bean.GuessLikeBean;
import com.aite.a.adapter.MeMenu1Adapter;
import com.aite.a.adapter.MeMenu2Adapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.MeMenu2info;
import com.aite.a.model.MeMenuinfo;
import com.aite.a.model.User;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心
 * Created by mayn on 2018/4/26.
 */
public class MeActivity extends BaseActivity implements View.OnClickListener {
    private TextView personal_tv_name, tv_integral, tv_freeze, tv_redenvelope, tv_voucher, tv_balance, tv_withdrawals;
    private CircleImageView personal_iv_avatr;
    private RecyclerView guesslike_recy;
    ImageView iv_more;
    private RelativeLayout rl_allorder;
    private MyGridView mgv_navigation;
    private MyListView mgv_menu;
    private MeMenu1Adapter meMenu1Adapter;
    private MeMenu2Adapter meMenu2Adapter;
    private List<GuessLikeBean.DatasBean> mDatas = new ArrayList<>();
    protected User user;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case member_id:
                    if (msg.obj != null) {
                        user = (User) msg.obj;
                        init(user);
                    }
                    break;
                case member_err:
                    Toast.makeText(MeActivity.this, R.string.systembusy,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        findViewById();
    }

    @Override
    protected void findViewById() {
        guesslike_recy = findViewById(R.id.guess_like_recy);
        personal_tv_name = (TextView) findViewById(R.id.personal_tv_name);
        tv_freeze = (TextView) findViewById(R.id.tv_freeze);
        tv_withdrawals = (TextView) findViewById(R.id.tv_withdrawals);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_redenvelope = (TextView) findViewById(R.id.tv_redenvelope);
        tv_voucher = (TextView) findViewById(R.id.tv_voucher);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        findViewById(R.id.wallet_balance_ll).setOnClickListener(this);
        findViewById(R.id.wallet_freeze_ll).setOnClickListener(this);
        findViewById(R.id.wallet_integral_ll).setOnClickListener(this);
        findViewById(R.id.wallet_present_ll).setOnClickListener(this);
        findViewById(R.id.wallet_vouchers_ll).setOnClickListener(this);
        personal_iv_avatr = (CircleImageView) findViewById(R.id.personal_iv_avatr);
        rl_allorder = (RelativeLayout) findViewById(R.id.rl_allorder);
        mgv_navigation = (MyGridView) findViewById(R.id.mgv_navigation);
        mgv_menu = (MyListView) findViewById(R.id.mgv_menu);
        iv_more = findViewById(R.id.iv_more);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        personal_iv_avatr.setOnClickListener(this);

        tv_withdrawals.setOnClickListener(this);
        rl_allorder.setOnClickListener(this);

        tv_integral.setOnClickListener(this);
        tv_freeze.setOnClickListener(this);
        tv_redenvelope.setOnClickListener(this);
        tv_voucher.setOnClickListener(this);
        tv_balance.setOnClickListener(this);
        iv_more.setOnClickListener(this);
        initData();

    }

    @Override
    protected void initData() {
        final GuessLikeAdapter guessLikeAdapter = new GuessLikeAdapter(this, mDatas);
        guesslike_recy.setAdapter(guessLikeAdapter);
        guesslike_recy.setLayoutManager(new GridLayoutManager(this, 2));
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
        netRun.getMember();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_right) {
            Intent intent = new Intent(MeActivity.this, MoreActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.personal_iv_avatr) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("icon", user.avator);
            openActivity(PersonalInformationActivity.class, bundle3);
        } else if (v.getId() == R.id.wallet_vouchers_ll) {
            Intent intent1 = new Intent(MeActivity.this, VouchersListActivity.class);
            startActivity(intent1);
        } else if (v.getId() == R.id.wallet_present_ll) {
            // 红包
            Intent intenthb = new Intent(MeActivity.this,
                    RedPackageActivityList.class);
            startActivity(intenthb);
        } else if (v.getId() == R.id.wallet_balance_ll) {
            // 余额
            Intent intentye = new Intent(MeActivity.this,
                    MyMoneyActivity.class);
            intentye.putExtra("isfreeze", false);
            startActivity(intentye);
        } else if (v.getId() == R.id.wallet_freeze_ll) {
            // 佣金
            Intent intentdj = new Intent(MeActivity.this,
                    MyMoneyActivity.class);
            intentdj.putExtra("isfreeze", true);
            startActivity(intentdj);
        } else if (v.getId() == R.id.wallet_integral_ll) {
            // 积分
            Intent intentjf = new Intent(MeActivity.this,
                    IntegralInfoActivity.class);
            startActivity(intentjf);
        } else if (v.getId() == R.id.tv_withdrawals) {
            // 提现
            intent = new Intent(this, BalanceTxActivity.class);
            intent.putExtra("predepoit", user.predepoit);
            startActivity(intent);
        } else if (v.getId() == R.id.rl_allorder) {
            Intent intentt = new Intent(this, BuyerOrderFgActivity.class);
            intentt.putExtra("viewPager", 0);
            startActivity(intentt);
        } else if (v.getId() == R.id.iv_more) {
            Intent intent32 = new Intent(this, MoreActivity.class);
            startActivity(intent32);
        }
//        switch (v.getId()) {
//            case R.id._iv_right:
//                Intent intent = new Intent(MeActivity.this, MoreActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.personal_iv_avatr:
//                Bundle bundle3 = new Bundle();
//                bundle3.putString("icon", user.avator);
//                openActivity(PersonalInformationActivity.class, bundle3);
////                Intent intent18 = new Intent(MeActivity.this, WebActivity.class);
////                intent18.putExtra("url", "https://aitecc.com/wap/index.php?act=member_information&op=member");
////                startActivity(intent18);
//                break;
//            case R.id.wallet_vouchers_ll:
//                Intent intent1 = new Intent(MeActivity.this, VouchersListActivity.class);
//                startActivity(intent1);
////                Uri uri = Uri.parse("http://aitecc.com/wap/index.php?act=pointshop");
////                Intent intentu = new Intent(Intent.ACTION_VIEW, uri);
////                startActivity(intentu);
//                break;
//            case R.id.wallet_present_ll:
//                // 红包
//                Intent intenthb = new Intent(MeActivity.this,
//                        RedPackageActivityList.class);
//                startActivity(intenthb);
//                break;
//            case R.id.wallet_balance_ll:
//                // 余额
//                Intent intentye = new Intent(MeActivity.this,
//                        MyMoneyActivity.class);
//                intentye.putExtra("isfreeze", false);
//                startActivity(intentye);
//                break;
//            case R.id.wallet_freeze_ll:
//                // 佣金
//                Intent intentdj = new Intent(MeActivity.this,
//                        MyMoneyActivity.class);
//                intentdj.putExtra("isfreeze", true);
//                startActivity(intentdj);
//                break;
//            case R.id.wallet_integral_ll:
//                // 积分
//                Intent intentjf = new Intent(MeActivity.this,
//                        IntegralInfoActivity.class);
//                startActivity(intentjf);
//                break;
//            case R.id.tv_withdrawals:
//                // 提现
//                intent = new Intent(this, BalanceTxActivity.class);
//                intent.putExtra("predepoit", user.predepoit);
//                startActivity(intent);
//                break;//订单页面 代付款 待发货 已发货 待评价
//            case R.id.rl_allorder:
//                Intent intentt = new Intent(this, BuyerOrderFgActivity.class);
//                intentt.putExtra("viewPager", 0);
//                startActivity(intentt);
//                break;
//            case R.id.iv_more:
//                Intent intent32 = new Intent(this, MoreActivity.class);
//                startActivity(intent32);
//                break;
//        }
    }

    /**
     * 初始化
     *
     * @param user
     */
    private void init(User user) {
        personal_tv_name.setText(user.nickname == null ? "" : user.nickname.equals("null") ? "" : user.nickname);
//        Glide.with(MeActivity.this).load(user.avator).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(personal_iv_avatr);
        if (user.avator != null && !user.avator.equals("null")
                && !user.avator.equals("")) {
            new DownloadImageTask().execute(user.avator);
        }
        tv_integral.setText(user.point);
        tv_freeze.setText(user.freeze_predeposit);
        tv_redenvelope.setText(user.redpackage_count);
        tv_voucher.setText(user.vouchercount);
        tv_balance.setText(user.predepoit);

        //我的订单数据
        List<MeMenuinfo> data = new ArrayList<>();
        data.add(new MeMenuinfo(R.drawable.me_menu1icon1, getString(R.string.distribution_center46), user.ORDER_STATE_NEW));
        data.add(new MeMenuinfo(R.drawable.me_menu1icon2, getString(R.string.storehome39), user.ORDER_STATE_PAY));
        data.add(new MeMenuinfo(R.drawable.me_menu1icon3, getString(R.string.delivered), user.ORDER_STATE_SEND));
        data.add(new MeMenuinfo(R.drawable.me_menu1icon4, getString(R.string.member_centre6), user.ORDER_STATE_SUCCESS));
        data.add(new MeMenuinfo(R.drawable.me_menu2icon1, getString(R.string.buyer_orders), "0"));
        data.add(new MeMenuinfo(R.drawable.me_menu2icon4, getString(R.string.my_shopping_cart), "0"));
        data.add(new MeMenuinfo(R.drawable.me_menu2icon2, getString(R.string.virtualorders), "0"));
        data.add(new MeMenuinfo(R.drawable.me_menu1icon5, getString(R.string.member_centre7), "0"));
        meMenu1Adapter = new MeMenu1Adapter(MeActivity.this, data);
        mgv_navigation.setAdapter(meMenu1Adapter);//我的订单

        List<MeMenu2info> data2 = new ArrayList<>();//菜单数据
/**************************买家中心***********************************/
        List<MeMenu2info.item> item1 = new ArrayList<>();
//        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon1, getString(R.string.buyer_orders), "0"));
//        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon2, getString(R.string.virtualorders), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon14, getString(R.string.collectionfcf), "0"));//店铺收藏
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon5, getString(R.string.my_footprint), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon15, getString(R.string.myevaluation), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon6, getString(R.string.personal_info), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon9, getString(R.string.distribution_center25), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon16, getString(R.string.distribution_center27), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon19, getString(R.string.address_manage), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon8, getString(R.string.distribution), "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2iconchongzhi, "在线充值", "0"));
        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon3, getString(R.string.gif2), "0"));
//        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon11,getString(R.string.discover),"0"));
//        item1.add(new MeMenu2info.item(R.drawable.me_menu2icon13, getString(R.string.collectionfcf2), "0"));

        data2.add(new MeMenu2info(item1, getString(R.string.buyers)));
//        item1.add(new MeMenu2info.item(R.drawable.kefu,"平台客服","0"));
        /***************会员福利****************/
        List<MeMenu2info.item> item2 = new ArrayList<>();
        item2.add(new MeMenu2info.item(R.drawable.report, "签到", "0"));
        item2.add(new MeMenu2info.item(R.drawable.forum, "红包活动", "0"));
//        item2.add(new MeMenu2info.item(R.drawable.live_show, getString(R.string.order_reminder163), "0"));
        data2.add(new MeMenu2info(item2, getString(R.string.welfare)));
//        List<MeMenu2info.item>item2=new ArrayList<>();
//        item2.add(new MeMenu2info.item(R.drawable.integral,"积分","0"));
//        item2.add(new MeMenu2info.item(R.drawable.shequ,"社区","0"));
//        item2.add(new MeMenu2info.item(R.drawable.weifaxian,"微发现","0"));
//        item2.add(new MeMenu2info.item(R.drawable.open_shop,"酒店","0"));
//        item2.add(new MeMenu2info.item(R.drawable.hotel_order,"酒店订单","0"));
//        item2.add(new MeMenu2info.item(R.drawable.service_menu,"服务","0"));
//
        /***************平台服务****************/
        List<MeMenu2info.item> item3 = new ArrayList<>();
//        item3.add(new MeMenu2info.item(R.drawable.tousu,"交易投诉","0"));
        item3.add(new MeMenu2info.item(R.drawable.tousu, getString(R.string.tradecomplaint), "0"));
        item3.add(new MeMenu2info.item(R.drawable.me_menu2icon12, getString(R.string.stand_inside_letter), "0"));
//        item3.add(new MeMenu2info.item(R.drawable.kefu,"退款退货","0"));
        item3.add(new MeMenu2info.item(R.drawable.me_menu2icon17, getString(R.string.integral_prompt19), "0"));
        item3.add(new MeMenu2info.item(R.drawable.me_menu2icon7, getString(R.string.update_password), "0"));
        item3.add(new MeMenu2info.item(R.drawable.me_menu2icon20, getString(R.string.set_up), "0"));
        data2.add(new MeMenu2info(item3, "平台服务"));

        /***************平台应用****************/
        List<MeMenu2info.item> item4 = new ArrayList<>();
//        item3.add(new MeMenu2info.item(R.drawable.kefu,"退款退货","0"));
        item4.add(new MeMenu2info.item(R.drawable.news, "新闻", "0"));
        item4.add(new MeMenu2info.item(R.drawable.me_menu2icon10, getString(R.string.integral_mall), "0"));
        item4.add(new MeMenu2info.item(R.drawable.brand, "品牌", "0"));
        item4.add(new MeMenu2info.item(R.drawable.my_circle, "圈子", "0"));
//        item4.add(new MeMenu2info.item(R.drawable.find, "发现", "0"));
        item4.add(new MeMenu2info.item(R.drawable.show_order, "晒单", "0"));
        data2.add(new MeMenu2info(item4, "平台应用"));
//        if (user.store_id != null && !user.store_id.equals("0")) {
//            //  显示卖家
//            List<MeMenu2info.item>item4=new ArrayList<>();
//            item4.add(new MeMenu2info.item(R.drawable.shangjiadianpu,"我的店铺","0"));
//            item4.add(new MeMenu2info.item(R.drawable.shangjiadingdan,"商家订单","0"));
//            data2.add(new MeMenu2info(item4,"卖家中心"));
//        }

        meMenu2Adapter = new MeMenu2Adapter(MeActivity.this, data2, user);//菜单
        mgv_menu.setAdapter(meMenu2Adapter);
    }

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
    public void ReGetData() {

    }
}
