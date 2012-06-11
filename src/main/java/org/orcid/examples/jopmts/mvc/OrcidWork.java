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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.ui.Model;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OrcidWork extends OrcidClientModel {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String,String> modelStringPaths;
	private static final Map<String, String> modelStringArrayPaths;
	static {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("type", "o:ref-type");
		map1.put("title", "o:titles/o:title");
		map1.put("isbn", "o:isbn");
		map1.put("issn", "o:issn");
		map1.put("doi", "o:electronic-resource-num[@type=\"doi\"]");
		map1.put("pmid", "o:electronic-resource-num[@type=\"pmid\"]");
		map1.put("year", "o:publication-date/o:year");
		map1.put("month", "o:publication-date/o:month");
		map1.put("day", "o:publication-date/o:day");
		map1.put("coverDate", "o:cover-date");
		map1.put("publisher", "o:publisher");
		modelStringPaths = Collections.unmodifiableMap(map1);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("otherTitles", "o:titles/o:secondary-title");
		map2.put("relatedUrls", "o:related-urls/o:url");
		modelStringArrayPaths = Collections.unmodifiableMap(map2);
	}
	
	protected Map<String, String> getModelStringPaths() {
		return modelStringPaths;
	}
	
	protected Map<String, String> getModelStringArrayPaths() {
		return modelStringArrayPaths;
	}
	
	public OrcidWork(Node partialDocument, XPath xpath) {
		super(partialDocument, xpath);
		try {
			this.addAttribute("authors", parseAuthors(xpath, partialDocument));
		} catch (XPathExpressionException xpe) {
			// Error handling!
		}
	}
	
	public static List<Model> parseAuthors(XPath xpath, Node orcidDocument) throws XPathExpressionException {
		NodeList authors = (NodeList) xpath.evaluate("o:contributors/o:authors/o:author", orcidDocument, XPathConstants.NODESET);
        ArrayList<Model> authList = new ArrayList<Model>();
        for (int i = 0; i < authors.getLength(); i++) {
        	Node author = authors.item(i);
        	Model authorModel = new OrcidAuthor(author, xpath);
        	authList.add(authorModel);
        }
		return authList;
	}
}
