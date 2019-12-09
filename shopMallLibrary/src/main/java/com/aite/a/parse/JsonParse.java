package com.aite.a.parse;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.aite.a.activity.li.bean.ShopMsgBean;
import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.base.Mark;
import com.aite.a.model.*;
import com.aite.a.model.BuySteOneInfo.AddressInfo2;
import com.aite.a.model.BuySteOneInfo.StoreCartlList;
import com.aite.a.model.BuySteOneInfo.StoreCartlList.GoodsList2;
import com.aite.a.model.GoodsDetailsInfo.Spec;
import com.aite.a.model.GoodsDetailsInfo.Spec.GoodsAttr;
import com.aite.a.model.GoodsDetailsInfo.Spec.GoodsSpec;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecImage;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecList;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecValues;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecValues.SpecValue;
import com.aite.a.model.HotelChooseAddressInfo.citylist;
import com.aite.a.model.NearbyList.NearbyStoreList;
import com.aite.a.model.OpenStoreList.GcList;
import com.aite.a.model.OpenStoreList.GradeList;
import com.aite.a.model.OrderGroupList.OrderList;
import com.aite.a.utils.BeanConvertor;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.lingshi;
import com.aiteshangcheng.a.R;
import com.community.adapter.CircleoffriendsInfo;
import com.community.model.CreateGroupbgInfo;
import com.community.model.FindHomeInfo;
import com.community.model.ForumClassifyInfo;
import com.community.model.ForumInfo;
import com.community.model.ForumListInfo;
import com.community.model.ForumSectionInfo;
import com.community.model.GettogetherClassifyInfo;
import com.community.model.GettogetherInfo;
import com.community.model.GroupDatailsInfo;
import com.community.model.MeInfo;
import com.community.model.MyActivityInfo;
import com.community.model.MyGroupInfo;
import com.community.model.PostGroupInfo;
import com.community.model.PraiseListInfo;
import com.community.model.SearchInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chat.model.CustomMsgInfo;
import chat.model.IMUserSigInfo;
import courier.model.AllIncomeInfo;
import courier.model.BatchOperationInfo;
import courier.model.ConsumeLogInfo;
import courier.model.CourierHomeInfo;
import courier.model.CourierMeInfo;
import courier.model.CourierstorageInfo;
import courier.model.CustomerListInfo;
import courier.model.DataStatisticsInfo;
import courier.model.DeliveryCourierInfo;
import courier.model.DeliveryOrderInfo;
import courier.model.DeliveryTallyInfo;
import courier.model.EditCostInfo;
import courier.model.MyStoreDataInfo;
import courier.model.NoticeInfo;
import courier.model.RecordInfo;
import courier.model.SendTypeInfo;
import courier.model.SmsModerInfo;
import courier.model.StorageLogInfo;
import courier.model.TopUpLogInfo;
import livestream.mode.AreaList;
import livestream.mode.ClassificationInfo;
import livestream.mode.GiftListInfo;
import livestream.mode.LiveBroadcastListInfo;
import livestream.mode.LiveRoomDatailsInfo;
import livestream.mode.RoomTagInfo;

public class JsonParse {
    /**
     * 获取商品列表
     *
     * @param curpage
     */
    public static List<GoodList> getGoodsList(String result, String curpage) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject object2 = object.getJSONObject("datas");
            String gc_name = object2.getString("gc_name");
            if (object.getInt("page_total") < Integer.valueOf(curpage)) {
                if (object.getString("hasmore").equals("false"))
                    return null;
            }
            lingshi.getInstance().setGc_name(gc_name);
            JSONArray array = object2.getJSONArray("goods_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<GoodList>>() {
            }.getType();
            return gson.fromJson(array.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取商品列表2
    public static Object getGoodsList2(String result, String curpage) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject object2 = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GoodList2>() {
            }.getType();
            return gson.fromJson(object2.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 自定义首页解析
     *
     * @param string
     * @return
     */
    public static List<CustomHomeInfo> getCustomHome(String string) {
        List<CustomHomeInfo> data = new ArrayList<CustomHomeInfo>();
        try {
            JSONObject object = new JSONObject(string);
            JSONArray datas = object.getJSONArray("datas");
            Gson gson = new Gson();
            for (int i = 0; i < datas.length(); i++) {
                CustomHomeInfo custom = new CustomHomeInfo();// 板块
                JSONObject jsonObject = datas.getJSONObject(i);
                Iterator keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String keyname = (String) keys.next();
                    JSONObject jsonObject2 = jsonObject.getJSONObject(keyname);// 数组对象
                    custom.type = keyname;
                    switch (keyname) {
                        case "adv_list":// 顶部轮播广告
                            java.lang.reflect.Type typeadv_list = new TypeToken<CustomHomeInfo.adv_list>() {
                            }.getType();
                            custom.adv_list = gson.fromJson(jsonObject2.toString(), typeadv_list);
                            break;
                        case "navigation":// 导航
                            java.lang.reflect.Type typenavigation = new TypeToken<CustomHomeInfo.navigation>() {
                            }.getType();
                            custom.navigation = gson.fromJson(jsonObject2.toString(), typenavigation);
                            break;
                        case "home1":// 单个广告
                            java.lang.reflect.Type type1 = new TypeToken<CustomHomeInfo.home1>() {
                            }.getType();
                            custom.home1 = gson.fromJson(jsonObject2.toString(), type1);
                            break;
                        case "home2":// 左一大右二小广告
                            java.lang.reflect.Type type2 = new TypeToken<CustomHomeInfo.home2>() {
                            }.getType();
                            custom.home2 = gson.fromJson(jsonObject2.toString(), type2);
                            break;
                        case "home3":// 一排两个列表广告
                            java.lang.reflect.Type type3 = new TypeToken<CustomHomeInfo.home3>() {
                            }.getType();
                            custom.home3 = gson.fromJson(jsonObject2.toString(), type3);
                            break;
                        case "home4":// 左二小右一大广告
                            java.lang.reflect.Type type4 = new TypeToken<CustomHomeInfo.home4>() {
                            }.getType();
                            custom.home4 = gson.fromJson(jsonObject2.toString(), type4);
                            break;
                        case "home5":// 一排三个广告
                            java.lang.reflect.Type type = new TypeToken<CustomHomeInfo.home5>() {
                            }.getType();
                            custom.home5 = gson.fromJson(jsonObject2.toString(), type);
                            break;
                        case "home6":// 一排四个广告
                            java.lang.reflect.Type type6 = new TypeToken<CustomHomeInfo.home5>() {
                            }.getType();
                            custom.home6 = gson.fromJson(jsonObject2.toString(), type6);
                            break;
                        case "goods":// 一排两个商品列表
                            java.lang.reflect.Type type7 = new TypeToken<CustomHomeInfo.goods>() {
                            }.getType();
                            custom.goods = gson.fromJson(jsonObject2.toString(), type7);
                            break;
                    }
                }
                data.add(custom);
            }
        } catch (Exception e) {
            System.out.println("-----------------首页解析错误  " + e.getMessage());
        }
        return data;
    }

    /**
     * 获取首页广告栏
     */
    public static Map<String, Object> getIndexVp(String string) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Gson gson = new Gson();
            JSONObject object = new JSONObject(string);
            JSONObject result = object.getJSONObject("datas");
            Iterator keys = result.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                // JSONObject result = object.getJSONObject(key);
                if (key.equals("wap_logo")) {
                    continue;
                }
                JSONObject obj = result.getJSONObject(key);
                if (obj.isNull("adv_list") == false) {
                    String adv_list = obj.getString("adv_list");
                    JSONObject o = new JSONObject(adv_list);
                    JSONArray item = o.getJSONArray("item");
                    java.lang.reflect.Type type = new TypeToken<List<AdColumnInfo>>() {
                    }.getType();
                    // 顶部广告
                    map.put("1", gson.fromJson(item.toString(), type));
                }
                if (obj.isNull("goods") == false) {
                    String goods = obj.getString("goods");
                    JSONObject obj2 = new JSONObject(goods);
                    JSONArray item2 = obj2.getJSONArray("item");
                    java.lang.reflect.Type type = new TypeToken<List<GoodList>>() {
                    }.getType();
                    // 掌上秒杀数据
                    map.put("2", gson.fromJson(item2.toString(), type));
                }
                if (obj.isNull("special_ad_list") == false) {
                    List<SpecialAdList> adLists = new ArrayList<SpecialAdList>();
                    try {
                        JSONArray ad_list = obj.getJSONArray("special_ad_list");
                        for (int j = 0; j < ad_list.length(); j++) {
                            JSONObject object2 = ad_list.getJSONObject(j);
                            if (!object2.equals("null")) {
                                java.lang.reflect.Type type = new TypeToken<SpecialAdList>() {
                                }.getType();
                                SpecialAdList adList = gson.fromJson(
                                        object2.toString(), type);
                                adLists.add(adList);
                            }
                        }
                    } catch (Exception e) {
                        String ad_list = obj.getString("special_ad_list");
                        JSONArray array = new JSONArray(ad_list);
                        for (int j = 0; j < array.length(); j++) {
                            String object2 = array.getString(j);
                            if (!object2.equals("null")) {
                                java.lang.reflect.Type type = new TypeToken<SpecialAdList>() {
                                }.getType();
                                SpecialAdList adList = gson.fromJson(
                                        object2.toString(), type);
                                adLists.add(adList);
                            }
                        }
                    }
                    // 今日特价数据
                    map.put("3", adLists);
                }
                if (obj.isNull("hot_ad_list") == false) {
                    List<SpecialAdList> adLists = new ArrayList<SpecialAdList>();
                    try {
                        JSONArray ad_list = obj.getJSONArray("hot_ad_list");
                        for (int j = 0; j < ad_list.length(); j++) {
                            JSONObject object2 = ad_list.getJSONObject(j);
                            if (!object2.equals("null")) {
                                java.lang.reflect.Type type = new TypeToken<SpecialAdList>() {
                                }.getType();
                                SpecialAdList adList = gson.fromJson(
                                        object2.toString(), type);
                                adLists.add(adList);
                            }
                        }
                    } catch (Exception e) {
                        String ad_list = obj.getString("hot_ad_list");
                        JSONArray array = new JSONArray(ad_list);
                        for (int j = 0; j < array.length(); j++) {
                            String object2 = array.getString(j);
                            if (!object2.equals("null")) {
                                java.lang.reflect.Type type = new TypeToken<SpecialAdList>() {
                                }.getType();
                                SpecialAdList adList = gson.fromJson(
                                        object2.toString(), type);
                                adLists.add(adList);
                            }
                        }
                    }
                    // 劲爆推荐数据
                    map.put("4", adLists);
                }
                if (obj.isNull("bottom_ad") == false) {
                    JSONArray ad_list = obj.getJSONArray("bottom_ad");
                    java.lang.reflect.Type type = new TypeToken<List<SpecialAdList>>() {
                    }.getType();
                    // 底部广告
                    if (ad_list.toString() == null
                            || ad_list.toString().equals("[null]")) {
                    } else {
                        map.put("5", gson.fromJson(ad_list.toString(), type));
                    }
                }
                // JSONArray datas = object.getJSONArray("datas");
                // for (int i = 0; i < 4; i++) {
                // JSONObject obj = result.getJSONObject(i + "");
                //
                // }

            }
            // JSONObject jsonObjectt = result.getJSONObject("1");
            // JSONObject navigation = jsonObjectt.getJSONObject("navigation");
            // java.lang.reflect.Type type = new TypeToken<MainNavigation>() {
            // }.getType();
            // if (navigation.toString() != null
            // && !navigation.toString().equals("[null]")) {
            // map.put("6", gson.fromJson(navigation.toString(), type));
            // }
            return map;
        } catch (Exception e) {
            System.out.println("-----------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取分类
     */
    public static List<CategoryList> getCategory(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject status = jsonObject.getJSONObject("datas");
            JSONArray class_list = status.getJSONArray("class_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<CategoryList>>() {
            }.getType();
            return gson.fromJson(class_list.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取商品详情
     */
    public static Object getProductDetails(String result) {
        GoodsDetailsInfo productDetails = new GoodsDetailsInfo();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GoodsDetailsInfo>() {
            }.getType();
            productDetails = gson.fromJson(datas.toString(), type);
            JSONObject goods_info = datas.getJSONObject("goods_info");
            List<SpecList> specLists = new ArrayList<SpecList>();
            try {
                JSONObject spec_list = datas.getJSONObject("spec_list");
                Iterator<?> keys = spec_list.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String string = spec_list.getString(key);
                    specLists.add(new SpecList(key, string));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<SpecImage> specImages = new ArrayList<SpecImage>();
            try {
                JSONObject spec_image = datas.getJSONObject("spec_image");
                Iterator<?> keys = spec_image.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String string = spec_image.getString(key);
                    specImages.add(new SpecImage(key, string));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<SpecValues> specValue = new ArrayList<SpecValues>();
            try {
                JSONObject spec_name = goods_info.getJSONObject("spec_name");
                Iterator<?> keys = spec_name.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String string = spec_name.getString(key);
                    specValue.add(new SpecValues(key, string, null));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONObject spec_value = goods_info.getJSONObject("spec_value");
                Iterator<?> keys = spec_value.keys();
                int i = 0;
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String string = spec_value.getString(key);
                    JSONObject object = new JSONObject(string);
                    Iterator<?> keys2 = object.keys();
                    List<SpecValue> specValues = new ArrayList<SpecValue>();
                    while (keys2.hasNext()) {
                        String key2 = (String) keys2.next();
                        specValues.add(new SpecValue(key2, object
                                .getString(key2)));
                    }
                    i++;
                    specValue.get(i - 1).spec_value = specValues;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<GoodsSpec> goodsSpecs = new ArrayList<GoodsSpec>();
            try {
                JSONObject goods_spec = goods_info.getJSONObject("goods_spec");
                Iterator<?> keys = goods_spec.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String string = goods_spec.getString(key);
                    goodsSpecs.add(new GoodsSpec(key, string));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<GoodsAttr> goodsAttrs = new ArrayList<GoodsAttr>();
            try {
                JSONObject goods_attr = goods_info.getJSONObject("goods_attr");
                Iterator<?> keys = goods_attr.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String string = goods_attr.getString(key);
                    goodsAttrs.add(new GoodsAttr(key, string));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Spec spec = new Spec(specLists, specImages, specValue, goodsSpecs,
                    goodsAttrs);
            productDetails.spec = spec;
            return productDetails;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取商品详情-推荐
     */
    public static List<GoodList> getGoodRecommend(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            JSONArray array = datas.getJSONArray("goods_commend_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<GoodList>>() {
            }.getType();
            return gson.fromJson(array.toString(), type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取商品详情-店铺信息
     */
    public static StoreInfoo getGoodStore(String result) {
        StoreInfoo storeInfo = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            JSONObject object = datas.getJSONObject("store_info");
            String store_id = object.getString("store_id");
            String store_name = object.getString("store_name");
            String member_id = object.getString("member_id");
            String member_name = object.getString("member_name");
            String avatar = object.getString("avatar");
            storeInfo = new StoreInfoo(store_id, store_name, member_id,
                    member_name, avatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return storeInfo;
    }

    /**
     * 登录
     */
    public static String login(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            String username = datas.getString("username");
            String key = datas.getString("key");
            JSONObject config = datas.getJSONObject("config");
            String member_id = config.getString("member_id");
            Mark.State.User = username;
            Mark.State.UserId = member_id;
            Mark.State.UserKey = key;
            return "1";
        } catch (JSONException e) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject datas = jsonObject.getJSONObject("datas");
                return datas.getString("error");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return "登录失败！";
    }

    /**
     * 微信登录
     */
    public static Wxlogininfo wxlogin(String result) {
        Wxlogininfo wxlogininfo = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            String expires_in = jsonObject.getString("expires_in");
            String refresh_token = jsonObject.getString("refresh_token");
            String openid = jsonObject.getString("openid");
            String scope = jsonObject.getString("scope");
            String unionid = jsonObject.getString("unionid");
            wxlogininfo = new Wxlogininfo(access_token, expires_in,
                    refresh_token, openid, scope, unionid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wxlogininfo;
    }

    /**
     * 微信登录第二步
     */
    public static Wxlogininfo wx2login(String result) {
        Wxlogininfo wxlogininfo = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            String access_token2 = jsonObject.getString("access_token");
            String expires_in2 = jsonObject.getString("expires_in");
            String refresh_token2 = jsonObject.getString("refresh_token");
            String openid2 = jsonObject.getString("openid");
            String scope2 = jsonObject.getString("scope");
            wxlogininfo = new Wxlogininfo(access_token2, expires_in2,
                    refresh_token2, openid2, scope2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wxlogininfo;
    }

    /**
     * 微信登录第三步
     *
     * @param result
     * @return
     */
    public static Wxlogininfo wx3login(String result) {
        Wxlogininfo wxlogininfo = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            String openid3 = jsonObject.getString("openid");
            String nickname3 = jsonObject.getString("nickname");
            String sex3 = jsonObject.getString("sex");
            String language3 = jsonObject.getString("language");
            String city3 = jsonObject.getString("city");
            String province3 = jsonObject.getString("province");
            String country3 = jsonObject.getString("country");
            String headimgurl3 = jsonObject.getString("headimgurl");
            String unionid3 = jsonObject.getString("unionid");
            JSONArray privilege3 = jsonObject.getJSONArray("privilege");
            wxlogininfo = new Wxlogininfo(openid3, nickname3, sex3, language3,
                    city3, province3, country3, headimgurl3, "null", unionid3);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wxlogininfo;
    }

    /**
     * 注册
     */
    public static String register(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            String username = datas.getString("username");
            String key = datas.getString("key");
            Mark.State.User = username;
            Mark.State.UserKey = key;
            return "1";
        } catch (JSONException e) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject datas = jsonObject.getJSONObject("datas");
                return datas.getString("error");
            } catch (JSONException e1) {
                e1.printStackTrace();
                return "注册失败！";
            }
        }
    }

    /**
     * 查看收货地址
     */
    public static List<AddressInfo> getAddress(String result) {
        List<AddressInfo> addressInfos = new ArrayList<AddressInfo>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            JSONArray jsonArray = datas.getJSONArray("address_list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                String address_id = jsonObject2.getString("address_id");
                String member_id = jsonObject2.getString("member_id");
                String area_id = jsonObject2.getString("area_id");
                String city_id = jsonObject2.getString("city_id");
                String dlyp_id = jsonObject2.getString("dlyp_id");
                String true_name = jsonObject2.getString("true_name");
                String area_info = jsonObject2.getString("area_info");
                String tel_phone = jsonObject2.getString("tel_phone");
                String mob_phone = jsonObject2.getString("mob_phone");
                String is_default = jsonObject2.getString("is_default");
                String address = jsonObject2.getString("address");
                AddressInfo addressInfo = new AddressInfo(address_id,
                        member_id, true_name, area_id, city_id, area_info,
                        address, tel_phone, mob_phone, is_default, dlyp_id);
                addressInfos.add(addressInfo);
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return addressInfos;
    }

    /**
     * 获取地区列表
     */
    public static List<String[]> getRegionList(String result) {
        List<String[]> strings = new ArrayList<String[]>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            JSONArray area_list = datas.getJSONArray("area_list");
            for (int i = 0; i < area_list.length(); i++) {
                JSONObject list = area_list.getJSONObject(i);
                String area_id = list.getString("area_id");
                String area_name = list.getString("area_name");
                strings.add(new String[]{area_id, area_name});
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     * 地区选择
     *
     * @param result
     * @return
     */
    public static List<ChooseAddressInfop> getRegionList2(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            JSONArray area_list = datas.getJSONArray("area_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<ChooseAddressInfop>>() {
            }.getType();
            return gson.fromJson(area_list.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "地区选择错误  " + e.getMessage());
        }
        return null;
    }

    /**
     * 购物车添加
     */
    public static void addCart(String result, Context context) {
        try {
            JSONObject object = new JSONObject(result);
            String datas = object.getString("datas");
            if (Integer.valueOf(datas) == 1) {
                CommonTools.showShortToast(context,
                        context.getString(R.string.add_success).toString());
            } else {
                JSONObject datas2 = object.getJSONObject("datas");
                String error = datas2.getString("error");
                CommonTools.showShortToast(context, error.toString());
            }
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject datas2 = object.getJSONObject("datas");
                String error = datas2.getString("error");
                CommonTools.showShortToast(context, error.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 购物车列表
     */
    public static List<CartListInfo> getCartList(String result) {
        List<CartListInfo> cartListInfos = new ArrayList<CartListInfo>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray cart_list = datas.getJSONArray("cart_list");
            for (int i = 0; i < cart_list.length(); i++) {
                JSONObject object2 = cart_list.getJSONObject(i);
                String bl_id = object2.getString("bl_id");
                String buyer_id = object2.getString("buyer_id");
                String goods_price = object2.getString("goods_price");
                String cart_id = object2.getString("cart_id");
                String goods_num = object2.getString("goods_num");
                String goods_id = object2.getString("goods_id");
                String goods_image_url = object2.getString("goods_image_url");
                String goods_name = object2.getString("goods_name");
                String store_id = object2.getString("store_id");
                String goods_sum = object2.getString("goods_sum");
                String goods_image = object2.getString("goods_image");
                String store_name = object2.getString("store_name");
                CartListInfo cartListInfo = new CartListInfo(bl_id, buyer_id,
                        goods_price, cart_id, goods_num, goods_id,
                        goods_image_url, goods_name, store_id, goods_sum,
                        goods_image, store_name);
                cartListInfos.add(cartListInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartListInfos;
    }

    public static Object getCartList2(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CartListInfo2>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 购物车
     *
     * @param result
     * @return
     */
    public static Object getCartList3(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ShoppingCartInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 购物车总金额
     */
    public static String getCartListSum(String result) {
        String cartListSum = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            cartListSum = datas.getString("sum");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartListSum;
    }

    /**
     * 购物车数量修改
     */
    public static CartListInfo CartQuantityUpdate(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            String total_price = datas.getString("total_price");
            String goods_num = datas.getString("quantity");
            String goods_price = datas.getString("goods_price");
            return new CartListInfo(goods_price, goods_num, total_price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 购物车数量修改
     */
    public static Object CartQuantityUpdate2(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error")) {
                Map<String, String> map = new HashMap<>();
                map.put("total_price", datas.getString("total_price"));
                map.put("quantity", datas.getString("quantity"));
                map.put("goods_price", datas.getString("goods_price"));
                return map;
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("error", datas.getString("error"));
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 购买第一步
     */
    public static Object buySteOne(String result) {
        LogUtils.d(result);
        Map<String, Object> map = new HashMap<String, Object>();
        AddressInfo2 addressInfo = new AddressInfo2();
        try {
            List<StoreCartlList> storeInfos = new ArrayList<StoreCartlList>();
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            // 解析订单信息
            String freight_hash = datas.getString("freight_hash");
            String ifshow_offpay = datas.getString("ifshow_offpay");
            String vat_hash = datas.getString("vat_hash");
            String is_vat_deny = datas.getString("is_vat_deny");
            String available_predeposit = datas
                    .getString("available_predeposit");
            String available_rc_balance = datas
                    .getString("available_rc_balance");
            // 解析发票
            JSONObject inv = datas.getJSONObject("inv_info");
            Object inv_info = new String[]{inv.getString("content")};
            // 解析收货地址
            try {
                JSONObject address_info = datas.getJSONObject("address_info");
                Gson gson = new Gson();
                java.lang.reflect.Type addrestype = new TypeToken<AddressInfo2>() {
                }.getType();
                addressInfo = gson
                        .fromJson(address_info.toString(), addrestype);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 解析店铺列表
            JSONObject JsonObject = datas.getJSONObject("store_cart_list");
            Iterator<?> keys = JsonObject.keys();
            while (keys.hasNext()) {
                String freight_message = null;
                String key = (String) keys.next();
                JSONObject digital = JsonObject.getJSONObject(key);
                String freight = digital.getString("freight");
                String store_mansong_rule_list = digital
                        .getString("store_mansong_rule_list");
                String store_voucher_list = digital
                        .getString("store_voucher_list");
                String store_name = digital.getString("store_name");
                String store_goods_total = digital
                        .getString("store_goods_total");

                if (Integer.valueOf(freight) == 0) {
                    freight_message = digital.getString("freight_message");
                }
                JSONArray goods_list = digital.getJSONArray("goods_list");

                Gson gson2 = new Gson();
                java.lang.reflect.Type type2 = new TypeToken<List<GoodsList2>>() {
                }.getType();

                JSONArray jsonArray = new JSONArray();
                JSONObject object3 = new JSONObject();
                new JsonArray();
                // 由于有秒杀信息的商品数据结构不同导致报错，所以此处重组JSON
                for (int i = 0; i < goods_list.length(); i++) {
                    JSONObject object2 = (JSONObject) goods_list.get(i);
                    String gc_id = object2.getString("gc_id");
                    String is_more_discount = object2
                            .getString("is_more_discount");
                    String state = object2.getString("state");
                    String goods_name = object2.getString("goods_name");
                    String is_vat = object2.getString("is_vat");
                    String goods_storage = object2.getString("goods_storage");
                    String goods_total = object2.getString("goods_total");
                    String transport_id = object2.getString("transport_id");

                    // String groupbuy_info =
                    // object2.getString("groupbuy_info");
                    String cart_id = object2.getString("cart_id");
                    String goods_price = object2.getString("goods_price");
                    String goods_commonid = object2.getString("goods_commonid");
                    String goods_costtotal = object2
                            .getString("goods_costtotal");
                    String goods_num = object2.getString("goods_num");
                    String goods_image_url = object2
                            .getString("goods_image_url");
                    String store_id = object2.getString("store_id");
                    String is_fcode = object2.getString("is_fcode");
                    String goods_image = object2.getString("goods_image");
                    String goods_vat = object2.getString("goods_vat");
                    String bl_id = object2.getString("bl_id");
                    String goods_freight = object2.getString("goods_freight");
                    String wholesale_price = object2
                            .getString("wholesale_price");

                    // String xianshi_info = object2.getString("xianshi_info");
                    String have_gift = object2.getString("have_gift");
                    String goods_storage_alarm = object2
                            .getString("goods_storage_alarm");
                    String goods_id = object2.getString("goods_id");
                    String storage_state = object2.getString("storage_state");
                    String goods_costprice = object2
                            .getString("goods_costprice");
                    object3 = new JSONObject();
                    object3.put("gc_id", gc_id);
                    object3.put("is_more_discount", is_more_discount);
                    object3.put("state", state);
                    object3.put("goods_name", goods_name);
                    object3.put("is_vat", is_vat);
                    object3.put("goods_storage", goods_storage);
                    object3.put("goods_total", goods_total);
                    object3.put("transport_id", transport_id);
                    object3.put("groupbuy_info", "null");
                    object3.put("cart_id", cart_id);
                    object3.put("goods_price", goods_price);
                    object3.put("goods_commonid", goods_commonid);
                    object3.put("goods_costtotal", goods_costtotal);
                    object3.put("goods_num", goods_num);
                    object3.put("goods_image_url", goods_image_url);
                    object3.put("store_id", store_id);
                    object3.put("is_fcode", is_fcode);
                    object3.put("goods_image", goods_image);
                    object3.put("goods_vat", goods_vat);
                    object3.put("bl_id", bl_id);
                    object3.put("goods_freight", goods_freight);
                    object3.put("wholesale_price", wholesale_price);
                    object3.put("xianshi_info", "null");
                    object3.put("have_gift", have_gift);
                    object3.put("goods_storage_alarm", goods_storage_alarm);
                    object3.put("goods_id", goods_id);
                    object3.put("storage_state", storage_state);
                    object3.put("goods_costprice", goods_costprice);
                    jsonArray.put(object3);
                }
                // List<GoodsList2> goodsList2s = gson2.fromJson(
                // goods_list.toString(), type2);
                List<GoodsList2> goodsList2s = gson2.fromJson(
                        jsonArray.toString(), type2);

                StoreCartlList storeInfo = new StoreCartlList(freight,
                        store_mansong_rule_list, store_voucher_list,
                        store_name, store_goods_total, freight_message,
                        goodsList2s);
                storeInfos.add(storeInfo);
            }
            BuySteOneInfo buySteOneInfo = new BuySteOneInfo(storeInfos,
                    addressInfo, freight_hash, ifshow_offpay, vat_hash, is_vat_deny,
                    available_predeposit, available_rc_balance, inv_info);
            map.put("1", "1");
            map.put("2", buySteOneInfo);
        } catch (Exception e) {
            Log.i("--------------", "解析错误1 " + e.getMessage());
            try {
                JSONObject object = new JSONObject(result);
                map.put("1", "0");
                map.put("2", object.getJSONObject("datas").getString("error"));
                return map;
            } catch (Exception e2) {
                Log.i("--------------", "解析错误2 " + e.getMessage());
                e2.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 购买第二步
     */
    public static String buyStepTwo(String result) {
        String pay_sn = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error") == false) {
                pay_sn = "error";
            } else {
                pay_sn = datas.getString("pay_sn");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pay_sn;
    }

    /**
     * 订单详情
     *
     * @param result
     * @return
     */
    public static Object getorderinfo(String result) {
        Orderdetails orderdetails = new Orderdetails();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("goods_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<Orderdetails>() {
            }.getType();
            orderdetails = gson.fromJson(datas.toString(), type);
            return orderdetails;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更改地址
     */
    public static Object changeAddress(String result) {
        AddressInfo addressInfo = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            boolean error = datas.isNull("error");
            if (error == true) {
                String content = datas.getString("content");
                String allow_offpay = datas.getString("allow_offpay");
                String offpay_hash = datas.getString("offpay_hash");
                String offpay_hash_batch = datas.getString("offpay_hash_batch");
                addressInfo = new AddressInfo(content, allow_offpay,
                        offpay_hash, offpay_hash_batch);
            } else {
                return datas.getString("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressInfo;

    }

    /**
     * 获取订单列表信息
     *
     * @param curpage
     */
    public static List<OrderGroupList> getOrderList(String result,
                                                    String order_state, String curpage) {
        List<OrderGroupList> group = new ArrayList<OrderGroupList>();
        Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject(result);
            if (object.getInt("page_total") < Integer.valueOf(curpage)) {
                if (object.getBoolean("hasmore") == false)
                    return null;
            }
            JSONObject datas = object.getJSONObject("datas");
            JSONArray group_list = datas.getJSONArray("order_group_list");
            for (int i = 0; i < group_list.length(); i++) {
                JSONObject obj = group_list.getJSONObject(i);
                java.lang.reflect.Type type = new TypeToken<OrderGroupList>() {
                }.getType();
                OrderGroupList orderGroupList = gson.fromJson(obj.toString(),
                        type);
                JSONArray order_list = obj.getJSONArray("order_list");
                List<OrderList> order_lists = new ArrayList<OrderList>();
                for (int j = 0; j < order_list.length(); j++) {
                    JSONObject obj2 = order_list.getJSONObject(j);
                    java.lang.reflect.Type type2 = new TypeToken<OrderList>() {
                    }.getType();
                    OrderList orderlist = gson.fromJson(obj2.toString(), type2);
                    String state = obj2.getString("order_state");
                    // if (state.equals(order_state))
                    order_lists.add(orderlist);
                }
                orderGroupList.order_list = order_lists;
                // if (order_lists.size() > 0)
                group.add(orderGroupList);
            }
            return group;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证
     */
    public static String Operation(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error") == false) {
                return datas.getString("error");
            } else {
                return "1";
            }
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                String datas = object.getString("datas");
                if (Integer.valueOf(datas) == 1) {
                    return "1";
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取商品收藏列表
     */
    public static List<FavoriteGoodsList> getFavoriteList(String result,
                                                          Context context) {
        List<FavoriteGoodsList> favoriteStroeLists = new ArrayList<FavoriteGoodsList>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray favorites_list = datas.getJSONArray("favorites_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<FavoriteGoodsList>>() {
            }.getType();
            favoriteStroeLists = gson.fromJson(favorites_list.toString(), type);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favoriteStroeLists;

    }

    /**
     * 获取店铺详情
     * (String store_avatar,
     * String store_banner,
     * String store_id,
     * String store_name,
     * String seller_name,
     * String area_info,
     * String store_label,
     * String store_zy,
     * String goods_count,
     * String store_time
     * , String store_qq,
     * String store_ww,
     * String store_phone,
     * String store_sales,
     * String store_desccredit,
     * String store_servicecredit,
     * String store_deliverycredit,
     * String goods_new_count,
     * String store_collect,
     * List<StoreCredit> credits
     */
    public static StoreInfoo getStoreDetils(String result) {
//        StoreInfoo storeInfo=new StoreInfoo();
        ShopMsgBean shopMsgBean = BeanConvertor.convertBean(result, ShopMsgBean.class);
        StoreInfoo  storeInfo = new StoreInfoo(shopMsgBean.getDatas().getStore_avatar(),
                shopMsgBean.getDatas().getStore_banner(),
                shopMsgBean.getDatas().getStore_id(),
                shopMsgBean.getDatas().getStore_name(),
               "",  "", "", "",
                "", "", "", "", "",
                "", "", "",
                "", "", shopMsgBean.getDatas().getFavorites_count(),
                null);


//        try {
//            JSONObject object = new JSONObject(result);
//            JSONObject datas = object.getJSONObject("datas");
//            JSONObject store_info = datas.getJSONObject("store_info");
////            String store_avatar = store_info.getString("store_avatar");
//            String store_avatar = shopMsgBean.getDatas().getStore_avatar();
//
////            String store_banner = store_info.getString("store_banner");
//            String store_banner = shopMsgBean.getDatas().getStore_banner();
//
////            String store_id = store_info.getString("store_id");
//            String store_id = shopMsgBean.getDatas().getStore_id();
//
////            String store_name = store_info.getString("store_name");
//            String store_name = shopMsgBean.getDatas().getStore_name();
//
//            String seller_name = store_info.getString("seller_name");
//            String area_info = store_info.getString("area_info");
//            String store_label = shopMsgBean.getDatas().getStore_label();
//            String store_zy = store_info.getString("store_zy");
//            String goods_count = store_info.getString("goods_count");
//            String store_time = store_info.getString("store_time");
//            String store_qq = store_info.getString("store_qq");
//            String store_ww = store_info.getString("store_ww");
//            String store_phone = store_info.getString("store_phone");
//            String store_sales = store_info.getString("store_sales");
//            String store_desccredit = store_info.getString("store_desccredit");
//            String store_collect = store_info.getString("store_collect");
//
//            String store_servicecredit = store_info
//                    .getString("store_servicecredit");
//            String store_deliverycredit = store_info
//                    .getString("store_deliverycredit");
//            // String goods_new_count = store_info.getString("goods_new_count");
//            String goods_new_count = "";
//            JSONObject store_credit = store_info.getJSONObject("store_credit");
//            List<StoreCredit> credits = new ArrayList<StoreCredit>();
//            Iterator<?> keys = store_credit.keys();
//            while (keys.hasNext()) {
//                String key = (String) keys.next();
//                JSONObject array = store_credit.getJSONObject(key);
//                String text = array.getString("text");
//                String credit = array.getString("credit");
//                StoreCredit storeCredit = new StoreCredit(text, credit);
//                credits.add(storeCredit);
//            }
//            storeInfo = new StoreInfoo(shopMsgBean.getDatas().getStore_avatar(), shopMsgBean.getDatas().getStore_banner(), store_id,
//                    store_name, seller_name, area_info, store_label, store_zy,
//                    goods_count, store_time, store_qq, store_ww, store_phone,
//                    store_sales, store_desccredit, store_servicecredit,
//                    store_deliverycredit, goods_new_count, store_collect,
//                    credits);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return storeInfo;
    }

    /**
     * 获取我的商城
     */
    public static User getMember(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONObject member_info = datas.getJSONObject("member_info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<User>() {
            }.getType();
            return gson.fromJson(member_info.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取物流信息
     */
    public static String[] searchSeliver(String result) {
        String[] strings = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            // JSONObject member_info = datas.getJSONObject("express_name");
            String express_name = datas.getString("express_name");
            String shipping_code = datas.getString("shipping_code");
            strings = new String[]{express_name, shipping_code};
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject datas = object.getJSONObject("datas");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return strings;

    }

    public static List<String[]> searchPay(String result, Context mContext) {
        List<String[]> strings = new ArrayList<String[]>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray payment_list = datas.getJSONArray("payment_list");
            for (int i = 0; i < payment_list.length(); i++) {
                if (payment_list.get(i).equals("wxpay")) {
                    strings.add(new String[]{"wxpay",
                            mContext.getString(R.string.wx_pay).toString()});
                }
                if (payment_list.get(i).equals("alipay")) {
                    strings.add(new String[]{
                            "alipay",
                            mContext.getString(R.string.alipay_mobile_pay)
                                    .toString()});
                }
            }
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject datas = object.getJSONObject("datas");
                CommonTools.showShortToast(mContext, datas.getString("error"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return strings;
    }

    public static String[] Pay(String result, Context mContext) {
        String[] strings = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray payment_list = datas.getJSONArray("payment_list");
            // String express_name = datas.getString("express_name");
            // String shipping_code = datas.getString("shipping_code");
            strings = new String[]{payment_list.toString()};
            if (datas.isNull("error") == false) {
                CommonTools.showShortToast(mContext, datas.getString("error"));
            }

        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject datas = object.getJSONObject("datas");
                CommonTools.showShortToast(mContext, datas.getString("error"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return strings;
    }

    public static String[] Pay(String result, Activity activity) {
        String[] strings = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error") == false) {
                CommonTools.showShortToast(activity, datas.getString("error"));
            } else {
                CommonTools.showShortToast(activity, datas.toString());
            }
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject datas = object.getJSONObject("datas");
                CommonTools.showShortToast(activity, datas.getString("error"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return strings;
    }

    public static Object getStoreCategory(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OpenStoreList>() {
            }.getType();
            OpenStoreList openStoreLists = gson
                    .fromJson(datas.toString(), type);

            JSONObject grade_list = datas.getJSONObject("grade_list");
            Iterator<?> keys = grade_list.keys();
            List<GradeList> gradeLists = new ArrayList<GradeList>();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject array = grade_list.getJSONObject(key);
                java.lang.reflect.Type grade_list_type = new TypeToken<GradeList>() {
                }.getType();
                GradeList gradeList = gson.fromJson(array.toString(),
                        grade_list_type);
                gradeLists.add(gradeList);
            }
            openStoreLists.setGrade_lists(gradeLists);
            return openStoreLists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getGcList(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray gc_list = datas.getJSONArray("gc_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<GcList>>() {
            }.getType();
            return gson.fromJson(gc_list.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object upPersonalData(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String datas = object.getString("datas");
            if (datas.equals("1")) {
                return "1";
            } else {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 虚拟订单
     *
     * @param result
     * @return
     */
    public static Object geiVrorder(String result) {
        // {"code":200,"hasmore":false,"page_total":0,"datas":{"order_list":[]}}
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray order_list = datas.getJSONArray("order_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<Vrorderinfo>>() {
            }.getType();
            return gson.fromJson(order_list.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务订单
     *
     * @param result
     * @return
     */
    public static Object geiServiceorder(String result) {
        // {"code":200,"hasmore":false,"page_total":0,"datas":{"order_list":[]}}
        try {
            Vrorderinfo vrorderinfo = new Vrorderinfo();
            JSONObject object = new JSONObject(result);

            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<List>>() {
            }.getType();
            // return gson.fromJson(order_list.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的足迹
     *
     * @param result
     * @return
     */
    public static Object getmyfootprintt(String result) {
        try {
            Myfootprintinfo myfootprint = new Myfootprintinfo();
            JSONObject object = new JSONObject(result);
            // myfootprint.setCode(object.getString("code"));
            // myfootprint.setHasmore(object.getString("hasmore"));
            // myfootprint.setPage_total(object.getString("page_total"));
            JSONObject datas = object.getJSONObject("datas");
            JSONObject list = datas.getJSONObject("list");
            JSONArray browselist = list.getJSONArray("browselist");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<Myfootprintinfo>>() {
            }.getType();
            return gson.fromJson(browselist.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清空我的足迹
     *
     * @return
     */
    public static String emptyMyfootprintt(String result) {
        String datas = null;
        try {
            JSONObject object = new JSONObject(result);
            datas = object.getString("datas");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 删除我的足迹
     *
     * @return
     */
    public static String deteleMyfootprintt(String result) {
        String datas = null;
        try {
            JSONObject object = new JSONObject(result);
            datas = object.getString("datas");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }

    /***
     * 我的评价
     *
     * @param result
     * @return
     */
    public static Object getEvaluation(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<Myevaluationinfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (JSONException e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    public static Object getPersonalData(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONObject member_info = datas.getJSONObject("member_info");
            // JSONObject user_name = member_info.getJSONObject("user_name");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<PersonalDataInfo>() {
            }.getType();
            return gson.fromJson(member_info.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object personaLopenShop(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String datas = object.getString("datas");
            if (datas.equals("1")) {
                return "1";
            } else {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getGoodsManage(String result, int curpage) {
        List<GoodsManageList> lists = new ArrayList<GoodsManageList>();
        Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject(result);
            if (object.getInt("page_total") < curpage) {
                if (object.getBoolean("hasmore") == false)
                    return lists;
            }
            JSONObject datas = object.getJSONObject("datas");
            JSONArray array = datas.getJSONArray("goods_list");
            java.lang.reflect.Type type = new TypeToken<List<GoodsManageList>>() {
            }.getType();
            return gson.fromJson(array.toString(), type);
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                // JSONObject datas = object.getJSONObject("datas");
                // JSONObject grade_list = datas.getJSONObject("goods_list");
                Iterator<?> keys = object.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (!key.equals("datas")) {
                        JSONObject array = object.getJSONObject(key);
                        java.lang.reflect.Type goodsManag_type = new TypeToken<GoodsManageList>() {
                        }.getType();
                        GoodsManageList goodsManageList = gson.fromJson(
                                array.toString(), goodsManag_type);
                        lists.add(goodsManageList);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return lists;
    }

    public static Object getMyStoreData(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error")) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<MyStoreInfo>() {
                }.getType();
                return gson.fromJson(datas.toString(), type);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getRedactData(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONObject array = datas.getJSONObject("goods");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GoodsManageList>() {
            }.getType();
            return gson.fromJson(array.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object postGoodsData(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String datas = object.getString("datas");
            if (datas.equals("1")) {
                return "1";
            } else {
                return object.getString("datas");
            }
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject datas = object.getJSONObject("datas");
                return datas.getString("error");
            } catch (Exception e2) {
            }
        }
        return null;
    }

    public static Object getSellerOrder(String result, int curpage) {
        List<SellerOrderList> list = new ArrayList<SellerOrderList>();
        try {
            JSONObject object = new JSONObject(result);
            if (object.getInt("page_total") < curpage) {
                if (object.getBoolean("hasmore") == false)
                    return list;
            }
            JSONObject datas = object.getJSONObject("datas");
            JSONObject order_list = datas.getJSONObject("order_list");
            Iterator<?> keys = order_list.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject array = order_list.getJSONObject(key);
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<SellerOrderList>() {
                }.getType();
                SellerOrderList sellerOrderList = gson.fromJson(
                        array.toString(), type);
                list.add(sellerOrderList);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Object ModifyOrder(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            String id = datas.getString("id");
            Boolean re = datas.getBoolean("result");
            if (id != null && re == true) {
                return "1";
            } else {
                return "0";
            }
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                String datas = object.getString("datas");
                if (datas.equals("1")) {
                    return "1";
                } else {
                    return "0";
                }
            } catch (Exception e2) {
                return "0";
            }
        }
    }

    public static Confirmsend confirmm(String result) {
        try {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<Confirmsend>() {
            }.getType();
            Confirmsend confirmsendinfo2 = gson.fromJson(result.toString(),
                    type);
            return confirmsendinfo2;
        } catch (Exception e) {
        }
        return null;
    }

    public static Object getSecret(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("ex_time") == false) {
                return datas.getString("ex_time");
            } else {
                return datas.getString("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static Object smaRegister(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error") == false) {
                return datas.getString("error");
            } else {
                String username = datas.getString("username");
                String password = datas.getString("password");
                return new User(username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getNearby(String result) {
//        try {
//            JSONObject object = new JSONObject(result);
//            if (object.getJSONObject("datas").isNull("error") == false) {
//                return object.getJSONObject("datas").getString("error");
//            }
//        } catch (Exception e) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObjec = object.optJSONObject("datas");
            JSONArray array = jsonObjec.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<NearbyStoreList>>() {
            }.getType();
            return gson.fromJson(array.toString(), type);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
//        }
        return new ArrayList<NearbyStoreList>();
    }

    /**
     * 获得周边城市
     *
     * @param result
     * @return
     */
    public static Object getNearbySelectCity(String result) {
        String[] zm = new String[]{"A", "B", "C", "D", "E", "F", "G", "H",
                "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X",
                "Y", "Z",};
        Map<String, Object> map = new HashMap<String, Object>();
        Gson gson = new Gson();
//        try {
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result).getJSONObject("datas");
//            JSONObject object = new JSONObject(result);
//            JSONObject jsonObject = object.getJSONObject("datas");
        for (int i = 0; i < zm.length; i++) {
            java.lang.reflect.Type type = new TypeToken<List<SelectCityinfo>>() {
            }.getType();
            if (jsonObject.getJSONArray(zm[i]) != null
                    && !jsonObject.getJSONArray(zm[i]).equals("null")
                    && !jsonObject.getJSONArray(zm[i]).equals("")) {
                map.put(zm[i], gson.fromJson(jsonObject.getJSONArray(zm[i])
                        .toString(), type));

            }
        }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
        return map;
    }

    /**
     * 获得周边区域
     *
     * @param result
     * @return
     */
    public static Object getNearbySelectArea(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            JSONArray jsonArray = jsonObject.getJSONArray("");
            java.lang.reflect.Type type = new TypeToken<List<Area_list>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 资讯首页
     *
     * @param result
     * @return
     */
    public static Object getinformationhome(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<InformationInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 资讯分类
     *
     * @param result
     * @return
     */
    public static Object getinformationclassify(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<InformationclassifyInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新闻分类
     *
     * @param result
     * @return
     */
    public static Object getnewsclassify(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONArray jsonArray = Object.getJSONArray("datas");
            java.lang.reflect.Type type = new TypeToken<List<NewsclassifyInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 广告轮播
     *
     * @param result
     * @return
     */
    public static Object getadvertisinglb(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONArray jsonArray = Object.getJSONArray("datas");
            java.lang.reflect.Type type = new TypeToken<List<AdvertisinglbInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 置顶文章
     *
     * @param result
     * @return
     */
    public static Object gettopnews(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONArray jsonArray = Object.getJSONArray("datas");
            java.lang.reflect.Type type = new TypeToken<List<AdvertisinglbInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分类文章列表
     *
     * @param result
     * @return
     */
    public static Object getNewslistifyInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<NewslistifyInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文章详情
     *
     * @param result
     * @return
     */
    public static Object getNewsdetails(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<Newsdetails>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 相关文章
     *
     * @param result
     * @return
     */
    public static Object getRelatednews(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONArray jsonArray = Object.getJSONArray("datas");
            java.lang.reflect.Type type = new TypeToken<List<Relatednewsinfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 社区
     *
     * @param result
     * @return
     */
    public static Object getcommunityall(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            java.lang.reflect.Type type = new TypeToken<Communityallinfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(Object.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 话题列表
     *
     * @param result
     * @return
     */
    public static Object getTopicslistinfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<Topicslistinfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 话题详情
     *
     * @param result
     * @return
     */
    public static Object getTopicdateilsInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<TopicdateilsInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 话题评论列表
     *
     * @param result
     * @return
     */
    public static Object getTopicscommentlistinfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            lingshi.getInstance().page_total = Object.getString("page_total");
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<Topicscommentlistinfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 达人推荐
     *
     * @param result
     * @return
     */
    public static Object getStaffPicksInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<StaffPicksInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 店铺街
     *
     * @param result
     * @return
     */
    public static Object getShopStreetInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<ShopStreetInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 推荐产品
     *
     * @param result
     * @return
     */
    public static Object getCommunityGoodsInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            JSONObject jsonObject2 = jsonObject.getJSONObject("goods_info");
            java.lang.reflect.Type type = new TypeToken<CommunityGoodsInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject2.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 推荐产品评论列表
     *
     * @param result
     * @return
     */
    public static Object getFoundcommenlist(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<Foundcommenlistinfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新闻评论列表
     *
     * @param result
     * @return
     */
    public static Object getNewsCommenlistinfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<NewsCommenlistinfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 随心看
     *
     * @param result
     * @return
     */
    public static CasuallyLook getcasually_look(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<CasuallyLook>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    public static Object wxpay(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<WxpayInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object Operation2(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error") == false)
                return datas.getString("error");
            if (datas.isNull("msg") == false)
                return datas.getString("msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object evaluateGoodList(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray datas = object.getJSONArray("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<List<GoodList>>() {
            }.getType();
            return gson.fromJson(datas.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品评价
     *
     * @param result
     * @return
     */
    public static String getrecommendedevaluation(String result) {
        String re = null;
        try {
            JSONObject object = new JSONObject(result);
            if (!object.isNull("datas")) {
                re = object.optString("datas");
            }
            if (!object.isNull("error")) {
                re = object.optString("error");
            }
            return re;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 咨询列表
     *
     * @param result
     * @return
     */
    public static Object getCustomerService(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<CustomerServiceinfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 咨询详情
     *
     * @param result
     * @return
     */
    public static Object getCustomerDetails(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray jsonArray = object.getJSONArray("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<List<CustomerDetailsif>>() {
            }.getType();
            return gson.fromJson(jsonArray.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 咨询类型
     *
     * @param result
     * @return
     */
    public static Object getConsultingTypeinfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray jsonArray = object.getJSONArray("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<List<ConsultingTypeinfo>>() {
            }.getType();
            return gson.fromJson(jsonArray.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交咨询
     *
     * @param result
     * @return
     */
    public static String SubmitConsulting(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String datas = object.getString("datas");
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分销中心
     *
     * @param result
     * @return
     */
    public static Object getDistributionCenterInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject jsonObject2 = jsonObject.getJSONObject("member_info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<DistributionCenterInfo>() {
            }.getType();
            return gson.fromJson(jsonObject2.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的推荐人
     *
     * @param result
     * @return
     */
    public static Object getMyRefereeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyRefereeInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的团队
     *
     * @param result
     * @return
     */
    public static Object getMyTeamInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyTeamInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 投诉列表
     *
     * @param result
     * @return
     */
    public static Object getComplaintslistInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ComplaintslistInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 交易投诉
     *
     * @param result
     * @return
     */
    public static Object getComplaintsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ComplaintsInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------代金券解析错误");
            e.printStackTrace();
        }
        return null;
    }

    public static Object getTopiczan(String result) {
        try {
            JSONObject object = new JSONObject(result);
            if (!object.isNull("datas")) {
                return object.getString("datas");
            } else {
                return object.getString("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 投诉详情
     *
     * @param result
     * @return
     */
    public static Object getComplaintDetailsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ComplaintDetailsInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款申请
     *
     * @param result
     * @return
     */
    public static Object getRefundInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<RefundInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 账户安全
     *
     * @param result
     * @return
     */
    public static Object getPhoneCertificationInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<PhoneCertificationInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绑定手机号码
     *
     * @param result
     * @return
     */
    public static Object BindingPhone(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject2 = jsonObject.getJSONObject("datas");
            if (!jsonObject2.isNull("msg")) {
                return jsonObject2.getString("msg");
            } else {
                return jsonObject2.getString("error");
            }
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return "获取失败";
    }

    /**
     * 绑定手机号码
     *
     * @param result
     * @return
     */
    public static Object BindingPhone2(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("datas")) {
                return jsonObject.getString("datas");
            } else {
                return jsonObject.getString("error");
            }
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return "获取失败";
    }

    /**
     * 个人资料保存
     *
     * @param result
     * @return
     */
    public static Object getPersonaldatasave(String result) {
        String re = null;
        try {
            JSONObject object = new JSONObject(result);
            if (!object.isNull("datas")) {
                re = object.getString("datas");
            } else {
                re = object.getString("error");
            }
            return re;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绑定资料
     *
     * @param result
     * @return
     */
    public static Object PayPsw2(String result) {
        try {
            List<String> list = new ArrayList<String>();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject2 = jsonObject.getJSONObject("datas");
            String string = jsonObject2.getString("member_email");
            String string2 = jsonObject2.getString("member_mobile");
            if (string2 != null && !string2.equals("null")) {
                list.add(string2);
            }
            if (string != null && !string.equals("null")) {
                list.add(string);
            }
            return list;
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实名认证
     *
     * @param result
     * @return
     */
    public static Object getIdentityInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<IdentityInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 举报列表
     *
     * @param result
     * @return
     */
    public static Object getReportListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<ReportListInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 举报详情
     *
     * @param result
     * @return
     */
    public static Object getReportDatalisInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<ReportDatalisInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品举报
     *
     * @param result
     * @return
     */
    public static Object getGoodsReportInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type2 = new TypeToken<GoodsReportInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 举报提交
     *
     * @param result
     * @return
     */
    public static Object getSubmitReport(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            return jsonObject.getString("error");
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                return object.getString("datas");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取消举报
     *
     * @param result
     * @return
     */
    public static Object getCancelReport(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            if (!jsonObject.isNull("data")) {
                return jsonObject.getString("data");
            } else {
                return jsonObject.getString("errpr");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 系统消息列表
     *
     * @param result
     * @return
     */
    public static Object getStationList(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<StationLetterInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送站内消息
     *
     * @param result
     * @return
     */
    public static Object getSendWebmessage(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            return datas.getString("errpr");
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                return object.getString("datas");
            } catch (Exception e1) {
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会员预存款明细
     *
     * @param result
     * @return
     */
    public static Object getMyMoneyInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyMoneyInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会员积分明细
     *
     * @param result
     * @return
     */
    public static Object getMyIntegralInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyIntegralInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会员积分明细
     *
     * @param result
     * @return
     */
    public static Object getIntegralTypeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray points_type = datas.getJSONArray("points_type");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<IntegralTypeInfo>>() {
            }.getType();
            return gson.fromJson(points_type.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分规则
     *
     * @param result
     * @return
     */
    public static Object getIntegralRulesInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject jsonObject2 = jsonObject.getJSONObject("info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IntegralRulesInfo>() {
            }.getType();
            return gson.fromJson(jsonObject2.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 客户列表
     *
     * @param result
     * @return
     */
    public static Object getCustomerData(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<CustomerDataInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取地区列表
     */
    public static List<AreaInfo> getRegionList1(String result) {
        List<AreaInfo> areaInfos = new ArrayList<AreaInfo>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            JSONArray area_list = datas.getJSONArray("area_list");
            for (int i = 0; i < area_list.length(); i++) {
                JSONObject list = area_list.getJSONObject(i);
                AreaInfo info = new AreaInfo();
                info.area_id = list.getString("area_id");
                info.area_name = list.getString("area_name");
                areaInfos.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return areaInfos;
    }

    /**
     * 添加客户
     *
     * @param result
     * @return
     */
    public static Object addCustomerData2(String result) {
        try {
            JSONObject object = new JSONObject(result);
            try {
                JSONObject jsonObject = object.getJSONObject("datas");
                String string = jsonObject.getString("error");
                return string;
            } catch (Exception e) {
                String datas = object.getString("datas");
                return datas.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店首页
     *
     * @param result
     * @return
     */
    public static Object getHotelHomeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HotelHomeInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店地址
     *
     * @param result
     * @return
     */
    public static Object getHotelChooseAddressInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject2 = object.getJSONObject("datas");
            List<HotelChooseAddressInfo> data = new ArrayList<HotelChooseAddressInfo>();
            // 热门
            JSONArray jsonArray = jsonObject2.getJSONArray("hot_arr");
            HotelChooseAddressInfo hotelChooseAddressInfo3 = new HotelChooseAddressInfo();
            hotelChooseAddressInfo3.citylist = new ArrayList<citylist>();
            // 标题
            hotelChooseAddressInfo3.name = "热门城市";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                citylist citylist = new citylist(
                        jsonObject3.getString("id"),
                        jsonObject3.getString("name"), "", "");
                hotelChooseAddressInfo3.citylist.add(citylist);
            }
            data.add(hotelChooseAddressInfo3);
            // 首字母
            JSONObject jsonObject = jsonObject2.getJSONObject("scity");
            Iterator keys2 = jsonObject.keys();
            HotelChooseAddressInfo hotelChooseAddressInfo2 = new HotelChooseAddressInfo();
            hotelChooseAddressInfo2.citylist = new ArrayList<citylist>();
            // 标题
            hotelChooseAddressInfo2.name = "拼音搜索";
            while (keys2.hasNext()) {
                String key = (String) keys2.next();
                citylist citylist = new citylist(
                        "", key, "", "");
                hotelChooseAddressInfo2.citylist.add(citylist);
            }
            data.add(hotelChooseAddressInfo2);

            JSONObject areas = jsonObject2.getJSONObject("areas");
            Iterator keys = areas.keys();
            // 遍历城市
            while (keys.hasNext()) {
                String key = (String) keys.next();
                // 标题
                HotelChooseAddressInfo hotelChooseAddressInfo = new HotelChooseAddressInfo();
                hotelChooseAddressInfo.name = key;
                // 城市
                hotelChooseAddressInfo.citylist = new ArrayList<citylist>();
                JSONArray jsonArray3 = areas.getJSONArray(key);
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject jsonObjec = jsonArray3.getJSONObject(i);
                    citylist citylist = new citylist(
                            jsonObjec.getString("area_id"),
                            jsonObjec.getString("area_name"),
                            jsonObjec.getString("area_parent_id"),
                            jsonObjec.getString("a_letter"));
                    hotelChooseAddressInfo.citylist.add(citylist);
                }
                data.add(hotelChooseAddressInfo);
            }

            return data;
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店列表
     *
     * @param result
     * @return
     */
    public static Object getHotelListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HotelListInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 富豪榜
     *
     * @param result
     * @return
     */
    public static Object getRichInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<RichInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的红包
     *
     * @param result
     * @return
     */
    public static Object getMyRedPackageInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyRedPackageInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 红包活动列表
     *
     * @param result
     * @return
     */
    public static Object getRedPackageActivityListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<RedPackageActivityListInfo>>() {
            }.getType();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 红包活动详情
     *
     * @param result
     * @return
     */
    public static Object getRedPackageDatailsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject jsonObject2 = jsonObject.getJSONObject("info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<RedPackageDatailsInfo>() {
            }.getType();
            return gson.fromJson(jsonObject2.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 开红包操作
     *
     * @param result
     * @return
     */
    public static Object getOpenRedPackage(String result) {
        try {
            List<String> list = new ArrayList<String>();
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            if (jsonObject.isNull("ok")) {
                list.add(jsonObject.getString("error"));
                return list;
            } else {
                list.add(jsonObject.getString("ok"));
                list.add(jsonObject.getString("msg"));
                return list;
            }
        } catch (Exception e) {
            System.out.println("-------------解析错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 充值
     *
     * @param result
     * @return
     */
    public static Object gettopupInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            String pay_sn = jsonObject.getString("pay_sn");
            return pay_sn;
        } catch (Exception e) {
            System.out.println("-------------充值解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 充值明细
     *
     * @param result
     * @return
     */
    public static Object getTopUpInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<TopUpInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------充值详情解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 充值卡余额
     *
     * @param result
     * @return
     */
    public static Object getRechargeableCardInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<RechargeableCardInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 话题列表
     *
     * @param result
     * @return
     */
    public static Object getTopicList(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<TopicListInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会员中心
     *
     * @param result
     * @return
     */
    public static Object memall(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONObject member_info = datas.getJSONObject("member_info");
            String store_id = member_info.getString("store_id");
            return store_id;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片空间
     *
     * @param result
     * @return
     */
    public static Object getImageSpaceInfo(String result) {
        Gson gson = new Gson();
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            java.lang.reflect.Type type3 = new TypeToken<ImageSpaceInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品分类
     *
     * @param result
     * @return
     */
    public static Object getGoodsClassInfo(String result) {
        Gson gson = new Gson();
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<GoodsClassInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品所在地
     *
     * @param result
     * @return
     */
    public static Object getGoodsAddressInfo(String result) {
        Gson gson = new Gson();
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<GoodsAddressInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            Log.i("----------------", "所在地错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 品牌信息
     *
     * @param result
     * @return
     */
    public static Object getBrandDatailsInfo(String result) {
        Gson gson = new Gson();
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<BrandDatailsInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发布商品
     *
     * @param result
     * @return
     */
    public static Object getFBGoodsInfo(String result) {
        Gson gson = new Gson();
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<FBGoodsInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑商品
     *
     * @param result
     * @return
     */
    public static Object getEditorGoodsInfo(String result) {
        Gson gson = new Gson();
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<EditorGoodsInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 运费模板
     *
     * @param result
     * @return
     */
    public static Object getFreightModeInfo(String result) {
        try {
            Gson gson = new Gson();
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<FreightModeInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑商品图片(商品修改操作)
     *
     * @param result
     * @return
     */
    public static GoodssImageInfo getGoodssImageInfo(String result) {
        GoodssImageInfo goodssImageInfo = new GoodssImageInfo();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray image_array = datas.getJSONArray("image_array");
            goodssImageInfo.image_array = new ArrayList<>();
            List<GoodssImageInfo.image_array> imgarrinfo = new ArrayList<>();
            for (int i = 0; i < image_array.length(); i++) {
                GoodssImageInfo.image_array image_array1 = new GoodssImageInfo.image_array();
                image_array1.color_val = new ArrayList<>();
                JSONObject jsonObject = image_array.getJSONObject(i);
                image_array1.color_id = jsonObject.getString("color_id");
                JSONArray color_val = jsonObject.getJSONArray("color_val");
                for (int j = 0; j < color_val.length(); j++) {
                    JSONObject jsonObject1 = color_val.getJSONObject(j);
                    GoodssImageInfo.image_array.color_val color_val1 = new GoodssImageInfo.image_array.color_val();
                    color_val1.goods_image_id = jsonObject1
                            .getString("goods_image_id");
                    color_val1.goods_commonid = jsonObject1
                            .getString("goods_commonid");
                    color_val1.store_id = jsonObject1.getString("store_id");
                    color_val1.color_id = jsonObject1.getString("color_id");
                    color_val1.goods_image = jsonObject1
                            .getString("goods_image");
                    color_val1.goods_image_sort = jsonObject1
                            .getString("goods_image_sort");
                    color_val1.is_default = jsonObject1.getString("is_default");
                    color_val1.bdimg = jsonObject1.getString("bdimg");
                    image_array1.color_val.add(color_val1);
                }
                goodssImageInfo.image_array.add(image_array1);
            }
            goodssImageInfo.value_array = new ArrayList<>();
            JSONArray value_array = datas.getJSONArray("value_array");
            for (int i = 0; i < value_array.length(); i++) {
                GoodssImageInfo.value_array value_array1 = new GoodssImageInfo.value_array();
                JSONObject jsonObject = value_array.getJSONObject(i);
                value_array1.sp_value_id = jsonObject.getString("sp_value_id");
                value_array1.sp_value_name = jsonObject
                        .getString("sp_value_name");
                goodssImageInfo.value_array.add(value_array1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goodssImageInfo;
    }

    /**
     * 添加规格
     *
     * @param result
     * @return
     */
    public static Object getAddSpec(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String datas = object.getString("datas");
            return datas;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传图片
     *
     * @param result
     * @return
     */
    public static Object getUpload(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error")) {
                return datas.getString("thumb_name");
            } else {
                return "-1";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传图片2
     *
     * @param result
     * @return
     */
    public static Object getUpload2(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            String thumb_name = datas.getString("thumb_name");
            String img_path = datas.getString("img_path");
            list.add(thumb_name);
            list.add(img_path);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑提交
     *
     * @param result
     * @return
     */
    public static Object getEdit(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (!datas.isNull("success")) {
                return datas.getString("success");
            } else {
                return datas.getString("error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发布商品
     *
     * @param result
     * @return
     */
    public static Object getSaveGoods(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (!datas.isNull("success")) {
                list.add("1");
                list.add(datas.getString("success"));
                return list;
            } else {
                list.add("2");
                list.add(datas.getString("error"));
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商家中心——推荐组合（商品列表、组合商品）详情
     *
     * @param result
     * @return
     */
    public static Object GetRecommendedInfo(String result) {
        try {
            Gson gson = new Gson();
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<GetRecommendedInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加组合商品:搜索商品列表
     *
     * @param result
     * @return
     */
    public static Object GetGoodsListInfo(String result) {
        try {
            Gson gson = new Gson();
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<GoodsListInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绑定规格
     *
     * @param result
     * @return
     */
    public static Object GetParameterInfo(String result) {
        try {
            Gson gson = new Gson();
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            java.lang.reflect.Type type3 = new TypeToken<ParameterInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存推荐组合
     *
     * @param result
     * @return
     */
    public static String getSaveCombo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas1 = object.getJSONObject("datas");
            String error = datas1.getString("error");
            return error;
        } catch (JSONException e) {
            try {
                JSONObject object = new JSONObject(result);
                String datas = object.getString("datas");
                return datas;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑参数列表
     *
     * @param result
     * @return
     */
    public static Object getEditValInfo(String result) {
        try {
            Gson gson = new Gson();
            JSONObject object = new JSONObject(result);
            JSONArray datas1 = object.getJSONArray("datas");
            java.lang.reflect.Type type3 = new TypeToken<List<EditValInfo>>() {
            }.getType();
            return gson.fromJson(datas1.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 赠品
     *
     * @param result
     * @return
     */
    public static Object getGiftsInfo(String result) {
        try {
            Gson gson = new Gson();
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray goods_array = datas.getJSONArray("goods_array");
            java.lang.reflect.Type type3 = new TypeToken<List<GiftsInfo>>() {
            }.getType();
            return gson.fromJson(goods_array.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 晒单
     *
     * @param result
     * @return
     */
    public static Object getShaiDanInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<ShaiDanInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 晒单上传图片
     *
     * @param result
     * @return
     */
    public static Object getUploadImgInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<UploadImgInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 晒单提交
     *
     * @param result
     * @return
     */
    public static Object getUploadShaiDan(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            return Object.getString("datas");
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店详情
     *
     * @param result
     * @return
     */
    public static Object getHotelDatailsInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<HotelDatailsInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店评价详情
     *
     * @param result
     * @return
     */
    public static Object getHotelCommentsInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            java.lang.reflect.Type type = new TypeToken<HotelCommentsInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(Object.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在线支付
     *
     * @param result
     * @return
     */
    public static Object getOnlineTopup(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            if (!jsonObject.isNull("pay_sn")) {
                return jsonObject.getString("pay_sn");
            }
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实名认证保存
     *
     * @param result
     * @return
     */
    public static Object getYYIdentitysave(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            return Object.getJSONObject("datas").getString("succ");
//            return  Object.getJSONObject("datas").getString("state");
           /* if ( stateStr != null && "1".equals(stateStr )) {
                return  "提交成功";
            } else {
            }*/
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退货列表
     *
     * @param result
     * @return
     */
    public static Object getSalesReturnListInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            java.lang.reflect.Type type = new TypeToken<SalesReturnInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(Object.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单详情
     *
     * @param result
     * @return
     */
    public static Object getOrderinfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject order_info = jsonObject.getJSONObject("order_info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OrderInfo>() {
            }.getType();
            return gson.fromJson(order_info.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------订单详情解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 全部退款
     *
     * @param result
     * @return
     */
    public static Object getOrderRefundInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject order = jsonObject.getJSONObject("order");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OrderRefundInfo>() {
            }.getType();
            return gson.fromJson(order.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------全部退款错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单详情
     *
     * @param result
     * @return
     */
    public static Object getOrderinfo2(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OrderInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------订单详情解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单详情
     *
     * @param result
     * @return
     */
    public static Object getRefundInfo2(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject order = jsonObject.getJSONObject("order");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OrderRefundInfo>() {
            }.getType();
            return gson.fromJson(order.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------订单详情解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 部分退款
     *
     * @param result
     * @return
     */
    public static Object getOrderRefund2Info(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OrderRefund2Info>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------部分退款错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片添加
     *
     * @param result
     * @return
     */
    public static Object getImageadd(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            String string = jsonObject.getString("1");
            return string;
        } catch (Exception e) {
            System.out.println("-------------图片上传出错!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 热门代金券
     *
     * @param result
     * @return
     */
    public static Object getHotVouchersListInfo(String result) {
        // HotVouchersList2Info
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Gson gson = new Gson();
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArray2 = new JSONArray();
            List<Integer> k = new ArrayList<Integer>();
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject jsonObject2 = jsonObject.getJSONObject("store_class");
            Iterator<String> keys = jsonObject2.keys();
            while (keys.hasNext()) {
                String string = (String) keys.next();
                JSONObject jsonObject3 = jsonObject2.getJSONObject(string);
                jsonArray.put(jsonObject3);
                k.add(Integer.parseInt(string));
            }
            Collections.sort(k);
            for (int i = 0; i < k.size(); i++) {
                String string = k.get(i).toString();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(j);
                    String string2 = jsonObject3.getString("sc_id");
                    if (string.equals(string2)) {
                        jsonArray2.put(jsonObject3);
                    }
                }
            }
            java.lang.reflect.Type type1 = new TypeToken<List<HotVouchersList2Info>>() {
            }.getType();
            map.put("1", gson.fromJson(jsonArray2.toString(), type1));

            java.lang.reflect.Type type = new TypeToken<HotVouchersListInfo>() {
            }.getType();
            map.put("2", gson.fromJson(object.toString(), type));
            return map;
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 代金券兑换
     *
     * @param result
     * @return
     */
    public static Object getCreditsExchangeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            if (jsonObject.isNull("template_info")) {
                return jsonObject.getString("message");
            }
            JSONObject jsonObject2 = jsonObject.getJSONObject("template_info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CreditsExchangeInfo>() {
            }.getType();
            return gson.fromJson(jsonObject2.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 确认兑换
     *
     * @param result
     * @return
     */
    public static Object getCreditsExchangeSubmit(String result) {
        String string = null;
        try {
            JSONObject object = new JSONObject(result);
            JSONObject dataObj = object.getJSONObject("datas");
            if (dataObj != null) {
                string = dataObj.getString("error");
            } else {
//                JSONObject jsonObject = object.getJSONObject("datas");
                string = object.getString("message");
            }
            return string;
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分商城
     *
     * @param result
     * @return
     */
    public static Object getIntegralMallInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IntegralMallInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 代金券列表
     *
     * @param result
     * @return
     */
    public static Object getVouchersListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<VouchersListInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 代金券商品详情
     *
     * @param result
     * @return
     */
    public static Object getIntegralGoodsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IntegralGoodsInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加积分购物车
     *
     * @param result
     * @return
     */
    public static Object getAddIntegralGiftCart(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            if (!jsonObject.isNull("done")) {
                String string = jsonObject.getString("done");
                return string;
            } else {
                JSONObject jsonObject2 = jsonObject.getJSONObject("error");
                String string = jsonObject2.getString("msg");
                return string;
            }
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分购物车
     *
     * @param result
     * @return
     */
    public static Object getIntegralCartInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IntegralCartInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 购物车修改数量
     *
     * @param result
     * @return
     */
    public static Object getIntegralCartUpNum(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            String string = jsonObject.getString("done");
            return string;
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分第一步
     *
     * @param result
     * @return
     */
    public static Object getIntegralOneInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IntegralOneInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分购买第二步
     *
     * @param result
     * @return
     */
    public static Object getIntegralTwo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            String order_id = jsonObject.getString("order_id");
            return order_id;
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 购买第三步
     *
     * @param result
     * @return
     */
    public static Object getIntegralWinInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IntegralWinInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分记录
     *
     * @param result
     * @return
     */
    public static Object getExchangeRecordInfo(String result) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Gson gson = new Gson();
            JSONArray jsonArray2 = new JSONArray();
            JSONArray jsonArray3 = new JSONArray();
            List<Integer> px = new ArrayList<Integer>();

            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Iterator<String> keys = jsonObject.keys();
            // 遍历
            while (keys.hasNext()) {
                String string = (String) keys.next();
                JSONObject jsonObject2 = jsonObject.getJSONObject(string);
                jsonArray2.put(jsonObject2);
                px.add(Integer.parseInt(string));
            }
            // 排序
            Collections.sort(px);
            for (int i = 0; i < px.size(); i++) {
                String string = px.get(i).toString();
                for (int j = 0; j < jsonArray2.length(); j++) {
                    JSONObject jsonObject2 = jsonArray2.getJSONObject(j);
                    String string2 = jsonObject2.getString("point_orderid");
                    if (string.equals(string2)) {
                        jsonArray3.put(jsonObject2);
                    }
                }
            }
            java.lang.reflect.Type type = new TypeToken<List<ExchangeRecordInfo>>() {
            }.getType();
            map.put("1", gson.fromJson(jsonArray3.toString(), type));
            map.put("2", object.getString("hasmore"));
            map.put("3", object.getString("page_total"));
            return map;
        } catch (Exception e) {
            System.out.println("-------------解析错误    ");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取消积分订单
     *
     * @param result
     * @return
     */
    public static Object getCancelExchange(String result) {
        try {
            JSONObject object = new JSONObject(result);
            if (!object.isNull("datas")) {
                return object.getString("datas");
            } else {
                return "取消失败";
            }
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分订单详情
     *
     * @param result
     * @return
     */
    public static Object getIntegarlOrderInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IntegarlOrderInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 积分商品列表
     *
     * @param result
     * @return
     */
    public static Object getHotIntegralGiftListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HotIntegralGiftListInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 登录验证
     *
     * @param result
     * @return
     */
    public static Object getLoginValidation(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject2 = jsonObject.getJSONObject("datas");
            return jsonObject2.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店购买列表
     *
     * @param result
     * @return
     */
    public static Object getHotelPayDatailsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HotelPayDatailsInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店订单提交
     *
     * @param result
     * @return
     */
    public static Object getHotelSubmitOrder(String result) {
        try {
            Map map = new HashMap<String, String>();
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            map.put("state", "1");
            map.put("con", jsonObject.getString("error"));
            return map;
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                Map map = new HashMap<String, String>();
                map.put("state", "2");
                map.put("con", object.getString("datas"));
                return map;
            } catch (Exception e2) {
            }
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店订单列表
     *
     * @param result
     * @return
     */
    public static Object getHotelOrderListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HotelOrderListInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店订单详情
     *
     * @param result
     * @return
     */
    public static Object getHotelOrderDatailsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HotelOrderDatailsInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务首页
     *
     * @param result
     * @return
     */
    public static Object getservicehome(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<ServiceHomeInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务大厅
     *
     * @param result
     * @return
     */
    public static Object getServicehall(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONArray jsonArray = Object.getJSONArray("datas");
            java.lang.reflect.Type type = new TypeToken<List<ServicehallInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务详情
     *
     * @param result
     * @return
     */
    public static Object getServicedetails(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<Servicedetailsinfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务商详情
     *
     * @param result
     * @return
     */
    public static Object getStoredetails(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<StoreHomePageInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务商商品列表
     *
     * @param result
     * @return
     */
    public static Object getStoreHomePageList(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            java.lang.reflect.Type type = new TypeToken<StoreHomePageList>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(Object.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 服务商评价
     *
     * @param result
     * @return
     */
    public static Object getStoreHomePagePJInfo(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            java.lang.reflect.Type type = new TypeToken<StoreHomePagePJInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(Object.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发布分类
     *
     * @param result
     * @return
     */
    public static Object getReleaseTask(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONArray jsonArray = Object.getJSONArray("datas");
            java.lang.reflect.Type type = new TypeToken<List<ReleaseTaskInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 人才大厅分类
     *
     * @param result
     * @return
     */
    public static Object getServiceDemand(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            JSONArray jsonArray = jsonObject.getJSONArray("goodsclass");
            java.lang.reflect.Type type = new TypeToken<List<ReleaseTaskInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片上传
     *
     * @param result
     * @return
     */
    public static Object getImageUpload(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            ReleaseFileInfo releaseFileInfo = new ReleaseFileInfo(
                    jsonObject.getString("eid"), jsonObject.getString("files"),
                    jsonObject.getString("file_sn"));
            return releaseFileInfo;
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发布需求
     *
     * @param result
     * @return
     */
    public static String getReleaseTask2(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            String string = Object.getString("datas");
            return string;
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 人才大厅列表
     *
     * @param result
     * @return
     */
    public static Object getServiceDemandlist(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<TalenthallInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 找回密码获取验证码
     *
     * @param result
     * @return
     */
    public static Object geRegistration(String result) {
        try {
            JSONObject object = new JSONObject(result);
            if (!object.isNull("datas")) {
                return object.getString("datas");
            } else {
                return object.getString("error");
            }
        } catch (Exception e) {
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取验证
     *
     * @param result
     * @return
     */
    public static Object getCode(String result) {
        Map map = new HashMap<String, String>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            map.put("0", "0");
            map.put("1", jsonObject.getString("error"));
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(result);
                map.put("0", "1");
                map.put("1", object.getString("datas"));
            } catch (Exception e2) {
            }
            System.out.println("-------------解析错误");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 支付宝
     *
     * @param result
     * @return
     */
    public static Object getZfbGetConfig(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            java.lang.reflect.Type type = new TypeToken<ZfbGetConfigInfo>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付列表
     *
     * @param result
     * @return
     */
    public static Object getPaymentList(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONArray datas = Object.getJSONArray("datas");
            java.lang.reflect.Type type = new TypeToken<List<PayListInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(datas.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退货列表
     *
     * @param result
     * @return
     */
    public static Object getGetBundling(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject jsonObject = Object.getJSONObject("datas");
            JSONArray jsonArray = jsonObject.getJSONArray("b_list");
            java.lang.reflect.Type type = new TypeToken<List<PreferentialSuitInfo>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (JSONException e) {
            System.out.println("-----------  错误" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 推荐组合
     *
     * @param result
     * @return
     */
    public static Object getAssemblageGoods(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<RecommendCombinationInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------推荐组合  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签到
     *
     * @param result
     * @return
     */
    public static Object getMemberSign(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            if (Object.getString("status").equals("1")) {
                return "1";
            } else {
                JSONObject datas = Object.getJSONObject("datas");
                return datas.getString("error");
            }
        } catch (JSONException e) {
            System.out.println("-----------  错误 ");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签到
     *
     * @param result
     * @return
     */
    public static Object getOpenPost(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            if (Object.isNull("error")) {
                return Object.getString("datas");
            } else {
                return Object.getString("error");
            }
        } catch (JSONException e) {
            System.out.println("-----------  错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 短信模板
     *
     * @param result
     * @return
     */
    public static Object getSmsModerInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONArray list = jsonObject.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<SmsModerInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------短信模板  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 站点信息
     *
     * @param result
     * @return
     */
    public static Object getMyStoreDataInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject info = jsonObject.getJSONObject("info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyStoreDataInfo>() {
            }.getType();
            return gson.fromJson(info.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------短信模板  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 未入库列表
     *
     * @param result
     * @return
     */
    public static Object getCourierstorageInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CourierstorageInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------未入库列表  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通知列表
     *
     * @param result
     * @return
     */
    public static Object getNoticeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<NoticeInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------通知列表错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 可添加快递公司列表
     *
     * @param result
     * @return
     */
    public static Object getRecordInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<RecordInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------可添加快递公司列表错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 本店快递公司列表
     *
     * @param result
     * @return
     */
    public static Object getDeliveryCourierInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<DeliveryCourierInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------可添加快递公司列表错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 记录列表
     *
     * @param result
     * @return
     */
    public static Object getDeliveryTallyInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<DeliveryTallyInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------可添加快递公司列表错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取定位地址
     *
     * @param result
     * @return
     */
    public static Object getgpsInfoToAppInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<gpsInfoToAppInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------可添加快递公司列表错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取二级分类
     *
     * @param result
     * @return
     */
    public static Object getAmClassify2Info(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<AmClassify2Info>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------推荐组合  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据统计
     *
     * @param result
     * @return
     */
    public static Object getDataStatisticsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject list = jsonObject.getJSONObject("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<DataStatisticsInfo>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------数据统计  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 总收入
     *
     * @param result
     * @return
     */
    public static Object getAllIncomeInfoo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject list = jsonObject.getJSONObject("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<AllIncomeInfo>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------数据统计  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的驿站
     *
     * @param result
     * @return
     */
    public static Object getCourierMeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            JSONObject list = jsonObject.getJSONObject("info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CourierMeInfo>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------数据统计  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 驿站首页
     *
     * @param result
     * @return
     */
    public static Object getCourierHomeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject jsonObject = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CourierHomeInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------驿站首页  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品列表
     *
     * @param result
     * @return
     */
    public static Object getGoodsListModerInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GoodsListModerInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------商品列表  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 客户列表
     *
     * @param result
     * @return
     */
    public static Object getCustomerListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<CustomerListInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------客户列表  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 消费记录
     *
     * @param result
     * @return
     */
    public static Object getTopupLogInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ConsumeLogInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------消费记录  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 入库记录
     *
     * @param result
     * @return
     */
    public static Object getStorageLogInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<StorageLogInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------消费记录  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 充值记录
     *
     * @param result
     * @return
     */
    public static Object getTopUpLogInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<TopUpLogInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------消费记录  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 短信发送列表
     *
     * @param result
     * @return
     */
    public static Object getSendTypeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<SendTypeInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------短信发送列表  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 投递费用列表
     *
     * @param result
     * @return
     */
    public static Object getEditCostInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<EditCostInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------投递费用列表  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 站点订单信息
     *
     * @param result
     * @return
     */
    public static Object getDeliveryOrderInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONObject info = datas.getJSONObject("info");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<DeliveryOrderInfo>() {
            }.getType();
            return gson.fromJson(info.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------站点订单信息  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 编辑
     *
     * @param result
     * @return
     */
    public static Object getEditCost(String result) {
        try {
            JSONObject Object = new JSONObject(result);
            JSONObject datas = Object.getJSONObject("datas");
            return datas.getString("error");
        } catch (JSONException e) {
            try {
                JSONObject Object = new JSONObject(result);
                return Object.getString("datas");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取入库编号
     *
     * @param result
     * @return
     */
    public static Object getExpressNo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            return datas.getString("info");
        } catch (Exception e) {
            System.out.println("-------------获取入库编号  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 批量入库编号
     *
     * @param result
     * @return
     */
    public static Object getBatchOperationInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<BatchOperationInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------批量入库编号  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 授权
     */
    public static Object getH5Token(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<H5Token>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            Log.i("--------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 签到列表
     *
     * @param result
     * @return
     */
    public static Object getSignDate(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas1 = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<SigninInfo>() {
            }.getType();
            return gson.fromJson(datas1.toString(), type);
        } catch (Exception e) {
            System.out.println("-------------错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发票列表
     */
    public static Object getInvoiceListInfo(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<InvoiceListInfo>() {
            }.getType();
            return gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            Log.i("--------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增发票
     */
    public static Object getNewInvoice(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");
            if (message.equals("添加成功")) {
                JSONObject data = jsonObject.getJSONObject("data");
                return data.getString("id");
            } else {
                return message;
            }
        } catch (JSONException e) {
            Log.i("--------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 虚拟订单购买第一步
     */
    public static Object getVirtualConfirmOrderStep1Info(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<VirtualConfirmOrderStep1Info>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (JSONException e) {
            Log.i("--------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 虚拟订单购买第三步
     */
    public static Object getVirtualConfirmOrderStep3(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject datas = jsonObject.getJSONObject("datas");
            if (!datas.isNull("error")) {
                return datas.getString("error");
            } else {
                return datas.getString("order_id");
            }
        } catch (JSONException e) {
            Log.i("--------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单详情2
     *
     * @param result
     * @return
     */
    public static Object getOrderdetails2(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<Orderdetails2>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 活动首页
     *
     * @param result
     * @return
     */
    public static Object getGettogetherInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GettogetherInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发现首页
     *
     * @param result
     * @return
     */
    public static Object getFindHomeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<FindHomeInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 群组首页
     *
     * @param result
     * @return
     */
    public static Object getPostGroupInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<PostGroupInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 群组首页
     *
     * @param result
     * @return
     */
    public static Object getCircleoffriendsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CircleoffriendsInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 帖子首页
     *
     * @param result
     * @return
     */
    public static Object getForumInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ForumInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 帖子板块首页
     *
     * @param result
     * @return
     */
    public static Object getForumSectionInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ForumSectionInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 活动列表
     *
     * @param result
     * @return
     */
    public static Object getGettogetherListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GettogetherListInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 活动分类
     *
     * @param result
     * @return
     */
    public static Object getGettogetherClassifyInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray class_list = datas.getJSONArray("class_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<GettogetherClassifyInfo>>() {
            }.getType();
            return gson.fromJson(class_list.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建群组分类
     *
     * @param result
     * @return
     */
    public static Object getCreateGroupbgInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray class_list = datas.getJSONArray("adv_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<CreateGroupbgInfo>>() {
            }.getType();
            return gson.fromJson(class_list.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 搜索类型
     *
     * @param result
     * @return
     */
    public static Object getSearchInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray class_list = datas.getJSONArray("searct_list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<SearchInfo>>() {
            }.getType();
            return gson.fromJson(class_list.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 群组详情
     *
     * @param result
     * @return
     */
    public static Object getGroupDatailsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GroupDatailsInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 帖子分类
     *
     * @param result
     * @return
     */
    public static Object getForumClassifyInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<ForumClassifyInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 帖子列表
     *
     * @param result
     * @return
     */
    public static Object getForumListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ForumListInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 朋友圈详情
     *
     * @param result
     * @return
     */
    public static Object getCircleoffriendsDatilsInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CircleoffriendsDatilsInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 朋友圈点赞列表
     *
     * @param result
     * @return
     */
    public static Object getPraiseListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<PraiseListInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发现——会员中心
     *
     * @param result
     * @return
     */
    public static Object getMeInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MeInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的活动
     *
     * @param result
     * @return
     */
    public static Object getMyActivityInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyActivityInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的群组
     *
     * @param result
     * @return
     */
    public static Object getMyGroupInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONObject list = datas.getJSONObject("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MyGroupInfo>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 帖子板块首页
     *
     * @param result
     * @return
     */
    public static Object getLikeClass(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            return datas.getString("msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发布活动
     *
     * @param result
     * @return
     */
    public static Object getCreateGettogether(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error")) {
                return datas.getString("msg");
            } else {
                return datas.getString("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * IM账户签名
     *
     * @param result
     * @return
     */
    public static Object getIMUserSigInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IMUserSigInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 申诉对话
     *
     * @param result
     * @return
     */
    public static Object getComplaintDialogueInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONArray datas = object.getJSONArray("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<ComplaintDialogueInfo>>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品评论
     *
     * @param result
     * @return
     */
    public static Object getGoodsEvaluateInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<GoodsEvaluateInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商品消息
     *
     * @param result
     * @return
     */
    public static CustomMsgInfo getCustomMsgInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CustomMsgInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 部分退款提交
     *
     * @param result
     * @return
     */
    public static Object getConfirmRefund2(String result) {
        try {
            JSONObject object = new JSONObject(result);
            return object.getString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 部分退款提交
     *
     * @param result
     * @return
     */
    public static Object getpublishComplainTalk(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String datas1 = object.getString("datas");
            if (datas1.equals("1")) {
                return datas1;
            } else {
                JSONObject datas = object.getJSONObject("datas");
                return datas.getString("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交仲裁
     *
     * @param result
     * @return
     */
    public static Object getApplyHandle(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String datas1 = object.getString("datas");
            return datas1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单确认
     *
     * @param result
     * @return
     */
    public static Object getImOrderAffirm(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("success")) {
                return datas.getString("error");
            } else {
                return datas.getString("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 开始直播
     *
     * @param result
     * @return
     */
    public static Object getStartPush(String result) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error")) {
                map.put("push_url", datas.getString("push_url"));
                map.put("room_id", datas.getString("room_id"));
            } else {
                map.put("error", datas.getString("error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 直播列表
     *
     * @param result
     * @return
     */
    public static Object getLiveBroadcastListInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<LiveBroadcastListInfo>() {
            }.getType();
            return gson.fromJson(object.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否通过认证
     *
     * @param result
     * @return
     */
    public static Object getisCertification(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            if (datas.isNull("error")) {
                return datas.getString("success");
            } else {
                return datas.getString("error");
            }
        } catch (Exception e) {
            Log.i("--------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 房间分类
     *
     * @param result
     * @return
     */
    public static Object getClassificationInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ClassificationInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 房间标签
     *
     * @param result
     * @return
     */
    public static Object getRoomTagInfo(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject datas = object.getJSONObject("datas");
            JSONArray list = datas.getJSONArray("list");
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<RoomTagInfo>>() {
            }.getType();
            return gson.fromJson(list.toString(), type);
        } catch (Exception e) {
            Log.i("-----------------", "解析错误  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 地址列表
     *
     * @param result
     * @return
     */
    public static Object getAreaList(String result) {
        Gson gson = new Gson();
        try {
            JSONArray data = new JSONArray(result);
            //分类等级
            java.lang.reflect.Type type3 = new TypeToken<List<AreaList>>() {
            }.getType();
            return gson.fromJson(data.toString(), type3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 房间列表
     *
     * @param result
     * @return
     */
    public static Object getLiveRoomDatailsInfo(String result) {
        Gson gson = new Gson();
        try {
            JSONObject result1 = new JSONObject(result);
            JSONObject datas = result1.getJSONObject("datas");
            //分类等级
            java.lang.reflect.Type type3 = new TypeToken<LiveRoomDatailsInfo>() {
            }.getType();
            return gson.fromJson(datas.toString(), type3);
        } catch (JSONException e) {
            Log.i("---------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 礼物列表
     *
     * @param result
     * @return
     */
    public static Object getGiftListInfo(String result) {
        Gson gson = new Gson();
        try {
            JSONObject result1 = new JSONObject(result);
            java.lang.reflect.Type type3 = new TypeToken<GiftListInfo>() {
            }.getType();
            return gson.fromJson(result1.toString(), type3);
        } catch (JSONException e) {
            Log.i("---------------", "错误 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
