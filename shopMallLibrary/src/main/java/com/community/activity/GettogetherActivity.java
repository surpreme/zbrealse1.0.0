package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.community.adapter.GettogetherHotAdapter;
import com.community.adapter.GettogetherMenuAdapter;
import com.community.adapter.GettogetherimgAdapter;
import com.community.adapter.OfficialAdapter;
import com.community.model.GettogetherInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动
 * Created by mayn on 2018/8/31.
 */
public class GettogetherActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback;
    private TextView tv_new, tv_hotmore, tv_official;
    private MyAdGallery adgallery;
    private MyListView lv_img;
    private LinearLayout ovalLayout;
    private MyGridView gv_menu;
    private RecyclerView rv_hot, rv_official;

    private GettogetherMenuAdapter gettogetherMenuAdapter;
    private GettogetherHotAdapter gettogetherHotAdapter;
    private GettogetherimgAdapter gettogetherimgAdapter;
    private OfficialAdapter officialAdapter;
    private GettogetherInfo gettogetherInfo;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case gettogether_home_id:
                    if (msg.obj != null) {
                        gettogetherInfo = (GettogetherInfo) msg.obj;
                        setAD(gettogetherInfo.adv_list);
                        gettogetherMenuAdapter = new GettogetherMenuAdapter(GettogetherActivity.this, gettogetherInfo.class_list);
                        gv_menu.setAdapter(gettogetherMenuAdapter);
                        gettogetherHotAdapter = new GettogetherHotAdapter(GettogetherActivity.this, gettogetherInfo.hot_list);
                        rv_hot.setAdapter(gettogetherHotAdapter);
                        officialAdapter = new OfficialAdapter(GettogetherActivity.this, gettogetherInfo.off_list);
                        rv_official.setAdapter(officialAdapter);
                        gettogetherimgAdapter = new GettogetherimgAdapter(GettogetherActivity.this, gettogetherInfo.adv_buttom);
                        lv_img.setAdapter(gettogetherimgAdapter);
                    }
                    break;
                case gettogether_home_err:
                    Toast.makeText(appSingleton, getString(R.string.placedatthetop), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettogether);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        tv_new = (TextView) findViewById(R.id.tv_new);
        tv_official = (TextView) findViewById(R.id.tv_official);
        tv_hotmore = (TextView) findViewById(R.id.tv_hotmore);
        adgallery = (MyAdGallery) findViewById(R.id.adgallery);
        ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);
        gv_menu = (MyGridView) findViewById(R.id.gv_menu);
        rv_hot = (RecyclerView) findViewById(R.id.rv_hot);
        rv_official = (RecyclerView) findViewById(R.id.rv_official);
        lv_img = (MyListView) findViewById(R.id.lv_img);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_goback.setOnClickListener(this);
        tv_official.setOnClickListener(this);
        tv_hotmore.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_hot.setLayoutManager(linearLayoutManager);//活动
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_official.setLayoutManager(linearLayoutManager2);//官方
        initData();
    }

    @Override
    protected void initData() {
        netRun.GettogetherHome();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if (v.getId()==R.id.tv_new){
            Intent intent1 = new Intent(GettogetherActivity.this, CreateGettogetherActivity.class);
            startActivity(intent1);
        }else if(v.getId()==R.id.tv_hotmore){
            Intent intent = new Intent(GettogetherActivity.this, GettogetherListActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.tv_official){
            Intent intent2 = new Intent(GettogetherActivity.this, WebActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("url", gettogetherInfo.off_list_url);
            bundle2.putString("title", getString(R.string.find_reminder61));
            intent2.putExtras(bundle2);
            startActivity(intent2);
        }

//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_new:
//                Intent intent1 = new Intent(GettogetherActivity.this, CreateGettogetherActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.tv_hotmore:
////                Intent intent = new Intent(GettogetherActivity.this,WebActivity.class);
////                Bundle bundle = new Bundle();
////                bundle.putString("url", gettogetherInfo.hot_list_url);
////                bundle.putString("title", "热门活动");
////                intent.putExtras(bundle);
////                startActivity(intent);
//                Intent intent = new Intent(GettogetherActivity.this, GettogetherListActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.tv_official:
//                Intent intent2 = new Intent(GettogetherActivity.this, WebActivity.class);
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("url", gettogetherInfo.off_list_url);
//                bundle2.putString("title", getString(R.string.find_reminder61));
//                intent2.putExtras(bundle2);
//                startActivity(intent2);
//                break;
//        }
    }

    /**
     * 设置广告轮播
     *
     * @param obj
     */
    protected void setAD(final List<GettogetherInfo.adv_list> obj) {
        List<String> listAd = new ArrayList<String>();
        for (GettogetherInfo.adv_list string : obj) {
            listAd.add(string.adv_pic);
        }
        String[] ADurl = listAd.toArray(new String[listAd.size()]);
        if (adgallery.mUris == null)
            adgallery.start(GettogetherActivity.this, ADurl, null, 3000,
                    ovalLayout, R.drawable.dot_focused, R.drawable.dot_normal);
        adgallery.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {

            @Override
            public void onItemClick(int curIndex) {
                if (obj.get(curIndex).adv_pic_url != null
                        && obj.get(curIndex).adv_pic_url.length() != 0) {
                    Intent intent = new Intent(GettogetherActivity.this,
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
