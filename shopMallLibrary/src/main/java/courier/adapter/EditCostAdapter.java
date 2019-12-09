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

import courier.model.EditCostInfo;

/**
 * 投递费用列表
 * Created by Administrator on 2018/1/30.
 */
public class EditCostAdapter extends BaseAdapter {
    private Context mcontext;
    private List<EditCostInfo.datas.list> list;
    private Handler handler;

    public EditCostAdapter(Context mcontext, List<EditCostInfo.datas.list> list, Handler handler) {
        this.mcontext = mcontext;
        this.list = list;
        this.handler = handler;
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
            convertView = View.inflate(mcontext, R.layout.item_editcost, null);
            new EditCostHodler(convertView);
        }
        EditCostHodler holder = (EditCostHodler) convertView.getTag();
        final EditCostInfo.datas.list list = this.list.get(position);
        holder.tv_name.setText(list.express_name);
        holder.tv_price.setText(list.money+"元/票");
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改
                handler.sendMessage(handler.obtainMessage(105,list.id));
            }
        });
        return convertView;
    }

    class EditCostHodler {
        RelativeLayout rl_item;
        TextView tv_name,tv_price;

        public EditCostHodler(View convertView) {
            rl_item= (RelativeLayout) convertView.findViewById(R.id.rl_item);
            tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(this);
        }
    }
}
