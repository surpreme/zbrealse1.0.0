package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.Foundcommenlistinfo;
import com.aite.a.model.Foundcommenlistinfo.comment_list;
import com.aite.a.model.NewsCommenlistinfo;
import com.aite.a.model.Topicscommentlistinfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.lingshi;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 评论列表
 *
 * @author Administrator
 */
public class FoundCommenListActivity extends BaseActivity implements
        OnClickListener {
    private TextView _tv_name, tv_shouye, tv_shangyiye, tv_pagenumber,
            tv_xiayiye, tv_weiye, tv_submit, tv_pl, tv_zan;
    private ImageView _iv_back, iv_zan, iv_pl;
    private ListView lv_communit;
    private EditText ed_input;

    private NetRun netRun;
    private BitmapUtils bitmaputils;
    private Foundcommenlistinfo foundcommenlistinfo;
    private CommenAdapter commenAdapter;
    private NewsCommenlistinfo newsCommenlistinfo;
    private NewsAdapter newsAdapter;
    private Topicscommentlistinfo topicscommentlistinfo;
    private TopicsAdapter topicsAdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case found_commenlist_id:
                    // 推荐商品评价列表
                    if (msg.obj != null) {
                        foundcommenlistinfo = (Foundcommenlistinfo) msg.obj;
                        if (commenAdapter == null) {
                            commenAdapter = new CommenAdapter(foundcommenlistinfo);
                            lv_communit.setAdapter(commenAdapter);
                        } else {
                            commenAdapter.addList(foundcommenlistinfo.comment_list);
                        }
                    }
                    break;
                case found_commenlist_err:
                    Toast.makeText(FoundCommenListActivity.this,
                            getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case recommended_evaluation_id:
                    // 推荐商品评价
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        Toast.makeText(FoundCommenListActivity.this, re,
                                Toast.LENGTH_SHORT).show();
                        ed_input.setText("");
                        commenAdapter = null;
                        System.out.println("----------------隐藏软键盘");
                        /** 隐藏软键盘 **/
                        View view = getWindow().peekDecorView();
                        if (view != null) {
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(
                                    view.getWindowToken(), 0);
                        }
                        netRun.foundcommenlist(commend_id, comment_type, pager + "");
                    }
                    break;
                case recommended_evaluation_err:
                    Toast.makeText(FoundCommenListActivity.this,
                            getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case wz_commentlist_id:
                    // 新闻评价列表
                    /** 隐藏软键盘 **/
                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(
                                view.getWindowToken(), 0);
                    }
                    if (msg.obj != null) {
                        newsCommenlistinfo = (NewsCommenlistinfo) msg.obj;
                        if (newsAdapter == null) {
                            newsAdapter = new NewsAdapter(newsCommenlistinfo);
                            lv_communit.setAdapter(newsAdapter);
                        } else {
                            newsAdapter.addList(newsCommenlistinfo.comment_list);
                        }
                    }
                    break;
                case add_comment_id:
                    // 新闻评价
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {

                            /** 隐藏软键盘 **/
                            View vieww = getWindow().peekDecorView();
                            if (vieww != null) {
                                InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputmanger.hideSoftInputFromWindow(
                                        vieww.getWindowToken(), 0);
                            }

                            Toast.makeText(FoundCommenListActivity.this,
                                    getString(R.string.evaluation_of_success),
                                    Toast.LENGTH_SHORT).show();
                            ed_input.setText("");
                            newsAdapter = null;
                            netRun.Wzcommentlist(article_id, pager + "");
                        } else {
                            Toast.makeText(FoundCommenListActivity.this, re,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case news_praise_id:
                    // 新闻-赞
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {
                            Toast.makeText(FoundCommenListActivity.this,
                                    getString(R.string.dianzanchenggong),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FoundCommenListActivity.this, re,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case topics_commentlist_id:
                    if (msg.obj != null) {
                        // 推荐话题评论列表
                        topicscommentlistinfo = (Topicscommentlistinfo) msg.obj;
                        if (topicsAdapter == null) {
                            topicsAdapter = new TopicsAdapter(
                                    topicscommentlistinfo.comment_list);
                            lv_communit.setAdapter(topicsAdapter);
                        } else {
                            topicsAdapter
                                    .addList(topicscommentlistinfo.comment_list);
                        }
                    }
                    break;
                case topics_comment_id:
                    // 推荐话题评论
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {
                            /** 隐藏软键盘 **/
                            View vieww = getWindow().peekDecorView();
                            if (vieww != null) {
                                InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputmanger.hideSoftInputFromWindow(
                                        vieww.getWindowToken(), 0);
                            }

                            Toast.makeText(FoundCommenListActivity.this,
                                    getString(R.string.evaluation_of_success),
                                    Toast.LENGTH_SHORT).show();
                            ed_input.setText("");
                            topicsAdapter = null;
                            netRun.Topicscommentlist(commend_id, "1");
                        } else {
                            Toast.makeText(FoundCommenListActivity.this, re,
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
        setContentView(R.layout.activity_commenlist);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_shouye = (TextView) findViewById(R.id.tv_shouye);
        tv_shangyiye = (TextView) findViewById(R.id.tv_shangyiye);
        tv_pagenumber = (TextView) findViewById(R.id.tv_pagenumber);
        tv_xiayiye = (TextView) findViewById(R.id.tv_xiayiye);
        tv_weiye = (TextView) findViewById(R.id.tv_weiye);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        lv_communit = (ListView) findViewById(R.id.lv_communit);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        ed_input = (EditText) findViewById(R.id.ed_input);
        tv_pl = (TextView) findViewById(R.id.tv_pl);
        tv_zan = (TextView) findViewById(R.id.tv_zan);
        iv_zan = (ImageView) findViewById(R.id.iv_zan);
        iv_pl = (ImageView) findViewById(R.id.iv_pl);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.commencist));
        _iv_back.setImageResource(R.drawable.jd_return);
        _iv_back.setOnClickListener(this);
        tv_shouye.setOnClickListener(this);
        tv_shangyiye.setOnClickListener(this);
        tv_xiayiye.setOnClickListener(this);
        tv_weiye.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_zan.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        lv_communit.setOnScrollListener(listener);
        initData();
    }

    private String commend_id = null;
    private String article_comment_count = null;
    private String article_click = null;
    private String stringExtra = null;
    private String type = null;
    private String article_id = null;
    private String comment_type = null;
    private Boolean isnews = false;

    @Override
    protected void initData() {
        commend_id = getIntent().getStringExtra("commend_id");
        article_comment_count = getIntent().getStringExtra(
                "article_comment_count");
        article_click = getIntent().getStringExtra("article_click");
        stringExtra = getIntent().getStringExtra("stringExtra");
        type = getIntent().getStringExtra("type");
        if (type == null) {
            if (commend_id != null && !commend_id.equals("")) {
                // 商品
                isnews = false;
                comment_type = getIntent().getStringExtra("comment_type");
                netRun.foundcommenlist(commend_id, comment_type, "" + pager);
            } else {
                // 新闻
                isnews = true;
                tv_pl.setText(article_comment_count);
                tv_zan.setText(article_click);

                article_id = getIntent().getStringExtra("article_id");
                netRun.Wzcommentlist(article_id, pager + "");
            }
        } else {
            if (type.equals(getString(R.string.foundmenu2))) {
                tv_pl.setText(article_comment_count);
                tv_zan.setText(article_click);
                netRun.foundcommenlist(commend_id, comment_type, "" + pager);
            } else if (type.equals(getString(R.string.join_merchant5))) {
                iv_pl.setVisibility(View.GONE);
                iv_zan.setVisibility(View.GONE);
                netRun.Topicscommentlist(commend_id, "" + pager);
            }
        }
    }

    private int pager = 1;

    private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if (lv_communit.getLastVisiblePosition() == (lv_communit.getCount() - 1)) {
                if (pager < (countpager())) {
                    pager++;
                    if (type == null) {
                        if (!isnews) {
                            netRun.foundcommenlist(commend_id, comment_type,
                                    pager + "");
                        } else {
                            netRun.Wzcommentlist(article_id, pager + "");
                        }
                    } else {
                        if (type.equals(getString(R.string.foundmenu2))) {
                            netRun.foundcommenlist(commend_id, comment_type, ""
                                    + pager);
                        } else if (type.equals(getString(R.string.join_merchant5))) {
                            netRun.Topicscommentlist(commend_id, "" + pager);
                        }
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
        }
    };

    /**
     * 计算总页数
     *
     * @return
     */
    private int countpager() {
        int count = 1;
        if (type == null) {
            if (!isnews) {
                count = Integer
                        .parseInt(foundcommenlistinfo.micro_info.comment_count);
            } else {
                count = Integer.parseInt(newsCommenlistinfo.comment_count);
            }
        } else {
            if (type.equals(getString(R.string.foundmenu2))) {
                count = Integer
                        .parseInt(foundcommenlistinfo.micro_info.comment_count);
            } else if (type.equals(getString(R.string.join_merchant5))) {
                count = Integer.parseInt(lingshi.getInstance().page_total);
            }
        }
        if (count <= 10) {
            count = 1;
        } else {
            count = count % 10;
        }
        return count;
    }

    @Override
    public void ReGetData() {
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                Intent intent2 = new Intent(FoundCommenListActivity.this,
//                        InformationActivity.class);
//                setResult(0, intent2);
//                finish();
//                break;
//            case R.id.tv_shouye:
//                break;
//            case R.id.tv_shangyiye:
//                break;
//            case R.id.tv_xiayiye:
//                break;
//            case R.id.tv_weiye:
//                break;
//            case R.id.iv_zan:
//                // 赞
//                netRun.Newspraise(stringExtra);
//                break;
//            case R.id.tv_submit:
//                // 提交
//                if (TextUtils.isEmpty(ed_input.getText().toString())) {
//                    Toast.makeText(FoundCommenListActivity.this,
//                            getString(R.string.input_comments), Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    if (type == null) {
//                        if (!isnews) {
//                            // 商品评论
//                            netRun.RecommendedEvaluation(commend_id, comment_type,
//                                    ed_input.getText().toString());
//                        } else {
//                            // 新闻评论
//                            netRun.Addcomment(article_id, ed_input.getText()
//                                    .toString());
//                        }
//                    } else {
//                        if (type.equals(getString(R.string.foundmenu2))) {
//                            netRun.RecommendedEvaluation(commend_id, comment_type,
//                                    ed_input.getText().toString());
//                        } else if (type.equals(getString(R.string.join_merchant5))) {
//                            netRun.Topicscomment(commend_id, ed_input.getText()
//                                    .toString());
//                        }
//                    }
//                }
//                break;
//        }
        if (v.getId() == R.id.tv_submit) {
            // 提交
            if (TextUtils.isEmpty(ed_input.getText().toString())) {
                Toast.makeText(FoundCommenListActivity.this,
                        getString(R.string.input_comments), Toast.LENGTH_SHORT)
                        .show();
            } else {
                if (type == null) {
                    if (!isnews) {
                        // 商品评论
                        netRun.RecommendedEvaluation(commend_id, comment_type,
                                ed_input.getText().toString());
                    } else {
                        // 新闻评论
                        netRun.Addcomment(article_id, ed_input.getText()
                                .toString());
                    }
                } else {
                    if (type.equals(getString(R.string.foundmenu2))) {
                        netRun.RecommendedEvaluation(commend_id, comment_type,
                                ed_input.getText().toString());
                    } else if (type.equals(getString(R.string.join_merchant5))) {
                        netRun.Topicscomment(commend_id, ed_input.getText()
                                .toString());
                    }
                }
            }
        } else if (v.getId() == R.id.iv_zan) {
            // 赞
            netRun.Newspraise(stringExtra);
        } else if (v.getId() == R.id._iv_back) {
            Intent intent2 = new Intent(FoundCommenListActivity.this,
                    InformationActivity.class);
            setResult(0, intent2);
            finish();
        }
    }

    /**
     * 商品评论列表
     *
     * @author Administrator
     */
    private class CommenAdapter extends BaseAdapter {
        Foundcommenlistinfo foundcommenlistinfo;

        // 添加数据
        public void addList(List<comment_list> comment_list) {
            if (comment_list == null) {
                comment_list = new Foundcommenlistinfo().comment_list;
            }
            foundcommenlistinfo.comment_list.addAll(comment_list);
            notifyDataSetChanged();
        }

        public CommenAdapter(Foundcommenlistinfo foundcommenlistinfo) {
            this.foundcommenlistinfo = foundcommenlistinfo;
        }

        @Override
        public int getCount() {

            return foundcommenlistinfo.comment_list.size();
        }

        @Override
        public Object getItem(int position) {

            return foundcommenlistinfo.comment_list == null ? null
                    : foundcommenlistinfo.comment_list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(FoundCommenListActivity.this,
                        R.layout.found_commen_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            comment_list comment_list = foundcommenlistinfo.comment_list
                    .get(position);
            bitmapUtils.display(holder.iv_title, comment_list.member_avatar);
            holder.tv_name.setText(comment_list.member_name);
            holder.tv_centent.setText(comment_list.comment_message);
            holder.tv_time.setText(comment_list.comment_time);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_title;
            TextView tv_name, tv_centent, tv_time;

            public ViewHolder(View convertView) {
                iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_centent = (TextView) convertView
                        .findViewById(R.id.tv_centent);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 新闻评论列表
     *
     * @author Administrator
     */
    private class NewsAdapter extends BaseAdapter {
        NewsCommenlistinfo newsCommenlistinfo;

        // 添加数据
        public void addList(
                List<NewsCommenlistinfo.comment_list> comment_list) {
            if (comment_list == null) {
                comment_list = new NewsCommenlistinfo().comment_list;
            }
            newsCommenlistinfo.comment_list.addAll(comment_list);
            notifyDataSetChanged();
        }

        public NewsAdapter(NewsCommenlistinfo newsCommenlistinfo) {
            this.newsCommenlistinfo = newsCommenlistinfo;
        }

        @Override
        public int getCount() {
            return newsCommenlistinfo.comment_list.size();
        }

        @Override
        public Object getItem(int position) {

            return newsCommenlistinfo.comment_list == null ? null
                    : newsCommenlistinfo.comment_list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(FoundCommenListActivity.this,
                        R.layout.found_commen_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            NewsCommenlistinfo.comment_list comment_list = newsCommenlistinfo.comment_list
                    .get(position);
            bitmapUtils.display(holder.iv_title, comment_list.member_avatar);
            holder.tv_name.setText(comment_list.member_name);
            holder.tv_centent.setText(comment_list.comment_message);
            holder.tv_time.setText(TimeStamp2Date(comment_list.comment_time,
                    "yyyy-MM-dd HH:mm:ss"));
            return convertView;
        }

        class ViewHolder {
            ImageView iv_title;
            TextView tv_name, tv_centent, tv_time;

            public ViewHolder(View convertView) {
                iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_centent = (TextView) convertView
                        .findViewById(R.id.tv_centent);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 话题
     *
     * @author Administrator
     */
    private class TopicsAdapter extends BaseAdapter {
        List<Topicscommentlistinfo.comment_list> comment_list;

        // 添加数据
        public void addList(
                List<Topicscommentlistinfo.comment_list> comment_list) {
            if (comment_list == null) {
                comment_list = new Topicscommentlistinfo().comment_list;
            }
            this.comment_list.addAll(comment_list);
            notifyDataSetChanged();
        }

        public TopicsAdapter(
                List<Topicscommentlistinfo.comment_list> comment_list2) {
            this.comment_list = comment_list2;
        }

        @Override
        public int getCount() {
            return comment_list.size();
        }

        @Override
        public Object getItem(int position) {

            return comment_list == null ? null : comment_list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(FoundCommenListActivity.this,
                        R.layout.found_commen_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            Topicscommentlistinfo.comment_list comment_list2 = comment_list
                    .get(position);
            bitmapUtils.display(holder.iv_title, comment_list2.member_avatar);
            holder.tv_name.setText(comment_list2.member_name);
            holder.tv_centent.setText(comment_list2.reply_content);
            holder.tv_time.setText(comment_list2.reply_addtime);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_title;
            TextView tv_name, tv_centent, tv_time;

            public ViewHolder(View convertView) {
                iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_centent = (TextView) convertView
                        .findViewById(R.id.tv_centent);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString
     * @param formats
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }
}
