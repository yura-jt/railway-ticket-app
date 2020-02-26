<%@include file="template/resources.jsp" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="${sessionScope.lang}" class="no-js">
<head>
    <%@include file="template/head.jsp" %>
    <style>
        <%@ include file="/css/item.css" %>
    </style>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css"/>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css"/>

    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
    <style>
        <%@ include file="/css/row.css" %>
    </style>
    <style>
        <%@ include file="/css/item.css" %>
    </style>

    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
    <script src="http://cdn.jsdelivr.net/timepicker.js/latest/timepicker.min.js"></script>
    <link href="http://cdn.jsdelivr.net/timepicker.js/latest/timepicker.min.css" rel="stylesheet"/>

</head>

<body>
<div class="container">
    <img src="http://i.piccy.info/i9/b869ed99199dbc216732e7b323ecd45f/1582457398/16646/1364039/train_logo_400px.jpg"
         alt="" width="399" height="98"/>
    <header class="clearfix">

        <span><fmt:message key="app.title"/></span>

        <nav>
            <a href="<my:replaceParameter name='lang' value='en' />" class="bp-icon bp-icon-next"
               data-info="<fmt:message key="switch.en" />"><span><fmt:message key="switch.en"/></span></a>
            <a href="<my:replaceParameter name='lang' value='ua' />" class="bp-icon bp-icon-drop"
               data-info="<fmt:message key="switch.ua" />"><span><fmt:message key="switch.ua"/></span></a>
            <a href="<my:replaceParameter name='lang' value='ru' />" class="bp-icon bp-icon-archive"
               data-info="<fmt:message key="switch.ru" />"><span><fmt:message
                    key="switch.ru"/></span></a>
        </nav>
    </header>
    <div class="main">
        <nav class="cbp-hsmenu-wrapper" id="cbp-hsmenu-wrapper">
            <div class="cbp-hsinner">
                <ul class="cbp-hsmenu">
                    <li><a href="orderForm"><fmt:message key="profile.make.order"/></a></li>
                    <li><a href="trains"><fmt:message key="profile.tran.schedule"/></a></li>
                    <li><a href="tickets"><fmt:message key="profile.my.tickets"/></a></li>
                    <li><a href="bills"><fmt:message key="profile.my.bills"/></a></li>
                    <li><a href="orders"><fmt:message key="profile.my.orders"/></a></li>
                    <li><a href="logout"><fmt:message key="profile.log.out"/></a></li>
                </ul>
            </div>
        </nav>
    </div>

    <h3><span style="color: #266b9c; "><fmt:message key="order.success"/></span></h3>


</div>
</body>
</html>