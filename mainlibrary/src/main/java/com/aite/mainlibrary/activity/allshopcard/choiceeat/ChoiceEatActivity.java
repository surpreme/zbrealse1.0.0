package com.aite.mainlibrary.activity.allshopcard.choiceeat;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.RecyChoiceUIBean;
import com.aite.mainlibrary.Mainbean.ShopCardlistBean;
import com.aite.mainlibrary.Mainbean.TypeChoiceUIBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.shopcard.ShopCardActivity;
import com.aite.mainlibrary.adapter.ChoiceEatRecyAdapter;
import com.aite.mainlibrary.adapter.ChoiceEatTypeRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChoiceEatActivity extends BaseActivity<ChoiceEatContract.View, ChoiceEatPresenter> implements ChoiceEatContract.View, OnBannerListener {

    @BindView(R2.id.type_recy)
    RecyclerView typeRecy;
    @BindView(R2.id.buy_recy)
    RecyclerView buyRecy;
    @BindView(R2.id.shopcard_ll)
    RelativeLayout shopcardLl;
    @BindView(R2.id.add_shopcar_btn)
    Button addShopcarBtn;
    @BindView(R2.id.banner)
    Banner banner;
    @BindView(R2.id.all_price_tv)
    TextView allPriceTv;
    @BindView(R2.id.sender_price_tv)
    TextView senderPriceTv;
    private int mThingNumber;
    private ImageView shopcardImg;
    private ChoiceEatTypeRecyAdapter choiceEatTypeRecyAdapter;
    private List<TypeChoiceUIBean.ListClassBean> listClassBeans = new ArrayList<>();
    private ChoiceEatRecyAdapter choiceEatRecyAdapter;
    private List<RecyChoiceUIBean.GoodsListBean> goodsListBeanList = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    private Badge badge;
    private List<ShopCardlistBean.CartListBean> mCartListBean = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.chooce_eat;
    }

    @Override
    protected void initView() {
        initToolbar("菜品选择");
        shopcardLl.setOnClickListener(this);
        addShopcarBtn.setOnClickListener(this);
        //banner
        initBanner(banner);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);
        shopcardImg = findViewById(R.id.shopcard_img);
        badge = new QBadgeView(this).bindTarget(shopcardImg).setBadgeText("15").setBadgeBackgroundColor(getResources().getColor(R.color.red));
        badge.setBadgeTextSize(30, false);
        badge.setGravityOffset(2, 5, false);
        badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {

            }
        });
        //1
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        typeRecy.setLayoutManager(linearLayoutManager);
        choiceEatTypeRecyAdapter = new ChoiceEatTypeRecyAdapter(context, listClassBeans);
        typeRecy.setAdapter(choiceEatTypeRecyAdapter);

        //2
        LinearLayoutManager sencondlinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        buyRecy.setLayoutManager(sencondlinearLayoutManager);
        choiceEatRecyAdapter = new ChoiceEatRecyAdapter(context, goodsListBeanList);
        choiceEatRecyAdapter.setLstenerInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                mPresenter.addShopCard(initAddShopCardParams(postion));

            }
        });
        buyRecy.setAdapter(choiceEatRecyAdapter);

    }

    //添加类型 0系统默认 1助餐服务-菜品 cart_type
    //购买数量  quantity
    private HttpParams initAddShopCardParams(int postion) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("cart_type", 1);
        httpParams.put("goods_id", goodsListBeanList.get(postion).getGoods_id());
        httpParams.put("quantity", 1);
        return httpParams;
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    //添加类型 0系统默认 1助餐服务-菜品
    private HttpParams initTypeParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("cart_type", 1);
        return httpParams;
    }

    private HttpParams initScondParams(String id) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("gc_id", id);
        httpParams.put("curpage", 1);
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    @OnClick({R2.id.shopcard_ll, R2.id.add_shopcar_btn})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shopcard_ll || v.getId() == R.id.add_shopcar_btn) {
            startActivity(ShopCardActivity.class);
        }

    }

    @Override
    protected void initDatas() {
        mPresenter.getDatalist(initParams());

    }

    @Override
    protected void initResume() {
        mPresenter.getShopCard(initTypeParams());

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void getDataSuccess(Object msg) {
        listClassBeans.addAll(((TypeChoiceUIBean) msg).getList_class());
        choiceEatTypeRecyAdapter.notifyDataSetChanged();
        if (((TypeChoiceUIBean) msg).getList_class().get(0) != null) {
            mPresenter.getScondDatalist(initScondParams(((TypeChoiceUIBean) msg).getList_class().get(0).getGc_id()));
        }
        if (((TypeChoiceUIBean) msg).getAdv_list() != null || ((TypeChoiceUIBean) msg).getAdv_list().size() > 0) {
            for (TypeChoiceUIBean.AdvListBean advListBean : ((TypeChoiceUIBean) msg).getAdv_list()) {
                list_img.add(advListBean.getAdv_content().getAdv_pic());
                list_title.add(advListBean.getAdv_title());
                LogUtils.d(advListBean.getAdv_title());

            }
            banner.setImages(list_img);
            banner.setBannerTitles(list_title);
            banner.startAutoPlay();
            banner.start();
        }
    }

    @Override
    public void getScondDataSuccess(Object msg) {
        goodsListBeanList.addAll(((RecyChoiceUIBean) msg).getGoods_list());
        choiceEatRecyAdapter.notifyDataSetChanged();
    }

    @Override
    public void addShopCardSuccesss(Object msg) {
        if (((String) msg).equals("1")) {
            showToast("添加成功", Gravity.TOP);
            mPresenter.getShopCard(initTypeParams());
            LogUtils.d(msg);
        }

    }

    @Override
    public void onShopCardSuccess(Object msg) {
        if (!mCartListBean.isEmpty()) {
            mCartListBean.clear();
        }
        mThingNumber = 0;
        //总价
        allPriceTv.setText(String.format("￥ %s", ((ShopCardlistBean) msg).getSum()));
        senderPriceTv.setText(String.format("配送费%s", String.valueOf(((ShopCardlistBean) msg).getTotal_shipping_fee())));
        mCartListBean.addAll(((ShopCardlistBean) msg).getCart_list());
        for (int i = 0; i < mCartListBean.size(); i++) {
            try {
                mThingNumber += Integer.valueOf(((ShopCardlistBean) msg).getCart_list().get(i).getGoods_num());
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        badge.setBadgeText(String.valueOf(mThingNumber));

    }


    @Override
    public void OnBannerClick(int position) {

    }

}
