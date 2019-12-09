package livestream.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;

import java.util.List;

import livestream.mode.AreaList;


/**
 * 地址选择
 */
public class AddressPopu extends PopupWindow {
    private Activity mActivity = null;
    private ListView lv_address;
    private Level1Adapter level1Adapter;
    private List<AreaList> areaLists;

    public AddressPopu(Activity activity, List<AreaList> areaLists) {
        mActivity = activity;
        this.areaLists = areaLists;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_zbaddress, null);
        lv_address = (ListView) view.findViewById(R.id.lv_address);
        level1Adapter = new Level1Adapter(areaLists);
        lv_address.setAdapter(level1Adapter);
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
//		setAnimationStyle(R.style.AnimBottomPopup);

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
     * 一级
     */
    private class Level1Adapter extends BaseAdapter {
        List<AreaList> areaLists;

        public Level1Adapter(List<AreaList> areaLists) {
            this.areaLists = areaLists;
        }

        @Override
        public int getCount() {
            return areaLists == null ? 0 : areaLists.size();
        }

        @Override
        public Object getItem(int position) {
            return areaLists == null ? null : areaLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_zmchooseaddress, null);
                new ViewHodler(convertView);
            }
            final AreaList areaList = areaLists.get(position);
            final ViewHodler holder = (ViewHodler) convertView.getTag();
            holder.tv_address.setText(areaList.area_name);

            Leve21Adapter adapter = new Leve21Adapter(areaList.city_list,areaList.area_name);
            holder.lv_address2.setAdapter(adapter);
            holder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (areaList.ispick) {
                        holder.lv_address2.setVisibility(View.GONE);
                        areaList.ispick=false;
                    } else {
                        holder.lv_address2.setVisibility(View.VISIBLE);
                        areaList.ispick=true;
                    }
                }
            });
            return convertView;
        }

        class ViewHodler {
            TextView tv_address;
            ImageView iv_direction;
            RelativeLayout rl_item;
            MyListView lv_address2;

            public ViewHodler(View convertView) {
                tv_address = convertView.findViewById(R.id.tv_address);
                iv_direction = convertView.findViewById(R.id.iv_direction);
                rl_item = convertView.findViewById(R.id.rl_item);
                lv_address2 = convertView.findViewById(R.id.lv_address2);
                convertView.setTag(this);
            }
        }
    }

    private class Leve21Adapter extends BaseAdapter {
        List<AreaList.city_list> areaLists;
        private String area_name;

        public Leve21Adapter(List<AreaList.city_list> areaLists,String area_name) {
            this.areaLists = areaLists;
            this.area_name=area_name;
        }

        @Override
        public int getCount() {
            return areaLists == null ? 0 : areaLists.size();
        }

        @Override
        public Object getItem(int i) {
            return areaLists == null ? null : areaLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mActivity, R.layout.item_addresslevel2, null);
                new Leve21Holder(view);
            }
            Leve21Holder holder = (Leve21Holder) view.getTag();
            final AreaList.city_list areaList = areaLists.get(i);
            holder.tv_address.setText(areaList.area_name);
            holder.tv_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mdate != null) {
                        mdate.onItemClick(area_name, areaList.area_name);
                        dismiss();
                    }
                }
            });
            return view;
        }

        class Leve21Holder {
            TextView tv_address;

            public Leve21Holder(View view) {
                tv_address = view.findViewById(R.id.tv_address);
                view.setTag(this);
            }
        }
    }


    private date mdate = null;

    public void setdate(date p) {
        mdate = p;
    }

    public interface date {
        void onItemClick(String address1, String address2);
    }
}
