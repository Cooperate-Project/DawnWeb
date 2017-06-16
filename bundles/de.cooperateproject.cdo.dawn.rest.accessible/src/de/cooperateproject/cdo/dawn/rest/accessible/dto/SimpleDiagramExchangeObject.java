package de.cooperateproject.cdo.dawn.rest.accessible.dto;

import java.util.UUID;

/**
 * This class does exist to provide a cycle free DiagramExchangeObject-hierarchy.
 * 
 * @author Sebastian Hahner
 *
 */
public class SimpleDiagramExchangeObject {

	// TODO: Create a new cycle free DTO-representation for the accessible
	// project. Not just a simple representations without references.

	private String value;

	private String id;

	/**
	 * The most generic constructor constructing an empty DEO.
	 */
	public SimpleDiagramExchangeObject() {
		value = null;
		id = UUID.randomUUID().toString();
	}

	/**
	 * Constructs an empty object with an id.
	 */
	public SimpleDiagramExchangeObject(String id) {
		this();
		if (id != null) {
			this.id = id;
		}
	}

	/**
	 * Constructs a new object with the given value.
	 *
	 * @param value
	 *            The value of the object to construct.
	 */
	public SimpleDiagramExchangeObject(String id, String value) {
		this(id);
		this.value = value;
	}

	/**
	 * Returns the value of the object.
	 *
	 * @return Value of the current object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Return the id of the object.
	 *
	 * @return Id of the object
	 */
	public String getId() {
		return id;
	}

}
