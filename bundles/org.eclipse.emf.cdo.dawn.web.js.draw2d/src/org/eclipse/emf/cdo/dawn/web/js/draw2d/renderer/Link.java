package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

/**
 * A link is a unidirectional connection between two nodes.
 *
 * @author Shengjia Feng
 */
public class Link
{
  private GraphNode source;

  private GraphNode target;

  /**
   * Weight is a non-negative integer. A value of -1 represents an undefined weight.
   */
  private int weight;

  /**
   * Constructs a link with undefined weight.
   *
   * @param source
   *          The source node of the link.
   * @param target
   *          The target node of the link.
   */
  public Link(GraphNode source, GraphNode target)
  {
    this.source = source;
    this.target = target;
    weight = -1;
  }

  /**
   * Constructs a link with given weight.
   *
   * @param source
   *          The source node of the link.
   * @param target
   *          The target node of the link
   * @param weight
   *          The weight of the link.
   */
  public Link(GraphNode source, GraphNode target, int weight)
  {
    this.source = source;
    this.target = target;
    this.weight = weight;
  }

  /**
   * Returns the source node of the link.
   *
   * @return The source node of the link
   */
  public GraphNode getSource()
  {
    return source;
  }

  /**
   * Returns the target node of the link.
   *
   * @return The target node of the link
   */
  public GraphNode getTarget()
  {
    return target;
  }

  /**
   * Returns the weight of the link.
   *
   * @return The weight of the link
   */
  public int getWeight()
  {
    return weight;
  }

  /**
   * Sets the source node of the link.
   *
   * @param source
   *          The new source node for the link.
   */
  public void setSource(GraphNode source)
  {
    this.source = source;
  }

  /**
   * Sets the target node of the link.
   *
   * @param target
   *          The new target node for the link.
   */
  public void setTarget(GraphNode target)
  {
    this.target = target;
  }

  /**
   * Sets the weight of the link.
   *
   * @param weight
   *          The new weight for the link.
   */
  public void setWeight(int weight)
  {
    this.weight = weight;
  }

  @Override
  public String toString()
  {
    return source.toString() + " -[" + weight + "]> " + target.toString();
  }
}
