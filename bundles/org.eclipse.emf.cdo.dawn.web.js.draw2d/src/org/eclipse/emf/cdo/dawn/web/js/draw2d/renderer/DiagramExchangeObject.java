package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The DiagramExchangeObject is used to transfer syntax hiarchies from the renderer to the Xtend template.
 *
 * @author Shengjia Feng
 */
public class DiagramExchangeObject
{
  private ArrayList<DiagramExchangeObject> children;

  private DiagramExchangeObject referencedObject = null;

  private String value;

  /**
   * If there is a corresponding CDO object, its ID should be passed to the constructor. Otherwise, a generic uuid will
   * be generated.
   */
  private String id;

  private boolean removable = false;

  private boolean mutable = false;

  private int x = 0;

  private int y = 0;

  /**
   * Constructs an empty object.
   */
  public DiagramExchangeObject(String id)
  {
    children = new ArrayList<DiagramExchangeObject>();
    value = null;
    if (id == null)
    {
      id = UUID.randomUUID().toString();
    }
    this.id = id;
  }

  /**
   * Constructs a new object with the given value.
   *
   * @param value
   *          The value of the object to construct.
   */
  public DiagramExchangeObject(String id, String value)
  {
    children = new ArrayList<DiagramExchangeObject>();
    this.value = value;
    if (id == null)
    {
      id = UUID.randomUUID().toString();
    }
    this.id = id;
  }

  /**
   * Constructs a new object with a given value that is appended to the given parent object.
   *
   * @param parent
   *          Parent object to append to.
   * @param value
   *          Value of the constructed object.
   */
  public DiagramExchangeObject(String id, DiagramExchangeObject parent, String value)
  {
    children = new ArrayList<DiagramExchangeObject>();
    this.value = value;
    parent.appendChild(this);
    if (id == null)
    {
      id = UUID.randomUUID().toString();
    }
    this.id = id;
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
  public DiagramExchangeObject(String id, DiagramExchangeObject parent, String value,
      DiagramExchangeObject crossReference)
  {
    children = new ArrayList<DiagramExchangeObject>();
    referencedObject = crossReference;
    this.value = value;
    parent.appendChild(this);
    if (id == null)
    {
      id = UUID.randomUUID().toString();
    }
    this.id = id;
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
   * Searches for a class with the given name.
   *
   * @param name
   *          The name of the class to search for.
   * @return The DiagramExchangeObject with the specified class or <code>null</code> if there is no such object found.
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
   * Searches for a child with the given name.
   *
   * @param name
   *          The name of the child to look for.
   * @return The specified child or returns <code>null</code> if there is no such child.
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
   * Searches for a child with the given id.
   *
   * @param name
   *          The name of the child to look for.
   * @return The specified child or returns <code>null</code> if there is no such child.
   */
  public DiagramExchangeObject getChildById(String id)
  {
    for (DiagramExchangeObject deo : children)
    {
      if (deo.getId().equals(id))
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
   * Returns whether this object is removable.
   *
   * @return <code>true</code> if removable, <code>false</code> if not.
   */
  public boolean getRemovable()
  {
    return removable;
  }

  /**
   * Returns whether this object is mutable.
   *
   * @return <code>true</code> if mutable, <code>false</code> if not.
   */
  public boolean getMutable()
  {
    return mutable;
  }

  /**
   * Returns the x position of the object.
   *
   * @return The x-coordinate of the object.
   */
  public int getX()
  {
    return x;
  }

  /**
   * Returns the y position of the object.
   *
   * @return The y-coordinate of the object.
   */
  public int getY()
  {
    return y;
  }

  /**
   * Sets the ID of this object.
   *
   * @param id
   *          The target ID for this object.
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * Sets the value of this object.
   *
   * @param value
   *          The target value for this object.
   */
  public void setValue(String value)
  {
    this.value = value;
  }

  /**
   * Sets the mutability of this object.
   *
   * @param b
   *          Boolean determining whether this object is mutable.
   */
  public void setMutable(boolean b)
  {
    mutable = b;
  }

  /**
   * Sets the removability of this object.
   *
   * @param b
   *          Boolean determining whether this object is removable.
   */
  public void setRemovable(boolean b)
  {
    removable = b;
  }

  /**
   * Sets the x coordinate.
   *
   * @param x
   *          The target x coordinate.
   */
  public void setX(int x)
  {
    this.x = x;
  }

  /**
   * Sets the y coordinate.
   *
   * @param y
   *          The target y coordinate.
   */
  public void setY(int y)
  {
    this.y = y;
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

  /**
   * Returns whether the current object is a reference.
   *
   * @return <code>true</code> if this objects is a reference, <code>false</code> otherwise
   */
  public boolean isReference()
  {
    return referencedObject != null;
  }

  /**
   * Returns the referenced object.
   *
   * @return The referenced object
   */
  public DiagramExchangeObject getReferencedObject()
  {
    return referencedObject;
  }
}
