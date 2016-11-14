package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList;

public class Graph
{
  private ArrayList<GraphNode> nodes;

  private ArrayList<Link> links;

  public Graph(ArrayList<GraphNode> nodes2, ArrayList<Link> links)
  {
    nodes = nodes2;
    this.links = links;
  }

  public ArrayList<GraphNode> getNodes()
  {
    return nodes;
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

  public GraphNode findNode(String id)
  {
    for (GraphNode n : nodes)
    {
      if (n.getId().equals(id))
      {
        return n;
      }
    }
    return null;
  }

  public void addLink(Link l)
  {
    links.add(l);
  }

  @Override
  public String toString()
  {
    return nodes.toString() + "\n" + links.toString();
  }
}
