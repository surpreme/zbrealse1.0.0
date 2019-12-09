package courier.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.NoticeInfo;

/**
 * 通知
 * Created by Administrator on 2018/1/11.
 */
public class NoticeAdapter extends BaseAdapter {
    private Context mcontext;
    public List<NoticeInfo.datas.list> list;

    public NoticeAdapter(Context mcontext, List<NoticeInfo.datas.list> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    /**
     * 刷新
     *
     * @param list
     */
    public void refreshData(List<NoticeInfo.datas.list> list) {
        if (list == null || list.size() == 0) return;
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 加载
     *
     * @param list
     */
    public void addData(List<NoticeInfo.datas.list> list) {
        if (list == null || list.size() == 0) return;
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = View.inflate(mcontext, R.layout.item_notice, null);
            new ViewHolter(convertView);
        }
        ViewHolter holder = (ViewHolter) convertView.getTag();
        NoticeInfo.datas.list list = this.list.get(position);
        holder.tv_title.setText(list.title);
        holder.tv_context.setText(list.content);
        holder.tv_time.setText(TimeStamp2Date(list.addtime,"yyyy-MM-dd"));
//        if (list.type!=null){
//            int i = Integer.parseInt(list.type);
//            if (i>=1){
//                //寄件类型
//            }
//        }
        return convertView;
    }

    class ViewHolter {
        TextView tv_title, tv_context, tv_time;
        ImageView iv_iocn;

        public ViewHolter(View convertView) {
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            tv_context = (TextView) convertView.findViewById(R.id.tv_context);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            iv_iocn = (ImageView) convertView.findViewById(R.id.iv_iocn);
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
