<!-- This comment keeps IE6/7 in the reliable quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>

<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.Trails"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	pageContext.setAttribute("username",username);
	String serverURL = "http://" + request.getServerName() + ":" + request.getServerPort() + 
						request.getContextPath() + "/";

	List mylist = null;
	mylist = (List)request.getAttribute("ListSimilar");
	String metricname = (String)request.getAttribute("metric");
	String target = (String)request.getAttribute("data");
	
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0

	pageContext.setAttribute("myTrail",mylist);
	pageContext.setAttribute("metric",metricname);	
	pageContext.setAttribute("target",target);
%>
<html>
<head>
	<title>L4ALL Timelines Search</title>
	<style type="text/css">
		<!-- 
			@import "../css/skidoo_redux.css"; 
			@import "../css/skidoo_redux_theme.css";
			@import "../css/skidoo_l4all.css";
			@import "../css/skidoo_form.css";
			@import "../css/skidoo_popup.css";
		-->
	</style>
	<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">

<script type="text/javascript">
function doIt(simil)
{
	if (window.opener && !window.opener.closed)
	{
		window.opener.showSimilarUser(simil);
		window.opener.focus();
	}
}

function closeMe()
{
	<%session.removeAttribute("ListSimilar");%>
	self.close();
}

</script>

<style type="text/css">    

div.tableContainer {
	width: 100%;		/* table width will be 99% of this*/
	height: 200px; 	/* must be greater than tbody*/
	overflow: auto;
	margin: 0 auto;
	min-width: 300px;
	}

table {
	width: 99%;		/*100% of container produces horiz. scroll in Mozilla*/
	border: none;
	background-color: #f7f7f7;
	}
	
table>tbody	{  /* child selector syntax which IE6 and older do not support*/
	overflow: auto; 
	height: 150px;
	overflow-x: hidden;
	}
	
thead tr	{
	position:relative; 
	top: expression(offsetParent.scrollTop); /*IE5+ only*/
	}
	
thead td, thead th {
	text-align: center;
	font-size: 14px; 
	background-color: oldlace;
	color: steelblue;
	font-weight: bold;
	border-top: solid 1px #d8d8d8;
	border-bottom: solid 2px slategray;
	}	
	
td	{
	color: #000;
	padding-right: 2px;
	font-size: 12px;
	text-align: left;
	border-bottom: solid 1px #d8d8d8;
	border-left: solid 1px #d8d8d8;
	}

tr.expert	{
	background-color: #F7D7D7;
}
	
tfoot td	{
	text-align: center;
	font-size: 11px;
	//font-weight: bold;
	background-color: papayawhip;
	color: steelblue;
	border-top: solid 2px slategray;
	}

td:last-child {padding-right: 20px;} /*prevent Mozilla scrollbar from hiding cell content*/

</style>

<style type="text/css" media="print">
div.tableContainer {overflow: visible;	}
table>tbody	{overflow: visible; }
td {height: 14pt;} /*adds control for test purposes*/
thead td	{font-size: 11pt;	}
tfoot td	{
	text-align: center;
	font-size: 9pt;
	border-bottom: solid 1px slategray;
	}
	
thead	{display: table-header-group;	}
tfoot	{display: table-footer-group;	}
thead th, thead td	{position: static; } 


</style>
</head>


<script type="text/javascript">
function resizeFrame(frame,index){ 
	var htmlheight = document.body.parentNode.scrollHeight; 
	var windowheight = window.innerHeight; 

	//alert("ht="+htmlheight + " , wn=" + windowheight);
	if ( htmlheight < windowheight ) 
		{ frame.style.height = (htmlheight - index) + "px"; } 
	else 
		{ frame.style.height = (windowheight - index) + "px"; } 
	//alert( frame.style.height);
}

function resize()
{ 
	var frame = document.getElementById("cont"); 
	resizeFrame(frame,170);
	frame = document.getElementById("tbod"); 
	resizeFrame(frame,240);
	
}

</script>

<xtags:parse>
	<%=(target!=null)? target : "<main_message></main_message>"%>
</xtags:parse>

<body onresize="resize();">

<div id="page-container">

<div id="masthead"><div class="inside">
	<h1>Search for people like me</h1>
</div></div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column"><div class="inside">
			
	<!-- Start Search Table -->			
	<div class="tableContainer" id="cont">
	<table>
	<thead><tr>
		<th>Timeline</th>
		<th>Name</th>
		<th>Encoding</th>
		<th>Relevance</th>
	</tr></thead>
	<tfoot><tr> 
    	<td colspan="4">
			<xtags:choose>
				<xtags:when test="main_message/message_content/users/@cardinality != 0">
					Search results: <b><xtags:valueOf select="main_message/message_content/users/@cardinality"/></b>
					<c:out value="<%=(metricname!=null)? " with " + metricname + "" : ""%>"/>
				</xtags:when>
				<xtags:otherwise>
					Search results: N/A
				</xtags:otherwise>
			</xtags:choose>							    	
    	</td>
	</tr></tfoot>
	<tbody id="tbod">
	<xtags:forEach select="/main_message/message_content/users/user">
		<xtags:variable id="userStatus" select="status"/> 
		<tr <%= (Trails.TYPE_EXPERT.equals(userStatus)) ? "class='expert'" : "" %>>
			<td><input name="btn" value="Show" type="button" onclick="doIt('<xtags:valueOf select="username"/>')"></input></td>
			<td><xtags:valueOf select="name"/></td>
			<td><xtags:valueOf select="coding"/></td>
		    <td><xtags:valueOf select="relevance"/></td>
		</tr>	
	</xtags:forEach></tbody>
	</table>
	</div>
				
		</div></div>
		
		<div id="left-column"><div class="inside">

			
		</div></div>		
		<div class="clear-columns"><!-- do not delete --></div>
	</div>
	
	<div id="right-column"><div class="inside">
	</div></div>
	<div class="clear-columns"><!-- do not delete --></div>
	</div>
</div>

<div id="footer"><div class="inside">
	<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
</div></div>

</div>

<script type="text/javascript">
	resize();
</script>
			
</body>
</html>