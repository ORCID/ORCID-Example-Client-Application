<%--

    =============================================================================
    Copyright (C) 2011 by ORCID

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
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<authz:authorize ifAllGranted="ROLE_USER">
  <c:redirect url="index.jsp"/>
</authz:authorize>
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
      <li><a href="<c:url value="/login.jsp"/>" class="selected">Login</a></li>
      <li><a href="<c:url value="/orcid/info"/>">ORCID info</a></li>
    </ul>

  <div id="content">
    <c:if test="${!empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
      <h1>Woops!</h1>

      <p class="error">Your login attempt was not successful. (<%= ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>)</p>
    </c:if>
    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>

    <authz:authorize ifNotGranted="ROLE_USER">
      <h1>Login</h1>

      <p>JOPMTS has only two users: "josiah" and "truman".  The password for "josiah" is password is "carberry" and for "truman" the password is "grayson".</p>

      <form action="<c:url value="/login.do"/>" method="post">
        <p><label>Username: <input type='text' name='j_username' value="josiah"></label></p>
        <p><label>Password: <input type='text' name='j_password' value="carberry"></label></p>
        
        <p><input name="login" value="Login" type="submit"></p>
      </form>
    </authz:authorize>

    <p class="footer">© ORCID 2011</p>
  </div>
</div>
</body>
</html>