package org.orcid.examples.jopmts.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;

import org.w3c.dom.Node;

public class OrcidAuthor extends OrcidClientModel {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String,String> modelStringPaths;
	private static final Map<String, String> modelStringArrayPaths;
	static {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("orcid", "o:orcid");
		map1.put("full_name", "o:credit-name");
        map1.put("given_names", "o:given-names");
        map1.put("family_name", "o:family-name");
        map1.put("vocative_name", "o:vocative-name");
		modelStringPaths = Collections.unmodifiableMap(map1);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("otherNames", "o:other-names/o:other-name");
		modelStringArrayPaths = Collections.unmodifiableMap(map2);
	}
	
	protected Map<String, String> getModelStringPaths() {
		return modelStringPaths;
	}
	
	protected Map<String, String> getModelStringArrayPaths() {
		return modelStringArrayPaths;
	}
	
	public OrcidAuthor(Node partialDocument, XPath xpath) {
		super(partialDocument, xpath);
	}
}
