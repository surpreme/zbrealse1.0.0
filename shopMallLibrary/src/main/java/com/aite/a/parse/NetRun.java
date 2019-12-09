package com.aite.a.parse;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.adapter.Cart2Adapter;
import com.aite.a.adapter.CartAdapter.ViewHolder;
import com.aite.a.base.Mark;
import com.aite.a.model.CartListInfo;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.Sqlutls;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class NetRun implements Mark {
    public Handler handler;
    public Context context;
    private HttpUtils httpUtils;
    private RequestParams params;

    public NetRun(Context context, Handler handler) {
        super();
        this.context = context;
        this.handler = handler;
    }

    public NetRun(Context context) {
        super();
        this.context = context;
    }

    /**
     * 首页
     */
    public void Intex(final Sqlutls sqlutls) {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, index, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> response) {
                handler.sendMessage(handler.obtainMessage(home_ad_id,
                        JsonParse.getIndexVp(response.result)));
                sqlutls.add("home", response.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                handler.sendMessage(handler.obtainMessage(home_ad_err,
                        home_ad_err));
            }

            @Override
            public void onStart() {
                handler.sendMessage(handler.obtainMessage(home_ad_start,
                        home_ad_start));
                super.onStart();
            }
        });
    }

    /**
     * 首页
     */
    public void Intex() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, index, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> response) {
                handler.sendMessage(handler.obtainMessage(home_ad_id,
                        JsonParse.getCustomHome(response.result)));
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                handler.sendMessage(handler.obtainMessage(home_ad_err,
                        home_ad_err));
            }
        });
    }

    /**
     * 获取商品列表
     *
     * @param key      排序方式 1-销量 2-浏览量 3-价格 空-按最新发布排序
     * @param order    排序方式 1-升序 2-降序
     * @param page     每页数量
     * @param curpage  当前页码
     * @param keyword  搜索关键字
     * @param gc_id    分类编号
     * @param store_id 店铺编号
     */
    public void getGoodsList(String key, String order, String page,
                             final String curpage, String keyword, final String gc_id,
                             String store_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", key);
        params.addQueryStringParameter("order", order);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("curpage", curpage);
        if (keyword != null)
            params.addQueryStringParameter("keyword", keyword);
        if (store_id != null)
            params.addQueryStringParameter("store_id", store_id);
        if (gc_id != null)
            params.addQueryStringParameter("gc_id", gc_id);
        httpUtils.send(HttpMethod.GET, good_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        if (gc_id != null) {
                            handler.sendMessage(handler.obtainMessage(
                                    goods_list_id, Integer.valueOf(gc_id), 0,
                                    JsonParse.getGoodsList(response.result,
                                            curpage)));
                        } else {
                            handler.sendMessage(handler.obtainMessage(
                                    goods_list_id, JsonParse.getGoodsList(
                                            response.result, curpage)));
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                goods_list_err, msg));
                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(goods_list_start));
                        super.onStart();
                    }
                });

    }

    /**
     * 获取商品列表2
     *
     * @param key      排序方式 1-销量 2-浏览量 3-价格 空-按最新发布排序
     * @param order    排序方式 1-升序 2-降序
     * @param page     每页数量
     * @param curpage  当前页码
     * @param keyword  搜索关键字
     * @param gc_id    分类编号
     * @param store_id 店铺编号
     */
    public void getGoodsList2(String key, String order, String page,
                              final String curpage, String keyword, final String gc_id,
                              String store_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", key);
        params.addQueryStringParameter("order", order);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("curpage", curpage);
        if (keyword != null)
            params.addQueryStringParameter("keyword", keyword);
        if (store_id != null)
            params.addQueryStringParameter("store_id", store_id);
        if (gc_id != null)
            params.addQueryStringParameter("gc_id", gc_id);
        httpUtils.send(HttpMethod.GET, good_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        if (gc_id != null) {
                            handler.sendMessage(handler.obtainMessage(
                                    goods_list_id, Integer.valueOf(gc_id), 0,
                                    JsonParse.getGoodsList2(response.result,
                                            curpage)));
                        } else {
                            handler.sendMessage(handler.obtainMessage(
                                    goods_list_id, JsonParse.getGoodsList(
                                            response.result, curpage)));
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                goods_list_err, msg));
                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(goods_list_start));
                        super.onStart();
                    }
                });
    }

    public void getCategorOne() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, category, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                handler.sendMessage(handler.obtainMessage(one_category_id,
                        JsonParse.getCategory(responseInfo.result)));
            }

            @Override
            public void onStart() {
                handler.sendMessage(handler.obtainMessage(one_category_start));
                super.onStart();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                handler.sendMessage(handler
                        .obtainMessage(one_category_err, msg));
            }
        });
    }

    private Boolean a = true;

    public void getCategoryTeo(String gc_id, final int position) {
        // 第一次进入加载第四个分类
        // if (a && gc_id != null) {
        // gc_id = "527";
        // a = false;
        // }
        httpUtils = new HttpUtils();
        params = new RequestParams();
        if (gc_id != null)
            params.addQueryStringParameter("gc_id", gc_id);
        httpUtils.send(HttpMethod.GET, category, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d(responseInfo.result);
//                        handler.sendMessage(handler.obtainMessage(
//                                two_category_id, position, 0,
//                                JsonParse.getCategory(responseInfo.result)));
                    }

                    @Override
                    public void onStart() {
//                        handler.sendMessage(handler
//                                .obtainMessage(two_category_start));
                        super.onStart();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
//                        handler.sendMessage(handler.obtainMessage(
//                                two_category_err, msg));
                    }
                });
    }

    /**
     * 首页分类
     *
     * @param gc_id
     * @param position
     */
    public void getCategoryTeo2(String gc_id, final int position,
                                final Sqlutls sqlutls) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        if (gc_id != null)
            params.addQueryStringParameter("gc_id", gc_id);
        httpUtils.send(HttpMethod.GET, category, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                two_category_id, position, 0,
                                JsonParse.getCategory(responseInfo.result)));
                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(two_category_start));
                        super.onStart();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                two_category_err, msg));
                    }
                });
    }

    public void getFavoriteList(final String fav_type, final int arg1) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("fav_type", fav_type);
        httpUtils.send(HttpMethod.POST, favorites_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                collectibles_id, arg1, 0, JsonParse
                                        .getFavoriteList(responseInfo.result,
                                                context)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(collectibles_err));
                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(collectibles_start));
                        super.onStart();
                    }
                });
    }

    public void cancelGoodsFavorite(String fav_id, String fav_type) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("fav_id", fav_id);
        params.addBodyParameter("fav_type", fav_type);
        httpUtils.send(HttpMethod.POST, favorites_del, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                collectibles_del_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(collectibles_del_err));
                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(collectibles_del_start));
                        super.onStart();
                    }
                });
    }

    /**
     * 获得周边店铺列表
     *
     * @param points
     */
    public void getNearby(String points, String city_id, String area_id,
                          String class_id, String sort_type, String sort_dis_type) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("points", points);
        params.addQueryStringParameter("city_id", city_id);
        params.addQueryStringParameter("area_id", area_id);
        params.addQueryStringParameter("class_id", class_id);
        params.addQueryStringParameter("sort_type", sort_type);
        params.addQueryStringParameter("sort_dis_type", sort_dis_type);
        httpUtils.send(HttpMethod.GET, nearby_store, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler.obtainMessage(nearby__err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(nearby__id,
                                JsonParse.getNearby(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(nearby__start));
                    }
                });
    }
    /**
     * 获得选择城市
     */
    public void getAroundCity() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, celectcity, null,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        LogUtils.d(JsonParse.getNearbySelectCity(arg0.result));
//                        handler.sendMessage(handler.obtainMessage(
//                                nearbySelectCity__id,
//                                JsonParse.getNearbySelectCity(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(nearbySelectCity__err));
                    }
                });
    }
    /**
     * 获得选择城市
     */
    public void getSelectCity() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, celectcity, null,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                nearbySelectCity__id,
                                JsonParse.getNearbySelectCity(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(nearbySelectCity__err));
                    }
                });
    }

    /**
     * 获得区域列表
     */
    public void getArealist(String city_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("city_id", city_id);
        httpUtils.send(HttpMethod.GET, area, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                nearbySelectArea__id,
                                JsonParse.getNearbySelectArea(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(nearbySelectArea__err));
                    }
                });
    }

    /**
     * 当前登录令牌
     *
     * @param company_name                       商家名称
     * @param seller_name                        商家账号
     * @param store_name                         店铺名称
     * @param sg_id                              店铺等级id
     * @param sg_name                            店铺等级name
     * @param sc_id                              店铺分类id
     * @param sc_name                            店铺分类name
     * @param store_class_ids                    []=”1,2,3” ：三级分类id 经营类目id
     * @param store_class_names                  []=”特产优联,进口食品 ,饼干蛋糕 ” ：三级分类名称 经营类目 name
     * @param company_province_id                所在地 省id
     * @param company_city_id                    所在地 市id
     * @param company_area_id                    所在地 区域id
     * @param company_address                    地址（省市区）
     * @param company_points                     经纬度
     * @param contacts_phone                     联系人电话
     * @param contacts_name                      联系人姓名
     * @param contacts_email                     电子邮箱
     * @param business_sphere                    姓名
     * @param business_licence_number_electronic 身份证扫描件
     * @param business_licence_number            身份证号
     * @param settlement_bank_account_name       支付宝姓名
     * @param settlement_bank_account_number     支付宝账号
     */
    public void personaLopenShop(String company_name, String seller_name,
                                 String store_name, String sg_id, String sg_name, String sc_id,
                                 String sc_name, String store_class_ids, String store_class_names,
                                 String company_province_id, String company_city_id,
                                 String company_area_id, String company_address,
                                 String company_points, String contacts_phone, String contacts_name,
                                 String contacts_email, String business_sphere,
                                 String business_licence_number_electronic,
                                 String business_licence_number,
                                 String settlement_bank_account_name,
                                 String settlement_bank_account_number) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("company_name", company_name);
        params.addBodyParameter("seller_name", seller_name);
        params.addBodyParameter("store_name", store_name);
        params.addBodyParameter("sg_id", sg_id);
        params.addBodyParameter("sg_name", sg_name);
        params.addBodyParameter("sc_id", sc_id);
        params.addBodyParameter("sc_name", sc_name);
        params.addBodyParameter("store_class_ids", store_class_ids);
        params.addBodyParameter("store_class_names", store_class_names);
        params.addBodyParameter("company_province_id", company_province_id);
        params.addBodyParameter("company_city_id", company_city_id);
        params.addBodyParameter("company_area_id", company_area_id);
        params.addBodyParameter("company_address", company_address);
        params.addBodyParameter("company_points", company_points);
        params.addBodyParameter("contacts_phone", contacts_phone);
        params.addBodyParameter("contacts_name", contacts_name);
        params.addBodyParameter("contacts_email", contacts_email);
        params.addBodyParameter("business_sphere", business_sphere);
        params.addBodyParameter("business_licence_number_electronic", new File(
                business_licence_number_electronic));
        params.addBodyParameter("business_licence_number",
                business_licence_number);
        params.addBodyParameter("settlement_bank_account_name",
                settlement_bank_account_name);
        params.addBodyParameter("settlement_bank_account_number",
                settlement_bank_account_number);
        httpUtils.send(HttpMethod.POST, open_shop, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(open_store_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                open_store_id,
                                JsonParse.personaLopenShop(arg0.result)));
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(open_store_start));
                    }
                });
    }

    /**
     * 地址信息
     *
     * @param area_id
     */
    public void getSregionList(String area_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        if (area_id != null)
            params.addBodyParameter("area_id", area_id);
        httpUtils.send(HttpMethod.POST, sregion_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_region_id,
                                JsonParse.getRegionList(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(get_region_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(get_region_start));
                    }
                });
    }

    /**
     * 地址信息
     *
     * @param area_id
     */
    public void getSregionList2(String area_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        if (area_id != null)
            params.addBodyParameter("area_id", area_id);
        httpUtils.send(HttpMethod.POST, sregion_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_region_id,
                                JsonParse.getRegionList2(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(get_region_err));
                    }
                });
    }

    public void getStoreCategory() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, store_category, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                store_category_id,
                                JsonParse.getStoreCategory(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(store_category_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(store_category_start));
                    }
                });
    }

    public void getGcList(String one_category_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("gc_id", one_category_id);
        httpUtils.send(HttpMethod.POST, getgclist, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(getgclist_id,
                                JsonParse.getGcList(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(getgclist_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(getgclist_err));
                    }
                });
    }

    public void addFavorites(String goods_id, String fav_type) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("fav_id", goods_id);
        params.addBodyParameter("fav_type", fav_type);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, add_favorites, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                collectibles_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(collectibles_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(collectibles_start));
                    }
                });
    }

    /**
     * @param member_truename
     * @param member_sex
     * @param member_qq
     * @param member_ww
     * @param province_id
     * @param city_id
     * @param area_id
     * @param area_info
     * @param birthday
     */
    public void upPersonalData(final String member_truename,
                               final String member_sex, final String member_qq,
                               final String member_ww, final String province_id,
                               final String city_id, final String area_id, final String area_info,
                               final String birthday, final File tempFile) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("member_truename", member_truename);
        params.addBodyParameter("member_sex", member_sex);
        params.addBodyParameter("member_qq", member_qq);
        params.addBodyParameter("member_ww", member_ww);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("area_info", area_info);
        params.addBodyParameter("birthday", birthday);
        if (tempFile != null && tempFile.exists()) {
            params.addBodyParameter("avator", tempFile);
        }
        httpUtils.send(HttpMethod.POST, up_PersonalData, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "更新资料  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                up_PersonalData_id,
                                JsonParse.upPersonalData(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        CommonTools.showShortToast(context, msg);
                        handler.sendMessage(handler
                                .obtainMessage(up_PersonalData_err));
                    }
                });

    }

    public void getPersonalData() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, get_PersonalData, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("--------------------", "个人资料  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                get_PersonalData_id,
                                JsonParse.getPersonalData(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        CommonTools.showShortToast(context, msg);
                        handler.sendMessage(handler
                                .obtainMessage(get_PersonalData_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(get_PersonalData_start));
                    }
                });
    }

    /**
     * 虚拟订单列表
     */
    public void getVirtualorder() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, virtualorders, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_vrorder_id,
                                JsonParse.geiVrorder(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        CommonTools.showShortToast(context, arg1);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }
                });
    }

    /**
     * 服务订单列表
     */
    public void getServiceorder() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, serviceorders, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_serviceorder_id,
                                JsonParse.geiServiceorder(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        CommonTools.showShortToast(context, arg1);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }
                });
    }

    /**
     * 我的足迹
     */
    public void getMyfootprint(String gc_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("gc_id", gc_id);
        httpUtils.send(HttpMethod.POST, myfootprint, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_myfootprint_id,
                                JsonParse.getmyfootprintt(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        CommonTools.showShortToast(context, arg1);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }
                });
    }

    /**
     * 清空我的足迹
     */
    public void emptyMyfootprint() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, emptymyfootprint, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                empty_myfootprint_id, JsonParse
                                        .emptyMyfootprintt(responseInfo.result)));
                        String datas = JsonParse
                                .emptyMyfootprintt(responseInfo.result);
                        if (datas.equals("1")) {
                            CommonTools.showShortToast(context, "清除成功");
                        }
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        CommonTools.showShortToast(context, "清除失败");
                    }

                });
    }

    /**
     * 删除我的足迹
     *
     * @param goods_id
     */
    public void deteleMyfootprint(String goods_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("goods_id", goods_id);
        httpUtils.send(HttpMethod.POST, detelemyfootprint, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        handler.sendMessage(handler.obtainMessage(
                                detele_myfootprint_id,
                                JsonParse
                                        .deteleMyfootprintt(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(detele_myfootprint_err));
                    }

                });
    }

    /**
     * 获取评价
     */
    public void getEvaluation() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, get_Evaluation, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        CommonTools.showShortToast(context, msg);
                        handler.sendMessage(handler.obtainMessage(comment_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            Log.i("----------------",
                                    "评价" + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(comment_id,
                                JsonParse.getEvaluation(responseInfo.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        // handler.sendMessage(handler
                        // .obtainMessage(comment_start));
                    }
                });
    }

    public void getGoodsManage(String url, final int i, String page,
                               final int show_add) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("curpage", String.valueOf(show_add));
        params.addBodyParameter("page", page);
        params.addBodyParameter("key", State.UserKey);
        if (i == 1)
            params.addBodyParameter("type", "lock_up");
        if (i == 2)
            params.addBodyParameter("type", "wait_verify");
        if (i == 3)
            params.addBodyParameter("type", "verified");
        httpUtils.send(HttpMethod.POST, url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                goods_manage_id, i, 0, JsonParse
                                        .getGoodsManage(responseInfo.result,
                                                show_add)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        CommonTools.showShortToast(context, msg);
                        handler.sendMessage(handler
                                .obtainMessage(goods_manage_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(goods_manage_start));
                    }
                });
    }

    /**
     * 我的店铺
     */
    public void getMyStoreData() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, my_store, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "我的店铺  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(my_store_id,
                                JsonParse.getMyStoreData(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        CommonTools.showShortToast(context, msg);
                        handler.sendMessage(handler.obtainMessage(my_store_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(my_store_start));
                    }
                });
    }

    public void getRedactData(String commonid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("commonid", commonid);
        httpUtils.send(HttpMethod.GET, get_RedactData, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_redact_data_id,
                                JsonParse.getRedactData(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        CommonTools.showShortToast(context, msg);
                        handler.sendMessage(handler
                                .obtainMessage(get_redact_data_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(get_redact_data_start));
                    }
                });
    }

    File path = null;

    public void postGoodsData(String url, String commonid, String goods_name,
                              String cate_id, String cate_name, String image_path,
                              String g_price, String g_marketprice, String g_storage,
                              String province_id, String city_id, String g_freight) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        if (commonid != null)
            params.addBodyParameter("commonid", commonid);
        params.addBodyParameter("g_name", goods_name);
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("cate_name", cate_name);
        if (image_path != null) {
            path = new File(image_path);
            params.addBodyParameter("image_path", path);
        }
        params.addBodyParameter("g_price", g_price);
        params.addBodyParameter("g_marketprice", g_marketprice);
        params.addBodyParameter("g_storage", g_storage);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("g_freight", g_freight);
        httpUtils.send(HttpMethod.POST, url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                post_redact_data_id,
                                JsonParse.postGoodsData(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(post_redact_data_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(post_redact_data_start));
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        int i = (int) ((current / (float) total) * 100);
                        handler.sendMessage(handler.obtainMessage(
                                post_redact_data_long, i, 0, null));
                    }
                });
    }

    public void getSellerOrder(String state_type, String page,
                               final int show_add) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("state_type", state_type);
        params.addQueryStringParameter("curpage", String.valueOf(show_add));
        params.addBodyParameter("page", page);
        httpUtils.send(HttpMethod.POST, get_SellerOrder, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_seller_order_id, JsonParse.getSellerOrder(
                                        responseInfo.result, show_add)));

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        CommonTools.showShortToast(context, msg);
                        handler.sendMessage(handler
                                .obtainMessage(get_seller_order_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(get_seller_order_start));
                    }
                });
    }

    public void ModifyOrder(String order_id, String state_type, String info) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("state_type", state_type);
        if (state_type.equals("goods_price"))
            params.addBodyParameter("goods_amount", info);
        if (state_type.equals("modify_price"))
            params.addBodyParameter("shipping_fee", info);
        if (state_type.equals("state_info1"))
            params.addBodyParameter("state_info", info);
        httpUtils.send(HttpMethod.POST, Modify_Order, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                modify_order_id,
                                JsonParse.ModifyOrder(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(modify_order_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(modify_order_start));
                    }
                });
    }

    /**
     * 确认发货
     *
     * @param order_id
     */
    public void confirmsend(String order_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, orderdelivery, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                confirm_send_id,
                                JsonParse.confirmm(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(confirm_send_err));
                    }

                });

    }

    public void goodsOperation(String url, String commonid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commonid", commonid);
        httpUtils.send(HttpMethod.POST, url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                goods_operation_id,
                                JsonParse.ModifyOrder(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(goods_operation_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(goods_operation_start));
                    }
                });
    }

    public void getMember() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, member, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("---------------我的商城  "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(member_id,
                                JsonParse.getMember(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(member_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler.obtainMessage(member_start));
                    }
                });

    }

    public void dataleAddress(String address_id) {
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("address_id", address_id);
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.POST, detele_address, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                drop_consignee_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(drop_consignee_err));

                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(drop_consignee_start));

                    }
                });

    }

    /**
     * 地址
     */
    public void getAddress() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, address_list, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-------------------地址  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                address_list_id,
                                JsonParse.getAddress(responseInfo.result)));

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(address_list_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(address_list_start));
                    }
                });
    }

    public void getCartList() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, cart_list, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("------------------", "购物车  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(cart_list_id,
                                JsonParse.getCartList2(responseInfo.result)));

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(cart_list_err));

                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(cart_list_start));
                        super.onStart();
                    }
                });
    }

    /**
     * 购物车
     */
    public void getCartList2() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, getcart_list2, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("------------------", "购物车  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(getcart_list2_id,
                                JsonParse.getCartList3(responseInfo.result)));

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("------------------", "购物车错误  " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(getcart_list2_err));

                    }
                });
    }

    public void delCartGoods(String cart_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("cart_id", cart_id);
        Log.i("----------------", "删除  " + cart_id);
        httpUtils.send(HttpMethod.POST, Mark.delete_goods, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "删除商品a  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(drop_cart_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("----------------", "删除错误  " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(drop_cart_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(drop_cart_start));
                    }
                });

    }

    public void upCartGoodsNum(String cart_id, final String quantity,
                               final CartListInfo info, final ViewHolder holder) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("cart_id", cart_id);
        params.addBodyParameter("quantity", quantity);
        httpUtils.send(HttpMethod.POST, Mark.quantity_update, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        CartListInfo info2 = JsonParse
                                .CartQuantityUpdate(responseInfo.result);
                        if (info2 != null) {
                            holder.price.setText("¥" + info2.getGoods_price());
                            holder.num.setText(info2.getGoods_num());
                            holder.tv_amountfcf1.setText("X"
                                    + holder.num.getText().toString());
                            info.setGoods_num(quantity);
                            handler.sendMessage(handler.obtainMessage(
                                    up_cart_num_id, "1"));
                            handler.sendMessage(handler.obtainMessage(0));
                        } else {
                            handler.sendMessage(handler.obtainMessage(
                                    up_cart_num_id, "0"));
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(up_cart_num_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(up_cart_num_start));
                    }
                });
    }

    public void addInCart(String goods_id, String quantity) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("quantity", quantity);
        httpUtils.send(HttpMethod.POST, add_cart, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(add_cart_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(add_cart_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(add_cart_start));
                    }
                });
    }

    public void getProductDetails(String goods_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("goods_id", goods_id);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, product_detils, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d(responseInfo.result);
                        int maxLogSize = 4000;
                        String veryLongString =
                                responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            Log.i("----------------", " 商品详情 " + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                goods_details_id, JsonParse
                                        .getProductDetails(responseInfo.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(goods_details_start));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(goods_details_err));

                    }
                });
    }

    public void login(String username, String password, String client) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("username", username);
        params.addBodyParameter("password", password);
        params.addBodyParameter("client", client);
        httpUtils.send(HttpMethod.POST, user_login, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("------------------", "登录   " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(login_id,
                                JsonParse.login(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(login_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler.obtainMessage(login_start));
                    }
                });
    }

    /**
     * 获得微信登录的信息
     *
     * @param code   登录返回值
     * @param appid  APP_ID
     * @param secret APP秘钥
     */
    public void wxlogin(String code, String appid, String secret) {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.POST,
                "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid="
                        + appid + "&secret=" + secret + "&code=" + code
                        + "&grant_type=authorization_code", null,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(wxlogin_id,
                                JsonParse.wxlogin(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(wxlogin_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(wxlogin_start));
                    }
                });
    }

    /**
     * 微信登陆第二步
     *
     * @param appid         应用唯一标识
     * @param refresh_token 填写通过access_token获取到的refresh_token参数
     */
    public void wxlogin2(String appid, String refresh_token) {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.POST,
                "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
                        + appid + "&grant_type=refresh_token&refresh_token="
                        + refresh_token, null, new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(wx2login_id,
                                JsonParse.wx2login(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler.obtainMessage(wx2login_err));
                    }

                });
    }

    /**
     * 微信登录第三步
     *
     * @param access_token 调用凭证
     * @param openid       普通用户的标识，对当前开发者帐号唯一
     */
    public void wxlogin3(String access_token, String openid) {
        // https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.POST,
                "https://api.weixin.qq.com/sns/userinfo?access_token="
                        + access_token + "&openid=" + openid, null,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(wx3login_id,
                                JsonParse.wx3login(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(wx3login_err));
                    }
                });
    }

    public void userOut() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("username", State.User);
        params.addBodyParameter("client", "android");
        httpUtils.send(HttpMethod.POST, user_logout, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(userOut_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(userOut_err));
                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(userOut_start));
                        super.onStart();
                    }
                });
    }

    /**
     * 短信注册请求
     *
     * @param phone
     * @param secret
     */
//    public void smaRegister(String regtype,String phone, String secret,String password,String password_confirm,String email) {
    public void smaRegister(String phone, String secret) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("secret", secret);
        params.addBodyParameter("password", "");
        /*params.addBodyParameter("vercode", secret);
        params.addBodyParameter("regtype", regtype);
        params.addBodyParameter("username", phone);
        params.addBodyParameter("password", password);
        params.addBodyParameter("password_confirm", password_confirm);
        params.addBodyParameter("email", email);*/
        httpUtils.send(HttpMethod.POST, user_sms_singup, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("------------------", "短信注册 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                sms_register_id,
                                JsonParse.smaRegister(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(sms_register_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(sms_register_start));
                    }
                });
    }

    /**
     * 普通注册请求
     *
     * @param username
     * @param password
     * @param password_confirm
     * @param email
     */
    public void register(String username, String password,
                         String password_confirm, String email) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("username", username);
        params.addBodyParameter("password", password);
        params.addBodyParameter("password_confirm", password_confirm);
        params.addBodyParameter("email", email);
        params.addBodyParameter("client", "android");

        httpUtils.send(HttpMethod.POST, user_singup, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(register_id,
                                JsonParse.register(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(register_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(register_start));
                    }
                });
    }

    /**
     * 更改地址
     *
     * @param name
     * @param phone
     * @param mobile
     * @param address
     * @param current_address
     * @param add_address_port
     */
    public void postAddress(String address_id, String city_id, String area_id, String country_id, String province_id,
                            String name, String phone, String mobile, String address,
                            String current_address, String add_address_port, String position) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        if (address_id != null)
            params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("true_name", name);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("country_id", country_id);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("area_info", current_address);
        params.addBodyParameter("address", address);
        params.addBodyParameter("tel_phone", phone);
        params.addBodyParameter("mob_phone", mobile);
        params.addBodyParameter("points", position);
        httpUtils.send(HttpMethod.POST, add_address_port, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                edit_address_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(edit_address_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(edit_address_start));
                    }
                });
    }

    public void getAddressPosition(String address, RequestCallBack<String> callBack) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, GooglePositon + address, params,
                callBack);
    }

    public void getStoreDetails(String store_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("store_id", store_id);
        httpUtils.configCurrentHttpCacheExpiry(1000);// 缓存
        System.out.println("------------------店铺信息   " + store_detils
                + "&store_id=" + store_id);
        httpUtils.send(HttpMethod.GET, store_detils, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                store_detils_id,
                                JsonParse.getStoreDetils(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(store_detils_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(store_detils_start));
                    }
                });
    }

    /**
     * 取消订单
     *
     * @param order_id
     */
    public void deteleOrder(String order_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        httpUtils.send(HttpMethod.POST, detele_order, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                detele_order_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(detele_order_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(detele_order_start));
                    }
                });
    }

    /**
     * 订单物流
     *
     * @param order_id
     */
    public void searchSeliver(String order_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        httpUtils.send(HttpMethod.POST, search_deliver, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                search_deliver_id,
                                JsonParse.searchSeliver(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(search_deliver_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(search_deliver_start));
                    }
                });
    }

    /**
     * 订单列表
     *
     * @param order_state
     * @param page
     * @param curpage
     */
    public void getOrderList(final String order_state, String page,
                             final String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("page", page);
        if (!order_state.equals("0"))
            params.addBodyParameter("type", order_state);
        params.addQueryStringParameter("curpage", curpage);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, order_list, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------订单列表     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                order_list_id, Integer.valueOf(order_state), 0,
                                JsonParse.getOrderList(responseInfo.result,
                                        order_state, curpage)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(order_list_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(order_list_start));
                    }
                });
    }

    /**
     * 确认收货
     *
     * @param order_id
     */
    public void orderReceive(String order_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        httpUtils.send(HttpMethod.POST, order_receive, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                order_receive_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(order_receive_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(order_receive_start));
                    }
                });
    }

    /**
     * 更换收货地址
     *
     * @param freight_hash 运费
     * @param city_id      城市ID
     * @param area_id      地区ID
     *                     是否提示信息
     */
    public void changeAddress(String freight_hash, String city_id,
                              String area_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("freight_hash", freight_hash);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        httpUtils.send(HttpMethod.POST, change_address, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                change_address_id,
                                JsonParse.changeAddress(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(change_address_err));
                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(change_address_start));
                        super.onStart();
                    }
                });
    }

    /**
     * @param cart_id 购买参数
     */
    public void buyStepOne(String ifcart, String cart_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("cart_id", cart_id);
        if (ifcart.equals("1"))
            params.addBodyParameter("ifcart", "1");
        Log.i("----------------", "购买第一步  key=" + State.UserKey + "  cart_id=" + cart_id);
        httpUtils.send(HttpMethod.POST, buy_step1, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        LogUtils.d(response.result);
                        int maxLogSize = 4000;
                        String veryLongString =
                                response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------购买第一步     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(buy_step1_id,
                                JsonParse.buySteOne(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(buy_step1_err));

                    }

                    @Override
                    public void onStart() {
                        handler.sendMessage(handler
                                .obtainMessage(buy_step1_start));
                        super.onStart();
                    }
                });
    }

    /**
     * @param ifcart
     * @param cart_id
     * @param address_id
     * @param vat_hash
     * @param offpay_hash
     * @param offpay_hash_batch
     * @param pay_name          付款方式，可选值 online(线上付款) offline(货到付款
     * @param invoice_id
     * @param pd_pay            是否使用预存款支付 1-使用 0-不使用
     * @param password          支付密码
     */
    public void buyStepTwo(String ifcart, String cart_id, String address_id,
                           String vat_hash, String offpay_hash, String offpay_hash_batch,
                           String pay_name, String invoice_id, String pd_pay, String password,
                           String fcode, String pay_message) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        if (ifcart.equals("1"))
            params.addBodyParameter("ifcart", "1");
        params.addBodyParameter("cart_id", cart_id);
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("vat_hash", vat_hash);
        params.addBodyParameter("offpay_hash", offpay_hash);
        params.addBodyParameter("offpay_hash_batch", offpay_hash_batch);
        params.addBodyParameter("pay_name", pay_name);
        params.addBodyParameter("invoice_id", invoice_id);
        params.addBodyParameter("pay_message", pay_message);
        params.addBodyParameter("voucher", "");
        if (pay_name.equals("online"))
            params.addBodyParameter("pd_pay", pd_pay);
        params.addBodyParameter("password", password);
        params.addBodyParameter("fcode", fcode);
        Log.i("-----------------", "购买第二步  key=" + State.UserKey + "  cart_id=" + cart_id
                + "  address_id=" + address_id + "  vat_hash=" + vat_hash + "  offpay_hash=" + offpay_hash + "  offpay_hash_batch=" + offpay_hash_batch + "  pay_name=" + pay_name
                + "  invoice_id=" + invoice_id + "  voucher=" + "  pd_pay=" + pd_pay + "  password=" + password + "  fcode=" + fcode);
        httpUtils.send(HttpMethod.POST, buy_step2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(buy_step2_id,
                                JsonParse.buyStepTwo(responseInfo.result)));
                        System.out.println("------------------"
                                + responseInfo.result.toString());
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(buy_step2_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(buy_step2_start));
                    }
                });
    }

    public void order_del(String order_del) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("pay_sn", order_del);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, orderdetails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                orderdetails_id,
                                JsonParse.getorderinfo(responseInfo.result)));
                        System.out.println("---------------order_del---"
                                + responseInfo.result.toString());
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(orderdetails_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(buy_step2_start));
                    }
                });
    }

    public void checkPassword(final String string) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("password", string);
        httpUtils.send(HttpMethod.POST, check_password, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                check_password_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(check_password_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(check_password_start));
                    }
                });
    }

    /**
     * 获取验证码
     *
     * @param string
     */
    public void getSecret(String string) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("phone", string);
        httpUtils.send(HttpMethod.POST, get_secret, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("------------------", "获取验证码 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                get_secret_id,
                                JsonParse.getSecret(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(get_secret_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(get_secret_start));
                    }
                });
    }

    public void reSetPassWord(String old_pwd, String new_pwd) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("old_password", old_pwd);
        params.addBodyParameter("new_password", new_pwd);
        httpUtils.send(HttpMethod.POST, change_password, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                change_password_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(change_password_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(change_password_start));
                    }
                });
    }

    public void wxpay(String body, String pay_sn, String price) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("body", body);
        params.addBodyParameter("out_trade_no", pay_sn);
        params.addBodyParameter("total_fee", price);
        httpUtils.send(HttpMethod.POST, prepay_wxpay, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(prepay_wxpay_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                prepay_wxpay_id, JsonParse.wxpay(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(prepay_wxpay_start));
                    }
                });
    }

    /**
     * 评论提交
     *
     * @param order_sn
     * @param string
     */
    public void Comment(String order_sn, String string) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_sn", order_sn);
        params.addBodyParameter("good_arr", string);
        System.out.println("------------------评论参数  key=" + State.UserKey
                + "  order_sn=" + order_sn + "  good_arr=" + string);
        httpUtils.send(HttpMethod.POST, comment, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler.obtainMessage(comment_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(comment_id,
                                JsonParse.Operation(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(comment_start));
                    }
                });
    }

    public void findPassStep1(String string) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("mobile_phone", string);
        httpUtils.send(HttpMethod.POST, find_pass_step1, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(find_pass_step1_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                find_pass_step1_id,
                                JsonParse.Operation2(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(find_pass_step1_start));
                    }
                });
    }

    /**
     * 忘记密码
     *
     * @param username
     * @param check_code
     * @param password
     * @param password_confirm
     */
    public void findPassStep2(String username, String check_code,
                              String password, String password_confirm) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("mobile_phone", username);
        params.addBodyParameter("secret", check_code);
        params.addBodyParameter("password", password);
        params.addBodyParameter("password_confirm", password_confirm);
        httpUtils.send(HttpMethod.POST, find_pass_step2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(find_pass_step2_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                find_pass_step2_id,
                                JsonParse.Operation2(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(find_pass_step2_start));
                    }
                });
    }

    /**
     * 评价
     *
     * @param order_sn
     */
    public void evaluateGoodList(String order_sn) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("order_sn", order_sn);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, evaluate_goods_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(evaluate_goods_list_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------评价     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                evaluate_goods_list_id,
                                JsonParse.evaluateGoodList(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(evaluate_goods_list_start));
                    }
                });
    }

    /**
     * 资讯首页
     */
    public void informationhome() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, information_home,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                information_home_id,
                                JsonParse.getinformationhome(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(information_home_err));
                    }
                });
    }

    /**
     * 资讯分类
     */
    public void informationclassify(String class_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("class_id", class_id);
        httpUtils.send(HttpMethod.GET, information_classify, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                information_classify_id,
                                JsonParse.getinformationclassify(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(information_classify_err));
                    }
                });
    }

    /**
     * 随心看
     */
    public void casuallylook() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = casually_look;
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        handler.sendMessage(handler.obtainMessage(
                                casually_look_id,
                                JsonParse.getcasually_look(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(casually_look_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 个人秀
     */
    public void Goodthings() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = good_things;
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        handler.sendMessage(handler.obtainMessage(
                                good_things_id,
                                JsonParse.getcasually_look(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(good_things_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 推荐商品详情
     */
    public void casuallylookgoodsinfo(String goods_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("goods_id", goods_id);
        System.out.println("-------------------" + casuallylook_goodsinfo
                + "&goods_id=" + goods_id);
        httpUtils.send(HttpMethod.GET, casuallylook_goodsinfo, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                casuallylook_goodsinfo_id,
                                JsonParse.getCommunityGoodsInfo(arg0.result)));
                        System.out.println("----------------"
                                + arg0.result.toString());
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(casuallylook_goodsinfo_err));
                    }
                });
    }

    /**
     * 推荐商品评价
     *
     * @param commend_id      信息编号
     * @param comment_type    1.随心看 2.个人秀
     * @param comment_message 评论内容
     */
    public void RecommendedEvaluation(String commend_id, String comment_type,
                                      String comment_message) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commend_id", commend_id);
        params.addBodyParameter("comment_type", comment_type);
        params.addBodyParameter("comment_message", comment_message);
        httpUtils.send(HttpMethod.POST, recommended_evaluation, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(recommended_evaluation_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                recommended_evaluation_id,
                                JsonParse.getrecommendedevaluation(arg0.result)));
                        System.out.println("--------------"
                                + arg0.result.toString());
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(evaluate_goods_list_start));
                    }
                });
    }

    /**
     * 赞
     *
     * @param commend_id
     * @param comment_type
     */
    public void Praise(String commend_id, String comment_type) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("comment_id", commend_id);
        params.addBodyParameter("comment_type", comment_type);
        System.out.println("---------------commend_id--" + commend_id);
        httpUtils.send(HttpMethod.POST, look_praise, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(look_praise_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                look_praise_id,
                                JsonParse.getrecommendedevaluation(arg0.result)));
                        System.out.println("--------------"
                                + arg0.result.toString());
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(look_praise_start));
                    }
                });
    }

    /**
     * 发现评论列表
     */
    public void foundcommenlist(final String commend_id,
                                final String comment_type, final String curpage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = found_commenlist + "&commend_id="
                            + commend_id + "&comment_type=" + comment_type
                            + "&curpage=" + curpage;
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        System.out.println("------------------" + result);
                        handler.sendMessage(handler.obtainMessage(
                                found_commenlist_id,
                                JsonParse.getFoundcommenlist(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(found_commenlist_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 新闻分类
     */
    public void newsclassify() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, article_class,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                article_class_id,
                                JsonParse.getnewsclassify(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(article_class_err));
                    }
                });
    }

    /**
     * 广告轮播
     */
    public void advertisinglb() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, top_advertising,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                top_advertising_id,
                                JsonParse.getadvertisinglb(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(top_advertising_err));
                    }
                });
    }

    /**
     * 置顶新闻
     */
    public void topnews() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, top_news, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                handler.sendMessage(handler.obtainMessage(top_news_id,
                        JsonParse.gettopnews(arg0.result)));
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                System.out.println("--------------失败");
                handler.sendMessage(handler.obtainMessage(top_news_err));
            }
        });
    }

    /**
     * 推荐新闻
     */
    public void recommendednews() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, recommended_news,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                recommended_news_id,
                                JsonParse.gettopnews(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(recommended_news_err));
                    }
                });
    }

    /**
     * 新闻分类列表
     */
    public void newslistify(String class_id, String pagesize) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("class_id", class_id);
        params.addQueryStringParameter("pagesize", pagesize);
        System.out.println("------------------快报   " + new_slistify
                + "&class_id=" + class_id + "&pagesize=" + pagesize);
        httpUtils.send(HttpMethod.GET, new_slistify, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                new_slistify_id,
                                JsonParse.getNewslistifyInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(new_slistify_err));
                    }
                });
    }

    /**
     * 新闻详情
     */
    public void Newsinfo(String article_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("article_id", article_id);
        System.out.println("--------------------" + news_info + "&article_id="
                + article_id);
        httpUtils.send(HttpMethod.GET, news_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(news_info_id,
                                JsonParse.getNewsdetails(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(news_info_err));
                    }
                });
    }

    /**
     * 相关新闻
     *
     * @param class_id
     */
    public void Relatednews(String class_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("class_id", class_id);
        System.out.println("--------------------" + related_news + "&class_id="
                + class_id);
        httpUtils.send(HttpMethod.GET, related_news, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                related_news_id,
                                JsonParse.getRelatednews(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(related_news_err));
                    }
                });
    }

    /**
     * 社区
     * <p>
     * 是否推荐（0：否 1：是）
     */
    public void communityall(String pagesize, String is_recommend) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("is_recommend", is_recommend);
        params.addQueryStringParameter("pagesize", pagesize);
        System.out.println("---------------社区  " + community_all
                + "&is_recommend=" + is_recommend + "&pagesize=" + pagesize);
        httpUtils.send(HttpMethod.GET, community_all, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {

                        handler.sendMessage(handler.obtainMessage(
                                community_all_id,
                                JsonParse.getcommunityall(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(community_all_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(community_all_start));
                    }
                });
    }

    /**
     * 添加新闻评论
     *
     * @param article_id
     * @param comment_message
     */
    public void Addcomment(String article_id, String comment_message) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("article_id", article_id);
        params.addBodyParameter("comment_message", comment_message);
        httpUtils.send(HttpMethod.POST, add_comment, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(add_comment_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                add_comment_id,
                                JsonParse.getrecommendedevaluation(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(add_comment_start));
                    }
                });
    }

    /**
     * 文章评论列表
     *
     * @param article_id
     */
    public void Wzcommentlist(final String article_id, final String curpage) {
        // params = new RequestParams();
        // httpUtils = new HttpUtils();
        // params.addQueryStringParameter("article_id", article_id);
        // params.addQueryStringParameter("curpage", curpage);
        // httpUtils.send(HttpMethod.GET, wz_commentlist, params,
        // new RequestCallBack<String>() {
        //
        // @Override
        // public void onSuccess(ResponseInfo<String> arg0) {
        // handler.sendMessage(handler.obtainMessage(
        // wz_commentlist_id,
        // JsonParse.getNewsCommenlistinfo(arg0.result)));
        // }
        //
        // @Override
        // public void onFailure(HttpException arg0, String arg1) {
        // System.out.println("--------------失败");
        // handler.sendMessage(handler
        // .obtainMessage(wz_commentlist_err));
        // }
        // });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = wz_commentlist + "&article_id=" + article_id
                            + "&curpage=" + curpage;
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        handler.sendMessage(handler.obtainMessage(
                                wz_commentlist_id,
                                JsonParse.getNewsCommenlistinfo(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(wz_commentlist_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 新闻赞
     */
    public void Newspraise(String comment_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("comment_id", comment_id);
        httpUtils.send(HttpMethod.POST, news_praise, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(news_praise_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                news_praise_id,
                                JsonParse.getrecommendedevaluation(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(news_praise_start));
                    }
                });
    }

    /**
     * 达人推荐
     */
    public void StaffPicks() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, good_things,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                good_things_id,
                                JsonParse.getStaffPicksInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(good_things_err));
                    }
                });
    }

    /**
     * 店铺街
     */
    public void Shopstreet() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, shop_street,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                shop_street_id,
                                JsonParse.getShopStreetInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(shop_street_err));
                    }
                });
    }

    /**
     * 话题列表
     *
     * @param is_recommend
     * @param circle_id
     */
    public void Topicslist(String is_recommend, String circle_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("is_recommend", is_recommend);
        params.addQueryStringParameter("circle_id", circle_id);
        httpUtils.send(HttpMethod.GET, topics_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                topics_list_id,
                                JsonParse.getTopicslistinfo(arg0.result)));

                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(topics_list_err));
                    }
                });
    }

    /**
     * 话题评论列表
     *
     * @param theme_id
     */
    public void Topicscommentlist(final String theme_id, final String curpage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = topics_commentlist + "&theme_id=" + theme_id
                            + "&curpage=" + curpage;
                    System.out.println("-------------------话题评论列表   " + path);
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        handler.sendMessage(handler.obtainMessage(
                                topics_commentlist_id,
                                JsonParse.getTopicscommentlistinfo(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(topics_commentlist_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 话题评价
     *
     * @param theme_id
     * @param reply_content
     */
    public void Topicscomment(String theme_id, String reply_content) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("theme_id", theme_id);
        params.addBodyParameter("reply_content", reply_content);
        httpUtils.send(HttpMethod.POST, topics_comment, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(topics_comment_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                topics_comment_id,
                                JsonParse.getrecommendedevaluation(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(topics_comment_start));
                    }
                });
    }

    /**
     * 话题详情
     *
     * @param theme_id
     */
    public void Topicdateils(String theme_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("theme_id", theme_id);
        httpUtils.send(HttpMethod.GET, topic_dateils, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                topic_dateils_id,
                                JsonParse.getTopicdateilsInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(topic_dateils_err));
                    }
                });
    }

    /**
     * 我的圈子
     */
    public void MySocial() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, my_social, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(my_social_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // handler.sendMessage(handler.obtainMessage(
                        // my_social_id,
                        // JsonParse.getrecommendedevaluation(arg0.result)));
                        System.out.println("-----------------"
                                + arg0.result.toString());
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(my_social_start));
                    }
                });
    }

    /**
     * 咨询列表
     *
     * @param curpage
     */
    public void CustomerServiceList(final String curpage) {
        // params = new RequestParams();
        // httpUtils = new HttpUtils();
        // params.addQueryStringParameter("key", State.UserKey);
        // params.addQueryStringParameter("curpage", curpage);
        // httpUtils.send(HttpMethod.GET, customerservice_list, params,
        // new RequestCallBack<String>() {
        //
        // @Override
        // public void onSuccess(ResponseInfo<String> arg0) {
        // handler.sendMessage(handler.obtainMessage(
        // customerservice_list_id,
        // JsonParse.getCustomerService(arg0.result)));
        // }
        //
        // @Override
        // public void onFailure(HttpException arg0, String arg1) {
        // System.out.println("--------------失败");
        // handler.sendMessage(handler
        // .obtainMessage(customerservice_list_err));
        // }
        // });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = customerservice_list + "&key="
                            + State.UserKey + "&curpage=" + curpage;
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        System.out.println("--------------   " + result);
                        handler.sendMessage(handler.obtainMessage(
                                customerservice_list_id,
                                JsonParse.getCustomerService(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(customerservice_list_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 咨询详情
     */
    public void CustomerDetails(String id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("id", id);
        httpUtils.send(HttpMethod.GET, customer_details, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                customer_details_id,
                                JsonParse.getCustomerDetails(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(customer_details_err));
                    }
                });
    }

    /**
     * 添加咨询
     */
    public void AddCustomer() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        Log.i("---------------", "咨询添加   " + customer_add + "&key="
                + State.UserKey);
        httpUtils.send(HttpMethod.GET, customer_add, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                customer_add_id,
                                JsonParse.getConsultingTypeinfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(customer_add_err));
                    }
                });
    }

    /**
     * 提交咨询
     */
    public void SubmitCustomer(String mct_id, String mc_content) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("mct_id", mct_id);
        params.addBodyParameter("mc_content", mc_content);
        httpUtils.send(HttpMethod.POST, submit_customer, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                submit_customer_id,
                                JsonParse.SubmitConsulting(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(submit_customer_err));
                    }
                });
    }

    /**
     * 删除咨询
     */
    public void DeleteCustomer(String id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("id", id);
        httpUtils.send(HttpMethod.GET, delete_customer, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("--------------  "
                                + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delete_customer_id,
                                JsonParse.SubmitConsulting(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delete_customer_err));
                    }
                });
    }

    /**
     * 线上支付
     */
    public void OnlineTopup(String pdr_amount) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("pdr_amount", pdr_amount);
        httpUtils.send(HttpMethod.POST, online_topup, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "充值   " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                online_topup_id,
                                JsonParse.getOnlineTopup(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(online_topup_err));
                    }
                });
    }

    /**
     * 分销中心首页
     */
    public void DistributionCenter() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, distribution_center, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                distribution_center_id,
                                JsonParse
                                        .getDistributionCenterInfo(responseInfo.result)));
                        System.out.println("---------分销中心首页 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(distribution_center_err));
                    }
                });
    }

    /**
     * 我的推荐人
     */
    public void MyReferee() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, my_referee, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------我的推荐人     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                my_referee_id,
                                JsonParse.getMyRefereeInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(my_referee_err));
                    }
                });
    }

    /**
     * 我的团队
     *
     * @param pagesize
     * @param curpage
     */
    public void MyTeam(String pagesize, String curpage, String level) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("level", level);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, my_team, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(my_team_id,
                                JsonParse.getMyTeamInfo(responseInfo.result)));
                        System.out.println("---------我的团队 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(my_team_err));
                    }
                });
    }

    /**
     * 交易投诉列表
     */
    public void ComplaintsList(String curpage, String complain_state) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("complain_state", complain_state);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        System.out.println("----------------交易投诉列表  " + complaints_list
                + "&key=" + State.UserKey + "&curpage=" + curpage
                + "&complain_state=" + complain_state);
        httpUtils.send(HttpMethod.GET, complaints_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                complaints_list_id,
                                JsonParse
                                        .getComplaintslistInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(complaints_list_err));
                    }
                });
    }

    /**
     * 投诉申请
     */
    public void Complaint(String order_id, String goods_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("goods_id", goods_id);
        Log.i("---------------", complaints + "&key=" + State.UserKey + "&order_id=" + order_id + "&goods_id=" + goods_id);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.POST, complaints, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------------投诉 "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                complaints_id, JsonParse
                                        .getComplaintsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(complaints_err));
                    }
                });
    }

    /**
     * 取消投诉
     */
    public void CancelComplaint(String complain_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("complain_id", complain_id);
        httpUtils.send(HttpMethod.POST, cancel_complaint, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // System.out.println("--------------------取消投诉 "
                        // + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                cancel_complaint_id,
                                JsonParse.getTopiczan(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(cancel_complaint_err));
                    }
                });
    }

    /**
     * 获取投诉对话
     */
    public void getComplainTalk(String complain_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("complain_id", complain_id);
        httpUtils.send(HttpMethod.POST, get_complain_talk, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "获取投诉对话  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                get_complain_talk_id,
                                JsonParse.getComplaintDialogueInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败 " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(get_complain_talk_err));
                    }
                });
    }

    /**
     * 发布投诉对话
     */
    public void publishComplainTalk(String complain_id, String complain_talk) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("complain_id", complain_id);
        params.addBodyParameter("complain_talk", complain_talk);
        httpUtils.send(HttpMethod.POST, publish_complain_talk, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "发布投诉对话  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                publish_complain_talk_id,
                                JsonParse.getpublishComplainTalk(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------发布投诉对话失败 " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(publish_complain_talk_err));
                    }
                });
    }

    /**
     * 提交仲裁
     */
    public void ApplyHandle(String input_complain_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("input_complain_id", input_complain_id);
        httpUtils.send(HttpMethod.POST, apply_handle, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "提交仲裁  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                apply_handle_id,
                                JsonParse.getApplyHandle(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------提交仲裁失败 " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(apply_handle_err));
                    }
                });
    }

    /**
     * 投诉详情
     */
    public void Complaintdetails(String complain_id, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("complain_id", complain_id);
        params.addBodyParameter("curpage", curpage);
        httpUtils.send(HttpMethod.POST, complaint_details, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------------投诉 "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                complaint_details_id,
                                JsonParse
                                        .getComplaintDetailsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(complaint_details_err));
                    }
                });
    }

    /**
     * 退款列表
     */
    public void RefundApply(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("pagesize", pagesize);
        params.addBodyParameter("curpage", curpage);
        httpUtils.send(HttpMethod.POST, refund_apply, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------退款列表     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                refund_apply_id,
                                JsonParse.getRefundInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(refund_apply_err));
                    }
                });
    }

    /**
     * 账户安全
     */
    public void YYAccountSecurity() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, account_security, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("-----------------账户安全  "
                                + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                account_security_id, JsonParse
                                        .getPhoneCertificationInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(account_security_err));
                    }
                });
    }

    /**
     * 绑定手机验证码
     */
    public void BindingPhone(String mobile) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("mobile", mobile);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, binding_phone, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        handler.sendMessage(handler.obtainMessage(
                                binding_phone_id,
                                JsonParse.BindingPhone(response.result)));
                        System.out.println("-------------验证  "
                                + response.result.toString() + "   "
                                + State.UserKey);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                binding_phone_err, binding_phone_err));
                    }
                });
    }

    /**
     * 绑定手机
     */
    public void BindingPhone2(String mobile, String vcode) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("vcode", vcode);
        params.addBodyParameter("mobile", mobile);
        Log.i("-----------------", "key=" + State.UserKey + " vcode=" + vcode + " mobile=" + mobile);
        httpUtils.send(HttpMethod.POST, binding_phone2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                binding_phone2_id,
                                JsonParse.BindingPhone2(responseInfo.result)));
                        System.out.println("-----------------绑定  "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(binding_phone2_err));
                    }
                });
    }

    /**
     * 邮箱绑定
     */
    public void YYEmailBinding(String email) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("email", email);
        httpUtils.send(HttpMethod.POST, email_bunding, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                email_bunding_id,
                                JsonParse.getPersonaldatasave(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(email_bunding_err));
                    }
                });
    }

    /**
     * 获得资料
     *
     * @param type
     */
    public void PayPsw2(String type) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("type", type);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, pay_psw2, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------循环打印     "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(pay_psw2_id,
                                JsonParse.PayPsw2(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(pay_psw2_err,
                                pay_psw2_err));
                    }
                });
    }

    /**
     * 设置支付密码
     */
    public void PayPsw(String password, String confirm_password,
                       String auth_modify_paypwd) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("password", password);
        params.addBodyParameter("confirm_password", confirm_password);
        params.addBodyParameter("auth_modify_paypwd", auth_modify_paypwd);
        httpUtils.send(HttpMethod.POST, pay_psw, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(pay_psw_id,
                                JsonParse.BindingPhone2(responseInfo.result)));
                        System.out.println("-----------------  "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler.obtainMessage(pay_psw_err));
                    }
                });
    }

    /**
     * 实名认证
     */
    public void YYIdentity() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, identity, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("-----------------实名认证   "
                                + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(identity_id,
                                JsonParse.getIdentityInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler.obtainMessage(identity_err));
                    }
                });
    }

    /**
     * 实名认证保存
     */
    public void YYIdentitysave(String truename, String CardNO,
                               final File id_card_img) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("truename", truename);
        params.addBodyParameter("CardNO", CardNO);
        if (id_card_img.exists()) {
            params.addBodyParameter("id_card_img", id_card_img);
        }
        httpUtils.send(HttpMethod.POST, identity_save, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        id_card_img.delete();
                        System.out.println("------------------实名认证保存   "
                                + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                identity_save_id,
                                JsonParse.getPersonaldatasave(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        id_card_img.delete();
                        handler.sendMessage(handler
                                .obtainMessage(identity_save_err));
                    }
                });
    }

    /**
     * 实名认证保存
     */
    public void YYIdentitysave(String mobile, final File id_card_img,
                               final File business_license_img, String is_live) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("mobile", mobile);
//        params.addBodyParameter("is_live", is_live);
        if (id_card_img != null && id_card_img.exists()) {
            params.addBodyParameter("id_card_img", id_card_img);
        }
        if (business_license_img != null && business_license_img.exists()) {
            params.addBodyParameter("business_license_img",
                    business_license_img);
        }
        httpUtils.send(HttpMethod.POST, identity_save2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (id_card_img != null)
                            id_card_img.delete();
                        System.out.println("------------------实名认证保存   "
                                + arg0.result);
                        try {
                            JSONObject Object = new JSONObject(arg0.result);
                            String succ = Object.getJSONObject("datas").getString("succ");
                            if (succ == null || succ.equals("")) {

                                handler.sendMessage(handler
                                        .obtainMessage(identity_save_err, Object.getJSONObject("datas").getString("error")));
                            } else {
                                handler.sendMessage(handler.obtainMessage(
                                        identity_save_id, succ
                                ));
                            }
//                  getYYIdentitysave (arg0.result)
                        } catch (JSONException e) {
                            System.out.println("-----------  错误");
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("--------------失败");
                        if (id_card_img != null)
                            id_card_img.delete();
                        handler.sendMessage(handler
                                .obtainMessage(identity_save_err));
                    }
                });
    }

    /**
     * 统一验证验证码
     */
    public void AuthSubmit(String type, String auth_code) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("type", type);
        params.addBodyParameter("auth_code", auth_code);
        httpUtils.send(HttpMethod.POST, authsu_bmit, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                authsu_bmit_id,
                                JsonParse.BindingPhone2(responseInfo.result)));
                        System.out.println("-----------------  "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(authsu_bmit_err));
                    }
                });
    }

    /**
     * 举报列表
     *
     * @param curpage
     * @param select_inform_state
     */
    public void ReportList(String curpage, String select_inform_state) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("select_inform_state",
                select_inform_state);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, report_list, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------举报列表     "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                report_list_id,
                                JsonParse.getReportListInfo(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                report_list_err, report_list_err));
                    }
                });
    }

    /**
     * 举报详情
     */
    public void ReportDatalis(String inform_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("inform_id", inform_id);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, report_datalis, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------举报详情     "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                report_datalis_id,
                                JsonParse.getReportDatalisInfo(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                report_datalis_err, report_datalis_err));
                    }
                });
    }

    /**
     * 举报信息填写
     *
     * @param goods_id
     */
    public void GoodsReport(String goods_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("goods_id", goods_id);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, goods_report, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------举报信息填写     "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                goods_report_id,
                                JsonParse.getGoodsReportInfo(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                goods_report_err, goods_report_err));
                    }
                });
    }

    /**
     * 举报信息填写
     *
     * @param room_id
     */
    public void RoomReport(String room_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("goods_id", room_id);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, room_report, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------举报信息填写     "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                goods_report_id,
                                JsonParse.getGoodsReportInfo(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                goods_report_err, goods_report_err));
                    }
                });
    }

    /**
     * 提交举报信息
     */
    public void SubmitReport(String inform_goods_id,
                             String inform_subject_type, String inform_subject,
                             String inform_content, final File inform_pic1,
                             final File inform_pic2, final File inform_pic3) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("inform_goods_id", inform_goods_id);
        params.addBodyParameter("inform_subject_type", inform_subject_type);
        params.addBodyParameter("inform_subject", inform_subject);
        params.addBodyParameter("inform_content", inform_content);
        if (inform_pic1 != null && inform_pic1.exists()) {
            params.addBodyParameter("inform_pic1", inform_pic1);
        }
        if (inform_pic2 != null && inform_pic2.exists()) {
            params.addBodyParameter("inform_pic2", inform_pic2);
        }
        if (inform_pic3 != null && inform_pic3.exists()) {
            params.addBodyParameter("inform_pic3", inform_pic3);
        }

        httpUtils.send(HttpMethod.POST, submit_report, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------提交举报信息     "
                                    + veryLongString.substring(start, end));
                        }
                        if (inform_pic1 != null) {
                            inform_pic1.delete();
                        }
                        if (inform_pic2 != null) {
                            inform_pic2.delete();
                        }
                        if (inform_pic3 != null) {
                            inform_pic3.delete();
                        }
                        handler.sendMessage(handler.obtainMessage(
                                submit_report_id,
                                JsonParse.getSubmitReport(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        if (inform_pic1 != null) {
                            inform_pic1.delete();
                        }
                        if (inform_pic2 != null) {
                            inform_pic2.delete();
                        }
                        if (inform_pic3 != null) {
                            inform_pic3.delete();
                        }
                        handler.sendMessage(handler.obtainMessage(
                                submit_report_err, submit_report_err));
                    }
                });
    }

    /**
     * 提交举报信息
     */
    public void RoomSubmitReport(String inform_goods_id,
                                 String inform_subject_type, String inform_subject,
                                 String inform_content, final File inform_pic1,
                                 final File inform_pic2, final File inform_pic3) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("inform_goods_id", inform_goods_id);
        params.addBodyParameter("inform_subject_type", inform_subject_type);
        params.addBodyParameter("inform_subject", inform_subject);
        params.addBodyParameter("inform_content", inform_content);
        if (inform_pic1 != null && inform_pic1.exists()) {
            params.addBodyParameter("inform_pic1", inform_pic1);
        }
        if (inform_pic2 != null && inform_pic2.exists()) {
            params.addBodyParameter("inform_pic2", inform_pic2);
        }
        if (inform_pic3 != null && inform_pic3.exists()) {
            params.addBodyParameter("inform_pic3", inform_pic3);
        }

        httpUtils.send(HttpMethod.POST, roomsubmit_report, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------提交举报信息     "
                                    + veryLongString.substring(start, end));
                        }
                        if (inform_pic1 != null) {
                            inform_pic1.delete();
                        }
                        if (inform_pic2 != null) {
                            inform_pic2.delete();
                        }
                        if (inform_pic3 != null) {
                            inform_pic3.delete();
                        }
                        handler.sendMessage(handler.obtainMessage(
                                submit_report_id,
                                JsonParse.getSubmitReport(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        if (inform_pic1 != null) {
                            inform_pic1.delete();
                        }
                        if (inform_pic2 != null) {
                            inform_pic2.delete();
                        }
                        if (inform_pic3 != null) {
                            inform_pic3.delete();
                        }
                        handler.sendMessage(handler.obtainMessage(
                                submit_report_err, submit_report_err));
                    }
                });
    }

    /**
     * 申请提现
     */
    public void Withdrawals(String money, String pdc_bank_name,
                            String pdc_bank_no, String pdc_bank_user, String pdc_alipay_account) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("money", money);
        params.addBodyParameter("pdc_bank_name", pdc_bank_name);
        params.addBodyParameter("pdc_bank_no", pdc_bank_no);
        params.addBodyParameter("pdc_bank_user", pdc_bank_user);
        params.addBodyParameter("pdc_alipay_account", pdc_alipay_account);
        httpUtils.send(HttpMethod.POST, apply_withdrawals, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------申请提现     "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                apply_withdrawals_id,
                                JsonParse.getSubmitReport(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                apply_withdrawals_err, apply_withdrawals_err));
                    }
                });
    }

    /**
     * 取消举报
     *
     * @param inform_id
     */
    public void CancelReport(String inform_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("inform_id", inform_id);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, cancel_report, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                        int maxLogSize = 4000;
                        String veryLongString = response.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------取消举报     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                cancel_report_id,
                                JsonParse.getCancelReport(response.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(
                                cancel_report_err, cancel_report_err));
                    }
                });
    }

    /**
     * 系统消息列表
     *
     * @param curpage 当前页 无此参数默认第一页
     */
    public void stationLetterList(String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        if (curpage != null) {
            params.addQueryStringParameter("curpage", curpage);
        }
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, station_letter, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                station_letter_id,
                                JsonParse.getStationList(responseInfo.result)));
                        System.out
                                .println("----------- " + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(station_letter_err));
                    }
                });
    }


    /**
     * 获取头像
     *
     * @param
     */
    public void HuanxinIconMessageList(String member_name, RequestCallBack<String> callBack) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        if (member_name != null) {
            params.addBodyParameter("member_names", member_name);
        }
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.POST, CHATICON_URL, params,
                callBack);
    }

    /**
     * 普通消息列表
     *
     * @param curpage 当前页 无此参数默认第一页
     */
    public void OrdinaryMessageList(String curpage, RequestCallBack<String> callBack) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        if (curpage != null) {
            params.addQueryStringParameter("curpage", curpage);
        }
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, ordinarymessage_list, params,
                callBack);
    }

    /**
     * 普通消息列表
     *
     * @param curpage 当前页 无此参数默认第一页
     */
    public void OrdinaryMessageList(String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        if (curpage != null) {
            params.addQueryStringParameter("curpage", curpage);
        }
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, ordinarymessage_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                ordinarymessage_list_id,
                                JsonParse.getStationList(responseInfo.result)));
                        System.out.println("-----------普通消息列表 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(ordinarymessage_list_err));
                    }
                });
    }

    /**
     * 站内私信列表
     *
     * @param curpage 当前页 无此参数默认第一页
     */
    public void DirectMessages(String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        if (curpage != null) {
            params.addQueryStringParameter("curpage", curpage);
        }
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, direct_messages, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                direct_messages_id,
                                JsonParse.getStationList(responseInfo.result)));
                        System.out.println("-----------站内私信列表 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(direct_messages_err));
                    }
                });
    }

    /**
     * 发送站内信
     *
     * @param msg_content
     * @param to_member_name
     * @param msg_type
     */
    public void SendWebmessage(String msg_content, String to_member_name,
                               String msg_type) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("msg_content", msg_content);
        params.addBodyParameter("to_member_name", to_member_name);
        params.addBodyParameter("msg_type", msg_type);
        httpUtils.send(HttpMethod.POST, send_webmessage, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                send_webmessage_id, JsonParse
                                        .getSendWebmessage(responseInfo.result)));
                        System.out.println("---------发送站内信 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(send_webmessage_err));
                    }
                });
    }

    /**
     * 会员预存款明细
     */
    public void DepositInfo(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("pagesize", pagesize);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, deposit_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                deposit_info_id,
                                JsonParse.getMyMoneyInfo(responseInfo.result)));
                        System.out.println("---------会员预存款明细 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(deposit_info_err));
                    }
                });
    }

    /**
     * 会员佣金明细
     */
    public void CommissionInfo(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("pagesize", pagesize);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, commission_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                commission_info_id,
                                JsonParse.getMyMoneyInfo(responseInfo.result)));
                        System.out.println("---------会员佣金明细 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(commission_info_err));
                    }
                });
    }

    /**
     * 会员积分明细
     */
    public void IntegralInfo(String pagesize, String curpage, String type, String time) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("time", time);
        httpUtils.configCurrentHttpCacheExpiry(500); // 设置缓存
        httpUtils.send(HttpMethod.GET, integral_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                integral_info_id, JsonParse
                                        .getMyIntegralInfo(responseInfo.result)));
                        System.out.println("---------会员积分明细 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(integral_info_err));
                    }
                });
    }

    /**
     * 积分积分类型
     */
    public void IntegralType() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(500); // 设置缓存
        httpUtils.send(HttpMethod.GET, integral_type, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                integral_type_id, JsonParse
                                        .getIntegralTypeInfo(responseInfo.result)));
                        System.out.println("---------会员积分类型 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(integral_type_err));
                    }
                });
    }

    /**
     * 积分规则
     */
    public void IntegralRules() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, integral_rules, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                integral_rules_id,
                                JsonParse
                                        .getIntegralRulesInfo(responseInfo.result)));
                        System.out.println("---------积分规则 "
                                + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(integral_rules_err));
                    }
                });
    }

    /**
     * 客户\搜索列表
     *
     * @param page    每页数量
     * @param curpage 当前页码
     *                手机号码
     * @author gph
     */
    public void CustomerList(String page, String curpage, String mobile) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("mobile", mobile);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, customer_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(customer_list_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                customer_list_id,
                                JsonParse.getCustomerData(arg0.result)));
                        System.out.println("---------------CustomerList=="
                                + arg0.result);
                    }
                });
    }

    /**
     * 获取省级
     */
    public void getProvinceList(String area_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        if (area_id == null) {
            params.addBodyParameter("area_id", area_id);
        }
        httpUtils.send(HttpMethod.POST, sregion_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                get_province_id,
                                JsonParse.getRegionList1(responseInfo.result)));
                        // System.out.println("--- " + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(get_province_err));
                    }
                });
    }

    /**
     * 获取市级
     */
    public void getCityList(String area_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        if (area_id == null) {
            params.addBodyParameter("area_id", area_id);
        }
        httpUtils.send(HttpMethod.POST, sregion_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(get_city_id,
                                JsonParse.getRegionList1(responseInfo.result)));
                        // System.out.println("--- " + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(get_city_err));
                    }
                });
    }

    /**
     * 获取县级
     */
    public void getAreaList(String area_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        if (area_id == null) {
            params.addBodyParameter("area_id", area_id);
        }
        httpUtils.send(HttpMethod.POST, sregion_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(get_area_id,
                                JsonParse.getRegionList1(responseInfo.result)));
                        // System.out.println("--- " + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(get_area_err));
                    }
                });
    }

    /**
     * 添加客户
     *
     * @param username    用户名称
     * @param mobile      手机号码
     * @param area_info   地址(省 市 区)
     * @param province_id 省份编号
     * @param city_id     城市编号
     * @param area_id     地区编号
     * @author gph
     */
    public void addCustomer(String username, String mobile, String area_info,
                            String province_id, String city_id, String area_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("username", username);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("area_info", area_info);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        httpUtils.send(HttpMethod.POST, add_customer, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(add_customer_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                add_customer_id,
                                JsonParse.addCustomerData2(arg0.result)));
                        System.out.println("---------------   " + arg0.result);
                    }

                });
    }

    /**
     * 酒店首页
     */
    public void HotelHome() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, hotel_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(hotel_home_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                hotel_home_id,
                                JsonParse.getHotelHomeInfo(arg0.result)));
                        System.out.println("---------------酒店 " + arg0.result);
                    }
                });
    }

    /**
     * 城市列表选择
     */
    public void CityList() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, city_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(city_list_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(city_list_id,
                                JsonParse
                                        .getHotelChooseAddressInfo(arg0.result)));
                        System.out.println("---------------酒店 " + arg0.result);
                    }
                });
    }

    /**
     * 酒店列表
     */
    public void HotelList(String points, String cityid, String htype,
                          String search_key, String stime, String price, String stars,
                          String brand, String sheshi, String promote, String price_sort,
                          String juli_sort, String pingfen_sort, String curpage,
                          String distance) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("points", points);
        params.addQueryStringParameter("cityid", cityid);
        params.addQueryStringParameter("htype", htype);
        params.addQueryStringParameter("search_key", search_key);
        params.addQueryStringParameter("stime", stime);
        params.addQueryStringParameter("price", price);
        params.addQueryStringParameter("stars", stars);
        params.addQueryStringParameter("brand", brand);
        params.addQueryStringParameter("sheshi", sheshi);
        params.addQueryStringParameter("promote", promote);
        params.addQueryStringParameter("price_sort", price_sort);
        params.addQueryStringParameter("juli_sort", juli_sort);
        params.addQueryStringParameter("pingfen_sort", pingfen_sort);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("distance", distance);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, hotel_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(hotel_list_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                hotel_list_id,
                                JsonParse.getHotelListInfo(arg0.result)));
                        System.out
                                .println("---------------酒店列表 " + arg0.result);
                    }
                });
    }

    /**
     * 富豪榜
     */
    public void RichList(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, rich_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(rich_list_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // System.out
                        // .println("---------------富豪榜 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(rich_list_id,
                                JsonParse.getRichInfo(arg0.result)));
                    }
                });
    }

    /**
     * 红包活动列表
     */
    public void RedPackageActivityList() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, redpackage_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(redpackage_list_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                redpackage_list_id,
                                JsonParse
                                        .getRedPackageActivityListInfo(arg0.result)));
                        System.out.println("--------------- 红包列表  "
                                + arg0.result);
                    }

                });
    }

    /**
     * 我的红包
     */
    public void MyRedPackage(String pagesize, String curpage,
                             String withdrawal_state) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("withdrawal_state", withdrawal_state);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, my_redpackage, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(my_redpackage_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out
                                .println("---------------我的红包 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                my_redpackage_id,
                                JsonParse.getMyRedPackageInfo(arg0.result)));
                    }
                });
    }

    /**
     * 红包活动详情
     */
    public void RedPackagedatails(String artivity_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("artivity_id", artivity_id);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, redpackage_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(redpackage_datails_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("---------------红包活动详情 "
                                + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                redpackage_datails_id,
                                JsonParse.getRedPackageDatailsInfo(arg0.result)));
                    }
                });
    }

    /**
     * 开红包
     */
    public void OpenRedPackage(String id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", id);
        httpUtils.send(HttpMethod.POST, open_redpackage, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(open_redpackage_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("--------------- 开红包操作  "
                                + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                open_redpackage_id,
                                JsonParse.getOpenRedPackage(arg0.result)));

                    }

                });
    }

    /**
     * 红包提现到余额
     */
    public void ToPredeposit(String id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", id);
        httpUtils.send(HttpMethod.POST, to_predeposit, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(to_predeposit_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("--------------- 红包提现到余额  "
                                + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                to_predeposit_id,
                                JsonParse.getOpenRedPackage(arg0.result)));

                    }

                });
    }

    /**
     * 红包提现到微信
     */
    public void ToWX(String id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", id);
        httpUtils.send(HttpMethod.POST, to_wx, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler.obtainMessage(to_wx_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("--------------- 红包提现到微信  "
                                + arg0.result);
                        handler.sendMessage(handler.obtainMessage(to_wx_id,
                                JsonParse.getOpenRedPackage(arg0.result)));

                    }

                });
    }

    /**
     * 充值卡充值
     */
    public void Cardtopup(String rc_sn) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("rc_sn", rc_sn);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, card_topup, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------------充值卡充值 "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                card_topup_id,
                                JsonParse.getTopiczan(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------充值卡充值失败");
                        handler.sendMessage(handler
                                .obtainMessage(card_topup_err));
                    }
                });
    }

    /**
     * 在线充值明细
     */
    public void YYTopUp(String curpage, String pdr_sn) {
        params = new RequestParams();
        httpUtils = new HttpUtils(1000);
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("curpage", curpage);
        params.addBodyParameter("pagesize", "1000");
        params.addBodyParameter("pdr_sn", pdr_sn);
        httpUtils.send(HttpMethod.POST, topup_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("-------------------充值明细   "
                                + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                topup_info_id,
                                JsonParse.getTopUpInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("-------------------充值明细错误");
                        handler.sendMessage(handler
                                .obtainMessage(topup_info_err));
                    }
                });
    }

    /**
     * 充值明细删除
     */
    public void YYTopUpdel(String id) {
        params = new RequestParams();
        httpUtils = new HttpUtils(1000);
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", id);
        httpUtils.send(HttpMethod.POST, topup_infodel, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("-------------------充值删除   "
                                + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                topup_infodel_id,
                                JsonParse.getTopiczan(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("-------------------充值删除错误");
                        handler.sendMessage(handler
                                .obtainMessage(topup_infodel_err));
                    }
                });
    }

    /**
     * 充值卡
     *
     * @param curpage
     */
    public void RechargeableCard(String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("curpage", curpage);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, rechargeable_card, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------------充值卡 "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                rechargeable_card_id,
                                JsonParse
                                        .getRechargeableCardInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------充值卡失败");
                        handler.sendMessage(handler
                                .obtainMessage(rechargeable_card_err));
                    }
                });
    }

    /**
     * 充值支付
     *
     * @param pay_sn
     */
    public void TopupPay(String pay_sn) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pay_sn", pay_sn);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        System.out.println("----------------- 充值支付   " + topup_pay + "&key="
                + State.UserKey + "&pay_sn=" + pay_sn);
        httpUtils.send(HttpMethod.GET, topup_pay, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(topup_pay_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "充值支付 " + arg0.result);
                        handler.sendMessage(handler
                                .obtainMessage(topup_pay_id, JsonParse
                                        .getRedPackageDatailsInfo(arg0.result)));
                    }
                });
    }

    /**
     * 话题列表
     *
     * @param pagesize
     * @param is_recommend
     * @param circle_id
     */
    public void TopicList(String pagesize, String is_recommend,
                          String circle_id, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("is_recommend", is_recommend);
        params.addQueryStringParameter("circle_id", circle_id);
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        System.out.println("-----------------话题列表   " + topic_list + "&key="
                + State.UserKey + "&pagesize=" + pagesize + "&is_recommend="
                + is_recommend + "&circle_id=" + circle_id + "&curpage="
                + curpage);
        httpUtils.send(HttpMethod.GET, topic_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(topic_list_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {

                        handler.sendMessage(handler.obtainMessage(
                                topic_list_id,
                                JsonParse.getTopicList(arg0.result)));
                    }
                });
    }

    public void MemberIndex() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);

        httpUtils.send(HttpMethod.POST, member_index, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------我的商城"
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                member_index_id,
                                JsonParse.memall(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(member_index_err));
                    }
                });
    }

    /**
     * 商品所有分类
     */
    public void GoodsClassifyAll() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, goodsclassify_all, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(goodsclassify_all_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------商品所有分类    "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                goodsclassify_all_id,
                                JsonParse.getGoodsClassInfo(arg0.result)));
                    }
                });
    }

    /**
     * 品牌信息
     */
    public void BrandDatails(String goods_class_type_id, String gc_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("goods_class_type_id",
                goods_class_type_id);
        params.addQueryStringParameter("gc_id", gc_id);
        httpUtils.send(HttpMethod.GET, brand_datails, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("---------------", " 品牌信息 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                brand_datails_id,
                                JsonParse
                                        .getBrandDatailsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        handler.sendMessage(handler
                                .obtainMessage(brand_datails_err));
                    }

                });
    }

    /**
     * 图片空间
     *
     * @param curpage
     * @param id
     */
    public void ImageSpace(String curpage, String id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("id", id);
        httpUtils.send(HttpMethod.GET, image_space, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(image_space_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("---------------", " 图片空间  " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                image_space_id,
                                JsonParse.getImageSpaceInfo(arg0.result)));
                    }
                });
    }

    /**
     * 商品所在地
     */
    public void GoodsAddress() {
        httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.POST, goods_address,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(goods_address_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------商品所在地     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                goods_address_id,
                                JsonParse.getGoodsAddressInfo(arg0.result)));
                    }
                });
    }

    /**
     * 新增商品数据
     */
    public void AddGoods(String gc_id, String type_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("gc_id", gc_id);
        params.addQueryStringParameter("type_id", type_id);
        httpUtils.send(HttpMethod.GET, add_goods, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(add_goods_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------循环打印     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(add_goods_id,
                                JsonParse.getFBGoodsInfo(arg0.result)));
                    }
                });
    }

    /**
     * 添加商品规格
     */
    public void AddSpec(String name, String gc_id, String sp_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("name", name);
        params.addQueryStringParameter("gc_id", gc_id);
        params.addQueryStringParameter("sp_id", sp_id);
        httpUtils.send(HttpMethod.GET, add_spec, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler.obtainMessage(add_spec_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------循环打印     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(add_spec_id,
                                JsonParse.getAddSpec(arg0.result)));
                    }
                });
    }

    /**
     * 运费模板
     */
    public void FreightMode() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, freight_mode, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(freight_mode_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                freight_mode_id,
                                JsonParse.getFreightModeInfo(arg0.result)));
                        Log.i("-----------", "模板  " + arg0.result.toString());
                    }
                });
    }

    /**
     * 商品图片上传
     */
    public void GoodsImageUpload(final File name) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("name", name);
        httpUtils.send(HttpMethod.POST, goodsimage_upload, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        if (name != null) {
                            name.delete();
                        }
                        handler.sendMessage(handler
                                .obtainMessage(goodsimage_upload_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (name != null) {
                            name.delete();
                        }
                        Log.i("--------------",
                                "上传   " + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                goodsimage_upload_id,
                                JsonParse.getUpload(arg0.result)));
                    }
                });
    }

    /**
     * 商品图片上传2
     */
    public void GoodsImageUpload2(final File name) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("name", name);
        httpUtils.send(HttpMethod.POST, goodsimage_upload, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        if (name != null) {
                            name.delete();
                        }
                        handler.sendMessage(handler
                                .obtainMessage(goodsimage_upload_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (name != null) {
                            name.delete();
                        }
                        Log.i("--------------",
                                "上传   " + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                goodsimage_upload_id,
                                JsonParse.getUpload2(arg0.result)));
                    }
                });
    }

    /**
     * 编辑商品图片(商品修改操作)
     */
    public void EditImage(String commonid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commonid", commonid);
        httpUtils.send(HttpMethod.POST, edit_image, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(edit_image_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("--------------",
                                "编辑   " + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                edit_image_id,
                                JsonParse.getGoodssImageInfo(arg0.result)));
                    }
                });
    }

    /**
     * 编辑商品保存
     */
    public void EditorSave(String commission, String g_name, String g_jingle,
                           String cate_id, String cate_name, String b_id, String b_name,
                           String type_id, final File image_path, String g_price,
                           String g_marketprice, String g_costprice, String g_discount,
                           String g_serial, String g_alarm, String attr, String m_body,
                           String g_commend, String g_state, String goods_selltime,
                           String spec, String sp_name, String sp_val, String g_vat,
                           String province_id, String city_id, String freight,
                           String transport_title, String g_freight, String sgcate_id,
                           String plateid_top, String plateid_bottom, String is_virtual,
                           String virtual_indate, String virtual_limit,
                           String virtual_invalid_refund, String is_fcode, String is_appoint,
                           String appoint_satedate, String is_presell,
                           String presell_deliverdate, String g_fccount, String g_fcprefix,
                           String is_kuajing, String g_native, String is_native,
                           String g_seaport, String g_clearance, String g_clearance_sn,
                           String md, String is_more_discount, String commonid, String stock,
                           String level_0_price, String level_1_price, String level_2_price,
                           String level_3_price, String level_0_auth_price,
                           String level_1_auth_price, String level_2_auth_price,
                           String level_3_auth_price, String goods_points_offset,
                           String costprice) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commission", commission);
        params.addBodyParameter("g_name", g_name);
        params.addBodyParameter("g_jingle", g_jingle);
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("cate_name", cate_name);
        params.addBodyParameter("b_id", b_id);
        params.addBodyParameter("b_name", b_name);
        params.addBodyParameter("type_id", type_id);
        if (image_path.exists()) {
            params.addBodyParameter("image_path", image_path);
        } else {
            params.addBodyParameter("image_path", image_path.toString());
        }
        params.addBodyParameter("g_price", g_price);
        params.addBodyParameter("g_marketprice", g_marketprice);
        params.addBodyParameter("g_costprice", g_costprice);
        params.addBodyParameter("g_discount", g_discount);
        params.addBodyParameter("g_serial", g_serial);
        params.addBodyParameter("g_alarm", g_alarm);
        params.addBodyParameter("attr", attr);
        params.addBodyParameter("m_body", m_body);
        params.addBodyParameter("g_commend", g_commend);
        params.addBodyParameter("g_state", g_state);
        params.addBodyParameter("goods_selltime", goods_selltime);
        params.addBodyParameter("spec", spec);
        params.addBodyParameter("sp_name", sp_name);
        params.addBodyParameter("sp_val", sp_val);
        params.addBodyParameter("g_vat", g_vat);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("freight", freight);
        params.addBodyParameter("transport_title", transport_title);
        params.addBodyParameter("g_freight", g_freight);
        params.addBodyParameter("sgcate_id", sgcate_id);
        params.addBodyParameter("plateid_top", plateid_top);
        params.addBodyParameter("plateid_bottom", plateid_bottom);
        params.addBodyParameter("is_virtual", is_virtual);
        params.addBodyParameter("virtual_indate", virtual_indate);
        params.addBodyParameter("virtual_limit", virtual_limit);
        params.addBodyParameter("virtual_invalid_refund",
                virtual_invalid_refund);
        params.addBodyParameter("is_fcode", is_fcode);
        params.addBodyParameter("is_appoint", is_appoint);
        params.addBodyParameter("appoint_satedate", appoint_satedate);
        params.addBodyParameter("is_presell", is_presell);
        params.addBodyParameter("presell_deliverdate", presell_deliverdate);
        params.addBodyParameter("g_fccount", g_fccount);
        params.addBodyParameter("g_fcprefix", g_fcprefix);

        params.addBodyParameter("is_kuajing", is_kuajing);
        params.addBodyParameter("g_native", g_native);
        params.addBodyParameter("is_native", is_native);
        params.addBodyParameter("g_seaport", g_seaport);
        params.addBodyParameter("g_clearance", g_clearance);
        params.addBodyParameter("g_clearance_sn", g_clearance_sn);
        params.addBodyParameter("md", md);
        params.addBodyParameter("is_more_discount", is_more_discount);
        params.addBodyParameter("commonid", commonid);
        params.addBodyParameter("stock", stock);
        params.addBodyParameter("level_0_price", level_0_price);
        params.addBodyParameter("level_1_price", level_1_price);
        params.addBodyParameter("level_2_price", level_2_price);
        params.addBodyParameter("level_3_price", level_3_price);
        params.addBodyParameter("level_0_auth_price", level_0_auth_price);
        params.addBodyParameter("level_1_auth_price", level_1_auth_price);
        params.addBodyParameter("level_2_auth_price", level_2_auth_price);
        params.addBodyParameter("level_3_auth_price", level_3_auth_price);
        params.addBodyParameter("goods_points_offset", goods_points_offset);
        params.addBodyParameter("costprice", costprice);
        httpUtils.send(HttpMethod.POST, editor_save, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("-----------------", "错误  " + arg1);
                        if (image_path != null) {
                            image_path.delete();
                        }
                        handler.sendMessage(handler
                                .obtainMessage(editor_save_err));

                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (image_path != null) {
                            image_path.delete();
                        }
                        handler.sendMessage(handler.obtainMessage(
                                editor_save_id, JsonParse.getEdit(arg0.result)));
                        Log.i("-----------", "编辑  " + arg0.result.toString());
                    }
                });
    }

    /**
     * 编辑商品
     *
     * @param commonid
     */
    public void getRedactData(String commonid, String class_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("commonid", commonid);
        httpUtils.send(HttpMethod.GET, get_RedactData, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------编辑商品     "
                                    + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                get_redact_data_id,
                                JsonParse
                                        .getEditorGoodsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        handler.sendMessage(handler
                                .obtainMessage(get_redact_data_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(get_redact_data_start));
                    }
                });
    }

    /**
     * 发布商品提交
     */
    public void SaveGoods(String commission, String g_name, String g_jingle,
                          String cate_id, String cate_name, String b_id, String b_name,
                          String type_id, final File image_path, String g_price,
                          String g_marketprice, String g_costprice, String g_discount,
                          String g_serial, String g_alarm, String attr, String m_body,
                          String g_commend, String g_state, String goods_selltime,
                          String spec, String sp_name, String sp_val, String g_vat,
                          String province_id, String city_id, String freight,
                          String transport_title, String g_freight, String sgcate_id,
                          String plateid_top, String plateid_bottom, String is_virtual,
                          String virtual_indate, String virtual_limit,
                          String virtual_invalid_refund, String is_fcode, String is_appoint,
                          String appoint_satedate, String is_presell, String g_deliverdate,
                          String g_fccount, String g_fcprefix, String is_kuajing,
                          String g_native, String is_native, String g_seaport,
                          String g_clearance, String g_clearance_sn, String md,
                          String is_more_discount, String stock, String level_0_price,
                          String level_1_price, String level_2_price, String level_3_price,
                          String level_0_auth_price, String level_1_auth_price,
                          String level_2_auth_price, String level_3_auth_price,
                          String goods_points_offset, String costprice) {
        Log.i("------------------", "发布  commission=" + commission
                + "\ng_name=" + g_name + "\ng_jingle=" + g_jingle
                + "\ncate_id=" + cate_id + "\ncate_name=" + cate_name
                + "\nb_id=" + b_id + "\nb_name=" + b_name + "\ntype_id="
                + type_id + "\nimage_path=" + image_path + "\ng_price="
                + g_price + "\ng_marketprice=" + g_marketprice
                + "\ng_costprice=" + g_costprice + "\ng_discount=" + g_discount
                + "\ng_serial=" + g_serial + "\ng_alarm=" + g_alarm + "\nattr="
                + attr + "\nm_body=" + m_body + "\ng_commend=" + g_commend
                + "\ng_state=" + g_state + "\ngoods_selltime=" + goods_selltime
                + "\nspec=" + spec + "\nsp_name=" + sp_name + "\nsp_val="
                + sp_val + "\ng_vat=" + g_vat + "\nprovince_id=" + province_id
                + "\ncity_id=" + city_id + "\nfreight=" + freight
                + "\ntransport_title=" + transport_title + "\ng_freight="
                + g_freight + "\nsgcate_id=" + sgcate_id + "\nplateid_top="
                + plateid_top + "\nplateid_bottom=" + plateid_bottom
                + "\nis_virtual=" + is_virtual + "\nvirtual_indate="
                + virtual_indate + "\nvirtual_limit=" + virtual_limit
                + "\nvirtual_invalid_refund=" + virtual_invalid_refund
                + "\nis_fcode=" + is_fcode + "\nis_appoint=" + is_appoint
                + "\nappoint_satedate=" + appoint_satedate + "\nis_presell="
                + is_presell + "\ng_deliverdate=" + g_deliverdate
                + "\ng_fccount=" + g_fccount + "\ng_fcprefix=" + g_fcprefix
                + "\nis_kuajing=" + is_kuajing + "\ng_native=" + g_native
                + "\nis_native=" + is_native + "\ng_seaport=" + g_seaport
                + "\ng_clearance=" + g_clearance + "\ng_clearance_sn="
                + g_clearance_sn + "\nmd=" + md + "\nis_more_discount="
                + is_more_discount + "\nstock=" + stock + "\nlevel_0_price="
                + level_0_price + "\nlevel_1_price=" + level_1_price
                + "\nlevel_2_price=" + level_2_price + "\nlevel_3_price="
                + level_3_price + "\nlevel_0_auth_price=" + level_0_auth_price
                + "\nlevel_1_auth_price=" + level_1_auth_price
                + "\nlevel_2_auth_price=" + level_2_auth_price
                + "\nlevel_3_auth_price=" + level_3_auth_price
                + "\ngoods_points_offset=" + goods_points_offset
                + "\ncostprice=" + costprice);

        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commission", commission);
        params.addBodyParameter("g_name", g_name);
        params.addBodyParameter("g_jingle", g_jingle);
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("cate_name", cate_name);
        params.addBodyParameter("b_id", b_id);
        params.addBodyParameter("b_name", b_name);
        params.addBodyParameter("type_id", type_id);
        if (image_path.exists()) {
            params.addBodyParameter("image_path", image_path);
        } else {
            params.addBodyParameter("image_path", image_path.toString());
        }
        params.addBodyParameter("g_price", g_price);
        params.addBodyParameter("g_marketprice", g_marketprice);
        params.addBodyParameter("g_costprice", g_costprice);
        params.addBodyParameter("g_discount", g_discount);
        params.addBodyParameter("g_serial", g_serial);
        params.addBodyParameter("g_alarm", g_alarm);
        params.addBodyParameter("attr", attr);
        params.addBodyParameter("m_body", m_body);
        params.addBodyParameter("g_commend", g_commend);
        params.addBodyParameter("g_state", g_state);
        params.addBodyParameter("goods_selltime", goods_selltime);
        params.addBodyParameter("spec", spec);
        params.addBodyParameter("sp_name", sp_name);
        params.addBodyParameter("sp_val", sp_val);
        params.addBodyParameter("g_vat", g_vat);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("freight", freight);
        params.addBodyParameter("transport_title", transport_title);
        params.addBodyParameter("g_freight", g_freight);
        params.addBodyParameter("sgcate_id", sgcate_id);
        params.addBodyParameter("plateid_top", plateid_top);
        params.addBodyParameter("plateid_bottom", plateid_bottom);
        params.addBodyParameter("is_virtual", is_virtual);
        params.addBodyParameter("virtual_indate", virtual_indate);
        params.addBodyParameter("virtual_limit", virtual_limit);
        params.addBodyParameter("virtual_invalid_refund",
                virtual_invalid_refund);
        params.addBodyParameter("is_fcode", is_fcode);
        params.addBodyParameter("is_appoint", is_appoint);
        params.addBodyParameter("appoint_satedate", appoint_satedate);
        params.addBodyParameter("is_presell", is_presell);
        params.addBodyParameter("g_deliverdate", g_deliverdate);
        params.addBodyParameter("g_fccount", g_fccount);
        params.addBodyParameter("g_fcprefix", g_fcprefix);

        params.addBodyParameter("is_kuajing", is_kuajing);
        params.addBodyParameter("g_native", g_native);
        params.addBodyParameter("is_native", is_native);
        params.addBodyParameter("g_seaport", g_seaport);
        params.addBodyParameter("g_clearance", g_clearance);
        params.addBodyParameter("g_clearance_sn", g_clearance_sn);
        params.addBodyParameter("md", md);
        params.addBodyParameter("is_more_discount", is_more_discount);
        params.addBodyParameter("stock", stock);
        params.addBodyParameter("level_0_price", level_0_price);
        params.addBodyParameter("level_1_price", level_1_price);
        params.addBodyParameter("level_2_price", level_2_price);
        params.addBodyParameter("level_3_price", level_3_price);
        params.addBodyParameter("level_0_auth_price", level_0_auth_price);
        params.addBodyParameter("level_1_auth_price", level_1_auth_price);
        params.addBodyParameter("level_2_auth_price", level_2_auth_price);
        params.addBodyParameter("level_3_auth_price", level_3_auth_price);
        params.addBodyParameter("goods_points_offset", goods_points_offset);
        params.addBodyParameter("costprice", costprice);

        httpUtils.send(HttpMethod.POST, save_goods, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("-----------------", "错误  " + arg1);
                        if (image_path != null) {
                            image_path.delete();
                        }
                        handler.sendMessage(handler
                                .obtainMessage(save_goods_err));

                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("-----------", "发布 " + arg0.result.toString());
                        if (image_path != null) {
                            image_path.delete();
                        }
                        handler.sendMessage(handler.obtainMessage(
                                save_goods_id,
                                JsonParse.getSaveGoods(arg0.result)));
                    }
                });
    }

    /**
     * 上传商品图片(商品添加操作)
     */
    public void UploadGoodsImg(String commonid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commonid", commonid);
        httpUtils.send(HttpMethod.POST, upload_goodsimg, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("--------------", "错误   ");
                        handler.sendMessage(handler
                                .obtainMessage(edit_image_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("--------------",
                                "新增   " + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                edit_image_id,
                                JsonParse.getGoodssImageInfo(arg0.result)));
                    }
                });
    }

    /**
     * 保存上传图片(商品添加操作)
     */
    public void SaveImage2(String commonid, String img) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commonid", commonid);
        params.addBodyParameter("img", img);
        httpUtils.send(HttpMethod.POST, upload_goodsimg2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(save_image_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("--------------",
                                "保存   " + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                save_image_id, JsonParse.getEdit(arg0.result)));
                    }
                });
    }

    /**
     * 推荐组合（商品列表、组合商品）详情
     */
    public void GetRecommended(String commonid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("commonid", commonid);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        httpUtils.send(HttpMethod.GET, get_recommended, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(get_recommended_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------推荐组合     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                get_recommended_id,
                                JsonParse.GetRecommendedInfo(arg0.result)));
                    }
                });
    }

    /**
     * 商家中心———添加组合商品:搜索商品列表
     */
    public void SearchGoods(String name, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("name", name);
        params.addBodyParameter("curpage", curpage);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        Log.i("--------------------", "搜索商品列表 " + search_goods + "&key="
                + State.UserKey + "&name=" + name + "&curpage=" + curpage);
        httpUtils.send(HttpMethod.POST, search_goods, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(search_goods_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // int maxLogSize = 4000;
                        // String veryLongString = arg0.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // Log.i("--------------------", "搜索商品列表 "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                search_goods_id,
                                JsonParse.GetGoodsListInfo(arg0.result)));
                    }
                });
    }

    /**
     * 商家中心——保存推荐组合
     */
    public void SaveCombo(String commonid, String combo) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commonid", commonid);
        params.addBodyParameter("combo", combo);
        Log.i("-----------------", commonid + "   " + combo);
        httpUtils.send(HttpMethod.POST, save_combo, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(save_combo_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "保存推荐组合  " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                save_combo_id,
                                JsonParse.getSaveCombo(arg0.result)));
                    }
                });
    }

    /**
     * 商家中心——商品赠品详情
     */
    public void GiftInfo(String commonid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("commonid", commonid);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        Log.i("-----------------", "赠品" + gift_info + "&key=" + State.UserKey
                + "&comm" + "onid=" + commonid);
        // System.out.println("---------------赠品   "+gift_info+"&key="+State.UserKey+"&comm"
        // + "onid="+commonid);
        httpUtils.send(HttpMethod.GET, gift_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(gift_info_err));
                        System.out.println("----------------赠送商品错误  " + arg1);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------商品赠品详情     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(gift_info_id,
                                JsonParse.getGiftsInfo(arg0.result)));
                    }
                });
    }

    /**
     * 添加参数绑定
     */
    public void ValBinding(String goods_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("goods_id", goods_id);
        httpUtils.send(HttpMethod.GET, val_binding, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(val_binding_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "添加参数绑定  " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                val_binding_id,
                                JsonParse.GetParameterInfo(arg0.result)));
                    }
                });
    }

    /**
     * 编辑商品参数 ---商品列表
     */
    public void EditVal(String commonid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("commonid", commonid);
        httpUtils.send(HttpMethod.GET, edit_val, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler.obtainMessage(edit_val_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "编辑商品参数  " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(edit_val_id,
                                JsonParse.getEditValInfo(arg0.result)));
                    }
                });
    }

    /**
     * 添加参数绑定保存
     */
    public void EditValSave(String goods_id, String is_use, String param_value) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("is_use", is_use);
        params.addBodyParameter("param_value", param_value);
        httpUtils.send(HttpMethod.POST, editval_save, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("------------------", "失败" + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(editval_save_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "添加参数绑定保存  " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                editval_save_id,
                                JsonParse.getSaveCombo(arg0.result)));
                    }
                });
    }

    /**
     * 保存赠品
     */
    public void SaveGifts(String commonid, String combo) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commonid", commonid);
        params.addBodyParameter("gift", combo);
        httpUtils.send(HttpMethod.POST, save_gifts, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(save_gifts_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "保存赠品  " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                save_gifts_id,
                                JsonParse.getSaveCombo(arg0.result)));
                    }
                });
    }

    /**
     * 编辑图片保存(商品修改操作)
     */
    public void SaveImage(String commonid, String img) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("commonid", commonid);
        params.addBodyParameter("img", img);
        httpUtils.send(HttpMethod.POST, save_image, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(save_image_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("--------------",
                                "保存   " + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                save_image_id, JsonParse.getEdit(arg0.result)));
                    }
                });
    }

    /**
     * 上传晒单图片
     *
     * @param ac_id
     * @param evaluate_image
     * @param imgname
     */
    public void UploadImg(String ac_id, final File evaluate_image,
                          String imgname) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("ac_id", ac_id);
        params.addBodyParameter("evaluate_image", imgname);
        if (evaluate_image.exists()) {
            params.addBodyParameter(imgname, evaluate_image);
        }
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, upload_img, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("-------------------上传晒单图片  "
                                + arg0.result.toString());
                        evaluate_image.delete();
                        handler.sendMessage(handler.obtainMessage(
                                upload_img_id,
                                JsonParse.getUploadImgInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        evaluate_image.delete();
                        handler.sendMessage(handler
                                .obtainMessage(upload_img_err));
                    }
                });
    }

    /**
     * 晒单图片提交
     */
    public void ShaiDanUpload(String geval_id, List<File> data, String ac_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("geval_id", geval_id);
        params.addBodyParameter("ac_id", ac_id);
        if (data.size() > 0) {
            params.addBodyParameter("image_upload1", data.get(0));
        }
        if (data.size() > 1) {
            params.addBodyParameter("image_upload2", data.get(1));
        }
        if (data.size() > 2) {
            params.addBodyParameter("image_upload3", data.get(2));
        }
        if (data.size() > 3) {
            params.addBodyParameter("image_upload4", data.get(3));
        }
        if (data.size() > 4) {
            params.addBodyParameter("image_upload5", data.get(4));
        }
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, shaidan_upload, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("--------------", "上传晒单图片  " + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                shaidan_upload_id,
                                JsonParse.getUploadShaiDan(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("--------------", "上传晒单图片错误  " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(shaidan_upload_err));
                    }
                });
    }

    /**
     * 晒单
     *
     * @param geval_id
     */
    public void ShaiDan(String geval_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("geval_id", geval_id);
        System.out.println("-------------------晒单  " + shai_dan + "&key="
                + State.UserKey + "&geval_id=" + geval_id);
        httpUtils.configCurrentHttpCacheExpiry(1000);// 缓存
        httpUtils.send(HttpMethod.GET, shai_dan, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {

                        handler.sendMessage(handler.obtainMessage(shai_dan_id,
                                JsonParse.getShaiDanInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler.obtainMessage(shai_dan_err));
                    }
                });
    }

    /**
     * 酒店详情
     *
     * @param hotel_id
     * @param points
     * @param stime
     * @param etime
     */
    public void HotelDatails(String hotel_id, String points, String stime,
                             String etime) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("hotel_id", hotel_id);
        params.addQueryStringParameter("points", points);
        params.addQueryStringParameter("stime", stime);
        params.addQueryStringParameter("etime", etime);
        System.out.println("-------------------酒店详情   " + hotel_datails
                + "&key=" + State.UserKey + "&hotel_id=" + hotel_id
                + "&points=" + points + "&stime=" + stime + "&etime=" + etime);
        httpUtils.configCurrentHttpCacheExpiry(1000);// 缓存
        httpUtils.send(HttpMethod.GET, hotel_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                hotel_datails_id,
                                JsonParse.getHotelDatailsInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(hotel_datails_err));
                    }
                });
    }

    /**
     * 酒店评价详情
     *
     * @param hotel_id
     * @param type
     * @param page
     * @param curpage
     */
    public void CommentsDatails(String hotel_id, String type, String page,
                                String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("hotel_id", hotel_id);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("curpage", curpage);
        Log.i("-----------------酒店详情", comments_datails + "&key="
                + State.UserKey + "&hotel_id=" + hotel_id + "&type=" + type
                + "&page=" + page + "&curpage=" + curpage);
        httpUtils.configCurrentHttpCacheExpiry(1000);// 缓存
        httpUtils.send(HttpMethod.GET, comments_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                comments_datails_id,
                                JsonParse.getHotelCommentsInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(comments_datails_err));
                    }
                });
    }

    /**
     * 退货列表
     *
     * @param curpage
     * @param pagesize
     */
    public void SalesReturn(String curpage, String pagesize) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("pagesize", pagesize);
        Log.i("-----------------退货列表", sales_return + "&key=" + State.UserKey
                + "&curpage=" + curpage + "&pagesize=" + pagesize);
        httpUtils.configCurrentHttpCacheExpiry(1000);// 缓存
        httpUtils.send(HttpMethod.GET, sales_return, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                sales_return_id,
                                JsonParse.getSalesReturnListInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(sales_return_err));
                    }
                });
    }

    /**
     * 订单详情
     */
    public void OrderInfo(String order_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("order_id", order_id);
        params.addQueryStringParameter("key", State.UserKey);
        System.out.println("--------------订单详情  " + order_info + "&order_id="
                + order_id + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, order_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------订单详情     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                order_info_id,
                                JsonParse.getOrderinfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(order_info_err));
                    }
                });
    }

    /**
     * 投诉图片上传
     */
    public void complaintsimg(String complain_pic, final File img) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("complain_pic", complain_pic);
        params.addBodyParameter(complain_pic, img);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, complaints_img, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "图片添加 " + responseInfo.result.toString());
                        // System.out.println("--------------------图片添加 "
                        // + responseInfo.result.toString());
//                        img.delete();
                        handler.sendMessage(handler.obtainMessage(
                                complaints_img_id,
                                JsonParse.getImageadd(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------图片添加失败");
//                        img.delete();
                        handler.sendMessage(handler
                                .obtainMessage(complaints_img_err));
                    }
                });
    }

    /**
     * 投诉提交
     */
    public void ComplaintsSubmit(String input_order_id, String input_goods_id,
                                 String input_complain_subject, String input_complain_content,
                                 String complain_pic1, String complain_pic2, String complain_pic3) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("input_order_id", input_order_id);
        params.addBodyParameter("input_goods_id", input_goods_id);
        params.addBodyParameter("input_complain_subject",
                input_complain_subject);
        params.addBodyParameter("input_complain_content",
                input_complain_content);
        params.addBodyParameter("complain_pic1", complain_pic1);
        params.addBodyParameter("complain_pic2", complain_pic2);
        params.addBodyParameter("complain_pic3", complain_pic3);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, complaints_submit, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                complaints_submit_id,
                                JsonParse.getTopiczan(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------投诉提交失败");
                        handler.sendMessage(handler
                                .obtainMessage(complaints_submit_err));
                    }
                });
    }

    /**
     * 退款详情
     *
     * @param order_id
     */
    public void RefundInfo(String order_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, refund_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------退款详情     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                refund_info_id,
                                JsonParse.getRefundInfo2(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------退款详情失败");
                        handler.sendMessage(handler
                                .obtainMessage(refund_info_err));
                    }
                });
    }

    /**
     * 部分退款
     *
     * @param order_id
     */
    public void RefundInfo2(String order_id, String goods_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("key", State.UserKey);
        Log.i("--------------", "order_id=" + order_id + "   goods_id=" + goods_id);
        httpUtils.send(HttpMethod.POST, refund2_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------部分退款     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                refund2_info_id,
                                JsonParse.getOrderRefund2Info(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------退款详情失败");
                        handler.sendMessage(handler
                                .obtainMessage(refund2_info_err));
                    }
                });
    }

    /**
     * 部分退款提交
     */
    public void ConfirmRefund2(String order_id, String goods_id, String refund_amount, String goods_num, String reason_id, String refund_type, String buyer_message
            , File refund_pic1, File refund_pic2, File refund_pic3) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("refund_amount", refund_amount);
        params.addBodyParameter("goods_num", goods_num);
        params.addBodyParameter("reason_id", reason_id);
        params.addBodyParameter("refund_type", refund_type);
        params.addBodyParameter("buyer_message", buyer_message);
        params.addBodyParameter("key", State.UserKey);
        if (refund_pic1 != null && refund_pic1.exists()) {
            params.addBodyParameter("refund_pic1", refund_pic1);
        }
        if (refund_pic2 != null && refund_pic2.exists()) {
            params.addBodyParameter("refund_pic2", refund_pic2);
        }
        if (refund_pic3 != null && refund_pic3.exists()) {
            params.addBodyParameter("refund_pic3", refund_pic3);
        }
        Log.i("--------------", "order_id=" + order_id + "   goods_id=" + goods_id
                + "   refund_amount=" + refund_amount + "   goods_num=" + goods_num + "   reason_id=" + reason_id + "   refund_type=" + refund_type
                + "   buyer_message=" + buyer_message);
        httpUtils.send(HttpMethod.POST, confirm_refund2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------部分退款提交     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                confirm_refund2_id,
                                JsonParse.getConfirmRefund2(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------退款详情失败");
                        handler.sendMessage(handler
                                .obtainMessage(confirm_refund2_err));
                    }
                });
    }

    /**
     * 退款提交
     *
     * @param order_id
     */
    public void ConfirmRefund(String order_id, String buyer_message, File refund_pic1, File refund_pic2, File refund_pic3) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("buyer_message", buyer_message);
        if (refund_pic1 != null && refund_pic1.exists()) {
            params.addBodyParameter("refund_pic1", refund_pic1);
        }
        if (refund_pic2 != null && refund_pic2.exists()) {
            params.addBodyParameter("refund_pic2", refund_pic2);
        }
        if (refund_pic3 != null && refund_pic3.exists()) {
            params.addBodyParameter("refund_pic3", refund_pic3);
        }
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, confirm_refund, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("----------------退款提交  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                confirm_refund_id,
                                JsonParse.getTopiczan(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------退款提交失败");
                        handler.sendMessage(handler
                                .obtainMessage(confirm_refund_err));
                    }
                });
    }

    /**
     * 热门代金券
     */
    public void HotVouchers(String sc_id, String curpage, String price,
                            String sort_type) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("sc_id", sc_id);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("price", price);
        params.addQueryStringParameter("sort_type", sort_type);
        httpUtils.send(HttpMethod.GET, hot_vouchers, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------循环打印     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                hot_vouchers_id,
                                JsonParse
                                        .getHotVouchersListInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(hot_vouchers_err));
                    }
                });
    }

    /**
     * 确认积分兑换
     *
     * @param vid
     */
    public void CreditsExchange(String vid) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("vid", vid);
        params.addQueryStringParameter("key", State.UserKey);
        System.out.println("------------------确认积分兑换券  " + credits_exchange
                + "&vid=" + vid + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, credits_exchange, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                credits_exchange_id,
                                JsonParse
                                        .getCreditsExchangeInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(credits_exchange_err));
                    }
                });
    }

    /**
     * 代金券兑换保存信息
     */
    public void CreditsExchangeSubmit(String vid) {
        params = new RequestParams();
        httpUtils = new HttpUtils(1000);
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("vid", vid);
        httpUtils.send(HttpMethod.POST, creditsexchange_submit, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                creditsexchange_submit_id,
                                JsonParse
                                        .getCreditsExchangeSubmit(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(creditsexchange_submit_err));
                    }
                });
    }

    /**
     * 积分商城
     */
    public void IntegralMall() {
        params = new RequestParams();
        httpUtils = new HttpUtils(1000);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, integral_mall, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                integral_mall_id,
                                JsonParse
                                        .getIntegralMallInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integral_mall_err));
                    }
                });
    }

    /**
     * 代金券列表
     */
    public void VouchersList(String voucher_state) {
        params = new RequestParams();
        httpUtils = new HttpUtils(1000);
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("voucher_state", voucher_state);
        httpUtils.send(HttpMethod.POST, vouchers_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------循环打印     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                vouchers_list_id,
                                JsonParse
                                        .getVouchersListInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(vouchers_list_err));
                    }
                });
    }

    /**
     * 积分礼品详细
     */
    public void IntegralGiftInfo(String id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.GET, integral_giftinfo, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                integral_giftinfo_id,
                                JsonParse
                                        .getIntegralGoodsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integral_giftinfo_err));
                    }
                });
    }

    /**
     * 积分礼品添加购物车
     */
    public void AddIntegralGiftCart(String pgid, String quantity) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("pgid", pgid);
        params.addQueryStringParameter("quantity", quantity);
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.GET, addIntegralgift_cart, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                addIntegralgift_cart_id, "1"));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(addIntegralgift_cart_err));
                    }
                });
    }

    /**
     * 积分礼品购物车首页
     */
    public void IntegralCart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = integral_cart + "&key=" + State.UserKey;
                    System.out.println("-------------积分礼品购物车首页  " + path);
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");

                        handler.sendMessage(handler.obtainMessage(
                                brand_classify_id,
                                JsonParse.getIntegralCartInfo(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(brand_classify_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 购物车更新数量
     *
     * @param quantity
     */
    public void IntegralCartUpNum(String pc_id, String quantity) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("pc_id", pc_id);
        params.addQueryStringParameter("quantity", quantity);
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.GET, integralcart_upnum, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                integralcart_upnum_id,
                                JsonParse
                                        .getIntegralCartUpNum(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integralcart_upnum_err));
                    }
                });
    }

    /**
     * 商品删除
     *
     * @param pc_id
     */
    public void IntegralCartDel(String pc_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("pc_id", pc_id);
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.GET, integralcart_del, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------循环打印     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                integralcart_del_id,
                                JsonParse
                                        .getIntegralCartUpNum(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integralcart_del_err));
                    }
                });
    }

    /**
     * 积分订单第一步
     */
    public void IntegralOne() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = integral_one + "&key=" + State.UserKey;
                    // System.out.println("-------------积分订单第一步  " + path);
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        handler.sendMessage(handler.obtainMessage(
                                integral_one_id,
                                JsonParse.getIntegralOneInfo(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integral_one_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 购买第二步
     */
    public void IntegralTwo(String address_options) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("address_options", address_options);
        httpUtils.send(HttpMethod.POST, integral_two, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------购买第二步  "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                integral_two_id,
                                JsonParse.getIntegralTwo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integral_two_err));
                    }
                });
    }

    /**
     * 购买第三步
     */
    public void IntegralThree(String order_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("order_id", order_id);
        httpUtils.send(HttpMethod.GET, integral_three, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // System.out.println("--------------购买第三步  "+responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                integral_three_id,
                                JsonParse
                                        .getIntegralWinInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integral_three_err));
                    }
                });
    }

    /**
     * 我的兑换记录
     *
     * @param curpage
     */
    public void MyExchangeRecord(String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("curpage", curpage);
        httpUtils.send(HttpMethod.POST, myexchange_record, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------循环打印     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                myexchange_record_id,
                                JsonParse
                                        .getExchangeRecordInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(myexchange_record_err));
                    }
                });
    }

    /**
     * 取消兑换
     *
     * @param order_id
     */
    public void CancelExchange(String order_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("order_id", order_id);
        httpUtils.send(HttpMethod.GET, jfcancel_order, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------取消兑换  "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                jfcancel_order_id, JsonParse
                                        .getCancelExchange(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(jfcancel_order_err));
                    }
                });
    }

    /**
     * 积分订单详情
     */
    public void IntegarlOrderInfo(final String order_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = integarlorder_info + "&key=" + State.UserKey
                            + "&order_id=" + order_id;
                    System.out.println("-------------积分订单详情  " + path);
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        handler.sendMessage(handler.obtainMessage(
                                integarlorder_info_id,
                                JsonParse.getIntegarlOrderInfo(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(integarlorder_info_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 积分商品列表
     */
    public void HotIntegralGiftList(final String sort_type,
                                    final String grade_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = hotintegralgift_list + "&key="
                            + State.UserKey + "&sort_type=" + sort_type
                            + "&grade_id=" + grade_id;
                    System.out.println("-------------积分商品列表  " + path);
                    HttpGet httpGet = new HttpGet(path);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResp = null;
                    httpResp = httpClient.execute(httpGet);
                    // 判断是够请求成功
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        // 获取返回的数据
                        String result = EntityUtils.toString(
                                httpResp.getEntity(), "UTF-8");
                        handler.sendMessage(handler.obtainMessage(
                                hotintegralgift_list_id,
                                JsonParse.getHotIntegralGiftListInfo(result)));
                    } else {
                        System.out.println("--------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(hotintegralgift_list_err));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("-----------------", "错误");
                }
            }
        }).start();
    }

    /**
     * 购物车购买数量修改
     *
     * @param cart_id
     * @param quantity
     * @param info
     * @param holder
     */
    public void upCartGoodsNum(String cart_id, final String quantity,
                               final com.aite.a.model.CartListInfo2.cart_list info,
                               final Cart2Adapter.Gadapter.ViewHolder holder) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("cart_id", cart_id);
        params.addBodyParameter("quantity", quantity);
        httpUtils.send(HttpMethod.POST, Mark.quantity_update, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        CartListInfo info2 = JsonParse
                                .CartQuantityUpdate(responseInfo.result);
                        if (info2 != null) {
                            holder.tv_gprice.setText("¥"
                                    + info2.getGoods_price());
                            holder.add_sub_tv_num.setText(info2.getGoods_num());
                            // holder.tv_amountfcf1.setText("X"
                            // + holder.num.getText().toString());
                            info.goods_num = quantity;
                            handler.sendMessage(handler.obtainMessage(
                                    up_cart_num_id, "1"));
                            handler.sendMessage(handler.obtainMessage(0));
                        } else {
                            handler.sendMessage(handler.obtainMessage(
                                    up_cart_num_id, "0"));
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(up_cart_num_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(up_cart_num_start));
                    }
                });
    }

    /**
     * 购物车购买数量修改
     *
     * @param cart_id
     * @param quantity
     */
    public void upCartGoodsNum(String cart_id, String quantity) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("cart_id", cart_id);
        params.addBodyParameter("quantity", quantity);
        httpUtils.send(HttpMethod.POST, Mark.quantity_update, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("--------------", "购物车购买数量修改= " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                up_cart_num_id, JsonParse.CartQuantityUpdate2(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("--------------", "购物车购买数量修改错误" + msg);
                        handler.sendMessage(handler.obtainMessage(up_cart_num_err));
                    }
                });
    }

    /**
     * 登录验证
     */
    public void LoginValidation() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, login_validation, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                login_validation_id,
                                JsonParse
                                        .getLoginValidation(responseInfo.result)));
                        // System.out.println("-------------------验证   "+responseInfo.result.toString());
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(login_validation_err));
                    }
                });
    }

    /**
     * 房间详情
     *
     * @param room_id
     * @param stime
     * @param etime
     */
    public void RoomDatails(String room_id, String stime, String etime) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("room_id", room_id);
        params.addQueryStringParameter("stime", stime);
        params.addQueryStringParameter("etime", etime);
        httpUtils.send(HttpMethod.GET, room_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("--------------房间详情  "
                                + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                room_datails_id, JsonParse
                                        .getCancelExchange(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(room_datails_err));
                    }
                });
    }

    /**
     * 酒店购买详情
     *
     * @param stime
     * @param etime
     */
    public void HotelPayDatails(String id, String stime, String etime) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("stime", stime);
        params.addQueryStringParameter("etime", etime);
        System.out.println("-----------------购买详情   " + hotelpay_datails
                + "&key=" + State.UserKey + "&id=" + id + "&stime=" + stime
                + "&etime=" + etime);
        httpUtils.send(HttpMethod.GET, hotelpay_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // System.out.println("--------------购买详情  "
                        // + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                hotelpay_datails_id,
                                JsonParse
                                        .getHotelPayDatailsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(hotelpay_datails_err));
                    }
                });
    }

    /**
     * 酒店提交订单
     */
    public void HotelSubmitOrder(String stime, String etime, String ArriveTime,
                                 String roomnum, String id, String checkin_mobile,
                                 String checkin_person) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("stime", stime);
        params.addBodyParameter("etime", etime);
        params.addBodyParameter("ArriveTime", ArriveTime);
        params.addBodyParameter("roomnum", roomnum);
        params.addBodyParameter("id", id);
        params.addBodyParameter("checkin_mobile", checkin_mobile);
        params.addBodyParameter("checkin_person", checkin_person);
        httpUtils.send(HttpMethod.POST, hotelsubmit_order, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                hotelsubmit_order_id,
                                JsonParse
                                        .getHotelSubmitOrder(responseInfo.result)));
                        System.out.println("-------------------提交订单   "
                                + responseInfo.result.toString());
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(hotelsubmit_order_err));
                    }
                });
    }

    /**
     * 酒店订单列表
     *
     * @param type
     * @param page
     * @param curpage
     */
    public void HotelOrderList(String type, String page, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("curpage", curpage);
        System.out.println("-----------------酒店订单列表   " + hotelorder_list
                + "&key=" + State.UserKey + "&type=" + type + "&page=" + page
                + "&curpage=" + curpage);
        httpUtils.send(HttpMethod.GET, hotelorder_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // System.out.println("--------------购买详情  "
                        // + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                hotelorder_list_id,
                                JsonParse
                                        .getHotelOrderListInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(hotelorder_list_err));
                    }
                });
    }

    /**
     * 酒店订单详情
     *
     * @param order_id
     */
    public void HotelOrderDatails(String order_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("order_id", order_id);
        System.out.println("-----------------酒店订单详情   " + hotelorder_datails
                + "&key=" + State.UserKey + "&order_id=" + order_id);
        httpUtils.send(HttpMethod.GET, hotelorder_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                hotelorder_datails_id,
                                JsonParse
                                        .getHotelOrderDatailsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(hotelorder_datails_err));
                    }
                });
    }

    /**
     * 酒店订单取消
     *
     * @param order_id
     */
    public void HotelOrderCancel(String order_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("stime", order_id);
        httpUtils.send(HttpMethod.POST, hotelorder_cancel, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                hotelorder_cancel_id,
                                JsonParse
                                        .getHotelSubmitOrder(responseInfo.result)));
                        System.out.println("-------------------酒店订单取消   "
                                + responseInfo.result.toString());
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(hotelorder_cancel_err));
                    }
                });
    }

    /**
     * 服务首页
     */
    public void servicehome() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        // params.addQueryStringParameter("class_id", class_id);
        httpUtils.send(HttpMethod.GET, service_home,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                service_home_id,
                                JsonParse.getservicehome(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(service_home_err));
                    }
                });
    }

    /**
     * 人才大厅
     */
    public void Talenthall(String keywork, String sincerity, String sort) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("keywork", keywork);
        params.addQueryStringParameter("sincerity", sincerity);
        params.addQueryStringParameter("sort", sort);
        httpUtils.send(HttpMethod.GET, talent_hall, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                talent_hall_id,
                                JsonParse.getservicehome(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(talent_hall_err));
                    }
                });
    }

    /**
     * 服务大厅
     */
    public void Servicehall(String curpage, String gc_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("gc_id", gc_id);
        httpUtils.send(HttpMethod.GET, servicehall, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                servicehall_id,
                                JsonParse.getServicehall(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(servicehall_err));
                    }
                });
    }

    /**
     * 服务详情
     *
     * @param goods_id
     */
    public void Servicedetails(String goods_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("goods_id", goods_id);
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存
        System.out.println("---------------- 服务详情  " + service_details
                + "&goods_id=" + goods_id + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, service_details, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                service_details_id,
                                JsonParse.getServicedetails(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(service_details_err));
                    }
                });
    }

    /**
     * 服务商详情
     */
    public void Storedetails(String store_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("store_id", store_id);
        System.out.println("------------------服务商详情  " + store_details
                + "&store_id=" + store_id);
        httpUtils.send(HttpMethod.GET, store_details, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // int maxLogSize = 4000;
                        // String veryLongString = arg0.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------服务商详情     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                store_details_id,
                                JsonParse.getStoredetails(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(store_details_err));
                    }
                });
    }

    /**
     * 服务商服务列表
     */
    public void StoreGoodsList(String store_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("store_id", store_id);
        System.out.println("------------------服务列表  " + storegoods_list
                + "&store_id=" + store_id);
        httpUtils.send(HttpMethod.GET, storegoods_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // int maxLogSize = 4000;
                        // String veryLongString = arg0.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------服务商服务列表     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                storegoods_list_id,
                                JsonParse.getStoreHomePageList(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(storegoods_list_err));
                    }
                });
    }

    /**
     * 评分列表
     */
    public void StoreList(String store_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("store_id", store_id);
        System.out.println("------------------评分列表  " + store_list
                + "&store_id=" + store_id);
        httpUtils.send(HttpMethod.GET, store_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        int maxLogSize = 4000;
                        String veryLongString = arg0.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------评分列表     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                store_list_id,
                                JsonParse.getStoreHomePagePJInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(store_list_err));
                    }
                });
    }

    /**
     * 发布任务分类
     */
    public void ReleaseTask() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.GET, release_task, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("------------------- 发布分类 "
                                + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                release_task_id,
                                JsonParse.getReleaseTask(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(release_task_err));
                    }
                });
    }

    /**
     * 发布任务
     *
     * @param title
     * @param content
     * @param phone
     * @param task_type
     * @param price
     * @param gc_id
     * @param class_name
     * @param select_date
     * @param flie_sn
     */
    public void ReleaseTask2(String title, String content, String phone,
                             String task_type, String price, String gc_id, String class_name,
                             String select_date, String flie_sn) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("title", title);
        params.addBodyParameter("content", content);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("task_type", task_type);
        params.addBodyParameter("price", price);
        params.addBodyParameter("gc_id", gc_id);
        params.addBodyParameter("class_name", class_name);
        params.addBodyParameter("select_date", select_date);
        params.addBodyParameter("flie_sn", flie_sn);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, release_task2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                release_task2_id,
                                JsonParse.getReleaseTask2(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(release_task2_err));
                    }
                });
    }

    /**
     * 图片上传
     *
     * @param file_sn
     * @param tempFile
     */
    public void ImageUpload(String file_sn, final File tempFile) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("file_sn", file_sn);
        params.addBodyParameter("key", State.UserKey);
        // TODO 上传文件
        if (tempFile.exists()) {
            params.addBodyParameter("fj-input", tempFile);
        }
        httpUtils.send(HttpMethod.POST, image_upload, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // 无论上传成功与否,均删除临时图片
                        tempFile.delete();
                        handler.sendMessage(handler.obtainMessage(
                                image_upload_id,
                                JsonParse.getImageUpload(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        // 无论上传成功与否,均删除临时图片
                        tempFile.delete();
                        handler.sendMessage(handler
                                .obtainMessage(image_upload_err));
                    }
                });
    }

    /**
     * 服务需求分类
     */
    public void ServiceDemand() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        // params.addQueryStringParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.GET, service_demand,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                service_demand_id,
                                JsonParse.getServiceDemand(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(service_demand_err));
                    }
                });
    }

    /**
     * 服务商收藏
     *
     * @param id
     */
    public void StoreCollection(String id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", id);
        httpUtils.send(HttpMethod.POST, store_collection, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                store_collection_id,
                                JsonParse.getReleaseTask2(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(store_collection_err));
                    }
                });
    }

    /**
     * 服务收藏
     *
     * @param id
     */
    public void ServiceCollection(String id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", id);
        httpUtils.send(HttpMethod.POST, service_collection, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                service_collection_id,
                                JsonParse.getReleaseTask2(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(service_collection_err));
                    }
                });
    }

    /**
     * 人才大厅
     *
     * @param keywork 搜索
     * @param gc_id
     * @param typekey
     * @param type
     */
    public void TalentHalllist(String curpage, String keywork, String gc_id,
                               String typekey, String type) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("keywork", keywork);
        params.addQueryStringParameter("gc_id", gc_id);
        params.addQueryStringParameter(typekey, type);
        params.addQueryStringParameter("curpage", curpage);
        // System.out.println("-----------------" + keywork + "   " + gc_id
        // + "   " + typekey + "   " + type);
        httpUtils.send(HttpMethod.GET, talent_halllist, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                talent_halllist_id,
                                JsonParse.getServiceDemandlist(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(talent_halllist_err));
                    }
                });
    }

    /**
     * 人才大厅
     */
    public void TalentHalllist(String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.send(HttpMethod.GET, talent_halllist, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                talent_halllist_id,
                                JsonParse.getServiceDemandlist(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(talent_halllist_err));
                    }
                });
    }

    /**
     * 服务订单取消
     */
    public void Servicedel(String order_id, String state_info,
                           String state_info1) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("state_info", state_info);
        params.addBodyParameter("state_info1", state_info1);
        httpUtils.send(HttpMethod.POST, service_del, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                service_del_id,
                                JsonParse.getReleaseTask2(arg0.result)));
                        // System.out.println("----------------服务订单取消   "
                        // + arg0.result.toString());
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(service_del_err));
                    }
                });
    }

    /**
     * 图片删除
     */
    public void ImageDel(final String eid) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("eid", eid);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, image_del, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("-----------------图片删除   "
                                + arg0.result.toString() + "   " + eid);
                        handler.sendMessage(handler.obtainMessage(image_del_id,
                                JsonParse.getImageUpload(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(image_del_err));
                    }
                });
    }

    /**
     * 服务收藏列表
     */
    public void ServiceCollectionList(String pagesize, String curpage,
                                      String type) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("type", type);
        System.out.println("-------------------服务收藏列表  "
                + servicecollection_list + "&key=" + State.UserKey
                + "&pagesize=" + pagesize + "&curpage=" + curpage + "&type="
                + type);
        httpUtils.send(HttpMethod.GET, servicecollection_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {

                        handler.sendMessage(handler.obtainMessage(
                                servicecollection_list_id,
                                JsonParse.getReleaseTask2(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(servicecollection_list_err));
                    }
                });
    }

    /**
     * 默认地址
     *
     * @param address_id
     */
    public void DefaultAddress(String address_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("address_id", address_id);
        System.out.println("-------------------默认地址  "
                + default_address + "&key=" + State.UserKey
                + "&address_id=" + address_id);
        httpUtils.send(HttpMethod.POST, default_address, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("---------------", "默认地址  " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                default_address_id,
                                JsonParse.getReleaseTask2(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("---------------", "默认地址错误  " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(default_address_err));
                    }
                });
    }

    /**
     * 找回密码
     *
     * @param mobile
     */
    public void ForgetPassword(String mobile, String mobile_code) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("mobile_code", mobile_code);
        // System.out.println("----------------------找回密码   mobile_code="+mobile_code+"  mobile_code="+mobile_code);
        httpUtils.send(HttpMethod.POST, forget_password, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        // System.out.println("---------------验证码错误   "+arg1);
                        handler.sendMessage(handler
                                .obtainMessage(forget_password_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // System.out.println("---------------找回密码   "
                        // + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                forget_password_id,
                                JsonParse.geRegistration(arg0.result)));
                    }
                });
    }

    /**
     * 找回密码验证码
     *
     * @param mobile
     */
    public void PasswordCode(String mobile) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("mobile", mobile);
        httpUtils.send(HttpMethod.POST, password_code, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        // System.out.println("---------------验证码错误   "+arg1);
                        handler.sendMessage(handler
                                .obtainMessage(password_code_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("---------------验证码   "
                                + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                password_code_id,
                                JsonParse.getCode(arg0.result)));
                    }
                });
    }

    /**
     * 支付宝
     *
     * @param payment_code
     */
    public void ZfbGetConfig(String payment_code) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("payment_code", payment_code);
        httpUtils.send(HttpMethod.POST, zfb_getConfig, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("---------------支付宝错误   " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(zfb_getConfig_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("---------------支付宝   "
                                + arg0.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                zfb_getConfig_id,
                                JsonParse.getZfbGetConfig(arg0.result)));
                    }
                });
    }

    /**
     * 查询支付列表
     */
    public void GetPaymentList() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, get_paymentList, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "支付列表   " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                get_paymentList_id,
                                JsonParse.getPaymentList(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("----------------", "支付列表失败   " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(zfb_getConfig_err));
                    }
                });
    }

    /**
     * 支付宝
     */
    public void ZfbPay(String pay_sn, String payment_code) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pay_sn", pay_sn);
        params.addQueryStringParameter("payment_code", payment_code);
        System.out.println("-------------------支付宝  "
                + zfb_pay + "&key=" + State.UserKey
                + "&pay_sn=" + pay_sn + "&payment_code=" + payment_code);
        httpUtils.send(HttpMethod.GET, zfb_pay, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                zfb_pay_id,
                                JsonParse.getCode(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(zfb_pay_err));
                    }
                });
    }


    /**
     * 优惠套装
     *
     * @param goods_id
     */
    public void GetBundling(String goods_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey == null ? "" : State.UserKey);
        params.addQueryStringParameter("goods_id", goods_id);
        Log.i("-----------------优惠套装", get_bundling + "&key=" + State.UserKey
                + "&goods_id=" + goods_id);
        httpUtils.configCurrentHttpCacheExpiry(1000);// 缓存
        httpUtils.send(HttpMethod.GET, get_bundling, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println("----------------优惠套装  "
                                + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                get_bundling_id,
                                JsonParse.getGetBundling(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("----------------优惠套装错误" + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(get_bundling_err));
                    }
                });
    }

    /**
     * 套餐加入购物车
     */
    public void TCaddInCart(String bl_id) {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        params.addBodyParameter("bl_id", bl_id);
        httpUtils.send(HttpMethod.POST, add_cart, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------套餐加入购物车  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(add_cart_id,
                                JsonParse.Operation(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------套餐加入购物车 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(add_cart_err));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(add_cart_start));
                    }
                });
    }

    /**
     * 组合商品
     */
    public void AssemblageGoods(String goods_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey == null ? ""
                : State.UserKey);
        params.addQueryStringParameter("goods_id", goods_id);
        System.out.println("--------------组合商品  " + assemblage_goods
                + "&goods_id=" + goods_id + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, assemblage_goods, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------订单详情     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                assemblage_goods_id,
                                JsonParse
                                        .getAssemblageGoods(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(assemblage_goods_err));
                    }
                });
    }

    /**
     * 组合商品加入购物车
     */
    public void AddAssemblageGoods(String goods_ids) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey == null ? ""
                : State.UserKey);
        params.addQueryStringParameter("goods_ids", goods_ids);
        System.out.println("--------------组合商品加入购物车  " + add_assemblage_goods
                + "&goods_ids=" + goods_ids + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, add_assemblage_goods, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // int maxLogSize = 4000;
                        // String veryLongString =
                        // responseInfo.result.toString();
                        // for (int i = 0; i <= veryLongString.length()
                        // / maxLogSize; i++) {
                        // int start = i * maxLogSize;
                        // int end = (i + 1) * maxLogSize;
                        // end = end > veryLongString.length() ? veryLongString
                        // .length() : end;
                        // System.out.println("------------------订单详情     "
                        // + veryLongString.substring(start, end));
                        // }
                        handler.sendMessage(handler.obtainMessage(
                                add_assemblage_goods_id,
                                JsonParse.getCode(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(add_assemblage_goods_err));
                    }
                });
    }


    /**
     * 会员签到
     */
    public void MemberSign() {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        httpUtils.send(HttpMethod.POST, member_sign, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------会员签到  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(member_sign_id,
                                JsonParse.getMemberSign(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------会员签到 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(member_sign_err));
                    }

                });
    }

    /**
     * 申请开通驿站功能
     *
     * @param dtruename    姓名（必填）
     * @param dmobile      手机（必填）
     * @param dtelephony   固定电话
     * @param daddressname 固定电话
     * @param area_id_2    城市编号（必填）
     * @param area_id      区域编号（必填）
     * @param area_info    省市区中文名字拼接起来的字符串（必填）
     * @param daddress     详细地址（必填）
     * @param didcard      身份证号码（必填）
     * @param didcardimg   身份证扫描件（必填）
     */
    public void OpenPost(String dtruename, String dmobile, String dtelephony, String daddressname, String area_id_2
            , String area_id, String area_info, String daddress, String didcard, final File didcardimg) {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        params.addBodyParameter("dtruename", dtruename);
        params.addBodyParameter("dmobile", dmobile);
        params.addBodyParameter("dtelephony", dtelephony);
        params.addBodyParameter("daddressname", daddressname);
        params.addBodyParameter("area_id_2", area_id_2);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("area_info", area_info);
        params.addBodyParameter("daddress", daddress);
        params.addBodyParameter("didcard", didcard);
        params.addBodyParameter("didcardimg", didcardimg);
        httpUtils.send(HttpMethod.POST, open_post, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        didcardimg.delete();
                        System.out.println("-----------------开通驿站  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(open_post_id,
                                JsonParse.getOpenPost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        didcardimg.delete();
                        System.out.println("-----------------开通驿站 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(open_post_err));
                    }

                });
    }

    /**
     * 短信模板
     */
    public void SmsMode() {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        httpUtils.send(HttpMethod.POST, sms_mode, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------短信模板  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(sms_mode_id,
                                JsonParse.getSmsModerInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------短信模板 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(sms_mode_err));
                    }

                });
    }

    /**
     * 编辑站点信息
     *
     * @param dmobile             手机（必填）
     * @param daddressname        驿站名称（必填）
     * @param area_id_2           城市编号（必填）
     * @param area_id             区域编号（必填）
     * @param area_info           省市区中文名字拼接起来的字符串（必填）
     * @param daddress            详细地址（必填）
     * @param business_store_time 开店时间
     * @param business_colse_time 关店时间
     * @param sms_code            短信模板
     */
    public void EditDelivery(String dmobile, String daddressname, String area_id_2, String area_id, String area_info, String daddress, String business_store_time, String business_colse_time, String sms_code) {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        params.addBodyParameter("dmobile", dmobile);
        params.addBodyParameter("daddressname", daddressname);
        params.addBodyParameter("area_id_2", area_id_2);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("area_info", area_info);
        params.addBodyParameter("daddress", daddress);
        params.addBodyParameter("business_store_time", business_store_time);
        params.addBodyParameter("business_colse_time", business_colse_time);
        params.addBodyParameter("sms_code", sms_code);
        httpUtils.send(HttpMethod.POST, edit_delivery, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------编辑站点  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(edit_delivery_id,
                                JsonParse.getOpenPost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------编辑站点 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(edit_delivery_err));
                    }

                });
    }

    /**
     * 获取驿站信息
     */
    public void getDeliveryInfo() {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        httpUtils.send(HttpMethod.POST, getdelivery_info, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------获取驿站信息  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(getdelivery_info_id,
                                JsonParse.getMyStoreDataInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------获取驿站信息 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(getdelivery_info_err));
                    }

                });
    }

    /**
     * 未入库列表
     *
     * @param pagesize
     * @param curpage
     */
    public void DeliveryOrderList(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey == null ? "" : State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        System.out.println("-------------未入库列表  " + delivery_order_list
                + "&pagesize=" + pagesize + "&curpage=" + curpage + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, delivery_order_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                delivery_order_list_id,
                                JsonParse
                                        .getCourierstorageInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delivery_order_list_err));
                    }
                });
    }

    /**
     * 通知列表
     *
     * @param pagesize 每页记录条数 默认10
     * @param curpage  当前页码 默认1
     */
    public void DeliveryInformList(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey == null ? "" : State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        System.out.println("-------------通知列表  " + delivery_inform_list
                + "&pagesize=" + pagesize + "&curpage=" + curpage + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, delivery_inform_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                delivery_inform_list_id,
                                JsonParse
                                        .getNoticeInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delivery_inform_list_err));
                    }
                });
    }

    /**
     * 本店快递公司列表
     */
    public void DeliveryCourier() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey == null ? "" : State.UserKey);
        System.out.println("-------------本店快递公司列表  " + delivery_courier + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, delivery_courier, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                delivery_courier_id,
                                JsonParse
                                        .getDeliveryCourierInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delivery_courier_err));
                    }
                });
    }

    /**
     * 可添加快递公司列表
     */
    public void DeliveryAddlist() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey == null ? "" : State.UserKey);
        System.out.println("-------------可添加快递公司列表  " + delivery_addlist + "&key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, delivery_addlist, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                delivery_addlist_id,
                                JsonParse
                                        .getRecordInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delivery_addlist_err));
                    }
                });
    }

    /**
     * 记账列表
     *
     * @param pagesize 每页记录条数 默认10
     * @param curpage  当前页码 默认1
     */
    public void DeliveryTally(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey == null ? "" : State.UserKey);
        params.addBodyParameter("pagesize", pagesize);
        params.addBodyParameter("curpage", curpage);
        httpUtils.send(HttpMethod.POST, delivery_tally, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------记账列表  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                delivery_tally_id,
                                JsonParse
                                        .getDeliveryTallyInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delivery_tally_err));
                    }
                });
    }

    /**
     * 添加快递公司
     *
     * @param id
     */
    public void DeliveryAddcourier(String id) {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        params.addBodyParameter("id", id);
        httpUtils.send(HttpMethod.POST, delivery_addcourier, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------添加快递公司  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(delivery_addcourier_id,
                                JsonParse.getOpenPost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------添加快递公司 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(delivery_addcourier_err));
                    }

                });
    }

    /**
     * 编辑记账列表
     *
     * @param id   快递公司id
     * @param num  编辑数量
     * @param type 为1时编辑寄件数量，为2时编辑派件
     */
    public void DeliveryTallyUpdate(String id, String num, String type) {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        params.addBodyParameter("id", id);
        params.addBodyParameter("num", num);
        params.addBodyParameter("type", type);
        httpUtils.send(HttpMethod.POST, delivery_tally_update, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------编辑记账列表  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(delivery_tally_update_id,
                                JsonParse.getOpenPost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------编辑记账列表 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(delivery_tally_update_err));
                    }

                });
    }

    /**
     * 未入库搜索
     *
     * @param reciver_mobphone
     */
    public void DeliveryOrderPhone(String reciver_mobphone) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey == null ? "" : State.UserKey);
        params.addQueryStringParameter("reciver_mobphone", reciver_mobphone);
        System.out.println("-------------未入库搜索  " + delivery_Order_phone + "&key=" + State.UserKey + "&reciver_mobphone=" + reciver_mobphone);
        httpUtils.send(HttpMethod.GET, delivery_Order_phone, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        handler.sendMessage(handler.obtainMessage(
                                delivery_order_list_id,
                                JsonParse
                                        .getCourierstorageInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delivery_order_list_err));
                    }
                });
    }

    /**
     * 上传省市区
     *
     * @param province_name
     * @param city_name
     * @param area_name
     */
    public void getGPSInfo(String province_name, String city_name, String area_name) {
        if (Mark.State.UserKey == null) return;
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey);
        params.addBodyParameter("province_name", province_name);
        params.addBodyParameter("city_name", city_name);
        params.addBodyParameter("area_name", area_name);
        Log.i("-----------------", "上传省市区 province_name=" + province_name + " city_name=" + city_name + " area_name=" + area_name);
        httpUtils.send(HttpMethod.POST, get_gpsinfo, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------上传省市区  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(delivery_tally_update_id,
                                JsonParse.getOpenPost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------上传省市区 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(get_gpsinfo_err));
                    }

                });
    }

    /**
     * 获取地址
     */
    public void gpsInfoToApp() {
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", Mark.State.UserKey == null ? "" : Mark.State.UserKey);
        httpUtils.send(HttpMethod.POST, gpsinfoto_app, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        System.out.println("-----------------获取地址  "
                                + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(gpsinfoto_app_id,
                                JsonParse.getgpsInfoToAppInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("-----------------获取地址 错误" + msg);
                        handler.sendMessage(handler.obtainMessage(gpsinfoto_app_err));
                    }

                });
    }

    /**
     * 获取二级分类
     *
     * @param firstid
     */
    public void getClassify2(String firstid) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("firstid", firstid);
        System.out.println("--------------------二级   " + firstid);
        httpUtils.send(HttpMethod.POST, Mark.get_classify2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------获取二级分类    "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                get_classify2_id,
                                JsonParse
                                        .getAmClassify2Info(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(get_classify2_err));
                    }
                });
    }

    /**
     * 收入总额
     */
    public void DeliveryMoney(String year, String month) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("year", year);
        params.addBodyParameter("month", month);
        httpUtils.send(HttpMethod.POST, Mark.delivery_money, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString =
                                responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            Log.i("-------------------", "收入总额  " + veryLongString.substring(start, end));
                        }

                        handler.sendMessage(handler.obtainMessage(
                                delivery_money_id,
                                JsonParse
                                        .getAllIncomeInfoo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_money_err));
                    }
                });
    }

    /**
     * 数据统计
     */
    public void ExpressStorageNum(String year, String month) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("year", year);
        params.addBodyParameter("month", month);
        httpUtils.send(HttpMethod.POST, Mark.express_storage_num, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "数据统计  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                express_storage_num_id,
                                JsonParse
                                        .getDataStatisticsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(express_storage_num_err));
                    }
                });
    }

    /**
     * 我的驿站
     */
    public void CourierMe() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, Mark.courier_me, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "我的驿站  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                courier_me_id,
                                JsonParse
                                        .getCourierMeInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(courier_me_err));
                    }
                });
    }

    /**
     * 驿站首页
     */
    public void CourierHome() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, Mark.courier_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "驿站首页  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                courier_home_id,
                                JsonParse
                                        .getCourierHomeInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(courier_home_err));
                    }
                });
    }

    public void GoodsList(String key, String store_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", key);
        if (store_id != null) {
            params.addQueryStringParameter("store_id", store_id);
            httpUtils.send(HttpMethod.GET, good_list, params,
                    new RequestCallBack<String>() {

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            Log.i("-----------------", "商品列表  " + responseInfo.result);
                            handler.sendMessage(handler.obtainMessage(
                                    goods_list_id,
                                    JsonParse
                                            .getGoodsListModerInfo(responseInfo.result)));
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            System.out.println("----------------失败");
                            handler.sendMessage(handler
                                    .obtainMessage(goods_list_err));
                        }
                    });
        }
    }


    /**
     * 商品列表
     *
     * @param key        排序方式 1-销量 2-浏览量 3-价格 空-按最新发布排序
     * @param order      排序方式 1-升序 2-降序
     * @param page       每页数量
     * @param curpage    当前页码
     * @param gc_id      分类编号
     * @param store_id   店铺编号
     * @param keyword    搜索关键字
     *                   gc_id,store_id和keyword三选一不能同时出现
     * @param b_id       品牌编号
     * @param own_shop   0：否 1：是  是否自营
     * @param gift       0：否 1：是 是否有赠品
     * @param groupbuy   0：否 1：是 是否抢购
     * @param xianshi    0：否 1：是  是否限时
     * @param virtual    0：否 1：是 是否虚拟
     * @param a_id       属性集合拼接  例如 1_2_3_4_5
     * @param price_from 价格开始
     * @param price_to   价格结束
     */
    public void GoodsList(String key, String order, String page, String curpage, String
            gc_id, String store_id, String keyword
            , String b_id, String own_shop, String gift, String groupbuy, String xianshi, String
                                  virtual, String a_id, String price_from, String price_to) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", key);
        params.addQueryStringParameter("order", order);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("own_shop", own_shop);
        params.addQueryStringParameter("gift", gift);
        params.addQueryStringParameter("groupbuy", groupbuy);
        params.addQueryStringParameter("xianshi", xianshi);
        params.addQueryStringParameter("virtual", virtual);
        if (!TextUtils.isEmpty(price_from)) {
            params.addQueryStringParameter("price_from", price_from);
        }
        if (!TextUtils.isEmpty(price_to)) {
            params.addQueryStringParameter("price_to", price_to);
        }
        if (b_id != null) {
            params.addQueryStringParameter("b_id", b_id);
        }
        if (a_id != null) {
            params.addQueryStringParameter("a_id", a_id);
        }
        if (gc_id != null) {
            params.addQueryStringParameter("gc_id", gc_id);
        } else if (store_id != null) {
            params.addQueryStringParameter("store_id", store_id);
        } else if (keyword != null) {
            params.addQueryStringParameter("keyword", keyword);
        }
        Log.i("-------------------", "商品列表 " + good_list + "&key=" + key
                + "&order=" + order + "&page=" + page + "&curpage=" + curpage + "&gc_id=" + gc_id
                + "&store_id=" + store_id + "&keyword=" + keyword + "&b_id=" + b_id + "&own_shop=" + own_shop
                + "&gift=" + gift + "&groupbuy=" + groupbuy + "&xianshi=" + xianshi + "&virtual=" + virtual
                + "&a_id=" + a_id + "&price_from=" + price_from + "&price_to=" + price_to);
        httpUtils.send(HttpMethod.GET, good_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "商品列表  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                goods_list_id,
                                JsonParse
                                        .getGoodsListModerInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(goods_list_err));
                    }
                });
    }

    /**
     * 驿站客户列表
     *
     * @param type 默认空 is_vip:是否VIP is_member：是否会员
     */
    public void DeliveryCustomerList(String type, String consignee_mobile) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("consignee_mobile", consignee_mobile);
        httpUtils.send(HttpMethod.GET, delivery_customer_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "驿站客户列表  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                delivery_customer_list_id,
                                JsonParse
                                        .getCustomerListInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("----------------失败");
                        handler.sendMessage(handler
                                .obtainMessage(delivery_customer_list_err));
                    }
                });
    }

    /**
     * 余额提现
     *
     * @param pdc_amount         提现金额（不能少于1元）
     * @param pdc_bank_name      收款银行名称（选填）
     * @param pdc_bank_no        收款银行账号（选填）
     * @param pdc_bank_user      开户人姓名（选填）
     * @param pdc_alipay_account 支付宝帐号（选填）
     */
    public void DeliveryYuerDeposit(String pdc_amount, String pdc_bank_name, String
            pdc_bank_no, String pdc_bank_user, String pdc_alipay_account) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("pdc_amount", pdc_amount);
        params.addBodyParameter("pdc_bank_name", pdc_bank_name);
        params.addBodyParameter("pdc_bank_no", pdc_bank_no);
        params.addBodyParameter("pdc_bank_user", pdc_bank_user);
        params.addBodyParameter("pdc_alipay_account", pdc_alipay_account);
        httpUtils.send(HttpMethod.POST, Mark.delivery_yuer_deposit, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "余额提现  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_yuer_deposit_id,
                                JsonParse
                                        .getOpenPost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_yuer_deposit_err));
                    }
                });
    }

    /**
     * 加入 vip
     *
     * @param id
     */
    public void DeliveryCustomerStatevip(String id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("id", id);
        httpUtils.send(HttpMethod.GET, delivery_customer_statevip, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "加入 vip  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                delivery_customer_statevip_id,
                                JsonParse
                                        .getOpenPost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-----------------", "加入 vip失败" + msg);
                        handler.sendMessage(handler
                                .obtainMessage(delivery_customer_statevip_err));
                    }
                });
    }


    /**
     * 消费记录
     *
     * @param pagesize
     * @param curpage
     */
    public void DeliveryConsume(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.send(HttpMethod.GET, delivery_consume, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "消费记录 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                delivery_consume_id,
                                JsonParse
                                        .getTopupLogInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-----------------", "消费记录 错误" + msg);
                        handler.sendMessage(handler
                                .obtainMessage(delivery_consume_err));
                    }
                });
    }

    /**
     * 入库列表
     *
     * @param pagesize
     * @param curpage
     * @param status
     */
    public void ExpressStoragelist(String pagesize, String curpage, String status, String con) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("status", status);
        params.addQueryStringParameter("con", con);
        httpUtils.send(HttpMethod.GET, express_storage_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "入库列表 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                express_storage_list_id,
                                JsonParse
                                        .getStorageLogInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-----------------", "入库列表 错误" + msg);
                        handler.sendMessage(handler
                                .obtainMessage(express_storage_list_err));
                    }
                });
    }


    /**
     * 短信发送
     *
     * @param express_code
     * @param code
     * @param sms_code
     * @param consignee_mobile
     */
    public void DeliverySmsSend(String express_code, String code, String sms_code, String
            consignee_mobile) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("express_code", express_code);
        params.addBodyParameter("code", code);
        params.addBodyParameter("sms_code", sms_code);
        params.addBodyParameter("consignee_mobile", consignee_mobile);
        httpUtils.send(HttpMethod.POST, Mark.delivery_sms_send, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "短信发送  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_sms_send_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_sms_send_err));
                    }
                });
    }

    /**
     * 充值记录
     *
     * @param pagesize
     * @param curpage
     */
    public void DeliveryRecharge(String pagesize, String curpage) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.send(HttpMethod.GET, delivery_recharge, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "充值记录 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                delivery_recharge_id,
                                JsonParse
                                        .getTopUpLogInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-----------------", "充值记录 错误" + msg);
                        handler.sendMessage(handler
                                .obtainMessage(delivery_recharge_err));
                    }
                });
    }

    /**
     * 短信发送列表
     *
     * @param pagesize
     * @param curpage
     * @param state
     * @param inphone
     */
    public void SmsLogList(String pagesize, String curpage, String state, String inphone) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("state", state);
        params.addQueryStringParameter("inphone", inphone);
        httpUtils.send(HttpMethod.GET, sms_log_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "短信发送列表 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                sms_log_list_id,
                                JsonParse
                                        .getSendTypeInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-----------------", "短信发送列表 错误" + msg);
                        handler.sendMessage(handler
                                .obtainMessage(sms_log_list_err));
                    }
                });
    }


    /**
     * 签收
     *
     * @param code
     */
    public void SingFor(String code) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("code", code);
        httpUtils.send(HttpMethod.POST, Mark.sing_for, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "签收  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                sing_for_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(sing_for_err));
                    }
                });
    }

    /**
     * 投递费用列表
     */
    public void DeliveryCost() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("pagesize", "100");
        params.addBodyParameter("curpage", "1");
        httpUtils.send(HttpMethod.POST, Mark.delivery_cost, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "投递费用列表  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_cost_id,
                                JsonParse
                                        .getEditCostInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_cost_err));
                    }
                });
    }

    /**
     * 新增投递费用
     */
    public void DeliveryAddCost(String express_id, String money) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", express_id);
        params.addBodyParameter("money", money);
        Log.i("-----------------", "id=" + express_id + "   money=" + money);
        httpUtils.send(HttpMethod.POST, Mark.delivery_add_cost, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "新增投递费用  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_add_cost_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_add_cost_err));
                    }
                });
    }

    /**
     * 编辑投递费用
     *
     * @param id
     * @param money
     */
    public void DeliveryEdit(String id, String money) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("id", id);
        params.addBodyParameter("money", money);
        httpUtils.send(HttpMethod.POST, Mark.delivery_edit, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "编辑投递费用  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_edit_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_edit_err));
                    }
                });
    }

    /**
     * 入库编号
     */
    public void ExpressNo() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, Mark.express_no, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "入库编号  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                express_no_id,
                                JsonParse
                                        .getExpressNo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(express_no_err));
                    }
                });
    }

    /**
     * 快递入库
     *
     * @param express_id       快递公司编号（必填）
     * @param express_name     快递公司名称（必填）
     * @param express_code     快递单号（必填）
     * @param express_no       入库编号
     * @param consignee        联系人（必填）
     * @param consignee_mobile 联系人手机（必填）
     * @param is_receivables   是否代收货款 0：否 1：是 默认0
     * @param is_send          是否送货上门 0：否 1：是 默认0
     * @param is_sendsms       是否发送短信 0：否 1：是 默认0
     * @param sms_code         短信模板（发送短信是必选）
     */
    public void ExpressStorage(String express_id, String express_name, String
            express_code, String express_no, String consignee, String consignee_mobile,
                               String is_receivables, String is_send, String is_sendsms, String sms_code) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("express_id", express_id);
        params.addBodyParameter("express_name", express_name);
        params.addBodyParameter("express_code", express_code);
        params.addBodyParameter("express_no", express_no);
        params.addBodyParameter("consignee", consignee);
        params.addBodyParameter("consignee_mobile", consignee_mobile);
        params.addBodyParameter("is_receivables", is_receivables);
        params.addBodyParameter("is_send", is_send);
        params.addBodyParameter("is_sendsms", is_sendsms);
        params.addBodyParameter("sms_code", sms_code);
        Log.i("-------------------", "快递入库  key=" + State.UserKey + " express_id=" + express_id + " express_name=" + express_name + " express_code=" + express_code + " express_no=" + express_no
                + " consignee=" + consignee + " consignee_mobile=" + consignee_mobile + " is_receivables=" + is_receivables + " is_send=" + is_send + " is_sendsms=" + is_sendsms + " sms_code=" + sms_code);
        httpUtils.send(HttpMethod.POST, Mark.express_storage, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "快递入库  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                express_storage_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(express_storage_err));
                    }
                });
    }

    /**
     * 站点订单详情
     *
     * @param order_id
     */
    public void DeliveryOrderInfo(String order_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        httpUtils.send(HttpMethod.POST, Mark.delivery_order_info, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "站点订单详情  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_order_info_id,
                                JsonParse
                                        .getDeliveryOrderInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_order_info_err));
                    }
                });
    }

    /**
     * 站点入库
     *
     * @param order_id         订单号（必填）
     * @param express_id       快递公司编号（必填）
     * @param express_name     快递公司名称（必填）
     * @param express_code     快递单号（必填）
     * @param express_no       入库编号
     * @param consignee        联系人（必填）
     * @param consignee_mobile 联系人手机（必填）
     * @param is_receivables   是否代收货款 0：否 1：是 默认0
     * @param is_send          是否送货上门 0：否 1：是 默认0
     * @param is_sendsms       是否发送短信 0：否 1：是 默认0
     * @param sms_code         短信模板（发送短信是必选）
     */
    public void DeliveryOrderState(String order_id, String express_id, String
            express_name, String express_code, String express_no, String consignee,
                                   String consignee_mobile, String is_receivables, String is_send, String
                                           is_sendsms, String sms_code) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("express_id", express_id);
        params.addBodyParameter("express_name", express_name);
        params.addBodyParameter("express_code", express_code);
        params.addBodyParameter("express_no", express_no);
        params.addBodyParameter("consignee", consignee);
        params.addBodyParameter("consignee_mobile", consignee_mobile);
        params.addBodyParameter("is_receivables", is_receivables);
        params.addBodyParameter("is_send", is_send);
        params.addBodyParameter("is_sendsms", is_sendsms);
        params.addBodyParameter("sms_code", sms_code);
        Log.i("-------------------", "站点入库  key=" + State.UserKey + " order_id=" + order_id + " express_id=" + express_id + " express_name=" + express_name + " express_code=" + express_code + " express_no=" + express_no
                + " consignee=" + consignee + " consignee_mobile=" + consignee_mobile + " is_receivables=" + is_receivables + " is_send=" + is_send + " is_sendsms=" + is_sendsms + " sms_code=" + sms_code);
        httpUtils.send(HttpMethod.POST, Mark.express_storage, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "站点入库 " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                express_storage_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(express_storage_err));
                    }
                });
    }

    /**
     * 批量入库编号
     *
     * @param express_code
     */
    public void DeliveryBlukStorage(String express_code) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("express_code", express_code);
        httpUtils.send(HttpMethod.POST, Mark.delivery_bluk_storage, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "批量入库编号  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_bluk_storage_id,
                                JsonParse
                                        .getBatchOperationInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_bluk_storage_err));
                    }
                });
    }

    /**
     * 批量入库
     *
     * @param bluk_storage
     */
    public void DeliveryBlukStorageSuccess(String bluk_storage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("bluk_storage", bluk_storage);
        httpUtils.send(HttpMethod.POST, Mark.delivery_bluk_storage_success, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "批量入库  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                delivery_bluk_storage_success_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(delivery_bluk_storage_success_err));
                    }
                });
    }

    /**
     * 统一发送身份验证码
     *
     * @param type
     */
    public void SendVerifycode(String type) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("type", type);
        httpUtils.send(HttpMethod.GET, send_verifycode, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-----------------", "统一发送身份验证码 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                send_verifycode_id,
                                JsonParse
                                        .BindingPhone(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-----------------", "统一发送身份验证码 错误" + msg);
                        handler.sendMessage(handler
                                .obtainMessage(send_verifycode_err));
                    }
                });
    }

    /**
     * 验证验证码
     *
     * @param type
     * @param auth_code
     */
    public void VerifyVerifyCode(String type, String auth_code) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("type", type);
        params.addBodyParameter("auth_code", auth_code);
        httpUtils.send(HttpMethod.POST, Mark.verify_verifycode, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "验证验证码  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                verify_verifycode_id,
                                JsonParse
                                        .getEditCost(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(verify_verifycode_err));
                    }
                });
    }

    /**
     * 获取授权
     */
    public void getToken() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        Log.i("-------------------", "获取授权  key=" + State.UserKey);
        httpUtils.send(HttpMethod.GET, Mark.get_token, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "获取授权  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                get_token_id, JsonParse.getH5Token(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(get_token_err));
                    }
                });
    }

    /**
     * 签到日期
     */
    public void SignDate() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, Mark.sign_date, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString = responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            Log.i("----------------", "签到日期" + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                sign_date_id,
                                JsonParse
                                        .getSignDate(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(sign_date_err));
                    }
                });
    }

    /**
     * 发票列表
     *
     * @param vat_hash
     * @param del_id
     */
    public void getInvoiceList(String vat_hash, String del_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("vat_hash", vat_hash);
        params.addQueryStringParameter("del_id", del_id);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, invoice_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------------", "发票列表  " + responseInfo.result.toString());
                        handler.sendMessage(handler.obtainMessage(
                                invoice_list_id, JsonParse.getInvoiceListInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler.obtainMessage(invoice_list_err));
                    }
                });
    }

    /**
     * 新增发票信息
     *
     * @param invoice_type
     * @param inv_title
     * @param inv_content
     * @param inv_company
     * @param inv_code
     * @param inv_reg_addr
     * @param inv_reg_phone
     * @param inv_reg_bname
     * @param inv_reg_baccount
     * @param inv_rec_name
     * @param inv_rec_mobphone
     * @param area_info
     * @param inv_goto_addr
     */
    public void NewInvoice(String invoice_type, String inv_title, String inv_content, String
            inv_company
            , String inv_code, String inv_reg_addr, String inv_reg_phone, String
                                   inv_reg_bname, String inv_reg_baccount
            , String inv_rec_name, String inv_rec_mobphone, String area_info, String
                                   inv_goto_addr) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("invoice_type", invoice_type);
        params.addBodyParameter("inv_title", inv_title);
        params.addBodyParameter("inv_content", inv_content);
        params.addBodyParameter("inv_company", inv_company);
        params.addBodyParameter("inv_code", inv_code);
        params.addBodyParameter("inv_reg_addr", inv_reg_addr);
        params.addBodyParameter("inv_reg_phone", inv_reg_phone);
        params.addBodyParameter("inv_reg_bname", inv_reg_bname);
        params.addBodyParameter("inv_reg_baccount", inv_reg_baccount);
        params.addBodyParameter("inv_rec_name", inv_rec_name);
        params.addBodyParameter("inv_rec_mobphone", inv_rec_mobphone);
        params.addBodyParameter("area_info", area_info);
        params.addBodyParameter("inv_goto_addr", inv_goto_addr);
        httpUtils.send(HttpMethod.POST, new_invoice, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("------------------", "新增发票信息  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                new_invoice_id, JsonParse.getNewInvoice(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-------------------", "新增发票信息错误  " + msg);
                        handler.sendMessage(handler.obtainMessage(new_invoice_err));
                    }
                });
    }

    /**
     * 虚拟订单
     *
     * @param cart_id
     * @param quantity
     */
    public void VirtualConfirmOrderStep1(String cart_id, String quantity) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("cart_id", cart_id);
        params.addBodyParameter("quantity", quantity);
        httpUtils.send(HttpMethod.POST, Mark.virtualconfirmorder_step1, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        int maxLogSize = 4000;
                        String veryLongString =
                                responseInfo.result.toString();
                        for (int i = 0; i <= veryLongString.length()
                                / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > veryLongString.length() ? veryLongString
                                    .length() : end;
                            System.out.println("------------------虚拟订单     "
                                    + veryLongString.substring(start, end));
                        }
                        handler.sendMessage(handler.obtainMessage(
                                virtualconfirmorder_step1_id,
                                JsonParse
                                        .getVirtualConfirmOrderStep1Info(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(virtualconfirmorder_step1_err));
                    }
                });
    }

    /**
     * 虚拟订单第三步
     *
     * @param goods_id
     * @param quantity
     * @param buyer_phone
     * @param pd_pay
     * @param password
     */
    public void VirtualConfirmOrderStep3(String goods_id, String quantity, String
            buyer_phone, String pd_pay, String password, String rcb_pay) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("quantity", quantity);
        params.addBodyParameter("buyer_phone", buyer_phone);
        params.addBodyParameter("pd_pay", pd_pay);
        params.addBodyParameter("password", password);
        params.addBodyParameter("rcb_pay", rcb_pay);
        httpUtils.send(HttpMethod.POST, Mark.virtualconfirmorder_step3, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "虚拟订单第三步 " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                virtualconfirmorder_step3_id,
                                JsonParse
                                        .getVirtualConfirmOrderStep3(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(virtualconfirmorder_step3_err));
                    }
                });
    }


    public void order_del2(String order_del) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("pay_sn", order_del);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, orderdetails2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------", "虚拟订单  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                orderdetails2_id,
                                JsonParse.getOrderdetails2(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        handler.sendMessage(handler
                                .obtainMessage(orderdetails2_err));
                    }
                });
    }

    /**
     * 虚拟订单预支付
     */
    public void vrPay(String pay_sn, String payment_code) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pay_sn", pay_sn);
        params.addQueryStringParameter("payment_code", payment_code);
        System.out.println("-------------------虚拟订单预支付  "
                + zfb_pay2 + "&key=" + State.UserKey
                + "&pay_sn=" + pay_sn + "&payment_code=" + payment_code);
        httpUtils.send(HttpMethod.GET, zfb_pay2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                zfb_pay_id,
                                JsonParse.getCode(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(zfb_pay_err));
                    }
                });
    }

    /**
     * 活动首页
     */
    public void GettogetherHome() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        System.out.println("-------------------活动首页  " + gettogether_home);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, gettogether_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                gettogether_home_id,
                                JsonParse.getGettogetherInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(gettogether_home_err));
                    }
                });
    }

    /**
     * 发现首页
     */
    public void FindHome() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        System.out.println("-------------------发现首页  " + find_home);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, find_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                find_home_id,
                                JsonParse.getFindHomeInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(find_home_err));
                    }
                });
    }

    /**
     * 群組首页
     */
    public void GroupHome(String keyword, String orderType, String pagesize, String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("keyword", keyword);
        params.addQueryStringParameter("orderType", orderType);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        System.out.println("-------------------群組首页  " + group_home + "&key=" + State.UserKey
                + "&keyword=" + keyword + "&orderType=" + orderType + "&pagesize=" + pagesize + "&curpage=" + curpage);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, group_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                group_home_id,
                                JsonParse.getPostGroupInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(group_home_err));
                    }
                });
    }

    /**
     * 朋友圈首页
     */
    public void CircleoffriendsHome(String pagesize, String curpage, String circle_type, String
            keyword) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("circle_type", circle_type);
        params.addQueryStringParameter("keyword", keyword);
        System.out.println("-------------------朋友圈首页  " + circleoffriends_home + "&key=" + State.UserKey
                + "&pagesize=" + pagesize + "&curpage=" + curpage + "&circle_type=" + circle_type + "&keyword=" + keyword);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, circleoffriends_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                circleoffriends_home_id,
                                JsonParse.getCircleoffriendsInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(circleoffriends_home_err));
                    }
                });
    }

    /**
     * 帖子首页
     *
     * @param orderType
     */
    public void ForumHome(String orderType) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("orderType", orderType);
        System.out.println("-------------------帖子首页  " + forum_home + "&key=" + State.UserKey
                + "&orderType=" + orderType);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, forum_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                forum_home_id,
                                JsonParse.getForumInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(forum_home_err));
                    }
                });
    }

    /**
     * 帖子板块首页
     *
     * @param class_id
     */
    public void ForumSectionHome(String class_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("class_id", class_id);
        System.out.println("-------------------帖子板块首页  " + forumsection_home + "&key=" + State.UserKey
                + "&class_id=" + class_id);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, forumsection_home, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                forumsection_home_id,
                                JsonParse.getForumSectionInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(forumsection_home_err));
                    }
                });
    }

    /**
     * 关注操作
     *
     * @param class_id
     */
    public void LikeClass(String class_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("class_id", class_id);
        System.out.println("-------------------关注操作  " + like_class + "&key=" + State.UserKey
                + "&class_id=" + class_id);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, like_class, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                like_class_id,
                                JsonParse.getLikeClass(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(like_class_err));
                    }
                });
    }

    /**
     * 活动列表
     *
     * @param class_id
     */
    public void CircleoffriendsList(String class_id, String last_time, String keyword, String
            pagesize, String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("class_id", class_id);
        params.addQueryStringParameter("last_time", last_time);
        params.addQueryStringParameter("keyword", keyword);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, circleoffriends_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                circleoffriends_list_id,
                                JsonParse.getGettogetherListInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(circleoffriends_list_err));
                    }
                });
    }

    /**
     * 发布活动
     *
     * @param title
     * @param class_id
     * @param start_time
     * @param end_time
     * @param price
     * @param address
     * @param content
     * @param thumb
     */
    public void CreateGettogether(String title, String class_id, String start_time, String
            end_time
            , String price, String address, String content, File thumb) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("title", title);
        params.addBodyParameter("class_id", class_id);
        params.addBodyParameter("start_time", start_time);
        params.addBodyParameter("end_time", end_time);
        params.addBodyParameter("price", price);
        params.addBodyParameter("address", address);
        params.addBodyParameter("content", content);
        params.addBodyParameter("key", State.UserKey);
        if (thumb != null && thumb.exists()) {
            params.addBodyParameter("thumb", thumb);
        }
        httpUtils.send(HttpMethod.POST, create_gettogether, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("-------------", "发布活动  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                create_gettogether_id,
                                JsonParse.getCreateGettogether(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("-------------", "发布活动错误  " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(create_gettogether_err));
                    }
                });
    }

    /**
     * 活动分类
     */
    public void GettogetherClassify() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, gettogether_classify, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                gettogether_classify_id,
                                JsonParse.getGettogetherClassifyInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(gettogether_classify_err));
                    }
                });
    }

    /**
     * 加入群组
     *
     * @param circle_id
     * @param c_desc
     */
    public void JoinGroup(String circle_id, String c_desc) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("circle_id", circle_id);
        params.addBodyParameter("c_desc", c_desc);
        Log.i("---------------", "key=" + State.UserKey + "  circle_id=" + circle_id + "  c_desc=" + c_desc);
        httpUtils.send(HttpMethod.POST, join_group, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "加入群组 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                join_group_id,
                                JsonParse.getCreateGettogether(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(join_group_err));
                    }
                });
    }


    /**
     * 群组详情
     */
    public void GroupDatails(String id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("id", id);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, group_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "群组详情 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                group_datails_id,
                                JsonParse.getGroupDatailsInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(group_datails_err));
                    }
                });
    }

    /**
     * 创建群组
     *
     * @param circle_name
     * @param c_desc
     * @param c_tag
     * @param c_pursuereason
     * @param circle_back_img
     */
    public void CreateGroup(String circle_name, String c_desc, String c_tag, String
            c_pursuereason, String circle_back_img) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("circle_name", circle_name);
        params.addBodyParameter("c_desc", c_desc);
        params.addBodyParameter("c_tag", c_tag);
        params.addBodyParameter("c_pursuereason", c_pursuereason);
        if (circle_back_img != null) {
            params.addBodyParameter("circle_back_img", circle_back_img);
        }
        httpUtils.send(HttpMethod.POST, create_group, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "创建群组 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                create_group_id,
                                JsonParse.getCreateGettogether(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(create_group_err));
                    }
                });
    }

    /**
     * 创建群组背景
     */
    public void CreateGroupbg() {
        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, create_groupbg,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                create_groupbg_id,
                                JsonParse.getCreateGroupbgInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(create_groupbg_err));
                    }
                });
    }

    /**
     * 搜索类型
     */
    public void SearchClassify() {
        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, search_classify,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                search_classify_id,
                                JsonParse.getSearchInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(search_classify_err));
                    }
                });
    }

    /**
     * 帖子分类
     */
    public void ForumClassify() {
        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, forum_classify,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                forum_classify_id,
                                JsonParse.getForumClassifyInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(forum_classify_err));
                    }
                });
    }

    /**
     * 帖子列表
     *
     * @param keyword
     * @param pagesize
     * @param curpage
     * @param orderType
     */
    public void ForumList(String keyword, String pagesize, String curpage, String orderType) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("keyword", keyword);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("orderType", orderType);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, forum_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "帖子列表 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                forum_list_id,
                                JsonParse.getForumListInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(forum_list_err));
                    }
                });
    }

    /**
     * 发布帖子
     *
     * @param article_title
     * @param article_class
     * @param article_image_upload
     * @param article_content
     */
    public void ReleasePost(String article_title, String article_class, File
            article_image_upload, String article_content) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("article_title", article_title);
        params.addBodyParameter("article_class", article_class);
        if (article_image_upload != null && article_image_upload.exists()) {
            params.addBodyParameter("article_image_upload", article_image_upload);
        }
        params.addBodyParameter("article_content", article_content);
        httpUtils.send(HttpMethod.POST, release_post, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "发布帖子 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                release_post_id,
                                JsonParse.getCreateGettogether(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(release_post_err));
                    }
                });
    }

    /**
     * 动态评论
     *
     * @param theme_id
     * @param reply_content
     */
    public void DynamicComment(String theme_id, String reply_content) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("theme_id", theme_id);
        params.addQueryStringParameter("reply_content", reply_content);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, dynamic_comment, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "帖子列表 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                dynamic_comment_id,
                                JsonParse.getCreateGettogether(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(dynamic_comment_err));
                    }
                });
    }

    /**
     * 发朋友圈
     *
     * @param theme_content
     */
    public void ReleaseCircleoffriends(String theme_content, List<File> data, String
            address, String privacy) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("type", "app");
        params.addBodyParameter("address", address);
        params.addBodyParameter("privacy", privacy);
        params.addBodyParameter("theme_content", theme_content);
        if (data.size() > 0) {
            params.addBodyParameter("image_upload1", data.get(0));
        }
        if (data.size() > 1) {
            params.addBodyParameter("image_upload2", data.get(1));
        }
        if (data.size() > 2) {
            params.addBodyParameter("image_upload3", data.get(2));
        }
        if (data.size() > 3) {
            params.addBodyParameter("image_upload4", data.get(3));
        }
        if (data.size() > 4) {
            params.addBodyParameter("image_upload5", data.get(4));
        }
        if (data.size() > 5) {
            params.addBodyParameter("image_upload6", data.get(5));
        }
        if (data.size() > 6) {
            params.addBodyParameter("image_upload7", data.get(6));
        }
        if (data.size() > 7) {
            params.addBodyParameter("image_upload8", data.get(7));
        }
        if (data.size() > 8) {
            params.addBodyParameter("image_upload9", data.get(8));
        }
        httpUtils.send(HttpMethod.POST, release_circleoffriends, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "发朋友圈 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                release_circleoffriends_id,
                                JsonParse.getCreateGettogether(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(release_circleoffriends_err));
                    }
                });
    }

    /**
     * 朋友圈详情
     *
     * @param theme_id
     */
    public void DynamicDatails(String theme_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("theme_id", theme_id);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, dynamic_datails, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "朋友圈详情 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                dynamic_datails_id,
                                JsonParse.getCircleoffriendsDatilsInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(dynamic_datails_err));
                    }
                });
    }

    /**
     * 朋友圈点赞列表
     *
     * @param theme_id
     */
    public void DynamicPraiseList(String theme_id, String pagesize, String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("theme_id", theme_id);
        params.addQueryStringParameter("pagesize", pagesize);
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, dynamic_praiselist, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "朋友圈点赞列表 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                dynamic_praiselist_id,
                                JsonParse.getPraiseListInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(dynamic_praiselist_err));
                    }
                });
    }

    /**
     * 加好友
     *
     * @param member_id
     */
    public void AddFriend(String member_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("member_id", member_id);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, add_friend, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "加好友 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                add_friend_id,
                                JsonParse.getCreateGettogether(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(add_friend_err));
                    }
                });
    }


    /**
     * 发现板块——会员中心
     */
    public void FindUser() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, find_user, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "会员中心 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                find_user_id,
                                JsonParse.getMeInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(find_user_err));
                    }
                });
    }

    /**
     * 我的活动
     *
     * @param curpage
     */
    public void MyActivity(String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("curpage", curpage);
        params.addQueryStringParameter("pagesize", "20");
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, my_activity, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "我的活动 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                my_activity_id,
                                JsonParse.getMyActivityInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(my_activity_err));
                    }
                });
    }

    /**
     * 我的群组
     */
    public void MyGroup() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, my_group, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "我的群组 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                my_group_id,
                                JsonParse.getMyGroupInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(my_group_err));
                    }
                });
    }

    /**
     * 朋友圈点赞
     *
     * @param theme_id
     */
    public void PraiseCircleoffriends(String theme_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("theme_id", theme_id);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, praise_circleoffriends, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "朋友圈点赞 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                praise_circleoffriends_id,
                                JsonParse.getCreateGettogether(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(praise_circleoffriends_err));
                    }
                });
    }

    /**
     * 获取Im用户帐号签名
     *
     * @param member_id
     */
    public void getIMUserSig(String member_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("member_id", member_id);
        Log.i("-------------", "获取Im用户帐号签名 member_id=" + member_id);
        httpUtils.send(HttpMethod.POST, get_imusersig, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "获取Im用户帐号签名 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                get_imusersig_id,
                                JsonParse.getIMUserSigInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "获取Im用户帐号签名错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(get_imusersig_err));
                    }
                });
    }

    /**
     * 商品评价
     *
     * @param goods_id
     * @param type
     * @param curpage
     */
    public void GoodsEvaluateList(String goods_id, String type, String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("key", State.UserKey);
        params.addQueryStringParameter("goods_id", goods_id);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, goodsevaluate_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "商品评价 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                goodsevaluate_list_id,
                                JsonParse.getGoodsEvaluateInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(goodsevaluate_list_err));
                    }
                });
    }

    /**
     * 订单确认
     *
     * @param order_id
     */
    public void ImOrderAffirm(String order_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        httpUtils.send(HttpMethod.POST, imorder_affirm, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "订单确认 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                imorder_affirm_id,
                                JsonParse.getImOrderAffirm(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "订单确认错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(imorder_affirm_err));
                    }
                });
    }

    /**
     * 订单修改地址
     *
     * @param order_id
     */
    public void ImOrderAddress(String order_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        httpUtils.send(HttpMethod.POST, imorder_address, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "订单修改地址 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                imorder_address_id,
                                JsonParse.getImOrderAffirm(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "订单修改地址错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(imorder_address_err));
                    }
                });
    }

    /**
     * 订单修改地址提交
     *
     * @param order_id
     */
    public void ImOrderAddress2(String order_id, String address_id) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("address_id", address_id);
        httpUtils.send(HttpMethod.POST, imorder_address2, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "订单修改地址提交 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                imorder_address2_id,
                                JsonParse.getImOrderAffirm(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "订单修改地址提交错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(imorder_address2_err));
                    }
                });
    }

    /**
     * 直播列表
     */
    public void LiveBroadcastList(String curpage) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addQueryStringParameter("curpage", curpage);
        httpUtils.configCurrentHttpCacheExpiry(500);// 缓存
        httpUtils.send(HttpMethod.GET, livebroadcast_list, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("------------------", "直播列表 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                livebroadcast_list_id,
                                JsonParse.getLiveBroadcastListInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("------------------", "直播失败 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(livebroadcast_list_err));
                    }
                });
    }

    /**
     * 开始直播
     *
     * @param title
     * @param cover
     */
    public void StartPush(String title, File cover, String room_class, String
            room_label, String area_info) {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("title", title);
        params.addBodyParameter("room_class", room_class);
        params.addBodyParameter("room_label", room_label);
        params.addBodyParameter("city_info", area_info);
        if (cover != null && cover.exists()) {
            params.addBodyParameter("cover", cover);
        }
        Log.i("--------------", "key=" + State.UserKey + " title=" + title + " room_class=" + room_class
                + " room_label=" + room_label + " city_info=" + area_info);
        httpUtils.send(HttpMethod.POST, start_push, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "开始直播 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                start_push_id,
                                JsonParse.getStartPush(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "开始直播错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(start_push_err));
                    }
                });
    }

    /**
     * 直播分类
     */
    public void RoomType() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        httpUtils.send(HttpMethod.POST, room_type, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "直播分类 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                room_type_id,
                                JsonParse.getClassificationInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "直播分类错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(room_type_err));
                    }
                });
    }

    /**
     * 是否通过认证
     */
    public void isCertification() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, is_ertification, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "是否通过认证 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                is_ertification_id,
                                JsonParse.getisCertification(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "是否通过认证错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(is_ertification_err));
                    }
                });
    }

    /**
     * 房间标签
     */
    public void RoomTag() {
        httpUtils = new HttpUtils();
        params = new RequestParams();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpMethod.POST, room_tag, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Log.i("----------------", "房间标签 " + arg0.result);
                        handler.sendMessage(handler.obtainMessage(
                                room_tag_id,
                                JsonParse.getRoomTagInfo(arg0.result)));
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.i("----------------", "房间标签错误 " + arg1);
                        handler.sendMessage(handler
                                .obtainMessage(room_tag_err));
                    }
                });
    }

    /**
     * 地址列表
     */
    public void getAddressList(String area_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", Mark.State.UserKey);
        params.addBodyParameter("area_id", area_id);
        httpUtils.send(HttpRequest.HttpMethod.POST, sregion2_list, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "地址  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                address_list_id,
                                JsonParse.getAreaList(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        handler.sendMessage(handler
                                .obtainMessage(address_list_err));
                    }
                });
    }

    /**
     * 直播房间
     *
     * @param room_id
     */
    public void LiveRoomDatails(String room_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("room_id", room_id);
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpRequest.HttpMethod.POST, liveroom_datails, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "直播房间  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                liveroom_datails_id,
                                JsonParse.getLiveRoomDatailsInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        handler.sendMessage(handler
                                .obtainMessage(liveroom_datails_err));
                    }
                });
    }

    /**
     * 直播关注
     *
     * @param room_id
     */
    public void FollowRoom(String room_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("room_id", room_id);
        Log.i("----------------", "直播关注  room_id=" + room_id);
        httpUtils.send(HttpRequest.HttpMethod.POST, follow_room, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "直播关注  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                follow_room_id,
                                JsonParse.getisCertification(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        handler.sendMessage(handler
                                .obtainMessage(follow_room_err));
                    }
                });
    }

    /**
     * 直播礼物列表
     */
    public void GiftList() {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        httpUtils.send(HttpRequest.HttpMethod.POST, gift_list, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "直播礼物列表  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                gift_list_id,
                                JsonParse.getGiftListInfo(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("----------------", "直播礼物列表错误  " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(gift_list_err));
                    }
                });
    }

    /**
     * 直播礼物赠送
     */
    public void GivingGift(String gift_id, String room_id) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", State.UserKey);
        params.addBodyParameter("gift_id", gift_id);
        params.addBodyParameter("room_id", room_id);
        httpUtils.send(HttpRequest.HttpMethod.POST, giving_gift, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("----------------", "直播礼物赠送  " + responseInfo.result);
                        handler.sendMessage(handler.obtainMessage(
                                giving_gift_id,
                                JsonParse.getisCertification(responseInfo.result)));
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("----------------", "直播礼物赠送错误  " + msg);
                        handler.sendMessage(handler
                                .obtainMessage(giving_gift_err));
                    }
                });
    }

    /**
     * 在线充值
     */
    public void onLineTopUp(String amount) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", Mark.State.UserKey);
        params.addBodyParameter("pdr_amount", amount);
        httpUtils.send(HttpRequest.HttpMethod.POST, online_top_up, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        handler.sendMessage(handler
                                .obtainMessage(online_top_up_err));
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        handler.sendMessage(handler.obtainMessage(
                                online_top_up_id,
                                JsonParse.getisCertification(arg0.result)));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        handler.sendMessage(handler
                                .obtainMessage(my_social_start));
                    }
                });
    }

    /**
     * 分类 -品牌
     */
    public void onBrandtypeChoice(RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", Mark.State.UserKey);
        params.addBodyParameter("keyword", "");

//        params.addBodyParameter("pdr_amount", amount);
        httpUtils.send(HttpRequest.HttpMethod.POST, band_type_choice, params, call
        );
    }

    /**
     * 周边 -城市
     */
    public void onAroundLocationChoiceActivity(RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", Mark.State.UserKey);

//        params.addBodyParameter("pdr_amount", amount);
        httpUtils.send(HttpRequest.HttpMethod.POST, AROUND_CITY_URL, params, call
        );
    }

    /**
     * 周边-分类-分类
     */
    public void onNearTypeChoice(RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", Mark.State.UserKey);

//        params.addBodyParameter("pdr_amount", amount);
        httpUtils.send(HttpRequest.HttpMethod.POST, NEAR_CHOICE_TYPE_URL, params, call
        );
    }

    /**
     * 周边-全城-
     */
    public void onNearAreaChoice(String area_name, RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", Mark.State.UserKey);
        params.addQueryStringParameter("area_name", area_name);


//        params.addBodyParameter("pdr_amount", amount);
        httpUtils.send(HttpRequest.HttpMethod.GET, NEAR_CHOICE_Area_URL, params, call
        );
    }

    /**
     * 周边-广告-
     */
    public void onNearAdvertisement(RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", Mark.State.UserKey);


//        params.addBodyParameter("pdr_amount", amount);
        httpUtils.send(HttpRequest.HttpMethod.GET, NEAR_Advertisement_URL, params, call
        );
    }

    public void onnearAround(String points, String pages, String city_id, String area_id, String class_id, String sort_type, String sort_dis_type, RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", Mark.State.UserKey);
        params.addQueryStringParameter("points", points);
        params.addQueryStringParameter("city_id", city_id);
        params.addQueryStringParameter("area_id", area_id);
        params.addQueryStringParameter("class_id", class_id);
        params.addQueryStringParameter("sort_type", sort_type);
        params.addQueryStringParameter("curpage", String.valueOf(pages));
        params.addQueryStringParameter("sort_dis_type", sort_dis_type);
        httpUtils.send(HttpMethod.GET, NEAR_SHOP_LIST_URL, params, call
        );
    }

    public void onAround(String type, int lowtop, String localction, int pages, RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        LogUtils.d("" + pages);
        params.addQueryStringParameter("key", Mark.State.UserKey);
        params.addQueryStringParameter("sort_cate", type);
        params.addQueryStringParameter("sort_type", String.valueOf(lowtop));
        params.addQueryStringParameter("curpage", String.valueOf(pages));
        params.addQueryStringParameter("points", String.valueOf(localction));
        httpUtils.send(HttpMethod.GET, shop_type_choice, params, call
        );
    }

    public void OnGuseeLove(RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", Mark.State.UserKey);
        httpUtils.send(HttpRequest.HttpMethod.POST, GUSSESLOVEURL, params, call
        );
    }
    /**
     *
     * 开屏页
     * @param call
     */
    public void OnWelcome(RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", Mark.State.UserKey);
        httpUtils.send(HttpRequest.HttpMethod.GET, WELCOME_IMAGE_URL, params, call
        );
    }
    /**
     *
     * 安装起始页
     * @param call
     */
    public void OnFirstWelcome(RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addBodyParameter("key", Mark.State.UserKey);
        httpUtils.send(HttpRequest.HttpMethod.POST, WELCOME_ONE_IMAGE_URL, params, call
        );
    }

    public void onShoptypeChoice(String type, int lowtop, int pages, RequestCallBack<String> call) {
        params = new RequestParams();
        httpUtils = new HttpUtils();
        params.addQueryStringParameter("key", Mark.State.UserKey);
        httpUtils.send(HttpRequest.HttpMethod.GET, shop_type_choice, params, call
        );
    }

}
