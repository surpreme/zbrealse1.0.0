package com.aite.a.activity;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.RecommendCombinationInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 推荐组合
 *
 * @author Administrator
 */
public class RecommendCombinationActivity extends BaseActivity implements
        OnClickListener {
    private ImageView iv_bcreturn, iv_img;
    private TextView tv_bctitle, tv_name, tv_price1, tv_addcart;
    private MyListView lv_lis;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private MyAdapter myAdapter;
    private String goods_id;
    private RecommendCombinationInfo preferentialSuitInfo;
    private RelativeLayout rl_item;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case assemblage_goods_id:
                    // 推荐组合
                    if (msg.obj != null) {
                        preferentialSuitInfo = (RecommendCombinationInfo) msg.obj;
                        if (preferentialSuitInfo.goods_info == null) {
                            NewVersion();
                            return;
                        }
                        bitmapUtils.display(iv_img,
                                preferentialSuitInfo.goods_info.goods_image);
                        tv_name.setText(preferentialSuitInfo.goods_info.goods_name);
                        tv_price1
                                .setText(preferentialSuitInfo.goods_info.goods_promotion_price);
                        myAdapter = new MyAdapter(preferentialSuitInfo.gcombo_list);
                        lv_lis.setAdapter(myAdapter);
                    }
                    break;
                case assemblage_goods_err:
                    Toast.makeText(RecommendCombinationActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case add_assemblage_goods_id:
                    // 加入购物车
                    if (msg.obj != null) {
                        Map map = (Map) msg.obj;
                        String str1 = (String) map.get("0");
                        String str2 = (String) map.get("1");
                        Toast.makeText(RecommendCombinationActivity.this, str2,
                                Toast.LENGTH_SHORT).show();
                        if (str1.equals("1")) {
                            finish();
                        }
                    }
                    break;
                case add_assemblage_goods_err:
                    Toast.makeText(RecommendCombinationActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_combination);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        tv_bctitle = (TextView) findViewById(R.id._tv_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_price1 = (TextView) findViewById(R.id.tv_price1);
        tv_addcart = (TextView) findViewById(R.id.tv_addcart);
        lv_lis = (MyListView) findViewById(R.id.lv_lis);
        initView();
    }

    @Override
    protected void initView() {
        tv_bctitle.setText(getString(R.string.release_goods4));
        iv_bcreturn.setOnClickListener(this);
        tv_addcart.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        goods_id = getIntent().getStringExtra("goods_id");
        initData();
    }

    @Override
    protected void initData() {
        netRun.AssemblageGoods(goods_id);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_addcart) {
            // 添加购物车
            netRun.AddAssemblageGoods(myAdapter.getchooseid());
        }
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_addcart:
//                // 添加购物车
//                netRun.AddAssemblageGoods(myAdapter.getchooseid());
//                break;
//        }
    }

    /**
     * 提示
     */
    private void NewVersion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setTitle(getString(R.string.order_reminder79));
        dialog.setCancelable(false);// 设置点击屏幕Dialog不消失
        dialog.setNegativeButton(getI18n(R.string.confirm),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        dialog.create().show();
    }

    /**
     * 列表
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {
        List<RecommendCombinationInfo.gcombo_list> gcombo_list;

        public MyAdapter(List<RecommendCombinationInfo.gcombo_list> gcombo_list) {
            this.gcombo_list = gcombo_list;
        }

        /**
         * 获取选中ID
         *
         * @return
         */
        public String getchooseid() {
            if (gcombo_list == null) {
                return preferentialSuitInfo.goods_info.goods_id;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(preferentialSuitInfo.goods_info.goods_id + "|");
            for (int i = 0; i < gcombo_list.size(); i++) {
                if (gcombo_list.get(i).ischoose) {
                    stringBuffer.append(gcombo_list.get(i).goods_id + "|");
                }
            }
            String s = stringBuffer.toString();
            return s.substring(0, s.length() - 1);
        }

        @Override
        public int getCount() {
            return gcombo_list.size();
        }

        @Override
        public Object getItem(int position) {
            return gcombo_list == null ? null : gcombo_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(RecommendCombinationActivity.this,
                        R.layout.item_preferential_suit3, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final RecommendCombinationInfo.gcombo_list gcombo_list2 = gcombo_list.get(position);
            holder.tv_name.setText(gcombo_list2.goods_name);
            holder.tv_price1.setText(getString(R.string.order_reminder80)
                    + gcombo_list2.goods_promotion_price);
            bitmapUtils.display(holder.iv_img, gcombo_list2.goods_image);
            holder.cb_choose.setChecked(gcombo_list2.ischoose);
            holder.cb_choose
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            gcombo_list2.ischoose = isChecked;
                        }
                    });
            return convertView;
        }

        class ViewHolder {
            TextView tv_name, tv_price1;
            ImageView iv_img;
            CheckBox cb_choose;

            public ViewHolder(View convertView) {
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_price1 = (TextView) convertView.findViewById(R.id.tv_price1);
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                cb_choose = (CheckBox) convertView.findViewById(R.id.cb_choose);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 计算节省
     */
    private String getPrice(String s1, String s2) {
        try {
            return (Float.parseFloat(s1) - Float.parseFloat(s2)) + "";
        } catch (Exception e) {
        }
        return "";
    }

}
