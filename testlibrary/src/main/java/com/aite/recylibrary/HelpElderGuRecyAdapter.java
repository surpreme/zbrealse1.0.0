package com.aite.recylibrary;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.recylibrary.emnu.Status;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

public class HelpElderGuRecyAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    private Context context;

    public HelpElderGuRecyAdapter(Context context, @Nullable List<Status> data) {
        super(data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.text, item.getText());

    }

}
