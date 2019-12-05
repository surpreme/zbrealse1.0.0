package com.aite.mainlibrary.activity.allsetting.addbindinguser;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.AddBindingUserAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddBindingUserActivity extends BaseActivity<AddBindingUserContract.View, AddBindingUserPresenter> implements AddBindingUserContract.View {
    @BindView(R2.id.name_edit)
    TextInputEditText nameEdit;
    @BindView(R2.id.phone_edit)
    TextInputEditText phoneEdit;
    @BindView(R2.id.choice_family_ll)
    LinearLayout choiceFamilyLl;
    @BindView(R2.id.famlily_tv)
    TextView famlilyTv;
    private List<AddbinduserfamilyBean.DatasBean> datasBeans = new ArrayList<>();
    private String FAMILY_ID = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.add_binding_user;
    }

    @Override
    protected void initView() {
        initToolbar("添加新账号");
        initBottomBtn("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditTextEmpty(nameEdit) || isEditTextEmpty(phoneEdit)) {
                    showToast("请检查输入信息");
                    return;
                }
                mPresenter.postBindUser(initParams());

            }
        });
        choiceFamilyLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choice_family_ll) {
            if (datasBeans == null || datasBeans.isEmpty()) return;
            AddBindingUserAdapter addBindingUserAdapter = new AddBindingUserAdapter(context, datasBeans);
            PopwindowUtils.getmInstance().showChioceBottomPopupWindow(context, Gravity.BOTTOM, 0.7f, addBindingUserAdapter);
            addBindingUserAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
                @Override
                public void getPostion(int postion) {
                    LogUtils.d("id=======" + datasBeans.get(postion).getId());
                    FAMILY_ID = String.valueOf(datasBeans.get(postion).getId());
                    PopwindowUtils.getmInstance().dismissPopWindow();
                    famlilyTv.setText(datasBeans.get(postion).getTitle());

                }
            });
        }
    }

    @Override
    protected void initDatas() {
        mPresenter.getBindUserfamily(initFamilyParams());

    }

    private HttpParams initFamilyParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("realname", getEditString(nameEdit).equals("") ? "" : getEditString(nameEdit));
        httpParams.put("mobile", getEditString(phoneEdit));
        httpParams.put("relation", FAMILY_ID);
        return httpParams;
    }

    @Override
    protected void initResume() {
    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onPostBindUsersuccess(Object msg) {

    }

    @Override
    public void onGetBindUserfamilysuccess(Object msg) {
        datasBeans.addAll(((AddbinduserfamilyBean) msg).getDatas());

    }



}
