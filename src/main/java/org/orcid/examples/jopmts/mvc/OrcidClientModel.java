package org.orcid.examples.jopmts.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.ui.ExtendedModelMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class OrcidClientModel extends ExtendedModelMap {
	private static final long serialVersionUID = 1L;
	protected abstract Map<String,String> getModelStringPaths();
	protected abstract Map<String, String> getModelStringArrayPaths();
	
	public OrcidClientModel() {
		super();
	}
	
	public OrcidClientModel(Node partialDocument, XPath xpath) {
		try {
			for (String key: getModelStringPaths().keySet()) {
				this.addAttribute(key, xpath.evaluate(getModelStringPaths().get(key), partialDocument));
			}
			for (String key: getModelStringArrayPaths().keySet()) {
				this.addAttribute(key, stringListFromDocument(getModelStringArrayPaths().get(key), xpath, partialDocument));
			}
		} catch (XPathExpressionException e) {
			// Error handling needed?!
		}
	}

	private List<String> stringListFromDocument(String path, XPath xpath, Node partialDocument) throws XPathExpressionException {
		NodeList stringNodes = (NodeList) xpath.evaluate(path, partialDocument, XPathConstants.NODESET);
		ArrayList<String> stringList = new ArrayList<String>();
		for (int i = 0; i < stringNodes.getLength(); i++) {
			stringList.add(stringNodes.item(i).getTextContent());
		}
		return stringList;
	}
}