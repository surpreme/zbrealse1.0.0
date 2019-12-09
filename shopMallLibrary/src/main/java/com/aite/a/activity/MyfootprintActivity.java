package com.aite.a.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.Myfootprintinfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的足迹
 *
 * @author Administrator
 *
 */
public class MyfootprintActivity extends BaseActivity implements
		OnClickListener {
	private ListView lv_myfootprintlist;
	private ImageView _iv_back;
	private TextView _tv_name,_tx_right;
	private NetRun netRun;
	private Myadapder myadapder;
	private List<Myfootprintinfo> browselist = new ArrayList<>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case get_myfootprint_id:
					if (msg.obj != null) {
						browselist = (List<Myfootprintinfo>) msg.obj;
						lv_myfootprintlist.setAdapter(myadapder);
					}
					break;
				case empty_myfootprint_id:
					browselist.clear();
					myadapder.refreshadapder();
					break;
				case detele_myfootprint_id:
					if (msg.obj != null) {
						if (msg.obj.equals("1")) {
							netRun.getMyfootprint("0");
						}
					}
					break;
				case detele_myfootprint_err:
					CommonTools.showShortToast(MyfootprintActivity.this, getString(R.string.act_del_fail));
					break;
			}
		};
	};
	int widthpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acticity_myfootprint);
		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		widthpm = wm.getDefaultDisplay().getWidth();
		findViewById();
		initData();
		initView();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (State.UserKey == null) {
			Intent intent2 = new Intent(MyfootprintActivity.this,
					LoginActivity.class);
			startActivity(intent2);
			finish();
		}
	}

	@Override
	protected void findViewById() {
		lv_myfootprintlist = (ListView) findViewById(R.id.lv_myfootprintlist);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_tx_right =  findViewById(R.id._tx_right);
		_tx_right.setVisibility(View.VISIBLE);
		_tx_right.setOnClickListener(this);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		myadapder = new Myadapder();
		_iv_back.setOnClickListener(this);
		bitmapUtils = new BitmapUtils(MyfootprintActivity.this);

		lv_myfootprintlist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				String goods_id = browselist.get(position).getGoods_id();
				Intent intent2 = new Intent(MyfootprintActivity.this,
						ProductDetailsActivity.class);
				intent2.putExtra("goods_id", goods_id);
				startActivity(intent2);
			}
		});
	}

	@Override
	protected void initView() {
		_tv_name.setText(getI18n(R.string.my_footprint));
	}

	@Override
	protected void initData() {
		netRun = new NetRun(this, handler);
		netRun.getMyfootprint("0");
	}

	@Override
	public void onClick(View v) {
		if(v.getId()== R.id._iv_back){
			finish();
		}else if(v.getId()==R.id._tx_right){
			// TODO 清空足迹
			netRun.emptyMyfootprint();
		}
//		switch (v.getId()) {
//			case R.id._iv_back:
//				finish();
//				break;
//			case R.id._tx_right:
//				// TODO 清空足迹
//				netRun.emptyMyfootprint();
//				break;
//		}
	}

	public class Myadapder extends BaseAdapter {

		@Override
		public int getCount() {

			return browselist.size();
		}

		@Override
		public Object getItem(int position) {
			return browselist.get(position);
		}

		// 刷新
		public void refreshadapder() {
			notifyDataSetChanged();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(MyfootprintActivity.this,
						R.layout.myfootprint_item, null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_goodsnamemi.setText(browselist.get(position)
					.getGoods_name() + "");
			holder.tv_goodsprice.setText(browselist.get(position)
					.getGoods_promotion_price() + "");
			bitmapUtils.display(holder.iv_goodstu, browselist.get(position)
					.getGoods_image());
			holder.iv_close.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					netRun.deteleMyfootprint(browselist.get(position)
							.getGoods_id());
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_goodsnamemi;
			TextView tv_goodsprice;
			ImageView iv_goodstu,iv_close;
			RelativeLayout rl_zhuitem;

			public ViewHolder(View convertView) {
				tv_goodsnamemi = (TextView) convertView
						.findViewById(R.id.tv_goodsnamemi);
				tv_goodsprice = (TextView) convertView
						.findViewById(R.id.tv_goodsprice);
				iv_goodstu = (ImageView) convertView
						.findViewById(R.id.iv_goodstu);
				iv_close = (ImageView) convertView
						.findViewById(R.id.iv_close);
				rl_zhuitem = (RelativeLayout) convertView
						.findViewById(R.id.rl_zhuitem);
				convertView.setTag(this);
			}
		}
	}
}
