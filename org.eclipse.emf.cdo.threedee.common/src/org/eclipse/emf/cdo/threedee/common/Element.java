/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.threedee.common;

import org.eclipse.emf.cdo.threedee.common.ElementEvent.Change;

import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.container.Container;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public final class Element extends Container<Element>
{
  public static final String ID_ATTRIBUTE = "id";

  public static final String KEY_ATTRIBUTE = "key";

  public static final String NAME_ATTRIBUTE = "name";

  public static final String LABEL_ATTRIBUTE = "label";

  private int id;

  private ElementDescriptor descriptor;

  private ElementProvider provider;

  private Map<String, String> attributes = new HashMap<String, String>();

  private Map<Integer, Boolean> references = new HashMap<Integer, Boolean>();

  public Element(int id, ElementDescriptor descriptor, ElementProvider provider)
  {
    this.id = id;
    this.descriptor = descriptor;
    this.provider = provider;
    activate();
  }

  public Element(ExtendedDataInputStream in, ElementProvider provider) throws IOException
  {
    this.provider = provider;
    id = in.readInt();
    String descriptorName = in.readString();
    descriptor = ElementDescriptor.Registry.INSTANCE.get(descriptorName);
    if (descriptor == null)
    {
      throw new IllegalStateException("Descriptor missing: " + descriptorName);
    }

    int size = in.readInt();
    for (int i = 0; i < size; i++)
    {
      String key = in.readString();
      String value = in.readString();
      attributes.put(key, value);
    }

    size = in.readInt();
    for (int i = 0; i < size; i++)
    {
      int target = in.readInt();
      references.put(Math.abs(target), target < 0 ? Boolean.TRUE : Boolean.FALSE);
    }

    activate();
  }

  public void write(ExtendedDataOutputStream out) throws IOException
  {
    out.writeInt(id);
    out.writeString(descriptor.getName());

    out.writeInt(attributes.size());
    for (Entry<String, String> entry : attributes.entrySet())
    {
      out.writeString(entry.getKey());
      out.writeString(entry.getValue());
    }

    out.writeInt(references.size());
    for (Entry<Integer, Boolean> entry : references.entrySet())
    {
      int target = entry.getKey();
      if (Boolean.TRUE.equals(entry.getValue()))
      {
        target = -target;
      }

      out.writeInt(target);
    }
  }

  public int getID()
  {
    return id;
  }

  public ElementDescriptor getDescriptor()
  {
    return descriptor;
  }

  public ElementProvider getProvider()
  {
    return provider;
  }

  public Map<String, String> getAttributes()
  {
    return attributes;
  }

  public Map<Integer, Boolean> getReferences()
  {
    return references;
  }

  public void setAttribute(String key, String value)
  {
    if (key != null && value != null)
    {
      attributes.put(key, value);
    }
  }

  public void setIDAttribute(String value)
  {
    setAttribute(ID_ATTRIBUTE, value);
  }

  public void setKeyAttribute(String value)
  {
    setAttribute(KEY_ATTRIBUTE, value);
  }

  public void setNameAttribute(String value)
  {
    setAttribute(NAME_ATTRIBUTE, value);
  }

  public void setLabelAttribute(String value)
  {
    setAttribute(LABEL_ATTRIBUTE, value);
  }

  public void addReference(boolean containment, Object object)
  {
    if (object != null)
    {
      Element element = provider.getElement(object);
      if (element != null)
      {
        references.put(element.getID(), containment);
      }
    }
  }

  public void addReferences(boolean containment, Collection<?> objects)
  {
    if (objects != null)
    {
      for (Object object : objects)
      {
        addReference(containment, object);
      }
    }
  }

  public void addReferences(boolean containment, Object[] objects)
  {
    if (objects != null)
    {
      for (Object object : objects)
      {
        addReference(containment, object);
      }
    }
  }

  public ElementEvent.Change compare(Element oldElement)
  {
    Change result = new Change();

    Map<String, String> oldAttributes = oldElement.getAttributes();
    for (Entry<String, String> entry : attributes.entrySet())
    {
      String key = entry.getKey();
      String newValue = entry.getValue();
      String oldValue = oldAttributes.get(key);
      if (!ObjectUtil.equals(oldValue, newValue))
      {
        result.attributeChanged(key, newValue);
      }
    }

    for (String key : oldAttributes.keySet())
    {
      if (!attributes.containsKey(key))
      {
        result.attributeRemoved(key);
      }
    }

    Map<Integer, Boolean> oldReferences = oldElement.getReferences();
    for (Entry<Integer, Boolean> entry : references.entrySet())
    {
      int id = entry.getKey();
      Boolean newContainment = entry.getValue();
      Boolean oldContainment = oldReferences.get(id);

      if (oldContainment == Boolean.TRUE)
      {
        if (newContainment == Boolean.TRUE)
        {
          // Do nothing
        }
        else if (newContainment == Boolean.FALSE)
        {
          result.referenceType(id, false);
        }
        else
        {
          result.referenceRemoved(id);
        }
      }
      else if (oldContainment == Boolean.FALSE)
      {
        if (newContainment == Boolean.TRUE)
        {
          result.referenceType(id, true);
        }
        else if (newContainment == Boolean.FALSE)
        {
          // Do nothing
        }
        else
        {
          result.referenceRemoved(id);
        }
      }
      else
      {
        if (newContainment == Boolean.TRUE)
        {
          result.referenceAdded(id, true);
        }
        else if (newContainment == Boolean.FALSE)
        {
          result.referenceAdded(id, false);
        }
        else
        {
          // Do nothing
        }
      }
    }

    Set<Integer> keySet = oldReferences.keySet();
    for (Integer id : keySet)
    {
      if (!references.containsKey(id))
      {
        result.referenceRemoved(id);
      }
    }

    return result.isEmpty() ? null : result;
  }

  public Element[] getElements()
  {
    List<Element> result = new ArrayList<Element>();
    for (Entry<Integer, Boolean> entry : references.entrySet())
    {
      if (Boolean.TRUE.equals(entry.getValue()))
      {
        int id = entry.getKey();
        Element element = provider.getElement(id);
        if (element != null)
        {
          result.add(element);
        }
      }
    }

    return result.toArray(new Element[result.size()]);
  }

  @Override
  public boolean isEmpty()
  {
    for (Entry<Integer, Boolean> entry : references.entrySet())
    {
      if (Boolean.TRUE.equals(entry.getValue()))
      {
        return false;
      }
    }

    return true;
  }

  @Override
  public String toString()
  {
    return descriptor.getLabel(this);
  }
}