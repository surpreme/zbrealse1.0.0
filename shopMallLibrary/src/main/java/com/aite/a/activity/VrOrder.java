package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.Vrorderinfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshView;
import com.lidroid.xutils.BitmapUtils;

public class VrOrder extends BaseActivity implements OnClickListener {
	private PullToRefreshView vrorder_list_refreshView;
	private ListView vrorder_list_lv;
	private TextView vrorder_text, _tv_name;
	private ImageView _iv_back;
	private NetRun netRun;
	private MyAdapder myadapter;
	private List<Vrorderinfo> vrorder_list;
	private Vrorderinfo vrorderinfo;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case get_vrorder_id:
				_tv_name.setText(getI18n(R.string.virtualorders));
				if (msg.obj != null) {
					vrorderinfo = new Vrorderinfo();
					vrorder_list = (List<Vrorderinfo>) msg.obj;
					if (vrorder_list.size() == 0) {
						vrorder_list_refreshView.setVisibility(View.GONE);
						vrorder_text.setVisibility(View.VISIBLE);
					} else {
						myadapter = new MyAdapder(vrorder_list);
						vrorder_list_lv.setAdapter(myadapter);
					}
				} else {
					vrorder_list_refreshView.setVisibility(View.GONE);
					vrorder_text.setVisibility(View.VISIBLE);
				}
				break;
			case get_serviceorder_id:
				_tv_name.setText(getI18n(R.string.servicelorders));
				if (msg.obj != null) {
					vrorderinfo = new Vrorderinfo();
					List<List> vrorder_list = (List<List>) msg.obj;
					if (vrorder_list.size() == 0) {
						vrorder_list_refreshView.setVisibility(View.GONE);
						vrorder_text.setVisibility(View.VISIBLE);
					} else {
						// vrorder_list_lv.setAdapter(new MyAdapder());
					}
				} else {
					vrorder_list_refreshView.setVisibility(View.GONE);
					vrorder_text.setVisibility(View.VISIBLE);
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vrorder);
		findViewById();
		initData();
		initView();

	}

	@Override
	public void ReGetData() {
	}

	@Override
	protected void findViewById() {
		vrorder_list_refreshView = (PullToRefreshView) findViewById(R.id.vrorder_list_refreshView);
		vrorder_list_lv = (ListView) findViewById(R.id.vrorder_list_lv);
		vrorder_text = (TextView) findViewById(R.id.vrorder_text);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
	}

	@Override
	protected void initView() {

		_iv_back.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		bitmapUtils = new BitmapUtils(this);
		netRun = new NetRun(VrOrder.this, handler);
		String type = getIntent().getStringExtra("type");
		if (type.equals("0")) {
			netRun.getVirtualorder();
		} else if (type.equals("1")) {
			netRun.getServiceorder();
		}
	}

	/**
	 * 虚拟订单适配
	 * 
	 * @author Administrator
	 *
	 */
	public class MyAdapder extends BaseAdapter {
		private List<Vrorderinfo> vrorder_list;

		public MyAdapder(List<Vrorderinfo> vrorder_list) {
			this.vrorder_list = vrorder_list;
		}

		@Override
		public int getCount() {
			return vrorder_list.size();
		}

		@Override
		public Object getItem(int position) {
			return vrorder_list == null ? null : vrorder_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(VrOrder.this, R.layout.vr_listitem,
						null);
				new ViewHandler(convertView);
			}
			final ViewHandler handler = (ViewHandler) convertView.getTag();
			handler.tv_vr_order.setText(vrorder_list.get(position).order_sn);
			handler.tv_vr_storename
					.setText(vrorder_list.get(position).store_name+"  >");
			handler.tv_vr_orderstate
					.setText(vrorder_list.get(position).order_state_text);
			handler.tv_vr_goodsname
					.setText(vrorder_list.get(position).goods_name);
			handler.tv_vr_price.setText(vrorder_list.get(position).goods_price);
			handler.tv_vr_num.setText(vrorder_list.get(position).goods_num);
			handler.tv_vr_way.setText(vrorder_list.get(position).payment_name);
			handler.tv_vr_practical
					.setText(vrorder_list.get(position).pd_amount);
			bitmapUtils.display(handler.iv_vr_goodsimage,
					vrorder_list.get(position).goods_image_url);
			handler.ll_vr_goods.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent2 = new Intent(VrOrder.this,ProductDetailsActivity.class);
					intent2.putExtra("goods_id", vrorder_list.get(position).goods_id);
					startActivity(intent2);
				}
			});
			handler.rl_vr_store.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent3 = new Intent(VrOrder.this, StoreDetailsActivity.class);
					intent3.putExtra("store_id", vrorder_list.get(position).store_id);
					startActivity(intent3);
				}
			});
			return convertView;
		}

		class ViewHandler {
			TextView tv_vr_order, tv_vr_storename, tv_vr_orderstate,
					tv_vr_goodsname, tv_vr_price, tv_vr_num, tv_vr_way,
					tv_vr_freight, tv_vr_practical;
			ImageView iv_vr_goodsimage;
			LinearLayout ll_vr_goods;
			RelativeLayout rl_vr_store;
			public ViewHandler(View convertView) {
				tv_vr_order = (TextView) convertView
						.findViewById(R.id.tv_vr_order);
				tv_vr_storename = (TextView) convertView
						.findViewById(R.id.tv_vr_storename);
				tv_vr_orderstate = (TextView) convertView
						.findViewById(R.id.tv_vr_orderstate);
				tv_vr_goodsname = (TextView) convertView
						.findViewById(R.id.tv_vr_goodsname);
				tv_vr_price = (TextView) convertView
						.findViewById(R.id.tv_vr_price);
				tv_vr_num = (TextView) convertView.findViewById(R.id.tv_vr_num);
				tv_vr_way = (TextView) convertView.findViewById(R.id.tv_vr_way);
				tv_vr_freight = (TextView) convertView
						.findViewById(R.id.tv_vr_freight);
				tv_vr_practical = (TextView) convertView
						.findViewById(R.id.tv_vr_practical);
				iv_vr_goodsimage = (ImageView) findViewById(R.id.iv_vr_goodsimage);
				ll_vr_goods=(LinearLayout) convertView.findViewById(R.id.ll_vr_goods);
				rl_vr_store=(RelativeLayout)  convertView.findViewById(R.id.rl_vr_store);
				convertView.setTag(this);
			}
		}
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			finish();
		}
	}
}
