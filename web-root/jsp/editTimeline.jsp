<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>

<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.EpisodeType"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.EpisodeClassification"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.TrailEpisode"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Message"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Trail"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.Trails"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ManageTimelineServlet"%>

<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Comparator"%>

<%@page import="uk.ac.bbk.dcs.l4all.beans.L4AllUser"%>


<jsp:useBean id="trailMgr" class="uk.ac.bbk.dcs.l4all.beans.UserTrailManager"/>
<jsp:useBean id="userProfile" class="uk.ac.bbk.dcs.l4all.beans.L4AllUserManager"/>
<%
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	String tlname = (String) request.getParameter("name");
	String action = (String) request.getParameter("action");
	String resEdit = (String)request.getAttribute("result");
	String tlInterval = "";
	String tlScale = "";
	boolean bIsNew = ManageTimelineServlet.ACTION_NEW.equals(action);
	boolean bIsDelete = ManageTimelineServlet.ACTION_DEL.equals(action);
	boolean bIsTemplate = true;

	Message msgUser = userProfile.getUserIdentificationDetails(username);
	L4AllUser user = (uk.ac.bbk.dcs.l4all.beans.L4AllUser)msgUser.getResultObject();		
	String type = user.getStatus();
	
	pageContext.setAttribute("action",action);
	pageContext.setAttribute("type",type);
	pageContext.setAttribute("rdonly",(bIsDelete)?"disabled=\"disabled\"":"");
	pageContext.setAttribute("subtitle",(bIsDelete)?"Delete Timeline":"Save Timeline");

	if (!bIsNew)
	{
		if (tlname==null) tlname = username;

		Message msg = trailMgr.getUserTrailDetails(username, tlname);
		Trail trail = (Trail)msg.getResultObject();	
		
		type = trail.getTrailType();
		bIsTemplate = (Trails.TYPE_TEMPLATE.equals(type));
		
		if (trail!=null)
		{
			String privilege = "" + trail.getTrailPrivileges() + "";
			String description  = trail.getTrailDescription();
			String keywords  = trail.getTrailKeywords();
			String start = trail.getStart();
			String end = trail.getEnd();
			String privPublic="", privPrivate="";
			if ("1".equals(privilege))
			{
			    privPublic="checked=\"checked\"";
			}
			else
			{
			    privPrivate="checked=\"checked\"";
			}
			pageContext.setAttribute("resEdit",resEdit);
			pageContext.setAttribute("username",username);
			pageContext.setAttribute("name",tlname);
			pageContext.setAttribute("privPublic",privPublic);
			pageContext.setAttribute("privPrivate",privPrivate);
			pageContext.setAttribute("description",description);
			pageContext.setAttribute("keywords",keywords);
			pageContext.setAttribute("start",start);
			pageContext.setAttribute("end",end);
			pageContext.setAttribute("type",type);
		}
	
		Cookie[] cookies = request.getCookies();
		tlInterval = ServletUtilities.getCookieStringValue(cookies,"TL_Interval");
		if (tlInterval==null) tlInterval = "150";
		tlScale = ServletUtilities.getCookieStringValue(cookies,"TL_Scale");
		if (tlScale==null) tlScale = "Timeline.DateTime.MONTH";
		pageContext.setAttribute("tlInterval",tlInterval);
		pageContext.setAttribute("tlScale",tlScale);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><%= (bIsTemplate)? "L4ALL Recommandation Editor" : "L4ALL Timeline Editor" %></title>
	<LINK REL=StyleSheet HREF="../css/tabber.css" TYPE="text/css" MEDIA=screen>
	<link rel="stylesheet" type="text/css" media="all" href="../jscalendar/calendar-system.css"/>
	<style type="text/css">
		<!-- 
			@import "../css/skidoo_redux.css"; 
			@import "../css/skidoo_redux_theme.css";
			@import "../css/skidoo_l4all.css";
			@import "../css/skidoo_form.css";
			@import "../css/skidoo_popup.css";
			@import "../css/colorpick.css";
			@import "../css/tabber.css";
		-->
	</style>

	<script type="text/javascript" src="../js/tabber-minimized.js"></script>
	<script type="text/javascript" src="../js/colorpick.js"></script>	
	<script type="text/javascript" src="../jscalendar/calendar.js"></script>
	<script type="text/javascript" src="../jscalendar/lang/calendar-en.js"></script>
	<script type="text/javascript" src="../jscalendar/calendar-setup.js"></script>
	
<script type="text/javascript">
<%-- Update the timeline (assumed to be in the opener window). --%>
function updateTimeline()
{
	if (window.opener && !window.opener.closed)
		window.opener.updateTimeline();
}

<%-- Close this window (only if open as a popup. --%>
function closeMe()
{
	if (window.opener && !window.opener.closed)
		self.close();
}

<%-- Temporarily hide the "tabber" class so it does not "flash" 
     on the page as plain HTML. After tabber runs, the class is changed
     to "tabberlive" and it will appear. 
     A similar procedure is used for the color picker (plugin) --%>
document.write('<style type="text/css">.tabber{display:none;}<\/style>');
document.write('<style type="text/css">#plugin{display:none;}<\/style>');

var tabberOptions = {
<%-- Code run when the user clicks a tab. Hide the colorpicker when the 
     "details" tab (id=0) is actvated. --%>
  'onClick': function(argsObj) {
	    var t = argsObj.tabber; /* Tabber object */
	    var id = t.id; /* ID of the main tabber DIV */
	    var i = argsObj.index; /* Which tab was clicked (0 is the first tab) */
    	var e = argsObj.event; /* Event object */
	    if (id == 'myform' && i==0 ) 
    	{
    		$S('plugin').display='none';
	    }
  }
};
</script>
</head>

<%-- Retrieve and parse the XML string (if any) coming from the servlet --%>
<xtags:parse>
	<%=(resEdit!=null)? resEdit : "<main_message></main_message>"%>
</xtags:parse>

<body>

<div id="page-container">

<div id="masthead">
	<div class="inside">
	<h1><%= (bIsTemplate)? "My Recommandation" : "My Timeline" %></h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column"><div class="inside">
		
<form id="myform" name="My_timeline" class="tableless tabber" action="manageTimeline" method="post">

<fieldset class="tabbertab" title="Details">
	<div class="notes">
		<h4>Details</h4>
		<p>Change the details of your timeline.</p>
		<p>Set the limits (<b>start</b> and <b>end</b>) in time of your timeline.</p>
		<p>Set the <b>privacy</b> of your timeline to let others accessing it.</p>
		<p class="last">Edit the <b>description</b> and the <b>keywords</b> associated with your 
			timeline to allows search.</p>
	</div>
	<%if (bIsNew){ %>
	<div class="required">
			<input name="username" size="5" value="${username}" type="text" >
			<input name="action" size="5" value="${action}" type="text">
			<input name="type" size="5" value="${type}" type="text">
			<label for="name">Password:</label>
			<input name="name" id="name" value="${name}" size="15">
	</div>
	<%} else {%>
			<input name="username" id="username" size="1" value="${username}" type="hidden">
			<input name="name" id="name" size="3" value="${name}" type="hidden">
			<input name="type" id="type" size="3" value="${type}" type="hidden">
			<input name="action" id="action" size="3" value="${action}" type="hidden">
	<%} %>
	<%if (!bIsTemplate){ %>
	<div class="required">
			<label for="start">Start:</label>
			<input name="start" id="start" value="${start}" size="10">
	</div>
	<div class="required">
			<label for="end">End:</label>
			<input name="end" id="end" value="${end}" size="10">
	</div>
	<%} %>
	<div class="required">
	<fieldset>
			<legend>Privacy:</legend>
			<label for="privileges_0" class="labelRadio">
				<input ${rdonly} id="privileges_0"  name="privileges" type="radio" value="1" class="inputRadio" ${privPublic}>
				Public 
			</label>
			<label for="privileges_1" class="labelCheckbox">
				<input ${rdonly} id="privileges_1" name="privileges" type="radio" class="inputRadio" value="0" ${privPrivate}>
				Private
			</label>
	</fieldset>
	</div>
	<div class="optional">
			<label for="description">Description:</label>
			<textarea ${rdonly} name="description" id="description" rows="3" cols="30">${description}</textarea>
	</div>
	<div class="optional">
			<label for="keywords">Keywords:</label>
			<textarea ${rdonly} name="keywords" id="keywords" rows="1" cols="30">${keywords}</textarea>
	</div>	
</fieldset>

<fieldset class="tabbertab" title="Display">

	<div class="notes">
		<h4>Display</h4>
		<p >Change the way your timeline is presented.</p>
		<p >You can modify the <b>scale</b> and the <b>width</b> of each time unit in the timeline.</p>
		<p class="last">You can modify the background <b>colour</b> of the timeline and of the events.</p>
	</div>

	<div class="optional">
			<label for="band_scale">Scale:</label>
			<select name="band_scale" id="band_scale" class="selectOne">
			  <option value="Timeline.DateTime.DECADE" <%= (tlScale.equals("Timeline.DateTime.DECADE"))? "selected=\"selected\"" : "" %>>Decade</option> 
			  <option value="Timeline.DateTime.YEAR" <%= (tlScale.equals("Timeline.DateTime.YEAR"))? "selected=\"selected\"" : "" %>>Year</option> 
			  <option value="Timeline.DateTime.MONTH" <%= (tlScale.equals("Timeline.DateTime.MONTH"))? "selected=\"selected\"" : "" %>>Month</option> 
			</select>
	</div>
	<div class="optional">
			<label for="band_width">Unit Width:</label>
			<input type="text" name="band_width" id="band_width" size="2" value="${tlInterval}">
	</div>
	<div class="optional">
			<label for="band_center">Center on:</label>
			<select name="band_center" id="band_center" class="selectOne">
			  <option value="Timeline.DateTime.YEAR" selected="selected">Current date</option> 
			  <option value="Timeline.DateTime.DECADE">Last episode</option> 
			  <option value="Timeline.DateTime.MONTH">First episode</option> 
			</select>
	</div>

	<div class="optional">
		<fieldset style="clear: none;">
			<legend>Timeline colours:</legend>
			<!--Sample DIV to show selected color -->
			<div class="colorpicker">Past
				<input class="colorpicker" readonly="readonly" id="colorbox1" name="colorbox1" 
						style="background-color: #E7DFD6;"
						onclick="javascript:updateColorBox('colorbox1')">
			</div>
			<div class="colorpicker">Present
				<input class="colorpicker" readonly="readonly" id="colorbox2" name="colorbox2" 
						style="background-color: #FF0000"
						onclick="javascript:updateColorBox('colorbox2')">
			</div>
			<div class="colorpicker">Future
				<input class="colorpicker" readonly="readonly" id="colorbox3" name="colorbox3" 
						style="background-color: #FFDAB9"
						onclick="javascript:updateColorBox('colorbox3')">
			</div>

			<div class="colorpicker">Scroll
				<input class="colorpicker" readonly="readonly" id="colorbox4" name="colorbox4" 
						style="background-color: #D1CECA"
						onclick="javascript:updateColorBox('colorbox4')">
			</div>
			<div class="colorpicker">People
				<input class="colorpicker" readonly="readonly" id="colorbox5" name="colorbox5" 
						style="background-color: #E8E8F4"
						onclick="javascript:updateColorBox('colorbox5')">
			</div>
			</fieldset>
	</div>
	<div class="optional">
		<fieldset>
		
		<legend>Event colours:</legend>
			<!--Sample DIV to show selected color -->
			<div class="colorpicker">Fact
				<input class="colorpicker" readonly="readonly" id="colorbox6" name="colorbox6" 
						style="background-color: #58A0DC" 
						onclick="javascript:updateColorBox('colorbox6')">
			</div>
			<div class="colorpicker">Wish
				<input class="colorpicker" readonly="readonly" id="colorbox7" name="colorbox7" 
						style="background-color: #DB9957"
						onclick="javascript:updateColorBox('colorbox7')">
			</div>		
		</fieldset>
	</div>
		
</fieldset>

<xtags:choose>
	<%-- check if there is a message --%>
	<xtags:when test="not(main_message/message_type)">
		<fieldset  class="submit">
			<div class="submit">
				<input  class="inputSubmit" name="save" value="${subtitle}" type="submit">
			</div>
		</fieldset>
	</xtags:when>

	<%-- check if there is an ERROR message --%>
	<xtags:when test="main_message/message_type = 'ERROR'">
		<fieldset  class="submit">
			<div class="submit">
				<input  class="inputSubmit" name="save" value="${subtitle}" type="submit">
			</div>
		</fieldset>
		<p class="error"><xtags:valueOf select="main_message/message_content/error_description"/></p>
	</xtags:when>

	<%-- otherwise assume it is a SUCCESS message --%>
	<xtags:otherwise>
		<fieldset  class="submit">
			<div class="submit">
				<input disabled="disabled"  class="inputSubmit" name="save" value="${subtitle}" type="submit">
			</div>
		</fieldset>
		<p class="success">Information successfully modified.<br>
			You can <a href="javascript:void(0);" onclick="self.close();if (opener) opener.location.reload(true);">close the window</a>.
		</p>
		<script type="text/javascript">
			setTimeout("self.close();if (opener) opener.location.reload(true);",30000);
		</script>
	</xtags:otherwise>
</xtags:choose>

</form>

		</div></div>
		
		<div id="left-column">	
			<div class="inside">b
			</div>
		</div>
		<div class="clear-columns"><!-- do not delete --></div>
	</div>
	<div class="clear-columns"><!-- do not delete --></div>
	</div>
</div>


	<div id="footer">
		<div class="inside">
	<xtags:choose>
		<%-- check if there is an SUCCESS message --%>
		<xtags:when test="main_message/message_type = 'SUCCESS'">
			<input name="cancel" value="Close"  class="btn" type="button" onclick="self.close();if (opener) opener.location.reload(true);">
		</xtags:when>
		<xtags:otherwise>
				<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
		</xtags:otherwise>
	</xtags:choose>
		</div>
	</div>
	
</div>

<%-- Add the colorpicker (initially hidden) --%>
<div id="plugin" onmousedown="HSVslide('drag','plugin',event)" style="TOP: 267px; LEFT: 310px; Z-INDEX: 20;">
 <div id="plugHEX" onmousedown="stop=0; setTimeout('stop=1',100);">F1FFCC</div><div id="plugCLOSE" onmousedown="toggle('plugin')">X</div><br>

 <div id="SV" onmousedown="HSVslide('SVslide','plugin',event)" title="Saturation + Value">
  <div id="SVslide" style="TOP: -4px; LEFT: -4px;"><br /></div>
 </div>
 <form id="H" onmousedown="HSVslide('Hslide','plugin',event)" title="Hue">
  <div id="Hslide" style="TOP: -7px; LEFT: -8px;"><br /></div>
  <div id="Hmodel"></div>
 </form>
</div>

<script type="text/javascript">
<%-- Customize mkcolor() function below to perform the desired action when the color 
	 picker is being dragged/used. 
	 Parameter "v" contains the latest color being selected.
	 In this case, update the background color, value and title of the input field
	 activated by the user (variable 'colorbox').  --%>
function mkColor(v){
	$S(colorbox).backgroundColor ='#'+v;
	$S(colorbox).color ='#'+v;
	$(colorbox).value ='#'+v;
	$(colorbox).alt ='#'+v;
	$(colorbox).title ='#'+v;
}
</script>

</body>
</html>