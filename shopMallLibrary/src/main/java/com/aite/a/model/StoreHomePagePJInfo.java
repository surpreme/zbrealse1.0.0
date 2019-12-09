package com.aite.a.model;

import java.util.List;

/**
 * 评价
 * 
 * @author Administrator
 *
 */
public class StoreHomePagePJInfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<list> list;

		public static class list {
			public String seval_id;
			public String seval_taskid;
			public String seval_tasksn;
			public String seval_addtime;
			public String seval_memberid;
			public String seval_membername;
			public String seval_storeid;
			public String seval_storename;
			public String seval_desccredit;
			public String seval_servicecredit;
			public String seval_deliverycredit;
			public String seval_content;
			public String employers_desc;
			public String employers_attitude;
			public String employers_pay;
			public String employers_evacon;
			public String service_evaltime;
			public String service_is_eval;
			public String employers_avg;
			public String service_avg;
		}
	}

}
