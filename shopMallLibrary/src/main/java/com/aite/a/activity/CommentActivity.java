package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.GoodList;
import com.aite.a.model.good_arr;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.EditTextWithDel;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.lidroid.xutils.BitmapUtils;

/**
 * 评价
 * 
 * @author Administrator
 *
 */
public class CommentActivity extends BaseActivity implements OnClickListener {
	private RatingBar re_grade;
	private EditTextWithDel et_content;
	private TextView tv_submit;
	private NetRun netRun;
	private List<GoodList> goodLists;
	private String order_sn;
	private Spinner sp_goods;
	private ImageView iv_goodsimmg,iv_yingliang;
	private ImageButton iv_yuyin;
	private int position;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case evaluate_goods_list_id:
				if (msg.obj != null) {
					goodLists = (List<GoodList>) msg.obj;
					sp_goods.setAdapter(regionList(goodLists));
				} else {
					CommonTools.showShortToast(CommentActivity.this,
							getI18n(R.string.act_no_data));
				}
				mdialog.dismiss();
				break;
			case evaluate_goods_list_err:
				CommonTools.showShortToast(CommentActivity.this,
						getI18n(R.string.systembusy));
				mdialog.dismiss();
				break;
			case evaluate_goods_list_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case comment_id:
				if (msg.obj == "1") {
					CommonTools.showShortToast(CommentActivity.this,
							getI18n(R.string.evaluation_of_success));
					finish();
				} else {
					CommonTools.showShortToast(CommentActivity.this,
							getI18n(R.string.evaluation_of_fail));
				}
				mdialog.dismiss();
				break;
			case comment_err:
				CommonTools.showShortToast(CommentActivity.this,
						getI18n(R.string.systembusy));
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
		setContentView(R.layout.comment_activity);
		netRun = new NetRun(this, handler);
		findViewById();
		initView();
	}

	@Override
	public void ReGetData() {
	}

	@Override
	protected void findViewById() {
		re_grade = (RatingBar) findViewById(R.id.re_grade);
		et_content = (EditTextWithDel) findViewById(R.id.et_content);
		tv_submit = (TextView) findViewById(R.id.tv_submit);
		sp_goods = (Spinner) findViewById(R.id.sp_goods);
		tv_title_name = (TextView) findViewById(R.id._tv_name);
		iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_yingliang=(ImageView) findViewById(R.id.iv_yingliang);
		tv_title_name.setText(getI18n(R.string.evaluation));
		iv_goodsimmg = (ImageView) findViewById(R.id.iv_goodsimmg);
		iv_yuyin = (ImageButton) findViewById(R.id.iv_yuyin);
		xunfei();
		iv_yuyin.setOnTouchListener(l);
		tv_submit.setOnClickListener(this);
		iv_back.setOnClickListener(this);
	}

	private OnTouchListener l = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isrepeat=false;
				mIat.startListening(mRecoListener);
				iv_yingliang.setVisibility(View.VISIBLE);
				break;
			case MotionEvent.ACTION_UP:
				mIat.stopListening();
				//清空内容
				sb.setLength(0);
				iv_yingliang.setVisibility(View.GONE);
				break;
			}
			return false;
		}
	};

	@Override
	protected void initView() {
		order_sn = getIntent().getStringExtra("order_sn");
		netRun.evaluateGoodList(order_sn);
		sp_goods.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int i,
					long id) {
				position = i;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void initData() {
		List<good_arr> good_arrs = new ArrayList<good_arr>();
		String goods_id = goodLists.get(position).goods_id;
		String goods_score = String.valueOf(re_grade.getRating());
		String goods_comment = et_content.getText().toString();
		String goods_name = goodLists.get(position).goods_name;
		String rec_id = goodLists.get(position).rec_id;
		String goods_price = goodLists.get(position).goods_price;
		String goods_image = goodLists.get(position).goods_image_url;
		good_arr good_arr = new good_arr(goods_id, goods_score, goods_comment,
				goods_name, rec_id, goods_price, goods_image);
		good_arrs.add(good_arr);
		String t = new Gson().toJson(good_arrs);
		netRun.Comment(order_sn, t);
	}

	private SpeechRecognizer mIat;

	private void xunfei() {
		// 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
		mIat = SpeechRecognizer.createRecognizer(this, null);
		// 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
		mIat.setParameter(SpeechConstant.DOMAIN, "iat");
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
		// 3.开始听写 mIat.startListening(mRecoListener);

	}
	boolean isrepeat=false;
	StringBuffer sb = new StringBuffer();
	// 听写监听器
	private RecognizerListener mRecoListener = new RecognizerListener() {
		// 听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
		// 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
		// 关于解析Json的代码可参见MscDemo中JsonParser类；
		// isLast等于true时会话结束。
		@Override
		public void onResult(RecognizerResult arg0, boolean arg1) {
			// System.out.println("--------------   " + arg0.getResultString());
			try {
				JSONObject jsonObject = new JSONObject(arg0.getResultString());
				JSONArray jsonArray = jsonObject.getJSONArray("ws");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					JSONArray jsonArray2 = jsonObject2.getJSONArray("cw");
					for (int j = 0; j < jsonArray2.length(); j++) {
						JSONObject jsonObject3 = jsonArray2.getJSONObject(j);
						String text = jsonObject3.getString("w");
						sb.append(text);
					}
				}
				if (!isrepeat) {
					et_content.append(sb.toString());
					isrepeat=true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// 会话发生错误回调接口
		@Override
		public void onError(SpeechError arg0) {
			String plainDescription = arg0.getPlainDescription(true); // 获取错误码描述}
			CommonTools.showShortToast(CommentActivity.this, plainDescription);
		}

		// 开始录音
		@Override
		public void onBeginOfSpeech() {
			CommonTools.showShortToast(CommentActivity.this,
					getI18n(R.string.startrecording));
		}

		// 音量值0~30
		@Override
		public void onVolumeChanged(int arg0, byte[] arg1) {
			if (arg0>=0&&arg0<=5) {
				iv_yingliang.setBackgroundResource(R.drawable.volume1);
			}
			if (arg0>=5&&arg0<=10) {
				iv_yingliang.setBackgroundResource(R.drawable.volume2);
			}
			if (arg0>=10&&arg0<=15) {
				iv_yingliang.setBackgroundResource(R.drawable.volume3);
			}
			if (arg0>=15&&arg0<=20) {
				iv_yingliang.setBackgroundResource(R.drawable.volume4);
			}
			if (arg0>=20&&arg0<=25) {
				iv_yingliang.setBackgroundResource(R.drawable.volume5);
			}
			if (arg0>=25&&arg0<=30) {
				iv_yingliang.setBackgroundResource(R.drawable.volume6);
			}
		}

		// 结束录音
		@Override
		public void onEndOfSpeech() {
		}

		// 扩展用接口
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

		}

	};

	private ArrayAdapter<String> regionList(List<GoodList> goodLists) {
		bitmapUtils = new BitmapUtils(CommentActivity.this);
		if (goodLists != null) {
			bitmapUtils.display(iv_goodsimmg, goodLists.get(0).goods_image_url);
		}
		List<String> strings = new ArrayList<String>();
		for (GoodList string : goodLists) {
			strings.add(string.goods_name);
		}
		ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strings);
		_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return _Adapter;
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_submit:
//			initData();
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//
//		}
		if(v.getId()== R.id.tv_submit){
			initData();
		}else if(v.getId()== R.id._iv_back){
			finish();
		}
	}

}
