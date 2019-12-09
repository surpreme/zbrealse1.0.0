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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegralCartInfo;
import com.aite.a.model.IntegralCartInfo.cart_array;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 积分购物车
 * 
 * @author Administrator
 *
 */
public class IntegralCartActivity extends BaseActivity implements
		OnClickListener {
	private TextView _tv_name, tv_totalscore, tv_ensure;
	private ImageView _iv_back;
	private ListView lv_gift;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private IntegralCartInfo integralCartInfo;
	private GiftAdapter giftAdapter;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case brand_classify_id:
				if (msg.obj != null) {
					integralCartInfo = (IntegralCartInfo) msg.obj;
					if (giftAdapter==null) {
						giftAdapter = new GiftAdapter(integralCartInfo.cart_array);
						lv_gift.setAdapter(giftAdapter);
					}else {
						giftAdapter.updata(integralCartInfo.cart_array);
					}
					tv_totalscore.setText(getString(R.string.integral_prompt7)+integralCartInfo.pgoods_pointall+getString(R.string.integrall));
				}
				break;
			case brand_classify_err:
				Toast.makeText(IntegralCartActivity.this, getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			case integralcart_upnum_id:
				//修改数量
				if (msg.obj!=null) {
					String str=(String) msg.obj;
					if (str.equals("true")) {
						netRun.IntegralCart();
					}
				}
				break;
			case integralcart_upnum_err:
				Toast.makeText(IntegralCartActivity.this, getString(R.string.integrall),
						Toast.LENGTH_SHORT).show();
				break;
			case integralcart_del_id:
				//删除
				if (msg.obj!=null) {
					String str=(String) msg.obj;
					if (str.equals("true")) {
						netRun.IntegralCart();
					}
				}
				break;
			case integralcart_del_err:
				Toast.makeText(IntegralCartActivity.this, getString(R.string.integrall),
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.integralcart_activity);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		lv_gift = (ListView) findViewById(R.id.lv_gift);
		tv_totalscore = (TextView) findViewById(R.id.tv_totalscore);
		tv_ensure = (TextView) findViewById(R.id.tv_ensure);
		initView();
	}

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		_tv_name.setText(getString(R.string.evaluation_tips35));
		_iv_back.setOnClickListener(this);
		tv_ensure.setOnClickListener(this);
		initData();
	}

	@Override
	protected void initData() {
		netRun.IntegralCart();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_ensure:
//			// 确认
//			Intent intent2 = new Intent(IntegralCartActivity.this,ConfirmIntegralActivity.class);
//			startActivity(intent2);
//			finish();
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.tv_ensure){
			// 确认
			Intent intent2 = new Intent(IntegralCartActivity.this,ConfirmIntegralActivity.class);
			startActivity(intent2);
			finish();
		}
	}

	/**
	 * 购物车
	 * 
	 * @author Administrator
	 *
	 */
	private class GiftAdapter extends BaseAdapter {
		List<cart_array> cart_array;

		public void updata(List<cart_array> cart_array){
			this.cart_array = cart_array;
			notifyDataSetChanged();
		}
		
		public GiftAdapter(List<cart_array> cart_array) {
			this.cart_array = cart_array;
		}

		@Override
		public int getCount() {
			return cart_array.size();
		}

		@Override
		public Object getItem(int position) {
			return cart_array == null ? null : cart_array.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(IntegralCartActivity.this,
						R.layout.integralcart_item, null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final cart_array cart_array2 = cart_array.get(position);
			bitmapUtils.display(holder.iv_gimg, cart_array2.pgoods_image);
			holder.tv_gname.setText(cart_array2.pgoods_name);
			holder.tv_num.setText(getString(R.string.integral_prompt3) + cart_array2.pgoods_choosenum);
			holder.tv_total.setText(getString(R.string.evaluation_tips36) + cart_array2.pgoods_pointone
					+ getString(R.string.integrall));
			holder.add_sub_tv_num.setText(cart_array2.pgoods_choosenum);
			holder.iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 删除
					netRun.IntegralCartDel(cart_array2.pgoods_id);
				}
			});
			holder.add_sub_btn_sub.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 减少数量
					UpNum(holder.add_sub_tv_num.getText().toString(),
							cart_array2.pgoods_id, 2);
				}
			});
			holder.add_sub_btn_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 增加数量
					UpNum(holder.add_sub_tv_num.getText().toString(),
							cart_array2.pgoods_id, 1);
				}
			});

			return convertView;
		}

		class ViewHolder {
			ImageView iv_gimg, iv_del;
			TextView tv_gname, tv_num, tv_total, add_sub_tv_num;
			Button add_sub_btn_sub, add_sub_btn_add;

			public ViewHolder(View convertView) {
				iv_gimg = (ImageView) convertView.findViewById(R.id.iv_gimg);
				iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
				tv_gname = (TextView) convertView.findViewById(R.id.tv_gname);
				tv_num = (TextView) convertView.findViewById(R.id.tv_num);
				tv_total = (TextView) convertView.findViewById(R.id.tv_total);
				add_sub_tv_num = (TextView) convertView
						.findViewById(R.id.add_sub_tv_num);
				add_sub_btn_sub = (Button) convertView
						.findViewById(R.id.add_sub_btn_sub);
				add_sub_btn_add = (Button) convertView
						.findViewById(R.id.add_sub_btn_add);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 更新数量
	 * 
	 * @param num
	 */
	private void UpNum(String num, String id, int tepe) {
		int parseInt = Integer.parseInt(num);
		if (tepe == 1) {
			parseInt++;
		} else if (tepe == 2) {
			if (parseInt > 1) {
				parseInt--;
			}
		}
		netRun.IntegralCartUpNum(id, parseInt + "");
	}

}
