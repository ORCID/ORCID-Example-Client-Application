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
package org.orcid.examples.jopmts;

import java.util.Map;

import org.w3c.dom.Document;

/**
 * @author Will Simpson
 */
public interface OrcidService {

    /**
     * Get the ORCID info for the current user.
     * 
     * @return XML document containing orcid info
     */
    Document getOrcidDocument() throws OrcidException;

    /**
     * Get the ORCID info for the specified id
     * 
     * @param orcid
     *            - ID requested
     * @return XML document containing orcid info
     */
    Document getOrcidDocument(String orcid) throws OrcidException;

    /**
     * Search ORCID data
     * 
     * @param searchTerms
     *            - field:value pairs for searching
     * @return XML document containing search results
     */
    Document searchOrcid(Map<String, String> searchTerms) throws OrcidException;

    /**
     * Search ORCID data
     * 
     * @param query
     *            - string form search query
     * @return XML document containing search results
     */
    Document searchOrcid(String query) throws OrcidException;
}
