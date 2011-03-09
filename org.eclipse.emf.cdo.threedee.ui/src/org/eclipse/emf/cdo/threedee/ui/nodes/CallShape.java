/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.threedee.ui.nodes;

import org.eclipse.emf.cdo.threedee.common.Element;
import org.eclipse.emf.cdo.threedee.ui.ThreeDeeUtil;

import org.eclipse.net4j.util.collection.Pair;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;

import java.awt.Color;

/**
 * @author Eike Stepper
 */
public class CallShape extends ThreeDeeNode<Pair<Element, Element>>
{
  public CallShape(Element from, Element to)
  {
    super(new Pair<Element, Element>(from, to), createAppearance());
  }

  @Override
  public Shape3D getShape()
  {
    return (Shape3D)super.getShape();
  }

  public void setGeometry(Geometry geometry)
  {
    getShape().setGeometry(geometry);
  }

  @Override
  protected Node createShape(Appearance appearance)
  {
    Shape3D shape = new Shape3D();
    shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
    shape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
    shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
    shape.setAppearance(appearance);
    return shape;
  }

  private static Appearance createAppearance()
  {
    Appearance appearance = ThreeDeeUtil.getDefaultAppearance(Color.white);

    TransparencyAttributes transparencyAttributes = appearance.getTransparencyAttributes();
    if (transparencyAttributes == null)
    {
      transparencyAttributes = new TransparencyAttributes();
      appearance.setTransparencyAttributes(transparencyAttributes);
    }

    transparencyAttributes.setTransparencyMode(TransparencyAttributes.FASTEST);
    transparencyAttributes.setTransparency(0.0f);
    return appearance;
  }

  @Override
  protected void layout(ThreeDeeNode<Pair<Element, Element>>[] children, int n)
  {
    throw new UnsupportedOperationException("Should plcaement be done here?");
  }
}