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
org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort = draw2d.Port.extend({
    NAME : "org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort", 
    
    init : function()
    {
    	this._super({bgColor: new draw2d.util.Color(255, 255, 255)});
    	this.setColor(new draw2d.util.Color(220, 220, 220));
    	this.setDimension(1, 1);
    },


/**
 * @private
 * @param {draw2d.Port}
 *            port The port on which this port has been droped
 */
onDrop : function(/* :draw2d.Port */port)
{
	if (this.parentNode.id == port.parentNode.id)
	{
		// same parentNode -> do nothing
	} else
	{
		var command = new draw2d.command.CommandConnect(this.parentNode.workflow, this, port);
		// command.setConnection(new draw2d.shape.uml.InheritanceConnection());
		// command.setConnection(new
		// org.eclipse.emf.cdo.dawn.web.basic.uml.KlasseErbendeKlasseFigure());
		command.setConnection(new org.eclipse.emf.cdo.dawn.web.basic.DawnConnectionFigure());
		this.parentNode.workflow.getCommandStack().execute(command);
	}
}
});