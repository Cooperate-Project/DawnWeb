/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others. All
 * rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Martin Fluegge - initial API and implementation
 */
org.eclipse.emf.cdo.dawn.web.basic.DawnLabel = draw2d.shape.basic.Label.extend({
    NAME : "org.eclipse.emf.cdo.dawn.web.basic.DawnLabel", 
    
    /**
     * @constructor
     * Creates a new text element.
     * 
    * @param {Object} [attr] the configuration of the shape
      */
    init: function(attr, setter, getter)
	{
    	this._super($.extend({fontSize:14, height:25, stroke: 0},attr), setter, getter);
    	this.installEditPolicy(new draw2d.policy.figure.WidthSelectionFeedbackPolicy());
	},

setImage : function(image)
{
	this.image = image;
},


setSemanticElementId : function(id)
{
 this.semanticElementId=id;
},

setFeatureId: function(id)
{
 this.featureId=id;
},

/*setText : function(text)
{
	this._super(text);
	
	DawnWebUtil.changeValue(this.semanticElementId, this.featureId, text);
	return this;
}*/



});
