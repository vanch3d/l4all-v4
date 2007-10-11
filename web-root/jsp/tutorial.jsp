<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String username = (String) request.getParameter("username");
	String timeline = (String) request.getParameter("timeline") ;
	pageContext.setAttribute("timeline",timeline);
	pageContext.setAttribute("username",username);
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>L4All Flash Client</title>
</head>

<body bgcolor="#BABA96">

<table align="center" width="900" border="0" cellpadding="5">
	<tr>
		<td>
		  <p>&nbsp;</p>
	    <p>&nbsp;</p>
	    </td>
	</tr>
  <tr>
    <td><object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" width="800" height="600" id="l4all_flash" align="middle">
<param name="allowScriptAccess" value="sameDomain" />
<param name="movie" value="tutorial004.swf" />
<param name="quality" value="high" />
<param name="bgcolor" value="#ffffff" />
<embed src="tutorial004.swf" quality="high" bgcolor="#ffffff" width="800" height="600" name="l4all_flash" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
</object>
</td>
  </tr>
</table>
</body>
</html>