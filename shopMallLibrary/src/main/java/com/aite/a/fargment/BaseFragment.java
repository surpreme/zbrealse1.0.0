package com.aite.a.fargment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aite.a.base.Mark;

public abstract class BaseFragment extends Fragment implements Mark{
	protected View layout = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = inflater.inflate(layoutResID(), null);
		initView();
		initData();
		return layout;
	}

	/**
	 * 初始化视图
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 返回布局ID
	 * 
	 * @return
	 */
	protected abstract int layoutResID();

}
