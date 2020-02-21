<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<html lang="${sessionScope.lang}">
<!DOCTYPE html>
<head>
  <title>railway-ticket-booking/login</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
  <style>
    <%@ include file="/css/main.css" %>
  </style>
</head>
<body>
<div class="container h-100">

  <div class="d-flex justify-content-center h-100">
    <div class="card-header">
      <h3> <fmt:message key="app.title" /> </h3>
      <h5>
      <ul>
        <li><a href="loginForm?lang=en"><fmt:message key="switch.en" /></a></li>
        <li><a href="loginForm?lang=ua"><fmt:message key="switch.ua" /></a></li>
        <li><a href="loginForm?lang=ru"><fmt:message key="switch.ru" /></a></li>
      </ul>
        </h5>
    </div>
<%--    <ul class="navbar-nav my-2 my-lg-0">--%>
    <div class="user_card">
      <div class="d-flex justify-content-center">

        <div class="brand_logo_container">
          <img src="https://images.vexels.com/media/users/3/137010/isolated/preview/e9d76cc17bd895579486b1b9ad053444-train-circle-icon-by-vexels.png" class="brand_logo" alt="Logo">
        </div>
      </div>
      <div class="d-flex justify-content-center form_container">
        <form method="post" action="login">
          <div class="input-group mb-3">
            <div class="input-group-append">
              <span class="input-group-text"><i class="fas fa-user"></i></span>
            </div>
            <input type="text" name="email" class="form-control input_user" value="" placeholder="e-mail">
          </div>
          <div class="input-group mb-2">
            <div class="input-group-append">
              <span class="input-group-text"><i class="fas fa-key"></i></span>
            </div>
            <input type="password" name="password" class="form-control input_pass" value="" placeholder="<fmt:message key="password" />">
          </div>
          <div class="form-group">

          </div>
          <div class="d-flex justify-content-center mt-3 login_container">
            <button type="submit" href="login" name="loginButton" class="btn login_btn"><fmt:message key="login.button" /></button>
<%--                  <td><input type="submit" class="button" name="btnLogin" value=<fmt:message key="login.button"/> /></td>--%>
          </div>
        </form>
      </div>

      <div class="mt-4">
        <div class="d-flex justify-content-center links">
          <fmt:message key="dont.have.account" /> <a href="#" class="ml-2">
            <div align="center"><a href="registrationForm"><fmt:message key="register"/></a></div>
        </a>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- YOUR CODE HERE -->
</body>
</html>