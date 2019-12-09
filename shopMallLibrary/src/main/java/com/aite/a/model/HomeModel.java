package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

public class HomeModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> playImageUrls;
	private List<String> miaoshaImageUrls;
	private List<String> miaoshaIds;
	private List<String> miaoName;
	private List<String> miaoPrice;

	private List<String> womanImageUrls;
	private List<String> shoesImageUrls;
	private List<String> babyImageUrls;
	private List<String> undiesImageUrls;
	private List<String> bagImageUrls;

	private List<String> womanIds;
	private List<String> shoesIds;
	private List<String> babyIds;
	private List<String> bagIds;
	private List<String> undIds;

	@Override
	public String toString() {
		return "HomeModel [playImageUrls=" + playImageUrls
				+ ", miaoshaImageUrls=" + miaoshaImageUrls + ", miaoshaIds="
				+ miaoshaIds + ", miaoName=" + miaoName + ", miaoPrice="
				+ miaoPrice + ", womanImageUrls=" + womanImageUrls
				+ ", shoesImageUrls=" + shoesImageUrls + ", babyImageUrls="
				+ babyImageUrls + ", undiesImageUrls=" + undiesImageUrls
				+ ", bagImageUrls=" + bagImageUrls + ", womanIds=" + womanIds
				+ ", shoesIds=" + shoesIds + ", babyIds=" + babyIds
				+ ", bagIds=" + bagIds + ", undIds=" + undIds + "]";
	}

	public HomeModel() {
		super();
	}

	public HomeModel(List<String> playImageUrls, List<String> miaoshaImageUrls,
			List<String> miaoshaIds, List<String> miaoName,
			List<String> miaoPrice, List<String> womanImageUrls,
			List<String> shoesImageUrls, List<String> babyImageUrls,
			List<String> undiesImageUrls, List<String> bagImageUrls,
			List<String> womanIds, List<String> shoesIds, List<String> babyIds,
			List<String> bagIds, List<String> undIds) {
		super();
		this.playImageUrls = playImageUrls;
		this.miaoshaImageUrls = miaoshaImageUrls;
		this.miaoshaIds = miaoshaIds;
		this.miaoName = miaoName;
		this.miaoPrice = miaoPrice;
		this.womanImageUrls = womanImageUrls;
		this.shoesImageUrls = shoesImageUrls;
		this.babyImageUrls = babyImageUrls;
		this.undiesImageUrls = undiesImageUrls;
		this.bagImageUrls = bagImageUrls;
		this.womanIds = womanIds;
		this.shoesIds = shoesIds;
		this.babyIds = babyIds;
		this.bagIds = bagIds;
		this.undIds = undIds;
	}

	public List<String> getMiaoName() {
		return miaoName;
	}

	public void setMiaoName(List<String> miaoName) {
		this.miaoName = miaoName;
	}

	public List<String> getMiaoPrice() {
		return miaoPrice;
	}

	public void setMiaoPrice(List<String> miaoPrice) {
		this.miaoPrice = miaoPrice;
	}

	public List<String> getMiaoshaImageUrls() {
		return miaoshaImageUrls;
	}

	public void setMiaoshaImageUrls(List<String> miaoshaImageUrls) {
		this.miaoshaImageUrls = miaoshaImageUrls;
	}

	public List<String> getMiaoshaIds() {
		return miaoshaIds;
	}

	public void setMiaoshaIds(List<String> miaoshaIds) {
		this.miaoshaIds = miaoshaIds;
	}

	public List<String> getWomanIds() {
		return womanIds;
	}

	public void setWomanIds(List<String> womanIds) {
		this.womanIds = womanIds;
	}

	public List<String> getShoesIds() {
		return shoesIds;
	}

	public void setShoesIds(List<String> shoesIds) {
		this.shoesIds = shoesIds;
	}

	public List<String> getBabyIds() {
		return babyIds;
	}

	public void setBabyIds(List<String> babyIds) {
		this.babyIds = babyIds;
	}

	public List<String> getBagIds() {
		return bagIds;
	}

	public void setBagIds(List<String> bagIds) {
		this.bagIds = bagIds;
	}

	public List<String> getUndIds() {
		return undIds;
	}

	public void setUndIds(List<String> undIds) {
		this.undIds = undIds;
	}

	public List<String> getWomanImageUrls() {
		return womanImageUrls;
	}

	public void setWomanImageUrls(List<String> womanImageUrls) {
		this.womanImageUrls = womanImageUrls;
	}

	public List<String> getShoesImageUrls() {
		return shoesImageUrls;
	}

	public void setShoesImageUrls(List<String> shoesImageUrls) {
		this.shoesImageUrls = shoesImageUrls;
	}

	public List<String> getBabyImageUrls() {
		return babyImageUrls;
	}

	public void setBabyImageUrls(List<String> babyImageUrls) {
		this.babyImageUrls = babyImageUrls;
	}

	public List<String> getUndiesImageUrls() {
		return undiesImageUrls;
	}

	public void setUndiesImageUrls(List<String> undiesImageUrls) {
		this.undiesImageUrls = undiesImageUrls;
	}

	public List<String> getBagImageUrls() {
		return bagImageUrls;
	}

	public void setBagImageUrls(List<String> bagImageUrls) {
		this.bagImageUrls = bagImageUrls;
	}

	public List<String> getPlayImageUrls() {
		return playImageUrls;
	}

	public void setPlayImageUrls(List<String> playImageUrls) {
		this.playImageUrls = playImageUrls;
	}

}
