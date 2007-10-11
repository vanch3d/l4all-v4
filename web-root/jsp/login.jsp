<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// Clean the session
	session.removeAttribute("User");
	session.invalidate();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>L4All System Login</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<style type="text/css">
		<!-- 
			@import "../css/skidoo_redux.css"; 
			@import "../css/skidoo_redux_theme.css";
			@import "../css/skidoo_l4all.css";
			@import "../css/skidoo_form.css";
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
			form.tableless {
			  margin: 80px 0px 80px 0px;
			}
	</style>
	<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
	<script type="text/javascript" src="../js/ruthsarian_utilities.js"></script>
</head>
<body style="font-family: Verdana">

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

	<form id="myform" class="tableless" action="../jsp/auth" method="post">
	<fieldset>
		<legend>System Login</legend>
		<div class="notes">
			<h4>Not yet a member?</h4>
			<p class="last">Please <a href="register.jsp?newUser=1">Register Here</a>
			</p>
		</div>
		<div class="required">
			<label for="username">Username:</label>
			<input type="text" id="username" name="username" size="30" maxlength="30"/>
		</div>
		<div class="required">
			<label for="password">Password:</label>
			<input type="password" id="password" name="password" size="30" maxlength="30"/>
		</div>
	</fieldset>
	
	<fieldset>
		<div class="submit">
			<input class="inputSubmit" type="submit" name="submit" value="Login"/>
		</div>
	</fieldset>
	
	<%	String strError = (String)request.getAttribute("LOGIN_MSG");
		if (strError != null) {%>		    
		<p class="error"><%= strError %></p>
	<%}%>
	
	</form>

	</div></div>
	
	<!-- Begin Left Column -->
	<div id="left-column"><div class="inside">
	
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><A href="#" onclick="document.location.href='../index.jsp';return false;">Home</A></li>
		</ul>
	
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