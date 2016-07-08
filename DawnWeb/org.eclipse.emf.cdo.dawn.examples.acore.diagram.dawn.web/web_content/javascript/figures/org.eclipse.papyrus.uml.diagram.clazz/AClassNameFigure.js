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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassNameFigure = org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.extend({
    NAME : "org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassNameFigure", 
    
    /**
     * @constructor
     * Creates a new text element.
     * 
    * @param {Object} [attr] the configuration of the shape
      */
    init: function(msg)
    {
    this._super({text: msg});
	this.msg = msg;
	// this.image="dawn/javascript/figures/org.eclipse.emf.cdo.dawn.examples.acore.diagram/icons/full/obj16/AClassNameFigure.gif";
	this.setBackgroundColor(null);
	this.color = new draw2d.util.Color(0, 0, 0);
	this.fontSize = 10;
	this.textNode = null;
	this.align = "left";
	this.stroke = 0;

	this.labelImage = new draw2d.shape.basic.Image({
        path:"renderer/draw2d/javascript/figures/org.eclipse.emf.cdo.dawn.examples.acore.diagram/icons/full/obj16/AClass.png", 
        padding:10,
        width: 20,
        height: 20
    });
   
    
    this.add(this.labelImage, new draw2d.layout.locator.LeftLocator(this));
	
}});

