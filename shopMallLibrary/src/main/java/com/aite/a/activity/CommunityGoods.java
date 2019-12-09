package com.aite.a.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.CommunityGoodsInfo;
import com.aite.a.model.CasuallyLook.goods_list;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 推荐产品详情
 * 
 * @author Administrator
 *
 */
public class CommunityGoods extends BaseActivity implements OnClickListener {
	private ImageView iv_title, iv_goodsimg, iv_pl, iv_zan, _iv_back;
	private TextView tv_name, tv_time, tv_goodsname, tv_price, tv_gobuy,
			tv_content, tv_pl, tv_zan, tv_submit, _tv_name;
	private EditText ed_input;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private CommunityGoodsInfo communityGoodsInfo;
	private goods_list goodsinfo = null;
	private com.aite.a.model.StaffPicksInfo.list list = null;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case casuallylook_goodsinfo_id:
				if (msg.obj != null) {
					communityGoodsInfo = (CommunityGoodsInfo) msg.obj;
					// initgoodsinfo(communityGoodsInfo);
				}
				break;
			case casuallylook_goodsinfo_err:
				Toast.makeText(CommunityGoods.this,
						getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			case recommended_evaluation_id:
				if (msg.obj != null) {
					String re = (String) msg.obj;
					if (re.equals("1")) {
						Toast.makeText(CommunityGoods.this,
								getString(R.string.evaluation_of_success),
								Toast.LENGTH_SHORT).show();
						ed_input.setText("");
						return;
					}
					Toast.makeText(CommunityGoods.this, re, Toast.LENGTH_SHORT)
							.show();
					ed_input.setText("");
				}
				break;
			case recommended_evaluation_err:
				Toast.makeText(CommunityGoods.this,
						getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			case look_praise_id:
				if (msg.obj != null) {
					String re = (String) msg.obj;
					if (re.equals("1")) {
						int parseInt = Integer.parseInt(tv_zan.getText()
								.toString());
						parseInt++;
						tv_zan.setText(parseInt + "");
						Toast.makeText(CommunityGoods.this,
								getString(R.string.praisesuccess),
								Toast.LENGTH_SHORT).show();
						return;
					}
					Toast.makeText(CommunityGoods.this, re, Toast.LENGTH_SHORT)
							.show();
					ed_input.setText("");
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_goods);
		findViewById();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void findViewById() {
		iv_title = (ImageView) findViewById(R.id.iv_title);
		iv_goodsimg = (ImageView) findViewById(R.id.iv_goodsimg);
		iv_pl = (ImageView) findViewById(R.id.iv_pl);
		iv_zan = (ImageView) findViewById(R.id.iv_zan);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_goodsname = (TextView) findViewById(R.id.tv_goodsname);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_gobuy = (TextView) findViewById(R.id.tv_gobuy);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_pl = (TextView) findViewById(R.id.tv_pl);
		tv_zan = (TextView) findViewById(R.id.tv_zan);
		tv_submit = (TextView) findViewById(R.id.tv_submit);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		ed_input = (EditText) findViewById(R.id.ed_input);
		initView();
	}

	@Override
	protected void initView() {
		tv_gobuy.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
		iv_zan.setOnClickListener(this);
		iv_pl.setOnClickListener(this);
		_iv_back.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		_iv_back.setImageResource(R.drawable.jd_return);
		_tv_name.setText(getString(R.string.recommendedinfo));
		initData();
	}

	private int type = 0;

	@Override
	protected void initData() {
		type = getIntent().getIntExtra("type", 0);
		switch (type) {
		case 1:
			Serializable serializableExtra = getIntent().getSerializableExtra(
					"goodsinfo");
			goodsinfo = (goods_list) serializableExtra;
			if (goodsinfo != null) {
				initgoodsinfo(goodsinfo);
			} else {
				Toast.makeText(this, getString(R.string.no_product_information), Toast.LENGTH_SHORT).show();
			}
			break;
		case 2:
			Serializable serializableExtra2 = getIntent().getSerializableExtra(
					"list2");
			list = (com.aite.a.model.StaffPicksInfo.list) serializableExtra2;
			if (list != null) {
				initgoodsinfo(list);
			} else {
				Toast.makeText(this, getString(R.string.no_product_information), Toast.LENGTH_SHORT).show();
			}
			break;
		}
//		if (serializableExtra == null) {
//			
//		} else {
//			
//		}
	}

	/**
	 * 初始化随心看信息
	 * 
	 */
	private void initgoodsinfo(
			goods_list goodsinfo) {

		bitmapUtils.display(iv_title, goodsinfo.member_avatar);
		bitmapUtils.display(iv_goodsimg, goodsinfo.commend_goods_image);
		tv_name.setText(goodsinfo.member_name);
		// tv_time.setText(getStrTime(goodsinfo.commend_time));
		tv_goodsname.setText(goodsinfo.commend_goods_name);
		tv_price.setText(goodsinfo.commend_goods_price
				+ getString(R.string.yuan));
		tv_goodsname.setText(goodsinfo.commend_goods_name);
		tv_content.setText(goodsinfo.commend_message);
		tv_pl.setText(goodsinfo.comment_count);
		tv_zan.setText(goodsinfo.like_count);
	}

	/**
	 * 初始化个人秀
	 * 
	 * @param list
	 */
	private void initgoodsinfo(com.aite.a.model.StaffPicksInfo.list list) {
		bitmapUtils.display(iv_title, list.member_avatar);
		bitmapUtils.display(iv_goodsimg, list.commend_image);
		tv_name.setText(list.member_name);
		tv_time.setText(TimeStamp2Date(list.commend_time, "yyyy-MM-dd HH:mm:ss"));
		// tv_time.setText(getStrTime(goodsinfo.commend_time));
		// tv_goodsname.setText(list.commend_goods_name);
		// tv_price.setText(list.commend_goods_price
		// + getString(R.string.yuan));
		tv_goodsname.setVisibility(View.GONE);
		tv_price.setVisibility(View.GONE);
		tv_gobuy.setVisibility(View.GONE);
		tv_content.setText(list.commend_message);
		tv_pl.setText(list.comment_count);
		tv_zan.setText(list.like_count);
	}

	/**
	 * 时间戳转时间
	 * 
	 * @param timestampString
	 * @param formats
	 * @return
	 */
	public String TimeStamp2Date(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formats)
				.format(new Date(timestamp));
		return date;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.tv_gobuy){
			// 去购买
//			System.out.println("-----------------去购买  "+goodsinfo.commend_goods_id);
			if (goodsinfo!=null) {
				Intent intent = new Intent(CommunityGoods.this,
						ProductDetailsActivity.class);
				intent.putExtra("goods_id", goodsinfo.commend_goods_id);
				startActivity(intent);
			}
		}else if(v.getId()==R.id.tv_submit){
			// 提交
			if (TextUtils.isEmpty(ed_input.getText().toString())) {
				Toast.makeText(CommunityGoods.this,
						getString(R.string.input_comments), Toast.LENGTH_SHORT)
						.show();
			} else {
				switch (type) {
					case 1:
						netRun.RecommendedEvaluation(goodsinfo.commend_id, "1",
								ed_input.getText().toString());
						break;
					case 2:
						System.out.println("---------------"+type+"");
						netRun.RecommendedEvaluation(list.personal_id, "2",
								ed_input.getText().toString());
						break;
				}
			}
		}else if(v.getId()==R.id.iv_pl) {
			// 评论
			switch (type) {
				case 1:
					Intent intent2 = new Intent(CommunityGoods.this,
							FoundCommenListActivity.class);
					intent2.putExtra("commend_id", goodsinfo.commend_id);
					intent2.putExtra("type", getString(R.string.foundmenu1));
					intent2.putExtra("comment_type", "1");
					startActivity(intent2);
					break;
				case 2:
					Intent intent3 = new Intent(CommunityGoods.this,
							FoundCommenListActivity.class);
					intent3.putExtra("commend_id", list.personal_id);
					intent3.putExtra("type", getString(R.string.foundmenu2));
					intent3.putExtra("comment_type", "2");
					startActivity(intent3);
			}
		}else if(v.getId()==R.id.iv_zan){
			// 赞
			switch (type) {
				case 1:
					netRun.Praise(goodsinfo.commend_id, "1");
					break;
				case 2:
					netRun.Praise(list.personal_id, "2");
					break;
			}
		}else if(v.getId()==R.id._iv_back){
			// 返回
			Intent intent3 = new Intent(CommunityGoods.this,
					InformationActivity.class);
			setResult(0, intent3);
			finish();
		}

//		switch (v.getId()) {
//		case R.id.tv_gobuy:
//			// 去购买
////			System.out.println("-----------------去购买  "+goodsinfo.commend_goods_id);
//			if (goodsinfo!=null) {
//				Intent intent = new Intent(CommunityGoods.this,
//						ProductDetailsActivity.class);
//				intent.putExtra("goods_id", goodsinfo.commend_goods_id);
//				startActivity(intent);
//			}
//			break;
//		case R.id.tv_submit:
//			// 提交
//			if (TextUtils.isEmpty(ed_input.getText().toString())) {
//				Toast.makeText(CommunityGoods.this,
//						getString(R.string.input_comments), Toast.LENGTH_SHORT)
//						.show();
//			} else {
//				switch (type) {
//				case 1:
//					netRun.RecommendedEvaluation(goodsinfo.commend_id, "1",
//							ed_input.getText().toString());
//					break;
//				case 2:
//					System.out.println("---------------"+type+"");
//					netRun.RecommendedEvaluation(list.personal_id, "2",
//							ed_input.getText().toString());
//					break;
//				}
//			}
//			break;
//		case R.id.iv_pl:
//			// 评论
//			switch (type) {
//			case 1:
//				Intent intent2 = new Intent(CommunityGoods.this,
//						FoundCommenListActivity.class);
//				intent2.putExtra("commend_id", goodsinfo.commend_id);
//				intent2.putExtra("type", getString(R.string.foundmenu1));
//				intent2.putExtra("comment_type", "1");
//				startActivity(intent2);
//				break;
//			case 2:
//				Intent intent3 = new Intent(CommunityGoods.this,
//						FoundCommenListActivity.class);
//				intent3.putExtra("commend_id", list.personal_id);
//				intent3.putExtra("type", getString(R.string.foundmenu2));
//				intent3.putExtra("comment_type", "2");
//				startActivity(intent3);
//				break;
//			}
//
//			break;
//		case R.id.iv_zan:
//			// 赞
//			switch (type) {
//			case 1:
//				netRun.Praise(goodsinfo.commend_id, "1");
//				break;
//			case 2:
//				netRun.Praise(list.personal_id, "2");
//				break;
//			}
//			break;
//		case R.id._iv_back:
//			// 返回
//			Intent intent3 = new Intent(CommunityGoods.this,
//					InformationActivity.class);
//			setResult(0, intent3);
//			finish();
//			break;
//		}
	}

}
