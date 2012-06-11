/**
 * =============================================================================
 * Copyright (C) 2011-12 by ORCID
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =============================================================================
 */
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