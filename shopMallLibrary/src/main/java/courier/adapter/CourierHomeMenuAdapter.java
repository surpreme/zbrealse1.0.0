package courier.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.HomeTabActivity;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

import courier.CourierstorageAcivity;
import courier.CustomerListActivity;
import courier.DataStatisticsActivity;
import courier.RecordActivity;
import courier.SendTypeActivity;
import courier.SmsModerActivity;
import courier.StorageLogActivity;
import courier.model.CourierHomeMenuInfo;

/**
 * 首页菜单适配
 * Created by Administrator on 2018/1/8.
 */
public class CourierHomeMenuAdapter extends BaseAdapter {
    private Context mcontext;
    public List<CourierHomeMenuInfo> courierHomeMenuInfo;
    private Handler handler;

    public CourierHomeMenuAdapter(Context mcontext,Handler handler) {
        this.mcontext = mcontext;
        this.handler=handler;
        courierHomeMenuInfo = new ArrayList<>();
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("发送状态", R.drawable.courier_home1, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("入库记录", R.drawable.courier_home2, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("未入库", R.drawable.courier_home3, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("签收", R.drawable.courier_home4, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("短信", R.drawable.courier_home5, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("商城", R.drawable.courier_home6, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("数据统计", R.drawable.courier_home7, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("记账", R.drawable.courier_home8, 0));
        courierHomeMenuInfo.add(new CourierHomeMenuInfo("客户管理", R.drawable.courier_home9, 0));
    }

    @Override
    public int getCount() {
        return courierHomeMenuInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return courierHomeMenuInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_courierhome, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final CourierHomeMenuInfo Info = this.courierHomeMenuInfo.get(position);
        holder.iv_icon.setImageResource(Info.icon);
        holder.tv_name.setText(Info.name);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Info.name) {
                    case "发送状态":
//                        Intent intent = new Intent(mcontext, CourierDistributionActivity.class);
//                        mcontext.startActivity(intent);
                        Intent intent = new Intent(mcontext, SendTypeActivity.class);
                        mcontext.startActivity(intent);
                        break;
                    case "入库记录":
                        Intent intent2 = new Intent(mcontext, StorageLogActivity.class);
                        mcontext.startActivity(intent2);
                        break;
                    case "未入库":
                        Intent intent3 = new Intent(mcontext, CourierstorageAcivity.class);
                        mcontext.startActivity(intent3);
                        break;
                    case "签收":
                        handler.sendEmptyMessage(103);
//                        Intent intent4 = new Intent(mcontext, StorageLogActivity.class);
//                        mcontext.startActivity(intent4);
                        break;
                    case "短信":
                        Intent intent5 = new Intent(mcontext, SmsModerActivity.class);
                        mcontext.startActivity(intent5);
                        break;
                    case "商城":
                        Intent intent6 = new Intent(mcontext, HomeTabActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent6);
                        break;
                    case "数据统计":
                        Intent intent7 = new Intent(mcontext, DataStatisticsActivity.class);
                        mcontext.startActivity(intent7);
                        break;
                    case "记账":
                        Intent intent8 = new Intent(mcontext, RecordActivity.class);
                        mcontext.startActivity(intent8);
                        break;
                    case "客户管理":
                        Intent intent9 = new Intent(mcontext, CustomerListActivity.class);
                        mcontext.startActivity(intent9);
                        break;
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        LinearLayout ll_item;

        public ViewHolder(View convertView) {
            iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(this);
        }
    }
}
