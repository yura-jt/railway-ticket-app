<%@include file="template/resources.jsp" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="${sessionScope.lang}" class="no-js">
<head>
    <%@include file="template/head.jsp" %>
    <style>
        <%@ include file="/css/item.css" %>
    </style>
    <style>
        <%@ include file="/css/row.css" %>
    </style>
    <style>
        <%@ include file="/css/item.css" %>
    </style>
</head>

<body>
<div class="container">
    <img src="http://i.piccy.info/i9/b869ed99199dbc216732e7b323ecd45f/1582457398/16646/1364039/train_logo_400px.jpg"
         alt="" width="399" height="98"/>
    <header class="clearfix">

        <span><fmt:message key="app.title"/></span>
        <h1><fmt:message key="orders.title" /></h1>

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
    <h5>
        <a href="profile" style="background-color:#4390ff;border:1px solid #4390ff;border-radius:3px;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:16px;line-height:44px;text-align:center;text-decoration:none;width:150px;-webkit-text-size-adjust:none;mso-hide:all;"> &larr; <fmt:message key="proceed.login" /></a>
    </h5>
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
                                         href="orders?lang=${lang}&page=${page-1}"><fmt:message key="prev" /></a>
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
                                                 href="orders?lang=${lang}&page=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${page lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="orders?lang=${lang}&page=${page+1}"><fmt:message key="next" /></a>
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