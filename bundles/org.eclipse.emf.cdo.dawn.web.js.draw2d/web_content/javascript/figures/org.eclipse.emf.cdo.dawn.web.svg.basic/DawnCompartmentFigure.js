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
org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure = draw2d.shape.layout.VerticalLayout.extend({
    NAME : "org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure", 
    
    /**
     * @constructor
     * Creates a new text element.
     * 
    * @param {Object} [attr] the configuration of the shape
      */
    init: function(attr)
    {
    	this._super($.extend({stroke: 2},attr));
    	this.defaultColor = new draw2d.util.Color(230, 230, 250);
		this.setBackgroundColor(this.defaultColor);
    },
    

onFigureLeave : function(_2642)
{

	draw2d.CompartmentFigure.prototype.onFigureLeave.call(this, _2642);
	if (_2642 instanceof draw2d.CompartmentFigure)
	{
		_2642.setBackgroundColor(_2642.defaultColor);
	}
},

onFigureDrop : function(_2643)
{

	this._super( _2643);
	if (_2643 instanceof draw2d.CompartmentFigure)
	{
		_2643.setBackgroundColor(this.getBackgroundColor().darker(0.1));
	}
},

setBackgroundColor : function(color)
{

	this._super(color);
	for ( var i = 0; i < this.children.getSize(); i++)
	{
		var child = this.children.get(i);
		if (child instanceof draw2d.CompartmentFigure)
		{
			child.setBackgroundColor(this.getBackgroundColor().darker(0.1));
		}
	}
}


});