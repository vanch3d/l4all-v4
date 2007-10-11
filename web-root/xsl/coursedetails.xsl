<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="ldCourse">
    <xsl:param name="course"/> 
		aaa
	</xsl:template>

	<!-- Content:template -->
	<xsl:template match="course_id">
 	<tr>
		<th>Course ID</th>
		<td><xsl:value-of select="text()"/></td>
	</tr>
	</xsl:template>

	<!-- Content:template -->
	<xsl:template match="course_title">
 	<tr>
		<th>Title</th>
		<td><xsl:value-of select="text()"/></td>
	</tr>
	</xsl:template>
	
	<!-- Content:template -->
	<xsl:template match="course_institution">
 	<tr>
		<th>Institution</th>
		<td><xsl:value-of select="text()"/></td>
	</tr>
	</xsl:template>

 <xsl:template name="l4allCourse">
	<xsl:param name="course"/>
	<xsl:if test="$course">
		<xsl:apply-templates select="$course/course_id"/>
		<xsl:apply-templates select="$course/course_title"/>
		<xsl:apply-templates select="$course/course_institution"/>
	<tr>
		<th>Level</th>
		<td><xsl:value-of select="$course/course_level"/></td>
	</tr>
	<tr><th>Scope</th><td><xsl:value-of select="$course/course_scope"/></td></tr>
	<tr><th>Subject</th><td><xsl:value-of select="$course/course_subject"/></td></tr>	
	<tr>
		<th>Description</th>
		<td><xsl:value-of select="$course/course_description"/></td>
	</tr>
	<tr><th>Entry requirements</th><td><xsl:value-of select="$course/course_entryRequirements"/></td></tr>
	<tr>
		<th>Creator</th>
		<td><xsl:value-of select="$course/course_creator"/></td>
	</tr>
	<tr>
		<th>Subject Leader</th>
		<td><xsl:value-of select="$course/course_memberOfStaff"/></td>
	</tr>
	<tr>
		<th>Start</th>
		<td><xsl:value-of select="$course/course_startDate"/></td>
	</tr>
	<tr><th>Type</th><td><xsl:value-of select="$course/course_courseType"/></td></tr>
	<tr><th>Mode of study</th><td><xsl:value-of select="$course/course_modeOfStudy"/></td></tr>
	<tr><th>Language</th><td><xsl:value-of select="$course/course_language"/></td></tr>
	<tr><th>Skills</th><td><xsl:value-of select="$course/course_skills"/></td></tr>
	<tr>
		<th>Cost</th>
		<td><xsl:value-of select="$course/course_cost"/></td>
	</tr>
	<xsl:variable name="bodyTextSize"><xsl:value-of select="$course/course_URL"/></xsl:variable>
	
	<tr>
		<th>More information: </th>
		<td><a href="{$bodyTextSize}">go to the institution website</a></td>
	</tr>
	</xsl:if>
  </xsl:template>


<xsl:template match="/">
	<table>
	<tbody>
	<xsl:if test="main_message/message_content/course/@learn_direct=0">
		<xsl:call-template name="l4allCourse">
			<xsl:with-param name="course" select="main_message/message_content/course/."/>
		</xsl:call-template> 
			
	</xsl:if>
	<xsl:if test="main_message/message_content/course/@learn_direct=1">
		<xsl:call-template name="l4allCourse">
			<xsl:with-param name="course" select="main_message/message_content/course/."/>
		</xsl:call-template> 
	</xsl:if>
	</tbody>
	</table>
  </xsl:template> 
 
</xsl:stylesheet>