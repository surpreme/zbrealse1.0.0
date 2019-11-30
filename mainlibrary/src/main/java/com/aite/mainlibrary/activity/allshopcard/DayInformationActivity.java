package com.aite.mainlibrary.activity.allshopcard;

import android.view.View;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.BuyDayTogetherActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;

public class DayInformationActivity extends BaseActivity {
    @BindView(R2.id.buy_tv)
    TextView buy_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.day_togther_information;
    }

    @Override
    protected void initView() {
        buy_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buy_tv) {
            startActivity(BuyDayTogetherActivity.class);
        }
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
