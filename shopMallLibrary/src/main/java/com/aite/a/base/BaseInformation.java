package com.aite.a.base;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseInformation extends Fragment implements Mark {
	protected View layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		layout = inflater.inflate(getlayoutResId(), null);
		initView();
		initData();
		return layout;
	}

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 请求数据
	 */
	protected abstract void initData();

	/**
	 * 布局
	 * 
	 * @return 布局的ID
	 */
	protected abstract int getlayoutResId();
}
