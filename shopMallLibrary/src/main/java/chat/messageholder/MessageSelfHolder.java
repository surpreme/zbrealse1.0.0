package chat.messageholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;

/**
 * Created by mayn on 2018/9/5.
 */

public class MessageSelfHolder extends RecyclerView.ViewHolder {
    public CircleImageView iv_icon;
    public LinearLayout ll_content;
    public RelativeLayout rl_item;
    public TextView tv_time;

    public MessageSelfHolder(View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        ll_content = itemView.findViewById(R.id.ll_content);
        rl_item = itemView.findViewById(R.id.rl_item);
        tv_time = itemView.findViewById(R.id.tv_time);
    }
}
