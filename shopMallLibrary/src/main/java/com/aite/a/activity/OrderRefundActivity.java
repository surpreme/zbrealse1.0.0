package com.aite.a.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.SpinnerAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.OrderRefund2Info;
import com.aite.a.model.OrderRefundInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 订单退款
 *
 * @author Administrator
 */
public class OrderRefundActivity extends BaseActivity implements
        OnClickListener {
    private TextView _tv_name, tv_why, tv_money, tv_xz1, tv_xz2, tv_xz3,
            tv_name1, tv_name2, tv_name3, tv_confirm, tv_type1, tv_type2, tv_max;
    private ImageView _iv_back,iv_dropdown;
    private LinearLayout ll_type, ll_number;
    private RelativeLayout rl_inputmoney;
    private EditText et_sm, rl_money, rl_number;
    private Spinner sp_type;
    private NetRun netRun;
    private int imgtype = 1;
    private File img1, img2, img3;
    private OrderRefundInfo orderRefundInfo;
    private OrderRefund2Info orderRefund2Info;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case refund_info_id:
                    // 订单退款
                    if (msg.obj != null) {
                        orderRefundInfo = (OrderRefundInfo) msg.obj;
                        tv_money.setText(orderRefundInfo.order_amount + getString(R.string.yuan));
                    }
                    break;
                case refund_info_err:
                    Toast.makeText(OrderRefundActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_LONG)
                            .show();
                    break;
                case refund2_info_id://部分退款
                    if (msg.obj != null) {
                        orderRefund2Info = (OrderRefund2Info) msg.obj;
                        tv_max.setText("（" + getString(R.string.goodsdatails_reminder22) + orderRefund2Info.goods.goods_pay_price +
                                getString(R.string.yuan) + "）");
                        rl_money.setText(orderRefund2Info.goods.goods_pay_price);
//                        tv_money.setText(orderRefund2Info.goods.goods_pay_price + getString(R.string.yuan));
                        List<String> menu = new ArrayList<String>();
                        for (int i = 0; i < orderRefund2Info.reason_list.size(); i++) {
                            menu.add(orderRefund2Info.reason_list.get(i).reason_info);
                        }
                        // 定义适配器
                        SpinnerAdapter adapter = new SpinnerAdapter(OrderRefundActivity.this,
                                android.R.layout.simple_spinner_item, menu);
                        sp_type.setAdapter(adapter);
                    }
                    break;
                case refund2_info_err:
                    Toast.makeText(OrderRefundActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_LONG)
                            .show();
                    break;
                case confirm_refund2_id:
                    // 部分退款提交
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(OrderRefundActivity.this, str,
                                Toast.LENGTH_LONG).show();
                        if (str.equals(getString(R.string.operationissuccessful))) {
                            finish();
                        }
                    }
                    break;
                case confirm_refund2_err:
                    Toast.makeText(OrderRefundActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_LONG)
                            .show();
                    break;
                case confirm_refund_id:
                    // 订单退款提交
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(OrderRefundActivity.this, str,
                                Toast.LENGTH_LONG).show();
                        if (str.equals(getString(R.string.operationissuccessful))) {
                            finish();
                        }
                    }
                    break;
                case confirm_refund_err:
                    Toast.makeText(OrderRefundActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_LONG)
                            .show();
                    break;
            }
        }

        ;
    };
    private String order_id, goods_id, type = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refund);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = findViewById(R.id._tv_name);
        tv_why = findViewById(R.id.tv_why);
        tv_money = findViewById(R.id.tv_money);
        tv_xz1 = findViewById(R.id.tv_xz1);
        tv_xz2 = findViewById(R.id.tv_xz2);
        tv_xz3 = findViewById(R.id.tv_xz3);
        tv_name1 = findViewById(R.id.tv_name1);
        tv_name2 = findViewById(R.id.tv_name2);
        tv_name3 = findViewById(R.id.tv_name3);
        tv_confirm = findViewById(R.id.tv_confirm);
        _iv_back = findViewById(R.id._iv_back);
        et_sm = findViewById(R.id.et_sm);
        tv_type1 = findViewById(R.id.tv_type1);
        tv_type2 = findViewById(R.id.tv_type2);
        ll_type = findViewById(R.id.ll_type);
        sp_type = findViewById(R.id.sp_type);
        iv_dropdown = findViewById(R.id.iv_dropdown);
        rl_inputmoney = findViewById(R.id.rl_inputmoney);
        rl_money = findViewById(R.id.rl_money);
        tv_max = findViewById(R.id.tv_max);
        ll_number = findViewById(R.id.ll_number);
        rl_number = findViewById(R.id.rl_number);
        initView();
    }

    @Override
    protected void initView() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        _tv_name.setText(getString(R.string.order_reminder4));
        _iv_back.setOnClickListener(this);
        tv_xz1.setOnClickListener(this);
        tv_xz2.setOnClickListener(this);
        tv_xz3.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        tv_type1.setOnClickListener(this);
        tv_type2.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        order_id = getIntent().getStringExtra("order_id");
        goods_id = getIntent().getStringExtra("goods_id");
        if (goods_id == null) {//订单退款
            tv_why.setVisibility(View.VISIBLE);
            tv_money.setVisibility(View.VISIBLE);
            ll_type.setVisibility(View.GONE);
            sp_type.setVisibility(View.GONE);
            iv_dropdown.setVisibility(View.GONE);
            rl_inputmoney.setVisibility(View.GONE);
            tv_why.setText(getString(R.string.order_reminder20));
        } else {//部分退款
            tv_why.setVisibility(View.GONE);
            tv_money.setVisibility(View.GONE);
            ll_type.setVisibility(View.VISIBLE);
            sp_type.setVisibility(View.VISIBLE);
            iv_dropdown.setVisibility(View.VISIBLE);
            rl_inputmoney.setVisibility(View.VISIBLE);
        }
        initData();
    }

    @Override
    protected void initData() {
        if (goods_id == null) {//全部退款
            netRun.RefundInfo(order_id);
        } else {//部分退款
            netRun.RefundInfo2(order_id, goods_id);
        }
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id._iv_back){
            finish();
        }else if(v.getId()== R.id.tv_xz1){
            imgtype = 1;
            editAvatar();
        }else if(v.getId()==R.id.tv_xz2){
            imgtype = 2;
            editAvatar();
        }else if(v.getId()==R.id.tv_xz3){
            imgtype = 3;
            editAvatar();
        }else if(v.getId()==R.id.tv_confirm){
            // 提交
            String buyer_message = et_sm.getText().toString();
            if (TextUtils.isEmpty(buyer_message)) {
                buyer_message = "";
            }
            if (goods_id == null) {//全部退款
                netRun.ConfirmRefund(order_id, buyer_message, img1, img2, img3);
            } else {//部分退款
                String refund_amount = rl_money.getText().toString();
                String goods_num = rl_number.getText().toString();
                if (TextUtils.isEmpty(refund_amount)) {
                    Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder23), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(goods_num)) {
                    if (type.equals("2")){
                        Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder24), Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        goods_num=orderRefund2Info.goods.goods_num;
                    }
                }
                String s = sp_type.getSelectedItem().toString();
                String reason_id = "";
                for (int i = 0; i < orderRefund2Info.reason_list.size(); i++) {
                    if (s.equals(orderRefund2Info.reason_list.get(i).reason_info)) {
                        reason_id = orderRefund2Info.reason_list.get(i).reason_id;
                    }
                }
                if (Float.parseFloat(refund_amount)>Float.parseFloat(orderRefund2Info.goods.goods_pay_price)||Float.parseFloat(refund_amount)<0.1){
                    Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder25), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(goods_num)>Integer.parseInt(orderRefund2Info.goods.goods_num)||Integer.parseInt(goods_num)<1){
                    Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder26), Toast.LENGTH_SHORT).show();
                    return;
                }
                netRun.ConfirmRefund2(order_id, goods_id, refund_amount, goods_num, reason_id, type, buyer_message, img1, img2, img3);
            }
        }else if(v.getId()== R.id.tv_type1){
            //仅退款
            type = "1";
            tv_type1.setBackgroundResource(R.drawable.drawmoney_typea1);
            tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb2);
            tv_type1.setTextColor(Color.WHITE);
            tv_type2.setTextColor(0xffED5457);
            ll_number.setVisibility(View.GONE);
        }else if(v.getId()== R.id.tv_type2){
            //退货退款
            type = "2";
            tv_type1.setBackgroundResource(R.drawable.drawmoney_typea2);
            tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb1);
            tv_type2.setTextColor(Color.WHITE);
            tv_type1.setTextColor(0xffED5457);
            ll_number.setVisibility(View.VISIBLE);
        }

//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_xz1:
//                imgtype = 1;
//                editAvatar();
//                break;
//            case R.id.tv_xz2:
//                imgtype = 2;
//                editAvatar();
//                break;
//            case R.id.tv_xz3:
//                imgtype = 3;
//                editAvatar();
//                break;
//            case R.id.tv_confirm:
//                // 提交
//                String buyer_message = et_sm.getText().toString();
//                if (TextUtils.isEmpty(buyer_message)) {
//                    buyer_message = "";
//                }
//                if (goods_id == null) {//全部退款
//                    netRun.ConfirmRefund(order_id, buyer_message, img1, img2, img3);
//                } else {//部分退款
//                    String refund_amount = rl_money.getText().toString();
//                    String goods_num = rl_number.getText().toString();
//                    if (TextUtils.isEmpty(refund_amount)) {
//                        Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder23), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (TextUtils.isEmpty(goods_num)) {
//                        if (type.equals("2")){
//                            Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder24), Toast.LENGTH_SHORT).show();
//                            return;
//                        }else{
//                            goods_num=orderRefund2Info.goods.goods_num;
//                        }
//                    }
//                    String s = sp_type.getSelectedItem().toString();
//                    String reason_id = "";
//                    for (int i = 0; i < orderRefund2Info.reason_list.size(); i++) {
//                        if (s.equals(orderRefund2Info.reason_list.get(i).reason_info)) {
//                            reason_id = orderRefund2Info.reason_list.get(i).reason_id;
//                        }
//                    }
//                    if (Float.parseFloat(refund_amount)>Float.parseFloat(orderRefund2Info.goods.goods_pay_price)||Float.parseFloat(refund_amount)<0.1){
//                        Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder25), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (Integer.parseInt(goods_num)>Integer.parseInt(orderRefund2Info.goods.goods_num)||Integer.parseInt(goods_num)<1){
//                        Toast.makeText(appSingleton, getString(R.string.goodsdatails_reminder26), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    netRun.ConfirmRefund2(order_id, goods_id, refund_amount, goods_num, reason_id, type, buyer_message, img1, img2, img3);
//                }
//                break;
//            case R.id.tv_type1:
//                //仅退款
//                type = "1";
//                tv_type1.setBackgroundResource(R.drawable.drawmoney_typea1);
//                tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb2);
//                tv_type1.setTextColor(Color.WHITE);
//                tv_type2.setTextColor(0xffED5457);
//                ll_number.setVisibility(View.GONE);
//                break;
//            case R.id.tv_type2:
//                //退货退款
//                type = "2";
//                tv_type1.setBackgroundResource(R.drawable.drawmoney_typea2);
//                tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb1);
//                tv_type2.setTextColor(Color.WHITE);
//                tv_type1.setTextColor(0xffED5457);
//                ll_number.setVisibility(View.VISIBLE);
//                break;
//        }
    }


    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            // 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            tempFile = outputFile;
            if (imgtype == 1) {
                // 身份证
                img1 = tempFile;
                tv_name1.setText(tempFile.getName());
            } else if (imgtype == 2) {
                // 营业执照
                img2 = tempFile;
                tv_name2.setText(tempFile.getName());
            } else if (imgtype == 3) {
                img3 = tempFile;
                tv_name3.setText(tempFile.getName());
            }

        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(OrderRefundActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        PermissionGen.with(OrderRefundActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(OrderRefundActivity.this,
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
                intent.setData(Uri.parse("package:" + OrderRefundActivity.this.getPackageName()));
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

}
