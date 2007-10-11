<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
<%
	String isAdmin = (String) session.getAttribute(ServletUtilities.SESSION_ADMIN);
	if (null == isAdmin) 
	{
		// if session has expired (or user logged out)
		request.setAttribute("redirect", "admin/index.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.include(request, response);
		return;
	}
%>
<html>
<head>
	<title>L4All - Administration</title>
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
	<script type="text/javascript" src="../js/ruthsarian_utilities.js"></script>
</head>
<body>

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
	
	<h1>L4<em>All</em> Administration</h1>

	</div></div>
	
	<!-- Begin Left Column -->
	<div id="left-column"><div class="inside">
	
		<h3>Users</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="document.location.href='register.jsp';return false;">Create</a></li>
			<li><a href="javascript:void(0);" onclick="document.location.href='showUsers.jsp';return false;">Show / Modify</a></li>
		</ul>

		<h3>Courses</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><span>Show/Modify</span></li>
		</ul>

		<h3>L4All</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="popUp('about.jsp');return false;">Help</a></li>
			<li><A href="#" onclick="document.location.href='viewTree.jsp';return false;">Classifications</A></li>
		</ul>

		<p class="fontsize-set"></p>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><A href="#" onclick="document.location.href='login.jsp';return false;">Log Out</A></li>
		</ul>
	
		<p class="fontsize-set">
			<input type="image" onclick="setFontSize(0); return false;" src="../images/font_small.gif" alt="S" title="Small Font">
			<input type="image" onclick="setFontSize(1); return false;" src="../images/font_medium.gif" alt="M" title="Medium Font">
			<input type="image" onclick="setFontSize(2); return false;" src="../images/font_large.gif" alt="L" title="Large Font">
		</p>
		<noscript>
			<p class="fontsize-set">JavaScript Required</p>
		</noscript>
			
	
	</div></div>
	<div class="clear-columns"><!-- do not delete --></div>
	
	</div>
	
	<div id="right-column"><div class="inside">
	</div></div>
	<div class="clear-columns"><!-- do not delete --></div>

	</div></div>

	<div id="footer"><div class="inside">
		<p><i>L4ALL © Birkbeck College - 2007</i></p>
	</div></div>
</div>

</body>
</html>