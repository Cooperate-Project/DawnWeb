<%@ page contentType="text/css" %>
<%--<jsp:useBean id="layout" class ="de.killaqueen.mediabase.global.Layout" scope="application" />--%>

/* basic-layout */
body {
  min-width: 550px;         /* 2 x LC width + RC width */
  
}
#container {
  padding-left: 210px;      /* LC width */
  padding-right: 150px;     /* RC width */

}
		
#container .column {
  position: relative;
  float: left;
  
}


		
#center {

  width: 100%;
}
		
#left {
  width: 200px;             /* LC width */
  right: 200px;             /* LC width */
  margin-left: -100%;
}

#left a:active
{
 text-decoration:none; 
 font-weight:bold; 


}
		
#right {
  width: 150px;             /* RC width */
  margin-right: -100%;
}
		
		
/*** IE6 Fix ***/
* html #left {
  left: 150px;              /* RC width */
}



/*** Just for Looks ***/
body {
  margin: 0;
  padding: 0;
  background: #FFF;
  
  color: black;
  font-size: 10pt;
  font-family: Helvetica,Arial,sans-serif;
}

img {
  border:0;
}

#header {
  background: #999;
}


/*----------------------------------header Part-----------------*/
div#header {
  font-size: 16pt;
  margin: 0 0 0; padding: 0;
  text-align: center;
  background:url("../images/headerNew_blue.png");
  border: 2px ridge silver;
  height:80px;
}
div#header h1 {
  border-color: gray;  /* Farbangleichung an den Internet Explorer  */
  color: #000000;
  font-size: 23pt;
}


/*----------------------------------subheader Part-----------------*/
#subheader {
  float:left;
  width:100%;
  background:#DAE0D2 url("../images/tabs/bg.gif") repeat-x bottom;
  line-height:normal;
}
#subheader ul {
  margin:0;
  margin-left:195px;
  padding:10px 10px 0;
  list-style:none;
}
#subheader li {
  float:left;
  background:url("../images/tabs/left.gif") no-repeat left top;
  margin:0;
  padding:0 0 0 9px;
}
#subheader a {
  float:left;
  display:block;
  background:url("../images/tabs/right.gif") no-repeat right top;
  padding:5px 15px 4px 6px;
  text-decoration:none;
  font-weight:bold;
  color:#765;
}



/* Commented Backslash Hack
   hides rule from IE5-Mac \*/
#subheader a {float:none;}
/* End IE5-Mac hack */
#subheader a:hover {
  color:#333;
}
#subheader #current li{
  background-image:url("../images/tabs/left_on.gif");
  border-width:0;
}
#subheader #current a {
  background-image:url("../images/tabs/right_on.gif");
  color:#333;
  padding-bottom:5px;
}




/*----------------------------------footer Part-----------------*/
#footer {
 position:fixed;
 bottom:0px;
 width:100%;
 clear: both;
 font-size: 8pt;
 margin: 5px; 
 padding: 8px;
 text-align: center;
 margin-top:1em;

  background:url("../images/infoHeader_blue.png") repeat-x #fff;

  border: 1px solid #b0b0b0;
}


  
/*----------------------------------Navigation Part-----------------*/
div#Navigation {
  margin: 0px; 

}

ul.navi {
  float: left; 
  width: 190px;
  margin: 0; 
  padding: 0;
  border-top: 1px solid #b0b0b0;
}

ul.navi li {
  list-style: none;
  margin: 0;
  padding: 0;
  border: 1px solid #b0b0b0;
  border-top:0;
}

ul.navi a { 
  color: black;
  display: block;
  font-weight: bold;
  padding: 5px 10px;
  text-decoration:none;
  background:url("../images/infoHeader_blue.png") repeat-x #fff;
}

/*** Sub-Navigation-Left ***/
ul.navi_sub {
  padding: 0;
  border-bottom: 0px;
}

ul.navi_sub li {
  list-style: none;
  margin: 0;
  padding: 0;
  border:0px;
  border-top: 1px solid #b0b0b0;
}

ul.navi_sub a {
  display: block;
  padding: 5px;
  padding-left:15px; 
  font-weight: normal;
  text-decoration:none;
  color:#000;  
}

ul.navi a:hover, a:active {
  color: #555; 
}




/* info boxes */
#InfoContent {}



/* form elements */
fieldset {  
  clear: both;  
  margin: 5px;
  padding: 5px;  
  border: 1px solid #b0b0b0;  
  background-image: url("../images/bg01.gif");  
  background-repeat: repeat-x;
}
legend {  
  margin-left: 10px;  
  color: #444;  
  font-weight: bold;
  font-size: 8pt;
}
fieldset.submit {  
  border-style: none;
}
label {  
  display: block;
  padding-top:5px;
  padding-bottom:2px;
}

input, textarea, option, label {
  font-size: 10pt;
  font-family: Helvetica,Arial,sans-serif;
  
}

input:focus, textarea:focus, option:focus {
  background-color:#ededed;
}

input, textarea, select {
  border: 1px solid #b0b0b0;
  padding:1px;
}

/*-------------basics---------------------------*/
#basics fieldset {
  width:290px;
}
#basics input {
  width:280px;
}
#basics textarea {
  width:280px;
  height:187px;
}

/*-------------files---------------------------*/
#files fieldset {
  width:130px;
}
#files input, #files select {
  width:120px;
}



/*-------------additonal Information---------------------------*/
#additonal {
  position:absolute;
  left:311px;
  top:452px;
}
#additonal fieldset {
  width:322px;
}

/*-------------button Field---------------------------*/
#buttonField fieldset {
  border: 0;
}

#basics, #files, #persons, #rating, #additonal {
  float:left;
}


/*-------------Links---------------------------*/


a {
  color:#333;
  text-decoration:none;
}

a:hover {
  text-decoration:underline;
}

/*-------------Headings---------------------------*/

h2 { margin-left:10px; }


h3 {
  color: #333;
  font-size:10pt;
  font-weight:bold;
  margin-bottom: 3px;
}



/*--------------------------- tables ----------------------*/
#persons table
{
	background-color: #FFFFFF; 
}

#persons td {
	background-color: #ffffff;
	text-align: left; 
	vertical-align: top; 
	font-size: smaller;
	font-size : 10pt;
	//line-height:5pt;
}
/*--------------------------- tables ----------------------*/
  table {
	background-color: #f1f1dd; 
	margin-top: 12px; 
	margin-bottom: 12px; 

}

table.header { width: auto }

td, th {
	vertical-align: top; 
	font-size: smaller;
	font-size : 10pt;
}

td.vmiddle { vertical-align: middle }

td.aligncenter { text-align: center }

th {
	padding: 3px;  
	text-align: left; 
	border-bottom: 1px solid black; 
}

td {
	padding: 5px; 
	text-align: left; 
	background-color: #fffff0;
}

td.stub, td.subitem, th {
	white-space: nowrap
}

td.subitem {
	padding-left: 24px
}