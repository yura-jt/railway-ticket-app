<%@include file="template/resources.jsp" %>
<!DOCTYPE html>
<html lang="${sessionScope.lang}" class="no-js">
<head>
    <%@include file="template/head.jsp" %>
    <style>
        <%@ include file="/css/error.css" %>
    </style>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://fonts.googleapis.com/css?family=Kanit:200" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/font-awesome.min.css" />
    <link type="text/css" rel="stylesheet" href="css/style.css" />

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <img src="http://i.piccy.info/i9/b869ed99199dbc216732e7b323ecd45f/1582457398/16646/1364039/train_logo_400px.jpg"
         alt="" width="399" height="98"/>
    <header class="clearfix">

        <span><fmt:message key="app.title"/></span>

        <nav>
            <a href="error?lang=en" class="bp-icon bp-icon-next"
               data-info="<fmt:message key="switch.en" />"><span><fmt:message key="switch.en"/></span></a>
            <a href="error?lang=ua" class="bp-icon bp-icon-drop"
               data-info="<fmt:message key="switch.ua" />"><span><fmt:message key="switch.ua"/></span></a>
            <a href="error?lang=ru" class="bp-icon bp-icon-archive"
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

    <div id="notfound">
        <div class="notfound">
            <div class="notfound-404">
                <h1>403</h1>
            </div>
            <h2><fmt:message key="access.denied.oops" /></h2>
            <p><fmt:message key="access.denied.message" /> <a href="loginForm"><fmt:message key="error.return.homepage" /></a></p>

        </div>
    </div>
</div>
</body>
</html>