package com.aite.a.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.BrandDatailsInfo;
import com.aite.a.model.CategoryList;
import com.aite.a.model.DiscountInfo;
import com.aite.a.model.EditorGoodsInfo;
import com.aite.a.model.FBGoodsInfo;
import com.aite.a.model.GoodsAddressInfo;
import com.aite.a.model.GoodsClassInfo;
import com.aite.a.model.ImageSpaceInfo;
import com.aite.a.model.InventoryInfo;
import com.aite.a.model.StoreClassifyInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.MyDatepopu2;
import com.aite.a.view.MyDialog;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import level3.adapter.ArrayWheelAdapter;
import level3.widget.WheelView;

/**
 * 发布商品 Created by Administrator on 2017/5/3.
 */
public class ReleaseGoodsActivity extends BaseActivity implements
        OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageView _iv_back, iv_goodsimg, iv_open, iv_open1;
    private TextView _tv_name, tv_gmenu1, tv_gmenu2, tv_gmenu3, tv_gmenu4,
            tv_gmenu5, tv_classify, tv_classifybj, tv_pagerbtn1, tv_pagerbtn2,
            tv_pagerbtn3, tv_pagerbtn4, tv_pagerbtn5, tv_pagerbtn11,
            tv_pagerbtn12, tv_pagerbtn13, tv_pagerbtn14, tv_time,
            tv_addclassify, tv_date, tv_netxt, tv_fstime, tv_mopfreight,
            tv_ypsm, tv_pp, tv_ypsm1, tv_pagerbtn15, tv_yfmode, tv_imgspace,
            tv_imgspace2, tv_addrules, ed_input15;
    private EditText ed_input1, ed_input2, ed_input3, ed_input4, ed_input5,
            ed_input6, ed_input7, ed_input8, ed_input9, ed_input10, ed_input11,
            ed_input12, ed_input13, ed_input14, ed_input16, ed_input17,
            ed_input18, ed_input19, ed_input20, ed_input21, ed_input22,
            ed_input23, ed_input24, ed_inputsource, ed_inputport,
            ed_inputgoodsstate, ed_inputordernumber;
    private RelativeLayout rl_sc, rl_xc, rl_sc1, rl_xc1, rl_fnum, rl_fqz,
            rl_fhrq, rl_gd, rl_mode, rl_fsdata, rl_pp, rl_datails, rl_gl,
            rl_fcodelist, rl_activityrules, rl_iszzsfp;
    private LinearLayout ll_album, i_border;
    private Spinner sp_album, sp_album1, sp_gltop, sp_glbtn, sp_province,
            sp_city;
    private MyGridView mgv_album, mgv_attribute, mgv_album1,
            mgv_class, mgv_info, mgv_fcode;
    private MyListView mlv_sprc, mlv_inventory;
    private RecyclerView mlv_activityrules;
    private CheckBox cb_yes, cb_no, cb_yes1, cb_no1, cb_yes2, cb_no2, cb_yes3,
            cb_no3, cb_yes4, cb_no4, cb_warehouse, cb_yes5, cb_no5, cb_yes6,
            cb_no6, cb_borderyes, cb_borderno, cb_morediscountes,
            cb_morediscountno, cb_logyes, cb_logno, cb_isfpyes, cb_isfp3;
    private ScrollView sc_scroll;
    private MyDialog myDialog, myDialog2, myDialog3;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private List<CategoryList> categoryOne;
    private View dialogbrand;
    private WheelView wheelview;
    private ImageSpaceInfo imageSpaceInfo;
    private ImgAdapter imgAdapter;
    private ImgAdapter2 imgAdapter2;
    private ImgInfoAdapter imgInfoAdapter;
    private String goodsimgclassify = "0", goodsimgclassify2 = "0";
    private int goodsimgpager = 1, album = 1, goodsimgpager2 = 1;
    private boolean isgoodsclassify = true, isrequest = false,
            isbdclassify = false;
    public GoodsClassInfo goodsClassInfo;
    private GoodsAddressInfo goodsAddressInfo;
    private FBGoodsInfo fBGoodsInfo;
    private AttributeAdapter attributeAdapter;
    public List<StoreClassifyInfo> storeclass;
    private StoreClassifyAdapter storeClassifyAdapter;
    public EditorGoodsInfo editorGoodsInfo;
    private StandardAdapter standardAdapter;
    private InventoryAdapter inventoryAdapter;
    private FCodeAdapter fCodeAdapter;
    private BrandDatailsInfo brandDatailsInfo;
    private DiscountAdapter discountAdapter;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private List<String> storeclassifysaze = new ArrayList<>();
    // 运费模板ID
    private String freight, freightname;

    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case goodsclassify_all_id:
                    // 商品分类
                    if (msg.obj != null) {
                        goodsClassInfo = (GoodsClassInfo) msg.obj;
                        if (goodsClassInfo != null
                                && goodsClassInfo.goods_class != null
                                && goodsClassInfo.goods_class.size() != 0) {
                            List<String> classify1 = new ArrayList<>();
                            List<String> classify2 = new ArrayList<>();
                            List<String> classify3 = new ArrayList<>();
                            for (int i = 0; i < goodsClassInfo.goods_class.size(); i++) {
                                GoodsClassInfo.goods_class goods_class = goodsClassInfo.goods_class
                                        .get(i);
                                classify1.add(goods_class.gc_name);
                            }
                            for (int j = 0; j < goodsClassInfo.goods_class.get(0).class2
                                    .size(); j++) {
                                GoodsClassInfo.goods_class.class2 class2 = goodsClassInfo.goods_class
                                        .get(0).class2.get(j);
                                classify2.add(class2.gc_name);
                            }
                            for (int k = 0; k < goodsClassInfo.goods_class.get(0).class2
                                    .get(0).class3.size(); k++) {
                                GoodsClassInfo.goods_class.class2.class3 class3 = goodsClassInfo.goods_class
                                        .get(0).class2.get(0).class3.get(k);
                                classify3.add(class3.gc_name);
                            }
                            if (classify2.size() == 0) {
                                classify2.add("");
                            }
                            if (classify3.size() == 0) {
                                classify3.add("");
                            }
                            chooseDialog(classify1, classify2, classify3,
                                    getString(R.string.release_goods95));
                        } else {
                            Toast.makeText(ReleaseGoodsActivity.this,
                                    getString(R.string.release_goods100),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
                case goodsclassify_all_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case image_space_id:
                    // 图片空间
                    if (msg.obj != null) {
                        imageSpaceInfo = (ImageSpaceInfo) msg.obj;
                        if (album == 1) {
                            // 商品图片

                            // 修改页码
                            tv_pagerbtn3.setText(goodsimgpager + "");
                            // 分类
                            List<String> menu = new ArrayList<>();
                            for (int i = 0; i < imageSpaceInfo.datas.class_list
                                    .size(); i++) {
                                if (i == 0) {
                                    tv_ypsm.setText(getString(R.string.release_goods99)
                                            + imageSpaceInfo.datas.class_list
                                            .get(i).aclass_name);
                                }
                                menu.add(imageSpaceInfo.datas.class_list.get(i).aclass_name);
                            }
                            SpinnerAdapter adapter = new SpinnerAdapter(
                                    ReleaseGoodsActivity.this,
                                    android.R.layout.simple_spinner_item, menu);
                            sp_album.setAdapter(adapter);
                            sp_album.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent,
                                                           View view, int position, long id) {
                                    if (isrequest) {
                                        goodsimgclassify = imageSpaceInfo.datas.class_list
                                                .get(position).aclass_id;
                                        isrequest = false;
                                        netRun.ImageSpace("1", goodsimgclassify);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                            // 图片适配
                            imgAdapter = new ImgAdapter(
                                    imageSpaceInfo.datas.pic_list);
                            mgv_album.setAdapter(imgAdapter);
                        } else if (album == 2) {
                            // 详情图片
                            // 修改页码
                            tv_pagerbtn15.setText(goodsimgpager2 + "");
                            // 分类
                            List<String> menu = new ArrayList<>();
                            for (int i = 0; i < imageSpaceInfo.datas.class_list
                                    .size(); i++) {
                                if (i == 0) {
                                    tv_ypsm1.setText(getString(R.string.release_goods99)
                                            + imageSpaceInfo.datas.class_list
                                            .get(i).aclass_name);
                                }
                                menu.add(imageSpaceInfo.datas.class_list.get(i).aclass_name);
                            }
                            SpinnerAdapter adapter = new SpinnerAdapter(
                                    ReleaseGoodsActivity.this,
                                    android.R.layout.simple_spinner_item, menu);
                            sp_album1.setAdapter(adapter);
                            sp_album1
                                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(
                                                AdapterView<?> parent, View view,
                                                int position, long id) {
                                            if (isrequest) {
                                                goodsimgclassify2 = imageSpaceInfo.datas.class_list
                                                        .get(position).aclass_id;
                                                isrequest = false;
                                                netRun.ImageSpace("1",
                                                        goodsimgclassify2);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(
                                                AdapterView<?> parent) {
                                        }
                                    });
                            // 图片适配
                            imgAdapter2 = new ImgAdapter2(
                                    imageSpaceInfo.datas.pic_list);
                            mgv_album1.setAdapter(imgAdapter2);
                        }
                    }
                    handler.sendEmptyMessageDelayed(10086, 1000);
                    break;
                case image_space_err:
                    handler.sendEmptyMessageDelayed(10086, 1000);
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 10086:
                    isrequest = true;
                    break;
                case 10087:
                    isbdclassify = true;
                    break;
                case goods_address_id:
                    // 商品所在地
                    if (msg.obj != null) {
                        goodsAddressInfo = (GoodsAddressInfo) msg.obj;
                        List<String> menu = new ArrayList<String>();
                        for (int i = 0; i < goodsAddressInfo.list.size(); i++) {
                            menu.add(goodsAddressInfo.list.get(i).area_name);
                        }
                        // 定义适配器
                        SpinnerAdapter adapter = new SpinnerAdapter(
                                ReleaseGoodsActivity.this,
                                android.R.layout.simple_spinner_item, menu);
                        sp_province.setAdapter(adapter);
                        // sp_province.getSelectedItem().toString().equals("寶貝")
                        sp_province
                                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(
                                            AdapterView<?> parent, View view,
                                            int position, long id) {
                                        sp_city.setVisibility(View.VISIBLE);
                                        List<String> menu = new ArrayList<String>();
                                        for (int i = 0; i < goodsAddressInfo.list
                                                .get(position).citylist.size(); i++) {
                                            menu.add(goodsAddressInfo.list
                                                    .get(position).citylist.get(i).area_name);
                                        }
                                        SpinnerAdapter adapter2 = new SpinnerAdapter(
                                                ReleaseGoodsActivity.this,
                                                android.R.layout.simple_spinner_item,
                                                menu);
                                        sp_city.setAdapter(adapter2);
                                    }

                                    @Override
                                    public void onNothingSelected(
                                            AdapterView<?> parent) {
                                    }
                                });
                    }
                    break;
                case goods_address_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case add_goods_id:
                    // 发布商品1
                    if (msg.obj != null) {
                        fBGoodsInfo = (FBGoodsInfo) msg.obj;
                        // 商品属性
                        attributeAdapter = new AttributeAdapter(
                                fBGoodsInfo.attr_list, null);
                        mgv_attribute.setAdapter(attributeAdapter);
                        // 关联板式
                        if (fBGoodsInfo.plate_list.size() == 0) {
                            rl_gl.setVisibility(View.GONE);
                        } else {
                            List<String> toplist = new ArrayList<>();
                            List<String> btnlist = new ArrayList<>();
                            for (int i = 0; i < fBGoodsInfo.plate_list.size(); i++) {
                                if (fBGoodsInfo.plate_list.get(i).plate_position
                                        .equals("1")) {
                                    toplist.add(fBGoodsInfo.plate_list.get(i).plate_name);
                                } else if (fBGoodsInfo.plate_list.get(i).plate_position
                                        .equals("0")) {
                                    btnlist.add(fBGoodsInfo.plate_list.get(i).plate_name);
                                }
                            }
                            SpinnerAdapter adapter = new SpinnerAdapter(
                                    ReleaseGoodsActivity.this,
                                    android.R.layout.simple_spinner_item, toplist);
                            sp_gltop.setAdapter(adapter);
                            SpinnerAdapter adapter2 = new SpinnerAdapter(
                                    ReleaseGoodsActivity.this,
                                    android.R.layout.simple_spinner_item, btnlist);
                            sp_glbtn.setAdapter(adapter2);
                        }
                        // 店铺分类
                        storeclass = new ArrayList<>();
                        for (int i = 0; i < fBGoodsInfo.store_goods_class.size(); i++) {
                            FBGoodsInfo.store_goods_class store_goods_class = fBGoodsInfo.store_goods_class
                                    .get(i);
                            String stc_name = store_goods_class.stc_name;
                            storeclass.add(new StoreClassifyInfo(true, stc_name));
                            if (store_goods_class.child != null) {
                                for (int j = 0; j < store_goods_class.child.size(); j++) {
                                    String stc_name1 = store_goods_class.child
                                            .get(j).stc_name;
                                    storeclass.add(new StoreClassifyInfo(false,
                                            stc_name1));
                                }
                            }
                        }
                        storeClassifyAdapter = new StoreClassifyAdapter();
                        mgv_class.setAdapter(storeClassifyAdapter);
                        // 规格适配
                        standardAdapter = new StandardAdapter(null,
                                fBGoodsInfo.spec_list);
                        mlv_sprc.setAdapter(standardAdapter);
                        // 库存适配
                        List<InventoryInfo> speclist = new ArrayList<>();
                        inventoryAdapter = new InventoryAdapter(speclist);
                        mlv_inventory.setAdapter(inventoryAdapter);
                        handler.sendEmptyMessageDelayed(10087, 2000);
                    }
                    break;
                case add_goods_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case get_redact_data_id:
                    // 编辑商品
                    if (msg.obj != null) {
                        editorGoodsInfo = (EditorGoodsInfo) msg.obj;
                        // 商品分类ID
                        gc_id = editorGoodsInfo.goods.gc_id;
                        String replace = editorGoodsInfo.goods.gc_name.replace(
                                "&gt;", ">");
                        // 品牌
                        netRun.BrandDatails(editorGoodsInfo.goods.type_id, gc_id);
                        // 商品分类
                        tv_classify.setText(replace);
                        // 商品名称
                        ed_input1.setText(editorGoodsInfo.goods.goods_name);
                        // 短标题
                        ed_input24.setText(editorGoodsInfo.goods.goods_short_title);
                        // 高级代理商价格
                        ed_input5.setText(editorGoodsInfo.goods.level_0_price);
                        // 铜牌代理商价格
                        ed_input6.setText(editorGoodsInfo.goods.level_1_price);
                        // 金牌代理商价格
                        ed_input7.setText(editorGoodsInfo.goods.level_2_price);
                        // 钻石代理商价格
                        ed_input8.setText(editorGoodsInfo.goods.level_3_price);
                        // 高级代理商价格
                        ed_input9.setText(editorGoodsInfo.goods.level_0_auth_price);
                        // 铜牌代理商价格
                        ed_input10
                                .setText(editorGoodsInfo.goods.level_1_auth_price);
                        // 金牌代理商价格
                        ed_input11
                                .setText(editorGoodsInfo.goods.level_2_auth_price);
                        // 钻石代理商价格
                        ed_input12
                                .setText(editorGoodsInfo.goods.level_3_auth_price);
                        // 可用积分
                        ed_input17
                                .setText(editorGoodsInfo.goods.goods_points_offset);

                        // 商品买点
                        ed_input2.setText(editorGoodsInfo.goods.goods_jingle);
                        // 商品标签
                        ed_input3.setText(editorGoodsInfo.goods.goods_tags);
                        // 商品价格
                        ed_input4.setText(editorGoodsInfo.goods.goods_price);
                        // 市场价
                        ed_input13.setText(editorGoodsInfo.goods.goods_marketprice);
                        // 成本价
                        ed_input14.setText(editorGoodsInfo.goods.goods_costprice);
                        // 折扣
                        ed_input15.setText(editorGoodsInfo.goods.goods_discount);
                        // 分配佣金
                        ed_input16.setText(editorGoodsInfo.goods.commission);
                        // 商品库存
                        ed_input18.setText(editorGoodsInfo.goods.g_storage);
                        // 库存预警
                        ed_input19
                                .setText(editorGoodsInfo.goods.goods_storage_alarm);
                        // 商品货号
                        ed_input20.setText(editorGoodsInfo.goods.goods_serial);
                        // 商品图片
                        bitmapUtils.display(iv_goodsimg,
                                editorGoodsInfo.goods.goods_fullimage);
                        // 商品图片短路径
                        imagepath = new File(editorGoodsInfo.goods.goods_image);
                        // 商品品牌
                        tv_pp.setText(editorGoodsInfo.goods.brand_name);
                        // 本店分类
                        if (editorGoodsInfo.store_class_goods.size() == 0) {
                            storeclassifysaze.add("請選擇");
                        } else {
                            for (int i = 0; i < editorGoodsInfo.store_class_goods
                                    .size(); i++) {
                                storeclassifysaze.add("請選擇");
                            }
                        }
                        // 商品规格
                        // 是否选中
                        for (int i = 0; i < editorGoodsInfo.spec_checked.size(); i++) {
                            String id = editorGoodsInfo.spec_checked.get(i).id;
                            for (int k = 0; k < editorGoodsInfo.spec_list.size(); k++) {
                                for (int j = 0; j < editorGoodsInfo.spec_list
                                        .get(k).value.size(); j++) {
                                    if (editorGoodsInfo.spec_list.get(k).value
                                            .get(j).sp_value_id.equals(id)) {
                                        editorGoodsInfo.spec_list.get(k).value
                                                .get(j).ischoose = true;
                                    }
                                }
                            }
                        }
                        // 得到选中的数组
                        List<List<EditorGoodsInfo.spec_list.value>> chooselist = new ArrayList<>();
                        for (int k = 0; k < editorGoodsInfo.spec_list.size(); k++) {
                            List<EditorGoodsInfo.spec_list.value> list2 = new ArrayList<>();
                            for (int j = 0; j < editorGoodsInfo.spec_list.get(k).value
                                    .size(); j++) {
                                if (editorGoodsInfo.spec_list.get(k).value.get(j).ischoose) {
                                    list2.add(editorGoodsInfo.spec_list.get(k).value
                                            .get(j));
                                }
                            }
                            chooselist.add(list2);
                        }
                        // 得到所有规格
                        List<InventoryInfo> speclist = new ArrayList<>();
                        List<InventoryInfo.data> li = new ArrayList<>();
                        kc2(chooselist, li, 0, speclist);

                        // 库存适配
                        inventoryAdapter = new InventoryAdapter(speclist);
                        mlv_inventory.setAdapter(inventoryAdapter);
                        // 规格适配
                        standardAdapter = new StandardAdapter(
                                editorGoodsInfo.spec_list, null);
                        mlv_sprc.setAdapter(standardAdapter);

                        // 关联板式
                        if (editorGoodsInfo.plate_list.size() == 0) {
                            rl_gl.setVisibility(View.GONE);
                        } else {
                            List<String> toplist = new ArrayList<>();
                            List<String> btnlist = new ArrayList<>();
                            for (int i = 0; i < editorGoodsInfo.plate_list.size(); i++) {
                                if (editorGoodsInfo.plate_list.get(i).plate_position
                                        .equals("1")) {
                                    toplist.add(editorGoodsInfo.plate_list.get(i).plate_name);
                                } else if (editorGoodsInfo.plate_list.get(i).plate_position
                                        .equals("0")) {
                                    btnlist.add(editorGoodsInfo.plate_list.get(i).plate_name);
                                }
                            }
                            SpinnerAdapter adapter = new SpinnerAdapter(
                                    ReleaseGoodsActivity.this,
                                    android.R.layout.simple_spinner_item, toplist);
                            sp_gltop.setAdapter(adapter);
                            String plateid_top = editorGoodsInfo.goods.plateid_top;
                            // 默认选中
                            for (int i = 0; i < editorGoodsInfo.plate_list.size(); i++) {
                                if (plateid_top.equals(editorGoodsInfo.plate_list
                                        .get(i).plate_id)) {
                                    sp_gltop.setSelection(i, true);
                                }
                            }
                            SpinnerAdapter adapter2 = new SpinnerAdapter(
                                    ReleaseGoodsActivity.this,
                                    android.R.layout.simple_spinner_item, btnlist);
                            sp_glbtn.setAdapter(adapter2);
                            String plateid_bottom = editorGoodsInfo.goods.plateid_bottom;
                            // 默认选中
                            for (int i = 0; i < editorGoodsInfo.plate_list.size(); i++) {
                                if (plateid_bottom
                                        .equals(editorGoodsInfo.plate_list.get(i).plate_id)) {
                                    sp_glbtn.setSelection(i, true);
                                }
                            }
                        }
                        // 商品属性
                        attributeAdapter = new AttributeAdapter(null,
                                editorGoodsInfo.attr_list);
                        mgv_attribute.setAdapter(attributeAdapter);
                        // 是否F码商品
                        if (editorGoodsInfo.goods.is_fcode.equals("1")) {
                            cb_yes.setChecked(true);
                            cb_no.setChecked(false);
                            rl_fnum.setVisibility(View.VISIBLE);
                            rl_fqz.setVisibility(View.VISIBLE);
                            tv_gmenu5.setVisibility(View.GONE);

                            if (_goods.equals("redact")
                                    && editorGoodsInfo.fcode_array != null
                                    && editorGoodsInfo.fcode_array.size() != 0) {
                                rl_fcodelist.setVisibility(View.VISIBLE);
                            } else {
                                rl_fcodelist.setVisibility(View.GONE);
                            }
                            // F码列表
                            fCodeAdapter = new FCodeAdapter(
                                    editorGoodsInfo.fcode_array);
                            mgv_fcode.setAdapter(fCodeAdapter);
                        } else {
                            cb_yes.setChecked(false);
                            cb_no.setChecked(true);
                            rl_fnum.setVisibility(View.GONE);
                            rl_fqz.setVisibility(View.GONE);
                            rl_fcodelist.setVisibility(View.GONE);
                            tv_gmenu5.setVisibility(View.VISIBLE);
                        }
                        // 是否预售
                        if (editorGoodsInfo.goods.is_presell.equals("1")) {
                            cb_yes1.setChecked(true);
                            cb_no1.setChecked(false);
                            rl_fhrq.setVisibility(View.VISIBLE);
                            tv_time.setText(TimeStamp2Date(
                                    editorGoodsInfo.goods.presell_deliverdate,
                                    "yyyy-MM-dd"));
                        } else {
                            cb_yes1.setChecked(false);
                            cb_no1.setChecked(true);
                            rl_fhrq.setVisibility(View.GONE);
                        }
                        // 运费模式
                        if (editorGoodsInfo.goods.transport_id.equals("0")) {
                            cb_yes2.setChecked(true);
                            cb_no2.setChecked(false);
                            ed_input23.setText(editorGoodsInfo.goods.goods_freight);
                            ed_input23.setVisibility(View.VISIBLE);
                            tv_mopfreight.setVisibility(View.VISIBLE);
                            rl_mode.setVisibility(View.GONE);
                        } else {
                            cb_yes2.setChecked(false);
                            cb_no2.setChecked(true);
                            tv_yfmode
                                    .setText(editorGoodsInfo.goods.transport_title);
                            ed_input23.setVisibility(View.GONE);
                            tv_mopfreight.setVisibility(View.GONE);
                            rl_mode.setVisibility(View.VISIBLE);
                        }
                        // 是否开增值税发票
                        if (editorGoodsInfo.goods.goods_vat.equals("0")) {
                            cb_yes3.setChecked(false);
                            cb_no3.setChecked(true);
                        } else {
                            cb_yes3.setChecked(true);
                            cb_no3.setChecked(false);
                        }

                        // 打折规则列表
                        List<DiscountInfo> discount = new ArrayList<>();
                        if (editorGoodsInfo.md_rule != null) {
                            for (int i = 0; i < editorGoodsInfo.md_rule.size(); i++) {
                                discount.add(new DiscountInfo(
                                        editorGoodsInfo.md_rule.get(i).count,
                                        editorGoodsInfo.md_rule.get(i).discount));
                            }
                        }
                        discountAdapter = new DiscountAdapter(discount);
                        mlv_activityrules
                                .setLayoutManager(new StaggeredGridLayoutManager(1,
                                        OrientationHelper.VERTICAL));
                        mlv_activityrules.setAdapter(discountAdapter);

                        // 是否多件打折
                        if (editorGoodsInfo.goods.is_more_discount.equals("0")) {
                            cb_morediscountes.setChecked(false);
                            cb_morediscountno.setChecked(true);
                            rl_activityrules.setVisibility(View.GONE);

                        } else {
                            cb_morediscountes.setChecked(true);
                            cb_morediscountno.setChecked(false);
                            rl_activityrules.setVisibility(View.VISIBLE);
                        }
                        // 是否跨境
                        if (editorGoodsInfo.goods.is_kuajing.equals("0")) {
                            cb_borderyes.setChecked(false);
                            cb_borderno.setChecked(true);
                            i_border.setVisibility(View.GONE);
                        } else {
                            cb_borderyes.setChecked(true);
                            cb_borderno.setChecked(false);
                            i_border.setVisibility(View.VISIBLE);
                            // 原产国
                            ed_inputsource
                                    .setText(editorGoodsInfo.goods.native_info == null ? ""
                                            : editorGoodsInfo.goods.native_info);
                            // 进口口岸
                            ed_inputport.setText(editorGoodsInfo.goods.seaport);
                            // 货物清关状态
                            ed_inputgoodsstate
                                    .setText(editorGoodsInfo.goods.clearance);
                            // 报关单号
                            ed_inputordernumber
                                    .setText(editorGoodsInfo.goods.clearance_sn);

                        }
                        // 有无原产标志
                        if (editorGoodsInfo.goods.is_native.equals("0")) {
                            cb_logyes.setChecked(false);
                            cb_logno.setChecked(true);
                        } else {
                            cb_logyes.setChecked(true);
                            cb_logno.setChecked(false);
                        }
                        // 是否提供发票
                        if (editorGoodsInfo.goods.is_native.equals("0")) {
                            cb_isfpyes.setChecked(false);
                            cb_isfp3.setChecked(true);
                            rl_iszzsfp.setVisibility(View.GONE);
                        } else {
                            cb_isfpyes.setChecked(true);
                            cb_isfp3.setChecked(false);
                            rl_iszzsfp.setVisibility(View.VISIBLE);
                        }

                        // 店铺分类
                        storeclass = new ArrayList<>();
                        for (int i = 0; i < editorGoodsInfo.store_goods_class
                                .size(); i++) {
                            EditorGoodsInfo.store_goods_class store_goods_class = editorGoodsInfo.store_goods_class
                                    .get(i);
                            String stc_name = store_goods_class.stc_name;
                            String stc_idd = store_goods_class.stc_id;
                            storeclass.add(new StoreClassifyInfo(true, stc_name,
                                    stc_idd));
                            if (store_goods_class.child != null) {
                                for (int j = 0; j < store_goods_class.child.size(); j++) {
                                    String stc_name1 = store_goods_class.child.get(j).stc_name;
                                    String stc_id = store_goods_class.child.get(j).stc_id;
                                    storeclass.add(new StoreClassifyInfo(false,
                                            stc_name1, stc_id));
                                }
                            }
                        }

                        storeClassifyAdapter = new StoreClassifyAdapter();
                        mgv_class.setAdapter(storeClassifyAdapter);
                        handler.sendEmptyMessageDelayed(10087, 2000);
                        // 发布时间
                        if (editorGoodsInfo.goods.goods_state.equals("1")) {
                            cb_yes4.setChecked(true);
                            cb_no4.setChecked(false);
                            cb_warehouse.setChecked(false);
                        } else if (editorGoodsInfo.goods.goods_state.equals("0")) {
                            cb_yes4.setChecked(false);
                            cb_no4.setChecked(false);
                            cb_warehouse.setChecked(true);
                            if (editorGoodsInfo.goods.goods_selltime != null
                                    && !editorGoodsInfo.goods.goods_selltime
                                    .equals("0")) {
                                tv_date.setText(TimeStamp2Date(
                                        editorGoodsInfo.goods.goods_selltime,
                                        "yyyy-MM-dd HH:mm"));
                            }

                        }
                        // 发布时间
                        if (editorGoodsInfo.goods.is_appoint.equals("1")) {
                            cb_yes5.setChecked(true);
                            cb_no5.setChecked(false);
                            rl_fsdata.setVisibility(View.VISIBLE);
                            if (editorGoodsInfo.goods.appoint_satedate != null
                                    && !editorGoodsInfo.goods.appoint_satedate
                                    .equals("0")) {
                                tv_fstime.setText(TimeStamp2Date(
                                        editorGoodsInfo.goods.appoint_satedate,
                                        "yyyy-MM-dd"));
                            }
                        } else if (editorGoodsInfo.goods.is_appoint.equals("0")) {
                            cb_yes5.setChecked(false);
                            cb_no5.setChecked(true);
                            rl_fsdata.setVisibility(View.GONE);
                        }
                        // 是否推荐商品
                        if (editorGoodsInfo.goods.goods_commend.equals("1")) {
                            cb_yes6.setChecked(true);
                            cb_no6.setChecked(false);
                        } else if (editorGoodsInfo.goods.goods_commend.equals("0")) {
                            cb_yes6.setChecked(false);
                            cb_no6.setChecked(true);
                        }
                        // 详情列表
                        imgInfoAdapter = new ImgInfoAdapter();
                        mgv_info.setAdapter(imgInfoAdapter);
                        for (int i = 0; i < editorGoodsInfo.goods.mobile_body
                                .size(); i++) {
                            ImageSpaceInfo.datas.pic_list pic_list = new ImageSpaceInfo.datas.pic_list();
                            pic_list.full_path = editorGoodsInfo.goods.mobile_body
                                    .get(i).value;
                            pic_list.apic_cover = editorGoodsInfo.goods.mobile_body
                                    .get(i).value;
                            imgInfoAdapter.additem(pic_list);
                        }

                    }
                    break;
                case get_redact_data_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case add_spec_id:
                    // 添加规格
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (isNumeric(str)) {
                            if (_goods.equals("redact")) {
                                standardAdapter
                                        .additem(
                                                new EditorGoodsInfo.spec_list.value(
                                                        str, addspecname), null,
                                                pid);
                            } else if (_goods.equals("add")) {
                                standardAdapter
                                        .additem(
                                                null,
                                                new FBGoodsInfo.spec_list.value(
                                                        str, addspecname), pid);
                            }
                        } else {
                            Toast.makeText(ReleaseGoodsActivity.this,
                                    getString(R.string.systembusy),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case add_spec_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case brand_datails_id:
                    // 品牌列表
                    if (msg.obj != null) {
                        brandDatailsInfo = (BrandDatailsInfo) msg.obj;
                    }
                    break;
                case brand_datails_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case goodsimage_upload_id:
                    // 上传图片
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("-1")) {
                            Toast.makeText(ReleaseGoodsActivity.this,
                                    getString(R.string.systembusy),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (album == 1) {
                                imagepath = new File(str);
                            } else if (album == 2) {
                                ImageSpaceInfo.datas.pic_list pic_list = new ImageSpaceInfo.datas.pic_list();
                                pic_list.full_path = tempFile.toString();
                                pic_list.apic_cover = str;
                                imgInfoAdapter.additem(pic_list);
                            }
                        }
                    }
                    break;
                case goodsimage_upload_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case editor_save_id:
                    // 编辑提交
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            Toast.makeText(ReleaseGoodsActivity.this,
                                    getString(R.string.release_goods146),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ReleaseGoodsActivity.this, str,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case editor_save_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case save_goods_id:
                    // 发布商品提交
                    if (msg.obj != null) {
                        List<String> list = (List<String>) msg.obj;
                        String s = list.get(0);
                        if (s.equals("1")) {
                            //  提交
                            Intent intent = new Intent(ReleaseGoodsActivity.this,
                                    GoodssImageActivity.class);
                            intent.putExtra("goods", "add");
                            intent.putExtra("common_id", list.get(1));
                            startActivity(intent);
                            finish();
                        } else if (s.equals("2")) {
                            Toast.makeText(ReleaseGoodsActivity.this, list.get(1),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case save_goods_err:
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_goods);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_goodsimg = (ImageView) findViewById(R.id.iv_goodsimg);
        iv_open = (ImageView) findViewById(R.id.iv_open);
        iv_open1 = (ImageView) findViewById(R.id.iv_open1);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_gmenu1 = (TextView) findViewById(R.id.tv_gmenu1);
        tv_gmenu2 = (TextView) findViewById(R.id.tv_gmenu2);
        tv_gmenu3 = (TextView) findViewById(R.id.tv_gmenu3);
        tv_gmenu4 = (TextView) findViewById(R.id.tv_gmenu4);
        tv_gmenu5 = (TextView) findViewById(R.id.tv_gmenu5);
        tv_fstime = (TextView) findViewById(R.id.tv_fstime);
        tv_classify = (TextView) findViewById(R.id.tv_classify);
        tv_classifybj = (TextView) findViewById(R.id.tv_classifybj);
        tv_pagerbtn1 = (TextView) findViewById(R.id.tv_pagerbtn1);
        tv_pagerbtn2 = (TextView) findViewById(R.id.tv_pagerbtn2);
        tv_pagerbtn3 = (TextView) findViewById(R.id.tv_pagerbtn3);
        tv_pagerbtn4 = (TextView) findViewById(R.id.tv_pagerbtn4);
        tv_pagerbtn5 = (TextView) findViewById(R.id.tv_pagerbtn5);
        tv_pagerbtn11 = (TextView) findViewById(R.id.tv_pagerbtn11);
        tv_pagerbtn12 = (TextView) findViewById(R.id.tv_pagerbtn12);
        tv_pagerbtn13 = (TextView) findViewById(R.id.tv_pagerbtn13);
        tv_pagerbtn14 = (TextView) findViewById(R.id.tv_pagerbtn14);
        tv_addclassify = (TextView) findViewById(R.id.tv_addclassify);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_netxt = (TextView) findViewById(R.id.tv_netxt);
        tv_mopfreight = (TextView) findViewById(R.id.tv_mopfreight);
        tv_ypsm = (TextView) findViewById(R.id.tv_ypsm);
        tv_pp = (TextView) findViewById(R.id.tv_pp);
        tv_ypsm1 = (TextView) findViewById(R.id.tv_ypsm1);
        tv_pagerbtn15 = (TextView) findViewById(R.id.tv_pagerbtn15);
        tv_yfmode = (TextView) findViewById(R.id.tv_yfmode);
        tv_imgspace = (TextView) findViewById(R.id.tv_imgspace);
        tv_imgspace2 = (TextView) findViewById(R.id.tv_imgspace2);
        tv_addrules = (TextView) findViewById(R.id.tv_addrules);
        ed_input1 = (EditText) findViewById(R.id.ed_input1);
        ed_input2 = (EditText) findViewById(R.id.ed_input2);
        ed_input3 = (EditText) findViewById(R.id.ed_input3);
        ed_input4 = (EditText) findViewById(R.id.ed_input4);
        ed_input5 = (EditText) findViewById(R.id.ed_input5);
        ed_input6 = (EditText) findViewById(R.id.ed_input6);
        ed_input7 = (EditText) findViewById(R.id.ed_input7);
        ed_input8 = (EditText) findViewById(R.id.ed_input8);
        ed_input9 = (EditText) findViewById(R.id.ed_input9);
        ed_input10 = (EditText) findViewById(R.id.ed_input10);
        ed_input11 = (EditText) findViewById(R.id.ed_input11);
        ed_input12 = (EditText) findViewById(R.id.ed_input12);
        ed_input13 = (EditText) findViewById(R.id.ed_input13);
        ed_input14 = (EditText) findViewById(R.id.ed_input14);
        ed_input15 = (TextView) findViewById(R.id.ed_input15);
        ed_input16 = (EditText) findViewById(R.id.ed_input16);
        ed_input17 = (EditText) findViewById(R.id.ed_input17);
        ed_input18 = (EditText) findViewById(R.id.ed_input18);
        ed_input19 = (EditText) findViewById(R.id.ed_input19);
        ed_input20 = (EditText) findViewById(R.id.ed_input20);
        ed_input21 = (EditText) findViewById(R.id.ed_input21);
        ed_input22 = (EditText) findViewById(R.id.ed_input22);
        ed_input23 = (EditText) findViewById(R.id.ed_input23);
        ed_input24 = (EditText) findViewById(R.id.ed_input24);
        ed_inputsource = (EditText) findViewById(R.id.ed_inputsource);
        ed_inputport = (EditText) findViewById(R.id.ed_inputport);
        ed_inputgoodsstate = (EditText) findViewById(R.id.ed_inputgoodsstate);
        ed_inputordernumber = (EditText) findViewById(R.id.ed_inputordernumber);
        rl_sc = (RelativeLayout) findViewById(R.id.rl_sc);
        rl_xc = (RelativeLayout) findViewById(R.id.rl_xc);
        rl_sc1 = (RelativeLayout) findViewById(R.id.rl_sc1);
        rl_xc1 = (RelativeLayout) findViewById(R.id.rl_xc1);
        rl_fnum = (RelativeLayout) findViewById(R.id.rl_fnum);
        rl_fqz = (RelativeLayout) findViewById(R.id.rl_fqz);
        rl_fhrq = (RelativeLayout) findViewById(R.id.rl_fhrq);
        rl_gd = (RelativeLayout) findViewById(R.id.rl_gd);
        rl_mode = (RelativeLayout) findViewById(R.id.rl_mode);
        rl_fsdata = (RelativeLayout) findViewById(R.id.rl_fsdata);
        rl_pp = (RelativeLayout) findViewById(R.id.rl_pp);
        rl_datails = (RelativeLayout) findViewById(R.id.rl_datails);
        rl_gl = (RelativeLayout) findViewById(R.id.rl_gl);
        rl_fcodelist = (RelativeLayout) findViewById(R.id.rl_fcodelist);
        rl_activityrules = (RelativeLayout) findViewById(R.id.rl_activityrules);
        rl_iszzsfp = (RelativeLayout) findViewById(R.id.rl_iszzsfp);
        ll_album = (LinearLayout) findViewById(R.id.ll_album);
        i_border = (LinearLayout) findViewById(R.id.i_border);
        sp_album = (Spinner) findViewById(R.id.sp_album);
        sp_album1 = (Spinner) findViewById(R.id.sp_album1);
        sp_gltop = (Spinner) findViewById(R.id.sp_gltop);
        sp_glbtn = (Spinner) findViewById(R.id.sp_glbtn);
        sp_province = (Spinner) findViewById(R.id.sp_province);
        sp_city = (Spinner) findViewById(R.id.sp_city);
        mgv_album = (MyGridView) findViewById(R.id.mgv_album);
        mgv_attribute = (MyGridView) findViewById(R.id.mgv_attribute);
        mgv_album1 = (MyGridView) findViewById(R.id.mgv_album1);
        mgv_class = (MyGridView) findViewById(R.id.mgv_class);
        mgv_info = (MyGridView) findViewById(R.id.mgv_info);
        mgv_fcode = (MyGridView) findViewById(R.id.mgv_fcode);
        mlv_sprc = (MyListView) findViewById(R.id.mlv_sprc);
        mlv_activityrules = (RecyclerView) findViewById(R.id.mlv_activityrules);
        mlv_inventory = (MyListView) findViewById(R.id.mlv_inventory);
        cb_yes = (CheckBox) findViewById(R.id.cb_yes);
        cb_no = (CheckBox) findViewById(R.id.cb_no);
        cb_yes2 = (CheckBox) findViewById(R.id.cb_yes2);
        cb_no2 = (CheckBox) findViewById(R.id.cb_no2);
        cb_yes3 = (CheckBox) findViewById(R.id.cb_yes3);
        cb_no3 = (CheckBox) findViewById(R.id.cb_no3);
        cb_yes4 = (CheckBox) findViewById(R.id.cb_yes4);
        cb_no4 = (CheckBox) findViewById(R.id.cb_no4);
        cb_warehouse = (CheckBox) findViewById(R.id.cb_warehouse);
        cb_yes5 = (CheckBox) findViewById(R.id.cb_yes5);
        cb_no5 = (CheckBox) findViewById(R.id.cb_no5);
        cb_yes6 = (CheckBox) findViewById(R.id.cb_yes6);
        cb_no6 = (CheckBox) findViewById(R.id.cb_no6);
        cb_yes1 = (CheckBox) findViewById(R.id.cb_yes1);
        cb_no1 = (CheckBox) findViewById(R.id.cb_no1);
        cb_borderyes = (CheckBox) findViewById(R.id.cb_borderyes);
        cb_borderno = (CheckBox) findViewById(R.id.cb_borderno);
        cb_morediscountes = (CheckBox) findViewById(R.id.cb_morediscountes);
        cb_morediscountno = (CheckBox) findViewById(R.id.cb_morediscountno);
        cb_logyes = (CheckBox) findViewById(R.id.cb_logyes);
        cb_logno = (CheckBox) findViewById(R.id.cb_logno);
        cb_isfpyes = (CheckBox) findViewById(R.id.cb_isfpyes);
        cb_isfp3 = (CheckBox) findViewById(R.id.cb_isfp3);

        sc_scroll = (ScrollView) findViewById(R.id.sc_scroll);
        initView();
    }

    @Override
    protected void initView() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        _iv_back.setOnClickListener(this);
        tv_classifybj.setOnClickListener(this);
        rl_sc.setOnClickListener(this);
        rl_xc.setOnClickListener(this);
        tv_gmenu1.setOnClickListener(this);
        tv_gmenu2.setOnClickListener(this);
        tv_gmenu4.setOnClickListener(this);
        tv_gmenu5.setOnClickListener(this);
        rl_sc1.setOnClickListener(this);
        rl_xc1.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_addrules.setOnClickListener(this);
        tv_pagerbtn1.setOnClickListener(this);
        tv_pagerbtn2.setOnClickListener(this);
        tv_pagerbtn3.setOnClickListener(this);
        tv_pagerbtn4.setOnClickListener(this);
        tv_pagerbtn5.setOnClickListener(this);
        tv_pagerbtn11.setOnClickListener(this);
        tv_pagerbtn12.setOnClickListener(this);
        tv_pagerbtn13.setOnClickListener(this);
        tv_pagerbtn14.setOnClickListener(this);
        tv_gmenu3.setOnClickListener(this);
        tv_fstime.setOnClickListener(this);
        tv_addclassify.setOnClickListener(this);
        rl_mode.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        rl_pp.setOnClickListener(this);
        tv_netxt.setOnClickListener(this);
        cb_yes.setOnCheckedChangeListener(this);
        cb_no.setOnCheckedChangeListener(this);
        cb_yes1.setOnCheckedChangeListener(this);
        cb_no1.setOnCheckedChangeListener(this);
        cb_borderyes.setOnCheckedChangeListener(this);
        cb_borderno.setOnCheckedChangeListener(this);
        cb_yes2.setOnCheckedChangeListener(this);
        cb_no2.setOnCheckedChangeListener(this);
        cb_yes3.setOnCheckedChangeListener(this);
        cb_no3.setOnCheckedChangeListener(this);
        cb_yes4.setOnCheckedChangeListener(this);
        cb_no4.setOnCheckedChangeListener(this);
        cb_warehouse.setOnCheckedChangeListener(this);
        cb_yes5.setOnCheckedChangeListener(this);
        cb_no5.setOnCheckedChangeListener(this);
        cb_yes6.setOnCheckedChangeListener(this);
        cb_no6.setOnCheckedChangeListener(this);
        cb_morediscountes.setOnCheckedChangeListener(this);
        cb_morediscountno.setOnCheckedChangeListener(this);
        cb_logyes.setOnCheckedChangeListener(this);
        cb_logno.setOnCheckedChangeListener(this);
        cb_isfpyes.setOnCheckedChangeListener(this);
        cb_isfp3.setOnCheckedChangeListener(this);

        ed_input4.setOnFocusChangeListener(focus);
        ed_input13.setOnFocusChangeListener(focus);
        ed_input18.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (inventoryAdapter != null
                            && inventoryAdapter.getCount() != 0) {
                        ed_input18
                                .setBackgroundResource(R.drawable.release_goods6);
                        inventoryAdapter.getInventory();
                        ed_input18.setFocusable(false);
                    } else {
                        ed_input18
                                .setBackgroundResource(R.drawable.release_goods2);
                    }
                }
            }
        });
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);

        initData();
    }

    private String _goods = "", commonid = "";

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            _goods = bundle.getString("goods");
            if (_goods.equals("redact")) {
                tv_netxt.setText(getString(R.string.release_goods88));
                _tv_name.setText(getString(R.string.release_goods108));
                commonid = bundle.getString("common_id");
                if (commonid == null) {
                    finish();
                    Toast.makeText(this, getString(R.string.release_goods109),
                            Toast.LENGTH_SHORT).show();
                }
                netRun.getRedactData(commonid, "");
            } else if (_goods.equals("add")) {
                tv_netxt.setText(getString(R.string.release_goods92));
                _tv_name.setText(getString(R.string.storehome26));
                tv_gmenu2.setVisibility(View.GONE);
                tv_gmenu3.setVisibility(View.GONE);
                tv_gmenu4.setVisibility(View.GONE);
                tv_gmenu5.setVisibility(View.GONE);
                // 本店分类
                storeclassifysaze.add("請選擇");
                // 分类
                netRun.GoodsClassifyAll();
                // 详情
                imgInfoAdapter = new ImgInfoAdapter();
                mgv_info.setAdapter(imgInfoAdapter);
                // 活动规则
                List<DiscountInfo> discount = new ArrayList<>();
                discountAdapter = new DiscountAdapter(discount);
                mlv_activityrules
                        .setLayoutManager(new StaggeredGridLayoutManager(1,
                                OrientationHelper.VERTICAL));
                mlv_activityrules.setAdapter(discountAdapter);
            } else {
                finish();
            }
            // 地址
            netRun.GoodsAddress();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_classifybj) {

            // 编辑分类
            isgoodsclassify = false;
            if (goodsClassInfo != null && goodsClassInfo.goods_class != null
                    && goodsClassInfo.goods_class.size() != 0) {
                List<String> classify1 = new ArrayList<>();
                List<String> classify2 = new ArrayList<>();
                List<String> classify3 = new ArrayList<>();
                for (int i = 0; i < goodsClassInfo.goods_class.size(); i++) {
                    GoodsClassInfo.goods_class goods_class = goodsClassInfo.goods_class
                            .get(i);
                    classify1.add(goods_class.gc_name);
                }
                for (int j = 0; j < goodsClassInfo.goods_class.get(0).class2
                        .size(); j++) {
                    GoodsClassInfo.goods_class.class2 class2 = goodsClassInfo.goods_class
                            .get(0).class2.get(j);
                    classify2.add(class2.gc_name);
                }
                for (int k = 0; k < goodsClassInfo.goods_class.get(0).class2
                        .get(0).class3.size(); k++) {
                    GoodsClassInfo.goods_class.class2.class3 class3 = goodsClassInfo.goods_class
                            .get(0).class2.get(0).class3.get(k);
                    classify3.add(class3.gc_name);
                }
                chooseDialog(classify1, classify2, classify3,
                        getString(R.string.release_goods95));
            } else {
                Toast.makeText(ReleaseGoodsActivity.this,
                        getString(R.string.release_goods100),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.rl_sc) {
            // 商品图片上传
            editAvatar();
            album = 1;
            iv_open.setImageResource(R.drawable.release_album_btn);
            tv_imgspace.setText(getString(R.string.release_goods42));
            ll_album.setVisibility(View.GONE);
        } else if (v.getId() == R.id.rl_xc) {
            // 商品相册选择
            if (ll_album.getVisibility() == View.VISIBLE) {
                iv_open.setImageResource(R.drawable.release_album_btn);
                tv_imgspace.setText(getString(R.string.release_goods42));
                ll_album.setVisibility(View.GONE);
            } else {
                iv_open.setImageResource(R.drawable.release_album_top);
                tv_imgspace.setText(getString(R.string.release_goods43));
                ll_album.setVisibility(View.VISIBLE);
                album = 1;
                isrequest = false;
                netRun.ImageSpace("1", "0");
            }
        } else if (v.getId() == R.id.tv_gmenu3) {
            //  编辑参数:
            Intent Editintent = new Intent(ReleaseGoodsActivity.this,
                    EditValActivity.class);
            Editintent.putExtra("commonid",
                    editorGoodsInfo.goods.goods_commonid);
            startActivity(Editintent);
        } else if (v.getId() == R.id.tv_gmenu2) {
            //  编辑图片
            Intent intent1 = new Intent(ReleaseGoodsActivity.this,
                    GoodssImageActivity.class);
            intent1.putExtra("common_id", commonid);
            intent1.putExtra("goods", "redact");
            startActivity(intent1);
        } else if (v.getId() == R.id.tv_gmenu4) {
            //  赠送商品
            Intent giftsintent = new Intent(ReleaseGoodsActivity.this,
                    GiftsActivity.class);
            giftsintent.putExtra("commonid", commonid);
            startActivity(giftsintent);
        } else if (v.getId() == R.id.tv_gmenu5) {
            //  推荐组合
            Intent Recommendedintent = new Intent(ReleaseGoodsActivity.this,
                    RecommendedActivity.class);
            Recommendedintent.putExtra("commonid", commonid);
            startActivity(Recommendedintent);
        } else if (v.getId() == R.id.tv_pagerbtn1) {
            // 商品图片首页goodsimgpager
            if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
                    && !imageSpaceInfo.page_total.equals("")
                    && !imageSpaceInfo.page_total.equals("1")) {
                isrequest = false;
                album = 1;
                netRun.ImageSpace("1", goodsimgclassify);
            }
        } else if (v.getId() == R.id.tv_pagerbtn2) {
            // 商品图片上一页
            if (goodsimgpager > 1) {
                goodsimgpager--;
                isrequest = false;
                album = 1;
                netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
            }
        } else if (v.getId() == R.id.tv_pagerbtn4) {
            // 商品图片下一页
            if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
                    && !imageSpaceInfo.page_total.equals("")) {
                int total = Integer.parseInt(imageSpaceInfo.page_total);
                if (goodsimgpager < total) {
                    goodsimgpager++;
                    isrequest = false;
                    album = 1;
                    netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
                }
            }
        } else if (v.getId() == R.id.tv_pagerbtn5) {
            // 商品图片末页
            if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
                    && !imageSpaceInfo.page_total.equals("")
                    && !imageSpaceInfo.page_total.equals(goodsimgpager + "")) {
                goodsimgpager = Integer.parseInt(imageSpaceInfo.page_total);
                isrequest = false;
                album = 1;
                netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
            }
        } else if (v.getId() == R.id.rl_sc1) {
            // 详情图片上传
            editAvatar();
            album = 2;
            iv_open1.setImageResource(R.drawable.release_album_btn);
            tv_imgspace2.setText(getString(R.string.release_goods42));
            rl_datails.setVisibility(View.GONE);
        } else if (v.getId() == R.id.rl_xc1) {
            // 详情图片相册
            if (rl_datails.getVisibility() == View.VISIBLE) {
                iv_open1.setImageResource(R.drawable.release_album_btn);
                tv_imgspace2.setText(getString(R.string.release_goods42));
                rl_datails.setVisibility(View.GONE);
            } else {
                iv_open1.setImageResource(R.drawable.release_album_top);
                tv_imgspace2.setText(getString(R.string.release_goods43));
                rl_datails.setVisibility(View.VISIBLE);
                album = 2;
                isrequest = false;
                netRun.ImageSpace("1", "0");
            }
        } else if (v.getId() == R.id.tv_pagerbtn11) {
            // 详情相册首页
            if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
                    && !imageSpaceInfo.page_total.equals("")
                    && !imageSpaceInfo.page_total.equals("1")) {
                isrequest = false;
                album = 2;
                netRun.ImageSpace("1", goodsimgclassify2);
            }
        } else if (v.getId() == R.id.tv_pagerbtn12) {
            // 详情相册上一页
            if (goodsimgpager2 > 1) {
                goodsimgpager2--;
                isrequest = false;
                album = 2;
                netRun.ImageSpace(goodsimgpager2 + "", goodsimgclassify2);
            }
        } else if (v.getId() == R.id.tv_pagerbtn13) {
            // 详情相册下一页
            if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
                    && !imageSpaceInfo.page_total.equals("")) {
                int total = Integer.parseInt(imageSpaceInfo.page_total);
                if (goodsimgpager2 < total) {
                    goodsimgpager2++;
                    isrequest = false;
                    album = 2;
                    netRun.ImageSpace(goodsimgpager2 + "", goodsimgclassify2);
                }
            }
        } else if (v.getId() == R.id.tv_pagerbtn14) {
            // 详情相册末页
            if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
                    && !imageSpaceInfo.page_total.equals("")
                    && !imageSpaceInfo.page_total.equals(goodsimgpager2 + "")) {
                goodsimgpager2 = Integer.parseInt(imageSpaceInfo.page_total);
                isrequest = false;
                album = 2;
                netRun.ImageSpace(goodsimgpager2 + "", goodsimgclassify2);
            }
        } else if (v.getId() == R.id.tv_time) {
            // 发货日期
            showPopup(1, tv_time);
        } else if (v.getId() == R.id.tv_fstime) {
            // 发售日期
            showPopup(1, tv_fstime);
        } else if (v.getId() == R.id.tv_date) {
            // 商品发布时间
            if (cb_no4.isChecked()) {
                showPopup(2, tv_date);
            }
        } else if (v.getId() == R.id.tv_netxt) {
            if (_goods.equals("redact")) {
                // 提交
                EditSave(1);
            } else if (_goods.equals("add")) {
                // 下一步
                Intent intent = new Intent(ReleaseGoodsActivity.this,
                        GoodssImageActivity.class);
                intent.putExtra("goods", "add");
                intent.putExtra("common_id", "12");
                startActivity(intent);
                EditSave(2);
            }
        } else if (v.getId() == R.id.rl_pp) {
            // 品牌
            bddialog();
        } else if (v.getId() == R.id.tv_addclassify) {
            // 新增分类
            if (storeClassifyAdapter != null) {
                storeClassifyAdapter.additem();
            }
        } else if (v.getId() == R.id.rl_mode) {
            // 运费模板
            Intent intent = new Intent(ReleaseGoodsActivity.this,
                    FreightModeActivity.class);
            startActivityForResult(intent, 10010);
        } else if (v.getId() == R.id.tv_addrules) {
            // 添加规则
            discountAdapter.addItem();
        }


//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_classifybj:
//			// 编辑分类
//			isgoodsclassify = false;
//			if (goodsClassInfo != null && goodsClassInfo.goods_class != null
//					&& goodsClassInfo.goods_class.size() != 0) {
//				List<String> classify1 = new ArrayList<>();
//				List<String> classify2 = new ArrayList<>();
//				List<String> classify3 = new ArrayList<>();
//				for (int i = 0; i < goodsClassInfo.goods_class.size(); i++) {
//					GoodsClassInfo.goods_class goods_class = goodsClassInfo.goods_class
//							.get(i);
//					classify1.add(goods_class.gc_name);
//				}
//				for (int j = 0; j < goodsClassInfo.goods_class.get(0).class2
//						.size(); j++) {
//					GoodsClassInfo.goods_class.class2 class2 = goodsClassInfo.goods_class
//							.get(0).class2.get(j);
//					classify2.add(class2.gc_name);
//				}
//				for (int k = 0; k < goodsClassInfo.goods_class.get(0).class2
//						.get(0).class3.size(); k++) {
//					GoodsClassInfo.goods_class.class2.class3 class3 = goodsClassInfo.goods_class
//							.get(0).class2.get(0).class3.get(k);
//					classify3.add(class3.gc_name);
//				}
//				chooseDialog(classify1, classify2, classify3,
//						getString(R.string.release_goods95));
//			} else {
//				Toast.makeText(ReleaseGoodsActivity.this,
//						getString(R.string.release_goods100),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.rl_sc:
//			// 商品图片上传
//			editAvatar();
//			album = 1;
//			iv_open.setImageResource(R.drawable.release_album_btn);
//			tv_imgspace.setText(getString(R.string.release_goods42));
//			ll_album.setVisibility(View.GONE);
//			break;
//		case R.id.rl_xc:
//			// 商品相册选择
//			if (ll_album.getVisibility() == View.VISIBLE) {
//				iv_open.setImageResource(R.drawable.release_album_btn);
//				tv_imgspace.setText(getString(R.string.release_goods42));
//				ll_album.setVisibility(View.GONE);
//			} else {
//				iv_open.setImageResource(R.drawable.release_album_top);
//				tv_imgspace.setText(getString(R.string.release_goods43));
//				ll_album.setVisibility(View.VISIBLE);
//				album = 1;
//				isrequest = false;
//				netRun.ImageSpace("1", "0");
//			}
//			break;
//		case R.id.tv_gmenu1:
//			break;
//		case R.id.tv_gmenu3:
//			//  编辑参数:
//			 Intent Editintent = new Intent(ReleaseGoodsActivity.this,
//			 EditValActivity.class);
//			 Editintent.putExtra("commonid",
//			 editorGoodsInfo.goods.goods_commonid);
//			 startActivity(Editintent);
//			break;
//		case R.id.tv_gmenu2:
//			//  编辑图片
//			 Intent intent1 = new Intent(ReleaseGoodsActivity.this,
//			 GoodssImageActivity.class);
//			 intent1.putExtra("common_id", commonid);
//			 intent1.putExtra("goods", "redact");
//			 startActivity(intent1);
//			break;
//		case R.id.tv_gmenu4:
//			//  赠送商品
//			 Intent giftsintent = new Intent(ReleaseGoodsActivity.this,
//			 GiftsActivity.class);
//			 giftsintent.putExtra("commonid", commonid);
//			 startActivity(giftsintent);
//			break;
//		case R.id.tv_gmenu5:
//			//  推荐组合
//			 Intent Recommendedintent = new Intent(ReleaseGoodsActivity.this,
//			 RecommendedActivity.class);
//			 Recommendedintent.putExtra("commonid", commonid);
//			 startActivity(Recommendedintent);
//			break;
//		case R.id.tv_pagerbtn1:
//			// 商品图片首页goodsimgpager
//			if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
//					&& !imageSpaceInfo.page_total.equals("")
//					&& !imageSpaceInfo.page_total.equals("1")) {
//				isrequest = false;
//				album = 1;
//				netRun.ImageSpace("1", goodsimgclassify);
//			}
//			break;
//		case R.id.tv_pagerbtn2:
//			// 商品图片上一页
//			if (goodsimgpager > 1) {
//				goodsimgpager--;
//				isrequest = false;
//				album = 1;
//				netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
//			}
//			break;
//		case R.id.tv_pagerbtn4:
//			// 商品图片下一页
//			if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
//					&& !imageSpaceInfo.page_total.equals("")) {
//				int total = Integer.parseInt(imageSpaceInfo.page_total);
//				if (goodsimgpager < total) {
//					goodsimgpager++;
//					isrequest = false;
//					album = 1;
//					netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
//				}
//			}
//			break;
//		case R.id.tv_pagerbtn5:
//			// 商品图片末页
//			if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
//					&& !imageSpaceInfo.page_total.equals("")
//					&& !imageSpaceInfo.page_total.equals(goodsimgpager + "")) {
//				goodsimgpager = Integer.parseInt(imageSpaceInfo.page_total);
//				isrequest = false;
//				album = 1;
//				netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
//			}
//			break;
//		case R.id.rl_sc1:
//			// 详情图片上传
//			editAvatar();
//			album = 2;
//			iv_open1.setImageResource(R.drawable.release_album_btn);
//			tv_imgspace2.setText(getString(R.string.release_goods42));
//			rl_datails.setVisibility(View.GONE);
//			break;
//		case R.id.rl_xc1:
//			// 详情图片相册
//			if (rl_datails.getVisibility() == View.VISIBLE) {
//				iv_open1.setImageResource(R.drawable.release_album_btn);
//				tv_imgspace2.setText(getString(R.string.release_goods42));
//				rl_datails.setVisibility(View.GONE);
//			} else {
//				iv_open1.setImageResource(R.drawable.release_album_top);
//				tv_imgspace2.setText(getString(R.string.release_goods43));
//				rl_datails.setVisibility(View.VISIBLE);
//				album = 2;
//				isrequest = false;
//				netRun.ImageSpace("1", "0");
//			}
//			break;
//		case R.id.tv_pagerbtn11:
//			// 详情相册首页
//			if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
//					&& !imageSpaceInfo.page_total.equals("")
//					&& !imageSpaceInfo.page_total.equals("1")) {
//				isrequest = false;
//				album = 2;
//				netRun.ImageSpace("1", goodsimgclassify2);
//			}
//			break;
//		case R.id.tv_pagerbtn12:
//			// 详情相册上一页
//			if (goodsimgpager2 > 1) {
//				goodsimgpager2--;
//				isrequest = false;
//				album = 2;
//				netRun.ImageSpace(goodsimgpager2 + "", goodsimgclassify2);
//			}
//			break;
//		case R.id.tv_pagerbtn13:
//			// 详情相册下一页
//			if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
//					&& !imageSpaceInfo.page_total.equals("")) {
//				int total = Integer.parseInt(imageSpaceInfo.page_total);
//				if (goodsimgpager2 < total) {
//					goodsimgpager2++;
//					isrequest = false;
//					album = 2;
//					netRun.ImageSpace(goodsimgpager2 + "", goodsimgclassify2);
//				}
//			}
//			break;
//		case R.id.tv_pagerbtn14:
//			// 详情相册末页
//			if (imageSpaceInfo != null && imageSpaceInfo.page_total != null
//					&& !imageSpaceInfo.page_total.equals("")
//					&& !imageSpaceInfo.page_total.equals(goodsimgpager2 + "")) {
//				goodsimgpager2 = Integer.parseInt(imageSpaceInfo.page_total);
//				isrequest = false;
//				album = 2;
//				netRun.ImageSpace(goodsimgpager2 + "", goodsimgclassify2);
//			}
//			break;
//		case R.id.tv_time:
//			// 发货日期
//			showPopup(1, tv_time);
//			break;
//		case R.id.tv_fstime:
//			// 发售日期
//			showPopup(1, tv_fstime);
//			break;
//		case R.id.tv_date:
//			// 商品发布时间
//			if (cb_no4.isChecked()) {
//				showPopup(2, tv_date);
//			}
//			break;
//		case R.id.tv_netxt:
//			if (_goods.equals("redact")) {
//				// 提交
//				EditSave(1);
//			} else if (_goods.equals("add")) {
//				// 下一步
//				 Intent intent = new Intent(ReleaseGoodsActivity.this,
//				 GoodssImageActivity.class);
//				 intent.putExtra("goods", "add");
//				 intent.putExtra("common_id", "12");
//				 startActivity(intent);
//				EditSave(2);
//			}
//			break;
//		case R.id.rl_pp:
//			// 品牌
//			bddialog();
//			break;
//		case R.id.tv_addclassify:
//			// 新增分类
//			if (storeClassifyAdapter != null) {
//				storeClassifyAdapter.additem();
//			}
//			break;
//		case R.id.rl_mode:
//			// 运费模板
//			 Intent intent = new Intent(ReleaseGoodsActivity.this,
//			 FreightModeActivity.class);
//			 startActivityForResult(intent, 10010);
//			break;
//		case R.id.tv_addrules:
//			// 添加规则
//			discountAdapter.addItem();
//
//			break;
//		}
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.cb_yes) {
                // F码商品是
                cb_no.setChecked(false);
                rl_fnum.setVisibility(View.VISIBLE);
                rl_fqz.setVisibility(View.VISIBLE);
                if (_goods.equals("redact")
                        && editorGoodsInfo.fcode_array != null
                        && editorGoodsInfo.fcode_array.size() != 0) {
                    rl_fcodelist.setVisibility(View.VISIBLE);
                } else {
                    rl_fcodelist.setVisibility(View.GONE);
                }
            } else if (buttonView.getId() == R.id.cb_no) {
                // F码商品否
                cb_yes.setChecked(false);
                rl_fnum.setVisibility(View.GONE);
                rl_fqz.setVisibility(View.GONE);
                rl_fcodelist.setVisibility(View.GONE);
            } else if (buttonView.getId() == R.id.cb_yes1) {
                // 预售是
                cb_no1.setChecked(false);
                rl_fhrq.setVisibility(View.VISIBLE);
            } else if (buttonView.getId() == R.id.cb_no1) {
                // 预售否
                cb_yes1.setChecked(false);
                rl_fhrq.setVisibility(View.GONE);
            } else if (buttonView.getId() == R.id.cb_yes2) {
                // 固定运费
                cb_no2.setChecked(false);
                ed_input23.setVisibility(View.VISIBLE);
                tv_mopfreight.setVisibility(View.VISIBLE);
                rl_mode.setVisibility(View.GONE);
            } else if (buttonView.getId() == R.id.cb_no2) {
                // 使用运费模板
                cb_yes2.setChecked(false);
                ed_input23.setVisibility(View.GONE);
                tv_mopfreight.setVisibility(View.GONE);
                rl_mode.setVisibility(View.VISIBLE);
            } else if (buttonView.getId() == R.id.cb_yes3) {
                // 增值税发票是
                cb_no3.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_no3) {
                // 增值税发票否
                cb_yes3.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_yes4) {
                // 立即发布
                cb_no4.setChecked(false);
                cb_warehouse.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_no4) {
                // 发布时间
                cb_yes4.setChecked(false);
                cb_warehouse.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_warehouse) {
                // 放入仓库
                cb_yes4.setChecked(false);
                cb_no4.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_yes5) {
                // 预约是
                cb_no5.setChecked(false);
                rl_fsdata.setVisibility(View.VISIBLE);
            } else if (buttonView.getId() == R.id.cb_no5) {
                // 预约否
                cb_yes5.setChecked(false);
                rl_fsdata.setVisibility(View.GONE);
            } else if (buttonView.getId() == R.id.cb_yes6) {
                // 推荐是
                cb_no6.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_no6) {
                // 推荐否
                cb_yes6.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_borderyes) {
                // 跨境是
                cb_borderno.setChecked(false);
                i_border.setVisibility(View.VISIBLE);
            } else if (buttonView.getId() == R.id.cb_borderno) {
                // 跨境否
                cb_borderyes.setChecked(false);
                i_border.setVisibility(View.GONE);
            } else if (buttonView.getId() == R.id.cb_morediscountes) {
                // 打折是
                cb_morediscountno.setChecked(false);
                rl_activityrules.setVisibility(View.VISIBLE);
                if (discountAdapter.getItemCount() == 0) {
                    discountAdapter.addItem();
                }
            } else if (buttonView.getId() == R.id.cb_morediscountno) {
                // 打折否
                cb_morediscountes.setChecked(false);
                rl_activityrules.setVisibility(View.GONE);
            } else if (buttonView.getId() == R.id.cb_logyes) {
                // 原产是
                cb_logno.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_logno) {
                // 原产否
                cb_logyes.setChecked(false);
            } else if (buttonView.getId() == R.id.cb_isfpyes) {
                // 提供发票是
                cb_isfp3.setChecked(false);
                rl_iszzsfp.setVisibility(View.VISIBLE);
            } else if (buttonView.getId() == R.id.cb_isfp3) {
                // 提供发票否
                cb_isfpyes.setChecked(false);
                rl_iszzsfp.setVisibility(View.GONE);
            }


//			switch (buttonView.getId()) {
//			case R.id.cb_yes:
//				// F码商品是
//				cb_no.setChecked(false);
//				rl_fnum.setVisibility(View.VISIBLE);
//				rl_fqz.setVisibility(View.VISIBLE);
//				if (_goods.equals("redact")
//						&& editorGoodsInfo.fcode_array != null
//						&& editorGoodsInfo.fcode_array.size() != 0) {
//					rl_fcodelist.setVisibility(View.VISIBLE);
//				} else {
//					rl_fcodelist.setVisibility(View.GONE);
//				}
//				break;
//			case R.id.cb_no:
//				// F码商品否
//				cb_yes.setChecked(false);
//				rl_fnum.setVisibility(View.GONE);
//				rl_fqz.setVisibility(View.GONE);
//				rl_fcodelist.setVisibility(View.GONE);
//				break;
//			case R.id.cb_yes1:
//				// 预售是
//				cb_no1.setChecked(false);
//				rl_fhrq.setVisibility(View.VISIBLE);
//				break;
//			case R.id.cb_no1:
//				// 预售否
//				cb_yes1.setChecked(false);
//				rl_fhrq.setVisibility(View.GONE);
//				break;
//			case R.id.cb_yes2:
//				// 固定运费
//				cb_no2.setChecked(false);
//				ed_input23.setVisibility(View.VISIBLE);
//				tv_mopfreight.setVisibility(View.VISIBLE);
//				rl_mode.setVisibility(View.GONE);
//				break;
//			case R.id.cb_no2:
//				// 使用运费模板
//				cb_yes2.setChecked(false);
//				ed_input23.setVisibility(View.GONE);
//				tv_mopfreight.setVisibility(View.GONE);
//				rl_mode.setVisibility(View.VISIBLE);
//				break;
//			case R.id.cb_yes3:
//				// 增值税发票是
//				cb_no3.setChecked(false);
//				break;
//			case R.id.cb_no3:
//				// 增值税发票否
//				cb_yes3.setChecked(false);
//				break;
//			case R.id.cb_yes4:
//				// 立即发布
//				cb_no4.setChecked(false);
//				cb_warehouse.setChecked(false);
//				break;
//			case R.id.cb_no4:
//				// 发布时间
//				cb_yes4.setChecked(false);
//				cb_warehouse.setChecked(false);
//				break;
//			case R.id.cb_warehouse:
//				// 放入仓库
//				cb_yes4.setChecked(false);
//				cb_no4.setChecked(false);
//				break;
//			case R.id.cb_yes5:
//				// 预约是
//				cb_no5.setChecked(false);
//				rl_fsdata.setVisibility(View.VISIBLE);
//				break;
//			case R.id.cb_no5:
//				// 预约否
//				cb_yes5.setChecked(false);
//				rl_fsdata.setVisibility(View.GONE);
//				break;
//			case R.id.cb_yes6:
//				// 推荐是
//				cb_no6.setChecked(false);
//				break;
//			case R.id.cb_no6:
//				// 推荐否
//				cb_yes6.setChecked(false);
//				break;
//			case R.id.cb_borderyes:
//				// 跨境是
//				cb_borderno.setChecked(false);
//				i_border.setVisibility(View.VISIBLE);
//				break;
//			case R.id.cb_borderno:
//				// 跨境否
//				cb_borderyes.setChecked(false);
//				i_border.setVisibility(View.GONE);
//				break;
//			case R.id.cb_morediscountes:
//				// 打折是
//				cb_morediscountno.setChecked(false);
//				rl_activityrules.setVisibility(View.VISIBLE);
//				if (discountAdapter.getItemCount() == 0) {
//					discountAdapter.addItem();
//				}
//				break;
//			case R.id.cb_morediscountno:
//				// 打折否
//				cb_morediscountes.setChecked(false);
//				rl_activityrules.setVisibility(View.GONE);
//				break;
//			case R.id.cb_logyes:
//				// 原产是
//				cb_logno.setChecked(false);
//				break;
//			case R.id.cb_logno:
//				// 原产否
//				cb_logyes.setChecked(false);
//				break;
//			case R.id.cb_isfpyes:
//				// 提供发票是
//				cb_isfp3.setChecked(false);
//				rl_iszzsfp.setVisibility(View.VISIBLE);
//				break;
//			case R.id.cb_isfp3:
//				// 提供发票否
//				cb_isfpyes.setChecked(false);
//				rl_iszzsfp.setVisibility(View.GONE);
//				break;
//			}
        }
    }

    private File imagepath;

    /**
     * 编辑商品提交
     */
    private void EditSave(int type) {
        String commission = ed_input16.getText().toString();
        String g_name = ed_input1.getText().toString();
        String g_jingle = ed_input2.getText().toString();
        if (g_jingle == null) {
            g_jingle = "";
        }
        // String goods_tags = ed_input3.getText().toString();
        String cate_id = gc_id;
        String cate_name = tv_classify.getText().toString();
        String b_name = tv_pp.getText().toString();
        if (b_name == null) {
            b_name = "";
        }
        String b_id = getBrand(b_name);
        if (b_id == null) {
            b_id = "0";
        }
        String type_id = "";
        if (type == 1) {
            type_id = editorGoodsInfo.goods.type_id;
        } else if (type == 2) {
            type_id = goods_class_type_id;
        }
        File image_path = imagepath;
        String g_price = ed_input4.getText().toString();
        String g_marketprice = ed_input13.getText().toString();
        String g_costprice = ed_input14.getText().toString();
        if (g_costprice == null) {
            g_costprice = "";
        }
        String g_discount = ed_input15.getText().toString();
        if (g_discount == null) {
            g_discount = "";
        }
        String g_serial = ed_input20.getText().toString();
        if (g_serial == null) {
            g_serial = "";
        }
        String g_alarm = ed_input19.getText().toString();
        if (g_alarm == null) {
            g_alarm = "";
        }
        String attr = attributeAdapter.getattrjson();
        if (attr == null) {
            attr = "";
        }
        String m_body = imgInfoAdapter.getimg();
        if (m_body == null) {
            m_body = "";
        }
        String g_commend = cb_yes6.isChecked() ? "1" : "0";

        String g_state = "";
        if (type == 1) {
            g_state = editorGoodsInfo.goods.goods_state;
        } else if (type == 2) {
            g_state = "1";
        }
        String goods_selltime = "0";
        String spec = "";
        List<String> chooseJson = standardAdapter.getChooseJson();
        String sp_name = chooseJson.get(0);
        String sp_val = chooseJson.get(1);
        Log.i("----------------------", "规格  " + sp_name);
        Log.i("----------------------", "规格2  " + sp_val);
        String g_vat = cb_yes3.isChecked() ? "1" : "0";
        String province = "";
        try {
            province = sp_province.getSelectedItem().toString();
        } catch (Exception e) {
        }
        String province_id = getAddressID(province);
        if (province_id == null) {
            province_id = "0";
        }
        String city = "";
        try {
            city = sp_city.getSelectedItem().toString();
        } catch (Exception e) {
        }
        String city_id = getCityID(city);
        if (city_id == null) {
            city_id = "0";
        }
        String freightt = freight;
        if (freightt == null) {
            freightt = "0";
        }
        String freightnamee = freightname;
        if (freightnamee == null) {
            freightnamee = "";
        }
        String g_freight = ed_input23.getText().toString();
        if (g_freight == null) {
            g_freight = "";
        }
        String sgcate_id = storeClassifyAdapter.getjson();
        if (sgcate_id == null) {
            sgcate_id = "";
        }
        String gltop = "";
        try {
            gltop = sp_gltop.getSelectedItem().toString();
        } catch (Exception e) {
        }

        String plateid_top = getAssociatedID(gltop);
        if (plateid_top == null) {
            plateid_top = "0";
        }
        String glbtn = "";
        try {
            glbtn = sp_glbtn.getSelectedItem().toString();
        } catch (Exception e) {
        }
        String plateid_bottom = getAssociatedID(glbtn);
        if (plateid_bottom == null) {
            plateid_bottom = "0";
        }
        String is_virtual = "0";
        String virtual_indate = "";
        String virtual_limit = "";
        String virtual_invalid_refund = "0";
        String is_fcode = cb_yes.isChecked() ? "1" : "0";
        String is_appoint = cb_yes5.isChecked() ? "1" : "0";
        String appoint_satedate = "0";
        String is_presell = cb_yes1.isChecked() ? "1" : "0";
        String presell_deliverdate = "0";
        String g_fccount = ed_input21.getText().toString();
        String commonidd = commonid;
        String is_kuajing = cb_borderyes.isChecked() ? "1" : "0";
        String g_native = ed_inputsource.getText().toString();
        String is_native = cb_logyes.isChecked() ? "1" : "0";
        String g_seaport = ed_inputport.getText().toString();
        String g_clearance = ed_inputgoodsstate.getText().toString();
        String g_clearance_sn = ed_inputordernumber.getText().toString();
        String is_more_discount = cb_morediscountes.isChecked() ? "1" : "0";
        String md = "";
        if (is_more_discount.equals("1")) {
            md = discountAdapter.getjson();
        } else {
            md = "";
        }

        if (TextUtils.isEmpty(g_native)) {
            g_native = "";
        }
        if (TextUtils.isEmpty(g_clearance_sn)) {
            g_clearance_sn = "";
        }
        if (TextUtils.isEmpty(g_clearance)) {
            g_clearance = "";
        }
        if (TextUtils.isEmpty(g_seaport)) {
            g_seaport = "";
        }
        if (g_fccount == null) {
            g_fccount = "";
        }
        String g_fcprefix = ed_input22.getText().toString();
        if (g_fcprefix == null) {
            g_fcprefix = "";
        }
        try {
            String s = tv_date.getText().toString();
            if (s != null && !s.equals("")) {
                goods_selltime = dateToStamp(s, "yyyy-MM-dd HH:mm");
            }
        } catch (ParseException e) {
            Log.i("---------------", "时间转换错误");
            e.printStackTrace();
        }
        try {
            String s1 = tv_time.getText().toString();
            if (s1 != null && !s1.equals("")) {
                presell_deliverdate = dateToStamp(s1, "yyyy-MM-dd");
            }
        } catch (Exception e) {
            Log.i("---------------", "预售时间转换错误" + e.getMessage());
        }
        try {
            String s2 = tv_fstime.getText().toString();
            if (s2 != null && !s2.equals("")) {
                appoint_satedate = dateToStamp(s2, "yyyy-MM-dd HH:mm");
            }
        } catch (Exception e) {
            Log.i("---------------", "时间转换错误");
        }
        spec = inventoryAdapter.getKCjson();
        if (is_presell.equals("1") && presell_deliverdate.equals("")) {
            Toast.makeText(ReleaseGoodsActivity.this,
                    getString(R.string.release_goods144), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (cb_yes5.isChecked() && appoint_satedate.equals("")) {
            Toast.makeText(ReleaseGoodsActivity.this,
                    getString(R.string.release_goods144), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (image_path == null) {
            Toast.makeText(ReleaseGoodsActivity.this,
                    getString(R.string.release_goods145), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        String stock = ed_input18.getText().toString();
        String level_0_price = ed_input5.getText().toString();
        String level_1_price = ed_input6.getText().toString();
        String level_2_price = ed_input7.getText().toString();
        String level_3_price = ed_input8.getText().toString();
        String level_0_auth_price = ed_input9.getText().toString();
        String level_1_auth_price = ed_input10.getText().toString();
        String level_2_auth_price = ed_input11.getText().toString();
        String level_3_auth_price = ed_input12.getText().toString();
        String goods_points_offset = ed_input17.getText().toString();
        String costprice = ed_input14.getText().toString();

        if (TextUtils.isEmpty(stock)) {
            stock = "";
        }
        if (TextUtils.isEmpty(goods_points_offset)) {
            goods_points_offset = "";
        }
        if (TextUtils.isEmpty(costprice)) {
            costprice = "";
        }
        if (TextUtils.isEmpty(level_0_price)
                || TextUtils.isEmpty(level_1_price)
                || TextUtils.isEmpty(level_2_price)
                || TextUtils.isEmpty(level_3_price)
                || TextUtils.isEmpty(level_0_auth_price)
                || TextUtils.isEmpty(level_1_auth_price)
                || TextUtils.isEmpty(level_2_auth_price)
                || TextUtils.isEmpty(level_3_auth_price)) {
            Toast.makeText(ReleaseGoodsActivity.this,
                    getString(R.string.release_goods144), Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        List<String> strlist = new ArrayList<>();
        strlist.add(commission);
        strlist.add(g_name);
        // strlist.add(goods_tags);
        strlist.add(cate_id);
        strlist.add(cate_name);
        strlist.add(type_id);
        strlist.add(g_price);
        strlist.add(g_marketprice);
        strlist.add(g_commend);
        strlist.add(g_state);
        strlist.add(spec);
        strlist.add(sp_name);
        strlist.add(sp_val);
        strlist.add(is_virtual);
        strlist.add(virtual_indate);
        strlist.add(virtual_limit);
        strlist.add(virtual_invalid_refund);
        strlist.add(is_fcode);
        strlist.add(is_appoint);
        strlist.add(is_presell);

        for (int i = 0; i < strlist.size(); i++) {
            if (strlist.get(i) == null) {
                Toast.makeText(ReleaseGoodsActivity.this,
                        getString(R.string.release_goods144),
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (type == 1) {
            netRun.EditorSave(commission, g_name, g_jingle, cate_id, cate_name,
                    b_id, b_name, type_id, image_path, g_price, g_marketprice,
                    g_costprice, g_discount, g_serial, g_alarm, attr, m_body,
                    g_commend, g_state, goods_selltime, spec, sp_name, sp_val,
                    g_vat, province_id, city_id, freightt, freightnamee,
                    g_freight, sgcate_id, plateid_top, plateid_bottom,
                    is_virtual, virtual_indate, virtual_limit,
                    virtual_invalid_refund, is_fcode, is_appoint,
                    appoint_satedate, is_presell, presell_deliverdate,
                    g_fccount, g_fcprefix, is_kuajing, g_native, is_native,
                    g_seaport, g_clearance, g_clearance_sn, md,
                    is_more_discount, commonid, stock, level_0_price,
                    level_1_price, level_2_price, level_3_price,
                    level_0_auth_price, level_1_auth_price, level_2_auth_price,
                    level_3_auth_price, goods_points_offset, costprice);
        } else if (type == 2) {
            // 新增提交
            netRun.SaveGoods(commission, g_name, g_jingle, cate_id, cate_name,
                    b_id, b_name, type_id, image_path, g_price, g_marketprice,
                    g_costprice, g_discount, g_serial, g_alarm, attr, m_body,
                    g_commend, g_state, goods_selltime, spec, sp_name, sp_val,
                    g_vat, province_id, city_id, freightt, freightnamee,
                    g_freight, sgcate_id, plateid_top, plateid_bottom,
                    is_virtual, virtual_indate, virtual_limit,
                    virtual_invalid_refund, is_fcode, is_appoint,
                    appoint_satedate, is_presell, presell_deliverdate,
                    g_fccount, g_fcprefix, is_kuajing, g_native, is_native,
                    g_seaport, g_clearance, g_clearance_sn, md,
                    is_more_discount, stock, level_0_price, level_1_price,
                    level_2_price, level_3_price, level_0_auth_price,
                    level_1_auth_price, level_2_auth_price, level_3_auth_price,
                    goods_points_offset, costprice);

        }
    }

    /**
     * 获得关联板式id
     *
     * @return
     */
    private String getAssociatedID(String str) {
        if (str == null) {
            return "0";
        }
        if (_goods.equals("redact")) {
            for (int i = 0; i < editorGoodsInfo.plate_list.size(); i++) {
                if (str.equals(editorGoodsInfo.plate_list.get(i).plate_name)) {
                    return editorGoodsInfo.plate_list.get(i).plate_id;
                }
            }
        } else if (_goods.equals("add")) {
            for (int i = 0; i < fBGoodsInfo.plate_list.size(); i++) {
                if (str.equals(fBGoodsInfo.plate_list.get(i).plate_name)) {
                    return fBGoodsInfo.plate_list.get(i).plate_id;
                }
            }
        }
        return "0";
    }

    /**
     * 获取省份id
     *
     * @return
     */
    private String getAddressID(String str) {
        if (goodsAddressInfo == null || str == null) {
            return "";
        }
        for (int i = 0; i < goodsAddressInfo.list.size(); i++) {
            if (str.equals(goodsAddressInfo.list.get(i).area_name)) {
                return goodsAddressInfo.list.get(i).area_id;
            }
        }
        return "";
    }

    /**
     * 获取城市id
     *
     * @return
     */
    private String getCityID(String str) {
        if (goodsAddressInfo == null || str == null) {
            return "";
        }
        for (int i = 0; i < goodsAddressInfo.list.size(); i++) {
            for (int j = 0; j < goodsAddressInfo.list.get(i).citylist.size(); j++) {
                if (str.equals(goodsAddressInfo.list.get(i).citylist.get(j).area_name)) {
                    return goodsAddressInfo.list.get(i).citylist.get(j).area_id;
                }
            }
        }
        return "";
    }

    /**
     * 获取品牌ID
     *
     * @param str
     * @return
     */
    private String getBrand(String str) {

        if (brandDatailsInfo == null || str == null) {
            return null;
        }
        for (int i = 0; i < brandDatailsInfo.brand_list.size(); i++) {
            if (str.equals(brandDatailsInfo.brand_list.get(i).brand_name)) {
                return brandDatailsInfo.brand_list.get(i).brand_id;
            }
        }
        return null;
    }

    /**
     * 时间弹窗
     *
     * @param type
     * @param v
     */
    private void showPopup(int type, final TextView v) {
        final MyDatepopu2 MyDatepopu2 = new MyDatepopu2(
                ReleaseGoodsActivity.this, type);
        MyDatepopu2.setdate(new MyDatepopu2.date() {
            @Override
            public void onItemClick(String year, String month, String dayOfMonth) {
                v.setText(year);
                MyDatepopu2.dismiss();
            }
        });
        MyDatepopu2.showAtLocation(tv_time, Gravity.BOTTOM, 0, 0);// 设置显示位置
    }

    // 品牌列表
    private BrandListAdapter blad;

    /**
     * 商品分类
     */
    protected void bddialog() {
        dialogbrand = View.inflate(ReleaseGoodsActivity.this,
                R.layout.dialog_brand, null);
        TextView tv_all = (TextView) dialogbrand.findViewById(R.id.tv_all);
        TextView tv_other = (TextView) dialogbrand.findViewById(R.id.tv_other);
        TextView tv_confirm = (TextView) dialogbrand
                .findViewById(R.id.tv_confirm);
        TextView tv_mop = (TextView) dialogbrand.findViewById(R.id.tv_mop);
        final TextView tv_index = (TextView) dialogbrand
                .findViewById(R.id.tv_index);
        final EditText ed_index = (EditText) dialogbrand
                .findViewById(R.id.ed_index);
        MyGridView mgv_zm = (MyGridView) dialogbrand.findViewById(R.id.mgv_zm);
        final ListView lv_pp = (ListView) dialogbrand.findViewById(R.id.lv_pp);
        final LinearLayout ll_ts = (LinearLayout) dialogbrand
                .findViewById(R.id.ll_ts);
        final List<BrandDatailsInfo.brand_list> brand_list = brandDatailsInfo.brand_list;

        if (brandDatailsInfo != null && brand_list != null) {
            blad = new BrandListAdapter(brand_list);
            lv_pp.setAdapter(blad);
        }
        BrandIndexAdapter bad = new BrandIndexAdapter(blad, tv_index, lv_pp,
                ll_ts);
        mgv_zm.setAdapter(bad);
        tv_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 全部品牌
                if (brandDatailsInfo != null && brand_list != null) {
                    blad.setdata(brand_list);
                }
            }
        });
        tv_other.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 其他品牌
            }
        });
        tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 确定
                myDialog.dismiss();
            }
        });
        tv_mop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 搜索
                if (brandDatailsInfo != null && brand_list != null) {
                    String str = ed_index.getText().toString();
                    if (TextUtils.isEmpty(str)) {
                        Toast.makeText(ReleaseGoodsActivity.this,
                                getString(R.string.release_goods106),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<BrandDatailsInfo.brand_list> brand_list = new ArrayList<BrandDatailsInfo.brand_list>();
                    boolean isnull = true;
                    for (int i = 0; i < brand_list.size(); i++) {
                        if (brand_list.get(i).brand_name.contains(str)) {
                            brand_list.add(brand_list.get(i));
                            isnull = false;
                        }
                    }
                    blad.setdata(brand_list);
                    if (isnull) {
                        ll_ts.setVisibility(View.VISIBLE);
                        lv_pp.setVisibility(View.GONE);
                        tv_index.setText(str);
                    } else {
                        ll_ts.setVisibility(View.GONE);
                        lv_pp.setVisibility(View.VISIBLE);
                        tv_index.setText("");
                    }
                }
            }
        });
        if (myDialog == null) {
            myDialog = new MyDialog(ReleaseGoodsActivity.this,
                    getw() - 70, 550, dialogbrand, R.style.loading_dialog);
            myDialog.show();
        } else {
            myDialog.show();
        }
    }

    // 添加规格的名字
    private String addspecname = "";
    // 添加规格的itemid
    private int pid = 0;

    /**
     * 添加规格
     */
    private void ShowAddSpec(final String gc_id, final String sp_id) {
        View addspec = View.inflate(ReleaseGoodsActivity.this,
                R.layout.dialog_addspec, null);
        final EditText ed_addspec = (EditText) addspec
                .findViewById(R.id.ed_addspec);
        ed_addspec.setText("");
        TextView tv_cancel = (TextView) addspec.findViewById(R.id.tv_cancel);
        TextView tv_qd = (TextView) addspec.findViewById(R.id.tv_qd);
        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消
                myDialog2.dismiss();
            }
        });
        tv_qd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 确定
                String s = ed_addspec.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.release_goods129),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                addspecname = s;
                netRun.AddSpec(s, gc_id, sp_id);
                myDialog2.dismiss();
            }
        });

        if (myDialog2 == null) {
            myDialog2 = new MyDialog(ReleaseGoodsActivity.this, getw() - 70,
                    550, addspec, R.style.loading_dialog);
            myDialog2.show();
        } else {
            myDialog2.show();
        }
    }

    // 分类ID
    private int goodsclassifypointer1 = 0, goodsclassifypointer2 = 0,
            goodsclassifypointer3 = 0;
    // 分类绑定的类型编号
    private String goods_class_type_id;
    // 分类编号
    private String gc_id;

    /**
     * 三级联动
     *
     * @param title
     */
    private void chooseDialog(List<String> datas1, final List<String> datas2,
                              final List<String> datas3, String title) {
        View view = LayoutInflater.from(ReleaseGoodsActivity.this).inflate(
                R.layout.dialog_level3, null);
        final WheelView<String> wheelView = (WheelView<String>) view
                .findViewById(R.id.wheelview);
        final WheelView<String> wheelView2 = (WheelView<String>) view
                .findViewById(R.id.wheelview2);
        final WheelView<String> wheelView3 = (WheelView<String>) view
                .findViewById(R.id.wheelview3);
        if (datas1 != null && datas1.size() != 0) {
            wheelView.setWheelAdapter(new ArrayWheelAdapter<String>(
                    ReleaseGoodsActivity.this));
            wheelView.setSkin(WheelView.Skin.Holo);
            wheelView.setWheelData(datas1);
        }
        if (datas2 != null && datas2.size() != 0) {
            wheelView2.setWheelAdapter(new ArrayWheelAdapter<String>(
                    ReleaseGoodsActivity.this));
            wheelView2.setSkin(WheelView.Skin.Holo);
            wheelView2.setWheelData(datas2);
        }
        if (datas3 != null && datas3.size() != 0) {
            wheelView3.setWheelAdapter(new ArrayWheelAdapter<String>(
                    ReleaseGoodsActivity.this));
            wheelView3.setSkin(WheelView.Skin.Holo);
            wheelView3.setWheelData(datas3);
        }
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 15;
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textSize = 12;
        wheelView.setStyle(style);
        wheelView2.setStyle(style);
        wheelView3.setStyle(style);
        wheelView
                .setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(int position, String t) {
                        goodsclassifypointer1 = position;
                        goodsclassifypointer2 = 0;
                        goodsclassifypointer3 = 0;
                        List<String> classify2 = new ArrayList<String>();
                        for (int j = 0; j < goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2.size(); j++) {
                            GoodsClassInfo.goods_class.class2 class2 = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2.get(j);
                            classify2.add(class2.gc_name);
                        }
                        if (classify2.size() == 0) {
                            classify2.add("");
                        }
                        if (classify2 != null && classify2.size() != 0) {
                            wheelView2.smoothScrollToPosition(0);
                            wheelView2.setWheelData(classify2);
                        }

                        List<String> classify3 = new ArrayList<String>();
                        for (int k = 0; k < goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size(); k++) {
                            GoodsClassInfo.goods_class.class2.class3 class3 = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3.get(k);
                            classify3.add(class3.gc_name);
                        }
                        if (classify3.size() == 0) {
                            classify3.add("");
                        }
                        if (classify3 != null && classify3.size() != 0) {
                            wheelView3.smoothScrollToPosition(0);
                            wheelView3.setWheelData(classify3);
                        }
                        if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2.size() == 0) {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).type_id;
                        } else if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size() == 0) {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).type_id;
                        } else {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3
                                    .get(goodsclassifypointer3).type_id;
                        }
                        if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2.size() == 0) {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).gc_id;
                        } else if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size() == 0) {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).gc_id;
                        } else {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3
                                    .get(goodsclassifypointer3).gc_id;
                        }
                    }
                });
        wheelView2
                .setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(int position, String t) {
                        goodsclassifypointer2 = position;
                        List<String> classify3 = new ArrayList<String>();
                        for (int k = 0; k < goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size(); k++) {
                            GoodsClassInfo.goods_class.class2.class3 class3 = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3.get(k);
                            classify3.add(class3.gc_name);
                        }
                        if (classify3.size() == 0) {
                            classify3.add("");
                        }
                        goodsclassifypointer3 = 0;
                        if (classify3 != null && classify3.size() != 0) {
                            wheelView3.smoothScrollToPosition(0);
                            wheelView3.setWheelData(classify3);
                        }
                        if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2.size() == 0) {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).type_id;
                        } else if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size() == 0) {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).type_id;
                        } else {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3
                                    .get(goodsclassifypointer3).type_id;
                        }
                        if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2.size() == 0) {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).gc_id;
                        } else if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size() == 0) {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).gc_id;
                        } else {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3
                                    .get(goodsclassifypointer3).gc_id;
                        }
                    }
                });
        wheelView3
                .setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(int position, String t) {
                        goodsclassifypointer3 = position;
                        if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2.size() == 0) {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).type_id;
                        } else if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size() == 0) {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).type_id;
                        } else {
                            goods_class_type_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3
                                    .get(goodsclassifypointer3).type_id;
                        }
                        if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2.size() == 0) {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).gc_id;
                        } else if (goodsClassInfo.goods_class
                                .get(goodsclassifypointer1).class2
                                .get(goodsclassifypointer2).class3.size() == 0) {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).gc_id;
                        } else {
                            gc_id = goodsClassInfo.goods_class
                                    .get(goodsclassifypointer1).class2
                                    .get(goodsclassifypointer2).class3
                                    .get(goodsclassifypointer3).gc_id;
                        }
                    }
                });
        // R.style.loading_dialog
        AlertDialog show = new AlertDialog.Builder(ReleaseGoodsActivity.this)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(
                        getResources().getString(R.string.release_goods94),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String str1 = "", str2 = "", str3 = "";
                                try {
                                    str1 = goodsClassInfo.goods_class
                                            .get(goodsclassifypointer1).gc_name;
                                } catch (Exception e) {
                                }
                                try {
                                    str2 = goodsClassInfo.goods_class
                                            .get(goodsclassifypointer1).class2
                                            .get(goodsclassifypointer2).gc_name;
                                } catch (Exception e) {
                                }
                                try {
                                    str3 = goodsClassInfo.goods_class
                                            .get(goodsclassifypointer1).class2
                                            .get(goodsclassifypointer2).class3
                                            .get(goodsclassifypointer3).gc_name;
                                } catch (Exception e) {
                                }
                                tv_classify.setText(str1 + ">" + str2 + ">"
                                        + str3);
                                // 新增商品
                                netRun.AddGoods(gc_id, goods_class_type_id);
                                // 品牌
                                netRun.BrandDatails(goods_class_type_id, gc_id);
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (isgoodsclassify) {
                                    finish();
                                }
                            }
                        }).show();
        show.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    /**
     * Spinner适配
     *
     * @author Administrator
     */
    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        List<String> items = new ArrayList<String>();

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, final List<String> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getDropDownView(final int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(12);
            int px2dip = dip2px(context, 7);
            int px2dip2 = dip2px(context, 10);
            tv.setPadding(px2dip2, px2dip, px2dip2, px2dip);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(12);
            return convertView;
        }
    }

    /**
     * 图片空间
     */
    private class ImgAdapter extends BaseAdapter {
        List<ImageSpaceInfo.datas.pic_list> pic_list;

        public ImgAdapter(List<ImageSpaceInfo.datas.pic_list> pic_list) {
            this.pic_list = pic_list;
        }

        @Override
        public int getCount() {
            return pic_list.size();
        }

        @Override
        public Object getItem(int position) {
            return pic_list == null ? null : pic_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_imgspace, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final ImageSpaceInfo.datas.pic_list pic_list = this.pic_list
                    .get(position);
            bitmapUtils.display(holder.iv_img, pic_list.full_path);
            holder.iv_img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 选择图片
                    imagepath = new File(pic_list.apic_cover);
                    bitmapUtils.display(iv_goodsimg, pic_list.full_path);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_img, iv_img_del;

            public ViewHolder(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                iv_img_del = (ImageView) convertView
                        .findViewById(R.id.iv_img_del);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 图片空间2
     */
    private class ImgAdapter2 extends BaseAdapter {
        List<ImageSpaceInfo.datas.pic_list> pic_list;

        public ImgAdapter2(List<ImageSpaceInfo.datas.pic_list> pic_list) {
            this.pic_list = pic_list;
        }

        @Override
        public int getCount() {
            return pic_list.size();
        }

        @Override
        public Object getItem(int position) {
            return pic_list == null ? null : pic_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_imgspace, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final ImageSpaceInfo.datas.pic_list pic_list = this.pic_list
                    .get(position);
            bitmapUtils.display(holder.iv_img, pic_list.full_path);
            holder.iv_img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 图片添加
                    imgInfoAdapter.additem(pic_list);
                }
            });

            return convertView;
        }

        class ViewHolder {
            ImageView iv_img, iv_img_del;

            public ViewHolder(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                iv_img_del = (ImageView) convertView
                        .findViewById(R.id.iv_img_del);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 详情列表
     */
    private class ImgInfoAdapter extends BaseAdapter {
        List<ImageSpaceInfo.datas.pic_list> pic_list = new ArrayList<>();

        public void setdata(List<ImageSpaceInfo.datas.pic_list> pic_list) {
            this.pic_list = pic_list;
            notifyDataSetChanged();
        }

        /**
         * 获取已添加图片
         *
         * @return
         */
        public String getimg() {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < 5; i++) {
                if (i < pic_list.size()) {
                    if (pic_list.get(i).apic_cover != null) {
                        stringBuffer.append(pic_list.get(i).full_path + "|");
                    }
                }
            }
            String s = stringBuffer.toString();
            if (s != null && s.length() != 0) {
                s = s.substring(0, s.length() - 1);
            }
            return s;
        }

        public void additem(ImageSpaceInfo.datas.pic_list pic) {
            if (this.pic_list.size() < 5) {
                this.pic_list.add(pic);
                notifyDataSetChanged();
            } else {
                Toast.makeText(ReleaseGoodsActivity.this,
                        getString(R.string.release_goods107),
                        Toast.LENGTH_SHORT).show();
            }
        }

        public List<ImageSpaceInfo.datas.pic_list> getdata() {
            return this.pic_list;
        }

        @Override
        public int getCount() {
            return pic_list.size();
        }

        @Override
        public Object getItem(int position) {
            return pic_list == null ? null : pic_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_imgspace, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final ImageSpaceInfo.datas.pic_list pic = this.pic_list
                    .get(position);
            bitmapUtils.display(holder.iv_img, pic.full_path);
            holder.iv_img_del.setVisibility(View.VISIBLE);
            holder.iv_img_del.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 删除
                    pic_list.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_img, iv_img_del;

            public ViewHolder(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                iv_img_del = (ImageView) convertView
                        .findViewById(R.id.iv_img_del);
                convertView.setTag(this);
            }
        }
    }

    // 品牌索引ABCDEFGHIJKLMNOPQRSTUVWXYZ
    private String[] BrandIndex = new String[]{"A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 品牌索引
     */
    private class BrandIndexAdapter extends BaseAdapter {
        BrandListAdapter blad;
        TextView tv_index;
        ListView lv_pp;
        LinearLayout ll_ts;

        public BrandIndexAdapter(BrandListAdapter blad, TextView tv_index,
                                 ListView lv_pp, LinearLayout ll_ts) {
            this.blad = blad;
            this.tv_index = tv_index;
            this.lv_pp = lv_pp;
            this.ll_ts = ll_ts;
        }

        @Override
        public int getCount() {
            return BrandIndex.length;
        }

        @Override
        public Object getItem(int position) {
            return BrandIndex == null ? null : BrandIndex[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_brandindex, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_index.setText(BrandIndex[position]);
            holder.tv_index.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 索引
                    if (brandDatailsInfo != null
                            && brandDatailsInfo.brand_list != null) {
                        String str = BrandIndex[position];
                        List<BrandDatailsInfo.brand_list> brand_list = new ArrayList<BrandDatailsInfo.brand_list>();
                        boolean isnull = true;
                        for (int i = 0; i < brandDatailsInfo.brand_list.size(); i++) {
                            if (brandDatailsInfo.brand_list.get(i).brand_initial
                                    .equals(str)) {
                                brand_list.add(brandDatailsInfo.brand_list
                                        .get(i));
                                isnull = false;
                            }
                        }
                        blad.setdata(brand_list);
                        if (isnull) {
                            ll_ts.setVisibility(View.VISIBLE);
                            lv_pp.setVisibility(View.GONE);
                            tv_index.setText(str);
                        } else {
                            ll_ts.setVisibility(View.GONE);
                            lv_pp.setVisibility(View.VISIBLE);
                            tv_index.setText("");
                        }
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_index;

            public ViewHolder(View convertView) {
                tv_index = (TextView) convertView.findViewById(R.id.tv_index);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 品牌列表
     */
    private class BrandListAdapter extends BaseAdapter {
        List<BrandDatailsInfo.brand_list> brand_list;

        public BrandListAdapter(List<BrandDatailsInfo.brand_list> brand_list) {
            this.brand_list = brand_list;
        }

        public void setdata(List<BrandDatailsInfo.brand_list> brand_list) {
            this.brand_list = brand_list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return brand_list.size();
        }

        @Override
        public Object getItem(int position) {
            return brand_list == null ? null : brand_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_brand, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final BrandDatailsInfo.brand_list brand = this.brand_list
                    .get(position);
            holder.tv_zm.setText(brand.brand_initial);
            holder.tv_name.setText(brand.brand_name);
            holder.ll_item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 确定
                    tv_pp.setText(brand.brand_name);
                    myDialog.dismiss();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_zm, tv_name;
            LinearLayout ll_item;

            public ViewHolder(View convertView) {
                tv_zm = (TextView) convertView.findViewById(R.id.tv_zm);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 商品属性
     */
    private class AttributeAdapter extends BaseAdapter {
        List<FBGoodsInfo.attr_list> attr_list;
        List<EditorGoodsInfo.attr_list> attr_list2;

        public AttributeAdapter(
                List<FBGoodsInfo.attr_list> attr_list,
                List<EditorGoodsInfo.attr_list> attr_list2) {
            if (_goods.equals("redact")) {
                this.attr_list2 = attr_list2;
            } else if (_goods.equals("add")) {
                this.attr_list = attr_list;
            }
        }

        /**
         * 获得商品属性集合数组json
         *
         * @return
         */
        public String getattrjson() {
            JSONArray jsonArray = new JSONArray();
            if (_goods.equals("redact")) {
                for (int i = 0; i < attr_list2.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    String choosename = attr_list2.get(i).choosename;
                    String chooseid = "";
                    if (choosename == null || choosename.equals("")) {
                        choosename = "不限";
                        chooseid = "0";
                    } else {
                        chooseid = getitemid(choosename);
                    }
                    try {
                        jsonObject.put("group_id", attr_list2.get(i).attr_id);
                        jsonObject.put("group_name",
                                attr_list2.get(i).attr_name);
                        jsonObject.put("child_id", chooseid);
                        jsonObject.put("choosename", choosename);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
            } else if (_goods.equals("add")) {
                for (int i = 0; i < attr_list.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    String choosename = attr_list.get(i).choosename;
                    String chooseid = "";
                    if (choosename == null || choosename.equals("")) {
                        choosename = "不限";
                        chooseid = "0";
                    } else {
                        chooseid = getitemid(choosename);
                    }
                    try {
                        jsonObject.put("group_id", attr_list.get(i).attr_id);
                        jsonObject
                                .put("group_name", attr_list.get(i).attr_name);
                        jsonObject.put("child_id", chooseid);
                        jsonObject.put("child_id", choosename);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray.toString();
        }

        /**
         * 获取ID
         *
         * @param str
         * @return
         */
        private String getitemid(String str) {
            if (_goods.equals("redact")) {
                for (int i = 0; i < attr_list2.size(); i++) {
                    for (int j = 0; j < attr_list2.get(i).value.size(); j++) {
                        if (attr_list2.get(i).value.get(j).attr_value_name
                                .equals(str)) {
                            return attr_list2.get(i).value.get(j).attr_value_id;
                        }
                    }
                }
            } else if (_goods.equals("add")) {
                for (int i = 0; i < attr_list.size(); i++) {
                    for (int j = 0; j < attr_list.get(i).value.size(); j++) {
                        if (attr_list.get(i).value.get(j).attr_value_name
                                .equals(str)) {
                            return attr_list.get(i).value.get(j).attr_value_id;
                        }
                    }
                }
            }
            return "0";
        }

        @Override
        public int getCount() {
            if (_goods.equals("redact")) {
                return attr_list2 == null ? 0 : attr_list2.size();
            } else if (_goods.equals("add")) {
                return attr_list == null ? 0 : attr_list.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (_goods.equals("redact")) {
                return attr_list2 == null ? null : attr_list2.get(position);
            } else if (_goods.equals("add")) {
                return attr_list == null ? null : attr_list.get(position);
            } else {
                return position;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_attribute, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            if (_goods.equals("redact")) {
                // 编辑
                final EditorGoodsInfo.attr_list attr = this.attr_list2
                        .get(position);
                holder.tv_name.setText(attr.attr_name);
                List<String> menu = new ArrayList<>();
                for (int i = 0; i < attr.value.size(); i++) {
                    menu.add(attr.value.get(i).attr_value_name);
                }
                SpinnerAdapter adapter = new SpinnerAdapter(
                        ReleaseGoodsActivity.this,
                        android.R.layout.simple_spinner_item, menu);
                holder.sp_sx.setAdapter(adapter);
                if (editorGoodsInfo.attr_checked != null) {
                    try {
                        String str = (String) editorGoodsInfo.attr_checked
                                .get(position);
                        for (int j = 0; j < attr.value.size(); j++) {
                            if (str.equals(attr.value.get(j).attr_value_id)) {
                                attr.choosename = attr.value.get(j).attr_value_name;
                                holder.sp_sx.setSelection(j, true);
                                break;
                            }
                        }
                    } catch (Exception e) {

                    }
                    holder.sp_sx
                            .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(
                                        AdapterView<?> parent, View view,
                                        int position, long id) {
                                    attr.choosename = holder.sp_sx
                                            .getSelectedItem().toString();
                                }

                                @Override
                                public void onNothingSelected(
                                        AdapterView<?> parent) {
                                }
                            });

                }
            } else if (_goods.equals("add")) {
                // 发布商品
                final FBGoodsInfo.attr_list attr = this.attr_list.get(position);
                holder.tv_name.setText(attr.attr_name);
                List<String> menu = new ArrayList<>();
                for (int i = 0; i < attr.value.size(); i++) {
                    menu.add(attr.value.get(i).attr_value_name);
                }
                SpinnerAdapter adapter = new SpinnerAdapter(
                        ReleaseGoodsActivity.this,
                        android.R.layout.simple_spinner_item, menu);
                holder.sp_sx.setAdapter(adapter);
                holder.sp_sx
                        .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent,
                                                       View view, int position, long id) {
                                attr.choosename = holder.sp_sx
                                        .getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

            }
            return convertView;
        }

        class ViewHolder {
            TextView tv_name;
            Spinner sp_sx;

            public ViewHolder(View convertView) {
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                sp_sx = (Spinner) convertView.findViewById(R.id.sp_sx);
                convertView.setTag(this);
            }
        }
    }

    /**
     * Spinner店铺分类适配
     *
     * @author Administrator
     */
    private class Spinner2Adapter extends ArrayAdapter<StoreClassifyInfo> {
        Context context;
        List<StoreClassifyInfo> items = new ArrayList<StoreClassifyInfo>();

        public Spinner2Adapter(final Context context,
                               final int textViewResourceId,
                               final List<StoreClassifyInfo> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getDropDownView(final int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position).stc_name);
            int px2dip = dip2px(context, 7);
            int px2dip2 = dip2px(context, 10);
            int px2dip3 = dip2px(context, 5);
            int px2dip4 = dip2px(context, 15);
            if (!items.get(position).istitle) {
                // tv.setGravity(Gravity.CENTER);
                tv.setPadding(px2dip4, px2dip, px2dip2, px2dip);
            } else {
                tv.setPadding(px2dip3, px2dip, px2dip2, px2dip);
            }
            tv.setTextSize(12);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position).stc_name);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(12);
            return convertView;
        }
    }

    /**
     * 本店分类
     */
    private class StoreClassifyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return storeclassifysaze.size();
        }

        public void additem() {
            storeclassifysaze.add("請選擇");
            notifyDataSetChanged();
        }

        /**
         * 获得已选属性拼接ID
         *
         * @return
         */
        private String getjson() {
            if (_goods.equals("redact")) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int j = 0; j < editorGoodsInfo.store_goods_class.size(); j++) {
                    stringBuffer.append(editorGoodsInfo.store_goods_class
                            .get(j).stc_id + ",");
                    boolean isfind = false;
                    for (int k = 0; k < editorGoodsInfo.store_goods_class
                            .get(j).child.size(); k++) {
                        String stc_name = editorGoodsInfo.store_goods_class
                                .get(j).child.get(k).stc_name;
                        for (int i = 0; i < storeclassifysaze.size(); i++) {
                            if (stc_name.equals(storeclassifysaze.get(i))) {
                                isfind = true;
                                stringBuffer
                                        .append(editorGoodsInfo.store_goods_class
                                                .get(j).child.get(k).stc_id
                                                + ",");
                            }
                        }
                        if (!isfind) {
                            stringBuffer.append("0,");
                        }
                    }
                }
                String s = stringBuffer.toString();
                if (s != null && s.length() != 0) {
                    s = s.substring(0, s.length() - 1);
                }
                return s;
            } else if (_goods.equals("add")) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int j = 0; j < fBGoodsInfo.store_goods_class.size(); j++) {
                    stringBuffer
                            .append(fBGoodsInfo.store_goods_class.get(j).stc_id
                                    + ",");
                    boolean isfind = false;
                    for (int k = 0; k < fBGoodsInfo.store_goods_class.get(j).child
                            .size(); k++) {
                        String stc_name = fBGoodsInfo.store_goods_class.get(j).child
                                .get(k).stc_name;
                        for (int i = 0; i < storeclassifysaze.size(); i++) {
                            if (stc_name.equals(storeclassifysaze.get(i))) {
                                isfind = true;
                                stringBuffer
                                        .append(fBGoodsInfo.store_goods_class
                                                .get(j).child.get(k).stc_id
                                                + ",");
                            }
                        }
                        if (!isfind) {
                            stringBuffer.append("0,");
                        }
                    }
                }
                String s = stringBuffer.toString();
                if (s != null && s.length() != 0) {
                    s = s.substring(0, s.length() - 1);
                }
                return s;
            }
            return "";
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_storeclassify, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            if (storeclass != null) {
                Spinner2Adapter adapter = new Spinner2Adapter(
                        ReleaseGoodsActivity.this,
                        android.R.layout.simple_spinner_item, storeclass);
                holder.sp_classify.setAdapter(adapter);
                try {
                    if (_goods.equals("redact")) {
                        // 默认选中
                        String s = (String) editorGoodsInfo.store_class_goods
                                .get(position);
                        for (int j = 0; j < storeclass.size(); j++) {
                            if (s.equals(storeclass.get(j).mrid)) {
                                holder.sp_classify.setSelection(j, true);
                                storeclassifysaze.set(position,
                                        storeclass.get(j).stc_name);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                }
                holder.sp_classify
                        .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent,
                                                       View view, int pos, long id) {
                                if (isbdclassify) {
                                    isbdclassify = false;
                                    storeclassifysaze.set(position,
                                            storeclass.get(pos).stc_name);
                                    handler.sendEmptyMessageDelayed(10087, 1000);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

            }
            return convertView;
        }

        class ViewHolder {
            Spinner sp_classify;

            public ViewHolder(View convertView) {
                sp_classify = (Spinner) convertView
                        .findViewById(R.id.sp_classify);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 规格1
     */
    private class StandardAdapter extends BaseAdapter {
        List<EditorGoodsInfo.spec_list> spec_list;
        List<FBGoodsInfo.spec_list> spec_list2;
        int lastid = -1;

        public StandardAdapter(List<EditorGoodsInfo.spec_list> spec_list,
                               List<FBGoodsInfo.spec_list> spec_list2) {
            lastid = -1;
            if (_goods.equals("redact")) {
                this.spec_list = spec_list;
            } else if (_goods.equals("add")) {
                this.spec_list2 = spec_list2;
            }
        }

        /**
         * 获取选中的规格json
         *
         * @return
         */
        public List<String> getChooseJson() {
            if (_goods.equals("redact")) {
                JSONArray jsonArray = new JSONArray();
                JSONArray jsonArra2 = new JSONArray();
                try {
                    for (int k = 0; k < spec_list.size(); k++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("spec_name", spec_list.get(k).sp_name);
                        jsonObject.put("spec_id", spec_list.get(k).sp_id);
                        jsonArray.put(jsonObject);
                        JSONArray jsonArra3 = new JSONArray();
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("spec_id", spec_list.get(k).sp_id);
                        for (int j = 0; j < spec_list.get(k).value.size(); j++) {
                            if (spec_list.get(k).value.get(j).ischoose) {
                                JSONObject jsonObject2 = new JSONObject();
                                jsonObject2
                                        .put("sp_value_id",
                                                spec_list.get(k).value.get(j).sp_value_id);
                                jsonObject2
                                        .put("sp_value_name",
                                                spec_list.get(k).value.get(j).sp_value_name);
                                jsonArra3.put(jsonObject2);
                            }
                        }
                        jsonObject1.put("value", jsonArra3);
                        jsonArra2.put(jsonObject1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<String> list = new ArrayList<>();
                list.add(jsonArray.toString());
                list.add(jsonArra2.toString());
                return list;
            } else if (_goods.equals("add")) {
                JSONArray jsonArray = new JSONArray();
                JSONArray jsonArra2 = new JSONArray();
                try {
                    for (int k = 0; k < spec_list2.size(); k++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("spec_name", spec_list2.get(k).sp_name);
                        jsonObject.put("spec_id", spec_list2.get(k).sp_id);
                        jsonArray.put(jsonObject);
                        JSONArray jsonArra3 = new JSONArray();
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("spec_id", spec_list2.get(k).sp_id);
                        for (int j = 0; j < spec_list2.get(k).value.size(); j++) {
                            if (spec_list2.get(k).value.get(j).ischoose) {
                                JSONObject jsonObject2 = new JSONObject();
                                jsonObject2
                                        .put("sp_value_id",
                                                spec_list2.get(k).value.get(j).sp_value_id);
                                jsonObject2
                                        .put("sp_value_name",
                                                spec_list2.get(k).value.get(j).sp_value_name);
                                jsonArra3.put(jsonObject2);
                            }
                        }
                        jsonObject1.put("value", jsonArra3);
                        jsonArra2.put(jsonObject1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<String> list = new ArrayList<>();
                list.add(jsonArray.toString());
                list.add(jsonArra2.toString());
                return list;
            }
            return null;
        }

        /**
         * 修改库存列表
         */
        public void setKC() {
            if (_goods.equals("redact")) {
                // 得到选中的数组
                List<List<EditorGoodsInfo.spec_list.value>> chooselist = new ArrayList<>();
                for (int k = 0; k < spec_list.size(); k++) {
                    List<EditorGoodsInfo.spec_list.value> list2 = new ArrayList<>();
                    for (int j = 0; j < spec_list.get(k).value.size(); j++) {
                        if (spec_list.get(k).value.get(j).ischoose) {
                            list2.add(spec_list.get(k).value.get(j));
                        }
                    }
                    chooselist.add(list2);
                }
                // 得到所有规格
                List<InventoryInfo> speclist = new ArrayList<>();
                List<InventoryInfo.data> li = new ArrayList<>();
                kc2(chooselist, li, 0, speclist);
                inventoryAdapter.updata(speclist);
            } else if (_goods.equals("add")) {
                // 得到选中的数组
                List<List<FBGoodsInfo.spec_list.value>> chooselist = new ArrayList<>();
                for (int k = 0; k < spec_list2.size(); k++) {
                    List<FBGoodsInfo.spec_list.value> list2 = new ArrayList<>();
                    for (int j = 0; j < spec_list2.get(k).value.size(); j++) {
                        if (spec_list2.get(k).value.get(j).ischoose) {
                            list2.add(spec_list2.get(k).value.get(j));
                        }
                    }
                    chooselist.add(list2);
                }
                // 得到所有规格
                List<InventoryInfo> speclist = new ArrayList<>();
                List<InventoryInfo.data> li = new ArrayList<>();
                kc3(chooselist, li, 0, speclist);
                inventoryAdapter.updata(speclist);
            }

        }

        // 添加
        public void additem(EditorGoodsInfo.spec_list.value spec,
                            FBGoodsInfo.spec_list.value spec2, int id) {
            if (_goods.equals("redact")) {
                this.spec_list.get(id).value.add(spec);
            } else if (_goods.equals("add")) {
                this.spec_list2.get(id).value.add(spec2);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (_goods.equals("redact")) {
                return spec_list.size();
            } else if (_goods.equals("add")) {
                return spec_list2.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (_goods.equals("redact")) {
                return spec_list == null ? null : spec_list.get(position);
            } else if (_goods.equals("add")) {
                return spec_list2 == null ? null : spec_list2.get(position);
            }
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_standard, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();

            if (_goods.equals("redact")) {
                // TODO 规格
                final EditorGoodsInfo.spec_list spec_list = this.spec_list
                        .get(position);
                if (parent.getChildCount() == position) {
                    if (position != lastid) {
                        holder.ed_ggname.setText(spec_list.sp_name);
                        holder.ed_ggname
                                .addTextChangedListener(new MyAdapterListener(
                                        spec_list, position, 1));
                        lastid = position;
                    }
                }
                Standar2dAdapter s2adapter = new Standar2dAdapter(
                        spec_list.value, null);
                holder.mgv_gg.setAdapter(s2adapter);
                holder.tv_add.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 添加规格
                        pid = position;
                        ShowAddSpec(gc_id, spec_list.sp_id);
                    }
                });
            } else if (_goods.equals("add")) {
                final FBGoodsInfo.spec_list spec_list = this.spec_list2
                        .get(position);
                if (parent.getChildCount() == position) {
                    if (position != lastid) {
                        holder.ed_ggname.setText(spec_list.sp_name);
                        holder.ed_ggname
                                .addTextChangedListener(new MyAdapterListener(
                                        this.spec_list2.get(position),
                                        position, 2));
                        lastid = position;
                    }
                }
                Standar2dAdapter s2adapter = new Standar2dAdapter(null,
                        spec_list.value);
                holder.mgv_gg.setAdapter(s2adapter);
                holder.tv_add.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 添加规格
                        pid = position;
                        ShowAddSpec(gc_id, spec_list.sp_id);
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            EditText ed_ggname;
            MyGridView mgv_gg;
            TextView tv_add;

            public ViewHolder(View convertView) {
                ed_ggname = (EditText) convertView.findViewById(R.id.ed_ggname);
                mgv_gg = (MyGridView) convertView.findViewById(R.id.mgv_gg);
                tv_add = (TextView) convertView.findViewById(R.id.tv_add);
                convertView.setTag(this);
            }
        }

        class MyAdapterListener implements TextWatcher {
            int position, type;
            EditorGoodsInfo.spec_list spec;
            FBGoodsInfo.spec_list spec2;

            public MyAdapterListener(EditorGoodsInfo.spec_list spec,
                                     int position, int type) {
                this.position = position;
                this.type = type;
                this.spec = spec;
            }

            public MyAdapterListener(FBGoodsInfo.spec_list spec2, int position,
                                     int type) {
                this.position = position;
                this.type = type;
                this.spec2 = spec2;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (type) {
                    case 1:
                        if (!spec.sp_name.equals(s.toString())) {
                            Log.i("----------------", "监听1  " + position);
                            spec.sp_name = s.toString();
                        }
                        break;
                    case 2:
                        if (!spec2.sp_name.equals(s.toString())) {
                            spec2.sp_name = s.toString();
                            Log.i("----------------", "监听2  " + position + "  "
                                    + spec2.sp_name);
                        }
                        break;
                }
            }
        }
    }

    /**
     * 规格2
     */
    private class Standar2dAdapter extends BaseAdapter {
        List<EditorGoodsInfo.spec_list.value> value;
        List<FBGoodsInfo.spec_list.value> value2;
        int lastid = -1;

        public Standar2dAdapter(List<EditorGoodsInfo.spec_list.value> value,
                                List<FBGoodsInfo.spec_list.value> value2) {
            lastid = -1;
            if (_goods.equals("redact")) {
                this.value = value;
            } else if (_goods.equals("add")) {
                this.value2 = value2;
            }
        }

        @Override
        public int getCount() {
            if (_goods.equals("redact")) {
                return value.size();
            } else if (_goods.equals("add")) {
                return value2.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (_goods.equals("redact")) {
                return value == null ? null : value.get(position);
            } else if (_goods.equals("add")) {
                return value2 == null ? null : value2.get(position);
            }
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_spec, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (_goods.equals("redact")) {
                if (parent.getChildCount() == position) {
                    if (position != lastid) {
                        holder.cb_choose
                                .setChecked(this.value.get(position).ischoose);
                        holder.ed_ggname
                                .setText(this.value.get(position).sp_value_name);
                        holder.ed_ggname
                                .addTextChangedListener(new MyAdapterListener(
                                        this.value.get(position), position, 1));
                        lastid = position;
                    }
                }
                holder.cb_choose
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(
                                    CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    Standar2dAdapter.this.value.get(position).ischoose = true;
                                } else {
                                    Standar2dAdapter.this.value.get(position).ischoose = false;
                                }
                                // 更新库存列表
                                standardAdapter.setKC();
                            }
                        });
            } else if (_goods.equals("add")) {
                if (parent.getChildCount() == position) {
                    if (position != lastid) {
                        holder.cb_choose
                                .setChecked(value2.get(position).ischoose);
                        holder.ed_ggname
                                .setText(value2.get(position).sp_value_name);
                        holder.ed_ggname
                                .addTextChangedListener(new MyAdapterListener(
                                        this.value2.get(position), position, 2));
                        lastid = position;
                    }
                }
                holder.cb_choose
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(
                                    CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    value2.get(position).ischoose = true;
                                } else {
                                    value2.get(position).ischoose = false;
                                }
                                // 更新库存列表
                                standardAdapter.setKC();
                            }
                        });
            }
            return convertView;
        }

        class ViewHolder {
            CheckBox cb_choose;
            EditText ed_ggname;

            public ViewHolder(View convertView) {
                cb_choose = (CheckBox) convertView.findViewById(R.id.cb_choose);
                ed_ggname = (EditText) convertView.findViewById(R.id.ed_ggname);
                convertView.setTag(this);
            }
        }

        class MyAdapterListener implements TextWatcher {
            int position, type;
            EditorGoodsInfo.spec_list.value value;
            FBGoodsInfo.spec_list.value value2;

            public MyAdapterListener(EditorGoodsInfo.spec_list.value value,
                                     int position, int type) {
                this.position = position;
                this.type = type;
                this.value = value;
            }

            public MyAdapterListener(FBGoodsInfo.spec_list.value value2,
                                     int position, int type) {
                this.position = position;
                this.type = type;
                this.value2 = value2;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (type) {
                    case 1:
                        if (!value.sp_value_name.equals(s.toString())) {
                            Log.i("----------------", "监听1  " + position);
                            value.sp_value_name = s.toString();
                        }
                        break;
                    case 2:
                        if (!value2.sp_value_name.equals(s.toString())) {
                            value2.sp_value_name = s.toString();
                            Log.i("----------------", "监听2  " + position + "  "
                                    + value2.sp_value_name);
                        }
                        break;
                }
            }
        }

    }

    /**
     * 库存配置
     */
    private class InventoryAdapter extends BaseAdapter {
        List<InventoryInfo> list;
        List<List<EditText>> edit;
        int lastid = -1;

        public InventoryAdapter(List<InventoryInfo> list) {
            this.list = list;
            this.edit = new ArrayList<>();
            lastid = -1;
        }

        /**
         * 获取库存
         *
         * @return
         */
        public void getInventory() {
            int number = 0;
            for (int i = 0; i < list.size(); i++) {
                String stock = list.get(i).stock;
                if (!TextUtils.isEmpty(stock)) {
                    int i1 = Integer.parseInt(stock);
                    number += i1;
                }
            }
            ed_input18.setText(number + "");
        }

        /**
         * 获取库存json
         *
         * @return
         */
        public String getKCjson() {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                JSONArray Array = new JSONArray();
                try {
                    jsonObject.put("sid", list.get(i).sid);
                    jsonObject.put("name", list.get(i).name);
                    jsonObject.put("marketprice", list.get(i).marketprice);
                    jsonObject.put("price", list.get(i).price);
                    jsonObject.put("goods_id", list.get(i).id);
                    jsonObject.put("stock", list.get(i).stock);
                    jsonObject.put("alarm", list.get(i).alarm);
                    jsonObject.put("sku", list.get(i).sku);
                    jsonObject
                            .put("sp_value_color", list.get(i).sp_value_color);
                    jsonObject.put("level_0_price", list.get(i).level_0_price);
                    jsonObject.put("level_1_price", list.get(i).level_1_price);
                    jsonObject.put("level_2_price", list.get(i).level_2_price);
                    jsonObject.put("level_3_price", list.get(i).level_3_price);
                    jsonObject.put("level_0_auth_price",
                            list.get(i).level_0_auth_price);
                    jsonObject.put("level_1_auth_price",
                            list.get(i).level_1_auth_price);
                    jsonObject.put("level_2_auth_price",
                            list.get(i).level_2_auth_price);
                    jsonObject.put("level_3_auth_price",
                            list.get(i).level_3_auth_price);
                    jsonObject.put("goods_points_offset",
                            list.get(i).goods_points_offset);
                    jsonObject.put("costprice", list.get(i).costprice);

                    for (int j = 0; j < list.get(i).data.size(); j++) {
                        JSONObject jsObject = new JSONObject();
                        jsObject.put("sp_value_id",
                                list.get(i).data.get(j).sp_value_id);
                        jsObject.put("sp_value_name",
                                list.get(i).data.get(j).sp_value_name);

                        Array.put(jsObject);
                    }
                    jsonObject.put("value", Array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
            Log.i("----------------", "库存" + jsonArray.toString());
            return jsonArray.toString();
        }

        public void updata(List<InventoryInfo> list) {
            lastid = -1;
            this.edit = new ArrayList<>();
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list == null ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_inventory, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final InventoryInfo inventoryInfo = list.get(position);
            if (parent.getChildCount() == position) {
                if (position != lastid) {
                    Log.i("-------------------", "" + position);
                    holder.ed_inputscj
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 1));
                    holder.ed_inputprice
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 2));
                    holder.ed_inputinventory
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 3));
                    holder.ed_inputwarning
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 4));
                    holder.ed_inputnum
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 5));
                    holder.ed_inputcostprice
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 6));
                    holder.ed_inputmenberprrice1
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 7));
                    holder.ed_inputmenberprrice2
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 8));
                    holder.ed_inputmenberprrice3
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 9));
                    holder.ed_inputmenberprrice4
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 10));
                    holder.ed_inputmenberprrice5
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 11));
                    holder.ed_inputmenberprrice6
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 12));
                    holder.ed_inputmenberprrice7
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 13));
                    holder.ed_inputmenberprrice8
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 14));
                    holder.ed_inputintegral
                            .addTextChangedListener(new MyAdapterListener(
                                    inventoryInfo, position, 15));
                    lastid = position;
                }
            }
            holder.tv_specname.setText(getString(R.string.release_goods130)
                    + " " + inventoryInfo.name);
            holder.ed_inputscj.setText(inventoryInfo.marketprice);
            holder.ed_inputprice.setText(inventoryInfo.price);
            holder.ed_inputinventory.setText(inventoryInfo.stock);
            holder.ed_inputwarning.setText(inventoryInfo.alarm);
            holder.ed_inputnum.setText(inventoryInfo.sku);
            holder.ed_inputmenberprrice1.setText(inventoryInfo.level_0_price);
            holder.ed_inputmenberprrice2.setText(inventoryInfo.level_1_price);
            holder.ed_inputmenberprrice3.setText(inventoryInfo.level_2_price);
            holder.ed_inputmenberprrice4.setText(inventoryInfo.level_3_price);
            holder.ed_inputmenberprrice5
                    .setText(inventoryInfo.level_0_auth_price);
            holder.ed_inputmenberprrice6
                    .setText(inventoryInfo.level_1_auth_price);
            holder.ed_inputmenberprrice7
                    .setText(inventoryInfo.level_2_auth_price);
            holder.ed_inputmenberprrice8
                    .setText(inventoryInfo.level_3_auth_price);
            holder.ed_inputintegral.setText(inventoryInfo.goods_points_offset);
            holder.ed_inputcostprice.setText(inventoryInfo.costprice);
            return convertView;
        }

        class ViewHolder {
            TextView tv_specname;
            EditText ed_inputscj, ed_inputprice, ed_inputinventory,
                    ed_inputwarning, ed_inputnum, ed_inputcostprice,
                    ed_inputmenberprrice1, ed_inputmenberprrice2,
                    ed_inputmenberprrice3, ed_inputmenberprrice4,
                    ed_inputmenberprrice5, ed_inputmenberprrice6,
                    ed_inputmenberprrice7, ed_inputmenberprrice8,
                    ed_inputintegral;

            public ViewHolder(View convertView) {
                tv_specname = (TextView) convertView
                        .findViewById(R.id.tv_specname);
                ed_inputscj = (EditText) convertView
                        .findViewById(R.id.ed_inputscj);
                ed_inputprice = (EditText) convertView
                        .findViewById(R.id.ed_inputprice);
                ed_inputinventory = (EditText) convertView
                        .findViewById(R.id.ed_inputinventory);
                ed_inputwarning = (EditText) convertView
                        .findViewById(R.id.ed_inputwarning);
                ed_inputnum = (EditText) convertView
                        .findViewById(R.id.ed_inputnum);
                ed_inputcostprice = (EditText) convertView
                        .findViewById(R.id.ed_inputcostprice);
                ed_inputmenberprrice1 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice1);
                ed_inputmenberprrice2 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice2);
                ed_inputmenberprrice3 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice3);
                ed_inputmenberprrice4 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice4);
                ed_inputmenberprrice5 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice5);
                ed_inputmenberprrice6 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice6);
                ed_inputmenberprrice7 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice7);
                ed_inputmenberprrice8 = (EditText) convertView
                        .findViewById(R.id.ed_inputmenberprrice8);
                ed_inputintegral = (EditText) convertView
                        .findViewById(R.id.ed_inputintegral);
                convertView.setTag(this);
            }
        }

        class MyAdapterListener implements TextWatcher {
            InventoryInfo inventoryInfo;
            int position, type;

            public MyAdapterListener(InventoryInfo inventoryInfo, int position,
                                     int type) {
                this.position = position;
                this.inventoryInfo = inventoryInfo;
                this.type = type;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (type) {
                    case 1:
                        if (!inventoryInfo.marketprice.equals(s.toString())) {
                            Log.i("----------------", "监听1  " + position);
                            inventoryInfo.marketprice = s.toString();
                        }
                        break;
                    case 2:
                        if (!inventoryInfo.price.equals(s.toString())) {
                            Log.i("----------------", "监听2  " + position);
                            inventoryInfo.price = s.toString();
                        }
                        break;
                    case 3:
                        if (!inventoryInfo.stock.equals(s.toString())) {
                            Log.i("----------------", "监听3  " + position);
                            inventoryInfo.stock = s.toString();
                        }
                        break;
                    case 4:
                        if (!inventoryInfo.alarm.equals(s.toString())) {
                            Log.i("----------------", "监听4  " + position);
                            inventoryInfo.alarm = s.toString();
                        }
                        break;
                    case 5:
                        if (!inventoryInfo.sku.equals(s.toString())) {
                            Log.i("----------------", "监听5  " + position);
                            inventoryInfo.sku = s.toString();
                        }
                        break;
                    case 6:
                        if (inventoryInfo.costprice != null
                                && !inventoryInfo.costprice.equals(s.toString())) {
                            Log.i("----------------", "监听6  " + position);
                            inventoryInfo.costprice = s.toString();
                        }
                        break;
                    case 7:
                        if (!inventoryInfo.level_0_price.equals(s.toString())) {
                            Log.i("----------------", "监听7  " + position);
                            inventoryInfo.level_0_price = s.toString();
                        }
                        break;
                    case 8:
                        if (!inventoryInfo.level_1_price.equals(s.toString())) {
                            Log.i("----------------", "监听8  " + position);
                            inventoryInfo.level_1_price = s.toString();
                        }
                        break;
                    case 9:
                        if (!inventoryInfo.level_2_price.equals(s.toString())) {
                            Log.i("----------------", "监听9  " + position);
                            inventoryInfo.level_2_price = s.toString();
                        }
                        break;
                    case 10:
                        if (!inventoryInfo.level_3_price.equals(s.toString())) {
                            Log.i("----------------", "监听10  " + position);
                            inventoryInfo.level_3_price = s.toString();
                        }
                        break;
                    case 11:
                        if (!inventoryInfo.level_0_auth_price.equals(s.toString())) {
                            Log.i("----------------", "监听11  " + position);
                            inventoryInfo.level_0_auth_price = s.toString();
                        }
                        break;
                    case 12:
                        if (!inventoryInfo.level_1_auth_price.equals(s.toString())) {
                            Log.i("----------------", "监听12  " + position);
                            inventoryInfo.level_1_auth_price = s.toString();
                        }
                        break;
                    case 13:
                        if (!inventoryInfo.level_2_auth_price.equals(s.toString())) {
                            Log.i("----------------", "监听13  " + position);
                            inventoryInfo.level_2_auth_price = s.toString();
                        }
                        break;
                    case 14:
                        if (!inventoryInfo.level_3_auth_price.equals(s.toString())) {
                            Log.i("----------------", "监听14  " + position);
                            inventoryInfo.level_3_auth_price = s.toString();
                        }
                        break;
                    case 15:
                        if (!inventoryInfo.goods_points_offset.equals(s.toString())) {
                            Log.i("----------------", "监听15  " + position);
                            inventoryInfo.goods_points_offset = s.toString();
                        }
                        break;
                }
            }
        }
    }

    /**
     * F码列表
     */
    private class FCodeAdapter extends BaseAdapter {
        List<EditorGoodsInfo.fcode_array> fcode_array;

        public FCodeAdapter(
                List<EditorGoodsInfo.fcode_array> fcode_array) {
            this.fcode_array = fcode_array;
        }

        @Override
        public int getCount() {
            return fcode_array.size();
        }

        @Override
        public Object getItem(int position) {
            return fcode_array == null ? null : fcode_array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ReleaseGoodsActivity.this,
                        R.layout.item_fcode, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            EditorGoodsInfo.fcode_array fcode_array = this.fcode_array
                    .get(position);
            String str = fcode_array.fc_state.equals("0") ? getString(R.string.release_goods134)
                    : getString(R.string.release_goods135);
            holder.tv_fcode.setText(fcode_array.fc_code + str);
            return convertView;
        }

        class ViewHolder {
            TextView tv_fcode;

            public ViewHolder(View convertView) {
                tv_fcode = (TextView) convertView.findViewById(R.id.tv_fcode);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 活动规则
     */
    private class DiscountAdapter extends RecyclerView.Adapter<ViewHolder> {
        public List<DiscountInfo> discount = new ArrayList<>();

        public DiscountAdapter(List<DiscountInfo> discount) {
            this.discount = discount;
        }

        /**
         * 添加item
         */
        public void addItem() {
            DiscountInfo info = new DiscountInfo();
            info.index = getItemCount() == 0 ? "1" : getItemCount() + "";
            discount.add(info);
            notifyDataSetChanged();
        }

        /**
         * 移除item
         */
        public void removeItem(int id) {
            discount.remove(id);
            notifyDataSetChanged();
        }

        /**
         * 修改数量
         *
         * @param str
         * @param id
         */
        public void updatanumber(String str, String str2, int id) {
            DiscountInfo discountInfo = discount.get(id);
            discountInfo.number = str;
            discountInfo.discount = str2;
            final Runnable r = new Runnable() {
                public void run() {
                    notifyDataSetChanged();
                }
            };
            handler.post(r);
        }

        public List<DiscountInfo> getdata() {
            return discount;
        }

        /**
         * 获取json数据
         *
         * @return
         */
        public String getjson() {
            JSONArray jsonArray = new JSONArray();
            try {
                for (int i = 0; i < discount.size(); i++) {
                    DiscountInfo discountInfo = discount.get(i);
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("index", i + "");
                    jsonObject.put("count", discountInfo.number);
                    jsonObject.put("discount", discountInfo.discount);
                    jsonArray.put(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("----------------", "活动规则  " + jsonArray.toString());
            return jsonArray.toString();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(
                    ReleaseGoodsActivity.this).inflate(R.layout.item_discount,
                    parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            DiscountInfo discountInfo = discount.get(position);
            holder.tv_index.setText((position + 1) + "");
            holder.ed_number.setText(discountInfo.number);
            holder.ed_rules.setText(discountInfo.discount);

            holder.tv_remove.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 移除
                    if (discountAdapter.getItemCount() > 1) {
                        discountAdapter.removeItem(position);
                    }
                }
            });
            holder.ed_number.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    showDiscount(position);
                }
            });
            holder.ed_rules.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    showDiscount(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return discount.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_index, tv_remove, ed_number, ed_rules;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_index = (TextView) itemView.findViewById(R.id.tv_index);
            tv_remove = (TextView) itemView.findViewById(R.id.tv_remove);
            ed_number = (TextView) itemView.findViewById(R.id.ed_number);
            ed_rules = (TextView) itemView.findViewById(R.id.ed_rules);

        }
    }

    int pos = 0;

    /**
     * 折扣弹窗
     */
    public void showDiscount(int id) {
        pos = id;
        rl_activityrules.setFocusable(true);
        rl_activityrules.requestFocus();
        View v = View.inflate(ReleaseGoodsActivity.this,
                R.layout.dialog_inputdiscount, null);
        final EditText ev_number = (EditText) v.findViewById(R.id.ev_number);
        final EditText ev_discount = (EditText) v
                .findViewById(R.id.ev_discount);
        TextView tv_qd = (TextView) v.findViewById(R.id.tv_qd);
        TextView tv_qx = (TextView) v.findViewById(R.id.tv_qx);
        tv_qd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 确定
                String number = ev_number.getText().toString();
                String discount = ev_discount.getText().toString();
                if (TextUtils.isEmpty(number) || TextUtils.isEmpty(discount)) {
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.release_goods144),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int i = Integer.parseInt(discount);
                    if (i < 1 || i > 100) {
                        Toast.makeText(ReleaseGoodsActivity.this,
                                getString(R.string.rules_ts),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.rules_ts), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                discountAdapter.updatanumber(number, discount, pos);
                if (myDialog3 != null) {
                    myDialog3.dismiss();
                }
            }
        });
        tv_qx.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消
                if (myDialog3 != null) {
                    myDialog3.dismiss();
                }
            }
        });
        if (myDialog3 == null) {
            myDialog3 = new MyDialog(ReleaseGoodsActivity.this, getw() - 70,
                    550, v, R.style.loading_dialog);
            myDialog3.show();
        } else {
            myDialog3.show();
        }

    }

    /**
     * 得到所有已选择规格
     *
     * @param chooselist
     * @param pid
     */
    private void kc2(
            List<List<EditorGoodsInfo.spec_list.value>> chooselist,
            List<InventoryInfo.data> li, int pid, List<InventoryInfo> speclist) {
        if (pid < chooselist.size()) {
            for (int i = 0; i < chooselist.get(pid).size(); i++) {
                List<InventoryInfo.data> liy = new ArrayList<>();
                liy.addAll(li);
                liy.add(new InventoryInfo.data(chooselist.get(
                        pid).get(i).sp_value_id,
                        chooselist.get(pid).get(i).sp_value_name, ""));
                kc2(chooselist, liy, (pid + 1), speclist);
            }
        } else {
            InventoryInfo inventoryInfo = new InventoryInfo();
            inventoryInfo.data = li;
            String id = "", name = "";
            for (int j = 0; j < inventoryInfo.data.size(); j++) {
                // 拼接规格和名称
                id = id + inventoryInfo.data.get(j).sp_value_id;
                name = name + inventoryInfo.data.get(j).sp_value_name + " ";
                // 获取一级ID
                if (j == 0) {
                    for (int k = 0; k < editorGoodsInfo.spec_list.size(); k++) {
                        for (int i = 0; i < editorGoodsInfo.spec_list.get(k).value
                                .size(); i++) {
                            if (inventoryInfo.data.get(j).sp_value_name
                                    .equals(editorGoodsInfo.spec_list.get(k).value
                                            .get(i).sp_value_name)) {
                                inventoryInfo.sp_value_color = editorGoodsInfo.spec_list
                                        .get(k).value.get(i).sp_value_id;
                            }
                        }
                    }
                }
            }
            inventoryInfo.name = name;
            // 得到已填规格的参数
            for (int i = 0; i < editorGoodsInfo.sp_value.size(); i++) {
                if (id.equals(editorGoodsInfo.sp_value.get(i).sid)) {
                    inventoryInfo.sid = editorGoodsInfo.sp_value.get(i).sid;
                    inventoryInfo.marketprice = editorGoodsInfo.sp_value.get(i).marketprice;
                    inventoryInfo.price = editorGoodsInfo.sp_value.get(i).price;
                    inventoryInfo.id = editorGoodsInfo.sp_value.get(i).id;
                    inventoryInfo.stock = editorGoodsInfo.sp_value.get(i).stock;
                    inventoryInfo.alarm = editorGoodsInfo.sp_value.get(i).alarm;
                    inventoryInfo.sku = editorGoodsInfo.sp_value.get(i).sku;
                    inventoryInfo.level_0_price = editorGoodsInfo.sp_value
                            .get(i).level_0_price;
                    inventoryInfo.level_1_price = editorGoodsInfo.sp_value
                            .get(i).level_1_price;
                    inventoryInfo.level_2_price = editorGoodsInfo.sp_value
                            .get(i).level_2_price;
                    inventoryInfo.level_3_price = editorGoodsInfo.sp_value
                            .get(i).level_3_price;
                    inventoryInfo.level_0_auth_price = editorGoodsInfo.sp_value
                            .get(i).level_0_auth_price;
                    inventoryInfo.level_1_auth_price = editorGoodsInfo.sp_value
                            .get(i).level_1_auth_price;
                    inventoryInfo.level_2_auth_price = editorGoodsInfo.sp_value
                            .get(i).level_2_auth_price;
                    inventoryInfo.level_3_auth_price = editorGoodsInfo.sp_value
                            .get(i).level_3_auth_price;
                    inventoryInfo.goods_points_offset = editorGoodsInfo.sp_value
                            .get(i).goods_points_offset;
                    inventoryInfo.costprice = editorGoodsInfo.sp_value.get(i).costprice;
                    break;
                }
            }
            speclist.add(inventoryInfo);
        }
    }

    /**
     * 得到所有已选择规格(发布)
     *
     * @param chooselist
     * @param pid
     */
    private void kc3(
            List<List<FBGoodsInfo.spec_list.value>> chooselist,
            List<InventoryInfo.data> li, int pid, List<InventoryInfo> speclist) {
        if (pid < chooselist.size()) {
            for (int i = 0; i < chooselist.get(pid).size(); i++) {
                List<InventoryInfo.data> liy = new ArrayList<>();
                liy.addAll(li);
                liy.add(new InventoryInfo.data(chooselist.get(
                        pid).get(i).sp_value_id,
                        chooselist.get(pid).get(i).sp_value_name, ""));
                kc3(chooselist, liy, (pid + 1), speclist);
            }
        } else {
            InventoryInfo inventoryInfo = new InventoryInfo();
            inventoryInfo.data = li;
            String id = "", name = "";
            for (int j = 0; j < inventoryInfo.data.size(); j++) {
                // 拼接规格和名称
                id = id + inventoryInfo.data.get(j).sp_value_id;
                name = name + inventoryInfo.data.get(j).sp_value_name + " ";
                // 获取一级ID
                if (j == 0) {
                    for (int k = 0; k < fBGoodsInfo.spec_list.size(); k++) {
                        for (int i = 0; i < fBGoodsInfo.spec_list.get(k).value
                                .size(); i++) {
                            if (inventoryInfo.data.get(j).sp_value_name
                                    .equals(fBGoodsInfo.spec_list.get(k).value
                                            .get(i).sp_value_name)) {
                                inventoryInfo.sp_value_color = fBGoodsInfo.spec_list
                                        .get(k).value.get(i).sp_value_id;
                            }
                        }
                    }
                }
            }
            inventoryInfo.name = name;
            speclist.add(inventoryInfo);
        }
    }

    /**
     * 计算折扣
     */
    private View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String s = ed_input4.getText().toString();
                String s2 = ed_input13.getText().toString();
                if (TextUtils.isEmpty(s) || TextUtils.isEmpty(s2)) {
                    return;
                }
                float ii = Float.parseFloat(s);
                float i2 = Float.parseFloat(s2);
                if (ii < i2) {
                    float i = (ii / i2) * 100;
                    int round = Math.round(i);
                    ed_input15.setText(round + "");
                } else {
                    Toast.makeText(ReleaseGoodsActivity.this,
                            getString(R.string.release_goods151),
                            Toast.LENGTH_SHORT).show();
                    ed_input4.setText(null);
                    ed_input13.setText(null);
                }
            }
        }
    };


    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            // 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            tempFile = outputFile;
            if (album == 1) {
                Glide.with(ReleaseGoodsActivity.this).load(tempFile).into(iv_goodsimg);
            } else if (album == 2) {
                // 详情添加图片
                // ImageSpaceInfo.datas.pic_list pic_list = new
                // ImageSpaceInfo.datas.pic_list();
                // pic_list.full_path = tempFile.toString();
                // pic_list.apic_cover = tempFile.toString();
                // imgInfoAdapter.additem(pic_list);
            }
            netRun.GoodsImageUpload(tempFile);

        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReleaseGoodsActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setTitle("请选择图片来源");
        String[] items = new String[]{"相册",
                "相机"};

        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // 调用相册
                        startPick(dialog);
                        break;
                    case 1:
                        // 调用拍照
                        startCamera(dialog);
                        break;
                }
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.create().show();
    }

    // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.with(ReleaseGoodsActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(ReleaseGoodsActivity.this,
                LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10010) {
            if (data != null) {
                Bundle extras = data.getExtras();
                freight = extras.getString("id");
                freightname = extras.getString("name");
                tv_yfmode.setText(freightname);
            }
        } else {
            // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
            mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-授权管理-应用权限管理 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + ReleaseGoodsActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }


    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private int getw() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 判断是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString 时间戳
     * @param formats         格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats)
                .format(new Date(timestamp));
        return date;
    }

    /**
     * 时间转时间戳
     *
     * @param s
     * @param formats
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s, String formats)
            throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formats);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    @Override
    public void ReGetData() {

    }

}
