/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others. All
 * rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Martin Fluegge - initial API and implementation
 */
this.operation = function()
{
};
this.delete_operation = function()
{
};
org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener = function(op, delOp)
{
	// op("Pau",23,23);
	this.operation = op;
	this.delete_operation = delOp;
	draw2d.CommandStackEventListener.call(this);
};
org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener.prototype = new draw2d.CommandStackEventListener;
org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener.prototype.type = "DawnCommandListener";
org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener.prototype.stackChanged = function(event)
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
		if (_2c72 instanceof draw2d.CommandAdd)
		{
			log.innerHTML = log.innerHTML + " => ADD Element";
		} else
		{
			if (_2c72 instanceof draw2d.CommandConnect)
			{
				// log.innerHTML = log.innerHTML + " => Connect two Ports";
			} else
			{
				if (_2c72 instanceof draw2d.CommandDelete)
				{
					// log.innerHTML = log.innerHTML + " => Delete Element";
					// alert("delete"+_2c72.figure.id);
					this.delete_operation(_2c72.figure.id);
				} else
				{
					if (_2c72 instanceof draw2d.CommandMove)
					{
						// log.innerHTML = log.innerHTML + " => Moving Element";
						// alert(_2c72.figure.id);
						var x = _2c72.figure.x;
						var y = _2c72.figure.y;
						this.operation(_2c72.figure.id, _2c72.figure.x, _2c72.figure.y);

					} else
					{
						if (_2c72 instanceof draw2d.CommandResize)
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
};