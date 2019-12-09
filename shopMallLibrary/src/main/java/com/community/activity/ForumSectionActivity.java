package com.community.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.adapter.Article3Adapter;
import com.community.adapter.ForumSectionAdapter;
import com.community.model.ForumSectionInfo;

/**
 * 帖子板块
 * Created by mayn on 2018/9/4.
 */
public class ForumSectionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_gobac, iv_search, iv_postimg1, iv_postimg2, iv_postimg3;
    private CircleImageView iv_icon;
    private TextView tv_name, tv_all, tv_essence, tv_num, attention, tv_description, tv_title,tv_titme;
    private MyListView lv_hot, lv_post;
    private String class_id;

    private Article3Adapter article3Adapter;
    private ForumSectionAdapter forumSectionAdapter;
    private ForumSectionInfo porumSectionInfo;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case forumsection_home_id:
                    if (msg.obj!=null){
                        porumSectionInfo= (ForumSectionInfo) msg.obj;
                        Glide.with(ForumSectionActivity.this).load(porumSectionInfo.class_info.class_thumb).into(iv_icon);
                        tv_name.setText(porumSectionInfo.class_info.class_name);
                        tv_all.setText(porumSectionInfo.class_info.article_total);
                        tv_essence.setText(porumSectionInfo.class_info.article_essence_total);
                        tv_num.setText(porumSectionInfo.class_info.article_curday_total);
                        tv_description.setText(porumSectionInfo.class_info.class_desc);
                        if (porumSectionInfo.class_info.is_like.equals("0")){
                            attention.setText(getString(R.string.find_reminder47));
                        }else{
                            attention.setText(getString(R.string.find_reminder54));
                        }
                        forumSectionAdapter=new ForumSectionAdapter(ForumSectionActivity.this,porumSectionInfo.cms_article_hot_list);
                        lv_hot.setAdapter(forumSectionAdapter);
                        if (porumSectionInfo.cms_article_list != null && porumSectionInfo.cms_article_list.size() != 0) {
                            tv_title.setText(porumSectionInfo.cms_article_list.get(0).article_title);
                            if (porumSectionInfo.cms_article_list.get(0).article_image_all != null && porumSectionInfo.cms_article_list.get(0).article_image_all.size() > 0) {
                                Glide.with(ForumSectionActivity.this).load(porumSectionInfo.cms_article_list.get(0).article_image_all.get(0)).into(iv_postimg1);
                            }
                            if (porumSectionInfo.cms_article_list.get(0).article_image_all != null && porumSectionInfo.cms_article_list.get(0).article_image_all.size() > 1) {
                                Glide.with(ForumSectionActivity.this).load(porumSectionInfo.cms_article_list.get(0).article_image_all.get(1)).into(iv_postimg2);
                            }
                            if (porumSectionInfo.cms_article_list.get(0).article_image_all != null && porumSectionInfo.cms_article_list.get(0).article_image_all.size() > 2) {
                                Glide.with(ForumSectionActivity.this).load(porumSectionInfo.cms_article_list.get(0).article_image_all.get(2)).into(iv_postimg3);
                            }
                            tv_titme.setText(porumSectionInfo.cms_article_list.get(0).article_publish_time + "\t" + porumSectionInfo.cms_article_list.get(0).article_publisher_name);
                            article3Adapter = new Article3Adapter(ForumSectionActivity.this, porumSectionInfo.cms_article_list);
                            lv_post.setAdapter(article3Adapter);
                        }
                    }
                    break;
                case forumsection_home_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case like_class_id://关注
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        netRun.ForumSectionHome(class_id);
                    }
                    break;
                case like_class_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumsection);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_gobac = (ImageView) findViewById(R.id.iv_gobac);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_postimg1 = (ImageView) findViewById(R.id.iv_postimg1);
        iv_postimg2 = (ImageView) findViewById(R.id.iv_postimg2);
        iv_postimg3 = (ImageView) findViewById(R.id.iv_postimg3);
        iv_icon = (CircleImageView) findViewById(R.id.iv_icon);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_essence = (TextView) findViewById(R.id.tv_essence);
        tv_num = (TextView) findViewById(R.id.tv_num);
        attention = (TextView) findViewById(R.id.attention);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_titme = (TextView) findViewById(R.id.tv_titme);
        lv_hot = (MyListView) findViewById(R.id.lv_hot);
        lv_post = (MyListView) findViewById(R.id.lv_post);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_gobac.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        attention.setOnClickListener(this);
        class_id=getIntent().getStringExtra("class_id");
        initData();
    }

    @Override
    protected void initData() {
        netRun.ForumSectionHome(class_id);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_gobac:
//                finish();
//                break;
//            case R.id.iv_search://搜索
//                break;
//            case R.id.attention://加关注
//                netRun.LikeClass(class_id);
////                if (porumSectionInfo.class_info.is_like.equals("0")){
////                }else{
////                }
//                break;
//        }
        if(v.getId()==R.id.iv_gobac){
            finish();
        }else if(v.getId()==R.id.attention){
            //加关注
            netRun.LikeClass(class_id);
        }
    }

    @Override
    public void ReGetData() {

    }
}
