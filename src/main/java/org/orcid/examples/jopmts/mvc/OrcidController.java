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

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.orcid.examples.jopmts.OrcidService;
import org.orcid.examples.jopmts.impl.NamespaceContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;

/**
 * @author Will Simpson
 */
@Controller
public class OrcidController {
    private OrcidService tier1Service;
    private OrcidService tier2Service;

    public void setOrcidService(OrcidService orcidService) {
        this.tier2Service = orcidService;
    }
    
    public void setOrcidServicePublic(OrcidService orcidService) {
        this.tier1Service = orcidService;
    }

    @RequestMapping("/orcid/info")
    public String orcidInfo(Model model) throws Exception {
        Document orcidDocument = tier2Service.getOrcidDocument();
        model.addAttribute("full_orcid_profile", documentXml(orcidDocument));
        
        XPath xpath = createXPath();
        model.mergeAttributes(new OrcidProfile(orcidDocument, xpath));
        
        return "orcid";
    }
    
    
    @RequestMapping("/orcid/search")
    public String orcidSearch(@RequestParam("text") String text, Model model) throws Exception {
    	Map<String, String> searchTerms = new HashMap<String,String>();
    	searchTerms.put("text", text);
        Document orcidDocument = tier1Service.searchOrcid(searchTerms);
        
        XPath xpath = createXPath();
        model.mergeAttributes(new OrcidSearchResults(orcidDocument, xpath));
        
        return "searchResults";
    }

	private String documentXml(Document orcidDocument)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(orcidDocument);
        trans.transform(source, result);
        String xmlString = sw.toString();
		return xmlString;
	}

    @RequestMapping("/orcid/work")
    public String workInfo(@RequestParam("workNum") int workNum, Model model) throws Exception {
        Document orcidDocument = tier2Service.getOrcidDocument();
        XPath xpath = createXPath();
        List<Model> pubs = OrcidProfile.parsePublications(xpath, orcidDocument);
        model.mergeAttributes(pubs.get(workNum).asMap());
        return "work";
    }
    
    @RequestMapping("/orcid/author")
    public String authorInfo(@RequestParam("workNum") int workNum, @RequestParam("authorNum") int authorNum, Model model) throws Exception {
        Document orcidDocument = tier2Service.getOrcidDocument();
        XPath xpath = createXPath();
        List<Model> pubs = OrcidProfile.parsePublications(xpath, orcidDocument);
        List<Model> authors = (List<Model>) pubs.get(workNum).asMap().get("authors");
        model.mergeAttributes(authors.get(authorNum).asMap());
        return "author";
    }

    @RequestMapping("/orcid/record")
    public String orcidRecord(@RequestParam("orcid") String orcid, Model model) throws Exception {
        Document orcidDocument = tier1Service.getOrcidDocument(orcid);
        model.addAttribute("full_orcid_profile", documentXml(orcidDocument));
        
        XPath xpath = createXPath();
        model.mergeAttributes(new OrcidProfile(orcidDocument, xpath));
        
        return "record";
    }
    
    @RequestMapping("/orcid/record/work")
    public String workForOrcid(@RequestParam("orcid") String orcid, @RequestParam("workNum") int workNum, Model model) throws Exception {
        Document orcidDocument = tier1Service.getOrcidDocument(orcid);
        XPath xpath = createXPath();
        List<Model> pubs = OrcidProfile.parsePublications(xpath, orcidDocument);
        model.mergeAttributes(pubs.get(workNum).asMap());
        return "work";
    }
    
    @RequestMapping("/orcid/record/author")
    public String authorForOrcid(@RequestParam("orcid") String orcid, @RequestParam("workNum") int workNum, @RequestParam("authorNum") int authorNum, Model model) throws Exception {
        Document orcidDocument = tier1Service.getOrcidDocument(orcid);
        XPath xpath = createXPath();
        List<Model> pubs = OrcidProfile.parsePublications(xpath, orcidDocument);
        List<Model> authors = (List<Model>) pubs.get(workNum).asMap().get("authors");
        model.mergeAttributes(authors.get(authorNum).asMap());
        return "author";
    }

    private XPath createXPath() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(createNamespaceContext());
        return xpath;
    }

    private NamespaceContext createNamespaceContext() {
        NamespaceContextImpl namespaceContext = new NamespaceContextImpl();
        namespaceContext.addNamespace("o", "http://www.orcid.org/ns/orcid");
        return namespaceContext;
    }
}
