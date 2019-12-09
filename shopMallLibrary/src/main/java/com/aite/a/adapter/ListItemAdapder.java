package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.BaseAdapter;

public abstract class ListItemAdapder<T> extends BaseAdapter {
	protected Context mycontext = null;

	public ListItemAdapder(Context context) {
		mycontext = context;
	}
	private List<T> mList = null;

	/**
	 * 获取适配器的数据
	 * 
	 * @return
	 */
	public List<T> getmList() {

		return mList;
	}
	/**
	 * 覆盖适配器的数据
	 * 
	 * @param mList
	 */
	public void setmList(List<T> mList) {
		this.mList = mList;
		flushAdapter();// 刷新
	}

	/**
	 * 删除某一项
	 * 
	 * @param position
	 */
	public void deleteItem(int position) {
		mList.remove(position);
		flushAdapter();
	}

	/**
	 * 改变某一项的数据
	 * 
	 * @param item
	 * @param position
	 */
	public void updateItem(T item, int position) {
		mList.set(position, item);
		flushAdapter();
	}

	/**
	 * 添加一个集合数据到适配器
	 * 
	 * @param list
	 */
	public void addList(List<T> list) {
		if (mList == null) {
			mList = new ArrayList<T>();
		}
		mList.addAll(list);
		flushAdapter();
	}

	/**
	 * 往适配器添加一项数据
	 * 
	 * @param item
	 */
	public void addItem(T item) {
		if (mList == null) {
			mList = new ArrayList<T>();
		}
		mList.add(item);
		flushAdapter();
	}

	/**
	 * 刷新适配器
	 */
	public void flushAdapter() {
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public T getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
