<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>My Tickets</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
</head>
<body class="m-3">
<div class="card-header">
    <h3>My tickets</h3>
    <h5>
        <ul>
            <li><a href="<my:replaceParameter name='lang' value='en' />"><fmt:message key="switch.en" /></a></li>
            <li><a href="<my:replaceParameter name='lang' value='ua' />"><fmt:message key="switch.ua" /></a></li>
            <li><a href="<my:replaceParameter name='lang' value='ru' />"><fmt:message key="switch.ru" /></a></li>


        </ul>
    </h5>
</div>
<div class="row col-md-6">
    <div class="alert alert-info">
        <a href="profile" class="btn btn-xs btn-primary pull-right"><fmt:message key="profile.button" /></a>
        <strong><fmt:message key="proceed.profile" /></strong>
    </div>
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th>Id</th>
            <th>Departure station</th>
            <th>Destination station</th>
            <th>Passenger name</th>
            <th>Price</th>
            <th>Created On</th>
        </tr>

        <c:forEach items="${tickets}" var="ticket">
            <tr>
                <td>${ticket.getId()}</td>
                <td>${ticket.getDepartureStation()}</td>
                <td>${ticket.getDestinationStation()}</td>
                <td>${ticket.getPassengerName()}</td>
                <td>${ticket.getPrice()}</td>
                <td>${ticket.getCreatedOn()}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Navigation for countries">
    <ul class="pagination">
        <c:if test="${page != 1}">
            <li class="page-item"><a class="page-link"
                                     href="tickets?lang=${lang}&page=${page-1}"><fmt:message key="prev" /></a>
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
                                             href="tickets?lang=${lang}&page=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${page lt noOfPages}">
            <li class="page-item"><a class="page-link"
                                     href="tickets?lang=${lang}&page=${page+1}"><fmt:message key="next" /></a>
            </li>
        </c:if>
    </ul>
</nav>

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>

</body>
</html>
