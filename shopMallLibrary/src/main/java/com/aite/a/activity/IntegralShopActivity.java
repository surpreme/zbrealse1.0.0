package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegralMallInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 积分商城
 *
 * @author Administrator
 */
public class IntegralShopActivity extends BaseActivity implements OnClickListener {

    private LinearLayout ll_integral, ll_vouchers, ll_gift;
    private TextView tv_integral, _tv_name;
    private ImageView _iv_right, _iv_back, iv_adv1;
    private MyGridView mgv_voucherslist;
    private MyGridView mlv_hotgift;
    private MyAdapter myadapter;
    private MygiftAdapter mygiftadapter;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private IntegralMallInfo integralMallInfo;
    private RelativeLayout rl_goods, rl_integral;
    private String voucher_t_id;
    private int imgh = 0;//兑换商品图片高度

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case integral_mall_id:
                    if (msg.obj != null) {
                        integralMallInfo = (IntegralMallInfo) msg.obj;
                        tv_integral
                                .setText(integralMallInfo.member_info.member_points);
                        if (integralMallInfo.recommend_voucher != null
                                && integralMallInfo.recommend_voucher.size() != 0) {
                            rl_integral.setVisibility(View.VISIBLE);
                            myadapter = new MyAdapter(
                                    integralMallInfo.recommend_voucher);
                            mgv_voucherslist.setAdapter(myadapter);
                        } else {
                            rl_integral.setVisibility(View.GONE);
                        }
                        if (integralMallInfo.recommend_pointsprod != null
                                && integralMallInfo.recommend_pointsprod.size() != 0) {
                            rl_goods.setVisibility(View.VISIBLE);
                            mygiftadapter = new MygiftAdapter(
                                    integralMallInfo.recommend_pointsprod);
                            mlv_hotgift.setAdapter(mygiftadapter);

                        } else {
                            rl_goods.setVisibility(View.GONE);
                        }

                    }
                    break;
                case integral_mall_err:
                    Toast.makeText(IntegralShopActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case credits_exchange_id:
                    if (msg.obj != null) {
                        try {
                            String str = (String) msg.obj;
                            Toast.makeText(IntegralShopActivity.this, str,
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Intent intent2 = new Intent(IntegralShopActivity.this,
                                    CreditsExchangeActivity.class);
                            intent2.putExtra("vid", voucher_t_id);
                            startActivity(intent2);
                        }
                    }
                    break;
                case credits_exchange_err:
                    Toast.makeText(IntegralShopActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralmall);
        findViewById();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        ll_integral = (LinearLayout) findViewById(R.id.ll_integral);
        ll_vouchers = (LinearLayout) findViewById(R.id.ll_vouchers);
        ll_gift = (LinearLayout) findViewById(R.id.ll_gift);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_right = (ImageView) findViewById(R.id._iv_right);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        mgv_voucherslist = (MyGridView) findViewById(R.id.mgv_voucherslist);
        mlv_hotgift = findViewById(R.id.mlv_hotgift);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        rl_goods = (RelativeLayout) findViewById(R.id.rl_goods);
        rl_integral = (RelativeLayout) findViewById(R.id.rl_integral);
        iv_adv1 = findViewById(R.id.iv_adv1);
        mgv_voucherslist.setFocusable(false);
        mlv_hotgift.setFocusable(false);
        _iv_back.setFocusable(true);
        _iv_back.setFocusableInTouchMode(true);
        _iv_back.requestFocus();
        imgh = ClutterUtils.getScreenWidth(this) / 2;
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        ll_integral.setOnClickListener(this);
        ll_vouchers.setOnClickListener(this);
        ll_gift.setOnClickListener(this);
        _iv_right.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        rl_integral.setOnClickListener(this);
        rl_goods.setOnClickListener(this);
        _tv_name.setText(getI18n(R.string.integral_mall));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_adv1.getLayoutParams();
        layoutParams.height = (int) (ClutterUtils.getScreenWidth(this) * 0.325);
        iv_adv1.setLayoutParams(layoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        netRun.IntegralMall();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ll_integral:
//                // 积分
//                Intent intent2 = new Intent(IntegralShopActivity.this,
//                        IntegralInfoActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.ll_vouchers:
//                // 可用代金券
//                Intent intent3 = new Intent(IntegralShopActivity.this,
//                        VouchersListActivity.class);
//                startActivity(intent3);
//                break;
//            case R.id.ll_gift:
//                // 已兑换礼品
//                Intent intent4 = new Intent(IntegralShopActivity.this,
//                        ExchangeRecordActivity.class);
//                startActivity(intent4);
//                break;
//            case R.id._iv_right:
//
//                break;
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.rl_integral:
//                // 热门代金券
//                Intent intent5 = new Intent(IntegralShopActivity.this, HotVouchersListActivity.class);
//                startActivity(intent5);
//                break;
//            case R.id.rl_goods:
//                // 热门礼品
//                Intent intent6 = new Intent(IntegralShopActivity.this, HotIntegralGiftListActivity.class);
//                startActivity(intent6);
//                break;
//        }
        if(v.getId()==R.id.ll_integral){
            // 积分
            Intent intent2 = new Intent(IntegralShopActivity.this,
                    IntegralInfoActivity.class);
            startActivity(intent2);
        }else if(v.getId()== R.id.ll_vouchers){
            // 可用代金券
            Intent intent3 = new Intent(IntegralShopActivity.this,
                    VouchersListActivity.class);
            startActivity(intent3);
        }else if(v.getId()==R.id.ll_gift){
            // 已兑换礼品
            Intent intent4 = new Intent(IntegralShopActivity.this,
                    ExchangeRecordActivity.class);
            startActivity(intent4);
        }else if(v.getId()== R.id._iv_back){
            finish();
        }else if(v.getId()== R.id.rl_integral){
            // 热门代金券
            Intent intent5 = new Intent(IntegralShopActivity.this, HotVouchersListActivity.class);
            startActivity(intent5);
        }else if(v.getId()==R.id.rl_goods){
            // 热门礼品
            Intent intent6 = new Intent(IntegralShopActivity.this, HotIntegralGiftListActivity.class);
            startActivity(intent6);
        }
    }

    /**
     * 热门代金券适配
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {
        List<IntegralMallInfo.recommend_voucher> recommend_voucher;

        public MyAdapter(List<IntegralMallInfo.recommend_voucher> recommend_voucher) {
            this.recommend_voucher = recommend_voucher;
        }

        @Override
        public int getCount() {
            return recommend_voucher.size();
        }

        @Override
        public Object getItem(int position) {
            return recommend_voucher == null ? null : recommend_voucher
                    .get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(IntegralShopActivity.this,
                        R.layout.hot_vouchers_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final IntegralMallInfo.recommend_voucher recommend_voucher2 = recommend_voucher
                    .get(position);
            holder.tv_shop_name.setText(recommend_voucher2.voucher_t_storename);
            holder.tv_cost.setText("￥" + recommend_voucher2.voucher_t_price);
            holder.tv_statement.setText(recommend_voucher2.voucher_t_desc);
            holder.tv_valid_time.setText(getString(R.string.evaluation_tips18)
                    + TimeStamp2Date(recommend_voucher2.voucher_t_end_date,
                    "yyyy-MM-dd HH:mm"));
            holder.tv_need.setText(getString(R.string.need)+ recommend_voucher2.voucher_t_points
                    + getString(R.string.integrall));
//			holder.bt_exchange.setText("兑换");
            holder.ll_item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 兑换
                    netRun.CreditsExchange(recommend_voucher2.voucher_t_id);
                    voucher_t_id = recommend_voucher2.voucher_t_id;
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_shop_name, tv_cost, tv_statement, tv_valid_time,
                    tv_need;
            LinearLayout ll_item;

            public ViewHolder(View convertView) {
                tv_shop_name = (TextView) convertView
                        .findViewById(R.id.tv_shop_name);
                tv_cost = (TextView) convertView.findViewById(R.id.tv_cost);
                tv_statement = (TextView) convertView
                        .findViewById(R.id.tv_statement);
                tv_valid_time = (TextView) convertView
                        .findViewById(R.id.tv_valid_time);
                tv_need = (TextView) convertView.findViewById(R.id.tv_need);
                ll_item = convertView.findViewById(R.id.ll_item);
                convertView.setTag(this);
            }
        }

    }

    /**
     * 热门礼品适配
     *
     * @author Administrator
     */
    private class MygiftAdapter extends BaseAdapter {
        List<IntegralMallInfo.recommend_pointsprod> recommend_pointsprod;

        public MygiftAdapter(List<IntegralMallInfo.recommend_pointsprod> recommend_pointsprod) {
            this.recommend_pointsprod = recommend_pointsprod;
        }

        @Override
        public int getCount() {
            return recommend_pointsprod.size();
        }

        @Override
        public Object getItem(int position) {
            return recommend_pointsprod == null ? null : recommend_pointsprod
                    .get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(IntegralShopActivity.this,
                        R.layout.hot_gift_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final IntegralMallInfo.recommend_pointsprod recommend_pointsprod2 = recommend_pointsprod
                    .get(position);
            bitmapUtils.display(holder.iv_gift_image,
                    recommend_pointsprod2.pgoods_image);
            holder.tv_gift_goodsname.setText(recommend_pointsprod2.pgoods_name);
            holder.tv_costnum.setText("￥" + recommend_pointsprod2.pgoods_price);
            holder.tv_points.setText(recommend_pointsprod2.pgoods_points +  getString(R.string.integrall));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.iv_gift_image.getLayoutParams();
            layoutParams.height = imgh;
            holder.iv_gift_image.setLayoutParams(layoutParams);
            holder.rl_item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(IntegralShopActivity.this, IntegralGoodsInfoActivity.class);
                    intent2.putExtra("id", recommend_pointsprod2.pgoods_id);
                    startActivity(intent2);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_gift_image, iv_gift_level;
            TextView tv_gift_goodsname, tv_costnum, tv_points;
            RelativeLayout rl_item;

            public ViewHolder(View convertView) {
                iv_gift_image = (ImageView) convertView
                        .findViewById(R.id.iv_gift_image);
                iv_gift_level = (ImageView) convertView
                        .findViewById(R.id.iv_gift_level);
                tv_gift_goodsname = (TextView) convertView
                        .findViewById(R.id.tv_gift_goodsname);
                tv_costnum = (TextView) convertView
                        .findViewById(R.id.tv_costnum);
                tv_points = (TextView) convertView.findViewById(R.id.tv_points);
                rl_item = convertView.findViewById(R.id.rl_item);
                convertView.setTag(this);
            }
        }

    }

    /**
     * 时间戳转时间
     *
     * @param timestampString 时间戳
     * @param formats         格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }

}
