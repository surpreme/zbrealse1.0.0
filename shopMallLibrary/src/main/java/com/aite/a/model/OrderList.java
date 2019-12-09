package com.aite.a.model;

import java.util.List;

public class OrderList {
	List<GoodList> goodsList;
	String delay_time;
	String refund_state;
	String order_state;
	String shipping_code;
	String if_lock;
	String pay_sn;
	String if_deliver;
	String order_id;
	String payment_time;
	String payment_code;
	String state_desc;
	String goods_amount;
	String order_amount;
	String if_cancel;
	String evaluation_state;
	String buyer_email;
	String order_from;
	String refund_amount;
	String store_name;
	String buyer_id;
	String store_id;
	String buyer_name;
	String lock_state;
	String shipping_fee;
	String pd_amount;
	String payment_name;
	String if_receive;
	String order_sn;
	String add_time;
	String finnshed_time;
	String delete_state;
	String rcb_amount;

	public OrderList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderList(List<GoodList> goodsList, String delay_time,
			String refund_state, String order_state, String shipping_code,
			String if_lock, String pay_sn, String if_deliver, String order_id,
			String payment_time, String payment_code, String state_desc,
			String goods_amount, String order_amount, String if_cancel,
			String evaluation_state, String buyer_email, String order_from,
			String refund_amount, String store_name, String buyer_id,
			String store_id, String buyer_name, String lock_state,
			String shipping_fee, String pd_amount, String payment_name,
			String if_receive, String order_sn, String add_time,
			String finnshed_time, String delete_state, String rcb_amount) {
		super();
		this.goodsList = goodsList;
		this.delay_time = delay_time;
		this.refund_state = refund_state;
		this.order_state = order_state;
		this.shipping_code = shipping_code;
		this.if_lock = if_lock;
		this.pay_sn = pay_sn;
		this.if_deliver = if_deliver;
		this.order_id = order_id;
		this.payment_time = payment_time;
		this.payment_code = payment_code;
		this.state_desc = state_desc;
		this.goods_amount = goods_amount;
		this.order_amount = order_amount;
		this.if_cancel = if_cancel;
		this.evaluation_state = evaluation_state;
		this.buyer_email = buyer_email;
		this.order_from = order_from;
		this.refund_amount = refund_amount;
		this.store_name = store_name;
		this.buyer_id = buyer_id;
		this.store_id = store_id;
		this.buyer_name = buyer_name;
		this.lock_state = lock_state;
		this.shipping_fee = shipping_fee;
		this.pd_amount = pd_amount;
		this.payment_name = payment_name;
		this.if_receive = if_receive;
		this.order_sn = order_sn;
		this.add_time = add_time;
		this.finnshed_time = finnshed_time;
		this.delete_state = delete_state;
		this.rcb_amount = rcb_amount;
	}

	@Override
	public String toString() {
		return "OrderList [goodsList=" + goodsList + ", delay_time="
				+ delay_time + ", refund_state=" + refund_state
				+ ", order_state=" + order_state + ", shipping_code="
				+ shipping_code + ", if_lock=" + if_lock + ", pay_sn=" + pay_sn
				+ ", if_deliver=" + if_deliver + ", order_id=" + order_id
				+ ", payment_time=" + payment_time + ", payment_code="
				+ payment_code + ", state_desc=" + state_desc
				+ ", goods_amount=" + goods_amount + ", order_amount="
				+ order_amount + ", if_cancel=" + if_cancel
				+ ", evaluation_state=" + evaluation_state + ", buyer_email="
				+ buyer_email + ", order_from=" + order_from
				+ ", refund_amount=" + refund_amount + ", store_name="
				+ store_name + ", buyer_id=" + buyer_id + ", store_id="
				+ store_id + ", buyer_name=" + buyer_name + ", lock_state="
				+ lock_state + ", shipping_fee=" + shipping_fee
				+ ", pd_amount=" + pd_amount + ", payment_name=" + payment_name
				+ ", if_receive=" + if_receive + ", order_sn=" + order_sn
				+ ", add_time=" + add_time + ", finnshed_time=" + finnshed_time
				+ ", delete_state=" + delete_state + ", rcb_amount="
				+ rcb_amount + "]";
	}

	public List<GoodList> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodList> goodsList) {
		this.goodsList = goodsList;
	}

	public String getDelay_time() {
		return delay_time;
	}

	public void setDelay_time(String delay_time) {
		this.delay_time = delay_time;
	}

	public String getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(String refund_state) {
		this.refund_state = refund_state;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public String getShipping_code() {
		return shipping_code;
	}

	public void setShipping_code(String shipping_code) {
		this.shipping_code = shipping_code;
	}

	public String getIf_lock() {
		return if_lock;
	}

	public void setIf_lock(String if_lock) {
		this.if_lock = if_lock;
	}

	public String getPay_sn() {
		return pay_sn;
	}

	public void setPay_sn(String pay_sn) {
		this.pay_sn = pay_sn;
	}

	public String getIf_deliver() {
		return if_deliver;
	}

	public void setIf_deliver(String if_deliver) {
		this.if_deliver = if_deliver;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPayment_time() {
		return payment_time;
	}

	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}

	public String getPayment_code() {
		return payment_code;
	}

	public void setPayment_code(String payment_code) {
		this.payment_code = payment_code;
	}

	public String getState_desc() {
		return state_desc;
	}

	public void setState_desc(String state_desc) {
		this.state_desc = state_desc;
	}

	public String getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(String goods_amount) {
		this.goods_amount = goods_amount;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getIf_cancel() {
		return if_cancel;
	}

	public void setIf_cancel(String if_cancel) {
		this.if_cancel = if_cancel;
	}

	public String getEvaluation_state() {
		return evaluation_state;
	}

	public void setEvaluation_state(String evaluation_state) {
		this.evaluation_state = evaluation_state;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getBuyer_name() {
		return buyer_name;
	}

	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}

	public String getLock_state() {
		return lock_state;
	}

	public void setLock_state(String lock_state) {
		this.lock_state = lock_state;
	}

	public String getShipping_fee() {
		return shipping_fee;
	}

	public void setShipping_fee(String shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public String getPd_amount() {
		return pd_amount;
	}

	public void setPd_amount(String pd_amount) {
		this.pd_amount = pd_amount;
	}

	public String getPayment_name() {
		return payment_name;
	}

	public void setPayment_name(String payment_name) {
		this.payment_name = payment_name;
	}

	public String getIf_receive() {
		return if_receive;
	}

	public void setIf_receive(String if_receive) {
		this.if_receive = if_receive;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getFinnshed_time() {
		return finnshed_time;
	}

	public void setFinnshed_time(String finnshed_time) {
		this.finnshed_time = finnshed_time;
	}

	public String getDelete_state() {
		return delete_state;
	}

	public void setDelete_state(String delete_state) {
		this.delete_state = delete_state;
	}

	public String getRcb_amount() {
		return rcb_amount;
	}

	public void setRcb_amount(String rcb_amount) {
		this.rcb_amount = rcb_amount;
	}

}
