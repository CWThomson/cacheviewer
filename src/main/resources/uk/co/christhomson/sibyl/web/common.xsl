<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template name="main">
		<xsl:param name="page"/>
	
		<html>
			<head>
				<title>Sibyl Cache Viewer : <xsl:value-of select="$page"/></title>
				<link rel="stylesheet" type="text/css" href="css/boosting/style.css" />
				<SCRIPT LANGUAGE="JavaScript" SRC="tree/mktree.js"></SCRIPT>
				<script language="JavaScript" src="cal/calendar_db.js"></script>
				<link rel="stylesheet" href="cal/calendar.css"/>
			</head>
			<body>
				<div id="wrapper">
					<div id="header">
						<div id="logo">

							<h1>
								<a href="#">Sibyl Cache Viewer : <xsl:value-of select="$page"/></a>
							</h1>
							<p>
								developed by
								<a href="http://www.chris-thomson.co.uk/">Chris Thomson</a>
							</p>
						</div>
					</div>
					<!-- end #header -->
					<div id="page">
						<div id="page-bgtop">

							<div id="page-bgbtm">
								<div id="content">
									<xsl:call-template name="content"></xsl:call-template>
								</div>

								<xsl:call-template name="sidebar">
									<xsl:with-param name="page" select="$page"/>
								</xsl:call-template>
								
								<div style="clear: both;"></div>
							</div>
						</div>
					</div>
					
				</div>
				<div id="footer">
					<p>Copyright (c) 2011 www.chris-thomson.co.uk. All rights reserved. Design by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
					<p><a href="http://sourceforge.net/donate/index.php?group_id=548677"><img src="http://images.sourceforge.net/images/project-support.jpg" width="88" height="32" border="0" alt="Support This Project" /> </a></p>
				</div>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="Cache">
		<h2 class="title">Cache Selector</h2>
		<div class="entry">
			Cache Name
			<xsl:element name="input">
				<xsl:attribute name="type">text</xsl:attribute>
				<xsl:attribute name="name">cacheName</xsl:attribute>
				<xsl:attribute name="size">50</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of
					select="@name" /></xsl:attribute>
			</xsl:element>
		</div>
		<div style="clear: both;"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></div>
	</xsl:template>
	
	<xsl:template name="sidebar">
		<xsl:param name="page"/>
		
		<div id="sidebar">
			<h2><a href="index.html">Home</a></h2>
			<h2><a href="Extractor">Extractor</a></h2>
	
			<xsl:if test="$page='Extractor'">
				<ul>
					<li>
						<form method="get">
							<ul>
								<li>
									<h3>Class Search</h3>
									<div>
										<input type="text" name="className" id="search-text"
											value="" />
									</div>
									<div>
										<input type="submit" id="search-submit" value="GO" />
										<a href="#">Advanced Search</a>
									</div>
								</li>
								<li>
									<h3>Class History</h3>
									<ul>
										<xsl:apply-templates select="ClassHistory/Class" mode="classHistory"/>
									</ul>
								</li>
							</ul>
						</form>
					</li>
	
					
				</ul>
			</xsl:if>
			<h2><a href="Query">Query</a></h2>
			<h2><a href="tutorial-web.html">Web Tutorial</a></h2>
			<h2><a href="tutorial-cl.html">Command Line Tutorial</a></h2>
			<h2><a href="license.html">License</a></h2>
		</div>
	</xsl:template>
	
	<xsl:template match="Messages">
		<h2 class="title">Messages</h2>
		<div class="entry">
			<ul>
				<xsl:apply-templates select="Message"/>		
			</ul>
		</div>
		<div style="clear: both;"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></div>
	</xsl:template>
	
	<xsl:template match="Message"> 
		<li><xsl:value-of select="text()"/></li>
	</xsl:template>
	
	<xsl:template match="Errors">
		<h2 class="title">Errors</h2>
		<div class="entry">
			<ul>
				<xsl:apply-templates select="Error"/>		
			</ul>
		</div>
		<div style="clear: both;"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></div>
	</xsl:template>
	
	<xsl:template match="Error"> 
		<li><xsl:value-of select="text()"/></li>
	</xsl:template>
	
	<xsl:template match="Field" mode="result">
		<li><xsl:value-of select="@name"/> - <xsl:value-of select="@value"/></li>
	</xsl:template>
	
	<xsl:template match="Class" mode="result">
		<li>
			<xsl:value-of select="@name"/><xsl:if test="@value"> - <xsl:value-of select="@value"/></xsl:if> 
			<ul>
				<xsl:apply-templates select="Class" mode="result"/>
				<xsl:apply-templates select="Field" mode="result"/>
			</ul>
		</li>
	</xsl:template>

		
</xsl:stylesheet>