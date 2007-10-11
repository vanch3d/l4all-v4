<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>L4All - version 4.0</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<style type="text/css">
		<!-- 
			@import "css/skidoo_redux.css"; 
			@import "css/skidoo_redux_theme.css";
			@import "css/skidoo_l4all.css";
		-->
	</style>
	<style type="text/css">
			#outer-column-container, #inner-column-container { border-right-width: 0; }
			#right-column {	display: none; }
			#source-order-container { margin: 0; }

			#masthead
			{
				background-image: url("images/l4all.png");
				background-repeat: repeat;
				background-position: 0% 0%;
			}
			table
			{
			 padding-top: 10px;
			 padding-bottom: 75px;
			}
	</style>
	<link rel="stylesheet" href="css/skidoo_redux_print.css" type="text/css" media="print">
	<script type="text/javascript" src="js/ruthsarian_utilities.js"></script>
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
	
	<h1>Welcome to L4<em>All</em></h1>

	<p>Welcome to Lifelong Learning for All (L4<em>All</em>)! </p>
  	
  	<p>The aim of this website is to help you clarify your learning needs and provide tools that will help your 
  	learning and career development. </p>
  	
  	<p>L4All enables you to create your own personal timeline based on your previous
 	learning and work related experiences. You can then use this to develop a
 	personal development plan and identify learning pathways that meet your
 	future learning and career development needs and requirements. </p>

 	<p>A key feature of L4All is that it provides you with access to a range of quality-assurred materials around 
 	London.</p>

	<h2>What next?</h2>

	<table border="0" cellpadding="10">
	
	<tr>
  		<td>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
		<li><a href="jsp/login.jsp" title="Login to L4ALL">Log-in</a></li>
		</ul></td>
		
	  	<td><b>Already a member?</b><br>Then click here to log-in
  		</td>
	</tr>
	
	<tr>
		<td>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
		<li><a href="jsp/register.jsp?newUser=1" title="Register to L4ALL">Register</a></li>
		</ul></td>
	
		<td><b>If you are new to L4All then you will need to register.</b><br>
		This requires you to choose an username and a password and answer a few simple questions.
		</td>
  	</tr>
  	

   	</table>
  	
	</div></div>
	
	<!-- Begin Left Column -->
	<div id="left-column"><div class="inside">
	</div></div>
	<div class="clear-columns"><!-- do not delete --></div>
	
	</div>
	
	<div id="right-column"><div class="inside">
	</div></div>
	<div class="clear-columns"><!-- do not delete --></div>

	</div></div>

	<div id="footer"><div class="inside">
		<div class="copyright">L4ALL © Birkbeck College - 2007</div>
		<div class="admin"><a href="admin/index.jsp">Go to Administration Panel</a></div>
	</div></div>
</div>

</body>
</html>