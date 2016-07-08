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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassFigure = function(/* :String */className)
{
	org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure.call(this);

	this.outputPort1 = null;
	this.outputPort2 = null;
	this.setDimension(100, 100);
	this.setResizeable(false);
	this.setClassName(className);
}
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassFigure.prototype = new org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure;
/** @private * */
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassFigure.prototype.type = "org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassFigure";

org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassFigure.prototype.createHTMLElement = function()
{
	var item = org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure.prototype.createHTMLElement.call(this);
	item.style.border = "1px solid #B0B0B0";
	item.style.backgroundColor = "rgb(230,230,255)";
	return item;
}
