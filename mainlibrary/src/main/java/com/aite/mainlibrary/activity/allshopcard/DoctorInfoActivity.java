package com.aite.mainlibrary.activity.allshopcard;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.fragment.doctorInfoFragment.AppointmentFagment;
import com.aite.mainlibrary.fragment.doctorInfoFragment.ConsultFagment;
import com.aite.mainlibrary.fragment.doctorInfoFragment.HomepageFagment;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.util.TextUtil;
import com.lzy.basemodule.view.RatingBarView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医生信息
 */

public class DoctorInfoActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    @BindView(R2.id.tv_position)
    TextView mTvPosition;
    @BindView(R2.id.tv_hospital)
    TextView mTvHospital;
    @BindView(R2.id.starView)
    RatingBarView mStarView;
    @BindView(R2.id.tv_grade1)
    TextView mTvGrade1;
    @BindView(R2.id.user_icon)
    CircleImageView mUserIcon;
    @BindView(R2.id.tv_grade)
    TextView mTvGrade;
    @BindView(R2.id.tv_subscribe_num)
    TextView mTvSubscribeNum;
    @BindView(R2.id.tv_consult_num)
    TextView mTvConsultNum;
    @BindView(R2.id.tv_reply_num)
    TextView mTvReplyNum;
    @BindView(R2.id.tv_text)
    TextView mTvText;
    @BindView(R2.id.tv_morbidity_type)
    TextView mTvMorbidityType;
    @BindView(R2.id.iv_more)
    ImageView mIvMore;
    @BindView(R2.id.tv_content)
    TextView mTvContent;
    @BindView(R2.id.tv_homepage)
    TextView mTvHomepage;
    @BindView(R2.id.tv_consult)
    TextView mTvConsult;
    @BindView(R2.id.tv_appointment)
    TextView mTvAppointment;
    @BindView(R2.id.fragment_vp)
    FrameLayout mFragmentVp;

    private int currentIndex = -1;

    private Fragment[] mFragments = new Fragment[3];

    private Fragment mHomepageFragment, mConsultFragment, mAppointmentFragment;


    @Override
    protected int getLayoutResId() {
        return R.layout.maindoctor_information;
    }

    @Override
    protected void initView() {

        initToolbar("个人主页：唐欣");
        //头像
        Glide.with(context).load(context.getResources().getDrawable(R.mipmap.orange_else)).into(mUserIcon);
        mTvGrade.setText(TextUtil.highlight(context, "粉丝9.4", "9.4", "#F4EA2A", 0, 0));
        mTvSubscribeNum.setText(TextUtil.highlight(context, "咨询量232", "232", "#F4EA2A", 0, 0));
        mTvConsultNum.setText(TextUtil.highlight(context, "预约数454", "454", "#F4EA2A", 0, 0));
        mTvReplyNum.setText(TextUtil.highlight(context, "回复速度快", "快", "#F4EA2A", 0, 0));
        //设置星级
        mStarView.setStar(4);

        showFragment(0);

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

    private void showFragment(int index) {
        initTabState(index);
        if (currentIndex != index) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //如果已经有旧内容在显示 隐藏旧的dettach
            if (currentIndex != -1) {
                hideFragments(ft);
            }

            //显示新的：
            if (mFragments[index] == null) {
                //第一次显示：创建对象（记录） 添加
                switch (index) {
                    case 0:
                        if (mHomepageFragment != null) {
                            ft.show(mHomepageFragment);
                            // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                        } else {
                            mHomepageFragment = new HomepageFagment();
                            mFragments[0] = mHomepageFragment;
                            ft.add(R.id.fragment_vp, mHomepageFragment);
                        }
                        break;
                    case 1:
                        if (mConsultFragment != null) {
                            ft.show(mConsultFragment);
                            // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                        } else {
                            mConsultFragment = new ConsultFagment();
                            mFragments[1] = mConsultFragment;
                            ft.add(R.id.fragment_vp, mConsultFragment);
                        }
                        break;
                    case 2:
                        if (mAppointmentFragment != null) {
                            ft.show(mAppointmentFragment);
                            // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                        } else {
                            mAppointmentFragment = new AppointmentFagment();
                            mFragments[2] = mAppointmentFragment;
                            ft.add(R.id.fragment_vp, mAppointmentFragment);
                        }
                        break;
                }
            } else {
                //已有对象 显示attach
                ft.show(mFragments[index]);
            }
            ft.commit();
            currentIndex = index;
        }
    }

    public void hideFragments(FragmentTransaction ft) {
        if (mHomepageFragment != null)
            ft.hide(mHomepageFragment);
        if (mConsultFragment != null)
            ft.hide(mConsultFragment);
        if (mAppointmentFragment != null)
            ft.hide(mAppointmentFragment);

    }


    private void initTabState(int index) {
        mTvHomepage.setSelected(index == 0);
        mTvConsult.setSelected(index == 1);
        mTvAppointment.setSelected(index == 2);

    }


    @OnClick({R2.id.tv_homepage, R2.id.tv_consult, R2.id.tv_appointment})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_homepage) {
            //主页
            showFragment(0);
        } else if (id == R.id.tv_consult) {
            //咨询
            showFragment(1);
        } else if (id == R.id.tv_appointment) {
            //预约
            showFragment(2);
        }
    }
}
