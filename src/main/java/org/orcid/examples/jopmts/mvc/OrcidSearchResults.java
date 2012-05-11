package org.orcid.examples.jopmts.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;

import org.w3c.dom.Node;

public class OrcidSearchResults extends OrcidClientModel {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String,String> modelStringPaths;
	private static final Map<String, String> modelStringArrayPaths;
	static {
		Map<String, String> map1 = new HashMap<String, String>();
		modelStringPaths = Collections.unmodifiableMap(map1);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("orcids", "//o:orcid-profile/o:orcid");
		map2.put("names", "//o:orcid-profile/o:orcid-bio/o:personal-details/o:credit-name");
		modelStringArrayPaths = Collections.unmodifiableMap(map2);
	}
	
	protected Map<String, String> getModelStringPaths() {
		return modelStringPaths;
	}
	
	protected Map<String, String> getModelStringArrayPaths() {
		return modelStringArrayPaths;
	}
	
	public OrcidSearchResults(Node partialDocument, XPath xpath) {
		super(partialDocument, xpath);
	}
}
