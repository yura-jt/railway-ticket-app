<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages" />

<html lang="${param.lang}">
<!DOCTYPE html>
<html>
<head>
    <title>railway-ticket-booking/registration</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        <%@ include file="/css/main.css" %>
    </style>
</head>
<body>
<div class="container">
    <h5>
        <ul>
            <li><a href="registrationForm?lang=en"><fmt:message key="switch.en" /></a></li>
            <li><a href="registrationForm?lang=ua"><fmt:message key="switch.ua" /></a></li>
            <li><a href="registrationForm?lang=ru"><fmt:message key="switch.ru" /></a></li>
        </ul>
    </h5>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="register.title" /></div>
                <div class="card-body">

                    <form class="form-horizontal" method="post" action="registration">

                        <div class="form-group">
                            <label for="first_name" class="cols-sm-2 control-label"><fmt:message key="first.name.field" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="first_name" id="first_name" placeholder="<fmt:message key="enter.first.name" />" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="cols-sm-2 control-label"><fmt:message key="last.name.field" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="last_name" id="lastname" placeholder="<fmt:message key="enter.last.name" />" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="cols-sm-2 control-label"><fmt:message key="email.field" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="email" id="email" placeholder="<fmt:message key="enter.email" />" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phonenumber" class="cols-sm-2 control-label"><fmt:message key="phone.number.field" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="phone_number" id="phonenumber" placeholder="<fmt:message key="enter.phone.number" />" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="cols-sm-2 control-label"><fmt:message key="password.field" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="enter.password" />" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password_repeated" class="cols-sm-2 control-label"><fmt:message key="confirm.password.field" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" name="password_repeated" id="password_repeated" placeholder="<fmt:message key="enter.confirm.password" />" />
                                </div>
                            </div>
                        </div>
                        <label class="cols-sm-2 control-label"><fmt:message key="mandatory.field" /> </label>
                        <div class="form-group ">
                            <button type="submit" name="registrationButton" class="btn btn-primary btn-lg btn-block login-button"><fmt:message key="register.button" /></button>
<%--                            <input type="submit" value="Submit" />--%>
<%--                            <div align="center"><a href="registration"><fmt:message key="register.button"/></a></div>--%>
                        </div>
                    </form>
                    <script type="text/javascript">
                        <%@include file="/js/validation.js"%>
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>

<%--<div class="register">--%>
<%--    <form method="post" action="${pageContext.request.contextPath}/controller?command=login">--%>
<%--        <p><input type="email" name="email" placeholder="Email" size="22" required/></p>--%>
<%--        <p><input type="password" name="password" placeholder="<fmt:message key="label.password" />"--%>
<%--                  size="22"--%>
<%--                  pattern="[A-Za-zА-Яа-яЁёІіЄєЇї0-9]{5,}" required/></p>--%>
<%--        <p><input type="submit" value="<fmt:message key="label.signIn"/>"/></p>--%>
<%--    </form>--%>