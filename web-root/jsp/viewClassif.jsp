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
	String strTitle = "sss";//(String)map.get(classif);
%>
<%@page import="java.util.HashMap"%>
<html>
<head>
	<title>L4ALL - Classifications</title>
	<style type="text/css">
			<!-- 
				@import "../css/skidoo_redux_theme.css";
			-->
	</style>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<!-- Common JS files -->
	<script type='text/javascript' src='../zapatec/utils/zapatec.js'></script>

	<!-- Custom includes -->	
	<script type='text/javascript' src='../zapatec/zptree/src/tree.js'></script>

<script type="text/javascript">

function openClassif(doc)
{
	document.location.href= "viewTree.jsp?classif="+doc;
}

function onDbClick()
{
	dd = this.data.attributes['id'];
	if (parent.opener) parent.selectItem(dd);
}

function onOpen()
{
	//	var ctx = document.getElementById("treeboxbox_help");
	//if (this!=null)
	//{
	//	dd = this.data.attributes['id'];
	//	ctx.innerHTML= dd + " - " + this.data.label;
	//}
	//else
	//	ctx.innerHTML= "";
	dd = this.data.attributes['id'];
	//if (parent) parent.browseItem(dd + " - " + this.data.label);
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

<body onload="" style="margin: 0;padding: 0;">
<div id="page-container">
	<div id="treeboxbox_search" style="height:21px; background-color:#f5f5f5;border :1px solid Silver;border-bottom:none; overflow:auto; font-size: x-small;padding: 2px">
		<div style="float: left;">
			<form name="form_search" method="get" action="javascript:search();">	
				Search for: <input name="criteria" type="text" size="10" style="height:12px; font-size: xx-small;">
			</form>
		</div>
		<div style="float: left;">
			<button id="gonext" onclick="javacript:next()" style="font-size: xx-small;">Next ></button>
		</div>
		<div style="float: right;">
			<strong><a href='http://www.zapatec.com/' target="_new">&copy; Zapatec, Inc.</a> </strong>
		</div>
	</div>
	<div id="treeboxbox_tree" style=" height:220px; background-color:white;border :1px solid Silver;; overflow:auto; border-bottom:none;">
		<ul id="tree">
		</ul>
	</div>
</div>
<script type="text/javascript">
	var tree = new Zapatec.Tree({
		tree: "tree",
		expandOnLabelClick: true,
		highlightSelectedNode: true,
		compact: true,
		eventListeners: {
            'select': onOpen,
            'labelDblclick': onDbClick,
            'loadDataEnd': test
        }
	});
		
	tree.rootNode.config.source = "<%= xmlTree%>";
	tree.rootNode.config.sourceType="xml/url"; 
	tree.rootNode.loadData(test);

	var ctx = document.getElementById("gonext");
	if (ctx!=null) ctx.disabled=true;
	
function test()
{
}

function search()
{
	var tt = document.forms['form_search'].elements['criteria']
	if (tt.value!= "")
	{	var cccc= tt.value;
		displayResult1(tree.findAll(function(node){return node.data.label.match(eval("/"+cccc+"/i"))}));
	}
	else alert("You need to type sometinhg here");
}
</script>
</body>
</html>