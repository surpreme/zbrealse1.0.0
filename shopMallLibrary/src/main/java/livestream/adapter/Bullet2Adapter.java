package livestream.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.aite.a.base.Mark.MESSAGE_TOBOTTOM;

/**
 * 弹幕
 * Created by mayn on 2018/12/28.
 */
public class Bullet2Adapter extends RecyclerView.Adapter<Bullet2Holder> {
    private Context mcontext;
    private List<TIMMessage> msgs;
    private Handler handler;

    public Bullet2Adapter(Context mcontext, List<TIMMessage> msgs, Handler handler) {
        this.mcontext = mcontext;
        this.msgs = msgs;
        this.handler = handler;
    }

    /**
     * 添加单个消息
     *
     * @param msgs
     */
    public void addMsg(TIMMessage msgs) {
        this.msgs.add(msgs);
        notifyItemChanged(getItemCount());
        handler.sendEmptyMessage(MESSAGE_TOBOTTOM);
    }

    /**
     * 添加多条消息
     *
     * @param msgs
     */
    public void addAllMsg(List<TIMMessage> msgs) {
        this.msgs.addAll(msgs);
        notifyItemRangeInserted(getItemCount(), msgs.size());
        handler.sendEmptyMessage(MESSAGE_TOBOTTOM);
    }

    @Override
    public Bullet2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Bullet2Holder holder = new Bullet2Holder(LayoutInflater.from(mcontext)
                .inflate(R.layout.item_zb_bullet2, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Bullet2Holder holder, int position) {
        TIMMessage timMessage = msgs.get(position);

        TIMElem elem = timMessage.getElement(0);
        //获取当前元素的类型
        TIMElemType elemType = elem.getType();
        Log.i("---------------","类型  "+elemType.name());
        if (elemType == TIMElemType.Text) {
            TIMTextElem e = (TIMTextElem) elem;
            String text = e.getText();
            holder.tv_bullet.setTextColor(Color.WHITE);
            holder.tv_bullet.setText(timMessage.getSender() + "：" + text);
        }else if (elemType == TIMElemType.Custom){
            TIMCustomElem e = (TIMCustomElem) elem;
            holder.tv_bullet.setTextColor(Color.YELLOW);
            try {
                String data = new String(e.getData(), "UTF-8");
                String desc = e.getDesc();
                if (desc.equals("Join")){
                    holder.tv_bullet.setText(data);
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return msgs == null ? 0 : msgs.size();
    }
}

class Bullet2Holder extends RecyclerView.ViewHolder {
    TextView tv_bullet;

    public Bullet2Holder(View itemView) {
        super(itemView);
        tv_bullet = itemView.findViewById(R.id.tv_bullet);
    }
}