<%@include file="template/resources.jsp" %>
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
            <a href="orderForm?lang=en" class="bp-icon bp-icon-next"
               data-info="<fmt:message key="switch.en" />"><span><fmt:message key="switch.en"/></span></a>
            <a href="orderForm?lang=ua" class="bp-icon bp-icon-drop"
               data-info="<fmt:message key="switch.ua" />"><span><fmt:message key="switch.ua"/></span></a>
            <a href="orderForm?lang=ru" class="bp-icon bp-icon-archive"
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

    <div class="login-form">
        <div class="main-div">
            <div class="panel">

                <h3><span style="color: #266b9c; "><fmt:message key="make.order.title"/></span></h3>

                <form class="form-horizontal" action="makeOrder">
                    <fieldset>
                        <div class="form-group row">
                            <label class="col-md-4 control-label"><fmt:message key="make.order.departure"/></label>
                            <c:if test="${departureError != null}"><fmt:message key="station.name.invalid"/></c:if>
                            <div class="col-md-4">
                                <input type="text" name = "departure" class="form-control" id="departure">
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-md-4 control-label"><fmt:message key="make.order.destination"/></label>
                            <c:if test="${destinationError != null}"><fmt:message key="station.name.invalid"/></c:if>
                            <div class="col-md-4">
                                <input type="text" name = "destination" class="form-control" id="destination">
                            </div>
                        </div>


                        <div class="form-group row">
                            <label class="col-md-4 control-label"><fmt:message key="make.order.fromTime"/></label>
                            <div class="col-md-4">
                                <div>
                                    <input type="time" id="appt" name="from_time"
                                           value="00:00" required>
                                </div>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-md-4 control-label"><fmt:message key="make.order.toTime"/></label>
                            <c:if test="${timeError != null}"><fmt:message key="time.invalid"/></c:if>
                            <div class="col-md-4">
                                <div>
                                    <input type="time" id="to_time" name="to_time"
                                           value="23:59" required>
                                </div>
                            </div>
                        </div>

                        <script>
                            $(document).ready(function () {
                                $('#datePicker')
                                    .datepicker({
                                        format: 'mm/dd/yyyy'
                                    })
                                    .on('changeDate', function (e) {
                                        // Revalidate the date field
                                        $('#eventForm').formValidation('revalidateField', 'date');
                                    });

                                $('#eventForm').formValidation({
                                    framework: 'bootstrap',
                                    icon: {
                                        valid: 'glyphicon glyphicon-ok',
                                        invalid: 'glyphicon glyphicon-remove',
                                        validating: 'glyphicon glyphicon-refresh'
                                    },
                                    fields: {
                                        name: {
                                            validators: {
                                                notEmpty: {
                                                    message: 'The name is required'
                                                }
                                            }
                                        },
                                        date: {
                                            validators: {
                                                notEmpty: {
                                                    message: 'The date is required'
                                                },
                                                date: {
                                                    format: 'MM/DD/YYYY',
                                                    message: 'The date is not a valid'
                                                }
                                            }
                                        }
                                    }
                                });
                            });
                        </script>

                        <div class="form-group row">
                            <label class="col-md-4 control-label"><fmt:message key="make.order.date"/></label>
                            <c:if test="${dateError != null}"><fmt:message key="date.invalid"/></c:if>
                            <div class="col-xs-5 date">
                                <div class="input-group input-append date row" id="datePicker">
                                    <input type="text" class="form-control" name="date"/>
                                    <span class="input-group-addon add-on"><span
                                            class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </div>
                        </div>


                        <div class="form-group row">
                            <label class="col-md-4 control-label" for="singlebutton"></label>
                            <div class="col-md-4">
                                <button id="singlebutton" name="singlebutton" class="btn btn-primary"><fmt:message key="make.order.find"/>
                                </button>
                            </div>
                        </div>

                    </fieldset>
                </form>


            </div>
        </div>
    </div>


</div>
</body>
</html>