package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.HomeTabActivity;
import com.aite.a.activity.li.activity.AroundActivity;
import com.aite.a.adapter.GoodsAdapter;
import com.aite.a.adapter.HomeNavigRvAdapter;
import com.aite.a.adapter.TwoImageAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.CustomHomeInfo;
import com.aite.a.model.EventBean;
import com.aite.a.model.NavigationInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyAdGallery.MyOnItemClickListener;
import com.aite.a.view.MyGridView;
import com.aite.a.view.RvItemClickListener;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lidroid.xutils.BitmapUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义首页
 *
 * @author Administrator
 */
public class CustomHomeActivity extends BaseActivity implements OnClickListener {
    private EditText et_search;
    // private RecyclerView rv_home;
    private LinearLayout  ll_pager;
    private View searchIv;
    private ImageView iv_location;
    private LinearLayout ll_right;
    private List<CustomHomeInfo> customHomeInfo;
    private List<NavigationInfo> navigationInfo, navigationInfo2;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case home_ad_id:// 首页数据
                    if (msg.obj != null) {
                        customHomeInfo = (List<CustomHomeInfo>) msg.obj;
                        addview(customHomeInfo);
                        // customHomeAdapter = new CustomHomeAdapter(
                        // CustomHomeActivity.this, customHomeInfo,
                        // bitmapUtils);
                        // rv_home.setAdapter(customHomeAdapter);
                    }
                    break;
                case home_ad_err:
                    Toast.makeText(CustomHomeActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };
    private TextView locationTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customhome);
        // 注册订阅者
        EventBus.getDefault().register(this);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_location=findViewById(R.id.iv_location);
        ll_right=findViewById(R.id.ll_right);
        et_search = (EditText) findViewById(R.id.et_search);
        // rv_home = (RecyclerView) findViewById(R.id.rv_home);
        searchIv =  findViewById(R.id.iv_search);
        ll_pager = (LinearLayout) findViewById(R.id.ll_pager);
        Log.i("----home-city", " " + APPSingleton.city);
        locationTv =  findViewById(R.id.home_location_tv);
        locationTv.setText(APPSingleton.city);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //, sticky = true
    public void onMessageEvent(EventBean event) {
//        Log.i(TAG, "message is " + event.getMessage());
        // 更新界面
        locationTv.setText(event.getCity());
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        searchIv.setOnClickListener(this);
        ll_right.setOnClickListener(this);
        // LinearLayoutManager linearLayoutManager = new
        // LinearLayoutManager(this);
        // rv_home.setLayoutManager(linearLayoutManager);
        // rv_home.setNestedScrollingEnabled(false);
        navigationInfo = new ArrayList<>();
        navigationInfo.add(new NavigationInfo(getString(R.string.signin), R.drawable.ico1));
//		navigationInfo.add(new NavigationInfo(getString(R.string.shequ),R.drawable.ico1));
        navigationInfo.add(new NavigationInfo(getString(R.string.distribution_center38), R.drawable.ico2));
        navigationInfo.add(new NavigationInfo(getString(R.string.footprint), R.drawable.ico3));
        navigationInfo.add(new NavigationInfo(getString(R.string.sundrying), R.drawable.ico4));
        navigationInfo.add(new NavigationInfo(getString(R.string.hotspot), R.drawable.ico5));
        navigationInfo.add(new NavigationInfo(getString(R.string.integrall), R.drawable.ico6));
        navigationInfo.add(new NavigationInfo(getString(R.string.distribution2), R.drawable.ico7));
        navigationInfo.add(new NavigationInfo(getString(R.string.getacoupon), R.drawable.ico8));
        navigationInfo.add(new NavigationInfo(getString(R.string.store_up), R.drawable.ico9));
        navigationInfo.add(new NavigationInfo(getString(R.string.typee), R.drawable.ico10));
        navigationInfo2 = new ArrayList<>();
        navigationInfo2.add(new NavigationInfo(getString(R.string.signin), R.drawable.ico1));
        et_search.setOnEditorActionListener(action);
        initData();
    }

    @Override
    protected void initData() {
        netRun.Intex();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_search:
//                search();
//                break;
//            case R.id.ll_right:
//                startActivity(new Intent(this, AroundActivity.class));
//                break;
//        }
        if(v.getId()==R.id.iv_search){
            search();
        }else if(v.getId()==R.id.ll_right){
            startActivity(new Intent(this, AroundActivity.class));
        }
    }

    private TextView.OnEditorActionListener action = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("--------------------", "搜索  " + textView.getText().toString());
                    search();
                }
                return true;
            }
            return false;
        }
    };


    private void search() {
        String key_wrods = et_search.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", key_wrods);
        if (!key_wrods.equals("")) {
            openActivity(GoodsListActivity.class, bundle);
        }
        et_search.setText("");
    }

    private void addview(List<CustomHomeInfo> customHomeInfo) {
        final int getw = getw();
        for (int i = 0; i < customHomeInfo.size(); i++) {
            final CustomHomeInfo custom = customHomeInfo.get(i);
            switch (custom.type) {
                case "adv_list":
                    LayoutInflater inflater = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView = inflater.inflate(
                            R.layout.item_custom_adv_list, null);
                    MyAdGallery adgallery = convertView
                            .findViewById(R.id.adgallery);
                    LinearLayout ovalLayout =  convertView
                            .findViewById(R.id.ovalLayout);
                    RelativeLayout rl_avditem =convertView
                            .findViewById(R.id.rl_avditem);

                    setAD(custom.adv_list.item, adgallery, ovalLayout);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rl_avditem
                            .getLayoutParams();
                    layoutParams.height = (int) (getw / 1.8); //原 1.82
                    rl_avditem.setLayoutParams(layoutParams);
                    ll_pager.addView(convertView);
                    break;
                case "navigation":
                    LayoutInflater inflater2 = LayoutInflater
                            .from(CustomHomeActivity.this);

                   /*  View convertView2 = inflater2.inflate(
                            R.layout.item_custom_navigation2, null);
                   ViewPager vp_navigation = convertView2.findViewById(R.id.vp_navigation);
                    View mPage = LayoutInflater.from(CustomHomeActivity.this).inflate(R.layout.item_custom_navigation, null);
//				View mPage2 = LayoutInflater.from(CustomHomeActivity.this).inflate(R.layout.item_custom_navigation, null);
                    MyGridView mlv_navigation = mPage.findViewById(R.id.mlv_navigation);
//				MyGridView mlv_navigation2 = mPage2.findViewById(R.id.mlv_navigation);
                    mlv_navigation.setAdapter(new NavigationActivity(CustomHomeActivity.this, navigationInfo));
//				mlv_navigation2.setAdapter(new NavigationActivity(CustomHomeActivity.this,navigationInfo2));
                    List<View> data = new ArrayList<>();
                    data.add(mPage);
//				data.add(mPage2);
                    HomeTopAdapter adapter = new HomeTopAdapter(CustomHomeActivity.this, data);
                    vp_navigation.setAdapter(adapter);
*/
                    View homeServiceNavig = inflater2.inflate(R.layout.home_service_navigation, null);
                    RecyclerView homeNavigRv = homeServiceNavig.findViewById(R.id.home_navig_rv);
                    homeNavigRv.setLayoutManager(new GridLayoutManager(CustomHomeActivity.this,2,RecyclerView.HORIZONTAL,false));
                    homeNavigRv.setAdapter(new HomeNavigRvAdapter (this,navigationInfo));
                    homeNavigRv.addOnItemTouchListener(new RvItemClickListener(homeNavigRv) {
                        @Override
                        public void itemSingleClick(RecyclerView.ViewHolder viewHolder, View itemView, int itemPosition) {
                            NavigationInfo navigationInfo = CustomHomeActivity.this.navigationInfo.get(itemPosition);
                            Log.i("----itemSingleClick", "pisition:"+itemPosition+"  "+navigationInfo.name+"  "+navigationInfo.img);
                            navigationItemClick(navigationInfo);
                        }

                        @Override
                        public void itemLongPress(RecyclerView.ViewHolder childViewHolder, View childView, int itemPosition) {
                            Log.i("----itemLongPress", " itemLongPress" );

                        }
                    });
                    ll_pager.addView(homeServiceNavig);
                    break;
                case "home1":           //首页顶部广告图
                    LayoutInflater inflater3 = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView3 = inflater3.inflate(
                            R.layout.item_custom_home1, null);
                    final ImageView iv_img = convertView3
                            .findViewById(R.id.iv_img);
                    // 获取图片真正的宽高
                    Glide.with(CustomHomeActivity.this).asBitmap()
                            .load(custom.home1.image)// 强制Glide返回一个Bitmap对象
                            .into(new SimpleTarget<Bitmap>() {

                                @Override
                                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> arg1) {
                                    float width = bitmap.getWidth();
                                    float height = bitmap.getHeight();
                                    float bl = height / width;   //使图片的宽高始终适配手机的宽
                                    float he = getw * bl;
                                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_img
                                            .getLayoutParams();
                                    layoutParams.width = getw;
                                    layoutParams.height = (int) (he);
                                    iv_img.setLayoutParams(layoutParams);
                                    Glide.with(CustomHomeActivity.this)
                                            .load(custom.home1.image).into(iv_img);
                                }
                            });
                    iv_img.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (custom.home1.data != null
                                    && custom.home1.data.length() != 0) {
                                Intent intent6 = new Intent(
                                        CustomHomeActivity.this, WebActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", custom.home1.data);
                                bundle.putString("title", custom.home1.title);
                                intent6.putExtras(bundle);
                                startActivity(intent6);
                            }

                        }
                    });
                    ll_pager.addView(convertView3);
                    break;
                case "home2":
                    LayoutInflater inflater4 = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView4 = inflater4.inflate(
                            R.layout.item_custom_home2, null);
                    ImageView iv_img1 =  convertView4
                            .findViewById(R.id.iv_img1);
                    ImageView iv_img2 = convertView4
                            .findViewById(R.id.iv_img2);
                    ImageView iv_img3 =  convertView4
                            .findViewById(R.id.iv_img3);
                    LinearLayout ll_home2item = convertView4
                            .findViewById(R.id.ll_home2item);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) ll_home2item.getLayoutParams();
                    layoutParams2.height = (int) (getw / 2.46);
                    ll_home2item.setLayoutParams(layoutParams2);
                    Glide.with(CustomHomeActivity.this)
                            .load(custom.home2.square_image).into(iv_img1);
                    Glide.with(CustomHomeActivity.this)
                            .load(custom.home2.rectangle1_image).into(iv_img2);
                    Glide.with(CustomHomeActivity.this)
                            .load(custom.home2.rectangle2_image).into(iv_img3);
                    iv_img1.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (custom.home2.square_data != null
                                    && custom.home2.square_data.length() != 0) {
                                Intent intent6 = new Intent(
                                        CustomHomeActivity.this, WebActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", custom.home2.square_data);
                                bundle.putString("title", custom.home2.title);
                                intent6.putExtras(bundle);
                                startActivity(intent6);
                            }
                        }
                    });
                    iv_img2.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (custom.home2.rectangle1_data != null
                                    && custom.home2.rectangle1_data.length() != 0) {
                                Intent intent6 = new Intent(
                                        CustomHomeActivity.this, WebActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url",
                                        custom.home2.rectangle1_data);
                                bundle.putString("title", custom.home2.title);
                                intent6.putExtras(bundle);
                                startActivity(intent6);
                            }
                        }
                    });
                    iv_img3.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (custom.home2.rectangle2_data != null
                                    && custom.home2.rectangle2_data.length() != 0) {
                                Intent intent6 = new Intent(
                                        CustomHomeActivity.this, WebActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url",
                                        custom.home2.rectangle2_data);
                                bundle.putString("title", custom.home2.title);
                                intent6.putExtras(bundle);
                                startActivity(intent6);
                            }
                        }
                    });
                    ll_pager.addView(convertView4);
                    break;
                case "home3":
                    LayoutInflater inflater5 = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView5 = inflater5.inflate(
                            R.layout.item_custom_home3, null);
                    MyGridView gv_img = convertView5
                            .findViewById(R.id.gv_img);

                    TwoImageAdapter TwoImage = new TwoImageAdapter(
                            custom.home3.item, CustomHomeActivity.this, getw);
                    gv_img.setAdapter(TwoImage);
                    ll_pager.addView(convertView5);
                    break;
                case "home4":
                    LayoutInflater inflater6 = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView6 = inflater6.inflate(
                            R.layout.item_custom_home4, null);
                    ImageView iv_img4 =  convertView6
                            .findViewById(R.id.iv_img4);
                    ImageView iv_img5 =  convertView6
                            .findViewById(R.id.iv_img5);
                    ImageView iv_img6 =  convertView6
                            .findViewById(R.id.iv_img6);
                    LinearLayout ll_home4 =  convertView6
                            .findViewById(R.id.ll_home4);
                    LinearLayout ll_home4item =convertView6
                            .findViewById(R.id.ll_home4item);
                    LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) ll_home4item.getLayoutParams();
                    layoutParams4.height = (int) (getw / 2.46);
                    ll_home4item.setLayoutParams(layoutParams4);

                    Glide.with(CustomHomeActivity.this)
                            .load(custom.home4.rectangle1_image).into(iv_img4);
                    Glide.with(CustomHomeActivity.this)
                            .load(custom.home4.rectangle2_image).into(iv_img5);
                    Glide.with(CustomHomeActivity.this)
                            .load(custom.home4.square_image).into(iv_img6);
                    iv_img4.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (custom.home4.rectangle1_data != null
                                    && custom.home4.rectangle1_data.length() != 0) {
                                Intent intent6 = new Intent(
                                        CustomHomeActivity.this, WebActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url",
                                        custom.home4.rectangle1_data);
                                bundle.putString("title", custom.home4.title);
                                intent6.putExtras(bundle);
                                startActivity(intent6);
                            }
                        }
                    });
                    iv_img5.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (custom.home4.rectangle2_data != null
                                    && custom.home4.rectangle2_data.length() != 0) {
                                Intent intent6 = new Intent(
                                        CustomHomeActivity.this, WebActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url",
                                        custom.home4.rectangle2_data);
                                bundle.putString("title", custom.home4.title);
                                intent6.putExtras(bundle);
                                startActivity(intent6);
                            }
                        }
                    });
                    iv_img6.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (custom.home4.square_data != null
                                    && custom.home4.square_data.length() != 0) {
                                Intent intent6 = new Intent(
                                        CustomHomeActivity.this, WebActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", custom.home4.square_data);
                                bundle.putString("title", custom.home4.title);
                                intent6.putExtras(bundle);
                                startActivity(intent6);
                            }
                        }
                    });
                    ll_pager.addView(convertView6);
                    break;
                case "goods":
                    LayoutInflater inflater7 = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView7 = inflater7.inflate(
                            R.layout.item_custom_goods, null);
                    MyGridView mlv_goodslist =  convertView7
                            .findViewById(R.id.mlv_goodslist);
                    GoodsAdapter goods = new GoodsAdapter(custom.goods.item,
                            CustomHomeActivity.this);
                    mlv_goodslist.setAdapter(goods);
                    ll_pager.addView(convertView7);
                    break;
                case "home5":
                    LayoutInflater inflater8 = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView8 = inflater8.inflate(
                            R.layout.item_custom_home5, null);
                    ImageView iv_home5t1 = convertView8
                            .findViewById(R.id.iv_home5t1);
                    ImageView iv_home5t2 =  convertView8
                            .findViewById(R.id.iv_home5t2);
                    ImageView iv_home5t3 =  convertView8
                            .findViewById(R.id.iv_home5t3);
                    final LinearLayout ll_home5item = convertView8
                            .findViewById(R.id.ll_home5item);
                    // 获取图片真正的宽高
                    if (custom.home5.item != null) {
                        if (custom.home5.item.size() > 0) {
                            Glide.with(CustomHomeActivity.this).asBitmap()
                                    .load(custom.home5.item.get(0).image)// 强制Glide返回一个Bitmap对象
                                    .into(new SimpleTarget<Bitmap>() {

                                        @Override
                                        public void onResourceReady(Bitmap bitmap,
                                                                    Transition<? super Bitmap> arg1) {
                                            float width = bitmap.getWidth();
                                            float height = bitmap.getHeight();
                                            float bl = height / width;
                                            float he = (getw / 3) * bl;
                                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_home5item
                                                    .getLayoutParams();
                                            layoutParams.height = (int) (he);
                                            ll_home5item.setLayoutParams(layoutParams);
                                        }
                                    });
                            Glide.with(CustomHomeActivity.this)
                                    .load(custom.home5.item.get(0).image).into(iv_home5t1);
                            iv_home5t1.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    if (custom.home5.item.get(0).data != null
                                            && custom.home5.item.get(0).data.length() != 0) {
                                        Intent intent6 = new Intent(
                                                CustomHomeActivity.this, WebActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("url", custom.home5.item.get(0).data);
                                        bundle.putString("title", custom.home5.item.get(0).type);
                                        intent6.putExtras(bundle);
                                        startActivity(intent6);
                                    }
                                }
                            });
                        }
                        if (custom.home5.item.size() > 1) {
                            Glide.with(CustomHomeActivity.this)
                                    .load(custom.home5.item.get(1).image).into(iv_home5t2);
                            iv_home5t2.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    if (custom.home5.item.get(1).data != null
                                            && custom.home5.item.get(1).data.length() != 0) {
                                        Intent intent6 = new Intent(
                                                CustomHomeActivity.this, WebActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("url", custom.home5.item.get(1).data);
                                        bundle.putString("title", custom.home5.item.get(1).type);
                                        intent6.putExtras(bundle);
                                        startActivity(intent6);
                                    }
                                }
                            });
                        }
                        if (custom.home5.item.size() > 2) {
                            Glide.with(CustomHomeActivity.this)
                                    .load(custom.home5.item.get(2).image).into(iv_home5t3);
                            iv_home5t3.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    if (custom.home5.item.get(2).data != null
                                            && custom.home5.item.get(2).data.length() != 0) {
                                        Intent intent6 = new Intent(
                                                CustomHomeActivity.this, WebActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("url", custom.home5.item.get(2).data);
                                        bundle.putString("title", custom.home5.item.get(2).type);
                                        intent6.putExtras(bundle);
                                        startActivity(intent6);
                                    }
                                }
                            });
                        }
                    }
                    ll_pager.addView(convertView8);
                    break;
                case "home6":
                    LayoutInflater inflater10 = LayoutInflater
                            .from(CustomHomeActivity.this);
                    View convertView10 = inflater10.inflate(
                            R.layout.item_custom_home6, null);
                    ImageView iv_home6t1 = convertView10
                            .findViewById(R.id.iv_home6t1);
                    ImageView iv_home6t2 =  convertView10
                            .findViewById(R.id.iv_home6t2);
                    ImageView iv_home6t3 = convertView10
                            .findViewById(R.id.iv_home6t3);
                    ImageView iv_home6t4 =  convertView10
                            .findViewById(R.id.iv_home6t4);
                    final LinearLayout ll_home6item =  convertView10
                            .findViewById(R.id.ll_home6item);
                    if (custom.home6.item != null) {
                        if (custom.home6.item.size() > 0) {
                            // 获取图片真正的宽高
                            Glide.with(CustomHomeActivity.this).asBitmap()
                                    .load(custom.home6.item.get(0).image)// 强制Glide返回一个Bitmap对象
                                    .into(new SimpleTarget<Bitmap>() {

                                        @Override
                                        public void onResourceReady(Bitmap bitmap,
                                                                    Transition<? super Bitmap> arg1) {
                                            float width = bitmap.getWidth();
                                            float height = bitmap.getHeight();
                                            float bl = height / width;
                                            float he = (getw / 4) * bl;
                                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_home6item
                                                    .getLayoutParams();
                                            layoutParams.height = (int) (he);
                                            ll_home6item.setLayoutParams(layoutParams);
                                        }
                                    });
                            Glide.with(CustomHomeActivity.this)
                                    .load(custom.home6.item.get(0).image).into(iv_home6t1);
                            iv_home6t1.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent6 = new Intent(CustomHomeActivity.this,
                                            WebActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url", custom.home6.item.get(0).data);
                                    bundle.putString("title", custom.home6.title);
                                    intent6.putExtras(bundle);
                                    startActivity(intent6);
                                }
                            });
                        }
                        if (custom.home6.item.size() > 1) {
                            Glide.with(CustomHomeActivity.this)
                                    .load(custom.home6.item.get(1).image).into(iv_home6t2);
                            iv_home6t2.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent6 = new Intent(CustomHomeActivity.this,
                                            WebActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url",custom.home6.item.get(1).data);
                                    bundle.putString("title", custom.home6.title);
                                    intent6.putExtras(bundle);
                                    startActivity(intent6);
                                }
                            });
                        }
                        if (custom.home6.item.size() > 2) {
                            Glide.with(CustomHomeActivity.this)
                                    .load(custom.home6.item.get(2).image).into(iv_home6t3);
                            iv_home6t3.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent6 = new Intent(CustomHomeActivity.this,
                                            WebActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url", custom.home6.item.get(2).data);
                                    bundle.putString("title", custom.home6.title);
                                    intent6.putExtras(bundle);
                                    startActivity(intent6);
                                }
                            });
                        }
                        if (custom.home6.item.size() > 3) {
                            Glide.with(CustomHomeActivity.this)
                                    .load(custom.home6.item.get(3).image).into(iv_home6t4);
                            iv_home6t4.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent6 = new Intent(CustomHomeActivity.this,
                                            WebActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url", custom.home6.item.get(3).data);
                                    bundle.putString("title", custom.home6.title);
                                    intent6.putExtras(bundle);
                                    startActivity(intent6);
                                }
                            });
                        }
                    }
                    ll_pager.addView(convertView10);
                    break;
            }
        }

    }

    private void navigationItemClick(NavigationInfo data) {
        // 导航
        if (data.name.equals(this.getString(R.string.signin))) {//签到
            if (Mark.State.UserKey != null) {
                Intent intent26 = new Intent(this, MyCalendarActivity.class);
                this.startActivity(intent26);
            } else {
                Toast.makeText(this, this.getString(R.string.not_login_please_login),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (data.name.equals(this.getString(R.string.distribution_center38))) {//圈子
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", "https://aitecc.com/wap/index.php?act=circle&recommend=1");
            this.startActivity(intent);
        }else if (data.name.equals(this.getString(R.string.shequ))) {//社区
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", "https://aitecc.com/wap/index.php?act=circle");
            this.startActivity(intent);
        } else if (data.name.equals(this.getString(R.string.footprint))) {//足迹
            if (Mark.State.UserKey != null) {
                Intent zuji = new Intent(this,
                        MyfootprintActivity.class);
                zuji.putExtra("person_in", "2");
                this.startActivity(zuji);
            } else {
                Toast.makeText(this, this.getString(R.string.not_login_please_login),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (data.name.equals(this.getString(R.string.sundrying))) {//晒单
            Intent fxintent = new Intent(this, WebActivity.class);
            fxintent.putExtra("url", "http://aitecc.com/wap/index.php?act=weifaxian");
            this.startActivity(fxintent);
        } else if (data.name.equals(this.getString(R.string.hotspot))) {//热点
            Intent rdintent = new Intent(this, WebActivity.class);
            rdintent.putExtra("url", "http://aitecc.com/wap/index.php?act=news");
            this.startActivity(rdintent);
        } else if (data.name.equals(this.getString(R.string.integrall))) {//积分
            if (Mark.State.UserKey == null) {
                Toast.makeText(this, this
                                .getString(R.string.not_login_please_login),
                        Toast.LENGTH_SHORT).show();
            } else {
                Intent intent4 = new Intent(this,
                        IntegralInfoActivity.class);
                intent4.putExtra("person_in", "1");
                this.startActivity(intent4);
            }
        } else if (data.name.equals(this.getString(R.string.message))) {//消息

        } else if (data.name.equals(this.getString(R.string.getacoupon))) {//领券
            Intent intent6 = new Intent(this,
                    HotVouchersListActivity.class);
            this.startActivity(intent6);
        } else if (data.name.equals(this.getString(R.string.store_up))) {//收藏
            if (Mark.State.UserKey == null) {
                Toast.makeText(this, this
                                .getString(R.string.not_login_please_login),
                        Toast.LENGTH_SHORT).show();
            } else {
                Intent intent7 = new Intent(this,
                        FavoriteListFargmentActivity.class);
                intent7.putExtra("i", 1);
                this.startActivity(intent7);
            }
        } else if (data.name.equals(this.getString(R.string.tab_category))) {//分类
            HomeTabActivity.categoryBtn.performClick();
        } else if (data.name.equals(this.getString(R.string.exchange))) {//兑换
            Intent intent9 = new Intent(this,
                    IntegralShopActivity.class);
            intent9.putExtra("person_in", "1");
            this.startActivity(intent9);
        } else if (data.name.equals(this.getString(R.string.distribution2))) {//分销
            if (Mark.State.UserKey == null) {
                Toast.makeText(this, this
                                .getString(R.string.not_login_please_login),
                        Toast.LENGTH_SHORT).show();
            } else {
                Intent intent16 = new Intent(this,
                        DistributionCenterActivity.class);
                this.startActivity(intent16);
            }
        } else if (data.name.equals(this.getString(R.string.sundrying))) {//晒单
            Intent rdintent11 = new Intent(this, WebActivity.class);
            rdintent11.putExtra("url", "http://aitecc.com/wap/index.php?act=weifaxian");
            this.startActivity(rdintent11);
        }
    }

    /**
     * 设置广告轮播
     *
     * @param obj
     */
    protected void setAD(final List<CustomHomeInfo.adv_list.item> obj,
                         MyAdGallery adgallery, LinearLayout ovalLayout) {
        List<String> listAd = new ArrayList<String>();
        for (CustomHomeInfo.adv_list.item string : obj) {
            listAd.add(string.image);
        }
        String[] ADurl = listAd.toArray(new String[listAd.size()]);
        if (adgallery.mUris == null)
            adgallery.start(CustomHomeActivity.this, ADurl, null, 3000,
                    ovalLayout, R.drawable.dot_focused, R.drawable.dot_normal);
        adgallery.setMyOnItemClickListener(new MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                if (obj.get(curIndex).data != null
                        && obj.get(curIndex).data.length() != 0) {
                    Intent intent = new Intent(CustomHomeActivity.this,
                            WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", obj.get(curIndex).data);
                    bundle.putString("title", obj.get(curIndex).type);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private int getw() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
