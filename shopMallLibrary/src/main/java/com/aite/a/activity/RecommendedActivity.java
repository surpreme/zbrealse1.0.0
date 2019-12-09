package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.GetRecommendedInfo;
import com.aite.a.model.GoodsListInfo;
import com.aite.a.model.GoodsListInfo.goods_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyChooseGiftPopu;
import com.aite.a.view.MyListView;
import com.aite.a.view.MyChooseGiftPopu.date;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐组合 Created by Administrator on 2017/6/6.
 */
public class RecommendedActivity extends BaseActivity implements
        OnClickListener {
    private ImageView _iv_back;
    private TextView _tv_name, tv_netxt;
    private MyListView rv_gifts;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private RelativeLayout cv_sm;
    private Madapter madapter;
    private GetRecommendedInfo getRecommendedInfo;
    private GoodsListInfo goodsListInfo;
    private String commonid;
    private String name = "";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case get_recommended_id:
                    if (msg.obj != null) {
                        // 推荐商品列表
                        getRecommendedInfo = (GetRecommendedInfo) msg.obj;
                        madapter = new Madapter(getRecommendedInfo);
                        rv_gifts.setAdapter(madapter);
                    }
                    break;
                case get_recommended_err:
                    Toast.makeText(RecommendedActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
//			case search_goods_id:
//				// 搜索商品列表
//				if (msg.obj != null) {
//					goodsListInfo = (GoodsListInfo) msg.obj;
//					GetRecommendedInfo data = madapter.getData();
//					Madapter2 adapter2 = (Madapter2) data.goods_array
//							.get(pagerid).adapter2;
//					adapter2.updata(goodsListInfo);
//					// data.goods_array.get(pagerid).tv_pagerbtn3.setText(pagernumber+"");
//					data.goods_array.get(pagerid).pagernumber = pagernumber;
//				}
//				break;
//			case search_goods_err:
//				Toast.makeText(RecommendedActivity.this,
//						getString(R.string.systembusy), Toast.LENGTH_SHORT)
//						.show();
//				break;
                case 10010:
                    isupdatapager = true;
                    break;
                case save_combo_id:
                    // 推荐保存
                    if (msg.obj != null) {
                        String str = msg.obj.toString();
                        if (str.equals("1")) {
                            Toast.makeText(RecommendedActivity.this,
                                    getString(R.string.operationissuccessful),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RecommendedActivity.this, str,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case save_combo_err:
                    Toast.makeText(RecommendedActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_netxt = (TextView) findViewById(R.id.tv_netxt);
        rv_gifts = (MyListView) findViewById(R.id.rv_gifts);
        cv_sm = (RelativeLayout) findViewById(R.id.cv_sm);
        rv_gifts.setFocusable(false);
        cv_sm.requestFocus();
        _tv_name.setText(getString(R.string.release_goods4));
        _iv_back.setOnClickListener(this);
        tv_netxt.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        commonid = getIntent().getStringExtra("commonid");
        netRun.GetRecommended(commonid);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_netxt) {
            // 提交
            if (madapter != null) {
                netRun.SaveCombo(commonid, madapter.getjson());
            }
        }
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_netxt:
//			// 提交
//			if (madapter != null) {
//				netRun.SaveCombo(commonid, madapter.getjson());
//			}
//			break;
//		}
    }

    /**
     * 显示礼物
     */
    private void shoupopu(int id) {
        MyChooseGiftPopu myChooseGiftPopu = new MyChooseGiftPopu(
                RecommendedActivity.this, id);
        myChooseGiftPopu.setdate(new date() {

            @Override
            public void onItemClick(goods_list goods, int idd) {
//				Log.i("------------------", goods.toString());
                GetRecommendedInfo.goods_array.combo_list combo_array = new GetRecommendedInfo.goods_array.combo_list();
                combo_array.goods_id = goods.goods_id;
                combo_array.goods_name = goods.goods_name;
                combo_array.goods_image = goods.goods_image;
                combo_array.goods_price = goods.goods_price;
                madapter.addItem(combo_array, idd);
            }
        });
        myChooseGiftPopu.showAtLocation(tv_netxt, Gravity.BOTTOM, 0, 0);
    }

    private int pagerid = 0, pagernumber = 1;
    private boolean isupdatapager = true;

    /**
     * 商品列表
     */
    class Madapter extends BaseAdapter {
        GetRecommendedInfo getRecommendedInfo;

        public Madapter(GetRecommendedInfo getRecommendedInfo) {
            this.getRecommendedInfo = getRecommendedInfo;
        }

        public GetRecommendedInfo getData() {
            return this.getRecommendedInfo;
        }

        /**
         * 获取已选数组
         */
        public String getjson() {
            try {
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < getRecommendedInfo.goods_array.size(); i++) {
                    JSONArray jsonArray2 = new JSONArray();
                    for (int j = 0; j < getRecommendedInfo.goods_array.get(i).combo_list
                            .size(); j++) {
                        jsonArray2
                                .put(getRecommendedInfo.goods_array.get(i).combo_list
                                        .get(j).goods_id);
                    }
                    jsonObject.put(
                            getRecommendedInfo.goods_array.get(i).goods_id,
                            jsonArray2);
                }
                Log.i("-----------------", "获取已选数组 " + jsonObject.toString());
                return jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * 添加
         *
         * @param combo
         */
        public void addItem(
                GetRecommendedInfo.goods_array.combo_list combo,
                int id) {
            getRecommendedInfo.goods_array.get(id).combo_list.add(combo);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return getRecommendedInfo.goods_array.size();
        }

        @Override
        public Object getItem(int position) {
            if (getRecommendedInfo == null) {
                return null;
            } else if (getRecommendedInfo.goods_array == null) {
                return null;
            } else {
                return getRecommendedInfo.goods_array.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(RecommendedActivity.this,
                        R.layout.item_recommended, null);
                new ViewHodler(convertView);
            }
            final ViewHodler holder = (ViewHodler) convertView.getTag();
            GetRecommendedInfo.goods_array goods_array = getRecommendedInfo.goods_array
                    .get(position);
            bitmapUtils.display(holder.img, goods_array.goods_image);
            holder.tv_name.setText(goods_array.goods_name);
            holder.tv_pagerbtn3.setText(goods_array.pagernumber + "");

            // if (getRecommendedInfo.combo_array != null &&
            // getRecommendedInfo.combo_array.size() != 0) {
            // 已选

            if (goods_array.combo_list == null) {
                goods_array.combo_list = new ArrayList<>();
            }
            Madapter3 adapter3 = new Madapter3(goods_array.combo_list);
            holder.rv_recommended.setAdapter(adapter3);
            // }
            // 商品库
            holder.tv_xz.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 选择商品推荐组合

                    shoupopu(position);
                }
            });
            return convertView;
        }

        class ViewHodler {
            ImageView img;
            TextView tv_name, tv_xz, tv_search, tv_pagerbtn1, tv_pagerbtn2,
                    tv_pagerbtn3, tv_pagerbtn4, tv_pagerbtn5;
            EditText ed_input1;
            View v_fg;
            RecyclerView rv_giftslist;
            RelativeLayout rl_search;
            MyListView rv_recommended;

            public ViewHodler(View convertView) {
                img = (ImageView) convertView.findViewById(R.id.img);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_xz = (TextView) convertView.findViewById(R.id.tv_xz);
                tv_search = (TextView) convertView.findViewById(R.id.tv_search);
                tv_pagerbtn1 = (TextView) convertView
                        .findViewById(R.id.tv_pagerbtn1);
                tv_pagerbtn2 = (TextView) convertView
                        .findViewById(R.id.tv_pagerbtn2);
                tv_pagerbtn3 = (TextView) convertView
                        .findViewById(R.id.tv_pagerbtn3);
                tv_pagerbtn4 = (TextView) convertView
                        .findViewById(R.id.tv_pagerbtn4);
                tv_pagerbtn5 = (TextView) convertView
                        .findViewById(R.id.tv_pagerbtn5);
                ed_input1 = (EditText) convertView.findViewById(R.id.ed_input1);
                v_fg = convertView.findViewById(R.id.v_fg);
                rv_giftslist = (RecyclerView) convertView
                        .findViewById(R.id.rv_giftslist);
                rv_recommended = (MyListView) convertView
                        .findViewById(R.id.rv_recommended);
                rl_search = (RelativeLayout) convertView
                        .findViewById(R.id.rl_search);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 赠品捆绑
     */
    class Madapter3 extends BaseAdapter {
        List<GetRecommendedInfo.goods_array.combo_list> combo_array;

        public Madapter3(
                List<GetRecommendedInfo.goods_array.combo_list> combo_array) {
            this.combo_array = combo_array;
        }

        /**
         * 添加
         *
         * @param combo
         */
        public void addItem(
                GetRecommendedInfo.goods_array.combo_list combo) {
            combo_array.add(combo);
            notifyDataSetChanged();
        }

        /**
         * 删除
         */
        public void deleteItem(int id) {
            if (id < combo_array.size()) {
                combo_array.remove(id);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return combo_array.size();
        }

        @Override
        public Object getItem(int position) {
            return combo_array == null ? null : combo_array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(RecommendedActivity.this, R.layout.item_giftbundled, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();
            GetRecommendedInfo.goods_array.combo_list combo_array = this.combo_array
                    .get(position);
            bitmapUtils.display(holder.iv_img, combo_array.goods_image);
            holder.tv_name.setText(combo_array.goods_name);
            holder.tv_number.setText("￥" + combo_array.goods_price);
            holder.iv_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                }
            });
            return convertView;
        }

        class ViewHodler {
            ImageView iv_img, iv_delete;
            TextView tv_name, tv_number;
            RelativeLayout cv_sm;

            public ViewHodler(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                cv_sm = (RelativeLayout) convertView.findViewById(R.id.cv_sm);
                convertView.setTag(this);
            }
        }
    }

    @Override
    public void ReGetData() {

    }

}
