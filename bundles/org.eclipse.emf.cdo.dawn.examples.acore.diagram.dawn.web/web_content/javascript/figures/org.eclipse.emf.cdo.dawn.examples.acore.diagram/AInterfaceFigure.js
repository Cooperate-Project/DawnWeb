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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AInterfaceFigure = function(
/* :String */className)
{
	org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.call(this, className, 12);

	this.outputPort1 = null;
	this.outputPort2 = null;
	this.setDimension(50, 50);
	this.setResizeable(false);

	this.setClassName(className);

}
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AInterfaceFigure.prototype = new org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure;
/** @private * */
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AInterfaceFigure.prototype.type = "org.eclipse.emf.cdo.dawn.examples.acore.diagram.AInterfaceFigure";

org.eclipse.emf.cdo.dawn.examples.acore.diagram.AInterfaceFigure.prototype.createHTMLElement = function()
{
	var item = org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.prototype.createHTMLElement.call(this);
	item.style.border = "1px solid #B0B0B0";
	item.style.backgroundColor = "rgb(250,250,190)";
	return item;
}
