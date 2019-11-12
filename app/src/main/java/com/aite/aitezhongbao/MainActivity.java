package com.aite.aitezhongbao;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aite.mainlibrary.AroundBackgroundFragment;
import com.aite.mainlibrary.MainFragment;
import com.aite.mainlibrary.MineFragment;
import com.aite.mainlibrary.NewsFragment;
import com.aite.mainlibrary.ShopFragment;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.main_img)
    ImageView mainImg;
    @BindView(R.id.main_tv)
    TextView mainTv;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.shop_img)
    ImageView shopImg;
    @BindView(R.id.shop_tv)
    TextView shopTv;
    @BindView(R.id.shop_layout)
    RelativeLayout shopLayout;
    @BindView(R.id.aroundbackground_img)
    ImageView aroundbackgroundImg;
    @BindView(R.id.aroundbackground_tv)
    TextView aroundbackgroundTv;
    @BindView(R.id.aroundbackground_layout)
    RelativeLayout aroundbackgroundLayout;
    @BindView(R.id.news_img)
    ImageView newsImg;
    @BindView(R.id.news_tv)
    TextView newsTv;
    @BindView(R.id.news_layout)
    RelativeLayout newsLayout;
    @BindView(R.id.my_img)
    ImageView myImg;
    @BindView(R.id.my_tv)
    TextView myTv;
    @BindView(R.id.my_layout)
    RelativeLayout myLayout;


    private MainFragment mainFragment;
    private MineFragment mineFragment;
    private NewsFragment newsFragment;
    private ShopFragment shopFragment;
    private AroundBackgroundFragment aroundBackgroundFragment;

    public MainActivity() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        startActivity(TabyActivity.class);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
//        MainFragment mainFragment = new MainFragment();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if (mainFragment == null) {
//            mainFragment = new MainFragment();
//        }
//        transaction.add(R.id.content, mainFragment);
//        transaction.commit();


    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    private FragmentManager fragmentManager;
    private Bundle bundle;


    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                mainImg.setImageResource(R.mipmap.mainimg);
                mainTv.setTextColor(getResources().getColor(R.color.blue));
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    transaction.add(R.id.content, mainFragment);
                } else {
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                shopImg.setImageResource(R.drawable.shop);
                shopTv.setTextColor(getResources().getColor(R.color.blue));
                if (shopFragment == null) {
                    shopFragment = new ShopFragment();
                    transaction.add(R.id.content, shopFragment);
                } else {
                    transaction.show(shopFragment);
                }
                break;
            case 2:
                aroundbackgroundImg.setImageResource(R.drawable.around);
                aroundbackgroundTv.setTextColor(getResources().getColor(R.color.blue));
                if (aroundBackgroundFragment == null) {
                    aroundBackgroundFragment = new AroundBackgroundFragment();
                    transaction.add(R.id.content, aroundBackgroundFragment);
                } else {
                    transaction.show(aroundBackgroundFragment);
                }
                break;
            case 3:
                newsImg.setImageResource(R.drawable.news);
                newsTv.setTextColor(getResources().getColor(R.color.blue));
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                break;
            case 4:
                myImg.setImageResource(R.drawable.mine);
                myTv.setTextColor(getResources().getColor(R.color.blue));
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.content, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();

    }

    private void clearSelection() {
        shopImg.setImageResource(R.mipmap.unshop);
        shopTv.setTextColor(Color.parseColor("#82858b"));
        mainImg.setImageResource(R.drawable.unmain);
        mainTv.setTextColor(Color.parseColor("#82858b"));
        aroundbackgroundImg.setImageResource(R.mipmap.unaroundbackground);
        aroundbackgroundTv.setTextColor(Color.parseColor("#82858b"));
        newsImg.setImageResource(R.mipmap.unnews);
        newsTv.setTextColor(Color.parseColor("#82858b"));
        myImg.setImageResource(R.mipmap.unmy);
        myTv.setTextColor(Color.parseColor("#82858b"));


    }

    /**
     * 隐藏碎片 避免重叠
     * 爱家 unnews
     * 我的 unmy /// mine
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (aroundBackgroundFragment != null) {
            transaction.hide(aroundBackgroundFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }

    }


    @OnClick({R.id.main_layout, R.id.shop_layout, R.id.aroundbackground_layout, R.id.news_layout, R.id.my_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.content:
//                break;
//            case R.id.main_img:
//                break;
//            case R.id.main_tv:
//                break;
            case R.id.main_layout:
                setTabSelection(0);
                break;
//            case R.id.shop_img:
//                break;
//            case R.id.shop_tv:
//                break;
            case R.id.shop_layout:
                setTabSelection(1);
                break;
//            case R.id.aroundbackground_img:
//                break;
//            case R.id.aroundbackground_tv:
//                break;
            case R.id.aroundbackground_layout:
                setTabSelection(2);
                break;
//            case R.id.news_img:
//                break;
//            case R.id.news_tv:
//                break;
            case R.id.news_layout:
                setTabSelection(3);
                break;
//            case R.id.my_img:
//                break;
//            case R.id.my_tv:
//                break;
            case R.id.my_layout:
                setTabSelection(4);
                break;
        }
    }
}
