<!-- This comment keeps IE6/7 in the reliable quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	String classif = request.getParameter("classif");
	if (classif==null) classif = "SOC";
	
	HashMap map = new HashMap();
	map.put("SOC","Standard Occupational Classification");
	map.put("SIC","Standard Industrial Classification of Economic Activities");
	map.put("NQF","National Qualifications Framework");
	map.put("SDS","Single Degree Subject Classification");
	
	String xmlTree = "../xml/classif_" + classif + ".xml";
	String strTitle = (String)map.get(classif);
%>
<%@page import="java.util.HashMap"%>
<html>
<head>
	<title>L4ALL - Classifications</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<style type="text/css">
			<!-- 
				@import "../css/skidoo_redux.css"; 
				@import "../css/skidoo_redux_theme.css";
				@import "../css/skidoo_l4all.css";
			-->
	</style>
	<style type="text/css">
			#outer-column-container, #inner-column-container { border-right-width: 0; }
			#right-column {	display: none; }
			#source-order-container { margin: 0; }
			#masthead
			{
				background-image: url("../images/l4all.png");
				background-repeat: repeat;
				background-position: 0% 0%;
			}
			#footer
			{
				background-color: #ffefd5;	/* Administration footer background */
			}
	</style>
	<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
	
	<title>Zapatec DHTML Tree Widget - Dynamically load data from server</title>

	<!-- Common JS files -->
	<script type='text/javascript' src='../zapatec/utils/zapatec.js'></script>

	<!-- Custom includes -->	
	<script type='text/javascript' src='../zapatec/zptree/src/tree.js'></script>

	<!-- ALL demos need these css -->
	<link href="../../website/css/zpcal.css" rel="stylesheet" type="text/css">
	<link href="../../website/css/template.css" rel="stylesheet" type="text/css">
	<style type="text/css">
		body {
			width: 778px;
		}
	</style>	

<script type="text/javascript">

function openClassif(doc)
{
	document.location.href= "viewTree.jsp?classif="+doc;
}

function tonOpen()
{
		var ctx = document.getElementById("treeboxbox_help");
	if (this!=null)
	{
		dd = this.data.attributes['id'];
		ctx.innerHTML= dd + " - " + this.data.label;
	}
	else
		ctx.innerHTML= "";
}

var myres = [];

function displayResult1(result){
		var ctx = document.getElementById("gonext");
		if(result == null || result.length == 0){
			alert("No node found");
			myres = [];
			if (ctx!=null) ctx.disabled=true;
		} else {
			myres = result;
			var tt = myres.shift();
			if (tt!=null)
				tt.sync();
			if (ctx!=null) ctx.disabled=false;
		}
}

function next()
{
		var ctx = document.getElementById("gonext");
		if(myres == null || myres.length == 0){
			alert("No node found");
			if (ctx!=null) ctx.disabled=true;
		} else {
			var tt = myres.shift();
			//alert(tt.data.label);
			if (tt!=null)
				tt.sync();
			if (ctx!=null) ctx.disabled=false;
		}
}

</script>
</head>

<body onload="">

<div id="page-container">
<div id="masthead">
	<div class="inside">
	<h1>&nbsp;</h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column"><div class="inside">

		<h2><%= strTitle%></h2>
		
		<div id="treeboxbox_search" style="background-color:#f5f5f5;border :1px solid Silver;border-bottom:none; overflow:auto; font-size: x-small;padding: 3px">
		<div style="float: left;">
			<form name="form_search" method="get" action="javascript:search();">	
			Search for: <input name="criteria" type="text" size="10" style="font-size: xx-small;">
			</form>
			</div>
		<div style="float: left;">
			<button id="gonext" onclick="javacript:next()" style="font-size: xx-small;">Next &gt;</button>
			</div>
		</div>
		<div id="treeboxbox_tree" style="height:300px; background-color:white;border :1px solid Silver;; overflow:auto;">
			<ul id="tree" class="zpLoadXML=../xml/classif_test.xml">
			</ul>
		</div>
		<div id="treeboxbox_help" style="height:40px; background-color:#f5f5f5;border :1px solid Silver;border-top:none; overflow:auto; font-size: x-small;">
		</div>
			
		<noscript>
		This page uses an <a href='http://www.zapatec.com/website/main/products/suite/'>
		AJAX Component</a> - Zapatec DHTML Tree Widget, but your browser does not support Javascript.
		</noscript>      
		<div style="text-align:right; font-size: x-small; padding: 0px 5px 0px 5px;">
			&copy; 2004-2007 <strong> <a href='http://www.zapatec.com/'>Zapatec, Inc.</a> </strong>
		</div>

    	</div></div>
    	
		<div id="left-column"><div class="inside">
			
			<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
				<li><A href="#" onclick="document.location.href='index.jsp';return false;">Go Back to Panel</A></li>
			</ul>
			<p class="fontsize-set"></p>

			<h3>Classifications</h3>
			<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
				<li><a href="javascript:void(0);" onclick="openClassif('SOC');return false;">Occupations</a></li>
				<li><a href="javascript:void(0);" onclick="openClassif('SIC');return false;">Industry Sectors</A></li>
				<li><a href="javascript:void(0);" onclick="openClassif('NQF');return false;">Qualifications</a></li>
				<li><a href="javascript:void(0);" onclick="openClassif('SDS');return false;">Degree Subjects</a></li>
			</ul>
		</div></div>
		
		<div class="clear-columns"><!-- do not delete --></div>

	</div>
	<div id="right-column">
	<div class="inside">
		<h3>Highlights</h3>
	</div>
	</div>
	<div class="clear-columns"><!-- do not delete --></div>
	</div>
</div>

	<div id="footer"><div class="inside">
		<p><i>L4ALL © Birkbeck College - 2007</i></p>
	</div></div>

</div>

<script type="text/javascript">
	var tree = new Zapatec.Tree({
		tree: "tree",
		expandOnLabelClick: true,
		highlightSelectedNode: true,
		compact: true,
		eventListeners: {
            'select': tonOpen
        },
	});
		
	tree.rootNode.config.source = "<%= xmlTree%>";
	tree.rootNode.config.sourceType="xml/url"; 
	tree.rootNode.loadData()

	var ctx = document.getElementById("gonext");
	if (ctx!=null) ctx.disabled=true;
	

function search()
{
	var tt = document.forms['form_search'].elements['criteria']
	if (tt.value!= "")
	{	var cccc= tt.value;
		displayResult1(tree.findAll(function(node){return node.data.label.match(eval("/"+cccc+"/i"))}));
	}
	else alert("You need to type sometinhg here");
}

	function loadJSON(jsonObj){
		tree.rootNode.config.source = jsonObj;
		tree.rootNode.config.sourceType="json"; 
		tree.rootNode.loadData()
	}

	function loadHTML(htmlString){
		tree.rootNode.config.source = htmlString;
		tree.rootNode.config.sourceType="html/text"; 
		tree.rootNode.loadData()
	}

	function loadXML(xmlString){
		tree.rootNode.config.source = xmlString;
		tree.rootNode.config.sourceType="xml"; 
		tree.rootNode.loadData()
	}

	if(document.location.toString().indexOf('http') != 0) {
		alert(
			'"slow loading" branch emulates a slow internet connection. It loads the branch with delay of 5 seconds. \n' + 
			'To test it - you should put this demo on the directory on your web-server where executing PHP scripts is allowed'
		);
	}
</script>

</body>

</html>