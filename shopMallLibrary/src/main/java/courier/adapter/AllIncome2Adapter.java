package courier.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.AllIncomeInfo;

/**
 * 派件收入明细
 * Created by Administrator on 2018/1/25.
 */
public class AllIncome2Adapter extends BaseAdapter {
    private Context mcontext;
    private List<AllIncomeInfo.list.express_list> express_list;

    public AllIncome2Adapter(Context mcontext, List<AllIncomeInfo.list.express_list> express_list) {
        this.mcontext = mcontext;
        this.express_list = express_list;
    }

    /**
     * 刷新数据
     *
     * @param express_list
     */
    public void refreshData(List<AllIncomeInfo.list.express_list> express_list) {
        if (express_list == null) return;
        this.express_list = express_list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return express_list == null ? 0 : express_list.size();
    }

    @Override
    public Object getItem(int position) {
        return express_list == null ? null : express_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_incomesend, null);
            new ViewHodler(convertView);
        }
        ViewHodler holder = (ViewHodler) convertView.getTag();
        AllIncomeInfo.list.express_list express = this.express_list.get(position);
        holder.tv_type.setText("公司 : "+express.express_name);
        holder.tv_ordersn.setText("单号 : "+express.express_code);
        holder.tv_time.setText(TimeStamp2Date(express.addtime,"yyyy-MM-dd"));
        holder.tv_store.setText("站点 : "+express.dlyp_name);
        holder.tv_money.setText("¥"+express.money);
        return convertView;
    }

    class ViewHodler {
        TextView tv_type, tv_ordersn, tv_time, tv_store, tv_money;

        public ViewHodler(View convertView) {
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            tv_ordersn = (TextView) convertView.findViewById(R.id.tv_ordersn);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_store = (TextView) convertView.findViewById(R.id.tv_store);
            tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(this);
        }
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString 时间戳
     * @param formats         格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }
}
