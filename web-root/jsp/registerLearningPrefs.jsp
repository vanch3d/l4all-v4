<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="uk.ac.bbk.dcs.l4all.servlets.ServletUtilities"%>

<jsp:useBean id="userProfile" class="uk.ac.bbk.dcs.l4all.beans.L4AllUserManager"/>
<%
	String username = (String) session.getAttribute(ServletUtilities.SESSION_USERNAME);
	String newUser = (String) request.getParameter("newUser") ;
	pageContext.setAttribute("newUser",newUser);
	pageContext.setAttribute("username",username);
	
	uk.ac.bbk.dcs.l4all.beans.Message msg = userProfile.getUserLearningPrefDetails(username);
	uk.ac.bbk.dcs.l4all.beans.L4AllUser user = (uk.ac.bbk.dcs.l4all.beans.L4AllUser)msg.getResultObject();		
	
	String [] goals = user.getGoals();
	String [] interests = user.getInterests();
	String [] skills = user.getSkills() ;
	String [] quals = user.getQualifications();
	
	StringBuffer strBuf = new StringBuffer();
	for (int i=0; i < skills.length; i++) {
		strBuf.append(skills[i]);
	}
	String totalSkills = strBuf.toString();
	
	StringBuffer strBuf2 = new StringBuffer();
	for (int i=0; i<goals.length;i++)
		strBuf2.append(goals[i]);
	
	String totalGoals = strBuf2.toString();
	
	if (goals != null && goals.length !=0)
		pageContext.setAttribute("goal",totalGoals);
	else
		pageContext.setAttribute("goal","unspecified");
		
	if (quals != null && quals.length !=0)
		pageContext.setAttribute("highest_qual",quals[0]);
	else
		pageContext.setAttribute("highest_qual","unspecified");
	
	pageContext.setAttribute("skills",totalSkills);
	pageContext.setAttribute("interest",interests[0]);
	pageContext.setAttribute("user",user);
%>

<html>
<head>
	<title>L4ALL - User Preferences</title>
	<LINK REL=StyleSheet HREF="../css/tabber.css" TYPE="text/css" MEDIA=screen>
    <script type="text/javascript" src="../js/tabber-minimized.js">
	</script>
	<style type="text/css">
		<!-- 
			@import "../css/skidoo_redux.css"; 
			@import "../css/skidoo_redux_theme.css";
			@import "../css/skidoo_l4all.css";
			@import "../css/skidoo_form.css";
			@import "../css/skidoo_popup.css";
		-->
	</style>
	
<script>
function closeMe()
{
	if (opener) self.close();
	else parent.ajaxwin.hide();
}
</script>	
</head>


<body>

<div id="page-container">

<div id="masthead">
	<div class="inside">
	<h1>My Profile</h1>
	</div>
</div>

<div id="outer-column-container">
	<div id="inner-column-container">
	<div id="source-order-container">
		<div id="middle-column">
			<div class="inside">

<form class="tableless tabber" name="My_LearningProfile" action="../saveUserLearningProfile" method="post">

<fieldset class="tabbertab" title="Work" >
	<div class="notes">
		<h4>Work Details</h4>
		<p class="last">Gives details about your previous and current <b>occupations</b></p>
	</div>

	<div class="required">
		<input name="username" value="<c:out value='${username}'/>" type="hidden">
		<input name="newUser" value="0" type="hidden">
	</div>
	<div class="optional">
		<label for="pastOccupation">Past Occupation:</label>
		<SELECT NAME="pastOccupation" id="pastOccupation">
		  <OPTION SELECTED>Choose one... 
		  <OPTION>Medicine and Nursing 
		  <OPTION>Healthcare Practitioners and Technical services 
		  <OPTION>Healthcare Support Occupations 
		  <OPTION>Protective Service Occupations 
		  <OPTION>Food Preparation and Serving Related 
		  <OPTION>Education, Training & Library 
		  <OPTION>Office, Administrative and Clerical 
		  <OPTION>Construction 
		  <OPTION>Social Services 
		  <OPTION>Arts, Crafts and Design 
		  <OPTION>Alternative Therapies 
		  <OPTION>Sport, Leisure and Tourism 
		  <OPTION>Animals, Plants and Land 
		  <OPTION>Catering Services 
		  <OPTION>Management and Planning 
		  <OPTION>Performing Arts, Broadcast, Entertainment and Media 
		  <OPTION>Transport 
		  <OPTION>Security and Uniformed Services 
		  <OPTION>Publishing and Journalism 
		  <OPTION>Environmental Sciences 
		  <OPTION>Financial Services 
		  <OPTION>General and Personal Services 
		  <OPTION>Info Technology & Info Management 
		  <OPTION>Legal Services 
		  <OPTION>Maintenance, Service and Repair 
		  <OPTION>Manufacturing and Engineering 
		  <OPTION>Medical Technology 
		  <OPTION>Retail Sales and Customer Service 
		  <OPTION>Science and Research 
		  <OPTION>Storage, Dispatching and Delivery 
		  <OPTION>Marketing, Selling and Advertising 
		  <OPTION>Building and Grounds Cleaning and Maintenance 
		  <OPTION>Personal Care and Service 
		  <OPTION>Farming, Fishing, and Forestry 
		  <OPTION>Construction and Extraction 
		  <OPTION>Installation, Maintenance, and Repair 
		  <OPTION>Production Occupations 
		  <OPTION>Transportation and Material Moving Occupations 
		  <OPTION>Military Specific Occupations 
		</SELECT>
	</div>
	<div class="optional">
		<label for="presentOccupation">Present Occupation :</label>
		<SELECT NAME="presentOccupation" id="presentOccupation">
		  <OPTION SELECTED>Choose one... 
		  <OPTION>Medicine and Nursing 
		  <OPTION>Healthcare Practitioners and Technical services 
		  <OPTION>Healthcare Support Occupations 
		  <OPTION>Protective Service Occupations 
		  <OPTION>Food Preparation and Serving Related 
		  <OPTION>Education, Training & Library 
		  <OPTION>Office, Administrative and Clerical 
		  <OPTION>Construction 
		  <OPTION>Social Services 
		  <OPTION>Arts, Crafts and Design 
		  <OPTION>Alternative Therapies 
		  <OPTION>Sport, Leisure and Tourism 
		  <OPTION>Animals, Plants and Land 
		  <OPTION>Catering Services 
		  <OPTION>Management and Planning 
		  <OPTION>Performing Arts, Broadcast, Entertainment and Media 
		  <OPTION>Transport 
		  <OPTION>Security and Uniformed Services 
		  <OPTION>Publishing and Journalism 
		  <OPTION>Environmental Sciences 
		  <OPTION>Financial Services 
		  <OPTION>General and Personal Services 
		  <OPTION>Info Technology & Info Management 
		  <OPTION>Legal Services 
		  <OPTION>Maintenance, Service and Repair 
		  <OPTION>Manufacturing and Engineering 
		  <OPTION>Medical Technology 
		  <OPTION>Retail Sales and Customer Service 
		  <OPTION>Science and Research 
		  <OPTION>Storage, Dispatching and Delivery 
		  <OPTION>Marketing, Selling and Advertising 
		  <OPTION>Building and Grounds Cleaning and Maintenance 
		  <OPTION>Personal Care and Service 
		  <OPTION>Farming, Fishing, and Forestry 
		  <OPTION>Construction and Extraction 
		  <OPTION>Installation, Maintenance, and Repair 
		  <OPTION>Production Occupations 
		  <OPTION>Transportation and Material Moving Occupations 
		  <OPTION>Military Specific Occupations 
		</SELECT>
	</div>	
	<div class="optional">
		<label for="skills">Work Skills :</label>
		<textarea name="skills" id="skills" rows="4"><c:out value="${skills}"/></textarea>
	</div>
</fieldset>

<fieldset class="tabbertab" title="Learning" >
	<div class="notes">
		<h4>Past Learning Experience</h4>
		<p class="last">Gives details about your previous and current <b>occupations</b></p>
	</div>
	
	<div class="optional">
		<label for="highqual">Highest Qualification:</label>
		<input name="highqual" id="highqual" size="30" value="<c:out value='${highest_qual}'/>" maxlength="100" type="text"> 
	</div>
	<div class="optional">
		<label for="quals">Other Qualification:</label>
		<select name="quals"  id="quals" size="1">
			<option selected value="PhD">PhD</option>
			<option value="MSc">MSc</option>
			<option value="BA">BA</option>
			<option value="BSc">BSc</option>
			<OPTION value="Course Certificate">Course Certificate</option>
			<OPTION value="Entry-level Qualifications">Entry-level Qualifications</option>
			<OPTION value="GCSE or equivalent">GCSE or equivalent</option>
			<OPTION value="NVQ 1/2/3">NVQ 1/2/3</option>
			<OPTION value="National/1st Cert/Dip./AVCE/GNVQ">National/1st Cert/Dip./AVCE/GNVQ</option>
			<OPTION value="Access/Foundation courses">Access/Foundation courses</option>
			<OPTION value="A/AS levels">A/AS levels</option>
			<OPTION value="AEA (Advanced Extension Awards)">AEA (Advanced Extension Awards)</option>
			<OPTION value="Professional Qualifications">Professional Qualifications</option>
			<OPTION value="NVQ 4/5">NVQ 4/5</option>
			<OPTION value="HNC/HND/Higher Education Awards">HNC/HND/Higher Education Awards</option>
			<OPTION value="Return to Learning courses">Return to Learning courses</option>
			<OPTION value="Foundation Degrees">Foundation Degrees</option>
			<OPTION value="First Degrees">First Degrees</option>
			<OPTION value="I. B.">I. B.</option>
			<OPTION value="Taught/Research Post-grad. Awards">Taught/Research Post-grad. Awards</option>
			<OPTION value="CATS">CATS</option>
			<OPTION value="Accreditation of Prior Learning">Accreditation of Prior Learning</option>
			<OPTION value="Overseas Qualifications">Overseas Qualifications</option> 
		</select> 	
	</div>
</fieldset>

<fieldset class="tabbertab" title="Needs">

	<div class="notes">
		<h4>Future Learning Needs</h4>
		<p class="last">Gives details about your previous and current <b>occupations</b></p>
	</div>
	<div class="optional">
		<label for="goals">My learning goal:</label>
		<input name="goals" id="goals" size="30" value="<c:out value='${goal}'/>" maxlength="100" type="text">
	</div>
	<div class="optional">
		<label for="studyType">Study Type:</label>
		<select name="studyType" id="studyType" class="selectOne">
			<OPTION value="Full-time" selected>Full-time</option>
			<OPTION value="Part-time Day">Part-time Day</option>
			<OPTION value="Part-time Evening">Part-time Evening</option>
			<OPTION value="Short">Short</option>
			<OPTION value="Weekend">Weekend</option>
			<OPTION value="Self-study">Self-study</option>
			<OPTION value="Customised">Customised</option>
			<OPTION value="Flexible">Flexible</option>
			<OPTION value="Day/Block Release">Day/Block Release</option>
		</select>
	</div>
	<div class="optional">
		<label for="hos">Hours of Study per Week:</label>
  		<input type="text" name="hos"  id="hos" size="20" value="<c:out value='${user.hoursOfStudyPW}'/>"/>
	</div>
	<div class="optional">
		<label for="budget">Budget:</label>
 		<input name="budget" class="inputText" id="budget" size="30" value="" maxlength="100" type="text" value="<c:out value='${user.budget}'/>"/> 
 		<small>Budget in GBP</small>
	</div>
	<div class="optional">
		<label for="learningMethod">Learning Method</label>
		<select name="learningMethod" id="learningMethod" class="selectOne">
			<OPTION value="Learning in Groups" selected>Learning in Groups</option>
			<OPTION value="Learning Alone">Learning Alone</option>
			<OPTION value="Learning Online">Learning Online</option>
			<OPTION value="Distance Education">Distance Education</option>
       </select>
	</div>
	<div class="optional">
		<fieldset>
			<legend>Disability:</legend>
			<label for="disability_0" class="labelRadio">
				<input class="inputRadio" type="radio" name="disability" id="disability_0" value="0" checked="checked"> No
			</label>
			<label for="disability_1" class="labelRadio">
				<input class="inputRadio" type="radio" name="disability" id="disability_1" value="1" > Yes
			</label>
		</fieldset>
	</div>
</fieldset>
 
<fieldset class="tabbertab" title="About me">
	<div class="notes">
		<h4>About Me</h4>
		<p class="last"></p>
	</div>
	<div class="optional">
		<label for="interests">My Interests:</label>
		<select name="interests" id="interests">
			<OPTION value="Activities" selected>Activities</option>
			<OPTION value="Animals & Pets">Animals & Pets</option>
			<OPTION value="Arts & Crafts">Arts & Crafts</option>
			<OPTION value="Cookery">Cookery</option>
			<OPTION value="Dance">Dance</option>
			<OPTION value="Dinosaurs">Dinosaurs</option>
			<OPTION value="Dolls">Dolls</option>
			<OPTION value="Gardening">Gardening</option> 
			<OPTION value="Geography & the Environment">Geography & the Environment</option>
			<OPTION value="Magic & Other Tricks">Magic & Other Tricks</option>
			<OPTION value="Music">Music</option>
			<OPTION value="Parties & Fancy Dress">Parties & Fancy Dress</option>
			<OPTION value="Science">Science</option>
			<OPTION value="Space Exploration & Travel">Space Exploration & Travel</option>
			<OPTION value="Sport">Sport</option>
			<OPTION value="Transport">Transport</option>
			<OPTION value="Travel & holiday">Travel & holiday</option>
		</select>	
	</div>
</fieldset>


<fieldset  class="submit">
	<div class="submit"><div>
		<input name="save" value="Save Profile" class="inputSubmit" type="submit"> 
	</div></div>
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


<div id="footer">
	<div class="inside">
			<input name="cancel" value="Close"  class="btn" type="button" onclick="closeMe()">
	</div>
	</div>
</div>


        
 </body>
 
 </html>