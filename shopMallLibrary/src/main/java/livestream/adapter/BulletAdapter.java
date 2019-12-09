package livestream.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

import livestream.mode.BulletInfo;

/**
 * 弹幕适配
 * Created by Administrator on 2017/9/13.
 */
public class BulletAdapter extends RecyclerView.Adapter<BulletHolder>{
    private Context mactivity;
    public List<BulletInfo>bulletInfo=new ArrayList<>();
    private Handler handler;

    public BulletAdapter(Context mactivity,Handler handler) {
        this.mactivity = mactivity;
        this.handler=handler;
    }

    /**
     * 添加弹幕
     * @param bulletInfo
     */
    public void additem(BulletInfo bulletInfo){
        this.bulletInfo.add(bulletInfo);
        notifyDataSetChanged();
    }

    @Override
    public BulletHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        BulletHolder holder = new BulletHolder(LayoutInflater.from(mactivity)
                .inflate(R.layout.item_zb_bullet, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BulletHolder videoHolder, int i) {
        BulletInfo bulletInfo = this.bulletInfo.get(i);
        switch (bulletInfo.type){
            case "普通":
                videoHolder.ll_level.setVisibility(View.VISIBLE);
                videoHolder.tv_name.setVisibility(View.VISIBLE);
                videoHolder.tv_txt.setTextColor(Color.WHITE);
                break;
            case "重要":
                videoHolder.ll_level.setVisibility(View.VISIBLE);
                videoHolder.tv_name.setVisibility(View.VISIBLE);
                videoHolder.tv_txt.setTextColor(Color.YELLOW);
                break;
            case "系统":
                videoHolder.ll_level.setVisibility(View.GONE);
                videoHolder.tv_name.setVisibility(View.GONE);
                videoHolder.tv_txt.setTextColor(Color.YELLOW);
                break;
        }
        videoHolder.tv_txt.setText(bulletInfo.txt);
        videoHolder.tv_level.setText(bulletInfo.level);
        videoHolder.tv_name.setText(bulletInfo.name+" : ");
        videoHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(102);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bulletInfo.size();
    }
}
class BulletHolder extends RecyclerView.ViewHolder{
    LinearLayout ll_level;
    TextView tv_level,tv_txt,tv_name;
    RelativeLayout rl_item;
    public BulletHolder(View itemView) {
        super(itemView);
        ll_level= (LinearLayout) itemView.findViewById(R.id.ll_level);
        tv_level= (TextView) itemView.findViewById(R.id.tv_level);
        tv_txt= (TextView) itemView.findViewById(R.id.tv_txt);
        tv_name= (TextView) itemView.findViewById(R.id.tv_name);
        rl_item= (RelativeLayout) itemView.findViewById(R.id.rl_item);
    }
}