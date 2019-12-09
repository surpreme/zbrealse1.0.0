package com.aite.a.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.SellerOrderAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.MyStoreInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import courier.CourierHomeTabActivity;
import courier.OpenPostActivity;

/**
 * 我的店铺
 *
 * @author CAOYOU
 */
public class MyStoreActivity extends BaseActivity implements OnClickListener {
    private CircleImageView store_img;
    private TextView tv_store_name, tv_level;
    private TextView tv_balance, _tv_name;
    private TextView tv_unShelve, tv_all_goods, tv_sold_out;
    private LinearLayout goods_manage, post_trade, order_manage, more;
    private SellerOrderAdapter orderAdapter;
    private TextView _tx_right, tv_area_info, tv_yesterdaysales, tv_month,
            tv_today, tv_yesterday;
    private NetRun netRun;
    private ImageView _iv_mystore;
    private BitmapUtils bitmapUtils;
    private ImageView _iv_back;
    private RelativeLayout tv_store_banner;
    private MyStoreInfo storeInfo = new MyStoreInfo();
    private ImageView iv_returnn, _iv_right;
    private TextView tv_openshop, tv_ts;
    private View i_to_open_shop;
    private ImageView tv_gengduo;

    //权限信息
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case my_store_id:
                    if (msg.obj != null) {
                        i_to_open_shop.setVisibility(View.GONE);
                        storeInfo = (MyStoreInfo) msg.obj;

                        if (!storeInfo.store_info.store_type.equals("0") && !storeInfo.store_info.store_type.equals("1")) {
                            Toast.makeText(MyStoreActivity.this, getString(R.string.system_prompt), Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        if (storeInfo.store_info.store_avatar != null && !storeInfo.store_info.store_avatar.equals("")
                                && !storeInfo.store_info.store_avatar.equals("")) {
                            bitmapUtils.display(store_img,
                                    storeInfo.store_info.store_avatar);
                        }
                        //背景
                        if (storeInfo.store_info.store_banner != null
                                && !storeInfo.store_info.store_banner.equals("")
                                && !storeInfo.store_info.store_banner.equals("null")) {
                            bitmapUtils.display(_iv_mystore,
                                    storeInfo.store_info.store_banner);
                        }
                        if (storeInfo.store_info.store_avatar != null && !storeInfo.store_info.store_avatar.equals("")) {
                            bitmapUtils.display(store_img,
                                    storeInfo.store_info.store_avatar);
                        }
                        tv_yesterdaysales.setText(storeInfo.daily_sales
                                + "");
                        tv_month.setText(storeInfo.monthly_sales + "");
                        tv_store_name.setText(storeInfo.store_info.store_name);
                        tv_area_info.setText(getString(R.string.location) + "："
                                + storeInfo.store_info.area_info);
//                        tv_balance.setText(Float.valueOf(storeInfo.total_money)
//                                + "MOP");
                        tv_today.setText(Float.valueOf(storeInfo.total_money)
                                + "MOP");
                        tv_unShelve.setText(storeInfo.onsell);
                        tv_all_goods.setText(storeInfo.all_shop_count);
                        tv_sold_out.setText(storeInfo.sold_out);
                        tv_yesterday.setText(storeInfo.Yesterday_total + "MOP");
                    } else {
//                        Toast.makeText(MyStoreActivity.this, "没有数据可加载! ", Toast.LENGTH_SHORT).show();
                        i_to_open_shop.setVisibility(View.VISIBLE);
                    }
                    break;
                case my_store_err:
                    Toast.makeText(MyStoreActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case my_store_start:
                    break;
                case member_index_id:
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("-1")) {
                            tv_ts.setText(getString(R.string.ruzhuinput17));
                            tv_openshop.setVisibility(View.GONE);
                        }
                    }
                    break;

            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_store);
        //权限
        getPersimmions();
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        findViewById();
        initView();
        initData();
    }

    @Override
    protected void findViewById() {
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tx_right = (TextView) findViewById(R.id._tx_right);
        tv_yesterdaysales = (TextView) findViewById(R.id.tv_yesterdaysales);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_yesterday = (TextView) findViewById(R.id.tv_yesterday);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_level = (TextView) findViewById(R.id.tv_level);
        store_img = (CircleImageView) findViewById(R.id.store_img);
        tv_store_name = (TextView) findViewById(R.id.tv_store_name);
        tv_store_banner = (RelativeLayout) findViewById(R.id.store_banner);
        tv_area_info = (TextView) findViewById(R.id.tv_area_info);
        tv_balance = (TextView) findViewById(R.id.balance);
        tv_unShelve = (TextView) findViewById(R.id.unShelve);
        tv_all_goods = (TextView) findViewById(R.id.all_goods);
        tv_sold_out = (TextView) findViewById(R.id.sold_out);
        goods_manage = (LinearLayout) findViewById(R.id.goods_manage);
        post_trade = (LinearLayout) findViewById(R.id.post_trade);
        order_manage = (LinearLayout) findViewById(R.id.order_manage);
        _iv_mystore = (ImageView) findViewById(R.id._iv_mystore);
        iv_returnn = (ImageView) findViewById(R.id.iv_returnn);
        tv_openshop = (TextView) findViewById(R.id.tv_openshop);
        i_to_open_shop = findViewById(R.id.i_to_open_shop);
        _iv_right = (ImageView) findViewById(R.id._iv_right);
        tv_gengduo = (ImageView) findViewById(R.id.tv_gengduo);
        tv_ts = (TextView) findViewById(R.id.tv_ts);
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.storehome29));
        store_img.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        tv_unShelve.setOnClickListener(this);
        tv_all_goods.setOnClickListener(this);
        tv_sold_out.setOnClickListener(this);
        goods_manage.setOnClickListener(this);
        post_trade.setOnClickListener(this);
        order_manage.setOnClickListener(this);
        iv_returnn.setOnClickListener(this);
        tv_openshop.setOnClickListener(this);
        _iv_right.setOnClickListener(this);
        tv_gengduo.setOnClickListener(this);
        tv_level.setOnClickListener(this);
        _iv_right.setVisibility(View.VISIBLE);
        _iv_right.setBackgroundResource(R.drawable.morefcf);
        orderAdapter = new SellerOrderAdapter(this, handler);
    }

    @Override
    protected void initData() {
        netRun.MemberIndex();
        Log.i("----------------", " key  " + State.UserKey);
        netRun.getMyStoreData();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        netRun.getMyStoreData();
//    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id._iv_back){
            finish();
        }else if(v.getId()==R.id.goods_manage){
            //商品管理
            Intent intent = new Intent(MyStoreActivity.this, GoodsManageActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.post_trade){
            //发布宝贝
            Intent intent1 = new Intent(MyStoreActivity.this, ReleaseGoodsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("goods", "add");
            intent1.putExtras(bundle);
            startActivity(intent1);
        }else if(v.getId()==R.id.order_manage){
            //订单管理
            Intent intent3 = new Intent(MyStoreActivity.this, SellerOrderActivity.class);
            startActivity(intent3);
        }else if(v.getId()== R.id.iv_returnn){
            finish();
        }else if(v.getId()== R.id.tv_openshop){
            //去开店
            Intent intent5 = new Intent(MyStoreActivity.this, FreeOpenShopActivity.class);
            startActivity(intent5);
        }else if(v.getId()==R.id._iv_right){
            //更多
            Intent intent6 = new Intent(MyStoreActivity.this, MoreActivity.class);
            startActivity(intent6);
        }else if(v.getId()==R.id.tv_gengduo){
            Intent intent7 = new Intent(MyStoreActivity.this, MoreActivity.class);
            startActivity(intent7);
        }else if(v.getId()==R.id.tv_level){
            if (storeInfo.store_info.dlyp_state.equals("0")||storeInfo.store_info.dlyp_state.equals("20")) {
                //申请开通驿站
                Intent intent8 = new Intent(MyStoreActivity.this, OpenPostActivity.class);
                startActivity(intent8);
            } else if (storeInfo.store_info.dlyp_state.equals("1")){
                //进入驿站
                Intent intent8 = new Intent(MyStoreActivity.this,CourierHomeTabActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent8);
//                    Intent intent8 = new Intent(MyStoreActivity.this, CourierHomeTabActivity.class);
//                    startActivity(intent8);
            }else if (storeInfo.store_info.dlyp_state.equals("10")){
                Toast.makeText(MyStoreActivity.this, getString(R.string.member_centre22), Toast.LENGTH_SHORT).show();
            }
        }
//        switch (v.getId()) {
//            case R.id.store_img:
//                // openActivity(MyStoreDataActivity.class);
//                break;
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.goods_manage:
//                //商品管理
//                Intent intent = new Intent(MyStoreActivity.this, GoodsManageActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.post_trade:
////                Bundle bundle = new Bundle();
////                bundle.putString("goods", "add");
////                Intent inten2 = new Intent(MyStoreActivity.this, PostTradeActivity.class);
////                inten2.putExtras(bundle);
////                startActivity(inten2);
//
//                //发布宝贝
//                Intent intent1 = new Intent(MyStoreActivity.this, ReleaseGoodsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("goods", "add");
//                intent1.putExtras(bundle);
//                startActivity(intent1);
//                break;
//            case R.id.order_manage:
//                //订单管理
//                Intent intent3 = new Intent(MyStoreActivity.this, SellerOrderActivity.class);
//                startActivity(intent3);
//                break;
//            case R.id.iv_returnn:
//                finish();
//                break;
//            case R.id.tv_openshop:
//                //去开店
//                Intent intent5 = new Intent(MyStoreActivity.this, FreeOpenShopActivity.class);
//                startActivity(intent5);
//                break;
//            case R.id._iv_right:
//                //更多
//                Intent intent6 = new Intent(MyStoreActivity.this, MoreActivity.class);
//                startActivity(intent6);
//                break;
//            case R.id.tv_gengduo:
//                Intent intent7 = new Intent(MyStoreActivity.this, MoreActivity.class);
//                startActivity(intent7);
//                break;
//            case R.id.tv_level:
//                if (storeInfo.store_info.dlyp_state.equals("0")||storeInfo.store_info.dlyp_state.equals("20")) {
//                    //申请开通驿站
//                    Intent intent8 = new Intent(MyStoreActivity.this, OpenPostActivity.class);
//                    startActivity(intent8);
//                } else if (storeInfo.store_info.dlyp_state.equals("1")){
//                    //进入驿站
//                    Intent intent8 = new Intent(MyStoreActivity.this,CourierHomeTabActivity.class)
//                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent8);
////                    Intent intent8 = new Intent(MyStoreActivity.this, CourierHomeTabActivity.class);
////                    startActivity(intent8);
//                }else if (storeInfo.store_info.dlyp_state.equals("10")){
//                    Toast.makeText(MyStoreActivity.this, getString(R.string.member_centre22), Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
    }

    private long mExitTime;

    /**
     * 防误按退出
     *
     * @return
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                Toast.makeText(this, getString(R.string.preventwrongoperation), Toast.LENGTH_SHORT).show();
//                mExitTime = System.currentTimeMillis();
//            } else {
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 用户如果禁止，则每次进入都会申请
             */
            // 读写权限
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
            }
            //摄像头权限
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void ReGetData() {

    }

}
