package jd.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 仿京东商品列表
 * 
 * @author Administrator
 *
 */
public class JDGoodsListActivity extends BaseActivity implements
		OnClickListener {
	private DrawerLayout dl_layout;
	private TextView tv_determine;
	private LinearLayout ll_menu1, ll_menu2, ll_menu3, ll_menu4;
	private RecyclerView rv_goods,rv_val;
	private ImageView iv_footprint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jd_goodslistt);
		findViewById();
	}

	@Override
	protected void findViewById() {
		dl_layout = (DrawerLayout) findViewById(R.id.dl_layout);
		tv_determine = (TextView) findViewById(R.id.tv_determine);
		ll_menu1 = (LinearLayout) findViewById(R.id.ll_menu1);
		ll_menu2 = (LinearLayout) findViewById(R.id.ll_menu2);
		ll_menu3 = (LinearLayout) findViewById(R.id.ll_menu3);
		ll_menu4 = (LinearLayout) findViewById(R.id.ll_menu4);
		rv_goods=(RecyclerView) findViewById(R.id.rv_goods);
		rv_val=(RecyclerView) findViewById(R.id.rv_val);
		iv_footprint=(ImageView) findViewById(R.id.iv_footprint);
		
		tv_determine.setOnClickListener(this);
		ll_menu1.setOnClickListener(this);
		ll_menu2.setOnClickListener(this);
		ll_menu3.setOnClickListener(this);
		ll_menu4.setOnClickListener(this);
		iv_footprint.setOnClickListener(this);
		initView();
	}

	@Override
	protected void initView() {
		rv_goods.setLayoutManager(new LinearLayoutManager(this));
		rv_val.setLayoutManager(new LinearLayoutManager(this));
	}

	@Override
	protected void initData() {

	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.tv_determine) {//确定
			dl_layout.closeDrawers();
		} else if (id == R.id.ll_menu1) {
		} else if (id == R.id.ll_menu2) {
		} else if (id == R.id.ll_menu3) {
		} else if (id == R.id.ll_menu4) {
			dl_layout.openDrawer(Gravity.RIGHT);
		} else if (id == R.id.iv_footprint) {
			Intent intent2 = new Intent(JDGoodsListActivity.this, JDGoodsData.class);
			startActivity(intent2);
		}
	}
}
