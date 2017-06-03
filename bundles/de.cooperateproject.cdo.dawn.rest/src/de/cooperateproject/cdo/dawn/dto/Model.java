package de.cooperateproject.cdo.dawn.dto;

import java.util.List;

public class Model {

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	List<Diagram> diagrams;

}
