package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.AddInCartWindows.OnItemClickListener;
import com.aite.a.base.BaseFargmentActivity;
import com.aite.a.fargment.EvaluationGoodsFargment;
import com.aite.a.fargment.GoodsDetailsFargment;
import com.aite.a.fargment.GraphicIntroductionFargment;
import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BooleanLogin;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.SPUtils;
import com.aite.a.utils.lingshi;
import com.aite.a.view.MyDialog;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import chat.activity.DialogueActivity;

/**
 * 商品详情
 *
 * @author CAOYOU
 */
public class GoodsDetailsFargmentActivity extends BaseFargmentActivity implements OnItemClickListener {
    public AddInCartWindows cartWindows;
    private ImageView roller, iv_return; // 动画滚动图片
    private int imageWidth;
    private int offset = 0; // 图片偏移量
    private int currentIndex = 0; // 当前页卡编号
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList; // 装载显示内容
    private TextView Details_tx, graphic_tx, evaluation_tx, tv_name;
    private ImageView iv_back, iv_right, iv_shoucang, iv_errorimage;
    public String goods_id;
    private GoodsDetailsInfo detailsInfo = new GoodsDetailsInfo();
    private GoodsDetailsFargment goodsFargment;
    private GraphicIntroductionFargment graphicFargment;
    private EvaluationGoodsFargment evaluationFargment;
    private Button product_btn_gm, product_btn_add_gwc_now, product_details_btn_cart;
    private NetRun netRun;
    private MyDialog errordialog;
    private View inflate;
    private LinearLayout ll_collect_store; // 收藏
    private LinearLayout ll_customer;// 客服
    private LinearLayout ll_store;// 店铺
    private LinearLayout ll_menu;// 菜单
    private String store_id;
    private String isfcode = "0";
    private RelativeLayout include1;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case add_cart_id:
                    if (msg.obj.equals("1")) {
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.add_success));
                        cartWindows.dissmiss();
                    } else if (msg.obj.equals("0")) {
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.add_fail));
                    } else {
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, msg.obj.toString());
                    }
                    mdialog.dismiss();
                    break;
                case add_cart_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.systembusy));
                    break;
                case add_cart_start:
//				mdialog.setMessage(getI18n(R.string.act_waiting));
                    mdialog.show();
                    break;
                case goods_details_id:
                    detailsInfo = (GoodsDetailsInfo) msg.obj;
                    if (msg.obj != null) {
                        //判断是否收藏
                        String isFavorites = detailsInfo.isFavorites;
                        if (isFavorites.equals("1")) {
                            isshoucang = true;
                            iv_shoucang.setBackgroundResource(R.drawable.yes_favorites);
                        } else {
                            isshoucang = false;
                            iv_shoucang.setBackgroundResource(R.drawable.shoucang1fcf);
                        }
                        String store_qq = detailsInfo.store_info.store_qq;

                        store_id = detailsInfo.store_info.store_id;
                        lingshi.getInstance().setStore_id(store_id);
                        lingshi.getInstance().setStore_name(detailsInfo.store_info.store_name);
                        //如果有商店编号就显示商店
                        if (store_id != null && !store_id.equals("") && !store_id.equals("null")) {
                            ll_store.setVisibility(View.VISIBLE);
                        }
                        //如果有QQ号码则显示客服
//                        if (store_qq != null && !store_qq.equals("") && !store_qq.equals("null")) {
//                            lingshi.getInstance().setStore_qq(store_qq);
//                            ll_customer.setVisibility(View.VISIBLE);
//                        }
                        ll_customer.setVisibility(View.VISIBLE);
                        goodsFargment.handler.sendMessage(goodsFargment.handler.obtainMessage(GoodsDetails_detailsInfo, detailsInfo));
                        //是否F码购买
                        isfcode = detailsInfo.goods_info.is_fcode;
                        if (isfcode.equals("1")) {
                            product_btn_gm.setVisibility(View.INVISIBLE);
                            product_btn_gm.setEnabled(false);
                            product_btn_add_gwc_now.setText(getI18n(R.string.fcoding));
                        }
                        if (detailsInfo.goods_info.is_virtual.equals("1")) {//虚拟商品
                            product_btn_gm.setVisibility(View.GONE);
                        }
                        evaluationFargment.handler.sendMessage(evaluationFargment.handler.obtainMessage(Evaluation_list, detailsInfo.goods_evaluate_list));
                    } else {
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.act_no_data));
                    }
                    mdialog.dismiss();
                    break;
                case goods_details_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.systembusy));
                    break;
                case goods_details_start:
//				mdialog.setMessage(getI18n(R.string.act_waiting));
                    mdialog.show();
                    break;
                case collectibles_id:
                    if (msg.obj.equals("1")) {
                        isshoucang = true;
                        iv_shoucang.setBackgroundResource(R.drawable.yes_favorites);
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.collection_success));
                    } else {
                        isshoucang = false;
                        iv_shoucang.setBackgroundResource(R.drawable.collection);
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, msg.obj.toString());
                    }
                    break;
                case collectibles_del_id:
                    if (msg.obj.equals("1")) {
                        isshoucang = false;
                        iv_shoucang.setBackgroundResource(R.drawable.collection);
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.cancel_collection_success));
                    } else {
                        isshoucang = true;
                        iv_shoucang.setBackgroundResource(R.drawable.yes_favorites);
                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.cancel_collection_fail));
                    }
                    break;
                case 10:
                    if (msg.obj != null) {
                        detailsInfo = (GoodsDetailsInfo) msg.obj;
                        goodsFargment.handler.sendMessage(goodsFargment.handler.obtainMessage(GoodsDetails_detailsInfo, detailsInfo));
                    }
                    break;
            }
        }

        ;
    };

    private boolean isshoucang = false;
    private boolean isClickBuy;
    private LinearLayout all_choice_layout;

    public ArrayList<Fragment> getFragmentList() {
        return fragmentList;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.product_details);
        netRun = new NetRun(this, handler);
        findViewById();
        initData();
        initView();
        initCursor(3);
    }

    protected void findViewById() {
        iv_back = (ImageView) findViewById(R.id._iv_back);

        iv_right = (ImageView) findViewById(R.id._iv_right);
        tv_name = (TextView) findViewById(R.id._tv_name);
        roller = (ImageView) findViewById(R.id.goods_list_iv_cursor);
        iv_return = (ImageView) findViewById(R.id.iv_return);
        Details_tx = (TextView) findViewById(R.id.goods_details);
        graphic_tx = (TextView) findViewById(R.id.graphic_introduction);
        evaluation_tx = (TextView) findViewById(R.id.evaluation_list);
        viewPager = (ViewPager) findViewById(R.id.favorite_list_viewpager);
        product_btn_gm = (Button) findViewById(R.id.product_btn_gm);
        product_btn_add_gwc_now = (Button) findViewById(R.id.product_btn_add_gwc_now);
        product_details_btn_cart = (Button) findViewById(R.id.product_details_btn_cart);
        all_choice_layout = (LinearLayout) findViewById(R.id.all_choice_layout);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        tv_name.setText(getI18n(R.string.goods_detail));
        ll_collect_store = (LinearLayout) findViewById(R.id.ll_collect_store);
        ll_customer = (LinearLayout) findViewById(R.id.ll_customer);
        ll_store = (LinearLayout) findViewById(R.id.ll_store);
        iv_shoucang = (ImageView) findViewById(R.id.iv_shoucang);
        include1 = (RelativeLayout) findViewById(R.id.include1);
        inflate = View.inflate(this, R.layout.dialog_error, null);
        iv_errorimage = (ImageView) inflate.findViewById(R.id.iv_errorimage);
        product_btn_gm.setOnClickListener(new MyOnclickListener());
        ll_store.setOnClickListener(new MyOnclickListener());
        product_btn_add_gwc_now.setOnClickListener(new MyOnclickListener());
        product_details_btn_cart.setOnClickListener(new MyOnclickListener());
        iv_back.setOnClickListener(new MyOnclickListener());
        iv_return.setOnClickListener(new MyOnclickListener());
        iv_right.setOnClickListener(new MyOnclickListener());
        Details_tx.setOnClickListener(new MyOnclickListener(0));
        graphic_tx.setOnClickListener(new MyOnclickListener(1));
        evaluation_tx.setOnClickListener(new MyOnclickListener(2));
        ll_collect_store.setOnClickListener(new MyOnclickListener(3));
        ll_customer.setOnClickListener(new MyOnclickListener(4));
        iv_errorimage.setOnClickListener(new MyOnclickListener(5));
    }

    private void initView() {
        errordialog = new MyDialog(this, 150, 150,
                inflate, R.style.loading_dialog);
        errordialog.setCanceledOnTouchOutside(false);
        cartWindows = new AddInCartWindows(this, handler);
        cartWindows.setOnItemClickListener(this);
        fragmentList = new ArrayList<Fragment>();
        goodsFargment = new GoodsDetailsFargment(goods_id);
        graphicFargment = new GraphicIntroductionFargment(goods_id);
        evaluationFargment = new EvaluationGoodsFargment();
        fragmentList.add(goodsFargment);
        fragmentList.add(graphicFargment);
        fragmentList.add(evaluationFargment);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) include1.getLayoutParams();
        layoutParams.height = getScreenWidth(this) / 9;
        include1.setLayoutParams(layoutParams);
    }

    /**
     * 放入数据
     */

    protected void initData() {
        goods_id = getIntent().getStringExtra("goods_id");
        netRun.getProductDetails(goods_id);
    }

    /**
     * 根据tagd的数量初始化游标的位置
     *
     * @param tagNum
     */

    public void initCursor(int tagNum) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_menu.getLayoutParams();
        layoutParams.width = displayMetrics.widthPixels / 2;
        ll_menu.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) roller.getLayoutParams();
        layoutParams2.width = displayMetrics.widthPixels / 2;
        roller.setLayoutParams(layoutParams2);

        imageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();

        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        offset = ((displayMetrics.widthPixels / 2) / tagNum - imageWidth) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        roller.setImageMatrix(matrix);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new PageChangeListener());
    }

    class PageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + imageWidth; // 一个页卡占的偏移量

        @Override
        public void onPageSelected(int position) {
            Animation animation = new TranslateAnimation(one * currentIndex, one * position, 0, 0);
            currentIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(300);
            roller.startAnimation(animation);
            switch (position) {
                case 0:
                    Details_tx.setTextColor(getResources().getColor(R.color.cursor_text));
                    graphic_tx.setTextColor(getResources().getColor(R.color.black));
                    evaluation_tx.setTextColor(getResources().getColor(R.color.black));
                    break;
                case 1:
                    Details_tx.setTextColor(getResources().getColor(R.color.black));
                    graphic_tx.setTextColor(getResources().getColor(R.color.cursor_text));
                    evaluation_tx.setTextColor(getResources().getColor(R.color.black));
                    break;
                case 2:
                    Details_tx.setTextColor(getResources().getColor(R.color.black));
                    graphic_tx.setTextColor(getResources().getColor(R.color.black));
                    evaluation_tx.setTextColor(getResources().getColor(R.color.cursor_text));
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // 当前页面被滑动时调用
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // 当前状态改变时调用
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> list;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm); // 必须调用
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    class MyOnclickListener implements View.OnClickListener {
        int index = 0;
        private SPUtils spUtils;

        public MyOnclickListener() {
            super();
        }

        public MyOnclickListener(int i) {
            this.index = i;
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.goods_details) {
                viewPager.setCurrentItem(index);
            } else if (v.getId() == R.id.graphic_introduction) {
                viewPager.setCurrentItem(index);
            } else if (v.getId() == R.id.evaluation_list) {
                viewPager.setCurrentItem(index);
            } else if (v.getId() == R.id.ll_collect_store) {
                if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
                    if (isshoucang) {
                        netRun.cancelGoodsFavorite(goods_id, "goods");
                    } else {
                        netRun.addFavorites(goods_id, "goods");
                    }
                }
            } else if (v.getId() == R.id.ll_customer) {
                Intent intent1 = new Intent(GoodsDetailsFargmentActivity.this, DialogueActivity.class);
                intent1.putExtra("member_id", detailsInfo.store_info.member_id);
                startActivity(intent1);
            } else if (v.getId() == R.id._iv_back || v.getId() == R.id.iv_return || v.getId() == R.id.iv_errorimage) {
                finish();
            } else if (v.getId() == R.id.product_btn_add_gwc_now) {
                if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
                    isClickBuy = false;
                    setBackgroundBlack(all_choice_layout, 0);
                    cartWindows.setData(detailsInfo, goodsFargment.list2);
                    cartWindows.setOnItemClickListener(GoodsDetailsFargmentActivity.this);
                    cartWindows.showAsDropDown(product_btn_add_gwc_now);
                }
            } else if (v.getId() == R.id.product_details_btn_cart) {
                if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
                    Intent intent = new Intent(GoodsDetailsFargmentActivity.this, CartActivity.class);
                    intent.putExtra("shoopping", "shoopping");
                    startActivity(intent);
                }
            } else if (v.getId() == R.id.product_btn_gm) {
                if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
                    isClickBuy = true;
                    setBackgroundBlack(all_choice_layout, 0);
                    cartWindows.setData(detailsInfo, goodsFargment.list2);
                    cartWindows.setOnItemClickListener(GoodsDetailsFargmentActivity.this);
                    cartWindows.showAsDropDown(product_btn_add_gwc_now);
                }
            } else if (v.getId() == R.id.ll_store) {
                if (store_id == null || store_id.equals("null")
                        || store_id.equals("")) {
                    CommonTools.showShortToast(GoodsDetailsFargmentActivity.this,
                            getI18n(R.string.noinformation));
                    return;
                }
                Intent intent = new Intent(GoodsDetailsFargmentActivity.this,
                        StoreDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
//            switch (v.getId()) {
//                case R.id.goods_details:
//                    viewPager.setCurrentItem(index);
//                    break;
//                case R.id.graphic_introduction:
//                    viewPager.setCurrentItem(index);
//                    break;
//                case R.id.evaluation_list:
//                    viewPager.setCurrentItem(index);
//                    break;
//                case R.id.ll_collect_store:
//                    if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
//                        if (isshoucang) {
//                            netRun.cancelGoodsFavorite(goods_id, "goods");
//                        } else {
//                            netRun.addFavorites(goods_id, "goods");
//                        }
//                    }
//                    break;
//                case R.id.ll_customer:
////				String store_qq = detailsInfo.store_info.store_qq;
////				String url = "mqqwpa://im/main_chat?chat_type=wpa&uin=" + store_qq;
////				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                    Intent intent1 = new Intent(GoodsDetailsFargmentActivity.this, DialogueActivity.class);
//                    intent1.putExtra("member_id",detailsInfo.store_info.member_id);
//                    startActivity(intent1);
//                    break;
//                case R.id._iv_back:
//                    finish();
//                    break;
//                case R.id.iv_return:
//                    finish();
//                    break;
//                case R.id.iv_errorimage://退出
//                    finish();
//                    break;
//
//                case R.id.product_btn_add_gwc_now:// 立即购买
//                    if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
//                        isClickBuy = false;
//                        setBackgroundBlack(all_choice_layout, 0);
//                        cartWindows.setData(detailsInfo, goodsFargment.list2);
//                        cartWindows.setOnItemClickListener(GoodsDetailsFargmentActivity.this);
//                        cartWindows.showAsDropDown(product_btn_add_gwc_now);
//                    }
//                    break;
//                case R.id.product_details_btn_cart:// 去购物车界面
//                    if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
//                        Intent intent = new Intent(GoodsDetailsFargmentActivity.this, CartActivity.class);
//                        intent.putExtra("shoopping", "shoopping");
//                        startActivity(intent);
//                    }
//                    break;
//                case R.id.product_btn_gm:// 加入购物车
//                    if (BooleanLogin.getInstance().hasLogin(GoodsDetailsFargmentActivity.this)) {
//                        isClickBuy = true;
//                        setBackgroundBlack(all_choice_layout, 0);
//                        cartWindows.setData(detailsInfo, goodsFargment.list2);
//                        cartWindows.setOnItemClickListener(GoodsDetailsFargmentActivity.this);
//                        cartWindows.showAsDropDown(product_btn_add_gwc_now);
//                    }
//                    break;
//                case R.id.ll_store:
////				Intent intent = new Intent(GoodsDetailsFargmentActivity.this, StoreAboutActivity.class);
////				if (store_id!=null) {
////					intent.putExtra("store_id", store_id);
////					startActivity(intent);
////				}else {
////					CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.nostop));
////				}
//                    if (store_id == null || store_id.equals("null")
//                            || store_id.equals("")) {
//                        CommonTools.showShortToast(GoodsDetailsFargmentActivity.this,
//                                getI18n(R.string.noinformation));
//                        return;
//                    }
//                    Intent intent = new Intent(GoodsDetailsFargmentActivity.this,
//                            StoreDetailsActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("store_id", store_id);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    break;
//            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lingshi.getInstance().getGoods_names().clear();
        lingshi.getInstance().getGoods_url().clear();
        lingshi.getInstance().getGoods_num1().clear();
        lingshi.getInstance().getOrder_amount().clear();
        lingshi.getInstance().getShipping_fee().clear();
        lingshi.getInstance().getGoods_price().clear();
        lingshi.getInstance().getStore_name1().clear();
    }

    @SuppressWarnings("null")
    @Override
    public void onClickOKPop() {
        if (cartWindows.isDel() == false) {
            if (detailsInfo.spec.spec_value.size() > 0)
                if (cartWindows.getSpecGoodsID() == null) {
                    CommonTools.showShortToast(this, getI18n(R.string.goods_empty));
                    return;
                } else {
                    goods_id = cartWindows.getSpecGoodsID().toString();
                }
            if (isClickBuy == false) {
                cartWindows.dissmiss();
                if (!detailsInfo.goods_info.goods_storage.equals("0")) {
                    if (detailsInfo.goods_info.is_virtual.equals("1")) {//虚拟商品
                        Intent intent = new Intent(GoodsDetailsFargmentActivity.this, VirtualConfirmOrderActivity.class);
                        intent.putExtra("cart_id", detailsInfo.goods_info.goods_id);
                        intent.putExtra("quantity", cartWindows.goods_num);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(
                                GoodsDetailsFargmentActivity.this,
                                OrderSureActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cart_id", goods_id + "|"
                                + cartWindows.goods_num);
                        bundle.putString("ifcart", "0");
                        bundle.putString("isfcode", isfcode);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    CommonTools.showShortToast(GoodsDetailsFargmentActivity.this, getI18n(R.string.nonekucun));
                }
            } else {
                netRun.addInCart(goods_id, cartWindows.goods_num);
            }
        } else {
            setBackgroundBlack(all_choice_layout, 1);
        }
    }

    /**
     * 控制背景变暗 0变暗 1变亮
     */
    public void setBackgroundBlack(View view, int what) {
        switch (what) {
            case 0:
                view.setVisibility(View.VISIBLE);
                break;
            case 1:
                view.setVisibility(View.GONE);
                break;
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
