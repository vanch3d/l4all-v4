<!-- This comment keeps IE6/7 in the reliable quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>

<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>
<%
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	
	String target = (String)request.getAttribute("data");

	pageContext.setAttribute("username",username);
	pageContext.setAttribute("target",target);
%>
<html>
<head>
	<title>L4ALL Search Courses</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   
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
		</style>
		<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
		
<style type="text/css">    

div.tableContainer {
	width: 100%;		/* table width will be 99% of this*/
	height: 300px; 	/* must be greater than tbody*/
	overflow: auto;
	margin: 0 0;
	//min-width: 400px;
	}

table {
	width: 100%;		/*100% of container produces horiz. scroll in Mozilla*/
	border: none;
	background-color: #f7f7f7;
	}
	
table>tbody	{  /* child selector syntax which IE6 and older do not support*/
	overflow: auto; 
	height: 250px;
	overflow-x: hidden;
	}
	
thead tr	{
	position:relative; 
	top: expression(offsetParent.scrollTop); /*IE5+ only*/
	}
	
thead td, thead th {
	text-align: center;
	font-size: 14px; 
	background-color: oldlace;
	color: steelblue;
	font-weight: bold;
	border-top: solid 1px #d8d8d8;
	border-bottom: solid 2px slategray;
	}	
	
td	{
	color: #000;
	padding-right: 0px;
	font-size: 12px;
	text-align: left;
	border-bottom: solid 1px #d8d8d8;
	border-left: solid 1px #d8d8d8;
	}

tfoot td	{
	text-align: center;
	font-size: 11px;
	font-weight: bold;
	background-color: papayawhip;
	color: steelblue;
	border-top: solid 2px slategray;
	}

td:last-child {padding-right: 20px;} /*prevent Mozilla scrollbar from hiding cell content*/
		tr.hidden{display:none}
		tr.show{display:table-row;}

</style>

<style type="text/css" media="print">
div.tableContainer {overflow: visible;	}
table>tbody	{overflow: visible; }
td {height: 14pt;} /*adds control for test purposes*/
thead td	{font-size: 11pt;	}
tfoot td	{
	text-align: center;
	font-size: 9pt;
	border-bottom: solid 1px slategray;
	}
	
thead	{display: table-header-group;	}
tfoot	{display: table-footer-group;	}
thead th, thead td	{position: static; } 



</style>

<LINK REL=StyleSheet HREF="../css/tabber.css" TYPE="text/css" MEDIA=screen>
    
<script type="text/javascript" src="../js/tabber-minimized.js">
</script>

<script type="text/javascript">
function closeMe()
{
	if (opener) self.close();
	else parent.ajaxwin.hide();
}

</script>
</head>

	<xtags:parse>
		<%=(target!=null)? target : "<main_message></main_message>"%>
	</xtags:parse>
	<xtags:variable id="hits" select="main_message/message_content/courses/@cardinality"/> 

<body>

<div id="page-container">
<div id="masthead">
	<div class="inside">
	<h1>Search for Courses</h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column">
			<div class="inside">

	<div class="tableContainer" id="cont">
	<table>
    <thead>
    <tr>
      <th></th>
      <th>ID</th>
      <th>LD</th>
      <th>Institution</th>
      <th width="100%">Title</th>
    </tr>
    </thead>

    <tfoot><tr> 
		<td colspan="5">Search results: <%= (hits.equals(""))? "N/A" : hits %></td>	
	</tr></tfoot>
	
	<tbody id="tbod">
	<% int nbg = 0;%>
    <xtags:forEach select="main_message/message_content/courses/course">
		<xtags:variable id="mydesc" select="course_description"/> 
    	<tr>
		  <td><% if (!mydesc.equals("")){ %>
			  	<img id="btn<%= nbg %>" src="../images/avshow.gif" onclick="doIt(<%= nbg %>)"/>
		  	  <%} %>
		  </td>
	      <td><a href="getCourseDetails?course_id=<xtags:valueOf select="course_Id"/>&username=<%= username %>"><xtags:valueOf select="course_Id"/></a></td>
	      <td><xtags:choose>
				<xtags:when test="@learn_direct != 0">yes
				</xtags:when>
			  	<xtags:otherwise>no
			  	</xtags:otherwise>
			 </xtags:choose>	</td>
	      
	      <td><xtags:valueOf select="course_institution"/></td>
	      <td width="100%"><xtags:valueOf select="course_title"/></td>
	    </tr>
		<% if (!mydesc.equals("")){ %>
	    <tr id="desc<%= nbg %>" class="hidden">
		  <td></td>
	      <td colspan="4" class="show"><%= mydesc %></td>
	    </tr>
	    <%} %>
		<%nbg++;%>
    </xtags:forEach>
    </tbody>
    </table>
    </div>	
    		</div>
		</div>
		<div id="left-column">	
			<div class="inside">
			
			<div class="tabber">
			<div class="tabbertab" title="LD">
			
			<form name="searchform" action="doSearchCourses">
			<input type="hidden" name="username" value="${username}"/>
			<input type="hidden" name="includeLD" value="1" checked="checked">
			<p>Search <b>LearnDirect</b>:<br/>
				<input type="input" name="keyword" size="15"/></p>
			<p><input name="submit" value="Search" type="submit"></p>
			</form>
	</div>
	
		<div class="tabbertab" title="L4ALL">
			
			<form name="searchform" action="doSearchCourses">
			<input type="hidden" name="username" value="${username}"/>
			<input type="hidden" name="includeLD" value="0" checked="checked">
			<p>Search <b>L4ALL</b>:<br/>
				<input type="input" name="keyword" size="15"/></p>
			<p><input name="submit" value="Search" type="submit"></p>
			</form>
	</div>
	</div>

			</div>
		</div>
		<div class="clear-columns"><!-- do not delete --></div>
	</div>
	<div id="right-column">
	<div class="inside">
		<h3>Highlights</h3>
	</div>
	</div>
	<div class="clear-columns"><!-- do not delete --></div>
	</div>
</div>

<div id="footer">
	<div class="inside">
			<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
	</div>
</div>

</div>
		
	<% if (hits!=null && !hits.equals("")) { %>
	<script language="JavaScript" type="text/javascript">
	function doIt(n)
	{
		var y = document.getElementById("desc"+n).className;
		if (y=="show")
		{
			document.getElementById("desc"+n).className = "hidden";	
			document.getElementById("btn"+n).src = "../images/avshow.gif";	
		}
		else
		{
			for (var i = 0; i < <%= hits %>; i++) 
			{
				var name = "desc"+i;
				var elt = document.getElementById(name);
				if (elt)
				{
					elt.className= "hidden";
					document.getElementById("btn"+i).src = "../images/avshow.gif";	
					}
			}

			document.getElementById("desc"+n).className = "show";
			document.getElementById("btn"+n).src = "../images/avhide.gif";	
		}
	}
	</script>
	<%}%>
	
</body>
</html>