package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单实体类
 * 
 * @author CAOYOU
 *
 */
public class OrderGroupList implements Serializable {
	private static final long serialVersionUID = 2340819876556388488L;

	public String pay_amount;
	public String pay_sn;
	public String add_time;
	public String order_sn;
	public List<OrderList> order_list;

	public static class OrderList {
		public String delay_time;
		public String refund_state;
		public String order_state;
		public String shipping_code;
		public String if_lock;
		public String pay_sn;
		public String if_deliver;
		public String order_id;
		public String payment_time;
		public String payment_code;
		public String state_desc;
		public String goods_amount;
		public String order_amount;
		public String if_cancel;
		public String evaluation_state;
		public String buyer_email;
		public String order_from;
		public String refund_amount;
		public String store_name;
		public String buyer_id;
		public String store_id;
		public String buyer_name;
		public String lock_state;
		public String shipping_fee;
		public String pd_amount;
		public String payment_name;
		public String if_receive;
		public String if_complain;
		public String order_sn;
		public String add_time;
		public String finnshed_time;
		public String delete_state;
		public String rcb_amount;
		public String if_evaluate;
		public String if_evaluation;
		public String if_refund_cancel;
		public List<ExtendOrderGoods> extend_order_goods;

		public static class ExtendOrderGoods  implements Serializable{
			public String buyer_id;
			public String goods_pay_price;
			public String refund;
			public String gc_id;
			public String promotions_id;
			public String commis_rate;
			public String goods_num;
			public String goods_image_url;
			public String store_id;
			public String goods_name;
			public String goods_image;
			public String order_id;
			public String goods_price;
			public String goods_id;
			public String goods_type;
			public String rec_id;

			@Override
			public String toString() {
				return "ExtendOrderGoods [buyer_id=" + buyer_id
						+ ", goods_pay_price=" + goods_pay_price + ", gc_id="
						+ gc_id + ", promotions_id=" + promotions_id
						+ ", commis_rate=" + commis_rate + ", goods_num="
						+ goods_num + ", goods_image_url=" + goods_image_url
						+ ", store_id=" + store_id + ", goods_name="
						+ goods_name + ", goods_image=" + goods_image
						+ ", order_id=" + order_id + ", goods_price="
						+ goods_price + ", goods_id=" + goods_id
						+ ", goods_type=" + goods_type + ", rec_id=" + rec_id
						+ "]";
			}
		}

		@Override
		public String toString() {
			return "OrderList [delay_time=" + delay_time + ", refund_state="
					+ refund_state + ", order_state=" + order_state
					+ ", shipping_code=" + shipping_code + ", if_lock="
					+ if_lock + ", pay_sn=" + pay_sn + ", if_deliver="
					+ if_deliver + ", order_id=" + order_id + ", payment_time="
					+ payment_time + ", payment_code=" + payment_code
					+ ", state_desc=" + state_desc + ", goods_amount="
					+ goods_amount + ", order_amount=" + order_amount
					+ ", if_cancel=" + if_cancel + ", evaluation_state="
					+ evaluation_state + ", buyer_email=" + buyer_email
					+ ", order_from=" + order_from + ", refund_amount="
					+ refund_amount + ", store_name=" + store_name
					+ ", buyer_id=" + buyer_id + ", store_id=" + store_id
					+ ", buyer_name=" + buyer_name + ", lock_state="
					+ lock_state + ", shipping_fee=" + shipping_fee
					+ ", pd_amount=" + pd_amount + ", payment_name="
					+ payment_name + ", if_receive=" + if_receive
					+ ", order_sn=" + order_sn + ", add_time=" + add_time
					+ ", finnshed_time=" + finnshed_time + ", delete_state="
					+ delete_state + ", rcb_amount=" + rcb_amount
					+ ", extend_order_goods=" + extend_order_goods + "]";
		}
	}

	@Override
	public String toString() {
		return "OrderGroupList [pay_amount=" + pay_amount + ", pay_sn="
				+ pay_sn + ", add_time=" + add_time + ", order_list="
				+ order_list + "]";
	}

	public OrderGroupList(String pay_amount, String pay_sn, String add_time,
			List<OrderList> order_list) {
		super();
		this.pay_amount = pay_amount;
		this.pay_sn = pay_sn;
		this.add_time = add_time;
		this.order_list = order_list;
	}

}
