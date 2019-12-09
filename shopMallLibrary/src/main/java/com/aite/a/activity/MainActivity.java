package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.aite.a.HomeTabActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.AdColumnInfo;
import com.aite.a.model.CategoryList;
import com.aite.a.model.GoodList;
import com.aite.a.model.GoodList2;
import com.aite.a.model.GoodList2.goods_list;
import com.aite.a.model.MainNavigation;
import com.aite.a.model.SpecialAdList;
import com.aite.a.parse.JsonParse;
import com.aite.a.parse.NetRun;
import com.aite.a.sqlbase.HomeCache;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.MyGifView;
import com.aite.a.utils.Sqlutls;
import com.aite.a.view.CustomScrollView;
import com.aite.a.view.CustomScrollView.OnScrollListener;
import com.aite.a.view.EditTextWithDel;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyAdGallery.MyOnItemClickListener;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hotel.HotelHomeActivity;
import jd.page.JDHomePage;

//import com.skythinking.squarecameralib1.ImageUtility;

/**
 * 首页
 *
 * @author xiaoyu
 */
public class MainActivity extends BaseActivity implements OnClickListener,
        OnScrollListener, OnItemClickListener {
    private ImageButton ib_serach;
    private ImageButton ib_camera;
    private EditTextWithDel et_serach;
    private ImageView ib_sort;
    private Intent intent;
    private CustomScrollView main_scrollview;
    private LinearLayout search_ll;
    private MyGifView gif_bottom;
    private ListView ad_sale_price_gv, ad_best_gv, lv_exceed;
    private Button bt_to_top;
    private GridView gv_miannavigation;
    private NavigationAdapter nav;
    private ImageView iv_1f_right, iv_2f_right, iv_3f_right, iv_4f_right,
            iv_5f_right;
    private MyGridView sale_goods_gr, hot_one_floor_gr, hot_two_floor_gr,
            hot_three_floor_gr, hot_four_floor_gr, hot_five_floor_gr;
    private List<GoodList> saleGoodsList, one_floor_list, two_floor_list,
            three_floor_list, four_floor_list,
            five_floor_list = new ArrayList<GoodList>();
    private RelativeLayout huobao_tj_rl, hot_two_floo_rl, hot_three_floo_rl,
            hot_four_floo_rl, hot_five_floo_rl, sale_price_rl, rl_main_hongbao;
    private LinearLayout hot_one_floo_rl, hot_one_floo_rl2, hot_commodity_ll,
            sale_price_ll;
    private RelativeLayout hot_two_floo_rl2, hot_three_floo_rl2,
            hot_four_floo_rl2, hot_five_floo_rl2, huobao_tj_rl2,
            sale_price_ll2;
    private TextView main_tv_1f_name, main_tv_2f_name, main_tv_3f_name,
            main_tv_4f_name, main_tv_5f_name;
    // private GifImageView adv_bottom;
    private MyAdGallery gallery;
    private LinearLayout ovalLayout;
    private WindowManager wm;
    private NetRun netRun;
    private String page = "4";
    private int run = 0;
    private List<AdColumnInfo> ad_Top = new ArrayList<AdColumnInfo>();
    private List<SpecialAdList> ad_BestList, ad_SalePriceList, ad_Bottom;
    private List<CategoryList> categoryOne;
    private MianGoodsAdapder2 myadapder2;
    private MainNavigation mainnav;
    private View miaosha_goods_rl;
    private static final int REQUEST_CAMERA = 0;
    private Point mSize;
    private RelativeLayout main_top_layout, rl_tese, rl_tesee;
    private View i_search;
    private List<GoodList2> goodlist2 = new ArrayList<GoodList2>();
    private GoodList2 oodList2;



    /**
     * 数据库
     */
    private HomeCache listDB;
    private SQLiteDatabase db;
    private Sqlutls sqlutls;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case home_ad_id:
                    if (msg.obj != null) {
                        Map<String, Object> map = (Map<String, Object>) msg.obj;
                        ad_Top = (List<AdColumnInfo>) map.get("1");
                        // 测试数据 begin
                        /*
                         * List list = new ArrayList(); for (int i = 0; i < 5; i++)
                         * { AdColumnInfo info = new AdColumnInfo(); info.data = "";
                         * info.type = ""; info.image =
                         * "http://aitejt.com/data/upload/mobile/special/s0/s0_04987459136500897.jpg"
                         * ; list.add(info); }
                         */
                        // 测试数据 end
                        saleGoodsList = (List<GoodList>) map.get("2");
                        ad_SalePriceList = (List<SpecialAdList>) map.get("3");
                        ad_BestList = (List<SpecialAdList>) map.get("4");
                        ad_Bottom = (List<SpecialAdList>) map.get("5");
                        // mainnav = (MainNavigation) map.get("6");
                        // 导航
                        // if (mainnav != null) {
                        // // gv_miannavigation.setLayoutParams(new LayoutParams(
                        // // LayoutParams.MATCH_PARENT,
                        // // LayoutParams.WRAP_CONTENT));
                        // // nav = new NavigationAdapter();
                        // // gv_miannavigation.setAdapter(nav);
                        // // gv_miannavigation.setOnItemClickListener(onitem);
                        // }
                        if (ad_Top == null) {
                            return;
                        }
                        if (ad_Top != null || !ad_Top.isEmpty()) {
                            setAD(ad_Top);
                        }
                        // setAD(list);
                        setSaleGoods(saleGoodsList);
                        setAD(ad_BestList, ad_best_gv);
                        setAD(ad_SalePriceList, ad_sale_price_gv);
                        // setGif("http://cdn.duitang.com/uploads/item/201311/20/20131120213622_mJCUy.thumb.600_0.gif");
                        if (ad_Bottom != null) {
                            setGif(ad_Bottom.get(0).adv_img);
                        }
                    } else {
                        CommonTools.showShortToast(MainActivity.this,
                                getI18n(R.string.act_no_data_load));
                    }
                    break;
                case home_ad_err:
                    CommonTools.showShortToast(MainActivity.this,
                            getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case home_ad_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
                    // mdialog.show();
                    break;
                case goods_list_id:
                    if (msg.obj != null) {
                        // setFloorData(String.valueOf(msg.arg1),
                        // (List<GoodList>) msg.obj);
                        // goodslist.add((List<GoodList>) msg.obj);
                        // if (categoryOne.size() == goodslist.size()) {
                        // if (categoryOne != null && categoryOne.size() != 0) {
                        // hot_one_floo_rl.setVisibility(View.VISIBLE);
                        // }
                        // myadapter = new MianGoodsAdapder();
                        // lv_exceed.setAdapter(myadapter);
                        // mdialog.dismiss();
                        // }
                        oodList2 = (GoodList2) msg.obj;
                        goodlist2.add(oodList2);
                        if (categoryOne.size() == goodlist2.size()) {
                            if (categoryOne != null && categoryOne.size() != 0) {
                                hot_one_floo_rl.setVisibility(View.VISIBLE);
                            }
                            myadapter = new MianGoodsAdapder();
                            lv_exceed.setAdapter(myadapter);
                            mdialog.dismiss();
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
                        CommonTools.showShortToast(MainActivity.this,
                                getI18n(R.string.act_no_data_load));
                    }
                    break;
                case goods_list_err:
                    CommonTools.showShortToast(MainActivity.this,
                            getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case goods_list_start:
                    break;
                case two_category_id:
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
                    CommonTools.showShortToast(MainActivity.this,
                            getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case two_category_start:
                    break;
            }
        }

        ;
    };

    List<GoodList> goods;
    List<List<GoodList>> goodslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);
        netRun = new NetRun(MainActivity.this, handler);
        bitmapUtils = new BitmapUtils(this);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        goodslist = new ArrayList<List<GoodList>>();
        findViewById();
        initView();
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "错误";
        }
    }

    private MianGoodsAdapder myadapter;

    @Override
    protected void initData() {
        netRun.Intex(sqlutls);
        netRun.getCategoryTeo2(null, 0, sqlutls);
    }

    @Override
    protected void findViewById() {
        miaosha_goods_rl = findViewById(R.id.miaosha_goods_rl);
        ib_serach = (ImageButton) findViewById(R.id._search_button);
        ib_camera = (ImageButton) findViewById(R.id.ib_camera);
        et_serach = (EditTextWithDel) findViewById(R.id._search_edit);
        ad_sale_price_gv = (ListView) findViewById(R.id.ad_sale_price_gv);
        ad_best_gv = (ListView) findViewById(R.id.ad_best_gv);
        lv_exceed = (ListView) findViewById(R.id.lv_exceed);
        hot_commodity_ll = (LinearLayout) findViewById(R.id.hot_commodity_ll);
        sale_price_ll = (LinearLayout) findViewById(R.id.sale_price_ll);
        sale_price_rl = (RelativeLayout) findViewById(R.id.sale_price_rl);
        sale_price_ll2 = (RelativeLayout) findViewById(R.id.sale_price_rl2);
        huobao_tj_rl = (RelativeLayout) findViewById(R.id.huobao_tj_rl);
        huobao_tj_rl2 = (RelativeLayout) findViewById(R.id.huobao_tj_rl2);
        hot_one_floo_rl = (LinearLayout) findViewById(R.id.hot_one_floor_rl);
        hot_two_floo_rl = (RelativeLayout) findViewById(R.id.hot_two_floor_rl);
        hot_three_floo_rl = (RelativeLayout) findViewById(R.id.hot_three_floor_rl);
        hot_four_floo_rl = (RelativeLayout) findViewById(R.id.hot_four_floor_rl);
        hot_five_floo_rl = (RelativeLayout) findViewById(R.id.hot_five_floor_rl);
        hot_one_floo_rl2 = (LinearLayout) findViewById(R.id.hot_one_floor_rl2);
        hot_two_floo_rl2 = (RelativeLayout) findViewById(R.id.hot_two_floor_rl2);
        hot_three_floo_rl2 = (RelativeLayout) findViewById(R.id.hot_three_floor_rl2);
        hot_four_floo_rl2 = (RelativeLayout) findViewById(R.id.hot_four_floor_rl2);
        hot_five_floo_rl2 = (RelativeLayout) findViewById(R.id.hot_five_floor_rl2);
        rl_main_hongbao = (RelativeLayout) findViewById(R.id.rl_main_hongbao);
        main_tv_1f_name = (TextView) findViewById(R.id.main_tv_1f_name);
        main_tv_2f_name = (TextView) findViewById(R.id.main_tv_2f_name);
        main_tv_3f_name = (TextView) findViewById(R.id.main_tv_3f_name);
        main_tv_4f_name = (TextView) findViewById(R.id.main_tv_4f_name);
        main_tv_5f_name = (TextView) findViewById(R.id.main_tv_5f_name);
        iv_1f_right = (ImageView) findViewById(R.id.main_iv_1f_right);
        iv_2f_right = (ImageView) findViewById(R.id.main_iv_2f_right);
        iv_3f_right = (ImageView) findViewById(R.id.main_iv_3f_right);
        iv_4f_right = (ImageView) findViewById(R.id.main_iv_4f_right);
        iv_5f_right = (ImageView) findViewById(R.id.main_iv_5f_right);
        sale_goods_gr = (MyGridView) findViewById(R.id.sale_goods_gr);
        hot_one_floor_gr = (MyGridView) findViewById(R.id.hot_one_floor_gr);
        hot_two_floor_gr = (MyGridView) findViewById(R.id.hot_two_floor_gr);
        hot_three_floor_gr = (MyGridView) findViewById(R.id.hot_three_floor_gr);
        hot_four_floor_gr = (MyGridView) findViewById(R.id.hot_four_floor_gr);
        hot_five_floor_gr = (MyGridView) findViewById(R.id.hot_five_floor_gr);
        ib_sort = (ImageView) findViewById(R.id.main_ib_sort);
        gallery = (MyAdGallery) findViewById(R.id.adgallery);
        ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);
        bt_to_top = (Button) findViewById(R.id.bt_to_top);
        main_scrollview = (CustomScrollView) findViewById(R.id.main_scrollview);
        // adv_bottom = (GifImageView) findViewById(R.id.adv_bottom);
        gv_miannavigation = (GridView) findViewById(R.id.gv_miannavigation);
        gif_bottom = (MyGifView) findViewById(R.id.gif_bottom);
        main_top_layout = (RelativeLayout) findViewById(R.id.main_top_layout);
        rl_tese = (RelativeLayout) findViewById(R.id.rl_tese);
        rl_tesee = (RelativeLayout) findViewById(R.id.rl_tesee);

        i_search = findViewById(R.id.i_search);
        // 禁用硬件加速
        // adv_bottom.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void initView() {
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

        sale_goods_gr.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startProductDetailsIntent(saleGoodsList.get(position).goods_id);
            }
        });
        ib_serach.setOnClickListener(this);
        ib_camera.setOnClickListener(this);
        iv_1f_right.setOnClickListener(this);
        iv_2f_right.setOnClickListener(this);
        iv_3f_right.setOnClickListener(this);
        iv_4f_right.setOnClickListener(this);
        iv_5f_right.setOnClickListener(this);
        rl_main_hongbao.setOnClickListener(this);
        ib_sort.setOnClickListener(this);
        bt_to_top.setOnClickListener(this);
        ad_sale_price_gv.setOnItemClickListener(this);
        ad_best_gv.setOnItemClickListener(this);
        hot_one_floor_gr.setOnItemClickListener(this);
        hot_two_floor_gr.setOnItemClickListener(this);
        hot_three_floor_gr.setOnItemClickListener(this);
        hot_four_floor_gr.setOnItemClickListener(this);
        hot_five_floor_gr.setOnItemClickListener(this);
        main_scrollview.setOnScrollListener(this);
        // 导航
        nav = new NavigationAdapter();
        gv_miannavigation.setAdapter(nav);
        gv_miannavigation.setOnItemClickListener(onitem);

        // 先加载缓存数据
        listDB = new HomeCache(this);
        // 获得数据库对象
        db = listDB.getReadableDatabase();
        sqlutls = new Sqlutls(db);

        String query = sqlutls.query("home");
        Map<String, Object> indexVp = JsonParse.getIndexVp(query);
        if (query != null && indexVp != null) {
            hauncun(indexVp);
        }
        sqlutls.delete();
    }

    /**
     * 设置广告轮播
     *
     * @param obj
     */
    protected void setAD(final List<AdColumnInfo> obj) {
        List<String> listAd = new ArrayList<String>();
        for (AdColumnInfo string : obj) {
            listAd.add(string.image);
        }
        String[] ADurl = listAd.toArray(new String[listAd.size()]);
        if (gallery.mUris == null)
            gallery.start(this, ADurl, null, 3000, ovalLayout,
                    R.drawable.dot_focused, R.drawable.dot_normal);
        gallery.setMyOnItemClickListener(new MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                Bundle bundle = new Bundle();
                bundle.putString("url", obj.get(curIndex).data);
                bundle.putString("title", obj.get(curIndex).type);
                openActivity(WebActivity.class, bundle);
            }
        });
    }

    /**
     * 设置掌上秒杀控件
     *
     * @param bestGoods
     */
    @SuppressWarnings("deprecation")
    private void setSaleGoods(List<GoodList> bestGoods) {
        HorizontalScrollView mImageContainer = (HorizontalScrollView) findViewById(R.id.hot_sell_scr);
        // mImageContainer.setVisibility(View.VISIBLE);
        miaosha_goods_rl.setVisibility(View.VISIBLE);
        int gridItemWidth = wm.getDefaultDisplay().getWidth() / 3 - 10;
        // 适配广告轮播高度
        RelativeLayout.LayoutParams galleryparams = (RelativeLayout.LayoutParams) gallery
                .getLayoutParams();
        galleryparams.height = wm.getDefaultDisplay().getWidth() / 2;
        gallery.setLayoutParams(galleryparams);
        // // 适配顶部菜单高度
        // RelativeLayout.LayoutParams topparams =
        // (android.widget.RelativeLayout.LayoutParams) main_top_layout
        // .getLayoutParams();
        // topparams.height = wm.getDefaultDisplay().getWidth() / 9;
        // main_top_layout.setLayoutParams(topparams);
        // // 搜索栏高度适配
        // RelativeLayout.LayoutParams searchparams =
        // (android.widget.RelativeLayout.LayoutParams) i_search
        // .getLayoutParams();
        // searchparams.height = (int) ((wm.getDefaultDisplay().getWidth() /
        // 10)*0.8);
        // i_search.setLayoutParams(searchparams);

        int rowNum = 1;
        if (bestGoods == null && bestGoods.size() == 0) {
            return;
        }
        int columnNum = bestGoods.size() / rowNum;
        if (columnNum % rowNum != 0) {
            columnNum++;
        }
        int gridViewWidth = (gridItemWidth) * columnNum;
        LayoutParams params = new LayoutParams(
                gridViewWidth, sale_goods_gr.getHeight());
        sale_goods_gr.setLayoutParams(params);
        sale_goods_gr.setColumnWidth(gridItemWidth);
        sale_goods_gr.setHorizontalSpacing(10);
        sale_goods_gr.setStretchMode(GridView.NO_STRETCH);
        sale_goods_gr.setNumColumns(columnNum);
        setReferralsGood(bestGoods);
    }

    /**
     * 设置掌上秒杀的数据
     *
     * @param goodsLists
     */
    protected void setReferralsGood(List<GoodList> goodsLists) {
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map1;
        for (GoodList list : goodsLists) {
            map1 = new HashMap<String, Object>();
            map1.put("image", list.getGoods_image());
            map1.put("price", list.getGoods_promotion_price()
                    + getI18n(R.string.yuan));
            map1.put("name", list.getGoods_name());
            imagelist.add(map1);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist,
                R.layout.good_recommend, new String[]{"image", "price",
                "name"}, new int[]{R.id.goods_image,
                R.id.goods_price, R.id.goods_name});
        simpleAdapter.setViewBinder(new ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView) {
                    ImageView iv = (ImageView) view;
                    bitmapUtils.display(iv, data.toString());
                    LayoutParams goodsparams = (LayoutParams) iv
                            .getLayoutParams();
                    goodsparams.height = wm.getDefaultDisplay().getWidth() / 3 - 10;
                    iv.setLayoutParams(goodsparams);
                    return true;
                }
                return false;
            }
        });
        sale_goods_gr.setAdapter(simpleAdapter);

    }

    /**
     * 设置楼层的数据
     *
     * @param arg1
     * @param obj
     */
    protected void setFloorData(String arg1, List<GoodList> obj) {

        // TODO
        try {
            if (categoryOne.get(0).getGc_id().equals(arg1)) {
                main_tv_1f_name.setText(categoryOne.get(0).getGc_name());
                setHotFloor(hot_one_floor_gr, obj);
                one_floor_list = obj;
            }
            if (categoryOne.get(1).getGc_id().equals(arg1)) {
                main_tv_2f_name.setText(categoryOne.get(1).getGc_name());
                setHotFloor(hot_two_floor_gr, obj);
                two_floor_list = obj;
            }
            if (categoryOne.get(2).getGc_id().equals(arg1)) {
                main_tv_3f_name.setText(categoryOne.get(2).getGc_name());
                setHotFloor(hot_three_floor_gr, obj);
                three_floor_list = obj;
            }
            if (categoryOne.get(3).getGc_id().equals(arg1)) {
                main_tv_4f_name.setText(categoryOne.get(3).getGc_name());
                setHotFloor(hot_four_floor_gr, obj);
                four_floor_list = obj;
            }
            if (categoryOne.get(4).getGc_id().equals(arg1)) {
                main_tv_5f_name.setText(categoryOne.get(4).getGc_name());
                setHotFloor(hot_five_floor_gr, obj);
                five_floor_list = obj;
                mdialog.dismiss();
            }

        } catch (Exception e) {
            mdialog.dismiss();
        }

    }

    /**
     * 设置热门楼层的数据
     *
     * @param view
     * @param goodsLists
     */
    protected void setHotFloor(GridView view, List<GoodList> goodsLists) {
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map1;
        for (GoodList list : goodsLists) {
            map1 = new HashMap<String, Object>();
            map1.put("image", list.getGoods_image_url());
            map1.put("name", list.getGoods_name());
            map1.put("price", list.getGoods_price());
            imagelist.add(map1);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist,
                R.layout.hot_floor_item, new String[]{"image", "name",
                "price"}, new int[]{R.id.hot_floor_img,
                R.id.hot_floor_name, R.id.hot_floor_price});
        simpleAdapter.setViewBinder(new ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView) {
                    ImageView iv = (ImageView) view;
                    bitmapUtils.display(iv, data.toString());
                    return true;
                }
                return false;
            }
        });
        view.setAdapter(simpleAdapter);
        view.setBackgroundResource(R.color.fengexian);
    }

    /**
     * 设置广告拦的数据
     *
     * @param adLists
     * @param view
     */
    protected void setAD(List<SpecialAdList> adLists, ListView view) {
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map1;
        if (adLists != null && adLists.size() != 0) {
            huobao_tj_rl.setVisibility(View.VISIBLE);
            sale_price_rl.setVisibility(View.VISIBLE);
        }

        for (SpecialAdList list : adLists) {
            map1 = new HashMap<String, Object>();
            map1.put("image", list.adv_img);
            map1.put("title", list.adv_title);
            map1.put("url", "");
            imagelist.add(map1);
        }
        // for (int i = 0; i < Datas.url.length; i++) {
        // map1 = new HashMap<String, Object>();
        // map1.put("image", Datas.url[i]);
        // map1.put("title", Datas.name[i]);
        // map1.put("url", Datas.price[i]);
        // imagelist.add(map1);
        // }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist,
                R.layout.hot_list_item,
                new String[]{"image", "title", "url"}, new int[]{
                R.id.hot_img, R.id.hot_title, R.id.hot_price});
        simpleAdapter.setViewBinder(new ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView) {
                    ImageView iv = (ImageView) view;
                    bitmapUtils.display(iv, data.toString());
                    RelativeLayout.LayoutParams goodsparams = (RelativeLayout.LayoutParams) iv
                            .getLayoutParams();
                    // 广告栏适配
                    goodsparams.height = wm.getDefaultDisplay().getWidth() / 2;
                    iv.setLayoutParams(goodsparams);
                    return true;
                }
                return false;
            }
        });
        view.setAdapter(simpleAdapter);
    }

    int i = 0;

    /**
     * 设置底部广告
     *
     * @param url
     */
    private void setGif(final String url) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (i < 3) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            i++;
                            gif_bottom.setMovieURL(url);
                        }
                    });
                }
            }
        }).start();
        // // 获取手机型号
        // final String models = android.os.Build.MODEL;
        // // 禁用硬件加速
        // adv_bottom.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
        // @SuppressLint("NewApi")
        // @Override
        // public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
        // GifDrawable drawable = null;
        // BitmapDrawable bd = null;
        // // Redmi Note 3和GifDrawable冲突
        // if (models.equals("Redmi Note 3")) {
        // Bitmap decodeByteArray = BitmapFactory.decodeByteArray(
        // arg2, 0, arg2.length);
        // bd = new BitmapDrawable(decodeByteArray);
        // adv_bottom.setBackground(bd);
        // } else {
        // try {
        // drawable = new GifDrawable(arg2);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // adv_bottom.setBackground(drawable);
        // }
        //
        // }
        //
        // @Override
        // public void onFailure(int arg0, Header[] arg1, byte[] arg2,
        // Throwable arg3) {
        // }
        // });
    }

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._search_button:
//			search();
//			break;
//		case R.id.ib_camera:
//			// 跳转到自定义相机
//			// Intent startCustomCameraIntent = new Intent(this,
//			// com.skythinking.squarecameralib1.CameraActivity.class);
//			// startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
//			break;
//		case R.id.main_ib_sort:
//			// 二维码
//			// openActivity(CaptureActivity.class);
//
//			break;
//		case R.id.main_iv_1f_right:
//			startGoodsListIntent(categoryOne.get(0).getGc_id());
//			break;
//		case R.id.main_iv_2f_right:
//			startGoodsListIntent(categoryOne.get(1).getGc_id());
//			break;
//		case R.id.main_iv_3f_right:
//			startGoodsListIntent(categoryOne.get(2).getGc_id());
//			break;
//		case R.id.main_iv_4f_right:
//			startGoodsListIntent(categoryOne.get(3).getGc_id());
//			break;
//		case R.id.main_iv_5f_right:
//			startGoodsListIntent(categoryOne.get(4).getGc_id());
//			break;
//		case R.id.bt_to_top:
//			CustomScrollView view = (CustomScrollView) findViewById(R.id.main_scrollview);
//			view.scrollTo(view.getTop(), 0);
//			bt_to_top.setVisibility(View.GONE);
//			huobao_tj_rl2.setVisibility(View.GONE);
//			hot_one_floo_rl2.setVisibility(View.GONE);
//			hot_two_floo_rl2.setVisibility(View.GONE);
//			hot_three_floo_rl2.setVisibility(View.GONE);
//			hot_four_floo_rl2.setVisibility(View.GONE);
//			hot_five_floo_rl2.setVisibility(View.GONE);
//			break;
//		case R.id.hot_one_floor_rl2:
//			startGoodsListIntent(categoryOne.get(0).getGc_id());
//			break;
//		case R.id.hot_two_floor_rl2:
//			startGoodsListIntent(categoryOne.get(1).getGc_id());
//			break;
//		case R.id.hot_three_floor_rl2:
//			startGoodsListIntent(categoryOne.get(2).getGc_id());
//			break;
//		case R.id.hot_four_floor_rl2:
//			startGoodsListIntent(categoryOne.get(3).getGc_id());
//			break;
//		case R.id.hot_five_floor_rl2:
//			startGoodsListIntent(categoryOne.get(4).getGc_id());
//			break;
//		case R.id.rl_main_hongbao:
//			rl_main_hongbao.setVisibility(View.GONE);
//			break;
//		}
        if (v.getId() == R.id._search_button) {
            search();
        } else if (v.getId() == R.id.ib_camera) {

        } else if (v.getId() == R.id.main_ib_sort) {

        } else if (v.getId() == R.id.main_iv_1f_right) {
            startGoodsListIntent(categoryOne.get(0).getGc_id());
        } else if (v.getId() == R.id.main_iv_2f_right) {
            startGoodsListIntent(categoryOne.get(1).getGc_id());
        } else if (v.getId() == R.id.main_iv_3f_right) {
            startGoodsListIntent(categoryOne.get(2).getGc_id());
        } else if (v.getId() == R.id.main_iv_4f_right) {
            startGoodsListIntent(categoryOne.get(3).getGc_id());
        } else if (v.getId() == R.id.main_iv_5f_right) {
            startGoodsListIntent(categoryOne.get(4).getGc_id());
        } else if (v.getId() == R.id.bt_to_top) {
            CustomScrollView view = (CustomScrollView) findViewById(R.id.main_scrollview);
            view.scrollTo(view.getTop(), 0);
            bt_to_top.setVisibility(View.GONE);
            huobao_tj_rl2.setVisibility(View.GONE);
            hot_one_floo_rl2.setVisibility(View.GONE);
            hot_two_floo_rl2.setVisibility(View.GONE);
            hot_three_floo_rl2.setVisibility(View.GONE);
            hot_four_floo_rl2.setVisibility(View.GONE);
            hot_five_floo_rl2.setVisibility(View.GONE);
        } else if (v.getId() == R.id.hot_one_floor_rl2) {
            startGoodsListIntent(categoryOne.get(0).getGc_id());
        } else if (v.getId() == R.id.hot_two_floor_rl2) {
            startGoodsListIntent(categoryOne.get(1).getGc_id());
        } else if (v.getId() == R.id.hot_three_floor_rl2) {
            startGoodsListIntent(categoryOne.get(2).getGc_id());
        } else if (v.getId() == R.id.hot_four_floor_rl2) {
            startGoodsListIntent(categoryOne.get(3).getGc_id());
        } else if (v.getId() == R.id.hot_five_floor_rl2) {
            startGoodsListIntent(categoryOne.get(4).getGc_id());
        } else if (v.getId() == R.id.rl_main_hongbao) {
            rl_main_hongbao.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CAMERA) {
            Uri photoUri = data.getData();
            // Get the bitmap in according to the width of the device
            // Bitmap bitmap =
            // ImageUtility.decodeSampledBitmapFromPath(photoUri.getPath(),
            // mSize.x, mSize.x);
            // ((ImageView) findViewById(R.id.iv_test)).setImageBitmap(bitmap);
            // TODO 在这里拿到bitmap,然后把它上传到服务器,进行模糊搜索
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		switch (parent.getId()) {
//		case R.id.hot_one_floor_gr:
//			startProductDetailsIntent(one_floor_list.get(position).goods_id);
//			break;
//		case R.id.hot_two_floor_gr:
//			startProductDetailsIntent(two_floor_list.get(position).goods_id);
//			break;
//		case R.id.hot_three_floor_gr:
//			startProductDetailsIntent(three_floor_list.get(position).goods_id);
//			break;
//		case R.id.hot_four_floor_gr:
//			startProductDetailsIntent(four_floor_list.get(position).goods_id);
//			break;
//		case R.id.hot_five_floor_gr:
//			startProductDetailsIntent(five_floor_list.get(position).goods_id);
//			break;
//		case R.id.ad_sale_price_gv:
//			startActivitiesIntent(ad_SalePriceList.get(position).adv_url,
//					ad_SalePriceList.get(position).adv_title);
//			break;
//		case R.id.ad_best_gv:
//			startActivitiesIntent(ad_BestList.get(position).adv_url,
//					ad_BestList.get(position).adv_title);
//			break;
//		}
        if (parent.getId() == R.id.hot_one_floor_gr) {
            startProductDetailsIntent(one_floor_list.get(position).goods_id);
        } else if (parent.getId() == R.id.hot_two_floor_gr) {
            startProductDetailsIntent(two_floor_list.get(position).goods_id);
        } else if (parent.getId() == R.id.hot_three_floor_gr) {
            startProductDetailsIntent(three_floor_list.get(position).goods_id);

        } else if (parent.getId() == R.id.hot_four_floor_gr) {
            startProductDetailsIntent(four_floor_list.get(position).goods_id);
        } else if (parent.getId() == R.id.hot_five_floor_gr) {
            startProductDetailsIntent(five_floor_list.get(position).goods_id);
        } else if (parent.getId() == R.id.ad_sale_price_gv) {
            startActivitiesIntent(ad_SalePriceList.get(position).adv_url,
                    ad_SalePriceList.get(position).adv_title);
        } else if (parent.getId() == R.id.ad_best_gv) {
            startActivitiesIntent(ad_BestList.get(position).adv_url,
                    ad_BestList.get(position).adv_title);
        }

    }

    private void search() {
        String key_wrods = et_serach.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", key_wrods);
        if (!key_wrods.equals("")) {
            openActivity(GoodsListActivity.class, bundle);
        }
    }

    /**
     * 跳转到商品详情界面"goods_id"
     */
    private void startProductDetailsIntent(String j) {
        intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("goods_id", j);
        startActivity(intent);
    }

    /**
     * 跳转到商品活动界面"url""title"
     */
    private void startActivitiesIntent(String url, String title) {
        intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    /**
     * 跳转到商品li列表界面"gc_id"
     */
    private void startGoodsListIntent(String j) {
        intent = new Intent(this, GoodsListActivity.class);
        intent.putExtra("gc_id", j);
        startActivity(intent);
    }

    public void onScroll(int scrollY) {
        if (scrollY >= wm.getDefaultDisplay().getHeight()) {
            bt_to_top.setVisibility(View.VISIBLE);
        } else {
            bt_to_top.setVisibility(View.GONE);
        }
        setFloorView(scrollY, sale_price_rl, sale_price_ll2, sale_price_ll);
        setFloorView(scrollY, huobao_tj_rl, huobao_tj_rl2, hot_commodity_ll);
        // setFloorView(scrollY, hot_one_floo_rl, hot_one_floo_rl2,
        // hot_one_floor_gr);
        // TextView tv_1f_name = (TextView) hot_one_floo_rl2
        // .findViewById(R.id.main_tv_1f_name);
        // if (categoryOne != null) {
        // tv_1f_name.setText(categoryOne.get(0).getGc_name());
        // setFloorView(scrollY, hot_two_floo_rl, hot_two_floo_rl2,
        // hot_two_floor_gr);
        // TextView tv_2f_name = (TextView) hot_two_floo_rl2
        // .findViewById(R.id.main_tv_2f_name);
        // tv_2f_name.setText(categoryOne.get(1).getGc_name());
        // setFloorView(scrollY, hot_three_floo_rl, hot_three_floo_rl2,
        // hot_three_floor_gr);
        // TextView tv_3f_name = (TextView) hot_three_floo_rl2
        // .findViewById(R.id.main_tv_3f_name);
        // tv_3f_name.setText(categoryOne.get(2).getGc_name());
        // setFloorView(scrollY, hot_four_floo_rl, hot_four_floo_rl2,
        // hot_four_floor_gr);
        // TextView tv_4f_name = (TextView) hot_four_floo_rl2
        // .findViewById(R.id.main_tv_4f_name);
        // tv_4f_name.setText(categoryOne.get(3).getGc_name());
        // setFloorView(scrollY, hot_five_floo_rl, hot_five_floo_rl2,
        // hot_five_floor_gr);
        // TextView tv_5f_name = (TextView) hot_five_floo_rl2
        // .findViewById(R.id.main_tv_5f_name);
        // tv_5f_name.setText(categoryOne.get(4).getGc_name());
        // }
    }

    private void setFloorView(int scrollY, View v1, View v2, View g1) {
        v2.setBackgroundColor(getResources().getColor(R.color.white));
        int top = v1.getTop();
        int height = g1.getTop() + g1.getHeight();
        if (scrollY > top && scrollY < height) {
            v2.setClickable(true);
            v2.setOnClickListener(this);
            v2.setVisibility(View.VISIBLE);
        } else {
            v2.setVisibility(View.GONE);
        }
        if (scrollY > rl_tese.getTop()) {
            rl_tesee.setVisibility(View.VISIBLE);
        } else {
            rl_tesee.setVisibility(View.GONE);
        }
    }

    private int[] louceng = new int[]{R.drawable.fc_1, R.drawable.fc_2,
            R.drawable.fc_3, R.drawable.fc_4, R.drawable.fc_5, R.drawable.fc_6,};
    private int[] jiantou = new int[]{R.drawable.fc_r_1, R.drawable.fc_r_2,
            R.drawable.fc_r_3, R.drawable.fc_r_4, R.drawable.fc_r_5,
            R.drawable.fc_r_6,};

    public class MianGoodsAdapder extends BaseAdapter {
        List<goods_list> list = null;

        public MianGoodsAdapder() {

        }

        @Override
        public int getCount() {

            return categoryOne.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryOne == null ? null : categoryOne.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        /**
         * 刷新适配器
         */
        public void flushAdapter() {
            notifyDataSetChanged();
        }

        // TODO
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(MainActivity.this,
                        R.layout.maingoodsitem, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();

            // List<String> gc_name = lingshi.getInstance().getGc_name();
            // if (gc_name != null) {
            // for (int i = 0; i < categoryOne.size(); i++) {
            // String string = gc_name.get(i);
            // if (categoryOne.get(position).getGc_name().equals(string)) {
            // list = goodslist.get(i);
            // myadapder2 = new MianGoodsAdapder2(list);
            // holder.lv_hot_five_floor_gr.setAdapter(myadapder2);
            // }
            // }
            // }

            for (int i = 0; i < goodlist2.size(); i++) {
                if (categoryOne.get(position).getGc_name()
                        .equals(goodlist2.get(i).gc_name)) {
                    list = null;
                    myadapder2 = null;
                    list = goodlist2.get(i).goods_list;
                    if (list.size() == 0) {
                        continue;
                    }
                    holder.listtv_main_iv_nf.setText((position + 1) + "F");
                    holder.listrl_main_iv_nf
                            .setBackgroundResource(louceng[(position % 6)]);
                    holder.listmain_iv_5f_right
                            .setImageResource(jiantou[(position % 6)]);
                    holder.listmain_tv_5f_name.setText(categoryOne
                            .get(position).getGc_name());
                    myadapder2 = new MianGoodsAdapder2(list);
                    holder.lv_hot_five_floor_gr.setAdapter(myadapder2);
                    list = null;
                    myadapder2 = null;
                }
            }
            holder.listmain_iv_5f_right
                    .setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            startGoodsListIntent(categoryOne.get(position)
                                    .getGc_id());
                        }
                    });
            return convertView;
        }

        class ViewHolder {
            RelativeLayout listrl_main_iv_nf;
            TextView listtv_main_iv_nf, listmain_tv_5f_name;
            MyGridView lv_hot_five_floor_gr;
            ImageView listmain_iv_5f_right;
            LinearLayout ll_goodsitem;

            public ViewHolder(View convertView) {
                listrl_main_iv_nf = (RelativeLayout) convertView
                        .findViewById(R.id.listrl_main_iv_nf);
                listtv_main_iv_nf = (TextView) convertView
                        .findViewById(R.id.listtv_main_iv_nf);
                listmain_iv_5f_right = (ImageView) convertView
                        .findViewById(R.id.listmain_iv_5f_right);
                listmain_tv_5f_name = (TextView) convertView
                        .findViewById(R.id.listmain_tv_5f_name);
                lv_hot_five_floor_gr = (MyGridView) convertView
                        .findViewById(R.id.lv_hot_five_floor_gr);
                ll_goodsitem = (LinearLayout) convertView
                        .findViewById(R.id.ll_goodsitem);
                convertView.setTag(this);
            }
        }

    }

    public class MianGoodsAdapder2 extends BaseAdapter {
        List<goods_list> list = null;

        public MianGoodsAdapder2(List<goods_list> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list == null ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this,
                        R.layout.good_recommend, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            bitmapUtils.display(holder.goods_image, list.get(position)
                    .getGoods_image_url());
            // 商品适配
            LayoutParams layoutParams = (LayoutParams) holder.goods_image
                    .getLayoutParams();
            layoutParams.height = (int) ((wm.getDefaultDisplay().getWidth() / 2) * 0.7);
            holder.goods_image.setLayoutParams(layoutParams);
            holder.goods_name.setText(list.get(position).getGoods_name());
            holder.goods_price.setText(list.get(position).getGoods_price());
            holder.goods_image.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    startProductDetailsIntent(list.get(position).getGoods_id());
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView goods_image;
            TextView goods_name, goods_price;

            public ViewHolder(View convertView) {
                goods_image = (ImageView) convertView
                        .findViewById(R.id.goods_image);
                goods_name = (TextView) convertView
                        .findViewById(R.id.goods_name);
                goods_price = (TextView) convertView
                        .findViewById(R.id.goods_price);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 导航适配
     *
     * @author Administrator
     */
    public class NavigationAdapter extends BaseAdapter {
        // MainNavigation miannav;
        //
        // public NavigationAdapter(MainNavigation miannav) {
        // this.miannav = miannav;
        // }

        @Override
        public int getCount() {
            // return this.miannav.item.size();
            return daohangname.length;
        }

        @Override
        public Object getItem(int position) {
            // return this.miannav.item == null ? null : this.miannav.item
            // .get(position);
            return daohangname == null ? null : daohangname[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this,
                        R.layout.miyamenuitem, null);
                new ViewHandler(convertView);
            }
            ViewHandler handler = (ViewHandler) convertView.getTag();
            handler.iv_miyamenuiag
                    .setBackgroundResource(daohangtu[(position % daohangtu.length)]);
            // handler.tv_miyamenutext
            // .setText(this.miannav.item.get(position).navigation_name
            // + "");
            handler.tv_miyamenutext.setText(daohangname[position]);
            return convertView;
        }

        class ViewHandler {
            ImageView iv_miyamenuiag;
            TextView tv_miyamenutext;

            public ViewHandler(View convertView) {
                iv_miyamenuiag = (ImageView) convertView
                        .findViewById(R.id.iv_miyamenuiag);
                tv_miyamenutext = (TextView) convertView
                        .findViewById(R.id.tv_miyamenutext);
                convertView.setTag(this);
            }
        }
    }

    private int[] daohangtu = new int[]{R.drawable.ico1, R.drawable.ico2,
            R.drawable.ico3, R.drawable.ico4, R.drawable.ico5, R.drawable.ico6,
            R.drawable.ico7, R.drawable.ico8, R.drawable.ico9, R.drawable.ico10};
    private String[] daohangname = new String[]{"商城", "社交", "直播", "社区", "新闻",
            "积分兑换", "聊天", "定制服务", "视频", "分类"};

    private OnItemClickListener onitem = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            switch (position) {
                // 分类
                case 9:
                    HomeTabActivity.categoryBtn.performClick();
                    break;
                // 视频
                case 8:
                    Intent intent3 = new Intent(MainActivity.this, JDHomePage.class);
                    startActivity(intent3);
                    break;
                // 定制服务
                case 7:

                    break;
                // 聊天
                case 6:

                    break;
                // 积分兑换
                case 5:
                    startActivity(new Intent(MainActivity.this,
                            IntegralShopActivity.class));
                    break;
                // 新闻
                case 4:
                    startActivity(new Intent(MainActivity.this,
                            InformationActivity.class));
                    break;
                // 社区
                case 3:
                    Intent intent1 = new Intent(MainActivity.this,
                            InformationActivity.class);
                    intent1.putExtra("person_in", "2");
                    startActivity(intent1);
                    break;
                // 直播
                case 2:
                    Intent intent6 = new Intent(MainActivity.this,
                            HotelHomeActivity.class);
                    startActivity(intent6);
                    // homeTabActivity.periphery.performClick();
                    break;
                // 社交(暂定为跳转到微发现界面)
                case 1:
                    Intent intent2 = new Intent(MainActivity.this,
                            InformationActivity.class);
                    intent2.putExtra("person_in", "1");
                    startActivity(intent2);
                    break;
                // 商城
                case 0:
                    HomeTabActivity.mainBtn.performClick();

                    /*
                     * startActivity(new Intent(MainActivity.this,
                     * MyfootprintActivity.class));
                     */
                    // startActivity(new Intent(MainActivity.this,
                    // IntegralShopActivity.class));
                    // startActivity(new Intent(MainActivity.this,
                    // BbsActivity.class));
                    break;
                // Intent web1 = new Intent(MainActivity.this, WebActivity.class);
                // web1.putExtra("url", mainnav.item.get(position).navigation_data);
                // web1.putExtra("title",
                // mainnav.item.get(position).navigation_name);
                // startActivity(web1);
            }
        }
    };
    private HomeTabActivity homeTabActivity = new HomeTabActivity();

    // 预加载首页
    private void hauncun(Map<String, Object> obj) {
        Map<String, Object> map = (Map<String, Object>) obj;
        ad_Top = (List<AdColumnInfo>) map.get("1");
        saleGoodsList = (List<GoodList>) map.get("2");
        ad_SalePriceList = (List<SpecialAdList>) map.get("3");
        ad_BestList = (List<SpecialAdList>) map.get("4");
        ad_Bottom = (List<SpecialAdList>) map.get("5");
        mainnav = (MainNavigation) map.get("6");
        if (ad_Top == null) {
            return;
        }
        if (ad_Top != null || !ad_Top.isEmpty()) {
            setAD(ad_Top);
        }
        setSaleGoods(saleGoodsList);
        setAD(ad_BestList, ad_best_gv);
        setAD(ad_SalePriceList, ad_sale_price_gv);
        if (ad_Bottom != null) {
            setGif(ad_Bottom.get(0).adv_img);
        }
    }

    @Override
    public void ReGetData() {
        initData();
    }

    @Override
    protected void onResume() {
        initData();
        super.onResume();
    }

}
