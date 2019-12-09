package com.aite.a.activity.li.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.NearbyActivity;
import com.aite.a.activity.li.adapter.NearRecyAdapter;
import com.aite.a.activity.li.adapter.PopAdapter;
import com.aite.a.activity.li.bean.AroundAdvertisementBean;
import com.aite.a.activity.li.bean.AroundareaBean;
import com.aite.a.activity.li.bean.NearBean;
import com.aite.a.activity.li.bean.NearChoiceTypeBean;
import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.activity.li.util.PopupWindowUtil;
import com.aite.a.activity.li.util.SpUtils;
import com.aite.a.activity.li.view.AnimationUtils;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.rxbus.RxBus;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

public class AroundActivity extends BaseActivity {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.iv_back)
    ImageView iv_back;
    @BindView(R2.id.smartlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.tv_city)
    LinearLayout tv_city;
    @BindView(R2.id.tv_classification)
    LinearLayout tv_classification;
    @BindView(R2.id.tv_typee)
    LinearLayout tv_typee;
    @BindView(R2.id.tv_distance)
    LinearLayout tv_distance;

    @BindView(R2.id.img_distance)
    ImageView img_distance;
    @BindView(R2.id.nearby_distrinct_tv)
    TextView nearby_distrinct_tv;
    @BindView(R2.id.nearby_category_tv)
    TextView nearby_category_tv;
    @BindView(R2.id.nearby_distance_tv)
    TextView nearby_distance_tv;
    @BindView(R2.id.tv_reset)
    TextView tv_reset;
    @BindView(R2.id.location_tv_name)
    TextView location_tv_name;
    @BindView(R2.id._search_edit)
    EditText _search_edit;


    @BindView(R2.id.top_img)
    ImageView top_img;
    @BindView(R2.id.top_tv_1)
    TextView top_tv_1;
    @BindView(R2.id.top_img_1_1)
    ImageView top_img_1_1;
    @BindView(R2.id.top_img_1_2)
    ImageView top_img_1_2;

    @BindView(R2.id.top_tv_2)
    TextView top_tv_2;
    @BindView(R2.id.top_img_2_1)
    ImageView top_img_2_1;
    @BindView(R2.id.top_img_2_2)
    ImageView top_img_2_2;


    private NearRecyAdapter nearRecyAdapter;
    private LocationClient locationClient;
    private List<NearBean.DatasBean.ListBean> mDatasList = new ArrayList<>();
    private int pages = 1;
    private boolean iscanmore = true;
    private static String loctionnumber = "";
    private String class_id = "";
    private String area_id = "";
    private String sort_dis_type = "";
    private String area_name = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.round_layout;
    }

    @Override
    protected void initView() {
        class_id = "";
        area_id = "";
        iv_back.setOnClickListener(this);
        tv_distance.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        nearRecyAdapter = new NearRecyAdapter(context, mDatasList);
        recyclerView.setAdapter(nearRecyAdapter);
        smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(context));
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
                if (!iscanmore) {
                    smartRefreshLayout.finishLoadMore();
                    return;
                }
                pages++;
                initModel();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });
        initLocationOption();
    }

    @OnClick({R2.id.tv_city, R2.id.tv_classification, R2.id.tv_distance, R2.id.tv_reset,
            R2.id.location_tv_name, R2.id._search_edit})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        } else if (v.getId() == R.id.tv_distance) {
            initAwayOption();
        } else if (v.getId() == R.id.tv_city) {
            initNearAreaChoice();
        } else if (v.getId() == R.id.tv_classification) {
            initNearTypeChoice();
        } else if (v.getId() == R.id.tv_reset) {
            initReset();
            initModel();
        } else if (v.getId() == R.id.location_tv_name) {
            startActivity(NearbyActivity.class, "location", area_name);
        } else if (v.getId() == R.id._search_edit) {
            RxBus.getDefault().post("jumpType", "jumpType");
            onBackPressed();
        }
//        switch (v.getId()) {
//            case R.id.iv_back:
//
//                break;
//            case R.id.tv_distance:
////                initAnima();
//                initAwayOption();
//                break;
//            case R.id.tv_city:
//                initNearAreaChoice();
////                initAnima();
//                break;
//            case R.id.tv_classification:
//                initNearTypeChoice();
//                break;
//            case R.id.tv_reset:
//                initReset();
//                initModel();
//                break;
//            case R.id.location_tv_name:
////                startActivity(NearbyActivity.class);
//
//                break;
//            case R.id._search_edit:
//                RxBus.getDefault().post("jumpType", "jumpType");
//                onBackPressed();
//                break;
//            default:
//                break;
//        }
    }

    private void initReset() {
        area_id = "";
        class_id = "";
        sort_dis_type = "";
        nearby_distance_tv.setText("距离");
        nearby_distrinct_tv.setText("全城");
        nearby_category_tv.setText("分类");
        nearRecyAdapter.cleardata();
        nearRecyAdapter.notifyDataSetChanged();
    }

    private void initAwayOption() {
        final List<String> list = new ArrayList<>();
        list.add("附近");
        list.add("500m以内");
        list.add("500m-1km");
        list.add("1km-2km");
        list.add("2km-5km");
        list.add("5km以上");
        PopupWindowUtil.getmInstance().setNearbottomPop(context, tv_distance, list, new PopAdapter.OnclickInterface() {
            @Override
            public void getPostion(int postion) {
                nearRecyAdapter.cleardata();
                nearRecyAdapter.notifyDataSetChanged();
                if (postion == 0) initReset();
                else
                    sort_dis_type = String.valueOf(postion - 1);
                initModel();
                nearby_distance_tv.setText(postion == 0 ? "距离" : list.get(postion));
                PopupWindowUtil.getmInstance().dismissPopWindow();

            }
        });
    }

    private void initAdvertisement() {
        NetRun netRun = new NetRun(context);
        netRun.onNearAdvertisement(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                LogUtils.d(responseInfo.result);
                AroundAdvertisementBean aroundAdvertisementBean = BeanConvertor.convertBean(responseInfo.result, AroundAdvertisementBean.class);
                Glide.with(context).load(aroundAdvertisementBean.getDatas().getSwiper().get(0).getAdv_pic())
                        .into(top_img);
                Glide.with(context).load(aroundAdvertisementBean.getDatas().getAdv().get(0).getList().get(0).getAdv_pic()).into(top_img_1_1);
                Glide.with(context).load(aroundAdvertisementBean.getDatas().getAdv().get(0).getList().get(1).getAdv_pic()).into(top_img_1_2);
                Glide.with(context).load(aroundAdvertisementBean.getDatas().getAdv().get(1).getList().get(0).getAdv_pic()).into(top_img_2_1);
                Glide.with(context).load(aroundAdvertisementBean.getDatas().getAdv().get(1).getList().get(1).getAdv_pic()).into(top_img_2_2);
                top_tv_1.setText(aroundAdvertisementBean.getDatas().getAdv().get(0).getTitle());
                top_tv_2.setText(aroundAdvertisementBean.getDatas().getAdv().get(1).getTitle());

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initNearAreaChoice() {
        NetRun netRun = new NetRun(context);
        netRun.onNearAreaChoice(area_name, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtils.d(responseInfo.result);
                final AroundareaBean aroundareaBean = BeanConvertor.convertBean(responseInfo.result, AroundareaBean.class);
                if (aroundareaBean.getDatas().getError() != null) return;
                final List<String> list = new ArrayList<>();
                list.add("全城");
                for (int i = 0; i < aroundareaBean.getDatas().getAreaList().size(); i++) {
                    list.add(aroundareaBean.getDatas().getAreaList().get(i).getArea_name());
                }
                PopupWindowUtil.getmInstance().setNearbottomPop(context, tv_distance, list, new PopAdapter.OnclickInterface() {
                    @Override
                    public void getPostion(int postion) {
                        nearRecyAdapter.cleardata();
                        nearRecyAdapter.notifyDataSetChanged();
                        if (postion == 0) initReset();
                        else
                            area_id = aroundareaBean.getDatas().getAreaList().get(postion - 1).getArea_id();
                        class_id = "";
                        initModel();
                        nearby_distrinct_tv.setText(postion == 0 ? "全城" : list.get(postion));
                        PopupWindowUtil.getmInstance().dismissPopWindow();

                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        SpUtils.hideKeyboard(context, _search_edit);

    }

    private void initNearTypeChoice() {
        NetRun netRun = new NetRun(context);
        netRun.onNearTypeChoice(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtils.d(responseInfo.result);
                final NearChoiceTypeBean nearChoiceTypeBean = BeanConvertor.convertBean(responseInfo.result, NearChoiceTypeBean.class);
                final List<String> title = new ArrayList<>();
                title.add("全部分类");
                for (int i = 0; i < nearChoiceTypeBean.getDatas().size(); i++) {
                    title.add(nearChoiceTypeBean.getDatas().get(i).getSc_name());
                }
                PopupWindowUtil.getmInstance().setNearbottomPop(context, tv_distance, title, new PopAdapter.OnclickInterface() {
                    @Override
                    public void getPostion(int postion) {
                        nearRecyAdapter.cleardata();
                        nearRecyAdapter.notifyDataSetChanged();
                        if (postion == 0) initReset();
                        else class_id = nearChoiceTypeBean.getDatas().get(postion - 1).getSc_id();
                        area_id = "";
                        initModel();
                        nearby_category_tv.setText(postion == 0 ? "全部分类" : title.get(postion));
                        PopupWindowUtil.getmInstance().dismissPopWindow();

                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initAnima() {
        AnimationUtils.getmInstance().RotateAnima(img_distance);
    }

    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
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
//            //获取定位精度，默认值为0.0f
//            float radius = location.getRadius();
//            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//            String coorType = location.getCoorType();
//            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
//            int errorCode = location.getLocType();
            LogUtils.d("维度" + latitude + "经度" + longitude + "city" + location.getCity());
            loctionnumber = longitude + "," + latitude;
            area_name = location.getCity();
            LogUtils.d(location.getCity());
            initModel();

        }
    }

    @Override
    protected void initModel() {
        initAdvertisement();
        NetRun netRun = new NetRun(context);
        netRun.onnearAround(loctionnumber, String.valueOf(pages), "", area_id, class_id, "", sort_dis_type, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                NearBean nearBean = BeanConvertor.convertBean(responseInfo.result, NearBean.class);
                if (nearBean.getDatas().getError() != null) return;
                iscanmore = nearBean.getDatas().getPage().isHasmore();
                mDatasList.addAll(nearBean.getDatas().getList());
                nearRecyAdapter.notifyDataSetChanged();
                LogUtils.d(responseInfo.result);
                if (area_name != null && !TextUtils.isEmpty(area_name)) {
                    locationClient.stop();
                    location_tv_name.setText(area_name);

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationClient != null) {
            locationClient.stop();
        }

    }
}
