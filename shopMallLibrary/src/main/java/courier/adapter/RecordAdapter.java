package courier.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.DeliveryTallyInfo;

/**
 * 记账列表
 * Created by Administrator on 2018/1/22.
 */
public class RecordAdapter extends BaseAdapter {
    private Context mcontext;
    public List<DeliveryTallyInfo.datas.list> list;
    private Handler handler;

    public RecordAdapter(Context mcontext, List<DeliveryTallyInfo.datas.list> list, Handler handler) {
        this.mcontext = mcontext;
        this.list = list;
        this.handler = handler;
    }

    /**
     * 刷新数据
     *
     * @param list
     */
    public void refreshData(List<DeliveryTallyInfo.datas.list> list) {
        if (list == null) return;
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param list
     */
    public void addData(List<DeliveryTallyInfo.datas.list> list) {
        if (list == null) return;
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 修改数量
     *
     * @param id   订单id
     * @param num  数量
     * @param type 修改类型
     */
    public void setNum(String id, String num, String type) {
        for (int i = 0; i < list.size(); i++) {
            if (id.equals(list.get(i).express_id)) {
                if (type.equals("1")) {
                    list.get(i).delivery_num = num;
                } else {
                    list.get(i).ship_num = num;
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_record, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final DeliveryTallyInfo.datas.list list = this.list.get(position);
        holder.tv_name.setText(list.express_name);
        holder.tv_num1.setText(list.delivery_num==null?"0":list.delivery_num);
        holder.tv_num2.setText(list.ship_num==null?"0":list.ship_num);
        holder.tv_bj1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑寄件
                handler.sendMessage(handler.obtainMessage(101, list.express_id));
            }
        });
        holder.tv_bj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑派件
                handler.sendMessage(handler.obtainMessage(102, list.express_id));
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name, tv_num1, tv_num2, tv_bj1, tv_bj2;

        public ViewHolder(View convertView) {
            iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_num1 = (TextView) convertView.findViewById(R.id.tv_num1);
            tv_num2 = (TextView) convertView.findViewById(R.id.tv_num2);
            tv_bj1 = (TextView) convertView.findViewById(R.id.tv_bj1);
            tv_bj2 = (TextView) convertView.findViewById(R.id.tv_bj2);
            convertView.setTag(this);
        }
    }
}
