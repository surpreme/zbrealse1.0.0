package com.aite.a.model;

import android.graphics.Bitmap;

/**
 * 文件列表
 * 
 * @author Administrator
 *
 */
public class ReleaseFile2Info {
	public Bitmap img;
	public String file;
	
	public ReleaseFile2Info() {
	}
	public ReleaseFile2Info(Bitmap img, String file) {
		super();
		this.img = img;
		this.file = file;
	}
	
}
