package de.cooperateproject.cdo.dawn.dto;

import java.util.ArrayList;
import java.util.List;

public class Project {
	
	public Project() {
		models = new ArrayList<Model>();
	}

	private String name;
	List<Model> models;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addModel(Model model) {
		this.models.add(model);
	}
	
	public List<Model> getModels() {
		return models;
	}
	

}
