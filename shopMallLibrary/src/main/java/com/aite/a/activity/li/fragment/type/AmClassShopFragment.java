package com.aite.a.activity.li.fragment.type;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.activity.li.adapter.ShopRecyAdapter;
import com.aite.a.activity.li.bean.ShopBean;
import com.aite.a.activity.li.fragment.BaseFragment;
import com.aite.a.activity.li.p.Model;
import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.base.Mark;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AmClassShopFragment extends BaseFragment {
    @BindView(R2.id.all_ll)
    LinearLayout all_ll;
    @BindView(R2.id.buy_ll)
    LinearLayout buy_ll;
    @BindView(R2.id.away_ll)
    LinearLayout away_ll;


    @BindView(R2.id.buy_choice_ll)
    LinearLayout buy_choice_ll;
    @BindView(R2.id.away_choice_ll)
    LinearLayout away_choice_ll;

    @BindView(R2.id.buy_top_img)
    ImageView buy_top_img;
    @BindView(R2.id.away_top_img)
    ImageView away_top_img;

    private boolean buytop = false;
    private boolean awaytop = false;


    @BindView(R2.id.shop_type_recy)
    RecyclerView shop_type_recy;
    @BindView(R2.id.smartlayout)
    SmartRefreshLayout smartRefreshLayout;

    private ShopRecyAdapter shopRecyAdapter;
    private List<ShopBean.DatasBean.StoreListBean> mDatasList = new ArrayList<>();
    private int pages = 1;
    private boolean isChange = false;
    private boolean isCanHasMore = true;
    private boolean isAway = false;
    private int type = 1;
    private int toplow = 1;
    private LocationClient locationClient;
    private String loctionstring;


    @BindView(R2.id.all_tv)
    TextView all_tv;
    @BindView(R2.id.buy_tv)
    TextView buy_tv;
    @BindView(R2.id.away_tv)
    TextView away_tv;
    @BindView(R2.id.content)
    FrameLayout content;


    @Override
    protected void initModel() {
        final Model model = new Model(context);
        LogUtils.d(type + "-----" + toplow + "-----" + pages + "-----" + loctionstring);
        model.amClassShopFragment(String.valueOf(type), toplow, pages, new Model.ModelInteface.AmClassShopFragmentInterface() {
            @Override
            public void amClassShopFragmentInterfaceModelSuccess(Context context, final List<?> DatasList, boolean ishasmore) {
                if (!ishasmore) {
                    isCanHasMore = false;
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                } else smartRefreshLayout.setNoMoreData(false);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isChange) {
                            mDatasList.clear();
                            isChange = false;
                        }
                        mDatasList.addAll((Collection<? extends ShopBean.DatasBean.StoreListBean>) DatasList);
                        shopRecyAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void amClassShopFragmentInterfaceeModelFail(String error) {

            }
        });

    }

    private void getLoctionDatas() {
        final Model model = new Model(context);
        LogUtils.d(Mark.State.UserKey);
        LogUtils.d("location" + type + "-----" + toplow + "-----" + pages + "-----" + loctionstring);
        model.amAroundActivity(String.valueOf(3), toplow, loctionstring, pages, new Model.ModelInteface.AmClassShopFragmentInterface() {
            @Override
            public void amClassShopFragmentInterfaceModelSuccess(Context context, final List<?> DatasList, boolean ishasmore) {
                mDatasList.addAll((Collection<? extends ShopBean.DatasBean.StoreListBean>) DatasList);
                shopRecyAdapter.notifyDataSetChanged();

            }

            @Override
            public void amClassShopFragmentInterfaceeModelFail(String error) {

            }
        });
    }

    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
        locationClient = new LocationClient(context);
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(5000);
        locationOption.setIsNeedLocationDescribe(true);
        locationOption.setNeedDeviceDirect(false);
        locationOption.setLocationNotify(true);
        locationOption.setIgnoreKillProcess(true);
        locationOption.setIsNeedLocationDescribe(true);
        locationOption.setIsNeedLocationPoiList(true);
        locationOption.SetIgnoreCacheException(false);
        locationOption.setOpenGps(true);
        locationClient.setLocOption(locationOption);
        locationClient.start();
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();
            loctionstring = longitude + "," + latitude;
            LogUtils.d("维度" + latitude + "经度" + longitude);


        }
    }

    @Override
    protected void initViews() {
        all_ll.setOnClickListener((View.OnClickListener) this);
        buy_ll.setOnClickListener((View.OnClickListener) this);
        away_ll.setOnClickListener((View.OnClickListener) this);
//        buy_tv.setOnClickListener(this);
//        away_tv.setOnClickListener(this);
        buy_top_img.setOnClickListener(this);

        away_top_img.setOnClickListener(this);


        //下拉刷新
        smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(getActivity()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
                pages = 1;
                initModel();


            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (isCanHasMore) pages++;
                else smartRefreshLayout.finishLoadMoreWithNoMoreData();
                if (isAway) getLoctionDatas();
                else
                    initModel();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });

        shop_type_recy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        shopRecyAdapter = new ShopRecyAdapter(getActivity(), mDatasList);
        shop_type_recy.setAdapter(shopRecyAdapter);
        shopRecyAdapter.setOnClickInterface(new ShopRecyAdapter.OnClickInterface() {
            @Override
            public void getPostion(int position) {
                startActivity(StoreDetailsActivity.class, "store_id", mDatasList.get(position).getStore_id());
            }
        });

        setTabSelection(0);
        initLocationOption();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_type;
    }


    private void clearAdapterData() {
        shopRecyAdapter.removeAllData();
        shopRecyAdapter.notifyDataSetChanged();
    }

    @OnClick({R2.id.all_ll, R2.id.buy_ll, R2.id.away_ll})
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.all_ll){
            pages = 1;
            type = 1;
            isChange = true;
            isAway = false;
            clearAdapterData();
            initModel();
            setTabSelection(0);
        }
        else if(v.getId()==R.id.buy_ll){
            if (!buytop) {
                pages = 1;
                toplow = 1;
                type = 2;
                isAway = false;
                isChange = true;
                clearAdapterData();
                initModel();
                setTabSelection(1);
                buy_top_img.setImageDrawable(getResources().getDrawable(R.drawable.top_yellow));
                buytop = true;
            } else {
                pages = 1;
                toplow = 2;
                isChange = true;
                clearAdapterData();
                initModel();
                setTabSelection(1);
                buy_top_img.setImageDrawable(null);
                buy_top_img.setImageDrawable(getResources().getDrawable(R.drawable.low_yellow));
                buytop = false;
            }
        }
        else if(v.getId()==R.id.away_ll){
            if (!awaytop) {
                pages = 1;
                pages = 1;
                toplow = 1;
                type = 3;
                isChange = true;
                isAway = true;
                clearAdapterData();
                getLoctionDatas();
                setTabSelection(2);
                away_top_img.setImageDrawable(getResources().getDrawable(R.drawable.top_yellow));
                awaytop = true;
            } else {
                pages = 1;
                toplow = 2;
                isChange = true;
                clearAdapterData();
                getLoctionDatas();
                setTabSelection(2);
                away_top_img.setImageDrawable(getResources().getDrawable(R.drawable.low_yellow));
                awaytop = false;

            }
        }

//        switch (v.getId()) {
//            case R.id.all_ll:
//                pages = 1;
//                type = 1;
//                isChange = true;
//                isAway = false;
//                clearAdapterData();
//                initModel();
//                setTabSelection(0);
//                break;
//            case R.id.buy_ll:
//                if (!buytop) {
//                    pages = 1;
//                    toplow = 1;
//                    type = 2;
//                    isAway = false;
//                    isChange = true;
//                    clearAdapterData();
//                    initModel();
//                    setTabSelection(1);
//                    buy_top_img.setImageDrawable(getResources().getDrawable(R.drawable.top_yellow));
//                    buytop = true;
//                } else {
//                    pages = 1;
//                    toplow = 2;
//                    isChange = true;
//                    clearAdapterData();
//                    initModel();
//                    setTabSelection(1);
//                    buy_top_img.setImageDrawable(null);
//                    buy_top_img.setImageDrawable(getResources().getDrawable(R.drawable.low_yellow));
//                    buytop = false;
//                }
//
//                break;
//            case R.id.away_ll:
//                if (!awaytop) {
//                    pages = 1;
//                    pages = 1;
//                    toplow = 1;
//                    type = 3;
//                    isChange = true;
//                    isAway = true;
//                    clearAdapterData();
//                    getLoctionDatas();
//                    setTabSelection(2);
//                    away_top_img.setImageDrawable(getResources().getDrawable(R.drawable.top_yellow));
//                    awaytop = true;
//                } else {
//                    pages = 1;
//                    toplow = 2;
//                    isChange = true;
//                    clearAdapterData();
//                    getLoctionDatas();
//                    setTabSelection(2);
//                    away_top_img.setImageDrawable(getResources().getDrawable(R.drawable.low_yellow));
//                    awaytop = false;
//
//                }
//
//                break;
//
//
////            case R.id.buy_top_img:
////                pages = 1;
////                pages = 1;
////                toplow = 1;
////                isChange = true;
////                clearAdapterData();
////                initModel();
////                buy_top_img.setImageDrawable(getResources().getDrawable(R.drawable.top_yellow));
////                break;
////            case R.id.away_top_img:
////                pages = 1;
////                toplow = 1;
////                isChange = true;
////                clearAdapterData();
////                getLoctionDatas();
////                away_top_img.setImageDrawable(getResources().getDrawable(R.drawable.top_yellow));
////                break;
//            default:
//                break;
//        }

    }


    private void setTabSelection(int i) {
        clearSelection();
        switch (i) {
            case 0:
                all_tv.setTextColor(getResources().getColor(R.color.orangeff6c00));
                break;
            case 1:
                buy_tv.setTextColor(getResources().getColor(R.color.orangeff6c00));
                break;
            case 2:
                away_tv.setTextColor(getResources().getColor(R.color.orangeff6c00));
                break;
            default:
                break;
        }

    }

    private void clearSelection() {
        all_tv.setTextColor(getResources().getColor(R.color.gray));
        buy_tv.setTextColor(getResources().getColor(R.color.gray));
        away_tv.setTextColor(getResources().getColor(R.color.gray));

    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationClient != null) locationClient.stop();
    }

    public interface ChocieInterface {
        void buy(int i);

        void away(int i);
    }

}

