package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.aite.a.adapter.GoodsManageAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.GoodsManageList;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshView;
import com.aite.a.view.PullToRefreshView.OnFooterRefreshListener;
import com.aite.a.view.PullToRefreshView.OnHeaderRefreshListener;
import com.aiteshangcheng.a.R;


/**
 * 商品管理
 *
 * @author CAOYOU
 */
public class GoodsManageActivity extends BaseActivity implements OnClickListener,
        OnHeaderRefreshListener, OnFooterRefreshListener {
    private ListView goods_manage_List;
    private GoodsManageAdapter manageAdapter, manageAdapter2, manageAdapter3, manageAdapter4;
    private ImageView roller;
    private TextView lock_up, wait_verify, verified, un_shelve, tv_title_name;
    private NetRun netRun;
    private ImageView iv_back;
    private int imageWidth;
    private int offset;
    private int currentIndex;
    private int type = 0;
    private String url = un_shelve_goods;
    private String curpage = "1", curpage2 = "1", curpage3 = "1", curpage4 = "1";
    private String page = "10";
    private boolean isOneOpen = false;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case goods_manage_id:
                    if (msg.arg1 == 0) {
                        manageAdapter.addGoodsManageLists((List<GoodsManageList>) msg.obj, msg.arg1);
                        manageAdapter.notifyDataSetChanged();
                    }
                    if (msg.arg1 == 1) {
                        manageAdapter2.addGoodsManageLists((List<GoodsManageList>) msg.obj, msg.arg1);
                        manageAdapter2.notifyDataSetChanged();
                    }
                    if (msg.arg1 == 2) {
                        manageAdapter3.addGoodsManageLists((List<GoodsManageList>) msg.obj, msg.arg1);
                        manageAdapter3.notifyDataSetChanged();
                    }
                    if (msg.arg1 == 3) {
                        manageAdapter4.addGoodsManageLists((List<GoodsManageList>) msg.obj, msg.arg1);
                        manageAdapter4.notifyDataSetChanged();
                    }
                    if (((List<GoodsManageList>) msg.obj).size() == 0) {
                        Toast.makeText(GoodsManageActivity.this, getString(R.string.storehome30), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case goods_manage_err:
                    Toast.makeText(GoodsManageActivity.this, getString(R.string.storehome11), Toast.LENGTH_SHORT).show();
                    break;
                case goods_manage_start:
                    break;
                case goods_operation_id:
                    if (msg.obj.equals("1")) {
                        Toast.makeText(GoodsManageActivity.this, getString(R.string.storehome31), Toast.LENGTH_SHORT).show();
                        if (type == 3) {
                            url = un_shelve_goods;
                            type = 0;
                        }
                        initData();
                    } else {
                        Toast.makeText(GoodsManageActivity.this, getString(R.string.storehome32), Toast.LENGTH_SHORT).show();
                    }
                case goods_operation_err:
                    if (msg.obj == null)
                        Toast.makeText(GoodsManageActivity.this, getString(R.string.storehome11), Toast.LENGTH_SHORT).show();
                    break;
                case goods_operation_start:
                    break;
            }
        }

        ;
    };
    private PullToRefreshView goods_manage_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_manage);
        netRun = new NetRun(this, handler);
        findViewById();
        initView();
        initCursor(4);
        initData();
    }

    public void initCursor(int tagNum) {
        roller = (ImageView) findViewById(R.id.seller_order_iv_cursor);
        imageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        offset = (displayMetrics.widthPixels / tagNum - imageWidth) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        roller.setImageMatrix(matrix);
    }

    /**
     * 设置游标位置
     *
     * @param position
     */
    private void upCursor(int position) {
        int one = offset * 2 + imageWidth; // 一个页卡占的偏移量
        Animation animation = new TranslateAnimation(one * currentIndex, one * position, 0, 0);
        currentIndex = position;
        animation.setFillAfter(true);
        animation.setDuration(300);
        roller.startAnimation(animation);
    }


    @Override
    protected void findViewById() {
        goods_manage_rv = (PullToRefreshView) findViewById(R.id.goods_manage_rv);
        goods_manage_List = (ListView) findViewById(R.id.goods_manage_List);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        tv_title_name = (TextView) findViewById(R.id._tv_name);
        un_shelve = (TextView) findViewById(R.id.un_shelve);
        lock_up = (TextView) findViewById(R.id.lock_up);
        wait_verify = (TextView) findViewById(R.id.wait_verify);
        verified = (TextView) findViewById(R.id.verified);
    }

    @Override
    protected void initView() {
        tv_title_name.setText(getString(R.string.storehome27));
        goods_manage_rv.setOnHeaderRefreshListener(this);
        goods_manage_rv.setOnFooterRefreshListener(this);
        iv_back.setOnClickListener(this);
        un_shelve.setOnClickListener(this);
        lock_up.setOnClickListener(this);
        wait_verify.setOnClickListener(this);
        verified.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        manageAdapter = new GoodsManageAdapter(this, handler);
        manageAdapter2 = new GoodsManageAdapter(this, handler);
        manageAdapter3 = new GoodsManageAdapter(this, handler);
        manageAdapter4 = new GoodsManageAdapter(this, handler);
        setAdapter(type);
        netRun.getGoodsManage(url, type, page, 1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.un_shelve) {
            type = 0;
            url = un_shelve_goods;
            setAdapter(type);
            netRun.getGoodsManage(url, type, page, 1);
        } else if (v.getId() == R.id.lock_up) {
            type = 1;
            url = goods_warehouse;
            setAdapter(type);
            netRun.getGoodsManage(url, type, page, 1);
        } else if (v.getId() == R.id.wait_verify) {
            type = 2;
            url = goods_warehouse;
            setAdapter(type);
            netRun.getGoodsManage(url, type, page, 1);
        } else if (v.getId() == R.id.verified) {
            type = 3;
            url = goods_warehouse;
            setAdapter(type);
            netRun.getGoodsManage(url, type, page, 1);
        }
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.un_shelve:
//			type = 0;
//			url = un_shelve_goods;
//			setAdapter(type);
//			netRun.getGoodsManage(url, type, page, 1);
//			break;
//		case R.id.lock_up:
//			type = 1;
//			url = goods_warehouse;
//			setAdapter(type);
//			netRun.getGoodsManage(url, type, page, 1);
//			break;
//		case R.id.wait_verify:
//			type = 2;
//			url = goods_warehouse;
//			setAdapter(type);
//			netRun.getGoodsManage(url, type, page, 1);
//			break;
//		case R.id.verified:
//			type = 3;
//			url = goods_warehouse;
//			setAdapter(type);
//			netRun.getGoodsManage(url, type, page, 1);
//			break;
//		}
    }

    /**
     * 设置适配器
     *
     * @param type
     */
    private void setAdapter(int type) {
        upCursor(type);
        switch (type) {
            case 0:
                un_shelve.setTextColor(getResources().getColor(R.color.cursor_text));
                lock_up.setTextColor(getResources().getColor(R.color.black));
                wait_verify.setTextColor(getResources().getColor(R.color.black));
                verified.setTextColor(getResources().getColor(R.color.black));
                goods_manage_List.setAdapter(manageAdapter);
                manageAdapter.clear();
                break;
            case 1:
                un_shelve.setTextColor(getResources().getColor(R.color.black));
                lock_up.setTextColor(getResources().getColor(R.color.cursor_text));
                wait_verify.setTextColor(getResources().getColor(R.color.black));
                verified.setTextColor(getResources().getColor(R.color.black));
                goods_manage_List.setAdapter(manageAdapter2);
                manageAdapter2.clear();
                break;
            case 2:
                un_shelve.setTextColor(getResources().getColor(R.color.black));
                lock_up.setTextColor(getResources().getColor(R.color.black));
                wait_verify.setTextColor(getResources().getColor(R.color.cursor_text));
                verified.setTextColor(getResources().getColor(R.color.black));
                goods_manage_List.setAdapter(manageAdapter3);
                manageAdapter3.clear();
                break;
            case 3:
                un_shelve.setTextColor(getResources().getColor(R.color.black));
                lock_up.setTextColor(getResources().getColor(R.color.black));
                wait_verify.setTextColor(getResources().getColor(R.color.black));
                verified.setTextColor(getResources().getColor(R.color.cursor_text));
                goods_manage_List.setAdapter(manageAdapter4);
                manageAdapter4.clear();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOneOpen == false) {
            isOneOpen = true;
            return;
        }
        if (type == 0)
            manageAdapter.clear();
        if (type == 1)
            manageAdapter2.clear();
        if (type == 2)
            manageAdapter3.clear();
        if (type == 3)
            manageAdapter4.clear();
        netRun.getGoodsManage(url, type, page, 1);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        goods_manage_rv.postDelayed(new Runnable() {

            @Override
            public void run() {
                int show_add = 0;
                switch (currentIndex) {
                    case 0:
                        show_add = Integer.valueOf(curpage);
                        show_add = show_add + 1;
                        curpage = String.valueOf(show_add);
                        break;
                    case 1:
                        show_add = Integer.valueOf(curpage2);
                        show_add = show_add + 1;
                        curpage2 = String.valueOf(show_add);
                        break;
                    case 2:
                        show_add = Integer.valueOf(curpage3);
                        show_add = show_add + 1;
                        curpage3 = String.valueOf(show_add);
                        break;
                    case 3:
                        show_add = Integer.valueOf(curpage4);
                        show_add = show_add + 1;
                        curpage4 = String.valueOf(show_add);
                        break;
                }
                netRun.getGoodsManage(url, type, page, show_add);
                goods_manage_rv.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        goods_manage_rv.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (currentIndex) {
                    case 0:
                        curpage = "1";
                        manageAdapter.clear();
                        break;
                    case 1:
                        curpage2 = "1";
                        manageAdapter2.clear();
                        break;
                    case 2:
                        curpage3 = "1";
                        manageAdapter3.clear();
                        break;
                    case 3:
                        curpage4 = "1";
                        manageAdapter4.clear();
                        break;
                }
                netRun.getGoodsManage(url, type, page, 1);
                goods_manage_rv.onHeaderRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void ReGetData() {

    }

}
