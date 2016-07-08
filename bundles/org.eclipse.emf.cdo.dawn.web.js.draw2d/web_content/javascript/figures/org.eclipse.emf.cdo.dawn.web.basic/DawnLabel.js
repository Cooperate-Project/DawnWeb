/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others. All
 * rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Martin Fluegge - initial API and implementation
 */
org.eclipse.emf.cdo.dawn.web.basic.DawnLabel = function(msg)
{
	draw2d.Label.call(this);
	this.msg = msg;

	// this.bgColor = null;
	// this.color = new draw2d.Color(0, 0, 0);
	this.fontSize = 10;
	this.textNode = null;
	this.align = "left";

};
org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.setImage = function(image)
{
	this.image = image;
};

org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype = new draw2d.Label;
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.type =
// "org.eclipse.emf.cdo.dawn.web.basic.DawnLabel";
org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.type = "DawnLabel";
org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.createHTMLElement = function()
{
	var item = draw2d.Label.prototype.createHTMLElement.call(this);
	item.style.whiteSpace = "noWrap";

	if (this.image != null)
	{
		this.img = document.createElement("img");
		this.img.src = this.image;

		item.appendChild(this.img);
	}
	// this.textNode = document.createTextNode(this.msg);
	// alert(this.textNode);
	item.appendChild(this.textNode);
	item.style.color = this.color.getHTMLStyle();
	item.style.position = "relative";
	item.style.fontSize = this.fontSize + "pt";
	item.style.width = "auto";
	item.style.height = "auto";
	item.style.paddingLeft = "2px";
	item.style.paddingRight = "3px";
	item.style.textAlign = this.align;

	if (this.bgColor != null)
	{
		item.style.backgroundColor = this.bgColor.getHTMLStyle();
	}
	 //item.ondblclick = this.onDoubleClickHandler;
	var me=this;
	item.ondblclick = function()
	{
		org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.onDoubleClickHandler.call(this,me);
	}
//alert(this.textNode);
	return item;
}

org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.onDoubleClickHandler = function(obj)
{
	
	//alert(this+ " "+obj.msg);
	var textNode = this.childNodes[1];

	if (textNode == undefined)
	{
		textNode = this.childNodes[0];
	}
	var value = textNode.nodeValue;
	var node = document.createElement('input');
	//node.onkeydown = keyPressed;
	
	node.onkeydown = function(event)
	{
		//alert(event);
		keyPressed(event, this, obj);
	}
	node.value = value;

	this.replaceChild(node, textNode);
	node.focus();
}

org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setSemanticElementId=function(id)
{
 this.semanticElementId=id;
}

org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setFeatureId=function(id)
{
 this.featureId=id;
}

org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.getTextNode = function()
{
	return this.textNode;
}

function finishEditing(labelElement, obj)
{
	var parent = labelElement.parentNode;
	var newValue = labelElement.value;
	org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.createHTMLElement();
	var newNode = document.createTextNode(newValue);
	parent.replaceChild(newNode, labelElement);
	DawnWebUtil.changeValue(obj.semanticElementId, obj.featureId, newValue);
}

function keyPressed(event, label, obj)
{
	if (!event.which)
		event = window.event;
	if (navigator.appName == "Microsoft Internet Explorer")
	{
		if (event.which.keyCode == 13)
		{
			finishEditing(label, obj)
		}
	} else if (navigator.appName != "Microsoft Internet Explorer")
	{
		if (event.which == 13)
		{
			finishEditing(label, obj)
		}
	}
}

// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.isResizeable =
// function()
// {
// return false;
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setWordwrap =
// function(flag)
// {
// this.html.style.whiteSpace = flag ? "wrap" : "nowrap";
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setAlign =
// function(align)
// {
// this.align = align;
// this.html.style.textAlign = align;
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setBackgroundColor =
// function(color)
// {
// this.bgColor = color;
// if (this.bgColor != null)
// {
// this.html.style.backgroundColor = this.bgColor.getHTMLStyle();
// } else
// {
// this.html.style.backgroundColor = "transparent";
// }
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setColor =
// function(color)
// {
// this.color = color;
// this.html.style.color = this.color.getHTMLStyle();
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setFontSize =
// function(size)
// {
// this.fontSize = size;
// this.html.style.fontSize = this.fontSize + "pt";
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.getWidth = function()
// {
// if (window.getComputedStyle)
// {
// return parseInt(getComputedStyle(this.html, "").getPropertyValue(
// "width"));
// }
// return parseInt(this.html.clientWidth);
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.getHeight = function()
// {
// if (window.getComputedStyle)
// {
// return parseInt(getComputedStyle(this.html, "").getPropertyValue(
// "height"));
// }
// return parseInt(this.html.clientHeight);
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.getText = function()
// {
// this.msg = text;
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setText =
// function(text)
// {
// this.msg = text;
// this.html.removeChild(this.textNode);
// this.textNode = document.createTextNode(this.msg);
// this.html.appendChild(this.textNode);
// };
// org.eclipse.emf.cdo.dawn.web.basic.DawnLabel.prototype.setStyledText =
// function(text)
// {
// this.msg = text;
// this.html.removeChild(this.textNode);
// this.textNode = document.createElement("div");
// this.textNode.style.whiteSpace = "nowrap";
// this.textNode.innerHTML = text;
// this.html.appendChild(this.textNode);
// };
