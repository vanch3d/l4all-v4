<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.L4AllUser"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Message"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.L4AllUsersSearch"%>
<%@page import="uk.ac.bbk.dcs.l4all.util.MessageBuilder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Trail"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.Trails"%>

<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>

<jsp:useBean id="userMng" class="uk.ac.bbk.dcs.l4all.beans.L4AllUserManager"/>
<jsp:useBean id="trailMgr" class="uk.ac.bbk.dcs.l4all.beans.UserTrailManager"/>
<%
	L4AllUser[] list = L4AllUsersSearch.getAllUsers();
	int nbUsers = (list==null)? 0 : list.length;
	String newline = System.getProperty("line.separator");

	int nbuser=0;
	int nbtemplate=0;
	int nbexpert=0;
	
	//String res = MessageBuilder.createSuccessMessageHeader("searchSimilar") + newline;
	//res += "<users cardinality='" + nbUsers + "'>" + newline;
	String res = "";
	for (int i = 0; i < nbUsers; i++)
	{
	    L4AllUser user = (L4AllUser) list[i];

		Message msg = userMng.getUserIdentificationDetails(user.getUsername());
		L4AllUser userdetails = (L4AllUser)msg.getResultObject();		

		Message msg2 = trailMgr.getAllTrails(user.getUsername());
		ArrayList trails = (ArrayList)msg2.getResultObject();
		String[] tt = (String[]) trails.toArray(new String[trails.size()]);
	
		String status = userdetails.getStatus();
		if (Trails.TYPE_USER.equals(status)) nbuser++;
		if (Trails.TYPE_EXPERT.equals(status)) nbexpert++;
		if (Trails.TYPE_TEMPLATE.equals(status)) nbtemplate++;
	    res += "<user>" + newline;
	    res += "<status>" + status + "</status>"+ newline;
	    res += "<username>" + userdetails.getUsername() + "</username>"+ newline;
	    res += "<name><![CDATA[" + userdetails.getName() + "]]></name>"+ newline;
	    res += "<timelines cardinality='"+tt.length+"'>"+ newline;
	    for (int j=0;j<tt.length;j++)
	    {
			String trailname = (String)trails.get(j);
			Message msgTrail = trailMgr.getUserTrailDetails(user.getUsername(),trailname);
			Trail trail = (Trail)msgTrail.getResultObject();	
			int priv = trail.getTrailPrivileges();
			String strPriv = (priv==0) ? "private" : "public";
		    res += "<timeline privilege='"+strPriv+"'>" + newline;
		    res += "<timeline_title>" + trail.getTrailName() + "</timeline_title>"+ newline;
		    res += "</timeline>"+ newline;
			
	    }
	    res += "</timelines>"+ newline;
	    res += "</user>" + newline;
	}
	res += "</users>"+ newline;
	res += MessageBuilder.createSuccessMessageTail() + newline;
	
	String head = MessageBuilder.createSuccessMessageHeader("searchSimilar") + newline;
	head += "<users cardinality='" + nbUsers + "' user='" + nbuser +
			"' template='" + nbtemplate +
			"' expert='" + nbexpert + "'>" + newline;
	res = head + res;

	
	pageContext.setAttribute("users",res);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>L4All - User Administration</title>
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
			td{
				vertical-align: middle;
			}
	</style>
<style type="text/css">    
div.tableContainer {
	width: 100%;		/* table width will be 99% of this*/
	height: 300px; 	/* must be greater than tbody*/
	overflow: auto;
	margin: 0 auto;
	min-width: 300px;
	}

table {
	width: 99%;		/*100% of container produces horiz. scroll in Mozilla*/
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
	padding-right: 2px;
	font-size: 12px;
	text-align: left;
	border-bottom: solid 1px #d8d8d8;
	border-left: solid 1px #d8d8d8;
	}
	
	div.public
	{
		font-weight: bold;
		color: MediumBlue  ;
	}
	div.private
	{
		font-weight: bold;
		color: DimGray    ;
	}
	.user
	{
	background-color: papayawhip;
	}
	.expert
	{
	background-color: LightSalmon ;
	}
	.template
	{
	background-color: PaleGreen ;
	}

tfoot td	{
	text-align: center;
	font-size: 11px;
	//font-weight: bold;
	background-color: papayawhip;
	color: steelblue;
	border-top: solid 2px slategray;
	}

td:last-child {padding-right: 20px;} /*prevent Mozilla scrollbar from hiding cell content*/

</style>	
	<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
	<script type="text/javascript" src="../js/ruthsarian_utilities.js"></script>
</head>

<xtags:parse>
	${users}
</xtags:parse>

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
	
	<h1>L4<em>All</em> User Administration</h1>

	<table>
	<thead><tr>
		<td>User Name</td>
		<td>Full Name</td>
		<td>Status</td>
		<td>Timelines</td>
	</tr></thead>
	
	<tbody>
	<xtags:forEach select="/main_message/message_content/users/user">
	<tr class="<xtags:valueOf select="status"/>">
		<td><xtags:valueOf select="username"/></td>
		<td><xtags:valueOf select="name"/></td>
		<td><xtags:valueOf select="status"/></td>
		<td>
		
		<xtags:forEach select="timelines/timeline">
		<div class="<xtags:valueOf select="@privilege"/>" style="float: left;margin-left: 5px">
			<xtags:valueOf select="timeline_title"/>
			[<xtags:valueOf select="@privilege"/>]
		</div>
		</xtags:forEach>
		</td>
	</tr>	
	</xtags:forEach>
  	</tbody>
  	
  	<tfoot><tr>
  	<td colspan="4">
  		<div style="float: left;">Registred users: <xtags:valueOf select="/main_message/message_content/users/@cardinality"/> (</div>
  		<div class="expert" style="float: left;width: 30px;"><xtags:valueOf select="/main_message/message_content/users/@expert"/></div>
  		<div class="user" style="float: left;width: 30px;"><xtags:valueOf select="/main_message/message_content/users/@user"/></div>
  		<div class="template" style="float: left;width: 30px;"><xtags:valueOf select="/main_message/message_content/users/@template"/></div>
  		<div style="float: left;">)</div>
 	</td>
  	</tr></tfoot>
  	</table>
	</div></div>
	
	<!-- Begin Left Column -->
	<div id="left-column"><div class="inside">
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><A href="#" onclick="document.location.href='index.jsp';return false;">Go Back to Panel</A></li>
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