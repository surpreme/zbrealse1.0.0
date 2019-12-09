package chat.adapter;

import android.content.Context;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.aiteshangcheng.a.R;

import java.util.List;

import chat.model.SessionMuneInfo;

import static com.aite.a.base.Mark.CHOOSE_MENU;
import static com.aite.a.base.Mark.EMOJI_CODE;


/**
 * 会话底部菜单
 * Created by Administrator on 2017/11/22.
 */
public class SessionBottomAdapter extends RecyclerView.Adapter<SessionBottomHolder> {
    private int type = 1;// 1表情 2菜单
    private Context mContext;
    private Handler handler;
    private int intenh;
    private List<SessionMuneInfo> sessionMuneInfo;

    /**
     * emoji表情码
     */
    public int EmojiCode[] = new int[]{0x1F601, 0x1F602, 0x1F603, 0x1F604,
            0x1F605, 0x1F606, 0x1F607, 0x1F608, 0x1F609, 0x1F60A, 0x1F60B,
            0x1F60C, 0x1F60D, 0x1F60F, 0x1F612, 0x1F613, 0x1F614, 0x1F615,
            0x1F616, 0x1F617, 0x1F618, 0x1F61A, 0x1F61C, 0x1F61D, 0x1F61E,
            0x1F620, 0x1F621, 0x1F622, 0x1F623, 0x1F624, 0x1F625, 0x1F626,
            0x1F627, 0x1F628, 0x1F629};

    /**
     * 将unicode编码转换为一个char数组
     *
     * @param unicode
     * @return
     */
    private String getEmojiStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public SessionBottomAdapter(int type, Context mContext, Handler handler, int intenh,List<SessionMuneInfo> sessionMuneInfo) {
        this.type = type;
        this.mContext = mContext;
        this.handler=handler;
        this.intenh=intenh;
        this.sessionMuneInfo=sessionMuneInfo;
    }

    /**
     * 刷新
     */
    public void setType(int type,int intenh){
        this.type = type;
        this.intenh=intenh;
    }

    @Override
    public SessionBottomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SessionBottomHolder(LayoutInflater.from(mContext).inflate(R.layout.item_emoji, parent, false));
    }

    @Override
    public void onBindViewHolder(SessionBottomHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.ll_item.getLayoutParams();
        layoutParams.height=intenh;
        holder.ll_item.setLayoutParams(layoutParams);
        if (type == 1) {
            holder.tv_expression.setVisibility(View.VISIBLE);
            holder.ll_menu.setVisibility(View.GONE);
            holder.tv_expression.setText(getEmojiStringByUnicode(EmojiCode[position]));
        } else {
            holder.tv_expression.setVisibility(View.GONE);
            holder.ll_menu.setVisibility(View.VISIBLE);
            holder.iv_img.setImageResource(sessionMuneInfo.get(position).img);
            holder.tv_txt.setText(sessionMuneInfo.get(position).text);
        }
        holder.tv_expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //表情
                handler.sendMessage(handler.obtainMessage(EMOJI_CODE,EmojiCode[position]));
            }
        });
        holder.ll_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //菜单
                handler.sendMessage(handler.obtainMessage(CHOOSE_MENU,position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return type == 1 ? EmojiCode.length : sessionMuneInfo.size();
    }
}

class SessionBottomHolder extends RecyclerView.ViewHolder {
    TextView tv_expression, tv_txt;
    LinearLayout ll_menu,ll_item;
    ImageView iv_img;

    public SessionBottomHolder(View itemView) {
        super(itemView);
        tv_expression = (TextView) itemView.findViewById(R.id.tv_expression);
        tv_txt = (TextView) itemView.findViewById(R.id.tv_txt);
        ll_menu = (LinearLayout) itemView.findViewById(R.id.ll_menu);
        ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
    }
}
