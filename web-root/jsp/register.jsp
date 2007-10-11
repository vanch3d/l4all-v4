<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String username = (String) request.getParameter("username");
	String newUser = (String) request.getParameter("newUser") ;
	pageContext.setAttribute("newUser",newUser);
	pageContext.setAttribute("username",username);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>L4ALL - Registration</title>
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
	<!-- Begin Content -->
		
	<form id="myform"  class="tableless" name="My_Details" action="saveUserIDDetails" method="post">
	<fieldset>
		<legend>Identification</legend>
		<div class="notes">
			<h4>Identification</h4>
			<p>Please complete the following fields to create your own L4All account.
			<br>Once logged in L4ALL, you will be able to change these details, as well as adding
				more information to your profile</p>
			<p class="last">The fields in <b>bold</b> are compulsory.</p>
		</div>
		
		<div class="required">
			<input name="newUser" value="1" type="hidden">
			<input name="status" value="user" type="hidden">
			<label for="username">Username:</label>
			<input id="username" name="username" size="15" maxlength="15" type="text" value="${username}">
		</div>
		<div class="required">
			<label for="password">Password:</label>
			<input id="password" name="password" size="15" value="" maxlength="15" type="password">
		</div>
		<div class="required">
			<label for="confirmPassword">Confirm Password:</label>
			<input id="confirmPassword" name="confirmPassword" size="15" maxlength="15" type="password">
			<small>Must match the password you entered just above.</small>
		</div>
	</fieldset>
	<fieldset>
		<legend>Personal Details</legend>
		<div class="notes">
			<h4>Personal Details</h4>
			<p>The <b>Year of birth</b> is used to calibrate your timeline. For privacy issues, your full date of birth is not required.</p>
			<p class="last">Your <b>email</b> will remain private, unless you decide otherwise.</p>
		</div>
		<div class="required">
			<label for="name">Full Name:</label>
			<input id="name" name="name" size="30" maxlength="50" type="text">
		</div>
		<div class="required">
			<label for="age">Year of Birth:</label>
			<input id="age" name="age" size="15" maxlength="15" type="text">
		</div>
		<div class="required">
			<label for="email">Email:</label>
			<input id="email" name="email" size="30" maxlength="30" type="text">
		</div>
	</fieldset>
	
	<fieldset>
		<div class="submit">
			<input class="inputSubmit" name="save" value="Create Profile" type="submit">
		</div>
	</fieldset>

	<%	String strError = (String)request.getAttribute("LOGIN_MSG");
		if (strError != null) {%>		    
		<p class="error"><%= strError %></p>
	<%}%>

	</form>

	<!-- End Content -->
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