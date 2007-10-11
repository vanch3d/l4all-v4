<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <head>
  	<style type="text/css">
		body{
			margin:0;padding:0;
			font-family: Arial, Helvetica, sans-serif;
			font-size : 9pt;
		}
		html, body{height:100%}
		tr.hidden{display:none}
		tr.show{display:table-row;}
		td.show{background: #ffffff;border-style: solid; border-width: 0px 0px 1px 1px;}
		td.frame{border-style: solid; border-width: 0px 0px 1px 1px;}
		th.frame{border-style: solid; border-width: 1px 0px 1px 1px;background-color:#9acd32;}
	</style>

	<script language="JavaScript" type="text/javascript">
	function doIt(n)
	{
		var y = document.getElementById("desc"+n).className;
		if (y=="show")
		{
			document.getElementById("desc"+n).className = "hidden";	
			document.getElementById("btn"+n).src = "images/avshow.gif";	
		}
		else
		{
			for (var i = 0; i &lt; <xsl:value-of select="main_message/message_content/courses/@cardinality"/>; i++) 
			{
				var name = "desc"+i;
				document.getElementById(name).className= "hidden";
				document.getElementById("btn"+i).src = "images/avshow.gif";	
			}

			document.getElementById("desc"+n).className = "show";
			document.getElementById("btn"+n).src = "images/avhide.gif";	
		}
	}
	
	</script>
  </head>
  <body>
    Search result: <b><xsl:value-of select="main_message/message_content/courses/@cardinality"/></b>
    <table border="0" width="100%" cellspacing="0" cellpadding="1">
    <tr>
      <th></th>
      <th class="frame" align="left">ID</th>
      <th class="frame" align="left">LD</th>
      <th class="frame" width="100%" align="left">Relevance</th>
    </tr>
    <xsl:for-each select="main_message/message_content/courses/course">
    <tr>
	  <td><img id="btn{position() - 1}" src="images/avshow.gif" onclick="doIt({position() - 1})"/>
	  </td>
      <td class="frame"><xsl:value-of select="course_Id"/></td>
      <td class="frame"><xsl:value-of select="@learn_direct"/></td>
      <td class="frame"><xsl:value-of select="course_title"/></td>
    </tr>
    <tr id="desc{position() - 1}" class="hidden">
	  <td></td>
      <td colspan="3" class="show"><xsl:value-of select="course_description"/></td>
    </tr>
    </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>