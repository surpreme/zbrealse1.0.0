package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ParameterInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数配置 Created by Administrator on 2017/6/8.
 */
public class ParameterActivity extends BaseActivity implements
		View.OnClickListener {

	private TextView _tv_name, tv_classify, tv_netxt;
	private ImageView _iv_back;
	private RecyclerView rv_parameter;
	private Madapter madapter;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private String goods_id = "", goods_name = "";
	private ParameterInfo parameterInfo;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case val_binding_id:
				if (msg.obj != null) {
					parameterInfo = (ParameterInfo) msg.obj;
					madapter = new Madapter(parameterInfo.param_class_list);
					rv_parameter.setAdapter(madapter);
				}
				break;
			case val_binding_err:
				Toast.makeText(ParameterActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case editval_save_id:
				if (msg.obj != null) {
					String str = (String) msg.obj;
					if (str.equals("1")) {
						Toast.makeText(ParameterActivity.this,
								getString(R.string.operationissuccessful),
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(ParameterActivity.this, str,
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case editval_save_err:
				Toast.makeText(ParameterActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameter);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_classify = (TextView) findViewById(R.id.tv_classify);
		tv_netxt = (TextView) findViewById(R.id.tv_netxt);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		rv_parameter = (RecyclerView) findViewById(R.id.rv_parameter);
		_iv_back.setOnClickListener(this);
		tv_netxt.setOnClickListener(this);
		_tv_name.setText(getString(R.string.recommended8));
		initView();
	}

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		rv_parameter.setNestedScrollingEnabled(false);
		rv_parameter.setLayoutManager(new LinearLayoutManager(this));
		initData();
	}

	@Override
	protected void initData() {
		goods_id = getIntent().getStringExtra("goodsid");
		goods_name = getIntent().getStringExtra("goods_name");
		tv_classify.setText(goods_name);
		netRun.ValBinding(goods_id);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.tv_netxt){
			// 提交
			if (madapter==null) {
				return;
			}
			List<String> list = madapter.getparam_value();
			if (list == null || list.size() == 0) {
				return;
			}
			netRun.EditValSave(goods_id, list.get(0), list.get(1));
		}

//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_netxt:
//			// 提交
//			if (madapter==null) {
//				return;
//			}
//			List<String> list = madapter.getparam_value();
//			if (list == null || list.size() == 0) {
//				return;
//			}
//			netRun.EditValSave(goods_id, list.get(0), list.get(1));
//			break;
//		}
	}

	/**
	 * 参数配置
	 */
	class Madapter extends RecyclerView.Adapter<ViewHodler> {
		List<ParameterInfo.param_class_list> param_class_list;

		public Madapter(List<ParameterInfo.param_class_list> param_class_list) {
			this.param_class_list = param_class_list;
		}

		/**
		 * 获取json
		 *
		 * @return
		 */
		public List<String> getparam_value() {
			List<String> list = new ArrayList<>();
			if (param_class_list == null) {
				return list;
			}
			JSONObject param_value = new JSONObject();
			JSONObject is_use = new JSONObject();
			try {
				for (int i = 0; i < param_class_list.size(); i++) {
					for (int j = 0; j < param_class_list.get(i).param.size(); j++) {
						param_value
								.put(param_class_list.get(i).param.get(j).param_id,
										param_class_list.get(i).param.get(j).inputcontent);
						if (param_class_list.get(i).param.get(j).ischoose) {
							is_use.put(
									param_class_list.get(i).param.get(j).param_id,
									"1");
						} else {
							is_use.put(
									param_class_list.get(i).param.get(j).param_id,
									"0");
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list.add(is_use.toString());
			list.add(param_value.toString());
			Log.i("--------------", " is_use " + is_use);
			Log.i("--------------", " param_value " + param_value);
			return list;
		}

		@Override
		public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
			ViewHodler holder = new ViewHodler(LayoutInflater.from(
					ParameterActivity.this).inflate(R.layout.item_parameter,
					parent, false));
			return holder;
		}

		@Override
		public void onBindViewHolder(ViewHodler holder, int position) {
			ParameterInfo.param_class_list param_class_list = this.param_class_list
					.get(position);
			holder.tv_name.setText(param_class_list.class_name);
			holder.rv_chid.setNestedScrollingEnabled(false);
			holder.rv_chid.setLayoutManager(new LinearLayoutManager(
					ParameterActivity.this));
			Madapter2 madapter2 = new Madapter2(param_class_list.param);
			holder.rv_chid.setAdapter(madapter2);
		}

		@Override
		public int getItemCount() {
			return param_class_list == null ? 0 : param_class_list.size();
		}
	}

	class ViewHodler extends RecyclerView.ViewHolder {
		TextView tv_name;
		RecyclerView rv_chid;

		public ViewHodler(View itemView) {
			super(itemView);
			tv_name = (TextView) itemView.findViewById(R.id.tv_name);
			rv_chid = (RecyclerView) itemView.findViewById(R.id.rv_chid);
		}
	}

	/**
	 * 子适配
	 */
	class Madapter2 extends RecyclerView.Adapter<ViewHodler2> {
		List<ParameterInfo.param_class_list.param> param;

		public Madapter2(List<ParameterInfo.param_class_list.param> param) {
			this.param = param;
		}

		@Override
		public ViewHodler2 onCreateViewHolder(ViewGroup parent, int viewType) {
			ViewHodler2 holder = new ViewHodler2(LayoutInflater.from(
					ParameterActivity.this).inflate(
					R.layout.item_chidparameter, parent, false));
			return holder;
		}

		@Override
		public void onBindViewHolder(ViewHodler2 holder, int position) {
			final ParameterInfo.param_class_list.param param = this.param
					.get(position);
			holder.cb_choose.setText(param.param_name);
			holder.ed_input1.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (param.inputcontent != null
							&& !param.inputcontent.equals(s)) {
						// Log.i("----------------","输入  "+s);
						param.inputcontent = s.toString();
					}
				}
			});

			holder.cb_choose
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							param.ischoose = isChecked;
						}
					});
		}

		@Override
		public int getItemCount() {
			return param == null ? 0 : param.size();
		}
	}

	class ViewHodler2 extends RecyclerView.ViewHolder {
		CheckBox cb_choose;
		EditText ed_input1;

		public ViewHodler2(View itemView) {
			super(itemView);
			cb_choose = (CheckBox) itemView.findViewById(R.id.cb_choose);
			ed_input1 = (EditText) itemView.findViewById(R.id.ed_input1);

		}
	}

	@Override
	public void ReGetData() {
		// TODO Auto-generated method stub

	}

}
