package com.aite.a.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.aiteshangcheng.a.R;
import com.aite.a.adapter.CategoryOneAdapter;
import com.aite.a.adapter.CategoryTwoAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.CategoryList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.EditTextWithDel;

/**
 * 分类
 * 
 * @author CAOYOU
 * 
 */
public class CategoryActivity extends BaseActivity implements OnClickListener {
	private EditTextWithDel et_serach;
	private Button btn_search;
	private ListView mCategory_list;
	private GridView mCategory_Grid;
	private ArrayList<HashMap<String, Object>> classifyGrid;
	private CategoryOneAdapter oneAdapter;
	private CategoryTwoAdapter twoAdapter;
	private List<CategoryList> categoryOne,
			categorytwo = new ArrayList<CategoryList>();
	private NetRun netRun;
	private LinearLayout ll_title;
	private RelativeLayout i_inputbox;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case one_category_id:
				if (msg.obj != null) {
					// 过滤数据,gc_show_in_menu=1的分类是需要显示的,其余的不显示,所以不添加进temp这个List中
					List<CategoryList> msgObj = (List<CategoryList>) msg.obj;
					List<CategoryList> temp = new ArrayList<CategoryList>();
					for (int i = 0; i < msgObj.size(); i++) {
						if (msgObj.get(i).getGc_show_in_menu().equals("1")) {
							String gc_id = msgObj.get(i).getGc_id();
							String gc_name = msgObj.get(i).getGc_name();
							String gc_show_in_menu = msgObj.get(i)
									.getGc_show_in_menu();
							CategoryList categoryList = new CategoryList();
							categoryList.setGc_id(gc_id);
							categoryList.setGc_name(gc_name);
							categoryList.setGc_show_in_menu(gc_show_in_menu);
							temp.add(categoryList);
						}
					}
					oneAdapter.setList(categoryOne = temp);
					netRun.getCategoryTeo(categoryOne.get(0).getGc_id(), 0);
				} else {
					oneAdapter
							.setList(categoryOne = new ArrayList<CategoryList>());

					CommonTools.showShortToast(CategoryActivity.this,
							getI18n(R.string.act_no_data));
				}

				oneAdapter.notifyDataSetChanged();
				mdialog.dismiss();
				break;
			case one_category_err:
				CommonTools.showShortToast(CategoryActivity.this,
						getI18n(R.string.act_net_error));
				mdialog.dismiss();
				break;
			case one_category_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case two_category_id:
				if (msg.obj != null) {
					twoAdapter
							.setList(categorytwo = (List<CategoryList>) msg.obj);
				} else {
					twoAdapter
							.setList(categorytwo = new ArrayList<CategoryList>());
					CommonTools.showShortToast(CategoryActivity.this,
							getI18n(R.string.act_no_data));
				}

				twoAdapter.notifyDataSetChanged();
				mdialog.dismiss();
				break;
			case two_category_err:
				CommonTools.showShortToast(CategoryActivity.this,
						getI18n(R.string.act_net_error));
				mdialog.dismiss();
				break;
			case two_category_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_more);
		findViewById();
		initModle();
		initView();
	}

	@Override
	protected void findViewById() {
		et_serach = (EditTextWithDel) findViewById(R.id._search_edit);
		btn_search = (Button) findViewById(R.id.btn_search);
		mCategory_list = (ListView) findViewById(R.id.Search_more_mainlist);
		mCategory_Grid = (GridView) findViewById(R.id.Search_more_morelist);
		ll_title = (LinearLayout) findViewById(R.id.ll_title);
		i_inputbox = (RelativeLayout) findViewById(R.id.i_inputbox);
		mCategory_list.setOnItemClickListener(new MainOnItemClick(1));
		mCategory_Grid.setOnItemClickListener(new MainOnItemClick(2));
		oneAdapter = new CategoryOneAdapter(this);
		mCategory_list.setAdapter(oneAdapter);
		twoAdapter = new CategoryTwoAdapter(this);
		mCategory_Grid.setAdapter(twoAdapter);
		// oneAdapter.setSelectItem(3);
		// oneAdapter.notifyDataSetChanged();
	}

	private void initModle() {

		netRun = new NetRun(CategoryActivity.this, handler);
		netRun.getCategorOne();
	}

	// 获取屏幕的宽度
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	private class MainOnItemClick implements OnItemClickListener {
		private int i;

		public MainOnItemClick(int arg2) {
			super();
			i = arg2;
		}

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (i) {
			case 1:
				netRun.getCategoryTeo(categoryOne.get(arg2).getGc_id(), 0);
				oneAdapter.setSelectItem(arg2);
				oneAdapter.notifyDataSetChanged();
				twoAdapter.notifyDataSetChanged();
				break;
			case 2:
				Bundle bundle = new Bundle();
				bundle.putString("gc_id", categorytwo.get(arg2).getGc_id());
				bundle.putString("gc_name", categorytwo.get(arg2).getGc_name());
				openActivity(GoodsListActivity.class, bundle);
				break;
			}
		}
	}

	@Override
	protected void initView() {
//		// 适配顶部菜单高度
//		LinearLayout.LayoutParams topparams = (LinearLayout.LayoutParams) ll_title
//				.getLayoutParams();
//		topparams.height = getScreenWidth(this) / 9;
//		ll_title.setLayoutParams(topparams);
//		// 搜索栏高度适配
//		LinearLayout.LayoutParams searchparams = (LayoutParams) i_inputbox
//				.getLayoutParams();
//		searchparams.height = (int) ((getScreenWidth(this) / 10) * 0.8);
//		i_inputbox.setLayoutParams(searchparams);
		et_serach.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					search();
				}
				return false;
			}
		});
		btn_search.setOnClickListener(this);
	}

	private void search() {
		String key_wrods = et_serach.getText().toString();
		Bundle bundle = new Bundle();
		bundle.putString("keyword", key_wrods);
		if (!key_wrods.equals("")) {
			openActivity(GoodsListActivity.class, bundle);
		}
	}

	@Override
	protected void initData() {
	}

	@Override
	public void onBackPressed() {
		intent = new Intent(CATEGORY_);
		this.sendBroadcast(intent);
		super.onBackPressed();
	}

	@Override
	public void ReGetData() {
		initData();
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_search:
//			search();
//			break;
//		}
		if(v.getId()==R.id.btn_search){
			search();
		}

	}

}
