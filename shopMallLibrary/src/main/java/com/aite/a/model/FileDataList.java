package com.aite.a.model;

import java.io.InputStream;

import android.graphics.Bitmap;

public class FileDataList {
	public String fileName;
	public String filePath;
	public InputStream is;
	public Bitmap bitmap;

	public FileDataList(String fileName, String filePath, InputStream is,
			Bitmap bitmap) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.is = is;
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		return "FileDataList [fileName=" + fileName + ", filePath=" + filePath
				+ ", is=" + is + ", bitmap=" + bitmap + "]";
	}

}
