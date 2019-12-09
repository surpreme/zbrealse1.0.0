package com.aite.mainlibrary.activity.allsetting.bindinguser;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.BinderUserListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.addbindinguser.AddBindingUserActivity;
import com.aite.mainlibrary.adapter.BinderUserRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;
import com.lzy.basemodule.util.FileUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BindingUserActivity extends BaseActivity<BindingUserContract.View, BindingUserPresenter> implements BindingUserContract.View {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.bank_recy)
    RecyclerView bankRecy;
    @BindView(R2.id.bottom_btn)
    Button bottomBtn;
    private BinderUserRecyAdapter binderUserRecyAdapter;
    private List<BinderUserListBean.ListBean> binderUserListBeans = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.binding_user;
    }

    @Override
    protected void initView() {
        tvTitle.setText("绑定账号");
        ivBack.setOnClickListener(this);
        bottomBtn.setOnClickListener(this);
        bankRecy.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bankRecy.setAdapter(binderUserRecyAdapter = new BinderUserRecyAdapter(context, binderUserListBeans));
        binderUserRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.bottom_btn) startActivity(AddBindingUserActivity.class);

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    @Override
    protected void initDatas() {
        mPresenter.getInformation(initParams());

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetInformationSuccess(Object msg) {
        binderUserListBeans.addAll(((BinderUserListBean) msg).getList());
        binderUserRecyAdapter.notifyDataSetChanged();

    }
}
