package courier.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

import courier.model.DeliveryCourierInfo;

/**
 * 快递公司适配
 * Created by Administrator on 2018/1/30.
 */
public class CompanyAdapter extends BaseAdapter {
    private Handler handler;
    private Context mcontext;
    private List<DeliveryCourierInfo> deliveryCourierInfo;

    public CompanyAdapter(Handler handler, Context mcontext, List<DeliveryCourierInfo> deliveryCourierInfo) {
        this.handler = handler;
        this.mcontext = mcontext;
        this.deliveryCourierInfo = deliveryCourierInfo;
    }

    @Override
    public int getCount() {
        return deliveryCourierInfo == null ? 0 : deliveryCourierInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return deliveryCourierInfo == null ? null : deliveryCourierInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_company, null);
            new CompanyHolder(convertView);
        }
        CompanyHolder holder = (CompanyHolder) convertView.getTag();
        final DeliveryCourierInfo info = this.deliveryCourierInfo.get(position);
        Glide.with(mcontext).load(info.img_path).into(holder.iv_icon);
        holder.tv_name.setText(info.e_name);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(104,info.id));
            }
        });
        return convertView;
    }

    class CompanyHolder {
        CircleImageView iv_icon;
        TextView tv_name;
        RelativeLayout rl_item;
        public CompanyHolder(View convertView) {
            iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}
