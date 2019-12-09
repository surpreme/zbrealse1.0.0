package jd.page;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aite.a.adapter.JD_Level1Adapter;
import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 仿京东分类
 * 
 * @author Administrator
 *
 */
public class JDClassifyActivity extends BaseActivity {
	private LinearLayout ll_qrcode, ll_message;
	private ImageView iv_search, iv_topimg;
	private EditText et_search;
	private RelativeLayout rl_se;
	private RecyclerView rv_level1, rv_level2;
	private JD_Level1Adapter jd_Level1Adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
		setContentView(R.layout.activity_jdclassify);
		findViewById();
	}

	@Override
	protected void findViewById() {
		ll_qrcode = (LinearLayout) findViewById(R.id.ll_qrcode);
		ll_message = (LinearLayout) findViewById(R.id.ll_message);
		iv_search = (ImageView) findViewById(R.id.iv_search);
		iv_topimg = (ImageView) findViewById(R.id.iv_topimg);
		et_search = (EditText) findViewById(R.id.et_search);
		rl_se = (RelativeLayout) findViewById(R.id.rl_se);
		rv_level1 = (RecyclerView) findViewById(R.id.rv_level1);
		rv_level2 = (RecyclerView) findViewById(R.id.rv_level2);
		initView();
	}

	@Override
	protected void initView() {
		rv_level1.setLayoutManager(new LinearLayoutManager(this));
		jd_Level1Adapter=new JD_Level1Adapter(this);
		rv_level1.setAdapter(jd_Level1Adapter);
		rv_level2.setLayoutManager(new LinearLayoutManager(this));
		
		initData();
	}

	@Override
	protected void initData() {

	}

	@Override
	public void ReGetData() {

	}
}
