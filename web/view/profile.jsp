<%@include file="template/resources.jsp" %>
<!DOCTYPE html>
<html lang="${sessionScope.lang}" class="no-js">
	<head>
		<%@include file="template/head.jsp" %>
	</head>
	<body>
		<div class="container">
			<img src="http://i.piccy.info/i9/b869ed99199dbc216732e7b323ecd45f/1582457398/16646/1364039/train_logo_400px.jpg"
				 alt=""  width="399" height="98"/>
			<header class="clearfix">

				<span><fmt:message key="app.title" /></span>
				<h1><fmt:message key="profile.title" /></h1>

				<nav>
					<a href="profile?lang=en" class="bp-icon bp-icon-next" data-info="<fmt:message key="switch.en" />"><span><fmt:message key="switch.en" /></span></a>
					<a href="profile?lang=ua" class="bp-icon bp-icon-drop" data-info="<fmt:message key="switch.ua" />"><span><fmt:message key="switch.ua" /></span></a>
					<a href="profile?lang=ru" class="bp-icon bp-icon-archive" data-info="<fmt:message key="switch.ru" />"><span><fmt:message key="switch.ru" /></span></a>
				</nav>
			</header>	
			<div class="main">
				<nav class="cbp-hsmenu-wrapper" id="cbp-hsmenu-wrapper">
					<div class="cbp-hsinner">
						<ul class="cbp-hsmenu">
							<li><a href="#"><fmt:message key="profile.make.order" /></a></li>
							<li><a href="trains"><fmt:message key="profile.tran.schedule" /></a></li>
							<li><a href="tickets"><fmt:message key="profile.my.tickets" /></a></li>
							<li><a href="bills"><fmt:message key="profile.my.bills" /></a></li>
							<li><a href="orders"><fmt:message key="profile.my.orders" /></a></li>
							<li><a href="logout"><fmt:message key="profile.log.out" /></a></li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
	</body>
</html>