package com.aite.a.model;

import android.graphics.Bitmap;

public class WorkerDataInfo {

	public String member_name;
	public String member_truename;
	public String member_avatar_url;
	public String member_mobile;
	public String workers;
	public Bitmap bitmap_photo;

	public String toString() {
		return "[" + "member_truename=" + member_truename + "，member_avatar_url="
				+ member_avatar_url + "，member_mobile=" + member_mobile
				+ "，workers=" + workers + "]";
	}
}
