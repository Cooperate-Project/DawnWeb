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
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure = function(/* :String */className, round)
{
	this.round = round;
	org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure.call(this);
	this.setClassName(className);

}

// org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.prototype = new
// draw2d.Node;
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.prototype = new org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure;
/** @private * */
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.prototype.type = "org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure";

/*******************************************************************************
 * Create the figure. The figure is a simple HTML table with a "tr" for the
 * header and a "tr" for each attribute of the Class.
 * 
 * @private
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.prototype.createHTMLElement = function()
{
	var item = org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure.prototype.createHTMLElement.call(this);
	// alert(this.round);
	item.style.MozBorderRadius = this.round + "px";
	return item;
}
