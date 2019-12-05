package com.aite.recylibrary;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.lzy.basemodule.adpter.BaseRecyclerAdapter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-01
 * @desc:
 */
public class TestRecyAdapter extends BaseRecyclerAdapter {
    public TestRecyAdapter(Context mContext, CompositeDisposable disposable) {
        super(mContext, disposable);
    }

    @Override
    protected int getItemView() {
        return R.layout.album_list_item;
    }

    @Override
    protected int getHeadView() {
        return super.getHeadView();
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view) {
        return null;
    }
}
