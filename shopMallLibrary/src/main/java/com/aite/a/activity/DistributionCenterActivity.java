package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.DistributionCenterInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 分销中心
 *
 * @author Administrator
 */
public class DistributionCenterActivity extends BaseActivity implements
        OnClickListener {

    private ImageView iv_bcreturn;
    private TextView tv_bctitle, tv_namme, tv_money, tv_integral, tv_fans,
            tv_withdrawals, tv_price;
    private CircleImageView cv_icon;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private DistributionCenterInfo distributionCenterInfo;
    private MyGridView mv_menu;
    private MyAdapter myAdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case distribution_center_id:
                    if (msg.obj != null) {
                        distributionCenterInfo = (DistributionCenterInfo) msg.obj;
                        bitmapUtils.display(cv_icon, distributionCenterInfo.avator);
                        tv_namme.setText(distributionCenterInfo.nickname);
                        tv_money.setText(distributionCenterInfo.available_predeposit);
                        tv_integral.setText(distributionCenterInfo.member_points);
                        tv_fans.setText(distributionCenterInfo.wx_member_count);
                        tv_price.setText(distributionCenterInfo.commission_total);
                    } else {
                        Toast.makeText(DistributionCenterActivity.this, getString(R.string.distribution_center11), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                case distribution_center_err:
                    Toast.makeText(DistributionCenterActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributioncenter);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
        tv_bctitle = (TextView) findViewById(R.id._tv_name);
        tv_namme = (TextView) findViewById(R.id.tv_namme);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_fans = (TextView) findViewById(R.id.tv_fans);
        tv_withdrawals = (TextView) findViewById(R.id.tv_withdrawals);
        cv_icon = (CircleImageView) findViewById(R.id.cv_icon);
        tv_bctitle.setText(getString(R.string.distribution_center5));
        mv_menu = (MyGridView) findViewById(R.id.mv_menu);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        iv_bcreturn.setOnClickListener(this);
        tv_withdrawals.setOnClickListener(this);
        munutxt = new String[]{getString(R.string.distribution_center6), getString(R.string.distribution_center7),
                getString(R.string.distribution_center8), getString(R.string.myredpackage),
                getString(R.string.rich), getString(R.string.distribution_center9)};
        initData();
    }

    @Override
    protected void initData() {
        myAdapter = new MyAdapter();
        mv_menu.setAdapter(myAdapter);
        mv_menu.setOnItemClickListener(l);
        netRun.DistributionCenter();
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
//            case R.id.tv_withdrawals:
//                // 提现
//                Intent intent7 = new Intent(DistributionCenterActivity.this,
//                        BalanceTxActivity.class);
//                startActivity(intent7);
//                break;
//        }
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_withdrawals) {
            // 提现
            Intent intent7 = new Intent(DistributionCenterActivity.this,
                    BalanceTxActivity.class);
            startActivity(intent7);
        }
    }

    private OnItemClickListener l = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            switch (position) {
                case 0:
                    // 我的推荐人
                    Intent intent2 = new Intent(DistributionCenterActivity.this,
                            MyRefereeActivity.class);
                    startActivity(intent2);
                    break;
                case 1:
                    // 我的团队
                    Intent intent3 = new Intent(DistributionCenterActivity.this,
                            MyTeamActivity.class);
                    startActivity(intent3);
                    break;
                case 2:
                    // 我的佣金
                    Intent intent5 = new Intent(DistributionCenterActivity.this,
                            MyMoneyActivity.class);
                    startActivity(intent5);
                    break;
                case 3:
                    // 我的红包
                    Intent intent8 = new Intent(DistributionCenterActivity.this,
                            RedPackageActivity.class);
                    startActivity(intent8);

                    break;
                case 4:
                    // 富豪榜
                    Intent intent7 = new Intent(DistributionCenterActivity.this,
                            RichListActivity.class);
                    startActivity(intent7);
                    break;
                case 5:
                    // 申请提现
                    Intent intent6 = new Intent(DistributionCenterActivity.this,
                            BalanceTxActivity.class);
                    intent6.putExtra("money",
                            distributionCenterInfo.available_predeposit);
                    startActivity(intent6);
                    break;
            }
        }
    };

    private String[] munutxt;
    private int[] menuimg = new int[]{R.drawable.distribution_menuicon1,
            R.drawable.distribution_menuicon2, R.drawable.distribution_menuicon3, R.drawable.distribution_menuicon4,
            R.drawable.distribution_menuicon5, R.drawable.distribution_menuicon6};

    /**
     * 分销中心
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return munutxt.length;
        }

        @Override
        public Object getItem(int position) {
            return munutxt == null ? null : munutxt[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(DistributionCenterActivity.this,
                        R.layout.item_distributionmenu, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_name.setText(munutxt[position]);
            holder.iv_img.setImageResource(menuimg[position]);
            return convertView;
        }

        class ViewHolder {
            TextView tv_name;
            ImageView iv_img;

            public ViewHolder(View convertView) {
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                convertView.setTag(this);
            }
        }
    }

}
