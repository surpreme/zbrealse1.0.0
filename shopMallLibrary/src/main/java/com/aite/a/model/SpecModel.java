package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

public class SpecModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private List<SpectItemModel> spectItemModels;
	
	public SpecModel(String name, List<SpectItemModel> spectItemModels) {
		super();
		this.name = name;
		this.spectItemModels = spectItemModels;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SpectItemModel> getSpectItemModels() {
		return spectItemModels;
	}
	public void setSpectItemModels(List<SpectItemModel> spectItemModels) {
		this.spectItemModels = spectItemModels;
	}
	@Override
	public String toString() {
		return "SpecModel [name=" + name + ", spectItemModels="
				+ spectItemModels + "]";
	}
	
}
