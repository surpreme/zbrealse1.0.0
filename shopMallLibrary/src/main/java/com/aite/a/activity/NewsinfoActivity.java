package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.Newsdetails;
import com.aite.a.model.Relatednewsinfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 新闻详情
 *
 * @author Administrator
 */
public class NewsinfoActivity extends BaseActivity implements OnClickListener {
    private TextView _tv_name, tv_headline, tv_name, tv_time, tv_pl, tv_zan,
            tv_submit;
    private EditText ed_input;
    private ImageView _iv_back, iv_title, iv_pl, iv_zan;
    private WebView webView;
    private MyListView mlv_related;
    private String url = null;
    private boolean firstLoading = false;
    private boolean isVisibleTop = false;
    private RelativeLayout title_ll;
    private BitmapUtils bitmapUtils;
    private RelatedAdapter relatedAdapter;
    private NetRun netRun;
    private Newsdetails newsdetails;
    private List<Relatednewsinfo> relatednewsinfo;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case news_info_id:
                    if (msg.obj != null) {
                        newsdetails = (Newsdetails) msg.obj;
                        iniv(newsdetails);
                        netRun.Relatednews(newsdetails.article_class_id);
                    }
                    break;
                case related_news_id:
                    if (msg.obj != null) {
                        relatednewsinfo = (List<Relatednewsinfo>) msg.obj;
                        relatedAdapter = new RelatedAdapter(relatednewsinfo);
                        mlv_related.setAdapter(relatedAdapter);
                    }
                    break;
                case add_comment_id:
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {
                            Toast.makeText(NewsinfoActivity.this,
                                    getString(R.string.evaluation_of_success),
                                    Toast.LENGTH_SHORT).show();
                            ed_input.setText("");
                        } else {
                            Toast.makeText(NewsinfoActivity.this, re,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case news_praise_id:
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {
                            Toast.makeText(NewsinfoActivity.this, getString(R.string.dianzanchenggong),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NewsinfoActivity.this, re,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsinfoweb_activity);
        findViewById();
        initView();
        initData();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_headline = (TextView) findViewById(R.id.tv_headline);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_pl = (TextView) findViewById(R.id.tv_pl);
        tv_zan = (TextView) findViewById(R.id.tv_zan);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        ed_input = (EditText) findViewById(R.id.ed_input);

        _iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        iv_pl = (ImageView) findViewById(R.id.iv_pl);
        iv_zan = (ImageView) findViewById(R.id.iv_zan);
        webView = (WebView) findViewById(R.id.web);
        mlv_related = (MyListView) findViewById(R.id.mlv_related);
    }

    @Override
    protected void initView() {
        _iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_pl.setOnClickListener(this);
        iv_zan.setOnClickListener(this);
        bitmapUtils = new BitmapUtils(this);
        netRun = new NetRun(this, handler);

    }

    /**
     * 初始化
     *
     * @param newsdetails
     */
    private void iniv(Newsdetails newsdetails) {
        tv_headline.setText(newsdetails.article_title);
        tv_name.setText(newsdetails.article_publisher_name);
        tv_time.setText(newsdetails.article_publish_time);
        tv_pl.setText(newsdetails.article_comment_count);
        tv_zan.setText(newsdetails.article_click);
        bitmapUtils.display(iv_title, newsdetails.publisher_avatar);

    }

    private String stringExtra;

    @Override
    protected void initData() {
        Intent intent2 = getIntent();
        stringExtra = intent2.getStringExtra("url");
        netRun.Newsinfo(stringExtra);
        url = "http://aitecc.com/wap/index.php?act=cms&op=show&article_id="
                + stringExtra;
        _tv_name.setText(getString(R.string.wenzhangxiangqing));
        _iv_back.setImageResource(R.drawable.jd_return);
        init(url);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.iv_zan) {
            // 赞
            netRun.Newspraise(stringExtra);
        } else if (v.getId() == R.id.iv_pl) {
            // 评论
            Intent intent2 = new Intent(NewsinfoActivity.this,
                    FoundCommenListActivity.class);
            intent2.putExtra("article_id", newsdetails.article_id);
            intent2.putExtra("article_comment_count",
                    newsdetails.article_comment_count);
            intent2.putExtra("article_click", newsdetails.article_click);
            intent2.putExtra("stringExtra", stringExtra);
            startActivityForResult(intent2, 0);
        }else if(v.getId()==R.id.tv_submit){
            if (TextUtils.isEmpty(ed_input.getText().toString())) {
                Toast.makeText(NewsinfoActivity.this,
                        getString(R.string.input_comments), Toast.LENGTH_SHORT)
                        .show();
            } else {
                // 提交评论
                netRun.Addcomment(newsdetails.article_id, ed_input.getText()
                        .toString());
            }
        }

//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.iv_zan:
//                // 赞
//                netRun.Newspraise(stringExtra);
//                break;
//            case R.id.iv_pl:
//                // 评论
//                Intent intent2 = new Intent(NewsinfoActivity.this,
//                        FoundCommenListActivity.class);
//                intent2.putExtra("article_id", newsdetails.article_id);
//                intent2.putExtra("article_comment_count",
//                        newsdetails.article_comment_count);
//                intent2.putExtra("article_click", newsdetails.article_click);
//                intent2.putExtra("stringExtra", stringExtra);
//                startActivityForResult(intent2, 0);
//                break;
//            case R.id.tv_submit:
//                if (TextUtils.isEmpty(ed_input.getText().toString())) {
//                    Toast.makeText(NewsinfoActivity.this,
//                            getString(R.string.input_comments), Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    // 提交评论
//                    netRun.Addcomment(newsdetails.article_id, ed_input.getText()
//                            .toString());
//                }
//                break;
//        }
    }

    /**
     * 相关
     *
     * @author Administrator
     */
    private class RelatedAdapter extends BaseAdapter {
        List<Relatednewsinfo> relatednewsinfo;

        public RelatedAdapter(List<Relatednewsinfo> relatednewsinfo) {
            this.relatednewsinfo = relatednewsinfo;
        }

        @Override
        public int getCount() {

            return relatednewsinfo.size();
        }

        @Override
        public Object getItem(int position) {

            return relatednewsinfo == null ? null : relatednewsinfo
                    .get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(NewsinfoActivity.this,
                        R.layout.recommendedlist_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            bitmapUtils.display(holder.iv_img,
                    relatednewsinfo.get(position).article_image);
            holder.tv_listname.setText(toStringHex(relatednewsinfo
                    .get(position).article_title));
            holder.tv_listtime
                    .setText(relatednewsinfo.get(position).article_comment_count
                            + getString(R.string.member_centre30) + "       "
                            + relatednewsinfo.get(position).article_publish_time);
            holder.ll_item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NewsinfoActivity.this,
                            NewsinfoActivity.class);
                    intent.putExtra("url",
                            relatednewsinfo.get(position).article_id);
                    startActivity(intent);
                    finish();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_listname, tv_listtime;
            ImageView iv_img;
            LinearLayout ll_item;

            public ViewHolder(View convertView) {
                tv_listname = (TextView) convertView
                        .findViewById(R.id.tv_listname);
                tv_listtime = (TextView) convertView
                        .findViewById(R.id.tv_listtime);
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
                convertView.setTag(this);
            }
        }
    }

    private void init(String url) {
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

    WebViewClient viewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
                view.loadUrl(url);
            }
            if (firstLoading == false) {
                if (title_ll.getVisibility() == View.VISIBLE)
                    title_ll.setVisibility(View.GONE);
                firstLoading = true;
            }

            return true;

        }

    };

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

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回上一页面
                return true;
            } else {
                if (title_ll.getVisibility() == View.VISIBLE) {
                    finish();
                    return false;
                }
                title_ll.setVisibility(View.VISIBLE);
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
            title_ll.setVisibility(View.VISIBLE);
        } else {
            isVisibleTop = true;
            title_ll.setVisibility(View.GONE);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 16进制转UTF-8
     *
     * @param s
     * @return
     */
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String replaceFirst = s.replaceFirst("&quot;", "“");
        String replaceAll = replaceFirst.replaceAll("&quot;", "”");
        return replaceAll;
    }
}
