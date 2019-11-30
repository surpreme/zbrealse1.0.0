package com.aite.recylibrary;

import android.content.Context;
import android.widget.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.aite.recylibrary.emnu.Constant;
import com.aite.recylibrary.emnu.MainMultipleItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MultipleQuickAdapter extends BaseMultiItemQuickAdapter<MainMultipleItem, BaseViewHolder> {
    private Context context;

    public MultipleQuickAdapter(Context context, List data) {
        super(data);
        this.context = context;
        addItemType(MainMultipleItem.DEVICE, R.layout.main_device);
        addItemType(MainMultipleItem.SOUND, R.layout.main_sound);
        addItemType(MainMultipleItem.TITLE, R.layout.main_title);
        addItemType(MainMultipleItem.BOTTOM_LL, R.layout.main_bottom_button);
        addItemType(MainMultipleItem.HELPOLDERSERVICE, R.layout.recy_layout);


    }


    @Override
    protected void convert(BaseViewHolder helper, MainMultipleItem item) {
        switch (helper.getItemViewType()) {
            case MainMultipleItem.DEVICE:
                helper.setText(R.id.device_name_tv, item.getTitlebar());
                break;
            case MainMultipleItem.SOUND:
                helper.setText(R.id.sound_tv, item.getSoundstring());
                break;
            case MainMultipleItem.HELPOLDERSERVICE:
                RecyclerView recyclerView = helper.getView(R.id.recycler_view);
                recyclerView.setAdapter(new HelpElderRecyAdapter(context, Constant.settingTv));
                break;
            default:
                break;
        }
    }
}

