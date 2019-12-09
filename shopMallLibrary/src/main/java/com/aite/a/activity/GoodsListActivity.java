package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.AttrAdapter;
import com.aite.a.adapter.GoodsList2Adapter;
import com.aite.a.adapter.GoodsListAdapter;
import com.aite.a.adapter.PpAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.GoodsListModerInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.GoodsSortingPopu;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableGridView;
import com.aiteshangcheng.a.R;

import androidx.drawerlayout.widget.DrawerLayout;

/**
 * 商品列表
 * Created by Administrator on 2018/1/25.
 */
public class GoodsListActivity extends BaseActivity implements View.OnClickListener {
    private NetRun netRun;
    private ImageView iv_break, iv_type;
    private TextView tv_name, tv_comprehensive, tv_sales, tv_screening;
    private PullToRefreshLayout refresh_view;
    private PullableGridView lv_list;
    private LinearLayout ll_null;
    private DrawerLayout dl;
    /**
     * 侧边栏
     **/
    private RelativeLayout rl_screen;
    private ImageView iv_close;
    private TextView tv_reset, tv_zp, tv_qg, tv_xs, tv_xn, tv_storetype, tv_determine;
    private EditText et_minimum, et_highest;
    private LinearLayout ll_pp;
    private MyGridView gb_pp;
    private MyListView lv_attr;

    private int refreshtype = 0, curpage = 1, layouttype = 1, chooseid = 1;
    private MyListener myListenr;//刷新监听
    private String key = "0", order = "0", page = "20", gc_id, store_id, gc_name, b_id, own_shop = "0", gift = "0", groupbuy = "0", xianshi = "0", virtual = "0", a_id;//筛选条件
    public String keyword = "";
    private GoodsListModerInfo goodsListModerInfo;//商品列表类
    private GoodsListAdapter goodsListAdapter;//垂直
    private GoodsList2Adapter goodsList2Adapter;//横向
    private AttrAdapter attrAdapter;//额外属性
    private PpAdapter ppAdapter;//品牌属性
    private boolean isloadattr = true;//是否加载筛选条件

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case goods_list_id://商品列表
                    if (msg.obj != null) {
                        goodsListModerInfo = (GoodsListModerInfo) msg.obj;
                        if (isloadattr) {
                            if (goodsListModerInfo.datas.brand_list != null && goodsListModerInfo.datas.brand_list.size() != 0) {//是否显示品牌
                                ll_pp.setVisibility(View.VISIBLE);
                                ppAdapter = new PpAdapter(GoodsListActivity.this, goodsListModerInfo.datas.brand_list);
                                gb_pp.setAdapter(ppAdapter);
                            } else {
                                ll_pp.setVisibility(View.GONE);
                            }
                            if (goodsListModerInfo.datas.attr_list != null && goodsListModerInfo.datas.attr_list.size() != 0) {//是否显示额外属性
                                lv_attr.setVisibility(View.VISIBLE);
                                attrAdapter = new AttrAdapter(GoodsListActivity.this, goodsListModerInfo.datas.attr_list);
                                lv_attr.setAdapter(attrAdapter);
                            } else {
                                lv_attr.setVisibility(View.GONE);
                            }
                            isloadattr = false;
                        }

                        if (goodsListModerInfo.datas.goods_list == null || goodsListModerInfo.datas.goods_list.size() == 0) {//是否显示商品列表
                            if (refreshtype == 2) {
                                Toast.makeText(GoodsListActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                                myListenr.loadMoreSucceed();
                            } else
                                ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || goodsListAdapter == null || goodsList2Adapter == null) {
                                goodsListAdapter = new GoodsListAdapter(GoodsListActivity.this, goodsListModerInfo.datas.goods_list);
                                goodsList2Adapter = new GoodsList2Adapter(GoodsListActivity.this, goodsListModerInfo.datas.goods_list);
                                lv_list.setAdapter(goodsList2Adapter);
                            } else if (refreshtype == 1) {
//                                if (layouttype==0){
                                goodsListAdapter.refreshData(goodsListModerInfo.datas.goods_list);
//                                }else {
                                goodsList2Adapter.refreshData(goodsListModerInfo.datas.goods_list);
//                                }
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) { //加载更多
//                                if (layouttype==0){
                                goodsListAdapter.moreData(goodsListModerInfo.datas.goods_list);
//                                }else {
                                goodsList2Adapter.moreData(goodsListModerInfo.datas.goods_list);
//                                }
                                myListenr.loadMoreSucceed();
                            }
                        }
                    } else {
                        if (refreshtype == 1) {
                            myListenr.refreshSucceed();
                        } else if (refreshtype == 2) {   //加载更多
                            myListenr.loadMoreSucceed();
                        }
                        Toast.makeText(GoodsListActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case goods_list_err:
                    Toast.makeText(GoodsListActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodslist);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_break = (ImageView) findViewById(R.id.iv_break);
        iv_type = (ImageView) findViewById(R.id.iv_type);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_comprehensive = (TextView) findViewById(R.id.tv_comprehensive);
        tv_sales = (TextView) findViewById(R.id.tv_sales);
        tv_screening = (TextView) findViewById(R.id.tv_screening);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_list = (PullableGridView) findViewById(R.id.lv_list);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        dl = (DrawerLayout) findViewById(R.id.dl);
        //侧边栏
        rl_screen = (RelativeLayout) findViewById(R.id.rl_screen);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        tv_reset = (TextView) findViewById(R.id.tv_reset);
        tv_zp = (TextView) findViewById(R.id.tv_zp);
        tv_qg = (TextView) findViewById(R.id.tv_qg);
        tv_xs = (TextView) findViewById(R.id.tv_xs);
        tv_xn = (TextView) findViewById(R.id.tv_xn);
        tv_storetype = (TextView) findViewById(R.id.tv_storetype);
        tv_determine = (TextView) findViewById(R.id.tv_determine);
        et_minimum = (EditText) findViewById(R.id.et_minimum);
        et_highest = (EditText) findViewById(R.id.et_highest);
        ll_pp = (LinearLayout) findViewById(R.id.ll_pp);
        lv_attr = (MyListView) findViewById(R.id.lv_attr);
        gb_pp = (MyGridView) findViewById(R.id.gb_pp);
        initView();
    }

    @Override
    protected void initView() {
        tv_zp.setOnClickListener(this);
        tv_qg.setOnClickListener(this);
        tv_xs.setOnClickListener(this);
        tv_xn.setOnClickListener(this);
        tv_storetype.setOnClickListener(this);
        tv_determine.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        iv_break.setOnClickListener(this);
        iv_type.setOnClickListener(this);
        tv_comprehensive.setOnClickListener(this);
        tv_sales.setOnClickListener(this);
        tv_screening.setOnClickListener(this);

        gc_id = getIntent().getStringExtra("gc_id");
        b_id = getIntent().getStringExtra("b_id");
        keyword = getIntent().getStringExtra("keyword");
        store_id = getIntent().getStringExtra("store_id");
        gc_name = getIntent().getStringExtra("gc_name");
        if (gc_name != null && gc_name.length() != 0) {
            tv_name.setText(gc_name);

        } else if (keyword != null && keyword.length() != 0) {
            tv_name.setText(keyword);
        } else {
            tv_name.setText(getString(R.string.goods_list));
        }

        netRun = new NetRun(this, handler);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        initData();
    }


    @Override
    protected void initData() {
        netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, null, null);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_break) {
            finish();
        } else if (v.getId() == R.id.tv_comprehensive) {
            //综合排序
            showpopu(chooseid);
        } else if (v.getId() == R.id.tv_sales) {
            //销量优先
            key = "1";
            order = "2";
            chooseid = 0;
            tv_sales.setTextColor(0xffF44848);
            tv_comprehensive.setText(getString(R.string.join_merchant9));
            tv_comprehensive.setTextColor(0xff808080);
            refreshtype = 1;
            curpage = 1;
            String price_from = et_minimum.getText().toString();
            String price_to = et_highest.getText().toString();
            netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, price_from, price_to);
        } else if (v.getId() == R.id.tv_screening) {
            //筛选
            dl.openDrawer(Gravity.RIGHT);
        } else if (v.getId() == R.id.iv_type) {
            //切换
            setLayoutType();
        } else if (v.getId() == R.id.iv_close) {
            //关闭
            dl.closeDrawers();
        } else if (v.getId() == R.id.tv_reset) {
            //重置
            reset();
        } else if (v.getId() == R.id.tv_zp) {
            //赠品
            if (gift.equals("0")) {
                gift = "1";
                tv_zp.setBackgroundResource(R.drawable.goods_screen3);
                tv_zp.setTextColor(0xffffffff);
            } else {
                gift = "0";
                tv_zp.setBackgroundResource(R.drawable.goods_screen2);
                tv_zp.setTextColor(0xff808080);
            }
        } else if (v.getId() == R.id.tv_qg) {
            //抢购
            if (groupbuy.equals("0")) {
                groupbuy = "1";
                tv_qg.setBackgroundResource(R.drawable.goods_screen3);
                tv_qg.setTextColor(0xffffffff);
            } else {
                groupbuy = "0";
                tv_qg.setBackgroundResource(R.drawable.goods_screen2);
                tv_qg.setTextColor(0xff808080);
            }
        } else if (v.getId() == R.id.tv_xs) {
            //限时折扣
            if (xianshi.equals("0")) {
                xianshi = "1";
                tv_xs.setBackgroundResource(R.drawable.goods_screen3);
                tv_xs.setTextColor(0xffffffff);
            } else {
                xianshi = "0";
                tv_xs.setBackgroundResource(R.drawable.goods_screen2);
                tv_xs.setTextColor(0xff808080);
            }
        } else if (v.getId() == R.id.tv_xn) {
            //虚拟
            if (virtual.equals("0")) {
                virtual = "1";
                tv_xn.setBackgroundResource(R.drawable.goods_screen3);
                tv_xn.setTextColor(0xffffffff);
            } else {
                virtual = "0";
                tv_xn.setBackgroundResource(R.drawable.goods_screen2);
                tv_xn.setTextColor(0xff808080);
            }
        } else if (v.getId() == R.id.tv_storetype) {
            //是否自营
            if (own_shop.equals("0")) {
                own_shop = "1";
                tv_storetype.setBackgroundResource(R.drawable.goods_screen3);
                tv_storetype.setTextColor(0xffffffff);
            } else {
                own_shop = "0";
                tv_storetype.setBackgroundResource(R.drawable.goods_screen2);
                tv_storetype.setTextColor(0xff808080);
            }
        } else if (v.getId() == R.id.tv_determine) {
            //确定
            dl.closeDrawers();
            if (ppAdapter != null) {
                b_id = ppAdapter.getb_id();
            } else {
                b_id = "";
            }
            if (attrAdapter != null) {
                a_id = attrAdapter.geta_id();
            } else {
                a_id = "";
            }
            refreshtype = 1;
            curpage = 1;
            netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, et_minimum.getText().toString(), et_highest.getText().toString());
        }

//        switch (v.getId()) {
//            case R.id.iv_break:
//                finish();
//                break;
//            case R.id.tv_comprehensive:
//                //综合排序
//                showpopu(chooseid);
//                break;
//            case R.id.tv_sales:
//                //销量优先
//                key = "1";
//                order = "2";
//                chooseid = 0;
//                tv_sales.setTextColor(0xffF44848);
//                tv_comprehensive.setText(getString(R.string.join_merchant9));
//                tv_comprehensive.setTextColor(0xff808080);
//                refreshtype = 1;
//                curpage = 1;
//                String price_from = et_minimum.getText().toString();
//                String price_to = et_highest.getText().toString();
//                netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, price_from, price_to);
//                break;
//            case R.id.tv_screening:
//                //筛选
//                dl.openDrawer(Gravity.RIGHT);
//                break;
//            case R.id.iv_type:
//                //切换
//                setLayoutType();
//                break;
//            case R.id.iv_close:
//                //关闭
//                dl.closeDrawers();
//                break;
//            case R.id.tv_reset:
//                //重置
//                reset();
//                break;
//            case R.id.tv_zp:
//                //赠品
//                if (gift.equals("0")) {
//                    gift = "1";
//                    tv_zp.setBackgroundResource(R.drawable.goods_screen3);
//                    tv_zp.setTextColor(0xffffffff);
//                } else {
//                    gift = "0";
//                    tv_zp.setBackgroundResource(R.drawable.goods_screen2);
//                    tv_zp.setTextColor(0xff808080);
//                }
//                break;
//            case R.id.tv_qg:
//                //抢购
//                if (groupbuy.equals("0")) {
//                    groupbuy = "1";
//                    tv_qg.setBackgroundResource(R.drawable.goods_screen3);
//                    tv_qg.setTextColor(0xffffffff);
//                } else {
//                    groupbuy = "0";
//                    tv_qg.setBackgroundResource(R.drawable.goods_screen2);
//                    tv_qg.setTextColor(0xff808080);
//                }
//                break;
//            case R.id.tv_xs:
//                //限时折扣
//                if (xianshi.equals("0")) {
//                    xianshi = "1";
//                    tv_xs.setBackgroundResource(R.drawable.goods_screen3);
//                    tv_xs.setTextColor(0xffffffff);
//                } else {
//                    xianshi = "0";
//                    tv_xs.setBackgroundResource(R.drawable.goods_screen2);
//                    tv_xs.setTextColor(0xff808080);
//                }
//                break;
//            case R.id.tv_xn:
//                //虚拟
//                if (virtual.equals("0")) {
//                    virtual = "1";
//                    tv_xn.setBackgroundResource(R.drawable.goods_screen3);
//                    tv_xn.setTextColor(0xffffffff);
//                } else {
//                    virtual = "0";
//                    tv_xn.setBackgroundResource(R.drawable.goods_screen2);
//                    tv_xn.setTextColor(0xff808080);
//                }
//                break;
//            case R.id.tv_storetype:
//                //是否自营
//                if (own_shop.equals("0")) {
//                    own_shop = "1";
//                    tv_storetype.setBackgroundResource(R.drawable.goods_screen3);
//                    tv_storetype.setTextColor(0xffffffff);
//                } else {
//                    own_shop = "0";
//                    tv_storetype.setBackgroundResource(R.drawable.goods_screen2);
//                    tv_storetype.setTextColor(0xff808080);
//                }
//                break;
//            case R.id.tv_determine:
//                //确定
//                dl.closeDrawers();
//                if (ppAdapter != null) {
//                    b_id = ppAdapter.getb_id();
//                } else {
//                    b_id = "";
//                }
//                if (attrAdapter != null) {
//                    a_id = attrAdapter.geta_id();
//                } else {
//                    a_id = "";
//                }
//                refreshtype = 1;
//                curpage = 1;
//                netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, et_minimum.getText().toString(), et_highest.getText().toString());
//                break;
//        }
    }

    /**
     * 弹出排序
     *
     * @param choose
     */
    private void showpopu(int choose) {
        GoodsSortingPopu goodsSortingPopu = new GoodsSortingPopu(GoodsListActivity.this, choose);
        goodsSortingPopu.setmenu(new GoodsSortingPopu.menu() {
            @Override
            public void onItemClick(String type) {
                switch (type) {
                    case "0":
                        key = "0";
                        order = "0";
                        chooseid = 1;
                        tv_comprehensive.setText(getString(R.string.join_merchant9));
                        break;
                    case "1":
                        key = "3";
                        order = "2";
                        chooseid = 2;
                        tv_comprehensive.setText(getString(R.string.join_merchant13));
                        break;
                    case "2":
                        key = "3";
                        order = "1";
                        chooseid = 3;
                        tv_comprehensive.setText(getString(R.string.join_merchant14));
                        break;
                    case "3":
                        key = "2";
                        order = "2";
                        chooseid = 4;
                        tv_comprehensive.setText(getString(R.string.join_merchant15));
                        break;
                }
                tv_sales.setTextColor(0xff808080);
                tv_comprehensive.setTextColor(0xffF44848);
                refreshtype = 1;
                curpage = 1;
                String price_from = et_minimum.getText().toString();
                String price_to = et_highest.getText().toString();
                netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, price_from, price_to);
            }
        });
        goodsSortingPopu.showAsDropDown(tv_comprehensive, 0, 0);
    }

    /**
     * 重置
     */
    private void reset() {
        b_id = null;
        own_shop = "0";
        gift = "0";
        groupbuy = "0";
        xianshi = "0";
        virtual = "0";
        a_id = null;
        et_minimum.setText("");
        et_highest.setText("");
        tv_zp.setBackgroundResource(R.drawable.goods_screen2);
        tv_qg.setBackgroundResource(R.drawable.goods_screen2);
        tv_xs.setBackgroundResource(R.drawable.goods_screen2);
        tv_xn.setBackgroundResource(R.drawable.goods_screen2);
        tv_storetype.setBackgroundResource(R.drawable.goods_screen2);
        tv_zp.setTextColor(0xff808080);
        tv_qg.setTextColor(0xff808080);
        tv_xs.setTextColor(0xff808080);
        tv_xn.setTextColor(0xff808080);
        tv_storetype.setTextColor(0xff808080);
        if (ppAdapter != null) {
            ppAdapter.reset();
        }
        if (attrAdapter != null) {
            attrAdapter.reset();
        }
        netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, et_minimum.getText().toString(), et_highest.getText().toString());
        dl.closeDrawers();
    }

    /**
     * 修改列表布局类型
     */
    private void setLayoutType() {
        if (lv_list == null) return;
        if (layouttype == 0) {
            iv_type.setImageResource(R.drawable.goods_listtype2);
            layouttype = 1;
            lv_list.setNumColumns(2);
            lv_list.setAdapter(goodsList2Adapter);
        } else {
            iv_type.setImageResource(R.drawable.goods_listtype1);
            layouttype = 0;
            lv_list.setNumColumns(1);
            lv_list.setAdapter(goodsListAdapter);
        }
    }

    /**
     * 下拉监听
     */
    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        private PullToRefreshLayout pullToRefreshLayout;

        private Handler refreshHandler = new Handler() {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case pull_down_refresh_success:// 下拉刷新成功
                        if (pullToRefreshLayout != null) {
                            pullToRefreshLayout
                                    .refreshFinish(PullToRefreshLayout.SUCCEED);
                        }
                        break;
                    case pull_up_loaded_success:// 上拉加载成功
                        if (pullToRefreshLayout != null) {
                            pullToRefreshLayout
                                    .loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        }
                        break;
                }
            }
        };

        /**
         * 下拉式刷新成功
         */
        public void refreshSucceed() {
            refreshHandler.sendEmptyMessageDelayed(pull_down_refresh_success,
                    400);
        }

        /**
         * 上拉加载成功
         */
        public void loadMoreSucceed() {
            refreshHandler.sendEmptyMessageDelayed(pull_up_loaded_success, 400);
        }

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            refreshtype = 1;  //刷新界面
            curpage = 1;
            String price_from = et_minimum.getText().toString();
            String price_to = et_highest.getText().toString();
            netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, price_from, price_to);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (goodsListModerInfo.hasmore != null && goodsListModerInfo.hasmore.equals("true")) {
                refreshtype = 2;   //表示加载更多
                curpage++;
                String price_from = et_minimum.getText().toString();
                String price_to = et_highest.getText().toString();
                netRun.GoodsList(key, order, page, curpage + "", gc_id, store_id, keyword, b_id, own_shop, gift, groupbuy, xianshi, virtual, a_id, price_from, price_to);
            } else {
                loadMoreSucceed();
                Toast.makeText(GoodsListActivity.this,
                        getString(R.string.act_no_data_load),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


}
