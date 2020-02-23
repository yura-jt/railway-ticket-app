<%@include file="template/resources.jsp" %>
<!DOCTYPE html>
<html lang="${sessionScope.lang}" class="no-js">
<head>
    <%@include file="template/head.jsp" %>
    <style>
        <%@ include file="/css/register.css" %>
    </style>
</head>
<body>
<div class="container">
    <img src="http://i.piccy.info/i9/b869ed99199dbc216732e7b323ecd45f/1582457398/16646/1364039/train_logo_400px.jpg"
         alt="" width="399" height="98"/>
    <header class="clearfix">

        <span><fmt:message key="app.title"/></span>
        <nav>
            <a href="registrationForm?lang=en" class="bp-icon bp-icon-next"
               data-info="<fmt:message key="switch.en" />"><span><fmt:message key="switch.en"/></span></a>
            <a href="registrationForm?lang=ua" class="bp-icon bp-icon-drop"
               data-info="<fmt:message key="switch.ua" />"><span><fmt:message key="switch.ua"/></span></a>
            <a href="registrationForm?lang=ru" class="bp-icon bp-icon-archive"
               data-info="<fmt:message key="switch.ru" />"><span><fmt:message key="switch.ru"/></span></a>
        </nav>
        <h5>
            <a href="loginForm" style="background-color:#4390ff;border:1px solid #4390ff;border-radius:3px;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:16px;line-height:44px;text-align:center;text-decoration:none;width:150px;-webkit-text-size-adjust:none;mso-hide:all;"> &larr; <fmt:message key="proceed.login" /></a>
        </h5>
    </header>
    <div class="main">
        <nav class="cbp-hsmenu-wrapper" id="cbp-hsmenu-wrapper">
            <div class="cbp-hsinner">
                <ul class="cbp-hsmenu">

                </ul>
            </div>
        </nav>
    </div>


    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><h4><fmt:message key="register.title" /></h4></div>
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
</body>
</html>