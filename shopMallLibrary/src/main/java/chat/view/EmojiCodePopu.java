package chat.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.model.ConsultingTypeinfo;
import com.aiteshangcheng.a.R;

import java.util.List;

public class EmojiCodePopu extends PopupWindow {
    private Activity mActivity = null;
    private RecyclerView rv_emoji;
    private EmojiAdapter emojiAdapter;
    public EmojiCodePopu(Activity activity,
                         List<ConsultingTypeinfo> consultingTypeinfo) {
        mActivity = activity;
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_emoji, null);
        rv_emoji= (RecyclerView) view.findViewById(R.id.rv_emoji);
        rv_emoji.setLayoutManager(new GridLayoutManager(mActivity,7));
        emojiAdapter=new EmojiAdapter();
        rv_emoji.setAdapter(emojiAdapter);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                android.view.WindowManager.LayoutParams lp = mActivity
                        .getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup);

    }

    Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    android.view.WindowManager.LayoutParams lp = mActivity
                            .getWindow().getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }

        ;
    };

    private void showEvent() {
        h.sendEmptyMessageDelayed(0, 500);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showEvent();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showEvent();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showEvent();
    }
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

    /**
     * 表情监听
     */
    private class EmojiAdapter extends RecyclerView.Adapter<EmojiHolder>{
        @Override
        public EmojiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EmojiHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_emoji,parent,false));
        }

        @Override
        public void onBindViewHolder(EmojiHolder holder, int position) {
            holder.tv_expression.setText(getEmojiStringByUnicode(EmojiCode[position]));
        }

        @Override
        public int getItemCount() {
            return EmojiCode.length;
        }
    }

    class EmojiHolder extends RecyclerView.ViewHolder{
        TextView tv_expression;

        public EmojiHolder(View itemView) {
            super(itemView);
            tv_expression= (TextView) itemView.findViewById(R.id.tv_expression);
        }
    }
    private huidiao mhuidiao = null;

    public void sethuidiao(huidiao p) {
        mhuidiao = p;
    }

    public interface huidiao {
        void onItemClick(ConsultingTypeinfo data);
    }
}
