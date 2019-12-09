package com.aite.a.activity;

import java.util.List;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.activity.MyOrientationListener.OnOrientationListener;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.NearbyList.NearbyStoreList;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.lidroid.xutils.BitmapUtils;

public class BaiduMapActivity extends BaseActivity implements OnClickListener{
	/** 地图控件 */
	private MapView mMapView = null;
	/** 地图实例 */
	private BaiduMap mBaiduMap;
	/** 定位的客户端 */
	private LocationClient mLocationClient;
	/** 定位的监听器 */
	public MyLocationListener mMyLocationListener;
	/** 当前定位的模 */
	private LocationMode mCurrentMode = LocationMode.NORMAL;
	/*** 是否是第一次定位 */
	private volatile boolean isFristLocation = true;
	private volatile boolean isLocationThreadRun = true;
	/** 最新一次的经纬度 */
	private double mCurrentLantitude;
	private double mCurrentLongitude;
	/** 当前的精度 */
	private float mCurrentAccracy;
	/** 方向传感器的监听器 */
	private MyOrientationListener myOrientationListener;
	/** 方向传感器X方向的值 */
	private int mXDirection;
	/** 地图定位的模式 */
	private int mCurrentStyle = 0;
	// 初始化全局 bitmap 信息，不用时及时 recycle
	private BitmapDescriptor mIconMaker;
	/** 详细信息的 布局 */
	private RelativeLayout mMarkerInfoLy;
	private TextView map_style;
	private ImageView iv_back;
	private Thread locatioThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 注意该方法要再setContentView方法之前实现
//		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.baidu_map);
		findViewById();
		initView();
		initData();
	}

	@Override
	protected void findViewById() {
		tv_title_name = (TextView) findViewById(R.id._tv_name);
		iv_back = (ImageView) findViewById(R.id._iv_back);
		mMapView = (MapView) findViewById(R.id.id_bmapView);
		mMarkerInfoLy = (RelativeLayout) findViewById(R.id.id_marker_info);
		map_style = (TextView) findViewById(R.id.id_menu_map_style);
	}

	@Override
	protected void initView() {
		map_style.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_title_name.setText(getI18n(R.string.see_map));
		iv_back.setBackgroundResource(R.drawable.jd_return);
		locatioThread = new Thread(new MyThread());
		bitmapUtils = new BitmapUtils(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initData() {
		// 第一次定位
		isFristLocation = true;
		// 获得地图的实例
		mBaiduMap = mMapView.getMap();
		mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		initOritationListener();
		initMyLocation();
		initMarkerClickEvent();
		initMapClickEvent();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			List<NearbyStoreList> storeLists = (List<NearbyStoreList>) bundle.get("nearby");
			String pointss = bundle.getString("pointss",null);
			addInfosOverlay(storeLists,pointss);
		}

	}
	
	
	
	
	private void initMapClickEvent() {
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				mMarkerInfoLy.setVisibility(View.GONE);
				map_style.setVisibility(View.VISIBLE);
				mBaiduMap.hideInfoWindow();
			}
		});
	}

	private void initMarkerClickEvent() {
		// 对Marker的点击
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				// 获得marker中的数据
				final NearbyStoreList info = (NearbyStoreList) marker.getExtraInfo().get("info");
				// 生成一个TextView用户在地图中显示InfoWindow
				TextView location = new TextView(getApplicationContext());
				location.setBackgroundResource(R.drawable.location_tips);
				location.setPadding(30, 20, 30, 50);
				location.setText(info.store_name);
				// 将marker所在的经纬度的信息转化成屏幕上的坐标
				final LatLng ll = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				// 为弹出的InfoWindow添加点击事件
				InfoWindow mInfoWindow = new InfoWindow(location, llInfo, 0);
				// 显示InfoWindow
				mBaiduMap.showInfoWindow(mInfoWindow);
				// 设置详细信息布局为可见
				mMarkerInfoLy.setVisibility(View.VISIBLE);
				map_style.setVisibility(View.GONE);
				// 根据商家信息为详细信息布局设置信息
				popupInfo(mMarkerInfoLy, info);
				mMarkerInfoLy.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("store_id", info.store_id);
						openActivity(StoreDetailsActivity.class, bundle);
					}
				});
				location.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("store_id", info.store_id);
						openActivity(StoreDetailsActivity.class, bundle);
					}
				});
				return true;
			}
		});
	}

	/**
	 * 根据info为布局上的控件设置信息
	 * 
	 * @param info
	 */
	protected void popupInfo(RelativeLayout mMarkerLy, NearbyStoreList info) {
		ViewHolder viewHolder = null;
		if (mMarkerLy.getTag() == null) {
			viewHolder = new ViewHolder();
			viewHolder.infoImg = (ImageView) mMarkerLy.findViewById(R.id.info_img);
			viewHolder.infoName = (TextView) mMarkerLy.findViewById(R.id.info_name);
			viewHolder.infoDistance = (TextView) mMarkerLy.findViewById(R.id.info_distance);
			viewHolder.info_area = (TextView) mMarkerLy.findViewById(R.id.info_area);
			mMarkerLy.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) mMarkerLy.getTag();
		String distance = null;
		double distance2 = Double.valueOf(info.distance);
		if (distance2 > 1000) {
			distance = String.valueOf((int) (distance2 / 1000)) + getI18n(R.string.km);
		} else {
			distance = (String) ((int) distance2 + getI18n(R.string.m));
		}
		if (info.store_label != null && !info.store_label.equals("")) {
			bitmapUtils.display(viewHolder.infoImg, info.store_label.toString());
		} else {
			viewHolder.infoImg.setBackgroundResource(R.drawable.zanwushuju);
		}
		viewHolder.infoDistance.setText(distance);
		viewHolder.infoName.setText(info.store_name);
		viewHolder.info_area.setText(info.area_info);
	}

	/**
	 * 复用弹出面板mMarkerLy的控件
	 * 
	 * @author zhy
	 * 
	 */
	private class ViewHolder {
		ImageView infoImg;
		TextView infoName;
		TextView infoDistance;
		TextView info_area;
	}

	/**
	 * 初始化方向传感器
	 */
	private void initOritationListener() {
		myOrientationListener = new MyOrientationListener(getApplicationContext());
		myOrientationListener.setOnOrientationListener(new OnOrientationListener() {
			@Override
			public void onOrientationChanged(float x) {
				mXDirection = (int) x;
				// 构造定位数据
				MyLocationData locData = new MyLocationData.Builder().accuracy(mCurrentAccracy)
				// 此处设置开发者获取到的方向信息，顺时针0-360
						.direction(mXDirection).latitude(mCurrentLantitude).longitude(mCurrentLongitude).build();
				// 设置定位数据
				mBaiduMap.setMyLocationData(locData);
			}
		});
	}

	/**
	 * 初始化定位相关代码
	 */
	private void initMyLocation() {
		// 定位初始化
		mLocationClient = new LocationClient(this);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		// 设置定位的相关配置
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 初始化图层
	 */
	public void addInfosOverlay(List<NearbyStoreList> infos,String pointss) {
		mBaiduMap.clear();
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		
		for (NearbyStoreList info : infos) {
			try {
				if (pointss==null) {
				String[] strarray = info.store_points.split(",");
				// 位置
				latLng = new LatLng(Double.valueOf(strarray[1]), Double.valueOf(strarray[0]));
				}else {
				String[] strarray = pointss.split(",");
				// 位置
				latLng = new LatLng(Double.valueOf(strarray[1]), Double.valueOf(strarray[0]));
				}
				// 图标
				overlayOptions = new MarkerOptions().position(latLng).icon(mIconMaker).zIndex(5);
				marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", info);
				marker.setExtraInfo(bundle);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 将地图移到到最后一个经纬度位置
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(u);
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(mXDirection).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mCurrentAccracy = location.getRadius();
			// 设置定位数据
			mBaiduMap.setMyLocationData(locData);
			mCurrentLantitude = location.getLatitude();
			mCurrentLongitude = location.getLongitude();
			// 第一次定位时，将地图位置移动到当前位置
			if (isFristLocation) {
				isFristLocation = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}
	}

	/**
	 * 地图移动到我的位置,此处可以重新发定位请求，然后定位； 直接拿最近一次经纬度，如果长时间没有定位成功，可能会显示效果不好
	 */
	private void center2myLoc() {
		LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
	}

	class MyThread implements Runnable {
		@Override
		public void run() {
			// 开启图层定位
			mBaiduMap.setMyLocationEnabled(true);
			if (!mLocationClient.isStarted()) {
				mLocationClient.start();
			}
			myOrientationListener.start();
		}
	}

	@Override
	protected void onStop() {
		// 关闭图层定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		locatioThread.interrupt();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mIconMaker.recycle();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.id_menu_map_style:
//			mCurrentStyle = (++mCurrentStyle) % 3;
//			BitmapDescriptor mCurrentMarker = null;
//			// 设置自定义图标
//			switch (mCurrentStyle) {
//			case 0:
//				mCurrentMode = LocationMode.NORMAL;
//				mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
//				map_style.setBackgroundResource(R.drawable.navi_map_gps_locked);
//				break;
//			case 1:
//				mCurrentMode = LocationMode.FOLLOWING;
//				mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.main_icon_follow);
//				map_style.setBackgroundResource(R.drawable.main_icon_follow);
//				break;
//			case 2:
//				mCurrentMode = LocationMode.COMPASS;
//				mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.main_icon_compass);
//				map_style.setBackgroundResource(R.drawable.main_icon_compass);
//				break;
//			}
//			MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
//			mBaiduMap.setMyLocationConfigeration(config);
//			if (isFristLocation) {
//				isFristLocation = false;
//				locatioThread.start();
//			}
//			break;
//		}


		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.id_menu_map_style){
			mCurrentStyle = (++mCurrentStyle) % 3;
			BitmapDescriptor mCurrentMarker = null;
			// 设置自定义图标
			switch (mCurrentStyle) {
				case 0:
					mCurrentMode = LocationMode.NORMAL;
					mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
					map_style.setBackgroundResource(R.drawable.navi_map_gps_locked);
					break;
				case 1:
					mCurrentMode = LocationMode.FOLLOWING;
					mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.main_icon_follow);
					map_style.setBackgroundResource(R.drawable.main_icon_follow);
					break;
				case 2:
					mCurrentMode = LocationMode.COMPASS;
					mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.main_icon_compass);
					map_style.setBackgroundResource(R.drawable.main_icon_compass);
					break;
			}
			MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
			mBaiduMap.setMyLocationConfigeration(config);
			if (isFristLocation) {
				isFristLocation = false;
				locatioThread.start();
			}
		}
	}

	@Override
	public void ReGetData() {
		// TODO Auto-generated method stub
		
	}

}
