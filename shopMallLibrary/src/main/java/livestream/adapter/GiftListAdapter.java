package livestream.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

import livestream.mode.GiftListInfo;

/**
 * 礼物列表
 * Created by mayn on 2019/1/3.
 */
public class GiftListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GiftListInfo.datas.gift_list> data;

    public GiftListAdapter(Context mcontext, List<GiftListInfo.datas.gift_list> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    /**
     * 获取选中ID
     * @return
     */
    public String getPickID() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).ischoose) {
               return data.get(i).id;
            }
        }
        return null;
    }
    /**
     * 获取选中礼品
     * @return
     */
    public GiftListInfo.datas.gift_list getPickGift() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).ischoose) {
               return data.get(i);
            }
        }
        return null;
    }

    /**
     * 选中
     *
     * @param id
     */
    private void setChoose(int id) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).ischoose = false;
        }
        data.get(id).ischoose = true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_gift, null);
            new GiftListHolder(view);
        }
        GiftListHolder holder = (GiftListHolder) view.getTag();
        GiftListInfo.datas.gift_list gift_list = data.get(i);
        Glide.with(mcontext).load(gift_list.gift_image).into(holder.iv_img);
        holder.tv_name.setText(gift_list.gift_name);
        holder.tv_money1.setText("￥" + gift_list.need_money);
        holder.tv_money2.setText(gift_list.need_points + "分");
        if (gift_list.ischoose) {
            holder.ll_item.setBackgroundResource(R.drawable.dialog_bg5);
        } else {
            holder.ll_item.setBackgroundResource(R.drawable.dialog_bg6);
        }
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoose(i);
            }
        });
        return view;
    }

    class GiftListHolder {
        ImageView iv_img;
        TextView tv_name, tv_money1, tv_money2;
        LinearLayout ll_item;

        public GiftListHolder(View view) {
            iv_img = view.findViewById(R.id.iv_img);
            tv_name = view.findViewById(R.id.tv_name);
            tv_money1 = view.findViewById(R.id.tv_money1);
            tv_money2 = view.findViewById(R.id.tv_money2);
            ll_item = view.findViewById(R.id.ll_item);
            view.setTag(this);
        }
    }
}
