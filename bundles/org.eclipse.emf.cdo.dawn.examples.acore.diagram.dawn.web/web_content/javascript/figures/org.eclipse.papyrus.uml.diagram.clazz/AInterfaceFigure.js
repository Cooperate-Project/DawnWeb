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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AInterfaceFigure = org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.extend({
    NAME : "org.eclipse.emf.cdo.dawn.examples.acore.diagram.AInterfaceFigure", 
    
    /**
     * @constructor
     * Creates a new text element.
     * 
    * @param {Object} [attr] the configuration of the shape
      */
    init: function(className)
    {
    this._super(className, 12);

    
	this.outputPort1 = null;
	this.outputPort2 = null;
	this.setDimension(100, 100);
	this.setResizeable(false);
	this.setBackgroundColor(250,250,190);
	this.setStroke(2);
	this.setClassName(className);

},

createHTMLElement : function()
{
	var item = org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure.prototype.createHTMLElement.call(this);
	item.style.border = "1px solid #B0B0B0";
	item.style.backgroundColor = "rgb(250,250,190)";
	return item;
}
});