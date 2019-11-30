package com.aite.mainlibrary.activity.allsetting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.util.ImageUtils;
import com.zhihu.matisse.Matisse;
import java.util.List;
import butterknife.BindView;

public class UserInformationActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.user_icon)
    ImageView userIcon;
    @BindView(R2.id.user_icon_ll)
    LinearLayout userIconLl;
    private List<Uri> mSelected;


    @Override
    protected int getLayoutResId() {
        return R.layout.user_setting_layout;
    }

    @Override
    protected void initView() {
        tvTitle.setText("个人信息");
        ivBack.setOnClickListener(this);
        userIcon.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
//            File file = FileUtils.getFileByUri(context, mSelected.get(0));
            ImageUtils.getmInstance().photoClip(UserInformationActivity.this, mSelected.get(0));

        } else if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_CLIP && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //在这里获得了剪裁后的Bitmap对象，可以用于上传
                Bitmap bitmap = bundle.getParcelable("data");
                ImageUtils.getmInstance().saveBitmap(context, bitmap, "userIcon");
                Glide.with(context).load(bitmap).transform(new CircleCrop()).into(userIcon);

            }


        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.user_icon) {
            openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE);
        }

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }
}
