/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.dawn.web.util;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FigureMappingParser
{

  public HashMap<String, FigureMapping> parse(URL url)
  {
    HashMap<String, FigureMapping> ret = new HashMap<String, FigureMapping>();

    Document document;
    try
    {
      document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());

      Node root = document.getFirstChild();

      List<Node> childNodes = getNodesByName(root, "figureMapping");

      for (Node node : childNodes)
      {

        String type = getAttributeValue(node, "type");
        FigureMapping figureMapping = createFigureMapping(node);

        ret.put(type, figureMapping);
      }
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }

    return ret;
  }

  private FigureMapping createFigureMapping(Node figureMappingNode)
  {
    FigureMapping figureMapping = new FigureMapping();

    List<Node> javaScriptClass = getNodesByName(figureMappingNode, "javaScriptClass");
    String javaScriptClassName = getAttributeValue(javaScriptClass.get(0), "name");

    figureMapping.setJavaScriptClass(javaScriptClassName);

    List<ViewAttribute> viewAttributes = getViewAttributes(figureMappingNode);
    figureMapping.setViewAttributes(viewAttributes);

    List<Node> viewPatternNodes = getNodesByName(figureMappingNode, "viewPattern");
    if (viewPatternNodes.size() > 0)
    {
      String viewPattern = getAttributeValue(viewPatternNodes.get(0), "name");
      figureMapping.setViewPattern(viewPattern);
    }

    return figureMapping;
  }

  private List<ViewAttribute> getViewAttributes(Node figureMappingNode)
  {
    List<Node> viewAttributes = getNodesByName(figureMappingNode, "viewAttribute");

    List<ViewAttribute> ret = new ArrayList<ViewAttribute>();

    for (Node node : viewAttributes)
    {
      ViewAttribute viewAttribute = new ViewAttribute();
      String name = getAttributeValue(node, "name");
      viewAttribute.setName(name);
      ret.add(viewAttribute);
    }
    return ret;
  }

  private List<Node> getNodesByName(Node figureMappingNode, String name)
  {
    NodeList childNodes = figureMappingNode.getChildNodes();

    List<Node> nodes = new ArrayList<Node>();
    for (int i = 0; i < childNodes.getLength(); i++)
    {
      Node item = childNodes.item(i);
      String nodeName = item.getNodeName();

      if (name.equals(nodeName))
      {
        nodes.add(item);
      }
    }
    return nodes;
  }

  private String getAttributeValue(Node figureMappingNode, String name)
  {
    NamedNodeMap attributes = figureMappingNode.getAttributes();
    if (attributes != null)
    {
      Node namedItem = attributes.getNamedItem(name);
      return namedItem.getNodeValue();
    }
    return "";
  }
}
