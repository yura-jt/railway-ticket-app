<%@include file="../template/resources.jsp" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

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
         alt=""  width="399" height="98"/>
    <header class="clearfix">

        <span><fmt:message key="app.title" /></span>
        <h1><fmt:message key="admin.orders.title" /></h1>

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

    <div class="row col-md-6">

        <table class="table table-striped table-bordered table-sm" >
            <tr>
                <th style="width: 10px ; background-color:dodgerblue"><span style="color: white; ">id</span></th>
                <th style="width: 10px ; background-color:dodgerblue"><span style="color: white; "><fmt:message key="order.departure" /></span></th>
                <th style="width: 10px ; background-color:dodgerblue"><span style="color: white; "><fmt:message key="order.destination" /></span></th>
                <th style="width: 10px ; background-color:dodgerblue"><span style="color: white; "><fmt:message key="order.date" /></span></th>
                <th style="width: 10px ; background-color:dodgerblue"><span style="color: white; "><fmt:message key="order.created" /></span></th>
            </tr>

            <c:forEach items="${orders}" var="order">
                <tr>
                    <td><span style="color: black; ">${order.getId()}</span></td>
                    <td><span style="color: black; ">${order.getDepartureStation()}</span></td>
                    <td><span style="color: black; ">${order.getDestinationStation()}</span></td>
                    <td><span style="color: black; ">${order.getDepartureDate()}</span></td>
                    <td><span style="color: black; ">${order.getStatus()}</span></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <nav aria-label="Navigation for countries">
        <ul class="pagination">
            <c:if test="${page != 1}">
                <li class="page-item"><a class="page-link"
                                         href="adminOrders?lang=${lang}&page=${page-1}"><fmt:message key="prev" /></a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${page eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                                                 href="adminOrders?lang=${lang}&page=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${page lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="adminOrders?lang=${lang}&page=${page+1}"><fmt:message key="next" /></a>
                </li>
            </c:if>
        </ul>
    </nav>

    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>



</div>
</body>
</html>