package com.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.WebActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.adapter.ArticleAdapter;
import com.community.adapter.DynamicAdapter;
import com.community.adapter.FindHotAdapter;
import com.community.adapter.FindMenu2Adapter;
import com.community.adapter.FindMenuAdapter;
import com.community.adapter.GroupAdapter;
import com.community.model.FindHomeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现首页
 * Created by mayn on 2018/8/30.
 */

public class FindActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_gobac, iv_search, iv_postimg1, iv_postimg2, iv_postimg3, iv_button;
    private MyAdGallery adgallery;
    private LinearLayout ovalLayout;
    private MyGridView gv_menu, gv_postmenu;
    private RecyclerView rv_hot;
    private TextView tv_releasehd, tv_releasedt, tv_title, tv_tv_releasetz, tv_gettogether, tv_postmore, tv_groupmore, tv_titme, tv_morepost, tv_sendmessage;
    private MyListView lv_dynamic, lv_group, lv_post;
    private RelativeLayout rl_article1, rl_input;
    private EditText et_input;

    private String dynamicid;//评论ID

    private FindHomeInfo findHomeInfo;
    private FindMenuAdapter findMenuAdapter;
    private FindHotAdapter findHotAdapter;
    private DynamicAdapter dynamicAdapter;
    private FindMenu2Adapter findMenu2Adapter;
    private ArticleAdapter articleAdapter;
    private GroupAdapter groupAdapter;

    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case find_home_id:
                    if (msg.obj != null) {
                        findHomeInfo = (FindHomeInfo) msg.obj;
                        setAD(findHomeInfo.adv_list);
                        findMenuAdapter = new FindMenuAdapter(FindActivity.this, findHomeInfo.nav_list);
                        gv_menu.setAdapter(findMenuAdapter);
                        findHotAdapter = new FindHotAdapter(FindActivity.this, findHomeInfo.hot_activity_list);
                        rv_hot.setAdapter(findHotAdapter);
                        dynamicAdapter = new DynamicAdapter(FindActivity.this, findHomeInfo.theme_list, handler, FindActivity.this);
                        lv_dynamic.setAdapter(dynamicAdapter);
                        groupAdapter = new GroupAdapter(FindActivity.this, findHomeInfo.circle_list);
                        lv_group.setAdapter(groupAdapter);
                        findMenu2Adapter = new FindMenu2Adapter(FindActivity.this, findHomeInfo.cms_class_list);
                        gv_postmenu.setAdapter(findMenu2Adapter);
                        if (findHomeInfo.cms_article_list != null && findHomeInfo.cms_article_list.size() != 0) {
                            tv_title.setText(findHomeInfo.cms_article_list.get(0).article_title);
                            if (findHomeInfo.cms_article_list.get(0).article_image_all != null && findHomeInfo.cms_article_list.get(0).article_image_all.size() > 0) {
                                Glide.with(FindActivity.this).load(findHomeInfo.cms_article_list.get(0).article_image_all.get(0)).into(iv_postimg1);
                            }
                            if (findHomeInfo.cms_article_list.get(0).article_image_all != null && findHomeInfo.cms_article_list.get(0).article_image_all.size() > 1) {
                                Glide.with(FindActivity.this).load(findHomeInfo.cms_article_list.get(0).article_image_all.get(1)).into(iv_postimg2);
                            }
                            if (findHomeInfo.cms_article_list.get(0).article_image_all != null && findHomeInfo.cms_article_list.get(0).article_image_all.size() > 2) {
                                Glide.with(FindActivity.this).load(findHomeInfo.cms_article_list.get(0).article_image_all.get(2)).into(iv_postimg3);
                            }
                            tv_titme.setText(findHomeInfo.cms_article_list.get(0).article_publish_time + "\t" + findHomeInfo.cms_article_list.get(0).article_publisher_name);
                            articleAdapter = new ArticleAdapter(FindActivity.this, findHomeInfo.cms_article_list);
                            lv_post.setAdapter(articleAdapter);
                        }
                        if (findHomeInfo.adv_buttom != null && findHomeInfo.adv_buttom.size() != 0) {
                            Glide.with(FindActivity.this).load(findHomeInfo.adv_buttom.get(0).adv_pic).into(iv_button);
                        }
                        //
                    }
                    break;
                case find_home_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case dynamic_comment_id://评论
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            rl_input.setVisibility(View.GONE);
                            netRun.FindHome();
                        }
                    }
                    break;
                case dynamic_comment_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case DYNAMIC://动态评论
                    Log.i("--------------", "评论1");
                    if (msg.obj != null) {

                        dynamicid = (String) msg.obj;
                        rl_input.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_gobac = (ImageView) findViewById(R.id.iv_gobac);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_postimg1 = (ImageView) findViewById(R.id.iv_postimg1);
        iv_postimg2 = (ImageView) findViewById(R.id.iv_postimg2);
        iv_postimg3 = (ImageView) findViewById(R.id.iv_postimg3);
        iv_button = (ImageView) findViewById(R.id.iv_button);
        adgallery = (MyAdGallery) findViewById(R.id.adgallery);
        ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);
        gv_menu = (MyGridView) findViewById(R.id.gv_menu);
        gv_postmenu = (MyGridView) findViewById(R.id.gv_postmenu);
        rv_hot = (RecyclerView) findViewById(R.id.rv_hot);
        tv_releasehd = (TextView) findViewById(R.id.tv_releasehd);
        tv_releasedt = (TextView) findViewById(R.id.tv_releasedt);
        tv_postmore = (TextView) findViewById(R.id.tv_postmore);
        tv_groupmore = (TextView) findViewById(R.id.tv_groupmore);
        tv_morepost = (TextView) findViewById(R.id.tv_morepost);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_tv_releasetz = (TextView) findViewById(R.id.tv_tv_releasetz);
        tv_gettogether = (TextView) findViewById(R.id.tv_gettogether);
        tv_titme = (TextView) findViewById(R.id.tv_titme);
        tv_sendmessage = (TextView) findViewById(R.id.tv_sendmessage);
        lv_dynamic = (MyListView) findViewById(R.id.lv_dynamic);
        lv_group = (MyListView) findViewById(R.id.lv_group);
        lv_post = (MyListView) findViewById(R.id.lv_post);
        rl_article1 = (RelativeLayout) findViewById(R.id.rl_article1);
        rl_input = (RelativeLayout) findViewById(R.id.rl_input);
        et_input = (EditText) findViewById(R.id.et_input);
        initView();
    }

    @Override
    protected void initView() {
        iv_gobac.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        tv_gettogether.setOnClickListener(this);
        tv_postmore.setOnClickListener(this);
        tv_releasehd.setOnClickListener(this);
        tv_releasedt.setOnClickListener(this);
        tv_groupmore.setOnClickListener(this);
        rl_article1.setOnClickListener(this);
        tv_tv_releasetz.setOnClickListener(this);
        iv_button.setOnClickListener(this);
        tv_morepost.setOnClickListener(this);
        rl_input.setOnClickListener(this);
        tv_sendmessage.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_hot.setLayoutManager(linearLayoutManager);//活动
        initData();
    }

    @Override
    protected void initData() {
        netRun.FindHome();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_gobac) {
            finish();
        } else if (v.getId() == R.id.tv_releasehd) {
            Intent intent = new Intent(FindActivity.this, CreateGettogetherActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.iv_search) {
            Intent intent2 = new Intent(FindActivity.this, SearchActivity.class);
            startActivity(intent2);
        } else if (v.getId() == R.id.tv_gettogether) {
            Intent intent3 = new Intent(FindActivity.this, GettogetherListActivity.class);
            startActivity(intent3);
        } else if (v.getId() == R.id.tv_postmore) {
            Intent intent4 = new Intent(FindActivity.this, ForumListActivity.class);
            startActivity(intent4);
        } else if (v.getId() == R.id.tv_releasedt) {
            Intent intent5 = new Intent(FindActivity.this, ReleaseCircleoffriendsActivity.class);
            startActivity(intent5);
        } else if (v.getId() == R.id.rl_article1) {
            Intent intent7 = new Intent(FindActivity.this, ForumListActivity.class);
            startActivity(intent7);
        } else if (v.getId() == R.id.iv_button) {
            //底部广告
            Intent intent8 = new Intent(FindActivity.this,
                    WebActivity.class);
            Bundle bundle8 = new Bundle();
            bundle8.putString("url", findHomeInfo.circle_list_url);
            intent8.putExtras(bundle8);
            startActivity(intent8);
        } else if (v.getId() == R.id.tv_tv_releasetz) {
            Intent intent9 = new Intent(FindActivity.this, ReleasePostActivity.class);
            startActivity(intent9);
        } else if (v.getId() == R.id.tv_morepost) {
            Intent intent10 = new Intent(FindActivity.this, ForumListActivity.class);
            startActivity(intent10);
        } else if (v.getId() == R.id.rl_input) {
            //评论
            et_input.setText("");
            rl_input.setVisibility(View.GONE);
        } else if (v.getId() == R.id.tv_sendmessage) {
            String s = et_input.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(this, getString(R.string.input_comments), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.DynamicComment(dynamicid, s);
            et_input.setText("");
        }

//        switch (v.getId()) {
//            case R.id.iv_gobac:
//                finish();
//                break;
//            case R.id.tv_releasehd://发布活动
////                Intent intent = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle = new Bundle();
////                bundle.putString("url", findHomeInfo.hot_activity_create_url);
////                bundle.putString("title", "发布活动");
////                intent.putExtras(bundle);
////                startActivity(intent);
//                Intent intent = new Intent(FindActivity.this, CreateGettogetherActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.iv_search://搜索
//                Intent intent2 = new Intent(FindActivity.this, SearchActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.tv_gettogether://更多活动
////                Intent intent3 = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle3 = new Bundle();
////                bundle3.putString("url", findHomeInfo.hot_activity_list_url);
////                bundle3.putString("title", "更多活动");
////                intent3.putExtras(bundle3);
////                startActivity(intent3);
//                Intent intent3 = new Intent(FindActivity.this, GettogetherListActivity.class);
//                startActivity(intent3);
////                Intent intent2 = new Intent(FindActivity.this, GettogetherActivity.class);
////                startActivity(intent2);
//                break;
//            case R.id.tv_postmore://更多帖子
//                Intent intent4 = new Intent(FindActivity.this, ForumListActivity.class);
//                startActivity(intent4);
////                Intent intent4 = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle4 = new Bundle();
////                bundle4.putString("url", findHomeInfo.cms_class_list_url);
////                bundle4.putString("title", "更多帖子");
////                intent4.putExtras(bundle4);
////                startActivity(intent4);
////                Intent intent4 = new Intent(FindActivity.this, ForumSectionActivity.class);
////                startActivity(intent4);
////                Intent intent3 = new Intent(FindActivity.this, ForumActivity.class);
////                startActivity(intent3);
////                Intent intent3 = new Intent(FindActivity.this, PostGroupActivity.class);
////                startActivity(intent3);
////                Intent intent3 = new Intent(FindActivity.this, AdvisoryActivity.class);
////                startActivity(intent3);
//                break;
//            case R.id.tv_releasedt://发布动态
////                Intent intent5 = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle5 = new Bundle();
////                bundle5.putString("url", findHomeInfo.theme_list_url);
////                bundle5.putString("title", "发布动态");
////                intent5.putExtras(bundle5);
////                startActivity(intent5);
//                Intent intent5 = new Intent(FindActivity.this, ReleaseCircleoffriendsActivity.class);
//                startActivity(intent5);
//                break;
//            case R.id.tv_groupmore://更多群组
////                Intent intent6 = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle6 = new Bundle();
////                bundle6.putString("url", findHomeInfo.circle_list_url);
////                bundle6.putString("title", "更多群组");
////                intent6.putExtras(bundle6);
////                startActivity(intent6);
//                Intent intent6 = new Intent(FindActivity.this, PostGroupActivity.class);
//                startActivity(intent6);
//                break;
//            case R.id.rl_article1://热门帖子
////                Intent intent7 = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle7 = new Bundle();
////                bundle7.putString("url", findHomeInfo.circle_list_url);
////                bundle7.putString("title", "更多群组");
////                intent7.putExtras(bundle7);
////                startActivity(intent7);
//                Intent intent7 = new Intent(FindActivity.this, ForumListActivity.class);
//                startActivity(intent7);
//                break;
//            case R.id.iv_button://底部广告
//                Intent intent8 = new Intent(FindActivity.this,
//                        WebActivity.class);
//                Bundle bundle8 = new Bundle();
//                bundle8.putString("url", findHomeInfo.circle_list_url);
//                intent8.putExtras(bundle8);
//                startActivity(intent8);
//                break;
//            case R.id.tv_tv_releasetz://发帖
////                Intent intent9 = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle9 = new Bundle();
////                bundle9.putString("url", findHomeInfo.cms_article_create_url);
////                bundle9.putString("title", "发帖");
////                intent9.putExtras(bundle9);
////                startActivity(intent9);
//                Intent intent9 = new Intent(FindActivity.this, ReleasePostActivity.class);
//                startActivity(intent9);
//                break;
//            case R.id.tv_morepost://更多帖子
////                Intent intent10 = new Intent(FindActivity.this,
////                        WebActivity.class);
////                Bundle bundle10 = new Bundle();
////                bundle10.putString("url", findHomeInfo.cms_article_list_url);
////                bundle10.putString("title", "帖子");
////                intent10.putExtras(bundle10);
////                startActivity(intent10);
//                Intent intent10 = new Intent(FindActivity.this, ForumListActivity.class);
//                startActivity(intent10);
//                break;
//            case R.id.rl_input://评论
//                et_input.setText("");
//                rl_input.setVisibility(View.GONE);
//                break;
//            case R.id.tv_sendmessage://发送
//                String s = et_input.getText().toString();
//                if (TextUtils.isEmpty(s)) {
//                    Toast.makeText(this, getString(R.string.input_comments), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.DynamicComment(dynamicid, s);
//                et_input.setText("");
//                break;
//        }
    }


    /**
     * 设置广告轮播
     *
     * @param obj
     */
    protected void setAD(final List<FindHomeInfo.adv_list> obj) {
        List<String> listAd = new ArrayList<String>();
        for (FindHomeInfo.adv_list string : obj) {
            listAd.add(string.adv_pic);
        }
        String[] ADurl = listAd.toArray(new String[listAd.size()]);
        if (adgallery.mUris == null)
            adgallery.start(FindActivity.this, ADurl, null, 3000,
                    ovalLayout, R.drawable.dot_focused, R.drawable.dot_normal);
        adgallery.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                if (obj.get(curIndex).adv_pic_url != null
                        && obj.get(curIndex).adv_pic_url.length() != 0) {
                    Intent intent = new Intent(FindActivity.this,
                            WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", obj.get(curIndex).adv_pic_url);
                    bundle.putString("title", obj.get(curIndex).adv_title);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void ReGetData() {

    }

}
