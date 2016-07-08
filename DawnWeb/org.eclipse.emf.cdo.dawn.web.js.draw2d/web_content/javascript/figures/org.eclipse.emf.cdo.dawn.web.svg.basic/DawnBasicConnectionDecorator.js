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
org.eclipse.emf.cdo.dawn.web.basic.DawnBasicConnectionDecorator =  draw2d.decoration.connection.Decorator.extend({
    NAME : "org.eclipse.emf.cdo.dawn.web.basic.DawnBasicConnectionDecorator", 
    
    /**
     * @constructor
     * Creates a new text element.
     * 
    * @param {Object} [attr] the configuration of the shape
      */
    init: function(attr, setter, getter)
    {
    	this._super(attr, setter, getter);
    	this.setBackgroundColor(new draw2d.util.Color(176, 176, 176));
},

/**
 **/
paint : function(g)
{
	// draw the background
	//
	// g.drawPolygon([3,10,10],[0,5,-5]);
	// if(this.backgroundColor!=null)
	// {
	//	 
	// g.setColor(this.backgroundColor);
	// g.fillPolygon([3,9,9],[0,6,-6]);
	// }
	g.drawLine(0, 0, 8, -5);
	g.drawLine(0, 0, 8, 5);

	// draw the border
	g.setColor(this.color);
	g.setStroke(1);

}
});
