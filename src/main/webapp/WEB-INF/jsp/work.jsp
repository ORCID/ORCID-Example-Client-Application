<%--

    =============================================================================
    Copyright (C) 2011-12 by ORCID

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
    =============================================================================

--%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <link href="<c:url value="/main.css"/>" rel="stylesheet" type="text/css"/>
  <title>Journal of Psychoceramics - Manuscript Tracking System</title>
</head>
<body>
<div id="container">

    <ul id="mainlinks">
      <li><a href="<c:url value="/index.jsp"/>">Home</a></li>
      <authz:authorize ifNotGranted="ROLE_USER">
        <li><a href="<c:url value="/login.jsp"/>">Login</a></li>
      </authz:authorize>
      <li><a href="<c:url value="/orcid/info"/>">ORCID info</a></li>
    </ul>

  <div id="content">
    <h1>Work info</h1>
    <div>Type of work: <c:out value="${type}"/></div>
    <div>DOI: <c:out value="${doi}"/></div>
    <div>PMID: <c:out value="${pmid}"/></div>
    <div>Title: <c:out value="${title}"/><br/>
        <ul><c:forEach items="${otherTitles}" var="otherTitle">
        	<li><c:out value="${otherTitle}"/></li>
    	</c:forEach></ul>
    </div>
    <div>Authors:<ul><c:forEach items="${authors}" var="author">
    		<li><c:out value="${author.full_name}"/></li>
    	</c:forEach></ul></div>
    <div></div>
    <div>Publication Date: <c:out value="${year}"/>-<c:out value="${month}"/>-<c:out value="${day}"/></div>
    <div>Publisher: <c:out value="${publisher}"/></div>
    <div>Cover Date: <c:out value="${coverDate}"/></div>
    <div>ISBN: <c:out value="${isbn}"/></div>
    <div>ISSN: <c:out value="${issn}"/></div>
    <div>URLs: <c:forEach items="${relatedUrls}" var="url">: <a href="<c:url value="${url}"/>"><c:out value="${url}"/></a></c:forEach></div>    
  </div>
  
  <p class="footer">© ORCID 2011</p>
  
</div>
</body>
</html>