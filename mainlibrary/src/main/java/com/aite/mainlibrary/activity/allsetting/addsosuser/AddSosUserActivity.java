package com.aite.mainlibrary.activity.allsetting.addsosuser;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.AddBindingUserAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.FileUtils;
import com.lzy.basemodule.util.ImageUtils;
import com.lzy.okgo.model.HttpParams;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddSosUserActivity extends BaseActivity<AddSosUserContract.View, AddSosUserPresenter> implements AddSosUserContract.View {
    @BindView(R2.id.user_icon_ll)
    LinearLayout userIconLl;
    @BindView(R2.id.user_icon)
    ImageView userIcon;
    @BindView(R2.id.phone_edit)
    TextInputEditText phoneEdit;
    @BindView(R2.id.name_edit)
    TextInputEditText nameEdit;
    @BindView(R2.id.choice_family_ll)
    LinearLayout choiceFamilyLl;
    @BindView(R2.id.famlily_tv)
    TextView famlilyTv;
    @BindView(R2.id.isMust_switch)
    SwitchMaterial isMustSwitch;
    private List<Uri> mSelected = new ArrayList<>();
    private List<AddbinduserfamilyBean.DatasBean> datasBeans = new ArrayList<>();
    private String FAMILY_ID = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.add_sos_user;
    }

    @Override
    protected void initView() {
        initToolbar("添加紧急联系人");
        initBottomBtn("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.postAllBindUserfamilyInformation(initParams());
            }
        });
    }

    @OnClick({R2.id.choice_family_ll, R2.id.user_icon_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.user_icon_ll)
            openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE);
        else if (v.getId() == R.id.choice_family_ll) {
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

    private HttpParams initFamilyParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    /**
     * 参数名字	提交方式	类型	是否必须	默认值	其他	说明	test
     * key	post	字符串	必须			会员登录key
     * id	post	整型	可选			紧急联系人id
     * type	post	整型	必须			保存类型 1添加 2修改(需上传紧急联系人id)
     * avatar	post	文件	必须	1		头像文件参数
     * realname	post	字符串	必须			真实姓名
     * mobile	post	字符串	必须			手机号
     * relation	post	整型	必须			关系id
     * is_default	post	整型	可选			是否默认
     *
     * @return
     */
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        if (mSelected != null && !mSelected.isEmpty()) {
            if (mSelected.get(0) != null) {
                if (FileUtils.getFileByUri(context, mSelected.get(0)).exists()) {
                    httpParams.put("avatar", FileUtils.getFileByUri(context, mSelected.get(0)));

                }
            }

        }
        httpParams.put("type", 1);
        httpParams.put("realname", isEditTextEmpty(nameEdit) ? "" : getEditString(nameEdit));
        httpParams.put("mobile", isEditTextEmpty(phoneEdit) ? "" : getEditString(phoneEdit));
        httpParams.put("relation", FAMILY_ID);
        httpParams.put("is_default", isMustSwitch.isChecked() ? "1" : "0");


        return httpParams;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
//            File file = FileUtils.getFileByUri(context, mSelected.get(0));
            ImageUtils.getmInstance().photoClip(this, mSelected.get(0));

        } else if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_CLIP && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //在这里获得了剪裁后的Bitmap对象，可以用于上传
                Bitmap bitmap = bundle.getParcelable("data");
                ImageUtils.getmInstance().saveBitmap(context, bitmap, "userIcon");
                Glide.with(context).load(bitmap).transform(new CircleCrop()).into(userIcon);

            }


        }
    }

    @Override
    protected void initDatas() {
        mPresenter.getBindUserfamily(initFamilyParams());
//SAVESOSUSERURL
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onGetBindUserfamilysuccess(Object msg) {
        if (((AddbinduserfamilyBean) msg).getDatas() != null || !((AddbinduserfamilyBean) msg).getDatas().isEmpty())
            datasBeans.addAll(((AddbinduserfamilyBean) msg).getDatas());
    }

    @Override
    public void onPostAllBindUserfamilyInformationSuccess(Object msg) {
        if (((TwoSuccessCodeBean) msg).getMsg().equals("保存成功") && ((TwoSuccessCodeBean) msg).getResult().equals("1")) {
            showToast(((TwoSuccessCodeBean) msg).getMsg(), Gravity.TOP);
            onBackPressed();
        }
    }


}
