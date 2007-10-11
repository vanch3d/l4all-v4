<!-- This comment keeps IE6/7 in the reliable quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
<%
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	
	String target = (String)request.getAttribute("data");

	pageContext.setAttribute("username",username);
	pageContext.setAttribute("target",target);
%>
<html>
<head>
	<title>Insert title here</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<!-- <link rel="stylesheet" type="text/css" href="../css/l4all-popup.css"> -->
<style type="text/css">
			<!-- 
				@import "../css/skidoo_redux.css"; 
				@import "../css/skidoo_redux_theme.css";
			-->
			
	
				#outer-column-container, #inner-column-container { border-right-width: 0; }
				#right-column {	display: none; }
				#source-order-container { margin: 0; }
				#page-container
					{
						min-width: 300px;		/* limit how narrow the layout will
						}
		</style>
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
	
tbody th {
	vertical-align: top;
	text-align: left;
	background-color: oldlace;
	color: steelblue;
	font-weight: bold;
	border-bottom: solid 1px #d8d8d8;
	}	
	
td	{
	color: #000;
	padding-right: 2px;
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

		

</style>	
</head>

<body>

<xtags:parse id="cdList">
	<%=(target!=null)? target : "<main_message></main_message>"%>
</xtags:parse>

<div id="page-container">

	<div id="masthead">
		<div class="inside">
		<h1>Course Detail</h1>
		</div>
	</div>
			
	<div class="tableContainer">
		<xtags:style xsl="../xsl/coursedetails.xsl" document="<%= cdList %>"/>	
	</div>

				
	<div id="footer">
	<div class="inside">
		<input type="button" name="addTimeLine" value="Add this course to your timeline" >
	</div>
	</div>

</div>


</body>
</html>