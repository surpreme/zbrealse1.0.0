package livestream.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

import livestream.mode.HostMenuInfo;

/**
 * 主播菜单
 */
public class HostMenuPopu extends PopupWindow {
    private Activity mActivity = null;
    private RecyclerView rv_menu;
    private List<HostMenuInfo> imglist;
    private MenuAdapter menuAdapter;
    public HostMenuPopu(Activity activity) {
        mActivity = activity;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
		setHeight((int) (height * 0.3));
//        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_zbhostmenu, null);
        rv_menu = (RecyclerView) view.findViewById(R.id.rv_menu);
        rv_menu.setLayoutManager(new GridLayoutManager(mActivity,4));
        imglist = new ArrayList<>();
        imglist.add(new HostMenuInfo(R.drawable.zb_prepareicon4, "反转", false));
        imglist.add(new HostMenuInfo(R.drawable.zb_childmenu1, "公告", false));
        imglist.add(new HostMenuInfo(R.drawable.zb_childmenu2, "静音", false));
        imglist.add(new HostMenuInfo(R.drawable.zb_childmenu3, "清屏", false));
        imglist.add(new HostMenuInfo(R.drawable.zb_childmenu4, "闪光灯", false));
        imglist.add(new HostMenuInfo(R.drawable.zb_childmenu5, "消息", false));
        imglist.add(new HostMenuInfo(R.drawable.zb_childmenu6, "大字幕", false));
        menuAdapter=new MenuAdapter();
        rv_menu.setAdapter(menuAdapter);

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
        setAnimationStyle(R.style.AnimBottomPopup);

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

    /**
     * 菜单列表
     */
    private class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

        @Override
        public MenuHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            MenuHolder holder = new MenuHolder(LayoutInflater.from(mActivity)
                    .inflate(R.layout.item_hostmenu, viewGroup, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MenuHolder meniolder, final int i) {
            meniolder.iv_img.setImageResource(imglist.get(i).img);
            meniolder.tv_name.setText(imglist.get(i).txt);
            meniolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mdate.onItemClick(imglist.get(i).txt);

                }
            });
        }

        @Override
        public int getItemCount() {
            return imglist.size();
        }
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_name;
        LinearLayout ll_item;
        public MenuHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ll_item= (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    private date mdate = null;

    public void setdate(date p) {
        mdate = p;
    }

    public interface date {
        void onItemClick(String year);
    }
}
