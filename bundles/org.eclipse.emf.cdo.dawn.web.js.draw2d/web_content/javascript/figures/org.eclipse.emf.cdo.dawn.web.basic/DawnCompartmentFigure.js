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
org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure = function()
{

	draw2d.CompartmentFigure.call(this);
	this.defaultColor = new draw2d.Color(230, 230, 250);
	this.setBackgroundColor(this.defaultColor);
};
org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure.prototype = new draw2d.CompartmentFigure;
org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure.prototype.onFigureLeave = function(_2642)
{

	draw2d.CompartmentFigure.prototype.onFigureLeave.call(this, _2642);
	if (_2642 instanceof draw2d.CompartmentFigure)
	{
		_2642.setBackgroundColor(_2642.defaultColor);
	}
};
org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure.prototype.onFigureDrop = function(_2643)
{

	draw2d.CompartmentFigure.prototype.onFigureDrop.call(this, _2643);
	if (_2643 instanceof draw2d.CompartmentFigure)
	{
		_2643.setBackgroundColor(this.getBackgroundColor().darker(0.1));
	}
};
org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure.prototype.setBackgroundColor = function(color)
{

	draw2d.CompartmentFigure.prototype.setBackgroundColor.call(this, color);
	for ( var i = 0; i < this.children.getSize(); i++)
	{
		var child = this.children.get(i);
		if (child instanceof draw2d.CompartmentFigure)
		{
			child.setBackgroundColor(this.getBackgroundColor().darker(0.1));
		}
	}
};

org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure.prototype.createHTMLElement = function()
{

	var item = draw2d.Figure.prototype.createHTMLElement.call(this);
	item.id = this.id;
	item.style.position = "relative";
	// item.style.left = this.x + "px";
	// item.style.top = this.y + "px";
	// item.style.height = this.width + "px";
	// item.style.width = this.height + "px";
	item.style.margin = "0px";
	item.style.padding = "0px";
	item.style.outline = "none";
	item.style.border = "1px solid #B0B0B0";
	item.style.zIndex = "" + draw2d.Figure.ZOrderBaseIndex;
	// item.style.backgroundColor = "rgb(255,255,255)";
	//
	this.table = document.createElement("table");
	// this.table.border="1";
	// this.table.border = "1px solid #B0B0B0";
	this.table.style.width = "100%";
	this.table.style.height = "100%";
	this.table.style.margin = "0px";
	this.table.style.padding = "0px";
	item.appendChild(this.table);

	item.style.height = this.table.style.height;
	item.style.width = this.table.style.width;
	//
	var tableBody = document.createElement("tbody");
	this.table.appendChild(tableBody);

	for ( var i = 0; i < this.getChildren().getSize(); i++)
	{

		// var header = document.createElement("tr");
		// header.style.padding = "0px";
		// header.style.margin = "0px";
		//	
		//
		// var innerLabel = document.createElement("td");
		// innerLabel = document.createElement("td");
		// innerLabel.style.verticalAlign = "top";
		// innerLabel.style.padding = "0px";
		//		
		//		
		// innerLabel.style.textAlign = "left";
		// innerLabel.style.fontFamily = "Tahoma";
		// innerLabel.style.fontSize = "10pt";
		var child = this.children.get(i);
		// if (child instanceof draw2d.CompartmentFigure)
		{
			item.appendChild(child.createHTMLElement());

		}
		// header.appendChild(innerLabel);
		// tableBody.appendChild(header);
	}

	return item;
};