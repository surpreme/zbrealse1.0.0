package com.aite.mainlibrary.activity.allshopcard.sureshopbook;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aite.mainlibrary.Mainbean.BookInfprmationMorningNoonEatBean;
import com.aite.mainlibrary.Mainbean.MoreAdressInormationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.adressfix.AdressFixActivity;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SureShopBookActivity extends BaseActivity<SureShopBookContract.View, SureShopBookPresenter> implements SureShopBookContract.View {

    @BindView(R2.id.sure_buy_btn)
    Button sureBuyBtn;
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
    @BindView(R2.id.store_name_tv)
    TextView storeNameTv;
    private BookInfprmationMorningNoonEatBean bookInfprmationMorningNoonEatBean;


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
        params.put("order_id", getIntent().getStringExtra("order_id"));
        return params;
    }

    /**
     * address_id	post	整型	必须			地址编号
     * key	post	字符串	必须			会员登陆key
     *
     * @param ADDRESS_ID
     * @return
     */
    private HttpParams initParams(String ADDRESS_ID) {
        HttpParams params = new HttpParams();
        params.put("key", AppConstant.KEY);
        params.put("address_id", ADDRESS_ID);
        return params;
    }

    @Override
    protected void initResume() {

    }


    @Override
    protected void initReStart() {

    }

    @OnClick({R2.id.sure_buy_btn, R2.id.user_information_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure_buy_btn) {
//            startActivity(SeviceShopBookActivity.class);
//            PopwindowUtils.getmInstance().showPayRecyPopupWindow(context, Gravity.BOTTOM, bookInfprmationMorningNoonEatBean.getOrder_info().getGoods_price(), new OnClickLstenerInterface.OnThingClickInterface() {
//                @Override
//                public void getString(String msg) {
//                    LogUtils.d(msg);
//                }
//            });
        } else if (v.getId() == R.id.user_information_ll) {
            startActivityWithCls(AdressFixActivity.class, BaseConstant.ACTIVITY_RESULT_CODE.REQUEST_CODE_ACTIVITY_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.ACTIVITY_RESULT_CODE.REQUEST_CODE_ACTIVITY_RESULT && resultCode == RESULT_OK) {
            if (data != null) {
//                ADDRESS_ID = data.getStringExtra("address_id");
                mPresenter.getAddress(initParams(data.getStringExtra("address_id")));

            }
        }
    }

    /**
     * substring(0, 11)字符串0-11个
     * substring(11)字符串11以后
     * 无购餐详情
     * * 返回结果
     * * 返回字段	类型	说明
     * * page_total	整型	总页数
     * * datas->order_info[]	数组	订单记录
     * * datas->order_info[]->order_id	字符串	订单id
     * * datas->order_info[]->order_amount	字符串	订单价格
     * * datas->order_info[]->order_shipping_fee	字符串	订单配送费
     * * datas->order_info[]->order_state_text	字符串	订单状态文字
     * * datas->order_info[]->buyer_name	字符串	姓名
     * * datas->order_info[]->buyer_phone	字符串	手机号码
     * * datas->order_info[]->goods_id	字符串	商品id
     * * datas->order_info[]->goods_name	字符串	商品名称
     * * datas->order_info[]->goods_price	字符串	商品价格
     * * datas->order_info[]->goods_image_url	字符串	商品图片
     * * datas->order_info[]->order_sn	字符串	订单编号
     * * datas->order_info[]->payment_name	字符串	支付方式名称
     * * datas->order_info[]->trade_no	字符串	第三方交易号
     * * datas->order_info[]->add_time	字符串	下单时间
     * * datas->order_info[]->store_qq	字符串	服务商QQ
     * * datas->order_info[]->store_phone	字符串	服务商电话
     * * datas->order_info[]->meal_info[]	字符串	早、午餐信息
     * * datas->order_info[]->meal_info[]->meal_time	字符串	用餐时间
     * * datas->order_info[]->meal_info[]->meal_address	字符串	用餐地址
     * * datas->order_info[]->meal_info[]->type_text	字符串	订餐方式文字
     * * datas->order_info[]->is_delete	字符串	是否可以删除 1是
     * <p>
     * <p>
     * * order_id : 38
     * * order_sn : 820628432284431007
     * * store_id : 2
     * * store_name : 艾特技术
     * * buyer_id : 7
     * * buyer_name : 18614079738
     * * buyer_phone : 18614079738
     * * add_time : 1575088284
     * * payment_code :
     * * payment_time : 0
     * * trade_no : null
     * * close_time : 0
     * * close_reason : null
     * * finnshed_time : null
     * * order_amount : 0.01
     * * goods_points_offset : 0
     * * order_points_offset : 0
     * * order_costamount : 0.00
     * * refund_amount : 0.00
     * * rcb_amount : 0.00
     * * pd_amount : 0.00
     * * order_state : 10
     * * refund_state : 0
     * * buyer_msg : null
     * * delete_state : 0
     * * goods_id : 8
     * * goods_name : 测试早餐1
     * * goods_price : 0.01
     * * goods_num : 1
     * * goods_image : 2019/10/29/2_06256787071214709.jpg
     * * commis_rate : 200
     * * gc_id : 14
     * * gc_id2 : 3
     * * vr_indate : 1890230399
     * * vr_send_times : 0
     * * vr_invalid_refund : 0
     * * order_promotion_type : 0
     * * promotions_id : 0
     * * order_from : 2
     * * evaluation_state : 0
     * * evaluation_time : 0
     * * use_state : 0
     * * first_comm : 0.00
     * * second_comm : 0.00
     * * three_comm : 0.00
     * * is_visit_comm : 0
     * * is_Independent_comm : 0
     * * comm_rule : null
     * * is_buy_apply : 0
     * * meal_info : {"type":1,"meal_time":"1970-01-01 08:00","meal_address":"南山科技园福安大厦","type_text":"到店吃"}
     * * order_shipping_fee : 0.00
     * * order_state_text : 待付款
     * * payment_name :
     * * is_delete : 1
     * * store_qq : null
     * * store_phone : null
     *
     * @param msg
     */
    @Override
    public void onGetInformationSuccess(Object msg) {
        bookInfprmationMorningNoonEatBean = ((BookInfprmationMorningNoonEatBean) msg);
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
        storeNameTv.setText(String.format("店铺名字%s", bookInfprmationMorningNoonEatBean.getOrder_info().getStore_name()));
    }

    /**
     * 返回字段	类型	说明
     * address_info[]	数组	地址列表
     * address_info[].address_id	整型	地址id
     * address_info[].true_name	字符串	收货人姓名
     * address_info[].province_id	整型	省级id
     * address_info[].city_id	整型	市级id
     * address_info[].area_id	整型	区域id
     * address_info[].area_info	字符串	省市区
     * address_info[].address	字符串	详细地址
     * address_info[].mob_phone	字符串	手机号码
     * address_info[].is_default	整型	是否默认
     *
     * @param msg
     */
    @Override
    public void onGetAddressSuccess(Object msg) {
        MoreAdressInormationBean moreAdressInormationBean = ((MoreAdressInormationBean) msg);
        if (moreAdressInormationBean == null) return;
        addressTv.setText(moreAdressInormationBean.getAddress_info().getIs_default().equals("1") ? "默认地址：" : "地址：" + String.format("%s%s", moreAdressInormationBean.getAddress_info().getArea_info(), moreAdressInormationBean.getAddress_info().getAddress()));
        namePhonenumberTv.setText(String.format("%s  %s", moreAdressInormationBean.getAddress_info().getTrue_name(), moreAdressInormationBean.getAddress_info().getMob_phone()));
    }

}
