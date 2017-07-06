package de.cooperateproject.cdo.dawn.rest.accessible.dto;

import java.util.ArrayList;
import java.util.Optional;

public interface IDiagramExchangeObject {

	/**
	   * Appends a child to the list of children.
	   *
	   * @param child
	   *          The child to append to the list of children.
	   */
	void appendChild(IDiagramExchangeObject child);

	/**
	   * Returns the value of the object.
	   *
	   * @return Value of the current object.
	   */
	String getValue();

	/**
	   * Return the children of the current object.
	   *
	   * @return The children of the current object.
	   */
	ArrayList<IDiagramExchangeObject> getChildren();

	/**
	   * Searches for a child with the given value.
	   *
	   * @param value
	   *          The value of the child to look for.
	   * @return The specified child or returns <code>null</code> if there is no such child.
	   */
	Optional<IDiagramExchangeObject> getChildByValue(String value);

	/**
	   * Searches for a child with the given id.
	   *
	   * @param id
	   *          The id of the child to look for.
	   * @return The specified child or returns <code>null</code> if there is no such child.
	   */
	IDiagramExchangeObject getChildById(String id);

	/**
	   * Return the id of the object.
	   *
	   * @return Id of the object
	   */
	String getId();

	/**
	   * Returns whether this object is removable.
	   *
	   * @return <code>true</code> if removable, <code>false</code> if not.
	   */
	boolean getRemovable();

	/**
	   * Returns whether this object is mutable.
	   *
	   * @return <code>true</code> if mutable, <code>false</code> if not.
	   */
	boolean getMutable();

	/**
	   * Returns the x position of the object.
	   *
	   * @return The x-coordinate of the object.
	   */
	int getX();

	/**
	   * Returns the y position of the object.
	   *
	   * @return The y-coordinate of the object.
	   */
	int getY();

	/**
	   * Sets the value of this object.
	   *
	   * @param value
	   *          The target value for this object.
	   */
	void setValue(String value);

	/**
	   * Sets the mutability of this object.
	   *
	   * @param b
	   *          Boolean determining whether this object is mutable.
	   */
	void setMutable(boolean b);

	/**
	   * Sets the removability of this object.
	   *
	   * @param b
	   *          Boolean determining whether this object is removable.
	   */
	void setRemovable(boolean b);

	/**
	   * Sets the x coordinate.
	   *
	   * @param x
	   *          The target x coordinate.
	   */
	void setX(int x);

	/**
	   * Sets the y coordinate.
	   *
	   * @param y
	   *          The target y coordinate.
	   */
	void setY(int y);

	/**
	   * Returns whether the current object is parent to a group of objects.
	   *
	   * @return <code>true</code> if this objects is parent to a group, <code>false</code> otherwise
	   */
	boolean isGroup();

	/**
	   * Returns whether the current object is a reference.
	   *
	   * @return <code>true</code> if this objects is a reference, <code>false</code> otherwise
	   */
	boolean isReference();

	/**
	   * Returns the referenced object.
	   *
	   * @return The referenced object
	   */
	IDiagramExchangeObject getReferencedObject();

}