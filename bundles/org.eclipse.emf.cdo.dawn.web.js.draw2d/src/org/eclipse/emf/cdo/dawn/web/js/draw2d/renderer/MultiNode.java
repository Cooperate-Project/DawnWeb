package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList;

/**
 * A MultiNode represents multiple GraphNodes by collecting their IDs.
 *
 * @author Shengjia Feng
 */
public class MultiNode extends GraphNode
{

  private ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

  public MultiNode(String id)
  {
    super(id, -1, -1);
  }

  public void addNode(GraphNode node)
  {
    if (!nodes.contains(node))
    {
      nodes.add(node);
      recalculateCenterPoint();
    }
  }

  public ArrayList<GraphNode> getNodes()
  {
    return nodes;
  }

  protected boolean contains(GraphNode node)
  {
    return nodes.contains(node);
  }

  protected void setX(int x)
  {
    this.x = x;
  }

  protected void setY(int y)
  {
    this.y = y;
  }

  private void recalculateCenterPoint()
  {
    int minX = -1;
    int maxX = -1;
    int minY = -1;
    int maxY = -1;

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
    x = (maxX - minX) / 2;
    y = (maxY - minX) / 2;
  }

}
