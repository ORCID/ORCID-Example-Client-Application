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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;

import org.w3c.dom.Node;

public class OrcidAuthor extends OrcidClientModel {
    private static final long serialVersionUID = 1L;

    private static final Map<String, String> modelStringPaths;
    static {
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("orcid", "o:contributor-orcid");
        map1.put("full_name", "o:credit-name");
        modelStringPaths = Collections.unmodifiableMap(map1);
    }

    protected Map<String, String> getModelStringPaths() {
        return modelStringPaths;
    }

    protected Map<String, String> getModelStringArrayPaths() {
        return Collections.emptyMap();
    }

    public OrcidAuthor(Node partialDocument, XPath xpath) {
        super(partialDocument, xpath);
    }
}
