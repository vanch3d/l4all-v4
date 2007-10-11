<html>
<head>

<script language="JavaScript">
function popUp(URL) {
day = new Date();
id = day.getTime();
eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=400,height=300,left = 0,top = 0');")
}
</script>

<title>Create timeline</title>
</head>


<body background="images/REGbKG3dLNG.jpg">
<table style="font-family: Verdana;" align="center" border="0" cellpadding="5" width="875">
  <tbody> 
    <tr> 
      <td colspan="4"><font color="#000033" size="5"><b><font color="#CCFFFF">Create Timeline</font>
    </tr>
  </tbody> 
</table>
 
<table align="center" border="0" cellpadding="5" width="858">
  <tbody> 
    <tr>
      <td> 

	
        <form name="MyTimeline" action="../createTimeline" method="post" style="font-family: Verdana; text-align: center;">
          <div style="text-align: center;"> 
            <table style="font-family: Verdana;"  border="0" cellpadding="5" width="829">
              <tbody>
                <tr> 
                  <td colspan="4">&nbsp; </td>
                </tr>
                <tr> 
                  <td colspan="4"><font color="#003366" size="3"><b><font color="#CCFFFF" size="4">Timeline Details:</font></b></font> </td>
                </tr>
                <tr> 
                  <td width="164"><font color="#FFFFFF" size="2"><strong>Timeline Name:</strong></font></td>
                  <td width="188"> 
                    <input name="name" size="15" maxlength="15" type="text"> 
                  </td>
                </tr>
                <tr> 
                  <td width="164"><strong><font color="#FFFFFF" size="2">Description:</font></strong></td>
                  <td width="188"> 
                    <input name="description" size="15" value="" maxlength="15" type="text"> 
                  </td>
                  <td width="171"></td>
                  <td width="261">&nbsp; </td>
                </tr>
                <tr> 
                  <td width="164"><strong><font color="#FFFFFF" size="2">Confirm 
                    Password*:</font></strong></td>
                  <td width="188"> 
                    <input name="privililrgr" size="15" maxlength="15" type="password"> 
                  </td>
                  <td width="171">&nbsp;</td>
                  <td width="261">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="164"><strong><font color="#FFFFFF" size="2">Full
                  Name*:</font></strong></td>
                  <td width="188"> 
                    <input name="name" size="30" maxlength="50" type="text"> 
                  </td>
                </tr>
                <tr> 
                  <td width="164"><font color="#FFFFFF" size="2"><strong>Age:</strong></font></td>
                  <td width="188"> 
                    <input name="age" size="15" maxlength="15" type="text"> 
                  </td>
                  <td width="171"></td>
                  <td width="261">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="164"><font color="#003366"><strong><font color="#FFFFFF" size="2">e-mail*:</font></strong></font></td>
                  <td width="188"> 
                    <input name="email" size="30" maxlength="30" type="text"> 
                  </td>
                  <td width="171"></td>
                  <td width="261">&nbsp; </td>
                </tr>
                <tr> 
                  <td width="164"><strong><font color="#FFFFFF" size="2">Gender:</font></strong></td>
                  <td width="188"> 
                    <input name="gender" size="15" value="" maxlength="15" type="text"></td>
                  <td width="171">&nbsp;</td>
                  <td width="261">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="164"> 
                    <p><strong><font color="#FFFFFF" size="2">Willing to travel
                    up to:</font></strong></p>
                  </td>
                  <td width="188"> 
                    <input name="travelWill" size="15" maxlength="15" type="text"><strong><font color="#FFFFFF" size="2">Km.</font></strong></td>
                  <td width="171">&nbsp;</td>
                  <td width="261">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="164"><strong><font color="#FFFFFF" size="2">My
                  Location</font></strong><strong><font color="#FFFFFF" size="2">/Post
                  Code :</font></strong></td>
                  <td width="188"> 
                    <input name="my_location" size="15" maxlength="15" type="text"></td>
                  <td width="171">&nbsp;</td>
                  <td width="261">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="164">&nbsp;</td>
                  <td width="188">
                    <input name="newUser" value="0" type="hidden"></td>
                  <td width="171">&nbsp;</td>
                  <td width="261">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="164">
                    <input name="save" value="Create Profile" style="color: black; font-family: 'Impact'; font-size: small; font-style: normal; text-align: centre;" type="submit"></td>
                  <td colspan="3"><font color="#FFFFFF"><strong><font size="1">Create
                  profile and go to the interface or add more details to
                  section 2.</font></strong> </font></td>
                </tr>
                <tr>
                  <td colspan="4">&nbsp;</td>
                </tr>
              
            </tbody></table>
          
        </div></form>

      </td>
    </tr>
  </tbody>
</table>

<hr>

</body>
</html>