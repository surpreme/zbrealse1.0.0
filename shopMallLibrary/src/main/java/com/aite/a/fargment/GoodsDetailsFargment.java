package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.aite.a.activity.PreferentialSuitActivity;
import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.activity.RecommendCombinationActivity;
import com.aite.a.model.StoreInfoo;
import com.aite.a.view.MyAdGallery;
import com.aiteshangcheng.a.R;
import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.base.BaseFargment;
import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.model.GoodsDetailsInfo.GoodsCommendList;
import com.aite.a.model.GoodsDetailsInfo.GoodsInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BooleanLogin;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.MyGridView;
import com.lidroid.xutils.BitmapUtils;

/**
 * 商品详情界面
 *
 * @author xiaoyu
 */
@SuppressLint("ValidFragment")
public class GoodsDetailsFargment extends BaseFargment implements
        OnClickListener {
    private boolean IsFavorites;
    // 标题========
    private TextView tv_miaosha_time;
    private TextView tv_price, tv_market_price;
    private TextView tv_brand,_price3;
    private TextView tv_sold;
    public List<String> list2 = new ArrayList<String>();
    private TextView tv_goods_name;
    private String typeface = "<font size='14px' color='#000000'>";
    private String font = "</font>";
    private String font_t = "</font></font>\t\t\t\t";
    private TextView product_tv_storage;
    private TextView product_Sales, tv_suit, tv_gostore;
    private TextView product_tv_browser;
    private TextView product_Promotion,tv_imgnumber;
    private MyGridView recommend_goods;
    private ImageView store_img;
    private TextView store_name;
    private TextView member_name;
    private Button btu_add_favorites;
    private MyAdGallery adgallery;

    private View view;
    private String store_id;
    private List<GoodsCommendList> goodRecommend = new ArrayList<GoodsCommendList>();
    private GoodsDetailsInfo detailsInfo = new GoodsDetailsInfo();
    private StoreInfoo storeInfo = new StoreInfoo();
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GoodsDetails_detailsInfo:
                    if (msg.obj != null)
                        detailsInfo = (GoodsDetailsInfo) msg.obj;
                    list2 = new ArrayList<String>();
                    setGoodImg(detailsInfo.goods_image);
                    GoodDetails(detailsInfo.goods_info);
                    setRecommendGood(goodRecommend = detailsInfo.goods_commend_list);
                    setGoodStore(storeInfo = detailsInfo.store_info);
                    break;

                case collectibles_del_id:
                    if (msg.obj.equals("1")) {
                        IsFavorites = false;
                        btu_add_favorites
                                .setBackgroundResource(R.drawable.collection);
                        CommonTools.showShortToast(getActivity(),
                                getI18n(R.string.cancel_collection_success));
                    } else {
                        IsFavorites = true;
                        btu_add_favorites
                                .setBackgroundResource(R.drawable.yes_favorites);
                        CommonTools.showShortToast(getActivity(),
                                getI18n(R.string.cancel_collection_fail));
                    }
                    break;
                case collectibles_del_err:
                    CommonTools.showShortToast(getActivity(),
                            getI18n(R.string.systembusy));
                    break;
                case collectibles_id:
                    if (msg.obj.equals("1")) {
                        IsFavorites = true;
                        btu_add_favorites
                                .setBackgroundResource(R.drawable.yes_favorites);
                        CommonTools.showShortToast(getActivity(),
                                getI18n(R.string.collection_success));
                    } else {
                        IsFavorites = true;
                        btu_add_favorites
                                .setBackgroundResource(R.drawable.collection);
                        CommonTools.showShortToast(getActivity(),
                                getI18n(R.string.collection_fail));
                    }
                    break;
                case collectibles_err:
                    CommonTools.showShortToast(getActivity(),
                            getI18n(R.string.systembusy));
                    break;
            }
        }

        ;
    };
    private RelativeLayout store_ll;
    private String goods_id;

    public GoodsDetailsFargment(String goods_id) {
        this.goods_id = goods_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.product_details_fg, container, false);
        bitmapUtils = new BitmapUtils(getActivity());
        netRun = new NetRun(getActivity(), handler);
        findViewById();
        initView();
        return view;
    }

    // @Override
    // public void onAttach(Activity activity) {
    // // TODO Auto-generated method stub
    // super.onAttach(activity);
    // System.out.println("GoodsDetailsFargment.onAttach()");
    // }
    // @Override
    // public void onCreate(Bundle savedInstanceState) {
    // // TODO Auto-generated method stub
    // super.onCreate(savedInstanceState);
    // }

    @SuppressLint("CutPasteId")
    @Override
    protected void findViewById() {
        tv_goods_name = (TextView) view
                .findViewById(R.id.product_tv_goods_name);
        btu_add_favorites = (Button) view.findViewById(R.id.add_favorites);
        tv_sold = (TextView) view.findViewById(R.id.product_tv_sold);
        tv_brand = (TextView) view.findViewById(R.id.product_tv_brand);
        _price3 = (TextView) view.findViewById(R.id._price3);
        tv_imgnumber = (TextView) view.findViewById(R.id.tv_imgnumber);
        adgallery = (MyAdGallery) view.findViewById(R.id.adgallery);
        // scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        tv_miaosha_time = (TextView) view
                .findViewById(R.id.miaosha_tv_show_time);
        tv_price = (TextView) view.findViewById(R.id.product_tv_price);
        tv_market_price = (TextView) view
                .findViewById(R.id.product_tv_market_price);
        product_tv_storage = (TextView) view
                .findViewById(R.id.product_tv_storage);
        product_Sales = (TextView) view.findViewById(R.id.product_Sales);
        product_tv_browser = (TextView) view
                .findViewById(R.id.product_tv_browser);
        product_Promotion = (TextView) view
                .findViewById(R.id.product_Promotion);
        recommend_goods = (MyGridView) view.findViewById(R.id.recommend_goods);
        store_img = (ImageView) view.findViewById(R.id.store_img);
        store_name = (TextView) view.findViewById(R.id.store_name);
        member_name = (TextView) view.findViewById(R.id.member_name);
        tv_gostore = (TextView) view.findViewById(R.id.tv_gostore);
        tv_suit = (TextView) view.findViewById(R.id.tv_suit);
        store_ll = (RelativeLayout) view.findViewById(R.id.store_ll);
    }

    protected void initView() {
        btu_add_favorites.setOnClickListener(this);
        tv_gostore.setOnClickListener(this);
        tv_suit.setOnClickListener(this);
        store_ll.setOnClickListener(this);
        tv_miaosha_time
                .setText(getI18n(R.string.left)
                        + CommonTools.getSystemHourTime()
                        + getI18n(R.string.hour)
                        + CommonTools.getSystemMinuteTime()
                        + getI18n(R.string.minus)
                        + CommonTools.getSystemSecondTime()
                        + getI18n(R.string.seconds));
        tv_miaosha_time.setVisibility(View.GONE);
        recommend_goods.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),
                        ProductDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("goods_id",
                        goodRecommend.get(position).goods_id);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            }
        });
//        goods_gallery.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                bitmapUtils.display(goods_is, list2.get(position).toString());
//            }
//        });
//        // 主图适配
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) goods_is
//                .getLayoutParams();
//        layoutParams.width = (int) (getScreenWidth(getActivity()) * 0.7);
//        layoutParams.height = (int) (getScreenWidth(getActivity()) * 0.7);
//        goods_is.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_favorites){
            if (BooleanLogin.getInstance().hasLogin(getActivity())) {
                if (IsFavorites == false) {
                    netRun.addFavorites(goods_id, "goods");
                } else {
                    netRun.cancelGoodsFavorite(goods_id, "goods");
                }
            }
        }else if(v.getId()==R.id.store_ll){
            // 如果没有店铺ID就不跳转并提示
            if (storeInfo.store_id == null || storeInfo.store_id.equals("null")
                    || storeInfo.store_id.equals("")) {
                CommonTools.showShortToast(getActivity(),
                        getI18n(R.string.noinformation));
                return;
            }
            Intent intent = new Intent(getActivity(),
                    StoreDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("store_id", storeInfo.store_id);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(v.getId()==R.id.tv_suit){
            //优惠套装
            Intent intent3 = new Intent(getActivity(), PreferentialSuitActivity.class);
            intent3.putExtra("goods_id", detailsInfo.goods_info.goods_id);
            startActivity(intent3);
        }else if(v.getId()==R.id.tv_gostore){
            //推荐组合
            Intent zhintent = new Intent(getActivity(),
                    RecommendCombinationActivity.class);
            zhintent.putExtra("goods_id", goods_id);
            startActivity(zhintent);
        }


//        switch (v.getId()) {
//            case R.id.add_favorites:
//                if (BooleanLogin.getInstance().hasLogin(getActivity())) {
//                    if (IsFavorites == false) {
//                        netRun.addFavorites(goods_id, "goods");
//                    } else {
//                        netRun.cancelGoodsFavorite(goods_id, "goods");
//                    }
//                }
//                break;
//
//            case R.id.store_ll:
//                // 如果没有店铺ID就不跳转并提示
//                if (storeInfo.store_id == null || storeInfo.store_id.equals("null")
//                        || storeInfo.store_id.equals("")) {
//                    CommonTools.showShortToast(getActivity(),
//                            getI18n(R.string.noinformation));
//                    return;
//                }
//                Intent intent = new Intent(getActivity(),
//                        StoreDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("store_id", storeInfo.store_id);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.tv_suit:
//                //优惠套装
//                Intent intent3 = new Intent(getActivity(), PreferentialSuitActivity.class);
//                intent3.putExtra("goods_id", detailsInfo.goods_info.goods_id);
//                startActivity(intent3);
//                break;
//            case R.id.tv_gostore:
//                //推荐组合
//                Intent zhintent = new Intent(getActivity(),
//                        RecommendCombinationActivity.class);
//                zhintent.putExtra("goods_id", goods_id);
//                startActivity(zhintent);
//                break;
//        }
    }

    /**
     * 设置店铺数据
     *
     * @param storeInfo2
     */
    protected void setGoodStore(StoreInfoo storeInfo2) {
        store_id = storeInfo2.store_id;
        bitmapUtils.display(store_img, storeInfo2.avatar);
        store_name.setText(storeInfo2.store_name);
        member_name.setText(storeInfo2.member_name);
    }

    /**
     * 设置推荐商品
     *
     * @param goodsCommendLists
     */
    protected void setRecommendGood(List<GoodsCommendList> goodsCommendLists) {
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map1;
        for (GoodsCommendList list : goodsCommendLists) {
            map1 = new HashMap<String, Object>();
            map1.put("image", list.goods_image_url);
            map1.put("price", list.goods_price + getI18n(R.string.yuan));
            map1.put("name", list.goods_name);
            imagelist.add(map1);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                imagelist, R.layout.goods_list_item2, new String[]{"image",
                "price", "name"}, new int[]{R.id.list_iv_image,
                R.id.list_tv_price, R.id.list_tv_content});
        simpleAdapter.setViewBinder(new ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof String) {
                    ImageView iv = (ImageView) view;
                    bitmapUtils.display(iv, data.toString());
                    return true;
                }
                return false;
            }
        });

        recommend_goods.setAdapter(simpleAdapter);

    }

    /**
     * 设置商品详细信息
     */
    protected void GoodDetails(GoodsInfo goods_info) {
        String promotion_type = null;
        if (goods_info.goods_promotion_type.equals("0"))
            promotion_type = getI18n(R.string.no);
        if (goods_info.goods_promotion_type.equals("groupbuy"))
            promotion_type = getI18n(R.string.panic_buying);
        if (goods_info.goods_promotion_type.equals("xianshi"))
            promotion_type = getI18n(R.string.limited_time_discount);
        String name = typeface + goods_info.goods_name + font_t;
        String market_price = "￥" + goods_info.goods_marketprice;
        String Promotion = getI18n(R.string.promotion_type) + typeface
                + promotion_type + font_t + getI18n(R.string.promotion_price)
                + "￥" + typeface + goods_info.goods_marketprice
                + getI18n(R.string.yuan) + font_t;
        String brand = getI18n(R.string.brand) + typeface
                + getI18n(R.string.no) + font_t;
        String Sales = getI18n(R.string.sold) + typeface
                + goods_info.goods_salenum + getI18n(R.string.act_a) + font;
        String storage = getI18n(R.string.act_stock) + typeface
                + goods_info.goods_storage + getI18n(R.string.act_a) + font;
        String sold = getI18n(R.string.pingjiadengji) + typeface
                + goods_info.evaluation_good_star + font_t
                + getI18n(R.string.praise_has) + typeface
                + goods_info.evaluation_count + "</font>"
                + getI18n(R.string.person_evaluation);
        String browser = getI18n(R.string.scan_quantity) + typeface
                + goods_info.goods_click + font_t
                + getI18n(R.string.limit_counts) + typeface
                + goods_info.evaluation_count + getI18n(R.string.act_a)
                + "</font>/" + getI18n(R.string.every_one);
        tv_goods_name.setText(Html.fromHtml(name));
        if (goods_info.promotion_type==null||goods_info.promotion_type.length()==0){
            _price3.setVisibility(View.GONE);
            tv_price.setText("￥" + goods_info.goods_price+goods_info.goods_unit);
            tv_market_price.setText(market_price);
        }else if (goods_info.promotion_type.equals("groupbuy")){//抢购
            _price3.setVisibility(View.VISIBLE);
            tv_price.setText(getString(R.string.order_reminder169) + goods_info.promotion_price+goods_info.goods_unit+getString(R.string.order_reminder170));
            tv_market_price.setText(getString(R.string.order_reminder171)+goods_info.goods_price);
            _price3.setText(getString(R.string.order_reminder172)+goods_info.goods_marketprice);
        }else if (goods_info.promotion_type.equals("xianshi")){//限时
            _price3.setVisibility(View.VISIBLE);
            tv_price.setText(getString(R.string.order_reminder169) + goods_info.promotion_price+goods_info.goods_unit+getString(R.string.order_reminder173));
            tv_market_price.setText(getString(R.string.order_reminder171)+goods_info.goods_price);
            _price3.setText(getString(R.string.order_reminder172)+goods_info.goods_marketprice);
        }else if (goods_info.promotion_type.equals("miaosha")){//秒杀
            _price3.setVisibility(View.VISIBLE);
            tv_price.setText(getString(R.string.order_reminder169) + goods_info.promotion_price+goods_info.goods_unit+getString(R.string.order_reminder174));
            tv_market_price.setText(getString(R.string.order_reminder171)+goods_info.goods_price);
            _price3.setText(getString(R.string.order_reminder172)+goods_info.goods_marketprice);
        }
        tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        product_tv_storage.setText(Html.fromHtml(storage));
        product_Promotion.setText(Html.fromHtml(Promotion));
        tv_brand.setText(Html.fromHtml(brand));
        product_Sales.setText(Html.fromHtml(Sales));
        tv_sold.setText(Html.fromHtml(sold));
        product_tv_browser.setText(Html.fromHtml(browser));
//        boolean contains = detailsInfo.goods_image.contains(",");
//        if (!contains) {
//            bitmapUtils.display(goods_is, detailsInfo.goods_image);
//        } else {
//            bitmapUtils.display(goods_is, list2.get(0).toString());
//        }
//        setGridView(list2.size());
//        goods_gallery.setAdapter(new ImageAdapter(getActivity(), list2));
        if (detailsInfo.isFavorites.equals("0")) {
            IsFavorites = false;
            btu_add_favorites.setBackgroundResource(R.drawable.collection);
        }
        if (detailsInfo.isFavorites.equals("1")) {
            IsFavorites = true;
            btu_add_favorites.setBackgroundResource(R.drawable.yes_favorites);
        }
    }

    /**
     * 设置顶部图
     *
     * @param obj
     */
    protected void setAD(final List<String> obj) {
        tv_imgnumber.setText("1/"+obj.size());
        final String[] ADurl = obj.toArray(new String[obj.size()]);
        if (adgallery.mUris == null)
            adgallery.start(getActivity(), ADurl, null, 0,
                    null, 0,0,false);
        adgallery.setImageIndex(new MyAdGallery.ImageIndex() {
            @Override
            public void onImageIndex(int curIndex) {
                tv_imgnumber.setText((curIndex+1)+"/"+obj.size());
            }
        });
    }

//    /**
//     * 设置GridView的宽度
//     *
//     * @param size
//     */
//    private void setGridView(int size) {
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int allWidth = (int) (110 * size * density);
//        int itemWidth = (int) (100 * density);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
//        goods_gallery.setLayoutParams(params);
//        goods_gallery.setColumnWidth(itemWidth);
//        goods_gallery.setHorizontalSpacing(10);
//        goods_gallery.setStretchMode(GridView.NO_STRETCH);
//        goods_gallery.setNumColumns(size);
//    }

    protected void setGoodImg(String pictures) {
        StringTokenizer st = new StringTokenizer(pictures, ",");
        while (st.hasMoreTokens()) {
            String i = st.nextToken();
            list2.add(i);
        }
        setAD(list2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class ImageAdapter extends BaseAdapter {
        private BitmapUtils bitmapUtils;
        private Context mContext;
        private List<String> good_img_list;

        public ImageAdapter(Context c, List<String> good_img_list) {
            mContext = c;
            this.good_img_list = good_img_list;
            bitmapUtils = new BitmapUtils(mContext);
        }

        public int getCount() {
            return good_img_list.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            ImageView textView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (good_img_list != null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.goods_img_item, parent, false);
                holder.textView = (ImageView) convertView
                        .findViewById(R.id.goods_img);
                bitmapUtils.display(holder.textView, good_img_list
                        .get(position).toString());
            }
            return convertView;
        }

    }

    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}
