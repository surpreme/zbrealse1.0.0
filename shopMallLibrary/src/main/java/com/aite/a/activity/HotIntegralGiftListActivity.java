package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotIntegralGiftListInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 热门兑换商品
 *
 * @author Administrator
 */
public class HotIntegralGiftListActivity extends BaseActivity implements
        OnClickListener {
    private MygiftAdapter mygiftAdapter;
    private TextView _tv_name;
    private ImageView _iv_back;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private LinearLayout ll_menu_1, ll_menu_2;
    private ListView lv_glist;
    private String sort_type = "", grade_id = "";
    private ListView lv_menu;
    private int state = 1;
    private List<String> menudata;
    private MenuAdapter menuAdapter;
    private HotIntegralGiftListInfo hotIntegralGiftListInfo;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case hotintegralgift_list_id:
                    if (msg.obj != null) {
                        hotIntegralGiftListInfo = (HotIntegralGiftListInfo) msg.obj;
                        mygiftAdapter = new MygiftAdapter(hotIntegralGiftListInfo.datas.pointprod_list);
                        lv_glist.setAdapter(mygiftAdapter);
                    }
                    break;
                case hotintegralgift_list_err:
                    Toast.makeText(HotIntegralGiftListActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralgiftlist);
        findViewById();
    }


    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        ll_menu_1 = (LinearLayout) findViewById(R.id.ll_menu_1);
        ll_menu_2 = (LinearLayout) findViewById(R.id.ll_menu_2);
        lv_glist = (ListView) findViewById(R.id.lv_glist);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.evaluation_tips7));
        netRun = new NetRun(this, handler);
        _iv_back.setOnClickListener(this);
        ll_menu_1.setOnClickListener(this);
        ll_menu_2.setOnClickListener(this);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.HotIntegralGiftList(sort_type, grade_id);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.ll_menu_1:
//                state = 1;
//                if (lv_menu.getVisibility() == View.VISIBLE) {
//                    lv_menu.setVisibility(View.GONE);
//                } else {
//                    lv_menu.setVisibility(View.VISIBLE);
//                }
//                menudata = new ArrayList<String>();
//                menudata.add(getString(R.string.evaluation_tips8));
//                for (int i = 0; i < hotIntegralGiftListInfo.datas.membergrade_arr.size(); i++) {
//                    menudata.add(hotIntegralGiftListInfo.datas.membergrade_arr.get(i).level_name);
//                }
//                menuAdapter = new MenuAdapter(menudata);
//                lv_menu.setAdapter(menuAdapter);
//                break;
//            case R.id.ll_menu_2:
//                state = 2;
//                if (lv_menu.getVisibility() == View.VISIBLE) {
//                    lv_menu.setVisibility(View.GONE);
//                } else {
//                    lv_menu.setVisibility(View.VISIBLE);
//                }
//                menudata = new ArrayList<String>();
//                menudata.add(getString(R.string.evaluation_tips9));
//                menudata.add(getString(R.string.evaluation_tips10));
//                menudata.add(getString(R.string.evaluation_tips11));
//                menuAdapter = new MenuAdapter(menudata);
//                lv_menu.setAdapter(menuAdapter);
//                break;
//        }
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() ==R.id.ll_menu_1) {
            state = 1;
            if (lv_menu.getVisibility() == View.VISIBLE) {
                lv_menu.setVisibility(View.GONE);
            } else {
                lv_menu.setVisibility(View.VISIBLE);
            }
            menudata = new ArrayList<String>();
            menudata.add(getString(R.string.evaluation_tips8));
            for (int i = 0; i < hotIntegralGiftListInfo.datas.membergrade_arr.size(); i++) {
                menudata.add(hotIntegralGiftListInfo.datas.membergrade_arr.get(i).level_name);
            }
            menuAdapter = new MenuAdapter(menudata);
            lv_menu.setAdapter(menuAdapter);
        }else if(v.getId()==R.id.ll_menu_2){
            state = 2;
            if (lv_menu.getVisibility() == View.VISIBLE) {
                lv_menu.setVisibility(View.GONE);
            } else {
                lv_menu.setVisibility(View.VISIBLE);
            }
            menudata = new ArrayList<String>();
            menudata.add(getString(R.string.evaluation_tips9));
            menudata.add(getString(R.string.evaluation_tips10));
            menudata.add(getString(R.string.evaluation_tips11));
            menuAdapter = new MenuAdapter(menudata);
            lv_menu.setAdapter(menuAdapter);
        }
    }

    /**
     * 获取ID
     *
     * @param name
     * @return
     */
    private String getallid(String name) {
        String id = "-1";
        for (int i = 0; i < hotIntegralGiftListInfo.datas.membergrade_arr.size(); i++) {
            if (name.equals(hotIntegralGiftListInfo.datas.membergrade_arr.get(i).level_name)) {
                id = hotIntegralGiftListInfo.datas.membergrade_arr.get(i).level;
            }
        }
        return id;
    }

    /**
     * 热门礼品适配
     *
     * @author Administrator
     */
    private class MygiftAdapter extends BaseAdapter {
        List<HotIntegralGiftListInfo.datas.pointprod_list> pointprod_list;

        public MygiftAdapter(List<HotIntegralGiftListInfo.datas.pointprod_list> pointprod_list) {
            this.pointprod_list = pointprod_list;
        }

        @Override
        public int getCount() {
            return pointprod_list.size();
        }

        @Override
        public Object getItem(int position) {
            return pointprod_list == null ? null : pointprod_list
                    .get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(HotIntegralGiftListActivity.this,
                        R.layout.hot_gift_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final HotIntegralGiftListInfo.datas.pointprod_list pointprod_list2 = pointprod_list.get(position);

            bitmapUtils.display(holder.iv_gift_image,
                    pointprod_list2.pgoods_image);
            holder.tv_gift_goodsname.setText(pointprod_list2.pgoods_name);
            holder.tv_costnum.setText("￥" + pointprod_list2.pgoods_price);
            holder.tv_points
                    .setText(pointprod_list2.pgoods_points + getString(R.string.integrall));
            holder.bt_exchange.setText(getString(R.string.immediately_exchange));

            holder.bt_exchange.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(
                            HotIntegralGiftListActivity.this,
                            IntegralGoodsInfoActivity.class);
                    intent2.putExtra("id", pointprod_list2.pgoods_id);
                    startActivity(intent2);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_gift_image, iv_gift_level;
            TextView tv_gift_goodsname, tv_costnum, tv_points, bt_exchange;

            public ViewHolder(View convertView) {
                iv_gift_image = (ImageView) convertView
                        .findViewById(R.id.iv_gift_image);
                iv_gift_level = (ImageView) convertView
                        .findViewById(R.id.iv_gift_level);
                tv_gift_goodsname = (TextView) convertView
                        .findViewById(R.id.tv_gift_goodsname);
                tv_costnum = (TextView) convertView
                        .findViewById(R.id.tv_costnum);
                tv_points = (TextView) convertView.findViewById(R.id.tv_points);
                bt_exchange = (TextView) convertView
                        .findViewById(R.id.bt_exchange);
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
                convertView = View.inflate(HotIntegralGiftListActivity.this,
                        R.layout.popu_textitem, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_popu1.setText(menudata.get(position));
            holder.tv_popu1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (state == 1) {
                        // 等级
                        grade_id = getallid(menudata.get(position));
                    } else if (state == 2) {
                        // 排序
                        sort_type = position + "";
                    }
                    netRun.HotIntegralGiftList(sort_type, grade_id);
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

}
