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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassAssociationClassesFigure = org.eclipse.emf.cdo.dawn.web.basic.DawnConnectionFigure.extend({
NAME : "org.eclipse.emf.cdo.dawn.examples.acore.diagram.AClassAssociationClassesFigure", 
    
    /**
     * @constructor
     * Creates a new text element.
     * 
    * @param {Object} [attr] the configuration of the shape
      */
    init: function(attr)
    {
    	this._super($.extend({
    		router: new draw2d.layout.connection.VertexRouter,
    			}, attr));
    }
	

});


