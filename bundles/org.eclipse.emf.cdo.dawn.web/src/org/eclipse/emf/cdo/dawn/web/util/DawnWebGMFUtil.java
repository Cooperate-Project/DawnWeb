/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.dawn.web.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Martin Fluegge
 */
public class DawnWebGMFUtil
{
  public static void deleteViewInResource(Resource resource, EObject e)
  {
    Diagram diagram = getDiagramFromResource(resource);
    EObject element = ((View)e).getElement();

    if (element != null)
    {
      removeElementFromContainer(element);
    }

    if (e instanceof Node)
    {
      View node = (View)e;
      diagram.removeChild(node);// ..getChildren().add(v);
      @SuppressWarnings("unchecked")
      List<Edge> toBeDeleted = new ArrayList<Edge>(node.getSourceEdges());
      for (Object obj : toBeDeleted)
      {
        Edge edge = (Edge)obj;
        deleteViewInResource(resource, edge);
      }
    }
    else if (e instanceof Edge)
    {
      Edge edge = (Edge)e;
      diagram.removeEdge(edge);// ..getChildren().add(v);
      edge.setSource(null);
      edge.setTarget(null);
    }
  }

  /**
   * returns the diagram from the resource if no diagram can be found it returns null.
   * 
   * @param res
   * @return if it exists the diagram otherwise null
   */
  public static Diagram getDiagramFromResource(Resource res)
  {
    for (Object o : res.getContents())
    {
      if (o instanceof Diagram)
      {
        return (Diagram)o;
      }
    }
    return null;
  }

  private static void removeElementFromContainer(EObject element)
  {
    EStructuralFeature containingFeature = element.eContainingFeature();
    EObject container = element.eContainer();
    if (container != null)
    {
      Object get = container.eGet(containingFeature);
      if (get instanceof Collection<?>)
      {
        Collection<?> list = (Collection<?>)get;
        list.remove(element);
      }
      else
      {
        container.eSet(containingFeature, null);
      }
    }
  }
}
