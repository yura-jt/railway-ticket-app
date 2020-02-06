<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title><fmt:message key="app.title"/></title>

</head>
<body>

<h1><fmt:message key="app.title"/></h1>
<h3><font color="red">
  <c:if test="${not empty userNotExists and userNotExists eq 'true'}">
    <fmt:message key="user.absent"/> <!-- учетная запись отсутствует -->
  </c:if>
</font>
</h3>
<c:if test="${empty sessionScope.user}">
  <FORM method="POST">
    <table style="margin: auto">
      <ul>
        <li><a href="?lang=en"><fmt:message key="switch.en" /></a></li>
        <li><a href="?lang=ua"><fmt:message key="switch.ua" /></a></li>
        <li><a href="?lang=ru"><fmt:message key="switch.ru" /></a></li>
      </ul>
      <tr>
        <td style="text-align: left">E-mail:</td>
        <td><input name="email" type="email" size="35" placeholder="E-mail" autocomplete="on" autofocus required /></td>
      </tr>
      <tr>
        <td style="text-align: left"><fmt:message key="password"/>:</td>
        <td><input name="password" type="password" placeholder="<fmt:message key="password"/>" size="35" maxlength="35" required /></td>
      </tr>
      <tr>
        <td><input type="submit" class="button" name="btnLogin" value=<fmt:message key="login.button"/> /></td>
      </tr>
    </table>
  </FORM>
  <div align="center"><a href="registration"><fmt:message key="register"/></a></div>
</c:if>
</body>
</html>