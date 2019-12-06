package com.aite.mainlibrary.fragment.activityfragment.minefragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.mainlibrary.Constant.MainUIConstant;
import com.aite.mainlibrary.Mainbean.UseInformationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.HealthBookActivity;
import com.aite.mainlibrary.activity.MineCollectActivity;
import com.aite.mainlibrary.activity.allmain.AddDeviceMainActvity;
import com.aite.mainlibrary.activity.allmain.device.DeviceListActivity;
import com.aite.mainlibrary.activity.allmain.messager.MessagerActivity;
import com.aite.mainlibrary.activity.allmoney.MoneycartActivity;
import com.aite.mainlibrary.activity.allsetting.LessbodybookActivity;
import com.aite.mainlibrary.activity.allsetting.MinePostBookActivity;
import com.aite.mainlibrary.activity.allsetting.SettingActivity;
import com.aite.mainlibrary.activity.allsetting.minerunning.MineRunningActivity;
import com.aite.mainlibrary.activity.allsetting.minerural.MineRuralActivity;
import com.aite.mainlibrary.activity.allsetting.thingsbook.ThingsbookActivity;
import com.aite.mainlibrary.activity.allsetting.userinformation.UserInformationActivity;
import com.aite.mainlibrary.adapter.GridViewIconAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseFragment;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.MVPBaseFragment;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MineFragment extends BaseFragment<MineContract.View, MinePresenter> implements MineContract.View {
    @BindView(R2.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R2.id.setting_gridview)
    GridView settingGridview;
    @BindView(R2.id.book_gridview)
    GridView bookGridview;
    @BindView(R2.id.else_gridview)
    GridView elseGridview;
    @BindView(R2.id.device_gridview)
    GridView deviceGridview;
    @BindView(R2.id.fix_friends_btn)
    Button fixFriendsBtn;
    @BindView(R2.id.setting_img)
    ImageView settingImg;
    @BindView(R2.id.user_phone_number_tv)
    TextView userPhoneNumberTv;
    @BindView(R2.id.user_icon)
    ImageView userIcon;
    @BindView(R2.id.message_iv)
    ImageView messageIv;


    @Override
    protected void initModel() {
        mPresenter.getUserInformation(initParams());

    }
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }
    @Override
    protected void initViews() {
        fixFriendsBtn.setOnClickListener(this);
        settingImg.setOnClickListener(this);
        userIcon.setOnClickListener(this);
        messageIv.setOnClickListener(this);
        userPhoneNumberTv.setOnClickListener(this);

        settingGridview.setAdapter(new GridViewIconAdapter(context, MainUIConstant.MineConstant.settingImg, MainUIConstant.MineConstant.settingTv));
        bookGridview.setAdapter(new GridViewIconAdapter(context, MainUIConstant.MineConstant.bookImg, MainUIConstant.MineConstant.bookTv));
        elseGridview.setAdapter(new GridViewIconAdapter(context, MainUIConstant.MineConstant.elseImg, MainUIConstant.MineConstant.elseTv));
        deviceGridview.setAdapter(new GridViewIconAdapter(context, MainUIConstant.MineConstant.deviceImg, MainUIConstant.MineConstant.deviceTv));
        settingGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(DeviceListActivity.class);
                        break;
                    case 1:
                        startActivity(MineRunningActivity.class);
                        break;
                    case 2:
                        startActivity(MineRuralActivity.class);
                        break;
                    case 3:
                        startActivity(MineCollectActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });
        bookGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(ThingsbookActivity.class);
                        break;
                    case 1:
                        startActivity(LessbodybookActivity.class, "page_type", "5");
                        break;
                    case 2:
                        startActivity(LessbodybookActivity.class, "page_type", "1");
                        break;
                    case 3:
                        startActivity(LessbodybookActivity.class, "page_type", "2");
                        break;
                    case 4:
                        startActivity(LessbodybookActivity.class, "page_type", "3");
                        break;
                    case 5:
                        startActivity(LessbodybookActivity.class, "page_type", "4");
                        break;
                    default:
                        break;
                }
            }
        });
        deviceGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(AddDeviceMainActvity.class);
                        break;
                    default:
                        break;
                }
            }
        });
        elseGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(MoneycartActivity.class);
                        break;
                    case 1:
                        startActivity(HealthBookActivity.class);
                        break;
                    case 2:
                        startActivity(MinePostBookActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mine_fragment;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fix_friends_btn) {
            LogUtils.d("被点击");

        } else if (v.getId() == R.id.setting_img) {
            startActivity(SettingActivity.class);
        } else if (v.getId() == R.id.user_phone_number_tv || v.getId() == R.id.user_icon)
            startActivity(UserInformationActivity.class);
        else if (v.getId() == R.id.message_iv) {
            startActivity(MessagerActivity.class);
        }

    }


    @Override
    public void onGetUserInformation(Object msg) {
        if (((UseInformationBean) msg).getMember_info().getMember_avatar() == null) return;
        AppConstant.ICON_URL=((UseInformationBean) msg).getMember_info().getMember_avatar();
        AppConstant.PHONENUMBER=((UseInformationBean) msg).getMember_info().getMember_mobile();
        Glide.with(context).load(((UseInformationBean) msg).getMember_info().getMember_avatar()).apply(RequestOptions.circleCropTransform()).into(userIcon);
        userPhoneNumberTv.setText( replaceString(((UseInformationBean)msg).getMember_info().getMember_mobile(),2,8));


    }


}
