package jd.page;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.GoodsListActivity;
import com.aite.a.activity.InformationActivity;
import com.aite.a.activity.WebActivity;
import com.aite.a.adapter.JD_AdvertisingAdapter;
import com.aite.a.adapter.JD_HomeGoodsAdapter;
import com.aite.a.adapter.JD_KillAdapter;
import com.aite.a.adapter.JD_LettersAdapter;
import com.aite.a.adapter.JD_NavigationAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.AdColumnInfo;
import com.aite.a.model.CategoryList;
import com.aite.a.model.GoodList;
import com.aite.a.model.GoodList2;
import com.aite.a.model.NewslistifyInfo;
import com.aite.a.model.SpecialAdList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.ListeningScrollView;
import com.aite.a.view.ListeningScrollView.ScrollYListener;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyAdGallery.MyOnItemClickListener;
import com.aite.a.view.MyListView;
import com.aite.a.zxing.CaptureActivity;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 仿京东首页
 *
 * @author Administrator
 */
public class JDHomePage extends BaseActivity implements ScrollYListener,
        OnClickListener {

    private LinearLayout ll_qrcode, ll_message, ovalLayout, ll_coupons, i_dh;
    private ImageView iv_search, iv_aide, iv_advertising, circular_01,
            circular_02, iv_brandadvertising, iv_recommended, iv_message,
            iv_sas;
    private EditText et_search;
    private MyAdGallery adgallery;
    private RelativeLayout rl_title, pointer, ll_pager, rl_pp1, rl_pp2, rl_pp3,
            rl_pp4, rl_pp5, rl_pp6, rl_se, rl_ts;
    private ViewPager vp_navigation, vp_advertising;
    private TextView tv_checkpoint, tv_showrecord, tv_more, tv_time, tv_hour,
            tv_minutes, tv_seconds, tv_super, tv_sas, tv_message;
    private RecyclerView rv_recommended, id_recyclerview_horizontal, rv_brand1,
            rv_brand2, rv_life, rv_goodstj, rv_navigation1, rv_navigation2;
    private MyListView id_advertising1, id_advertising2, ml_goods;
    private ListeningScrollView msv_gd;
    private String page = "4";
    private JD_HomeGoodsAdapter jD_HomeGoodsAdapter;
    private final int REQUEST_CODE = 999;
    /**
     * 存放导航内容的View
     */

    private List<View> mViews = new ArrayList<View>();
    private View mPage1, mPage2;
    private JD_NavigationAdapter jd_NavigationAdapter, jd_NavigationAdapter2;
    // 快报
    private JD_LettersAdapter jd_LettersAdapter;
    private final int EXPRESS_THE_SCROLL = 1415926;
    private int Letterspointer = 0;
    private int dip2px = 0;
    // 秒杀
    private JD_KillAdapter jd_KillAdapter;
    // 广告
    private JD_AdvertisingAdapter jD_AdvertisingAdapter,
            jD_AdvertisingAdapter2;
    // 顶部广告数据
    private List<AdColumnInfo> ad_Top = new ArrayList<AdColumnInfo>();
    // 秒杀数据
    private List<GoodList> saleGoodsList;
    // 今日特价数据/劲爆推荐数据/底部广告数据
    private List<SpecialAdList> ad_SalePriceList, ad_BestList, ad_Bottom;
    // 分类数据
    private List<CategoryList> categoryOne;
    // 商品
    private GoodList2 oodList2;
    private List<GoodList2> goodlist2 = new ArrayList<GoodList2>();
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private NewslistifyInfo newslistifyInfo;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case EXPRESS_THE_SCROLL:
                    // 快报滚动
                    if (Letterspointer == jd_LettersAdapter.getItemCount() - 1) {
                        Letterspointer = 0;
                        rv_recommended.scrollToPosition(0);
                        handler.sendEmptyMessage(EXPRESS_THE_SCROLL);
                        return;
                    }
                    if (Letterspointer < jd_LettersAdapter.getItemCount() - 1) {
                        Letterspointer++;
                        rv_recommended.smoothScrollBy(0, dip2px);
                    }
                    handler.sendEmptyMessageDelayed(EXPRESS_THE_SCROLL, 1800);
                    break;
                case home_ad_id:
                    // 首页数据
                    if (msg.obj != null) {
                        Map<String, Object> map = (Map<String, Object>) msg.obj;
                        ad_Top = (List<AdColumnInfo>) map.get("1");
                        saleGoodsList = (List<GoodList>) map.get("2");
                        ad_SalePriceList = (List<SpecialAdList>) map.get("3");
                        ad_BestList = (List<SpecialAdList>) map.get("4");
                        ad_Bottom = (List<SpecialAdList>) map.get("5");
                        // 顶部
                        setAD(ad_Top);
                        // 秒杀
                        jd_KillAdapter = new JD_KillAdapter(JDHomePage.this,
                                saleGoodsList);
                        id_recyclerview_horizontal.setAdapter(jd_KillAdapter);
                        // 广告
                        jD_AdvertisingAdapter = new JD_AdvertisingAdapter(
                                JDHomePage.this, ad_BestList);
                        jD_AdvertisingAdapter2 = new JD_AdvertisingAdapter(
                                JDHomePage.this, ad_SalePriceList);
                        id_advertising1.setAdapter(jD_AdvertisingAdapter);
                        id_advertising2.setAdapter(jD_AdvertisingAdapter2);
                    } else {
                        CommonTools.showShortToast(JDHomePage.this,
                                getI18n(R.string.act_no_data_load));
                    }
                    break;
                case home_ad_err:
                    CommonTools.showShortToast(JDHomePage.this,
                            getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case two_category_id:
                    // 楼层分类
                    if (msg.obj != null) {
                        categoryOne = (List<CategoryList>) msg.obj;
                        for (int i = 0; i < categoryOne.size(); i++) {
                            // netRun.getGoodsList(null, "2", page, "1", null,
                            // categoryOne.get(i).getGc_id(), null);
                            netRun.getGoodsList2(null, "2", page, "1", null,
                                    categoryOne.get(i).getGc_id(), null);
                        }
                    }
                    break;
                case two_category_err:
                    CommonTools.showShortToast(JDHomePage.this,
                            getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case goods_list_id:
                    // 商品
                    if (msg.obj != null) {
                        oodList2 = (GoodList2) msg.obj;
                        goodlist2.add(oodList2);
                        if (categoryOne.size() == goodlist2.size()) {
                            if (categoryOne != null && categoryOne.size() != 0) {
                                rl_ts.setVisibility(View.VISIBLE);
                            } else {
                                rl_ts.setVisibility(View.GONE);
                            }
                            jD_HomeGoodsAdapter = new JD_HomeGoodsAdapter(
                                    JDHomePage.this, categoryOne, goodlist2);
                            ml_goods.setAdapter(jD_HomeGoodsAdapter);
                        }
                        // 清除没有商铺的楼层
                        for (int i = 0; i < goodlist2.size(); i++) {
                            if (goodlist2.get(i).goods_list.size() == 0) {
                                String gc_name = goodlist2.get(i).gc_name;
                                for (int j = 0; j < categoryOne.size(); j++) {
                                    if (gc_name.equals(categoryOne.get(j)
                                            .getGc_name())) {
                                        categoryOne.remove(j);
                                        goodlist2.remove(i);
                                    }
                                }
                            }
                        }
                    } else {
                        CommonTools.showShortToast(JDHomePage.this,
                                getI18n(R.string.act_no_data_load));
                    }
                    break;
                case goods_list_err:
                    CommonTools.showShortToast(JDHomePage.this,
                            getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case new_slistify_id:
                    if (msg.obj != null) {
                        // 快报
                        newslistifyInfo = (NewslistifyInfo) msg.obj;
                        jd_LettersAdapter = new JD_LettersAdapter(JDHomePage.this, newslistifyInfo.news_list);
                        rv_recommended.setAdapter(jd_LettersAdapter);
                        dip2px = dip2px(JDHomePage.this, 33);
                        handler.sendEmptyMessageDelayed(EXPRESS_THE_SCROLL, 1200);
                    }
                    break;
            }
        }

        ;
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (jD_HomeGoodsAdapter != null) {
            jD_HomeGoodsAdapter.notifyDataSetChanged();
//			jD_HomeGoodsAdapter = new JD_HomeGoodsAdapter(
//					JDHomePage.this, categoryOne, goodlist2);
//			ml_goods.setAdapter(jD_HomeGoodsAdapter);
        }
    }

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
        setContentView(R.layout.activity_jdhomepager);
        getPersimmions();
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_qrcode = (LinearLayout) findViewById(R.id.ll_qrcode);
        ll_message = (LinearLayout) findViewById(R.id.ll_message);
        ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);
        ll_coupons = (LinearLayout) findViewById(R.id.ll_coupons);
        i_dh = (LinearLayout) findViewById(R.id.i_dh);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_aide = (ImageView) findViewById(R.id.iv_aide);
        iv_advertising = (ImageView) findViewById(R.id.iv_advertising);
        circular_01 = (ImageView) findViewById(R.id.circular_01);
        circular_02 = (ImageView) findViewById(R.id.circular_02);
        iv_brandadvertising = (ImageView) findViewById(R.id.iv_brandadvertising);
        iv_recommended = (ImageView) findViewById(R.id.iv_recommended);
        iv_message = (ImageView) findViewById(R.id.iv_message);
        iv_sas = (ImageView) findViewById(R.id.iv_sas);

        et_search = (EditText) findViewById(R.id.et_search);
        adgallery = (MyAdGallery) findViewById(R.id.adgallery);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        pointer = (RelativeLayout) findViewById(R.id.pointer);
        rl_pp1 = (RelativeLayout) findViewById(R.id.rl_pp1);
        rl_pp2 = (RelativeLayout) findViewById(R.id.rl_pp2);
        rl_pp3 = (RelativeLayout) findViewById(R.id.rl_pp3);
        rl_pp4 = (RelativeLayout) findViewById(R.id.rl_pp4);
        rl_pp5 = (RelativeLayout) findViewById(R.id.rl_pp5);
        rl_pp6 = (RelativeLayout) findViewById(R.id.rl_pp6);
        ll_pager = (RelativeLayout) findViewById(R.id.ll_pager);
        rl_ts = (RelativeLayout) findViewById(R.id.rl_ts);
        rl_se = (RelativeLayout) findViewById(R.id.rl_se);
        vp_navigation = (ViewPager) findViewById(R.id.vp_navigation);
        vp_advertising = (ViewPager) findViewById(R.id.vp_advertising);
        tv_checkpoint = (TextView) findViewById(R.id.tv_checkpoint);
        tv_showrecord = (TextView) findViewById(R.id.tv_showrecord);
        tv_more = (TextView) findViewById(R.id.tv_more);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_minutes = (TextView) findViewById(R.id.tv_minutes);
        tv_seconds = (TextView) findViewById(R.id.tv_seconds);
        tv_super = (TextView) findViewById(R.id.tv_super);
        tv_sas = (TextView) findViewById(R.id.tv_sas);
        tv_message = (TextView) findViewById(R.id.tv_message);

        rv_recommended = (RecyclerView) findViewById(R.id.rv_recommended);
        id_recyclerview_horizontal = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        rv_brand1 = (RecyclerView) findViewById(R.id.rv_brand1);
        rv_brand2 = (RecyclerView) findViewById(R.id.rv_brand2);
        rv_life = (RecyclerView) findViewById(R.id.rv_life);
        rv_goodstj = (RecyclerView) findViewById(R.id.rv_goodstj);
        id_advertising1 = (MyListView) findViewById(R.id.id_advertising1);
        id_advertising2 = (MyListView) findViewById(R.id.id_advertising2);
        ml_goods = (MyListView) findViewById(R.id.ml_goods);
        msv_gd = (ListeningScrollView) findViewById(R.id.msv_gd);
        // 导航
        mPage1 = LayoutInflater.from(this).inflate(
                R.layout.pager_jd_navigation1, null);
        mPage2 = LayoutInflater.from(this).inflate(
                R.layout.pager_jd_navigation2, null);
        rv_navigation1 = (RecyclerView) mPage1
                .findViewById(R.id.rv_navigation1);
        rv_navigation2 = (RecyclerView) mPage2
                .findViewById(R.id.rv_navigation2);

        initView();
    }

    private String txtlist1[] = new String[]{"商城", "社区", "足迹", "发现", "热点",
            "积分", "分销", "领券", "收藏", "分类"};
    private String txtlist2[] = new String[]{"兑换", "分销"};

    @Override
    protected void initView() {
        ll_qrcode.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        tv_more.setOnClickListener(this);

        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        rl_title.getBackground().mutate().setAlpha(0);
        msv_gd.setScrollYViewListener(this);

        rv_brand1.setLayoutManager(new LinearLayoutManager(this));
        rv_brand2.setLayoutManager(new LinearLayoutManager(this));
        rv_life.setLayoutManager(new LinearLayoutManager(this));
        rv_goodstj.setLayoutManager(new LinearLayoutManager(this));
        // 秒杀
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        id_recyclerview_horizontal.setLayoutManager(linearLayoutManager);

        // 快报
        rv_recommended.setNestedScrollingEnabled(false);
        rv_recommended.setLayoutManager(new LinearLayoutManager(this));


        // 导航
        rv_navigation1.setNestedScrollingEnabled(false);
        rv_navigation2.setNestedScrollingEnabled(false);
        rv_navigation1.setLayoutManager(new GridLayoutManager(this, 5));
        rv_navigation2.setLayoutManager(new GridLayoutManager(this, 5));
        jd_NavigationAdapter = new JD_NavigationAdapter(this, txtlist1);
//		jd_NavigationAdapter2 = new JD_NavigationAdapter(this, txtlist2);
        rv_navigation1.setAdapter(jd_NavigationAdapter);
//		rv_navigation2.setAdapter(jd_NavigationAdapter2);
        mViews.add(mPage1);
//		mViews.add(mPage2);
        vp_navigation.setAdapter(new ViewPagerAdapter());
        vp_navigation.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        circular_01.setImageDrawable(getResources().getDrawable(
                                R.drawable.jd_dian1));
                        circular_02.setImageDrawable(getResources().getDrawable(
                                R.drawable.jd_dian2));
                        break;
                    case 1:
                        circular_01.setImageDrawable(getResources().getDrawable(
                                R.drawable.jd_dian2));
                        circular_02.setImageDrawable(getResources().getDrawable(
                                R.drawable.jd_dian1));
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        initData();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void initData() {
        netRun.Intex();
        netRun.getCategoryTeo(null, 0);
        //快报
        netRun.newslistify("1", "10");
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onScrollChanged(int y) {
        // 顶部透明度设置
        if (y < i_dh.getTop()) {
            float i = (y / (float) (i_dh.getTop() - rl_title.getBottom())) * 255;
            if (i > 0 && i <= 255) {
                rl_title.getBackground().mutate().setAlpha((int) i);
                if ((int) i > 125) {
                    tv_message.setTextColor(Color.BLACK);
                    tv_sas.setTextColor(Color.BLACK);
                    iv_message.setImageResource(R.drawable.jd_message2);
                    iv_sas.setImageResource(R.drawable.jd_qrcode2);
                    rl_se.setBackgroundResource(R.drawable.jd_homesearch2);
                } else {
                    tv_message.setTextColor(Color.WHITE);
                    tv_sas.setTextColor(Color.WHITE);
                    iv_message.setImageResource(R.drawable.jd_message);
                    iv_sas.setImageResource(R.drawable.jd_qrcode);
                    rl_se.setBackgroundResource(R.drawable.jd_homesearch);
                }
            }
        }
    }

    /**
     * 设置广告轮播
     */
    protected void setAD(final List<AdColumnInfo> toplist) {
        if (toplist == null) return;
        List<String> listAd = new ArrayList<String>();
        for (int i = 0; i < toplist.size(); i++) {
            listAd.add(toplist.get(i).image);
        }
        String[] ADurl = listAd.toArray(new String[listAd.size()]);
        if (adgallery.mUris == null)
            adgallery.start(this, ADurl, null, 3000, ovalLayout,
                    R.drawable.jd_quan, R.drawable.jd_heng);
        adgallery.setMyOnItemClickListener(new MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("url", toplist.get(curIndex).data);
                bundle.putString("title", "艾特商城");
                openActivity(WebActivity.class, bundle);
            }
        });
    }

    /**
     * 导航适配器
     *
     * @author xiaoyu
     */
    private class ViewPagerAdapter extends PagerAdapter {

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mViews.get(arg1));
        }

        public void finishUpdate(View arg0) {

        }

        public int getCount() {

            return mViews.size();
        }

        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mViews.get(arg1));
            return mViews.get(arg1);

        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        public Parcelable saveState() {
            return null;
        }

        public void startUpdate(View arg0) {

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_qrcode) {// 扫啊扫
            Intent intent = new Intent(JDHomePage.this, CaptureActivity.class);
            intent.putExtra("type", 0);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (id == R.id.ll_message) {// 消息
        } else if (id == R.id.iv_search) {// 搜索
            search();
        } else if (id == R.id.tv_more) {//更多
            Intent intent3 = new Intent(JDHomePage.this,
                    InformationActivity.class);
            intent3.putExtra("person_in", "0");
            startActivity(intent3);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                String result = data.getStringExtra("result");
                Log.i("-----------------------", "扫描结果 " + result);
            }
        }
    }

    /**
     * 搜索
     */
    private void search() {
        String key_wrods = et_search.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", key_wrods);
        if (!key_wrods.equals("")) {
            openActivity(GoodsListActivity.class, bundle);
        }
        et_search.setText("");
    }

    // 权限信息
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            /*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            // MediaPlayer权限
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
            }
            // 摄像头权限
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            // 录音和播放
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }

            if (permissions.size() > 0) {
                requestPermissions(
                        permissions.toArray(new String[permissions.size()]),
                        SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList,
                                  String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}
