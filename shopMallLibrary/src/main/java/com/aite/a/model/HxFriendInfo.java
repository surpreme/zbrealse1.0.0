package com.aite.a.model;

/**
 * 环信好友信息
 * 
 * @author Administrator
 *
 */
public class HxFriendInfo {
	public String friendname;
	public String friendmessage;
	public String friendtitle;
	public String friendtime;
	public String friendunread;

	public HxFriendInfo(String friendname, String friendmessage,
			String friendtitle, String friendtime, String friendunread) {
		super();
		this.friendname = friendname;
		this.friendmessage = friendmessage;
		this.friendtitle = friendtitle;
		this.friendtime = friendtime;
		this.friendunread = friendunread;
	}

	@Override
	public String toString() {
		return "HxFriendInfo [friendname=" + friendname + ", friendmessage="
				+ friendmessage + ", friendtitle=" + friendtitle
				+ ", friendtime=" + friendtime + ", friendunread="
				+ friendunread + "]";
	}

}
