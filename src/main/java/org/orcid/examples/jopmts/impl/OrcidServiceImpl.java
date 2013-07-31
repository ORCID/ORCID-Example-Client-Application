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

import java.util.Map;

import javax.xml.transform.dom.DOMSource;

import org.orcid.examples.jopmts.OrcidException;
import org.orcid.examples.jopmts.OrcidService;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.w3c.dom.Document;

/**
 * @author Will Simpson
 */
public class OrcidServiceImpl implements OrcidService {
    private String orcidInfoURL;
    private String orcidSearchURL;
    private OAuth2RestTemplate orcidRestTemplate;

    public Document getOrcidDocument() throws OrcidException {
        return getOrcidDocument(null);
    }

    public Document getOrcidDocument(String orcid) throws OrcidException {
        if (orcid == null) {
            OAuth2AccessToken accessToken = orcidRestTemplate.getAccessToken();
            orcid = (String) accessToken.getAdditionalInformation().get("orcid");
        }
        String url = String.format(orcidInfoURL, orcid);
        return (Document) orcidRestTemplate.getForObject(url, DOMSource.class).getNode();
    }

    public Document searchOrcid(Map<String, String> searchTerms) throws OrcidException {
        StringBuilder query = new StringBuilder();

        for (String field : searchTerms.keySet()) {
            if (query.length() > 0)
                query.append(" AND ");
            query.append(field + ":" + searchTerms.get(field));
        }
        return searchOrcid(query.toString());
    }

    public Document searchOrcid(String query) throws OrcidException {
        String url = orcidSearchURL + "?q=" + query;
        return orcidRestTemplate.getForObject(url, Document.class);
    }

    public void setOrcidInfoURL(String infoURL) {
        this.orcidInfoURL = infoURL;
    }

    public void setOrcidSearchURL(String infoURL) {
        this.orcidSearchURL = infoURL;
    }

    public void setOrcidRestTemplate(OAuth2RestTemplate orcidRestTemplate) {
        this.orcidRestTemplate = orcidRestTemplate;
    }

}
