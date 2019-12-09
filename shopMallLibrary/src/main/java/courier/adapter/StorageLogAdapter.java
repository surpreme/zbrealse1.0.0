package courier.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.StorageLogInfo;

/**
 * 入库列表
 * <p>
 * Created by Administrator on 2018/1/9.
 */
public class StorageLogAdapter extends BaseAdapter {
    private Context mcontext;
    private List<StorageLogInfo.datas.list> list;
    private Handler handler;

    public StorageLogAdapter(Context mcontext, List<StorageLogInfo.datas.list> list, Handler handler) {
        this.mcontext = mcontext;
        this.list = list;
        this.handler = handler;
    }

    /**
     * 刷新
     *
     * @param list
     */
    public void refreshData(List<StorageLogInfo.datas.list> list) {
        if (list == null) return;
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 添加
     *
     * @param list
     */
    public void addData(List<StorageLogInfo.datas.list> list) {
        if (list == null) return;
        this.list.addAll(list);
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
            convertView = View.inflate(mcontext, R.layout.item_storagelog, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final StorageLogInfo.datas.list list = this.list.get(position);
        holder.tv_time.setText("出站时间 : " + TimeStamp2Date(list.addtime, "yyyy-MM-dd HH:mm"));
        holder.tv_num.setText("入库编号 " + list.express_no);
        holder.tv_num2.setText(list.express_name + " " + list.express_code);
        holder.tv_phone.setText(list.consignee_mobile);
        holder.tv_name.setText(list.consignee);
        holder.tv_phone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        if (list.status.equals("1")) {//到站
            holder.tv_type2.setVisibility(View.GONE);
            holder.tv_vrcode.setVisibility(View.GONE);
            if (list.is_sms.equals("2")) {
                holder.tv_type.setVisibility(View.VISIBLE);
                holder.tv_type.setText("发送短信");
            } else {
                holder.tv_type.setVisibility(View.GONE);
            }

        } else if (list.status.equals("2")) {//滞留
            holder.tv_type.setVisibility(View.GONE);
            holder.tv_type2.setVisibility(View.GONE);
            holder.tv_vrcode.setVisibility(View.GONE);
        } else if (list.status.equals("3")) {//出站
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_vrcode.setVisibility(View.VISIBLE);
            if (list.is_send.equals("1")) {//送货上门
                holder.tv_type2.setVisibility(View.GONE);
            } else if (list.is_send.equals("0")) {
                holder.tv_type2.setVisibility(View.VISIBLE);
                holder.tv_type2.setText("自提件");
            }
        } else if (list.status.equals("4")) {//退回
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_type2.setVisibility(View.GONE);
            holder.tv_vrcode.setVisibility(View.GONE);
            holder.tv_type.setText("已退回");
        }
        holder.tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.status.equals("1") && list.is_sms.equals("2"))
                    //发送短信
                    handler.sendMessage(handler.obtainMessage(102, list));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_time, tv_identity, tv_num, tv_type, tv_num2, tv_type2, tv_phone, tv_name, tv_vrcode;

        public ViewHolder(View convertView) {
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_identity = (TextView) convertView.findViewById(R.id.tv_identity);
            tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            tv_num2 = (TextView) convertView.findViewById(R.id.tv_num2);
            tv_type2 = (TextView) convertView.findViewById(R.id.tv_type2);
            tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_vrcode = (TextView) convertView.findViewById(R.id.tv_vrcode);
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
