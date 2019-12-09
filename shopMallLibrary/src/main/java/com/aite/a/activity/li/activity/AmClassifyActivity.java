package com.aite.a.activity.li.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.aite.a.activity.GoodsListActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.AmClassify2Info;
import com.aite.a.model.AmClassify2Info.goods_class;
import com.aite.a.model.AmClassify2Info.goods_class.child;
import com.aite.a.model.CategoryList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.EditTextWithDel;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 *
 * @author Administrator
 */
public class AmClassifyActivity extends BaseActivity implements OnClickListener {
    private EditTextWithDel _search_edit;
    private View btn_search;
    private ListView lv_level1;
    private ListView gv_level2;
    private Level1Adapter level1Adapter;
    private Level2Adapter level2Adapter;
    private List<CategoryList> categoryOne;
    private AmClassify2Info amClassify2Info;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case one_category_id:
                    if (msg.obj != null) {
                        // 过滤数据,gc_show_in_menu=1的分类是需要显示的,其余的不显示,所以不添加进temp这个List中
                        List<CategoryList> msgObj = (List<CategoryList>) msg.obj;
                        List<CategoryList> temp = new ArrayList<CategoryList>();
                        for (int i = 0; i < msgObj.size(); i++) {
                            String gc_id = msgObj.get(i).getGc_id();
                            String gc_name = msgObj.get(i).getGc_name();
                            CategoryList categoryList = new CategoryList();
                            categoryList.setGc_id(gc_id);
                            categoryList.setGc_name(gc_name);
                            temp.add(categoryList);
                        }
                        level1Adapter = new Level1Adapter(categoryOne = temp, 0);
                        lv_level1.setAdapter(level1Adapter);
                        netRun.getClassify2(categoryOne.get(0).getGc_id());
                    } else {
                        CommonTools.showShortToast(AmClassifyActivity.this,
                                getI18n(R.string.act_no_data));
                    }
                    break;
                case one_category_err:
                    CommonTools.showShortToast(AmClassifyActivity.this,
                            getI18n(R.string.act_net_error));
                    break;
                case get_classify2_id:
                    // 二级分类
                    if (msg.obj != null) {
                        amClassify2Info = (AmClassify2Info) msg.obj;
                        level2Adapter = new Level2Adapter(
                                amClassify2Info.goods_class);
                        gv_level2.setAdapter(level2Adapter);
                    }
                    break;
                case get_classify2_err:
                    CommonTools.showShortToast(AmClassifyActivity.this,
                            getI18n(R.string.act_net_error));
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_amclassify);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _search_edit = (EditTextWithDel) findViewById(R.id._search_edit);
        btn_search = findViewById(R.id.btn_search);
        lv_level1 = (ListView) findViewById(R.id.lv_level1);
        gv_level2 = (ListView) findViewById(R.id.gv_level2);
        initView();
    }

    @Override
    protected void initView() {
        _search_edit.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });
        btn_search.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        netRun.getCategorOne();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            // 搜索
            search();
        }
    }

    private void search() {
        String key_wrods = _search_edit.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", key_wrods);
        if (!key_wrods.equals("")) {
            openActivity(GoodsListActivity.class, bundle);
        }
    }

    /**
     * 一级
     *
     * @author Administrator
     */
    private class Level1Adapter extends BaseAdapter {
        private List<CategoryList> list;
        private int choose;

        public Level1Adapter(List<CategoryList> list, int choose) {
            this.list = list;
            this.choose = choose;
        }

        /**
         * 修改选中
         *
         * @param id
         */
        public void setchoose(int id) {
            choose = id;
            notifyDataSetChanged();
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
                convertView = View.inflate(AmClassifyActivity.this,
                        R.layout.search_more_mainlist_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.Search_more_mainitem_layout
                    .setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            setchoose(position);
                            netRun.getClassify2(list.get(position).getGc_id());
                        }
                    });
            holder.Search_more_mainitem_txt.setText(list.get(position)
                    .getGc_name());
            holder.Search_more_mainitem_layout
                    .setBackgroundColor(0xfff8f8f8);
            if (choose == position) {
                holder.Search_more_mainitem_layout
                        .setBackgroundResource(R.drawable.list_bkg_img);
/*
				holder.Search_more_mainitem_layout
						.setBackgroundResource(R.drawable.search_more_mainlistselect);
*/
            }
            return convertView;
        }

        class ViewHolder {
            View Search_more_mainitem_layout;
            TextView Search_more_mainitem_txt;

            public ViewHolder(View convertView) {
                Search_more_mainitem_layout = convertView
                        .findViewById(R.id.Search_more_mainitem_layout);
                Search_more_mainitem_txt = convertView
                        .findViewById(R.id.Search_more_mainitem_txt);
                convertView.setTag(this);
            }
        }

    }

    /**
     * 二级
     *
     * @author Administrator
     */
    private class Level2Adapter extends BaseAdapter {
        private List<AmClassify2Info.goods_class> goods_class;

        public Level2Adapter(List<AmClassify2Info.goods_class> goods_class) {
            this.goods_class = goods_class;
        }

        /**
         * 刷新
         *
         * @param goods_class
         */
        public void Refresh(List<AmClassify2Info.goods_class> goods_class) {
            this.goods_class = goods_class;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return goods_class.size();
        }

        @Override
        public Object getItem(int position) {
            return goods_class == null ? null : goods_class.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(AmClassifyActivity.this,
                        R.layout.item_classify2, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final goods_class goods_class2 = goods_class.get(position);
            holder.tv_title.setText(goods_class2.gc_name);
            Glide.with(AmClassifyActivity.this).load(goods_class2.gc_image).into(holder.classify2Iv);
            holder.classify2Iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gc_id", goods_class2.gc_id);
                    bundle.putString("gc_name", goods_class2.gc_name);
                    openActivity(GoodsListActivity.class, bundle);
                }
            });
            Level3Adapter level3Adapter = new Level3Adapter(goods_class2.child);
            holder.mv_goodslist.setAdapter(level3Adapter);
            holder.tv_title.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gc_id", goods_class2.gc_id);
                    bundle.putString("gc_name", goods_class2.gc_name);
                    System.out.println("----------------gc_id="
                            + goods_class2.gc_id + "  gc_name="
                            + goods_class2.gc_name);
                    openActivity(GoodsListActivity.class, bundle);

                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView classify2Iv;
            MyGridView mv_goodslist;
            TextView tv_title;

            public ViewHolder(View convertView) {
                mv_goodslist = (MyGridView) convertView
                        .findViewById(R.id.mv_goodslist);
                tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                classify2Iv = convertView.findViewById(R.id.classify2_iv);
                convertView.setTag(this);
            }
        }

    }

    /**
     * 三级
     *
     * @author Administrator
     */
    private class Level3Adapter extends BaseAdapter {
        private List<child> child;

        public Level3Adapter(List<child> child) {
            this.child = child;
        }

        @Override
        public int getCount() {
            return child.size();
        }

        @Override
        public Object getItem(int position) {
            return child == null ? null : child.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(AmClassifyActivity.this,
                        R.layout.item_classify3, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final com.aite.a.model.AmClassify2Info.goods_class.child child2 = child
                    .get(position);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_img
                    .getLayoutParams();
            layoutParams.height = getw() / 4;
            holder.iv_img.setLayoutParams(layoutParams);

            bitmapUtils.display(holder.iv_img, child2.gc_image);
            holder.tv_name.setText(child2.gc_name);
            holder.iv_img.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gc_id", child2.gc_id);
                    bundle.putString("gc_name", child2.gc_name);
                    openActivity(GoodsListActivity.class, bundle);
                }
            });
            return convertView;
        }

        /*
         * index.php?act=goods&op=goods_list&gc_id=644
         * */
        class ViewHolder {
            ImageView iv_img;
            TextView tv_name;

            public ViewHolder(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(this);
            }
        }
    }

    private int getw() {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

}
