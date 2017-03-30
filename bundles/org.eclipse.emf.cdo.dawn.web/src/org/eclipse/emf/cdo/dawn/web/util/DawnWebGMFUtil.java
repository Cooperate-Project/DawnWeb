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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Martin Fluegge
 */
public class DawnWebGMFUtil
{

  /**
   * Deletes an element from the diagram.
   *
   * @param resource
   *          The resource containing the element.
   * @param e
   *          The object to be deleted.
   */
  @SuppressWarnings("unchecked")
  public static void deleteViewInResource(Resource resource, EObject e)
  {
    Optional<Diagram> diagramOptional = getDiagramFromResource(resource);

    if (!diagramOptional.isPresent())
    {
      return;
    }
    Diagram diagram = diagramOptional.get();
    EObject element = ((View)e).getElement();

    if (element != null)
    {
      EcoreUtil.remove(element);
    }

    if (e instanceof Node)
    {
      // Deleting a node
      View node = (View)e;
      List<Edge> toBeDeleted = new ArrayList<Edge>(node.getSourceEdges());
      toBeDeleted.addAll(new ArrayList<Edge>(node.getTargetEdges()));

      // Delete all incident edges
      for (Object obj : toBeDeleted)
      {
        Edge edge = (Edge)obj;
        deleteViewInResource(resource, edge);
      }

      // Eventually delete the node itself
      EcoreUtil.delete(node);
    }
    else if (e instanceof Edge)
    {
      // Deleting an edge
      Edge edge = (Edge)e;
      diagram.removeEdge(edge);
      edge.setSource(null);
      edge.setTarget(null);
    }
  }

  /**
   * Adds a new class (Node) to the diagram.
   *
   * @param res
   *          The resource to add the class to.
   * @param className
   *          The name of the class to be created.
   * @param posX
   *          The position of the new class relative to the x-axis.
   * @param posY
   *          The position of the new class relative to the y-axis.
   */
  public static void addClassToResource(Resource res, String className, int posX, int posY)
  {
    Optional<Diagram> diagramOptional = getDiagramFromResource(res);

    if (!diagramOptional.isPresent())
    {
      return;
    }
    Diagram diagram = diagramOptional.get();

    // Create element in diagram
    Node classShape = diagram.createChild(NotationPackage.eINSTANCE.getShape());
    classShape.setType("Class_Shape");
    Node attributesCompartment = classShape.createChild(NotationPackage.eINSTANCE.getBasicCompartment());
    attributesCompartment.setType("Class_AttributeCompartment");
    Node operationsCompartment = classShape.createChild(NotationPackage.eINSTANCE.getBasicCompartment());
    operationsCompartment.setType("Class_OperationCompartment");
    Node nestedClassifiersCompartment = classShape.createChild(NotationPackage.eINSTANCE.getBasicCompartment());
    nestedClassifiersCompartment.setType("Class_NestedClassifierCompartment");

    Node classNameLabel = classShape.createChild(NotationPackage.eINSTANCE.getDecorationNode());
    classNameLabel.setType("Class_NameLabel");
    Node classFloatingNameLabel = classShape.createChild(NotationPackage.eINSTANCE.getDecorationNode());
    classFloatingNameLabel.setType("Class_FloatingNameLabel");

    // Set up class
    Bounds classBounds = (Bounds)classShape.createLayoutConstraint(NotationPackage.eINSTANCE.getBounds());
    classBounds.setX(posX);
    classBounds.setY(posY);

    // Create resource element
    Model diagramElement = (Model)diagram.getElement();
    org.eclipse.uml2.uml.Class newClass = diagramElement.createOwnedClass(className, false);
    classShape.setElement(newClass);
  }

  /**
   * Returns the diagram from the resource, if there is one.
   *
   * @param res
   *          The resource to retrieve the diagram from.
   * @return Optional containing the diagram if it exists.
   */
  public static Optional<Diagram> getDiagramFromResource(Resource res)
  {
    for (Object o : res.getContents())
    {
      if (o instanceof Diagram)
      {
        return Optional.of((Diagram)o);
      }
    }
    return Optional.empty();
  }
}
