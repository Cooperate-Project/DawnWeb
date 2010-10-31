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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AnOperationLabelFigure = function(msg)
{

	this.msg = msg;
	// this.bgColor = new draw2d.Color(0,0,0);
	// this.color = new draw2d.Color(0,0,0);
	this.fontSize = 10;
	this.textNode = null;
	this.align = "left";
	org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.call(this, msg);
};
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AnOperationLabelFigure.prototype = new org.eclipse.emf.cdo.dawn.web.basic.DawnLabel;
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AnOperationLabelFigure.prototype.type = "org.eclipse.emf.cdo.dawn.examples.acore.diagram.AnOperationLabelFigure";
