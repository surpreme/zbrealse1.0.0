package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.RefundInfo;
import com.aite.a.model.RefundInfo.datas.refund_list;
import com.aite.a.model.SalesReturnInfo;
import com.aite.a.model.SalesReturnInfo.datas.return_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 退款退货
 *
 * @author Administrator
 */
public class RefundActivity extends BaseActivity implements OnClickListener {
    private TextView _tv_name, tv_menu1, tv_menu2, tv_nodata;
    private ImageView _iv_back;
    private NetRun netRun;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private int refreshtype = 0, curpage = 1, menustate = 1;
    private MyListener myListenr;
    private RefundInfo refundInfo;
    private RefundAdapter refundAdapter;
    private SalesReturnInfo salesReturnInfo;
    private MyAdapter myAdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case refund_apply_id:
                    // 退款列表
                    if (msg.obj != null) {
                        refundInfo = (RefundInfo) msg.obj;
                        if (refundInfo.datas.refund_list == null
                                || refundInfo.datas.refund_list.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
//						lv_list.setVisibility(View.GONE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                            lv_list.setVisibility(View.VISIBLE);
                        }
                        if (refreshtype == 0) {
                            refundAdapter = new RefundAdapter(
                                    refundInfo.datas.refund_list);
                            lv_list.setAdapter(refundAdapter);
                        } else if (refreshtype == 1) {
                            // 刷新
                            refundAdapter.updata(refundInfo.datas.refund_list);
                            myListenr.refreshSucceed();
                        } else if (refreshtype == 2) {
                            // 加载
                            refundAdapter.adddata(refundInfo.datas.refund_list);
                            myListenr.loadMoreSucceed();
                        }
                    }
                    break;
                case refund_apply_err:
                    Toast.makeText(RefundActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case sales_return_id:
                    // 退货
                    if (msg.obj != null) {
                        salesReturnInfo = (SalesReturnInfo) msg.obj;
                        if (salesReturnInfo.datas.return_list == null
                                || salesReturnInfo.datas.return_list.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
//						lv_list.setVisibility(View.GONE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                            lv_list.setVisibility(View.VISIBLE);
                        }
                        if (refreshtype == 0) {
                            myAdapter = new MyAdapter(
                                    salesReturnInfo.datas.return_list);
                            lv_list.setAdapter(myAdapter);
                        } else if (refreshtype == 1) {
                            // 刷新
                            myAdapter.updata(salesReturnInfo.datas.return_list);
                            myListenr.refreshSucceed();
                        } else if (refreshtype == 2) {
                            // 加载
                            myAdapter.adddata(salesReturnInfo.datas.return_list);
                            myListenr.loadMoreSucceed();
                        }
                    }
                    break;
                case sales_return_err:
                    Toast.makeText(RefundActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };
    private View menu1Line, menu2Line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        menu1Line = findViewById(R.id.tv_menu1_line);
        menu2Line = findViewById(R.id.tv_menu2_line);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_list = (PullableListView) findViewById(R.id.lv_list);
        _tv_name.setText(getString(R.string.distribution_center24));
        initView();
    }

    @Override
    protected void initView() {
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        _iv_back.setOnClickListener(this);
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.RefundApply("20", curpage + "");
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_menu1) {
            // 退款
            lv_list.setVisibility(View.GONE);
            refreshtype = 0;
            curpage = 1;
            menustate = 1;
            netRun.RefundApply("20", curpage + "");
            doSelectStyle(R.id.tv_menu1);
        } else if (v.getId() == R.id.tv_menu2) {
            // 退货
            lv_list.setVisibility(View.GONE);
            refreshtype = 0;
            curpage = 1;
            menustate = 2;
            netRun.SalesReturn(curpage + "", "20");
            doSelectStyle(R.id.tv_menu2);
        }

//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_menu1:
//                // 退款
//                lv_list.setVisibility(View.GONE);
//                refreshtype = 0;
//                curpage = 1;
//                menustate = 1;
//                netRun.RefundApply("20", curpage + "");
//                doSelectStyle(R.id.tv_menu1);
//                break;
//            case R.id.tv_menu2:
//                // 退货
//                lv_list.setVisibility(View.GONE);
//                refreshtype = 0;
//                curpage = 1;
//                menustate = 2;
//                netRun.SalesReturn(curpage + "", "20");
//                doSelectStyle(R.id.tv_menu2);
//                break;
//        }
    }

    private void doSelectStyle(int viewId) {
        tv_menu1.setSelected(viewId == R.id.tv_menu1);
        tv_menu2.setSelected(viewId == R.id.tv_menu2);
        menu1Line.setVisibility(viewId == R.id.tv_menu1 ? View.VISIBLE : View.GONE);
        menu2Line.setVisibility(viewId == R.id.tv_menu2 ? View.VISIBLE : View.GONE);
    }

    /**
     * 退款列表
     *
     * @author Administrator
     */
    private class RefundAdapter extends BaseAdapter {
        List<refund_list> refund_list;

        public RefundAdapter(List<refund_list> refund_list) {
            System.out.println("---------------退款数量  " + refund_list.size());
            this.refund_list = refund_list;
        }

        /**
         * 刷新
         *
         * @param refund_list
         */
        public void updata(List<refund_list> refund_list) {
            if (refund_list == null) {
                return;
            }
            this.refund_list = refund_list;
            notifyDataSetChanged();
        }

        /**
         * 加载
         */
        public void adddata(List<refund_list> refund_list) {
            if (refund_list == null) {
                return;
            }
            this.refund_list.addAll(refund_list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return refund_list.size();
        }

        @Override
        public Object getItem(int position) {
            return refund_list == null ? null : refund_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(RefundActivity.this,
                        R.layout.refund_item, null);
                new ViewHolder(convertView);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_time.setText(getString(R.string.order_reminder92)
                    + TimeStamp2Date(refund_list.get(position).add_time,
                    "yyyy-MM-dd HH:mm"));
            holder.tv_storename.setText(getString(R.string.order_reminder93)
                    + refund_list.get(position).store_name);
            holder.tv_refundnumber.setText(getString(R.string.order_reminder94)
                    + refund_list.get(position).refund_sn);
            holder.tv_goods_name.setText(refund_list.get(position).goods_name);
            holder.tv_ordernumber.setText(getString(R.string.order_reminder2)
                    + refund_list.get(position).order_sn);
            holder.tv_refundprice.setText(getString(R.string.order_reminder25)
                    + refund_list.get(position).refund_amount);
            holder.tv_refundwhy.setText("退款说明: " + refund_list.get(position).reason_info);
            holder.tv_refundexplain.setText(getString(R.string.order_reminder24)
                    + refund_list.get(position).buyer_message);
            holder.tv_auditstate
                    .setText(refund_list.get(position).seller_state.equals("2") ? getString(R.string.order_reminder95)
                            : refund_list.get(position).seller_state
                            .equals("1") ? getString(R.string.order_reminder96)
                            : refund_list.get(position).seller_state
                            .equals("3") ? getString(R.string.order_reminder97) : "");
            holder.tv_storenote.setText(getString(R.string.order_reminder98)
                    + (refund_list.get(position).seller_message == null ? "无" : refund_list.get(position).seller_message));
            // holder.tv_enote.setText(refund_list.get(position).seller_message);
            if (refund_list.get(position).pic_image != null && refund_list.get(position).pic_image.size() != 0) {
                holder.ll_img.setVisibility(View.VISIBLE);
                Glide.with(RefundActivity.this).load(refund_list.get(position).pic_image.get(0)).into(holder.iv_img1);
                if (refund_list.get(position).pic_image.size() > 1) {
                    Glide.with(RefundActivity.this).load(refund_list.get(position).pic_image.get(1)).into(holder.iv_img2);
                }
                if (refund_list.get(position).pic_image.size() > 2) {
                    Glide.with(RefundActivity.this).load(refund_list.get(position).pic_image.get(2)).into(holder.iv_img3);
                }
            } else {
                holder.ll_img.setVisibility(View.GONE);
            }
            return convertView;
        }

        class ViewHolder {
            TextView tv_time, tv_storename, tv_refundnumber, tv_goods_name,
                    tv_ordernumber, tv_refundprice, tv_refundwhy,
                    tv_refundexplain, tv_auditstate, tv_storenote, tv_enote;
            ImageView iv_img1, iv_img2, iv_img3;
            LinearLayout ll_img;

            public ViewHolder(View convertView) {
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                tv_storename = (TextView) convertView
                        .findViewById(R.id.tv_storename);
                tv_refundnumber = (TextView) convertView
                        .findViewById(R.id.tv_refundnumber);
                tv_goods_name = (TextView) convertView
                        .findViewById(R.id.tv_goods_name);
                tv_ordernumber = (TextView) convertView
                        .findViewById(R.id.tv_ordernumber);
                tv_refundprice = (TextView) convertView
                        .findViewById(R.id.tv_refundprice);
                tv_refundwhy = (TextView) convertView
                        .findViewById(R.id.tv_refundwhy);
                tv_refundexplain = (TextView) convertView
                        .findViewById(R.id.tv_refundexplain);
                tv_auditstate = (TextView) convertView
                        .findViewById(R.id.tv_auditstatee);
                tv_storenote = (TextView) convertView
                        .findViewById(R.id.tv_storenote);
//                tv_enote = convertView.findViewById(R.id.tv_enote);
                iv_img1 = convertView.findViewById(R.id.iv_img1);
                iv_img2 = convertView.findViewById(R.id.iv_img2);
                iv_img3 = convertView.findViewById(R.id.iv_img3);
                ll_img = convertView.findViewById(R.id.ll_img);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 退货列表
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {
        List<return_list> return_list;

        public MyAdapter(List<return_list> return_list) {
            this.return_list = return_list;
        }

        /**
         * 刷新
         *
         * @param return_list
         */
        public void updata(List<return_list> return_list) {
            if (return_list == null) {
                return;
            }
            this.return_list = return_list;
            notifyDataSetChanged();
        }

        /**
         * 加载
         */
        public void adddata(List<return_list> return_list) {
            if (return_list == null) {
                return;
            }
            this.return_list.addAll(return_list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return return_list == null ? 0 : return_list.size();
        }

        @Override
        public Object getItem(int position) {
            return return_list == null ? null : return_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(RefundActivity.this,
                        R.layout.item_sales_return, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();
            return_list return_list2 = return_list.get(position);
            holder.tv_time
                    .setText(getString(R.string.order_reminder92)
                            + TimeStamp2Date(return_list2.add_time,
                            "yyyy-MM-dd HH:mm"));
            holder.tv_storename.setText(getString(R.string.order_reminder93) + return_list2.store_name);
            holder.tv_refundnumber
                    .setText(getString(R.string.order_reminder99) + return_list2.refund_sn);
            holder.tv_refundprice.setText(getString(R.string.order_reminder25)
                    + return_list2.refund_amount + "  " + getString(R.string.order_reminder100) + return_list2.goods_num);
            /*holder.tv_refundshuliang
                    .setText(getString(R.string.order_reminder100) + return_list2.goods_num);*/
            holder.tv_refundwhy.setText(getString(R.string.order_reminder101) + return_list2.reason_info);
            holder.tv_refundexplain.setText(getString(R.string.order_reminder102)
                    + return_list2.buyer_message);
            String str = return_list2.seller_state.equals("2") ? getString(R.string.order_reminder103)
                    : return_list2.seller_state.equals("1") ? getString(R.string.order_reminder104)
                    : return_list2.seller_state.equals("3") ? getString(R.string.order_reminder105)
                    : "";
            holder.tv_auditstatee.setText(getString(R.string.order_reminder106) + str);
            Glide.with(holder.returnGoodsIv).load(return_list2.goods_image).into(holder.returnGoodsIv);
            holder.return_goods_Tv.setText(return_list2.goods_name);
//            holder.mv_goods.setAdapter(new );
            if (return_list2.pic_list != null && return_list2.pic_list.size() != 0) {
                holder.ll_img.setVisibility(View.VISIBLE);
                Glide.with(RefundActivity.this).load(return_list2.pic_list.get(0).imgurl).into(holder.iv_img1);
                if (return_list2.pic_list.size() > 1) {
                    Glide.with(RefundActivity.this).load(return_list2.pic_list.get(1).imgurl).into(holder.iv_img2);
                }
                if (return_list2.pic_list.size() > 2) {
                    Glide.with(RefundActivity.this).load(return_list2.pic_list.get(2).imgurl).into(holder.iv_img3);
                }
            } else {
                holder.ll_img.setVisibility(View.GONE);
            }
            return convertView;
        }

        class ViewHodler {
            ImageView returnGoodsIv;
            TextView tv_time, tv_storename, tv_refundnumber, tv_refundprice,
                    tv_refundshuliang, tv_refundwhy, tv_refundexplain,
                    tv_auditstatee, tv_consignee, tv_phone, tv_storenote,
                    tv_official, return_goods_Tv;
            MyListView mv_goods;
            ImageView iv_img1, iv_img2, iv_img3;
            LinearLayout ll_img;

            public ViewHodler(View convertView) {
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                tv_storename = (TextView) convertView
                        .findViewById(R.id.tv_storename);
                tv_refundnumber = (TextView) convertView
                        .findViewById(R.id.tv_refundnumber);
                tv_refundprice = (TextView) convertView
                        .findViewById(R.id.tv_refundprice);
                tv_refundshuliang = (TextView) convertView
                        .findViewById(R.id.tv_refundshuliang);
                tv_refundwhy = (TextView) convertView
                        .findViewById(R.id.tv_refundwhy);
                tv_refundexplain = (TextView) convertView
                        .findViewById(R.id.tv_refundexplain);
                tv_auditstatee = (TextView) convertView
                        .findViewById(R.id.tv_auditstatee);
                tv_consignee = (TextView) convertView
                        .findViewById(R.id.tv_consignee);
                tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
                tv_storenote = (TextView) convertView
                        .findViewById(R.id.tv_storenote);
                tv_official = (TextView) convertView
                        .findViewById(R.id.tv_official);
                mv_goods = (MyListView) convertView.findViewById(R.id.mv_goods);
                iv_img1 = convertView.findViewById(R.id.iv_img1);
                iv_img2 = convertView.findViewById(R.id.iv_img2);
                iv_img3 = convertView.findViewById(R.id.iv_img3);
                ll_img = convertView.findViewById(R.id.ll_img);
                returnGoodsIv = convertView.findViewById(R.id.return_goods_iv);
                return_goods_Tv = convertView.findViewById(R.id.return_goods_tv);
                convertView.setTag(this);
            }
        }
    }

    public class MyListener implements OnRefreshListener {

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

            ;
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
            refreshtype = 1;
            curpage = 1;
            if (menustate == 1) {
                // 退款
                netRun.RefundApply("20", curpage + "");
            } else if (menustate == 2) {
                // 退货
                netRun.SalesReturn(curpage + "", "20");
            }
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (menustate == 1) {
                // 退款
                if (refundInfo.hasmore.equals("true")) {
                    refreshtype = 2;
                    curpage++;
                    netRun.RefundApply("20", curpage + "");
                } else {
                    loadMoreSucceed();
                    Toast.makeText(RefundActivity.this,
                            getString(R.string.act_no_data_load),
                            Toast.LENGTH_SHORT).show();
                }
            } else if (menustate == 2) {
                // 退货
                if (salesReturnInfo.hasmore.equals("true")) {
                    refreshtype = 2;
                    curpage++;
                    netRun.SalesReturn(curpage + "", "20");
                } else {
                    loadMoreSucceed();
                    Toast.makeText(RefundActivity.this,
                            getString(R.string.act_no_data_load),
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString 时间戳
     * @param formats         格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }

}
