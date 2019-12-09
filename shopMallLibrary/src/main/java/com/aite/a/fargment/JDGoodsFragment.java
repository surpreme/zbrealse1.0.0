package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aite.a.view.ListeningScrollView;
import com.aite.a.view.ListeningScrollView.ScrollYListener;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyAdGallery.MyOnItemClickListener;
import com.aiteshangcheng.a.R;

/**
 * 商品
 * 
 * @author Administrator
 *
 */
public class JDGoodsFragment extends BaseFragment implements ScrollYListener {
	private RecyclerView rv_salespromotion, rv_chooseval;
	private MyAdGallery adgallery;
	private RelativeLayout rl_topimg;
	private LinearLayout ovalLayout, ll_info;
	private LinearLayout.LayoutParams layoutParams;
	private ListeningScrollView sv_gd;

	@Override
	protected int layoutResID() {
		return R.layout.fragment_jd_goods;
	}

	@Override
	protected void initView() {
		rv_salespromotion = (RecyclerView) layout
				.findViewById(R.id.rv_salespromotion);
		rv_chooseval = (RecyclerView) layout.findViewById(R.id.rv_chooseval);
		adgallery = (MyAdGallery) layout.findViewById(R.id.adgallery);
		rl_topimg = (RelativeLayout) layout.findViewById(R.id.rl_topimg);
		ovalLayout = (LinearLayout) layout.findViewById(R.id.ovalLayout);
		ll_info = (LinearLayout) layout.findViewById(R.id.ll_info);
		sv_gd=(ListeningScrollView) layout.findViewById(R.id.sv_gd);
		initData();
	}

	@Override
	protected void initData() {
		sv_gd.setScrollYViewListener(this);
		layoutParams = (LinearLayout.LayoutParams) rl_topimg.getLayoutParams();
		setAD();
		rv_salespromotion.setLayoutManager(new LinearLayoutManager(
				getActivity()));
		rv_chooseval.setLayoutManager(new LinearLayoutManager(getActivity()));
	}



	/**
	 * 设置广告轮播
	 *
	 */
	protected void setAD() {
		List<String> listAd = new ArrayList<String>();
		listAd.add("http://img10.360buyimg.com/n1/s450x450_jfs/t277/193/1005339798/768456/29136988/542d0798N19d42ce3.jpg");
		listAd.add("http://img10.360buyimg.com/n1/s450x450_jfs/t352/148/1022071312/209475/53b8cd7f/542d079bN3ea45c98.jpg");
		listAd.add("http://img10.360buyimg.com/n1/s450x450_jfs/t337/181/1064215916/27801/b5026705/542d079aNf184ce18.jpg");
		String[] ADurl = listAd.toArray(new String[listAd.size()]);
		if (adgallery.mUris == null)
			adgallery.start(getActivity(), ADurl, null, 3000, ovalLayout,
					R.drawable.jd_quan, R.drawable.jd_heng, false);
		adgallery.setMyOnItemClickListener(new MyOnItemClickListener() {

			@Override
			public void onItemClick(int curIndex) {

			}
		});
	}

	@Override
	public void onScrollChanged(int y) {
		int a = (int) ((y / (float) ll_info.getTop()) * 100);
		a = a > 100 ? 100 : a;
		a = a < 0 ? 0 : a;
		int dip2px = dip2px(getActivity(), (int) (a * 2));
		layoutParams.setMargins(0, dip2px, 0, 0);
		rl_topimg.setLayoutParams(layoutParams);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
