package com.aite.a.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aiteshangcheng.a.R;
import com.aite.a.adapter.SpeAdapter;
import com.aite.a.base.Mark;
import com.aite.a.fargment.GoodsDetailsFargment;
import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecList;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecValues;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecValues.SpecValue;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.AnalyticalStr;
import com.aite.a.utils.CommonTools;
import com.lidroid.xutils.BitmapUtils;

/**
 * 宝贝详情界面的弹窗
 *
 * @author http://yecaoly.taobao.com
 */

public class AddInCartWindows implements OnDismissListener, OnClickListener, Mark {
    private TextView pop_add, pop_reduce, pop_num, pop_ok;
    private ImageView pop_del;
    private PopupWindow popupWindow;
    private ProgressDialog mdialog;
    private OnItemClickListener listener;
    private ListView spe_list;
    private LinearLayout ll_con;
    private ImageView spe_goods_img;
    private TextView spe_goods_price, spe_goods_inventory;
    private SpeAdapter speAdapter;
    private Activity activity;
    private GoodsDetailsInfo detailsInfo;
    private BitmapUtils bitmapUtils;
    private NetRun netRun;
    public String goods_num = "1";
    private final int ADDORREDUCE = 1;
    private boolean del;
    private List<String> list;
    private String goods_storage;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Mark.goods_details_id:
                    if (msg.obj != null) {
                        setView((GoodsDetailsInfo) msg.obj);
                    } else {
                        CommonTools
                                .showShortToast(activity,
                                        activity.getString(R.string.act_no_data)
                                                .toString());
                    }
                    mdialog.dismiss();
                    break;
                case Mark.goods_details_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(activity,
                            activity.getString(R.string.systembusy).toString());
                    break;
                case Mark.goods_details_start:
//				mdialog.setMessage(activity.getString(R.string.act_waiting)
//						.toString());
                    mdialog.show();
                    break;
                case 0:
                    String goods_id = getSpecGoodsID();
                    if (goods_id != null) {
                        netRun.getProductDetails(goods_id);
                    }
                    break;
            }
        }

        ;
    };

    private specifications goods_id1 = null;

    public void setspecifications(specifications gg) {
        goods_id1 = gg;
    }

    //将规格商品ID传出去的接口
    interface specifications {
        void onClick(String goods_id);
    }

    Handler handlerrr;

    public AddInCartWindows(Activity activity, Handler handlerr) {
        handlerrr = handlerr;
        this.activity = activity;
        mdialog = new ProgressDialog(activity);
        netRun = new NetRun(activity, handler);
        bitmapUtils = new BitmapUtils(activity);
        mdialog.setProgressStyle(mdialog.STYLE_SPINNER);
        View view = LayoutInflater.from(activity).inflate(R.layout.add_cart,
                null);
        spe_list = (ListView) view.findViewById(R.id.spe_list);
        pop_add = (TextView) view.findViewById(R.id.pop_add);
        pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
        pop_num = (TextView) view.findViewById(R.id.pop_num);
        pop_ok = (TextView) view.findViewById(R.id.pop_ok);
        ll_con = (LinearLayout) view.findViewById(R.id.ll_con);
        spe_goods_inventory = (TextView) view
                .findViewById(R.id.spe_goods_inventory);
        pop_del = (ImageView) view.findViewById(R.id.pop_del);
        spe_goods_img = (ImageView) view.findViewById(R.id.spe_goods_img);
        spe_goods_price = (TextView) view.findViewById(R.id.spe_goods_price);
        pop_add.setOnClickListener(this);
        pop_reduce.setOnClickListener(this);
        pop_ok.setOnClickListener(this);
        pop_del.setOnClickListener(this);
        ll_con.setOnClickListener(this);
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        // 设置popwindow的动画效果
        popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOnDismissListener(this);// 当popWindow消失时的监听
        speAdapter = new SpeAdapter(activity, handler);
        spe_list.setAdapter(speAdapter);
        spe_list.setFocusable(false);
        spe_list.setDividerHeight(0);

    }

    /**
     * 设置宝贝详情界面的弹窗的数据
     */
    public void setData(GoodsDetailsInfo detailsInfo, List<String> list) {
        this.detailsInfo = detailsInfo;
        this.list = list;
        setView(detailsInfo);
        // TODO 详细
        speAdapter.setSpes(detailsInfo);
        speAdapter.notifyDataSetChanged();
    }

    private String goods_id = null;
    private GoodsDetailsFargment goodsFargment;

    /**
     * 设置宝贝详情界面的弹窗的规格 信息
     */
    private void setView(GoodsDetailsInfo detailsInfo) {
        if (getSpecGoodsID() != null) {
//		goods_id1.onClick(getSpecGoodsID());
//		goodsFargment=new GoodsDetailsFargment(goods_id);
//		goodsFargment.handler.sendMessage(goodsFargment.handler.obtainMessage(GoodsDetails_detailsInfo, detailsInfo));
            handlerrr.sendMessage(handlerrr.obtainMessage(10, detailsInfo));
        }
        goods_id = detailsInfo.goods_info.goods_id;
        goods_storage = detailsInfo.goods_info.goods_storage;
        spe_goods_price.setText("￥" + detailsInfo.goods_info.goods_price);
        spe_goods_inventory
                .setText(APPSingleton.getContext()
                        .getString(R.string.act_stock).toString()
                        + goods_storage
                        + activity.getString(R.string.act_a).toString());
        String image = detailsInfo.goods_image;
        try {
            String substring = image.substring(0, image.indexOf(","));
            bitmapUtils.display(spe_goods_img, substring);
        } catch (Exception e) {
            bitmapUtils.display(spe_goods_img, detailsInfo.goods_image);
        }
        if (speAdapter != null) {
            speAdapter.flushAdapter();
        }
    }

    public interface OnItemClickListener {
        /**
         * 设置点击确认按钮时监听接口
         */
        public void onClickOKPop();
    }

    /**
     * 设置监听
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 当popWindow消失时响应
    @Override
    public void onDismiss() {
        del = true;
        listener.onClickOKPop();
        dissmiss();
        del = false;
    }

    /**
     * 弹窗显示的位置
     */
    public void showAsDropDown(View parent) {
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    /**
     * 消除弹窗
     */
    public void dissmiss() {
        popupWindow.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pop_add) {
            if (!pop_num.getText().toString().equals(goods_storage)) {
                String num_add = Integer.valueOf(pop_num.getText().toString())
                        + ADDORREDUCE + "";
                pop_num.setText(num_add);
                goods_num = num_add;
            } else {
                CommonTools.showShortToast(activity,
                        activity.getString(R.string.act_no_more).toString());
            }
        } else if (v.getId() == R.id.pop_reduce) {
            if (!pop_num.getText().toString().equals("1")) {
                String num_reduce = Integer.valueOf(pop_num.getText()
                        .toString()) - ADDORREDUCE + "";
                pop_num.setText(num_reduce);
                goods_num = num_reduce;
            } else {
                CommonTools.showShortToast(activity,
                        activity.getString(R.string.act_less_one).toString());
            }
        } else if (v.getId() == R.id.pop_del) {
            del = true;
            listener.onClickOKPop();
            dissmiss();
            del = false;
        } else if (v.getId() == R.id.pop_ok) {
            listener.onClickOKPop();
        } else if (v.getId() == R.id.ll_con) {
            popupWindow.dismiss();
        }

//		switch (v.getId()) {
//		case R.id.pop_add:
//			if (!pop_num.getText().toString().equals(goods_storage)) {
//				String num_add = Integer.valueOf(pop_num.getText().toString())
//						+ ADDORREDUCE + "";
//				pop_num.setText(num_add);
//				goods_num = num_add;
//			} else {
//				CommonTools.showShortToast(activity,
//						activity.getString(R.string.act_no_more).toString());
//			}
//			break;
//		case R.id.pop_reduce:
//			if (!pop_num.getText().toString().equals("1")) {
//				String num_reduce = Integer.valueOf(pop_num.getText()
//						.toString()) - ADDORREDUCE + "";
//				pop_num.setText(num_reduce);
//				goods_num = num_reduce;
//			} else {
//				CommonTools.showShortToast(activity,
//						activity.getString(R.string.act_less_one).toString());
//			}
//			break;
//		case R.id.pop_del:
//			del = true;
//			listener.onClickOKPop();
//			dissmiss();
//			del = false;
//			break;
//		case R.id.pop_ok:
//			listener.onClickOKPop();
//			break;
//			case R.id.ll_con:
//				//TODO
//				popupWindow.dismiss();
//				break;
//		}
    }

    /**
     * 获取规格列表（spec_list）里的商品ID
     *
     * @param
     * @return
     */
    public String getSpecGoodsID() {
        List<String> ids = new ArrayList<String>();
        for (SpecValues values : speAdapter.spes) {
            for (SpecValue value : values.spec_value) {
                if (value.isSelect == true) {
                    ids.add(value.id);
                }
            }
        }
        for (SpecList specList : detailsInfo.spec.spec_list) {
            List<String> list = Arrays.asList(AnalyticalStr.stringAnalytical(
                    specList.id, "|"));
            if (list.size() != ids.size())
                return null;
            if (isEquals(ids, specList.id) == true)
                return specList.goods_id;
        }
        return null;
    }

    /**
     * 判断规格列表（spec_list）是否包含所选择所有 的规格
     *
     * @param ids
     * @param id
     * @return
     */
    public Boolean isEquals(List<String> ids, String id) {
        boolean isEquals = true;
        for (String string : ids) {
            if (!id.contains(string)) {
                isEquals = false;
            }
        }
        return isEquals;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

}
