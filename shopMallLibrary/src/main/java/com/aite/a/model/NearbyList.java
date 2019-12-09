package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

public class NearbyList {

	public String distance;
	public List<NearbyStoreList> nearbyStoreLists;

	public static class NearbyStoreList implements Serializable {
		private static final long serialVersionUID = -6217724224311725306L;
		public String store_id;
		public String store_name;
		public String area_info;
		public String store_address;
		public String store_points;
		public String store_label;
		public String store_zy;
		public String distance;

		@Override
		public String toString() {
			return "NearbyStoreList [store_id=" + store_id + ", store_name="
					+ store_name + ", area_info=" + area_info
					+ ", store_address=" + store_address + ", store_points="
					+ store_points + ", store_label=" + store_label
					+ ", store_zy=" + store_zy + ", distance=" + distance + "]";
		}

	}

	@Override
	public String toString() {
		return "NearbyList [distance=" + distance + ", nearbyStoreLists="
				+ nearbyStoreLists + "]";
	}

	public NearbyList(String distance, List<NearbyStoreList> nearbyStoreLists) {
		super();
		this.distance = distance;
		this.nearbyStoreLists = nearbyStoreLists;
	}

}
