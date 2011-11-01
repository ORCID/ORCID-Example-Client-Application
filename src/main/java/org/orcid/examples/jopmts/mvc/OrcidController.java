/**
 * =============================================================================
 * Copyright (C) 2011 by ORCID
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

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.orcid.examples.jopmts.OrcidService;
import org.orcid.examples.jopmts.impl.NamespaceContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;

/**
 * @author Will Simpson
 */
@Controller
public class OrcidController {

    private OrcidService orcidService;

    @RequestMapping("/orcid/info")
    public String orcidInfo(Model model) throws Exception {
        Document orcidDocument = orcidService.getOrcidDocument();
        XPath xpath = createXPath();
        model.addAttribute("full_name", xpath.evaluate("//o:personal-details/o:full-name", orcidDocument));
        model.addAttribute("email", xpath.evaluate("//o:email", orcidDocument));
        model.addAttribute("institution_name", xpath.evaluate("//o:primary-institution/o:primary-institution-name", orcidDocument));
        return "orcid";
    }

    public void setOrcidService(OrcidService orcidService) {
        this.orcidService = orcidService;
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
