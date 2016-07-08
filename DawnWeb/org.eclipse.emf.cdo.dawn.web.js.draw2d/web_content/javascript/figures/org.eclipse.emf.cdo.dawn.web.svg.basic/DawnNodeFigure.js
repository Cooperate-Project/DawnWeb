/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others. All
 * rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Martin Fluegge - initial API and implementation
 */
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure = draw2d.shape.layout.VerticalLayout.extend({
    NAME : "org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure", 
	
	
    /**
     * @constructor
     * Creates a new composite element which are not assigned to any canvas.
     * 
     * @param {String} [className] the name of the Figure
    */
	init: function(attr, setter, getter)
	{
	this._super($.extend({
		stroke: 1,
		minWidth: 100,
        resizeable:false
    },attr), setter, getter);

	this.outputPort1 = null;
	this.outputPort2 = null;
	
	
	// avoid "undefined" values. This breaks the code on iOS.
    if(typeof className ==="undefined"){
        this.className = null;
    }
    else{
        this.className = className;
    }
    
    this.initPorts();
    
	
	
	},

	/*******************************************************************************
 * setWorkflow
 * 
 * @description sets the inharitancePorts and the size
 ******************************************************************************/
initPorts : function()
{

	if (this.portTop == null)
	{
		this.portTop = new org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort();
		this.addPort(this.portTop, 0, 0);

		this.portRight = new org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort();
		this.addPort(this.portRight, 0, 0);

		this.portBottom = new org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort();
		this.addPort(this.portBottom, 0, 0);

		this.portLeft = new org.eclipse.emf.cdo.dawn.web.basic.DawnInheritancePort();
		this.addPort(this.portLeft, 0, 0);

		this.repaint();
	}
},

/*******************************************************************************
 * 
 ******************************************************************************/
setId : function(/* :String */name)
{
	this.id = name;
},

getMinWidth: function()
{
  return Math.max(this.minWidth, this._super());
},

/*******************************************************************************
 * Adjust the ports if the user resize the element
 * 
 ******************************************************************************/
setDimension : function(/* :int */w, /* :int */
h)
{
		this._super(w, h);

	if (this.portTop != null)
	{
		this.portTop.setPosition(this.width / 2, 0);
		this.portRight.setPosition(this.width, this.height / 2);
		this.portBottom.setPosition(this.width / 2, this.height);
		this.portLeft.setPosition(0, this.height / 2);
	}
}





});
