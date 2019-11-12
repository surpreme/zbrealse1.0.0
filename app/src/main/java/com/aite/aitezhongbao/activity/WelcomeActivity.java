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
import com.aite.aitezhongbao.adapter.WelcomeAdapter;
import com.lzy.basemodule.view.StatusBarUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class WelcomeActivity extends BaseWelcomeActivity {
    @Override
    protected void initDatas() {
//        NetRun netRun = new NetRun(context);
//        netRun.OnFirstWelcome(new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                WelcomeBean welcomeBean = BeanConvertor.convertBean(responseInfo.result, WelcomeBean.class);
//                for (int i = 0; welcomeBean.getDatas().getUpload_images().size() > i; i++)
//                    urls.add(welcomeBean.getDatas().getUpload_images().get(i).getImg_path());
//                LogUtils.d(responseInfo.result);
//                welcomeAdapter.notifyDataSetChanged();
//                initCurrentLogo(1);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//            }
//        });
    }

    @Override
    protected void initResume() {
        welcomeAdapter.setOnStartclickViewListener(new WelcomeAdapter.OnclickViewListener() {
            @Override
            public void postion(int i) {

            }
        });

    }

    @Override
    protected void initReStart() {

    }
}
