<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>L4ALL Timelines Search</title>
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
		form.tableless div label.labelOption
		{
			text-align: left;
			float:none;
			display: inline;	
		}
	</style>
	
	
</head>

<script type="text/javascript">
var oldloc="";
var oldage="";

function closeMe()
{
	self.close();
}

function updateAge()
{
	var dis = document.searchform.same_age.checked;
	alert(dis);
	document.searchform.age.disabled = !(dis);
	if (dis)
	{
		document.searchform.age.value = oldage;
	}
	else
	{
		oldage = document.searchform.age.value;
		document.searchform.age.value = "";
	}
	return true;
}
function updateLocation()
{
	var dis = document.searchform.same_loc.checked;
	document.searchform.location.disabled = !(dis);
	if (dis)
	{
		document.searchform.location.value = oldloc;
	}
	else
	{
		oldloc = document.searchform.location.value;
		document.searchform.location.value = "";
	}
}

function updatePattern(id)
{
	var bPat1 = (id != "pattern1");
	var bPat2 = (id != "pattern2");
	{
		document.searchform.pattern1_sel.disabled = bPat1;
		document.searchform.pattern1_num.disabled = bPat1;
		document.searchform.pattern1_type.disabled = bPat1;
		updateEpisode(id);
	}
	{
		document.searchform.pattern2_sel.disabled = bPat2;
		document.searchform.pattern2_num.disabled = bPat2;
		document.searchform.pattern2_type.disabled = bPat2;
		updateEpisode(id);
	}
}

function updateEpisode(id)
{
	obj = eval("document.searchform." + id + "_sel");
	if (!obj) return true;
	var dis = (obj.selectedIndex==0);
	obj = eval("document.searchform." + id + "_num");
	if (!obj) return true;
	obj.disabled = dis;
	obj.visible = dis;
}

</script>

<body onload="updatePattern('pattern0')">

<div id="page-container">

	<div id="masthead"><div class="inside">
		<h1>Search for people like me ...</h1>
	</div></div>
	
	<div id="outer-column-container">
		<div id="inner-column-container">
			<div id="source-order-container">
				<div id="middle-column"><div class="inside">
				
				<form name="searchform" class="tableless" action="doSearchSimilar" method="get">
				
				<fieldset>
					<legend>... who share</legend>
					<div class="notes">
						<h4>User Profile</h4>
						<p>Define the matching conditions on the users' profile.</p>
						<p class="last">Your <b>email</b> will remain private, unless you decide otherwise.</p>
					</div>
					
					<div class="optional"><label for="same_age" class="labelOption">
						<input name="same_age" id="same_age" class="inputCheckbox" type="checkbox" onchange="updateAge();">
						my age </label> 
						(within <input id="age" style="width:50px;" name="age" size="3" maxlength="15" type="text" value="${username}"> years)
					</div>
					<div class="optional"><label for="same_gender"  class="labelOption">
						<input name="same_gender" id="same_gender" class="inputCheckbox" type="checkbox">
						my gender
					</label></div>
					<div class="optional"><label for="same_qualif" class="labelOption">
						<input name="same_qualif" id="same_qualif" class="inputCheckbox" type="checkbox">
						my qualification 
					</label> </div>
					<div class="optional"><label for="same_occup" class="labelOption">
						<input name="same_occup" id="same_occup" class="inputCheckbox" type="checkbox">
						my occupation 
					</label> </div>
					<div class="optional"><label for="same_loc" class="labelOption">
						<input name="same_loc" id="same_loc" class="inputCheckbox" type="checkbox" onchange="updateLocation();">
						my location</label>  (within <input id="location" style="width:50px;" name="location" size="3" maxlength="15" type="text" value="${username}"> miles)
					</div>
				</fieldset>
			
				<fieldset>
					<legend>... and who have a similar pattern with</legend>
					<div class="notes">
						<h4>Timeline</h4>
						<p class="last">Define a matching pattern based on the users' timeline</p>
					</div>
					
					<div class="optional"><label for="pattern0" class="labelOption">
						<input name="pattern" id="pattern0" class="inputRadio" type="radio" checked="checked" onchange="updatePattern(this.id);">
						all my episodes</label> 
					</div>
					<div class="optional"><label for="pattern1" class="labelOption">
						<input name="pattern" id="pattern1" class="inputRadio" type="radio" onchange="updatePattern(this.id);">
						</label> 
						<select style="width:70px;" id="pattern1_sel" onchange="updateEpisode('pattern1');">
					  		<OPTION SELECTED value="all">all my
					  		<OPTION value="last">my last
					  		<OPTION value="first">my first
					  	</select>
						<input id="pattern1_num" value="5" type="text" style="width:15px;" maxlength="2" size="1"/>
					  	<select style="width:90px;" id="pattern1_type"> 
					  		<OPTION SELECTED>Learning
					  		<OPTION>Occupational
					  		<OPTION>Personal
					  	</select> episode(s)
					</div>
					<div class="optional"><label for="pattern2" class="labelOption">
						<input name="pattern" id="pattern2" class="inputRadio" type="radio" onchange="updatePattern(this.id);">
						</label> 
						<select style="width:70px;" id="pattern2_sel" onchange="updateEpisode('pattern2');">
					  		<OPTION SELECTED value="all">all my
					  		<OPTION value="last">my last
					  		<OPTION value="first">my first
					  	</select>
						<input id="pattern2_num" value="5" type="text" style="width:15px;" maxlength="2" size="1"/>
					  	<select style="width:90px;" id="pattern2_type"> 
					  		<OPTION value="school" SELECTED>School
					  		<OPTION value="college">College
					  		<OPTION value="university">University
					  		<OPTION value="course">Course
					  	</select> episode(s)
					  	<button style="width: 15px;">+</button>
					</div>
				</fieldset>
			
				<fieldset>
					<div class="submit">
						<input class="inputSubmit" name="save" value="Search" type="submit">
						with 
						<select name="metric" title="Select the metric you want to use"> 
					  		<option value="Levenshtein" selected="selected">Levenshtein</option>
					  		<option value="CosineSimilarity">Cosine Similarity</option>
					  		<option value="DiceSimilarity">Dice Similarity</option>
					  		<option value="JaccardSimilarity">Jaccard Similarity</option>
					  		<option value="EuclideanDistance">Euclidean Distance</option>
					  		<option value="JaroToken">Jaro Distance</option>
					  		<option value="EuclideanDistance">Euclidean Distance</option>
					  		<option value="NeedlemanWunchToken">Needleman Wunch</option>
					  		<option value="NeedlemanWunchTimeline">Needleman Timeline</option>
					  	</select>
					</div>
				</fieldset>
				
				</form>
				
				</div></div>
		
			<div id="left-column"><div class="inside">
`			</div></div>		
			<div class="clear-columns"><!-- do not delete --></div>
		</div>
				
		<div id="right-column"><div class="inside">
		</div></div>
	
		<div class="clear-columns"><!-- do not delete --></div>
	</div>
	
	<div id="footer"><div class="inside">
		<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
	</div></div>

</div>
			
</body>
</html>