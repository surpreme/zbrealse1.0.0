package livestream.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

/**
 * 视频适配
 * Created by Administrator on 2017/9/13.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoHolder>{
    private Context mactivity;

    public VideoAdapter(Context mactivity) {
        this.mactivity = mactivity;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        VideoHolder holder = new VideoHolder(LayoutInflater.from(mactivity)
                .inflate(R.layout.item_livestream, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoHolder videoHolder, int i) {
        videoHolder.iv_lable.setVisibility(View.GONE);
        videoHolder.tv_state.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
class VideoHolder extends RecyclerView.ViewHolder{
    ImageView iv_bg;
    TextView iv_lable, tv_name, tv_number, tv_state;
    RelativeLayout rl_zbitem;
    public VideoHolder(View itemView) {
        super(itemView);
        iv_bg = (ImageView) itemView.findViewById(R.id.iv_bg);
        iv_lable = (TextView) itemView.findViewById(R.id.iv_lable);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_number = (TextView) itemView.findViewById(R.id.tv_number);
        tv_state = (TextView) itemView.findViewById(R.id.tv_state);
        rl_zbitem = (RelativeLayout) itemView.findViewById(R.id.rl_zbitem);
    }
}