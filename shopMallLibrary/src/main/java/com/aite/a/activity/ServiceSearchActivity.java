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
import com.aite.a.model.TalenthallInfo;
import com.aite.a.model.TalenthallInfo.storelist;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 搜索
 * 
 * @author Administrator
 *
 */
public class ServiceSearchActivity extends BaseActivity implements
		OnClickListener {

	private ImageView _iv_back;
	private TextView _tv_name;
	private ListView lv_talent;
	private int curpage=1;
	private NetRun netRun;
	private TalenthallInfo talenthallInfo;
	private TalentAdapter talentAdapter;
	private BitmapUtils bitmapUtils;
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
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
			}
		};
	};
	private String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_search);
		findViewById();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void findViewById() {
		_iv_back=(ImageView) findViewById(R.id._iv_back);
		_tv_name=(TextView) findViewById(R.id._tv_name);
		lv_talent=(ListView) findViewById(R.id.lv_talent);
		initView();
	}

	@Override
	protected void initView() {
		name = getIntent().getStringExtra("name");
		_iv_back.setBackgroundResource(R.drawable.jd_return);
		_iv_back.setOnClickListener(this);
		netRun=new NetRun(this, handler);
		bitmapUtils=new BitmapUtils(this);
		_tv_name.setText(name);
		lv_talent.setOnScrollListener(listener);
		initData();
	}

	@Override
	protected void initData() {
		netRun.TalentHalllist(curpage + "", name, "", "",
				"");
	}
	
	private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

			if (lv_talent.getLastVisiblePosition() == (lv_talent.getCount() - 1)) {
				curpage++;
				netRun.TalentHalllist(curpage + "", name, "",
						"", "");
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

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
	/**
	 * 人才适配
	 * 
	 * @author Administrator
	 */
	private class TalentAdapter extends BaseAdapter {
		List<storelist> storelist;

		public TalentAdapter(List<storelist> storelist) {
			this.storelist = storelist;
		}

		public void addList(List<storelist> storelist) {
			if (this.storelist == null) {
				this.storelist = new ArrayList<storelist>();
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
				convertView = View.inflate(ServiceSearchActivity.this,
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
					Intent intent = new Intent(ServiceSearchActivity.this,
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
