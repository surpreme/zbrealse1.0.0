package com.aite.a.fargment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.GoodsDatailsActivity;
import com.aite.a.activity.GoodsListActivity;
import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.adapter.GoodsEvaluateAdapter;
import com.aite.a.adapter.RecommendedProductAdapter;
import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.GoodsParameterPopu;
import com.aite.a.view.GoodsSpecPopu;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 商品详情
 * Created by mayn on 2018/11/13.
 */
public class GoodsFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rl_avditem, rl_srec, rl_parameter, rl_offer, rl_topjall, rl_allgoods;
    private MyAdGallery adgallery;
    private TextView tv_imgnumber, tv_price1, tv_activitytype, tv_price2, tv_goodsname, tv_expressdelivery, tv_monthlysales, tv_address, tv_spec, tv_parameter2, tv_storename, tv_allgoods, tv_tostore, tv_offer, tv_description, tv_service, tv_logistics;
    private LinearLayout ll_share, rl_pj;
    private MyListView lv_pj;
    private ImageView iv_storeimg;
    private GoodsDetailsInfo detailsInfo;
    private GoodsEvaluateAdapter goodsEvaluateAdapter;//评价
    private RecommendedProductAdapter recommendedProductAdapter;//推荐商品
    private MyGridView gv_goods;
    private GoodsDatailsActivity activity;
    private NetRun netRun;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoodsDetails_detailsInfo:
                    if (msg.obj != null) {
                        detailsInfo = (GoodsDetailsInfo) msg.obj;
//                        if (detailsInfo.goods_info.goods_spec_info!=null&&detailsInfo.goods_info.goods_spec_info.size()!=0){//默认选中规格
//                            for (int i=0;i<detailsInfo.goods_info.goods_spec_info.size();i++){
//                                if (detailsInfo.goods_info.goods_spec_info.get(i).spec_value!=null&&detailsInfo.goods_info.goods_spec_info.get(i).spec_value.size()!=0)
//                                detailsInfo.goods_info.goods_spec_info.get(i).spec_value.get(0).ispick=true;
//                            }
//                        }
                        init();
                    }
                    break;
                case GOODS_SHOWSPEC://显示规格
                    showSpecPopu();
                    break;
            }
        }
    };

    @Override
    protected int layoutResID() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView() {
        rl_avditem = layout.findViewById(R.id.rl_avditem);
        rl_srec = layout.findViewById(R.id.rl_srec);
        rl_parameter = layout.findViewById(R.id.rl_parameter);
        adgallery = layout.findViewById(R.id.adgallery);
        tv_imgnumber = layout.findViewById(R.id.tv_imgnumber);
        tv_price1 = layout.findViewById(R.id.tv_price1);
        tv_activitytype = layout.findViewById(R.id.tv_activitytype);
        tv_price2 = layout.findViewById(R.id.tv_price2);
        tv_expressdelivery = layout.findViewById(R.id.tv_expressdelivery);
        tv_monthlysales = layout.findViewById(R.id.tv_monthlysales);
        tv_address = layout.findViewById(R.id.tv_address);
        tv_spec = layout.findViewById(R.id.tv_spec);
        tv_parameter2 = layout.findViewById(R.id.tv_parameter2);
        tv_storename = layout.findViewById(R.id.tv_storename);
        tv_allgoods = layout.findViewById(R.id.tv_allgoods);
        tv_tostore = layout.findViewById(R.id.tv_tostore);
        ll_share = layout.findViewById(R.id.ll_share);
        lv_pj = layout.findViewById(R.id.lv_pj);
        iv_storeimg = layout.findViewById(R.id.iv_storeimg);
        rl_offer = layout.findViewById(R.id.rl_offer);
        tv_offer = layout.findViewById(R.id.tv_offer);
        tv_description = layout.findViewById(R.id.tv_description);
        tv_service = layout.findViewById(R.id.tv_service);
        tv_logistics = layout.findViewById(R.id.tv_logistics);
        rl_pj = layout.findViewById(R.id.rl_pj);
        rl_topjall = layout.findViewById(R.id.rl_topjall);
        rl_allgoods = layout.findViewById(R.id.rl_allgoods);
        gv_goods = layout.findViewById(R.id.gv_goods);
        netRun = new NetRun(getActivity(), handler);
        rl_srec.setOnClickListener(this);
        rl_parameter.setOnClickListener(this);
        tv_allgoods.setOnClickListener(this);
        tv_tostore.setOnClickListener(this);
        rl_topjall.setOnClickListener(this);
        rl_allgoods.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        activity = (GoodsDatailsActivity) getActivity();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_srec) {
            //规格
            showSpecPopu();
        } else if (view.getId() == R.id.rl_parameter) {
            //参数
            showParameterPopu();
        } else if (view.getId() == R.id.tv_allgoods) {
            //全部商品
            Intent intent = new Intent(getActivity(), GoodsListActivity.class);
            intent.putExtra("store_id", detailsInfo.store_info.store_id);
            startActivity(intent);
        } else if (view.getId() == R.id.rl_allgoods) {
            //全部商品
            Intent intent2 = new Intent(getActivity(), GoodsListActivity.class);
            intent2.putExtra("store_id", detailsInfo.store_info.store_id);
            startActivity(intent2);
        } else if (view.getId() == R.id.tv_tostore) {
            //去店铺
            Intent intent3 = new Intent(getActivity(), StoreDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("store_id", detailsInfo.store_info.store_id);
            intent3.putExtras(bundle);
            startActivity(intent3);
        } else if (view.getId() == R.id.rl_topjall) {
            //全部评价
            activity.favorite_list_viewpager.setCurrentItem(2, true);
        } else if (view.getId() == R.id.ll_share) {
            //分享
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, detailsInfo.goods_info.goods_url);
            startActivity(Intent.createChooser(textIntent, getString(R.string.order_reminder90)));
        }

//        switch (view.getId()) {
//            case R.id.rl_srec://规格
//                showSpecPopu();
//                break;
//            case R.id.rl_parameter://参数
//                showParameterPopu();
//                break;
//            case R.id.tv_allgoods://全部商品
//                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
//                intent.putExtra("store_id", detailsInfo.store_info.store_id);
//                startActivity(intent);
//                break;
//            case R.id.rl_allgoods://全部商品
//                Intent intent2 = new Intent(getActivity(), GoodsListActivity.class);
//                intent2.putExtra("store_id", detailsInfo.store_info.store_id);
//                startActivity(intent2);
//                break;
//            case R.id.tv_tostore://去店铺
//                Intent intent3 = new Intent(getActivity(), StoreDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("store_id", detailsInfo.store_info.store_id);
//                intent3.putExtras(bundle);
//                startActivity(intent3);
//                break;
//            case R.id.rl_topjall://全部评价
////                Intent intent1 = new Intent(getActivity(), GoodsEvaluateActivity.class);
////                intent1.putExtra("goods_id", detailsInfo.goods_info.goods_id);
////                startActivity(intent1);
//                activity.favorite_list_viewpager.setCurrentItem(2, true);
//                break;
//            case R.id.ll_share://分享
//                Intent textIntent = new Intent(Intent.ACTION_SEND);
//                textIntent.setType("text/plain");
//                textIntent.putExtra(Intent.EXTRA_TEXT, detailsInfo.goods_info.goods_url);
//                startActivity(Intent.createChooser(textIntent, getString(R.string.order_reminder90)));
//                break;
//        }
    }

    /**
     * 规格
     */
    private void showSpecPopu() {
        GoodsSpecPopu goodsSpecPopu = new GoodsSpecPopu(getActivity(), detailsInfo, "", false);
        goodsSpecPopu.setmenu(new GoodsSpecPopu.menu() {
            @Override
            public void onItemClick(int type, String id, String name, int selectNum) {
                Log.i("---------------", "规格  " + type + "    " + id);
                tv_spec.setText(name);
                switch (type) {
                    case 1://加入购物车
                        activity.handler.sendMessage(activity.handler.obtainMessage(GOODS_ADDCART, id));
                        break;
                    case 2://确认购买
                        activity.handler.sendMessage(activity.handler.obtainMessage(GOODS_BUY, id));
                        break;
                    case 3://选中规格
                        activity.handler.sendMessage(activity.handler.obtainMessage(GOODS_PICK, id));
                        break;
                }
            }
        });
        //tv_spec
        goodsSpecPopu.showAtLocation(rl_srec, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 参数
     */
    private void showParameterPopu() {
        GoodsParameterPopu goodsSpecPopu = new GoodsParameterPopu(getActivity(), detailsInfo.goods_info.goods_param_info);
        goodsSpecPopu.showAtLocation(rl_srec, Gravity.BOTTOM, 0, 0);
    }

    private void init() {
        tv_price1.setText(detailsInfo.goods_info.goods_price);
        if (detailsInfo.goods_info.promotion_type == null || detailsInfo.goods_info.promotion_type.length() == 0) {
            tv_price1.setText("￥" + detailsInfo.goods_info.goods_price + detailsInfo.goods_info.goods_unit);
            tv_price2.setText(getString(R.string.g_price) + "￥" + detailsInfo.goods_info.goods_marketprice);
            tv_activitytype.setVisibility(View.GONE);
        } else if (detailsInfo.goods_info.promotion_type.equals("groupbuy")) {//抢购
            tv_price1.setText(getString(R.string.order_reminder169) + detailsInfo.goods_info.promotion_price);
            tv_price2.setText(getString(R.string.order_reminder171) + detailsInfo.goods_info.goods_price);
            tv_activitytype.setVisibility(View.VISIBLE);
            tv_activitytype.setText(getString(R.string.panic_buying));
        } else if (detailsInfo.goods_info.promotion_type.equals("xianshi")) {//限时
            tv_price1.setText(getString(R.string.order_reminder169) + detailsInfo.goods_info.promotion_price);
            tv_price2.setText(getString(R.string.order_reminder171) + detailsInfo.goods_info.goods_price);
            tv_activitytype.setVisibility(View.VISIBLE);
            tv_activitytype.setText(getString(R.string.goodsdatails_reminder11));
        } else if (detailsInfo.goods_info.promotion_type.equals("miaosha")) {//秒杀
            tv_price1.setText(getString(R.string.order_reminder169) + detailsInfo.goods_info.promotion_price);
            tv_price2.setText(getString(R.string.order_reminder171) + detailsInfo.goods_info.goods_price);
            tv_activitytype.setVisibility(View.VISIBLE);
            tv_activitytype.setText(getString(R.string.goodsdatails_reminder12));
        }
        tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_goodsname.setText(detailsInfo.goods_info.goods_name);
        tv_expressdelivery.setText(getString(R.string.find_reminder338) + ":" + detailsInfo.goods_info.goods_freight + getString(R.string.yuan));//快递费
        tv_monthlysales.setText(getString(R.string.goodsdatails_reminder1) + ":" + detailsInfo.goods_info.goods_salenum);
        tv_address.setText(detailsInfo.goods_info.area_info);
        if (detailsInfo.mansong_info == null) {
            rl_offer.setVisibility(View.GONE);
        } else {
            rl_offer.setVisibility(View.VISIBLE);
            tv_offer.setText(detailsInfo.mansong_info.mansong_name);
        }
        Glide.with(getActivity()).load(detailsInfo.store_info.avatar).into(iv_storeimg);
        tv_storename.setText(detailsInfo.store_info.store_name);

        tv_description.setText(detailsInfo.store_info.store_credit_info.store_desccredit.credit);
        tv_service.setText(detailsInfo.store_info.store_credit_info.store_servicecredit.credit);
        tv_logistics.setText(detailsInfo.store_info.store_credit_info.store_deliverycredit.credit);
        StringTokenizer st = new StringTokenizer(detailsInfo.goods_image, ",");
        List<String> list2 = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String i = st.nextToken();
            list2.add(i);
        }
        setAD(list2);
        if (detailsInfo.goods_evaluate_list == null || detailsInfo.goods_evaluate_list.size() == 0) {
            rl_pj.setVisibility(View.GONE);
        } else {
            rl_pj.setVisibility(View.VISIBLE);
            goodsEvaluateAdapter = new GoodsEvaluateAdapter(getActivity(), detailsInfo.goods_evaluate_list);
            lv_pj.setAdapter(goodsEvaluateAdapter);
        }
        if (detailsInfo.goods_info.goods_spec_info == null || detailsInfo.goods_info.goods_spec_info.size() == 0) {
            rl_srec.setVisibility(View.GONE);
        } else {
            rl_srec.setVisibility(View.VISIBLE);
        }
        if (detailsInfo.goods_info.goods_param_info == null || detailsInfo.goods_info.goods_param_info.size() == 0) {
            rl_parameter.setVisibility(View.GONE);
        } else {
            rl_parameter.setVisibility(View.VISIBLE);
            tv_parameter2.setText(detailsInfo.goods_info.goods_param_info.get(0).data.get(0).param_name + " "
                    + detailsInfo.goods_info.goods_param_info.get(0).data.get(0).param_value + "...");
        }
        recommendedProductAdapter = new RecommendedProductAdapter(getActivity(), detailsInfo.goods_commend_list);
        gv_goods.setAdapter(recommendedProductAdapter);
    }

    /**
     * 设置顶部图
     *
     * @param obj
     */
    protected void setAD(final List<String> obj) {
        tv_imgnumber.setText("1/" + obj.size());
        final String[] ADurl = obj.toArray(new String[obj.size()]);
        if (adgallery.mUris == null)
            adgallery.start(getActivity(), ADurl, null, 0,
                    null, 0, 0, false);
        adgallery.setImageIndex(new MyAdGallery.ImageIndex() {
            @Override
            public void onImageIndex(int curIndex) {
                tv_imgnumber.setText((curIndex + 1) + "/" + obj.size());
            }
        });
    }

}
