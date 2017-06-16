package de.cooperateproject.cdo.dawn.rest.accessible.util;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A GraphNode represents a node in a Graph.
 *
 * @author Shengjia Feng
 */
public class GraphNode
{
  // If an ID is available, the ID should be handed over in the constructor. Otherwise, a random UUID will be assigned.
  protected String id;

  protected ArrayList<Link> links = new ArrayList<Link>();

  protected int x;

  protected int y;

  /**
   * Constructs a GraphNode with the given information.
   *
   * @param id
   *          The id of the node to be constructed.
   * @param x
   *          The x-coordinate of the node.
   * @param y
   *          The y-coordinate of the node
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

  /**
   * Constructs a new GraphNode as a copy from another GraphNode.
   *
   * @param node
   *          The GraphNode to be copied.
   */
  public GraphNode(GraphNode node)
  {
    id = node.getId();
    x = node.getX();
    y = node.getY();
  }

  @Override
  public String toString()
  {
    return id;
  }

  /**
   * Returns the ID of the GraphNode.
   *
   * @return The ID of the GraphNode.
   */
  public String getId()
  {
    return id;
  }

  /**
   * Returns all incident links.
   *
   * @return All incident links as a list of Link.
   */
  public ArrayList<Link> getLinks()
  {
    return links;
  }

  /**
   * Returns the x-coordinate of the GraphNode.
   *
   * @return The x-coordinate of the GraphNode.
   */
  public int getX()
  {
    return x;
  }

  /**
   * Returns the y-coordinate of the GraphNode.
   *
   * @return The y-coordinate of the GraphNode.
   */
  public int getY()
  {
    return y;
  }

  /**
   * Adds a incident link to the GraphNode.
   *
   * @param link
   *          The link to be added.
   */
  public void addLink(Link link)
  {
    links.add(link);
  }

  /**
   * Sets the id of the GraphNode.
   *
   * @param id
   *          The new id for the GraphNode.
   */
  public void setId(String id)
  {
    this.id = id;
  }
}
