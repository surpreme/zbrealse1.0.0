package com.aite.a.model;

import java.io.Serializable;

/**
 * 子分类
 * 
 * @author xiaoyu
 *
 */
public class MainSortChildModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String goodsId;
	private String childName;

	
	@Override
	public String toString() {
		return "SortChildModel [goodsId=" + goodsId + ", childName="
				+ childName + "]";
	}

	public MainSortChildModel() {
		super();
	}

	public MainSortChildModel(String goodsId, String childName) {
		super();
		this.goodsId = goodsId;
		this.childName = childName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

}
