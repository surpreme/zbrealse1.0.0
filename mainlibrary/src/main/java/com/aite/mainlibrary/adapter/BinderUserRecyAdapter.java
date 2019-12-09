package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.BinderUserListBean;
import com.aite.mainlibrary.Mainbean.MineTogetherServiceBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BinderUserRecyAdapter extends RecyclerView.Adapter<BinderUserRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<BinderUserListBean.ListBean> listBean;

    public BinderUserRecyAdapter(Context context, List<BinderUserListBean.ListBean> listBean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recy_item_binding_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private OnClickLstenerInterface.OnRecyClickInterface clickInterface;

    public OnClickLstenerInterface.OnRecyClickInterface getClickInterface() {
        return clickInterface;
    }

    public void setClickInterface(OnClickLstenerInterface.OnRecyClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    /**
     * "id": 1,
     * "title": "父亲"
     * },
     * {
     * "id": 2,
     * "title": "母亲"
     * },
     * {
     * "id": 3,
     * "title": "子女"
     * },
     * {
     * "id": 4,
     * "title": "爱人"
     * },
     * {
     * "id": 5,
     * "title": "其他"
     * }
     *
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (listBean.get(position).getRelation()) {
            case "1":
                holder.informationTv.setText(String.format("  %s %s 父亲", listBean.get(position).getRealname(), listBean.get(position).getMobile()));
                break;
            case "2":
                holder.informationTv.setText(String.format("  %s %s 母亲", listBean.get(position).getRealname(), listBean.get(position).getMobile()));
                break;
            case "3":
                holder.informationTv.setText(String.format("  %s %s 子女", listBean.get(position).getRealname(), listBean.get(position).getMobile()));
                break;
            case "4":
                holder.informationTv.setText(String.format("  %s %s 爱人", listBean.get(position).getRealname(), listBean.get(position).getMobile()));
                break;
            case "5":
                holder.informationTv.setText(String.format("  %s %s 其他", listBean.get(position).getRealname(), listBean.get(position).getMobile()));
                break;
            default:
                holder.informationTv.setText(String.format("  %s %s 未知", listBean.get(position).getRealname(), listBean.get(position).getMobile()));
                break;

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.getPostion(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBean == null ? 0 : listBean.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.information_tv)
        TextView informationTv;
        @BindView(R2.id.state_tv)
        TextView stateTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
