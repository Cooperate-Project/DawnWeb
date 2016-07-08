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
org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort = function()
{
	draw2d.Port.call(this, new draw2d.Rectangle());
	this.setBackgroundColor(new draw2d.Color(255, 255, 255));
	this.setColor(new draw2d.Color(220, 220, 220));
	this.setDimension(1, 1);
}

org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort.prototype = new draw2d.Port;
/** @private * */
org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort.prototype.type = "org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort";

/**
 * @private
 * @param {draw2d.Port}
 *            port The port on which this port has been droped
 */
org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort.prototype.onDrop = function(/* :draw2d.Port */port)
{
	if (this.parentNode.id == port.parentNode.id)
	{
		// same parentNode -> do nothing
	} else
	{
		var command = new draw2d.CommandConnect(this.parentNode.workflow, this, port);
		// command.setConnection(new draw2d.shape.uml.InheritanceConnection());
		// command.setConnection(new
		// org.eclipse.emf.cdo.dawn.web.basic.uml.KlasseErbendeKlasseFigure());
		command.setConnection(new org.eclipse.emf.cdo.dawn.web.basic.DawnConnectionFigure());
		this.parentNode.workflow.getCommandStack().execute(command);
	}
}
