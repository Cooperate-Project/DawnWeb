package org.eclipse.emf.cdo.dawn.web.view.util;

import java.util.ArrayList;

/**
 * A MultiNode represents multiple GraphNodes in a Graph by collecting their IDs.
 *
 * @author Shengjia Feng
 */
public class MultiNode extends GraphNode
{

  private ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

  /**
   * Constructs a MultiNode with the given ID. Same ID policy as for GraphNodes.
   *
   * @param id
   *          The ID for the new MultiNode, can be <code>null</code>.
   */
  public MultiNode(String id)
  {
    super(id, -1, -1);
  }

  /**
   * Returns the list of contained nodes.
   *
   * @return The contained nodes as a list.
   */
  public ArrayList<GraphNode> getNodes()
  {
    return nodes;
  }

  /**
   * Add a new GraphNode.
   *
   * @param node
   *          The GraphNode to be added.
   */
  public void addNode(GraphNode node)
  {
    if (!nodes.contains(node))
    {
      nodes.add(node);
      recalculateCenterPoint();
    }
  }

  /**
   * Returns whether a specified node is contained.
   *
   * @param node
   *          The node to check for.
   * @return <code>true</code> if contained, <code>false</code> otherwise
   */
  protected boolean contains(GraphNode node)
  {
    return nodes.contains(node);
  }

  /**
   * Sets the x-coordinate.
   *
   * @param x
   *          The target x-coordinate.
   */
  protected void setX(int x)
  {
    this.x = x;
  }

  /**
   * Sets the y-coordinate.
   *
   * @param y
   *          The target y-coordinate.
   */
  protected void setY(int y)
  {
    this.y = y;
  }

  /**
   * Recalculates the x-/y-coordinates for the MultiNode.
   */
  private void recalculateCenterPoint()
  {
    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;

    for (GraphNode node : nodes)
    {
      int nodeX = node.getX();
      int nodeY = node.getY();

      minX = Math.min(minX, nodeX);
      maxX = Math.max(maxX, nodeX);
      minY = Math.min(minY, nodeY);
      maxY = Math.max(maxY, nodeY);
    }

    // Calculate arithmetic mean of the borders
    x = minX + (maxX - minX) / 2;
    y = minY + (maxY - minX) / 2;
  }
}
