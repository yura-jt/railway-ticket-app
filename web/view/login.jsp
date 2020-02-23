<%@include file="template/resources.jsp" %>
<!DOCTYPE html>
<html lang="${sessionScope.lang}" class="no-js">
<head>
    <%@include file="template/head.jsp" %>
    <style>
        <%@ include file="/css/login.css" %>
    </style>
</head>
<body>
<div class="container">
    <img src="http://i.piccy.info/i9/b869ed99199dbc216732e7b323ecd45f/1582457398/16646/1364039/train_logo_400px.jpg"
         alt="" width="399" height="98"/>
    <header class="clearfix">

        <span><fmt:message key="app.title"/></span>

        <nav>
            <a href="loginForm?lang=en" class="bp-icon bp-icon-next"
               data-info="<fmt:message key="switch.en" />"><span><fmt:message key="switch.en"/></span></a>
            <a href="loginForm?lang=ua" class="bp-icon bp-icon-drop"
               data-info="<fmt:message key="switch.ua" />"><span><fmt:message key="switch.ua"/></span></a>
            <a href="loginForm?lang=ru" class="bp-icon bp-icon-archive"
               data-info="<fmt:message key="switch.ru" />"><span><fmt:message key="switch.ru"/></span></a>
        </nav>
    </header>
    <div class="main">
        <nav class="cbp-hsmenu-wrapper" id="cbp-hsmenu-wrapper">
            <div class="cbp-hsinner">
                <ul class="cbp-hsmenu">

                </ul>
            </div>
        </nav>
    </div>

    <section class="login-block">
        <div class="container">
            <div class="row">
                <div class="col-md-4 login-sec">
                    <h2 class="text-center"><fmt:message key="login.title" /></h2>
                    <form class="login-form" method="post" action="login">
                        <div class="form-group">
                            <label class="text-uppercase">E-mail</label>
                            <input type="text" name="email" class="form-control" placeholder="e-mail">

                        </div>
                        <div class="form-group">
                            <label class="text-uppercase"><fmt:message key="login.password" /></label>
                            <input type="password" name="password" class="form-control" placeholder="<fmt:message key="password" />">
                        </div>

                        <div class="form-check">
                            <button type="submit" class="btn btn-login float-сenter"><fmt:message key="login.button" /></button>
                        </div>
                        <div class="mt-4">
                            <div class="d-flex justify-content-center links">
                                <fmt:message key="dont.have.account" /> <a href="#" class="ml-2">
                                <div align="center"><a href="registrationForm"><fmt:message key="register"/></a></div>
                            </a>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-8 banner-sec">
                    <img class="d-block img-fluid"
                         src="https://picua.org/images/2020/02/23/04ddc8e35c7e114caf318c7b40da15d8.jpg"
                         alt="img"/>
                    <div class="carousel-caption d-none d-md-block">
                        <div class="banner-text">
                            <h2>Book Your Dream</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>