package com.aite.mainlibrary.fragment.activityfragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.mainlibrary.Constant.MainUIConstant;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.HealthBookActivity;
import com.aite.mainlibrary.activity.MineCollectActivity;
import com.aite.mainlibrary.activity.allmain.AddDeviceMainActvity;
import com.aite.mainlibrary.activity.allmain.ElseHelpActivity;
import com.aite.mainlibrary.activity.allmain.device.DeviceListActivity;
import com.aite.mainlibrary.activity.allmoney.MoneycartActivity;
import com.aite.mainlibrary.activity.allsetting.SettingActivity;
import com.aite.mainlibrary.activity.allsetting.UserInformationActivity;
import com.aite.mainlibrary.activity.allsetting.minerural.MineRuralActivity;
import com.aite.mainlibrary.activity.allshopcard.helpdoctor.HelpdoctorActivity;
import com.aite.mainlibrary.activity.allstep.StepActivity;
import com.aite.mainlibrary.adapter.GridViewIconAdapter;
import com.lzy.basemodule.base.BaseFragment;
import com.lzy.basemodule.logcat.LogUtils;

import butterknife.BindView;

public class MineFragment extends BaseFragment {
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


    @Override
    protected void initModel() {

    }

    @Override
    protected void initViews() {
        fixFriendsBtn.setOnClickListener(this);
        settingImg.setOnClickListener(this);
        userIcon.setOnClickListener(this);
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
                        startActivity(HelpdoctorActivity.class);
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
                        startActivity(StepActivity.class);
                        break;
                    case 1:
                        startActivity(HelpdoctorActivity.class);
                        break;
                    case 2:
                        startActivity(HelpdoctorActivity.class);
                        break;
                    case 3:
                        startActivity(ElseHelpActivity.class);
                        break;
                    case 4:
                        startActivity(HelpdoctorActivity.class);
                        break;
                    case 5:
                        startActivity(HelpdoctorActivity.class);
                        break;
                    case 6:
                        startActivity(HelpdoctorActivity.class);
                        break;
                    case 7:
                        startActivity(HelpdoctorActivity.class);
                        break;
                    case 8:
                        startActivity(HelpdoctorActivity.class);
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
                    case 2:
                        startActivity(HealthBookActivity.class);
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

    }


}
