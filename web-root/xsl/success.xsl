<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
<html>
<xsl:choose>
	<xsl:when test="main_message/method_name = 'saveUserLearningProfile'">
		<title>L4ALL - User Preferences</title>
	</xsl:when>
	<xsl:otherwise>
		<title>L4ALL - Error</title>
	</xsl:otherwise>
</xsl:choose>
<link rel="stylesheet" type="text/css" href="css/l4all-popup.css"/>
<body>

<xsl:choose>
	<xsl:when test="main_message/message_type = 'SUCCESS'">
		<h2>Success</h2>
		<p>The preferences of 
			<b><xsl:value-of select="main_message/message_content/username"/></b>
			have been modified.
		</p> 
		<p>You can now <a href="javascript:self.close();">close</a> the window.</p>
	</xsl:when>
	<xsl:otherwise>
	
	</xsl:otherwise>
</xsl:choose>
</body>
</html>
</xsl:template>
</xsl:stylesheet>