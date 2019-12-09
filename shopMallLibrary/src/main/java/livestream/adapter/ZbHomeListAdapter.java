package livestream.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

import livestream.activity.AudienceActivity;
import livestream.mode.LiveBroadcastListInfo;

/**
 * 直播首页适配
 * Created by Administrator on 2017/9/12.
 */
public class ZbHomeListAdapter extends RecyclerView.Adapter<ZbHomeHolder> {
    private int width = 0;
    private Context mactivity;
    private List<LiveBroadcastListInfo.datas.list> data;

    public ZbHomeListAdapter(Context mactivity, List<LiveBroadcastListInfo.datas.list> data) {
        this.mactivity = mactivity;
        this.data = data;
        WindowManager wm = (WindowManager) mactivity
                .getSystemService(Context.WINDOW_SERVICE);
        this.width = wm.getDefaultDisplay().getWidth();
    }

    /**
     * 刷新
     *
     * @param data
     */
    public void refreshData(List<LiveBroadcastListInfo.datas.list> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 添加
     *
     * @param data
     */
    public void addData(List<LiveBroadcastListInfo.datas.list> data) {
        if (data==null)return;
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ZbHomeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ZbHomeHolder holder = new ZbHomeHolder(LayoutInflater.from(mactivity)
                .inflate(R.layout.item_livestream, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ZbHomeHolder zbHomeHolder, int i) {
        final LiveBroadcastListInfo.datas.list list = data.get(i);
        zbHomeHolder.tv_number.setText(list.look_num);
        zbHomeHolder.tv_name.setText(list.member_name);
        zbHomeHolder.tv_state.setText(list.status_str);
        zbHomeHolder.iv_lable.setText(list.room_class);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) zbHomeHolder.rl_zbitem.getLayoutParams();
        layoutParams.height = width / 2;
        zbHomeHolder.rl_zbitem.setLayoutParams(layoutParams);
        Glide.with(mactivity).load(list.room_cover).into(zbHomeHolder.iv_bg);
        zbHomeHolder.iv_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mactivity, AudienceActivity.class);
                intent.putExtra("flvUrl", list.play_url);
                intent.putExtra("room_id", list.room_id);
                mactivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

class ZbHomeHolder extends RecyclerView.ViewHolder {
    ImageView iv_bg;
    TextView iv_lable, tv_name, tv_number, tv_state;
    RelativeLayout rl_zbitem;

    public ZbHomeHolder(View itemView) {
        super(itemView);
        iv_bg = (ImageView) itemView.findViewById(R.id.iv_bg);
        iv_lable = (TextView) itemView.findViewById(R.id.iv_lable);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_number = (TextView) itemView.findViewById(R.id.tv_number);
        tv_state = (TextView) itemView.findViewById(R.id.tv_state);
        rl_zbitem = (RelativeLayout) itemView.findViewById(R.id.rl_zbitem);
    }
}
