package com.aite.a.model;

import java.util.List;

/**
 * 地址
 * 
 * @author Administrator
 *
 */
public class HotelChooseAddressInfo {
	public String name;
	public List<citylist> citylist;
	public static class citylist {
		public String area_id;
		public String area_name;
		public String area_parent_id;
		public String a_letter;
		public citylist() {
		}
		public citylist(String area_id, String area_name,
				String area_parent_id, String a_letter) {
			super();
			this.area_id = area_id;
			this.area_name = area_name;
			this.area_parent_id = area_parent_id;
			this.a_letter = a_letter;
		}
		
	}

}
