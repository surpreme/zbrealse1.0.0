package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ServicehallInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 服务大厅
 * 
 * @author Administrator
 *
 */
public class ServiceHallActivity extends BaseActivity implements
		OnClickListener {
	private TextView _tv_name;
	private ListView lv_service;
	private ImageView _iv_back;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private List<ServicehallInfo> servicehallInfo;
	private Recommended2Adapter recommended2Adapter;
	private int curpage = 1;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case servicehall_id:
				if (msg.obj != null) {
					servicehallInfo = (List<ServicehallInfo>) msg.obj;
					if (recommended2Adapter == null) {
						recommended2Adapter = new Recommended2Adapter(
								servicehallInfo);
						lv_service.setAdapter(recommended2Adapter);
					} else {
						recommended2Adapter.addList(servicehallInfo);
					}
				}
				break;
			case servicehall_err:

				break;
			}
		};
	};
	private String gc_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_service);
		findViewById();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		lv_service = (ListView) findViewById(R.id.lv_service);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		initView();
	}

	@Override
	protected void initView() {
		_iv_back.setBackgroundResource(R.drawable.jd_return);
		_iv_back.setOnClickListener(this);
		netRun = new NetRun(ServiceHallActivity.this, handler);
		bitmapUtils = new BitmapUtils(ServiceHallActivity.this);
		lv_service.setOnScrollListener(listener);
		initData();
	}

	@Override
	protected void initData() {
		String name = getIntent().getStringExtra("name");
		gc_id = getIntent().getStringExtra("gc_id");
		_tv_name.setText(name);
		netRun.Servicehall(curpage + "", gc_id);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		}
		if (v.getId()==R.id._iv_back) {
			finish();
		}
	}

	private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

			if (lv_service.getLastVisiblePosition() == (lv_service.getCount() - 1)) {
				curpage++;
				netRun.Servicehall(curpage + "", gc_id);
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

	/**
	 * 服务列表适配
	 * 
	 * @author Administrator
	 *
	 */
	private class Recommended2Adapter extends BaseAdapter {
		List<ServicehallInfo> servicehallInfo;

		public Recommended2Adapter(List<ServicehallInfo> servicehallInfo) {
			this.servicehallInfo = servicehallInfo;
		}

		public void addList(List<ServicehallInfo> servicehallInfo) {
			if (this.servicehallInfo == null) {
				this.servicehallInfo = new ArrayList<ServicehallInfo>();
			}
			this.servicehallInfo.addAll(servicehallInfo);
			flushAdapter();
		}

		public void flushAdapter() {
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return servicehallInfo.size();
		}

		@Override
		public Object getItem(int position) {
			return servicehallInfo == null ? null : servicehallInfo
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ServiceHallActivity.this,
						R.layout.recommended_servic_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final ServicehallInfo servicehallInfo2 = servicehallInfo
					.get(position);
			bitmapUtils.display(holder.iv_serviceprovidersimg2,
					servicehallInfo2.goods_image_url);
			holder.tv_serviceprovidersname2
					.setText(servicehallInfo2.goods_name);
			holder.tv_type.setText(servicehallInfo2.gc);
			holder.tv_price.setText(servicehallInfo2.goods_price);
			holder.tv_sellnumber.setText(servicehallInfo2.goods_salenum);

			holder.ll_svitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 详情
					Intent intent = new Intent(ServiceHallActivity.this,
							ServiceDetails.class);
					intent.putExtra("goods_id", servicehallInfo2.goods_id);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_serviceprovidersimg2;
			TextView tv_serviceprovidersname2, tv_type, tv_price,
					tv_sellnumber;
			LinearLayout ll_svitem;

			public ViewHolder(View convertView) {
				iv_serviceprovidersimg2 = (ImageView) convertView
						.findViewById(R.id.iv_serviceprovidersimg2);
				tv_serviceprovidersname2 = (TextView) convertView
						.findViewById(R.id.tv_serviceprovidersname2);
				tv_type = (TextView) convertView.findViewById(R.id.tv_type);
				tv_price = (TextView) convertView.findViewById(R.id.tv_fcfpricee);
				tv_sellnumber = (TextView) convertView
						.findViewById(R.id.tv_sellnumber);
				ll_svitem = (LinearLayout) convertView
						.findViewById(R.id.ll_svitem);
				convertView.setTag(this);
			}
		}
	}

}
