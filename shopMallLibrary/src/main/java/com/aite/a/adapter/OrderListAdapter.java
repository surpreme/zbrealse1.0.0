package com.aite.a.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.activity.BuyerOrderFgActivity;
import com.aite.a.activity.GoodsSevaluationActivity;
import com.aite.a.activity.OrderInfoActivity;
import com.aite.a.activity.OrderRefundActivity;
import com.aite.a.activity.PayListActivity;
import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.activity.WebActivity;
import com.aite.a.base.Mark.State;
import com.aite.a.model.OrderGroupList.OrderList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.Mypopu;
import com.aite.a.utils.lingshi;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 订单适配器
 *
 * @author xiaoyu
 */
public class OrderListAdapter extends BaseAdapter {
    private Activity activity;
    private List<OrderList> mOrderList;
    private int what;
    private NetRun netRun;

    public OrderListAdapter(Activity activity, List<OrderList> mOrderList, int what, Handler handler) {
        super();
        this.activity = activity;
        this.mOrderList = mOrderList;
        this.what = what;
        netRun = new NetRun(activity, handler);
    }

    @Override
    public int getCount() {
        return mOrderList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            v = LayoutInflater.from(activity).inflate(R.layout.order_item, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        final OrderList orderList = mOrderList.get(position);
        holder.order_store.setText(orderList.store_name);
        holder.goods_price.setText(activity.getString(R.string.zhifufangshi) + orderList.payment_name + "\t" + activity.getString(R.string.release_goods96));
        holder.amount.setText(orderList.order_amount);
        holder.shippingFeeTv.setText(orderList.shipping_fee);

        holder.detele_bt.setOnClickListener(listener(orderList, holder));
        holder.affirm_tx.setOnClickListener(listener(orderList, holder));
        holder.order_datails.setOnClickListener(listener(orderList, holder));
        holder.order_pay.setOnClickListener(listener(orderList, holder));
        holder.order_logistics.setOnClickListener(listener(orderList, holder));
        setBooton(holder, orderList);
        OrderGoodsListAdapter adapter = new OrderGoodsListAdapter(activity, mOrderList.get(position).extend_order_goods, orderList.if_complain);
        holder.listView.setAdapter(adapter);
        holder.listView.setDividerHeight(0);

        if (orderList.if_lock.equals("true")) {
            holder.state_desc.setText(activity.getString(R.string.refundstate));
//			holder.tv_refund.setVisibility(View.VISIBLE);
        } else {
            holder.state_desc.setText(orderList.state_desc);
//			holder.tv_refund.setVisibility(View.GONE);
        }
        holder.order_store.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, StoreDetailsActivity.class);
                intent.putExtra("store_id", orderList.store_id);
                activity.startActivity(intent);
            }
        });
        return v;
    }

    //不同状态订单显示对应操作
    private void setBooton(ViewHolder holder, OrderList orderList) {
        switch (what) {
            case 0:
                if (Integer.valueOf(orderList.order_state) == 0) {
                    holder.detele_bt.setVisibility(View.GONE);
                    holder.affirm_tx.setVisibility(View.GONE);
                    holder.order_datails.setVisibility(View.GONE);
                    holder.order_logistics.setVisibility(View.GONE);
                } else if (Integer.valueOf(orderList.order_state) == 10) {
                    holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.cancel_order).toString());
                    holder.order_datails.setVisibility(View.GONE);
                    holder.affirm_tx.setVisibility(View.GONE);
                    holder.order_logistics.setVisibility(View.GONE);
                    holder.order_pay.setVisibility(View.VISIBLE);
                    if (Boolean.valueOf(orderList.if_cancel) == true) {
                        holder.detele_bt.setVisibility(View.VISIBLE);
                    } else {
                        holder.detele_bt.setVisibility(View.GONE);
                    }

                } else if (Integer.valueOf(orderList.order_state) == 20) {
                    holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.order_refund).toString());
                    holder.affirm_tx.setVisibility(View.GONE);
                    holder.order_logistics.setVisibility(View.GONE);
                    holder.order_datails.setVisibility(View.VISIBLE);
                    if (Boolean.valueOf(orderList.if_refund_cancel) == true && Boolean.valueOf(orderList.if_lock) != true) {
                        holder.detele_bt.setVisibility(View.VISIBLE);
                    } else {
                        holder.affirm_tx.setVisibility(View.GONE);
                        holder.detele_bt.setVisibility(View.GONE);
                    }
                } else if (Integer.valueOf(orderList.order_state) == 30) {
                    holder.affirm_tx.setText(APPSingleton.getContext().getString(R.string.confirm_receipt).toString());
                    holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.order_refund).toString());
//				holder.order_logistics.setVisibility(View.VISIBLE);
                    holder.order_datails.setVisibility(View.VISIBLE);
//				holder.detele_bt.setVisibility(View.GONE);
                    if (Boolean.valueOf(orderList.if_receive) == true) {
                        holder.affirm_tx.setVisibility(View.VISIBLE);
                    } else {
                        holder.affirm_tx.setVisibility(View.GONE);
                    }
                    if (Boolean.valueOf(orderList.if_refund_cancel) == true && Boolean.valueOf(orderList.if_lock) != true) {
                        holder.detele_bt.setVisibility(View.VISIBLE);
                    } else {
                        holder.detele_bt.setVisibility(View.GONE);
                    }
                    // if (Boolean.valueOf(orderList.if_deliver) == true) {
                    // holder.detele_bt.setVisibility(View.VISIBLE);
                    // } else {

                    // }
                } else if (Integer.valueOf(orderList.order_state) == 40) {
                    holder.affirm_tx.setVisibility(View.GONE);
                    holder.order_logistics.setVisibility(View.GONE);
                    holder.order_datails.setVisibility(View.VISIBLE);
                    if (Boolean.valueOf(orderList.if_evaluation) == true) {
                        holder.detele_bt.setVisibility(View.VISIBLE);
                        holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.evaluation).toString());
                    } else {
                        holder.detele_bt.setVisibility(View.GONE);
                    }
                } else {
                }

                break;
            case 1:
                holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.cancel_order).toString());
                holder.affirm_tx.setVisibility(View.GONE);
                holder.order_logistics.setVisibility(View.GONE);
                holder.order_pay.setVisibility(View.VISIBLE);
                if (Boolean.valueOf(orderList.if_cancel) == true) {
                    holder.detele_bt.setVisibility(View.VISIBLE);
                } else {
                    holder.detele_bt.setVisibility(View.GONE);
                }

                break;
            case 2:
                //待收货
                holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.order_refund).toString());
                holder.affirm_tx.setVisibility(View.GONE);
                holder.order_logistics.setVisibility(View.GONE);

                if (Boolean.valueOf(orderList.if_refund_cancel) == true && Boolean.valueOf(orderList.if_lock) != true) {
                    holder.detele_bt.setVisibility(View.VISIBLE);
                } else {
                    holder.affirm_tx.setVisibility(View.GONE);
                    holder.detele_bt.setVisibility(View.GONE);
                }
                break;
            case 3:
                holder.affirm_tx.setText(APPSingleton.getContext().getString(R.string.confirm_receipt).toString());
//			holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.view_logistics).toString());
//			holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.order_refund).toString());
                holder.detele_bt.setVisibility(View.GONE);
//			holder.order_logistics.setVisibility(View.VISIBLE);
                if (Boolean.valueOf(orderList.if_receive) == true) {
                    holder.affirm_tx.setVisibility(View.VISIBLE);
                } else {
                    holder.affirm_tx.setVisibility(View.GONE);
                }
                // if (Boolean.valueOf(orderList.if_deliver) == true) {
                // holder.detele_bt.setVisibility(View.VISIBLE);
                // } else {
                // }
                break;
            case 4:
                holder.affirm_tx.setVisibility(View.VISIBLE);
                holder.affirm_tx.setText(APPSingleton.getContext().getString(R.string.order_refund));
                holder.order_logistics.setVisibility(View.GONE);
//			String userId = State.UserId;
                if (Boolean.valueOf(orderList.if_evaluation)) {
                    holder.detele_bt.setVisibility(View.VISIBLE);
                    holder.detele_bt.setText(APPSingleton.getContext().getString(R.string.evaluation));
                } else {
                    holder.detele_bt.setVisibility(View.GONE);
                }
                break;
        }
    }

    private OnClickListener listener(final OrderList orderList, final ViewHolder holder) {
        final int state = Integer.valueOf(orderList.order_state);
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.affirm_tx) {
                    switch (what) {        //来自哪个订单状态界面的点击
                        case 0:                //全部订单
                            if (state == 10) {
                                Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
                            } else if (state == 20) {
                            } else if (state == 30) {
                                //确认收货
                                netRun.orderReceive(orderList.order_id);
                            } else if (state == 40) {

                            } else {
                            }
                            break;
                        case 2:            //待发货
                            //物流
//						netRun.searchSeliver(orderList.order_id);
                            break;
                        case 3:            //待收货
//						showpopw(orderList,holder.affirm_tx);
                            //收货
                            netRun.orderReceive(orderList.order_id);
                            break;
                        case 4:            //交易完成
                            //交易完成 -> 订单退款
                            Intent intenttk2 = new Intent(activity, OrderRefundActivity.class);
                            intenttk2.putExtra("order_id", orderList.order_id);
                            activity.startActivity(intenttk2);
                            break;
                    }
                } else if (v.getId() == R.id.detele_bt) {
                    //同一按钮对不同订单状态可执行不同操作
                    switch (what) {
                        case 0:
                            if (state == 10) {
                                Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
                            } else if (state == 20) {
                                //退款
                                Intent intent = new Intent(activity, OrderRefundActivity.class);
                                intent.putExtra("order_id", orderList.order_id);
                                activity.startActivity(intent);
//							Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
                            } else if (state == 30) {
                            } else if (state == 40) {
                                //评价
                                Intent intent = new Intent();
                                intent.setClass(activity, GoodsSevaluationActivity.class);
                                intent.putExtra("order_sn", orderList.order_sn);
                                activity.startActivity(intent);
                            } else {
                            }
                            break;
                        case 1:
                            Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
                            break;
                        case 2:
                            System.out.println("------------------待发货退款");
                            Intent intenttk = new Intent(activity, OrderRefundActivity.class);
                            intenttk.putExtra("order_id", orderList.order_id);
                            activity.startActivity(intenttk);
//						Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
                            break;
                        case 3:
                            //待收货->退款
                            Intent intenttk2 = new Intent(activity, OrderRefundActivity.class);
                            intenttk2.putExtra("order_id", orderList.order_id);
                            activity.startActivity(intenttk2);
                            break;
                        case 4:
                            Intent intent = new Intent();
                            intent.setClass(activity, GoodsSevaluationActivity.class);
                            intent.putExtra("order_sn", orderList.order_sn);
                            activity.startActivity(intent);
                            break;
                    }
                } else if (v.getId() == R.id.order_datails) {
                    //订单详情
                    Intent intent = new Intent(activity, OrderInfoActivity.class);
                    intent.putExtra("goods_id", orderList.order_id);
                    activity.startActivity(intent);
                } else if (v.getId() == R.id.order_pay) {
                    //订单支付
                    goodsPay(orderList.pay_sn);
                } else if (v.getId() == R.id.order_logistics) {
                    //查看物流
                    Intent intentwl = new Intent(activity, WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", "http://aitecc.com/wap/index.php?act=member_order&op=deliver_index&order_id=" + orderList.order_id
                            + "&key=" + State.UserKey);
                    bundle.putString("title", activity.getString(R.string.view_logistics));
                    intentwl.putExtras(bundle);
                    activity.startActivity(intentwl);
                }

//				switch (v.getId()) {
//				case R.id.affirm_tx:           //同一按钮对不同订单状态可执行不同操作
//					switch (what) {		//来自哪个订单状态界面的点击
//					case 0: 				//全部订单
//						if (state == 10) {
//							Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
//						} else if (state == 20) {
//						} else if (state == 30) {
//							//确认收货
//							netRun.orderReceive(orderList.order_id);
//						} else if (state == 40) {
//
//						} else {
//						}
//						break;
//					case 2:			//待发货
//						//物流
////						netRun.searchSeliver(orderList.order_id);
//						break;
//					case 3:			//待收货
////						showpopw(orderList,holder.affirm_tx);
//						//收货
//						netRun.orderReceive(orderList.order_id);
//						break;
//					case 4:			//交易完成
//						//交易完成 -> 订单退款
//						Intent intenttk2 = new Intent(activity, OrderRefundActivity.class);
//						intenttk2.putExtra("order_id", orderList.order_id);
//						activity.startActivity(intenttk2);
//						break;
//					}
//					break;
//				case R.id.detele_bt:		//同一按钮对不同订单状态可执行不同操作
//					switch (what) {
//					case 0:
//						if (state == 10) {
//							Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
//						} else if (state == 20) {
//							//退款
//							Intent intent = new Intent(activity, OrderRefundActivity.class);
//							intent.putExtra("order_id", orderList.order_id);
//							activity.startActivity(intent);
////							Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
//						} else if (state == 30) {
//						} else if (state == 40) {
//							//评价
//							Intent intent = new Intent();
//							intent.setClass(activity, GoodsSevaluationActivity.class);
//							intent.putExtra("order_sn", orderList.order_sn);
//							activity.startActivity(intent);
//						} else {
//						}
//						break;
//					case 1:
//						Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
//						break;
//					case 2:
//						System.out.println("------------------待发货退款");
//						Intent intenttk = new Intent(activity, OrderRefundActivity.class);
//						intenttk.putExtra("order_id", orderList.order_id);
//						activity.startActivity(intenttk);
////						Dialog(APPSingleton.getContext().getString(R.string.sure_cancel_order).toString(), v, what, orderList);
//						break;
//					case 3:
//						//待收货->退款
//						Intent intenttk2 = new Intent(activity, OrderRefundActivity.class);
//						intenttk2.putExtra("order_id", orderList.order_id);
//						activity.startActivity(intenttk2);
//						break;
//					case 4:
//						Intent intent = new Intent();
//						intent.setClass(activity, GoodsSevaluationActivity.class);
//						intent.putExtra("order_sn", orderList.order_sn);
//						activity.startActivity(intent);
//						break;
//					}
//					break;
//				case R.id.order_datails:
//					//订单详情
//					Intent intent = new Intent(activity, OrderInfoActivity.class);
//					intent.putExtra("goods_id", orderList.order_id);
//					activity.startActivity(intent);
//					break;
//				case R.id.order_pay:
//					//订单支付
//					goodsPay(orderList.pay_sn);
//					break;
//				case R.id.order_logistics:
//					//查看物流
//					Intent intentwl = new Intent(activity, WebActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("url", "http://aitecc.com/wap/index.php?act=member_order&op=deliver_index&order_id="+orderList.order_id
//							+"&key="+State.UserKey);
//					bundle.putString("title", activity.getString(R.string.view_logistics));
//					intentwl.putExtras(bundle);
//					activity.startActivity(intentwl);
//					break;
//				}
            }

        };
        return listener;
    }


    private void goodsPay(String pay_sn) {
        Intent intent = new Intent(activity, PayListActivity.class);
        intent.putExtra("goods", activity.getString(R.string.app_name));
        intent.putExtra("describe",
                APPSingleton.getContext().getString(R.string.no).toString());
        intent.putExtra("pay_sn", pay_sn);
        activity.startActivity(intent);
    }

    private void showpopw(final OrderList list, View v) {
        Mypopu mypopu = new Mypopu(activity);
        mypopu.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        mypopu.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                String pay2 = lingshi.getInstance().getPay();
                if (pay2 != null && !pay2.equals("")) {
                    netRun.orderReceive(list.order_id);
                }
                lingshi.getInstance().setPay("");
                android.view.WindowManager.LayoutParams lp = activity
                        .getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);

            }
        });
    }

    private void refresh(int i) {
        if (i == 1) {
            activity.finish();
            Intent intent = new Intent(activity, BuyerOrderFgActivity.class);
            intent.putExtra("viewPager", what);
            activity.startActivity(intent);
        }
    }

    public void Dialog(String string, final View v, final int what, final OrderList orderList) {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
        builder2.setTitle(APPSingleton.getContext().getString(R.string.tip).toString());// 设置对话框标题
        builder2.setItems(new String[]{string}, null);
        builder2.setNegativeButton(APPSingleton.getContext().getString(R.string.cancel).toString(), null);
        builder2.setPositiveButton(APPSingleton.getContext().getString(R.string.confirm).toString(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (v.getId() == R.id.detele_bt) {
                    switch (what) {
                        case 0:
                            //取消订单
                            netRun.deteleOrder(orderList.order_id);
                            break;
                        case 1:
                            //取消订单
                            netRun.deteleOrder(orderList.order_id);
                            break;
                        case 2:
                            //取消订单
                            netRun.deteleOrder(orderList.order_id);
                            break;
                    }
                }else if(v.getId()==R.id.affirm_tx){
                    switch (what) {
                            case 4:
                                break;
                        }
                }

//                switch (v.getId()) {
//                    case R.id.affirm_tx:
//                        switch (what) {
//                            case 4:
//                                break;
//                        }
//                        break;
//                    case R.id.detele_bt:
//                        switch (what) {
//                            case 0:
//                                //取消订单
//                                netRun.deteleOrder(orderList.order_id);
//                                break;
//                            case 1:
//                                //取消订单
//                                netRun.deteleOrder(orderList.order_id);
//                                break;
//                            case 2:
//                                //取消订单
//                                netRun.deteleOrder(orderList.order_id);
//                                break;
//                            case 3:
//
//                                break;
//                            case 4:
//
//                                break;
//                        }
//                        break;
//                }
            }
        });
        builder2.show();
    }

    private static class ViewHolder {
        public Button affirm_tx;
        public TextView state_desc;
        public Button detele_bt, order_pay;
        public TextView amount;
        public TextView shippingFeeTv;
        public TextView goods_price;
        public TextView order_store, tv_refund, order_datails, order_logistics;
        public MyListView listView;

        public ViewHolder(View v) {
            super();
            order_store = (TextView) v.findViewById(R.id.order_store);
            goods_price = (TextView) v.findViewById(R.id.goods_price);
            tv_refund = (TextView) v.findViewById(R.id.tv_refund);
            order_logistics = (TextView) v.findViewById(R.id.order_logistics);
            order_datails = (TextView) v.findViewById(R.id.order_datails);
            amount = (TextView) v.findViewById(R.id.amount);
            detele_bt = (Button) v.findViewById(R.id.detele_bt);
            affirm_tx = (Button) v.findViewById(R.id.affirm_tx);
            order_pay = (Button) v.findViewById(R.id.order_pay);
            listView = (MyListView) v.findViewById(R.id.order_item_lv);
            state_desc = (TextView) v.findViewById(R.id.state_desc);
            shippingFeeTv = (TextView) v.findViewById(R.id.goods_shipping_fee);
        }
    }
}
