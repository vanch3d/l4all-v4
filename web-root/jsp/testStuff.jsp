<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/xtags-1.0" prefix="xtags" %>

<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%
	String username = (String) session.getAttribute("username");
	pageContext.setAttribute("username",username);
	String serverURL = "http://" + request.getServerName() + ":" + request.getServerPort() + 
						request.getContextPath() + "/";

	List mylist = null;
	mylist = (List)request.getAttribute("ListSimilar");
	String metricname = (String)request.getAttribute("metric");
	String target = (String)request.getAttribute("data");
	
	pageContext.setAttribute("myTrail",mylist);
	pageContext.setAttribute("metric",metricname);	
	pageContext.setAttribute("target",target);
%>
<!-- This comment keeps IE6/7 in the reliable quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Dropdown Sample</title>
   <link rel="stylesheet" type="text/css" href="../css/l4all-popup.css">

<script type="text/javascript" src="../js/dropdown.js">
</script>

<style type="text/css">



</style>

<style type="text/css">
div.tableContainer {
	width: 65%;		/* table width will be 99% of this*/
	height: 295px; 	/* must be greater than tbody*/
	overflow: auto;
	margin: 0 auto;
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
	}	
	
td	{
	color: #000;
	padding-right: 2px;
	font-size: 12px;
	text-align: right;
	border-bottom: solid 1px #d8d8d8;
	border-left: solid 1px #d8d8d8;
	}

tfoot td	{
	text-align: center;
	font-size: 11px;
	font-weight: bold;
	background-color: papayawhip;
	color: steelblue;
	border-top: solid 2px slategray;
	}

td:last-child {padding-right: 20px;} /*prevent Mozilla scrollbar from hiding cell content*/

</style>



<!-- print style sheet -->
<style type="text/css" media="print">
div.tableContainer {overflow: visible;	}
table>tbody	{overflow: visible; }
td {height: 14pt;} /*adds control for test purposes*/
thead td	{font-size: 11pt;	}
tfoot td	{
	text-align: center;
	font-size: 9pt;
	border-bottom: solid 1px slategray;
	}
	
thead	{display: table-header-group;	}
tfoot	{display: table-footer-group;	}
thead th, thead td	{position: static; } 


</style>

</head>
<body>

<h3>Dropdown Sample</h3>

<!-- Dropdown Menu -->

<div id="menu_parent" class="sample_attach">
Main Menu
</div>
<div id="menu_child" style="position: absolute; visibility: hidden;">
<a class="sample_attach" href="#">Item 1</a>
<a class="sample_attach" href="#">Item 2</a>
<a class="sample_attach" style="border-bottom: 1px solid black;" href="#">Item 3</a>
</div>

<script type="text/javascript">
at_attach("menu_parent", "menu_child", "hover", "y", "pointer");
</script>



<!-- Search Form -->

<div class="sample_attach" id="src_parent">
Site Search
</div>
<form class="sample_attach" id="src_child" action="dropdown.php">
<b>Enter search terms:</b><br />
<input style="margin-bottom: 1px; width: 170px;" type="text" name="terms" />
<center><input type="submit" value="Submit" /></center>
</form>

<script type="text/javascript">
at_attach("src_parent", "src_child", "click", "x", "pointer");
</script>

<div class="tableContainer">
  <table cellspacing="0">
     <thead>
      <tr> 
        <td width="18%">Station</td>
        <td width="38%">Date</td>

        <td width="28%">Status</td>
        <td width="16%">Num.</td>
      </tr>
    </thead>
    <tfoot>
      <tr> 
        <td colspan="5">Table footer repeats on print</td>
      </tr>

    </tfoot>
    <tbody>
      <tr> 
        <td>KABC</td>
        <td>09/12/2002</td>
        <td>Submitted</td>
        <td>0</td>

      </tr>
      <tr> 
        <td>KCBS</td>
        <td>09/11/2002</td>
        <td>Lockdown</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WFLA</td>
        <td>09/11/2002</td>
        <td>Submitted</td>
        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>

        <td>09/15/2002</td>
        <td>In-Progress</td>
        <td>10</td>
      </tr>
      <tr> 
        <td>WROC</td>
        <td>10/11/2002</td>

        <td>Submitted</td>
        <td>12</td>
      </tr>
      <tr> 
        <td>WPPP</td>
        <td>09/16/2002</td>
        <td>In-Progress</td>

        <td>0</td>
      </tr>
      <tr> 
        <td>WRRR</td>
        <td>09/06/2002</td>
        <td>Submitted</td>
        <td>5</td>

      </tr>
      <tr> 
        <td>WTTT</td>
        <td>09/21/2002</td>
        <td>In-Progress</td>
        <td>0</td>
      </tr>

      <tr> 
        <td>W000</td>
        <td>11/11/2002</td>
        <td>Submitted</td>
        <td>7</td>
      </tr>
      <tr> 
        <td>KABC</td>

        <td>10/01/2002</td>
        <td>Submitted</td>
        <td>10</td>
      </tr>
      <tr> 
        <td>KCBS</td>
        <td>10/18/2002</td>

        <td>Lockdown</td>
        <td>2</td>
      </tr>
      <tr> 
        <td>WFLA</td>
        <td>10/18/2002</td>
        <td>Submitted</td>

        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>
        <td>10/19/2002</td>
        <td>In-Progress</td>
        <td>0</td>

      </tr>
      <tr> 
        <td>WROC</td>
        <td>07/18/2002</td>
        <td>Submitted</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WPPP</td>
        <td>10/28/2002</td>
        <td>In-Progress</td>
        <td>10</td>
      </tr>
      <tr> 
        <td>WRRR</td>

        <td>10/28/2002</td>
        <td>Submitted</td>
        <td>5</td>
      </tr>
      <tr> 
        <td>WTTT</td>
        <td>10/08/2002</td>

        <td>In-Progress</td>
        <td>0</td>
      </tr>
      <tr> 
        <td>WIL0</td>
        <td>10/18/2001</td>
        <td>Submitted</td>

        <td>7</td>
      </tr>
      <tr> 
        <td>KABC</td>
        <td>04/18/2002</td>
        <td>Submitted</td>
        <td>0</td>

      </tr>
      <tr> 
        <td>KCBS</td>
        <td>10/05/2001</td>
        <td>Lockdown</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WFLA</td>
        <td>10/18/2002</td>
        <td>Submitted</td>
        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>

        <td>10/19/2002</td>
        <td>In-Progress</td>
        <td>0</td>
      </tr>
      <tr> 
        <td>WROC</td>
        <td>12/18/2002</td>

        <td>Submitted</td>
        <td>2</td>
      </tr>
      <tr> 
        <td>WPPP</td>
        <td>12/28/2002</td>
        <td>In-Progress</td>

        <td>8</td>
      </tr>
      <tr> 
        <td>WRRR</td>
        <td>12/20/2002</td>
        <td>Submitted</td>
        <td>5</td>

      </tr>
      <tr> 
        <td>WTTT</td>
        <td>12/11/2002</td>
        <td>In-Progress</td>
        <td>0</td>
      </tr>

      <tr> 
        <td>W0VB</td>
        <td>01/18/2003</td>
        <td>Submitted</td>
        <td>17</td>
      </tr>
      <tr> 
        <td>KABC</td>

        <td>12/17/2002</td>
        <td>Submitted</td>
        <td>20</td>
      </tr>
      <tr> 
        <td>KCBS</td>
        <td>12/16/2002</td>

        <td>Lockdown</td>
        <td>2</td>
      </tr>
      <tr> 
        <td>WFAA</td>
        <td>12/18/2002</td>
        <td>Submitted</td>

        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>
        <td>12/18/2002</td>
        <td>In-Progress</td>
        <td>0</td>

      </tr>
      <tr> 
        <td>WROC</td>
        <td>12/19/2002</td>
        <td>Submitted</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WPPP</td>
        <td>12/06/2002</td>
        <td>In-Progress</td>
        <td>0</td>
      </tr>
      <tr> 
        <td>WRRR</td>

        <td>12/28/2002</td>
        <td>Submitted</td>
        <td>5</td>
      </tr>
      <tr> 
        <td>WTTT</td>
        <td>12/30/2002</td>

        <td>In-Progress</td>
        <td>0</td>
      </tr>
      <tr> 
        <td>UMBA</td>
        <td>12/26/2002</td>
        <td>Submitted</td>

        <td>7</td>
      </tr>
      <tr> 
        <td>KABC</td>
        <td>12/18/2002</td>
        <td>Submitted</td>
        <td>0</td>

      </tr>
      <tr> 
        <td>KCBS</td>
        <td>12/29/2002</td>
        <td>Lockdown</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WFFF</td>
        <td>12/22/2002</td>
        <td>Submitted</td>
        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>

        <td>12/18/2001</td>
        <td>In-Progress</td>
        <td>9</td>
      </tr>
      <tr> 
        <td>WROC</td>
        <td>11/19/2001</td>

        <td>Submitted</td>
        <td>2</td>
      </tr>
      <tr> 
        <td>WPPP</td>
        <td>11/20/2001</td>
        <td>In-Progress</td>

        <td>0</td>
      </tr>
      <tr> 
        <td>WRRR</td>
        <td>10/19/2001</td>
        <td>Submitted</td>
        <td>5</td>

      </tr>
      <tr> 
        <td>WTTT</td>
        <td>11/29/2001</td>
        <td>In-Progress</td>
        <td>8</td>
      </tr>

      <tr> 
        <td>KPLT</td>
        <td>11/19/2001</td>
        <td>Submitted</td>
        <td>7</td>
      </tr>
      <tr> 
        <td>KABC</td>

        <td>11/19/2001</td>
        <td>Submitted</td>
        <td>13</td>
      </tr>
      <tr> 
        <td>KBRE</td>
        <td>11/19/2001</td>

        <td>Lockdown</td>
        <td>2</td>
      </tr>
      <tr> 
        <td>WFLA</td>
        <td>11/19/2001</td>
        <td>Submitted</td>

        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>
        <td>02/19/2003</td>
        <td>In-Progress</td>
        <td>0</td>

      </tr>
      <tr> 
        <td>WROC</td>
        <td>02/17/2003</td>
        <td>Submitted</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WPPP</td>
        <td>02/16/2003</td>
        <td>In-Progress</td>
        <td>16</td>
      </tr>
      <tr> 
        <td>WRRR</td>

        <td>02/29/2003</td>
        <td>Submitted</td>
        <td>5</td>
      </tr>
      <tr> 
        <td>WTTT</td>
        <td>03/19/2003</td>

        <td>In-Progress</td>
        <td>19</td>
      </tr>
      <tr> 
        <td>KLTR</td>
        <td>02/10/2003</td>
        <td>Submitted</td>

        <td>7</td>
      </tr>
      <tr> 
        <td>KABC</td>
        <td>04/05/2003</td>
        <td>Submitted</td>
        <td>16</td>

      </tr>
      <tr> 
        <td>KCBS</td>
        <td>02/19/2003</td>
        <td>Lockdown</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WFLA</td>
        <td>02/16/2003</td>
        <td>Submitted</td>
        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>

        <td>02/13/2003</td>
        <td>In-Progress</td>
        <td>5</td>
      </tr>
      <tr> 
        <td>WROC</td>
        <td>02/14/2003</td>

        <td>Submitted</td>
        <td>2</td>
      </tr>
      <tr> 
        <td>WPPP</td>
        <td>03/19/2003</td>
        <td>In-Progress</td>

        <td>0</td>
      </tr>
      <tr> 
        <td>WRRR</td>
        <td>02/19/2002</td>
        <td>Submitted</td>
        <td>5</td>

      </tr>
      <tr> 
        <td>WTTT</td>
        <td>02/19/2002</td>
        <td>In-Progress</td>
        <td>0</td>
      </tr>

      <tr> 
        <td>WYYD</td>
        <td>02/11/2002</td>
        <td>Submitted</td>
        <td>7</td>
      </tr>
      <tr> 
        <td>KABC</td>

        <td>02/19/2002</td>
        <td>Submitted</td>
        <td>11</td>
      </tr>
      <tr> 
        <td>KCBS</td>
        <td>02/19/2002</td>

        <td>Lockdown</td>
        <td>12</td>
      </tr>
      <tr> 
        <td>WFLA</td>
        <td>05/19/2002</td>
        <td>Submitted</td>

        <td>1</td>
      </tr>
      <tr> 
        <td>WTSP</td>
        <td>02/20/2002</td>
        <td>In-Progress</td>
        <td>0</td>

      </tr>
      <tr> 
        <td>WROC</td>
        <td>05/20/2002</td>
        <td>Submitted</td>
        <td>2</td>
      </tr>

      <tr> 
        <td>WPPP</td>
        <td>02/19/2003</td>
        <td>In-Progress</td>
        <td>13</td>
      </tr>
      <tr> 
        <td>WRRR</td>

        <td>02/19/2002</td>
        <td>Submitted</td>
        <td>5</td>
      </tr>

    </tbody>
  </table>

</div>

</body>
</html>
