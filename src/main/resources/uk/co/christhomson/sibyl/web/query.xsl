<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:import href="uk/co/christhomson/sibyl/web/common.xsl"/>
	
	<xsl:param name="cacheName"></xsl:param>

	<xsl:output method="html"/>
	
	<xsl:template match="/QueryRequest">
		<xsl:call-template name="main">
			<xsl:with-param name="page" select="'Query'"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="content">
		<div class="post">
			<xsl:apply-templates select="Errors"/>
			<xsl:apply-templates select="Messages"/>
			
			<form method="get" action="Query" name="query">
				<xsl:apply-templates select="Cache"/>
				<xsl:apply-templates select="Query"/>
			</form>
			
			<xsl:apply-templates select="Results" />

		</div>
	</xsl:template>
	
	<xsl:template match="Query"> 
	
		<h2 class="title">Query</h2>
		<!-- div style="clear: both;">&nbsp;</div-->
		<div class="entry">
			<xsl:element name="textarea">
				<xsl:attribute name="name">query</xsl:attribute>
				<xsl:attribute name="cols">70</xsl:attribute>
				<xsl:attribute name="rows">4</xsl:attribute>
				<xsl:value-of select="@query" />
			</xsl:element>
			
			<br/>
			
			<input type="submit" name="submit" value="Query"/>
		</div>
		<div style="clear: both;"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></div>
		
	</xsl:template>
	
	<xsl:template match="Results">
		<h2 class="title">Results</h2>
		<div class="entry">
			<table>
				<tr>
					<th>Key</th>
					<th>Value</th>
				</tr>
				<xsl:apply-templates select="Result"/>
			</table>
		</div>
		<div style="clear: both;"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></div>
	</xsl:template>
	
	<xsl:template match="Result">
		<tr>
			<td>
				<ul class="mktree" id="tree1">
					<xsl:apply-templates select="Key/Class" mode="result"/>
					<xsl:apply-templates select="Key/Field" mode="result"/>
				</ul>
			</td>
			<td>
				<ul class="mktree" id="tree1">
					<xsl:apply-templates select="Value/Class" mode="result"/>
					<xsl:apply-templates select="Value/Field" mode="result"/>
				</ul>
			</td>
			
		</tr>
	</xsl:template>
	
</xsl:stylesheet>