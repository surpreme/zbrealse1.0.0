package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.SettingAddressListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdrressFixRecyAdapter extends RecyclerView.Adapter<AdrressFixRecyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<SettingAddressListBean.AddressListBean> addressListBeans;

    public AdrressFixRecyAdapter(Context context, List<SettingAddressListBean.AddressListBean> addressListBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.addressListBeans = addressListBeans;
    }

    public OnClickLstenerInterface.OnThingClickInterface getOnItemRecyClickInterface() {
        return onItemRecyClickInterface;
    }

    public void setOnItemRecyClickInterface(OnClickLstenerInterface.OnThingClickInterface onItemRecyClickInterface) {
        this.onItemRecyClickInterface = onItemRecyClickInterface;
    }

    private OnClickLstenerInterface.OnThingClickInterface onItemRecyClickInterface;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sosuser_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvAddress.setText(addressListBeans.get(position).getArea_info());
        holder.tvNamephone.setText(String.format("%s %s", addressListBeans.get(position).getTrue_name(), addressListBeans.get(position).getMob_phone()));
        holder.press.setOnClickListener(v -> {
            if (onItemRecyClickInterface!= null)
                onItemRecyClickInterface.getString(addressListBeans.get(position).getAddress_id());
        });
    }

    @Override
    public int getItemCount() {
        return addressListBeans == null ? 0 : addressListBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_namephone)
        TextView tvNamephone;
        @BindView(R2.id.tv_address)
        TextView tvAddress;
        @BindView(R2.id.tv_edit)
        TextView tvEdit;
        @BindView(R2.id.tv_delete)
        TextView tvDelete;
        @BindView(R2.id.swipeMenuLayout)
        SwipeMenuLayout swipeMenuLayout;
        @BindView(R2.id.press)
        LinearLayout press;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

}
