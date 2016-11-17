package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList;
import java.util.UUID;

public class GraphNode
{
  protected String id;

  protected ArrayList<Link> links = new ArrayList<Link>();

  protected int x;

  protected int y;

  /**
   * Constructs a node with the given id.
   *
   * @param id
   *          The id of the node to be constructed.
   */
  public GraphNode(String id, int x, int y)
  {
    if (id == null)
    {
      id = UUID.randomUUID().toString();
    }
    this.id = id;
    this.x = x;
    this.y = y;
  }

  public GraphNode(GraphNode node)
  {
    id = node.getId();
    x = node.getX();
    y = node.getY();
  }

  /**
   * Returns the id of the node.
   *
   * @return The id of the node.
   */
  public String getId()
  {
    return id;
  }

  /**
   * Sets the id of the node.
   *
   * @param id
   *          The new id for the node.
   */
  public void setId(String id)
  {
    this.id = id;
  }

  public void addLink(Link l)
  {
    links.add(l);
  }

  public ArrayList<Link> getLinks()
  {
    return links;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  @Override
  public String toString()
  {
    return id;
  }
}
