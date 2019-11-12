package com.aite.mainlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.mainlibrary.mvp.SampleContract;
import com.lzy.basemodule.base.BaseFragment;
import com.lzy.basemodule.logcat.LogUtils;

import butterknife.BindView;

public class MineFragment extends BaseFragment{
    @BindView(R2.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R2.id.setting_gridview)
    GridView settingGridview;
    @BindView(R2.id.book_gridview)
    GridView bookGridview;
    @BindView(R2.id.else_gridview)
    GridView elseGridview;
    @BindView(R2.id.device_gridview)
    GridView deviceGridview;
    @BindView(R2.id.fix_friends_btn)
    Button fixFriendsBtn;


    @Override
    protected void initModel() {

    }

    @Override
    protected void initViews() {
        settingGridview.setAdapter(new HomeAdapter(context, Constant.settingImg, Constant.settingTv));
        bookGridview.setAdapter(new HomeAdapter(context, Constant.bookImg, Constant.bookTv));
        elseGridview.setAdapter(new HomeAdapter(context, Constant.elseImg, Constant.elseTv));
        deviceGridview.setAdapter(new HomeAdapter(context, Constant.deviceImg, Constant.deviceTv));

        fixFriendsBtn.setOnClickListener(this);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mine_fragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fix_friends_btn) {
            LogUtils.d("被点击");

        }

    }


    private class HomeAdapter extends BaseAdapter {

        private Context context;
        private int[] imgs;
        private String[] names;

        public HomeAdapter(Context context, int[] imgs, String[] names) {
            this.context = context;
            this.imgs = imgs;
            this.names = names;
        }

        @Override
        public int getCount() {
            return names == null ? 0 : names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View view = View.inflate(context,
                    R.layout.mine_setting_item, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            TextView title = (TextView) view.findViewById(R.id.title);
            if (imgs != null && names != null) {
                title.setText(names[position]);
                icon.setImageResource(imgs[position]);
            }
            return view;
        }

    }

    private static class Constant {
        private static int[] settingImg = {
                R.mipmap.mine_device,
                R.mipmap.activity,
                R.mipmap.house,
                R.mipmap.collect,
        };

        private static String[] settingTv = {
                "我的设备", "我的活动", "我的社区", "我的收藏"
        };

        private static int[] bookImg = {
                R.mipmap.eat,
                R.mipmap.helpdoctor,

                R.mipmap.service,
                R.mipmap.elsesevice,

                R.drawable.baomu,
                R.mipmap.learn_work,

                R.drawable.finduser,
                R.drawable.lessbodything

        };
        private static String[] bookTv = {
                "助餐", "助医", "喘息服务", "其它服务",
                "日托", "培训", "辅助就业", "助残活动"
        };

        private static int[] elseImg = {
                R.mipmap.money,
                R.mipmap.health,
                R.mipmap.opennew,
                R.mipmap.error,
        };
        private static String[] elseTv = {
                "我的钱包", "健康档案", "我的发布", "交易投诉"
        };
        private static int[] deviceImg = {
                R.mipmap.add_blue
        };
        private static String[] deviceTv = {
                "添加设备"
        };
    }
}
