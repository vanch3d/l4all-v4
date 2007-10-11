<link rel="stylesheet" type="text/css" href="../css/menu.css" />

<script type="text/javascript" src="../js/dropdowntabs.js">
/***********************************************
* Drop Down Tabs Menu- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/
</script>

<div id="l4allmenu" class="l4allmenu">

<ul>
<li><a href="javascript:void(0);" rel="dropmenu_profile">My Profile</a></li>
<li><a href="javascript:void(0);" rel="dropmenu_search">Search</a></li>
<li><a href="javascript:void(0);" onclick="popUp('about.jsp');return false;">Help</a></li>
<li class="right"><A href="#" onclick="document.location.href='login.jsp';return false;">Log Out</A></li>
</ul>
</div>

<!--1st drop down menu -->                                                   

<div id="dropmenu_profile" class="dropmenudiv_e">
<a href="javascript:void(0);" onclick="popUp('editDetails.jsp?newUser=0');return false;">Details</a>
<a href="javascript:void(0);" onclick="popUp('registerLearningPrefs.jsp');return false;">Preferences</A>
</div>

<!--2nd drop down menu -->                                                
<div id="dropmenu_search" class="dropmenudiv_e">
<a href="javascript:void(0);" onclick="popUp('searchTimeline.jsp');return false;">Search Timelines</a>
<a href="javascript:void(0);" onclick="popUp('searchCourse.jsp');return false;">Search Courses</a>
</div>

<script type="text/javascript">
//SYNTAX: tabdropdown.init("menu_id", [integer OR "auto"])
tabdropdown.init("l4allmenu", "auto")
</script>