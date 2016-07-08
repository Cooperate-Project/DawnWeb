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
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure = org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigure.extend({
    NAME : "org.eclipse.emf.cdo.dawn.web.basic.DawnNodeRoundedFigure", 
	
	
    /**
     * @constructor
     * Creates a new composite element which are not assigned to any canvas.
     * 
     * @param {String} [className] the name of the Figure
    */
	init: function(className, round)
	{
	this._super({
        radius: round,
        className: className
    });
}


});
