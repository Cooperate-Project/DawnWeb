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
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Model;

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
      // diagram.removeChild(node);// ..getChildren().add(v);
      @SuppressWarnings("unchecked")
      List<Edge> toBeDeleted = new ArrayList<Edge>(node.getSourceEdges());
      for (Object obj : toBeDeleted)
      {
        Edge edge = (Edge)obj;
        deleteViewInResource(resource, edge);
      }
      EcoreUtil.delete(node);
    }
    else if (e instanceof Edge)
    {
      Edge edge = (Edge)e;
      diagram.removeEdge(edge);// ..getChildren().add(v);
      edge.setSource(null);
      edge.setTarget(null);
    }
  }

  public static void addClassToResource(Resource res, String className, int posX, int posY)
  {
    Diagram diagram = getDiagramFromResource(res);

    // Create element in diagram
    Node node = diagram.createChild(NotationPackage.eINSTANCE.getShape());
    node.createChild(NotationPackage.eINSTANCE.getDecorationNode());
    node.createChild(NotationPackage.eINSTANCE.getDecorationNode());
    node.createChild(NotationPackage.eINSTANCE.getBasicCompartment());
    node.createChild(NotationPackage.eINSTANCE.getBasicCompartment());
    node.createChild(NotationPackage.eINSTANCE.getBasicCompartment());

    // Set up class
    node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
    Bounds bounds = (Bounds)node.getLayoutConstraint();
    bounds.setX(posX);
    bounds.setY(posY);

    // Create resource element
    Model diagramElement = (Model)diagram.getElement();
    org.eclipse.uml2.uml.Class newClass = diagramElement.createOwnedClass(className, false);
    node.setElement(newClass);
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
