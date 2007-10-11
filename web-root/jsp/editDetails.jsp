<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>

<%
	response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	if (null == username) 
	{
	   request.setAttribute("LOGIN_MSG", "Session has ended. You need to login again before using the system.");
	   RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
	   rd.forward(request, response);
	}
%>

<jsp:useBean id="userProfile" class="uk.ac.bbk.dcs.l4all.beans.L4AllUserManager"/>
<%
	String newUser = (String) request.getParameter("newUser") ;
	pageContext.setAttribute("newUser",newUser);
	pageContext.setAttribute("username",username);
	
	uk.ac.bbk.dcs.l4all.beans.Message msg = userProfile.getUserIdentificationDetails(username);
	uk.ac.bbk.dcs.l4all.beans.L4AllUser user = (uk.ac.bbk.dcs.l4all.beans.L4AllUser)msg.getResultObject();		
	
	pageContext.setAttribute("user",user);
%>
<html>
<head>

<title>L4ALL - My Profile</title>

	<LINK REL=StyleSheet HREF="../css/tabber.css" TYPE="text/css" MEDIA=screen>
    
	<script type="text/javascript" src="../js/tabber-minimized.js"></script>
	<style type="text/css">
		<!-- 
			@import "../css/skidoo_redux.css"; 
			@import "../css/skidoo_redux_theme.css";
			@import "../css/skidoo_l4all.css";
			@import "../css/skidoo_form.css";
			@import "../css/skidoo_popup.css";
		-->
	</style>
	<style type="text/css">
			div.error_msg
			{
				font-family: Verdana;
				font-size: medium;
				padding:0.5cm;
				color: red;
				font-weight: bold;
				text-align: center;
			}
		
	</style>
</head>

<script>
function closeMe()
{
	if (opener) self.close();
	else parent.ajaxwin.hide();
}

function popUp(URL)
{	
	mySecWindow = window.open(URL, 'secondary', 
		'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,' + 
		'width=500,height=500,left = 0,top = 0');
	mySecWindow.focus();
}

function getLocation(loc)
{	
	popUp("getLocation.jsp")
}
</script>

<body>
	
<div id="page-container">

<div id="masthead">
	<div class="inside">
	<h1>My Profile</h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column">
			<div class="inside">


			
<form class="tabber tableless" name="My_Details" action="saveUserIDDetails" method="post">

<fieldset class="tabbertab" title="Details">
		
	<%--<legend>Personal Details</legend>--%>
	<div class="notes">
		<h4>Personal Details</h4>
		<p>The <b>Year of birth</b> is used to calibrate your timeline.</p>
		<p>Your <b>email</b> will remain private, unless you decide otherwise.</p>
		<p class="last">The <b>location</b> is used to determine distance to institutions. 
			For privacy purpose, you can decide to put only a partial postcode (ie WC1N).</p>
	</div>
	<div class="required">
		<label for="name">Full Name:</label>
		<input id="name" name="name" size="30" maxlength="50" type="text" value="<c:out value='${user.name}'/>">
	</div>
	<div class="required">
		<label for="age">Year of Birth:</label>
		<input id="age" name="age" size="15" maxlength="15" type="text" value="<c:out value='${user.age}'/>">
	</div>
	<div class="required">
		<label for="email">Email:</label>
		<input id="email" name="email" size="30" maxlength="30" type="text" value="<c:out value='${user.email}'/>">
	</div>
	<div class="optional">
		<label for="gender">Gender:</label>
		<input id="gender" name="gender" size="15" maxlength="15" type="text" value="<c:out value='${user.gender}'/>">
	</div>
	<div class="optional">
		<label for="travelWill">Willing to travel up to:</label>
		<input id="travelWill" name="travelWill" size="15" maxlength="15" type="text" value="<c:out value='${user.travelWillingness}'/>">
		<small>Distance in kilometers</small>
	</div>
	<div class="optional">
		<label for="my_location">Location / Post Code:</label>
		<input class="inputPicker" id="my_location" name="my_location" size="15" maxlength="15" type="text" value="<c:out value='${user.myLocation}'/>">
		<button disabled="disabled" type="button" class="inputPicker" id="trigger_location" onclick="getLocation(this);return false;">
			<img src="../images/map.gif" border="0">
		</button>
	</div>
</fieldset>
	
<fieldset class="tabbertab" title="Identification">
		
	<%-- <legend>Identification</legend> --%>
	<div class="notes">
		<h4>Identification</h4>
		<p class="last">Changing your password requires both fields to be edited. 
			Leave them blank if you don't want to change it.</p>
	</div>
		
	<div class="required">
		<input name="username" size="1" maxlength="15" type="hidden" value="${username}">
		<input name="newUser" value="0" type="hidden">
		<input name="status" value="${user.status}" type="hidden">
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
	
<fieldset class="submit">
	<div class="submit">
		<input class="inputSubmit" name="save" value="Save Profile" type="submit">
	</div>
</fieldset>

<%	String strError = (String)request.getAttribute("LOGIN_MSG");
	if (strError != null) {%>		    
	<p class="error"><%= strError %></p>
<%}%>
</form>	


			</div>
		</div>
		<div id="left-column">	
			<div class="inside">b
			</div>
		</div>
		<div class="clear-columns"><!-- do not delete --></div>
	</div>
	<div class="clear-columns"><!-- do not delete --></div>
	</div>



<div id="footer">
	<div class="inside">
			<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
	</div>
	</div>
</div>



</body>
</html>