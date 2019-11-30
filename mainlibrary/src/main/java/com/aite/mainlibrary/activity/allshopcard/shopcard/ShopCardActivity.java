package com.aite.mainlibrary.activity.allshopcard.shopcard;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.LessAddShopCardNumberBean;
import com.aite.mainlibrary.Mainbean.ShopCardlistBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.bookinformation.BookinformationActivity;
import com.aite.mainlibrary.adapter.shopcard.ShopcatAdapter;
import com.bumptech.glide.Glide;
import com.facebook.stetho.common.StringUtil;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ShopCardActivity extends BaseActivity<ShopCardContract.View, ShopCardPresenter> implements ShopCardContract.View {
    @BindView(R2.id.add_shopcar_btn)
    Button addShopcarBtn;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R2.id.all_check)
    CheckBox allCheck;
    @BindView(R2.id.all_price_tv)
    TextView allPriceTv;
    @BindView(R2.id.senduser_price_tv)
    TextView senduserPriceTv;
    @BindView(R2.id.choice_delete_all_check)
    CheckBox choice_delete_all_check;
    @BindView(R2.id.delete_bottom_ll)
    RelativeLayout deleteBottomLl;
    @BindView(R2.id.all_mag_ll)
    RelativeLayout allMagLl;
    @BindView(R2.id.tv_delete)
    Button tvDelete;
    private ShopcatAdapter shopcatAdapter;
    private List<ShopCardlistBean.CartListBean> cartListBeans = new ArrayList<>();
    //完成 编辑 的tag
    private boolean flag = false;
    //数量
    private int totalCount = 0;
    //价格
    private double totalPrice = 0.00;
    //添加减少的初始值
    private int addlessPostion = 0;


    @Override
    protected int getLayoutResId() {
        return R.layout.shop_card_list;
    }

    @Override
    protected void initView() {
        initToolbar("购物车");
        tvTitleRight.setText("编辑");
        tvTitleRight.setTextColor(getResources().getColor(R.color.black));
        deleteBottomLl.setVisibility(View.GONE);
        allMagLl.setVisibility(View.VISIBLE);
        tvTitleRight.setOnClickListener(this);
        addShopcarBtn.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        shopcatAdapter = new ShopcatAdapter(context, cartListBeans);
        recyclerView.setAdapter(shopcatAdapter);
        initShopCardUI();
    }


    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < cartListBeans.size(); i++) {
            ShopCardlistBean.CartListBean mCartListBean = cartListBeans.get(i);
            if (mCartListBean.isChoosed()) {
                totalCount += Integer.valueOf(cartListBeans.get(i).getGoods_num());
                totalPrice += Double.valueOf(cartListBeans.get(i).getGoods_price()) * Double.valueOf(cartListBeans.get(i).getGoods_num());
            }
        }

        allPriceTv.setText("合计:" + haveTwoDouble(totalPrice));
        senduserPriceTv.setText("结算(" + totalCount + ")");
    }

    private StringBuilder initList(List<String> fixList) {
        StringBuilder warehouse_order_id = new StringBuilder();
        if (fixList != null) {
            for (int i = 0; i < fixList.size(); i++) {
                String a = fixList.get(i);
                if (i != fixList.size() - 1)
                    warehouse_order_id.append(a).append("|");
                else
                    warehouse_order_id.append(a);
            }
        }
        return warehouse_order_id;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_shopcar_btn) startActivity(BookinformationActivity.class);
        if (v.getId() == R.id.tv_delete) initDlete();
        if (v.getId() == R.id.tv_title_right) initTopState();
    }

    /**
     * 初始化 完成 编辑 隐藏功能
     * click
     */
    private void initTopState() {
        flag = !flag;
        if (flag) {
            tvTitleRight.setText("完成");
            deleteBottomLl.setVisibility(View.VISIBLE);
            allMagLl.setVisibility(View.GONE);
            shopcatAdapter.isShow(false);

        } else {
            tvTitleRight.setText("编辑");
            deleteBottomLl.setVisibility(View.GONE);
            allMagLl.setVisibility(View.VISIBLE);
            shopcatAdapter.isShow(true);
        }

    }

    /**
     * 删除选中
     * click
     */
    private void initDlete() {
        AlertDialog alert = new AlertDialog.Builder(context).create();
        alert.setTitle("操作提示");
        alert.setMessage("您确定要将这些商品从购物车中移除吗？");
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        List<String> fixList = new ArrayList<>();
                        for (int i = 0; i < cartListBeans.size(); i++) {
                            if (cartListBeans.get(i).isChoosed())
                                fixList.add(cartListBeans.get(i).getCart_id());
                            LogUtils.d(cartListBeans.get(i).isChoosed());
                        }
                        // initList(fixList);
                        mPresenter.deleteShopCardItem(initDeleteCardParams(initList(fixList)));

                    }
                });
        alert.show();

    }

    //初始化控件
    private void initShopCardUI() {
        /**
         * 实现单个点击计算价钱
         */
        shopcatAdapter.setCheckInterface(new ShopcatAdapter.CheckInterface() {
            @Override
            public void checkGroup(int position, boolean isChecked) {
                statistics();
            }
        });
        /**
         * 1+ 2- 添加减少
         */
        shopcatAdapter.setChangeshopCardNumberInterface(new ShopcatAdapter.ChangeshopCardNumberInterface() {
            @Override
            public boolean addlessThings(int position, int islessAdd) {
                mPresenter.addlessShopThingNumber(initParams(islessAdd, position));
                addlessPostion = position;
                return false;
            }
        });
        //删除 选择全部
        choice_delete_all_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cartListBeans.size() != 0) {
                    if (choice_delete_all_check.isChecked()) {
                        for (int i = 0; i < cartListBeans.size(); i++) {
                            cartListBeans.get(i).setChoosed(true);
                        }
                        shopcatAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < cartListBeans.size(); i++) {
                            cartListBeans.get(i).setChoosed(false);
                        }
                        shopcatAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        //购买 选择全部
        allCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cartListBeans.size() != 0) {
                    if (allCheck.isChecked()) {
                        for (int i = 0; i < cartListBeans.size(); i++) {
                            cartListBeans.get(i).setChoosed(true);
                        }
                        shopcatAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < cartListBeans.size(); i++) {
                            cartListBeans.get(i).setChoosed(false);
                        }
                        shopcatAdapter.notifyDataSetChanged();
                    }
                    statistics();
                }
            }
        });


    }

    //实现接口
    @Override
    protected void initDatas() {
        mPresenter.getShopCardList(initParams());

    }

    /**
     * 添加类型 0系统默认 1助餐服务-菜品
     *
     * @return
     */
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("cart_type", 1);
        return httpParams;
    }

    /**
     * 更新购物车购买数量 1+ 2-
     * 购物车id cart_id
     * 购买数量quantity
     *
     * @return
     */
    private HttpParams initParams(int type, int number) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("cart_id", cartListBeans.get(number).getCart_id());
        if (!cartListBeans.get(number).getGoods_num().equals("1")) {
            httpParams.put("quantity", type == 1 ?
                    Integer.valueOf(cartListBeans.get(number).getGoods_num()) + 1 :
                    Integer.valueOf(cartListBeans.get(number).getGoods_num()) - 1);
        } else {
            if (type == 1) {
                httpParams.put("quantity", Integer.valueOf(cartListBeans.get(number).getGoods_num()) + 1);
            } else {
                showToast("亲 不能再少了", Gravity.CENTER);
                httpParams.put("quantity", Integer.valueOf(cartListBeans.get(number).getGoods_num()));

            }

        }
        return httpParams;
    }

    /**
     * 删除购物车 删除多条购物车时用 ‘|’ 号隔开
     *
     * @return
     */
    private HttpParams initDeleteCardParams(StringBuilder list) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("cart_id", list.toString());
        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onShopCardListSuccess(Object msg) {
        cartListBeans.addAll(((ShopCardlistBean) msg).getCart_list());
        shopcatAdapter.notifyDataSetChanged();
    }

    @Override
    public void ondeleteShopCardItemSuccess(Object msg) {
        LogUtils.d(msg);
        shopcatAdapter.clearData();
        initDatas();


    }

    @Override
    public void onlessShopThingNumberSuccess(Object msg) {
        LogUtils.d(((LessAddShopCardNumberBean) msg).getQuantity());
        shopcatAdapter.notifyItemChanged(addlessPostion, String.valueOf(((LessAddShopCardNumberBean) msg).getQuantity()));
        cartListBeans.get(addlessPostion).setGoods_num(String.valueOf(((LessAddShopCardNumberBean) msg).getQuantity()));
        statistics();
    }
}
