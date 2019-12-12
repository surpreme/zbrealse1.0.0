package com.lzy.basemodule.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.R;


public class TextViewBaseRecyAdapter extends RecyclerView.Adapter<TextViewBaseRecyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private String[] txts;

    public TextViewBaseRecyAdapter(Context context, String[] txts) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.txts = txts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.text, parent, false);
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


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(txts[position]);
        holder.itemView.setAlpha(0.8f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.getPostion(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return txts == null ? 0 : txts.length;
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);


        }
    }
}
