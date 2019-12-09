package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.AddressManageActivity;
import com.aite.a.activity.ChangePassword;
import com.aite.a.activity.ComplaintsListActivity;
import com.aite.a.activity.CustomerServiceActtivity;
import com.aite.a.activity.DistributionCenterActivity;
import com.aite.a.activity.ExchangeRecordActivity;
import com.aite.a.activity.FavoriteListFargmentActivity;
import com.aite.a.activity.IdentityActivity;
import com.aite.a.activity.InformationActivity;
import com.aite.a.activity.IntegralInfoActivity;
import com.aite.a.activity.IntegralShopActivity;
import com.aite.a.activity.MoreActivity;
import com.aite.a.activity.MyCalendarActivity;
import com.aite.a.activity.MyStoreActivity;
import com.aite.a.activity.Myevaluation;
import com.aite.a.activity.MyfootprintActivity;
import com.aite.a.activity.OnlineTopUpActivity;
import com.aite.a.activity.PersonalInformationActivity;
import com.aite.a.activity.PhoneCertificationActivity;
import com.aite.a.activity.RedPackageActivityList;
import com.aite.a.activity.RefundActivity;
import com.aite.a.activity.SellerOrderActivity;
import com.aite.a.activity.ServicehomeActivity;
import com.aite.a.activity.StationLetterListActivity;
import com.aite.a.activity.WebActivity;
import com.aite.a.model.MeMenu2info;
import com.aite.a.model.User;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.community.activity.TabActivity;

import java.util.List;

import hotel.HotelHomeActivity;
import hotel.HotelOrderListActivity;
import livestream.activity.LiveStreamTabActivity;

/**
 * 个人中心菜单
 * Created by mayn on 2018/4/26.
 */
public class MeMenu2Adapter extends BaseAdapter {
    private Context mcontext;
    private List<MeMenu2info> data;
    private User user;

    public MeMenu2Adapter(Context mcontext, List<MeMenu2info> data, User user) {
        this.mcontext = mcontext;
        this.data = data;
        this.user = user;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_memenu2, null);
            new MeMenu2Holder(convertView);
        }
        MeMenu2Holder holder = (MeMenu2Holder) convertView.getTag();
        final MeMenu2info info = data.get(position);
        List<MeMenu2info.item> item = info.item;
        holder.tv_title.setText(info.title);
        MeMenu3Adapter meMenu3Adapter = new MeMenu3Adapter(item);
        holder.mgv_menu.setAdapter(meMenu3Adapter);
        return convertView;
    }

    class MeMenu2Holder {
        TextView tv_title;
        MyGridView mgv_menu;

        public MeMenu2Holder(View convertView) {
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mgv_menu = (MyGridView) convertView.findViewById(R.id.mgv_menu);
            convertView.setTag(this);
        }
    }

    class MeMenu3Adapter extends BaseAdapter {
        private List<MeMenu2info.item> item;

        public MeMenu3Adapter(List<MeMenu2info.item> item) {
            this.item = item;
        }

        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mcontext, R.layout.maijia_item, null);
                new MeMenu3Holder(convertView);
            }
            MeMenu3Holder holder = (MeMenu3Holder) convertView.getTag();
            // 高度适配
            ViewGroup.LayoutParams layoutParams = holder.ll_menuitem.getLayoutParams();
            layoutParams.height = getScreenWidth(mcontext) / 4;
            holder.ll_menuitem.setLayoutParams(layoutParams);
            final MeMenu2info.item item = this.item.get(position);
            holder.iv_menuimg.setImageResource(item.img);
            holder.tv_menutext.setText(item.txt);
            holder.ll_menuitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.txt.equals(mcontext.getString(R.string.discover))){//发现
                        Intent intent0 = new Intent(mcontext, TabActivity.class);
                        mcontext.startActivity(intent0);
                    }else if (item.txt.equals(mcontext.getString(R.string.buyer_orders))){//订单管理
                       /* Intent intent1 = new Intent(mcontext, BuyerOrderFgActivity.class);
                        mcontext.startActivity(intent1);*/
                    }else if (item.txt.equals(mcontext.getString(R.string.virtualorders))){//虚拟订单
                       /* Intent intentvr = new Intent(mcontext, WebActivity.class);
                        intentvr.putExtra("url", "http://aitecc.com/wap/index.php?act=member_vr_order");
                        mcontext.startActivity(intentvr);*/
                    }else if (item.txt.equals(mcontext.getString(R.string.gif2))){//兑换记录
                        Intent intent3 = new Intent(mcontext, ExchangeRecordActivity.class);
                        mcontext.startActivity(intent3);
                    }else if (item.txt.equals(mcontext.getString(R.string.my_shopping_cart))){//购物车
                        /*Bundle bundle1 = new Bundle();
                        bundle1.putString("shoopping", "shoopping");
                        Intent intent4 = new Intent(mcontext, CartActivity.class);
                        intent4.putExtras(bundle1);
                        mcontext.startActivity(intent4);*/
                    }else if (item.txt.equals(mcontext.getString(R.string.my_footprint))){//我的足迹
                        Intent intent5 = new Intent(mcontext, MyfootprintActivity.class);
                        mcontext.startActivity(intent5);
                    }else if (item.txt.equals(mcontext.getString(R.string.address_manage))){//地址管理
                        Intent intent6 = new Intent(mcontext, AddressManageActivity.class);
                        mcontext.startActivity(intent6);
                    }else if (item.txt.equals(mcontext.getString(R.string.perdata))){//个人资料
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("icon", user.avator);
                        Intent intent7 = new Intent(mcontext, PersonalInformationActivity.class);
                        intent7.putExtras(bundle2);
                        mcontext.startActivity(intent7);
//                        Intent intent18 = new Intent(mcontext, WebActivity.class);
//                        intent18.putExtra("url", "https://aitecc.com/wap/index.php?act=member_information&op=member");
//                        mcontext.startActivity(intent18);
                    }else if (item.txt.equals(mcontext.getString(R.string.update_password))){//修改密码
                        Intent intent8 = new Intent(mcontext, ChangePassword.class);
                        mcontext.startActivity(intent8);
                    }else if (item.txt.equals(mcontext.getString(R.string.collectionfcf2))){//收藏店铺
                        Intent intent9 = new Intent(mcontext, FavoriteListFargmentActivity.class);
                        intent9.putExtra("i", 2);
                        mcontext.startActivity(intent9);
                    }else if (item.txt.equals(mcontext.getString(R.string.collectionfcf))){//收藏宝贝
                        Intent intent10 = new Intent(mcontext, FavoriteListFargmentActivity.class);
                        intent10.putExtra("i", 1);
                        mcontext.startActivity(intent10);
                    }else if (item.txt.equals(mcontext.getString(R.string.myevaluation))){//我的评价
                        Intent intent11 = new Intent(mcontext, Myevaluation.class);
                        intent11.putExtra("touxiang", user.avator);
                        intent11.putExtra("names", user.user_name);
                        mcontext.startActivity(intent11);
                    }else if (item.txt.equals(mcontext.getString(R.string.distribution_center5))){//分销中心
                        if (user == null || user.member_rank == null
                                || user.member_rank.equals("0")) {
                            Toast.makeText(mcontext, mcontext.getString(R.string.distribution_center11), Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent12 = new Intent(mcontext, DistributionCenterActivity.class);
                            mcontext.startActivity(intent12);
                        }
                    }else if (item.txt.equals(mcontext.getString(R.string.integral_mall))){//积分商城
                        Intent intent13 = new Intent(mcontext, IntegralShopActivity.class);
                        intent13.putExtra("person_in", "1");
                        mcontext.startActivity(intent13);
                    }else if (item.txt.equals(mcontext.getString(R.string.distribution_center27))){//会员认证
                        Intent intent14 = new Intent(mcontext, IdentityActivity.class);
                        mcontext.startActivity(intent14);
                    }else if (item.txt.equals(mcontext.getString(R.string.distribution_center25))){//账户安全
                        Intent intent15 = new Intent(mcontext, PhoneCertificationActivity.class);
                        mcontext.startActivity(intent15);
                    }else if (item.txt.equals(mcontext.getString(R.string.stand_inside_letter))){//站内信
                        Intent intent16 = new Intent(mcontext, StationLetterListActivity.class);
                        intent16.putExtra("type", "ordinary");
                        mcontext.startActivity(intent16);
                    }else if (item.txt.equals(mcontext.getString(R.string.integrall))){//积分
                        Intent intent17 = new Intent(mcontext, IntegralInfoActivity.class);
                        intent17.putExtra("person_in", "1");
                        mcontext.startActivity(intent17);
                    }else if (item.txt.equals(mcontext.getString(R.string.news))){//新闻
                        Intent intent18 = new Intent(mcontext, WebActivity.class);
                        intent18.putExtra("url", "http://aitecc.com/wap/index.php?act=news");
                        mcontext.startActivity(intent18);
                    }else if (item.txt.equals(mcontext.getString(R.string.shequ))){//社区
                        Intent intent19 = new Intent(mcontext, InformationActivity.class);
                        intent19.putExtra("person_in", "2");
                        mcontext.startActivity(intent19);
                    }else if (item.txt.equals(mcontext.getString(R.string.weifaxian))){//微发现
                        Intent intent20 = new Intent(mcontext, WebActivity.class);
                        intent20.putExtra("url", "http://aitecc.com/wap/index.php?act=weifaxian");
                        mcontext.startActivity(intent20);
                    }else if (item.txt.equals("品牌")){//品牌
                        Intent intent20 = new Intent(mcontext, WebActivity.class);
                        intent20.putExtra("url", "https://aitecc.com/wap/index.php?act=brand");
                        mcontext.startActivity(intent20);
                    }else if (item.txt.equals("圈子")){//圈子
                        Intent intent20 = new Intent(mcontext, WebActivity.class);
                        intent20.putExtra("url", "https://aitecc.com/wap/index.php?act=circle");
                        mcontext.startActivity(intent20);
                    }else if (item.txt.equals("发现")){//发现
                        Intent intent20 = new Intent(mcontext, WebActivity.class);
                        intent20.putExtra("url", "https://aitecc.com/wap/index.php?act=find");
                        mcontext.startActivity(intent20);
                    }else if (item.txt.equals("晒单")){//晒单
                        Intent intent20 = new Intent(mcontext, WebActivity.class);
                        intent20.putExtra("url", "https://aitecc.com/wap/index.php?act=weifaxian");
                        mcontext.startActivity(intent20);
                    }else if (item.txt.equals(mcontext.getString(R.string.hotel))){//酒店
                        Intent intent21 = new Intent(mcontext, HotelHomeActivity.class);
                        mcontext.startActivity(intent21);
                    }else if (item.txt.equals(mcontext.getString(R.string.redpackageactivity))){//红包活动
                        Intent intent22 = new Intent(mcontext, RedPackageActivityList.class);
                        mcontext.startActivity(intent22);
                    }else if (item.txt.equals(mcontext.getString(R.string.hotel_order))){//酒店订单
                        Intent intent23 = new Intent(mcontext, HotelOrderListActivity.class);
                        mcontext.startActivity(intent23);
                    }else if (item.txt.equals(mcontext.getString(R.string.service))){//服务
                        Intent intent24 = new Intent(mcontext, ServicehomeActivity.class);
                        mcontext.startActivity(intent24);
                    }else if (item.txt.equals("在线充值")){//在线充值
//                        Intent intent24 = new Intent(mcontext, TopUpActivity.class);
                        Intent intent24 = new Intent(mcontext, OnlineTopUpActivity.class);
                        mcontext.startActivity(intent24);
                    }else if (item.txt.equals(mcontext.getString(R.string.order_reminder163))){//直播
                        Intent intent25 = new Intent(mcontext, LiveStreamTabActivity.class);
                        mcontext.startActivity(intent25);
                    }else if (item.txt.equals(mcontext.getString(R.string.signin))){//签到
                        Intent intent26 = new Intent(mcontext, MyCalendarActivity.class);
                        intent26.putExtra("name", user.nickname);
                        intent26.putExtra("icoon", user.avator);
                        mcontext.startActivity(intent26);
                    }else if (item.txt.equals(mcontext.getString(R.string.integral_prompt19))){//平台客服
                        Intent intent27 = new Intent(mcontext, CustomerServiceActtivity.class);
                        mcontext.startActivity(intent27);
                    }else if (item.txt.equals(mcontext.getString(R.string.tradecomplaint))){//交易投诉
                        Intent intent28 = new Intent(mcontext, ComplaintsListActivity.class);
                        mcontext.startActivity(intent28);
                    }else if (item.txt.equals(mcontext.getString(R.string.distribution_center24))){//退款退货
                        Intent intent29 = new Intent(mcontext, RefundActivity.class);
                        mcontext.startActivity(intent29);
                    }else if (item.txt.equals(mcontext.getString(R.string.storehome29))){//我的店铺
                        Intent intent30 = new Intent(mcontext, MyStoreActivity.class);
                        mcontext.startActivity(intent30);
                    }else if (item.txt.equals(mcontext.getString(R.string.merchant_order))){//商家订单
                        Intent intent31 = new Intent(mcontext, SellerOrderActivity.class);
                        mcontext.startActivity(intent31);
                    }else if (item.txt.equals(mcontext.getString(R.string.set_up))){//设置
                        Intent intent32 = new Intent(mcontext, MoreActivity.class);
                        mcontext.startActivity(intent32);
                    }
                }
            });
            return convertView;
        }

        class MeMenu3Holder {
            LinearLayout ll_menuitem;
            ImageView iv_menuimg;
            TextView tv_menutext;

            public MeMenu3Holder(View convertView) {
                ll_menuitem = (LinearLayout) convertView.findViewById(R.id.ll_menuitem);
                iv_menuimg = (ImageView) convertView.findViewById(R.id.iv_menuimg);
                tv_menutext = (TextView) convertView.findViewById(R.id.tv_menutext);
                convertView.setTag(this);
            }
        }
    }

    // 获得屏幕宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}
