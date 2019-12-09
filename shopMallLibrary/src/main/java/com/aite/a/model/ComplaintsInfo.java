package com.aite.a.model;

import java.util.List;

/**
 * 交易投诉
 * @author Administrator
 *
 */
public class ComplaintsInfo {
	public List<subject_list>subject_list;
	public String goods_id;
	public static class subject_list{
		public String complain_subject_id;
		public String complain_subject_content;
		public String complain_subject_desc;
		public String complain_subject_state;
		public boolean isxz=false;
	}
}
