package courier.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.AllIncomeInfo;

/**
 * 商城收入明细
 * Created by Administrator on 2018/1/25.
 */
public class AllIncomeAdapter extends BaseAdapter {
    private Context mcontext;
    private List<AllIncomeInfo.list.order_list> order_list;

    public AllIncomeAdapter(Context mcontext, List<AllIncomeInfo.list.order_list> order_list) {
        this.mcontext = mcontext;
        this.order_list = order_list;
    }

    /**
     * 刷新数据
     * @param order_list
     */
    public void refreshData(List<AllIncomeInfo.list.order_list> order_list){
        if (order_list==null)return;
        this.order_list=order_list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return order_list == null ? 0 : order_list.size();
    }

    @Override
    public Object getItem(int position) {
        return order_list == null ? null : order_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_incomemall, null);
            new ViewHodler(convertView);
        }
        ViewHodler holder = (ViewHodler) convertView.getTag();
        AllIncomeInfo.list.order_list order_list = this.order_list.get(position);
        holder.tv_ordersn.setText("订单编号 : "+order_list.order_sn);
        holder.tv_time.setText(TimeStamp2Date(order_list.add_time,"yyyy-MM-dd"));
        holder.tv_form.setText("来源 : "+order_list.store_name);
        holder.tv_money.setText("¥"+order_list.order_amount);
        return convertView;
    }

    class ViewHodler {
        TextView tv_ordersn, tv_time, tv_form, tv_money;
        RelativeLayout rl_item;
        public ViewHodler(View convertView) {
            tv_ordersn = (TextView) convertView.findViewById(R.id.tv_ordersn);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_form = (TextView) convertView.findViewById(R.id.tv_form);
            tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            rl_item= (RelativeLayout) convertView.findViewById(R.id.rl_item);
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
