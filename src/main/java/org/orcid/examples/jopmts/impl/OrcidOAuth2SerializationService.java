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
package org.orcid.examples.jopmts.impl;

import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2SerializationService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class OrcidOAuth2SerializationService extends DefaultOAuth2SerializationService {

    @Override
    public OAuth2AccessToken deserializeAccessToken(Map<String, String> tokenParams) {
        OrcidOAuth2AccessToken token = new OrcidOAuth2AccessToken(super.deserializeAccessToken(tokenParams));
        if (tokenParams.containsKey("orcid")) {
            token.setOrcid(tokenParams.get("orcid"));
        }
        return token;
    }

}
