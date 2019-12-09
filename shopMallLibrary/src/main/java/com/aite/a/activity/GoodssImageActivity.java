package com.aite.a.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.GoodssImageInfo;
import com.aite.a.model.ImageSpaceInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.MyDialog;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 商品上传图片
 * Created by Administrator on 2017/5/5.
 */
public class GoodssImageActivity extends BaseActivity implements View.OnClickListener {
    private ImageView _iv_back;
    private TextView _tv_name, tv_netxt;
//    private MyListView mlv_img;
    private ListView mlv_img;

    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private MyDialog myDialog;
    private ImgAdapter imgAdapter;
    private boolean isrequest = false;
    private ImageSpaceInfo imageSpaceInfo;
    private String goodsimgclassify = "0";
    private int goodsimgpager = 1;
    private ImgAdapter2 imgAdapter2;
    private String _goods = "", commonid = "";
    private GoodssImageInfo goodssImageInfo;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case image_space_id:
                    //图片空间
                    if (msg.obj != null) {
                        imageSpaceInfo = (ImageSpaceInfo) msg.obj;
                        List<GoodssImageInfo.image_array> getdata = imgAdapter.getdata();
                        GoodssImageInfo.image_array image_array = getdata.get(adapderid);
                        //修改页码
                        image_array.tv_pagerbtn3.setText(goodsimgpager + "");
                        //分类
                        List<String> menu = new ArrayList<>();
                        for (int i = 0; i < imageSpaceInfo.datas.class_list.size(); i++) {
                            if (i == 0) {
                                image_array.tv_ypsm.setText(getString(R.string.release_goods99) + imageSpaceInfo.datas.class_list.get(i).aclass_name);
                            }
                            menu.add(imageSpaceInfo.datas.class_list.get(i).aclass_name);
                        }
                        SpinnerAdapter adapter = new SpinnerAdapter(GoodssImageActivity.this,
                                android.R.layout.simple_spinner_item, menu);
                        image_array.sp_album.setAdapter(adapter);
                        image_array.sp_album.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (isrequest) {
                                    goodsimgclassify = imageSpaceInfo.datas.class_list.get(position).aclass_id;
                                    isrequest = false;
                                    netRun.ImageSpace("1", goodsimgclassify);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        //图片适配
                        imgAdapter2 = new ImgAdapter2(imageSpaceInfo.datas.pic_list, (ImgInfoAdapter) (image_array.imgIfAdapter));
                        image_array.mgv_album.setAdapter(imgAdapter2);

                    }
                    handler.sendEmptyMessageDelayed(10086, 1000);
                    break;
                case image_space_err:
                    handler.sendEmptyMessageDelayed(10086, 1000);
                    Toast.makeText(GoodssImageActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case 10086:
                    isrequest = true;
                    break;
                case edit_image_id:
                    //编辑商品图片(商品修改操作)
                    if (msg.obj != null) {
                        goodssImageInfo = (GoodssImageInfo) msg.obj;
                        if (goodssImageInfo.image_array==null||goodssImageInfo.image_array.size()==0){
                            List<GoodssImageInfo.image_array> array=new ArrayList<>();
                            GoodssImageInfo.image_array image_array = new GoodssImageInfo.image_array();
                            image_array.color_id="0";
                            image_array.color_val=new ArrayList<>();
                            array.add(image_array);
                            goodssImageInfo.image_array=array;
                        }
                        imgAdapter = new ImgAdapter(goodssImageInfo.image_array);
                        mlv_img.setAdapter(imgAdapter);
                    }
                    break;
                case edit_image_err:
                    Toast.makeText(GoodssImageActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case goodsimage_upload_id:
                    //上传图片
                    if (msg.obj != null) {
                        List<String>list= (List<String>) msg.obj;
                        List<GoodssImageInfo.image_array> getdata1 = imgAdapter.getdata();
                        ImgInfoAdapter imgIfAdapter = (ImgInfoAdapter) getdata1.get(xqid).imgIfAdapter;
                        GoodssImageInfo.image_array.color_val color_val = new GoodssImageInfo.image_array.color_val();
                        color_val.goods_image = list.get(0);
                        color_val.bdimg = list.get(1);
                        color_val.is_default = "0";
                        color_val.goods_image_sort = "0";
                        imgIfAdapter.additem(color_val, childid);
                    }
                    break;
                case goodsimage_upload_err:
                    Toast.makeText(GoodssImageActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case save_image_id:
                    //编辑图片保存
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        if (str.equals("1")){
                            Toast.makeText(GoodssImageActivity.this, getString(R.string.release_goods146), Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(GoodssImageActivity.this, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case save_image_err:
                    Toast.makeText(GoodssImageActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsimg);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_netxt = (TextView) findViewById(R.id.tv_netxt);
        mlv_img = (ListView) findViewById(R.id.mlv_img);
        _iv_back.setOnClickListener(this);
        tv_netxt.setOnClickListener(this);
        _tv_name.setText(getString(R.string.release_goods111));
        initView();
    }

    @Override
    protected void initView() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        ShowDialog();
        Intent intent = getIntent();
        _goods = intent.getStringExtra("goods");
        if (_goods.equals("redact")) {
            commonid = intent.getStringExtra("common_id");
            netRun.EditImage(commonid);
            tv_netxt.setText(getString(R.string.release_goods88));
        } else if (_goods.equals("add")) {
            tv_netxt.setText(getString(R.string.release_goods110));
            commonid = intent.getStringExtra("common_id");
            netRun.UploadGoodsImg(commonid);
        }
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_netxt:
//                if (_goods.equals("redact")) {
//                    //提交
//                    netRun.SaveImage(commonid, imgAdapter.getjson());
//                } else if (_goods.equals("add")) {
//                    //确认发布
//                    netRun.SaveImage2(commonid, imgAdapter.getjson());
//                }
//                break;
//        }
        if(v.getId()==R.id._iv_back){
            finish();
        }else if(v.getId()==R.id.tv_netxt){
            if (_goods.equals("redact")) {
                //提交
                netRun.SaveImage(commonid, imgAdapter.getjson());
            } else if (_goods.equals("add")) {
                //确认发布
                netRun.SaveImage2(commonid, imgAdapter.getjson());
            }
        }
    }

    /**
     * 弹出提示框
     */
    private void ShowDialog() {
        View dialogbrand = View.inflate(GoodssImageActivity.this, R.layout.dialog_imgts, null);
        TextView tv_qd = (TextView) dialogbrand.findViewById(R.id.tv_qd);
        tv_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        if (myDialog == null) {
            myDialog = new MyDialog(GoodssImageActivity.this, getw() - 70, 550, dialogbrand,
                    R.style.loading_dialog);
            myDialog.show();
        } else {
            myDialog.show();
        }
    }

    private int adapderid = 0;

    /**
     * 图片列表
     */
    public class ImgAdapter extends BaseAdapter {
        List<GoodssImageInfo.image_array> image_array;
        int lastid = -1;

        public ImgAdapter(List<GoodssImageInfo.image_array> image_array) {
            this.image_array = image_array;
            lastid = -1;
        }

        /**
         * 获得
         *
         * @return
         */
        public String getjson() {
            JSONArray jsonArray = new JSONArray();
            try {
                for (int i = 0; i < image_array.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("sp_value_id", image_array.get(i).color_id);
                    JSONArray jsonArray2 = new JSONArray();
                    for (int j = 0; j < image_array.get(i).color_val.size(); j++) {
                        if (image_array.get(i).color_val.get(j).goods_image != null) {
                            JSONObject jsonObject2 = new JSONObject();
                            jsonObject2.put("goods_image", image_array.get(i).color_val.get(j).bdimg);
                            jsonObject2.put("is_default", image_array.get(i).color_val.get(j).is_default);
                            jsonObject2.put("goods_image_sort", image_array.get(i).color_val.get(j).goods_image_sort);
                            jsonArray2.put(jsonObject2);
                        }
                    }
                    jsonObject.put("color_val", jsonArray2);
                    jsonArray.put(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("---------------", "json  " + jsonArray.toString());
            return jsonArray.toString();
        }

        public void setdata(List<GoodssImageInfo.image_array> image_array) {
            this.image_array = image_array;
            notifyDataSetChanged();
        }

        public List<GoodssImageInfo.image_array> getdata() {
            return image_array;
        }

        @Override
        public int getCount() {
            return image_array.size();
        }

        @Override
        public Object getItem(int position) {
            return image_array == null ? null : image_array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(GoodssImageActivity.this, R.layout.item_goodsimage, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final GoodssImageInfo.image_array image = this.image_array.get(position);
//            bitmapUtils.display(holder.iv_img, pic_list.full_path);
            if (parent.getChildCount() == position) {
                if (position != lastid) {
                    //凑齐5个
                    for (int i = 0; i < 5; i++) {
                        if (image.color_val.size() < 5) {
                            GoodssImageInfo.image_array.color_val color_val = new GoodssImageInfo.image_array.color_val();
                            color_val.is_default = "0";
                            color_val.goods_image_sort = "0";
                            image.color_val.add(new GoodssImageInfo.image_array.color_val());
                        }
                    }
                    final ImgInfoAdapter imgInfoAdapter = new ImgInfoAdapter(image.color_val, position);
                    holder.mgv_info.setAdapter(imgInfoAdapter);

                    image.sp_album = holder.sp_album;
                    image.tv_pagerbtn3 = holder.tv_pagerbtn3;
                    image.tv_ypsm = holder.tv_ypsm;
                    image.mgv_album = holder.mgv_album;
                    image.imgIfAdapter = imgInfoAdapter;
                    lastid=position;
                }
            }
            if (goodssImageInfo.value_array!=null){
                for (int i = 0; i < goodssImageInfo.value_array.size(); i++) {
                    if (image.color_id.equals(goodssImageInfo.value_array.get(i).sp_value_id)) {
                        holder.tv_title.setText(goodssImageInfo.value_array.get(i).sp_value_name);
                    }
                }
            }
            if (image.iszk) {
                holder.iv_open.setImageResource(R.drawable.release_album_top);
                holder.ll_album.setVisibility(View.VISIBLE);
            } else {
                holder.iv_open.setImageResource(R.drawable.release_album_btn);
                holder.ll_album.setVisibility(View.GONE);
            }

            holder.rl_xc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 相册
                    if (image.iszk) {
                        image.iszk = false;
                        notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < image_array.size(); i++) {
                            image_array.get(i).iszk = false;
                        }
                        image_array.get(position).iszk = true;
                        adapderid = position;
                        goodsimgpager=1;
                        netRun.ImageSpace("1", "0");
                        notifyDataSetChanged();
                    }
//                    holder.ll_album.setVisibility(View.VISIBLE);
                }
            });
            holder.tv_pagerbtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //首页
                    if (imageSpaceInfo != null && imageSpaceInfo.page_total != null && !imageSpaceInfo.page_total.equals("")
                            && !imageSpaceInfo.page_total.equals("1")) {
                        isrequest = false;
                        adapderid = position;
                        netRun.ImageSpace("1", goodsimgclassify);
                    }
                }
            });
            holder.tv_pagerbtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //上一页
                    if (goodsimgpager > 1) {
                        goodsimgpager--;
                        isrequest = false;
                        adapderid = position;
                        netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
                    }
                }
            });

            holder.tv_pagerbtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //下一页
                    if (imageSpaceInfo != null && imageSpaceInfo.page_total != null && !imageSpaceInfo.page_total.equals("")) {
                        int total = Integer.parseInt(imageSpaceInfo.page_total);
                        if (goodsimgpager <= total) {
                            goodsimgpager++;
                            isrequest = false;
                            adapderid = position;
                            netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
                        }
                    }
                }
            });
            holder.tv_pagerbtn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //尾页
                    if (imageSpaceInfo != null && imageSpaceInfo.page_total != null && !imageSpaceInfo.page_total.equals("")
                            && !imageSpaceInfo.page_total.equals(goodsimgpager + "")) {
                        goodsimgpager = Integer.parseInt(imageSpaceInfo.page_total);
                        isrequest = false;
                        adapderid = position;
                        netRun.ImageSpace(goodsimgpager + "", goodsimgclassify);
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            MyGridView mgv_info, mgv_album;
            RelativeLayout rl_xc;
            ImageView iv_open;
            LinearLayout ll_album;
            TextView tv_ypsm, tv_pagerbtn1, tv_pagerbtn2, tv_pagerbtn3, tv_pagerbtn4, tv_pagerbtn5, tv_title;
            Spinner sp_album;

            public ViewHolder(View convertView) {
                mgv_info = (MyGridView) convertView.findViewById(R.id.mgv_info);
                mgv_album = (MyGridView) convertView.findViewById(R.id.mgv_album);
                rl_xc = (RelativeLayout) convertView.findViewById(R.id.rl_xc);
                iv_open = (ImageView) convertView.findViewById(R.id.iv_open);
                ll_album = (LinearLayout) convertView.findViewById(R.id.ll_album);
                tv_ypsm = (TextView) convertView.findViewById(R.id.tv_ypsm);
                tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                tv_pagerbtn1 = (TextView) convertView.findViewById(R.id.tv_pagerbtn1);
                tv_pagerbtn2 = (TextView) convertView.findViewById(R.id.tv_pagerbtn2);
                tv_pagerbtn3 = (TextView) convertView.findViewById(R.id.tv_pagerbtn3);
                tv_pagerbtn4 = (TextView) convertView.findViewById(R.id.tv_pagerbtn4);
                tv_pagerbtn5 = (TextView) convertView.findViewById(R.id.tv_pagerbtn5);
                sp_album = (Spinner) convertView.findViewById(R.id.sp_album);
                convertView.setTag(this);
            }
        }
    }

    //详情组id/子ID
    private int xqid = 0, childid = 0;

    /**
     * 详情列表
     */
    private class ImgInfoAdapter extends BaseAdapter {
        List<GoodssImageInfo.image_array.color_val> infofile;
        int adapteid = 0;
        int lastid = -1;

        public ImgInfoAdapter(List<GoodssImageInfo.image_array.color_val> infofile, int adapteid) {
            this.infofile = infofile;
            this.adapteid = adapteid;
            lastid = -1;
        }

        public void setdata(List<GoodssImageInfo.image_array.color_val> infofile) {
            this.infofile = infofile;
            notifyDataSetChanged();
        }

        public void additem(GoodssImageInfo.image_array.color_val infofile) {
            int number = 0;
            for (int i = 0; i < this.infofile.size(); i++) {
                if (this.infofile.get(i).goods_image != null) {
                    number++;
                }
            }
            if (number < 5) {
                for (int i = 0; i < this.infofile.size(); i++) {
                    if (this.infofile.get(i).goods_image == null) {
                        this.infofile.set(i, infofile);
                        break;
                    }
                }
                notifyDataSetChanged();
            } else {
                Toast.makeText(GoodssImageActivity.this, getString(R.string.release_goods107), Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 上传图片
         *
         * @param infofile
         * @param id
         */
        public void additem(GoodssImageInfo.image_array.color_val infofile, int id) {
            int number = 0;
            for (int i = 0; i < this.infofile.size(); i++) {
                if (this.infofile.get(i).goods_image != null) {
                    number++;
                }
            }
            if (number < 5) {
                this.infofile.get(id).goods_image = infofile.goods_image;
                this.infofile.get(id).is_default = infofile.is_default;
                this.infofile.get(id).goods_image_sort = infofile.goods_image_sort;
                this.infofile.get(id).bdimg = infofile.bdimg;
//                this.infofile.add(infofile);
                notifyDataSetChanged();
            } else {
                Toast.makeText(GoodssImageActivity.this, getString(R.string.release_goods107), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return infofile == null ? null : infofile.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(GoodssImageActivity.this, R.layout.item_imgspace2, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
//            if (position < infofile.size()) {
            final GoodssImageInfo.image_array.color_val color_val = this.infofile.get(position);
            if (parent.getChildCount() == position) {
                if (position != lastid) {
                    holder.et_inputsort1.addTextChangedListener(new MyAdapterListener(color_val, position));
                    lastid=position;
                }
            }
            if (color_val.goods_image != null) {
                bitmapUtils.display(holder.iv_img1, color_val.goods_image);
                holder.iv_del.setVisibility(View.VISIBLE);
                holder.iv_bg.setVisibility(View.GONE);
                holder.iv_img1.setVisibility(View.VISIBLE);
                holder.et_inputsort1.setText(color_val.goods_image_sort);
                if (color_val.is_default.equals("1")) {
                    holder.ll_mr.setVisibility(View.VISIBLE);
                } else {
                    holder.ll_mr.setVisibility(View.GONE);
                }
                holder.iv_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除
                        infofile.get(position).goods_image = null;
                        infofile.get(position).is_default = "0";
                        holder.ll_mr.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }
                });
                holder.iv_img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //默认主图
                        if (infofile.get(position).goods_image != null) {
                            for (int i = 0; i < infofile.size(); i++) {
                                infofile.get(i).is_default = "0";
                            }
                            infofile.get(position).is_default = "1";
                            notifyDataSetChanged();
                        }
                    }
                });
            } else {
                holder.iv_del.setVisibility(View.GONE);
                holder.iv_bg.setVisibility(View.VISIBLE);
                holder.iv_img1.setVisibility(View.INVISIBLE);
            }
            holder.ll_upupload1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //上传
                    xqid = adapteid;
                    childid = position;
                    editAvatar();
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_img1, iv_del, iv_bg;
            LinearLayout ll_mr, ll_upupload1;
            EditText et_inputsort1;

            public ViewHolder(View convertView) {
                iv_img1 = (ImageView) convertView.findViewById(R.id.iv_img1);
                iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
                iv_bg = (ImageView) convertView.findViewById(R.id.iv_bg);
                ll_mr = (LinearLayout) convertView.findViewById(R.id.ll_mr);
                ll_upupload1 = (LinearLayout) convertView.findViewById(R.id.ll_upupload1);
                et_inputsort1 = (EditText) convertView.findViewById(R.id.et_inputsort1);
                convertView.setTag(this);
            }
        }


        class MyAdapterListener implements TextWatcher {
            GoodssImageInfo.image_array.color_val color_val;
            int position;

            public MyAdapterListener(GoodssImageInfo.image_array.color_val color_val, int position) {
                this.position = position;
                this.color_val = color_val;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (color_val.goods_image_sort==null){
                    color_val.goods_image_sort="0";
                }
                if (!color_val.goods_image_sort.equals(s.toString())) {
                    color_val.goods_image_sort = s.toString();
                 }
            }
        }

    }

    /**
     * 图片空间
     */
    private class ImgAdapter2 extends BaseAdapter {
        List<ImageSpaceInfo.datas.pic_list> pic_list;
        ImgInfoAdapter imgInfoAdapter;

        public ImgAdapter2(List<ImageSpaceInfo.datas.pic_list> pic_list, ImgInfoAdapter imgInfoAdapter) {
            this.pic_list = pic_list;
            this.imgInfoAdapter = imgInfoAdapter;
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
                convertView = View.inflate(GoodssImageActivity.this, R.layout.item_imgspace, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            final ImageSpaceInfo.datas.pic_list pic_list = this.pic_list.get(position);
            bitmapUtils.display(holder.iv_img, pic_list.full_path);
            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择图片
//                    List<GoodssImageInfo.image_array> getdata = imgAdapter.getdata();
                    GoodssImageInfo.image_array.color_val color_val = new GoodssImageInfo.image_array.color_val();
                    color_val.goods_image = pic_list.full_path;
                    color_val.is_default = "0";
                    color_val.goods_image_sort = "0";
                    color_val.bdimg = pic_list.apic_cover;
                    imgInfoAdapter.additem(color_val);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_img, iv_img_del;

            public ViewHolder(View convertView) {
                iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                iv_img_del = (ImageView) convertView.findViewById(R.id.iv_img_del);
                convertView.setTag(this);
            }
        }
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


    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            // 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            tempFile = outputFile;
            netRun.GoodsImageUpload2(tempFile);
        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(GoodssImageActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setTitle(getString(R.string.complaint_prompt19));
        String[] items = new String[]{getString(R.string.complaint_prompt20),
                getString(R.string.complaint_prompt21)};

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
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.create().show();
    }

    // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.with(GoodssImageActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(GoodssImageActivity.this,
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
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle(getString(R.string.access_request));
        //设置正文
        builder.setMessage(getString(R.string.access_request2));

        //添加确定按钮点击事件
        builder.setPositiveButton(getString(R.string.go_to_set), new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + GoodssImageActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

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
        WindowManager wm = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);
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

	@Override
	public void ReGetData() {
		// TODO Auto-generated method stub
		
	}

}
