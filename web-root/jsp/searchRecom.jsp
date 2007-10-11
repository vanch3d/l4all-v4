<!-- This comment keeps IE6/7 in the reliable quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>

<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>
<%
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	
	String target = (String)request.getAttribute("data");

	pageContext.setAttribute("username",username);
	pageContext.setAttribute("target",target);
%>
<html>
<head>
	<title>L4ALL Search Advices</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   
<style type="text/css">
			<!-- 
				@import "../css/skidoo_redux.css"; 
				@import "../css/skidoo_redux_theme.css";
			-->
		</style>
		<style type="text/css">
				#outer-column-container, #inner-column-container { border-right-width: 0; }
				#right-column {	display: none; }
				#source-order-container { margin: 0; }
		</style>
		<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
		
<style type="text/css">    

div.tableContainer {
	width: 100%;		/* table width will be 99% of this*/
	height: 300px; 	/* must be greater than tbody*/
	overflow: auto;
	margin: 0 0;
	//min-width: 400px;
	}

table {
	width: 100%;		/*100% of container produces horiz. scroll in Mozilla*/
	border: none;
	background-color: #f7f7f7;
	}
	
table>tbody	{  /* child selector syntax which IE6 and older do not support*/
	overflow: auto; 
	height: 250px;
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
	padding-right: 0px;
	font-size: 12px;
	text-align: left;
	border-bottom: solid 1px #d8d8d8;
	border-left: solid 1px #d8d8d8;
	}

tfoot td	{
	text-align: center;
	font-size: 11px;
	font-weight: bold;
	background-color: papayawhip;
	color: steelblue;
	border-top: solid 2px slategray;
	}

td:last-child {padding-right: 20px;} /*prevent Mozilla scrollbar from hiding cell content*/
		tr.hidden{display:none}
		tr.show{display:table-row;}

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

<LINK REL=StyleSheet HREF="../css/tabber.css" TYPE="text/css" MEDIA=screen>
    
<script type="text/javascript" src="../js/tabber-minimized.js">
</script>

<script type="text/javascript">
function closeMe()
{
	if (opener) self.close();
	else parent.ajaxwin.hide();
}

</script>
</head>

	<xtags:parse>
		<%=(target!=null)? target : "<main_message></main_message>"%>
	</xtags:parse>

<body>

<div id="page-container">
<div id="masthead">
	<div class="inside">
	<h1>Search for Recommendations</h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column">
			<div class="inside">

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
    	<xtags:valueOf select="/main_message/message_content/advices/target"/>
    	</td>
	</tr></tfoot>
	<tbody>
		<xtags:forEach select="/main_message/message_content/advices/advice">
		<tr>
			<td><input name="btn" value="Show" type="button" onclick="doIt('<xtags:valueOf select="advice_title"/>')"></input></td>
			<td><xtags:valueOf select="advice_title"/></td>
		      <td><xtags:valueOf select="advice_format"/></td>
		      <td><xtags:valueOf select="@score"/></td>
		</tr>
		</xtags:forEach>
	</tbody>			
	
	</table></div>

    	</div></div>
    	
		<div id="left-column">	
			<div class="inside">
			
			<form name="searchStrong" action="doSearchRecommendation" method="get">
			<input name="search" value="strong" type="hidden">
			<input value="Search (Strong)" type="submit">
			</form>
			<form name="searchWeak" action="doSearchRecommendation" method="get">
			<input name="search" value="weak" type="hidden">
			<input value="Search (Weak)" type="submit">
			</form>
			
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

<div id="footer">
	<div class="inside">
			<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
	</div>
</div>

</div>
	
</body>
</html>