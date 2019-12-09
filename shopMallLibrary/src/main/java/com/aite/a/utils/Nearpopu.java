package com.aite.a.utils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.model.Area_list;
import com.aite.a.model.CategoryList;
import com.aiteshangcheng.a.R;

import java.util.List;

public class Nearpopu extends PopupWindow implements OnClickListener {
    private Activity mActivity = null;
    private ListView lv_nearpopulist, lv_nearpopulist2, lv_nearpopulist3;
    private MyAdapter myadapter;
    private List<Area_list> arealist;
    private List<CategoryList> categoryOne;
    private fenleiAdapter fla;
    private paixuAdapter pxa;
    private juliAdapter jla;
    private int pos;

    public Nearpopu(Activity activity, int pos) {
        myadapter = new MyAdapter();
        fla = new fenleiAdapter();
        pxa = new paixuAdapter();
        jla = new juliAdapter();
        mActivity = activity;
        this.arealist = lingshi.getInstance().getArealist();
        this.categoryOne = lingshi.getInstance().getCategoryOne();
        this.pos = pos;
        paixu = new String[]{mActivity.getString(R.string.near_reminder1), mActivity.getString(R.string.near_reminder2),
                mActivity.getString(R.string.near_reminder3), mActivity.getString(R.string.near_reminder4)};
        juli = new String[]{mActivity.getString(R.string.near_reminder5), mActivity.getString(R.string.near_reminder6),
                mActivity.getString(R.string.near_reminder7), mActivity.getString(R.string.near_reminder8)};
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager) mActivity
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        View view = View.inflate(mActivity, R.layout.nearpopu, null);
        lv_nearpopulist = (ListView) view.findViewById(R.id.lv_nearpopulist);
        lv_nearpopulist2 = (ListView) view.findViewById(R.id.lv_nearpopulist2);
        lv_nearpopulist3 = (ListView) view.findViewById(R.id.lv_nearpopulist3);
        switch (pos) {
            case 1:
                lv_nearpopulist.setAdapter(myadapter);
                break;
            case 2:
                lv_nearpopulist.setAdapter(fla);
                break;
            case 3:
                lv_nearpopulist.setAdapter(pxa);
                break;
            case 4:
                lv_nearpopulist.setAdapter(jla);
                break;
        }

        lv_nearpopulist.setOnItemClickListener(listener);
        lv_nearpopulist2.setOnItemClickListener(listener2);
        lv_nearpopulist3.setOnItemClickListener(listener3);

        // 改变二级菜单的宽度
        ViewGroup.LayoutParams layoutParam2 = lv_nearpopulist2
                .getLayoutParams();
        layoutParam2.width = (width / 3) * 2;
        lv_nearpopulist2.setLayoutParams(layoutParam2);
        // 改变三级菜单的宽度
        ViewGroup.LayoutParams layoutParams3 = lv_nearpopulist3
                .getLayoutParams();
        layoutParams3.width = width / 3;
        lv_nearpopulist3.setLayoutParams(layoutParams3);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity
                        .getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        // 外部可被操作
        setOutsideTouchable(false);
        // setFocusable(false);

        // switch (popwstyle) {
        // case 0:
        // // 设置PopupWindow弹出窗体动画效果
        // setAnimationStyle(R.style.AnimnearPopup);
        // break;
        // case 1:
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimnearPopup1);
        // break;
        // case 2:
        // // 设置PopupWindow弹出窗体动画效果
        // setAnimationStyle(R.style.AnimnearPopup2);
        // break;
        //
        // }
    }

  /*  Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    WindowManager.LayoutParams lp = mActivity
                            .getWindow().getAttributes();
                    lp.alpha = 0.8f;
//                    mActivity.getWindow().setAttributes(lp);
                    break;
                case 1:
                    dismiss();
                    break;
            }
        }

        ;
    };*/
    /**
     * 一级菜单监听
     */
    public OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            // touming(lv_nearpopulist2, R.anim.pingyi);
            // lv_nearpopulist2.setAdapter(myadapter);
            // lv_nearpopulist2.setVisibility(View.VISIBLE);
            // lv_nearpopulist3.setVisibility(View.GONE);
        }
    };
    /**
     * 二级菜单监听
     */
    public OnItemClickListener listener2 = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // touming(lv_nearpopulist3, R.anim.pingyi);
            // lv_nearpopulist3.setAdapter(myadapter);
            // lv_nearpopulist3.setVisibility(View.VISIBLE);
        }
    };
    /**
     * 三级菜单监听
     */
    public OnItemClickListener listener3 = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // dismiss();
        }
    };

  /*  private void showEvent() {
        h.sendEmptyMessageDelayed(0, 500);
    }*/

  /*  @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
//        showEvent();
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
*/
    @Override
    public void onClick(View v) {

    }

    String[] paixu ;
    String[] juli;
    // 区域回调
    public huidiao mhuidiao = null;

    public void sethuidiao(huidiao p) {
        mhuidiao = p;
    }

    public interface huidiao {
        void onItemClick(String area_id,String area_name);
    }

    // 分类回调
    public calsshuidiao1 calsshuidiao1mhuidiao1 = null;

    public void setcalsshuidiao1(calsshuidiao1 p1) {
        calsshuidiao1mhuidiao1 = p1;
    }

    public interface calsshuidiao1 {
        void onItemClick(String class_id,String category);
    }

    // 排序回调
    public paixuhuidiao1 paixuhuidiao1mhuidiao1 = null;

    public void setpaixuhuidiao1(paixuhuidiao1 paixu) {
        paixuhuidiao1mhuidiao1 = paixu;
    }

    public interface paixuhuidiao1 {
        void onItemClick(String sort_type,String sortName);
    }

    // 距离回调
    public julihuidiao1 julihuidiao1mhuidiao1 = null;

    public void setjulihuidiao1(julihuidiao1 paixu) {
        julihuidiao1mhuidiao1 = paixu;
    }

    public interface julihuidiao1 {
        void onItemClick(String sort_dis_type,String distanceName);
    }

    /**
     * 区域适配
     *
     * @author Administrator
     */
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arealist.size();
        }

        @Override
        public Object getItem(int position) {

            return arealist == null ? null : arealist.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.nearpopuitem,
                        null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (position == 0) {
                holder.tv_cityitem.setText(mActivity.getString(R.string.cityy));
            } else {
                holder.tv_cityitem.setText(arealist.get(position - 1).area_name);
            }
            holder.tv_cityitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        mhuidiao.onItemClick("",mActivity.getString(R.string.cityy));
                    } else {
                        mhuidiao.onItemClick(arealist.get(position - 1).area_id,arealist.get(position - 1).area_name);
                    }
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_cityitem;

            public ViewHolder(View convertView) {
                tv_cityitem = (TextView) convertView
                        .findViewById(R.id.tv_cityitem);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 分类适配
     *
     * @author Administrator
     */
    public class fenleiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return categoryOne.size() + 1;
        }

        @Override
        public Object getItem(int position) {

            return categoryOne == null ? null : categoryOne.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.nearpopuitem,
                        null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (position == 0) {
                holder.tv_cityitem.setText(mActivity.getString(R.string.all_classification));
            } else {
                holder.tv_cityitem.setText(categoryOne.get(position - 1).getGc_name());
            }
            holder.tv_cityitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        calsshuidiao1mhuidiao1.onItemClick("","全部分类");
                    } else {
                        calsshuidiao1mhuidiao1.onItemClick(categoryOne
                                .get(position - 1).getGc_id(),categoryOne.get(position - 1).getGc_name());
                    }
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_cityitem;

            public ViewHolder(View convertView) {
                tv_cityitem = (TextView) convertView
                        .findViewById(R.id.tv_cityitem);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 排序适配
     *
     * @author Administrator
     */
    public class paixuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return paixu.length;
        }

        @Override
        public Object getItem(int position) {

            return paixu == null ? null : paixu[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.nearpopuitem,
                        null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_cityitem.setText(paixu[position]);
            // paixuhuidiao1mhuidiao1
            holder.tv_cityitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    paixuhuidiao1mhuidiao1.onItemClick(position + "",paixu[position]);
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_cityitem;

            public ViewHolder(View convertView) {
                tv_cityitem = (TextView) convertView
                        .findViewById(R.id.tv_cityitem);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 距离适配
     *
     * @author Administrator
     */
    public class juliAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return juli.length;
        }

        @Override
        public Object getItem(int position) {

            return juli == null ? null : juli[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.nearpopuitem,
                        null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_cityitem.setText(juli[position]);
            holder.tv_cityitem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    julihuidiao1mhuidiao1.onItemClick(position + "",juli[position]);
                    dismiss();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_cityitem;

            public ViewHolder(View convertView) {
                tv_cityitem = (TextView) convertView
                        .findViewById(R.id.tv_cityitem);
                convertView.setTag(this);
            }
        }
    }

    private void touming(ListView view, int x) {
        Animation animation = AnimationUtils.loadAnimation(mActivity, x);
        view.startAnimation(animation);
    }

}
