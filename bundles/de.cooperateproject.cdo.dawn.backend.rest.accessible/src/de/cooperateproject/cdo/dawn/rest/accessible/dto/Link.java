package de.cooperateproject.cdo.dawn.rest.accessible.dto;

/**
 * A link is a bidirectional connection between two GraphNodes in a Graph.
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
   *          The source GraphNode of the link.
   * @param target
   *          The target GraphNode of the link.
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
   *          The source GraphNode of the link.
   * @param target
   *          The target GraphNode of the link
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
   * Constructs a new link as a copy of another link.
   *
   * @param link
   *          The link to be copied.
   */
  public Link(Link link)
  {
    source = link.getSource();
    target = link.getTarget();
    weight = link.getWeight();
  }

  @Override
  public String toString()
  {
    return source.toString() + " -[" + weight + "]- " + target.toString();
  }

  /**
   * Returns the source GraphNode of the link.
   *
   * @return The source GraphNode of the link
   */
  public GraphNode getSource()
  {
    return source;
  }

  /**
   * Returns the target GraphNode of the link.
   *
   * @return The target GraphNode of the link
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
   * Sets the source GraphNode of the link.
   *
   * @param source
   *          The new source GraphNode for the link.
   */
  public void setSource(GraphNode source)
  {
    this.source = source;
  }

  /**
   * Sets the target GraphNode of the link.
   *
   * @param target
   *          The new target GraphNode for the link.
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
}
