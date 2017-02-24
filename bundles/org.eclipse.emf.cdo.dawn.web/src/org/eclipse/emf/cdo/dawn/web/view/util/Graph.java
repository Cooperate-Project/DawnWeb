package org.eclipse.emf.cdo.dawn.web.view.util;

import java.util.ArrayList;

/**
 * Representation of a simple node-and-link graph.
 *
 * @author Shengjia Feng
 */
public class Graph
{
  private ArrayList<GraphNode> nodes;

  private ArrayList<Link> links;

  /**
   * Constructs an empty graph.
   */
  public Graph()
  {
    nodes = new ArrayList<GraphNode>();
    links = new ArrayList<Link>();
  }

  /**
   * Constructs a graph with the given nodes and links.
   *
   * @param nodes
   *          A set of nodes to add to the graph.
   * @param links
   *          A set of links to add to the graph.
   */
  public Graph(ArrayList<GraphNode> nodes, ArrayList<Link> links)
  {
    this.nodes = nodes;
    this.links = links;
  }

  /**
   * Constructs a graph as a copy of another graph.
   *
   * @param graph
   *          The graph to be copied.
   */
  public Graph(Graph graph)
  {
    nodes = new ArrayList<GraphNode>();
    links = new ArrayList<Link>();

    // Duplicate all nodes
    for (GraphNode node : graph.getNodes())
    {
      nodes.add(new GraphNode(node));
    }

    // Duplicate all links
    for (Link link : graph.getLinks())
    {
      links.add(new Link(link));
    }
  }

  @Override
  public String toString()
  {
    return nodes.toString() + "\n" + links.toString();
  }

  /**
   * Returns all nodes in the graph.
   *
   * @return All nodes in the graph as a list.
   */
  public ArrayList<GraphNode> getNodes()
  {
    return nodes;
  }

  /**
   * Returns IDs of all nodes in the graph.
   *
   * @return IDs of all nodes in the graph as a list.
   */
  public ArrayList<String> getNodeIds()
  {
    ArrayList<String> result = new ArrayList<String>();
    for (GraphNode g : nodes)
    {
      result.add(g.getId());
    }
    return result;
  }

  /**
   * Retrieves the node with the given ID.
   *
   * @param id
   *          The ID of the node to retrieve.
   * @return The node if found, <code>null</code> if there is no such node.
   */
  public GraphNode getNodeById(String id)
  {
    for (GraphNode n : nodes)
    {
      if (n instanceof MultiNode && ((MultiNode)n).getNodes().contains(id))
      {
        return n;
      }
      else if (n.getId().equals(id))
      {
        return n;
      }
    }
    return null;
  }

  /**
   * Returns all links in the graph.
   *
   * @return All links in the graph as a list.
   */
  public ArrayList<Link> getLinks()
  {
    return links;
  }

  /**
   * Returns the heaviest link in the graph.
   *
   * @return The heaviest link in the graph as Link.
   */
  public Link getHeaviestLink()
  {
    Link result = null;
    int currentRecord = -1;

    for (Link l : links)
    {
      if (l.getWeight() > currentRecord)
      {
        currentRecord = l.getWeight();
        result = l;
      }
    }

    return result;
  }

  /**
   * Returns the size of the graph (= number of nodes).
   *
   * @return The size of the graph.
   */
  public int getSize()
  {
    return nodes.size();
  }

  /**
   * Adds a node to the list of nodes.
   *
   * @param node
   *          The node to be added.
   */
  public void addNode(GraphNode node)
  {
    nodes.add(node);
  }

  /**
   * Adds a link to the list of links.
   *
   * @param link
   *          The link to be added.
   */
  public void addLink(Link link)
  {
    links.add(link);
  }

  /**
   * Sets the list of nodes.
   *
   * @param nodes
   *          A new list of nodes to be set.
   */
  public void setNodes(ArrayList<GraphNode> nodes)
  {
    this.nodes = nodes;
  }

  /**
   * Sets the list of links.
   *
   * @param links
   *          A new list of links to be set.
   */
  public void setLinks(ArrayList<Link> links)
  {
    this.links = links;
  }

  /**
   * Contracts the given link by removing the link and merging the source and target nodes.
   *
   * @param link
   *          The link to be contracted.
   */
  public void contractLink(Link link)
  {
    GraphNode node1 = link.getSource();
    GraphNode node2 = link.getTarget();

    mergeNodes(node1, node2);

    // Remove the link
    links.remove(link);
  }

  /**
   * Merges the two (spatially) closest nodes.
   */
  public void mergeClosestNodes()
  {
    // Calculate distances (Euclidean Distance)
    double closestDistance = Double.MAX_VALUE;
    GraphNode nodeOne = null;
    GraphNode nodeTwo = null;

    // Loop over nodes to calculate distances
    for (int i = 0; i < nodes.size(); ++i)
    {
      GraphNode a = nodes.get(i);

      for (int j = i + 1; j < nodes.size(); ++j)
      {
        GraphNode b = nodes.get(j);

        double distanceAB = calculateDistance(a, b);
        if (distanceAB < closestDistance)
        {
          closestDistance = distanceAB;
          nodeOne = a;
          nodeTwo = b;
        }
      }
    }

    // Merge the two nodes with the closest distance to each other
    mergeNodes(nodeOne, nodeTwo);
  }

  /**
   * Merges two given nodes to one multi-node.
   *
   * @param a
   *          First node to be merged.
   * @param b
   *          Second node to be merged.
   * @return The result node of the merge.
   */
  private GraphNode mergeNodes(GraphNode a, GraphNode b)
  {

    if (a instanceof MultiNode)
    {
      if (b instanceof MultiNode)
      {
        // a and b both MultiNodes
        for (GraphNode node : ((MultiNode)b).getNodes())
        {
          // Merge b into a
          ((MultiNode)a).addNode(node);
        }
        // Remove b from graph
        nodes.remove(b);

        return a;
      }
      // a is MultiNode, b is not MultiNode
      // Merge b into a and remove b
      ((MultiNode)a).addNode(b);
      nodes.remove(b);
      return a;
    }
    else if (b instanceof MultiNode)
    {
      // a is not MultiNode, b is MultiNode
      // Merge a into b and delete a
      ((MultiNode)b).addNode(a);
      nodes.remove(a);
      return b;
    }

    // Neither a nor b is MultiNode
    // Check whether a or b is part of a MultiNode

    MultiNode aParent = getParent(a);
    MultiNode bParent = getParent(b);

    if (aParent != null && bParent != null && aParent == bParent)
    {
      // Both parent MultiNodes are the same, no merging necessary
      return aParent;
    }

    if (aParent != null && bParent != null)
    {
      // Merge parent MultiNodes if it's not the same
      for (GraphNode n : bParent.getNodes())
      {
        aParent.addNode(n);
      }
      nodes.remove(bParent);
      return aParent;
    }
    else if (aParent != null)
    {
      // Merge b into aParent
      aParent.addNode(b);
      nodes.remove(b);
      return aParent;
    }
    else if (bParent != null)
    {
      // Merge a into bParent
      bParent.addNode(a);
      nodes.remove(a);
      return bParent;
    }

    // None of a and b has a parent MultiNode
    // Create a new MultiNode and remove the original GraphNode
    MultiNode temp = new MultiNode(null);
    temp.addNode(a);
    temp.addNode(b);
    nodes.add(temp);
    nodes.remove(a);
    nodes.remove(b);
    return temp;
  }

  /**
   * Calculates the distance between two given nodes.
   *
   * @param a
   *          The first node.
   * @param b
   *          The second node.
   * @return The distance between the two nodes as a double.
   */
  private double calculateDistance(GraphNode a, GraphNode b)
  {
    int xDiff = Math.abs(a.getX() - b.getX());
    int yDiff = Math.abs(a.getY() - b.getY());

    return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
  }

  /**
   * Returns the parent of a node.
   *
   * @param node
   *          The node to get the parent from.
   * @return The parent node, if available. <code>null</code> otherwise.
   */
  private MultiNode getParent(GraphNode node)
  {
    for (GraphNode n : nodes)
    {
      if (n instanceof MultiNode && ((MultiNode)n).contains(node))
      {
        return (MultiNode)n;
      }
    }
    return null;
  }
}
