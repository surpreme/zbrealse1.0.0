package courier.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

import courier.model.CustomerListInfo;

/**
 * 客户列表
 * Created by Administrator on 2018/1/9.
 */
public class CustomerListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<CustomerListInfo> customerListInfo;
    private int type=0;
    private Handler handler;
    public CustomerListAdapter(Context mcontext, List<CustomerListInfo> customerListInfo,int type,Handler handler) {
        this.mcontext = mcontext;
        this.customerListInfo = customerListInfo;
        this.type=type;
        this.handler=handler;
    }

    /**
     * 刷新
     * @param customerListInfo
     * @param type
     */
    public void refreshData(List<CustomerListInfo> customerListInfo,int type){
        this.customerListInfo = customerListInfo;
        this.type=type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return customerListInfo == null ? 0 : customerListInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return customerListInfo == null ? null : customerListInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_khfriend, null);
            new KHA1Hodler(convertView);
        }
        KHA1Hodler holder = (KHA1Hodler) convertView.getTag();
        CustomerListInfo Info = this.customerListInfo.get(position);
        holder.tv_title.setText(Info.type_shouzimu);
        KHAdapter kHAdapter = new KHAdapter(Info.info);
        holder.rv_friend.setAdapter(kHAdapter);
        return convertView;
    }

    class KHA1Hodler {
        TextView tv_title;
        MyListView rv_friend;

        public KHA1Hodler(View convertView) {
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            rv_friend = (MyListView) convertView.findViewById(R.id.rv_friend);
            convertView.setTag(this);
        }
    }

    /**
     *客户列表
     */
    class KHAdapter extends BaseAdapter {
        List<CustomerListInfo.info> info;

        public KHAdapter(List<CustomerListInfo.info> info) {
            this.info = info;
        }

        @Override
        public int getCount() {
            return info == null ? 0 : info.size();
        }

        @Override
        public Object getItem(int position) {
            return info == null ? null : info.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mcontext, R.layout.item_customer, null);
                new KHAHodler(convertView);
            }
            KHAHodler holder = (KHAHodler) convertView.getTag();
            final CustomerListInfo.info info = this.info.get(position);
            Glide.with(mcontext).load(info.img_path).into(holder.cv_icon);
            holder.tv_name.setText(info.customer_name);
            holder.tv_phone.setText(info.customer_mobile);
            if (type==0&&info.is_vip.equals("0")){
                holder.tv_add.setVisibility(View.VISIBLE);
            }else{
                holder.tv_add.setVisibility(View.GONE);
            }
            holder.tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加VIP
                    handler.sendMessage(handler.obtainMessage(101,info.id));
                }
            });
            holder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择客户
                    handler.sendMessage(handler.obtainMessage(102,info));
                }
            });
            return convertView;
        }

        class KHAHodler {
            CircleImageView cv_icon;
            TextView tv_name, tv_phone, tv_add;
            RelativeLayout rl_item;
            public KHAHodler(View convertView) {
                cv_icon = (CircleImageView) convertView.findViewById(R.id.cv_icon);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
                tv_add = (TextView) convertView.findViewById(R.id.tv_add);
                rl_item= (RelativeLayout) convertView.findViewById(R.id.rl_item);
                convertView.setTag(this);
            }
        }
    }

}
