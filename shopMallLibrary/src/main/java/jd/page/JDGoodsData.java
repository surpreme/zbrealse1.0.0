package jd.page;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.adapter.MyFragmentAdapter;
import com.aite.a.fargment.JDEvaluationFragment;
import com.aite.a.fargment.JDGoodsFragment;
import com.aite.a.fargment.JDGoodsInfoFragment;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * 仿京东商品详情
 * 
 * @author Administrator
 *
 */
public class JDGoodsData extends FragmentActivity implements OnClickListener {
	ImageView iv_return, iv_share, iv_more;
	TextView tv_goods, tv_datas, tv_evaluation, tv_pay;
	RelativeLayout rl_slider;
	ViewPager favorite_list_viewpager;
	LinearLayout ll_jimi, ll_store, ll_love, ll_shoppingcart;
	LayoutParams params = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏  
		setContentView(R.layout.activity_goodsdata);
		findViewById();
	}

	private void findViewById() {
//		iv_return = (ImageView) findViewById(R.id.iv_return);
//		iv_share = (ImageView) findViewById(R.id.iv_share);
//		iv_more = (ImageView) findViewById(R.id.iv_more);
//		tv_goods = (TextView) findViewById(R.id.tv_goods);
//		tv_datas = (TextView) findViewById(R.id.tv_datas);
//		tv_evaluation = (TextView) findViewById(R.id.tv_evaluation);
//		tv_pay = (TextView) findViewById(R.id.tv_pay);
//		rl_slider = (RelativeLayout) findViewById(R.id.rl_slider);
//		favorite_list_viewpager = (ViewPager) findViewById(R.id.favorite_list_viewpager);
//		ll_jimi = (LinearLayout) findViewById(R.id.ll_jimi);
//		ll_store = (LinearLayout) findViewById(R.id.ll_store);
//		ll_love = (LinearLayout) findViewById(R.id.ll_love);
//		ll_shoppingcart = (LinearLayout) findViewById(R.id.ll_shoppingcart);
		iv_return.setOnClickListener(this);
		tv_goods.setOnClickListener(this);
		tv_datas.setOnClickListener(this);
		tv_evaluation.setOnClickListener(this);
		iv_share.setOnClickListener(this);
		iv_more.setOnClickListener(this);
		initView();
	}

	private void initView() {
		params=(LayoutParams)rl_slider.getLayoutParams();
		favorite_list_viewpager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), getFragments()));
		favorite_list_viewpager.setOnPageChangeListener(pageChangeListener);
		initData();
	}

	private void initData() {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_return) {
			finish();
		} else if (id == R.id.tv_goods) {// 商品
			favorite_list_viewpager.setCurrentItem(0, true);
		} else if (id == R.id.tv_datas) {// 详情
			favorite_list_viewpager.setCurrentItem(1, true);
		} else if (id == R.id.tv_evaluation) {// 评价
			favorite_list_viewpager.setCurrentItem(2, true);
			//		case R.id.iv_share:
//			// 分享
//			break;
		} else if (id == R.id.iv_more) {// 更多
		}
	}
	
	private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int index) {
			System.out.println("--------index当前的位置   ：" + index);

		}

		@Override
		public void onPageScrolled(int index, float arg1, int arg2) {
			System.out.println("--------index当前的位置   ：" + index);
			System.out.println("--------arg1屏幕滑动的百分比   ：" + arg1);
			System.out.println("--------arg2屏幕滑动的距离  ：" + arg2);
			params.leftMargin = (int) (rl_slider.getWidth() * (index + arg1));
			rl_slider.setLayoutParams(params);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			System.out.println("--------onPageScrollStateChanged   ：" + arg0);
		}
	};
	
	/**
	 * 初始化所有Fragment
	 * 
	 * @return
	 */
	private List<Fragment> getFragments() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(new JDGoodsFragment());
		list.add(new JDGoodsInfoFragment());
		list.add(new JDEvaluationFragment());
		return list;
	}
	
}
