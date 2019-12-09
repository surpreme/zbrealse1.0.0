package com.aite.a.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.PullToRefreshView;
import com.aite.a.view.PullToRefreshView.OnFooterRefreshListener;
import com.aite.a.view.PullToRefreshView.OnHeaderRefreshListener;
import com.aiteshangcheng.a.R;

/**
 * 论坛界面
 * 
 * @author Administrator
 *
 */
public class BbsActivity extends BaseActivity implements
		OnHeaderRefreshListener, OnFooterRefreshListener, OnClickListener {
	private ListView lv_bbslist;
	private PullToRefreshView mPullToRefreshView;
	private MyAdapter myadapter;
	private ImageView _iv_back;
	private TextView _tv_name, _tx_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs);
		findViewById();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void findViewById() {
		lv_bbslist = (ListView) findViewById(R.id.lv_bbslist);
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.order_bbslist_refreshView);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_tx_right = (TextView) findViewById(R.id._tx_right);
		initView();
	}

	@Override
	protected void initView() {
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		_iv_back.setOnClickListener(this);
		_tx_right.setOnClickListener(this);
		View inflate = View.inflate(this, R.layout.bbs_listyou, null);
		lv_bbslist.addHeaderView(inflate);
		_tv_name.setText(getI18n(R.string.mallbbs));
		_tx_right.setVisibility(View.VISIBLE);
		_tx_right.setTextColor(Color.WHITE);
		_tx_right.setText(getI18n(R.string.release));
		myadapter = new MyAdapter();
		lv_bbslist.setAdapter(myadapter);
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		mPullToRefreshView.onFooterRefreshComplete();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {

		mPullToRefreshView.onHeaderRefreshComplete();
	}

	private String[] usernamedata = new String[] { "断桥花伤", "花桥端上", "花桥端上",
			"花桥端上", "花桥端上" };
	private int[] imgdata = new int[] { R.drawable.bbs_tu1, R.drawable.bbs_tu2,
			R.drawable.bbs_tu3, R.drawable.bbs_tu4 };

	/**
	 * 论坛 适配
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return usernamedata.length;
		}

		@Override
		public Object getItem(int position) {

			return usernamedata == null ? null : usernamedata[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(BbsActivity.this, R.layout.bbs_item,
						null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_bbs_username.setText(usernamedata[position]);
			MyimageAdapter myimageAdapter = new MyimageAdapter(imgdata);
			holder.gv_bbs_imagelist.setAdapter(myimageAdapter);
			holder.tv_bbs_praise.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String string = holder.tv_bbs_praise.getText().toString();
					int parseInt = Integer.parseInt(string);
					parseInt += 1;
					holder.tv_bbs_praise.setText(parseInt + "");
				}
			});
			holder.rl_bbs_content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent2 = new Intent(BbsActivity.this,CommentsDetailsActivity.class);
					startActivity(intent2);
				}
			});
			return convertView;
		}

		class ViewHolder {
			CircleImageView iv_bbs_titleicon;
			TextView tv_bbs_username, tv_bbs_address, tv_bbs_texttitle,
					tv_bbs_comments, tv_bbs_praise;
			GridView gv_bbs_imagelist;
			RelativeLayout rl_bbs_content;

			public ViewHolder(View convertView) {
				iv_bbs_titleicon = (CircleImageView) convertView
						.findViewById(R.id.iv_bbs_titleicon);
				tv_bbs_username = (TextView) convertView
						.findViewById(R.id.tv_bbs_username);
				tv_bbs_address = (TextView) convertView
						.findViewById(R.id.tv_bbs_address);
				tv_bbs_texttitle = (TextView) convertView
						.findViewById(R.id.tv_bbs_texttitle);
				tv_bbs_comments = (TextView) convertView
						.findViewById(R.id.tv_bbs_comments);
				tv_bbs_praise = (TextView) convertView
						.findViewById(R.id.tv_bbs_praise);
				gv_bbs_imagelist = (GridView) convertView
						.findViewById(R.id.gv_bbs_imagelist);
				rl_bbs_content = (RelativeLayout) convertView
						.findViewById(R.id.rl_bbs_content);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 说说图片适配
	 * 
	 * @author Administrator
	 *
	 */
	private class MyimageAdapter extends BaseAdapter {
		int[] imgdata;

		public MyimageAdapter(int[] imgdata) {
			this.imgdata = imgdata;
		}

		@Override
		public int getCount() {

			return imgdata.length;
		}

		@Override
		public Object getItem(int position) {

			return imgdata == null ? null : imgdata[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(BbsActivity.this,
						R.layout.bbs_imagelist, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.iv_img.setBackgroundResource(imgdata[position]);
			return convertView;
		}

		class ViewHolder {
			ImageView iv_img;

			public ViewHolder(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
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
//		case R.id._tx_right:
//			Intent intent2 = new Intent(BbsActivity.this,
//					ReleasepostActivity.class);
//			startActivity(intent2);
//			break;
//		}

		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id._tx_right){
			Intent intent2 = new Intent(BbsActivity.this,
					ReleasepostActivity.class);
			startActivity(intent2);
		}
	}

}
