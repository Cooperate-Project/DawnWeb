package de.cooperateproject.cdo.dawn.rest.accessible.dto;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * The DiagramExchangeObject is used to transfer syntax hiarchies from the renderer to the Xtend template.
 *
 * @author Shengjia Feng
 */
public class DiagramExchangeObject implements IDiagramExchangeObject
{
  private ArrayList<IDiagramExchangeObject> children;

  private IDiagramExchangeObject referencedObject = null;

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
   * The most generic constructor constructing an empty DEO.
   */
  public DiagramExchangeObject()
  {
    children = new ArrayList<IDiagramExchangeObject>();
    value = null;
    id = UUID.randomUUID().toString();
  }

  /**
   * Constructs an empty object with an id.
   */
  public DiagramExchangeObject(String id)
  {
    this();
    if (id != null)
    {
      this.id = id;
    }
  }

  /**
   * Constructs a new object with the given value.
   *
   * @param value
   *          The value of the object to construct.
   */
  public DiagramExchangeObject(String id, String value)
  {
    this(id);
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
  public DiagramExchangeObject(String id, IDiagramExchangeObject parent, String value)
  {
    this(id, value);
    parent.appendChild(this);
  }

  /**
   * Constructs a new element that references another DiagramExchangeObject object.
   *
   * @param parent
   *          Parent object to append to.
   * @param value
   *          The value of the constructed object.
   * @param reference
   *          Object to reference to.
   */
  public DiagramExchangeObject(String id, IDiagramExchangeObject parent, String value, IDiagramExchangeObject reference)
  {
    this(id, parent, value);
    referencedObject = new DiagramExchangeObjectProxy(reference.getId(), reference.getValue());
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#appendChild(de.cooperateproject.cdo.dawn.rest.accessible.dto.DiagramExchangeObject)
 */
  @Override
  public void appendChild(IDiagramExchangeObject child) 
  {
    children.add(child);
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getValue()
 */
  @Override
public String getValue()
  {
    return value;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getChildren()
 */
  @Override
public ArrayList<IDiagramExchangeObject> getChildren()
  {
    return children;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getChildByValue(java.lang.String)
 */
  @Override
public Optional<IDiagramExchangeObject> getChildByValue(String value)
  {
    for (IDiagramExchangeObject deo : children)
    {
      if (deo.getValue().equals(value))
      {
        return Optional.of(deo);
      }
    }

    return Optional.empty();
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getChildById(java.lang.String)
 */
  @Override
public IDiagramExchangeObject getChildById(String id)
  {
    for (IDiagramExchangeObject deo : children)
    {
      if (deo.getId().equals(id))
      {
        return deo;
      }
    }

    return null;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getId()
 */
  @Override
public String getId()
  {
    return id;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getRemovable()
 */
  @Override
public boolean getRemovable()
  {
    return removable;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getMutable()
 */
  @Override
public boolean getMutable()
  {
    return mutable;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getX()
 */
  @Override
public int getX()
  {
    return x;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getY()
 */
  @Override
public int getY()
  {
    return y;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#setValue(java.lang.String)
 */
  @Override
public void setValue(String value)
  {
    this.value = value;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#setMutable(boolean)
 */
  @Override
public void setMutable(boolean b)
  {
    mutable = b;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#setRemovable(boolean)
 */
  @Override
public void setRemovable(boolean b)
  {
    removable = b;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#setX(int)
 */
  @Override
public void setX(int x)
  {
    this.x = x;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#setY(int)
 */
  @Override
public void setY(int y)
  {
    this.y = y;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#isGroup()
 */
  @Override
public boolean isGroup()
  {
    return children.size() > 0;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#isReference()
 */
  @Override
public boolean isReference()
  {
    return referencedObject != null;
  }

  /* (non-Javadoc)
 * @see de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject#getReferencedObject()
 */
  @Override
public IDiagramExchangeObject getReferencedObject()
  {
    return referencedObject;
  }

}
