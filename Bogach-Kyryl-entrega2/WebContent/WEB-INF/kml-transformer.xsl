<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" indent="yes" encoding="UTF-8" />
	
	<!-- Miramos cuantas hay para construir el documento -->
	<xsl:variable name="contadorResultado" select="count(geonames/geoname)"/>

	<xsl:template match="/">
		<kml xmlns="http://www.opengis.net/kml/2.2">
			<xsl:choose>
				
				<!-- Si no hay, lo dejamos vacío -->
				<xsl:when test="$contadorResultado &lt; 1"/>
				
			
				<!-- Si hay uno ponemos 'Placemark' directamente -->
				<xsl:when test="$contadorResultado = 1">
					<Placemark>
						<name>
							<xsl:value-of select="//name" />
						</name>
						<description>
							<xsl:value-of select="//toponymName" />
						</description>
						<Point>
							<coordinates><xsl:value-of select="//lng" />,<xsl:value-of select="//lat" />,0</coordinates>
						</Point>
					</Placemark>
				</xsl:when>

				<!-- Si hay más de uno, usamos primero 'Document' -->
				<xsl:otherwise>
					<Document>
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
					</Document>
				</xsl:otherwise>
				
				
			</xsl:choose>

		</kml>

	</xsl:template>


</xsl:stylesheet>

