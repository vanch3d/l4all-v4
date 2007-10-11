<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	
	String field = (String) request.getParameter("field");
	pageContext.setAttribute("field",field);
%>
<head>

<title>L4ALL Registration</title>
</head>


<body bgcolor="#BABA96">
<h2><font color="white">Error during registration</font></h2>
<h3><font color="red">Mandatory field '<c:out value="${field}"/>' is missing</font></h3>
</body>
