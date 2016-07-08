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
org.eclipse.emf.cdo.dawn.web.basic.DawnConnectionFigure = function()
{
	draw2d.Connection.call(this);

	// this.setTargetDecorator(new
	// org.eclipse.emf.cdo.dawn.web.basic.DawnBasicConnectionDecorator());

	this.setSourceAnchor(new draw2d.ChopboxConnectionAnchor());
	this.setTargetAnchor(new draw2d.ChopboxConnectionAnchor());
	this.setRouter(new draw2d.NullConnectionRouter());
}

org.eclipse.emf.cdo.dawn.web.basic.DawnConnectionFigure.prototype = new draw2d.Connection();

/*******************************************************************************
 * 
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnConnectionFigure.prototype.setId = function(/* :String */name)
{
	this.id = name;
}
