package com.aite.a.activity.li.activity;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

import com.aiteshangcheng.a.R2;
import
com.google.android.material.tabs.TabLayout;


import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.activity.GoodsListActivity;
import com.aite.a.activity.li.adapter.ChoiceActivityViewPagerApdapter;
import com.aiteshangcheng.a.R;



import butterknife.BindView;

public class ChoiceActivity extends BaseActivity  {

    @BindView(R2.id.viewpager_id)
    ViewPager viewPager;
    @BindView(R2.id.thingsfix_tabMode)
    TabLayout tabLayout;
    @BindView(R2.id.search_img)
    ImageView search_img;
    @BindView(R2.id._search_edit)
    EditText _search_edit;

    private View[] views;
    private ChoiceActivityViewPagerApdapter viewPagerAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.choice_type_activity;
    }

    @Override
    protected void initView() {
        search_img.setOnClickListener((View.OnClickListener) context);
        _search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                  search( v.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });
        initFragment();
    }

    @Override
    protected void initModel() {
//        initLocationOption();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.search_img:
//                search(null);
//                break;
//        }
        if (v.getId()==R.id.search_img){
            search(null);
        }
    }

    private void search(String search_txt) {
        if (search_txt==null|| TextUtils.isEmpty(_search_edit.getText().toString()))
         search_txt = _search_edit.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", search_txt);
        if (!search_txt.equals("")) {
            startActivity(GoodsListActivity.class, bundle);
        }
    }

    private void initFragment() {
        views = new View[3];
        LayoutInflater layoutInflater = LayoutInflater.from(ChoiceActivity.this);
        views[0] = layoutInflater.inflate(R.layout.fragment_amclassify, null);
        views[1] = layoutInflater.inflate(R.layout.fragment_amclassbrand, null);
        views[2] = layoutInflater.inflate(R.layout.shop_type, null);
        viewPagerAdapter = new ChoiceActivityViewPagerApdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
