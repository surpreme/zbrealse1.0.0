package hotel;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

/**
 * 酒店支付
 * 
 * @author Administrator
 *
 */
public class HotelPayActivity extends BaseActivity implements OnClickListener {
	private TextView _tv_name, tv_confirm, tv_zfb, tv_wx;
	private ImageView _iv_back;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotelpay);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_confirm = (TextView) findViewById(R.id.tv_confirm);
		tv_zfb = (TextView) findViewById(R.id.tv_zfb);
		tv_wx = (TextView) findViewById(R.id.tv_wx);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		initView();
	}

	@Override
	protected void initView() {
		_iv_back.setOnClickListener(this);
		_tv_name.setText(getString(R.string.pay_wayfcf));
		id = getIntent().getStringExtra("id");
	}

	@Override
	protected void initData() {

	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id._iv_back) {
			finish();
		}
	}
}
