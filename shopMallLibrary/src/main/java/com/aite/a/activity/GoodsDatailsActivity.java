package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.MyFragmentAdapter;
import com.aite.a.base.Mark;
import com.aite.a.fargment.EvaluationFragment;
import com.aite.a.fargment.GoodsFragment;
import com.aite.a.fargment.GraphicIntroductionFargment;
import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BooleanLogin;
import com.aite.a.view.ServicePopu;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import chat.activity.DialogueActivity;

/**
 * 商品详情
 *
 * @author Administrator
 */
public class GoodsDatailsActivity extends FragmentActivity implements OnClickListener, Mark {
    private ImageView iv_return, iv_collection;
    private TextView tv_goods, tv_datas, tv_evaluation, tv_buy, tv_addcart;
    private RelativeLayout rl_slider;
    public ViewPager favorite_list_viewpager;
    private LinearLayout ll_jimi, ll_store, ll_love;
    private LayoutParams params = null;
    public String goods_id,spec_goods_id;//商品详情
    private GoodsFragment goodsFragment;
    private GraphicIntroductionFargment graphicFargment;
    private EvaluationFragment evaluationFragment;
    private GoodsDetailsInfo detailsInfo;
    private boolean isshoucang = false;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case goods_details_id://商品详情
                    if (msg.obj != null) {
                        detailsInfo = (GoodsDetailsInfo) msg.obj;
                        if (detailsInfo.isFavorites.equals("1")) {//是否收藏
                            isshoucang = true;
                            iv_collection.setImageResource(R.drawable.yes_favorites);
                        } else {
                            isshoucang = false;
                            iv_collection.setImageResource(R.drawable.shoucang1fcf);
                        }
                        //如果有商店编号就显示商店
                        if (detailsInfo.store_info.store_id != null && !detailsInfo.store_info.store_id.equals("") && !detailsInfo.store_info.store_id.equals("null")) {
                            ll_store.setVisibility(View.VISIBLE);
                        }
                        goodsFragment.handler.sendMessage(goodsFragment.handler.obtainMessage(GoodsDetails_detailsInfo, detailsInfo));

                    }
                    break;
                case goods_details_err:
                    Toast.makeText(GoodsDatailsActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case add_cart_id://添加购物车
                    if (msg.obj.equals("1")) {
                        Toast.makeText(GoodsDatailsActivity.this, getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                    } else if (msg.obj.equals("0")) {
                        Toast.makeText(GoodsDatailsActivity.this, getString(R.string.add_fail), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GoodsDatailsActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case add_cart_err:
                    Toast.makeText(GoodsDatailsActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case collectibles_id://添加收藏
                    if (msg.obj.equals("1")) {
                        isshoucang = true;
                        iv_collection.setImageResource(R.drawable.yes_favorites);
                        Toast.makeText(GoodsDatailsActivity.this, getString(R.string.collection_success), Toast.LENGTH_SHORT).show();
                    } else {
                        isshoucang = false;
                        iv_collection.setImageResource(R.drawable.collection);
                        Toast.makeText(GoodsDatailsActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case collectibles_del_id://删除收藏
                    if (msg.obj.equals("1")) {
                        isshoucang = false;
                        iv_collection.setImageResource(R.drawable.collection);
                        Toast.makeText(GoodsDatailsActivity.this, getString(R.string.cancel_collection_success), Toast.LENGTH_SHORT).show();
                    } else {
                        isshoucang = true;
                        iv_collection.setImageResource(R.drawable.yes_favorites);
                        Toast.makeText(GoodsDatailsActivity.this, getString(R.string.cancel_collection_fail), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case GOODS_ADDCART://添加购物车
                    if (msg.obj != null) {
                        spec_goods_id = (String) msg.obj;
                        Log.i("--------------", "添加购物车 "+spec_goods_id);
                        if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
                            netRun.addInCart(spec_goods_id, "1");
                        }
                    }
                    break;
                case GOODS_BUY://立即购买
                    if (msg.obj != null) {
                        spec_goods_id = (String) msg.obj;
                        Log.i("--------------", "立即购买 "+spec_goods_id);
//                        tv_buy.performLongClick();
                        if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
                            if (detailsInfo.goods_info.is_virtual.equals("1")) {//虚拟商品
                                Intent intent = new Intent(GoodsDatailsActivity.this, VirtualConfirmOrderActivity.class);
                                intent.putExtra("cart_id", spec_goods_id);
//                                intent.putExtra("cart_id", detailsInfo.goods_info.goods_id);
                                intent.putExtra("quantity", "1");
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(GoodsDatailsActivity.this, OrderSureActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cart_id", spec_goods_id + "|" + "1");
                                bundle.putString("ifcart", "0");
                                bundle.putString("isfcode", detailsInfo.goods_info.is_fcode);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }
                    break;
                case GOODS_PICK://选中规格
                    if (msg.obj!=null){
                        spec_goods_id = (String) msg.obj;
                        Log.i("--------------", "选中规格 "+spec_goods_id);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_goodsdata);
        findViewById();
    }

    private void findViewById() {
        iv_return = findViewById(R.id.iv_return);
        tv_goods = findViewById(R.id.tv_goods);
        tv_datas = findViewById(R.id.tv_datas);
        tv_evaluation = findViewById(R.id.tv_evaluation);
        tv_buy = findViewById(R.id.tv_buy);
        tv_addcart = findViewById(R.id.tv_addcart);
        rl_slider = findViewById(R.id.rl_slider);
        iv_collection = findViewById(R.id.iv_collection);
        favorite_list_viewpager = findViewById(R.id.favorite_list_viewpager);
        ll_jimi = findViewById(R.id.ll_jimi);
        ll_store = findViewById(R.id.ll_store);
        ll_love = findViewById(R.id.ll_love);
        iv_return.setOnClickListener(this);
        tv_goods.setOnClickListener(this);
        tv_datas.setOnClickListener(this);
        tv_evaluation.setOnClickListener(this);
        tv_addcart.setOnClickListener(this);
        tv_buy.setOnClickListener(this);
        ll_store.setOnClickListener(this);
        ll_jimi.setOnClickListener(this);
        ll_love.setOnClickListener(this);
        initView();
    }

    private void initView() {
        netRun = new NetRun(this, handler);
        params = (LayoutParams) rl_slider.getLayoutParams();
        List<Fragment> list = new ArrayList<Fragment>();
        goodsFragment = new GoodsFragment();
        graphicFargment = new GraphicIntroductionFargment(goods_id);
        evaluationFragment = new EvaluationFragment();
        list.add(goodsFragment);
        list.add(graphicFargment);
        list.add(evaluationFragment);
        favorite_list_viewpager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), list));
        favorite_list_viewpager.setOffscreenPageLimit(3);
        favorite_list_viewpager.addOnPageChangeListener(pageChangeListener);
        initData();
    }

    private void initData() {
        goods_id = getIntent().getStringExtra("goods_id");
        Log.i("--------------", "goods_id= " + goods_id);
        netRun.getProductDetails(goods_id);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_return){
            finish();
        }else if(v.getId()==R.id.tv_goods){
            // 商品
            favorite_list_viewpager.setCurrentItem(0, true);
        }else if(v.getId()==R.id.tv_datas){
            // 详情
            favorite_list_viewpager.setCurrentItem(1, true);
        }else if(v.getId()==R.id.tv_evaluation){
            // 评价
            favorite_list_viewpager.setCurrentItem(2, true);
        }else if(v.getId()==R.id.tv_addcart){
            Log.i("--------------", "添加购物车 "+spec_goods_id);
            if (spec_goods_id==null){
                goodsFragment.handler.sendEmptyMessage(GOODS_SHOWSPEC);
            }else if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
                netRun.addInCart(spec_goods_id, "1");
            }
        }else if(v.getId()==R.id.tv_buy){
            Log.i("--------------", "立即购买 "+spec_goods_id);
            if (spec_goods_id==null){
                goodsFragment.handler.sendEmptyMessage(GOODS_SHOWSPEC);
            }else if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
                if (detailsInfo.goods_info.is_virtual.equals("1")) {//虚拟商品
                    Intent intent = new Intent(GoodsDatailsActivity.this, VirtualConfirmOrderActivity.class);
                    intent.putExtra("cart_id", spec_goods_id);
//                        intent.putExtra("cart_id", detailsInfo.goods_info.goods_id);
                    intent.putExtra("quantity", "1");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(GoodsDatailsActivity.this, OrderSureActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cart_id", spec_goods_id + "|" + "1");
                    bundle.putString("ifcart", "0");
                    bundle.putString("isfcode", detailsInfo.goods_info.is_fcode);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }else if(v.getId()==R.id.ll_store){
            if (detailsInfo.store_info.store_id == null || detailsInfo.store_info.store_id.equals("null") || detailsInfo.store_info.store_id.equals("")) {
                Toast.makeText(GoodsDatailsActivity.this, getString(R.string.noinformation), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(GoodsDatailsActivity.this, StoreDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("store_id", detailsInfo.store_info.store_id);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(v.getId()==R.id.ll_jimi){
            showpopupw();
        }else if(v.getId()==R.id.ll_love){
            if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
                if (isshoucang) {
                    netRun.cancelGoodsFavorite(goods_id, "goods");
                } else {
                    netRun.addFavorites(goods_id, "goods");
                }
            }
        }
//        switch (v.getId()) {
//            case R.id.iv_return:
//                finish();
//                break;
//            case R.id.tv_goods:
//                // 商品
//                favorite_list_viewpager.setCurrentItem(0, true);
//                break;
//            case R.id.tv_datas:
//                // 详情
//                favorite_list_viewpager.setCurrentItem(1, true);
//                break;
//            case R.id.tv_evaluation:
//                // 评价
//                favorite_list_viewpager.setCurrentItem(2, true);
//                break;
//            case R.id.tv_addcart://添加购物车
//                Log.i("--------------", "添加购物车 "+spec_goods_id);
//                if (spec_goods_id==null){
//                    goodsFragment.handler.sendEmptyMessage(GOODS_SHOWSPEC);
//                }else if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
//                    netRun.addInCart(spec_goods_id, "1");
//                }
//                break;
//            case R.id.tv_buy://立即购买
//                Log.i("--------------", "立即购买 "+spec_goods_id);
//                if (spec_goods_id==null){
//                    goodsFragment.handler.sendEmptyMessage(GOODS_SHOWSPEC);
//                }else if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
//                    if (detailsInfo.goods_info.is_virtual.equals("1")) {//虚拟商品
//                        Intent intent = new Intent(GoodsDatailsActivity.this, VirtualConfirmOrderActivity.class);
//                        intent.putExtra("cart_id", spec_goods_id);
////                        intent.putExtra("cart_id", detailsInfo.goods_info.goods_id);
//                        intent.putExtra("quantity", "1");
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(GoodsDatailsActivity.this, OrderSureActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("cart_id", spec_goods_id + "|" + "1");
//                        bundle.putString("ifcart", "0");
//                        bundle.putString("isfcode", detailsInfo.goods_info.is_fcode);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                }
//                break;
//            case R.id.ll_store://店铺
//                if (detailsInfo.store_info.store_id == null || detailsInfo.store_info.store_id.equals("null") || detailsInfo.store_info.store_id.equals("")) {
//                    Toast.makeText(GoodsDatailsActivity.this, getString(R.string.noinformation), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Intent intent = new Intent(GoodsDatailsActivity.this, StoreDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("store_id", detailsInfo.store_info.store_id);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.ll_jimi://客服
//                showpopupw();
//                break;
//            case R.id.ll_love://收藏
//                if (BooleanLogin.getInstance().hasLogin(GoodsDatailsActivity.this)) {
//                    if (isshoucang) {
//                        netRun.cancelGoodsFavorite(goods_id, "goods");
//                    } else {
//                        netRun.addFavorites(goods_id, "goods");
//                    }
//                }
//                break;
//        }
    }

    private void showpopupw(){
        ServicePopu servicePopu=new ServicePopu(GoodsDatailsActivity.this,detailsInfo.store_callcenter);
        servicePopu.setmenu(new ServicePopu.menu() {
            @Override
            public void onItemClick(GoodsDetailsInfo.store_callcenter.callcenter_list callcenter_list) {
                if (callcenter_list.type.equals("qq")){
                    if (isQQClientAvailable(GoodsDatailsActivity.this)){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/main_chat?chat_type=wpa&uin="+callcenter_list.num)));
                    }else{
                        Toast.makeText(GoodsDatailsActivity.this, getString(R.string.nocustomerservice2), Toast.LENGTH_SHORT).show();
                    }
                }else if (callcenter_list.type.equals("im")){
                    Intent intent1 = new Intent(GoodsDatailsActivity.this, DialogueActivity.class);
                    intent1.putExtra("member_id", callcenter_list.num);
                    intent1.putExtra("goods_image", detailsInfo.goods_info.goods_image_primary);
                    intent1.putExtra("goods_name", detailsInfo.goods_info.goods_name);
                    intent1.putExtra("goods_price", detailsInfo.goods_info.goods_price);
                    intent1.putExtra("goods_sales", detailsInfo.goods_info.goods_salenum);
                    intent1.putExtra("goods_id", detailsInfo.goods_info.goods_id);
                    startActivity(intent1);
                }else if (callcenter_list.type.equals("ww")){
                    Toast.makeText(GoodsDatailsActivity.this, "开发中", Toast.LENGTH_SHORT).show();
                }
            }
        });
        servicePopu.showAtLocation(ll_jimi, Gravity.BOTTOM,0,0);
    }


    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }



    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int index) {
//            System.out.println("--------index当前的位置   ：" + index);
            if (index == 2) {
                evaluationFragment.goods_id = goods_id;
//                evaluationFragment.handler.sendMessage(evaluationFragment.handler.obtainMessage(GoodsDetails_detailsInfo, goods_id));
            }
        }

        @Override
        public void onPageScrolled(int index, float arg1, int arg2) {
//            System.out.println("--------index当前的位置   ：" + index);
//            System.out.println("--------arg1屏幕滑动的百分比   ：" + arg1);
//            System.out.println("--------arg2屏幕滑动的距离  ：" + arg2);
            params.leftMargin = (int) (rl_slider.getWidth() * (index + arg1));
            rl_slider.setLayoutParams(params);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
//            System.out.println("--------onPageScrollStateChanged   ：" + arg0);
        }
    };


}
