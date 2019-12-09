package com.aite.a.fargment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aite.a.APPSingleton;
import com.aite.a.adapter.OrderGroupListAdapter;
import com.aite.a.base.Mark;
import com.aite.a.model.OrderGroupList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.PullToRefreshView;
import com.aite.a.view.PullToRefreshView.OnFooterRefreshListener;
import com.aite.a.view.PullToRefreshView.OnHeaderRefreshListener;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 订单未付款
 */
@SuppressLint("HandlerLeak")
public class OrderFargment extends Fragment implements OnHeaderRefreshListener, OnFooterRefreshListener {
	private View view;
	private ListView listView;
	private PullToRefreshView mPullToRefreshView;
	/** 当前页面位置 */
	private int currentIndex = 0;
	private String page = "10";
	private NetRun netRun;
	private ProgressDialog mdialog;
	private String curpage = "1", curpage2 = "1", curpage3 = "1", curpage4 = "1", curpage5 = "1";

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	private OrderGroupListAdapter orderAdapter, orderAdapter2, orderAdapter3, orderAdapter4, orderAdapter5;

	private Handler handler = new Handler() { // NO_UCD (use private)
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Mark.order_list_id:
				if (msg.obj == null) {
					if (currentIndex == 0 && Integer.valueOf(curpage) > 1)
						CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.no_more_data).toString());
					if (currentIndex == 1 && Integer.valueOf(curpage2) > 1)
						CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.no_more_data).toString());
					if (currentIndex == 2 && Integer.valueOf(curpage3) > 1)
						CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.no_more_data).toString());
					if (currentIndex == 3 && Integer.valueOf(curpage4) > 1)
						CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.no_more_data).toString());
					if (currentIndex == 4 && Integer.valueOf(curpage5) > 1)
						CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.no_more_data).toString());
					return;
				}
				switch (currentIndex) {
				case 0:
					if (((List<OrderGroupList>) msg.obj).size() > 0) {
						orderAdapter.addOrderList((List<OrderGroupList>) msg.obj);
						orderAdapter.notifyDataSetChanged();
					}
					break;
				case 1:
					if (((List<OrderGroupList>) msg.obj).size() > 0) {
						orderAdapter2.addOrderList((List<OrderGroupList>) msg.obj);
						orderAdapter2.notifyDataSetChanged();
					}
					break;
				case 2:
					if (((List<OrderGroupList>) msg.obj).size() > 0) {
						orderAdapter3.addOrderList((List<OrderGroupList>) msg.obj);
						orderAdapter3.notifyDataSetChanged();
					}
					break;
				case 3:
					if (((List<OrderGroupList>) msg.obj).size() > 0) {
						orderAdapter4.addOrderList((List<OrderGroupList>) msg.obj);
						orderAdapter4.notifyDataSetChanged();
					}
					break;
				case 4:
					if (((List<OrderGroupList>) msg.obj).size() > 0) {
						orderAdapter5.addOrderList((List<OrderGroupList>) msg.obj);
						orderAdapter5.notifyDataSetChanged();
					}
					break;
				}
				break;
			case Mark.detele_order_id:
				if (msg.obj.equals("1")) {
					CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.cancel_successful).toString());
				} else {
					CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.cancel_failure).toString());
				}
				Refresh();
				mdialog.dismiss();
				break;
			case Mark.detele_order_err:
				CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.act_net_error).toString());
				mdialog.dismiss();
				break;
			case Mark.detele_order_start:
//				mdialog.setMessage(APPSingleton.getContext().getString(R.string.act_loading).toString());
				mdialog.show();
				break;
			case Mark.order_receive_id:
				if (msg.obj.equals("1")) {
					CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.confirmation_of_receipt).toString());
				} else {
					CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.confirmation_of_receipt_fail).toString());
				}
				Refresh();
				mdialog.dismiss();
				break;
			case Mark.order_receive_err:
				CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.act_net_error).toString());
				mdialog.dismiss();
				break;
			case Mark.order_receive_start:
//				mdialog.setMessage(APPSingleton.getContext().getString(R.string.act_loading).toString());
				mdialog.show();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.order_list_all, container, false);
		mdialog = new ProgressDialog(getActivity());
		mdialog.setProgressStyle(mdialog.STYLE_SPINNER);
		netRun = new NetRun(getActivity(), handler);
		findViewById();
		return view;
	}

	protected void findViewById() {
		listView = (ListView) view.findViewById(R.id.order_list_lv);
		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.order_list_refreshView);
		initView();
	}

	
	
	private void initView() {
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		switch (currentIndex) {
		case 0:
			orderAdapter = new OrderGroupListAdapter(getActivity(), currentIndex, handler);
			listView.setAdapter(orderAdapter);
			break;
		case 1:
			orderAdapter2 = new OrderGroupListAdapter(getActivity(), currentIndex, handler);
			listView.setAdapter(orderAdapter2);
			break;
		case 2:
			orderAdapter3 = new OrderGroupListAdapter(getActivity(), currentIndex, handler);
			listView.setAdapter(orderAdapter3);
			break;
		case 3:
			orderAdapter4 = new OrderGroupListAdapter(getActivity(), currentIndex, handler);
			listView.setAdapter(orderAdapter4);
			break;
		case 4:
			orderAdapter5 = new OrderGroupListAdapter(getActivity(), currentIndex, handler);
			listView.setAdapter(orderAdapter5);
			break;
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				int show_add = 0;
				switch (currentIndex) {
				case 0:
					show_add = Integer.valueOf(curpage);
					show_add = show_add + 1;
					curpage = String.valueOf(show_add);
					break;
				case 1:
					show_add = Integer.valueOf(curpage2);
					show_add = show_add + 1;
					curpage2 = String.valueOf(show_add);
					break;
				case 2:
					show_add = Integer.valueOf(curpage3);
					show_add = show_add + 1;
					curpage3 = String.valueOf(show_add);
					break;
				case 3:
					show_add = Integer.valueOf(curpage4);
					show_add = show_add + 1;
					curpage4 = String.valueOf(show_add);
					break;
				case 4:
					show_add = Integer.valueOf(curpage5);
					show_add = show_add + 1;
					curpage5 = String.valueOf(show_add);
					break;
				}
				netRun.getOrderList(String.valueOf(currentIndex), page, String.valueOf(show_add));
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				Refresh();
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 2000);
	}

	private void Refresh() {
		switch (currentIndex) {
		case 0:
			curpage = "1";
			orderAdapter.clear();
			netRun.getOrderList(String.valueOf(currentIndex), page, curpage);
			break;
		case 1:
			curpage2 = "1";
			orderAdapter2.clear();
			netRun.getOrderList(String.valueOf(currentIndex), page, curpage2);
			break;
		case 2:
			curpage3 = "1";
			orderAdapter3.clear();
			netRun.getOrderList(String.valueOf(currentIndex), page, curpage3);
			break;
		case 3:
			curpage4 = "1";
			orderAdapter4.clear();
			netRun.getOrderList(String.valueOf(currentIndex), page, curpage4);
			break;
		case 4:
			curpage5 = "1";
			orderAdapter5.clear();
			netRun.getOrderList(String.valueOf(currentIndex), page, curpage5);
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Refresh();
	}
}
