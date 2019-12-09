package com.aite.a.activity.li.activity;

import android.view.View;
import android.widget.ImageView;

import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.R2;

import butterknife.BindView;

public class ShopHomeActivity extends BaseActivity  {
    @BindView(R2.id.iv_back)
     ImageView iv_back;

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.iv_back){
            onBackPressed();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_all_layout;
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(this);

    }

    @Override
    protected void initModel() {

    }

}
