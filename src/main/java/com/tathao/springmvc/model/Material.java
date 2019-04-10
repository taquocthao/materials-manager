package com.tathao.springmvc.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Material {
	
	private String id;
	@NotEmpty
	@Size(min=1, max=30)
	private String name;
	@NotEmpty
	@Size(min=1, max=15)
	private String unit;
	
	public Material() {
		
	}
	
	public Material(String id, String name, String unit) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
