package de.cooperateproject.cdo.dawn.dto;

/**
 * A model represents a papyrus model on the connected cdo server.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class Model {

	/**
	 * The file extension of papyrus model files.
	 */
	public static final String MODEL_FILE_EXTENSION = "notation";

	private String name;

	/**
	 * Returns the (file) name of the model.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the model.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
