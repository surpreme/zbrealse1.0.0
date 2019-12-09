package courier.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.RecordInfo;

/**
 * 快递公司
 * Created by Administrator on 2018/1/22.
 */
public class CourierAdapter extends BaseAdapter {
    private Context mcontext;
    private List<RecordInfo> recordInfo;

    public CourierAdapter(Context mcontext, List<RecordInfo> recordInfo) {
        this.mcontext = mcontext;
        this.recordInfo = recordInfo;
    }

    /**
     * 获取选中快递公司ID
     * @return
     */
    public String getChoose() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < recordInfo.size(); i++) {
            if (recordInfo.get(i).ischoose){
                stringBuffer.append(recordInfo.get(i).id+",");
            }
        }
        String str = stringBuffer.toString();
        if (str.length()>1){
            str=str.substring(0,str.length()-1);
            return str;
        }
        return null;
    }

    @Override
    public int getCount() {
        return recordInfo == null ? 0 : recordInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return recordInfo == null ? null : recordInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_courier, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final RecordInfo Info = this.recordInfo.get(position);
        holder.tv_name.setText(Info.e_name);
        holder.iv_choose.setVisibility(Info.ischoose ? View.VISIBLE : View.GONE);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Info.ischoose){
                    Info.ischoose=false;
                }else{
                    Info.ischoose=true;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
        ImageView iv_choose;
        RelativeLayout rl_item;

        public ViewHolder(View convertView) {
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}
