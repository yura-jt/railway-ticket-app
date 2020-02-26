<%@include file="../template/resources.jsp" %>
<!DOCTYPE html>
<html lang="${sessionScope.lang}" class="no-js">
<head>
    <%@include file="../template/head.jsp" %>
    <style>
        <%@ include file="/css/admin.css" %>
    </style>
</head>
<body>
<div class="container">
    <img src="http://i.piccy.info/i9/b869ed99199dbc216732e7b323ecd45f/1582457398/16646/1364039/train_logo_400px.jpg"
         alt="" width="399" height="98"/>
    <header class="clearfix">

        <span><fmt:message key="app.title"/></span>
        <h1><fmt:message key="admin.panel"/></h1>

        <nav>
            <a href="adminPanel?lang=en" class="bp-icon bp-icon-next" data-info="<fmt:message key="switch.en" />"><span><fmt:message
                    key="switch.en"/></span></a>
            <a href="adminPanel?lang=ua" class="bp-icon bp-icon-drop" data-info="<fmt:message key="switch.ua" />"><span><fmt:message
                    key="switch.ua"/></span></a>
            <a href="adminPanel?lang=ru" class="bp-icon bp-icon-archive"
               data-info="<fmt:message key="switch.ru" />"><span><fmt:message key="switch.ru"/></span></a>
        </nav>
    </header>
    <div class="main">
        <nav class="cbp-hsmenu-wrapper" id="cbp-hsmenu-wrapper">
            <div class="cbp-hsinner">
                <ul class="cbp-hsmenu">
                    <li><a href="adminUsers"><fmt:message key="admin.manage.users"/></a></li>
                    <li><a href="adminBills"><fmt:message key="admin.manage.bills"/></a></li>
                    <li><a href="adminOrders"><fmt:message key="admin.manage.orders"/></a></li>
                    <li><a href="adminTickets"><fmt:message key="admin.manage.tickets"/></a></li>
                    <li><a href="adminTrains"><fmt:message key="admin.manage.trains"/></a></li>
                    <li><a href="logout"><fmt:message key="admin.log.out"/></a></li>
                </ul>
            </div>
        </nav>
    </div>
</div>
</body>
</html>