package com.aite.recylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.recylibrary.emnu.Constant;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private Map<Integer, List<Object>> mDatalist;

    public TestAdapter(Context context, Map<Integer, List<Object>> mDatalist) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mDatalist = mDatalist;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new SoundViewHolder(inflater.inflate(R.layout.main_sound, parent, false));
        if (viewType == 1)
            return new TitleViewHolder(inflater.inflate(R.layout.main_title, parent, false));
        if (viewType == 2)
            return new DeviceViewHolder(inflater.inflate(R.layout.main_device, parent, false));
        else if (viewType == 3)
            return new TitleViewHolder(inflater.inflate(R.layout.main_title, parent, false));
        else if (viewType == 4)
            return new RecyViewHolder(inflater.inflate(R.layout.recy_layout, parent, false));
        else if (viewType == 5)
            return new TitleViewHolder(inflater.inflate(R.layout.main_title, parent, false));
        else if (viewType == 6)
            return new RecyViewHolder(inflater.inflate(R.layout.recy_layout, parent, false));
        else if (viewType == 7)
            return new BotttmllViewHolder(inflater.inflate(R.layout.main_bottom_button, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof SoundViewHolder) {
//            ((SoundViewHolder) holder).title_tv.setText((mDatalist.get((Integer) position)).get(0));
//        } else if (holder instanceof TitleViewHolder) {
//            ((TitleViewHolder) holder).title_bar.setText(((List<String>) mDatalist.get(position)).get(0));
//        }

    }

    @Override
    public int getItemViewType(int position) {
        for (Integer key : mDatalist.keySet()) {
            return (int)key;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mDatalist == null ? 0 : mDatalist.size();
    }

    class BotttmllViewHolder extends RecyclerView.ViewHolder {
        BotttmllViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView title_bar;

        TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            title_bar = itemView.findViewById(R.id.title_bar);

        }
    }

    class RecyViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;

        RecyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);

        }
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_bankname, tv_banknumber;
        private SwipeMenuLayout swipeMenuLayout;


        DeviceViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }

    class SoundViewHolder extends RecyclerView.ViewHolder {
        private TextView title_tv;


        SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.sound_tv);


        }
    }
}
