package jd.page;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.adapter.JD_Personal1Adapter;
import com.aite.a.adapter.JD_Personal2Adapter;
import com.aite.a.adapter.JD_Personal3Adapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.CustomScrollView;
import com.aite.a.view.CustomScrollView.OnScrollListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 仿京东个人中心
 * 
 * @author Administrator
 *
 */
public class JDPersonalCenterActivity extends BaseActivity implements
		OnClickListener, OnScrollListener {

	private RelativeLayout rl_title, rl_privilege, rl_info;
	private ImageView iv_set, iv_message;
	private TextView tv_titlename, tv_name, tv_level, tv_credit, tv_hydj,
			tv_privilege;
	private CircleImageView civ_xicon, civ_icon;
	private RecyclerView rv_menu1, rv_menu2, rv_menu3;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private CustomScrollView csv_gd;
	private JD_Personal1Adapter jd_Personal1Adapter;
	private JD_Personal2Adapter jd_Personal2Adapter;
	private JD_Personal3Adapter jd_Personal3Adapter;
	// 动画
	private final int START_THE_ANIMATION = 141592;
	private final int END_THE_ANIMATION = 141593;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case START_THE_ANIMATION:
				zhuzhen();
				handler.sendEmptyMessageDelayed(END_THE_ANIMATION, 15000);
				break;
			case END_THE_ANIMATION:
				rl_privilege.clearAnimation();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 沉浸
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		setContentView(R.layout.activity_jd_personalcenter);
		findViewById();
	}

	@Override
	protected void findViewById() {
		rl_title = (RelativeLayout) findViewById(R.id.rl_title);
		rl_info = (RelativeLayout) findViewById(R.id.rl_info);
		rl_privilege = (RelativeLayout) findViewById(R.id.rl_privilege);
		iv_set = (ImageView) findViewById(R.id.iv_set);
		iv_message = (ImageView) findViewById(R.id.iv_message);
		tv_titlename = (TextView) findViewById(R.id.tv_titlename);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_level = (TextView) findViewById(R.id.tv_level);
		tv_credit = (TextView) findViewById(R.id.tv_credit);
		tv_hydj = (TextView) findViewById(R.id.tv_hydj);
		tv_privilege = (TextView) findViewById(R.id.tv_privilege);
		civ_xicon = (CircleImageView) findViewById(R.id.civ_xicon);
		civ_icon = (CircleImageView) findViewById(R.id.civ_icon);
		rv_menu1 = (RecyclerView) findViewById(R.id.rv_menu1);
		rv_menu2 = (RecyclerView) findViewById(R.id.rv_menu2);
		rv_menu3 = (RecyclerView) findViewById(R.id.rv_menu3);
		csv_gd = (CustomScrollView) findViewById(R.id.csv_gd);
		initView();
	}

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		csv_gd.setOnScrollListener(this);
		handler.sendEmptyMessageDelayed(START_THE_ANIMATION, 1000);
		rl_title.getBackground().setAlpha(0);
		// 菜单1
		rv_menu1.setNestedScrollingEnabled(false);
		rv_menu1.setLayoutManager(new GridLayoutManager(this, 5));
		jd_Personal1Adapter = new JD_Personal1Adapter(this);
		rv_menu1.setAdapter(jd_Personal1Adapter);
		// 菜单2
		rv_menu2.setNestedScrollingEnabled(false);
		rv_menu2.setLayoutManager(new GridLayoutManager(this, 4));
		jd_Personal2Adapter = new JD_Personal2Adapter(this);
		rv_menu2.setAdapter(jd_Personal2Adapter);
		// 菜单三
		rv_menu3.setNestedScrollingEnabled(false);
		rv_menu3.setLayoutManager(new GridLayoutManager(this, 4));
		jd_Personal3Adapter = new JD_Personal3Adapter(this);
		rv_menu3.setAdapter(jd_Personal3Adapter);
		initData();
	}

	@Override
	protected void initData() {
		bitmapUtils.display(civ_icon,
				"https://img10.360buyimg.com/mobilecms/s110x110_jfs/t2905/300/3847999660/"
						+ "312900/a9fb3e68/579b201cNed7566f1.jpg");
		bitmapUtils.display(civ_xicon,
				"https://img10.360buyimg.com/mobilecms/s110x110_jfs/t2905/300/3847999660/"
						+ "312900/a9fb3e68/579b201cNed7566f1.jpg");

	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onScroll(int scrollY) {
		// 顶部透明度设置
		if (scrollY < rl_info.getBottom()) {
			float i = (scrollY / (float) (rl_info.getBottom() - rl_title
					.getBottom())) * 255;
			if (i > 0 && i <= 255) {
				rl_title.getBackground().setAlpha((int) i);
				if ((int) i > 125) {
					tv_titlename.setVisibility(View.VISIBLE);
					civ_xicon.setVisibility(View.VISIBLE);
					iv_message.setImageResource(R.drawable.jd_message2);
					iv_set.setImageResource(R.drawable.jd_set2);
				} else {
					tv_titlename.setVisibility(View.GONE);
					civ_xicon.setVisibility(View.GONE);
					iv_message.setImageResource(R.drawable.jd_message);
					iv_set.setImageResource(R.drawable.jd_set);
				}
			}
		}
	}

	/**
	 * 会员特权动画
	 */
	private void zhuzhen() {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.handin);
		rl_privilege.startAnimation(animation);
	}
}
