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
package org.orcid.examples.jopmts.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.dom.DOMSource;

import org.orcid.examples.jopmts.OrcidException;
import org.orcid.examples.jopmts.OrcidService;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

/**
 * @author Arthur Smith
 * 
 *         This implements the OrcidService queries on the Tier-1 API (not
 *         requiring OAuth2 authentication)
 */
public class OrcidServicePublicImpl implements OrcidService {

    private String orcidInfoURL;
    private String orcidSearchURL;
    private RestTemplate orcidRestTemplate;

    public Document getOrcidDocument() throws OrcidException {
        throw new OrcidException("there is no default ORCID to fetch with tier-1");
    }

    public Document getOrcidDocument(String orcid) {
        String url = String.format(orcidInfoURL, orcid);

        return fetchDocument(url);
    }

    public Document searchOrcid(Map<String, String> searchTerms) {
        StringBuilder query = new StringBuilder();

        for (String field : searchTerms.keySet()) {
            if (query.length() > 0)
                query.append(" AND ");
            query.append(field + ":" + searchTerms.get(field));
        }
        return searchOrcid(query.toString());
    }

    public Document searchOrcid(String query) {
        String url = orcidSearchURL + "?q=" + query;
        return fetchDocument(url);
    }

    private Document fetchDocument(String url) {
        DOMSource orcidInput = orcidRestTemplate.getForObject(url, DOMSource.class);
        return (Document) orcidInput.getNode();
    }

    public void setOrcidInfoURL(String infoURL) {
        this.orcidInfoURL = infoURL;
    }

    public void setOrcidSearchURL(String searchURL) {
        this.orcidSearchURL = searchURL;
    }

    public void setOrcidRestTemplate(RestTemplate orcidRestTemplate) {
        this.orcidRestTemplate = orcidRestTemplate;
    }

}
