<%@ page language="java" contentType="text/html" %>
<%@ page import="java.lang.*" %>
<%@ page import="uk.ac.bbk.dcs.l4all.beans.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String username = (String) request.getParameter("username");
	pageContext.setAttribute("username",username);
	String timeline = (String) request.getParameter("timeline");
	pageContext.setAttribute("timeline",timeline) ;
%>

<html>
<head>
<title>L4All Home</title>
<!-- 

	<script language="JavaScript">
	function popUp(URL) {	
	day = new Date();
	id = day.getTime();
	eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,
										resizable=1,width=400,height=300,left = 0,top = 0');");
	}
	</script>
-->
<style type="text/css">
<!--
.style1 {
	color: #000000;
	font-weight: bold;
	font-size: x-small;
}
.style2 {
	color: #003399;
	font-weight: bold;
	font-size: x-small;
}
.style12 {font-size: x-small; font-weight: bold; }
.style13 {font-size: x-small}
-->
</style>
</head>

<body bgcolor="#BABA96">
<table style="font-family: Verdana;" align="center" border="0" cellpadding="5" width="800">
  <tbody>
    <tr> 
      <td width="287">&nbsp;</td>
      <td width="272"><font color="#003366"><strong></strong></font></td>
      
    <td width="179"><font color="#003366"><strong><font color="#FFFFFF" size="1"></font></strong></font></td>
      <td width="12">&nbsp;</td>
    </tr>
  </tbody>
</table>
 
<table align="center" border="0" cellpadding="5" width="800">
  <tbody>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      
      <td> <font color="#3399CC"><br>
        </font> </td>
    </tr>
    <tr> 
      <td> <form action="register" method="post" style="font-family: Verdana; text-align: center;">
          <div style="text-align: center;"> 
            
            <table style="font-family: Verdana;"  border="0" cellpadding="5" width="800">
              <tbody>
                <tr> 
                  <td colspan="4"><font color="#003399" size="4"><strong>Welcome 
                    to Lifelong Learning in London for all (L4<em>All</em>)!</strong></font></td>
                </tr>
                <tr>
                  <td colspan="4">&nbsp;</td>
                </tr>
                <tr> 
                  <td colspan="4"><p><font color="#003399" size="2"><strong>L4<em>All</em> 
                      provides resources to support your educational
                      choices<br> 
                      and career decisions.</strong></font><font color="#003399" size="2"><strong> L4<em>All</em> 
                      will help you to search
                      and browse<br>
                      courses in London. L4<em>All</em> will also allow you 
                      to 
                      create your own<br> timeline and then share it with other lifelong 
                      learners. Enjoy!</strong></font></p></td>
                </tr>
                <tr> 
                  <td width="167">&nbsp;</td>
                  <td width="262">&nbsp;</td>
                  <td colspan="2">&nbsp;</td>
                </tr>
                <tr> 
                  <td><a href="editDetails.jsp?username=${param.username}&newUser=0" class="style1"><font color="#003399">EDIT MY DETAILS </font></a></td>
                  <td><span class="style12"><font color="#FFFFFF">To edit your personal account.</font> </span></td>
                  <td colspan="2"><span class="style13"></span></td>
                </tr>
                <tr> 
                  <td><a href="registerLearningPrefs.jsp?username=${param.username}&newUser=0" class="style12"><font color="#003399">EDIT YOUR PERSONAL PROFILE </font></a></td>
                  <td><span class="style12"><font color="#FFFFFF">Add some extra information regarding your learning prefences here</font> </span></td>
                  <td colspan="2"><span class="style13"></span></td>
                </tr>
                <tr> 
                  <td><a href="timeline.jsp" class="style12"><font color="#003399">CREATE YOUR TIMELINE </font></a></td>
                  <td><span class="style12"><font color="#FFFFFF">To create your Timelines.</font></span></td>
                  <td colspan="2"><span class="style12"><font color="#003399"><font color="#FFFF00">Requires 
                    Macromedia Flash Player:<strong> <a href="http://www.adobe.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"><strong><font color="#FFFFFF">GET 
                    FLASH PLAYER</font></strong></a></strong></font></font></span></td>
                </tr>
                
                <tr> 
                  <td><a href="tutorial.jsp" class="style2">LEARN ABOUT HOW TO CREATE A TIMELINE </a>                  </td>
                  <td><span class="style12"><font color="#FFFFFF">To learn how to create 
                    your Timelines.</font> </span></td>
                </tr>
                <tr> 
                  <td><a href="resources.jsp" class="style12"><font color="#003399">RESOURCES</font></a></td>
                  <td><span class="style13"></span></td>
                  <td width="60"><span class="style13"></span></td>
                  <td width="261" align="right"></td>
                </tr>
				<tr>
					<td><a href="login.jsp" class="style12"><font color="#003399">LOGOUT</font></a></td>
				</tr>
                <!-- Section 2 -->
                <!--
    <tr>
      <td>Do you want to change your job?</td>
      <td>
        <input type="radio" name="job" checked="checked" value="yes" titel="Yes"/>Yes<br>
        <input type="radio" name="job" value="no" title="No"/>No
      </td>
    </tr>
    <tr>
      <td>What do you do now</td>
      <td>
        <select name="current_stae" size="1">
          <option selected="selected" value="tennis">Play Tennis</option>
          <option value="unemployed">Unemployed</option>
          <option value="jailed">Jailed</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>What would you like to do?</td>
      <td>
        <select name="current_stae" size="1">
          <option selected="selected" value="tennis">Play Tennis</option>
          <option value="unemployed">Unemployed</option>
          <option value="jailed">Jailed</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>Voluntary work. (Please specify)</td>
      <td>
        <input type="text" name="voluntary" size="30" maxlength="30"/>
      </td>
    </tr>
    <tr>
      <td>Current Goals?</td>
      <td>
        <select name="current_state" size="1">
          <option selected="selected" value="tennis">Play Tennis</option>
          <option value="unemployed">Unemployed</option>
          <option value="jailed">Jailed</option>
        </select>
      </td>
    </tr> -->
                <tr> 
                  <td colspan="4" align="center" valign="middle"><a href="call_me/LearningForAll.html"><img src="call_me/click2call2navy.GIF" width="105" height="29"></a>
				  <span class="style12"><font color="#FFFFFF">Speak to a LearnDirect Advisor</font></span></td>
                </tr>
                <tr> 
                  <td colspan="4">&nbsp;</td>
                </tr>
              </tbody>
            </table>
          </div>
        </form></td>
    </tr>
  </tbody>
</table>

<!--
<script language="JavaScript" type="text/javascript" src="js/wz_tooltip.js">
</script>
-->
</body></html>