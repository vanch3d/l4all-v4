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
	String admin = (String) session.getAttribute(ServletUtilities.SESSION_ADMIN);
	if (null == username) 
	{
		// if session has expired (or user logged out)
		request.setAttribute("LOGIN_MSG", "Session has ended. You need to login again before using the system.");
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
		return;
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

	// Get the cookie's values for the timeline
	Cookie[] cookies = request.getCookies();
    String tlInterval = ServletUtilities.getCookieStringValue(cookies,"TL_Interval");
	try
	{
	    tlInterval = "" + Integer.parseInt(tlInterval);
	}catch (NumberFormatException nfe)
	{
	    tlInterval = "150";
	}
    String tlScale = ServletUtilities.getCookieStringValue(cookies,"TL_Scale");
    if (tlScale==null || tlScale.isEmpty()) tlScale = "Timeline.DateTime.YEAR";

    String strFriends = ServletUtilities.getCookieStringValue(cookies,"TL_Friends");
    if (strFriends == null)
	{
	    Cookie searchStringCookie = new Cookie("TL_Friends", username + "%test1%test2%test3");
    	    searchStringCookie.setMaxAge(ServletUtilities.SECONDS_PER_YEAR);
    	    response.addCookie(searchStringCookie);
		strFriends = username + "%test1%test2%test3";
		
	}
    String listFriends[] = strFriends.split("%");
    int f = listFriends.length;
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>L4ALL - Timeline</title>
	
	<style type="text/css">
	<!-- 
		@import "../css/skidoo_redux.css"; 
		@import "../css/skidoo_redux_theme.css";
		@import "../css/skidoo_l4all.css";
		@import "../css/skidoo_timeline.css";
	-->
	</style>
	
	<style type="text/css">
	<%--
		#outer-column-container, #inner-column-container { border-right-width: 0; }
		#right-column {	display: none; }
		#source-order-container { margin: 0; }
	--%>
	
		#right-column h3 {
			text-align: right;
		}

		div.TimeBar {
			max-width: 2000px;
			height: 430px;
			border: 1px solid #aaa;
			margin-top: 10px;
			z-index: 50;
		}
		
		div.userid
		{
			border: medium outset #B7FAFF;
			float: right;
			margin-top: -2px;
			color: #B7FAFF;
		}
		
		#usericon
		{
			 background-color:red;
		}
		
		th {
			text-align: left;
		}
		#masthead {
			background-image: url("../images/l4all.png");
			background-repeat: repeat;
			background-position: 0% 0%;
		}
		
		.timeline-event-bubble-body {
			font-size: 80%;
			font-family: arial, helvetica, sans-serif;
		}
		
		.timeline-copyright {
			background-color:#DDDDFF;
			border:1px solid #9999FF;
		}
	</style>
	<link rel="stylesheet" href="../css/skidoo_redux_print.css" type="text/css" media="print">
	<script type="text/javascript" src="../js/ruthsarian_utilities.js"></script>
	<script type="text/javascript" src="../js/dropdown.js"></script>
	<script type="text/javascript" src="../js/ajax.js"></script>
	<script type="text/javascript" src="../js/date.js"></script>
	<script type="text/javascript" src="../timeline/timeline-api.js"></script>
</head>

<script type="text/javascript">
<!--
	var font_sizes = new Array( 100, 110, 120 ); 
	var current_font_size = 0;
	if ( ( typeof( loadFontSize ) ).toLowerCase() != 'undefined' ) { event_attach( 'onload' , loadFontSize ); }
	if ( ( typeof( set_min_width ) ).toLowerCase() != 'undefined' ) { set_min_width( 'page-container' , 600 ); }
	if ( ( typeof( sfHover ) ).toLowerCase() != 'undefined' ) { event_attach( 'onload' , function () { 

				sfHover( 'middlemenu',true );
		} ); }
//-->
</script>

<script type="text/javascript">
var mywindow = null;

<%-- Initialize the document on loading --%>
function loadDocument()
{
	loadTimeline() ;
}

<%-- Create a popup window, reusing the same browser window if available --%>
function popUp(URL)
{	
	mywindow = window.open(URL, 'details', 
		'toolbar=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,' + 
		'width=600,height=500,left = 0,top = 0');
	mywindow.focus();
}

<%-- Necessary cleaning when the page is closed --%>
function unloadDocument()
{
	if (mywindow) mywindow.close();
}

function setCookie(c_name,value,expiredays)
{
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}

function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=")
  if (c_start!=-1)
    { 
    c_start=c_start + c_name.length+1 
    c_end=document.cookie.indexOf(";",c_start)
    if (c_end==-1) c_end=document.cookie.length
    return unescape(document.cookie.substring(c_start,c_end))
    } 
  }
return ""
}

function removeFriend(friend)
{
	var outc = document.getElementById("friend_"+ friend);
	if (outc)
	{
		//outc.style.display = "none";
		alert(document.cookie);
		var myval = getCookie("TL_Friends");
		var myfriend_array=myval.split("|");
		var newstr = "";
		for (i=0;i<myfriend_array.length;i++)
		{
			if (myfriend_array[i]==friend)
			{
				alert(myfriend_array[i]);
				}
			else
			{
				newstr += myfriend_array[i]+"|";
			}
				
		}
		alert(newstr);
		setCookie("TL_Friends",newstr,-1);
	}
}

function toggleRightPanel(show)
{
	if (!show)
	{
		var outc = document.getElementById("outer-column-container");
		outc.style.borderRightWidth = 0;
		outc = document.getElementById("inner-column-container");
		outc.style.borderRightWidth = "1px";
		outc = document.getElementById("right-column");
		outc.style.display = "none";
		outc = document.getElementById("source-order-container");
		outc.style.margin = "-1px";
		outc = document.getElementById("openpane");
		outc.style.display = "inline";
//		#outer-column-container, #inner-column-container { border-right-width: 0; }
	//	#right-column {	display: none; }
		//#source-order-container { margin: 0; }
	}
	else
	{
		var outc = document.getElementById("outer-column-container");
		outc.style.borderRightWidth = "13em";
		outc = document.getElementById("inner-column-container");
		outc.style.borderRightWidth = "1px";
		outc = document.getElementById("right-column");
		outc.style.display = "inline";
		outc = document.getElementById("source-order-container");
		outc.style.margin = "-1px";
		outc = document.getElementById("openpane");
		outc.style.display = "none";
	}
}
</script>

<script type="text/javascript">
var timeline=null;			<%-- The timeline widget --%>
var eventUser=null;			<%-- The user's list of events --%>
var eventSimilar=null;		<%-- The secondary list of events --%>
var theme = null;

<%-- Initialise the timeline with the user's episodes --%>
function loadTimeline() {
	<%-- Create and redefine the theme --%>
	theme = Timeline.ClassicTheme.create();
    theme.event.label.width = 250;
    theme.event.bubble.width = 300;
    theme.event.bubble.height = 200;
	theme.event.track.gap = 0.1;
	theme.event.instant.impreciseOpacity = 50;
	theme.ether.backgroundColors = [
                "#D1CECA",
                "#E7DFD6",
                "#E8E8F4",
                "#D0D0E8"];
	
	<%-- Create the two event sources (user + similar) --%>
	eventUser = new Timeline.DefaultEventSource();
	eventSimilar = new Timeline.DefaultEventSource();
        
	<%-- Create the 3 event bands --%>
    var bandInfos = [
    	<%-- Define summary band at the top --%>
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

    	<%-- Define user band in the middle --%>
		Timeline.createBandInfo({
			eventSource:    eventUser,
			date:           "2007",
			width:          "80%", 
			intervalUnit:   <%=tlScale%>,
			//multiple:		2,
			theme:          theme, 
			intervalPixels: <%=tlInterval%>
			}),
			
    	<%-- Define the (empty) similar timeline at the bottom --%>
		Timeline.createBandInfo({
			eventSource:    eventSimilar,
			date:           "2007",
			width:          "0%", 
			intervalUnit:   <%=tlScale%>,
			theme:          theme, 
			//multiple:		2,
			intervalPixels: <%=tlInterval%>
			})
	];
             
    <%-- Synchronise the timelines --%>
	bandInfos[0].syncWith = 1;
	bandInfos[1].syncWith = 2;
	bandInfos[0].highlight = true;
	
	<%-- Add the "future" decorator --%>
	for (var i = 0; i < bandInfos.length; i++) 
	{
		bandInfos[i].decorators = [
			new Timeline.SpanHighlightDecorator({
            	startDate:  "<%= currdate %>",
                endDate:    "<%= futuredate %>",
                color:      "#FFDAB9",
                opacity:    50,
                startLabel: "",
                endLabel:   "",
                theme:      theme
                }),
			new Timeline.PointHighlightDecorator({
            	date:  		"<%= currdate %>",
            	width:		5,
                color:      "#FF0000",
                opacity:    20
                })                
                ];
	}	
	
	<%-- Create the timeline widget --%>
	timeline = Timeline.create(document.getElementById("userTimeline"), bandInfos);
	
	<%-- Load the user's episodes --%>
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
       t2.onclick= function() { closeBubbles(); removeEps(info); return true; };
	
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

function setSync()
{
	bandInfos[1].syncWith = null;
	bandInfos[0].highlight = true;
	timeline.layout();

}

function setSync(checked)
{
	alert(checked);
	if (checked)
	{	
		timeline._bands[1].setSyncWithBand(timeline._bands[2]);
	}
	else
	{
		if (timeline._bands[1]._syncWithBand) {
			timeline._bands[1]._syncWithBand.removeOnScrollListener(timeline._bands[1]._syncWithBandHandler);
			timeline._bands[1]._syncWithBand = null;
		}
	}

//   
	//timeline.layout();

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
	elmtCopyright.id = "userElement";
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
	
	var cb = document.createElement("input"); // create input node
	cb.type = "checkbox"; // set type
	cb.name = "checkboxName"; // set name if necessary
	cb.id = "checkboxName"; // set name if necessary
    cb.title = "Enable/Disable the synchronisation";
	cb.value="Close";
	cb.checked = cb.defaultChecked = true; // make it checked now and by default
    cb.onclick= function() { setSync(cb.checked); return true; };
	label = document.createElement('label');
	label.htmlFor = 'checkboxName';
	label.innerHTML = 'Synchronise';
	 
	elmtCopyright.appendChild(cb);
	elmtCopyright.appendChild(label);
	elmtCopyright.appendChild(document.createElement("br"));
	elmtCopyright.appendChild(btn1);
	elmtCopyright.appendChild(document.createElement("br"));
	elmtCopyright.appendChild(btn2);
	
	timeline.addDiv(elmtCopyright);
	
	try
	{
		timeline.loadXML("<%= serverURL %>getTimeline?username="+simil+"&format=JSON&edit=false", function(json, url)  
		{                
			eventSimilar.loadXML(json, url);
			if (eventSimilar._description)
			{
				cp = document.getElementById('desc_'+simil);
				if (cp)
					cp.innerHTML = eventSimilar._description;
			}
		});
		timeline.layout();
	}
	catch(err)
	{
		alert("DDDD");;
	}
	
}

Timeline._Impl.prototype.loadXML = function(url, f) {
    var tl = this;
    
    var fError = function(statusText, status, xmlhttp) {
        alert("Failed to load data xml from " + url + "\n" + statusText);
        tl.hideLoadingMessage();
    };
    var fDone = function(xmlhttp) {
        try {
            var xml = xmlhttp.responseXML;
            if (!xml.documentElement && xmlhttp.responseStream) {
                xml.load(xmlhttp.responseStream);
            } 
            f(xml, url);
        } 
        catch (e)
        {
         	alert("Failed to load data xml from " + url + "\n" + (Timeline.Platform.isIE ? e.message : e));
			closeUser();
        }
        finally {
            tl.hideLoadingMessage();
        }
    };
    
    this.showLoadingMessage();
    window.setTimeout(function() { Timeline.XmlHttp.get(url, fError, fDone); }, 0);
};


/////////////////////////////////////////////////////////////////////
/// Remove the similar user timeline from the widget
/////////////////////////////////////////////////////////////////////
function closeUser()
{
	var label=document.getElementById("userElement");	
	if (label==null) return;

	timeline._bandInfos[0].width="20%";
	timeline._bandInfos[1].width="80%";
	timeline._bandInfos[2].width="0%";
	eventSimilar.clear();
	while(label.hasChildNodes() ) { label.removeChild( label.lastChild ); }
	    
	timeline.removeDiv(label);
	timeline.layout();
}

function removeEps(info)
{
	var answer = confirm("Do you really want to remove this episode from yout timeline ["+info+"] ?");
	if (!answer) return;
	timeline.showLoadingMessage();	
	var xml = new JKL.ParseXML( "../removeEpisode?username=${username}&timelineName=${username}&position="+eval(info+1));
    xml.async( getRemovalDetail );
    xml.onerror(noUser);
    xml.parse();
}

function getRemovalDetail(data)
{
	var type = data.main_message.message_type;
	if (type == "ERROR")
		alert(data.main_message.message_content.error_description);
	else
	{
		updateTimeline();
	}
	timeline.hideLoadingMessage();	

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

function getRowContent(doc,title,elt)
{
	var tr = doc.createElement("tr");
	td1 = doc.createElement("th");
	td2 = doc.createElement("td");
	td1.appendChild(doc.createTextNode(title));
	td2.appendChild(doc.createTextNode(elt));
	tr.appendChild(td1);
    tr.appendChild(td2);
    return tr;

}
/////////////////////////////////////////////////////////////////////
/// Callback function when the user is found
/////////////////////////////////////////////////////////////////////
function getUserDetail(data)
{
	closeBubbles();

	var usercnt = data.main_message.message_content.user;
	var label=document.getElementById("userElement");	
	
	var c = Timeline.DOM.getPageCoordinates(label);
	var x = c.left+250;
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
	tab = bbl.ownerDocument.createElement("table");
	tabbody = bbl.ownerDocument.createElement("tbody");

	tabbody.appendChild(getRowContent(bbl.ownerDocument,"Name:",usercnt.fullname));
	tabbody.appendChild(getRowContent(bbl.ownerDocument,"Year of birth:",usercnt.age));
	tabbody.appendChild(getRowContent(bbl.ownerDocument,"Location:",usercnt.location));
	tabbody.appendChild(getRowContent(bbl.ownerDocument,"Qualification:",usercnt.qual));
	tabbody.appendChild(getRowContent(bbl.ownerDocument,"Skills:",usercnt.skills));
	tabbody.appendChild(getRowContent(bbl.ownerDocument,"Interests:",usercnt.interests));
	
	tab.appendChild(tabbody);
	divBody.appendChild(tab);
	bbl.appendChild(divBody);
}


</script>

<script type="text/javascript">
function createEpisode(type)
{
	 var c = document.getElementById('target_episode');
	 if (c!=null) c.style.visibility = 'hidden';
	 popUp('createEpisode.jsp?type='+type);
}
</script>

<body onload="loadDocument();" onresize="resizeTimeline();" onunload="unloadDocument();">

<div id="page-container">
<div id="masthead">
	<div class="inside">
	<div class="userid">
		<table border="0" cellspacing="0" cellpadding="2"><tbody><tr>
		<td style="vertical-align: middle;">user: <b>${username}</b><br>timeline: <b>public</b></td>
		<td style="vertical-align: middle;"><img id="usericon" border="0" src="../images/<%= (admin!=null)? "user_admin.png" : "user.png"%>" width="25px" height="25px"></td>
		</tr></tbody></table>
	</div><h1>&nbsp;</h1>
	</div>
</div>

<div id="outer-column-container">

	<div id="inner-column-container">
	<div id="source-order-container">
	
	<div id="middle-column"><div class="inside">
	
	<!-- Begin Timeline menu -->
	<div class="clearfix rMenu-hor"> 
	<ul id="middlemenu" class="clearfix rMenu-hor rMenu">
		<li><span>My Timeline ...</span></li>
		<li class="clearfix rMenu-expand">
			<a href="javascript:void(0);">Add Episode</a> 
			<ul class="clearfix rMenu-ver">			
	<% // Create the list of widgets from the episodes taxonomy
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
			out.print("						<li><a href=\"javascript:createEpisode('"+type+"')\">");
			out.print("<IMG align=\"absmiddle\" SRC=\"" + EpisodeType.getSmallImage(type) + "\">");
			out.print("&nbsp;"+type);
			out.println("</a></li>");
			count++;
		}
		out.println("					</ul>");
		out.println("				</li>");
	}%>			
			</ul>
	</ul> 
	<div id="openpane"><a href="javascript:toggleRightPanel(true);">Show ></a></div>
	</div><!-- End Timeline menu -->
	

	<div class="TimeBar" id="userTimeline"></div>

	</div></div>
	
	<!-- Begin Left Column -->
	<div id="left-column"><div class="inside">

		<h3>My Profile</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="popUp('editDetails.jsp?newUser=0');return false;">Details</a></li>
			<li><a href="javascript:void(0);" onclick="popUp('registerLearningPrefs.jsp');return false;">Preferences</A></li>
			<li><a href="javascript:void(0);" onclick="popUp('editTimeline.jsp?action=edit');return false;">My Timeline</a></li>
		</ul>

		<h3>Search</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="popUp('searchSimilar.jsp');return false;">Similar Timelines</a></li>
			<li><a href="javascript:void(0);" onclick="popUp('searchPeople.jsp');return false;">Timelines</a></li>
			<li><a href="javascript:void(0);" onclick="popUp('searchPeople.jsp');return false;">People</a></li>
			<li><a href="javascript:void(0);" onclick="popUp('searchCourse.jsp');return false;">Courses</a></li>
			<li><a href="javascript:void(0);" onclick="popUp('searchRecom.jsp');return false;">Advices</a></li>
		</ul>
		
		<h3>L4All</h3>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><a href="javascript:void(0);" onclick="popUp('about.jsp');return false;">Help</a></li>
		</ul>

		<p class="fontsize-set"></p>
		<ul id="leftmenu" class="rMenu-wide rMenu-ver rMenu">
			<li><A href="#" onclick="document.location.href='logout.jsp';return false;">Log Out</A></li>
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
		<div id="closepane">
			<a href="javascript:toggleRightPanel(false);">Hide</a>
		</div>
		<h3>My Friends</h3>
		<div class="friendlist">
		<% for (int h=0;h<listFriends.length;h++)
			{%>
				<div class="friend" id="friend_<%= listFriends[h] %>">
				<img src="../images/user.png">
				<button class="delete" onclick="javascript:removeFriend('<%= listFriends[h] %>');">X</button>
				<div class="title"><a href="javascript:showSimilarUser('<%= listFriends[h] %>');"><%= listFriends[h] %></a></div>
				<div class="description" id="desc_<%= listFriends[h] %>">????</div>
			</div>
			    
		<%	}%>
		</div>
	
	</div></div>
	<div class="clear-columns"><!-- do not delete --></div>

	</div></div>

	<div id="footer"><div class="inside">
		<div class="copyright">L4ALL © Birkbeck College - 2007</div>
		<% if (admin!=null) {%>
		<div class="admin"><a href="../admin/index.jsp">Go to Administration Panel</a></div>
		<% } %>
	</div></div>
</div>


</body>
</html>