<%@ page language="java" contentType="text/html" %>
<%@ page import="java.lang.*" %>
<%@ page import="uk.ac.bbk.dcs.l4all.beans.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String username = (String) request.getParameter("username");
	pageContext.setAttribute("username",username);
	String timeline = (String) request.getParameter("timeline");
	pageContext.setAttribute("timeline",timeline) ;
%>

<table border="0" cellpadding="5" style="font-family: Verdana"  align="center" width="800">
  <tr>
  	<td colspan="4" bgcolor="#e7e7e7">
  		<font size="5" color="#336699"><b>My Learning</b></font>
  		</td>
  </tr>
  <tr>
 	<td>
 		<font size="2"><a href="login.jsp">Logout</a></font>
 	</td>
  	<td align="right">
  		<font size="2">Welcome '<c:out value="${sessionScope.username}"/></font>'
  	</td>
  </tr>
</table>

<table border="0" cellpadding="5" style="font-family: Verdana" align="center" width="800">
  	<tr>
  		<td colspan="4" bgcolor="#336699">
  		<font size="4" color="#e7e7e7"><b>What do you want to do today ?</b></font>
  		</td>
  	</tr>
  	<tr>
  		<td>
  			   <a href="edit_profile.jsp?username=${param.username}">Edit My Profile</a>
  		</td>
  		<td>
  		   <a href="quicksearch.jsp?username=${param.username}">Search Courses</a>
  		</td>
  	</tr>
  	<tr>
  	  <td><a href="myTrails.jsp?username=${param.username}">View My&nbsp;Timelines</a></td>
  	  <td><a href="searchForTrails.jsp">Search Career Pathways</a></td>
  	</tr>
  	<tr>
  		<td><a href="flash.jsp?username=${param.username}&timeline=${param.timeline}">Flash</a></td>
  		<td>Search People&nbsp;Like Me
  			
  		</td>
  	</tr>
</table>