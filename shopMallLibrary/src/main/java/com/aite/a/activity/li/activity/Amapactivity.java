package com.aite.a.activity.li.activity;

import android.view.View;
import android.widget.ImageView;

import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 经纬度显示为地图
 * liziyang
 */
public class Amapactivity extends BaseActivity {
    @BindView(R2.id.mapview)
    MapView mapView;
    @BindView(R2.id.iv_back)
    ImageView iv_back;

    @Override
    protected int getLayoutResId() {
        return R.layout.amap_layout;
    }

    @Override
    protected void initView() {
        String str = getIntent().getStringExtra("store_points");
        String[] arr = str.split(",");
        System.out.println(arr[1] + "  " + arr[0]);
        //初始化地图
        BaiduMap mBaidumap = mapView.getMap();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loction_icon_red);
        LatLng cenpt = new LatLng(Double.valueOf(arr[1]), Double.valueOf(arr[0]));
        OverlayOptions option = new MarkerOptions().position(cenpt).icon(bitmap).draggable(true);
        mBaidumap.addOverlay(option);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaidumap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    protected void initModel() {

    }

    @OnClick(R2.id.iv_back)
    @Override
    public void onClick(View v) {
        if (R.id.iv_back == v.getId()) {
            onBackPressed();
        }
    }
}
