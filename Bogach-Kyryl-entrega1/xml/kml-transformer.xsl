<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" indent="yes" encoding="UTF-8"/>

	<xsl:template match="/">
	
		<kml xmlns="http://www.opengis.net/kml/2.2">
			<xsl:for-each select="geonames/geoname">
				<Placemark>
					<name>
						<xsl:value-of select="name" />
					</name>
					<description>
						<xsl:value-of select="toponymName" />
					</description>
					<Point>
						<coordinates><xsl:value-of select="lng" />,<xsl:value-of select="lat" />,0</coordinates>
					</Point>
				</Placemark>
			</xsl:for-each>
		</kml>
		
	</xsl:template>
	
	
</xsl:stylesheet>

