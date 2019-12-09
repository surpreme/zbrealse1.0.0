package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;











import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aite.a.adapter.SellerOrderAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.SellerOrderList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.lingshi;
import com.aite.a.view.PullToRefreshView;
import com.aite.a.view.PullToRefreshView.OnFooterRefreshListener;
import com.aite.a.view.PullToRefreshView.OnHeaderRefreshListener;
import com.aiteshangcheng.a.R;


/**
 * 卖家订单
 * 
 * @author CAOYOU
 * 
 */
public class SellerOrderActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener {
	private List<SellerOrderList> orderList = new ArrayList<SellerOrderList>();
	private ListView seller_order_lv;
	private String state_type;
	private PullToRefreshView seller_order_rv;
	private SellerOrderAdapter orderAdapter;
	private ImageView roller,iv_back;
	private int imageWidth;
	private int offset;
	private int currentIndex;
	private TextView order_yes_cancel,tv_title_name;
	private TextView order_no_pay;
	private TextView order_yes_pay;
	private TextView order_shipped;
	private TextView order_finish;
	private NetRun netRun;
	private String curpage = "1";
	private String page = "5";
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case get_seller_order_id:
				orderList = (List<SellerOrderList>) msg.obj;
				orderAdapter.addOrderList(orderList);
				orderAdapter.notifyDataSetChanged();
				if (orderList.size() < 1) {
					Toast.makeText(SellerOrderActivity.this, getString(R.string.storehome30), Toast.LENGTH_SHORT).show();
				}
//				mdialog.dismiss();
				break;
			case get_seller_order_err:
				Toast.makeText(SellerOrderActivity.this, getString(R.string.storehome11), Toast.LENGTH_SHORT).show();
//				mdialog.dismiss();
				break;
			case get_seller_order_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
//				mdialog.show();
				break;
			case modify_order_id:
				if (msg.obj == "1") {
					Toast.makeText(SellerOrderActivity.this, getString(R.string.storehome31), Toast.LENGTH_SHORT).show();

					netRun.getSellerOrder(state_type, page, 1);
				} else {
					Toast.makeText(SellerOrderActivity.this,  getString(R.string.storehome32), Toast.LENGTH_SHORT).show();

				}
//				mdialog.dismiss();
				break;
			case modify_order_err:
				Toast.makeText(SellerOrderActivity.this, getString(R.string.storehome11), Toast.LENGTH_SHORT).show();
//				mdialog.dismiss();
				break;
			case modify_order_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
//				mdialog.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seller_order);
		netRun = new NetRun(this, handler);
		findViewById();
		initView();
		initCursor(5);
		upCursor(0);
		initData();
	}

	@Override
	protected void onPause() {
		super.onPause();
//		mdialog.dismiss();
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean instance = lingshi.getInstance().isIsupdate();
		if (instance) {
			// 清空之前的数据
			List<Map<String, Object>> data = lingshi.getInstance().getData();
			data.clear();
			// 再加载一次
			order_yes_cancel.performClick();
			lingshi.getInstance().setIsupdate(false);
		}
	}

	public void initCursor(int tagNum) {
		roller = (ImageView) findViewById(R.id.seller_order_iv_cursor);
		imageWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.cursor).getWidth();
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		Display display = getWindowManager().getDefaultDisplay();
		display.getMetrics(displayMetrics);
		offset = (displayMetrics.widthPixels / tagNum - imageWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		roller.setImageMatrix(matrix);

	}

	private void upCursor(int position) {
		int one = offset * 2 + imageWidth; // 一个页卡占的偏移量
		Animation animation = new TranslateAnimation(one * currentIndex, one
				* position, 0, 0);
		currentIndex = position;
		animation.setFillAfter(true);
		animation.setDuration(300);
		roller.startAnimation(animation);
	}

	@Override
	protected void findViewById() {
		seller_order_rv = (PullToRefreshView) findViewById(R.id.seller_order_rv);
		seller_order_rv.setOnHeaderRefreshListener(this);
		seller_order_rv.setOnFooterRefreshListener(this);
		seller_order_lv = (ListView) findViewById(R.id.seller_order_lv);
		order_yes_cancel = (TextView) findViewById(R.id.order_yes_cancel);
		order_no_pay = (TextView) findViewById(R.id.order_no_pay);
		order_yes_pay = (TextView) findViewById(R.id.order_yes_pay);
		order_shipped = (TextView) findViewById(R.id.order_shipped);
		order_finish = (TextView) findViewById(R.id.order_finish);
		iv_back = (ImageView) findViewById(R.id._iv_back);
	}

	@Override
	protected void initView() {
		orderAdapter = new SellerOrderAdapter(this, handler);
		seller_order_lv.setAdapter(orderAdapter);
		tv_title_name = (TextView) findViewById(R.id._tv_name);
		order_yes_cancel.setOnClickListener(this);
		order_no_pay.setOnClickListener(this);
		order_yes_pay.setOnClickListener(this);
		order_shipped.setOnClickListener(this);
		order_finish.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_title_name.setOnClickListener(this);
		iv_back = (ImageView) findViewById(R.id._iv_back);
		tv_title_name.setText(getString(R.string.storehome42));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.order_yes_cancel){
			setBackground(0);
			upCursor(0);
			state_type = "";
		}else if(v.getId()== R.id.order_no_pay){
			setBackground(1);
			upCursor(1);
			state_type = "state_new";
		}else if(v.getId()==R.id.order_yes_pay){
			setBackground(2);
			upCursor(2);
			state_type = "state_pay";
		}else if(v.getId()==R.id.order_shipped){
			setBackground(3);
			upCursor(3);
			state_type = "state_send";
		}else if(v.getId()==R.id.order_finish){
			setBackground(4);
			upCursor(4);
			state_type = "state_success";
		}

//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.order_yes_cancel:
//			setBackground(0);
//			upCursor(0);
//			state_type = "";
//			break;
//		case R.id.order_no_pay:
//			setBackground(1);
//			upCursor(1);
//			state_type = "state_new";
//			break;
//		case R.id.order_yes_pay:
//			setBackground(2);
//			upCursor(2);
//			state_type = "state_pay";
//			break;
//		case R.id.order_shipped:
//			setBackground(3);
//			upCursor(3);
//			state_type = "state_send";
//			break;
//		case R.id.order_finish:
//			setBackground(4);
//			upCursor(4);
//			state_type = "state_success";
//			break;
//		}
		orderAdapter.clear();
		netRun.getSellerOrder(state_type, page, 1);
	}

	private void setBackground(int position) {
		switch (position) {
		case 0:
			order_yes_cancel.setTextColor(getResources().getColor(
					R.color.cursor_text));
			order_no_pay.setTextColor(getResources().getColor(R.color.black));
			order_yes_pay.setTextColor(getResources().getColor(R.color.black));
			order_shipped.setTextColor(getResources().getColor(R.color.black));
			order_finish.setTextColor(getResources().getColor(R.color.black));
			break;
		case 1:
			order_yes_cancel.setTextColor(getResources()
					.getColor(R.color.black));
			order_no_pay.setTextColor(getResources().getColor(
					R.color.cursor_text));
			order_yes_pay.setTextColor(getResources().getColor(R.color.black));
			order_shipped.setTextColor(getResources().getColor(R.color.black));
			order_finish.setTextColor(getResources().getColor(R.color.black));
			break;
		case 2:
			order_yes_cancel.setTextColor(getResources()
					.getColor(R.color.black));
			order_no_pay.setTextColor(getResources().getColor(R.color.black));
			order_yes_pay.setTextColor(getResources().getColor(
					R.color.cursor_text));
			order_shipped.setTextColor(getResources().getColor(R.color.black));
			order_finish.setTextColor(getResources().getColor(R.color.black));
			break;
		case 3:
			order_yes_cancel.setTextColor(getResources()
					.getColor(R.color.black));
			order_no_pay.setTextColor(getResources().getColor(R.color.black));
			order_yes_pay.setTextColor(getResources().getColor(R.color.black));
			order_shipped.setTextColor(getResources().getColor(
					R.color.cursor_text));
			order_finish.setTextColor(getResources().getColor(R.color.black));
			break;
		case 4:
			order_yes_cancel.setTextColor(getResources()
					.getColor(R.color.black));
			order_no_pay.setTextColor(getResources().getColor(R.color.black));
			order_yes_pay.setTextColor(getResources().getColor(R.color.black));
			order_shipped.setTextColor(getResources().getColor(R.color.black));
			order_finish.setTextColor(getResources().getColor(
					R.color.cursor_text));
			break;

		}
	}

	@Override
	protected void initData() {
		state_type = "";
		netRun.getSellerOrder(state_type, page, 1);
	}

//	@Override
//	public void ReGetData() {
//		netRun.getSellerOrder(state_type, page, 1);
//	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		seller_order_rv.postDelayed(new Runnable() {

			@Override
			public void run() {
				int show_add = 0;
				show_add = Integer.valueOf(curpage);
				show_add = show_add + 1;
				curpage = String.valueOf(show_add);
				netRun.getSellerOrder(state_type, page, show_add);
				seller_order_rv.onFooterRefreshComplete();
			}
		}, 1000);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		seller_order_rv.postDelayed(new Runnable() {

			@Override
			public void run() {
				curpage = "1";
				orderAdapter.clear();
				netRun.getSellerOrder(state_type, page, 1);
				seller_order_rv.onHeaderRefreshComplete();
			}
		}, 2000);
	}

	@Override
	public void ReGetData() {
		// TODO Auto-generated method stub
		
	}

}
