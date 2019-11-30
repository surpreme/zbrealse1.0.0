package com.aite.recylibrary;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lzy.basemodule.OnClickLstenerInterface;

public class HelpElderRecyAdapter extends RecyclerView.Adapter<HelpElderRecyAdapter.ViewHolder> implements Adapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] titles;

    public HelpElderRecyAdapter(Context context, String[] titles) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.titles = titles;
    }

    @NonNull
    @Override
    public HelpElderRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.help_elder, parent, false);
        HelpElderRecyAdapter.ViewHolder viewHolder = new HelpElderRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    public OnClickLstenerInterface.OnRecyClickInterface getLstenerInterface() {
        return lstenerInterface;
    }

    public void setLstenerInterface(OnClickLstenerInterface.OnRecyClickInterface lstenerInterface) {
        this.lstenerInterface = lstenerInterface;
    }

    private OnClickLstenerInterface.OnRecyClickInterface lstenerInterface;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(titles[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstenerInterface.getPostion(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.length;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);

        }
    }
}
