package com.aite.mainlibrary.activity.allsetting.minerural;


import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MineRuralActivity extends BaseActivity<MineRuralContract.View, MineRuralPresenter> implements MineRuralContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @Override
    protected int getLayoutResId() {
        return R.layout.mine_collectage;
    }

    @Override
    protected void initView() {
        initToolbar("我的社区");

    }

    @Override
    protected void initDatas() {
        mPresenter.GetMineList(initParams());

    }
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }
    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetMineListSuccess(Object msg) {

    }
}
