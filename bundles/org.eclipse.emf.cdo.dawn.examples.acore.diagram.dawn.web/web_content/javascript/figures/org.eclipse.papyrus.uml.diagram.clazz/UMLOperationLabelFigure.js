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
org.eclipse.emf.cdo.dawn.examples.acore.diagram.UMLOperationLabelFigure = org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.extend({
    NAME : "org.eclipse.emf.cdo.dawn.examples.acore.diagram.UMLOperationLabelFigure", 
    
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
	    this.fontSize = 10;
	    this.textNode = null;
	    this.align = "left";
    }
});
