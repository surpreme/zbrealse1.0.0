package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aite.a.activity.StoreHomePageActivity;
import com.aite.a.base.BaseInformation;
import com.aite.a.model.ReleaseTaskInfo;
import com.aite.a.model.TalenthallInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.RelaaseTaskPopu;
import com.aite.a.utils.RelaaseTaskPopu.Sortinghd;
import com.aite.a.utils.RelaaseTaskPopu.calsshuidiao1;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

//人才大厅
public class Fragment_Hall extends BaseInformation implements OnClickListener {

	private LinearLayout ll_all, ll_sort;
	private ListView lv_talent;
	private ImageView iv_talent1, iv_talent4;
	private TextView tv_talent1, tv_talent4, _tv_name;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private TalenthallInfo talenthallInfo;
	private List<ReleaseTaskInfo> releaseTaskInfo;
	private TalentAdapter talentAdapter;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case talent_halllist_id:
				netRun.ServiceDemand();
				talenthallInfo = (TalenthallInfo) msg.obj;
				if (msg.obj != null) {
					if (talentAdapter == null) {
						talentAdapter = new TalentAdapter(
								talenthallInfo.storelist);
						lv_talent.setAdapter(talentAdapter);
					} else {
						talentAdapter.addList(talenthallInfo.storelist);
					}
				}
				break;
			case talent_halllist_err:

				break;
			case service_demand_id:
				if (msg.obj != null) {
					releaseTaskInfo = (List<ReleaseTaskInfo>) msg.obj;
				}
				break;
			case service_demand_err:
				break;
			}
		};
	};

	private void initfindvew() {
		ll_all = (LinearLayout) layout.findViewById(R.id.ll_all);
		ll_sort = (LinearLayout) layout.findViewById(R.id.ll_sort);
		lv_talent = (ListView) layout.findViewById(R.id.lv_talent);
		iv_talent1 = (ImageView) layout.findViewById(R.id.iv_talent1);
		iv_talent4 = (ImageView) layout.findViewById(R.id.iv_talent4);
		tv_talent1 = (TextView) layout.findViewById(R.id.tv_talent1);
		tv_talent4 = (TextView) layout.findViewById(R.id.tv_talent4);
		_tv_name = (TextView) layout.findViewById(R.id._tv_name);
	}

	@Override
	protected void initView() {
		initfindvew();
		ll_all.setOnClickListener(this);
		ll_sort.setOnClickListener(this);
		netRun = new NetRun(getActivity(), handler);
		bitmapUtils = new BitmapUtils(getActivity());
		_tv_name.setText(getString(R.string.talentmarket));
		lv_talent.setOnScrollListener(listener);
	}

	@Override
	protected void initData() {
		netRun.TalentHalllist(curpage + "");
	}

	@Override
	protected int getlayoutResId() {
		return R.layout.fragment_talent;
	}

	@Override
	public void onClick(View v) {
        if(v.getId()==R.id.ll_all){
            if (releaseTaskInfo != null) {
                showpopw(releaseTaskInfo, false);
            }
        }else if(v.getId()==R.id.ll_sort){
            showpopw(null, true);
            if (type.equals("1")) {
                type = "2";
            } else {
                type = "1";
            }
        }

//		switch (v.getId()) {
//		case R.id.ll_all:
//			if (releaseTaskInfo != null) {
//				showpopw(releaseTaskInfo, false);
//			}
//			break;
//		case R.id.ll_sort:
//			showpopw(null, true);
//			if (type.equals("1")) {
//				type = "2";
//			} else {
//				type = "1";
//			}
//			break;
//		}
	}

	private RelaaseTaskPopu relaaseTaskPopu;

	/**
	 * 显示类目选择器
	 * 
	 * @param releaseTaskInfo
	 */
	private void showpopw(List<ReleaseTaskInfo> releaseTaskInfo,
			boolean issorting) {
		relaaseTaskPopu = new RelaaseTaskPopu(getActivity(), releaseTaskInfo,
				2, issorting);// Gravity.TOP,
		relaaseTaskPopu.showAsDropDown(ll_all);// 设置显示位置

		relaaseTaskPopu.setcalsshuidiao1(new calsshuidiao1() {

			@Override
			public void onItemClick(String class1_id, String clas2_id,
					String clas3_id, String class1_name, String clas2_name,
					String clas3_name) {
				category_id1 = class1_id;
				category_id2 = clas2_id;
				category_id3 = clas3_name;

				netRun.TalentHalllist(curpage + "", keywork, clas3_id, typekey,
						type);
				talentAdapter = null;
				// tv_category.setText(class1_name + "  " + clas2_name + "  "
				// + clas3_name);
			}
		});

		relaaseTaskPopu.setSortinghd(new Sortinghd() {

			@Override
			public void onsortingClick(String class1_id) {
				switch (class1_id) {
				case "信用":
					typekey = "sincerity";
					break;
				case "好评率":
					typekey = "goodp";
					break;
				case "交易额":
					typekey = "money";
					break;
				case "中标次数":
					typekey = "alcount";
					break;
				}
				netRun.TalentHalllist(curpage + "", keywork, category_id3,
						typekey, type);
				talentAdapter = null;
			}
		});
	}

	private String typekey = "";
	private String type = "1";
	private String keywork = "";
	private int curpage = 1;

	// 类目ID
	private String category_id1, category_id2, category_id3;

	private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

			if (lv_talent.getLastVisiblePosition() == (lv_talent.getCount() - 1)) {
				curpage++;
				netRun.TalentHalllist(curpage + "", keywork, category_id3,
						typekey, type);
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

	/**
	 * 人才适配
	 * 
	 * @author Administrator
	 */
	private class TalentAdapter extends BaseAdapter {
		List<TalenthallInfo.storelist> storelist;

		public TalentAdapter(List<TalenthallInfo.storelist> storelist) {
			this.storelist = storelist;
		}

		public void addList(List<TalenthallInfo.storelist> storelist) {
			if (this.storelist == null) {
				this.storelist = new ArrayList<TalenthallInfo.storelist>();
			}
			this.storelist.addAll(storelist);
			flushAdapter();
		}

		public void flushAdapter() {
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return storelist.size();
		}

		@Override
		public Object getItem(int position) {
			return storelist == null ? null : storelist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.recommended_talent_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_serviceprovidersimg,
					storelist.get(position).store_image_url);
			holder.tv_serviceprovidersname
					.setText(storelist.get(position).store_name);
			holder.tv_serviceproviderstext
					.setText(storelist.get(position).store_sales);
			holder.tv_serviceprovidersall.setVisibility(View.GONE);
			holder.tv_chance.setText(storelist.get(position).st + "%");
			holder.ll_talentitem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							StoreHomePageActivity.class);
					intent.putExtra("store_id",
							storelist.get(position).store_id);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_serviceprovidersimg;
			TextView tv_serviceprovidersname, tv_serviceproviderstext,
					tv_serviceprovidersall, tv_chance;
			LinearLayout ll_talentitem;

			public ViewHolder(View convertView) {
				iv_serviceprovidersimg = (ImageView) convertView
						.findViewById(R.id.iv_serviceprovidersimg);
				tv_serviceprovidersname = (TextView) convertView
						.findViewById(R.id.tv_serviceprovidersname);
				tv_serviceproviderstext = (TextView) convertView
						.findViewById(R.id.tv_serviceproviderstext);
				tv_serviceprovidersall = (TextView) convertView
						.findViewById(R.id.tv_serviceprovidersall);
				tv_chance = (TextView) convertView.findViewById(R.id.tv_chance);
				ll_talentitem = (LinearLayout) convertView
						.findViewById(R.id.ll_talentitem);
				convertView.setTag(this);
			}
		}
	}

}
