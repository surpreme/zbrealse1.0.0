package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.HomeTabActivity;
import com.aite.a.activity.DistributionCenterActivity;
import com.aite.a.activity.FavoriteListFargmentActivity;
import com.aite.a.activity.HotVouchersListActivity;
import com.aite.a.activity.InformationActivity;
import com.aite.a.activity.IntegralInfoActivity;
import com.aite.a.activity.IntegralShopActivity;
import com.aite.a.activity.MyfootprintActivity;
import com.aite.a.base.Mark.State;
import com.aiteshangcheng.a.R;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 仿京东导航适配
 *
 * @author Administrator
 */
public class JD_NavigationAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mactivity;
    private int imglist[] = new int[]{R.drawable.ico1, R.drawable.ico2,
            R.drawable.ico3, R.drawable.ico4, R.drawable.ico5, R.drawable.ico6,
            R.drawable.ico7, R.drawable.ico8, R.drawable.ico9, R.drawable.ico10};
    private String txtlist[];

    public JD_NavigationAdapter(Context mactivity, String txt[]) {
        this.mactivity = mactivity;
        this.txtlist = txt;
    }

    @Override
    public int getItemCount() {
        return txtlist.length;
    }

    @Override
    public void onBindViewHolder(ViewHolder arg0, final int arg1) {
        arg0.iv_img.setImageResource(imglist[arg1]);
        arg0.tv_name.setText(txtlist[arg1]);
        arg0.ll_item.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (txtlist[arg1]) {
                    case "商城":

                        break;
                    case "社区":
                        Intent intent1 = new Intent(mactivity,
                                InformationActivity.class);
                        intent1.putExtra("person_in", "2");
                        mactivity.startActivity(intent1);
                        break;
                    case "足迹":
                        if (State.UserKey != null) {
                            Intent zuji = new Intent(mactivity,
                                    MyfootprintActivity.class);
                            zuji.putExtra("person_in", "2");
                            mactivity.startActivity(zuji);
                        } else {
                            Toast.makeText(
                                    mactivity,
                                    mactivity
                                            .getString(R.string.not_login_please_login),
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "发现":
                        Intent intent2 = new Intent(mactivity,
                                InformationActivity.class);
                        intent2.putExtra("person_in", "1");
                        mactivity.startActivity(intent2);
                        break;
                    case "热点":
                        Intent intent3 = new Intent(mactivity,
                                InformationActivity.class);
                        intent3.putExtra("person_in", "0");
                        mactivity.startActivity(intent3);
                        break;
                    case "积分":
//					Intent intent4 = new Intent(mactivity,
//							IntegralShopActivity.class);
//					intent4.putExtra("person_in", "1");
//					mactivity.startActivity(intent4);
                        Intent intent4 = new Intent(mactivity,
                                IntegralInfoActivity.class);
                        intent4.putExtra("person_in", "1");
                        mactivity.startActivity(intent4);
                        break;
                    case "消息":
//                        HomeTabActivity.messageBrn.performClick();
                        break;
                    case "领券":
                        // Intent intent6 = new Intent(mactivity,
                        // IntegralShopActivity.class);
                        // mactivity.startActivity(intent6);
                        Intent intent6 = new Intent(mactivity,
                                HotVouchersListActivity.class);
                        mactivity.startActivity(intent6);
//					Uri uri = Uri
//							.parse("http://aitecc.com/wap/index.php?act=pointshop");
//					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//					mactivity.startActivity(intent);
                        break;
                    case "收藏":
                        Intent intent7 = new Intent(mactivity,
                                FavoriteListFargmentActivity.class);
                        intent7.putExtra("i", 2);
                        mactivity.startActivity(intent7);
                        break;
                    case "分类":
                        HomeTabActivity.categoryBtn.performClick();
                        break;
                    case "兑换":
                        Intent intent9 = new Intent(mactivity,
                                IntegralShopActivity.class);
                        intent9.putExtra("person_in", "1");
                        mactivity.startActivity(intent9);
//					Intent intent9 = new Intent(mactivity,
//							HotelHomeActivity.class);
//					mactivity.startActivity(intent9);
                        break;
                    case "分销":
                        if (State.UserKey == null) {
                            Toast.makeText(
                                    mactivity,
                                    mactivity
                                            .getString(R.string.not_login_please_login),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent16 = new Intent(mactivity,
                                    DistributionCenterActivity.class);
                            mactivity.startActivity(intent16);
                        }
                        break;

                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mactivity)
                .inflate(R.layout.item_jdnavigation, parent, false));
        return holder;
    }

}

class ViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_img;
    TextView tv_name;
    LinearLayout ll_item;

    public ViewHolder(View itemView) {
        super(itemView);
        iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
    }

    /*
     * 分类 JDClassifyActivity 个人中心 JDPersonalCenterActivity 购物车
     * JDShoppingCartActivity 商品列表 JDGoodsListActivity
     */

}
