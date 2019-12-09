package courier.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.model.IntegralMallInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 代金券
 * Created by Administrator on 2018/2/1.
 */
public class IntegralMallAdapter extends BaseAdapter {
    private Context mcontext;
    private List<IntegralMallInfo.recommend_voucher> recommend_voucher;
    private Handler handler;

    public IntegralMallAdapter(Context mcontext, List<IntegralMallInfo.recommend_voucher> recommend_voucher, Handler handler) {
        this.mcontext = mcontext;
        this.recommend_voucher = recommend_voucher;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return recommend_voucher == null ? 0 : recommend_voucher.size();
    }

    @Override
    public Object getItem(int position) {
        return recommend_voucher == null ? null : recommend_voucher.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_yzintegral, null);
            new IntegralMallHolder(convertView);
        }
        IntegralMallHolder holder = (IntegralMallHolder) convertView.getTag();
        final IntegralMallInfo.recommend_voucher recommend_voucher2 = recommend_voucher
                .get(position);
        holder.tv_shop_name.setText(recommend_voucher2.voucher_t_storename);
        holder.tv_cost.setText("￥" + recommend_voucher2.voucher_t_price);
        holder.tv_statement.setText(recommend_voucher2.voucher_t_desc);
        holder.tv_valid_time.setText("有效期至"
                + TimeStamp2Date(recommend_voucher2.voucher_t_end_date,
                "yyyy-MM-dd HH:mm"));
        holder.tv_need.setText(recommend_voucher2.voucher_t_points
                + "积分");
        holder.bt_exchange.setText("兑换");
        if ((position&1) != 1){
            holder.ll_con.setBackgroundResource(R.drawable.yz_integra1);
        }else{
            holder.ll_con.setBackgroundResource(R.drawable.yz_integra2);
        }
        holder.bt_exchange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 兑换
                handler.sendMessage(handler.obtainMessage(111,recommend_voucher2.voucher_t_id));
            }
        });
        return convertView;
    }

    class IntegralMallHolder {
        TextView tv_shop_name, tv_cost, tv_statement, tv_valid_time,
                tv_need;
        Button bt_exchange;
        LinearLayout ll_con;
        public IntegralMallHolder(View convertView) {
            tv_shop_name = (TextView) convertView
                    .findViewById(R.id.tv_shop_name);
            tv_cost = (TextView) convertView.findViewById(R.id.tv_cost);
            tv_statement = (TextView) convertView
                    .findViewById(R.id.tv_statement);
            tv_valid_time = (TextView) convertView
                    .findViewById(R.id.tv_valid_time);
            tv_need = (TextView) convertView.findViewById(R.id.tv_need);
            bt_exchange = (Button) convertView
                    .findViewById(R.id.bt_exchange);
            ll_con= (LinearLayout) convertView.findViewById(R.id.ll_con);
            convertView.setTag(this);
        }
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString
     *            时间戳
     * @param formats
     *            格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }
}
