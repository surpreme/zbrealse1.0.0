package chat.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.utils.ClutterUtils;
import com.tencent.TIMMessage;

import java.util.List;

import chat.messageholder.MessageOtherHolder;
import chat.messageholder.MessageSelfHolder;
import chat.utils.TXImUtils;

import static com.aite.a.base.Mark.MESSAGE_TOBOTTOM;

/**
 * 消息适配
 * Created by mayn on 2018/9/5.
 */
public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<TIMMessage> msgs;
    public String headurlself, headurother, formuser;//头像/消息来源
    public Handler handler;
    private int msgmaxw = 0;//消息最大宽度
    private MediaPlayer plater;

    public ChatListAdapter(Context mcontext, List<TIMMessage> msgs, String headurlself, String headurother, String formuser, Handler handler, MediaPlayer plater) {
        this.mcontext = mcontext;
        this.msgs = msgs;
        this.headurlself = headurlself;
        this.headurother = headurother;
        this.formuser = formuser;
        this.handler = handler;
        this.plater = plater;
        msgmaxw = ClutterUtils.dp2px(mcontext, 220);
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

    /**
     * 添加历史消息
     *
     * @param msgs
     */
    public void AddMessageHistory(List<TIMMessage> msgs) {
        this.msgs.addAll(0, msgs);
        notifyItemRangeInserted(0, msgs.size());
    }

    /**
     * 获取第一条消息
     *
     * @return
     */
    public TIMMessage getFirstMessage() {
        if (msgs == null || msgs.size() == 0) {
            return null;
        } else {
            return msgs.get(0);
        }
    }

    /**
     * 获取消息数据
     *
     * @return
     */
    public List<TIMMessage> getMsg() {
        return msgs;
    }

    @Override
    public int getItemViewType(int position) {
        return msgs.get(position).isSelf() ? 1 : 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_self, parent, false);
                return new MessageSelfHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_other, parent, false);
                return new MessageOtherHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TIMMessage timMessage = msgs.get(position);
        String time = TXImUtils.MsgTime(position, mcontext);
//        String time = ClutterUtils.TimeStamp2Date(timestamp + "", "yyyy-MM-dd HH:mm");
        if (holder instanceof MessageSelfHolder) {
            MessageSelfHolder holder1 = (MessageSelfHolder) holder;
            if (time == null) {
                holder1.tv_time.setVisibility(View.GONE);
            } else {
                holder1.tv_time.setVisibility(View.VISIBLE);
                holder1.tv_time.setText(time);
            }
            Glide.with(mcontext).load(headurlself).into(holder1.iv_icon);
            TXImUtils.AnalysisMsg(timMessage, holder1.ll_content, mcontext, msgmaxw, plater);
        } else if (holder instanceof MessageOtherHolder) {
            MessageOtherHolder holder1 = (MessageOtherHolder) holder;
            if (time == null) {
                holder1.tv_time.setVisibility(View.GONE);
            } else {
                holder1.tv_time.setVisibility(View.VISIBLE);
                holder1.tv_time.setText(time);
            }
            Glide.with(mcontext).load(headurother).into(holder1.iv_icon);
            TXImUtils.AnalysisMsg(timMessage, holder1.ll_content, mcontext, msgmaxw, plater);
        }
    }

    @Override
    public int getItemCount() {
        return msgs == null ? 0 : msgs.size();
    }


}

