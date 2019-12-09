package com.aite.a.activity;

import com.aite.a.fargment.Fragment_community;
import com.aite.a.fargment.Fragment_found;
import com.aite.a.fargment.Fragment_information;
import com.aiteshangcheng.a.R;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 资讯主页
 *
 * @author Administrator
 */
public class InformationActivity extends FragmentActivity implements
        OnClickListener {
    private FrameLayout fm_information;
    private LinearLayout ll_menu_mall, ll_menu_information, ll_menu_found,
            ll_menu_community;
    private ImageView iv_menu_mall, iv_menu_information, iv_menu_found,
            iv_menu_community, _iv_back;
    private TextView tv_menu_mall, tv_menu_information, tv_menu_found,
            tv_menu_community, _tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.actvivity_information);
        findview();
    }

    public InformationActivity() {
    }

    private void findview() {
        fm_information = (FrameLayout) findViewById(R.id.fm_information);
        ll_menu_mall = (LinearLayout) findViewById(R.id.ll_menu_mall);
        ll_menu_information = (LinearLayout) findViewById(R.id.ll_menu_information);
        ll_menu_found = (LinearLayout) findViewById(R.id.ll_menu_found);
        ll_menu_community = (LinearLayout) findViewById(R.id.ll_menu_community);
        iv_menu_mall = (ImageView) findViewById(R.id.iv_menu_mall);
        iv_menu_information = (ImageView) findViewById(R.id.iv_menu_information);
        iv_menu_found = (ImageView) findViewById(R.id.iv_menu_found);
        iv_menu_community = (ImageView) findViewById(R.id.iv_menu_community);
        tv_menu_mall = (TextView) findViewById(R.id.tv_menu_mall);
        tv_menu_information = (TextView) findViewById(R.id.tv_menu_information);
        tv_menu_found = (TextView) findViewById(R.id.tv_menu_found);
        tv_menu_community = (TextView) findViewById(R.id.tv_menu_community);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _iv_back.setImageDrawable(getResources().getDrawable(R.drawable.back));
        initView();
    }

    private void initView() {
        ll_menu_mall.setOnClickListener(this);
        ll_menu_information.setOnClickListener(this);
        ll_menu_found.setOnClickListener(this);
        ll_menu_community.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        _tv_name.setText(getString(R.string.informationcenter));

        // 通过用户中心进入
        String person_in = getIntent().getStringExtra("person_in");

        if (null != person_in && ("1").equals(person_in)) {
            //showFragment(1);
            ll_menu_found.performClick();
        } else if (null != person_in && ("2").equals(person_in)) {
            //showFragment(2);
            ll_menu_community.performClick();
        } else {
            showFragment(0);
        }


    }

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.ll_menu_mall:
//			// 商城
//			iv_menu_information.setImageResource(R.drawable.information1);
//			tv_menu_information.setTextColor(0xff888888);
//			iv_menu_found.setImageResource(R.drawable.found1);
//			tv_menu_found.setTextColor(0xff888888);
//			iv_menu_community.setImageResource(R.drawable.community1);
//			tv_menu_community.setTextColor(0xff888888);
//			iv_menu_mall.setImageResource(R.drawable.mall2);
//			tv_menu_mall.setTextColor(0xffff0000);
//			finish();
//			break;
//		case R.id.ll_menu_information:
//			// 资讯
//			showFragment(0);
//			/*Fragment_information a = (Fragment_information) fragments[0];
//			a.setvi();*/
//			iv_menu_information.setImageResource(R.drawable.information2);
//			tv_menu_information.setTextColor(0xffff0000);
//			iv_menu_found.setImageResource(R.drawable.found1);
//			tv_menu_found.setTextColor(0xff888888);
//			iv_menu_community.setImageResource(R.drawable.community1);
//			tv_menu_community.setTextColor(0xff888888);
//			iv_menu_mall.setImageResource(R.drawable.mall1);
//			tv_menu_mall.setTextColor(0xff888888);
//			_tv_name.setText(getString(R.string.informationcenter));
//			break;
//		case R.id.ll_menu_found:
//			// 微发现
//			showFragment(1);
//			iv_menu_information.setImageResource(R.drawable.information1);
//			tv_menu_information.setTextColor(0xff888888);
//			iv_menu_found.setImageResource(R.drawable.found2);
//			tv_menu_found.setTextColor(0xffff0000);
//			iv_menu_community.setImageResource(R.drawable.community1);
//			tv_menu_community.setTextColor(0xff888888);
//			iv_menu_mall.setImageResource(R.drawable.mall1);
//			tv_menu_mall.setTextColor(0xff888888);
//			_tv_name.setText(getString(R.string.aitefound));
//			break;
//		case R.id.ll_menu_community:
//			// 社区
//			showFragment(2);
//			iv_menu_information.setImageResource(R.drawable.information1);
//			tv_menu_information.setTextColor(0xff888888);
//			iv_menu_found.setImageResource(R.drawable.found1);
//			tv_menu_found.setTextColor(0xff888888);
//			iv_menu_community.setImageResource(R.drawable.community2);
//			tv_menu_community.setTextColor(0xffff0000);
//			iv_menu_mall.setImageResource(R.drawable.mall1);
//			tv_menu_mall.setTextColor(0xff888888);
//			_tv_name.setText(getString(R.string.aitecommunity));
//			break;
//		case R.id._iv_back:
//			// 退出
//			finish();
//			break;
//		}
        if (v.getId() == R.id._iv_back) {
            // 退出
            onBackPressed();
        } else if (v.getId() == R.id.ll_menu_mall) {
            // 商城
            iv_menu_information.setImageResource(R.drawable.information1);
            tv_menu_information.setTextColor(0xff888888);
            iv_menu_found.setImageResource(R.drawable.found1);
            tv_menu_found.setTextColor(0xff888888);
            iv_menu_community.setImageResource(R.drawable.community1);
            tv_menu_community.setTextColor(0xff888888);
            iv_menu_mall.setImageResource(R.drawable.mall2);
            tv_menu_mall.setTextColor(0xffff0000);
            finish();
        } else if (v.getId() == R.id.ll_menu_information) {
            // 资讯
            showFragment(0);
			/*Fragment_information a = (Fragment_information) fragments[0];
			a.setvi();*/
            iv_menu_information.setImageResource(R.drawable.information2);
            tv_menu_information.setTextColor(0xffff0000);
            iv_menu_found.setImageResource(R.drawable.found1);
            tv_menu_found.setTextColor(0xff888888);
            iv_menu_community.setImageResource(R.drawable.community1);
            tv_menu_community.setTextColor(0xff888888);
            iv_menu_mall.setImageResource(R.drawable.mall1);
            tv_menu_mall.setTextColor(0xff888888);
            _tv_name.setText(getString(R.string.informationcenter));
        } else if (v.getId() == R.id.ll_menu_found) {
            // 微发现
            showFragment(1);
            iv_menu_information.setImageResource(R.drawable.information1);
            tv_menu_information.setTextColor(0xff888888);
            iv_menu_found.setImageResource(R.drawable.found2);
            tv_menu_found.setTextColor(0xffff0000);
            iv_menu_community.setImageResource(R.drawable.community1);
            tv_menu_community.setTextColor(0xff888888);
            iv_menu_mall.setImageResource(R.drawable.mall1);
            tv_menu_mall.setTextColor(0xff888888);
            _tv_name.setText(getString(R.string.aitefound));
        } else if (v.getId() == R.id.ll_menu_community) {
            // 社区
            showFragment(2);
            iv_menu_information.setImageResource(R.drawable.information1);
            tv_menu_information.setTextColor(0xff888888);
            iv_menu_found.setImageResource(R.drawable.found1);
            tv_menu_found.setTextColor(0xff888888);
            iv_menu_community.setImageResource(R.drawable.community2);
            tv_menu_community.setTextColor(0xffff0000);
            iv_menu_mall.setImageResource(R.drawable.mall1);
            tv_menu_mall.setTextColor(0xff888888);
            _tv_name.setText(getString(R.string.aitecommunity));
        }
    }

    private int lastIndex = -1;

    /**
     * 显示Fragment
     *
     * @param index
     */
    private void showFragment(int index) {
        if (index == lastIndex) {
            return;
        }
        FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
        if (lastIndex != -1) {
            bt.hide(fragments[lastIndex]);
        }
        if (fragments[index] == null) {
            bt.add(R.id.fm_information, createFragment(index));
        } else {
            bt.show(fragments[index]);
        }
        bt.commit();
        lastIndex = index;
    }

    private Fragment[] fragments = new Fragment[3];

    /**
     * 创建Fragment
     *
     * @param index
     * @return
     */
    private Fragment createFragment(int index) {
        switch (index) {
            case 0:
                return (fragments[index] = new Fragment_information());
            case 1:
                return (fragments[index] = new Fragment_found());
            case 2:
                return (fragments[index] = new Fragment_community());
        }
        return null;
    }
}
