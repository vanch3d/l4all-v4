<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="uk.ac.bbk.dcs.l4all.vocabulary.EpisodeType"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Message"%>
<%@page import="uk.ac.bbk.dcs.l4all.beans.Trail"%>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>
    
<jsp:useBean id="trailMgr" class="uk.ac.bbk.dcs.l4all.beans.UserTrailManager"/>
<%
	response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	if (null == username) 
	{
	   request.setAttribute("LOGIN_MSG", "Session has ended. You need to login again before using the system.");
	   RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
	   rd.forward(request, response);
	}
	pageContext.setAttribute("username",username);

	// Get the server full path
	String serverURL = "http://" + request.getServerName() + ":" + request.getServerPort() + 
	request.getContextPath() + "/";

	// Get the start and end date of the timeline
	Message msg = trailMgr.getUserTrailDetails(username, username);
	Trail trail = (Trail)msg.getResultObject();		
	String start = trail.getStart();
	String end = trail.getEnd();
	
	// Get the current date
	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	String currdate = sdf.format(cal.getTime());
	cal.add(Calendar.YEAR, 1000);
	String futuredate = sdf.format(cal.getTime());
%>
<% 
	// Get the cookie's values for the timeline
	Cookie[] cookies = request.getCookies();
    String tlInterval = ServletUtilities.getCookieStringValue(cookies,"tlInterval");
    if (tlInterval==null)
    {
		System.out.println("create the cookies");
		tlInterval = "150";
	    Cookie searchStringCookie = new Cookie("tlInterval", tlInterval);
		response.addCookie(searchStringCookie);
    }
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
		<style type="text/css">
			<!-- 
				@import "../css/skidoo_redux.css"; 
				@import "../css/skidoo_redux_theme.css";
			-->
		</style>
		<style type="text/css">
			<!--
				/* ** DEMO CSS BEGINS ** */

				#outer-column-container, #inner-column-container { border-right-width: 0; }
				#right-column {	display: none; }
				#source-order-container { margin: 0; }
div.TimeBar
{
    max-width: 1000px;
    height: 600px;
    border: 1px solid #aaa;

}
				#masthead
				{
					background-image: url("../images/l4all.png");
					background-repeat: repeat;
					background-position: 0% 0%;
				}

				/* ** DEMO CSS ENDS ** */
			-->
		</style>
		<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
		<script type="text/javascript" src="../js/ruthsarian_utilities.js"></script>

		<script type="text/javascript">
		<!--
			var font_sizes = new Array( 100, 110, 120 ); 
			var current_font_size = 0;
			if ( ( typeof( loadFontSize ) ).toLowerCase() != 'undefined' ) { event_attach( 'onload' , loadFontSize ); }
			if ( ( typeof( set_min_width ) ).toLowerCase() != 'undefined' ) { set_min_width( 'page-container' , 600 ); }
			if ( ( typeof( sfHover ) ).toLowerCase() != 'undefined' ) { event_attach( 'onload' , function () { 
				sfHover( 'leftmenu' );
				sfHover( 'middlemenu' );
			} ); }
		//-->
		</script>
</head>

<script type="text/javascript" src="../js/dropdown.js"></script>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript" src="../js/date.js"></script>
<script src="../timeline/timeline-api.js" type="text/javascript"></script>
		<script type="text/javascript" src="ruthsarian_utilities.js"></script>

<script type="text/javascript">
var mywindow = null;

/////////////////////////////////////////////////////////////////////
/// Initialize the document on loading
/////////////////////////////////////////////////////////////////////
function loadDocument()
{
	loadTimeline() ;
}

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

/////////////////////////////////////////////////////////////////////
/// Necessary cleaning when the page is closed
/////////////////////////////////////////////////////////////////////
function unloadDocument()
{
	if (mywindow) mywindow.close();
}
</script>

<script type="text/javascript">
var timeline;		///< The timeline widget
var eventUser;		///< The user's list of events
var eventSimilar;	///< The secondary list of events
var userSimilar=null;
var elmtCopyright = null;
var theme = null;

/////////////////////////////////////////////////////////////////////
/// Initialise the timeline with the user's episodes
/////////////////////////////////////////////////////////////////////
function loadTimeline() 
{
	// Create and redefine the theme
	theme = Timeline.ClassicTheme.create();
    theme.event.label.width = 250; // px
    theme.event.bubble.width = 300;
    theme.event.bubble.height = 200;
	theme.event.track.gap = 0.1;
	theme.ether.backgroundColors = [
                "#D1CECA",
                "#E7DFD6",
                "#E8E8F4",
                "#D0D0E8"];
	
	// Create the two event sources (user + similar)
	eventUser = new Timeline.DefaultEventSource();
	eventSimilar = new Timeline.DefaultEventSource();
        
	// Create the 3 event bands
    var bandInfos = [
    	// Define summary band at the top
		Timeline.createBandInfo({
			showEventText:  false,
			trackHeight:    0.3,
			trackGap:       0.2,
			eventSource:    eventUser,
			date:           "2007",
			width:          "20%", 
			intervalUnit:   Timeline.DateTime.DECADE, 
			theme:          theme, 
			intervalPixels: 200}),

    	// Define user band in the middle
		Timeline.createBandInfo({
			eventSource:    eventUser,
			date:           "2007",
			width:          "80%", 
			intervalUnit:   Timeline.DateTime.YEAR,
			theme:          theme, 
			intervalPixels: <%=tlInterval%>
			}),
			
    	// Define the (empty) similar timeline at the bottom
		Timeline.createBandInfo({
			eventSource:    eventSimilar,
			date:           "2007",
			width:          "0%", 
			intervalUnit:   Timeline.DateTime.YEAR,
			theme:          theme, 
			intervalPixels: <%=tlInterval%>
			})
	];
             
    // Synchronise the timelines   
	bandInfos[0].syncWith = 1;
	bandInfos[1].syncWith = 2;
	bandInfos[0].highlight = true;
	//bandInfos[2].eventPainter.setLayout(bandInfos[2].eventPainter.getLayout());
	//bandInfos[1].eventPainter.setLayout(bandInfos[1].eventPainter.getLayout());
	//bandInfos[0].eventPainter.setLayout(bandInfos[0].eventPainter.getLayout());
	
	// Add the "future" decorator
	for (var i = 0; i < bandInfos.length; i++) 
	{
		bandInfos[i].decorators = [
			new Timeline.SpanHighlightDecorator({
            	startDate:  "<%= currdate %>",
                endDate:    "<%= futuredate %>",
                color:      "#FFDAB9",
                opacity:    50,
                startLabel: "now",
                endLabel:   "you are dead!",
                theme:      theme
                })];
	}	
	
	// Create the timeline widget
	timeline = Timeline.create(document.getElementById("userTimeline"), bandInfos);
	
	// Load the user's episodes
	timeline.loadXML("<%= serverURL %>getTimeline?username=${username}&format=JSON", function(json, url)  
	{                
		eventUser.loadXML(json, url);
	});
}

/////////////////////////////////////////////////////////////////////
/// Adjust the layout of the timeline
/////////////////////////////////////////////////////////////////////
var resizeTimerID = null;
function resizeTimeline() 
{
    if (resizeTimerID == null) {
        resizeTimerID = window.setTimeout(function() {
            resizeTimerID = null;
            timeline.layout();
        }, 500);
    }
}

/////////////////////////////////////////////////////////////////////
// Restrict the scrolling of the timeline
/////////////////////////////////////////////////////////////////////
Timeline._Band.prototype._moveEther = function(shift)
{
    this.closeBubble();
    
    var datemin = this.getMinVisibleDate().getFullYear();
    var datemax = this.getMaxVisibleDate().getFullYear();
	if ((datemin< <%= start %> && shift>0)|| (datemax> (<%= end %>-1) && shift<0))
	{
		return;
	}
   
    this._viewOffset += shift;
    this._ether.shiftPixels(-shift);
    if (this._timeline.isHorizontal()) 
    { this._div.style.left = this._viewOffset + "px";} 
    else 
    { this._div.style.top = this._viewOffset + "px";}
    
    if (this._viewOffset > -this._viewLength * 0.5 ||
        this._viewOffset < -this._viewLength * (Timeline._Band.SCROLL_MULTIPLES - 1.5))
	{ this._recenterDiv();} 
	else 
	{ this.softLayout();}
    this._onChanging();
}

/////////////////////////////////////////////////////////////////////
// Redefine the content of the info bubble
/////////////////////////////////////////////////////////////////////
Timeline.DefaultEventSource.Event.prototype.fillWikiInfo= function(elmt)
{
	var info = this.getPosition();
	var edit = this.isEditable();
	//alert(edit);

       var t = document.createElement("input");
	t.type="button";
      	t.title = "Change the details of this episode";
		t.value="Edit";
	t.disabled=!(this.isEditable());
       t.onclick= function() { closeBubbles();popUp("editEpisode.jsp?episode="+info); return true; };

       var t2 = document.createElement("input");
	t2.type="button";
      	t2.title = "Remove this episode from the timeline";
	t2.value="Delete";
		t2.disabled=!(this.isEditable());
       t2.onclick= function() { closeBubbles(); return true; };
	
       elmt.appendChild(t);
       elmt.appendChild(t2);
}
	
/////////////////////////////////////////////////////////////////////
// Redefine the format of the dates in the bubble
/////////////////////////////////////////////////////////////////////
Timeline.DefaultEventSource.Event.prototype.fillTime= function(elmt, labeller)		
{
	elmt.appendChild(elmt.ownerDocument.createTextNode(formatDate(this._start,"EE MMM d, y")));
    elmt.appendChild(elmt.ownerDocument.createElement("br"));
    elmt.appendChild(elmt.ownerDocument.createTextNode(formatDate(this._end,"EE MMM d, y")));
}

  /////////////////////////////////////////////////////////////////////
// Redefine the format of the dates in the bubble
/////////////////////////////////////////////////////////////////////
Timeline.DurationEventPainter.prototype._showBubble = function(x, y, evt) 
{
	closeBubbles();
    var div = this._band.openBubbleForPoint(
        x, y,
        this._theme.event.bubble.width,
        this._theme.event.bubble.height
    );
	evt.fillInfoBubble(div, this._theme, this._band.getLabeller());
};		
		
/*Timeline.DurationEventPainter.prototype._showBubble = function(x, y, evt) 
{
    var title = evt.getText();
	var link = evt.getLink();
	var image = evt.getImage();
	var desc = evt.getDescription();
	var start = this._band.getLabeller().labelPrecise(evt.getStart() );
	var end= this._band.getLabeller().labelPrecise(evt.getEnd() );
	var info = evt.getPosition();

	displayStaticMessage("<div class=\'timeline-event-bubble-title\'>"+
	    "<img class=\'timeline-event-bubble-image\' src=\'../images/work.png\'>"+
	    "   <a href=\'http://www.allposters.com/-sp/Rhythm-Joie-de-Vivre-Posters_i1250641_.htm\' target=\'_new\'>" + title + "</a></div>"+
	    "<div class=\'timeline-event-bubble-body\'>" + desc + "<br>" + info + "</div>"+
	    "<div class=\'timeline-event-bubble-time\'>Start: " + start + "<br>End: " + end + "</div>"+
	    "<div class=\'timeline-event-bubble-wiki\'>"+
	    	"[<a href=\'#\' onclick=\'closeMessage();return false\'>Close</a>]"+
	    	"[<a href=\'#\' onclick=\"closeMessage();popUp(\'editEpisode.jsp?episode="+info+"\');return false\">Edit</a>]</div>"+
	    "",false);
}*/
	
/////////////////////////////////////////////////////////////////////
/// Close all the info bubbles open in the timeline
/////////////////////////////////////////////////////////////////////
function closeBubbles() 
{
	for (var i = 0; i < timeline._bands.length; i++) {
        timeline._bands[i].closeBubble();
    }
}

/////////////////////////////////////////////////////////////////////
/// Update the content of the user timeline
/////////////////////////////////////////////////////////////////////
function updateTimeline()
{
	eventUser.clear();
	timeline.loadXML("<%= serverURL %>getTimeline?username=${username}&format=JSON", function(json, url)  
	{                
		eventUser.loadXML(json, url);
	});

}

/////////////////////////////////////////////////////////////////////
/// Update the content of the similar user timeline
/////////////////////////////////////////////////////////////////////
function showSimilarUser(simil)
{
	closeUser();
 	timeline._bandInfos[0].width="20%";
	timeline._bandInfos[1].width="40%";
	timeline._bandInfos[2].width="40%";
	eventSimilar.clear();
	
	elmtCopyright = document.createElement("div");
	elmtCopyright.id = "myElement";
	elmtCopyright.className = "timeline-copyright";
	
	btn1 = document.createElement("input");
	btn1.type="button";
    btn1.title = "Show information about this user";
	btn1.value="Info";
	btn1.user = simil;
    btn1.onclick= function() { getUserInfo(simil); return true; };

	btn2 = document.createElement("input");
	btn2.type="button";
    btn2.title = "Close this user's timeline";
	btn2.value="Close";
    btn2.onclick= function() { closeUser(); return true; };
	btn2.className = "btn";
	
	elmtCopyright.appendChild(btn1);
	elmtCopyright.appendChild(document.createElement("br"));
	elmtCopyright.appendChild(btn2);
	
	timeline.addDiv(elmtCopyright);
	
	try
	{
		timeline.loadXML("<%= serverURL %>getTimeline?username="+simil+"&format=JSON&edit=false", function(json, url)  
		{                
			eventSimilar.loadXML(json, url);
		});
		timeline.layout();
	}
	catch(err)
	{
	}
	
}

/////////////////////////////////////////////////////////////////////
/// Remove the similar user timeline from the widget
/////////////////////////////////////////////////////////////////////
function closeUser()
{
	var label=document.getElementById("myElement");	
	if (label!=null)
	{
		timeline._bandInfos[0].width="20%";
		timeline._bandInfos[1].width="80%";
		timeline._bandInfos[2].width="0%";
		eventSimilar.clear();
		while(label.hasChildNodes() ) { label.removeChild( label.lastChild ); }
	    
		timeline.removeDiv(elmtCopyright);
		elmtCopyright = null;
	    
		timeline.layout();
	}
}


/////////////////////////////////////////////////////////////////////
/// Get the similar user's information
/////////////////////////////////////////////////////////////////////
function getUserInfo(user)
{	
	timeline.showLoadingMessage();	
	var xml = new JKL.ParseXML( "../getUserDetails?req_username=" + user );
    xml.async( getUserDetail );
    xml.onerror(noUser);
    xml.parse();
}

/////////////////////////////////////////////////////////////////////
/// Callback function when the user is not found
/////////////////////////////////////////////////////////////////////
function noUser(msg)
{
	timeline.hideLoadingMessage();	
	alert(msg);
}

/////////////////////////////////////////////////////////////////////
/// Callback function when the user is found
/////////////////////////////////////////////////////////////////////
function getUserDetail(data)
{
	closeBubbles();
	var c = Timeline.DOM.getPageCoordinates(elmtCopyright);
	var x = c.left+100;
	var y = c.top -10;

	timeline.hideLoadingMessage();	

	var bbl = timeline.getBand(2).openBubbleForPoint(x,y,
					theme.event.bubble.width,
			        theme.event.bubble.height);

	img = bbl.ownerDocument.createElement("img");
    img.src = "../images/user.png";
	theme.event.bubble.imageStyler(img);
	bbl.appendChild(img);
	
	divBody = bbl.ownerDocument.createElement("div");
    theme.event.bubble.bodyStyler(divBody);
    divBody.innerHTML = 
    	"<table>" + 
		"<tr><td>Name:</td><td>"+data.main_message.message_content.user.fullname+"</td></tr>" +
		"<tr><td>Age:</td><td>"+data.main_message.message_content.user.age+"</td></tr>" +
		"<tr><td>Location:</td><td>"+data.main_message.message_content.user.location+"</td></tr>" +
		"<tr><td>Qualification:</td><td>"+data.main_message.message_content.user.qual+"</td></tr>" +
		"</table>";
	bbl.appendChild(divBody);
}


</script>

<script type="text/javascript">
function selectWidget(type)
{
	 var c = document.getElementById('target_episode');
	 if (c!=null) c.style.visibility = 'hidden';
	 popUp('createEpisode.jsp?type='+type);
}

function helpWidget(type,show)
{
	var c = document.getElementById('tooltip');
	if (show==true) c.innerHTML = type.title;
	else c.innerHTML = '-';
 }

</script>
<body onload="loadDocument();" onresize="resizeTimeline();" onunload="unloadDocument();">


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
	
	<!-- Begin Timeline menu -->
	<div class="clearfix rMenu-center"> 
	<ul id="middlemenu" class="clearfix rMenu-hor rMenu">
		<li><span>My Timeline ...</span></li>
		<li class="clearfix rMenu-expand">
			<a href="javascript:void(0);">Add Episode</a> 
			<ul class="clearfix rMenu-ver">			
<%

	// Create the list of widgets from the episodes taxonomy
	List cat = EpisodeType.getCategories();
	for (int i=0;i<cat.size();i++)
	{
		String desc = EpisodeType.getDescription((String)cat.get(i));
		out.println("				<li class=\"rMenu-expand\">");
		out.println("					<a href=\"javascript:void(0);\">"+desc+"</a>");
		out.println("					<ul class=\"\">");
		List types = EpisodeType.getTypes((String)cat.get(i));
		int count = 0;
		for (int j=0;j<types.size();j++)
		{	
			String type = (String)types.get(j);
			desc = EpisodeType.getDescription(type);
			if (desc.isEmpty() || desc.equals(" ")) desc = "-";
			out.print("						<li><a href=\"javascript:selectWidget('"+type+"')\">");
			out.print("<IMG align=\"absmiddle\" SRC=\"" + EpisodeType.getSmallImage(type) + "\">");
			out.print("&nbsp;"+type);
			out.println("</a></li>");
			count++;
		}
		out.println("					</ul>");
		out.println("				</li>");
	}
%>			
			</ul>
	</ul>
	</div><!-- End Timeline menu -->

	<p></p>
	<div class="TimeBar" id="userTimeline"></div>

	</div></div>
	
	<!-- Begin Left Column -->
	<div id="left-column"><div class="inside">

		<h3>My Profile</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="popUp('editDetails.jsp?newUser=0');return false;">Details</a></li>
			<li><a href="javascript:void(0);" onclick="popUp('registerLearningPrefs.jsp');return false;">Preferences</A></li>
			<li><span>My Timeline</span></li>
		</ul>

		<h3>Search</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
				<li><a href="javascript:void(0);" onclick="popUp('searchTimeline.jsp');return false;">Search Timelines</a></li>
				<li><a href="javascript:void(0);" onclick="popUp('searchCourse.jsp');return false;">Search Courses</a></li>
		</ul>

		<p class="fontsize-set"></p>
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

	<p>This website layout and associated stylesheets are released into the public domain. You do not need permission to use this layout.</p>

	</div></div>
</div>
</body>
</html>