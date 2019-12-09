package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.GoodList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 商品评价
 * 
 * @author Administrator
 *
 */
public class GoodsSevaluationActivity extends BaseActivity implements
		OnClickListener {
	private TextView _tv_name;
	private ImageView _iv_back;
	private MyListView mv_list;
	private Button new_btn_submit;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private String order_sn;
	private List<GoodList> goodLists;
	private AyAdapter ayAdapter;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case evaluate_goods_list_id:
				if (msg.obj != null) {
					goodLists = (List<GoodList>) msg.obj;
					ayAdapter = new AyAdapter(goodLists);
					mv_list.setAdapter(ayAdapter);
					// sp_goods.setAdapter(regionList(goodLists));
				} else {
					CommonTools.showShortToast(GoodsSevaluationActivity.this,
							getI18n(R.string.act_no_data));
				}
				mdialog.dismiss();
				break;
			case evaluate_goods_list_err:
				CommonTools.showShortToast(GoodsSevaluationActivity.this,
						getI18n(R.string.act_net_error));
				mdialog.dismiss();
				break;
			case evaluate_goods_list_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case comment_id:
				if (msg.obj == "1") {
					CommonTools.showShortToast(GoodsSevaluationActivity.this,
							getI18n(R.string.evaluation_of_success));
					finish();
				} else {
					CommonTools.showShortToast(GoodsSevaluationActivity.this,
							getI18n(R.string.evaluation_of_fail));
				}
				mdialog.dismiss();
				break;
			case comment_err:
				CommonTools.showShortToast(GoodsSevaluationActivity.this,
						getI18n(R.string.act_net_error));
				new_btn_submit.setEnabled(true);
				mdialog.dismiss();
				break;
			case comment_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsevaluation);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		mv_list = (MyListView) findViewById(R.id.mv_list);
		new_btn_submit = (Button) findViewById(R.id.new_btn_submit);
		initView();
	}

	@Override
	protected void initView() {
		_iv_back.setOnClickListener(this);
		new_btn_submit.setOnClickListener(this);
		_tv_name.setText(getString(R.string.evaluation_tips5));
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		order_sn = getIntent().getStringExtra("order_sn");
		netRun.evaluateGoodList(order_sn);
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
//		case R.id.new_btn_submit:
//			// 提交
//			new_btn_submit.setEnabled(false);
//			netRun.Comment(order_sn, ayAdapter.getjson());
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.new_btn_submit){
			// 提交
			new_btn_submit.setEnabled(false);
			netRun.Comment(order_sn, ayAdapter.getjson());
		}
	}

	/**
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class AyAdapter extends BaseAdapter {
		List<GoodList> goodLists;

		public AyAdapter(List<GoodList> goodLists) {
			this.goodLists = goodLists;
		}

		/**
		 * 获取json
		 * 
		 * @return
		 */
		public String getjson() {
			JSONArray jsonArray = new JSONArray();
			try {
				for (int i = 0; i < goodLists.size(); i++) {
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("goods_id", goodLists.get(i).goods_id);
					jsonObject2.put("evaluate_score", goodLists.get(i).score);
					jsonObject2.put("evaluate_comment", goodLists.get(i).context);
					jsonObject2.put("goods_name", goodLists.get(i).goods_name);
					jsonObject2.put("rec_id", goodLists.get(i).rec_id);
					jsonObject2
							.put("goods_price", goodLists.get(i).goods_price);
					jsonObject2
							.put("goods_image", goodLists.get(i).goods_image);
					jsonArray.put(jsonObject2);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonArray.toString();
		}

		@Override
		public int getCount() {
			return goodLists.size();
		}

		@Override
		public Object getItem(int position) {
			return goodLists == null ? null : goodLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(GoodsSevaluationActivity.this,
						R.layout.item_evaluationlist, null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final GoodList goodList = goodLists.get(position);
			bitmapUtils.display(holder.iv_img, goodList.goods_image_url);
			holder.tv_name.setText(goodList.goods_name);
			holder.tv_price.setText(getString(R.string.storehome33) + goodList.goods_price);
			holder.re_grade
					.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

						@Override
						public void onRatingChanged(RatingBar ratingBar,
								float rating, boolean fromUser) {
							goodList.score = (int) holder.re_grade.getRating();
						}
					});
			holder.et_con.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s.toString() != null) {
						goodList.context = s.toString();
					} else {
						goodList.context = "";
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_img;
			TextView tv_name, tv_price;
			RatingBar re_grade;
			EditText et_con;

			public ViewHolder(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				re_grade = (RatingBar) convertView.findViewById(R.id.re_grade);
				et_con = (EditText) convertView.findViewById(R.id.et_con);
				convertView.setTag(this);
			}
		}
	}

}
