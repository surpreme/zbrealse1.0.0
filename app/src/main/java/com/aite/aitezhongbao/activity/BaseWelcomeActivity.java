package com.aite.aitezhongbao.activity;

import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.login.LoginActivity;
import com.aite.aitezhongbao.adapter.WelcomeAdapter;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BaseWelcomeActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    protected WelcomeAdapter welcomeAdapter;
    protected List<String> urls = new ArrayList<>();
    @BindView(R.id.current_layout)
    LinearLayout current_layout;

    @Override
    protected int getLayoutResId() {
        return R.layout.welcome_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setTransparent(context);
        initiRecy();
        if (urls.size() == 0) {
            startActivity(LoginActivity.class);
            killThisActvity();
        }
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

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

    private void initiRecy() {
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler_view);
        welcomeAdapter = new WelcomeAdapter(this, urls);
        recycler_view.setAdapter(welcomeAdapter);
        current_layout.setGravity(Gravity.CENTER);
        current_layout.setOrientation(LinearLayout.HORIZONTAL);
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
}
