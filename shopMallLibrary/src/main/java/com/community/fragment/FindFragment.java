package com.community.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.aite.a.fargment.BaseFragment;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.CreateGettogetherActivity;
import com.community.activity.ForumListActivity;
import com.community.activity.GettogetherListActivity;
import com.community.activity.PostGroupActivity;
import com.community.activity.ReleaseCircleoffriendsActivity;
import com.community.activity.ReleasePostActivity;
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
 * 发现
 * Created by mayn on 2018/10/10.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {
    private ImageView iv_postimg1, iv_postimg2, iv_postimg3, iv_button;
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

    private boolean mReceiverTag = false; // 广播接受者标识
    private RadioListening receiver = new RadioListening();//广播接收

    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case find_home_id:
                    if (msg.obj != null) {
                        findHomeInfo = (FindHomeInfo) msg.obj;
//                        setAD(findHomeInfo.adv_list);
                        findMenuAdapter = new FindMenuAdapter(getActivity(), findHomeInfo.nav_list);
                        gv_menu.setAdapter(findMenuAdapter);
                        findHotAdapter = new FindHotAdapter(getActivity(), findHomeInfo.hot_activity_list);
                        rv_hot.setAdapter(findHotAdapter);
                        dynamicAdapter = new DynamicAdapter(getActivity(), findHomeInfo.theme_list, handler, getActivity());
                        lv_dynamic.setAdapter(dynamicAdapter);
                        groupAdapter = new GroupAdapter(getActivity(), findHomeInfo.circle_list);
                        lv_group.setAdapter(groupAdapter);
                        findMenu2Adapter = new FindMenu2Adapter(getActivity(), findHomeInfo.cms_class_list);
                        gv_postmenu.setAdapter(findMenu2Adapter);
                        if (findHomeInfo.cms_article_list != null && findHomeInfo.cms_article_list.size() != 0) {
                            tv_title.setText(findHomeInfo.cms_article_list.get(0).article_title);
                            if (findHomeInfo.cms_article_list.get(0).article_image_all != null && findHomeInfo.cms_article_list.get(0).article_image_all.size() > 0) {
                                Glide.with(getActivity()).load(findHomeInfo.cms_article_list.get(0).article_image_all.get(0)).into(iv_postimg1);
                            }
                            if (findHomeInfo.cms_article_list.get(0).article_image_all != null && findHomeInfo.cms_article_list.get(0).article_image_all.size() > 1) {
                                Glide.with(getActivity()).load(findHomeInfo.cms_article_list.get(0).article_image_all.get(1)).into(iv_postimg2);
                            }
                            if (findHomeInfo.cms_article_list.get(0).article_image_all != null && findHomeInfo.cms_article_list.get(0).article_image_all.size() > 2) {
                                Glide.with(getActivity()).load(findHomeInfo.cms_article_list.get(0).article_image_all.get(2)).into(iv_postimg3);
                            }
                            tv_titme.setText(findHomeInfo.cms_article_list.get(0).article_publish_time + "\t" + findHomeInfo.cms_article_list.get(0).article_publisher_name);
                            articleAdapter = new ArticleAdapter(getActivity(), findHomeInfo.cms_article_list);
                            lv_post.setAdapter(articleAdapter);
                        }
                        if (findHomeInfo.adv_buttom != null && findHomeInfo.adv_buttom.size() != 0) {
                            Glide.with(getActivity()).load(findHomeInfo.adv_buttom.get(0).adv_pic).into(iv_button);
                        }
                        //
                    }
                    break;
                case find_home_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case dynamic_comment_id://评论
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            rl_input.setVisibility(View.GONE);
                            netRun.FindHome();
                        }
                    }
                    break;
                case dynamic_comment_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case praise_circleoffriends_id://点赞
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            netRun.FindHome();
                        }
                    }
                    break;
                case praise_circleoffriends_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case DYNAMIC://动态评论
                    if (msg.obj != null) {
                        Log.i("----------------","评论2");
                        dynamicid = (String) msg.obj;
                        rl_input.setVisibility(View.VISIBLE);
                    }
                    break;
                case PRAISECIRCLEOFFRIENDS://动态点赞
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        netRun.PraiseCircleoffriends(str);
                    }
                    break;
                case FINDREFRESH://刷新
                    if (dynamicAdapter != null) {
                        lv_dynamic.setAdapter(dynamicAdapter);
                    }
                    break;
            }
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && dynamicAdapter != null) {
            lv_dynamic.setAdapter(dynamicAdapter);
        }
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        iv_postimg1 = (ImageView) layout.findViewById(R.id.iv_postimg1);
        iv_postimg2 = (ImageView) layout.findViewById(R.id.iv_postimg2);
        iv_postimg3 = (ImageView) layout.findViewById(R.id.iv_postimg3);
        iv_button = (ImageView) layout.findViewById(R.id.iv_button);
        adgallery = (MyAdGallery) layout.findViewById(R.id.adgallery);
        ovalLayout = (LinearLayout) layout.findViewById(R.id.ovalLayout);
        gv_menu = (MyGridView) layout.findViewById(R.id.gv_menu);
        gv_postmenu = (MyGridView) layout.findViewById(R.id.gv_postmenu);
        rv_hot = (RecyclerView) layout.findViewById(R.id.rv_hot);
        tv_releasehd = (TextView) layout.findViewById(R.id.tv_releasehd);
        tv_releasedt = (TextView) layout.findViewById(R.id.tv_releasedt);
        tv_postmore = (TextView) layout.findViewById(R.id.tv_postmore);
        tv_groupmore = (TextView) layout.findViewById(R.id.tv_groupmore);
        tv_morepost = (TextView) layout.findViewById(R.id.tv_morepost);
        tv_title = (TextView) layout.findViewById(R.id.tv_title);
        tv_tv_releasetz = (TextView) layout.findViewById(R.id.tv_tv_releasetz);
        tv_gettogether = (TextView) layout.findViewById(R.id.tv_gettogether);
        tv_titme = (TextView) layout.findViewById(R.id.tv_titme);
        tv_sendmessage = (TextView) layout.findViewById(R.id.tv_sendmessage);
        lv_dynamic = (MyListView) layout.findViewById(R.id.lv_dynamic);
        lv_group = (MyListView) layout.findViewById(R.id.lv_group);
        lv_post = (MyListView) layout.findViewById(R.id.lv_post);
        rl_article1 = (RelativeLayout) layout.findViewById(R.id.rl_article1);
        rl_input = (RelativeLayout) layout.findViewById(R.id.rl_input);
        et_input = (EditText) layout.findViewById(R.id.et_input);
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
        netRun = new NetRun(getActivity(), handler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_hot.setLayoutManager(linearLayoutManager);//活动
        registerBR();
    }

    @Override
    protected void initData() {
        netRun.FindHome();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_releasehd) {//发布活动
            Intent intent = new Intent(getActivity(), CreateGettogetherActivity.class);
            startActivity(intent);
        } else if (id == R.id.tv_gettogether) {//更多活动
//                Intent intent3 = new Intent(getActivity(),
//                        WebActivity.class);
//                Bundle bundle3 = new Bundle();
//                bundle3.putString("url", findHomeInfo.hot_activity_list_url);
//                bundle3.putString("title", "更多活动");
//                intent3.putExtras(bundle3);
//                startActivity(intent3);
            Intent intent3 = new Intent(getActivity(), GettogetherListActivity.class);
            startActivity(intent3);
//                Intent intent2 = new Intent(getActivity(), GettogetherActivity.class);
//                startActivity(intent2);
        } else if (id == R.id.tv_postmore) {//更多帖子
            Intent intent4 = new Intent(getActivity(), ForumListActivity.class);
            startActivity(intent4);
//                Intent intent4 = new Intent(getActivity(),
//                        WebActivity.class);
//                Bundle bundle4 = new Bundle();
//                bundle4.putString("url", findHomeInfo.cms_class_list_url);
//                bundle4.putString("title", "更多帖子");
//                intent4.putExtras(bundle4);
//                startActivity(intent4);
//                Intent intent4 = new Intent(getActivity(), ForumSectionActivity.class);
//                startActivity(intent4);
//                Intent intent3 = new Intent(getActivity(), ForumActivity.class);
//                startActivity(intent3);
//                Intent intent3 = new Intent(getActivity(), PostGroupActivity.class);
//                startActivity(intent3);
//                Intent intent3 = new Intent(getActivity(), AdvisoryActivity.class);
//                startActivity(intent3);
        } else if (id == R.id.tv_releasedt) {//发布动态
//                Intent intent5 = new Intent(getActivity(),
//                        WebActivity.class);
//                Bundle bundle5 = new Bundle();
//                bundle5.putString("url", findHomeInfo.theme_list_url);
//                bundle5.putString("title", "发布动态");
//                intent5.putExtras(bundle5);
//                startActivity(intent5);
            Intent intent5 = new Intent(getActivity(), ReleaseCircleoffriendsActivity.class);
            startActivity(intent5);
        } else if (id == R.id.tv_groupmore) {//更多群组
//                Intent intent6 = new Intent(getActivity(),
//                        WebActivity.class);
//                Bundle bundle6 = new Bundle();
//                bundle6.putString("url", findHomeInfo.circle_list_url);
//                bundle6.putString("title", "更多群组");
//                intent6.putExtras(bundle6);
//                startActivity(intent6);
            Intent intent6 = new Intent(getActivity(), PostGroupActivity.class);
            startActivity(intent6);
        } else if (id == R.id.rl_article1) {//热门帖子
//                Intent intent7 = new Intent(getActivity(),
//                        WebActivity.class);
//                Bundle bundle7 = new Bundle();
//                bundle7.putString("url", findHomeInfo.circle_list_url);
//                bundle7.putString("title", "更多群组");
//                intent7.putExtras(bundle7);
//                startActivity(intent7);
            Intent intent7 = new Intent(getActivity(), ForumListActivity.class);
            startActivity(intent7);
        } else if (id == R.id.iv_button) {//底部广告
            Intent intent8 = new Intent(getActivity(),
                    WebActivity.class);
            Bundle bundle8 = new Bundle();
            bundle8.putString("url", findHomeInfo.circle_list_url);
            intent8.putExtras(bundle8);
            startActivity(intent8);
        } else if (id == R.id.tv_tv_releasetz) {//发帖
//                Intent intent9 = new Intent(getActivity(),
//                        WebActivity.class);
//                Bundle bundle9 = new Bundle();
//                bundle9.putString("url", findHomeInfo.cms_article_create_url);
//                bundle9.putString("title", "发帖");
//                intent9.putExtras(bundle9);
//                startActivity(intent9);
            Intent intent9 = new Intent(getActivity(), ReleasePostActivity.class);
            startActivity(intent9);
        } else if (id == R.id.tv_morepost) {//更多帖子
//                Intent intent10 = new Intent(getActivity(),
//                        WebActivity.class);
//                Bundle bundle10 = new Bundle();
//                bundle10.putString("url", findHomeInfo.cms_article_list_url);
//                bundle10.putString("title", "帖子");
//                intent10.putExtras(bundle10);
//                startActivity(intent10);
            Intent intent10 = new Intent(getActivity(), ForumListActivity.class);
            startActivity(intent10);
        } else if (id == R.id.rl_input) {//评论
            et_input.setText("");
            rl_input.setVisibility(View.GONE);
            if (dynamicAdapter != null) {
                lv_dynamic.setAdapter(dynamicAdapter);
            }
        } else if (id == R.id.tv_sendmessage) {//发送
            String s = et_input.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(getActivity(), getString(R.string.input_comments), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.DynamicComment(dynamicid, s);
            et_input.setText("");
        }
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
            adgallery.start(getActivity(), ADurl, null, 3000,
                    ovalLayout, R.drawable.dot_focused, R.drawable.dot_normal);
        adgallery.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                if (obj.get(curIndex).adv_pic_url != null
                        && obj.get(curIndex).adv_pic_url.length() != 0) {
                    Intent intent = new Intent(getActivity(),
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


    /**
     * 动态注册
     */
    private void registerBR() {
        // 过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction("refreshdata");
        // 优先级
        filter.setPriority(777);
        // 种类
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        // 登记接受
        if (!mReceiverTag) { // 避免重复注册广播
            mReceiverTag = true;
            getActivity().registerReceiver(receiver, filter);
        }
    }

    /**
     * 广播
     */
    public class RadioListening extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;
            if (intent.getAction().equals("refreshdata")) {
                Bundle bundle = intent.getExtras();
                int index = bundle.getInt("index", 0);
                handler.sendMessage(handler.obtainMessage(FINDREFRESH, index));
            }
        }
    }


}
