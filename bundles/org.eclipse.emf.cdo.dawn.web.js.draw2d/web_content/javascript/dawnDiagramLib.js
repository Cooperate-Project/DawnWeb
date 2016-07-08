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
/**
 * var http = null; if (window.XMLHttpRequest) { http = new XMLHttpRequest(); }
 * else if (window.ActiveXObject) { http = new
 * ActiveXObject("Microsoft.XMLHTTP"); }
 */
timer = 500;
interval = window.setInterval(function()
{
	getDiagramData();
}, timer);

var DawnWebUtil = new Object();
// DawnWebUtil.resourceURI="";
// DawnWebUtil.resourceLastChanged;

/*******************************************************************************
 * 
 ******************************************************************************/
DawnWebUtil.init = function(uri, lastChanged)
{
	this.resourceURI = uri;
	this.resourceLastChanged = lastChanged;
}
/*******************************************************************************
 * 
 ******************************************************************************/
function getDiagramData()
{
	if (DawnWebUtil.resourceURI != undefined)
	{
		var newProjectVersion = getVersionFromProject()

		if (DawnWebUtil.resourceLastChanged != newProjectVersion)
		{
			// alert(DawnWebUtil.resourceLastChanged + "!=" +
			// newProjectVersion);
			DawnWebUtil.resourceLastChanged = newProjectVersion;
			location.reload();
		}
	}
}
/*******************************************************************************
 * 
 ******************************************************************************/
function getVersionFromProject()
{

	var ret;
	new jQuery.ajax("getVersionFromProject",
	{
		method : 'post',
		data :
		{
			resourceURI : DawnWebUtil.resourceURI
		},

		async : false,
		success : function(data, status, jqXHR )
		{
			ret = data;
		},
		error : function()
		{
			alert('Exception in JSON Call ')
		}
		
	});
	return ret;
}
/*******************************************************************************
 * 
 ******************************************************************************/
DawnWebUtil.moveNode = function(id, x, y)
{
	var command = "changeResource?resourceURI=" + DawnWebUtil.resourceURI + "&method=moveNode&uuid=" + id + "&x=" + x
			+ "&y=" + y;
	DawnWebUtil.sendCommand(command);
}

DawnWebUtil.deleteNode = function(id)
{
	var command = "changeResource?resourceURI=" + DawnWebUtil.resourceURI + "&method=deleteView&uuid=" + id;
	DawnWebUtil.sendCommand(command);
}

DawnWebUtil.sendCommand = function(command)
{
	var ret;
	new jQuery.ajax(command,
	{
		method : 'get',
		async : false,
		success : function(data, status, jqXHR)
		{
			ret = data;
		},
		error : function()
		{
			alert('Exception in JSON Call ')
		}
	});
	return ret;
}


DawnWebUtil.changeValue = function(uuid, featureId, value)
{
	//alert(uuid+ " / "+featureId+ " / "+value);
	var command = "changeResource?resourceURI=" + DawnWebUtil.resourceURI + "&method=changeFeature&uuid=" + uuid+"&featureId="+featureId+"&value="+value;
	DawnWebUtil.sendCommand(command);
}
