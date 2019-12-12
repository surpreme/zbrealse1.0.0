package com.aite.mainlibrary.activity.allmain.buydaytogether;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.BuySencondBean;
import com.aite.mainlibrary.Mainbean.LessBodyInformationBean;
import com.aite.mainlibrary.Mainbean.PayHelpServiceSuccessBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.PaySettingActivity;
import com.aite.mainlibrary.activity.allshopcard.sureshopbook.SureShopBookActivity;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BuyDayTogetherActivity extends BaseActivity<BuyDayTogetherContract.View, BuyDayTogetherPresenter> implements BuyDayTogetherContract.View {

    @BindView(R2.id.iphone_number_edit)
    EditText iphoneNumberEdit;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.book_price)
    TextView bookPrice;
    @BindView(R2.id.bottom_btn)
    Button bottomBtn;
    @BindView(R2.id.icon_img)
    ImageView iconImg;
    @BindView(R2.id.title_tv)
    TextView titleTv;
    @BindView(R2.id.price_tv)
    TextView priceTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.buy_day_together;
    }

    @Override
    protected void initView() {
        initToolbar("购买");
        initBottomBtn("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(PaySettingActivity.class, "goods_id", !isStringEmpty(getIntent().getStringExtra("goods_id")) ? getIntent().getStringExtra("goods_id") : "");
                if (isEditTextEmpty(iphoneNumberEdit)) return;
                mPresenter.buyService(initPostAllParams());
            }
        });
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("goods_id",
                !isStringEmpty(getIntent().getStringExtra("goods_id")) ? getIntent().getStringExtra("goods_id") : "");
        httpParams.put("quantity", 1);
        return httpParams;
    }

    private HttpParams initPostAllParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("buyer_phone", getEditString(iphoneNumberEdit));
        httpParams.put("quantity", 1);
        httpParams.put("goods_id", !isStringEmpty(getIntent().getStringExtra("goods_id")) ? getIntent().getStringExtra("goods_id") : "");
        return httpParams;
    }


    @Override
    protected void initDatas() {
        mPresenter.getInformation(initParams());
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onBuyThingSuccesss(Object msg) {
        if (((PayHelpServiceSuccessBean) msg).getOrder_id() != null)
            startActivity(SureShopBookActivity.class, "order_id", isStringEmpty(((PayHelpServiceSuccessBean) msg).getOrder_id()) ? "" : ((PayHelpServiceSuccessBean) msg).getOrder_id());
        else
            showToast("订单生成失败", Gravity.TOP);
    }

    /**
     * 返回字段	类型	说明
     * goods_info.goods_id	整型	商品id
     * goods_info.goods_name	字符串	商品名称
     * goods_info.goods_promotion_price	字符串	商品名单价
     * goods_info.goods_image_url	字符串	商品图片
     * goods_info.goods_addtime	字符串	发布时间
     * goods_info.quantity	字符串	购买数量
     * goods_info.goods_total	字符串	应付总额
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     * @param msg
     */
    @Override
    public void onGetInformationSuceess(Object msg) {
        Glide.with(context).load(((BuySencondBean) msg).getGoods_info().getGoods_image_url()).into(iconImg);
        titleTv.setText(((BuySencondBean) msg).getGoods_info().getGoods_name());
        priceTv.setText(String.format("￥%s", ((BuySencondBean) msg).getGoods_info().getGoods_price()));
        bookPrice.setText(String.format("￥%s", ((BuySencondBean) msg).getGoods_info().getGoods_total()));
    }
}
