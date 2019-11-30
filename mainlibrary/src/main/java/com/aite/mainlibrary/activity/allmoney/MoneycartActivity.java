package com.aite.mainlibrary.activity.allmoney;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Constant.MainUIConstant;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allmoney.banker.BankerActivity;
import com.aite.mainlibrary.adapter.MoneyCardRecyAdapter;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneycartActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.get_factmoney_ll)
    LinearLayout getFactmoneyLl;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    private MoneyCardRecyAdapter moneyCardRecyAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.mine_money_card;
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(this);
        getFactmoneyLl.setOnClickListener(this);
        tvTitle.setText("我的钱包");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        moneyCardRecyAdapter = new MoneyCardRecyAdapter(context,
                MainUIConstant.MoneycartActivityConstant.settingImg,
                MainUIConstant.MoneycartActivityConstant.settingTv);
        recyclerView.setAdapter(moneyCardRecyAdapter);
        moneyCardRecyAdapter.notifyDataSetChanged();
        moneyCardRecyAdapter.setOnRecyClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                switch (postion) {
                    case 0:
                        break;
                    case 1:
                    case 2:
                        startActivity(FactMoneyActivity.class);
                        break;
                    case 3:
                        startActivity(BankerActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.get_factmoney_ll)
            startActivity(FactMoneyActivity.class);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
