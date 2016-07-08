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

import java.util.ArrayList;
import java.util.List;

public class FigureMapping
{
  public String javaScriptClass = "";

  private List<ViewAttribute> viewAttributes;

  private String viewPattern;

  public String getJavaScriptClass()
  {
    return javaScriptClass;
  }

  public void setJavaScriptClass(String javaScriptClass)
  {
    this.javaScriptClass = javaScriptClass;
  }

  public List<ViewAttribute> getViewAttributes()
  {
    if (viewAttributes == null)
    {
      viewAttributes = new ArrayList<ViewAttribute>();
    }
    return viewAttributes;
  }

  public void setViewAttributes(List<ViewAttribute> viewAttributes)
  {
    this.viewAttributes = viewAttributes;
  }

  public void addAttribute(ViewAttribute attr)
  {
    getViewAttributes().add(attr);
  }

  public void setViewPattern(String viewPattern)
  {
    this.viewPattern = viewPattern;
  }

  public String getViewPattern()
  {
    return viewPattern;
  }
}
