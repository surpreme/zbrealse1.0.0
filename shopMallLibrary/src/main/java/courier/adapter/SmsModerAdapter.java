package courier.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.SmsModerInfo;

/**
 * 短信模板适配
 *
 * Created by Administrator on 2018/1/9.
 */
public class SmsModerAdapter extends BaseAdapter{
    private Context mcontext;
    private List<SmsModerInfo> smsModerInfo;
    private Handler handler;
    public SmsModerAdapter(Context mcontext,List<SmsModerInfo> smsModerInfo,Handler handler) {
        this.mcontext = mcontext;
        this.smsModerInfo=smsModerInfo;
        this.handler=handler;
    }

    @Override
    public int getCount() {
        return smsModerInfo==null?0:smsModerInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return smsModerInfo==null?null:smsModerInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(mcontext, R.layout.item_smsmoder,null);
            new ViewHodler(convertView);
        }
        ViewHodler holder = (ViewHodler) convertView.getTag();
        final SmsModerInfo smsModerInfo = this.smsModerInfo.get(position);
        holder.tv_name.setText(smsModerInfo.mmt_name);
        holder.tv_context.setText(smsModerInfo.mmt_short_content);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//选择短信
                handler.sendMessage(handler.obtainMessage(106,smsModerInfo));
            }
        });
        return convertView;
    }
    class ViewHodler{
        TextView tv_name,tv_context;
        RelativeLayout rl_item;
        public ViewHodler(View convertView) {
            tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            tv_context= (TextView) convertView.findViewById(R.id.tv_context);
            rl_item= (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}
