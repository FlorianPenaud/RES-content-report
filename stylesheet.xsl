<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:template match="/">
      <html>
         <body>
            <h1><center>RES CONTENT REPORT</center></h1>
            <h2>RULEAPPS</h2>
            <table border="1">
               <tr bgcolor = "#AC8E88">
                  <th>ruleApp name</th>
                  <th>version</th>
                  <th>description</th>
                  <th>creation Date</th>
                  <th>ruleset Count</th>
               </tr>
               <xsl:for-each select="ruleApps/ruleApp">
                  <tr>
                     <td>
                        <xsl:value-of select="name" />
                     </td>
                     <td>
                        <xsl:value-of select="version" />
                     </td>
                     <td>
                        <xsl:value-of select="description" />
                     </td>
                     <td>
                        <xsl:value-of select="creationDate" />
                     </td>
                     <td>
                        <xsl:value-of select="rulesetCount" />
                     </td>
                  </tr>
               </xsl:for-each>
            </table>
            <h2>RULESETS</h2>
              <xsl:for-each select="ruleApps/ruleApp">
               	<xsl:text>ruleApp name:</xsl:text>
               	<span style = "color:blue;">
               		<xsl:value-of select="name" />
               	</span>
               	<xsl:text>   version:</xsl:text>
               	<span style = "color:blue;">
               		<xsl:value-of select="version" />
               	</span>
               	<xsl:text>&#13;&#10;</xsl:text>
               	<table border="1">
               <tr bgcolor = "#AC8E88">
                  <th>ruleset name</th>
                  <th>version</th>
                  <th>creationDate</th>
                  <th>deployer name</th>
                  <th>decision service name</th>
               </tr>
               <xsl:for-each select="rulesets/ruleset">
                  <tr>
                     <td>
                        <xsl:value-of select="name" />
                     </td>
                     <td>
                        <xsl:value-of select="version" />
                     </td>
                     <td>
                        <xsl:value-of select="creationDate" />
                     </td>
                     <td>
               			<xsl:for-each select = "properties/property[id='decisionservice.deployer.name']"> 
							<xsl:value-of select = "value"/>
               			</xsl:for-each>
                     </td>
                     <td>
               			<xsl:for-each select = "properties/property[id='decisionservice.name']"> 
							<xsl:value-of select = "value"/>
               			</xsl:for-each>
                     </td>
                  </tr>
               </xsl:for-each>
            </table>
            <h1></h1>
            </xsl:for-each>

         </body>
      </html>
   </xsl:template>
</xsl:stylesheet>