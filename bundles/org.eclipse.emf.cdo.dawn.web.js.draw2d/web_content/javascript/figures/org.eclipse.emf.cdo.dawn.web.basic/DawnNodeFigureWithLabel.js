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
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel = function(/* :String */className)
{
	draw2d.Node.call(this);

	this.outputPort1 = null;
	this.outputPort2 = null;
	this.setDimension(50, 50);
	this.setResizeable(false);

	this.setClassName(className);

}

// org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype = new
// draw2d.Node;
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype = new draw2d.CompartmentFigure;
/** @private * */
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.type = "org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel";

/*******************************************************************************
 * setWorkflow
 * 
 * @description sets the inharitancePorts and the size
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.setWorkflow = function(
/* :draw2d.Workflow */workflow)
{

	draw2d.CompartmentFigure.prototype.setWorkflow.call(this, workflow);

	if (workflow != null && this.portTop == null)
	{
		this.portTop = new net.mftech.gmf.DawnInheritancePort();
		this.portTop.setWorkflow(workflow);
		this.addPort(this.portTop, 0, 0);

		this.portRight = new net.mftech.gmf.DawnInheritancePort();
		this.portRight.setWorkflow(workflow);
		this.addPort(this.portRight, 0, 0);

		this.portBottom = new net.mftech.gmf.DawnInheritancePort();
		this.portBottom.setWorkflow(workflow);
		this.addPort(this.portBottom, 0, 0);

		this.portLeft = new net.mftech.gmf.DawnInheritancePort();
		this.portLeft.setWorkflow(workflow);
		this.addPort(this.portLeft, 0, 0);

		this.recalculateSize();
	}
}
/*******************************************************************************
 * 
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.setClassName = function(
/* :String */name)
{

	this.headerLabel.style.whiteSpace = "nowrap";
	this.headerLabel.innerHTML = "<img src=\"javascript/dawn/gmf/de.mf.eclipse.plugins.dawn.diagram/icons/full/obj16/Klasse.gif\" alt=\"_\">"
			+ name;

	this.recalculateSize();
}

/*******************************************************************************
 * Adjust the ports if the user resize the element
 * 
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.setDimension = function(/* :int */w, /* :int */
h)
{

	draw2d.CompartmentFigure.prototype.setDimension.call(this, w, h);

	if (this.portTop != null)
	{
		this.portTop.setPosition(this.width / 2, 0);
		this.portRight.setPosition(this.width, this.height / 2);
		this.portBottom.setPosition(this.width / 2, this.height);
		this.portLeft.setPosition(0, this.height / 2);
	}
}

/*******************************************************************************
 * Create the figure. The figure is a simple HTML table with a "tr" for the
 * header and a "tr" for each attribute of the Class.
 * 
 * @private
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.createHTMLElement = function()
{
	var item = document.createElement('div');
	item.id = this.id;
	item.style.position = "absolute";
	item.style.left = this.x + "px";
	item.style.top = this.y + "px";
	item.style.height = this.width + "px";
	item.style.width = this.height + "px";
	item.style.margin = "0px";
	item.style.padding = "0px";
	item.style.outline = "none";
	item.style.border = "1px solid #B0B0B0";
	item.style.zIndex = "" + draw2d.Figure.ZOrderBaseIndex;
	item.style.backgroundColor = "rgb(255,255,255)";

	this.table = document.createElement("table");
	this.table.style.width = "100%";
	this.table.style.height = "100%";
	this.table.style.margin = "0px";
	this.table.style.padding = "0px";

	item.appendChild(this.table);
	var tableBody = document.createElement("tbody");

	this.table.appendChild(tableBody);
	var header = document.createElement("tr");

	tableBody.appendChild(header);
	this.headerLabel = document.createElement("td");
	this.headerLabel.style.align = "left";
	this.headerLabel.style.verticalAlign = "top";
	this.headerLabel.style.padding = "0px";
	this.headerLabel.style.textAlign = "left";
	this.headerLabel.style.fontFamily = "Tahoma";
	this.headerLabel.style.fontSize = "10pt";
	this.headerLabel.style.borderBottom = "1px solid black";
	this.headerLabel.style.height = "5px";

	header.appendChild(this.headerLabel);
	// this.headerLabel.innerHTML = "dfgfg";

	this.body = tableBody;

	for ( var i = 0; i < this.getChildren().getSize(); i++)
	{

		alert(i);
	}

	return item;
}
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.addChild = function(child)
{
	child.setParent(this);
	var bodyTR = document.createElement("tr");

	this.compartment1 = document.createElement("td");
	this.compartment1.style.verticalAlign = "top";
	this.compartment1.style.padding = "0px";

	this.compartment1.style.textAlign = "left";
	this.compartment1.style.fontFamily = "Tahoma";
	this.compartment1.style.fontSize = "10pt";
	// child.setDimension(1000,this.width);
	this.compartment1.appendChild(child.createHTMLElement());

	bodyTR.appendChild(this.compartment1);
	this.body.appendChild(bodyTR);

}
/*******************************************************************************
 * Recalculate and set the real dimension of the element.
 * 
 * @private
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.recalculateSize = function(
/* :String */name)
{
	this.setDimension(this.getWidth(), this.getHeight());
}

/*******************************************************************************
 * The figures is not resizeable by the user. So - we calculate the real size of
 * the figure. This depends on the content of the figure.
 * 
 * @private
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.getWidth = function()
{

	if (this.workflow == null)
		return 10;

	if (this.table.xgetBoundingClientRect)
		return this.table.getBoundingClientRect().right - this.table.getBoundingClientRect().left;
	else if (document.getBoxObjectFor)
		return document.getBoxObjectFor(this.table).width;
	else
		return this.table.offsetWidth;
}

/*******************************************************************************
 * The figures is not resizeable by the user. So - we calculate the real size of
 * the figure. This depends on the content of the figure.
 * 
 * @privates
 ******************************************************************************/
org.eclipse.emf.cdo.dawn.web.basic.DawnNodeFigureWithLabel.prototype.getHeight = function()
{

	if (this.workflow == null)
		return 10;

	if (this.table.xgetBoundingClientRect)
		return this.table.getBoundingClientRect().bottom - this.table.getBoundingClientRect().top;
	else if (document.getBoxObjectFor)
		return document.getBoxObjectFor(this.table).height;
	else
		return this.table.offsetHeight;
}
