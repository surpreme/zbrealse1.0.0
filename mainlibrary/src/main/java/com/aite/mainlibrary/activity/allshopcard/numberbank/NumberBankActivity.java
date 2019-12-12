package com.aite.mainlibrary.activity.allshopcard.numberbank;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aite.mainlibrary.Mainbean.NumberBankBean;
import com.aite.mainlibrary.Mainbean.NumberBankInformationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allmain.sendtimebank.SendTimeBankActivity;
import com.aite.mainlibrary.activity.allshopcard.numbershop.NumberShopActivity;
import com.aite.mainlibrary.activity.allshopcard.timebankrules.TimeBankRulesActivity;
import com.aite.mainlibrary.adapter.NumberBankRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.adpter.TextViewBaseRecyAdapter;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.TimeUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NumberBankActivity extends BaseActivity<NumberBankContract.View, NumberBankPresenter> implements NumberBankContract.View {
    @BindView(R2.id.all_number_tv)
    TextView allNumberTv;
    @BindView(R2.id.information_tv)
    TextView informationTv;
    @BindView(R2.id.send_time_btn)
    Button sendTimeBtn;
    @BindView(R2.id.all_ll)
    LinearLayout allLl;
    @BindView(R2.id.away_ll)
    LinearLayout awayLl;
    @BindView(R2.id.father_ll)
    LinearLayout fatherLl;
    @BindView(R2.id.time_shop_btn)
    Button timeShopBtn;
    private NumberBankRecyAdapter numberBankRecyAdapter;
    private List<NumberBankBean.ListBean> numberBankBean = new ArrayList<>();
    /**
     * 筛选类型 0全部 1活动累计 2交易累计 3全部消耗 4兑换 5过期
     */
    private String[] rulesStrings = {"全部", "活动累计", "交易累计", "兑换", "过期"};

    @Override
    protected int getLayoutResId() {
        return R.layout.number_bank;
    }

    @Override
    protected void initView() {
        initToolbar("积分银行", "规则", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TimeBankRulesActivity.class);
            }
        });
        initRecy();
        mBaserecyclerView.setAdapter(numberBankRecyAdapter = new NumberBankRecyAdapter(context, numberBankBean));
        mBaserecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        numberBankRecyAdapter.setOnItemRecyClickInterface(
                new OnClickLstenerInterface.OnThingClickInterface() {
                    @Override
                    public void getString(String msg) {

                    }
                }
        );

    }

    //1	http://zhongbyi.aitecc.com/mobile/index.php?act=member_timebank_point&op=points_goods_list&class_id=1&curpage=1&key=1954d4e5e8636c34fae3ff408235c9c9
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R2.id.send_time_btn, R2.id.all_ll, R2.id.away_ll, R2.id.time_shop_btn})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_time_btn) startActivity(SendTimeBankActivity.class);
        else if (v.getId() == R.id.all_ll) {
            List<String> yearlist = new ArrayList<>();
            for (int i = Integer.parseInt(TimeUtils.getCurrentYY()); i < 2050; i++) {
                yearlist.add(i + "年");
            }
            List<String> mondaylist = new ArrayList<>();
            for (int i = 1; i < 12; i++) {
                mondaylist.add(i + "月");
            }

            PopwindowUtils.getmInstance().showThreeRecyPopupWindow(context, yearlist, mondaylist, fatherLl);

        } else if (v.getId() == R.id.away_ll) {
            TextViewBaseRecyAdapter textViewBaseRecyAdapter = new TextViewBaseRecyAdapter(context, rulesStrings);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            PopwindowUtils.getmInstance().showRecyPopupWindow(context, textViewBaseRecyAdapter, gridLayoutManager, allLl);
            textViewBaseRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
                @Override
                public void getPostion(int postion) {
                    LogUtils.d(postion);
                }
            });
        } else if (v.getId() == R.id.time_shop_btn) {
            startActivity(NumberShopActivity.class);
        }

    }

    @Override
    protected void initDatas() {
        mPresenter.getMainUi(initKeyParams());
        mPresenter.getInformationList(initParams());

    }

    /**
     * \
     * 筛选类型 0全部 1活动累计 2交易累计 3全部消耗 4兑换 5过期
     * YYYY-mm
     *
     * @return
     */
    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        params.put("key", AppConstant.KEY);
        params.put("curpage", 1);
//        params.put("time", AppConstant.KEY);
        params.put("type", 0);

        return params;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    /**
     * 返回字段	类型	说明
     * datas->member_volunteer_points	字符串	积分数
     * datas->expired_credit	字符串	即将过期积分数 0表示无过期积分
     * datas->expired_time	字符串	即将过期时间
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     *
     * @param msg
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onGetMainUiSuccess(Object msg) {

        allNumberTv.setText(((NumberBankInformationBean) msg).getMember_volunteer_points());
        if (!((NumberBankInformationBean) msg).getExpired_credit().equals("0"))
            informationTv.setText(String.format("%s积分将于%s过期", ((NumberBankInformationBean) msg).getExpired_credit(), ((NumberBankInformationBean) msg).getExpired_time()));
        else
            informationTv.setText("暂无积分过期");

    }

    @Override
    public void onGetChoiceUiSuccess(Object msg) {

    }

    @Override
    public void onGetInformationListSuccess(Object msg) {
        numberBankBean.addAll(((NumberBankBean) msg).getList());
        numberBankRecyAdapter.notifyDataSetChanged();
    }


}
