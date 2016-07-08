/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others. All
 * rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Martin Fluegge - initial API and implementation
 */
org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener = draw2d.command.CommandStackEventListener.extend({
    NAME : "org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener", 
    
    /**
     * @constructor
     * Creates a new text element.
     * 
    * @param {Object} [attr] the configuration of the shape
      */
    init: function(op, delOp)
    {
    	this._super({});

	// op("Pau",23,23);
	this.operation = op;
	this.delete_operation = delOp;
},


stackChanged : function(event)
{
	var _2c6f = document.getElementById("body");
	var log = document.createElement("div");
	if (event.isPostChangeEvent())
	{
		// log.innerHTML = "POST:";
		// alert("post");
	} 
	else
	{

		var _2c71 = event.getDetails();

		var _2c72 = event.getCommand();
		if (_2c72 instanceof draw2d.command.CommandAdd)
		{
			log.innerHTML = log.innerHTML + " => ADD Element";
		} else
		{
			if (_2c72 instanceof draw2d.command.CommandConnect)
			{
				// log.innerHTML = log.innerHTML + " => Connect two Ports";
			} else
			{
				if (_2c72 instanceof draw2d.command.CommandDelete)
				{
					// log.innerHTML = log.innerHTML + " => Delete Element";
					// alert("delete"+_2c72.figure.id);
					this.delete_operation(_2c72.figure.id);
				} else
				{
					if (_2c72 instanceof draw2d.command.CommandMove)
					{
						// log.innerHTML = log.innerHTML + " => Moving Element";
						// alert(_2c72.figure.id);
						var x = _2c72.figure.x;
						var y = _2c72.figure.y;
						this.operation(_2c72.figure.id, _2c72.figure.x, _2c72.figure.y);

					} else
					{
						if (_2c72 instanceof draw2d.command.CommandResize)
						{
							// log.innerHTML = log.innerHTML + " => Resize
							// Element";
						}
					}
				}
			}
		}
		// log.innerHTML = "PRE:";
		// alert("PRE");
	}
	// _2c6f.appendChild(log);
}
});