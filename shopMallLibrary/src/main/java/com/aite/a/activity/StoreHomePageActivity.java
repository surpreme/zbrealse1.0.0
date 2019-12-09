package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.StoreHomePageInfo;
import com.aite.a.model.StoreHomePageList.datas.list;
import com.aite.a.model.StoreHomePageList;
import com.aite.a.model.StoreHomePagePJInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 店铺主页
 *
 * @author Administrator
 */
public class StoreHomePageActivity extends BaseActivity implements
        OnClickListener {
    private ImageView iv_stimg, _iv_back, _iv_right;
    private TextView tv_stname, tv_credit, tv_turnover, tv_rate, tv_case,
            tv_sell, tv_credit2, tv_stspeed, tv_stquality, tv_stattitude,
            _tv_name, tv_contact, tv_buy;
    private View v_1, v_2, v_3;
    private LinearLayout i_introduce, i_credit;
    private MyListView lv_sell, lv_pj;
    private WebView webView;
    private RatingBar re_stspeed, re_stquality, re_stattitude;
    private NetRun netRun;
    private BitmapUtils bitmaputils;
    private StoreHomePageInfo storeHomePageInfo;
    private StoreHomePageList storeHomePageList;
    private StoreHomePagePJInfo storeHomePagePJInfo;
    private Recommended2Adapter recommended2Adapter;
    private mComments mcomments;
    private String store_qq = null;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case store_details_id:
                    if (msg.obj != null) {
                        storeHomePageInfo = (StoreHomePageInfo) msg.obj;
                        // 初始化服务商信息
                        initinformation(storeHomePageInfo);
                        // web
                        init(storeHomePageInfo.storedel.introduce_url);
                    }
                    break;
                case store_details_err:

                    break;
                case store_collection_id:
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        Toast.makeText(StoreHomePageActivity.this, re,
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case storegoods_list_id:
                    // 服务列表
                    if (msg.obj != null) {
                        storeHomePageList = (StoreHomePageList) msg.obj;
                        // // 出售的服务
                        recommended2Adapter = new Recommended2Adapter(
                                storeHomePageList.datas.list);
                        lv_sell.setAdapter(recommended2Adapter);
                    }
                    break;
                case storegoods_list_err:
                    Toast.makeText(StoreHomePageActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case store_list_id:
                    // 评分列表
                    if (msg.obj != null) {
                        storeHomePagePJInfo = (StoreHomePagePJInfo) msg.obj;
                        // 评价
                        if (storeHomePagePJInfo.datas.list != null
                                && storeHomePagePJInfo.datas.list.size() != 0) {
                            mcomments = new mComments(
                                    storeHomePagePJInfo.datas.list);
                            lv_pj.setAdapter(mcomments);
                        }
                    }
                    break;
                case store_list_err:
                    Toast.makeText(StoreHomePageActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };
    private String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceproviders);
        findViewById();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        iv_stimg = (ImageView) findViewById(R.id.iv_stimg);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _iv_right = (ImageView) findViewById(R.id._iv_right);
        tv_stname = (TextView) findViewById(R.id.tv_stname);
        tv_credit = (TextView) findViewById(R.id.tv_credit);
        tv_turnover = (TextView) findViewById(R.id.tv_turnover);
        tv_rate = (TextView) findViewById(R.id.tv_rate);
        tv_case = (TextView) findViewById(R.id.tv_case);
        tv_sell = (TextView) findViewById(R.id.tv_sell);
        tv_credit2 = (TextView) findViewById(R.id.tv_credit2);
        tv_stspeed = (TextView) findViewById(R.id.tv_stspeed);
        tv_stquality = (TextView) findViewById(R.id.tv_stquality);
        tv_stattitude = (TextView) findViewById(R.id.tv_stattitude);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        v_1 = findViewById(R.id.v_1);
        v_2 = findViewById(R.id.v_2);
        v_3 = findViewById(R.id.v_3);
        i_introduce = (LinearLayout) findViewById(R.id.i_introduce);
        i_credit = (LinearLayout) findViewById(R.id.i_credit);
        lv_sell = (MyListView) findViewById(R.id.lv_sell);
        lv_pj = (MyListView) findViewById(R.id.lv_pj);
        webView = (WebView) findViewById(R.id.wv_introduce);
        re_stspeed = (RatingBar) findViewById(R.id.re_stspeed);
        re_stquality = (RatingBar) findViewById(R.id.re_stquality);
        re_stattitude = (RatingBar) findViewById(R.id.re_stattitude);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        bitmaputils = new BitmapUtils(this);
        _tv_name.setText(getString(R.string.storehome));
        initData();
    }

    @Override
    protected void initData() {
        tv_case.setOnClickListener(this);
        tv_sell.setOnClickListener(this);
        tv_credit2.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        // _iv_right.setOnClickListener(this);
        _iv_right.setVisibility(View.VISIBLE);
        tv_contact.setOnClickListener(this);
        tv_buy.setOnClickListener(this);
        store_id = getIntent().getStringExtra("store_id");
        netRun.Storedetails(store_id);
        netRun.StoreGoodsList(store_id);
        netRun.StoreList(store_id);
        // _iv_right.setImageResource(R.drawable.service_collection);

    }

    /**
     * 初始化店铺信息
     */
    private void initinformation(StoreHomePageInfo Info) {
        bitmaputils.display(iv_stimg, Info.storedel.store_image_url);
        tv_stname.setText(Info.storedel.store_name);
        tv_credit.setText(Info.storedel.goods_count == null ? "0"
                : Info.storedel.goods_count);
        tv_turnover.setText(getString(R.string.currency)
                + Info.storedel.order_amount == null ? "0￥"
                : Info.storedel.order_amount.equals("null") ? "0￥"
                : Info.storedel.order_amount + "￥");
        tv_rate.setText(Info.storedel.uu == null ? "0%" : Info.storedel.uu
                + "%");

        tv_stspeed.setText(Info.eval_info.employers_d);
        tv_stquality.setText(Info.eval_info.employers_a);
        tv_stattitude.setText(Info.eval_info.employers_p);
        store_qq = Info.storedel.store_qq;
        try {
            int deliverycredit = Integer.valueOf(Info.eval_info.employers_d)
                    .intValue();
            int desccredit = Integer.valueOf(Info.eval_info.employers_a)
                    .intValue();
            int servicecredit = Integer.valueOf(Info.eval_info.employers_p)
                    .intValue();
            re_stspeed.setRating(deliverycredit);
            re_stquality.setRating(desccredit);
            re_stattitude.setRating(servicecredit);
        } catch (Exception e) {
            System.out.println("----------------评分错误");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_case) {
            // 案列
            v_1.setVisibility(View.VISIBLE);
            v_2.setVisibility(View.INVISIBLE);
            v_3.setVisibility(View.INVISIBLE);
            i_introduce.setVisibility(View.VISIBLE);
            lv_sell.setVisibility(View.GONE);
            i_credit.setVisibility(View.GONE);
        } else if (v.getId() == R.id.tv_sell) {
            // 出售的服务
            v_2.setVisibility(View.VISIBLE);
            v_1.setVisibility(View.INVISIBLE);
            v_3.setVisibility(View.INVISIBLE);
            lv_sell.setVisibility(View.VISIBLE);
            i_introduce.setVisibility(View.GONE);
            i_credit.setVisibility(View.GONE);
        } else if (v.getId() == R.id.tv_credit2) {
            // 评价信用
            v_3.setVisibility(View.VISIBLE);
            v_2.setVisibility(View.INVISIBLE);
            v_1.setVisibility(View.INVISIBLE);
            i_credit.setVisibility(View.VISIBLE);
            i_introduce.setVisibility(View.GONE);
            lv_sell.setVisibility(View.GONE);
        } else if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id._iv_right) {
            // 收藏
            netRun.StoreCollection(store_id);
        } else if (v.getId() == R.id.tv_contact) {
            // 联系
            try {
                if (store_qq != null && !store_qq.equals("")) {
                    String url = "mqqwpa://im/main_chat?chat_type=wpa&uin="
                            + store_qq;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } else {
                    Toast.makeText(StoreHomePageActivity.this,
                            getString(R.string.nocustomerservice),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(StoreHomePageActivity.this,
                        getString(R.string.nocustomerservice2),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.tv_buy) {
            // 收藏
            netRun.StoreCollection(store_id);
        }
//		switch (v.getId()) {
//		case R.id.tv_case:
//			// 案列
//			v_1.setVisibility(View.VISIBLE);
//			v_2.setVisibility(View.INVISIBLE);
//			v_3.setVisibility(View.INVISIBLE);
//			i_introduce.setVisibility(View.VISIBLE);
//			lv_sell.setVisibility(View.GONE);
//			i_credit.setVisibility(View.GONE);
//			break;
//		case R.id.tv_sell:
//			// 出售的服务
//			v_2.setVisibility(View.VISIBLE);
//			v_1.setVisibility(View.INVISIBLE);
//			v_3.setVisibility(View.INVISIBLE);
//			lv_sell.setVisibility(View.VISIBLE);
//			i_introduce.setVisibility(View.GONE);
//			i_credit.setVisibility(View.GONE);
//			break;
//		case R.id.tv_credit2:
//			// 评价信用
//			v_3.setVisibility(View.VISIBLE);
//			v_2.setVisibility(View.INVISIBLE);
//			v_1.setVisibility(View.INVISIBLE);
//			i_credit.setVisibility(View.VISIBLE);
//			i_introduce.setVisibility(View.GONE);
//			lv_sell.setVisibility(View.GONE);
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id._iv_right:
//			// 收藏
//			netRun.StoreCollection(store_id);
//			break;
//		case R.id.tv_contact:
//			// 联系
//			try {
//				if (store_qq != null && !store_qq.equals("")) {
//					String url = "mqqwpa://im/main_chat?chat_type=wpa&uin="
//							+ store_qq;
//					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//				} else {
//					Toast.makeText(StoreHomePageActivity.this,
//							getString(R.string.nocustomerservice),
//							Toast.LENGTH_SHORT).show();
//				}
//			} catch (Exception e) {
//				Toast.makeText(StoreHomePageActivity.this,
//						getString(R.string.nocustomerservice2),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.tv_buy:
//			// 收藏
//			netRun.StoreCollection(store_id);
//			break;
//		}
    }

    /***
     * 出售的服务适配
     *
     * @author Administrator
     */
    private class Recommended2Adapter extends BaseAdapter {
        List<list> list;

        public Recommended2Adapter(List<list> list) {
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
                convertView = View.inflate(StoreHomePageActivity.this,
                        R.layout.recommended_servic_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            bitmaputils.display(holder.iv_serviceprovidersimg2,
                    list.get(position).fullpath);
            holder.tv_serviceprovidersname2
                    .setText(list.get(position).goods_name);
            holder.tv_type.setVisibility(View.GONE);
            if (list.get(position).goods_price != null
                    && list.get(position).goods_price != null) {
                holder.tv_price.setText("￥" + list.get(position).goods_price);
            } else {
                holder.tv_price.setText("");
            }
            holder.tv_sellnumber.setText(list.get(position).goods_salenum);
            holder.ll_svitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 详情
                    Intent intent = new Intent(StoreHomePageActivity.this,
                            ServiceDetails.class);
                    intent.putExtra("goods_id", list.get(position).goods_id);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_serviceprovidersimg2;
            TextView tv_serviceprovidersname2, tv_type, tv_price,
                    tv_sellnumber;
            LinearLayout ll_svitem;

            public ViewHolder(View convertView) {
                iv_serviceprovidersimg2 = (ImageView) convertView
                        .findViewById(R.id.iv_serviceprovidersimg2);
                tv_serviceprovidersname2 = (TextView) convertView
                        .findViewById(R.id.tv_serviceprovidersname2);
                tv_type = (TextView) convertView.findViewById(R.id.tv_type);
                tv_price = (TextView) convertView
                        .findViewById(R.id.tv_fcfpricee);
                tv_sellnumber = (TextView) convertView
                        .findViewById(R.id.tv_sellnumber);
                ll_svitem = (LinearLayout) convertView
                        .findViewById(R.id.ll_svitem);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 评论适配
     *
     * @author Administrator
     */
    private class mComments extends BaseAdapter {
        List<StoreHomePagePJInfo.datas.list> list;

        public mComments(
                List<StoreHomePagePJInfo.datas.list> list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(StoreHomePageActivity.this,
                        R.layout.service_pjitem, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            StoreHomePagePJInfo.datas.list list2 = list
                    .get(position);
            holder.tv_username.setText(list2.seval_membername);
            holder.tv_centent.setText(list2.seval_content);
            holder.tv_time.setText(TimeStamp2Date(list2.seval_addtime,
                    "yyyy-MM-dd"));
            try {
                int deliverycredit = Integer.valueOf(list2.seval_desccredit)
                        .intValue();
                int desccredit = Integer.valueOf(list2.seval_servicecredit)
                        .intValue();
                int servicecredit = Integer.valueOf(list2.seval_deliverycredit)
                        .intValue();

                holder.re_stspeed.setRating(deliverycredit);
                holder.re_stspeed2.setRating(desccredit);
                holder.re_stspeed3.setRating(servicecredit);
            } catch (Exception e) {
                System.out.println("----------------评分错误");
            }
            return convertView;
        }

        class ViewHolder {
            TextView tv_centent, tv_username, tv_time;
            RatingBar re_stspeed, re_stspeed2, re_stspeed3;

            public ViewHolder(View convertView) {
                tv_centent = (TextView) convertView
                        .findViewById(R.id.tv_centent);
                tv_username = (TextView) convertView
                        .findViewById(R.id.tv_username);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                re_stspeed = (RatingBar) convertView
                        .findViewById(R.id.re_stspeed);
                re_stspeed2 = (RatingBar) convertView
                        .findViewById(R.id.re_stspeed2);
                re_stspeed3 = (RatingBar) convertView
                        .findViewById(R.id.re_stspeed3);
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

    private void init(String url) {
        Log.i("sssss", url);
        // WebView加载web资源
        webView.loadUrl(url);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(viewClient);
        webView.addJavascriptInterface(object, "demo");
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(wvcc);
        WebSettings webSettings = webView.getSettings();
        // webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

    }// 改写物理按键——返回的逻辑

    Object object = new Object() {
        public void clickOnAndroid() {
            mHandler.post(new Runnable() {
                public void run() {
                    webView.loadUrl("javascript:wave()");
                }
            });
        }
    };
    private boolean firstLoading = false;
    private boolean isVisibleTop = false;
    //
    WebViewClient viewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
                view.loadUrl(url);
            }
            if (firstLoading == false) {
                // if (title_ll.getVisibility() == View.VISIBLE)
                // title_ll.setVisibility(View.GONE);
                firstLoading = true;
            }
            //
            return true;

        }

    };
    //
    WebChromeClient wvcc = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // message就是wave函数里alert的字符串，这样你就可以在android客户端里对这个数据进行处理
            result.confirm();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            if (newProgress == 100) {
                // 网页加载完成
                mdialog.dismiss();
            } else {
                // 加载中
//				mdialog.setMessage(getI18n(R.string.act_loading));
                mdialog.show();
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            // tv_title_name.setText(title);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回上一页面
                return true;
            } else {
                // if (title_ll.getVisibility() == View.VISIBLE) {
                finish();
                // return false;
                // }
                // title_ll.setVisibility(View.VISIBLE);
                isVisibleTop = false;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isVisibleTop == true) {
            isVisibleTop = false;
            // title_ll.setVisibility(View.VISIBLE);
        } else {
            isVisibleTop = true;
            // title_ll.setVisibility(View.GONE);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
