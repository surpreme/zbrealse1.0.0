package com.aite.mainlibrary.activity.allsetting;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Constant.MainUIConstant;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.minepostbookchriend.MinePostBookChriendActivity;
import com.aite.mainlibrary.adapter.IconHormationRecyAdapter;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-05
 * @desc:
 */
public class MinePostBookActivity extends BaseActivity {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    private IconHormationRecyAdapter iconHormationRecyAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.minepostbook_activity;
    }

    @Override
    protected void initView() {
        initToolbar("我的发布");
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(iconHormationRecyAdapter = new IconHormationRecyAdapter(context, MainUIConstant.MinePostServiceConstant.settingTv, MainUIConstant.MinePostServiceConstant.settingImg));
        iconHormationRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                startActivity(MinePostBookChriendActivity.class);

            }
        });
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


}
