<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<html lang="${sessionScope.lang}">

<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.css" rel="stylesheet"/>
    <style>
        <%@ include file="/css/user.css" %>
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div id="admin-sidebar" class="col-md-2 p-x-0 p-y-3">
            <ul class="sidenav admin-sidenav list-unstyled">
                <li><a href="#"><fmt:message key="profile.make.order" /></a></li>
                <li><a href="trains"><fmt:message key="profile.tran.schedule" /></a></li>
                <li><a href="tickets"><fmt:message key="profile.my.tickets" /></a></li>
                <li><a href="orders"><fmt:message key="profile.my.orders" /></a></li>
                <li><a href="bills"><fmt:message key="profile.my.bills" /></a></li>
                <li><a href="logout"><fmt:message key="profile.log.out" /></a></li>
            </ul>
        </div> <!-- /#admin-sidebar -->
        <div id="admin-main-control" class="col-md-10 p-x-3 p-y-1">
            <div class="content-title m-x-auto">
                <i class="fa fa-dashboard"></i> <fmt:message key="user.welcome" />
            </div>
            <p class="display-4"><fmt:message key="profile.title" /></p>
            </p>
        </div>
        <div id="main-control" class="col-md-10 p-x-3 p-y-5">
            <div class="content-title m-x-auto">
                <ul>
                    <li><a href="profile?lang=en"><fmt:message key="switch.en" /></a></li>
                    <li><a href="profile?lang=ua"><fmt:message key="switch.ua" /></a></li>
                    <li><a href="profile?lang=ru"><fmt:message key="switch.ru" /></a></li>
                </ul>
            </div>
            </p>
        </div><!-- /#admin-main-control -->
    </div> <!-- /.row -->
</div> <!-- /.container-fluid -->
</body>