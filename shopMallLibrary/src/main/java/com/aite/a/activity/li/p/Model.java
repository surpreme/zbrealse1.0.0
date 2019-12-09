package com.aite.a.activity.li.p;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aite.a.activity.GoodsListActivity;
import com.aite.a.activity.li.activity.MessageActivity;
import com.aite.a.activity.li.adapter.BanderRecyAdapter;
import com.aite.a.activity.li.adapter.MsgListAdapter;
import com.aite.a.activity.li.bean.AroundAdvertisementBean;
import com.aite.a.activity.li.bean.AroundLocationChoiceBean;
import com.aite.a.activity.li.bean.BrandBean;
import com.aite.a.activity.li.bean.IconBean;
import com.aite.a.activity.li.bean.MsgBean;
import com.aite.a.activity.li.bean.ShopBean;
import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.activity.li.util.SpUtils;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Model {
    private Context context;

    public Model(Context context) {
        this.context = context;
    }

    public void amClassShopFragment(String type, int toplow, final int pages, final ModelInteface.AmClassShopFragmentInterface amClassShopFragmentInterface) {
        NetRun netRun = new NetRun(context);
        netRun.onShoptypeChoice(type, toplow, pages, new RequestCallBack<String>() {
            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                final ShopBean shopBean = BeanConvertor.convertBean(responseInfo.result, ShopBean.class);
                amClassShopFragmentInterface.amClassShopFragmentInterfaceModelSuccess(context, shopBean.getDatas().getStore_list(), shopBean.getDatas().isHasmore());
                LogUtils.d(shopBean.getDatas().isHasmore() + "" + pages);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    public void amAroundActivity(String type, int toplow, String loction, final int pages, final ModelInteface.AmClassShopFragmentInterface amClassShopFragmentInterface) {
        NetRun netRun = new NetRun(context);
        netRun.onAround(type, toplow, loction, pages, new RequestCallBack<String>() {
            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                final ShopBean shopBean = BeanConvertor.convertBean(responseInfo.result, ShopBean.class);
                amClassShopFragmentInterface.amClassShopFragmentInterfaceModelSuccess(context, shopBean.getDatas().getStore_list(), shopBean.getDatas().isHasmore());
                LogUtils.d(shopBean.getDatas().isHasmore() + "" + pages);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 弃用
     * @param amClassBrandFragmentInterface
     */
    public void aAroundLocationChoiceActivity(final ModelInteface.AmClassBrandFragmentInterface amClassBrandFragmentInterface) {
        NetRun netRun = new NetRun(context);
        netRun.onAroundLocationChoiceActivity(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtils.d(responseInfo.result);
                AroundLocationChoiceBean aroundLocationChoiceBean = BeanConvertor.convertBean(responseInfo.result, AroundLocationChoiceBean.class);
                List<AroundLocationChoiceBean.DatasBean> datasBeans = new ArrayList<>();
//                datasBeans.addAll(aroundLocationChoiceBean.getDatas());
//

                List<String> list = new ArrayList<>();
                for (int i = 0; aroundLocationChoiceBean.getDatas().getA().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getA().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getB().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getB().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getC().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getC().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getD().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getD().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getE().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getE().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getF().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getF().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getG().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getG().get(i).getArea_name());
                }



                for (int i = 0; aroundLocationChoiceBean.getDatas().getH().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getA().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getJ().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getB().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getK().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getC().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getL().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getD().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getM().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getE().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getN().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getF().get(i).getArea_name());
                }
                for (int i = 0; aroundLocationChoiceBean.getDatas().getP().size() > i; i++) {
                    list.add(aroundLocationChoiceBean.getDatas().getG().get(i).getArea_name());
                }
                amClassBrandFragmentInterface.amClassBrandFragmentInterfaceModelSuccess(context,
                        list);
//                final BrandBean brandBean = BeanConvertor.convertBean(responseInfo.result, BrandBean.class);
//                List<AroundLocationChoiceBean.DatasBean> datas = new ArrayList<>();
//                datas.addAll(aroundLocationChoiceBean.getDatas());
//                amAroundLocationChoiceActivityInterface.amClassBrandFragmentInterfaceModelSuccess(context, datas);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                amClassBrandFragmentInterface.amClassBrandFragmentInterfaceModelFail(e + s);

            }
        });

    }

    public void amClassBrandFragment(final ModelInteface.AmClassBrandFragmentInterface amClassBrandFragmentInterface) {
        NetRun netRun = new NetRun(context);
        netRun.onBrandtypeChoice(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                final BrandBean brandBean = BeanConvertor.convertBean(responseInfo.result, BrandBean.class);
                List<BrandBean.DatasBean> datas = new ArrayList<>();
                datas.addAll(brandBean.getDatas());
                amClassBrandFragmentInterface.amClassBrandFragmentInterfaceModelSuccess(context, datas);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                amClassBrandFragmentInterface.amClassBrandFragmentInterfaceModelFail(e + s);

            }
        });

    }

    public void messageModel(final ModelInteface modelInteface) {
        final NetRun netRun = new NetRun(context);
        netRun.OrdinaryMessageList(null, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtils.d(responseInfo.result);
                final MsgBean msgBean = BeanConvertor.convertBean(responseInfo.result, MsgBean.class);
                if (msgBean.getCode() != 200) return;
                Iterator<MsgBean.DatasBean.MessageArrayBean> it = msgBean.getDatas().getMessage_array().iterator();
                final List<String> iconlisturl = new ArrayList<>();
                while (it.hasNext()) {
                    MsgBean.DatasBean.MessageArrayBean bean = it.next();
                    iconlisturl.add(bean.getFrom_member_name());
                }
                final NetRun netRun = new NetRun(context);
                netRun.HuanxinIconMessageList((SpUtils.initList(iconlisturl)).toString(), new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        final IconBean iconBean = BeanConvertor.convertBean(responseInfo.result, IconBean.class);
                        modelInteface.messageActivityModelSuccess(context, msgBean.getDatas().getMessage_array(), iconBean.getDatas());

                        //获取头像失败
                        LogUtils.d(responseInfo.result);

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        LogUtils.e(e + s);
                        modelInteface.messageActivityModelFail(e + s);

                    }
                });
                LogUtils.d(iconlisturl);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                modelInteface.messageActivityModelFail(e + s);

            }
        });
    }

    public interface ModelInteface {
        void messageActivityModelSuccess(Context context, List<MsgBean.DatasBean.MessageArrayBean> datas, List<IconBean.DatasBean> icbean);

        void messageActivityModelFail(String error);

        interface AmClassBrandFragmentInterface {
            //            void amClassBrandFragmentInterfaceModelSuccess(Context context, List<BrandBean.DatasBean> datas);
            void amClassBrandFragmentInterfaceModelSuccess(Context context, List<?> datas);

            void amClassBrandFragmentInterfaceModelFail(String error);

        }


        interface AmClassShopFragmentInterface {
            //            void amClassShopFragmentInterfaceModelSuccess(Context context, List<ShopBean.DatasBean.StoreListBean> mDatasList, boolean ishasmore);
            void amClassShopFragmentInterfaceModelSuccess(Context context, List<?> mDatasList, boolean ishasmore);

            void amClassShopFragmentInterfaceeModelFail(String error);
        }
//   msgrecy.setAdapter(new MsgListAdapter(context,msgBean.getDatas().getMessage_array(), iconBean.getDatas()));
    }
}
