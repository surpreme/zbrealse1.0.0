package com.aite.a.activity;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aiteshangcheng.a.R;
import com.aite.a.adapter.GoodsImgyAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.CategoryList;
import com.aite.a.model.FileDataList;
import com.aite.a.model.GoodsManageList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.ImageThumbnail;
import com.aite.a.view.EditTextWithDel;
import com.lidroid.xutils.BitmapUtils;

/**
 * 发布宝贝或编辑宝贝
 *
 * @author CAOYOU
 */
public class PostTradeActivity extends BaseActivity implements
        OnItemSelectedListener, OnClickListener {
    private List<FileDataList> fileDataLists = new ArrayList<FileDataList>();
    private EditTextWithDel et_goods_name, et_goods_price, et_market_price,
            et_goods_stocks, et_freight;
    private Spinner sp_one_category, sp_two_category, sp_three_category,
            sp_province, sp_city;
    private GridView gv_image;
    private NetRun netRun;
    private ScrollView sl_post;
    private GoodsManageList goodsList = new GoodsManageList();
    private List<String[]> provinceList = new ArrayList<String[]>(),
            cityList = new ArrayList<String[]>();
    private List<CategoryList> categoryOne = new ArrayList<CategoryList>(),
            categorytwo = new ArrayList<CategoryList>(),
            categorythree = new ArrayList<CategoryList>();
    private GoodsImgyAdapter imageAdapter;
    private Button bt_submit;
    private String _goods, commonid, pathImage;
    private String gc_name = "", gc_name2 = "", gc_name3 = "";
    private int get_area_num = 0, get_area_num2 = 0;
    private Uri imageFileUri = null;
    private ImageView iv_goodsimg, iv_goodsimg2;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case get_redact_data_id:
                    if (msg.obj != null) {
                        goodsList = (GoodsManageList) msg.obj;
                        setData(goodsList);
                        netRun.getSregionList(null);
                        netRun.getCategoryTeo(null, 0);
                    }
                    break;
                case get_region_id:
                    if (msg.obj != null) {
                        if (get_area_num == 0) {
                            provinceList = (List<String[]>) msg.obj;
                            sp_province.setAdapter(regionList(provinceList));
                        }
                        if (get_area_num == 1) {
                            cityList = (List<String[]>) msg.obj;
                            sp_city.setAdapter(regionList(cityList));
                        }
                        getAreaName((List<String[]>) msg.obj);
                    }
                    break;
                case two_category_id:
                    if (msg.obj != null) {
                        if (get_area_num2 == 0) {
                            categoryOne = (List<CategoryList>) msg.obj;
                            sp_one_category.setAdapter(CategoryList(categoryOne));
                        }
                        if (get_area_num2 == 1) {
                            categorytwo = (List<CategoryList>) msg.obj;
                            sp_two_category.setAdapter(CategoryList(categorytwo));
                        }
                        if (get_area_num2 == 2) {
                            categorythree = (List<CategoryList>) msg.obj;
                            sp_three_category
                                    .setAdapter(CategoryList(categorythree));
                        }
                        getGcName((List<CategoryList>) msg.obj);
                    }
                    break;
                case post_redact_data_id:
                    if (msg.obj.equals("1")) {
                        CommonTools.showShortToast(PostTradeActivity.this,
                                getI18n(R.string.commit_success));
                        finish();
                    } else {
                        CommonTools.showShortToast(PostTradeActivity.this,
                                msg.obj.toString());
                    }
                    mdialog.dismiss();
                    break;
                case post_redact_data_err:
                    CommonTools.showShortToast(PostTradeActivity.this,
                            getI18n(R.string.net_reconnect));
                    mdialog.dismiss();
                    break;
                case post_redact_data_start:
                    mdialog.setTitle(getI18n(R.string.commiting_data));
//				mdialog.setMessage(getI18n(R.string.uploading_picture));
//				mdialog.setMax(100);
//				mdialog.setIndeterminate(false);
//				mdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mdialog.show();
                    break;
                case post_redact_data_long:
                    int i = msg.arg1;
//				mdialog.incrementProgressBy(i);
                    break;
            }
        }

        ;
    };

    /**
     * 设置地区下拉列表的默认名称
     *
     * @param obj
     */
    private void getAreaName(List<String[]> obj) {
        for (int i = 0; i < obj.size(); i++) {
            if (get_area_num == 0) {
                if (obj.get(i)[0].equals(goodsList.province_id))
                    sp_province.setSelection(i, true);
            }
            if (get_area_num == 1) {
                if (obj.get(i)[0].equals(goodsList.city_id))
                    sp_city.setSelection(i, true);
            }
        }
    }

    /**
     * 设置分类下拉列表的默认名称
     *
     * @param obj
     */
    private void getGcName(List<CategoryList> obj) {
        for (int i = 0; i < obj.size(); i++) {
            if (get_area_num2 == 0) {
                if (obj.get(i).getGc_id().equals(goodsList.gc_id_1)) {
                    sp_one_category.setSelection(i, true);
                    gc_name = categoryOne.get(i).getGc_name();
                }
            }
            if (get_area_num2 == 1) {
                if (obj.get(i).getGc_id().equals(goodsList.gc_id_2)) {
                    sp_two_category.setSelection(i, true);
                    gc_name2 = categorytwo.get(i).getGc_name();
                }
            }
            if (get_area_num2 == 2) {
                if (obj.get(i).getGc_id().equals(goodsList.gc_id_3)) {
                    sp_three_category.setSelection(i, true);
                    gc_name3 = categorythree.get(i).getGc_name();
                }
            }
        }
    }

    protected void setData(GoodsManageList goodsManageList) {
        et_goods_name.setText(goodsManageList.goods_name);
        et_goods_price.setText(goodsManageList.goods_price);
        et_market_price.setText(goodsManageList.goods_marketprice);
        et_goods_stocks.setText(goodsManageList.goods_storage);
        et_freight.setText(goodsManageList.goods_freight);
        bitmapUtils = new BitmapUtils(PostTradeActivity.this);
        if (goodsList.goods_image != null) {
            iv_goodsimg2.setVisibility(View.VISIBLE);
            bitmapUtils.display(iv_goodsimg2, goodsManageList.goods_image);
        }
    }

    /**
     * 设置经营分类的适配器
     *
     * @param categorythree
     * @return
     */
    protected ArrayAdapter<String> CategoryList(List<CategoryList> categorythree) {
        List<String> strings = new ArrayList<String>();
        for (CategoryList list : categorythree) {
            strings.add(list.getGc_name());
        }
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, strings);
        _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return _Adapter;
    }

    /**
     * 设置经营地区的适配器
     *
     * @param list
     * @return
     */
    private ArrayAdapter<String> regionList(List<String[]> list) {
        List<String> strings = new ArrayList<String>();
        for (String[] string : list) {
            strings.add(string[1]);
        }
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, strings);
        _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return _Adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_trade);
        netRun = new NetRun(this, handler);
        findViewById();
        initView();
        initData();

    }

    // final RelativeLayout parentLayout = (RelativeLayout)
    // findViewById(R.id.parent);

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        et_goods_name = (EditTextWithDel) findViewById(R.id.et_goods_name);
        et_goods_price = (EditTextWithDel) findViewById(R.id.et_goods_price);
        et_market_price = (EditTextWithDel) findViewById(R.id.et_market_price);
        et_goods_stocks = (EditTextWithDel) findViewById(R.id.et_goods_stocks);
        et_freight = (EditTextWithDel) findViewById(R.id.et_freight);
        sp_one_category = (Spinner) findViewById(R.id.sp_one_category);
        sp_two_category = (Spinner) findViewById(R.id.sp_two_category);
        sp_three_category = (Spinner) findViewById(R.id.sp_three_category);
        sp_province = (Spinner) findViewById(R.id.sp_province);
        sp_city = (Spinner) findViewById(R.id.sp_city);
        gv_image = (GridView) findViewById(R.id.gv_goods_image);
        tv_title_name = (TextView) findViewById(R.id._tv_name);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        iv_goodsimg = (ImageView) findViewById(R.id.iv_goodsimg);
        iv_goodsimg2 = (ImageView) findViewById(R.id.iv_goodsimg2);
        sl_post = (ScrollView) findViewById(R.id.sl_post);
        ll_postfu = (LinearLayout) findViewById(R.id.ll_postfu);
        v_diandi = findViewById(R.id.v_diandi);

        WindowManager wm = (WindowManager) this.getSystemService(
                Context.WINDOW_SERVICE);

        int height = wm.getDefaultDisplay().getHeight();
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) v_diandi.getLayoutParams();
        linearParams.height = height / 2;
        v_diandi.setLayoutParams(linearParams);
        sl_post.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 关闭软键盘
                        // InputMethodManager immm = (InputMethodManager)
                        // getSystemService(Context.INPUT_METHOD_SERVICE);
                        // if (immm != null) {
                        // immm.hideSoftInputFromWindow(getWindow().getDecorView()
                        // .getWindowToken(), 0);
                        // }
                        controlKeyboardLayout(ll_postfu, sl_post);
                        break;
                }
                return false;
            }
        });
    }

    private LinearLayout ll_postfu;
    private View v_diandi;

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                        int rootInvisibleHeight = root.getRootView()
                                .getHeight() - rect.bottom;
                        // 若不可视区域高度大于100，则键盘显示
                        if (rootInvisibleHeight > 100) {
                            // int[] location = new int[2];
                            // //获取scrollToView在窗体的坐标
                            // scrollToView.getLocationInWindow(location);
                            // //计算root滚动高度，使scrollToView在可见区域
                            // int srollHeight = (location[1] +
                            // scrollToView.getHeight()) - rect.bottom;
                            // root.scrollTo(0, srollHeight);
                            v_diandi.setVisibility(View.VISIBLE);
                        } else {
                            // 键盘隐藏
                            // root.scrollTo(0, 0);
                            v_diandi.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String path = imageAdapter.imgLists
                .get(imageAdapter.imgLists.size() - 1).path;
        Bitmap BF = new BitmapFactory().decodeFile(path);
        if (path != null) {
            iv_goodsimg2.setVisibility(View.VISIBLE);
            iv_goodsimg2.setImageBitmap(BF);
        }
    }

    @Override
    protected void initView() {
        sp_one_category.setOnItemSelectedListener(this);
        sp_two_category.setOnItemSelectedListener(this);
        sp_three_category.setOnItemSelectedListener(this);
        sp_province.setOnItemSelectedListener(this);
        sp_city.setOnItemSelectedListener(this);
        imageAdapter = new GoodsImgyAdapter(this);
        et_goods_name.setOnClickListener(this);
        gv_image.setAdapter(imageAdapter);
        gv_image.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position == 0) { // 点击图片位置为+ 0对应0张图片
                    if (gv_image.getAdapter().getCount() == 9) { // 第一张为默认图片
                        Toast.makeText(PostTradeActivity.this,
                                getI18n(R.string.picture_full),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PostTradeActivity.this,
                                getI18n(R.string.add_picture),
                                Toast.LENGTH_SHORT).show();
                        editAvatar();
                    }
                } else {
                    File file = new File(
                            imageAdapter.imgLists.get(position).path);
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    Uri mUri = Uri.parse("file://" + file.getPath());
                    it.setDataAndType(mUri, "image/*");
                    startActivity(it);
                }
            }
        });
        gv_image.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                if (position > 0)
                    dialog(position);
                return true;
            }
        });

        iv_goodsimg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                editAvatar();
            }
        });
        iv_goodsimg2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                editAvatar();
            }
        });

        bt_submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                submitData();
            }
        });
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 提交数据
     */
    protected void submitData() {
        String gc_id = null;
        if (goodsList != null)
            if (goodsList.gc_id_3 != null) {
                gc_id = goodsList.gc_id_3;
            } else {
                if (goodsList.gc_id_2 != null) {
                    gc_id = goodsList.gc_id_2;
                } else {
                    if (goodsList.gc_id_1 != null) {
                        gc_id = goodsList.gc_id_1;
                    } else {
                        CommonTools.showShortToast(this,
                                getI18n(R.string.select_category));
                    }
                }
            }
        String goods_name = et_goods_name.getText().toString();
        String image_path = null;
        if (imageAdapter.imgLists.size() > 1) {
            image_path = imageAdapter.imgLists
                    .get(imageAdapter.imgLists.size() - 1).path;
        }
        String g_price = et_goods_price.getText().toString();
        String g_marketprice = et_market_price.getText().toString();
        String g_storage = et_goods_stocks.getText().toString();
        String g_freight = et_freight.getText().toString();
        String url = null;
        if (commonid == null) {
            url = add_GoodsData;
        } else {
            url = post_GoodsData;
        }
        netRun.postGoodsData(url, commonid, goods_name, gc_id, getCateName(),
                image_path, g_price, g_marketprice, g_storage,
                goodsList.province_id, goodsList.city_id, g_freight);
    }

    private String getCateName() {
        List<String> cate_names = new ArrayList<String>();
        if (gc_name != null) {
            cate_names.add(gc_name);
            if (gc_name2 != null) {
                cate_names.add(gc_name2);
                if (gc_name3 != null) {
                    cate_names.add(gc_name3);
                }
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < cate_names.size(); i++) {
            buffer.append(cate_names.get(i)).append(">");
        }
        return buffer.toString();
    }

    @Override
    protected void initData() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.gridview_addpic); // 加号
        imageAdapter.addImgData(bmp, pathImage);
        imageAdapter.notifyDataSetChanged();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            _goods = bundle.getString("goods");
            if (_goods.equals("redact")) {
                tv_title_name.setText(getI18n(R.string.edit_goods));
                commonid = bundle.getString("common_id");
                if (commonid == null) {
                    finish();
                    CommonTools.showShortToast(this,
                            getI18n(R.string.goods_not_allowed_edit));
                }
                netRun.getRedactData(commonid);
            } else if (_goods.equals("add")) {
                tv_title_name.setText(getI18n(R.string.release_goods));
                netRun.getSregionList(null);
                netRun.getCategoryTeo(null, 0);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_province) {
            get_area_num = 1;
            goodsList.province_id = provinceList.get(position)[0];
            netRun.getSregionList(goodsList.province_id);
        } else if (parent.getId() == R.id.sp_city) {
            get_area_num = 2;
            goodsList.city_id = cityList.get(position)[0];
            netRun.getSregionList(goodsList.city_id);
        } else if (parent.getId() == R.id.sp_one_category) {
            get_area_num2 = 1;
            goodsList.gc_id_1 = categoryOne.get(position).getGc_id();
            netRun.getCategoryTeo(goodsList.gc_id_1, position);
        } else if (parent.getId() == R.id.sp_two_category) {
            get_area_num2 = 2;
            goodsList.gc_id_2 = categorytwo.get(position).getGc_id();
            netRun.getCategoryTeo(goodsList.gc_id_2, position);
        } else if (parent.getId() == R.id.sp_three_category) {
            get_area_num2 = 3;
            goodsList.gc_id_3 = categorythree.get(position).getGc_id();
            netRun.getCategoryTeo(goodsList.gc_id_3, position);
        }
//        switch (parent.getId()) {
//            case R.id.sp_province:
//                get_area_num = 1;
//                goodsList.province_id = provinceList.get(position)[0];
//                netRun.getSregionList(goodsList.province_id);
//                break;
//            case R.id.sp_city:
//                get_area_num = 2;
//                goodsList.city_id = cityList.get(position)[0];
//                netRun.getSregionList(goodsList.city_id);
//                break;
//            case R.id.sp_one_category:
//                get_area_num2 = 1;
//                goodsList.gc_id_1 = categoryOne.get(position).getGc_id();
//                netRun.getCategoryTeo(goodsList.gc_id_1, position);
//                break;
//            case R.id.sp_two_category:
//                get_area_num2 = 2;
//                goodsList.gc_id_2 = categorytwo.get(position).getGc_id();
//                netRun.getCategoryTeo(goodsList.gc_id_2, position);
//                break;
//            case R.id.sp_three_category:
//                get_area_num2 = 3;
//                goodsList.gc_id_3 = categorythree.get(position).getGc_id();
//                netRun.getCategoryTeo(goodsList.gc_id_3, position);
//                break;
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * 选择图片数据源
     */
    private void editAvatar() {
        Builder dialog = new Builder(this);
        dialog.setTitle(getI18n(R.string.select_picture_source));
        String[] items = new String[]{getI18n(R.string.media_lib),
                getI18n(R.string.take_picture)};
        dialog.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0:
                        intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, 0);
                        break;
                    case 1:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        pathImage = Environment.getExternalStorageDirectory()
                                .getAbsolutePath()
                                + "/"
                                + Environment.DIRECTORY_DCIM
                                + "/"
                                + CommonTools.getSystemTime() + ".jpg";
                        imageFileUri = Uri.fromFile(new File(pathImage));
                        // 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                        startActivityForResult(intent, 1);
                        break;
                }
            }
        });
        dialog.setNegativeButton(getI18n(R.string.cancel), null);
        dialog.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Uri uri = data.getData();
                if (!TextUtils.isEmpty(uri.getAuthority())) {
                    // 查询选择图片
                    Cursor cursor = getContentResolver().query(uri,
                            new String[]{MediaStore.Images.Media.DATA},
                            null, null, null);
                    // 返回 没找到选择图片
                    if (null == cursor) {
                        return;
                    }
                    // 光标移动至开头 获取图片路径
                    cursor.moveToFirst();
                    pathImage = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    bitmap = ImageThumbnail.readBitmapAutoSize(pathImage, 800,
                            800);
                }
            } else if (requestCode == 1) {
                Bitmap camorabitmap = BitmapFactory.decodeFile(pathImage);
                if (null != camorabitmap) {
                    // 下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。
                    int scale = ImageThumbnail.reckonThumbnail(
                            camorabitmap.getWidth(), camorabitmap.getHeight(),
                            800, 800);
                    bitmap = ImageThumbnail.PicZoom(camorabitmap,
                            camorabitmap.getWidth() / scale,
                            camorabitmap.getHeight() / scale);
                    // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    camorabitmap.recycle();
                    // 将处理过的图片显示在界面上
                }
            }
            if (bitmap != null) {
                pathImage = ImageThumbnail.saveMyBitmap(pathImage, bitmap)
                        .getAbsolutePath();
                imageAdapter.addImgData(bitmap, pathImage);
                imageAdapter.notifyDataSetChanged();
                // 刷新后释放防止手机休眠后自动添加
                pathImage = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /*
     * Dialog对话框提示用户删除操作 position为删除图片位置
     */
    protected void dialog(final int position) {
        Builder builder = new Builder(this);
        builder.setMessage(getI18n(R.string.confirm_remove_picture));
        builder.setTitle(getI18n(R.string.tip));
        builder.setPositiveButton(getI18n(R.string.confirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        imageAdapter.imgLists.remove(position);
                        imageAdapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton(getI18n(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    // InputMethodManager imm = (InputMethodManager)
    // getSystemService(Activity.INPUT_METHOD_SERVICE);
    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.et_goods_name:
//                v_diandi.setVisibility(View.VISIBLE);
//                break;
//        }
        if(v.getId()==R.id.et_goods_name){
            v_diandi.setVisibility(View.VISIBLE);
        }
    }

    int realKeyboardHeight = 0;

    private int getkeyboardheight() {
        final Context context = getApplicationContext();
        sl_post.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        // r will be populated with the coordinates of your view
                        // that area still visible.
                        sl_post.getWindowVisibleDisplayFrame(r);
                        int screenHeight = bt_submit.getRootView().getHeight();
                        int heightDiff = screenHeight - (r.bottom - r.top);
                        int statusBarHeight = 0;
                        if (heightDiff > 100)
                            // if more than 100 pixels, its probably a keyboard
                            // get status bar height
                            try {
                                Class<?> c = Class
                                        .forName("com.android.internal.R$dimen");
                                Object obj = c.newInstance();
                                Field field = c.getField("status_bar_height");
                                int x = Integer.parseInt(field.get(obj)
                                        .toString());
                                statusBarHeight = context.getResources()
                                        .getDimensionPixelSize(x);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        realKeyboardHeight = heightDiff - statusBarHeight;
                        // Log.i("keyboard height = " + realKeyboardHeight);
                    }
                });
        return realKeyboardHeight;
    }

}
