package courier;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.AddresSregionListActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 申请开通驿站
 * Created by Administrator on 2018/1/15.
 */
public class OpenPostActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_break;
    private TextView tv_name, tv_right, tv_city;
    private EditText et_postname, et_name, et_phone, et_telephone, et_address, et_cardnum;
    private ImageView iv_sfz;
    private NetRun netRun;
    private String city_id,area_id;
    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;

    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case open_post_id:
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(OpenPostActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.equals("操作成功，等待管理员审核")){
                            finish();
                        }
                    }
                    break;
                case open_post_err:
                    Toast.makeText(OpenPostActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openpost);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_city = (TextView) findViewById(R.id.tv_city);
        et_postname = (EditText) findViewById(R.id.et_postname);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_telephone = (EditText) findViewById(R.id.et_telephone);
        et_address = (EditText) findViewById(R.id.et_address);
        et_cardnum = (EditText) findViewById(R.id.et_cardnum);
        iv_sfz = (ImageView) findViewById(R.id.iv_sfz);
        initView();
    }

    @Override
    protected void initView() {
        tv_right.setText("提交");
        tv_name.setText("申请开通驿站");
        ll_break.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        iv_sfz.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.tv_city) {//选择地址
            Intent intent = new Intent(this, AddresSregionListActivity.class);
            startActivityForResult(intent, Sregion_Result);
        } else if (id == R.id.tv_right) {//提交
            String postname = et_postname.getText().toString();
            String name = et_name.getText().toString();
            String phone = et_phone.getText().toString();
            String telephone = et_telephone.getText().toString();
            String address = tv_city.getText().toString();
            String cardnum = et_cardnum.getText().toString();
            String daddress = et_address.getText().toString();
            if (TextUtils.isEmpty(postname) || postname.length() == 0) {
                Toast.makeText(OpenPostActivity.this, "驿站名称没填", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name) || name.length() == 0) {
                Toast.makeText(OpenPostActivity.this, "姓名没填", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(phone) || phone.length() == 0) {
                Toast.makeText(OpenPostActivity.this, "手机号码没填", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(cardnum) || cardnum.length() == 0) {
                Toast.makeText(OpenPostActivity.this, "身份证没填", Toast.LENGTH_SHORT).show();
                return;
            }
            if (city_id == null || area_id == null) {
                Toast.makeText(OpenPostActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                return;
            }
            if (tempFile == null || !tempFile.exists()) {
                Toast.makeText(OpenPostActivity.this, "请选择身份证扫描件", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(telephone) || telephone.length() == 0) {
                telephone = "";
            }
            netRun.OpenPost(name, phone, telephone, postname, city_id, area_id, address, daddress, cardnum, tempFile);
        } else if (id == R.id.iv_sfz) {//身份证扫描件
            startPick();
        }
    }

    // 调用系统相册
    protected void startPick() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        this.startActivityForResult(intent, PHOTO_PICK);
    }

    // 调用系统裁剪
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        this.startActivityForResult(intent, PHOTO_CUT);
    }

    private Bitmap bmp = null;

    // 将裁剪后的图片显示在ImageView上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
//            Bitmap decodeFile = BitmapFactory.decodeFile(tempFile
//                    .getAbsolutePath());
            Drawable path = Drawable.createFromPath(tempFile.getAbsolutePath());
            iv_sfz.setImageDrawable(path);
            // Log.i("skythinking", tempFile.getAbsolutePath());
        }
    }

    // 把裁剪后的图片保存到sdcard上
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CARMERA:
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case PHOTO_PICK:
                if (null != data) {
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        bmp = bundle.getParcelable("data");
                        saveCropPic(bmp);
                    }
                    setPicToView(data);
                }
                break;
            case Sregion_Result:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    String district = bundle.getString("district_name");
                    String city = bundle.getString("city_name");
                    String province = bundle.getString("province_name");
                    city_id = bundle.getString("city_id");
                    area_id = bundle.getString("area_id");
                    if (district != null && city != null && province != null
                            && city_id != null && area_id != null) {
                        tv_city.setText(province + city + district);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    @Override
    public void ReGetData() {

    }
}
