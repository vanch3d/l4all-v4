<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>

<%@page import="uk.ac.bbk.dcs.l4all.beans.Message"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Trail"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.TrailEpisode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="uk.ac.bbk.dcs.l4all.util.MessageBuilder"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.EpisodeType"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.Episode"%>
    

<jsp:useBean id="userMng" class="uk.ac.bbk.dcs.l4all.beans.L4AllUserManager"/>
<jsp:useBean id="trailMgr" class="uk.ac.bbk.dcs.l4all.beans.UserTrailManager"/>
<%
	response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
	
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	if (null == username) 
	{
		// if session has expired (or user logged out)
		request.setAttribute("LOGIN_MSG", "Session has ended. You need to login again before using the system.");
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
		return;
	}
	pageContext.setAttribute("username",username);
	String recName = (String) request.getParameter("name");
	String newline = System.getProperty("line.separator");
	String res = MessageBuilder.createSuccessMessageHeader("searchSimilar") + newline;

	int nbObjective = 0;
	int nbPrereq = 0;
	if (recName != null) 
	{
		pageContext.setAttribute("recName",recName);

		Message msgTrail = trailMgr.getUserTrailDetails(username,recName);
		Trail trail = (Trail)msgTrail.getResultObject();		
		
		res += "<timelines cardinality='" + 1 + "'>" + newline;

		TrailEpisode[] tEpisodes = trail.getTrailEpisodes();

		res += ("<tl>")+ newline;
		res += ("<tl_id>" + trail.getTrailID() + "</tl_id>")+ newline;
		res += ("<tl_title>" + trail.getTrailName() + "</tl_title>")+ newline;
		res += ("<tl_description>" + trail.getTrailDescription() + "</tl_description>")+ newline;
		res += ("<tl_keywords><![CDATA[" + trail.getTrailKeywords()
			+ "]]></tl_keywords>")+ newline;
		res += ("<tl_duration>")+ newline;
		res += ("<tl_start>" + trail.getStart() + "</tl_start>")+ newline;
		res += ("<tl_end>" + trail.getEnd() + "</tl_end>")+ newline;
		res += ("</tl_duration>")+ newline;
		
		res += ("<tl_episodes cardinality='" + tEpisodes.length + "'>")+ newline;
		for (int l = 0; l < tEpisodes.length; l++)
		{
		    String nature = tEpisodes[l].getNature();
		    if (Episode.NATURE_FACT.equals(nature))
				nbPrereq++;
		    else
				nbObjective++;
		    
		    res += ("<tl_episode order='" + (l) + "'>")+ newline;
		    res += ("<title>" + tEpisodes[l].getTitle()
			    + "</title>")+ newline;
		    res += ("<URL>" + tEpisodes[l].getUrl() + "</URL>")+ newline;
		    res += ("<description><![CDATA["
			    + tEpisodes[l].getDescription()
			    + "]]></description>")+ newline;
		    res += ("<category>" + tEpisodes[l].getCategory()
			    + "</category>")+ newline;
		    res += ("<nature>" + nature + "</nature>")+ newline;
		    res += ("<start>" + tEpisodes[l].getStart()
			    + "</start>")+ newline;
		    res += ("<end>" + tEpisodes[l].getEnd() + "</end>")+ newline;
		    res += ("</tl_episode>")+ newline;
		}
		res += ("</tl_episodes>")+ newline;
			
		res += ("</tl>")+ newline;
		res += "</timelines>"+ newline;
	}
		res += MessageBuilder.createSuccessMessageTail() + newline;
		pageContext.setAttribute("users",res);
%> 
<html>
<head>
	<title>L4All - Recommendations</title>
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
			#masthead
			{
				background-image: url("../images/l4all.png");
				background-repeat: repeat;
				background-position: 0% 0%;
			}
			td{
				vertical-align: middle;
			}
			.episode
			{
			    BORDER: #dddddd 1px solid;
			    PADDING: 4px;
			    FONT-WEIGHT: normal;
			    FONT-SIZE: 9pt;
			    FLOAT: left;
			    MARGIN: 5px;
			    WIDTH: 90px;
			    FONT-FAMILY: Verdana, Arial, sans-serif;
			    HEIGHT: 100px;
			    BACKGROUND-COLOR: #ffffff;
			    TEXT-ALIGN: center;
			}
			.image{
	border-style:outset;
	border-color: #DDE;
	border-width:3px;
      }
	</style>
	<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
	<script type="text/javascript" src="../js/ruthsarian_utilities.js"></script>
<script type="text/javascript">
var mywindow = null;
/////////////////////////////////////////////////////////////////////
/// Create a popup window, reusing the same browser window if available
/////////////////////////////////////////////////////////////////////
function popUp(URL)
{
	mywindow = window.open(URL, 'details', 
		'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,' + 
		'width=500,height=500,left = 0,top = 0');
	mywindow.focus();
}

function onAction(check,action)
{	
	var val = "";
	if (check)
	{
		val = getCheckedValue(document.forms['RecommendationForm'].elements['select']);
		if (val == "") 
		{
			alert("Select one of the recommandation before");
			return;
		}
	}
	if (action== "editEps")
	{
		popUp('editEpisode.jsp?timelineName=${recName}&episode='+val);
	}
}

function getValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}
</script>
</head>



<body>
<xtags:parse>
	${users}
</xtags:parse>

<div id="page-container">
<div id="masthead">
	<div class="inside">
	<h1>&nbsp;</h1>
	</div>
</div>

<div id="outer-column-container">

	<div id="inner-column-container">
	<div id="source-order-container">
	
	<!-- Begin Middle Column -->
	<div id="middle-column"><div class="inside">
	
	<!-- Begin Timeline menu -->
	<div class="clearfix rMenu-center"> 
	<ul id="middlemenu" class="clearfix rMenu-hor rMenu">
		<li><span>Episodes for <b>${recName}</b>...</span></li>
	</ul>
	</div>
	
	<form name="RecommendationForm">
	<table width="100%" cellpadding="0" cellspacing="0" style="margin-top: 10px"><thead>
	<tr>
		<th width="100%">
			<table width="100%" cellpadding="0" cellspacing="2"><tr>
			<td width="75%" bgcolor="LightSteelBlue" nowrap="nowrap">Prerequisites
			</td>
			<td width="25%" bgcolor="LightCoral" nowrap="nowrap">Objective
			</td>
			</tr></table>
		</th>
	</tr>
	</thead>
	<tbody>
	<xtags:forEach select="/main_message/message_content/timelines/tl">
	<tr>
		<td valign="top" style="vertical-align: top;">
			<table width="100%" cellpadding="0" cellspacing="2">
			<tr>
			<td width="75%" bgcolor="LightSteelBlue" >
			<xtags:forEach select="tl_episodes/tl_episode">
			<xtags:variable id="cat" select="category"/>
			<xtags:choose>
				<xtags:when test="nature = 'fact'">
				<div class="episode">
					<label 	for="episode<xtags:valueOf select="@order"/>" 
							style="overflow: auto">
					<input type="radio" name="select" value="<xtags:valueOf select="@order"/>" id="episode<xtags:valueOf select="@order"/>">
					<img class="image" src="<%= EpisodeType.getImage(cat) %>"><br/>
						<xtags:valueOf select="title"/>
					</label>
					
				</div>
				</xtags:when>
			 </xtags:choose>
			</xtags:forEach></td>
			<td width="25%" bgcolor="LightCoral">
			<xtags:forEach select="tl_episodes/tl_episode">
			<xtags:variable id="cat" select="category"/>
			<xtags:choose>
				<xtags:when test="nature = 'wish'">
				<div class="episode">
					<label 	for="episode<xtags:valueOf select="@order"/>" 
							style="overflow: auto">
					<input type="radio" name="select" value="<xtags:valueOf select="@order"/>" id="episode<xtags:valueOf select="@order"/>">
					<img class="image" src="<%= EpisodeType.getImage(cat) %>"><br/>
						<xtags:valueOf select="title"/>
					</label>
				</div>
				</xtags:when>
			 </xtags:choose>
			</xtags:forEach></td>
			</tr></table>
		</td>
	</tr>	
	</xtags:forEach>
	</tbody>

	</table>
	</form>

	

	</div></div>

	<!-- Begin Left Column -->
	<div id="left-column"><div class="inside">

		<h3>Advices</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="document.location.href='recomdesk.jsp';">Go to List</a></li>
		</ul>
		
		<h3>Episodes</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<% if (nbObjective==0) { %>
			<li><a href="javascript:popUp('createEpisode.jsp?timelineName=${recName}&type=work&nature=wish');">Add Objective</a></li>
			<%} else { %>
			<li><span>Add Objective</span></li>
			<%} %>
			<li><a href="javascript:popUp('createEpisode.jsp?timelineName=${recName}&type=work&nature=fact');">Add Prerequisite</a></li>
			<p class="fontsize-set"></p>
			<% if ((nbObjective+nbPrereq)==0) { %>
			<li><span>Edit Episode</span></li>
			<li><span>Delete Episode</span></li>
			<%} else { %>
			<li><a href="javascript:onAction(true,'editEps');">Edit Episode</a></li>
			<li><a href="javascript:onAction(true,'editEps');">Delete Episode</a></li>
			<%} %>
		</ul>

		<h3>L4All</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="popUp('about.jsp');return false;">Help</a></li>
		</ul>

		<p class="fontsize-set"></p>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><A href="#" onclick="document.location.href='login.jsp';return false;">Log Out</A></li>
		</ul>
	
		<p class="fontsize-set">
			<input type="image" onclick="setFontSize(0); return false;" src="../images/font_small.gif" alt="S" title="Small Font">
			<input type="image" onclick="setFontSize(1); return false;" src="../images/font_medium.gif" alt="M" title="Medium Font">
			<input type="image" onclick="setFontSize(2); return false;" src="../images/font_large.gif" alt="L" title="Large Font">
		</p>
		<noscript>
			<p class="fontsize-set">JavaScript Required</p>
		</noscript>

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
