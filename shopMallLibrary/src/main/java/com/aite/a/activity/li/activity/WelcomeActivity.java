package com.aite.a.activity.li.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aite.a.AppManager;
import com.aite.a.HomeTabActivity;
import com.aite.a.activity.li.adapter.WelcomeAdapter;
import com.aite.a.activity.li.bean.WelcomeBean;
import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.activity.li.util.StatusBarUtils;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {
    @BindView(R2.id.recycler_view)
    RecyclerView recycler_view;
    private WelcomeAdapter welcomeAdapter;
    private List<String> urls = new ArrayList<>();
    @BindView(R2.id.current_layout)
    LinearLayout current_layout;

    @Override
    protected int getLayoutResId() {
        return R.layout.recy_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setTransparent(context);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler_view);
        welcomeAdapter = new WelcomeAdapter(this, urls);
        recycler_view.setAdapter(welcomeAdapter);
        current_layout.setGravity(Gravity.CENTER);
        current_layout.setOrientation(LinearLayout.HORIZONTAL);
        welcomeAdapter.setOnclickViewListener(new WelcomeAdapter.OnclickViewListener() {
            @Override
            public void postion(int i) {
                SharedPreferences.Editor editor;
                SharedPreferences sp;
                AppManager.getInstance().killAllActivity();
                Intent intent = new Intent(context,
                        HomeTabActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                sp = getSharedPreferences("config", MODE_PRIVATE);
                editor = sp.edit();
                editor.putBoolean("guide", false);
                editor.commit();
                finish();
            }
        });
//        initCurrentLogo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
                        RecyclerView.LayoutManager layoutManager = recycler_view.getLayoutManager();
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取最后一个可见view的位置
                        int lastItemPosition = linearManager.findLastVisibleItemPosition();
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        int totalItemCount = linearManager.getItemCount();
                        initCurrentLogo(firstItemPosition + 1);
                        LogUtils.d(lastItemPosition + "   " + firstItemPosition + "fdss" + totalItemCount + "  " + current_layout.getChildCount());
                    }
                }
            });
        }
    }


    private void initCurrentLogo(int postion) {
        if (current_layout.getChildCount() > 0) {
            current_layout.removeAllViews();
        }
        for (int i = 0; i < urls.size(); i++) {
            if (postion - 1 == i) {
                ImageView current = new ImageView(context);
                current.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
                current_layout.addView(current, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                ImageView unCurrent = new ImageView(context);
                unCurrent.setImageDrawable(getResources().getDrawable(R.drawable.circular_selected));
                current_layout.addView(unCurrent, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    protected void initModel() {
        NetRun netRun = new NetRun(context);
        netRun.OnFirstWelcome(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                WelcomeBean welcomeBean = BeanConvertor.convertBean(responseInfo.result, WelcomeBean.class);
                for (int i = 0; welcomeBean.getDatas().getUpload_images().size() > i; i++)
                    urls.add(welcomeBean.getDatas().getUpload_images().get(i).getImg_path());
                LogUtils.d(responseInfo.result);
                welcomeAdapter.notifyDataSetChanged();
                initCurrentLogo(1);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
