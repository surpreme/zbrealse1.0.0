package com.community.model;

import java.io.File;

/**
 * 图片
 * Created by mayn on 2018/9/22.
 */
public class PhotoInfo {
    public boolean isfile;
    public File img;

    public PhotoInfo(boolean isfile, File img) {
        this.isfile = isfile;
        this.img = img;
    }
}
