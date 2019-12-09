package jd.page;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 仿京东购物车
 * 
 * @author Administrator
 *
 */
public class JDShoppingCartActivity extends BaseActivity implements
		OnClickListener {
	private TextView tv_name, tv_edit;
	private ImageView iv_message;
	private RecyclerView rv_goods;
	private RelativeLayout rl_js;
	private LinearLayout ll_cz;
	private CheckBox cb_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jd_shoppingcart);
		findViewById();
	}

	@Override
	protected void findViewById() {
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		iv_message = (ImageView) findViewById(R.id.iv_message);
		rv_goods = (RecyclerView) findViewById(R.id.rv_goods);
		rl_js = (RelativeLayout) findViewById(R.id.rl_js);
		ll_cz = (LinearLayout) findViewById(R.id.ll_cz);
		cb_all = (CheckBox) findViewById(R.id.cb_all);
		tv_edit.setOnClickListener(this);
		initView();
	}

	@Override
	protected void initView() {
		rv_goods.setLayoutManager(new LinearLayoutManager(this));
	}

	@Override
	protected void initData() {

	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_edit) {
			String string = tv_edit.getText().toString();
			if (string.equals("编辑")) {
				tv_edit.setText("完成");
				rl_js.setVisibility(View.GONE);
				ll_cz.setVisibility(View.VISIBLE);
			} else {
				tv_edit.setText("编辑");
				rl_js.setVisibility(View.VISIBLE);
				ll_cz.setVisibility(View.GONE);
			}
		}
	}
}
