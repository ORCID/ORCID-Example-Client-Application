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
    <h1>Author info</h1>
    <div>ORCID: <c:out value="${orcid}"/></div>
    <div>Name: <c:out value="${full_name}"/>/<c:out value="${family_name}"/>,<c:out value="${given_names}"/>/<c:out value="${vocative_name}"/></div>
    <div>Other names<c:forEach items="${otherNames}" var="name">: <c:out value="${name}"/></c:forEach></div>
    <div>Email: <c:out value="${email}"/></div>
    <div>URLs<c:forEach items="${personalUrls}" var="url">: <a href="<c:url value="${url}"/>"><c:out value="${url}"/></a></c:forEach></div>
    <div>Institution: <c:out value="${institution_name}"/></div>    
  </div>
  
  <p class="footer">© ORCID 2011</p>
  
</div>
</body>
</html>