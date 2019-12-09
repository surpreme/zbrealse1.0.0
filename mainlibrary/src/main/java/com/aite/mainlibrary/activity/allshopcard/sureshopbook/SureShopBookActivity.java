package com.aite.mainlibrary.activity.allshopcard.sureshopbook;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.BookInfprmationMorningNoonEatBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.seviceshopbook.SeviceShopBookActivity;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SureShopBookActivity extends BaseActivity<SureShopBookContract.View, SureShopBookPresenter> implements SureShopBookContract.View {

    @BindView(R2.id.sure_buy_btn)
    Button sureBuyBtn;
    @BindView(R2.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R2.id.name_phonenumber_tv)
    TextView namePhonenumberTv;
    @BindView(R2.id.address_tv)
    TextView addressTv;
    @BindView(R2.id.user_information_ll)
    LinearLayout userInformationLl;
    @BindView(R2.id.year_time_tv)
    TextView yearTimeTv;
    @BindView(R2.id.oclock_tv)
    TextView oclockTv;
    @BindView(R2.id.icon)
    ImageView icon;
    @BindView(R2.id.title_thing_tv)
    TextView title_thing_tv;
    @BindView(R2.id.information_tv)
    TextView informationTv;
    @BindView(R2.id.thing_price_tv)
    TextView thingPriceTv;
    @BindView(R2.id.send_price_tv)
    TextView sendPriceTv;
    @BindView(R2.id.all_price_tv)
    TextView allPriceTv;
    @BindView(R2.id.all)
    TextView all;
    @BindView(R2.id.price_ll)
    LinearLayout priceLl;
    @BindView(R2.id.eat_fix_number_edit)
    EditText eatFixNumberEdit;
    @BindView(R2.id.eat_another_information_edit)
    EditText eatAnotherInformationEdit;
    @BindView(R2.id.card_all_price_tv)
    TextView cardAllPriceTv;
    @BindView(R2.id.card_send_price_tv)
    TextView cardSendPriceTv;


    @Override
    protected int getLayoutResId() {
        return R.layout.sureshop;
    }

    @Override
    protected void initView() {
        initToolbar("确认订单");

    }

    @Override
    protected void initDatas() {
        mPresenter.getInformation(initParams());

    }

    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        params.put("key", AppConstant.KEY);
        params.put("order_id", 91);
        return params;
    }

    @Override
    protected void initResume() {

    }


    @Override
    protected void initReStart() {

    }

    @OnClick({R2.id.sure_buy_btn,R2.id.user_information_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure_buy_btn) {
            startActivity(SeviceShopBookActivity.class);
        }
    }

    /**
     * substring(0, 11)字符串0-11个
     * substring(11)字符串11以后
     * 无购餐详情
     * @param msg
     */
    @Override
    public void onGetInformationSuccess(Object msg) {
        BookInfprmationMorningNoonEatBean bookInfprmationMorningNoonEatBean = ((BookInfprmationMorningNoonEatBean) msg);
        namePhonenumberTv.setText(String.format("%s  %s", bookInfprmationMorningNoonEatBean.getOrder_info().getBuyer_name(), bookInfprmationMorningNoonEatBean.getOrder_info().getBuyer_phone()));
        addressTv.setText(bookInfprmationMorningNoonEatBean.getOrder_info().getMeal_info().getMeal_address());
        allPriceTv.setText(String.format("￥%s", bookInfprmationMorningNoonEatBean.getOrder_info().getOrder_amount()));
        sendPriceTv.setText(String.format("￥%s", bookInfprmationMorningNoonEatBean.getOrder_info().getOrder_shipping_fee()));
        thingPriceTv.setText(String.format("￥%s", bookInfprmationMorningNoonEatBean.getOrder_info().getGoods_price()));
        title_thing_tv.setText(bookInfprmationMorningNoonEatBean.getOrder_info().getGoods_name());
//        Glide.with(context).load(bookInfprmationMorningNoonEatBean.getOrder_info().getGoods_image()).into(icon);
        informationTv.setText(bookInfprmationMorningNoonEatBean.getOrder_info().getMeal_info().getType_text());
        yearTimeTv.setText(new StringBuilder().append("用餐日期： ").append(bookInfprmationMorningNoonEatBean.getOrder_info().getMeal_info().getMeal_time().substring(0, 11)).toString());
        oclockTv.setText(new StringBuilder().append("用餐时间：").append(bookInfprmationMorningNoonEatBean.getOrder_info().getMeal_info().getMeal_time().substring(11)).toString());
        cardAllPriceTv.setText(String.format("￥%s", bookInfprmationMorningNoonEatBean.getOrder_info().getOrder_amount()));
        cardSendPriceTv.setText(String.format("配送费%s", bookInfprmationMorningNoonEatBean.getOrder_info().getOrder_shipping_fee()));

    }

}
