package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Shengjia Feng
 */
public class DiagramExchangeObject
{
  private ArrayList<DiagramExchangeObject> children;

  private DiagramExchangeObject referencedObject = null;

  private String value;

  private String id = UUID.randomUUID().toString();

  /**
   * Constructs an empty object.
   */
  public DiagramExchangeObject()
  {
    children = new ArrayList<DiagramExchangeObject>();
    value = null;
  }

  /**
   * Constructs a new object with the given value.
   *
   * @param value
   *          The value of the object to construct.
   */
  public DiagramExchangeObject(String value)
  {
    children = new ArrayList<DiagramExchangeObject>();
    this.value = value;
  }

  /**
   * Constructs a new object with a given value that is appended to the given parent object.
   *
   * @param parent
   *          Parent object to append to.
   * @param value
   *          Value of the constructed object.
   */
  public DiagramExchangeObject(DiagramExchangeObject parent, String value)
  {
    children = new ArrayList<DiagramExchangeObject>();
    this.value = value;
    parent.appendChild(this);
  }

  /**
   * Constructs a new element that references another DiagramExchangeObject object.
   *
   * @param parent
   *          Parent object to append to.
   * @param value
   *          The value of the constructed object.
   * @param crossReference
   *          Object to reference to.
   */
  public DiagramExchangeObject(DiagramExchangeObject parent, String value, DiagramExchangeObject crossReference)
  {
    children = new ArrayList<DiagramExchangeObject>();
    referencedObject = crossReference;
    this.value = value;
    parent.appendChild(this);
  }

  /**
   * Appends a child to the list of children.
   *
   * @param child
   *          The child to append to the list of children.
   */
  public void appendChild(DiagramExchangeObject child)
  {
    children.add(child);
  }

  /**
   * Returns the value of the object.
   *
   * @return Value of the current object.
   */
  public String getValue()
  {
    return value;
  }

  /**
   * Return the children of the current object.
   *
   * @return The children of the current object.
   */
  public ArrayList<DiagramExchangeObject> getChildren()
  {
    return children;
  }

  /**
   * Searches for a class with the given name. Returns <code>null</code> if there is no such class.
   *
   * @param name
   *          The name of the class to search for.
   */
  public DiagramExchangeObject getClassByName(String name)
  {
    if (getChildByName("classes") != null)
    {
      return getChildByName(name);
    }

    return null;
  }

  /**
   * Searches for a child with the given name. Returns <code>null</code> if there is no such child.
   *
   * @param name
   *          The name of the child to look for.
   */
  public DiagramExchangeObject getChildByName(String name)
  {
    for (DiagramExchangeObject deo : children)
    {
      if (deo.getValue().equals(name))
      {
        return deo;
      }
    }

    return null;
  }

  /**
   * Return the id of the object.
   *
   * @return Id of the object
   */
  public String getId()
  {
    return id;
  }

  /**
   * Returns whether the current object is parent to a group of objects.
   *
   * @return <code>true</code> if this objects is parent to a group, <code>false</code> otherwise
   */
  public boolean isGroup()
  {
    return children.size() > 0;
  }
}
