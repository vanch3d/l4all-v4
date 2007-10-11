<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.EpisodeType"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.TrailEpisode"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Message"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Trail"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.EpisodeClassification"%>

<jsp:useBean id="trailMgr" class="uk.ac.bbk.dcs.l4all.beans.UserTrailManager"/>
<%
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	String category = (String) request.getParameter("type") ;
	String timelineName = (String) request.getParameter("timelineName") ;
	String nature = (String) request.getParameter("nature") ;
	if (timelineName==null)
	    timelineName = username;
	pageContext.setAttribute("username",username);
	pageContext.setAttribute("timelineName",timelineName);
	
	Message msg = trailMgr.getUserTrailDetails(username, timelineName);
	Trail trail = (Trail)msg.getResultObject();		
	TrailEpisode[] tEpisodes = trail.getTrailEpisodes();
	int eposition = tEpisodes.length+1;
	TrailEpisode tEpisode = new TrailEpisode();
	tEpisode.setCategory(category);
	tEpisode.setOwnerTmln(timelineName);
	if (nature!=null)
	    tEpisode.setNature(nature);
	boolean isduration = EpisodeType.isDuration(tEpisode.getCategory());
	boolean isFixed = Trails.TYPE_TEMPLATE.equals(trail.getTrailType());
	
	String img = EpisodeType.getImage(category);
	pageContext.setAttribute("trail",trail);
	pageContext.setAttribute("imageType",img);
	pageContext.setAttribute("episode",tEpisode);
	pageContext.setAttribute("epos",new Integer(eposition));
	pageContext.setAttribute("isDuration",new Boolean(isduration));
	
	// Get the primary/secondary classification schema for the episode, based on its type 
	EpisodeClassification primClf = EpisodeClassification.getPrimaryFromType(tEpisode.getCategory());
	EpisodeClassification secClf = EpisodeClassification.getSecondaryFromType(tEpisode.getCategory());
	// Retrieve the associated XML file containing the elements (null if none)
	String primElts = primClf.getFile();
	String secElts = secClf.getFile();
	// Set the default code for the episode
	tEpisode.setPrimary(primClf.getDefaultCode());
	tEpisode.setSecondary(secClf.getDefaultCode());
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.Trails"%>
<html>
<head>
	<title>L4ALL Episode Editor</title>
	<style type="text/css">
		<!-- 
			@import "../css/skidoo_redux.css"; 
			@import "../css/skidoo_redux_theme.css";
			@import "../css/skidoo_l4all.css";
			@import "../css/skidoo_form.css";
			@import "../css/skidoo_popup.css";
		-->
	</style>
	<style type="text/css">
		#outer-column-container, #inner-column-container { border-right-width: 0; }
		#outer-column-container { border-left-width: 0; }
		#right-column {	display: none; }
		#left-column {	display: none; }
		#source-order-container { margin: 0; }
		#page-container {	min-width: 400px;}
	</style>

	<!-- calendar stylesheet -->
	<link rel="stylesheet" type="text/css" media="all" href="../jscalendar/calendar-system.css"/>
	
	<script type="text/javascript" src="../jscalendar/calendar.js"><!-- main calendar program --></script>
	<script type="text/javascript" src="../jscalendar/lang/calendar-en.js"><!-- language for the calendar --></script>
	<script type="text/javascript" src="../jscalendar/calendar-setup.js">
		<!-- the following script defines the Calendar.setup helper function, which makes
    	     adding a calendar a matter of 1 or 2 lines of code. -->
	</script>
	
	<!-- Common JS files -->
	<script type='text/javascript' src='../zapatec/utils/zapatec.js'></script>

	<!-- Custom includes -->	
	<script type='text/javascript' src='../zapatec/zpwin/src/window.js'></script>
	<script type='text/javascript' src='../zapatec/zptreepicker.js'></script>


<script type="text/javascript">

function updateTimeline()
{
	if (window.opener && !window.opener.closed)
		window.opener.updateTimeline();
}

function closeMe()
{
	if (opener) self.close();
	else parent.ajaxwin.hide();
}

function getHelp(msg,caption)
{
}

</script>
</head>

<body>

<div id="page-container">

<div id="masthead">
	<div class="inside">
	<img src="<c:out value="${imageType}"/>" ALIGN=top style="float: right; margin-top: -5px;">
	<h1>Create a New Episode</h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column">
			<div class="inside">
			
<form id="myform"  class="tableless" name="My_Episode" action="../addEpisode" method="post">
	<div class="debug">
			<input READONLY name="username" type="text" value="<c:out value='${username}'/>"> 
			<input READONLY name="timelineName" type="text" value="<c:out value='${episode.ownerTmln}'/>"> 
			<input READONLY name="position" type="text" value="<c:out value="${epos}"/>">
			<input READONLY name="category" type="text" value="<c:out value='${episode.category}'/>"> 
			<input READONLY name="prm_class" id="prm_class" type="text" value="<c:out value='${episode.primary}'/>"> 
			<input READONLY name="sec_class" id="sec_class" type="text" value="<c:out value='${episode.secondary}'/>"> 
	</div>

	<fieldset>
		<legend>"<c:out value='${episode.category}'/>" Episode</legend>
		<div class="notes">
			<h4>"<c:out value='${episode.category}'/>" Episode</h4>
			<p>Edit this episode</p>
			<p class="last">The fields in <b>bold</b> are compulsory.</p>
		</div>


	<%	if (primElts!=null) {%>
		<div class="required">
			<label for="primary"><%= primClf.getTitle() %>:</label>
			<input readonly="readonly" id="primary" class="inputPicker" value="${episode.primary}">
			<button type="button" class="inputPicker" id="pickerPrm" onclick="return false;">
				<img src="../images/treepicker.png" >
			</button> 
		</div>
	<%}%>
	<% if (secElts!=null) {%>
		<div class="required">
			<label for="secondary"><%= secClf.getTitle() %>:</label>
			<input readonly="readonly" id="secondary" class="inputPicker" value="${episode.secondary}">
			<button type="button" class="inputPicker" id="pickerSec" onclick="return false;">
				<img src="../images/treepicker.png">
			</button> 
		</div>
	<%}%>
	<div class="required"><fieldset>
			<legend>Nature:</legend>
			<label for="nature_0" class="labelRadio">
				<input class="inputRadio" type="radio" name="nature" id="nature_0" value="fact" <%= (tEpisode.getNature().equals("fact"))? "checked=\"checked\"" : "" %>/>
				Fact 
			</label>
			<label for="nature_1" class="labelRadio">
				<input class="inputRadio" type="radio" name="nature" id="nature_1" value="wish" <%= (tEpisode.getNature().equals("wish"))? "checked=\"checked\"" : "" %>/>
				Wish
			</label>
	</fieldset></div>
	
	<div class="required">
		<c:choose><c:when test='${isDuration}'>
		<label for="date_start">Start:</label>
	</c:when>
	<c:otherwise>
		<label for="date_start">Date:</label>
	</c:otherwise>
	</c:choose>
		<input class="inputPicker" name="start" size="30"  READONLY id="date_start" maxlength="50" type="text" value="<c:out value='${episode.start}'/>">
		<button class="inputPicker" type="button" id="trigger_start" >
			<img src="../jscalendar/calendar.gif">
		</button> 
	</div>
	
	<c:if test='${isDuration}'>
	<div class="required">
		<label for="date_end">End:</label>
		<input class="inputPicker" name="end" size="30"  READONLY id="date_end" maxlength="50" type="text" value="<c:out value='${episode.end}'/>">
		<button class="inputPicker" type="button" id="trigger_end" >
			<img src="../jscalendar/calendar.gif">
		</button> 
	</div>
	</c:if>
	
	<div class="required">
		<label for="title">Title:</label>
		<input name="title" id="title" maxlength="50" type="text" value="<c:out value='${episode.title}'/>"> 
	</div>	
	
	<div class="required">
		<label for="desc">Description:</label>
		<textarea name="desc" id="desc" rows="3"><c:out value="${episode.description}"/></textarea>
	</div>	

	<div class="optional">
		<label for="url">URL:</label>
			<input name="url"  id="url" size="30" maxlength="50" type="text" value="<c:out value='${episode.url}'/>"> 
	</div>	
	
	</fieldset>

	<fieldset>
		<div class="submit">
			<input name="save" class="inputSubmit" value="Add Episode" type="submit">
		</div>
	</fieldset>
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

	<div id="footer"><div class="inside">
		<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
		<div style="float: right;margin-top: -20px; font-size: xx-small; background-color:#f6f6f6;color:#f6f6f6 "> sddssd <a href='http://www.zapatec.com/' style="font-size: xx-small; background-color:#f6f6f6;color:#f6f6f6">&copy; Zapatec, Inc.</a></div>
	</div></div>

</div>

</body>

<script type="text/javascript">
Calendar.setup({
        inputField     :    "date_start",      // id of the input field
        ifFormat       :    "%Y/%m/%d",       // format of the input field
        showsTime      :    false,            // will display a time selector
        button         :    "trigger_start",   // trigger for the calendar (button ID)
        singleClick    :    true,           // double-click mode
        step           :    1,                // show all years in drop-down boxes (instead of every other year as default)
        cache 		   :	true				// cache the calendar object
    });
</script>
<c:if test='${isDuration}'>
<script type="text/javascript">
Calendar.setup({
        inputField     :    "date_end",      // id of the input field
        ifFormat       :    "%Y/%m/%d",       // format of the input field
        showsTime      :    false,            // will display a time selector
        button         :    "trigger_end",   // trigger for the calendar (button ID)
        singleClick    :    true,           // double-click mode
        step           :    1,                // show all years in drop-down boxes (instead of every other year as default)
        cache 		   :	true				// cache the calendar object
    });
</script>
</c:if>

<%	if (primElts!=null) {%>
<script type='text/javascript'>

var primaryPicker = new Zapatec.TreePicker({
		button : 'pickerPrm',
		inputValueField: 'prm_class',
		inputTextField: 'primary',
		itemID: '${episode.primary}',
		source: '../xml/<%=primClf.getFile()%>',
		title: '<%=primClf.getDescription()%>',
		offset: 0
	});

</script>
<%} %>

<%	if (secElts!=null) {%>
<script type='text/javascript'>

var secondaryPicker = new Zapatec.TreePicker({
		button : 'pickerSec',
		inputValueField: 'sec_class',
		inputTextField: 'secondary',
		itemID: '${episode.secondary}',
		source: '../xml/<%=secClf.getFile()%>',
		title: '<%=secClf.getDescription()%>',
		offset: 0
	});
	</script>
<%} %>

</html>