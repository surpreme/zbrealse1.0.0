package com.aite.mainlibrary.activity.allsetting.healthbook;


import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.aite.mainlibrary.Mainbean.HealthbookMainBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.addhealthbook.AddHealthBookActivity;
import com.aite.mainlibrary.activity.allsetting.healthbooklist.HealthBookListActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HealthBookActivity extends BaseActivity<HealthBookContract.View, HealthBookPresenter> implements HealthBookContract.View {
    @BindView(R2.id.disease_ll)
    LinearLayout diseaseLl;
    @BindView(R2.id.hostory_note_ll)
    LinearLayout hostoryNoteLl;
    @BindView(R2.id.hate_ll)
    LinearLayout hateLl;
    @BindView(R2.id.medicine_ll)
    LinearLayout medicineLl;
    @BindView(R2.id.send_body_switch)
    SwitchMaterial sendBodySwitch;
    @BindView(R2.id.high_edit)
    EditText highEdit;
    @BindView(R2.id.height_edit)
    EditText heightEdit;
    @BindView(R2.id.xue_type_edit)
    EditText xueTypeEdit;


    @Override
    protected int getLayoutResId() {
        return R.layout.health_book;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        initToolbar("健康档案", "编辑", new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (tv_title_right.getText().toString().equals("完成")) {
                    mPresenter.onPostInformation(initChangeParams());
                    onBackPressed();
                } else {
                    tv_title_right.setText("完成");
                    highEdit.setHint("身高");
                    xueTypeEdit.setHint("血型");
                    heightEdit.setHint("体重");

                    highEdit.setFocusableInTouchMode(true);
                    heightEdit.setFocusableInTouchMode(true);
                    sendBodySwitch.setEnabled(true);
                    xueTypeEdit.setFocusableInTouchMode(true);


                    xueTypeEdit.setHintTextColor(getColor(R.color.glay));
                    highEdit.setHintTextColor(getColor(R.color.glay));
                    heightEdit.setHintTextColor(getColor(R.color.glay));

                }


            }
        });
        highEdit.setFocusableInTouchMode(false);
        heightEdit.setFocusableInTouchMode(false);
        xueTypeEdit.setFocusableInTouchMode(false);
        sendBodySwitch.setTextColor(getColor(R.color.black));
        sendBodySwitch.setEnabled(false);
        sendBodySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.d(isChecked);
                sendBodySwitch.setText(String.format("是否捐赠器官者: %s", isChecked ? "是" : "否"));
            }
        });

    }

    @Override
    protected void initDatas() {
        mPresenter.getInformationList(initParams());

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    /**
     * 1 true 2 false
     *
     * @return
     */
    private HttpParams initChangeParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("member_height", getEditString(highEdit));
        httpParams.put("member_weight", getEditString(heightEdit));
        httpParams.put("member_blood_types", getEditString(xueTypeEdit));
        httpParams.put("is_organ_donor", sendBodySwitch.isChecked() ? "1" : "2");

        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @OnClick({R2.id.disease_ll, R2.id.hostory_note_ll, R2.id.hate_ll, R2.id.medicine_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.disease_ll) startActivity(HealthBookListActivity.class,"type","1");
        if (v.getId() == R.id.hostory_note_ll)startActivity(HealthBookListActivity.class,"type","2");
        if (v.getId() == R.id.hate_ll) startActivity(HealthBookListActivity.class,"type","3");
        if (v.getId() == R.id.medicine_ll)startActivity(HealthBookListActivity.class,"type","4");
    }

    /**
     * 返回字段	类型	说明
     * member_id	整型	会员id
     * member_height	整型	会员身高
     * member_weight	整型	会员体重
     * member_blood_types	字符串	会员血型
     * is_organ_donor	整型	会员是否是器官捐赠者,0为否,1为是
     *
     * @param msg
     */
    @Override
    public void onGetInformationListSuccess(Object msg) {
        if (msg.toString().isEmpty()) return;
        highEdit.setHint(String.format("身高：%s cm", getJsonToString(((HealthbookMainBean) msg).getMember_height())));
        heightEdit.setHint(String.format("体重：%s kg", getJsonToString(((HealthbookMainBean) msg).getMember_weight())));
        xueTypeEdit.setHint(String.format("血型：%s 型", getJsonToString(((HealthbookMainBean) msg).getMember_blood_types())));

//        weightTv.setText(String.format("体重：%s", ((HealthbookMainBean) msg).getMember_weight()) == null ? "未知" : ((HealthbookMainBean) msg).getMember_weight().toString());
//        xueTypeTv.setText(String.format("血型：%s", ((HealthbookMainBean) msg).getMember_blood_types()) == null ? "未知" : ((HealthbookMainBean) msg).getMember_blood_types().toString());
        if (((HealthbookMainBean) msg).getIs_organ_donor() != null) {
            sendBodySwitch.setText(String.format("是否捐赠器官者: %s", ((HealthbookMainBean) msg).getIs_organ_donor().toString().equals("1") ? "是" : "否"));
            sendBodySwitch.setChecked(((HealthbookMainBean) msg).getIs_organ_donor().toString().equals("1"));
        } else sendBodySwitch.setText("是否捐赠器官者:未知");


    }

    @Override
    public void onPostInformationSuccess(Object msg) {
        showToast(msg.toString());

    }

    @NotNull
    private String getJsonToString(String string) {
        return string == null
                ? "未知"
                : string;
    }


}