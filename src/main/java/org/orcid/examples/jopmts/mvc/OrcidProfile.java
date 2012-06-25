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

public class OrcidProfile extends OrcidClientModel {
	static final long serialVersionUID = 1L;
	
	private static final Map<String,String> modelStringPaths;
	private static final Map<String, String> modelStringArrayPaths;
	static {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("orcid", "//o:orcid-profile/o:orcid");
		map1.put("full_name", "//o:personal-details/o:credit-name");
        map1.put("given_names", "//o:personal-details/o:given-names");
        map1.put("family_name", "//o:personal-details/o:family-name");
        map1.put("vocative_name", "//o:personal-details/o:vocative-name");
        map1.put("email", "//o:contact-details/o:email");
        map1.put("institution_name", "//o:primary-institution/o:primary-institution-name");
        
		modelStringPaths = Collections.unmodifiableMap(map1);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("otherNames", "//o:personal-details/o:other-names/o:other-name");
		map2.put("personalUrls", "//o:researcher-urls/o:url");
		map2.put("affiliateInstitutionNames", "//o:affiliate-institutions/o:affiliate-institution/o:affiliate-institution-name");
		map2.put("pastInstitutionNames", "//o:past-institutions/o:past-institution/o:past-institution-name");

		modelStringArrayPaths = Collections.unmodifiableMap(map2);
	}

	protected Map<String, String> getModelStringPaths() {
		return modelStringPaths;
	}
	
	protected Map<String, String> getModelStringArrayPaths() {
		return modelStringArrayPaths;
	}
	
	public OrcidProfile(Node partialDocument, XPath xpath) {
		super(partialDocument, xpath);
		try {
			this.addAttribute("works", parsePublications(xpath, partialDocument));
		} catch (XPathExpressionException xpe) {
			// Error handling!
		}
	}
	
	public static List<Model> parsePublications(XPath xpath, Node orcidDocument) throws XPathExpressionException {
		NodeList works = (NodeList) xpath.evaluate("//o:orcid-works/o:orcid-work", orcidDocument, XPathConstants.NODESET);
        ArrayList<Model> pubList = new ArrayList<Model>();
        for (int i = 0; i < works.getLength(); i++) {
        	Node publication = works.item(i);
        	Model pubModel = new OrcidWork(publication, xpath);
        	pubModel.addAttribute("workNum", Integer.toString(i));
        	pubList.add(pubModel);
        }
		return pubList;
	}
}
