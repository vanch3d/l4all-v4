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
	<title>L4All Logout</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="Refresh" content="20;URL=login.jsp">
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

	<h1>Thanks for using L4ALL</h1>
	
	<p>L4All is a prototype, currently supporting a small subset of the functionalities 
	we will implement in the future.</p>
	
	<p>You can help us in improving the current system and defining further functionalities
	you would like to access through it.</p>
	
	<p>To do so, please spend a few more moment by accessing the questionnaire below
	and answering the questions as best as you can.</p>


	<table border="0" cellpadding="10">
	
	<tr>
  		<td>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
		<li><a href="#" title="Go the the questionnaire">Questionnaire</a></li>
		</ul></td>
		
	  	<td><b>Go the the questionnaire</b><br>If you have already filled the questionnaire, 
	  	you can log in again and modify your answers.
  		</td>
	</tr>
   	</table>
   	
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
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