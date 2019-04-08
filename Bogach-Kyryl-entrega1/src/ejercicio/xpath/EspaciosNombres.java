package ejercicio.xpath;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.namespace.NamespaceContext;

public class EspaciosNombres implements NamespaceContext {

	private HashMap<String, String> alias = new HashMap<String, String>();

	public EspaciosNombres() {
		alias.put("ns", "http://www.w3.org/2005/Atom");
		alias.put("openSearch", "http://a9.com/-/spec/opensearchrss/1.0/");
		alias.put("gbs", "http://schemas.google.com/books/2008");
		alias.put("gd", "http://schemas.google.com/g/2005");
		alias.put("batch", "http://schemas.google.com/gdata/batch");
		alias.put("dc", "http://purl.org/dc/terms");
	}

	@Override
	public Iterator<String> getPrefixes(String namespaceURI) {
		return alias.keySet().iterator();
	}

	@Override
	public String getPrefix(String namespaceURI) {
		for (Entry<String, String> entry : alias.entrySet())
			if (entry.getValue().equals(namespaceURI))
				return entry.getKey();

		return null;
	}

	@Override
	public String getNamespaceURI(String prefix) {
		return alias.get(prefix);
	}
}
