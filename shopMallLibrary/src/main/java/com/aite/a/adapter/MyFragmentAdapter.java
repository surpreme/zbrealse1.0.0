package com.aite.a.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> listF = null;

	public MyFragmentAdapter(FragmentManager fm,List<Fragment> listF) {
		super(fm);
		this.listF=listF;
	}

	@Override
	public Fragment getItem(int position) {
		return listF.get(position);
		
	}

	@Override
	public int getCount() {
		return listF.size();
		
	}

}
