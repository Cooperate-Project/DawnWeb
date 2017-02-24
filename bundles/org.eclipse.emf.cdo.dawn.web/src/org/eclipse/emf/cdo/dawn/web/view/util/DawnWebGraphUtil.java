package org.eclipse.emf.cdo.dawn.web.view.util;

import org.eclipse.emf.cdo.dawn.web.util.DawnWebUtil;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Generalization;

import java.util.ArrayList;

public class DawnWebGraphUtil
{

  private static final int ASSOCIATION_WEIGHT = 1;

  private static final int GENERALIZATION_WEIGHT = 5;

  private static final int CLUSTER_SIZE_THRESHOLD = 7;

  /**
   * Converts a diagram to a graph.
   *
   * @param diagram
   *          The diagram to convert into a graph.
   * @return The resulting graph.
   */
  public static Graph convertToGraph(Diagram diagram)
  {
    ArrayList<Link> links = new ArrayList<Link>();
    ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

    Graph resultGraph = new Graph(nodes, links);

    // Add the nodes and links
    for (Object o : diagram.getChildren())
    {
      if (o instanceof Node)
      {
        // Add a node
        Node node = (Node)o;

        // Get the node position
        int x = -1;
        int y = -1;
        LayoutConstraint l = node.getLayoutConstraint();
        if (l instanceof Location)
        {
          x = ((Location)l).getX();
          y = ((Location)l).getY();
        }
        nodes.add(new GraphNode(DawnWebUtil.getUniqueId(node), x, y));
      }
    }

    for (Object e : diagram.getEdges())
    {
      // Add an edge
      Edge edge = (Edge)e;

      if (edge.getElement() instanceof Association)
      {
        addEdgeInGraph(resultGraph, edge, ASSOCIATION_WEIGHT);
      }

      if (edge.getElement() instanceof Generalization)
      {
        addEdgeInGraph(resultGraph, edge, GENERALIZATION_WEIGHT);
      }
    }

    return resultGraph;
  }

  /**
   * Clusters a graph by dividing the given graph recursively into two parts until the cluster size threshold is
   * reached.
   *
   * @param graph
   *          The graph to cluster.
   * @return A list of Graphs (clusters).
   */
  public static ArrayList<Graph> clusterGraph(Graph graph)
  {

    ArrayList<Graph> result = new ArrayList<Graph>();

    // Store a backup of all links in their original state
    ArrayList<Link> allLinks = new ArrayList<Link>();
    for (Link l : graph.getLinks())
    {
      allLinks.add(new Link(l));
    }

    // Split graph if larger than threshold
    if (graph.getSize() > CLUSTER_SIZE_THRESHOLD)
    {
      // Recursively contract the heaviest edge (highest closeness) until halved
      while (graph.getSize() > 2)
      {
        Link heaviestLink = graph.getHeaviestLink();
        if (heaviestLink != null)
        {
          graph.contractLink(heaviestLink);
        }
        else
        {
          // There is no edge left, but still more than two parts
          graph.mergeClosestNodes();
        }
      }

      // Cut into two separate graphs
      Graph partOne = new Graph();
      Graph partTwo = new Graph();

      assert graph.getNodes().size() == 2;

      DawnWebGraphUtil.addNodesToGraph(partOne, graph.getNodes().get(0));
      DawnWebGraphUtil.addNodesToGraph(partTwo, graph.getNodes().get(1));
      DawnWebGraphUtil.addInternalLink(partOne, allLinks);
      DawnWebGraphUtil.addInternalLink(partTwo, allLinks);

      // Recursion
      result.addAll(clusterGraph(partOne));
      result.addAll(clusterGraph(partTwo));
    }
    else
    {
      // Return the input graph if smaller than threshold, end of recursion
      result.add(graph);
    }

    return result;
  }

  /**
   * Adds all given nodes to a graph.
   *
   * @param graph
   *          The graph to add the node(s) to.
   * @param node
   *          The node to be added to the graph. This can be a multi-node containing multiple separate nodes. All nodes
   *          will then be added.
   */
  public static void addNodesToGraph(Graph graph, GraphNode node)
  {
    // Avoid duplicate nodes
    if (graph.getNodes().contains(node))
    {
      return;
    }

    if (node instanceof MultiNode)
    {
      MultiNode nodeCluster = (MultiNode)node;
      // Add nodes from the node cluster
      for (GraphNode n : nodeCluster.getNodes())
      {
        graph.addNode(n);
      }
    }
    else
    {
      graph.addNode(node);
    }
  }

  /**
   * Adds all links from the original diagram to the given graph if applicable (both source and target are in the
   * graph).
   *
   * @param graph
   *          The graph to add the links to.
   * @param links
   *          A list of links from the original diagram.
   */
  public static void addInternalLink(Graph graph, ArrayList<Link> links)
  {
    ArrayList<GraphNode> nodes = graph.getNodes();

    for (Link l : links)
    {
      if (nodes.contains(l.getSource()) && nodes.contains(l.getTarget()))
      {
        graph.addLink(l);
      }
    }
  }

  /**
   * Adds an edge from a diagram to a graph with a specified weight.
   *
   * @param graph
   *          The graph to add the edge to.
   * @param edge
   *          The edge to be added.
   * @param weight
   *          The weight of the edge.
   */
  private static void addEdgeInGraph(Graph graph, Edge edge, int weight)
  {
    GraphNode sourceNode = graph.getNodeById(DawnWebUtil.getUniqueId(edge.getSource()));
    GraphNode targetNode = graph.getNodeById(DawnWebUtil.getUniqueId(edge.getTarget()));

    Link link = new Link(sourceNode, targetNode, weight);

    graph.addLink(link);

    sourceNode.addLink(link);
    targetNode.addLink(link);
  }
}
