package com.aite.recylibrary;


import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.aite.recylibrary.emnu.Constant;
import com.aite.recylibrary.emnu.MainMultipleItem;
import com.lzy.basemodule.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aite.recylibrary.emnu.Constant.settingTv;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TestFragment extends BaseFragment {
    private LinearLayout addDeiviceLl;
    private LottieAnimationView lottieAnimationView;
    private Banner banner;
    private RecyclerView recycler_view;
    private SmartRefreshLayout smartlayout;
    TestAdapter testAdapter;
    private MultipleQuickAdapter multipleQuickAdapter;

    @Override
    protected void initModel() {

    }

    @Override
    protected void initViews() {
//        addDeiviceLl = getMview().findViewById(R.id.add_deivice_ll);
//        banner = getMview().findViewById(R.id.banner);
//        recycler_view = getMview().findViewById(R.id.recycler_view);
//        Map<Integer, List<Object>> alldatamap = new HashMap<>();
//        List<String> soundlist = new ArrayList<>();
//        soundlist.add("关爱老年健康  记忆与爱同行");
//        alldatamap.put(MainMultipleItem.SOUND, (Object)soundlist);
//        List<String> titleList = new ArrayList<>();
//        titleList.add("我的设备");
//        alldatamap.put(MainMultipleItem.TITLE,titleList);
//        List<String> devicelllList = new ArrayList<>();
//        titleList.add("已连接设备AA");
//        alldatamap.put(MainMultipleItem.DEVICE,devicelllList);
//        List<String> titleList2 = new ArrayList<>();
//        titleList.add("养老服务");
//        alldatamap.put(MainMultipleItem.TITLE,titleList2);
//        List<String> recylist = new ArrayList<>();
//        for (String a : Arrays.asList(settingTv)) {
//            recylist.add(a);
//        }
//        alldatamap.put(MainMultipleItem.HELPOLDERSERVICE,recylist);
//
////        List<Object> objects = new ArrayList<>();
////        objects.add(soundlist);
////        objects.add(titleList);
////        objects.add(devicelllList);
////        objects.add(titleList2);
////        objects.add(recylist);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        recycler_view.setLayoutManager(linearLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        testAdapter = new TestAdapter(context, alldatamap);
//        recycler_view.setAdapter(testAdapter);
//        List<MainMultipleItem> data = BaseConstant.getMultipleItemData();
//
//        multipleQuickAdapter=new MultipleQuickAdapter(context,data);
//        recycler_view.setAdapter(multipleQuickAdapter);
//        multipleQuickAdapter.notifyDataSetChanged();


//        List<? extends IBaseHomeBean> mDatalist
//        switch (mDatalist.get(position).getState()) {
//            case :
//        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.test;
    }

    @Override
    public void onClick(View v) {

    }
}