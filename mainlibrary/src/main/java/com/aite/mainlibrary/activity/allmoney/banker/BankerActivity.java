package com.aite.mainlibrary.activity.allmoney.banker;


import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allmoney.AddBankcarActvity;
import com.aite.mainlibrary.adapter.BankCardRecyAdapter;
import com.lzy.basemodule.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BankerActivity extends BaseActivity<BankerContract.View, BankerPresenter> implements BankerContract.View {
    @BindView(R2.id.bank_recy)
    RecyclerView bankRecy;
    @BindView(R2.id.add_bankcard_ll)
    LinearLayout addBankcardLl;
    private List<String> banknames = new ArrayList<>();
    private List<String> banknumbers = new ArrayList<>();
    private BankCardRecyAdapter bankCardRecyAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.bank_carlayout;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back)
            onBackPressed();
        if (v.getId() == R.id.add_bankcard_ll)
            startActivity(AddBankcarActvity.class);
    }

    @Override
    protected void initView() {
        initToolbar("我的银行卡");
        addBankcardLl.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        bankRecy.setLayoutManager(linearLayoutManager);
        bankRecy.setItemAnimator(new DefaultItemAnimator());
        banknames.add("中国银行");
        banknumbers.add("**** **** **** 4124");
        bankCardRecyAdapter = new BankCardRecyAdapter(this, banknames, banknumbers);
        bankRecy.setAdapter(bankCardRecyAdapter);
        bankCardRecyAdapter.notifyDataSetChanged();

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
