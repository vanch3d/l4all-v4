<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>About L4ALL</title>

	<link rel=StyleSheet href="../css/tabber.css" type="text/css" media="screen">
	<style type="text/css">
		<!-- 
			@import "../css/skidoo_redux.css"; 
			@import "../css/skidoo_redux_theme.css";
			@import "../css/skidoo_l4all.css";
			@import "../css/skidoo_popup.css";
		-->
	</style>
	<script type="text/javascript">
		<%-- Indicate tabber to use 'div' as the tab placeholder (instead of 'form') --%>
		var tabberOptions = {'tabElement':'div'};
	</script>
	<script type="text/javascript" src="../js/tabber-minimized.js"></script>
</head>

<script type="text/javascript">
function closeMe()
{
	if (opener) self.close();
}
</script>

<body>


<div id="page-container">

<div id="masthead">
	<div class="inside">
	<h1>About L4ALL</h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column">
			<div class="inside">


<div class="tabber" id="tabber">

<div class="tabbertab" id="help" title="About L4ALL">
<h2>Welcome to Lifelong Learning for All (L4All)!</h2>

<p>The aim of this website is to help you clarify your learning needs and provide tools that 
will help your learning and career development.</p>

<p>L4All enables you to create your own personal timeline based on your previous learning and 
work related experiences. You can then use this to develop a personal development plan and 
identify learning pathways that meet your future learning and career development needs and 
requirements.</p>

<p>A key feature of L4All is that it provides you with access to a range of quality-assurred 
materials around London.</p>

</div>

<div class="tabbertab" id="help"  title="Timelines">
<h2>Using the Timeline</h2>
<p>The timeline represents all your past (and future) events that you have ...</p>

<p align="center"><img src="../images/help_timeline.png"></p>

</div>

<div class="tabbertab" id="help"  title="Searches">
<p>In L4ALL, several search tools are available:</p>
<ul>
	<li>similarity-based search for people like me
	<li>keyword-based search for timelines
	<li>keyword-based search for people
</ul>
</div>

</div>

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
			<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
	</div>
	</div>
</div>

</body>
</html>