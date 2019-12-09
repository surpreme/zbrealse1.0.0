package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.HomeTabActivity;
import com.aite.a.activity.DistributionCenterActivity;
import com.aite.a.activity.FavoriteListFargmentActivity;
import com.aite.a.activity.HotVouchersListActivity;
import com.aite.a.activity.IntegralInfoActivity;
import com.aite.a.activity.IntegralShopActivity;
import com.aite.a.activity.LoginActivity;
import com.aite.a.activity.MyCalendarActivity;
import com.aite.a.activity.MyfootprintActivity;
import com.aite.a.activity.WebActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.NavigationInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

public class NavigationActivity extends BaseAdapter {
    private Context mcontext;
    private List<NavigationInfo> navigationInfo;

    public NavigationActivity(Context mcontext, List<NavigationInfo> navigationInfo) {
        this.mcontext = mcontext;
        this.navigationInfo = navigationInfo;
    }

    @Override
    public int getCount() {
        return navigationInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationInfo == null ? null : navigationInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext,
                    R.layout.item_bc_navigation, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final NavigationInfo data = this.navigationInfo.get(position);
        holder.tv_miyamenutext.setText(data.name);
        holder.iv_miyamenuiag.setImageResource(data.img);
        holder.ll_item.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 导航
                if (data.name.equals(mcontext.getString(R.string.signin))) {//签到
                    if (Mark.State.UserKey != null) {
                        Intent intent26 = new Intent(mcontext, MyCalendarActivity.class);
                        mcontext.startActivity(intent26);
                    } else {
                        Toast.makeText(mcontext, mcontext.getString(R.string.not_login_please_login),
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (data.name.equals(mcontext.getString(R.string.distribution_center38))) {//圈子
                    Intent intent = new Intent(mcontext, WebActivity.class);
                    intent.putExtra("url", "https://aitecc.com/wap/index.php?act=circle&recommend=1");
                    mcontext.startActivity(intent);
                }else if (data.name.equals(mcontext.getString(R.string.shequ))) {//社区
                    Intent intent = new Intent(mcontext, WebActivity.class);
                    intent.putExtra("url", "https://aitecc.com/wap/index.php?act=circle");
                    mcontext.startActivity(intent);
                } else if (data.name.equals(mcontext.getString(R.string.footprint))) {//足迹
                    if (Mark.State.UserKey != null) {
                        Intent zuji = new Intent(mcontext,
                                MyfootprintActivity.class);
                        zuji.putExtra("person_in", "2");
                        mcontext.startActivity(zuji);
                    } else {
                        Toast.makeText(mcontext, mcontext.getString(R.string.not_login_please_login),
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (data.name.equals(mcontext.getString(R.string.sundrying))) {//晒单
                    Intent fxintent = new Intent(mcontext, WebActivity.class);
                    fxintent.putExtra("url", "http://aitecc.com/wap/index.php?act=weifaxian");
                    mcontext.startActivity(fxintent);
                } else if (data.name.equals(mcontext.getString(R.string.hotspot))) {//热点
                    Intent rdintent = new Intent(mcontext, WebActivity.class);
                    rdintent.putExtra("url", "http://aitecc.com/wap/index.php?act=news");
                    mcontext.startActivity(rdintent);
                } else if (data.name.equals(mcontext.getString(R.string.integrall))) {//积分
                    Intent intent4;
                    if (Mark.State.UserId == null || Mark.State.UserKey == null){
                        intent4 =  new Intent(mcontext,LoginActivity.class);
                    }else {

                        intent4 = new Intent(mcontext, IntegralInfoActivity.class);
                    intent4.putExtra("person_in", "1");
                    }
                    mcontext.startActivity(intent4);
                } else if (data.name.equals(mcontext.getString(R.string.message))) {//消息

                } else if (data.name.equals(mcontext.getString(R.string.getacoupon))) {//领券
                    Intent intent6 = new Intent(mcontext,
                            HotVouchersListActivity.class);
                    mcontext.startActivity(intent6);
                } else if (data.name.equals(mcontext.getString(R.string.store_up))) {//收藏
                    if (Mark.State.UserKey != null) {
                    Intent intent7 = new Intent(mcontext,
                            FavoriteListFargmentActivity.class);
                    intent7.putExtra("i", 2);
                    mcontext.startActivity(intent7);}
                    else {
                        Toast.makeText(mcontext, mcontext.getString(R.string.not_login_please_login),
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (data.name.equals(mcontext.getString(R.string.tab_category))) {//分类
                    HomeTabActivity.categoryBtn.performClick();
                } else if (data.name.equals(mcontext.getString(R.string.exchange))) {//兑换
                    Intent intent9 = new Intent(mcontext,
                            IntegralShopActivity.class);
                    intent9.putExtra("person_in", "1");
                    mcontext.startActivity(intent9);
                } else if (data.name.equals(mcontext.getString(R.string.distribution2))) {//分销
                    if (Mark.State.UserKey == null) {
                        Toast.makeText(mcontext, mcontext
                                        .getString(R.string.not_login_please_login),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent16 = new Intent(mcontext,
                                DistributionCenterActivity.class);
                        mcontext.startActivity(intent16);
                    }
                } else if (data.name.equals(mcontext.getString(R.string.sundrying))) {//晒单
                    Intent rdintent11 = new Intent(mcontext, WebActivity.class);
                    rdintent11.putExtra("url", "http://aitecc.com/wap/index.php?act=weifaxian");
                    mcontext.startActivity(rdintent11);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_miyamenutext;
        ImageView iv_miyamenuiag;
        LinearLayout ll_item;

        public ViewHolder(View convertView) {
            tv_miyamenutext = (TextView) convertView
                    .findViewById(R.id.tv_miyamenutext);
            iv_miyamenuiag = (ImageView) convertView
                    .findViewById(R.id.iv_miyamenuiag);
            ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(this);
        }
    }
}
