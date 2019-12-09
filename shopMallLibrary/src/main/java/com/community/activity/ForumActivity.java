package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.community.adapter.Article2Adapter;
import com.community.adapter.ForumMenuAdapter;
import com.community.model.ForumInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子
 * Created by mayn on 2018/9/4.
 */
public class ForumActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_gobac, iv_search, iv_postimg1, iv_postimg2, iv_postimg3, iv_buttomimg;
    private MyAdGallery adgallery;
    private LinearLayout ovalLayout;
    private MyGridView gv_menu;
    private TextView tv_releasehd, tv_title, tv_morepost, tv_titme, tv_menu1, tv_menu2, tv_menu3, tv_menu4;
    private View v_slip1, v_slip2, v_slip3, v_slip4;
    private MyListView lv_post;

    private Article2Adapter article2Adapter;
    private ForumMenuAdapter forumMenuAdapter;
    private ForumInfo forumInfo;
    private String orderType = "1";
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case forum_home_id:
                    if (msg.obj != null) {
                        forumInfo = (ForumInfo) msg.obj;
                        setAD(forumInfo.adv_list);
                        forumMenuAdapter = new ForumMenuAdapter(ForumActivity.this, forumInfo.class_list);
                        gv_menu.setAdapter(forumMenuAdapter);
                        if (forumInfo.cms_article_list != null && forumInfo.cms_article_list.size() != 0) {
                            tv_title.setText(forumInfo.cms_article_list.get(0).article_title);
                            if (forumInfo.cms_article_list.get(0).article_image_all != null && forumInfo.cms_article_list.get(0).article_image_all.size() > 0) {
                                Glide.with(ForumActivity.this).load(forumInfo.cms_article_list.get(0).article_image_all.get(0)).into(iv_postimg1);
                            }
                            if (forumInfo.cms_article_list.get(0).article_image_all != null && forumInfo.cms_article_list.get(0).article_image_all.size() > 1) {
                                Glide.with(ForumActivity.this).load(forumInfo.cms_article_list.get(0).article_image_all.get(1)).into(iv_postimg2);
                            }
                            if (forumInfo.cms_article_list.get(0).article_image_all != null && forumInfo.cms_article_list.get(0).article_image_all.size() > 2) {
                                Glide.with(ForumActivity.this).load(forumInfo.cms_article_list.get(0).article_image_all.get(2)).into(iv_postimg3);
                            }
                            tv_titme.setText(forumInfo.cms_article_list.get(0).article_publish_time + "\t" + forumInfo.cms_article_list.get(0).article_publisher_name);
                            article2Adapter = new Article2Adapter(ForumActivity.this, forumInfo.cms_article_list);
                            lv_post.setAdapter(article2Adapter);
                        }
                        if (forumInfo.adv_buttom != null && forumInfo.adv_buttom.size() != 0) {
                            Glide.with(ForumActivity.this).load(forumInfo.adv_buttom.get(0).adv_pic).into(iv_buttomimg);
                        }
                    }
                    break;
                case forum_home_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_gobac = (ImageView) findViewById(R.id.iv_gobac);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_postimg1 = (ImageView) findViewById(R.id.iv_postimg1);
        iv_postimg2 = (ImageView) findViewById(R.id.iv_postimg2);
        iv_postimg3 = (ImageView) findViewById(R.id.iv_postimg3);
        iv_buttomimg = (ImageView) findViewById(R.id.iv_buttomimg);
        adgallery = (MyAdGallery) findViewById(R.id.adgallery);
        ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);
        gv_menu = (MyGridView) findViewById(R.id.gv_menu);
        tv_releasehd = (TextView) findViewById(R.id.tv_releasehd);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_morepost = (TextView) findViewById(R.id.tv_morepost);
        tv_titme = (TextView) findViewById(R.id.tv_titme);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
        tv_menu4 = (TextView) findViewById(R.id.tv_menu4);
        lv_post = (MyListView) findViewById(R.id.lv_post);
        v_slip1 = findViewById(R.id.v_slip1);
        v_slip2 = findViewById(R.id.v_slip2);
        v_slip3 = findViewById(R.id.v_slip3);
        v_slip4 = findViewById(R.id.v_slip4);
        initView();
    }

    @Override
    protected void initView() {
        iv_gobac.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        tv_menu3.setOnClickListener(this);
        tv_menu4.setOnClickListener(this);
        tv_releasehd.setOnClickListener(this);
        tv_morepost.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.ForumHome(orderType);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_gobac:
//                finish();
//                break;
//            case R.id.iv_search://搜索
//                Intent intent2 = new Intent(ForumActivity.this, SearchActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.tv_menu1:
//                orderType = "1";
//                pickmenu(tv_menu1, v_slip1);
//                break;
//            case R.id.tv_menu2:
//                orderType = "2";
//                pickmenu(tv_menu2, v_slip2);
//                break;
//            case R.id.tv_menu3:
//                orderType = "3";
//                pickmenu(tv_menu3, v_slip3);
//                break;
//            case R.id.tv_menu4:
//                orderType = "4";
//                pickmenu(tv_menu4, v_slip4);
//                break;
//            case R.id.tv_releasehd://发帖
////                Intent intent9 = new Intent(ForumActivity.this,
////                        WebActivity.class);
////                Bundle bundle9 = new Bundle();
////                bundle9.putString("url", forumInfo.cms_article_create_url);
////                bundle9.putString("title", "发帖");
////                intent9.putExtras(bundle9);
////                startActivity(intent9);
//                Intent intent = new Intent(ForumActivity.this, ReleasePostActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.tv_morepost://查看更多
//                Intent intent9 = new Intent(ForumActivity.this,
//                        WebActivity.class);
//                Bundle bundle9 = new Bundle();
//                bundle9.putString("url", forumInfo.cms_article_list_url);
//                bundle9.putString("title", getString(R.string.find_reminder53));
//                intent9.putExtras(bundle9);
//                startActivity(intent9);
//                break;
//        }
        if (v.getId() == R.id.iv_gobac) {
            finish();
        } else if (v.getId() == R.id.iv_search) {
            //搜索
            Intent intent2 = new Intent(ForumActivity.this, SearchActivity.class);
            startActivity(intent2);
        } else if (v.getId() == R.id.tv_menu1) {
            orderType = "1";
            pickmenu(tv_menu1, v_slip1);
        } else if (v.getId() == R.id.tv_menu2) {
            orderType = "2";
            pickmenu(tv_menu2, v_slip2);
        } else if (v.getId() == R.id.tv_menu3) {
            orderType = "3";
            pickmenu(tv_menu3, v_slip3);
        } else if (v.getId() == R.id.tv_menu4) {
            orderType = "4";
            pickmenu(tv_menu4, v_slip4);
        } else if (v.getId() == R.id.tv_releasehd) {
            Intent intent = new Intent(ForumActivity.this, ReleasePostActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_morepost) {
            //查看更多
            Intent intent9 = new Intent(ForumActivity.this,
                    WebActivity.class);
            Bundle bundle9 = new Bundle();
            bundle9.putString("url", forumInfo.cms_article_list_url);
            bundle9.putString("title", getString(R.string.find_reminder53));
            intent9.putExtras(bundle9);
            startActivity(intent9);
        }

    }

    public void pickmenu(TextView txt, View v) {
        tv_menu1.setTextColor(0xff000000);
        tv_menu2.setTextColor(0xff000000);
        tv_menu3.setTextColor(0xff000000);
        tv_menu4.setTextColor(0xff000000);
        v_slip1.setVisibility(View.INVISIBLE);
        v_slip2.setVisibility(View.INVISIBLE);
        v_slip3.setVisibility(View.INVISIBLE);
        v_slip4.setVisibility(View.INVISIBLE);
        v.setVisibility(View.VISIBLE);
        txt.setTextColor(0xffff3c81);
        netRun.ForumHome(orderType);
    }

    /**
     * 设置广告轮播
     *
     * @param obj
     */
    protected void setAD(final List<ForumInfo.adv_list> obj) {
        List<String> listAd = new ArrayList<String>();
        for (ForumInfo.adv_list string : obj) {
            listAd.add(string.adv_pic);
        }
        String[] ADurl = listAd.toArray(new String[listAd.size()]);
        if (adgallery.mUris == null)
            adgallery.start(ForumActivity.this, ADurl, null, 3000,
                    ovalLayout, R.drawable.dot_focused, R.drawable.dot_normal);
        adgallery.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                if (obj.get(curIndex).adv_pic_url != null
                        && obj.get(curIndex).adv_pic_url.length() != 0) {
                    Intent intent = new Intent(ForumActivity.this,
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
