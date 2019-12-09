package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotVouchersList2Info;
import com.aite.a.model.HotVouchersListInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 热门代金券
 *
 * @author Administrator
 */
public class HotVouchersListActivity extends BaseActivity implements
        OnClickListener {

    private LinearLayout ll_menu_1, ll_menu_2, ll_menu_3;
    private GridView mgv_voucherslist;
    private TextView _tv_name;
    private ImageView _iv_back;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private MyAdapter myAdapter;
    private String sc_id = "", curpage = "1", price = "", sort_type = "";
    private List<HotVouchersList2Info> hotVouchersList2Info;
    private HotVouchersListInfo hotVouchersListInfo;
    private Map<String, Object> map = new HashMap<String, Object>();
    private ListView lv_menu;
    private MenuAdapter menuAdapter;
    private List<String> menudata;
    private int state = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case hot_vouchers_id:
                    if (msg.obj != null) {
                        map = (Map<String, Object>) msg.obj;
                        hotVouchersList2Info = (List<HotVouchersList2Info>) map
                                .get("1");
                        hotVouchersListInfo = (HotVouchersListInfo) map.get("2");
                        if (hotVouchersListInfo.datas.voucherlist == null || hotVouchersListInfo.datas.voucherlist.size() == 0) {
                            mgv_voucherslist.setVisibility(View.GONE);
                        } else {
                            mgv_voucherslist.setVisibility(View.VISIBLE);
                            myAdapter = new MyAdapter(
                                    hotVouchersListInfo.datas.voucherlist);
                            mgv_voucherslist.setAdapter(myAdapter);
                        }
                    }
                    break;
                case hot_vouchers_err:
                    Toast.makeText(HotVouchersListActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucherslist);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_menu_1 = (LinearLayout) findViewById(R.id.ll_menu_1);
        ll_menu_2 = (LinearLayout) findViewById(R.id.ll_menu_2);
        ll_menu_3 = (LinearLayout) findViewById(R.id.ll_menu_3);
        mgv_voucherslist = (GridView) findViewById(R.id.mgv_voucherslist);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.evaluation_tips12));
        ll_menu_1.setOnClickListener(this);
        ll_menu_2.setOnClickListener(this);
        ll_menu_3.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.HotVouchers(sc_id, curpage, price, sort_type);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.ll_menu_1:
//			state = 1;
//			if (lv_menu.getVisibility() == View.VISIBLE) {
//				lv_menu.setVisibility(View.GONE);
//			} else {
//				lv_menu.setVisibility(View.VISIBLE);
//			}
//			menudata = new ArrayList<>();
//			menudata.add(getString(R.string.all_classifying));
//			for (int i = 0; i < hotVouchersList2Info.size(); i++) {
//				menudata.add(hotVouchersList2Info.get(i).sc_name);
//			}
//			menuAdapter = new MenuAdapter(menudata);
//			lv_menu.setAdapter(menuAdapter);
//			break;
//		case R.id.ll_menu_2:
//			state = 2;
//			if (lv_menu.getVisibility() == View.VISIBLE) {
//				lv_menu.setVisibility(View.GONE);
//			} else {
//				lv_menu.setVisibility(View.VISIBLE);
//			}
//			menudata = new ArrayList<String>();
//			menudata.add(getString(R.string.evaluation_tips9));
//			menudata.add(getString(R.string.evaluation_tips13));
//			menudata.add(getString(R.string.evaluation_tips10));
//			menuAdapter = new MenuAdapter(menudata);
//			lv_menu.setAdapter(menuAdapter);
//			break;
//		case R.id.ll_menu_3:
//			state = 3;
//			if (lv_menu.getVisibility() == View.VISIBLE) {
//				lv_menu.setVisibility(View.GONE);
//			} else {
//				lv_menu.setVisibility(View.VISIBLE);
//			}
//			menudata = new ArrayList<String>();
//			menudata.add(getString(R.string.evaluation_tips14));
//			for (int i = 0; i < hotVouchersListInfo.datas.pricelist.size(); i++) {
//				menudata.add(hotVouchersListInfo.datas.pricelist.get(i).voucher_price
//						+ getString(R.string.evaluation_tips15));
//			}
//			menuAdapter = new MenuAdapter(menudata);
//			lv_menu.setAdapter(menuAdapter);
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		}
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.ll_menu_1) {
            state = 1;
            if (lv_menu.getVisibility() == View.VISIBLE) {
                lv_menu.setVisibility(View.GONE);
            } else {
                lv_menu.setVisibility(View.VISIBLE);
            }
            menudata = new ArrayList<>();
            menudata.add(getString(R.string.all_classifying));
            for (int i = 0; i < hotVouchersList2Info.size(); i++) {
                menudata.add(hotVouchersList2Info.get(i).sc_name);
            }
            menuAdapter = new MenuAdapter(menudata);
            lv_menu.setAdapter(menuAdapter);
        } else if (v.getId() == R.id.ll_menu_2) {
            state = 2;
            if (lv_menu.getVisibility() == View.VISIBLE) {
                lv_menu.setVisibility(View.GONE);
            } else {
                lv_menu.setVisibility(View.VISIBLE);
            }
            menudata = new ArrayList<String>();
            menudata.add(getString(R.string.evaluation_tips9));
            menudata.add(getString(R.string.evaluation_tips13));
            menudata.add(getString(R.string.evaluation_tips10));
            menuAdapter = new MenuAdapter(menudata);
            lv_menu.setAdapter(menuAdapter);
        } else if (v.getId() == R.id.ll_menu_3) {
            state = 3;
            if (lv_menu.getVisibility() == View.VISIBLE) {
                lv_menu.setVisibility(View.GONE);
            } else {
                lv_menu.setVisibility(View.VISIBLE);
            }
            menudata = new ArrayList<String>();
            menudata.add(getString(R.string.evaluation_tips14));
            for (int i = 0; i < hotVouchersListInfo.datas.pricelist.size(); i++) {
                menudata.add(hotVouchersListInfo.datas.pricelist.get(i).voucher_price
                        + getString(R.string.evaluation_tips15));
            }
            menuAdapter = new MenuAdapter(menudata);
            lv_menu.setAdapter(menuAdapter);
        }
    }

    /**
     * 获取全部分类ID
     *
     * @param name
     * @return
     */
    private String getallid(String name) {
        String id = "";
        for (int i = 0; i < hotVouchersList2Info.size(); i++) {
            if (name.equals(hotVouchersList2Info.get(i).sc_name)) {
                id = hotVouchersList2Info.get(i).sc_id;
            }
        }
        return id;
    }

    /**
     * 获取面额ID
     *
     * @param name
     * @return
     */
    private String getmeid(String name) {
        String id = "";
        for (int i = 0; i < hotVouchersListInfo.datas.pricelist.size(); i++) {
            if (name.indexOf(hotVouchersListInfo.datas.pricelist.get(i).voucher_price) != -1) {
                id = hotVouchersListInfo.datas.pricelist.get(i).voucher_price;
            }
        }
        return id;
    }

    /**
     * 热门代金券适配
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {
        List<HotVouchersListInfo.datas.voucherlist> voucherlist;

        public MyAdapter(List<HotVouchersListInfo.datas.voucherlist> voucherlist) {
            this.voucherlist = voucherlist;
        }

        @Override
        public int getCount() {
            return voucherlist.size();
        }

        @Override
        public Object getItem(int position) {
            return voucherlist == null ? null : voucherlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(HotVouchersListActivity.this,
                        R.layout.hot_vouchers_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final HotVouchersListInfo.datas.voucherlist voucherlist2 = voucherlist
                    .get(position);

            holder.tv_shop_name.setText(voucherlist2.voucher_t_storename);
            holder.tv_cost.setText("￥" + voucherlist2.voucher_t_price);
            holder.tv_statement.setText(getString(R.string.evaluation_tips16) + voucherlist2.voucher_t_limit
                    + getString(R.string.evaluation_tips17));
            holder.tv_valid_time.setText(getString(R.string.evaluation_tips18)
                    + TimeStamp2Date(voucherlist2.voucher_t_end_date,
                    "yyyy-MM-dd HH:mm"));
            // holder.tv_need.setText("需要" + voucherlist2.voucher_t_points +
            // "积分");
//			holder.ll_item.setText(getString(R.string.exchange));
            holder.ll_item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 兑换
                    Intent intent2 = new Intent(HotVouchersListActivity.this,
                            CreditsExchangeActivity.class);
                    intent2.putExtra("vid", voucherlist2.voucher_t_id);
                    startActivity(intent2);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_shop_name, tv_cost, tv_statement, tv_valid_time,
                    tv_need;
            LinearLayout ll_item;

            public ViewHolder(View convertView) {
                tv_shop_name = (TextView) convertView
                        .findViewById(R.id.tv_shop_name);
                tv_cost = (TextView) convertView.findViewById(R.id.tv_cost);
                tv_statement = (TextView) convertView
                        .findViewById(R.id.tv_statement);
                tv_valid_time = (TextView) convertView
                        .findViewById(R.id.tv_valid_time);
                tv_need = (TextView) convertView.findViewById(R.id.tv_need);
                tv_need.setVisibility(View.GONE);
                ll_item = convertView
                        .findViewById(R.id.ll_item);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 菜单
     *
     * @author Administrator
     */
    private class MenuAdapter extends BaseAdapter {
        List<String> menudata;

        public MenuAdapter(List<String> menudata) {
            this.menudata = menudata;
        }

        @Override
        public int getCount() {
            return menudata.size();
        }

        @Override
        public Object getItem(int position) {
            return menudata == null ? null : menudata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(HotVouchersListActivity.this,
                        R.layout.popu_textitem, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_popu1.setText(menudata.get(position));
            holder.tv_popu1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (state == 1) {
                        // 分类
                        sc_id = getallid(menudata.get(position));
                    } else if (state == 2) {
                        // 排序
                        sort_type = position + "";
                    } else if (state == 3) {
                        // 面额
                        price = getmeid(menudata.get(position));
                    }
                    netRun.HotVouchers(sc_id, curpage, price, sort_type);
                    lv_menu.setVisibility(View.GONE);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_popu1;

            public ViewHolder(View convertView) {
                tv_popu1 = (TextView) convertView.findViewById(R.id.tv_popu1);
                convertView.setTag(this);
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
