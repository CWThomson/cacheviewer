<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:import href="uk/co/christhomson/sibyl/web/common.xsl"/>
	
	<xsl:param name="cacheName"></xsl:param>
	<xsl:param name="className"></xsl:param>

	<xsl:output method="html"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="Extraction"/>
	</xsl:template>

	<xsl:template match="/Extraction">
		<xsl:call-template name="main">
			<xsl:with-param name="page" select="'Extractor'"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="content">
		<div class="post">
			<xsl:apply-templates select="Errors"/>
			<xsl:apply-templates select="Messages"/>
			
			<form method="get" action="Extractor" name="extractor">
				<xsl:apply-templates select="Cache"/>
				<xsl:apply-templates select="Key"/>
			</form>
			
			<xsl:apply-templates select="Result" />

		</div>
	</xsl:template>
	
	<xsl:template match="Key">
		<h2 class="title">Key Creator</h2>
		<!-- div style="clear: both;">&nbsp;</div-->
		<div class="entry">
			<xsl:element name="input">
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="name">className</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of
					select="$className" /></xsl:attribute>
			</xsl:element>

			<xsl:apply-templates select="Class" mode="key" />
			
			<br/>
			
			<input type="submit" id="extract-submit" name="extract" value="Extract" />
		</div>
		<div style="clear: both;"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></div>
	</xsl:template>

	<xsl:template match="Class" mode="key">
		<xsl:param name="prefix"></xsl:param>

		<table border="0">
			<tr>
				<td width="10px"></td>
				<th>Class Name</th>
				<td colspan="2">
					<xsl:element name="a">
						<xsl:attribute name="title"><xsl:value-of select="@type" /></xsl:attribute>
						<xsl:value-of select="@shorttype"/>
					</xsl:element>
				</td>
			</tr>

			<xsl:apply-templates select="Field" mode="key">
				<xsl:with-param name="prefix">
					<xsl:value-of select="$prefix" />.<xsl:value-of select="@name" />
				</xsl:with-param>
			</xsl:apply-templates>

			<tr>
				<td width="10px"></td>
				<td colspan="5">
					<xsl:apply-templates select="Class" mode="key">
						<xsl:with-param name="prefix">
							<xsl:value-of select="$prefix" />.<xsl:value-of select="@name" />
						</xsl:with-param>
					</xsl:apply-templates>
				</td>
			</tr>
		</table>
	</xsl:template>
	
	<xsl:template match="Class[@type='java.util.Date']" mode="key">
		<xsl:param name="prefix"></xsl:param>
		
		<tr>
			<td></td>
			<th>
				<xsl:value-of select="@name"></xsl:value-of>
			</th>
			<td>
				<xsl:element name="a">
					<xsl:attribute name="title"><xsl:value-of select="@type" /></xsl:attribute>
					<xsl:value-of select="@shorttype"/>
				</xsl:element>
			</td>
			<td>
				<xsl:element name="input">
		    		<xsl:attribute name="type">text</xsl:attribute>
					<xsl:attribute name="name">input.<xsl:value-of
						select="$prefix" />.<xsl:value-of select="@name"/></xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="@value"/></xsl:attribute>
				</xsl:element>
				<script language="JavaScript">
					new tcal ({
						// form name
						'formname': 'extractor',
						// input name
						'controlname': 'input.<xsl:value-of
						select="$prefix" />.<xsl:value-of select="@name"/>'
					});
				</script>
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="Class[@isenum='true']" mode="key">
		<xsl:param name="prefix"></xsl:param>
		
		<tr>
			<td></td>
			<th>
				<xsl:value-of select="@name"></xsl:value-of>
			</th>
			<td>
				<xsl:element name="a">
					<xsl:attribute name="title"><xsl:value-of select="@type" /></xsl:attribute>
					<xsl:value-of select="@shorttype"/>
				</xsl:element>
			</td>
			<td>
				<xsl:element name="select">
					<xsl:attribute name="name">input.<xsl:value-of
						select="$prefix" />.<xsl:value-of select="@name"/></xsl:attribute>
					<xsl:for-each select="Option">
						<xsl:element name="option">
							<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
							<xsl:if test="../@value = .">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="."/>
						</xsl:element>									
					</xsl:for-each>
				</xsl:element>
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="Field" mode="key">
		<xsl:param name="prefix"></xsl:param>

		<tr>
			<td></td>
			<th>
				<xsl:value-of select="@name"></xsl:value-of>
			</th>
			<td>
				<xsl:element name="a">
					<xsl:attribute name="title"><xsl:value-of select="@type" /></xsl:attribute>
					<xsl:value-of select="@shorttype"/>
				</xsl:element>
			</td>
			<td>
				<xsl:choose>
        			<xsl:when test="@type = 'java.lang.Boolean'">
        				<xsl:element name="select">
							<xsl:attribute name="name">input.<xsl:value-of
								select="$prefix" />.<xsl:value-of select="@name"></xsl:value-of></xsl:attribute>
							<option value=""></option>
							<option value="true">true</option>
							<option value="false">false</option>
						</xsl:element>
					</xsl:when>
					<xsl:when test="@type = 'boolean'">
        				<xsl:element name="select">
							<xsl:attribute name="name">input.<xsl:value-of
								select="$prefix" />.<xsl:value-of select="@name"></xsl:value-of></xsl:attribute>
							<option value="true">true</option>
							<option value="false">false</option>
						</xsl:element>
					</xsl:when>					
					<xsl:otherwise>
						<xsl:element name="input">
							<xsl:attribute name="type">text</xsl:attribute>
							<xsl:attribute name="name">input.<xsl:value-of
								select="$prefix" />.<xsl:value-of select="@name"></xsl:value-of></xsl:attribute>
							<xsl:attribute name="size">50</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="@value"/></xsl:attribute>
						</xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</td>
		</tr>
	</xsl:template>
	
	<xsl:template match="Result">
		<h2 class="title">Results</h2>
		<div class="entry">
			<ul class="mktree" id="tree1">
				<xsl:apply-templates select="Class" mode="result"/>
				<xsl:apply-templates select="Field" mode="result"/>
			</ul>
		</div>
		<div style="clear: both;"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></div>
	</xsl:template>
	
	<xsl:template match="Class" mode="classHistory">
		<li>
			<xsl:element name="a">
				<xsl:attribute name="href">Extractor?className=<xsl:value-of
					select="@name" /></xsl:attribute>
				<xsl:attribute name="title"><xsl:value-of select="@name"/></xsl:attribute>
				<xsl:value-of select="@shortname" />
			</xsl:element>
		</li>
	</xsl:template>
	
</xsl:stylesheet>