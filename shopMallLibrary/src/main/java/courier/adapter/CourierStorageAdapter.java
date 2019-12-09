package courier.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;

import courier.model.CourierstorageInfo;

/**
 * 编辑快递入库
 * <p>
 * Created by Administrator on 2018/1/10.
 */
public class CourierStorageAdapter extends BaseAdapter {
    private Context mcontext;
    private CourierstorageInfo courierstorageInfo;
    private Handler handler;

    public CourierStorageAdapter(Context mcontext, CourierstorageInfo courierstorageInfo,Handler handler) {
        this.mcontext = mcontext;
        this.courierstorageInfo = courierstorageInfo;
        this.handler=handler;
    }

    /**
     * 刷新
     * @param courierstorageInfo
     */
    public void refreshData(CourierstorageInfo courierstorageInfo){
        if (courierstorageInfo==null||courierstorageInfo.datas==null||courierstorageInfo.datas.list==null)return;
        this.courierstorageInfo = courierstorageInfo;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param courierstorageInfo
     */
    public void addData(CourierstorageInfo courierstorageInfo){
        if (courierstorageInfo==null||courierstorageInfo.datas==null||courierstorageInfo.datas.list==null)return;
        this.courierstorageInfo.datas.list.addAll(courierstorageInfo.datas.list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return courierstorageInfo.datas.list.size();
    }

    @Override
    public Object getItem(int position) {
        return courierstorageInfo.datas.list == null ? null : courierstorageInfo.datas.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_ourierstoragec, null);
            new ViewHodler(convertView);
        }
        ViewHodler holder = (ViewHodler) convertView.getTag();
        final CourierstorageInfo.datas.list list = courierstorageInfo.datas.list.get(position);
        holder.tv_time.setText("创建时间 : "+TimeStamp2Date(list.addtime,"yyyy-MM-dd HH:mm"));
        holder.tv_name.setText(list.express_name);
        holder.tv_phone.setText(list.reciver_mobphone);
        holder.tv_type.setText(list.dlyo_state.equals("10")?"未到站":
                list.dlyo_state.equals("20")?"已到站":list.dlyo_state.equals("30")?"已提取":"");
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入库
                handler.sendMessage(handler.obtainMessage(107,list.order_id));
            }
        });
        holder.iv_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除

            }
        });
        return convertView;
    }

    class ViewHodler {
        TextView tv_time, tv_name, tv_phone, tv_type, tv_add;
        CircleImageView cv_icon;
        ImageView iv_cha;

        public ViewHodler(View convertView) {
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            cv_icon = (CircleImageView) convertView.findViewById(R.id.cv_icon);
            iv_cha= (ImageView) convertView.findViewById(R.id.iv_cha);
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
