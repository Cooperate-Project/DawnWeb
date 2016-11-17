package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList;

public class Graph
{
  private ArrayList<GraphNode> nodes;

  private ArrayList<Link> links;

  public Graph()
  {
    nodes = new ArrayList<GraphNode>();
    links = new ArrayList<Link>();
  }

  public Graph(ArrayList<GraphNode> nodes, ArrayList<Link> links)
  {
    this.nodes = nodes;
    this.links = links;
  }

  public Graph(Graph graph)
  {
    nodes = new ArrayList<GraphNode>();
    links = new ArrayList<Link>();

    for (GraphNode node : graph.getNodes())
    {
      nodes.add(new GraphNode(node));
    }

    for (Link link : graph.getLinks())
    {
      links.add(new Link(link));
    }
  }

  public ArrayList<GraphNode> getNodes()
  {
    return nodes;
  }

  public ArrayList<String> getNodeIds()
  {
    ArrayList<String> result = new ArrayList<String>();
    for (GraphNode g : nodes)
    {
      result.add(g.getId());
    }
    return result;
  }

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

  public ArrayList<Link> getLinks()
  {
    return links;
  }

  public void setNodes(ArrayList<GraphNode> nodes)
  {
    this.nodes = nodes;
  }

  public void setLinks(ArrayList<Link> links)
  {
    this.links = links;
  }

  public void addNode(GraphNode node)
  {
    nodes.add(node);
  }

  public void addLink(Link l)
  {
    links.add(l);
  }

  public int getSize()
  {
    return nodes.size();
  }

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

  public void contractLink(Link l)
  {
    GraphNode node1 = l.getSource();
    GraphNode node2 = l.getTarget();

    mergeNodes(node1, node2);

    // Remove the link
    links.remove(l);
  }

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

  private double calculateDistance(GraphNode a, GraphNode b)
  {

    int xDiff = Math.abs(a.getX() - b.getX());
    int yDiff = Math.abs(a.getY() - b.getY());

    return Math.sqrt(xDiff * xDiff + yDiff * yDiff);

  }

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

  @Override
  public String toString()
  {
    return nodes.toString() + "\n" + links.toString();
  }
}
