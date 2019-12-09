package courier.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.SendTypeInfo;

/**
 * 订单发送状态
 * Created by Administrator on 2018/1/11.
 */
public class SendTypeAdapter extends BaseAdapter {
    private Context mcontext;
    private List<SendTypeInfo.datas.list> list;

    public SendTypeAdapter(Context mcontext, List<SendTypeInfo.datas.list> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    /**
     * 刷新
     *
     * @param list
     */
    public void refreshData(List<SendTypeInfo.datas.list> list) {
        if (list == null) return;
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param list
     */
    public void addData(List<SendTypeInfo.datas.list> list) {
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
            convertView = View.inflate(mcontext, R.layout.item_sendtype, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        SendTypeInfo.datas.list list = this.list.get(position);
        holder.tv_time.setText("发送时间 : " + TimeStamp2Date(list.addtime, "yyyy-MM-dd HH:mm"));
        holder.tv_oridersn.setText(list.order_id);
        holder.tv_phone.setText(list.phone);
        holder.tv_speech.setText(list.content);
        holder.tv_type.setText(list.state.equals("1") ? "发送成功" : "发送失败");
        return convertView;
    }

    class ViewHolder {
        TextView tv_time, tv_oridersn, tv_type, tv_phone, tv_speech;

        public ViewHolder(View convertView) {
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_oridersn = (TextView) convertView.findViewById(R.id.tv_oridersn);
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            tv_speech = (TextView) convertView.findViewById(R.id.tv_speech);
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
