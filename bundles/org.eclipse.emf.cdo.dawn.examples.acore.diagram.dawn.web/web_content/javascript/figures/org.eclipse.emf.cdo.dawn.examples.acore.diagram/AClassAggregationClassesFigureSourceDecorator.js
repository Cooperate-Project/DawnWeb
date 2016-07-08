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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassAggregationClassesFigureSourceDecorator = function()
{
	this.setBackgroundColor(new draw2d.Color(255, 0, 0));
}
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassAggregationClassesFigureSourceDecorator.prototype = new org.eclipse.emf.cdo.dawn.web.basic.DawnBasicConnectionDecorator;
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassAggregationClassesFigureSourceDecorator.prototype.type = "org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassAggregationClassesFigureSourceDecorator";

org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassAggregationClassesFigureSourceDecorator.prototype.paint = function(
/* :draw2d.Graphics */g)
{
	if (this.backgroundColor != null)
	{
		g.setColor(new draw2d.Color(255, 0, 0));
		g.fillPolygon(
		[ 10, 0, 10, 20, 10 ],
		[ 6, 0, -6, 0, 6 ]);
	}

	g.setColor(new draw2d.Color(0, 0, 0));
	g.setStroke(1);
	g.drawPolygon(
	[ 10, 0, 10, 20, 10 ],
	[ 6, 0, -6, 0, 6 ]);

}
