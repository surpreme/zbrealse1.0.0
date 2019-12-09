package chat.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 定位
 * 
 * @author Administrator
 *
 */
public class PlaceActivity extends BaseActivity implements OnClickListener {
	private MapView mMapView = null;
	private BaiduMap baiduMap;
	private OverlayOptions option;
	private BitmapDescriptor bitmap;
	public LocationClient mLocationClient = null;
	private GeoCoder geocode;
	private TextView _tv_name, _tx_right;
	private ImageView _iv_back, iv_tag;
	private double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_place);
		findViewById();
	}

	@Override
	protected void findViewById() {
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapVieww);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_tx_right = (TextView) findViewById(R.id._tx_right);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_tag = (ImageView) findViewById(R.id.iv_tag);
		baiduMap = mMapView.getMap();
		initView();
	}

	@Override
	protected void initView() {
		_iv_back.setOnClickListener(this);
		_tx_right.setOnClickListener(this);
		_tv_name.setMaxEms(10);
		_tv_name.setSingleLine(true);
		_tv_name.setEllipsize(TruncateAt.END);

		Intent intent2 = getIntent();
		String body = intent2.getStringExtra("body");
		if (body != null) {
			double latitude = intent2.getDoubleExtra("latitude", 0);
			double longitude = intent2.getDoubleExtra("longitude", 0);
			_tv_name.setText(body);
			iv_tag.setVisibility(View.GONE);

			/** 中间坐标 **/
			// 定义Maker坐标点
			LatLng point = new LatLng(latitude, longitude);
			// 构建Marker图标
			bitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marka);
			// 构建MarkerOption，用于在地图上添加Marker
			MarkerOptions option = new MarkerOptions().position(point).icon(
					bitmap);
			// 在地图上添加Marker，并显
			baiduMap.addOverlay(option);
			/** 定位到当前坐标 **/
			// 定义地图状态
			MapStatus mMapStatus = new MapStatus.Builder().target(point)
					.zoom(18).build();
			// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
			// 改变地图状态
			baiduMap.setMapStatus(mMapStatusUpdate);

		} else {
			iv_tag.setVisibility(View.VISIBLE);
			_tv_name.setText(getString(R.string.position));
			_tx_right.setText(getString(R.string.send));
			_tx_right.setTextColor(Color.WHITE);
			_tx_right.setVisibility(View.VISIBLE);
			initLocation();
		}
	}

	@Override
	protected void initData() {

	}

	@Override
	public void ReGetData() {

	}

	/**
	 * 初始化定位
	 */
	private void initLocation() {
		// 新建编码查询对象
		geocode = GeoCoder.newInstance();
		// 设置查询结果监听者
		geocode.setOnGetGeoCodeResultListener(GetGeo);

		LocationClient locationClient = new LocationClient(
				getApplicationContext());
		// 设置定位条件
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 是否打开GPS
		option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
		locationClient.setLocOption(option);
		// 注册位置监听器
		locationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					return;
				}
				/** 中间坐标 **/
				// 定义Maker坐标点
				LatLng point = new LatLng(location.getLatitude(), location
						.getLongitude());
				// 构建Marker图标
				bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.location_tag);
				// 构建MarkerOption，用于在地图上添加Marker
				MarkerOptions option = new MarkerOptions().position(point)
						.icon(bitmap);
				// 在地图上添加Marker，并显
				baiduMap.addOverlay(option);
				/** 定位到当前坐标 **/
				// 定义地图状态
				MapStatus mMapStatus = new MapStatus.Builder().target(point)
						.zoom(18).build();
				// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
						.newMapStatus(mMapStatus);
				// 改变地图状态
				baiduMap.setMapStatus(mMapStatusUpdate);

				// 新建查询对象要查询的条件
				ReverseGeoCodeOption options = new ReverseGeoCodeOption()
						.location(point);
				// 发起反地理编码请求
				geocode.reverseGeoCode(options);
			}

		});
		locationClient.start();
		initListener();

	}
	

	private OnGetGeoCoderResultListener GetGeo = new OnGetGeoCoderResultListener() {
		/**
		 * 反地理编码查询结果回调函数
		 * 
		 * @param result
		 *            反地理编码查询结果
		 */
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				return;
			}
			if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
				// 得到位置
				String address = result.getAddress();
				_tv_name.setText(address);
			}
		}

		/**
		 * 地理编码查询结果回调函数
		 * 
		 *            地理编码查询结果
		 */
		public void onGetGeoCodeResult(GeoCodeResult arg0) {

		}
	};

	/**
	 * 地图状态改变
	 */
	private void initListener() {
		baiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
			@Override
			public void onMapStatusChangeStart(MapStatus status) {
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus status) {
				updateMapState(status);
			}

			@Override
			public void onMapStatusChange(MapStatus status) {
			}
		});
	}

	private void updateMapState(MapStatus status) {
		LatLng mCenterLatLng = status.target;
		/** 获取经纬度 */
		latitude = mCenterLatLng.latitude;
		longitude = mCenterLatLng.longitude;
		// 新建查询对象要查询的条件
		ReverseGeoCodeOption options = new ReverseGeoCodeOption()
				.location(mCenterLatLng);
		// 发起反地理编码请求
		geocode.reverseGeoCode(options);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
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
		if(v.getId()==R.id._tx_right){
			// 发送
			Intent intent2 = getIntent();
			intent2.putExtra("latitude", latitude);
			intent2.putExtra("longitude", longitude);
			intent2.putExtra("locationAddress", _tv_name.getText().toString());
			setResult(6, intent2);
			finish();
		}else if(v.getId()==R.id._iv_back){
			finish();
		}

//		switch (v.getId()) {
//		case R.id._tx_right:
//			// 发送
//			Intent intent2 = getIntent();
//			intent2.putExtra("latitude", latitude);
//			intent2.putExtra("longitude", longitude);
//			intent2.putExtra("locationAddress", _tv_name.getText().toString());
//			setResult(6, intent2);
//			finish();
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		}
	}


}
