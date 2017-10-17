package de.cooperateproject.cdo.dawn.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * A projects represents a folder on the cdo server. It may contain several
 * models.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class Project {

	/**
	 * Creates a new project and initializes the model list.
	 */
	public Project() {
		models = new ArrayList<Model>();
	}

	private String name;
	private List<Model> models;

	/**
	 * Returns the (folder) name of the project.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the project.
	 * 
	 * @param name
	 *            the desired name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds a new model to the project model list.
	 * 
	 * @param model
	 *            a model DTO of a papyrus model located on the cdo server
	 */
	public void addModel(Model model) {
		this.models.add(model);
	}

	/**
	 * Returns the list of all models contained in the project.
	 * 
	 * @return a list of model DTOs
	 */
	public List<Model> getModels() {
		return models;
	}

}
