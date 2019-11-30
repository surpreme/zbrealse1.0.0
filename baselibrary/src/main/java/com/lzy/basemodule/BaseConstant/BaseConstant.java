package com.lzy.basemodule.BaseConstant;

import android.os.Environment;

import com.lzy.basemodule.base.BaseApp;

import java.io.File;

public class BaseConstant {
    public static class PATH {
        public static final String FILE_PROVIDER_AUTHORITY = "com.lzy.base.fileprovider";
        public static final String PATH_DATA = BaseApp.getContext().getCacheDir().getAbsolutePath() + File.separator + "data";

        public static final String PATH_CACHE = PATH_DATA + "/NetCache";

        public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "lzy" + File.separator + "GeekNews";

    }

    public class RESULT_CODE {
        //选择照片
        public static final int REQUEST_CODE_CHOOSE_IMAGE_CLIP = 18715;
        public static final int REQUEST_CODE_CHOOSE_IMAGE_CLIP_TWO = 382815;
        public static final int REQUEST_CODE_CHOOSE_IMAGE = 12048;
        public static final int REQUEST_CODE_CHOOSE_IMAGE_TWO = 13848;
        public static final int REQUEST_CODE_CHOOSE_IMAGE_THREE = 11848;
        public static final int REQUEST_CODE_CHOOSE_IMAGE_FOUR = 12548;
        //拍照
        public static final int REQUEST_CAMERA = 18500;


    }

}
