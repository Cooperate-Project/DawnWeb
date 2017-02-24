package org.eclipse.emf.cdo.dawn.web.view.util;

import org.eclipse.emf.cdo.dawn.web.util.DawnWebUtil;

import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.TypedElement;

import java.util.ArrayList;
import java.util.Optional;

public class DawnWebAccessibleUtil
{
  // Switches are necessary to read out EStructuralFeatures
  private final static NamedSwitch nameSwitch = new NamedSwitch();

  private final static TypedSwitch typeSwitch = new TypedSwitch();

  /**
   * Converts the diagram to an exchange format to be handed over to Xtend.
   *
   * @param diagram
   *          The diagram to be converted to a syntax hierarchy.
   * @param graph
   *          A graph containing a partial diagram to restrict the syntax hierarchy to a certain part. The graph can be
   *          <code>null</code> to include all available elements.
   * @return The syntax hierarchy as a DiagramExchangeObject.
   */
  public static DiagramExchangeObject toSyntaxHierarchy(Diagram diagram, Graph graph)
  {
    // If graph is not null, get the IDs of the nodes to be included
    ArrayList<String> nodeIds = graph == null ? null : graph.getNodeIds();

    // Create fixed root structure
    DiagramExchangeObject result = getFixedRootStructure(graph == null ? DawnWebUtil.getUniqueId(diagram) : null);
    Optional<DiagramExchangeObject> classes = result.getChildByValue(DawnWebAccessibleConfig.CLASSES_HEADING);
    Optional<DiagramExchangeObject> associations = result.getChildByValue(DawnWebAccessibleConfig.ASSOCIATIONS_HEADING);
    Optional<DiagramExchangeObject> generalizations = result
        .getChildByValue(DawnWebAccessibleConfig.GENERALIZATIONS_HEADING);

    if (!classes.isPresent() || !associations.isPresent() || !generalizations.isPresent())
    {
      // Root failed to build, return empty object
      return new DiagramExchangeObject(null);
    }
    int generalizationsCounter = 0; // Used for naming as generalizations aren't named

    // Tracking of the outer bounds for center calculation (for clusters view)
    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;

    for (Object v : diagram.getChildren())
    {
      if (v instanceof Node)
      {
        Node node = (Node)v;
        String nodeId = DawnWebUtil.getUniqueId(node);

        if (nodeIds != null && !nodeIds.contains(nodeId))
        {
          // There is a graph set and this node is not part of the graph
          continue;
        }

        DiagramExchangeObject temp = new DiagramExchangeObject(nodeId, classes.get(),
            nameSwitch.doSwitch(node.getElement()));
        temp.setMutable(true);
        temp.setRemovable(true);

        // Get the node position and add to tracking variables
        int localX = 0;
        int localY = 0;
        LayoutConstraint l = node.getLayoutConstraint();
        if (l instanceof Location)
        {
          localX = ((Location)l).getX();
          localY = ((Location)l).getY();
        }
        minX = Math.min(minX, localX);
        maxX = Math.max(maxX, localX);
        minY = Math.min(minY, localY);
        maxY = Math.max(maxY, localY);

        int compartmentCounter = 0;

        // Get more information from the compartments
        for (Object c : node.getChildren())
        {
          if (c instanceof BasicCompartment)
          {
            BasicCompartment compartment = (BasicCompartment)c;

            String compartmentName;
            switch (compartmentCounter)
            {
            case 0:
              compartmentName = DawnWebAccessibleConfig.COMPARTMENT_1_HEADING;
              break;
            case 1:
              compartmentName = DawnWebAccessibleConfig.COMPARTMENT_2_HEADING;
              break;
            case 2:
              compartmentName = DawnWebAccessibleConfig.COMPARTMENT_3_HEADING;
              break;
            default:
              compartmentName = DawnWebAccessibleConfig.COMPARTMENT_4_HEADING;
            }

            DiagramExchangeObject tempCompartment = new DiagramExchangeObject(DawnWebUtil.getUniqueId(compartment),
                temp, compartmentName);

            for (Object elem : compartment.getChildren())
            {
              if (elem instanceof Shape)
              {
                Shape s = (Shape)elem;

                DiagramExchangeObject entry = new DiagramExchangeObject(DawnWebUtil.getUniqueId(s), tempCompartment,
                    nameSwitch.doSwitch(s.getElement()));

                if (s.getElement() instanceof TypedElement)
                {
                  new DiagramExchangeObject(null, entry, typeSwitch.doSwitch(s.getElement()));
                }

              }
            }
            ++compartmentCounter;
          }
        }

        // Create compartments for the references to the links
        new DiagramExchangeObject(null, temp, DawnWebAccessibleConfig.INCOMING_ASSOCIATIONS_HEADING);
        new DiagramExchangeObject(null, temp, DawnWebAccessibleConfig.OUTGOING_ASSOCIATIONS_HEADING);
        new DiagramExchangeObject(null, temp, DawnWebAccessibleConfig.OUTGOING_GENERALIZATIONS_HEADING);
        new DiagramExchangeObject(null, temp, DawnWebAccessibleConfig.INCOMING_GENERALIZATIONS_HEADING);
      }
    }

    for (Object v : diagram.getEdges())
    {
      Edge edge = (Edge)v;
      String edgeId = DawnWebUtil.getUniqueId(edge);

      // Check if edge should be added
      if (nodeIds != null && (!nodeIds.contains(DawnWebUtil.getUniqueId(edge.getSource()))
          || !nodeIds.contains(DawnWebUtil.getUniqueId(edge.getTarget()))))
      {
        continue;
      }

      // Associations
      if (edge.getElement() instanceof Association)
      {
        // This edge is an association
        String name = nameSwitch.doSwitch(edge.getElement());
        DiagramExchangeObject temp = new DiagramExchangeObject(edgeId, associations.get(), name);
        temp.setMutable(true);
        temp.setRemovable(true);

        if (edge.getSource() != null)
        {
          addEndingToLinkInHierarchy(result, DawnWebUtil.getUniqueId(edge.getSource()), temp, true, 0, name);
        }

        if (edge.getTarget() != null)
        {
          addEndingToLinkInHierarchy(result, DawnWebUtil.getUniqueId(edge.getTarget()), temp, false, 0, name);
        }
      }

      // Generalizations
      if (edge.getElement() instanceof Generalization)
      {
        ++generalizationsCounter;
        String name = DawnWebAccessibleConfig.INHERITANCE_IDENTIFIER + " " + generalizationsCounter;

        // This edge is an generalization
        DiagramExchangeObject temp = new DiagramExchangeObject(edgeId, generalizations.get(), "Generalization");

        if (edge.getSource() != null)
        {
          addEndingToLinkInHierarchy(result, DawnWebUtil.getUniqueId(edge.getSource()), temp, true, 1, name);
        }

        if (edge.getTarget() != null)
        {
          addEndingToLinkInHierarchy(result, DawnWebUtil.getUniqueId(edge.getTarget()), temp, false, 1, name);
        }
      }
    }

    // Calculate position of the hierarchy (relevant for clusters)
    result.setX(minX + (maxX - minX) / 2);
    result.setY(minY + (maxY - minY) / 2);

    return result;
  }

  /**
   * Creates a fixed root structure for a hierarchy.
   *
   * @param id
   *          The requested ID of the new hierarchy. <code>null</code>, if a random UUID should be used.
   * @return The DiagramExchangeObject containing the root node of the hierarchy.
   */
  private static DiagramExchangeObject getFixedRootStructure(String id)
  {
    // Create fixed root structure
    DiagramExchangeObject result = new DiagramExchangeObject(id);

    DiagramExchangeObject classes = new DiagramExchangeObject(null, DawnWebAccessibleConfig.CLASSES_HEADING);
    result.appendChild(classes);

    DiagramExchangeObject associations = new DiagramExchangeObject(null, DawnWebAccessibleConfig.ASSOCIATIONS_HEADING);
    result.appendChild(associations);

    DiagramExchangeObject generalizations = new DiagramExchangeObject(null,
        DawnWebAccessibleConfig.GENERALIZATIONS_HEADING);
    result.appendChild(generalizations);

    return result;
  }

  /**
   * Adds an ending of a link to the hierarchy.
   *
   * @param hierarchy
   *          The hierarchy to be added to.
   * @param classCdoId
   *          The CDO ID of the incident class.
   * @param link
   *          The link object representing the link which this ending is part of.
   * @param isSource
   *          Indicates whether this ending is the source.
   * @param linkType
   *          An encoded type of the link (0 = association, 1 = generalization) - extendible for further types.
   * @param name
   *          The name of the link.
   */
  private static void addEndingToLinkInHierarchy(DiagramExchangeObject hierarchy, String classCdoId,
      DiagramExchangeObject link, boolean isSource, int linkType, String name)
  {
    String endTypeName = isSource ? DawnWebAccessibleConfig.SOURCE_KEYWORD : DawnWebAccessibleConfig.TARGET_KEYWORD;
    Optional<String> subTreeNameOptional = getCategoryInClassSubtree(linkType, isSource);

    if (!subTreeNameOptional.isPresent())
    {
      // Unknown link type
      return;
    }

    Optional<DiagramExchangeObject> classesRoot = hierarchy.getChildByValue(DawnWebAccessibleConfig.CLASSES_HEADING);
    if (!classesRoot.isPresent())
    {
      // There is no classes root
      return;
    }

    DiagramExchangeObject incidentClass = classesRoot.get().getChildById(classCdoId);

    // Append information to the link object
    new DiagramExchangeObject(link.getId() + endTypeName, link, endTypeName, incidentClass);

    // Append association to the class
    Optional<DiagramExchangeObject> classSubtree = incidentClass.getChildByValue(subTreeNameOptional.get());
    if (classSubtree.isPresent())
    {
      new DiagramExchangeObject(link.getId() + endTypeName + DawnWebAccessibleConfig.REFERENCE_SUFFIX,
          classSubtree.get(), name, link);
    }
  }

  /**
   * Retrieves the name of the subtree in a classes where the link belongs to.
   *
   * @param linkType
   *          The encoded link type.
   * @param isSource
   *          Whether this end of the link is the source or the target.
   * @return The name of the subtree where this link belongs to.
   */
  private static Optional<String> getCategoryInClassSubtree(int linkType, boolean isSource)
  {
    switch (linkType)
    {
    case 0:
      return Optional.of(isSource ? DawnWebAccessibleConfig.OUTGOING_ASSOCIATIONS_HEADING
          : DawnWebAccessibleConfig.INCOMING_ASSOCIATIONS_HEADING);
    case 1:
      return Optional.of(isSource ? DawnWebAccessibleConfig.OUTGOING_GENERALIZATIONS_HEADING
          : DawnWebAccessibleConfig.INCOMING_GENERALIZATIONS_HEADING);
    default:
      return Optional.empty();
    }
  }

}
