<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
<%
	session.removeAttribute(ServletUtilities.SESSION_ADMIN);
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
			div.error_msg
			{
				font-family: Verdana;
				font-size: medium;
				padding:0.5cm;
				color: red;
				font-weight: bold;
				text-align: center;
			}
			table
			{
				margin-top: 50px;
				margin-bottom: 50px;
				background: #DDE;
				padding:20px;	
				border:1px solid #aaa;
 			}
			td.form
			{
				font-family: Verdana;
				font-size: large;
				font-weight: bold;
			}
			td{
				vertical-align: middle;
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
	
	<h1>L4<em>All</em> Administration Login</h1>

	<p>In order to access this part of the system, you need to log in as an administrator</p>
	
	<form action="login" method="post">
	<table border="0" cellpadding="5" width="50%" align="center">
	<%
		String strRedirct = (String)request.getAttribute("redirect");
		if (strRedirct != null) {
			out.println("<tr>");
			out.println("<td colspan=\"2\"><input name=\"redirect\" type=\"hidden\" value=\""+strRedirct+"\"></td>");
			out.println("</tr>");
		}
	%>
    <tr>
      <td class="form" align="right">Administrative login:</td>
      <td class="form" align="left">
      	<input type="text" name="username" size="30" maxlength="30">
      </td>
    </tr>
    <tr>
      <td class="form" align="right">Password:</td>
      <td class="form" align="left">
      	<input type="password" name="password" size="30" maxlength="30">
      </td>
    </tr>
    <tr>
      <td></td>
      <td><input align="left" type="submit" name="submit" value="Login"></td>
    </tr>
  </table>
  </form>
	<%
	String strError = (String)request.getAttribute("LOGIN_MSG");
	if (strError != null) {
		out.write("<div class=\"error_msg\">");
		out.write(strError);
		out.write("</div>");
	}
%>

  	
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