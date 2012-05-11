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
      <li><a href="<c:url value="/orcid/info"/>" class="selected">ORCID info</a></li>
    </ul>

  <div id="content">
    <h1>ORCID search results</h1>
    <div>ORCIDs:<ul><c:forEach items="${orcids}" var="orcid">
    	<li><a href="/jopmts/orcid/record?orcid=<c:out value="${orcid}"/>"><c:out value="${orcid}"/></a></li>
    </c:forEach></ul></div>
  </div>
  
  <p class="footer">© ORCID 2011</p>
  
</div>
</body>
</html>