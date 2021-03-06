package livestream.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;

import livestream.mode.BeautifyInfo;

/**
 * 观众
 */
public class SpectatorListPopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private FiltrationAdapter filtrationAdapter;
    private RecyclerView rv_list;
    private Handler handler;
    public SpectatorListPopu(Activity activity,Handler handler) {
        mActivity = activity;
        this.handler=handler;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_zb_spectatorlist, null);
        rv_list = (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rv_list.setLayoutManager(linearLayoutManager);
        filtrationAdapter = new FiltrationAdapter();
        rv_list.setAdapter(filtrationAdapter);
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
                WindowManager.LayoutParams lp = mActivity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup3);
    }

    Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    WindowManager.LayoutParams lp = mActivity.getWindow()
                            .getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    private date mdate = null;

    public void setdate(date p) {
        mdate = p;
    }

    public interface date {
        void onItemClick(BeautifyInfo beautifyInfo);
    }


    /**
     * 观众适配
     */
    private class FiltrationAdapter extends RecyclerView.Adapter<FiltrationHolder> {

        @Override
        public FiltrationHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            FiltrationHolder holder = new FiltrationHolder(LayoutInflater.from(mActivity)
                    .inflate(R.layout.item_zb_spectator, viewGroup, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(FiltrationHolder filtrationHolder, final int i) {
            filtrationHolder.iv_icon.setImageResource(R.drawable.ic_launcher);
            filtrationHolder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(102);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 12;
        }
    }

    class FiltrationHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_icon;
        TextView tv_name, tv_level;

        public FiltrationHolder(View itemView) {
            super(itemView);
            iv_icon = (CircleImageView) itemView.findViewById(R.id.iv_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_level = (TextView) itemView.findViewById(R.id.tv_level);
        }
    }

}
