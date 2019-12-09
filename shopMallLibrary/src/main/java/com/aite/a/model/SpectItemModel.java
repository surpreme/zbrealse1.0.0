package com.aite.a.model;

import java.io.Serializable;

public class SpectItemModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private String label;
	private String id;
	
	public SpectItemModel(String label, String id) {
		super();
		this.label = label;
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "SpectItemModel [label=" + label + ", id=" + id + "]";
	}
	
	
}
