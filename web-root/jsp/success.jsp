<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	
	String username = (String) request.getParameter("username");
	pageContext.setAttribute("username",username);
%>
<head>

<title>L4ALL Registration</title>
</head>


<body bgcolor="#BABA96">
<h2>You have successfully changed your details</h2>
<h3><a href="jsp/home.jsp?username=${param.username}&timeline=${param.username}">Return Home</a></h3>
</body>
