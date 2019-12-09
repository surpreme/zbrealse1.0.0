package com.aite.a.base;

import androidx.fragment.app.Fragment;

public abstract class ViewTransition extends Fragment implements
		ViewSwitchover, Sort {
	public ViewSwitchover viewSwitchover;
	public Sort sort;

	public void setTransition(ViewSwitchover viewSwitchover) {
		this.viewSwitchover = viewSwitchover;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
}

/**
 * 视图切换
 * 
 * @author CAOYOU
 *
 */
interface ViewSwitchover {
	public void Transition(boolean Transition);
}

/**
 * 升降序
 * 
 * @author CAOYOU
 *
 */
interface Sort {
	public void Change(boolean ASC);
};