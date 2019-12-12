package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.aite.mainlibrary.base.BaseRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建时间 2019/12/11 20:23
 * 描述:
 */
public class HospitalListsAdapter extends BaseRecyclerViewAdapter<String> {

    private Context mContext;

    public HospitalListsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
