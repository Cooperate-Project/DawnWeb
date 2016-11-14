package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList;

public class GraphNode
{
  private String id;

  private ArrayList<Link> incoming = new ArrayList<Link>();

  private ArrayList<Link> outgoing = new ArrayList<Link>();

  /**
   * Constructs a node with the given id.
   *
   * @param id
   *          The id of the node to be constructed.
   */
  public GraphNode(String id)
  {
    this.id = id;
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

  public void addIncoming(Link l)
  {
    incoming.add(l);
  }

  public void addOutgoing(Link l)
  {
    outgoing.add(l);
  }

  @Override
  public String toString()
  {
    return id;
  }
}
