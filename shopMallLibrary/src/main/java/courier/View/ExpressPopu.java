package courier.View;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aiteshangcheng.a.R;

import java.util.List;

import courier.model.DeliveryCourierInfo;

/**
 * 快递方式
 * Created by Administrator on 2018/1/11.
 */
public class ExpressPopu extends PopupWindow {
    private Activity mActivity = null;
    private GridView gv_way;
    private WayAdapter wayAdapter;
    private List<DeliveryCourierInfo> deliveryCourierInfo;
    public ExpressPopu(final Activity mActivity,List<DeliveryCourierInfo> deliveryCourierInfo) {
        this.mActivity = mActivity;
        this.deliveryCourierInfo=deliveryCourierInfo;
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_expressway, null);
        gv_way= (GridView) view.findViewById(R.id.gv_way);
        wayAdapter=new WayAdapter();
        gv_way.setAdapter(wayAdapter);
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
        setAnimationStyle(R.style.AnimBottomPopup4);
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

        ;
    };
    private void showEvent() {
        h.sendEmptyMessage(0);
//        h.sendEmptyMessageDelayed(0, 300);
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

    private class WayAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return deliveryCourierInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return deliveryCourierInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=View.inflate(mActivity,R.layout.item_expressway,null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final DeliveryCourierInfo Info = ExpressPopu.this.deliveryCourierInfo.get(position);
            holder.tv_name.setText(Info.e_name);
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mmenu==null)return;
                    mmenu.onItemClick(Info.id);
                    dismiss();
                }
            });
            return convertView;
        }
        class ViewHolder{
            TextView tv_name;
            public ViewHolder(View convertView) {
                tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(this);
            }
        }
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(String id);
    }
}
