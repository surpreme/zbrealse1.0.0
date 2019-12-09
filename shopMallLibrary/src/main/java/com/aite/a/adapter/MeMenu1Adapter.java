package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.BuyerOrderFgActivity;
import com.aite.a.activity.RefundActivity;
import com.aite.a.activity.WebActivity;
import com.aite.a.model.MeMenuinfo;
import com.aiteshangcheng.a.R;
import com.blankj.rxbus.RxBus;

import java.util.List;

/**
 * 我的订单
 * Created by mayn on 2018/4/26.
 */

public class MeMenu1Adapter extends BaseAdapter {
    private Context mcontext;
    private List<MeMenuinfo> data;

    public MeMenu1Adapter(Context mcontext, List<MeMenuinfo> data) {
        this.mcontext = mcontext;
        this.data = data;
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
            convertView = View.inflate(mcontext, R.layout.navigation_item, null);
            new MeMenu1Holder(convertView);
        }
        MeMenu1Holder holder = (MeMenu1Holder) convertView.getTag();
        // 高度适配
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.ll_navigationitem
                .getLayoutParams();
        layoutParams.height = getScreenWidth(mcontext) / 5;
        holder.ll_navigationitem.setLayoutParams(layoutParams);
        final MeMenuinfo info = data.get(position);
        holder.iv_1daizhifufcf.setImageResource(info.img);
        holder.tv_navigationname.setText(info.txt);
        if (!info.num.equals("0")) {
            holder.tv_new.setVisibility(View.VISIBLE);
            holder.tv_new.setText(info.num);
        } else {
            holder.tv_new.setVisibility(View.INVISIBLE);
        }
        holder.ll_navigationitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, BuyerOrderFgActivity.class);
                if (info.txt.equals(mcontext.getString(R.string.distribution_center46))){//待支付
                    intent.putExtra("viewPager", 1);
                    mcontext.startActivity(intent);
                }else if (info.txt.equals(mcontext.getString(R.string.to_be_shipped))){//待发货
                    intent.putExtra("viewPager", 2);
                    mcontext.startActivity(intent);
                }else if (info.txt.equals(mcontext.getString(R.string.delivered))){//待收货
                    intent.putExtra("viewPager", 3);
                    mcontext.startActivity(intent);
                }else if (info.txt.equals(mcontext.getString(R.string.member_centre6))){//待评价
                    intent.putExtra("viewPager", 4);
                    mcontext.startActivity(intent);
                }else if (info.txt.equals(mcontext.getString(R.string.member_centre7))){//退换/售后
                    Intent intent29 = new Intent(mcontext, RefundActivity.class);
                    mcontext.startActivity(intent29);
                }else if (info.txt.equals(mcontext.getString(R.string.my_shopping_cart))){//购物车
                    RxBus.getDefault().post("jumpShopCar","jumpShopCar");
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("shoopping", "shoopping");
//                    Intent intent4 = new Intent(mcontext, CartActivity.class);
//                    intent4.putExtras(bundle1);
//                    mcontext.startActivity(intent4);
                }else if (info.txt.equals(mcontext.getString(R.string.buyer_orders))){//全部订单
                    Intent intent1 = new Intent(mcontext, BuyerOrderFgActivity.class);
                    mcontext.startActivity(intent1);
                }else if (info.txt.equals(mcontext.getString(R.string.virtualorders))){//虚拟订单
                    Intent intentvr = new Intent(mcontext, WebActivity.class);
                    intentvr.putExtra("url", "http://aitecc.com/wap/index.php?act=member_vr_order");
                    mcontext.startActivity(intentvr);
                }
            }
        });
        return convertView;
    }

    class MeMenu1Holder {
        LinearLayout ll_navigationitem;
        ImageView iv_1daizhifufcf;
        TextView tv_new, tv_navigationname;

        public MeMenu1Holder(View convertView) {
            ll_navigationitem = (LinearLayout) convertView.findViewById(R.id.ll_navigationitem);
            iv_1daizhifufcf = (ImageView) convertView.findViewById(R.id.iv_1daizhifufcf);
            tv_new = (TextView) convertView.findViewById(R.id.tv_new);
            tv_navigationname = (TextView) convertView.findViewById(R.id.tv_navigationname);
            convertView.setTag(this);
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
