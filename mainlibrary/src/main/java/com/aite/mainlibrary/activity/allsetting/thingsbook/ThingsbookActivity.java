package com.aite.mainlibrary.activity.allsetting.thingsbook;


import com.aite.mainlibrary.R;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;
import com.lzy.okgo.model.HttpParams;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ThingsBookFragment extends BaseActivity<ThingsbookContract.View, ThingsbookPresenter> implements ThingsbookContract.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.thingbook_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        mPresenter.getinformation(initParams());

    }

    /**
     * key	get	字符串	必须			会员登录key
     * curpage	get	字符串	必须	1		当前页码
     * state	get	整型	必须	0		状态 0全部 1待付款 2已付款 3已完成 4评价 5已取消
     * page_type	get	整型	必须	1		页面类型 1日托 2培训 3就业 4助残活动 5其他服务
     *
     * @return
     */
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("curpage", 1);
        httpParams.put("state", 1);
        httpParams.put("curpage", 1);

        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetinformationSuccess(Object msg) {

    }
}
